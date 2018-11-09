--单位信息表
drop table if exists yxy_organzation;
create table yxy_organzation(
    id bigserial primary key,
    org_nature int2 not null,
    full_name varchar(100) unique not null,
    short_name varchar(100)  not null,
    register_address varchar(250)  not null,
    legal_represent_ative varchar(100)  not null,
    taxpayer_number varchar(100)  not null,
    phone_number varchar(20)  not null,
    email varchar(30)  not null,
    org_number varchar(50),
    registration_number varchar(200),
    org_number_certificate varchar(255),
    area_id bigint not null,
    is_valid boolean not null default false,
    approve_status int not null default 0,
    is_property_org boolean not null default false,
    is_manage_org boolean not null  default false,
    is_facility_org boolean not null default false,
    is_regulatory boolean not null default false,
    created_by int,
    created_at timestamp,
    updated_by int,
    updated_at timestamp
);