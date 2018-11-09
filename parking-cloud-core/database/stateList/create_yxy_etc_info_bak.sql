-- ETC B表
create table yxy_etc_info_bak (
    etc_number char(20) not null,
    etc_net_id varchar(40) not null,
    version integer not null
);

create index yxy_etc_info_bak_etc_number_index on yxy_etc_info_bak (etc_number);