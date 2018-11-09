-- ETC历史表
create table yxy_etc_history (
    id bigserial,
    etc_number char(20) not null,
    etc_net_id varchar(40) not null,
    etc_version jsonb not null,
    primary key (id)
);
-- ETC 历史表唯一索引方便upsert
create unique index yxy_etc_history_uniq on yxy_etc_history (etc_number, etc_net_id);