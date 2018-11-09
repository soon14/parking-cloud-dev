/** 单位行政区划关系表 **/
drop table if exists yxy_org_area;
create table yxy_org_area(
  id bigserial primary key,
  org_id bigint not null,
  area_id bigint not null,

  created_at timestamp,
  updated_at timestamp,
  created_by bigint,
  updated_by bigint,

  unique(org_id, area_id)
);