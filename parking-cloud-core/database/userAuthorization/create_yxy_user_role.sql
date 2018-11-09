/** 用户角色关系表 **/
drop table if exists yxy_user_role;
create table yxy_user_role(
  id bigserial primary key,
  user_id bigint not null,
  role_id bigint not null,

  created_at timestamp,
  updated_at timestamp,
  created_by bigint,
  updated_by bigint,

  unique(user_id, role_id)
);