/** 用户组角色关系表 **/
drop table if exists yxy_user_group_role;
create table yxy_user_group_role(
  id bigserial primary key,
  group_id bigint not null,
  role_id bigint not null,

  created_at timestamp,
  updated_at timestamp,
  created_by bigint,
  updated_by bigint,

  unique(group_id, role_id)
);
