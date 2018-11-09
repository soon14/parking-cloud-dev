/** 权限信息表 **/
drop table if exists yxy_permission;
create table yxy_permission(
  id bigserial primary key,
  name varchar(64) not null, -- 权限名称
  code varchar(64) not null, -- 权限code
  module_id bigint not null, -- 模块编号
  status int not null default 1, -- 状态 1：有效 0：无效

  created_at timestamp,
  updated_at timestamp,
  unique(module_id, name),
  unique(module_id, code)
);