-- 优惠券领取关系
create table yxy_coupon_history (
  id bigserial primary key,
  promo_code_id bigint not null,
  received_by bigint not null default 0,
  received_car_id bigint not null default 0,
  status int2 not null default 0
);