-- 优惠码表
create table yxy_promo_code (
  id bigserial primary key,
  coupon_id bigint not null,
  describe varchar(255) not null,
  coupon_info decimal(6, 2) not null default 0,
  promo_code varchar(20) not null,
  status int2 not null,
  coupon_type int2 not null default 0,
  receive_start timestamp without time zone not null,
  receive_end timestamp without time zone not null,
  started_at timestamp without time zone not null,
  end_at timestamp without time zone not null,
  constraint yxy_promo_code_unique unique (promo_code)
);