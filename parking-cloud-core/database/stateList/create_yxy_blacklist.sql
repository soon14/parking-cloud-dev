-- 黑名单表
create table yxy_blacklist (
    id bigserial,
    plate_number varchar(20) not null,
    plate_color int4 not null,
    organization_id integer not null,
    parking_id integer not null default 0,
    created_at timestamp without time zone not null default now(),
    created_by bigint not null,
    updated_at timestamp without time zone default now(),
    updated_by bigint default 0,
    started_at timestamp without time zone not null,
    end_at timestamp without time zone not null,
    is_valid boolean not null default TRUE,
    join_reason varchar(255) not null,
    out_reason varchar(255) default '',
    primary key (id)
);