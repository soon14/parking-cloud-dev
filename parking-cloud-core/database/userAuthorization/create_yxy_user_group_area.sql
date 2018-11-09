/** 用户组行政区划关系表 **/
drop table if exists yxy_user_group_area;
create table yxy_user_group_area(
  id bigserial primary key,
  group_id bigint not null,
  area_id bigint not null,

  created_at timestamp,
  updated_at timestamp,
  created_by bigint,
  updated_by bigint,

  unique(group_id, area_id)
);