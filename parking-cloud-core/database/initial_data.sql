-- 区域数据
insert into yxy_area(name, code, executive_level, parent_id, sort_number, level)
values('西安市', 'xa', '市', 0, 1, 1);

insert into yxy_area(name, code, executive_level, parent_id, sort_number, level)
values('雁塔区', 'xagx', '区', 1, 2, 2);

insert into yxy_area(name, code, executive_level, parent_id, sort_number, level)
values('电子城街道', 'xagxdzc', '街道', 2, 3, 3);

-- 默认单位数据
insert into yxy_organzation (
  org_nature, full_name, short_name, register_address, legal_represent_ative, taxpayer_number, phone_number, email, org_number, registration_number, approve_status, is_property_org, is_manage_org, is_facility_org, is_regulatory, created_by, created_at, updated_by, updated_at, is_valid, area_id
)
values (1,'云星宇', 'yxy', '西安', '8888', '8888', '18888888888', 'admin@yxy.com', '88888888', '', 0, true, true, true, false, '0', '2017-10-23 20:16:20.246000', '0', '2017-10-23 20:21:24.879000', true, 3);

-- 默认管理员用户信息
insert into yxy_user (name, gender, birth, id_type, id_number, created_at, updated_at) values ('admin', '男', '19911020', '123123', '2131', now(), now());

-- 默认管理员账号
insert into yxy_user_account
(user_id, org_id, employee_number, mobile, email, password, is_active, last_login_at, created_at, updated_at)
values
  (1, 1, '00001', '18888888888', 'admin@yxy.com', '$2a$10$PGGL6n1fMicb5HcfsmfTOuJJ4t1LlV7gKZHSJEkd4nP8iDY4199HK', true, now(), now(), now());

-- 角色
insert into yxy_role (name, code, internal, created_at, created_by)
values
  ('超级管理员', 'SUPER_ADMIN', true, now(), -1),
  ('监管单位管理员', 'SUPERVISE_ADMIN', true, now(), -1),
  ('一般单位管理员', 'OPERATE_ADMIN', true, now(), -1);

-- 分配默认管理员超级管理员角色
insert into yxy_user_role (user_id, role_id, created_at, created_by)
values (1, 1, now(), -1);


-- 模块（子系统）数据
INSERT INTO "public"."yxy_module" VALUES ('1', '基础信息管理', 'basic_information', '1', null, null);
INSERT INTO "public"."yxy_module" VALUES ('2', '设备管理', 'equipment_manage', '2', null, null);
INSERT INTO "public"."yxy_module" VALUES ('3', '账号权限管理', 'account_auth_manage', '3', null, null);
INSERT INTO "public"."yxy_module" VALUES ('4', '交易管理', 'transaction_manage', '4', null, null);
INSERT INTO "public"."yxy_module" VALUES ('5', '统计分析', 'statictical_analysis', '5', null, null);
INSERT INTO "public"."yxy_module" VALUES ('6', '状态名单管理', 'status_list_manage', '6', null, null);
INSERT INTO "public"."yxy_module" VALUES ('7', '发票管理', 'invoice_manage', '7', null, null);
INSERT INTO "public"."yxy_module" VALUES ('8', '日志管理', 'log_manage', '8', null, null);
INSERT INTO "public"."yxy_module" VALUES ('9', '系统管理', 'system_manage', '9', null, null);
INSERT INTO "public"."yxy_module" VALUES ('10', '优惠券管理', 'coupon_manage', '10', null, null);
INSERT INTO "public"."yxy_module" VALUES ('11', '平台客户管理', 'customer_manage', '11', null, null);
INSERT INTO "public"."yxy_module" VALUES ('12', '首页管理', 'index_manage', '12', null, null);

-- 权限初始化数据
INSERT INTO "public"."yxy_permission" VALUES ('1', '区域管理', 'AREA_MANAGE', '1', '1', '2017-12-20 17:36:06.926', '2017-12-20 17:36:06.926');
INSERT INTO "public"."yxy_permission" VALUES ('2', '停车场创建', 'PARKING_CREATE', '1', '1', '2017-12-20 17:36:06.934', '2017-12-20 17:36:06.934');
INSERT INTO "public"."yxy_permission" VALUES ('3', '停车场修改', 'PARKING_UPDATE', '1', '1', '2017-12-20 17:36:06.968', '2017-12-20 17:36:06.968');
INSERT INTO "public"."yxy_permission" VALUES ('4', '停车场认证', 'PARKING_APPROVE', '1', '1', '2017-12-20 17:36:07.055', '2017-12-20 17:36:07.055');
INSERT INTO "public"."yxy_permission" VALUES ('5', '停车场停用', 'PARKING_NONUSE', '1', '1', '2017-12-20 17:36:06.978', '2017-12-20 17:36:06.978');
INSERT INTO "public"."yxy_permission" VALUES ('6', '停车场绑定费率', 'PARKING_BIND_SCHEMA', '1', '1', '2017-12-20 17:36:06.953', '2017-12-20 17:36:06.953');
INSERT INTO "public"."yxy_permission" VALUES ('7', '停车场认证审核', 'PARKING_CHECK', '1', '1', '2017-12-20 17:36:06.981', '2017-12-20 17:36:06.981');
INSERT INTO "public"."yxy_permission" VALUES ('8', '停车场查询', 'PARKING_QUERY', '1', '1', '2017-12-20 17:36:07.008', '2017-12-20 17:36:07.008');
INSERT INTO "public"."yxy_permission" VALUES ('9', '停车场启用', 'PARKING_USING', '1', '1', '2017-12-20 17:36:07.011', '2017-12-20 17:36:07.011');
INSERT INTO "public"."yxy_permission" VALUES ('10', '停车场资源查询', 'PARKING_RESOURCE_QUERY', '1', '1', '2017-12-20 17:36:07.046', '2017-12-20 17:36:07.046');
INSERT INTO "public"."yxy_permission" VALUES ('11', '停车场资源新建', 'PARKING_RESOURCE_CREATE', '1', '1', '2017-12-20 17:36:07.017', '2017-12-20 17:36:07.017');
INSERT INTO "public"."yxy_permission" VALUES ('12', '停车场资源停用', 'PARKING_RESOURCE_NONUSE', '1', '1', '2017-12-20 17:36:06.987', '2017-12-20 17:36:06.987');
INSERT INTO "public"."yxy_permission" VALUES ('13', '停车场资源修改', 'PARKING_RESOURCE_UPDATE', '1', '1', '2017-12-20 17:36:06.99', '2017-12-20 17:36:06.99');
INSERT INTO "public"."yxy_permission" VALUES ('14', '停车场资源启用', 'PARKING_RESOURCE_USING', '1', '1', '2017-12-20 17:36:06.959', '2017-12-20 17:36:06.959');
INSERT INTO "public"."yxy_permission" VALUES ('15', '单位认证', 'ORG_APPROVE', '1', '1', '2017-12-20 17:36:07.02', '2017-12-20 17:36:07.02');
INSERT INTO "public"."yxy_permission" VALUES ('16', '单位查询', 'ORG_QUERY', '1', '1', '2017-12-20 17:36:07.026', '2017-12-20 17:36:07.026');
INSERT INTO "public"."yxy_permission" VALUES ('17', '单位新建', 'ORG_CREATE', '1', '1', '2017-12-20 17:36:07.031', '2017-12-20 17:36:07.031');
INSERT INTO "public"."yxy_permission" VALUES ('18', '单位修改', 'ORG_UPDATE', '1', '1', '2017-12-20 17:36:07.036', '2017-12-20 17:36:07.036');
INSERT INTO "public"."yxy_permission" VALUES ('19', '单位认证审核', 'ORG_CHECK', '1', '1', '2017-12-20 17:36:07.044', '2017-12-20 17:36:07.044');

-- 设备信息
INSERT INTO "public"."yxy_permission" VALUES ('20', '设备创建', 'DEVICE_CREATE', '2', '1', '2017-12-20 17:36:06.984', '2017-12-20 17:36:06.984');
INSERT INTO "public"."yxy_permission" VALUES ('21', '设备启用', 'DEVICE_USING', '2', '1', '2017-12-20 17:36:07.049', '2017-12-20 17:36:07.049');
INSERT INTO "public"."yxy_permission" VALUES ('22', '设备修改', 'DEVICE_UPDATE', '2', '1', '2017-12-20 17:36:06.956', '2017-12-20 17:36:06.956');
INSERT INTO "public"."yxy_permission" VALUES ('23', '设备停用', 'DEVICE_NONUSE', '2', '1', '2017-12-20 17:36:06.962', '2017-12-20 17:36:06.962');
INSERT INTO "public"."yxy_permission" VALUES ('24', '设备查询', 'DEVICE_QUERY', '2', '1', '2017-12-20 17:36:06.942', '2017-12-20 17:36:06.942');

--权限管理
INSERT INTO "public"."yxy_permission" VALUES ('25', '权限管理', 'PERMISSION_MANAGE', '3', '1', '2017-12-20 17:36:06.939', '2017-12-20 17:36:06.939');
INSERT INTO "public"."yxy_permission" VALUES ('26', '用户组管理', 'USERGROUP_MANAGE', '3', '1', '2017-12-20 17:36:06.992', '2017-12-20 17:36:06.992');
INSERT INTO "public"."yxy_permission" VALUES ('27', '用户角色绑定', 'USERROLE_MANAGE', '3', '1', '2017-12-20 17:36:06.998', '2017-12-20 17:36:06.998');
INSERT INTO "public"."yxy_permission" VALUES ('28', '平台用户管理', 'USER_MANAGE', '3', '1', '2017-12-20 17:36:07.028', '2017-12-20 17:36:07.028');
INSERT INTO "public"."yxy_permission" VALUES ('29', '角色管理', 'ROLE_MANAGE', '3', '1', '2017-12-20 17:36:07.042', '2017-12-20 17:36:07.042');

--交易管理
INSERT INTO "public"."yxy_permission" VALUES ('30', '交易查询', 'ORDER_TRANSACTION_QUERY', '4', '1', '2017-12-20 17:36:06.946', '2017-12-20 17:36:06.946');

