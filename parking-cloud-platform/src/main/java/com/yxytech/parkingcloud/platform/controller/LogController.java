package com.yxytech.parkingcloud.platform.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.Log;
import com.yxytech.parkingcloud.core.entity.User;
import com.yxytech.parkingcloud.core.service.ILogService;
import com.yxytech.parkingcloud.core.service.IUserService;
import com.yxytech.parkingcloud.core.utils.DateParserUtil;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/log")
public class LogController extends BaseController {

    @Autowired
    private ILogService logService;

    @Autowired
    private IUserService userService;

    @GetMapping("")
    public ApiResponse<Object> index(@RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                                     @RequestParam(value = "size", defaultValue = pageSize, required = false) Integer size,
                                     @RequestParam(value = "start_time", required = false) String startTime,
                                     @RequestParam(value = "end_time", required = false) String endTime,
                                     @RequestParam(value = "name",required = false)String name,
                                     @RequestParam(value = "uri",required = false)String uri,
                                     @RequestParam(value = "retCode",required = false)String retCode) {

        Page<Log> p = new Page<>(page,size);
        EntityWrapper<Log> ew = new EntityWrapper<>();
        try {
            ew.between(DateParserUtil.parseDate(startTime,true)!=null
                    && DateParserUtil.parseDate(endTime,false)!=null,"time",
                    DateParserUtil.parseDate(startTime,true),
                    DateParserUtil.parseDate(endTime,false));
            ew.eq(StringUtils.isNotBlank(uri),"uri",uri);
            ew.eq(StringUtils.isNotBlank(retCode),"ret_code",retCode);
        }catch (Exception e){
            return apiFail("日期格式错误" + e.getMessage());
        }
        ew.in(!logService.getUserIds(name).isEmpty(),"user_id",logService.getUserIds(name));
        p = logService.selectPage(p,ew);

        List<Log> logs = p.getRecords();
        List<Long> userIds = new ArrayList<>();
        for(Log log : logs){
            if(StringUtils.isBlank(log.getUserId()))
                continue;
            userIds.add(Long.parseLong(log.getUserId()));
        }
        Map<Long,String> userIdName = userService.selectIdNameMap(userIds,"id","name");
        Object [] data = {p,userIdName};

        return apiSuccess(data);
    }

    @GetMapping("/{time}")
    public ApiResponse<Object> detail(@PathVariable Long time){
        EntityWrapper<Log> ew = new EntityWrapper<>();
        if(time == null)
            return apiFail("时间不能为空");

        try {
            Date date = new Date(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            ew.eq("time", calendar.getTime());
        }catch (Exception e){
            return apiFail("日期格式错误" + e.getMessage());
        }

        return apiSuccess(logService.selectOne(ew));
    }
}
