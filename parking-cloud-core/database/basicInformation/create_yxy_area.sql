--区域信息表
create table yxy_area(
   id bigserial primary key,
   name varchar(50) unique not null,
   code varchar(20)  not null,
   executive_level varchar(20) not null,
   parent_id bigint not null,
   sort_number varchar(20),
   level int2 not null
);