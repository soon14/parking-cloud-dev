--停车场出入口信息表
create table yxy_parking_port(
    id bigserial primary key,
    code varchar(20) not null,
    parking_id bigint not null,
    port_type int2 not null,
    gps point not null,
    lanes int4 not null,
    is_using boolean not null default false,
    created_by bigint,
    created_at timestamp,
    updated_by bigint,
    updated_at timestamp,
    unique(parking_id,code)
);