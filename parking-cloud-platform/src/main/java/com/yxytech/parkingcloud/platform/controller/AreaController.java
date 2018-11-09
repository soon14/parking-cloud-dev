package com.yxytech.parkingcloud.platform.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.Area;
import com.yxytech.parkingcloud.core.service.IAreaService;
import com.yxytech.parkingcloud.platform.config.Access;
import com.yxytech.parkingcloud.platform.form.AreaForm;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cj
 * @since 2017-10-18
 */
@RestController
@RequestMapping("/area")
public class AreaController extends BaseController {

    @Autowired
    private IAreaService areaService;


    /**
     * 查询所有区域信息
     * @return
     */
    @GetMapping("/index")
    public ApiResponse<Object> index(@RequestParam(value = "name",defaultValue = "",required = false) String name,
                                     @RequestParam(value = "page",defaultValue = "1",required = false) Integer page,
                                     @RequestParam(value = "size",required = false) Integer size){

        Page<Area> p  = new Page<Area>(page, size);
        EntityWrapper<Area> ew = new EntityWrapper<>();
        ew.like(StringUtils.isNotBlank(name),"name", name).
                                  eq("parent_id",0).
                        orderBy("sort_number",false);

        p = areaService.selectPage(p, ew);

        return apiSuccess(p);
    }

    /**
     * 根据区域Id查询区域详细信息
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public ApiResponse<Object> findById(@RequestParam(value = "id",required = false) Long id)throws NotFoundException{
        Area area = areaService.selectById(id);
        notFound(area,"区域信息不存在");

        Map<String,Object> data = areaService.find(id);

        return apiSuccess(data);
    }

    /**
     * 根据parentId查询上级区域信息
     * @return
     */
    @GetMapping("/findByLevel")
    public ApiResponse<Object> findAreaByLevel(@RequestParam(value = "level",required = false)Integer level){
        List data = areaService.findByLevel(level);

        return apiSuccess(data);
    }

    /**
     * 新增区域记录
     * @param
     * @return
     */
    @PostMapping("/addArea")
    @Access(permissionName = "区域管理",permissionCode = "AREA_MANAGE",moduleCode = "basic_information")
    public ApiResponse<Object> addArea(@Valid @RequestBody AreaForm areaForm, BindingResult areaErrors)throws BindException{
        validate(areaErrors);

        Area area = new Area();
        BeanUtils.copyProperties(areaForm,area);

        String msg = areaService.validateArea(area.getName());
        if(StringUtils.isNotBlank(msg))
             return apiFail(msg);

        areaService.insert(area);

        return apiSuccess(null);
    }

    /**
     * 根据ID查询下级区域信息
     */
    @GetMapping("/findChild")
    public ApiResponse<Object> findChild(@RequestParam(value = "id",required = false)Long id,
                                         @RequestParam(value = "page",defaultValue = "1",required = false)Integer page,
                                         @RequestParam(value = "size",required = false)Integer size){
        Page<Area> p = new Page<Area>(page,size);
        EntityWrapper<Area> ew = new EntityWrapper<>();
        ew.eq("parent_id",id);

        p = areaService.selectPage(p,ew);

        return apiSuccess(p);
    }

    /**
     * 查询所有的区域
     * @return
     */
    @GetMapping("/findAllAreas")
    public ApiResponse<Object> findAllAreas(){
        List data = areaService.findAll();

        return apiSuccess(data);
    }

    /**
     * 区域信息修改
     * @param areaForm
     * @param bindingResult
     * @return
     * @throws BindException
     */
    @PutMapping("")
    public ApiResponse<Object> update(@Valid @RequestBody AreaForm areaForm,BindingResult bindingResult)throws BindException{
        validate(bindingResult);

        Area area = new Area();
        BeanUtils.copyProperties(areaForm,area);

        String msg = areaService.updateValidate(area);
        if(StringUtils.isNotBlank(msg))
            return apiFail(msg);

        areaService.updateById(area);

        return apiSuccess(null);
    }
}
