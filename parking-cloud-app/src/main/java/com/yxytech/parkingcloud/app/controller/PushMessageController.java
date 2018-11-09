package com.yxytech.parkingcloud.app.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.PushMessage;
import com.yxytech.parkingcloud.core.enums.PushMessageStatusEnum;
import com.yxytech.parkingcloud.core.service.IPushMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class PushMessageController extends BaseController {

    @Autowired
    private IPushMessageService pushMessageService;

    /**
     * 查询消息列表
     *
     * @return
     */
    @GetMapping("")
    public ApiResponse<Object> index(@RequestParam(value = "page", defaultValue = "1", required = false) Integer currentPage,
                                     @RequestParam(value = "status", required = false) Integer status) {
        Page<PushMessage> page = new Page<>(currentPage, Integer.valueOf(pageSize));
        EntityWrapper<PushMessage> ew = new EntityWrapper<>();
        ew.eq("customer_id", getCurrentUser().getId());
        ew.eq(status != null,"status", status);
        ew.orderBy("id", false);
        page = pushMessageService.selectPage(page, ew);

        return apiSuccess(page);
    }

    /**
     * 设置为已读
     */
    @PostMapping("/update_read")
    @Transactional
    public ApiResponse<Object> updateRead(@RequestBody List<Long> ids) {
        if (ids.size() == 0) {
            return apiFail("参数不能为空");
        }

        EntityWrapper<PushMessage> ew = new EntityWrapper<>();
        ew.eq("customer_id", getCurrentUser().getId());
        ew.eq("status", PushMessageStatusEnum.UNREAD);
        ew.in("id", ids);
        ew.setSqlSelect("id", "status");
        List<PushMessage> items = pushMessageService.selectList(ew);

        items.forEach(item -> item.setStatus(PushMessageStatusEnum.READ));

        if (items.size() > 0) {
            pushMessageService.updateBatchById(items);
        }

        return apiSuccess(null);
    }
}
