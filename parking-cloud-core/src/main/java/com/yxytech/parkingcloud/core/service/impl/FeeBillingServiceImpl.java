package com.yxytech.parkingcloud.core.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.FeeRateMultistep;
import com.yxytech.parkingcloud.core.entity.FeeRateRule;
import com.yxytech.parkingcloud.core.entity.FeeSchema;
import com.yxytech.parkingcloud.core.entity.Parking;
import com.yxytech.parkingcloud.core.enums.CarTypeEnum;
import com.yxytech.parkingcloud.core.enums.CycleFeeTypeEnum;
import com.yxytech.parkingcloud.core.enums.CycleTypeEnum;
import com.yxytech.parkingcloud.core.enums.FeeRateEnum;
import com.yxytech.parkingcloud.core.mapper.FeeRateRuleMapper;
import com.yxytech.parkingcloud.core.service.IFeeBillingService;
import com.yxytech.parkingcloud.core.service.IFeeFestivalService;
import com.yxytech.parkingcloud.core.service.IFeeRateMultistepService;
import com.yxytech.parkingcloud.core.service.IFeeRateRuleService;
import com.yxytech.parkingcloud.core.service.IFeeSchemaService;
import com.yxytech.parkingcloud.core.service.IParkingService;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author cj
 * @since 2017-10-29
 */
@Service
@Scope("prototype")
public class FeeBillingServiceImpl extends ServiceImpl<FeeRateRuleMapper, FeeRateRule> implements IFeeBillingService {

	private static Long DAY_MILLIS = 24 * 60 * 60 * 1000l;
	private static Long TIME_ZONE_SPAN = -28800000l;

	@Autowired
	IFeeSchemaService feeSchemaService;

	@Autowired
	IFeeRateRuleService feeRateRuleService;

	@Autowired
	IFeeFestivalService festivalService;

	@Autowired
	IFeeRateMultistepService feeRateMultistepService;

