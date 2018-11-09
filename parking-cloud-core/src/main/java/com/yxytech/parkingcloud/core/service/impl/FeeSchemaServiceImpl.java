package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.FeeRateRule;
import com.yxytech.parkingcloud.core.entity.FeeSchema;
import com.yxytech.parkingcloud.core.entity.Parking;
import com.yxytech.parkingcloud.core.enums.FeeRateEnum;
import com.yxytech.parkingcloud.core.mapper.FeeSchemaMapper;
import com.yxytech.parkingcloud.core.service.IFeeRateRuleService;
import com.yxytech.parkingcloud.core.service.IFeeSchemaService;
import com.yxytech.parkingcloud.core.service.IParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author cj
 * @since 2017-10-29
 */
@Service
public class FeeSchemaServiceImpl extends ServiceImpl<FeeSchemaMapper, FeeSchema> implements IFeeSchemaService {

    @Autowired
    private IFeeRateRuleService ruleService;

    @Autowired
    private IParkingService parkingService;

    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));

    @Override
    public boolean bindRule(Long schemaId, List<Long> rules) throws Exception {
        boolean flag = false;

        FeeRateEnum feeRateType = null;
        for (Long s : rules) {
            FeeRateRule rule = ruleService.selectById(s);

            if (feeRateType != null && ! feeRateType.equals(rule.getType())) {
                throw new Exception("一种费率计划只能绑定一种类型的费率规则!");
            }

            feeRateType = rule.getType();
        }

        baseMapper.unbind(schemaId);

        for (Long s : rules) {
            int bindId = baseMapper.bind(schemaId, s);
            if (bindId <= 0) {
                flag = false;
                return flag;
            }
        }


        return flag;
    }

    @Override
    @Transactional
    public boolean bindParking(Long schemaId, List<Long> parking) throws Exception {
        boolean flag = false;
        for (Long p : parking) {
            Parking parkingInfo = parkingService.selectById(p);

            if (! parkingInfo.getAllDay()) {
                List<FeeRateRule> feeRateRules = baseMapper.getRateRulesBySchemaId(new ArrayList<Long>() {{
                    add(schemaId);
                }});

                for (FeeRateRule rule : feeRateRules) {
                    if (rule.getType().equals(FeeRateEnum.H)) {
                        throw new Exception("非24小时营业停车场不能绑定按时的费率!");
                    }
                }
            }
        }

        baseMapper.unbindParking(schemaId);

        for (Long p : parking) {
            int bindId = baseMapper.bindParking(schemaId, p);
            if (bindId <= 0) {
                flag = false;
                return flag;
            }
        }

        return flag;
    }

    @Override
    public List<FeeRateRule> getFeeRateRulesBySchemaIds(List<Long> ids) {
        return baseMapper.getRateRulesBySchemaId(ids);
    }

    @Override
    public boolean update(FeeSchema entity) {

        if (baseMapper.update(entity) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean create(FeeSchema entity) {

        if (baseMapper.create(entity) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public FeeSchema getDetail(String schemaId) {

        return baseMapper.selectById(schemaId);

    }

    @Override
    public Page<FeeSchema> getFeeSchemaPage(Page<FeeSchema> page, FeeSchema entity) {

        page.setRecords(baseMapper.getSchemaPage(page, entity));

        return page;
    }

    @Override
    public long getVersion() {

        int year = calendar.get(Calendar.YEAR);
        Long currentMaxVersion = baseMapper.maxVersion();
        String vStr = currentMaxVersion == null ? "0" : currentMaxVersion + "";
        if (vStr.matches("^" + year + "\\d+")) {
            vStr = vStr.replaceFirst(year + "", "");
        } else {
            vStr = "0";
        }
        Long dest = Long.parseLong(vStr) + 1;
        return Long.parseLong(year + "" + dest);
    }

    @Override
    public FeeSchema getByTime(Long enterTime, Long parkingId) {
        return baseMapper.getByTime(enterTime, parkingId);
    }
}
