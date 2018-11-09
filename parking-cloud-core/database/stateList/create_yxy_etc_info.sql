-- ETC A表
create table yxy_etc_info (
    etc_number char(20) not null,
    etc_net_id varchar(40) not null,
    version integer not null
);

create index yxy_etc_info_etc_number_index on yxy_etc_info (etc_number);