--统计分析
INSERT INTO "public"."yxy_permission" VALUES ('31', '停车点收费报表', 'PARKING_LOT_CHARGE', '5', '1', '2017-12-20 17:36:07.057', '2017-12-20 17:36:07.057');
INSERT INTO "public"."yxy_permission" VALUES ('32', '停车报表', 'PARKING_REPORT', '5', '1', '2017-12-20 17:36:06.921', '2017-12-20 17:36:06.921');
INSERT INTO "public"."yxy_permission" VALUES ('33', '车位使用率', 'CELL_USE_RATE', '5', '1', '2017-12-20 17:36:06.975', '2017-12-20 17:36:06.975');
INSERT INTO "public"."yxy_permission" VALUES ('34', '收益统计', 'CELL_INCOME', '5', '1', '2017-12-20 17:36:07.039', '2017-12-20 17:36:07.039');
INSERT INTO "public"."yxy_permission" VALUES ('35', '停车场地图可视化', 'NEAR_PARKING', '5', '1', '2017-12-20 17:36:07.039', '2017-12-20 17:36:07.039');

--状态名单管理
INSERT INTO "public"."yxy_permission" VALUES ('36', '状态名单移除', 'STATUSLIST_DELETE', '6', '1', '2017-12-20 17:36:06.995', '2017-12-20 17:36:06.995');
INSERT INTO "public"."yxy_permission" VALUES ('37', '状态名单新建', 'STATUSLIST_CREATE', '6', '1', '2017-12-20 17:36:07.001', '2017-12-20 17:36:07.001');
INSERT INTO "public"."yxy_permission" VALUES ('54', '状态名单列表', 'STATUSLIST_INDEX', '6', '1', '2017-12-20 17:36:07.001', '2017-12-20 17:36:07.001');


INSERT INTO "public"."yxy_permission" VALUES ('38', '发票列表', 'INVOICE_LIST', '7', '1', '2017-12-20 17:36:06.93', '2017-12-20 17:36:06.93');
INSERT INTO "public"."yxy_permission" VALUES ('39', '订单发票列表', 'ORDER_INVOICE_LIST', '7', '1', '2017-12-20 17:36:06.971', '2017-12-20 17:36:06.971');

--系统管理
INSERT INTO "public"."yxy_permission" VALUES ('40', '费率计划修改', 'FEE_SCHEMA_UPDATE', '9', '1', '2017-12-20 17:36:06.914', '2017-12-20 17:36:06.914');
INSERT INTO "public"."yxy_permission" VALUES ('41', '广告管理', 'ADVERTISEMENT_MANAGE', '9', '1', '2017-12-20 17:36:06.95', '2017-12-20 17:36:06.95');
INSERT INTO "public"."yxy_permission" VALUES ('42', '费率计划绑定费率规则', 'FEE_SCHEMA_RULE', '9', '1', '2017-12-20 17:36:07.004', '2017-12-20 17:36:07.004');
INSERT INTO "public"."yxy_permission" VALUES ('43', '费率规则修改', 'FEE_RETE_RULE_UPDATE', '9', '1', '2017-12-20 17:36:07.004', '2017-12-20 17:36:07.004');
INSERT INTO "public"."yxy_permission" VALUES ('44', '假日新增', 'FEE_FESTIVAL_CREATE', '9', '1', '2017-12-20 17:36:07.014', '2017-12-20 17:36:07.014');
INSERT INTO "public"."yxy_permission" VALUES ('45', '费率计划创建', 'FEE_SCHEMA_CREATE', '9', '1', '2017-12-20 17:36:07.034', '2017-12-20 17:36:07.034');
INSERT INTO "public"."yxy_permission" VALUES ('46', '费率规则新建', 'FEE_RETE_RULE_CREATE', '9', '1', '2017-12-20 17:36:07.052', '2017-12-20 17:36:07.052');
INSERT INTO "public"."yxy_permission" VALUES ('47', '费率计划绑定停车场', 'FEE_SCHEMA_PARKING', '9', '1', '2017-12-20 17:36:07.052', '2017-12-20 17:36:07.052');
INSERT INTO "public"."yxy_permission" VALUES ('53', '费率计划列表', 'FEE_SCHEMA_INDEX', '9', '1', '2017-12-20 17:36:06.914', '2017-12-20 17:36:06.914');
INSERT INTO "public"."yxy_permission" VALUES ('55', '费率规则详情', 'FEE_RULE_DETAIL', '9', '1', '2017-12-20 17:36:06.914', '2017-12-20 17:36:06.914');

--平台客户管理
INSERT INTO "public"."yxy_permission" VALUES ('48', '平台客户绑定车辆审核', 'CUSTOMER_CARS_CHECK', '11', '1', '2017-12-20 17:36:07.023', '2017-12-20 17:36:07.023');
INSERT INTO "public"."yxy_permission" VALUES ('49', '平台客户查询', 'CUSTOMER_QUERY', '11', '1', '2017-12-20 17:36:07.023', '2017-12-20 17:36:07.023');

--首页管理
INSERT INTO "public"."yxy_permission" VALUES ('50', '当日统计信息', 'SAME_DAY_INFORMATION', '12', '1', '2017-12-20 17:36:07.023', '2017-12-20 17:36:07.023');

--日志管理
INSERT INTO "public"."yxy_permission" VALUES ('51', '日志管理', 'LOG_MANAGE', '8', '1', '2017-12-20 17:36:07.023', '2017-12-20 17:36:07.023');

--优惠券管理
INSERT INTO "public"."yxy_permission" VALUES ('52', '优惠券管理', 'COUPON_MANAGE', '10', '1', '2017-12-20 17:36:07.023', '2017-12-20 17:36:07.023');



