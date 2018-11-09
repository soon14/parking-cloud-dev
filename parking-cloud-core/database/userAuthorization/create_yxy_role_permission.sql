/** 角色权限关系表 **/
drop table if exists yxy_role_permission;
create table yxy_role_permission(
  id bigserial primary key,
  role_id bigint not null,
  permission_id bigint not null,

  created_at timestamp,
  updated_at timestamp,
  created_by bigint,
  updated_by bigint,

  unique(role_id, permission_id)
);
