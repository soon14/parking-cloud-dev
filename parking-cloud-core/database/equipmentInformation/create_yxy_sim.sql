--电信运营商SIM卡管理表
create table yxy_sim(
    id bigserial primary key,
    sim_number varchar(50) unique not null,
    imsi varchar(50) not null,
    operator varchar(20) not null,
    network_type varchar(20) not null,
    network_flow varchar(100),
    status boolean default false not null,
    put_in_at timestamp,
    put_out_at timestamp,
    startusing_at timestamp,
    fac_org_id bigint not null,
    manage_org_id bigint not null,
    parking_id int8 not null,
    manager varchar(20) not null,
    teminal_device_id bigint not null,
    remarks varchar(200)
);