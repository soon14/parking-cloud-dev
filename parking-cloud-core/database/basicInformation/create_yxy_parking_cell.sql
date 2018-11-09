--停车位信息表
create table yxy_parking_cell(
    id bigserial primary key,
    code varchar(20) not null,
    roadbed_code varchar(20) not null,
    parking_id bigint not null,
    gps point not null,
    parking_cell_long varchar(10) not null,
    parking_cell_wide varchar(10) not null,
    parking_cell_direction varchar(10) not null,
    is_using boolean not null default false,
    created_by bigint,
    created_at timestamp,
    updated_by bigint,
    updated_at timestamp,
    unique(parking_id,roadbed_code,code)
);