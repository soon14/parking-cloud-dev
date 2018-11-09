--停车场车位使用状态表
create table yxy_parking_cell_use(
    id bigserial primary key,
    parking_id bigint not null,
    using_number int4 not null
);