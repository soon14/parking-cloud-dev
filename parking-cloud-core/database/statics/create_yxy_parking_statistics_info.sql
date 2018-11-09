-- 分析决策表
create table yxy_parking_statistics_info (
  id bigserial primary key,
  cell_used_time_length bigint not null default 0,
  cell_utilization numeric(6, 4) not null default 0,
  cell_reverse_rate numeric(6, 4) not null default 0,
  cell_usage numeric(6, 4) not null default 0,
  cell_count integer not null default 0,
  parking_id bigint not null,
  parking_name varchar(100) not null,
  gross_income decimal(8, 2) not null default 0,
  wechat_income decimal(8, 2) not null default 0,
  alipay_income decimal(8, 2) not null default 0,
  union_pay_income decimal(8, 2) not null default 0,
  discount_income decimal(8, 2) not null default 0,
  other_income decimal(8, 2) not null default 0,
  datetime date not null default now(),
  hour smallint not null default 0,
  month smallint not null default 0
);
-- 索引
CREATE INDEX yxy_parking_statistics_info_datetime_index ON yxy_parking_statistics_info (datetime);