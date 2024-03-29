-- 优惠券表
create table yxy_coupon_info (
  id bigserial primary key,
  coupon_unique varchar(20) not null,
  describe varchar(255) not null,
  organization_id bigint not null default 0,
  parking_id bigint not null,
  started_at timestamp without time zone not null default now(),
  end_at timestamp without time zone not null,
  time_interval_start varchar(30) not null,
  time_interval_end varchar(30) not null,
  can_superposed boolean not null default false,
  max_of_superposed smallint not null default 1,
  can_use_with_whitelist boolean not null default false,
  min_use_money decimal(6, 2) not null default 0,
  max_superposed_money decimal(6, 2) not null default 0,
  coupon_info decimal(6, 2) not null default 0,
  coupon_type int2 not null default 0,
  created_at timestamp without time zone not null default now(),
  created_by bigint not null,
  updated_at timestamp without time zone not null default now(),
  updated_by bigint not null,
  is_valid boolean not null default true,
  times integer not null default 1,
  constraint yxy_coupon_info_unique unique (coupon_unique)
);