	@Autowired
	IParkingService parkingService;

	Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.ROOT);
	Calendar calendar2 = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"), Locale.CHINESE);
	Calendar calendarFestival = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.CHINESE);

	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

	List<Map<String, Object>> feeItems = new ArrayList<>();

	// 返回结果
	{

		// sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		sdf.setCalendar(calendar);
		sdf2.setCalendar(calendar2);
		sdf3.setCalendar(calendar);
		sdf4.setCalendar(calendar);
		// calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
	}

	Integer gFreeTimes = 0;

	@Override
	public Boolean isInFreeTime(CarTypeEnum carType, Long parkingId, Long enterTime, Long leaveTime, Long payTime)
			throws ParseException {
		if (payTime == null) {
			return true;
		}

		List<Map<String, Object>> billingDurationList = getBillingDurationAndRate(carType, parkingId, enterTime,
				leaveTime);
		Integer len = billingDurationList.size();

		if (payTime > 0L) {
			Map<String, Object> lastDuration = billingDurationList.get(len - 1);
			FeeRateRule lastRate = (FeeRateRule) lastDuration.get("rate");
			if (lastRate.getFreeOutDuration() != null && lastRate.getFreeOutDuration() > 0) {
				if (leaveTime - payTime <= lastRate.getFreeOutDuration() * 60 * 1000) {
					return false;
				}
			}

		}

		return true;
	}

	@Override
	public Map<String, Object> feeBilling(CarTypeEnum vehicle, Long parkingId, Long enterTime, Long leaveTime,
			List<Map<String, String>> freeList, Integer freeTimes, Map<String, Date> freeValidTime, Long payTime)
					throws ParseException {
		return null;
	}

	@Override
	public Map<String, Object> feeBilling(CarTypeEnum vehicle, Long parkingId, String enterTime, String leaveTime,
			List<Map<String, String>> freeTimeList, List<Map<String, Object>> freeTimesList, String payTime)
					throws ParseException {

		Long pay = payTime == null ? null : sdf4.parse(payTime).getTime();
		Long enter = sdf4.parse(enterTime).getTime();
		Long leave = sdf4.parse(leaveTime).getTime();

		return this.feeBilling(vehicle, parkingId, enter, leave, freeTimeList, freeTimesList, pay);
	}

	// 总金额，应收费用，使用次数
	@SuppressWarnings("unchecked")
	// 免费车，有效期，
	// 按次和按时拆分
	@Override
	public Map<String, Object> feeBilling(CarTypeEnum vehicle, Long parkingId, Long enterTime, Long leaveTime,
			List<Map<String, String>> freeTimeList, List<Map<String, Object>> freeTimesList, Long payTime)
					throws ParseException {

		System.out.print(enterTime + "," + leaveTime);
		Map<String, Object> result = new HashMap<>();

		result.put("totalFee", 0d);
		result.put("usedTimes", 0);
		result.put("needFee", 0d);
		// 获取parking
		Parking parking = parkingService.selectById(parkingId);
		// 获取依费率拆分的计费时段
		List<Map<String, Object>> billingDurationList = getBillingDurationAndRate(vehicle, parkingId, enterTime,
				leaveTime);
		int i = 1, j = 0, len = billingDurationList.size();
		long totalNeedDuration = 0l;
		boolean reCalc = true;

		//
		Map<String, Object> firstTempDuration = billingDurationList.get(0);
		FeeRateRule tempRule = (FeeRateRule) firstTempDuration.get("rate");
		if (tempRule.getFreeInDuration() != null && tempRule.getFreeInDuration() > 0) {
			if (leaveTime - enterTime <= tempRule.getFreeInDuration() * 60 * 1000) {
				return result;
			}
		}

		// 获取最后一个计费时段
		if (payTime != null && payTime > 0l) {
			Map<String, Object> lastDuration = billingDurationList.get(len - 1);
			FeeRateRule lastRate = (FeeRateRule) lastDuration.get("rate");
			if (lastRate.getFreeOutDuration() != null && lastRate.getFreeOutDuration() > 0) {
				if (leaveTime - payTime <= lastRate.getFreeOutDuration() * 60 * 1000) {
					reCalc = false;
				}
			}

		}

		if (reCalc) {
			// 计费结果重计算那些切换费率和周期不足计费单位时造成的多余收费
			List<Map<String, Object>> resultList = new ArrayList<>();
			// 遍历计费时段
			for (Map<String, Object> m : billingDurationList) {
				// 结果包含一个计费时段的收费情况
				Map<String, Object> billDuration = billMoneyByFeeDuration(m, i, len, freeTimeList, freeTimesList,
						parking);
				// cycles contains opening time
				List<Map<String, Object>> cycles = (List<Map<String, Object>>) billDuration.get("cycles");

				FeeRateRule rate = (FeeRateRule) m.get("rate");
				// 按次和非24小时营业，不减单位时差，直接返回
				if (rate.getType() == FeeRateEnum.T || !parking.getAllDay()) {
					resultList.add(billDuration);
				} else {
					int n = 0, nSize = cycles.size();
					long cycleNeedDuration = 0;
					if (j < len - 1) {

						for (Map<String, Object> c : cycles) {

							if (c.get("needDuration") == null) {
								break;
							}
							cycleNeedDuration += (long) c.get("needDuration");
							n++;
						}
						totalNeedDuration += cycleNeedDuration;
					}
					if (j == len - 1) {
						for (Map<String, Object> c : cycles) {
							if (n < nSize - 2) {
								// 最后的list 为营业时间,所以计算周期的倒数第三为止，将不再减时间
								if (c.get("needDuration") == null) {
									break;
								}
								cycleNeedDuration += (long) c.get("needDuration");
								n++;
								totalNeedDuration += cycleNeedDuration;
							}
						}
						long start = (long) m.get("start"), end = (long) m.get("end");
						Map<String, Object> map = new HashMap<>();
						// 把多余的收费时间减去，重新计算，如果最后一个收费时段的时间不够前面的不足时间单位总的时间，则把最后一个单位减完，从倒数第二个周期再减
						// 减完再重新计算一次，这次将不再减
						if (totalNeedDuration <= end - start) {
							map.put("start", start);
							map.put("rate", m.get("rate"));
							map.put("end", end - totalNeedDuration);
							billDuration = billMoneyByFeeDuration(map, i, len, freeTimeList, freeTimesList, parking);
						} else {
							Map<String, Object> temp = new HashMap<>();
							if (len == 1) {
								temp = billingDurationList.get(0);
								map.put("end", start + 1000l);
							} else {
								temp = billingDurationList.get(len - 2);
								map.put("start", temp.get("start"));
								map.put("end", (long) temp.get("end") - (totalNeedDuration - (end - start)));
							}
							map.put("start", temp.get("start"));
							map.put("rate", temp.get("rate"));
							billDuration = billMoneyByFeeDuration(map, i, len, freeTimeList, freeTimesList, parking);
						}
					} else {

					}
					resultList.add(billDuration);

				}

				i++;
				j++;
			}

			double totalFee = 0.00, needFee = 0.00;
			int fTimes = 0, lastFreeTimes = 0;

			for (Map<String, Object> r : resultList) {

				List<Map<String, Object>> cyclesList = (List<Map<String, Object>>) r.get("cycles");

				System.out.println(cyclesList);
				System.out.println(cyclesList.size() - 1);

				int index = cyclesList.size() - 1;
				cyclesList.remove(index);

				Integer lastUsedTimes = (Integer) r.get("lastUsedTimes");
				lastUsedTimes = lastUsedTimes == null ? 0 : lastUsedTimes;

				lastFreeTimes += lastUsedTimes;

				for (Map<String, Object> c : cyclesList) {
					Integer freeCounter = (Integer) c.get("freeCounter");

					freeCounter = freeCounter == null ? 0 : freeCounter;
					fTimes += freeCounter;

					if (c.get("isFree") == null || c.get("isClose") == null) {
						totalFee += (double) c.get("money");
					}
					if (c.get("totalMoney") != null) {
						needFee += (double) c.get("totalMoney");
					}
				}
			}

			result.put("needFee", needFee);
			result.put("usedTimes", fTimes);
			result.put("totalFee", totalFee);
			result.put("lastUsedTimes", lastFreeTimes);
			result.put("splitBill", resultList);

		}
		return result;
	}

	@Override
	public Map<String, Object> feeBilling(CarTypeEnum vehicle, Long parkingId, Long enterTime, Long leaveTime,
			List<Map<String, String>> freeList, Integer freeTimes, Map<String, Date> freeValidTime)
					throws ParseException {
		return feeBilling(vehicle, parkingId, enterTime, leaveTime, freeList, freeTimes, freeValidTime, 0l);
	}

	@Override
	public Map<String, Object> feeBilling(CarTypeEnum vehicle, Long parkingId, String enterTime, String leaveTime,
			List<Map<String, String>> freeList, Integer freeTimes, Map<String, Date> freeValidTime, String payTime)
					throws ParseException {

		Long pay = payTime == null ? null : sdf4.parse(payTime).getTime();
		Long enter = sdf4.parse(enterTime).getTime();
		Long leave = sdf4.parse(leaveTime).getTime();

		return feeBilling(vehicle, parkingId, enter, leave, freeList, freeTimes, freeValidTime, pay);
	}

	@Override
	public FeeRateEnum getFeeRate(Long parkingId, Long enterAt, CarTypeEnum carType) throws ParseException {
		// 由入场时间，停车场ID查询收费计划.
		FeeSchema schema = feeSchemaService.getByTime(enterAt, parkingId);

		if (schema == null) {
			throw new ParseException("费率计划不存在", 1);
		}

		// 由收费计划车辆类型查询费率规则,可能不止一条费率
		List<FeeRateRule> rateList = this.getFeeRateRuleList(carType, schema.getId());

		if (rateList == null || rateList.size() == 0) {
			throw new ParseException("费率规则不存在", 1);
		}

		return rateList.get(0).getType();
	}

	Map<String, Object> billMoneyByFeeDuration(Map<String, Object> duration, int index, int len,
			List<Map<String, String>> freeTimeList, List<Map<String, Object>> freeTimesList, Parking parking)
					throws ParseException {

		Map<String, Object> result = new HashMap<>();

		FeeRateRule rate = (FeeRateRule) duration.get("rate");

		Long enterTime = (Long) duration.get("start"), leaveTime = (Long) duration.get("end");
		Double totalFee = 0.00, price = 0.00;

		FeeRateEnum type = rate.getType();
		CycleTypeEnum cycleType = rate.getCycleType();
		CycleFeeTypeEnum cycleFeeType = rate.getCycleFeeType();

		Boolean isCycled = rate.getIsCycled();

		Long cycleStart = 0l;
		if (rate.getCycleStart() != null) {
			cycleStart = sdf.parse(rate.getCycleStart()).getTime();
		}

		// 营业时间
		Map<String, Object> openingTime = new LinkedHashMap<>();
		// 相对时间

		Boolean is24 = parking.getAllDay();

		if (!is24) {
			openingTime.put("start", parking.getStartTime());
			openingTime.put("end", parking.getEndTime());
			isCycled = true;
			cycleType = CycleTypeEnum.DIY;
			cycleStart = sdf.parse(parking.getStartTime()).getTime();
		}

		Double maxFee = rate.getMaxFee() == null ? 0d : rate.getMaxFee();

		List<Map<String, Object>> cycleList = new ArrayList<>();

		if (FeeRateEnum.T == type) {

			// 非24营业，特指这个营业时时内收费，非营业时间不收费。

			FeeRateMultistep ratePrice = feeRateMultistepService.getRateSteps(rate.getId()).get(0);

			// 不循环定义为一个收费循环
			cycleList = getFeeCyclesWithFreeValidTime(isCycled, cycleType, cycleStart, enterTime, leaveTime,
					openingTime, freeTimesList, is24);
			price = ratePrice.getPrice();
			// 循环列表返回
			int cycles;
			int allCycles = cycles = cycleList.size();

			// 减去非营业时间循环
			System.out.println(cycleList);
			int freeTimes = 0, lastFreeTimes = 0;
			// 减去免费次数
			for (Map<String, Object> m : cycleList) {
				Object isFree = m.get("isFree");
				Integer freeCounter = (Integer) m.get("freeCounter");
				Boolean isLast = (Boolean) m.get("isLast");
				isLast = isLast == null ? false : isLast;
				freeCounter = freeCounter == null ? 0 : freeCounter;
				if (isFree != null) {
					cycles -= 1;
					m.put("money", 0d);
				} else {
					m.put("money", price);
				}
				freeTimes += freeCounter;
				if (isLast) {
					lastFreeTimes += freeCounter;
				}
				m.put("totalMoney", price);
			}

			if (CycleFeeTypeEnum.M == cycleFeeType && CycleTypeEnum.DATE == cycleType
					&& (len > 1 && index == 1 || len == 1 && allCycles > 1)) {
				Map<String, Object> firstCycle = cycleList.get(0);
				Long start = (Long) firstCycle.get("start"), end = (Long) firstCycle.get("end");
				if (end - start < DAY_MILLIS) {
					cycles = cycles == 1 ? 1 : cycles - 1;
					cycleList.get(0).put("money", 0d);
					if (freeTimes >= 0 && ((len == 1 && allCycles <= 2) || (len > 1 && len <= 2))) {
						freeTimes++;
					}
				}
			}

			int destCycles = cycles < 0 ? 0 : cycles;
			result.put("needFee", destCycles * price);
			result.put("usedTimes", freeTimes);
			result.put("totalFee", allCycles * price);
			result.put("lastUsedTimes", lastFreeTimes);
		}

		if (FeeRateEnum.H == type) {
			List<FeeRateMultistep> steps = feeRateMultistepService.getRateSteps(rate.getId());
			// 不循环定义为一个收费循环
			cycleList = getFeeCyclesWithFreeTimeList(isCycled, cycleType, cycleStart, freeTimeList, openingTime,
					enterTime, leaveTime, is24);
			// 循环
			totalFee = getTotalFeeByCycles(maxFee, steps, cycleList);
			result.put("totalFee", totalFee);
		}
		List<Map<String, Object>> resultList = getFeeItems(openingTime, cycleList);
		result.put("cycles", resultList);
		return result;

	}

	/**
	 * 拆分日期
	 *
	 * @param start
	 * @param end
	 * @return
	 */

	List<Map<String, Object>> getDaysByStartEnd(Long start, Long end) {

		// 获取时间基准点，取开始日期第二天的00:00
		Long base = get24ClockByTime(start) + DAY_MILLIS;
		// 获取时间基准点到结束点的一共经历的天数
		long days = end < base ? 0 : (long) Math.ceil((double) (end - base) / DAY_MILLIS);
		long lastDayTime = end < base ? (end + DAY_MILLIS - base) : (end - base) % DAY_MILLIS;

		calendarFestival.setTimeInMillis(get24ClockByTime(start) + TIME_ZONE_SPAN);
		Date startDate = calendarFestival.getTime();
		calendarFestival.setTimeInMillis(get24ClockByTime(end) + DAY_MILLIS + TIME_ZONE_SPAN);
		Date endDate = calendarFestival.getTime();
		List<Date> festivalList = festivalService.getAllHolidayByStartAndEnd(startDate, endDate);

		List<Map<String, Object>> dayList = new ArrayList<>();
		calendar.setTimeInMillis(start);
		long tstart = 0l, tend = 0l, tbase = base;

		for (int i = -1; i < days; i++) {
			Map<String, Object> map = new HashMap<>();

			tstart = base;
			if (i == days - 1) {
				base += lastDayTime;
			} else if (i > -1) {
				base += DAY_MILLIS;
			}
			tend = base;
			if (i == -1) {
				tstart = start;
				calendar.setTimeInMillis(start);
				tend = tbase > end ? end : tbase;
			} else {
				calendar.setTimeInMillis(base);
			}
			map.put("date", calendar.getTime().getTime());
			map.put("start", tstart);
			map.put("end", tend);

			calendar.setTimeInMillis(tstart);
			map.put("isHoliday", isFestival(calendar.getTime(), festivalList));
			map.put("dayOfWeek", calendar.get(Calendar.DAY_OF_WEEK));
			dayList.add(map);

		}

		System.out.println(dayList);
		return dayList;
	}

	boolean isFestival(Date date, List<Date> festivalList) {

		calendarFestival.setTimeInMillis(get24ClockByTime(date.getTime()) + TIME_ZONE_SPAN);
		Date destDate = calendarFestival.getTime();
		for (Date d : festivalList) {

			if (d.compareTo(destDate) == 0) {
				return true;
			}
		}
		return false;
	}

	// 按拆分日期匹配费率，返回需要计算费用的费率日期
	public List<Map<String, Object>> getBillingDurationAndRate(CarTypeEnum vehicle, Long parkingId, Long enterTime,
			Long leaveTime) throws ParseException {

		// 由入场时间，停车场ID查询收费计划.
		FeeSchema schema = feeSchemaService.getByTime(enterTime, parkingId);

		if (schema == null) {
			throw new ParseException("费率计划不存在", 1);
		}

		// 由收费计划车辆类型查询费率规则,可能不止一条费率
		List<FeeRateRule> rateList = this.getFeeRateRuleList(vehicle, schema.getId());

		if (rateList == null || rateList.size() == 0) {
			throw new ParseException("费率规则不存在", 1);
		}

		// 如果当前车型在本计划下不止一条费率，依据日期拆分匹配费率
		if (rateList.size() > 1) {
			// 拆分好的日期
			List<Map<String, Object>> dayList = getDaysByStartEnd(enterTime, leaveTime);
			for (Map<String, Object> s : dayList) {

				boolean isHoliday = (boolean) s.get("isHoliday");
				int dOfWeek = (int) s.get("dayOfWeek");
				// 一天只能匹配一条费率
				for (FeeRateRule f : rateList) {
					if (f.getOnFestival() && isHoliday) {
						s.put("rate", f);
						break;
					}
					boolean isMatch = false;
					switch (dOfWeek) {
					case 1:
						if (f.getOnSun()) {
							isMatch = true;
							s.put("rate", f);
						}
						break;
					case 2:

						if (f.getOnMon()) {
							isMatch = true;
							s.put("rate", f);
						}
						break;
					case 3:
						if (f.getOnThu()) {

							isMatch = true;
							s.put("rate", f);
						}
						break;
					case 4:
						if (f.getOnWed()) {
							isMatch = true;
							s.put("rate", f);
						}
						break;
					case 5:
						if (f.getOnThu()) {
							isMatch = true;
							s.put("rate", f);
						}
						break;
					case 6:
						if (f.getOnFri()) {
							isMatch = true;
							s.put("rate", f);
						}
						break;
					case 7:
						if (f.getOnSat()) {

							isMatch = true;
							s.put("rate", f);
						}
						break;
					}
					if (isMatch) {
						break;
					}
				}
			}
			// 拆分日期费率
			// [{'start':start,'end':end,'rate':rate}]
			FeeRateRule firstDayRate = (FeeRateRule) dayList.get(0).get("rate");
			List<Map<String, Object>> billingDuration = new LinkedList<>();
			Map<String, Object> duration = new HashMap<>();
			duration.put("start", dayList.get(0).get("start"));
			duration.put("rate", firstDayRate);
			int i = 0, size = dayList.size();
			for (Map<String, Object> m : dayList) {
				FeeRateRule rate = (FeeRateRule) m.get("rate");
				if (firstDayRate == rate) {
					duration.put("end", m.get("end"));
				} else {
					duration.put("end", m.get("start"));
					billingDuration.add(duration);
					duration = new HashMap<>();
					duration.put("start", m.get("start"));
					duration.put("rate", rate);
				}
				firstDayRate = rate;
				if (i == size - 1) {
					duration.put("end", m.get("end"));
					billingDuration.add(duration);
				}
				i++;

			}

			return billingDuration;

		} else {

			List<Map<String, Object>> billingDuration = new ArrayList<>();
			Map<String, Object> map = new HashMap<>();
			map.put("start", enterTime);
			map.put("end", leaveTime);
			map.put("rate", rateList.get(0));
			billingDuration.add(map);
			return billingDuration;
		}

	}

	/**
	 * 计算结果详细条目
	 *
	 * @throws ParseException
	 */
	@SuppressWarnings("unchecked")
	List<Map<String, Object>> getFeeItems(Map<String, Object> openingTime, List<Map<String, Object>> cycleList)
			throws ParseException {

		for (Map<String, Object> c : cycleList) {

			Object start = c.get("start"), end = c.get("end"), freeStart = c.get("freeStart"),
					freeEnd = c.get("freeEnd");

			List<Map<String, Object>> freeTimes = (List<Map<String, Object>>) c.get("freeTime");

			if (freeTimes != null && freeTimes.size() > 0) {
				for (Map<String, Object> m : freeTimes) {
					m.put("start", formatDateUTC2CST(m.get("start")));
					m.put("end", formatDateUTC2CST(m.get("end")));
				}
			}
			if (start != null) {
				c.put("start", formatDateUTC2CST(start));
			}
			if (end != null) {
				c.put("end", formatDateUTC2CST(end));
			}
			if (freeStart != null) {
				c.put("freeStart", formatDateUTC2CST(freeStart));
			}
			if (freeEnd != null) {
				c.put("freeEnd", formatDateUTC2CST(freeEnd));
			}
		}

		Map<String, Object> open = new HashMap<>();
		open.put("openTime", openingTime);
		cycleList.add(open);

		return cycleList;
	}

	/**
	 * 转换UTC时间到UTC+8并格式化
	 *
	 * @param time
	 * @return
	 * @throws ParseException
	 */

	String formatDateUTC2CST(Object time) throws ParseException {

		if (time != null) {
			Long t = Long.parseLong(time.toString());
			calendar.setTimeInMillis(t);
			return sdf3.format(calendar.getTime());
		}

		return null;
	}

	/**
	 * 根据免费资料有效期，和停车场循环类型拆分有效期循环
	 *
	 * @param isCycled
	 * @param cycleType
	 * @param cycleStart
	 * @param openingTime
	 * @param freeTimesList
	 * @param is24
	 * @throws ParseException
	 */
	List<Map<String, Object>> getFeeCyclesWithFreeValidTime(Boolean isCycled, CycleTypeEnum cycleType, Long cycleStart,
			Long enterTime, Long leaveTime, Map<String, Object> openingTime, List<Map<String, Object>> freeTimesList,
			Boolean is24) throws ParseException {

		List<Map<String, Object>> validList = new ArrayList<>();

		List<Map<String, Object>> cycleList;

		if (freeTimesList != null) {

			validList = getFeeFreeTimesCycles(isCycled, enterTime, leaveTime, cycleType, cycleStart, freeTimesList);
		}

		cycleList = getFeeCycles(isCycled, enterTime, leaveTime, cycleType, cycleStart);

		// 匹配营业时间
		if (!openingTime.isEmpty() && !is24) {
			matchOpeningTime(openingTime, cycleList);
		}

		for (Map<String, Object> m : validList) {
			Long vCStart = Long.parseLong(m.get("start").toString());
			Long vCEnd = Long.parseLong(m.get("end").toString());
			for (Map<String, Object> c : cycleList) {
				Long start = Long.parseLong(c.get("start").toString());
				Long end = Long.parseLong(c.get("end").toString());
				Boolean isValid = Boolean.parseBoolean(m.get("isValid").toString());
				Boolean isLast = (Boolean) m.get("isLast");
				isLast = isLast == null ? false : isLast;
				if (vCStart <= start && vCEnd >= end && isValid) {
					c.put("isFree", true);
					c.put("freeStart", start);
					c.put("freeEnd", end);
					c.put("needPay", 0d);
					c.put("freeCounter", 1);
					if (isLast) {
						c.put("isLast", isLast);
					}
					break;
				}
			}
		}

		return cycleList;
	}

	/**
	 * 匹配营业时间
	 *
	 * @param openingTime
	 *            营业时间
	 * @throws ParseException
	 */
	void matchOpeningTime(Map<String, Object> openingTime, List<Map<String, Object>> cycleList) throws ParseException {
		long start = sdf.parse(openingTime.get("start").toString()).getTime();
		long end = sdf.parse(openingTime.get("end").toString()).getTime();
		for (Map<String, Object> c : cycleList) {
			long cStart = Long.parseLong(c.get("start").toString());
			long cEnd = Long.parseLong(c.get("end").toString());
			long baseTime = get24ClockByTime(cStart);
			long oStart = start + baseTime;
			long oEnd = end + baseTime;

			// 考虑分割周期和营业时长范围没有交集的处理方案
			if (cEnd < oStart) {
				oStart = oStart - DAY_MILLIS;
			} else {
				oEnd = oEnd > oStart ? oEnd : oEnd + DAY_MILLIS;
			}

			calendar.setTimeInMillis(cStart);
			System.out.println(sdf3.format(calendar.getTime()));
			calendar.setTimeInMillis(cEnd);
			System.out.println(sdf3.format(calendar.getTime()));
			// 匹配营业时间，如果不在营业时间范围，则全部置为零
			if (cStart >= oEnd && cEnd <= oStart + DAY_MILLIS || cStart < oStart && cEnd <= oStart) {
				c.put("isClose", true);
				c.put("isFree", true);
			} else {
				cStart = cStart >= oStart ? cStart : oStart;
				cEnd = cEnd <= oEnd ? cEnd : oEnd;
			}
			c.put("start", cStart);
			c.put("end", cEnd);

		}

		// Map<String,Object> open = new HashMap<>();
		// open.put("openTime",openingTime);
		// cycleList.add(open);
	}

	/**
	 * 依据免费车列表从收费周期循环中减去免费时间
	 *
	 * @param isCycled
	 * @param cycleType
	 * @param cycleStart
	 * @param freeTimeList
	 * @param openingTime
	 * @param is24
	 * @throws ParseException
	 */
	protected List<Map<String, Object>> getFeeCyclesWithFreeTimeList(Boolean isCycled, CycleTypeEnum cycleType,
			Long cycleStart, List<Map<String, String>> freeTimeList, Map<String, Object> openingTime, Long enterTime,
			Long leaveTime, Boolean is24) throws ParseException {

		// 获取总的计费循环
		List<Map<String, Object>> cycleList = getFeeCycles(isCycled, enterTime, leaveTime, cycleType, cycleStart);

		// 匹配营业时间
		if (!openingTime.isEmpty() && !is24) {
			matchOpeningTime(openingTime, cycleList);
		}

		if (freeTimeList == null || freeTimeList.isEmpty()) {
			return cycleList;
		}

		// 遍历免费时段和所有计费循环，从循环时间里减去免费时段对应的时长，相对时间
		// 循环列表 绝对时间，时间戳
		for (Map<String, Object> cycle : cycleList) {
			long start = Long.parseLong(cycle.get("start").toString()),
					end = Long.parseLong(cycle.get("end").toString()),
					freeDuration = Long.parseLong(cycle.get("freeDuration").toString());

			// 以循环开始设置时间参考点
			List<Map<String, Long>> flist = new ArrayList<>();
			// 相对时间转化
			Long fStart = 0l, fEnd = 0l;
			for (Map<String, String> freeTime : freeTimeList) {
				Long freeStart = sdf4.parse(freeTime.get("start")).getTime();
				Long freeEnd = sdf4.parse(freeTime.get("end")).getTime();
				Long freeStartTime = freeStart;
				Long freeEndTime = freeEnd;
				Map<String, Long> map = new HashMap<>();

				// 免费时段与循环的四种情况
				// 免费时段完全包含循环
				//
				if (freeStartTime <= start && freeEndTime >= end) {
					freeDuration += end - start;
					fStart = start;
					fEnd = end;
				}
				// 免费时段在完全循环之内
				// [start,end]
				if (freeStartTime > start && freeEndTime < end) {
					freeDuration += freeEndTime - freeStartTime;
					fStart = freeStartTime;
					fEnd = freeEndTime;
				}
				// [start,end)

				// 免费时段包含循环的开始，不包含结束（可能跨循环，如24小时营业则一定跨循环）
				if (freeStartTime <= start && freeEndTime < end && freeEndTime > start) {

					freeDuration += freeEndTime - start;
					fStart = start;
					fEnd = freeEndTime;
				}
				// (start,end]
				// 免费时段包含循环的结束，不包含开始（可能跨循环，如24小时营业则一定跨循环）
				if (freeStartTime > start && freeStartTime < end && freeEndTime >= end) {

					freeDuration += end - freeStartTime;
					fStart = freeStartTime;
					fEnd = end;
				}
				if (fStart >= start && fEnd <= end && fEnd > fStart) {
					map.put("start", fStart);
					map.put("end", fEnd);
					flist.add(map);
				}
				fStart = fEnd = 0l;
			}
			cycle.put("freeDuration", freeDuration);
			if (flist.size() > 0) {
				cycle.put("freeTime", flist);
			}
		}

		return cycleList;
	}

	/**
	 * @param maxFee
	 *            最高收费一个循环
	 * @param steps
	 *            按时费率
	 * @return
	 */

	protected double getTotalFeeByCycles(Double maxFee, List<FeeRateMultistep> steps,
			List<Map<String, Object>> cycleList) {

		double totalFee = 0.00d;
		for (Map<String, Object> m : cycleList) {
			long start = Long.parseLong(m.get("start").toString());
			long end = Long.parseLong(m.get("end").toString());
			long freeDuration = Long.parseLong(m.get("freeDuration").toString());
			Object[] rs = getFeeByCycle(start, end - freeDuration, steps, maxFee);
			double cycleFee = Double.parseDouble(rs[0].toString());
			totalFee += cycleFee;
			m.put("steps", steps);
			m.put("money", cycleFee);
			m.put("needDuration", rs[1]);
		}

		return totalFee;
	}

	/**
	 * @param isCycled
	 *            循环标识
	 * @param enterTime
	 *            入场时间
	 * @param leaveTime
	 *            出场时间
	 * @param cycleType
	 *            循环类型
	 * @param cycleStart
	 *            循环起点
	 */

	List<Map<String, Object>> getFeeCycles(Boolean isCycled, Long enterTime, Long leaveTime, CycleTypeEnum cycleType,
			Long cycleStart) {

		Long start = enterTime;
		Long end = leaveTime;

		System.out.print(start + "," + end);

		List<Map<String, Object>> tempList = new ArrayList<>();
		// 循环依据循环参数分割循环周期，循环的起始和结束均为绝对时间，时间戳
		if (isCycled) {

			Long baseTime = get24ClockByTime(start);
			Long splitTime = 0l;
			if (cycleType == CycleTypeEnum.DIY) {
				splitTime = baseTime + cycleStart;
				if (splitTime <= start) {
					splitTime = splitTime + DAY_MILLIS;
				}
			}
			if (cycleType == CycleTypeEnum.DATE) {
				splitTime = baseTime + DAY_MILLIS;
			}

			long duration = 0l;
			int cycle = 0;

			if (cycleType == CycleTypeEnum.NOW) {
				duration = leaveTime - enterTime;
				cycle = (int) Math.ceil((double) duration / DAY_MILLIS);
			} else {
				duration = leaveTime - splitTime;
				cycle = duration < 0 ? 1 : (int) Math.ceil((double) duration / DAY_MILLIS) + 1;
			}

			for (int i = 0; i < cycle; i++) {
				Map<String, Object> map = new LinkedHashMap<>();

				if (i == 0) {
					if (cycle != 1) {
						end = getSpecifyClockByTime(start, cycleStart, cycleType);
					}
				}
				if (i > 0) {
					if (i == cycle - 1) {
						end = Long.parseLong(leaveTime + "");
					} else {
						end = getSpecifyClockByTime(start, cycleStart, cycleType);
					}
				}

				map.put("start", start);
				map.put("end", end);
				map.put("freeDuration", 0l);
				tempList.add(map);
				start = end;
			}
			// 不循环按一个循环，按停车入场和出场做为一个周期
		} else {
			Map<String, Object> map = new LinkedHashMap<>();
			map.put("start", start);
			map.put("end", end);
			map.put("freeDuration", 0l);
			tempList.add(map);
		}

		return tempList;
	}

	List<Map<String, Object>> getFeeFreeTimesCycles(Boolean isCycled, Long enterTime, Long leaveTime,
			CycleTypeEnum cycleType, Long cycleStart, List<Map<String, Object>> freeTimesList) throws ParseException {
		List<Map<String, Object>> tempList = new ArrayList<>();
		int i = 0, len = freeTimesList.size();
		for (Map<String, Object> freeMap : freeTimesList) {

			Long freeStart = sdf4.parse(freeMap.get("start").toString()).getTime();
			Long freeEnd = sdf4.parse(freeMap.get("end").toString()).getTime();
			int freeTimes = (int) freeMap.get("freeTimes");
			long baseTime = get24ClockByTime(enterTime);

			CycleTypeEnum fvCycleType = cycleType;
			if (cycleType == CycleTypeEnum.NOW) {
				cycleStart = enterTime - baseTime;
				fvCycleType = CycleTypeEnum.DIY;
			}

			if (freeEnd > freeStart && ((freeEnd <= leaveTime && freeEnd > enterTime && freeStart <= enterTime)
					|| (freeEnd < leaveTime && freeStart > enterTime)
					|| (freeEnd > leaveTime && freeStart > enterTime && freeStart < leaveTime)
					|| (freeEnd > leaveTime && freeStart <= enterTime))) {

				boolean isFirstFreeCycleValid = true;
				
				long baseFreeTime = get24ClockByTime(freeStart);
				long freeCycleStart = freeStart - baseFreeTime;
				if(freeCycleStart > cycleStart){
					isFirstFreeCycleValid = false;
				}
				
				freeEnd = freeEnd > leaveTime ? leaveTime : freeEnd;
				freeStart = freeStart > enterTime ? freeStart : enterTime;

				List<Map<String, Object>> cycleTempList = getFeeCycles(isCycled, freeStart, freeEnd, fvCycleType,
						cycleStart);

				if (i == 0) {
					cycleTempList.get(0).put("isFirstCycle", true);
				}

				// 免费有效期内的所有可使用免费次数
				freeTimes = freeTimes > cycleTempList.size() ? cycleTempList.size() : freeTimes;

				int j = 0;
				for (Map<String, Object> cmap : cycleTempList) {
					cmap.put("freeTimes", freeTimes);
					cmap.put("index", i);
					cmap.put("size", cycleTempList.size());
					// 标识循环免费是否可用
					if (j < freeTimes && isFirstFreeCycleValid) {
						cmap.put("isValid", true);
						j++;
					} else {
						cmap.put("isValid", false);
					}

					if (i == len - 1) {
						cmap.put("isLast", true);
					} else {
						cmap.put("isLast", false);
					}
					isFirstFreeCycleValid = true;
				}
				gFreeTimes += freeTimes;
				tempList.addAll(cycleTempList);

			}
			i++;

		}
		return tempList;
	}

	/**
	 * @param time
	 *            任意日期时间毫秒数
	 * @return 第二日0点毫秒数
	 */

	protected long get24ClockByTime(long time) {

		calendar.setTimeInMillis(time);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		// 设置日期到第二天0点
		calendar.set(year, month, day, 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	/**
	 * @param start
	 *            计费开始时间
	 * @param cycleStart
	 *            循环开始时间
	 * @param type
	 *            循环类型
	 * @return
	 */

	protected long getSpecifyClockByTime(long start, long cycleStart, CycleTypeEnum type) {

		if (CycleTypeEnum.NOW.equals(type)) {
			return start + DAY_MILLIS;
		} else {
			calendar.setTimeInMillis(start);
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			int day = calendar.get(Calendar.DATE);
			if (CycleTypeEnum.DATE == type) {
				calendar.set(year, month, day + 1, 0, 0, 0);
				return calendar.getTimeInMillis();
			} else {
				long baseTime = get24ClockByTime(start);
				calendar.setTimeInMillis(baseTime + cycleStart);
				long cycleStartTime = calendar.getTimeInMillis();

				if (start < cycleStartTime) {
					return cycleStartTime;
				} else {

					return cycleStartTime + DAY_MILLIS;
				}
			}
		}
	}

	/**
	 * 通过循环周期计费，时间均为绝对时间
	 *
	 * @param start
	 *            进入循环时间
	 * @param end
	 *            离开循环时间
	 * @param steps
	 *            阶梯费率
	 * @param maxFee
	 *            循环最高收费0不限
	 * @return
	 */
	Object[] getFeeByCycle(long start, long end, List<FeeRateMultistep> steps, double maxFee) {

		long temEnd = end;
		long duration = end - start;

		System.out.println(duration);
		double totalFee = 0d;
		long needDuration = 0l;
		int i = 0;
		for (FeeRateMultistep s : steps) {
			long stepDuration = s.getStepDuration() * 60 * 1000;
			if (stepDuration != 0 && i < steps.size() - 1) {
				if (duration > stepDuration) {
					end = start + stepDuration;
					Object[] rs = getFeeByStep(start, end, s);
					totalFee += Double.parseDouble(rs[0].toString());
					duration -= stepDuration;
					start += stepDuration;
					needDuration = Long.parseLong(rs[1].toString());
				} else {
					Object[] rs = getFeeByStep(start, end, s);
					totalFee = Double.parseDouble(rs[0].toString());
					needDuration = Long.parseLong(rs[1].toString());
					// 当计算时段不足计费单位时长时退出循环
					break;
				}

			} else {

				Object[] rs = getFeeByStep(start, temEnd, s);
				totalFee += Double.parseDouble(rs[0].toString());
				needDuration = Long.parseLong(rs[1].toString());
			}
			i++;
		}

		totalFee = totalFee > maxFee && maxFee > 0 ? maxFee : totalFee;
		Object[] rs = new Object[] { totalFee, needDuration };
		return rs;
	}

	/**
	 * 时间均为绝对时间，时间戳
	 *
	 * @param start
	 *            进入阶梯时间
	 * @param end
	 *            离开阶梯时间
	 * @param step
	 *            阶梯费率
	 * @return
	 */

	Object[] getFeeByStep(long start, long end, FeeRateMultistep step) {
		long duration = end - start;
		long pricingUnit = step.getPricingUnit() * 60 * 1000;
		double intervals = Math.ceil((double) duration / pricingUnit);

		// 计算计费单位不足的时差
		long leftDuration = duration % pricingUnit;
		long needDuration = 0l;
		if (leftDuration != 0) {
			needDuration = pricingUnit - leftDuration;
		}
		double totalFee = intervals * step.getPrice();
		System.out.println(totalFee);
		Object[] rs = new Object[2];
		rs[0] = totalFee;
		rs[1] = needDuration;
		return rs;
	}

	/**
	 * 获取费率规则
	 *
	 * @param vehicle
	 * @param schemaId
	 * @return
	 */
	private List<FeeRateRule> getFeeRateRuleList(CarTypeEnum vehicle, Long schemaId) {
		List<FeeRateRule> rateList = feeRateRuleService.findByVehicle(vehicle, schemaId);

		if (rateList != null && rateList.size() > 0) {
			return rateList;
		}

		return feeRateRuleService.findByVehicle(CarTypeEnum.ALL, schemaId);
	}
}
