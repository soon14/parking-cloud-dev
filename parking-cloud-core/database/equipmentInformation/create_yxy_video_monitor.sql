--视频监控管理设备表
create table yxy_video_monitor(
    id bigserial primary key,
    system_number varchar(50) not null,
    sn varchar(50) not null,
    status boolean default false not null,
    put_in_at timestamp,
    put_out_at timestamp,
    startusing_at timestamp,
    gps point not null,
    fac_org_id bigint not null,
    ip varchar(50)  not null,
    manage_org_id bigint not null,
    manager varchar(20) not null,
    parking_id bigint not null,
    parking_cell_code varchar(20) not null,
    video_address text,
    remarks varchar(200),

    unique(fac_org_id,sn)
);