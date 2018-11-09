/** 用户组用户关系表 **/
drop table if exists yxy_user_user_group;
create table yxy_user_user_group(
  id bigserial primary key,
  user_id bigint not null,
  group_id bigint not null,

  created_at timestamp,
  updated_at timestamp,
  created_by bigint,
  updated_by bigint,

  unique(user_id, group_id)
);