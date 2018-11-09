package com.yxytech.parkingcloud.platform.controller;

import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.WechatPaymentSetting;
import com.yxytech.parkingcloud.core.service.IWechatPaymentSettingService;
import com.yxytech.parkingcloud.platform.form.WechatPaymentSettingForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/setting")
public class WechatPaymentSettingController extends BaseController {

    @Autowired
    private IWechatPaymentSettingService wechatPaymentSettingService;

    @PostMapping("/create")
    public ApiResponse create(@Valid @RequestBody WechatPaymentSettingForm wechatPaymentSettingForm,
                              BindingResult bindingResult) throws BindException {
        validate(bindingResult);

        WechatPaymentSetting wechatPaymentSetting = new WechatPaymentSetting();
        BeanUtils.copyProperties(wechatPaymentSettingForm, wechatPaymentSetting);

        // 权限验证，感觉应该要试用短信验证码进行验证，通过之后才可以进行操作
        try {
            wechatPaymentSettingService.insert(wechatPaymentSetting);

            return this.apiSuccess("");
        } catch (Exception e) {
            return this.apiFail("添加微信支付信息失败: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ApiResponse get(@PathVariable Long id) {
        WechatPaymentSetting wechatPaymentSetting = wechatPaymentSettingService.selectById(id);

        return this.apiSuccess(wechatPaymentSetting);
    }

    @PutMapping("/{id}")
    public ApiResponse update(@PathVariable Long id, @Valid @RequestBody WechatPaymentSettingForm wechatPaymentSettingForm,
                              BindingResult bindingResult) throws BindException {
        validate(bindingResult);

        WechatPaymentSetting wechatPaymentSetting = wechatPaymentSettingService.selectById(id);

        if (wechatPaymentSetting == null) {
            return this.apiFail("修改失败, 非法操作!");
        }

        wechatPaymentSetting.setAppId(wechatPaymentSettingForm.getAppId());
        wechatPaymentSetting.setMchId(wechatPaymentSetting.getMchId());
        wechatPaymentSetting.setApiKey(wechatPaymentSetting.getApiKey());

        try {
            wechatPaymentSettingService.updateById(wechatPaymentSetting);

            return this.apiSuccess("");
        } catch (Exception e) {
            return this.apiFail("修改失败: " + e.getMessage());
        }
    }
}
