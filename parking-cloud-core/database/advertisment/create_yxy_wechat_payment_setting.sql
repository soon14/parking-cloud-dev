create table yxy_wechat_payment_setting (
  id bigserial primary key,
  parking_id bigint not null,
  app_id varchar(50) not null,
  mch_id varchar(20) not null,
  api_key varchar(100) not null,
  created_at timestamp without time zone not null,
  created_by bigint not null,
  updated_at timestamp without time zone not null,
  updated_by bigint not null
);

create unique index yxy_wechat_payment_setting_unique on yxy_wechat_payment_setting (parking_id, app_id, mch_id, api_key);