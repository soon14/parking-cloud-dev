/** 用户组停车关系表 **/
drop table if exists yxy_user_group_parking;
create table yxy_user_group_parking(
  id bigserial primary key,
  group_id bigint not null,
  parking_id bigint not null,

  created_at timestamp,
  updated_at timestamp,
  created_by bigint,
  updated_by bigint,

  unique(group_id, parking_id)

);
