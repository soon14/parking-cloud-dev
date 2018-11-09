-- 车辆表
create table yxy_customer_cars (
    id bigserial,
    plate_number varchar(15) not null,
    plate_color int2 not null default 0,
    car_type int2 not null default 0,
    created_by bigint not null default 0,
    is_green_energy boolean not null default false,
    primary key(id)
);
