-- 订单信息表
drop table if exists yxy_order_info;
create table yxy_order_info (
    id bigserial,
    order_number varchar(50) not null,
    user_id bigint default 0,
    customer_car_id bigint default 0,
    plate_number varchar(20) not null default '',
    plate_color int2 not null default 0,
    car_type int2 not null default 1,
    parking_id bigint not null,
    parking_name varchar(100) not null,
    organization_id bigint not null default 0,
    total_amount decimal(8, 2) not null default 0,
    receivable_amount decimal(8, 2) not null default 0,
    free_amount decimal(8, 2) not null default 0,
    prepaid_amount decimal(8, 2) not null default 0,
    paid_amount decimal(8, 2) not null default 0,
    invoice_amount decimal(8, 2) not null default 0,
    created_at timestamp without time zone not null default now(),
    enter_at timestamp without time zone,
    leave_at timestamp without time zone,
    duration integer not null default 0,
    requested_bill boolean not null default FALSE,
    status int2 not null default 0,
    car_status int2 not null default 0,
    from_type int2 not null default 0,
    is_valid boolean not null default TRUE,
    remark varchar(255) not null default '',
    last_payment_time timestamp without time zone,
    updated_by bigint not null default 0,
    in_device_sn varchar(50) not null default '',
    out_device_sn varchar(50) not null default '',
    primary key (id)
);

create unique index yxy_order_number_uniq on yxy_order_info(order_number);
create index yxy_order_plate_index on yxy_order_info(plate_number, plate_color);