INSERT INTO "public"."yxy_resource" VALUES ('1', '首页', 'INDEX', '', '0', '-1', '1', '1', 'f', 't', '/', '&#xe63b;', '1', null, null, '1');
INSERT INTO "public"."yxy_resource" VALUES ('49', '区域管理', 'AREA_LIST', '', '0', '100', '2', '100|101', 'f', 't', '/basic_info_manage/area_manage/area_list', '', '1', null, null, '49');
INSERT INTO "public"."yxy_resource" VALUES ('50', '单位管理', 'ORG_LIST', '', '0', '100', '2', '100|50', 'f', 't', '/basic_info_manage/unit_info_manage/unit_list', '', '1', null, null, '50');
INSERT INTO "public"."yxy_resource" VALUES ('100', '基础信息管理', 'basic_information', '', '0', '-1', '1', '100', 'f', 'f', '', '&#xe639;', '1', null, null, '100');
INSERT INTO "public"."yxy_resource" VALUES ('102', '添加区域', 'AREA_CREATE', '', '0', '101', '3', '100|101|102', 't', 't', '/basic_info_manage/area_manage/area_add', '', '1', null, null, '102');
INSERT INTO "public"."yxy_resource" VALUES ('103', '区域修改', 'AREA_UPDATE', '', '0', '101', '3', '100|101|103', 't', 't', '/basic_info_manage/area_manage/area_update/:id', '', '1', null, null, '103');
INSERT INTO "public"."yxy_resource" VALUES ('110', '停车场信息管理', 'PARKING_LIST', '', '0', '100', '2', '100|110', 'f', 't', '/basic_info_manage/parking_info_manage/parking_list', '', '1', null, null, '110');
INSERT INTO "public"."yxy_resource" VALUES ('111', '添加停车场信息', 'PARKING_CREATE', '', '0', '110', '3', '100|110|111', 't', 't', '/basic_info_manage/parking_info_manage/parking_add', '', '1', null, null, '111');
INSERT INTO "public"."yxy_resource" VALUES ('112', '停车场信息详情', 'PARKING_DETAIL', '', '0', '110', '3', '100|110|112', 't', 't', '/basic_info_manage/parking_info_manage/parking_detail/:id', '', '1', null, null, '112');
INSERT INTO "public"."yxy_resource" VALUES ('113', '停车场信息修改', 'PARKING_UPDATE', '', '0', '110', '3', '100|110|113', 't', 't', '/basic_info_manage/parking_info_manage/parking_update/:id', '', '1', null, null, '113');
INSERT INTO "public"."yxy_resource" VALUES ('114', '停车场信息认证', 'PARKING_APPROVE', '', '0', '110', '3', '100|110|114', 't', 't', '/basic_info_manage/parking_info_manage/parking_approve/:id', '', '1', null, null, '114');
INSERT INTO "public"."yxy_resource" VALUES ('115', '停车场停用', 'PARKING_NONUSE', '', '0', '110', '3', '100|110|115', 't', 't', '/basic_info_manage/parking_info_manage/non_use/:id', '', '1', null, null, '115');
INSERT INTO "public"."yxy_resource" VALUES ('116', '停车场启用', 'PARKING_USING', '', '0', '110', '3', '100|110|116', 't', 't', '/basic_info_manage/parking_info_manage/start_using/:id', '', '1', null, null, '116');
INSERT INTO "public"."yxy_resource" VALUES ('117', '停车场认证通过', 'PARKING_EXAMINE', '', '0', '110', '3', '100|110|117', 't', 't', '/basic_info_manage/parking_info_manage/examine/:id', '', '1', null, null, '117');
INSERT INTO "public"."yxy_resource" VALUES ('118', '停车场认证拒绝', 'PARKING_REFUSE', '', '0', '110', '3', '100|110|118', 't', 't', '/basic_info_manage/parking_info_manage/refuse/:id', '', '1', null, null, '118');
INSERT INTO "public"."yxy_resource" VALUES ('119', '停车场绑定费率', 'PARKING_BIND_FEE_SCHEMA', '', '0', '110', '3', '100|110|119', 't', 't', '/basic_info_manage/parking_info_manage/bind_fee_schema/:id', '', '1', null, null, '119');
INSERT INTO "public"."yxy_resource" VALUES ('120', '停车场资源管理', 'PARKING_PORT_MANAGE', '', '0', '100', '2', '100|120', 'f', 'f', '', '', '1', null, null, '120');
INSERT INTO "public"."yxy_resource" VALUES ('121', '出入口管理', 'PARKING_PORT_LIST', '', '0', '120', '3', '100|120|121', 'f', 't', '/basic_info_manage/parking_manage/im_export_manage', '', '1', null, null, '121');
INSERT INTO "public"."yxy_resource" VALUES ('122', '添加出入口', 'PORT_CREATE', '', '0', '121', '4', '100|120|121|122', 't', 't', '/basic_info_manage/parking_manage/im_export_add', '', '1', null, null, '122');
INSERT INTO "public"."yxy_resource" VALUES ('123', '出入口详情', 'PORT_DETAIL', '', '0', '121', '4', '100|120|121|123', 't', 't', '/basic_info_manage/parking_manage/im_export_detail/:id', '', '1', null, null, '123');
INSERT INTO "public"."yxy_resource" VALUES ('124', '修改出入口', 'PORT_UPDATE', '', '0', '121', '4', '100|120|121|124', 't', 't', '/basic_info_manage/parking_manage/im_export_update/:id', '', '1', null, null, '124');
INSERT INTO "public"."yxy_resource" VALUES ('125', '出入口启用', 'PORT_USING', '', '0', '121', '4', '100|120|121|125', 't', 't', '/basic_info_manage/parking_manage/im_export_using/:id', '', '1', null, null, '125');
INSERT INTO "public"."yxy_resource" VALUES ('126', '出入口停用', 'PORT_NONUSE', '', '0', '121', '4', '100|120|121|126', 't', 't', '/basic_info_manage/parking_manage/im_export_using/:id', '', '1', null, null, '126');
INSERT INTO "public"."yxy_resource" VALUES ('130', '车道管理', 'PARKING_LANE_LIST', '', '0', '120', '3', '100|120|130', 'f', 't', '/basic_info_manage/parking_manage/lane_manage', '', '1', null, null, '130');
INSERT INTO "public"."yxy_resource" VALUES ('131', '添加车道', 'LANE_CREATE', '', '0', '130', '4', '100|120|130|131', 't', 't', '/basic_info_manage/parking_manage/lane_add', '', '1', null, null, '131');
INSERT INTO "public"."yxy_resource" VALUES ('132', '车道详情', 'LANE_DETAIL', '', '0', '130', '4', '100|120|130|132', 't', 't', '/basic_info_manage/parking_manage/lane_detail/:id', '', '1', null, null, '132');
INSERT INTO "public"."yxy_resource" VALUES ('133', '修改车道', 'LANE_UPDATE', '', '0', '130', '4', '100|120|130|133', 't', 't', '/basic_info_manage/parking_manage/lane_update/:id', '', '1', null, null, '133');
INSERT INTO "public"."yxy_resource" VALUES ('134', '车道启用', 'LANE_USING', '', '0', '130', '4', '100|120|130|134', 't', 't', '/basic_info_manage/parking_manage/lane_using/:id', '', '1', null, null, '134');
INSERT INTO "public"."yxy_resource" VALUES ('135', '车道停用', 'LANE_NONUSE', '', '0', '130', '4', '100|120|130|135', 't', 't', '/basic_info_manage/parking_manage/lane_nonuse/:id', '', '1', null, null, '135');
INSERT INTO "public"."yxy_resource" VALUES ('140', '车位管理', 'PARKING_CELL_LIST', '', '0', '120', '3', '100|120|140', 'f', 't', '/basic_info_manage/parking_manage/parking_position_manage', '', '1', null, null, '140');
INSERT INTO "public"."yxy_resource" VALUES ('141', '添加车位', 'PARKING_CELL_CREATE', '', '0', '140', '4', '100|120|140|141', 't', 't', '/basic_info_manage/parking_manage/parking_position_add', '', '1', null, null, '141');
INSERT INTO "public"."yxy_resource" VALUES ('142', '车位详情', 'PARKING_CELL_DETAIL', '', '0', '140', '4', '100|120|140|142', 't', 't', '/basic_info_manage/parking_manage/parking_position_detail/:id', '', '1', null, null, '142');
INSERT INTO "public"."yxy_resource" VALUES ('143', '修改车位', 'PARKING_CELL_UPDATE', '', '0', '140', '4', '100|120|140|143', 't', 't', '/basic_info_manage/parking_manage/parking_position_update/:id', '', '1', null, null, '143');
INSERT INTO "public"."yxy_resource" VALUES ('144', '车位启用', 'PARKING_CELL_USING', '', '0', '140', '4', '100|120|140|144', 't', 't', '/basic_info_manage/parking_manage/parking_position_using/:id', '', '1', null, null, '144');
INSERT INTO "public"."yxy_resource" VALUES ('145', '车位停用', 'PARKING_CELL_NONUSE', '', '0', '140', '4', '100|120|140|145', 't', 't', '/basic_info_manage/parking_manage/parking_position_nonuse/:id', '', '1', null, null, '145');
INSERT INTO "public"."yxy_resource" VALUES ('151', '新增单位', 'ORG_CREATE', '', '0', '50', '3', '100|50|151', 't', 't', '/basic_info_manage/unit_info_manage/unit_add', '', '1', null, null, '151');
INSERT INTO "public"."yxy_resource" VALUES ('152', '单位详情', 'ORG_DETAIL', '', '0', '50', '3', '100|50|152', 't', 't', '/basic_info_manage/unit_info_manage/unit_detail/:id', '', '1', null, null, '152');
INSERT INTO "public"."yxy_resource" VALUES ('153', '单位修改', 'ORG_UPDATE', '', '0', '50', '3', '100|50|153', 't', 't', '/basic_info_manage/unit_info_manage/unit_update/:id', '', '1', null, null, '153');
INSERT INTO "public"."yxy_resource" VALUES ('154', '单位认证', 'ORG_APPROVE', '', '0', '50', '3', '100|50|154', 't', 't', '/basic_info_manage/unit_info_manage/unit_approve/:id', '', '1', null, null, '154');
INSERT INTO "public"."yxy_resource" VALUES ('156', '单位绑定', 'ORG_BIND_UPDATE', '', '0', '50', '3', '100|50|156', 'f', 't', '/basic_info_manage/unit_info_manage/unit_bind_update/:id', '', '1', null, null, '156');
INSERT INTO "public"."yxy_resource" VALUES ('157', '单位认证通过', 'ORG_EXAMINE', '', '0', '50', '3', '100|50|157', 'f', 't', '/basic_info_manage/unit_info_manage/examine/:id', '', '1', null, null, '157');
INSERT INTO "public"."yxy_resource" VALUES ('158', '单位认证拒绝', 'ORG_REFUSE', '', '0', '50', '3', '100|50|158', 'f', 't', '/basic_info_manage/unit_info_manage/refuse/:id', '', '1', null, null, '158');
INSERT INTO "public"."yxy_resource" VALUES ('200', '设备管理', 'equipment_manage', '', '0', '-1', '1', '200', 'f', 'f', '', '&#xe637;', '1', null, null, '200');
INSERT INTO "public"."yxy_resource" VALUES ('201', '收费终端管理', 'CHARGEFACILITY_LIST', '', '0', '200', '2', '200|201', 'f', 't', '/equipment_manage/terminal_device_list', '', '1', null, null, '201');
INSERT INTO "public"."yxy_resource" VALUES ('202', '添加收费终端', 'CHARGEFACILITY_CREATE', '', '0', '201', '3', '200|201|202', 't', 't', '/equipment_manage/charge_terminal_manage/terminal_add', '', '1', null, null, '202');
INSERT INTO "public"."yxy_resource" VALUES ('203', '收费终端详情', 'CHARGEFACILITY_DETAIL', '', '0', '201', '3', '200|201|203', 't', 't', '/equipment_manage/charge_terminal_manage/terminal_detail/:id', '', '1', null, null, '203');
INSERT INTO "public"."yxy_resource" VALUES ('204', '修改收费终端', 'CHARGEFACILITY_UPDATE', '', '0', '201', '3', '200|201|204', 't', 't', '/equipment_manage/charge_terminal_manage/terminal_update/:id', '', '1', null, null, '204');
INSERT INTO "public"."yxy_resource" VALUES ('205', '收费终端设备启用', 'CHARGEFACILITY_USING', '', '0', '201', '3', '200|201|205', 't', 't', '/equipment_manage/charge_terminal_manage/terminal_using/:id', '', '1', null, null, '205');
INSERT INTO "public"."yxy_resource" VALUES ('206', '收费终端设备停用', 'CHARGEFACILITY_NONUSE', '', '0', '201', '3', '200|201|206', 't', 't', '/equipment_manage/charge_terminal_manage/terminal_nonuse/:id', '', '1', null, null, '206');
INSERT INTO "public"."yxy_resource" VALUES ('210', '地磁设备管理', 'MAGNETICDEVICE_LIST', '', '0', '200', '2', '200|210', 'f', 't', '/equipment_manage/geomagnetic_manage/geomagnetic_list', '', '1', null, null, '210');
INSERT INTO "public"."yxy_resource" VALUES ('211', '添加地磁设备', 'MAGNETICDEVICE_CREATE', '', '0', '210', '3', '200|210|211', 't', 't', '/equipment_manage/geomagnetic_manage/geomagnetism_add', '', '1', null, null, '211');
INSERT INTO "public"."yxy_resource" VALUES ('212', '地磁设备详情', 'MAGNETICDEVICE_DETAIL', '', '0', '210', '3', '200|210|212', 't', 't', '/equipment_manage/geomagnetic_manage/geomagnetism_detail/:id', '', '1', null, null, '212');
INSERT INTO "public"."yxy_resource" VALUES ('213', '修改地磁设备', 'MAGNETICDEVICE_UPDATE', '', '0', '210', '3', '200|210|213', 't', 't', '/equipment_manage/geomagnetic_manage/geomagnetism_update/:id', '', '1', null, null, '213');
INSERT INTO "public"."yxy_resource" VALUES ('214', '地磁设备批量启用', 'MAGNETICDEVICE_USING', '', '0', '210', '3', '200|210|214', 't', 't', '/equipment_manage/geomagnetic_manage/geomagnetism_using/:id', '', '1', null, null, '214');
INSERT INTO "public"."yxy_resource" VALUES ('215', '地磁设备批量停用', 'MAGNETICDEVICE_NONUSE', '', '0', '210', '3', '200|210|215', 't', 't', '/equipment_manage/geomagnetic_manage/geomagnetism_nonuse/:id', '', '1', null, null, '215');
INSERT INTO "public"."yxy_resource" VALUES ('220', 'PSAM管理', 'PSAM_LIST', '', '0', '200', '2', '200|220', 'f', 't', '/equipment_manage/psam_manage/psam_list', '', '1', null, null, '220');
INSERT INTO "public"."yxy_resource" VALUES ('221', '添加PSAM', 'PSAM_CREATE', '', '0', '220', '3', '200|220|221', 't', 't', '/equipment_manage/psam_manage/psam_add', '', '1', null, null, '221');
INSERT INTO "public"."yxy_resource" VALUES ('222', 'PSAM详情', 'PSAM_DETAIL', '', '0', '220', '3', '200|220|222', 't', 't', '/equipment_manage/psam_manage/psam_detail/:id', '', '1', null, null, '222');
INSERT INTO "public"."yxy_resource" VALUES ('223', '修改PSAM', 'PSAM_UPDATE', '', '0', '220', '3', '200|220|223', 't', 't', '/equipment_manage/psam_manage/psam_update/:id', '', '1', null, null, '223');
INSERT INTO "public"."yxy_resource" VALUES ('230', 'SIM卡管理', 'SIM_LIST', '', '0', '200', '2', '200|230', 'f', 't', '/equipment_manage/sim_card_manage/sim_card_list', '', '1', null, null, '230');
INSERT INTO "public"."yxy_resource" VALUES ('231', '添加SIM卡', 'SIM_CREATE', '', '0', '230', '3', '200|230|231', 't', 't', '/equipment_manage/sim_card_manage/sim_card_add', '', '1', null, null, '231');
INSERT INTO "public"."yxy_resource" VALUES ('232', 'SIM卡详情', 'SIM_DETAIL', '', '0', '230', '3', '200|230|232', 't', 't', '/equipment_manage/sim_card_manage/sim_card_detail/:id', '', '1', null, null, '232');
INSERT INTO "public"."yxy_resource" VALUES ('233', '修改SIM卡', 'SIM_UPDATE', '', '0', '230', '3', '200|230|233', 't', 't', '/equipment_manage/sim_card_manage/sim_card_update/:id', '', '1', null, null, '233');
INSERT INTO "public"."yxy_resource" VALUES ('234', 'SIM卡批量启用', 'SIM_USING', '', '0', '230', '3', '200|230|234', 't', 't', '/equipment_manage/sim_card_manage/sim_card_using/:id', '', '1', null, null, '234');
INSERT INTO "public"."yxy_resource" VALUES ('235', 'SIM卡批量停用', 'SIM_NONUSE', '', '0', '230', '3', '200|230|235', 't', 't', '/equipment_manage/sim_card_manage/sim_card_nonuse/:id', '', '1', null, null, '235');
INSERT INTO "public"."yxy_resource" VALUES ('240', '视频监控器管理', 'VIDEO_MONITOR_LIST', '', '0', '200', '2', '200|240', 'f', 't', '/equipment_manage/video_monitor_manage/video_monitor_list', '', '1', null, null, '240');
INSERT INTO "public"."yxy_resource" VALUES ('241', '添加视频监控器', 'VIDEO_MONITOR_CREATE', '', '0', '240', '3', '200|240|241', 't', 't', '/equipment_manage/sim_card_manage/video_monitor_add', '', '1', null, null, '241');
INSERT INTO "public"."yxy_resource" VALUES ('242', '视频监控器详情', 'VIDEO_MONITOR_DETAIL', '', '0', '240', '3', '200|240|242', 't', 't', '/equipment_manage/sim_card_manage/video_monitor_detail/:id', '', '1', null, null, '242');
INSERT INTO "public"."yxy_resource" VALUES ('243', '视频监控器修改', 'VIDEO_MONITOR_UPDATE', '', '0', '240', '3', '200|240|243', 't', 't', '/equipment_manage/sim_card_manage/video_monitor_update/:id', '', '1', null, null, '243');
INSERT INTO "public"."yxy_resource" VALUES ('244', '视频监控器批量启用', 'VIDEO_MONITOR_USING', '', '0', '240', '3', '200|240|244', 't', 't', '/equipment_manage/sim_card_manage/video_monitor_using/:id', '', '1', null, null, '244');
INSERT INTO "public"."yxy_resource" VALUES ('245', '视频监控器批量停用', 'VIDEO_MONITOR_NONUSE', '', '0', '240', '3', '200|240|245', 't', 't', '/equipment_manage/sim_card_manage/video_monitor_nonuse/:id', '', '1', null, null, '245');
INSERT INTO "public"."yxy_resource" VALUES ('250', '视频桩管理', 'VIDEO_PILE_LIST', '', '0', '200', '2', '200|250', 'f', 't', '/equipment_manage/video_pile_manage/video_pile_list', '', '1', null, null, '250');
INSERT INTO "public"."yxy_resource" VALUES ('251', '添加视频桩', 'VIDEO_PILE_CREATE', '', '0', '250', '3', '200|250|251', 't', 't', '/equipment_manage/video_pile_manage/video_pile_add', '', '1', null, null, '251');
INSERT INTO "public"."yxy_resource" VALUES ('252', '视频桩详情', 'VIDEO_PILE_DETAIL', '', '0', '250', '3', '200|250|252', 't', 't', '/equipment_manage/video_pile_manage/video_pile_detail:id', '', '1', null, null, '252');
INSERT INTO "public"."yxy_resource" VALUES ('253', '修改视频桩', 'VIDEO_PILE_UPDATE', '', '0', '250', '3', '200|250|253', 't', 't', '/equipment_manage/video_pile_manage/video_pile_update:id', '', '1', null, null, '253');
INSERT INTO "public"."yxy_resource" VALUES ('254', '视频桩批量启用', 'VIDEO_PILE_USING', '', '0', '250', '3', '200|250|254', 't', 't', '/equipment_manage/video_pile_manage/video_pile_using:id', '', '1', null, null, '254');
INSERT INTO "public"."yxy_resource" VALUES ('255', '视频桩批量停用', 'VIDEO_PILE_NONUSE', '', '0', '250', '3', '200|250|255', 't', 't', '/equipment_manage/video_pile_manage/video_pile_nonuse:id', '', '1', null, null, '255');
INSERT INTO "public"."yxy_resource" VALUES ('300', '账号权限管理', 'account_auth_manage', '', '0', '-1', '1', '300', 'f', 'f', '', '&#xe638;', '1', null, null, '300');
INSERT INTO "public"."yxy_resource" VALUES ('301', '平台用户管理', 'USER_LIST', '', '0', '300', '2', '300|301', 'f', 't', '/users_permissions/users', '', '1', null, null, '301');
INSERT INTO "public"."yxy_resource" VALUES ('302', '平台用户新增', 'USER_CREATE', '', '0', '301', '2', '300|301|302', 't', 't', '/users_permissions/users/add', '', '1', null, null, '302');
INSERT INTO "public"."yxy_resource" VALUES ('303', '角色管理', 'ROLE_LIST', '', '0', '300', '2', '300|303', 'f', 't', '/users_permissions/roles', '', '1', null, null, '303');
INSERT INTO "public"."yxy_resource" VALUES ('304', '角色新增', 'ROLE_CREATE', '', '0', '303', '3', '300|303|304', 't', 't', '/users_permissions/roles/add', '', '1', null, null, '304');
INSERT INTO "public"."yxy_resource" VALUES ('305', '角色修改', 'ROLE_UPDATE', '', '0', '303', '3', '300|303|305', 't', 't', '/users_permissions/roles/:id/edit', '', '1', null, null, '305');
INSERT INTO "public"."yxy_resource" VALUES ('306', '角色权限绑定', 'ROLE_PERMISSION_BIND', '', '0', '303', '3', '300|303|306', 't', 't', '/users_permissions/roles/:id/permissions', '', '1', null, null, '306');
INSERT INTO "public"."yxy_resource" VALUES ('307', '平台用户修改', 'USER_UPDATE', '', '0', '301', '2', '300|301|307', 't', 't', '/users_permissions/users/:id/edit', '', '1', null, null, '307');
INSERT INTO "public"."yxy_resource" VALUES ('320', '用户组管理', 'USER_GROUP_LIST', '', '0', '300', '2', '300|320', 'f', 't', '/users_permissions/user_group', '', '1', null, null, '320');
INSERT INTO "public"."yxy_resource" VALUES ('321', '用户组新增', 'USER_GROUP_CREATE', '', '0', '320', '3', '300|320|321', 't', 't', '/users_permissions/user_group/add', '', '1', null, null, '321');
INSERT INTO "public"."yxy_resource" VALUES ('322', '用户组修改', 'USER_GROUP_UPDATE', '', '0', '320', '3', '300|320|322', 't', 't', '/users_permissions/user_group/update/:id', '', '1', null, null, '322');
INSERT INTO "public"."yxy_resource" VALUES ('323', '用户组绑定', 'USER_GROUP_BIND', '', '0', '320', '3', '300|320|323', 't', 't', '/users_permissions/user_group/bind/:id', '', '1', null, null, '323');
INSERT INTO "public"."yxy_resource" VALUES ('324', '用户组删除', 'USER_GROUP_DELETE', '', '0', '320', '3', '300|320|324', 't', 't', '/users_permissions/user_group/bind_update/:id', '', '1', null, null, '324');
INSERT INTO "public"."yxy_resource" VALUES ('325', '用户权限管理', 'USER_GROUP_ROLE_LIST', '', '0', '300', '2', '300|325', 'f', 't', '/users_permissions/user_group_role', '', '1', null, null, '325');
INSERT INTO "public"."yxy_resource" VALUES ('326', '用户权限修改', 'USER_GROUP_ROLE_UPDATE', '', '0', '325', '3', '300|325|326', 't', 't', '/users_permissions/user_group_role/update/:id', '', '1', null, null, '326');
INSERT INTO "public"."yxy_resource" VALUES ('330', '权限管理', 'PERMISSION_LIST', '', '0', '300', '2', '300|330', 'f', 't', '/users_permissions/permission', '', '1', null, null, '330');
INSERT INTO "public"."yxy_resource" VALUES ('331', '绑定资源', 'PERMISSION_BIND_RESOURCE', '', '0', '330', '3', '300|330|331', 't', 't', '/users_permissions/permission/:id/bind', '', '1', null, null, '331');
INSERT INTO "public"."yxy_resource" VALUES ('400', '优惠券管理', 'coupon_manage', '', '0', '-1', '1', '400', 'f', 'f', '', '&#xe63d;', '1', null, null, '400');
INSERT INTO "public"."yxy_resource" VALUES ('401', '优惠券列表', 'COUPON_LIST', '', '0', '400', '2', '400|401', 'f', 't', '/coupon_manage', '', '1', null, null, '401');
INSERT INTO "public"."yxy_resource" VALUES ('403', '优惠券详情', 'COUPON_DETAIL', '', '0', '401', '3', '400|401|403', 't', 't', '/coupon_manage/coupon_detail/:id', '', '1', null, null, '403');
INSERT INTO "public"."yxy_resource" VALUES ('500', '交易管理', 'transaction_manage', '', '0', '-1', '1', '500', 'f', 'f', '', '&#xe641;', '1', null, null, '500');
INSERT INTO "public"."yxy_resource" VALUES ('501', '订单列表', 'ORDER_FORM_LIST', '', '0', '500', '2', '500|501', 'f', 't', '/transaction_manage/order_manage/order_list', '', '1', null, null, '501');
INSERT INTO "public"."yxy_resource" VALUES ('502', '修改订单信息', 'ORDER_FORM_UPDATE', '', '0', '501', '3', '500|501|502', 't', 't', '/transaction_manage/order_manage/update/:id', '', '1', null, null, '502');
INSERT INTO "public"."yxy_resource" VALUES ('503', '订单详情', 'ORDER_FORM_DETAIL', '', '0', '501', '3', '500|501|503', 't', 't', '/transaction_manage/order_manage/detail/:id', '', '1', null, null, '503');
INSERT INTO "public"."yxy_resource" VALUES ('504', '支付管理', 'PAYMENT_LIST', '', '0', '500', '2', '500|504', 'f', 't', '/transaction_manage/payment_manage/payment_list', '', '1', null, null, '504');
INSERT INTO "public"."yxy_resource" VALUES ('505', '支付详情', 'PAYMENT_DETAIL', '', '0', '504', '3', '500|504|505', 't', 't', '/transaction_manage/payment_manage/payment_detail/:id', '', '1', null, null, '505');
INSERT INTO "public"."yxy_resource" VALUES ('507', '移除订单', 'ORDER_FORM_DELETE', '', '0', '501', '3', '500|501|507', 't', 't', '/transaction_manage/order_manage/delete', '', '1', null, null, '507');
INSERT INTO "public"."yxy_resource" VALUES ('600', '统计分析', 'statictical_analysis', '', '0', '-1', '1', '600', 'f', 'f', '', '&#xe63c;', '1', null, null, '600');
INSERT INTO "public"."yxy_resource" VALUES ('601', '地区区域化分布数据', 'AREA_DATA', '', '0', '600', '2', '600|601', 'f', 't', '/statistical_analysis/map_area_distribution_data', '', '1', null, null, '601');
INSERT INTO "public"."yxy_resource" VALUES ('602', '分析决策', 'DECESION_ANALYSIS', '', '0', '600', '2', '600|602', 'f', 'f', '', '', '1', null, null, '602');
INSERT INTO "public"."yxy_resource" VALUES ('603', '车位利用率', 'CELL_UTILIZATION', '', '0', '602', '3', '600|602|603', 'f', 't', '/statistical_analysis/analysis_page', '', '1', null, null, '603');
INSERT INTO "public"."yxy_resource" VALUES ('604', '车位翻转率', 'CELL_REVERSE_RATE', '', '0', '602', '3', '600|602|604', 'f', 't', '/statistical_analysis/analysis_flip', '', '1', null, null, '604');
INSERT INTO "public"."yxy_resource" VALUES ('605', '车位占用率', 'CELL_USAGE', '', '0', '602', '3', '600|602|605', 'f', 't', '/statistical_analysis/analysis_occupy', '', '1', null, null, '605');
INSERT INTO "public"."yxy_resource" VALUES ('606', '收益统计', 'GROSS_INCOME', '', '0', '602', '3', '600|602|606', 'f', 't', '/statistical_analysis/earnings_statistics', '', '1', null, null, '606');
INSERT INTO "public"."yxy_resource" VALUES ('607', '统计报表', 'STATICTICAL_REPORTS', '', '0', '600', '2', '600|607', 'f', 'f', '', '', '1', null, null, '607');
INSERT INTO "public"."yxy_resource" VALUES ('608', '停车点收费报表', 'PARKING_CHARGE_REPORT_FORM', '', '0', '607', '3', '600|607|608', 'f', 't', '/statistical_analysis/charge_report_form', '', '1', null, null, '608');
INSERT INTO "public"."yxy_resource" VALUES ('609', '停车报表', 'PARKING_REPORT_FORM', '', '0', '607', '3', '600|607|609', 'f', 't', '/statistical_analysis/parking_report_form', '', '1', null, null, '609');
INSERT INTO "public"."yxy_resource" VALUES ('700', '状态名单管理', 'STATUS_LIST', '', '0', '-1', '1', '700', 'f', 'f', '', '&#xe6f6;', '1', null, null, '700');
INSERT INTO "public"."yxy_resource" VALUES ('701', '车辆黑名单列表', 'VEHICLE_BLACK_LIST', '', '0', '700', '2', '700|701', 'f', 't', '/status_list_manage/vehicle_blacklist_list', '', '1', null, null, '701');
INSERT INTO "public"."yxy_resource" VALUES ('702', '新增黑名单', 'VEHICLE_BLACK_CREATE', '', '0', '701', '3', '700|701|702', 't', 't', '/status_list_manage/vehicle_blacklist_manage/vehicle_blacklist_add', '&#xe6f6;', '1', null, null, '702');
INSERT INTO "public"."yxy_resource" VALUES ('703', '车辆白名单', 'VEHICLE_WHITE_LIST', '', '0', '700', '2', '700|703', 'f', 't', '/status_list_manage/vehicle_whitelist_list', '', '1', null, null, '703');
INSERT INTO "public"."yxy_resource" VALUES ('704', '新增白名单', 'VEHICLE_WHITE_CREATE', '', '0', '703', '3', '700|703|704', 't', 't', '/status_list_manage/vehicle_whitelist_add', '', '1', null, null, '704');
INSERT INTO "public"."yxy_resource" VALUES ('705', '免费车名单', 'FREE_CAR_LIST', '', '0', '700', '2', '700|705', 'f', 't', '/status_list_manage/free_car_list', '', '1', null, null, '705');
INSERT INTO "public"."yxy_resource" VALUES ('706', '新增免费车名单', 'FREE_CAR_CREATE', '', '0', '705', '3', '700|705|706', 't', 't', '/status_list_manage/free_car_add', '', '1', null, null, '706');
INSERT INTO "public"."yxy_resource" VALUES ('707', 'ETC列表', 'ETC_LIST', '', '0', '700', '2', '700|707', 'f', 't', '/status_list_manage/etc_car_list', '', '1', null, null, '707');
INSERT INTO "public"."yxy_resource" VALUES ('708', '移除黑名单', 'VEHICLE_BLACK_UPDATE', '', '0', '701', '3', '700|702|708', 't', 't', '/status_list_manage/vehicle_blacklist_manage/vehicle_blacklist_update', '&#xe6f6;', '1', null, null, '708');
INSERT INTO "public"."yxy_resource" VALUES ('709', '移除白名单', 'VEHICLE_WHITE_UPDATE', '', '0', '703', '3', '700|704|709', 't', 't', '/status_list_manage/vehicle_whitelist_update', '', '1', null, null, '709');
INSERT INTO "public"."yxy_resource" VALUES ('710', '移除免费车名单', 'FREE_CAR_UPDATE', '', '0', '705', '3', '700|706|710', 't', 't', '/status_list_manage/free_car_add', '', '1', null, null, '710');
INSERT INTO "public"."yxy_resource" VALUES ('711', '查看免费车名单', 'FREE_CAR_EDIT', '', '0', '705', '3', '700|706|711', 't', 't', '/status_list_manage/free_car_edit/:id', '', '1', null, null, '711');
INSERT INTO "public"."yxy_resource" VALUES ('800', '发票管理', 'INVOICE_MANAGE', '', '0', '-1', '1', '800', 'f', 'f', '', '&#xe640;', '1', null, null, '800');
INSERT INTO "public"."yxy_resource" VALUES ('801', '发票列表', 'PLATFORM_INVOICE_LIST', '', '0', '800', '2', '800|801', 'f', 't', '/invoice_manage/platform_invoice_list', '', '1', null, null, '801');
INSERT INTO "public"."yxy_resource" VALUES ('802', '订单发票列表', 'PLATFORM_ORDER_INVOICE_LIST', '', '0', '800', '2', '800|802', 'f', 't', '/invoice_manage/platform_invoice_manage/platform_order_invoice_list', '', '1', null, null, '802');
INSERT INTO "public"."yxy_resource" VALUES ('803', '发票详情', 'PLATFORM_INVOICE_DETAIL', '', '0', '802', '3', '800|802|803', 't', 't', '/invoice_manage/platform_invoice_manage/platform_invoice_detail/:id', '', '1', null, null, '803');
INSERT INTO "public"."yxy_resource" VALUES ('900', '日志管理', 'LOG_MANAGE', '', '0', '-1', '1', '900', 'f', 'f', '', '&#xe625;', '1', null, null, '900');
INSERT INTO "public"."yxy_resource" VALUES ('901', '日志列表', 'TRANSACTION_lLOG_LIST', '', '0', '900', '2', '900|901', 'f', 't', '/log_manage/transaction_log_list', '', '1', null, null, '901');
INSERT INTO "public"."yxy_resource" VALUES ('902', '日志详情', 'TRANSACTION_LOG_DETAIL', '', '0', '901', '3', '900|901|902', 't', 't', '/log_manage/transaction_log_detail/:id', null, '1', null, null, '902');
INSERT INTO "public"."yxy_resource" VALUES ('1000', '系统管理', 'SYSTEM_MANAGE', '', '0', '-1', '1', '1000', 'f', 'f', '', '&#xe621;', '1', null, null, '1000');
INSERT INTO "public"."yxy_resource" VALUES ('1002', '费率管理', '参数管理', '', '0', '1000', '2', '1000|1002', 'f', 'f', '', '', '1', null, null, '1002');
INSERT INTO "public"."yxy_resource" VALUES ('1003', '费率计划管理', 'FEE_SCHEMA_LIST', '', '0', '1002', '3', '1000|1002|1003', 'f', 't', '/system_manage/parameter_manage/fee_schema_manage', '', '1', null, null, '1003');
INSERT INTO "public"."yxy_resource" VALUES ('1004', '新增费率计划', 'FEE_SCHEMA_CREATE', '', '0', '1003', '4', '1000|1002|1003|1004', 't', 't', '/system_manage/parameter_manage/fee_schema_add', '', '1', null, null, '1004');
INSERT INTO "public"."yxy_resource" VALUES ('1001', '费率计划列表', 'FEE_SCHEMA_INDEX', '', '0', '1003', '4', '1000|1002|1003|1001', 't', 't', '/system_manage/parameter_manage/fee_schema_index', '', '1', null, null, '1001');
INSERT INTO "public"."yxy_resource" VALUES ('1005', '修改费率计划', 'FEE_SCHEMA_UPDATE', '', '0', '1003', '4', '1000|1002|1003|1005', 't', 't', '/system_manage/parameter_manage/fee_schema_update/:id', '', '1', null, null, '1005');
INSERT INTO "public"."yxy_resource" VALUES ('1006', '费率规则管理', 'FEE_RATE_LIST', '', '0', '1002', '3', '1000|1002|1006', 'f', 't', '/system_manage/parameter_manage/fee_rate_list', '', '1', null, null, '1006');
INSERT INTO "public"."yxy_resource" VALUES ('1007', '新增费率规则', 'FEE_RATE_CREATE', '', '0', '1006', '4', '1000|1002|1006|1007', 't', 't', '/system_manage/parameter_manage/fee_rate_add', '', '1', null, null, '1007');
INSERT INTO "public"."yxy_resource" VALUES ('1008', '修改费率规则', 'FEE_RATE_UPDATE', '', '0', '1006', '4', '1000|1002|1006|1008', 't', 't', '/system_manage/parameter_manage/fee_rate_update/:id', '', '1', null, null, '1008');
INSERT INTO "public"."yxy_resource" VALUES ('1009', '删除费率规则', 'FEE_RATE_DELETE', '', '0', '1006', '4', '1000|1002|1006|1009', 't', 't', '/system_manage/parameter_manage/fee_rate_delete/:id', '', '1', null, null, '1009');
INSERT INTO "public"."yxy_resource" VALUES ('1012', '费率规则详情', 'FEE_RATE_DETAIL', '', '0', '1006', '4', '1000|1002|1006|1012', 't', 't', '/system_manage/parameter_manage/fee_rate_detail/:id', '', '1', null, null, '1012');
INSERT INTO "public"."yxy_resource" VALUES ('1011', '修改费率计划与规则绑定', 'FEE_RATE_SCHEMA', '', '0', '1003', '4', '1000|1002|1003|1011', 't', 't', '/system_manage/parameter_manage/fee_schema_rate_update/:id', '', '1', null, null, '1011');
INSERT INTO "public"."yxy_resource" VALUES ('1020', '费率计划绑定停车场', 'FEE_SCHEMA_BIND_PARKING', '', '0', '1003', '4', '1000|1002|1003|1020', 't', 't', '/system_manage/parameter_manage/fee_schema_parking_update/:id', '', '1', null, null, '1020');
INSERT INTO "public"."yxy_resource" VALUES ('1100', '假日管理', 'HOD_LIST', '', '0', '1000', '2', '1000|1100', 'f', 't', '/system_manage/holiday_manage', '', '1', null, null, '1100');
INSERT INTO "public"."yxy_resource" VALUES ('1101', '广告列表', 'ADV_LIST', '', '0', '1000', '2', '1000|1101', 'f', 't', '/system_manage/advertisement_list', '', '1', null, null, '1101');
INSERT INTO "public"."yxy_resource" VALUES ('1102', '新增广告', 'ADV_CREATE', '', '0', '1101', '3', '1000|1101|1002', 't', 't', '/system_manage/advertisement_add', '', '1', null, null, '1102');
INSERT INTO "public"."yxy_resource" VALUES ('1103', '修改广告', 'ADV_UPDATE', '', '0', '1101', '3', '1000|1101|1003', 't', 't', '/system_manage/advertisement_update/:id', '', '1', null, null, '1103');
INSERT INTO "public"."yxy_resource" VALUES ('1104', '假日批量导入', 'HOD_IMPORT', '', '0', '1100', '3', '1000|1100|1104', 'f', 't', '/system_manage/holiday_manage_import', '', '1', null, null, '1104');
INSERT INTO "public"."yxy_resource" VALUES ('1105', '假日下载模板', 'HOD_EXPORT', '', '0', '1100', '3', '1000|1100|1105', 'f', 't', '/system_manage/holiday_manage_export', '', '1', null, null, '1105');
INSERT INTO "public"."yxy_resource" VALUES ('1106', '广告批量启用', 'ADV_USING', '', '0', '1101', '3', '1000|1101|1104', 't', 't', '/system_manage/advertisement_using/:id', '', '1', null, null, null);
INSERT INTO "public"."yxy_resource" VALUES ('1107', '广告批量停用', 'ADV_NONUSE', '', '0', '1101', '3', '1000|1101|1105', 't', 't', '/system_manage/advertisement_nonuse/:id', '', '1', null, null, null);
INSERT INTO "public"."yxy_resource" VALUES ('1200', '平台客户管理', 'CUSTOMER', '', '0', '-1', '1', '1200', 'f', 'f', '', '&#xe699;', '1', null, null, '1200');
INSERT INTO "public"."yxy_resource" VALUES ('1201', '客户列表', 'CUSTOMER_LIST', '', '0', '1200', '2', '1200|1201', 'f', 't', '/customer/user/list', '', '1', null, null, '1201');
INSERT INTO "public"."yxy_resource" VALUES ('1221', '车辆认证管理', 'CUSTOMER_BIND_CAR_LIST', '', '0', '1200', '2', '1200|1221', 'f', 't', '/customer/user_bind_car/list', '', '1', null, null, '1221');
INSERT INTO "public"."yxy_resource" VALUES ('1222', '车辆认证通过', 'CUSTOMER_BIND_CAR_APP', '', '0', '1221', '3', '1200|1221|1222', 'f', 't', '/customer/user_bind_car/app', '', '1', null, null, '1222');
INSERT INTO "public"."yxy_resource" VALUES ('1223', '车辆认证拒绝', 'CUSTOMER_BIND_CAR_REFUSE', '', '0', '1221', '3', '1200|1221|1223', 'f', 't', '/customer/user_bind_car/cancel', '', '1', null, null, '1223');




