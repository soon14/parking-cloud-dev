-- 停车场信息表
create table yxy_parking(
    id bigserial primary key,
    code varchar(50) not null,
    full_name varchar(100) unique not null,
    short_name varchar(100) not null,
    road_type int2 not null,
    open_type int2 not null,
    fee_type int2 not null,
    layers int2 not null,
    underground_layers int2 not null,
    overground_layers int2 not null,
    business_number text,
    charge_licence text ,
    approve_status int2 not null default 0,
    owner_org_id bigint,
    operator_org_id bigint,
    regulatory_id bigint,
    principal varchar(20) not null,
    phone_number varchar(20) not null,
    email varchar(30) not null,
    address varchar(100) not null,
    is_using boolean not null default false,
    gps point not null,
    parking_cells int4 not null,
    business_hours_desc text not null,
    all_day boolean not null,
    start_time varchar(30),
    end_time varchar(30),
    rates text not null,
    street_area_id bigint not null,
    prove_images text,
    manage_prove text,
    parking_cell_map text not null,
    created_by bigint,
    created_at timestamp,
    updated_by bigint,
    updated_at timestamp
);