package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.core.entity.Organization;
import com.yxytech.parkingcloud.core.entity.User;
import com.yxytech.parkingcloud.core.entity.UserAccount;
import com.yxytech.parkingcloud.core.exception.ServiceException;
import com.yxytech.parkingcloud.core.mapper.UserMapper;
import com.yxytech.parkingcloud.core.service.IOrganizationService;
import com.yxytech.parkingcloud.core.service.IRoleService;
import com.yxytech.parkingcloud.core.service.IUserAccountService;
import com.yxytech.parkingcloud.core.service.IUserService;
import com.yxytech.parkingcloud.core.utils.YXYServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cj
 * @since 2017-10-20
 */
@Service("com.yxytech.parkingcloud.core.service.impl.UserServiceImpl")
public class UserServiceImpl extends YXYServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private IOrganizationService organizationService;

    @Autowired
    private IUserAccountService userAccountService;

    @Autowired
    private IRoleService roleService;

    @Override
    public Page<User> selectUsersWithRolesGroups(Page<User> page, Map<String, Object> params) {
        page.setRecords(baseMapper.selectUsersWithRolesGroups(params));
        return page;
    }

    @Override
    public Set<Long> userRoleIds(Long userId) {
        return baseMapper.selectUserRoleIds(userId);
    }

    @Override
    public Boolean needDataIsolate(Long userId) {
        boolean needDataIsolate = false;
        List<String> roleCodes = getRoleCodes(userId);

        if (!roleCodes.contains("SUPER_ADMIN")) {
            needDataIsolate = true;
        }

        return needDataIsolate;
    }

    private UserAccount getUserAccountById(Long userId) throws ServiceException {
        UserAccount account = userAccountService.selectByUserId(userId);
        if (account == null) {
            throw new ServiceException(ServiceException.NOT_FOUND_ACCOUNT, "账号不存在");
        }

        return account;
    }

    private Organization getUserOrgByOrgId(Long orgId) throws ServiceException {
        Organization org = organizationService.selectById(orgId);
        if (org == null) {
            throw new ServiceException(ServiceException.NOT_FOUND_ACCOUNT, "单位不存在");
        }

        return org;
    }

    private List<String> getRoleCodes(Long userId) {
        List<String> codes = new ArrayList<>();
        Set<Long> roleIds = userRoleIds(userId);
        if (!roleIds.isEmpty()) {
            codes = roleService.codesByIds(roleIds);
        }

        return codes;
    }

    @Override
    public String getIsolatedOrgsSql(Long userId) throws ServiceException {
        String sql = " = " + getUserAccountById(userId).getOrgId();
        return sql;
    }

    private String getParkingsSqlWithSuperviseAdmin(Long orgId) {
        String sql = " in " +
                "(select p.id from  yxy_parking p " +
                "where exists (select 1 from yxy_org_area where area_id = p.street_area_id and org_id = %d ))";

        return String.format(sql, orgId);
    }

    private String getParkingsSqlWithSuperviseUser(Long userId) {
        String sql = " in (select p.id from  yxy_parking p " +
                "where exists " +
                "(select 1 " +
                "from yxy_user_group_area ga " +
                "join yxy_user_user_group ug " +
                "on ga.group_id = ug.group_id " +
                "where  p.street_area_id = ga.area_id " +
                "and ug.user_id = %d " +
                "))";

        return String.format(sql, userId);
    }

    private String getParkingsSqlWithOperateAdmin(Long orgId) {
        String sql = " in (select p.id from  yxy_parking p " +
                "where exists " +
                "(select 1 from yxy_org_parking where parking_id = p.id and org_id = %d ))";

        return String.format(sql, orgId);
    }

    private String getParkingsSqlWithOperateUser(Long userId) {
        String sql = " in (select p.id from  yxy_parking p " +
                "where exists (" +
                "select 1 " +
                "from yxy_user_group_parking gp " +
                "join yxy_user_user_group ug " +
                "on gp.group_id = ug.group_id " +
                "where  p.id = gp.parking_id " +
                "and ug.user_id = %d " +
                "))";

        return String.format(sql, userId);
    }


    @Override
    public String getIsolatedParkingsSql(Long userId) throws ServiceException {
        UserAccount account = getUserAccountById(userId);
        Organization org = getUserOrgByOrgId(account.getOrgId());
        List<String> roleCodes = getRoleCodes(userId);
        String sql = null;

        // 是否监管单位
        if (org.getRegulatory()) {
            // 是否监管单位管理员
            if (roleCodes.contains("SUPERVISE_ADMIN")) {
                sql = getParkingsSqlWithSuperviseAdmin(org.getId());
            } else {
                sql = getParkingsSqlWithSuperviseUser(userId);
            }

        } else {
            // 是否运营单位管理员
            if (roleCodes.contains("OPERATE_ADMIN")) {
                sql = getParkingsSqlWithOperateAdmin(org.getId());
            } else {
                sql = getParkingsSqlWithOperateUser(userId);
            }
        }

        return sql;
    }
}
