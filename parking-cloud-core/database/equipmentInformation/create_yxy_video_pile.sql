-- 视频桩设备管理表
create table yxy_video_pile(
    id bigserial primary key,
    system_number varchar(50) not null,
    sn varchar(50) not null,
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
    remarks varchar(200),
    status boolean not null default false,
    unique(fac_org_id,sn)
);