INSERT INTO "public"."yxy_permission_resource" VALUES ('18', '1', '49');
INSERT INTO "public"."yxy_permission_resource" VALUES ('19', '1', '100');
INSERT INTO "public"."yxy_permission_resource" VALUES ('20', '2', '111');
INSERT INTO "public"."yxy_permission_resource" VALUES ('21', '2', '110');
INSERT INTO "public"."yxy_permission_resource" VALUES ('22', '2', '100');
INSERT INTO "public"."yxy_permission_resource" VALUES ('23', '3', '113');
INSERT INTO "public"."yxy_permission_resource" VALUES ('24', '3', '110');
INSERT INTO "public"."yxy_permission_resource" VALUES ('25', '3', '100');
INSERT INTO "public"."yxy_permission_resource" VALUES ('26', '4', '114');
INSERT INTO "public"."yxy_permission_resource" VALUES ('27', '4', '110');
INSERT INTO "public"."yxy_permission_resource" VALUES ('28', '4', '100');
INSERT INTO "public"."yxy_permission_resource" VALUES ('29', '5', '115');
INSERT INTO "public"."yxy_permission_resource" VALUES ('30', '5', '110');
INSERT INTO "public"."yxy_permission_resource" VALUES ('31', '5', '100');
INSERT INTO "public"."yxy_permission_resource" VALUES ('32', '6', '119');
INSERT INTO "public"."yxy_permission_resource" VALUES ('33', '6', '110');
INSERT INTO "public"."yxy_permission_resource" VALUES ('34', '6', '100');
INSERT INTO "public"."yxy_permission_resource" VALUES ('35', '7', '117');
INSERT INTO "public"."yxy_permission_resource" VALUES ('36', '7', '110');
INSERT INTO "public"."yxy_permission_resource" VALUES ('37', '7', '100');
INSERT INTO "public"."yxy_permission_resource" VALUES ('38', '7', '118');
INSERT INTO "public"."yxy_permission_resource" VALUES ('39', '8', '112');
INSERT INTO "public"."yxy_permission_resource" VALUES ('40', '8', '110');
INSERT INTO "public"."yxy_permission_resource" VALUES ('41', '8', '100');
INSERT INTO "public"."yxy_permission_resource" VALUES ('42', '9', '116');
INSERT INTO "public"."yxy_permission_resource" VALUES ('43', '9', '110');
INSERT INTO "public"."yxy_permission_resource" VALUES ('44', '9', '100');
INSERT INTO "public"."yxy_permission_resource" VALUES ('45', '10', '123');
INSERT INTO "public"."yxy_permission_resource" VALUES ('46', '10', '121');
INSERT INTO "public"."yxy_permission_resource" VALUES ('47', '10', '120');
INSERT INTO "public"."yxy_permission_resource" VALUES ('48', '10', '100');
INSERT INTO "public"."yxy_permission_resource" VALUES ('49', '10', '132');
INSERT INTO "public"."yxy_permission_resource" VALUES ('50', '10', '130');
INSERT INTO "public"."yxy_permission_resource" VALUES ('51', '10', '142');
INSERT INTO "public"."yxy_permission_resource" VALUES ('52', '10', '140');
INSERT INTO "public"."yxy_permission_resource" VALUES ('53', '11', '141');
INSERT INTO "public"."yxy_permission_resource" VALUES ('54', '11', '140');
INSERT INTO "public"."yxy_permission_resource" VALUES ('55', '11', '120');
INSERT INTO "public"."yxy_permission_resource" VALUES ('56', '11', '100');
INSERT INTO "public"."yxy_permission_resource" VALUES ('57', '11', '131');
INSERT INTO "public"."yxy_permission_resource" VALUES ('58', '11', '130');
INSERT INTO "public"."yxy_permission_resource" VALUES ('59', '11', '122');
INSERT INTO "public"."yxy_permission_resource" VALUES ('60', '11', '121');
INSERT INTO "public"."yxy_permission_resource" VALUES ('61', '12', '126');
INSERT INTO "public"."yxy_permission_resource" VALUES ('62', '12', '121');
INSERT INTO "public"."yxy_permission_resource" VALUES ('63', '12', '120');
INSERT INTO "public"."yxy_permission_resource" VALUES ('64', '12', '100');
INSERT INTO "public"."yxy_permission_resource" VALUES ('65', '12', '135');
INSERT INTO "public"."yxy_permission_resource" VALUES ('66', '12', '130');
INSERT INTO "public"."yxy_permission_resource" VALUES ('67', '12', '145');
INSERT INTO "public"."yxy_permission_resource" VALUES ('68', '12', '140');
INSERT INTO "public"."yxy_permission_resource" VALUES ('69', '13', '143');
INSERT INTO "public"."yxy_permission_resource" VALUES ('70', '13', '140');
INSERT INTO "public"."yxy_permission_resource" VALUES ('71', '13', '120');
INSERT INTO "public"."yxy_permission_resource" VALUES ('72', '13', '100');
INSERT INTO "public"."yxy_permission_resource" VALUES ('73', '13', '133');
INSERT INTO "public"."yxy_permission_resource" VALUES ('74', '13', '130');
INSERT INTO "public"."yxy_permission_resource" VALUES ('75', '13', '124');
INSERT INTO "public"."yxy_permission_resource" VALUES ('76', '13', '121');
INSERT INTO "public"."yxy_permission_resource" VALUES ('77', '14', '125');
INSERT INTO "public"."yxy_permission_resource" VALUES ('78', '14', '121');
INSERT INTO "public"."yxy_permission_resource" VALUES ('79', '14', '120');
INSERT INTO "public"."yxy_permission_resource" VALUES ('80', '14', '100');
INSERT INTO "public"."yxy_permission_resource" VALUES ('81', '14', '134');
INSERT INTO "public"."yxy_permission_resource" VALUES ('82', '14', '130');
INSERT INTO "public"."yxy_permission_resource" VALUES ('83', '14', '144');
INSERT INTO "public"."yxy_permission_resource" VALUES ('84', '14', '140');
INSERT INTO "public"."yxy_permission_resource" VALUES ('85', '15', '154');
INSERT INTO "public"."yxy_permission_resource" VALUES ('86', '15', '50');
INSERT INTO "public"."yxy_permission_resource" VALUES ('87', '15', '100');
INSERT INTO "public"."yxy_permission_resource" VALUES ('88', '16', '152');
INSERT INTO "public"."yxy_permission_resource" VALUES ('89', '16', '50');
INSERT INTO "public"."yxy_permission_resource" VALUES ('90', '16', '100');
INSERT INTO "public"."yxy_permission_resource" VALUES ('91', '17', '151');
INSERT INTO "public"."yxy_permission_resource" VALUES ('92', '17', '50');
INSERT INTO "public"."yxy_permission_resource" VALUES ('93', '17', '100');
INSERT INTO "public"."yxy_permission_resource" VALUES ('94', '18', '153');
INSERT INTO "public"."yxy_permission_resource" VALUES ('95', '18', '50');
INSERT INTO "public"."yxy_permission_resource" VALUES ('96', '18', '100');
INSERT INTO "public"."yxy_permission_resource" VALUES ('97', '19', '157');
INSERT INTO "public"."yxy_permission_resource" VALUES ('98', '19', '50');
INSERT INTO "public"."yxy_permission_resource" VALUES ('99', '19', '100');
INSERT INTO "public"."yxy_permission_resource" VALUES ('100', '19', '158');
INSERT INTO "public"."yxy_permission_resource" VALUES ('101', '20', '202');
INSERT INTO "public"."yxy_permission_resource" VALUES ('102', '20', '201');
INSERT INTO "public"."yxy_permission_resource" VALUES ('103', '20', '200');
INSERT INTO "public"."yxy_permission_resource" VALUES ('104', '20', '211');
INSERT INTO "public"."yxy_permission_resource" VALUES ('105', '20', '210');
INSERT INTO "public"."yxy_permission_resource" VALUES ('106', '20', '221');
INSERT INTO "public"."yxy_permission_resource" VALUES ('107', '20', '220');
INSERT INTO "public"."yxy_permission_resource" VALUES ('108', '20', '231');
INSERT INTO "public"."yxy_permission_resource" VALUES ('109', '20', '230');
INSERT INTO "public"."yxy_permission_resource" VALUES ('110', '20', '241');
INSERT INTO "public"."yxy_permission_resource" VALUES ('111', '20', '240');
INSERT INTO "public"."yxy_permission_resource" VALUES ('112', '20', '251');
INSERT INTO "public"."yxy_permission_resource" VALUES ('113', '20', '250');
INSERT INTO "public"."yxy_permission_resource" VALUES ('114', '21', '205');
INSERT INTO "public"."yxy_permission_resource" VALUES ('115', '21', '201');
INSERT INTO "public"."yxy_permission_resource" VALUES ('116', '21', '200');
INSERT INTO "public"."yxy_permission_resource" VALUES ('117', '21', '214');
INSERT INTO "public"."yxy_permission_resource" VALUES ('118', '21', '210');
INSERT INTO "public"."yxy_permission_resource" VALUES ('119', '21', '234');
INSERT INTO "public"."yxy_permission_resource" VALUES ('120', '21', '230');
INSERT INTO "public"."yxy_permission_resource" VALUES ('121', '21', '244');
INSERT INTO "public"."yxy_permission_resource" VALUES ('122', '21', '240');
INSERT INTO "public"."yxy_permission_resource" VALUES ('123', '21', '254');
INSERT INTO "public"."yxy_permission_resource" VALUES ('124', '21', '250');
INSERT INTO "public"."yxy_permission_resource" VALUES ('125', '22', '253');
INSERT INTO "public"."yxy_permission_resource" VALUES ('126', '22', '250');
INSERT INTO "public"."yxy_permission_resource" VALUES ('127', '22', '200');
INSERT INTO "public"."yxy_permission_resource" VALUES ('128', '22', '243');
INSERT INTO "public"."yxy_permission_resource" VALUES ('129', '22', '240');
INSERT INTO "public"."yxy_permission_resource" VALUES ('130', '22', '233');
INSERT INTO "public"."yxy_permission_resource" VALUES ('131', '22', '230');
INSERT INTO "public"."yxy_permission_resource" VALUES ('132', '22', '223');
INSERT INTO "public"."yxy_permission_resource" VALUES ('133', '22', '220');
INSERT INTO "public"."yxy_permission_resource" VALUES ('134', '22', '213');
INSERT INTO "public"."yxy_permission_resource" VALUES ('135', '22', '210');
INSERT INTO "public"."yxy_permission_resource" VALUES ('136', '23', '206');
INSERT INTO "public"."yxy_permission_resource" VALUES ('137', '23', '201');
INSERT INTO "public"."yxy_permission_resource" VALUES ('138', '23', '200');
INSERT INTO "public"."yxy_permission_resource" VALUES ('139', '23', '215');
INSERT INTO "public"."yxy_permission_resource" VALUES ('140', '23', '210');
INSERT INTO "public"."yxy_permission_resource" VALUES ('141', '23', '235');
INSERT INTO "public"."yxy_permission_resource" VALUES ('142', '23', '230');
INSERT INTO "public"."yxy_permission_resource" VALUES ('143', '23', '245');
INSERT INTO "public"."yxy_permission_resource" VALUES ('144', '23', '240');
INSERT INTO "public"."yxy_permission_resource" VALUES ('145', '23', '255');
INSERT INTO "public"."yxy_permission_resource" VALUES ('146', '23', '250');
INSERT INTO "public"."yxy_permission_resource" VALUES ('147', '24', '252');
INSERT INTO "public"."yxy_permission_resource" VALUES ('148', '24', '250');
INSERT INTO "public"."yxy_permission_resource" VALUES ('149', '24', '200');
INSERT INTO "public"."yxy_permission_resource" VALUES ('150', '24', '242');
INSERT INTO "public"."yxy_permission_resource" VALUES ('151', '24', '240');
INSERT INTO "public"."yxy_permission_resource" VALUES ('152', '24', '232');
INSERT INTO "public"."yxy_permission_resource" VALUES ('153', '24', '230');
INSERT INTO "public"."yxy_permission_resource" VALUES ('154', '24', '222');
INSERT INTO "public"."yxy_permission_resource" VALUES ('155', '24', '220');
INSERT INTO "public"."yxy_permission_resource" VALUES ('156', '24', '212');
INSERT INTO "public"."yxy_permission_resource" VALUES ('157', '24', '210');
INSERT INTO "public"."yxy_permission_resource" VALUES ('158', '24', '203');
INSERT INTO "public"."yxy_permission_resource" VALUES ('159', '24', '201');
INSERT INTO "public"."yxy_permission_resource" VALUES ('160', '25', '330');
INSERT INTO "public"."yxy_permission_resource" VALUES ('161', '25', '331');
INSERT INTO "public"."yxy_permission_resource" VALUES ('162', '25', '300');
INSERT INTO "public"."yxy_permission_resource" VALUES ('163', '26', '320');
INSERT INTO "public"."yxy_permission_resource" VALUES ('164', '26', '321');
INSERT INTO "public"."yxy_permission_resource" VALUES ('165', '26', '322');
INSERT INTO "public"."yxy_permission_resource" VALUES ('166', '26', '323');
INSERT INTO "public"."yxy_permission_resource" VALUES ('167', '26', '324');
INSERT INTO "public"."yxy_permission_resource" VALUES ('168', '26', '300');
INSERT INTO "public"."yxy_permission_resource" VALUES ('169', '28', '301');
INSERT INTO "public"."yxy_permission_resource" VALUES ('170', '28', '302');
INSERT INTO "public"."yxy_permission_resource" VALUES ('171', '28', '307');
INSERT INTO "public"."yxy_permission_resource" VALUES ('172', '28', '300');
INSERT INTO "public"."yxy_permission_resource" VALUES ('173', '29', '303');
INSERT INTO "public"."yxy_permission_resource" VALUES ('174', '29', '304');
INSERT INTO "public"."yxy_permission_resource" VALUES ('175', '29', '305');
INSERT INTO "public"."yxy_permission_resource" VALUES ('176', '29', '306');
INSERT INTO "public"."yxy_permission_resource" VALUES ('177', '29', '300');
INSERT INTO "public"."yxy_permission_resource" VALUES ('178', '27', '325');
INSERT INTO "public"."yxy_permission_resource" VALUES ('179', '27', '326');
INSERT INTO "public"."yxy_permission_resource" VALUES ('180', '27', '300');
INSERT INTO "public"."yxy_permission_resource" VALUES ('181', '31', '608');
INSERT INTO "public"."yxy_permission_resource" VALUES ('182', '31', '607');
INSERT INTO "public"."yxy_permission_resource" VALUES ('183', '31', '600');
INSERT INTO "public"."yxy_permission_resource" VALUES ('184', '32', '609');
INSERT INTO "public"."yxy_permission_resource" VALUES ('185', '32', '607');
INSERT INTO "public"."yxy_permission_resource" VALUES ('186', '32', '600');
INSERT INTO "public"."yxy_permission_resource" VALUES ('187', '33', '603');
INSERT INTO "public"."yxy_permission_resource" VALUES ('188', '33', '602');
INSERT INTO "public"."yxy_permission_resource" VALUES ('189', '33', '600');
INSERT INTO "public"."yxy_permission_resource" VALUES ('190', '34', '605');
INSERT INTO "public"."yxy_permission_resource" VALUES ('191', '34', '602');
INSERT INTO "public"."yxy_permission_resource" VALUES ('192', '34', '600');
INSERT INTO "public"."yxy_permission_resource" VALUES ('193', '35', '601');
INSERT INTO "public"."yxy_permission_resource" VALUES ('194', '35', '600');
INSERT INTO "public"."yxy_permission_resource" VALUES ('195', '36', '708');
INSERT INTO "public"."yxy_permission_resource" VALUES ('196', '36', '701');
INSERT INTO "public"."yxy_permission_resource" VALUES ('197', '36', '700');
INSERT INTO "public"."yxy_permission_resource" VALUES ('198', '36', '709');
INSERT INTO "public"."yxy_permission_resource" VALUES ('199', '36', '703');
INSERT INTO "public"."yxy_permission_resource" VALUES ('200', '36', '710');
INSERT INTO "public"."yxy_permission_resource" VALUES ('201', '36', '705');
INSERT INTO "public"."yxy_permission_resource" VALUES ('202', '37', '702');
INSERT INTO "public"."yxy_permission_resource" VALUES ('203', '37', '701');
INSERT INTO "public"."yxy_permission_resource" VALUES ('204', '37', '700');
INSERT INTO "public"."yxy_permission_resource" VALUES ('205', '37', '704');
INSERT INTO "public"."yxy_permission_resource" VALUES ('206', '37', '703');
INSERT INTO "public"."yxy_permission_resource" VALUES ('207', '37', '706');
INSERT INTO "public"."yxy_permission_resource" VALUES ('208', '37', '705');
INSERT INTO "public"."yxy_permission_resource" VALUES ('209', '38', '801');
INSERT INTO "public"."yxy_permission_resource" VALUES ('210', '38', '800');
INSERT INTO "public"."yxy_permission_resource" VALUES ('211', '39', '802');
INSERT INTO "public"."yxy_permission_resource" VALUES ('212', '39', '803');
INSERT INTO "public"."yxy_permission_resource" VALUES ('213', '39', '800');
INSERT INTO "public"."yxy_permission_resource" VALUES ('214', '40', '1005');
INSERT INTO "public"."yxy_permission_resource" VALUES ('215', '40', '1003');
INSERT INTO "public"."yxy_permission_resource" VALUES ('216', '40', '1002');
INSERT INTO "public"."yxy_permission_resource" VALUES ('217', '40', '1000');
INSERT INTO "public"."yxy_permission_resource" VALUES ('218', '41', '1102');
INSERT INTO "public"."yxy_permission_resource" VALUES ('219', '41', '1101');
INSERT INTO "public"."yxy_permission_resource" VALUES ('220', '41', '1000');
INSERT INTO "public"."yxy_permission_resource" VALUES ('221', '41', '1103');
INSERT INTO "public"."yxy_permission_resource" VALUES ('222', '41', '1106');
INSERT INTO "public"."yxy_permission_resource" VALUES ('223', '41', '1107');
INSERT INTO "public"."yxy_permission_resource" VALUES ('263', '42', '1011');
INSERT INTO "public"."yxy_permission_resource" VALUES ('264', '47', '1020');
INSERT INTO "public"."yxy_permission_resource" VALUES ('224', '43', '1008');
INSERT INTO "public"."yxy_permission_resource" VALUES ('225', '43', '1006');
INSERT INTO "public"."yxy_permission_resource" VALUES ('226', '43', '1002');
INSERT INTO "public"."yxy_permission_resource" VALUES ('227', '43', '1000');
INSERT INTO "public"."yxy_permission_resource" VALUES ('228', '44', '1104');
INSERT INTO "public"."yxy_permission_resource" VALUES ('229', '44', '1100');
INSERT INTO "public"."yxy_permission_resource" VALUES ('230', '44', '1000');
INSERT INTO "public"."yxy_permission_resource" VALUES ('231', '44', '1105');
INSERT INTO "public"."yxy_permission_resource" VALUES ('232', '45', '1004');
INSERT INTO "public"."yxy_permission_resource" VALUES ('233', '45', '1003');
INSERT INTO "public"."yxy_permission_resource" VALUES ('234', '45', '1002');
INSERT INTO "public"."yxy_permission_resource" VALUES ('235', '45', '1000');
INSERT INTO "public"."yxy_permission_resource" VALUES ('236', '46', '1007');
INSERT INTO "public"."yxy_permission_resource" VALUES ('237', '46', '1006');
INSERT INTO "public"."yxy_permission_resource" VALUES ('238', '46', '1002');
INSERT INTO "public"."yxy_permission_resource" VALUES ('239', '46', '1000');
INSERT INTO "public"."yxy_permission_resource" VALUES ('240', '48', '1221');
INSERT INTO "public"."yxy_permission_resource" VALUES ('241', '48', '1222');
INSERT INTO "public"."yxy_permission_resource" VALUES ('242', '48', '1223');
INSERT INTO "public"."yxy_permission_resource" VALUES ('243', '48', '1200');
INSERT INTO "public"."yxy_permission_resource" VALUES ('244', '49', '1201');
INSERT INTO "public"."yxy_permission_resource" VALUES ('245', '49', '1200');
INSERT INTO "public"."yxy_permission_resource" VALUES ('246', '50', '1');
INSERT INTO "public"."yxy_permission_resource" VALUES ('247', '51', '900');
INSERT INTO "public"."yxy_permission_resource" VALUES ('248', '51', '901');
INSERT INTO "public"."yxy_permission_resource" VALUES ('265', '51', '902');
INSERT INTO "public"."yxy_permission_resource" VALUES ('249', '52', '400');
INSERT INTO "public"."yxy_permission_resource" VALUES ('250', '52', '401');
INSERT INTO "public"."yxy_permission_resource" VALUES ('251', '52', '403');
INSERT INTO "public"."yxy_permission_resource" VALUES ('266', '53', '1001');
INSERT INTO "public"."yxy_permission_resource" VALUES ('252', '30', '500');
INSERT INTO "public"."yxy_permission_resource" VALUES ('253', '30', '501');
INSERT INTO "public"."yxy_permission_resource" VALUES ('254', '30', '502');
INSERT INTO "public"."yxy_permission_resource" VALUES ('255', '30', '503');
INSERT INTO "public"."yxy_permission_resource" VALUES ('256', '30', '507');
INSERT INTO "public"."yxy_permission_resource" VALUES ('257', '30', '504');
INSERT INTO "public"."yxy_permission_resource" VALUES ('258', '30', '505');
INSERT INTO "public"."yxy_permission_resource" VALUES ('259', '33', '604');
INSERT INTO "public"."yxy_permission_resource" VALUES ('260', '33', '605');
INSERT INTO "public"."yxy_permission_resource" VALUES ('261', '22', '204');
INSERT INTO "public"."yxy_permission_resource" VALUES ('262', '22', '201');
INSERT INTO "public"."yxy_permission_resource" VALUES ('267', '54', '707');
INSERT INTO "public"."yxy_permission_resource" VALUES ('269', '54', '700');
INSERT INTO "public"."yxy_permission_resource" VALUES ('270', '54', '701');
INSERT INTO "public"."yxy_permission_resource" VALUES ('271', '54', '703');
INSERT INTO "public"."yxy_permission_resource" VALUES ('272', '54', '705');
INSERT INTO "public"."yxy_permission_resource" VALUES ('268', '55', '1012');


INSERT INTO "public"."yxy_sales_information" VALUES ('1', 'ff808081594466e70159446722550000', '91500000747150426A', '北京市石景山区苹果园（地铁存车处）', '010-88732294', '13131', '1231', '1', '2017-12-03 16:40:30', null, '1', '1000', '1001', '0', 'kpyuan002', '收款人', '开票人', '审核人', '0.05', '按5%简易征收', '2017-12-03 16:41:50', '2017-12-03 16:41:56');

-- 优惠券详情
INSERT INTO "public"."yxy_resource" VALUES ('404', '优惠券信息', 'COUPON_INFO', '', '0', '401', '3', '400|401|404', 't', 't', '/coupon_manage/coupon_info/:id', '', '1', null, null, '404');
INSERT INTO "public"."yxy_permission_resource" VALUES ('280', '52', '404');