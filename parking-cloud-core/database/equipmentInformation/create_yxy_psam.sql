--PSAM设备管理表
create table yxy_psam(
    id bigserial primary key,
    psam_card_number varchar(50) unique not null,
    psam_type varchar(20) not null,
    put_in_at timestamp,
    put_out_at timestamp,
    startusing_at timestamp,
    manage_org_id bigint not null,
    parking_id int8 not null,
    manager varchar(20) not null,
    terminal_device_id bigint not null,
    remarks varchar(200)
);