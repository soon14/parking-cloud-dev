-- app 推送信息表
drop table if exists yxy_push_message;
create table yxy_push_message (
  id bigserial primary key,
  type varchar(32) not null,
  title varchar(255) not null,
  content text,
  status int not null,
  customer_id bigint not null,

  umeng_push_token varchar(128),
  umeng_sent_at timestamp,
  created_at timestamp,
  updated_at timestamp
);