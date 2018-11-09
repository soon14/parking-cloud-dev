-- 车辆时段表
create table yxy_timespan (
    id serial,
    freelist_id integer not null default 0,
    started_at timestamp without time zone not null,
    end_at timestamp without time zone not null,
    cycle int2 not null default 0,
    primary key (id)
);