/** 单位停车场关系表 **/
drop table if exists yxy_org_parking;
create table yxy_org_parking(
  id bigserial primary key,
  org_id bigint not null,
  parking_id bigint not null,
  is_regulatory boolean, -- 是否监管单位
  is_owner boolean, -- 是否产权单位
  is_operator boolean, -- 是否运营单位

  created_at timestamp,
  updated_at timestamp,
  created_by bigint,
  updated_by bigint,

  unique(org_id, parking_id)
);