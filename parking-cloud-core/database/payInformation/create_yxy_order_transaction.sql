drop table if exists yxy_order_transaction;
create table yxy_order_transaction (
  id varchar(32) not null,
  order_id integer not null default 0,
  order_number varchar(50) not null,
  user_id integer not null default 0,
  parking_id integer not null default 0,
  mch_id varchar(20) not null default '',
  plate_number varchar(20) not null,
  plate_color int2 not null,
  pay_way int2 not null default 0,
  pay_method varchar(20) not null default '',
  amount decimal(8, 2) not null default 0,
  created_at timestamp without time zone not null default now(),
  created_way int2 not null default 0,
  transaction_id varchar(50) not null default '',
  finished_at timestamp without time zone,
  transaction_way int2 not null default 0,
  transaction_detail text not null default '',
  updated_at timestamp without time zone,
  status int2 not null default 0,
  remark varchar(255) not null default '',
  primary key (id)
);