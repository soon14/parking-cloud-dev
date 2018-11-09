-- ETC版本表
create table yxy_etc_version (
    id bigserial,
    received_at timestamp without time zone not null default now(),
    updated_at timestamp without time zone not null,
    is_valid boolean not null default FALSE,
    started_at timestamp without time zone,
    version integer default 0,
    table_name varchar(20) not null,
    primary key (id)
);