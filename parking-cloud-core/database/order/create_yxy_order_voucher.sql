-- 订单停车入场/出场照片
create table yxy_order_voucher (
    id bigserial,
    order_id bigint not null,
    type int2 not null default 0,
    voucher varchar(100) not null default '',
    primary key (id)
);