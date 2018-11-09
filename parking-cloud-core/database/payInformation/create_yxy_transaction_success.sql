-- 记账信息表
create table yxy_transaction_success (
  id bigserial,
  user_id integer not null default 0,
  order_id integer not null default 0,
  plate_number varchar(20) not null,
  plate_color int2 not null,
  pay_way int2 not null default 0,
  pay_method varchar(20) not null default '',
  amount decimal(8, 2) not null default 0,
  created_at timestamp without time zone not null default now(),
  updated_at timestamp without time zone,
  primary key (id)
);