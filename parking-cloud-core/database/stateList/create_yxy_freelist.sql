-- 免费车
create table yxy_freelist (
    id serial,
    plate_number varchar(20) not null,
    plate_color int4 not null,
    organization_id integer not null,
    parking_id integer not null,
    created_at timestamp without time zone not null default now(),
    created_by integer not null,
    updated_at timestamp without time zone default now(),
    updated_by integer default 0,
    started_at timestamp without time zone not null,
    end_at timestamp without time zone not null,
    is_valid boolean not null default TRUE,
    total_times integer default 0,
    used_times integer default 0,
    join_reason varchar(255) not null,
    out_reason varchar(255) default '',
    primary key (id)
);