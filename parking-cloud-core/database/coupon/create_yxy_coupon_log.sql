-- 优惠券流水号
create table yxy_coupon_log (
  id bigserial primary key,
  promo_code_id bigint not null,
  created_by bigint not null,
  created_at timestamp without time zone default now(),
  status int2
);