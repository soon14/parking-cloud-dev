-- 订单停车信息表
create table yxy_order_parking_info (
    id bigserial,
    order_id bigint not null,
    parking_id bigint not null,
    parking_name varchar(100) not null,
    parking_address varchar(255) not null default '',
    in_parking_port_id bigint default 0,
    in_parking_port_code varchar(20) default '',
    out_parking_port_id bigint default 0,
    out_parking_port_code varchar(20) default '',
    in_parking_lane_id bigint default 0,
    in_parking_lane_code varchar(20) default '',
    out_parking_lane_id bigint default 0,
    out_parking_lane_code varchar(20) default '',
    parking_cell_id bigint default 0,
    parking_cell_code varchar(20) not null default '',
    primary key (id)
);