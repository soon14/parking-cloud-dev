-- 停车收费表
create table yxy_parking_amount_statistics (
  id bigserial primary key,
  parking_id bigint not null,
  parking_name varchar(100) not null,
  receivable_amount decimal(10, 2) not null default 0,
  paid_amount decimal(10, 2) not null default 0,
  real_paid_aount decimal(10, 2) not null default 0,
  pre_paid_amount decimal(10, 2) not null default 0,
  supplementary_amout decimal(10, 2) not null default 0,
  pre_paid_refunds_amount decimal(10, 2) not null default 0,
  unpaid_amount decimal(10, 2) not null default 0,
  alipay_amount decimal(10, 2) not null default 0,
  wechat_amount decimal(10, 2) not null default 0,
  discount_amount decimal(10, 2) not null default 0,
  cash_amount decimal(10, 2) not null default 0,
  datetime date not null default now(),
  type int2 not null default 0,
  month smallint not null default 0
);

CREATE INDEX yxy_parking_amount_statistics_datetime_index ON yxy_parking_amount_statistics (datetime);