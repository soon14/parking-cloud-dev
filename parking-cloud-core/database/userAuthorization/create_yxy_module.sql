/** 系统模块(子系统）表 **/
drop table if exists yxy_module;
create table yxy_module(
  id bigserial primary key,
  name varchar(64) not null,  -- 模块（子系统）名称
  code varchar(64) not null,
  sort int not null, -- 排序字段

  created_at timestamp,
  updated_at timestamp,
  unique(name),
  unique(code)
);