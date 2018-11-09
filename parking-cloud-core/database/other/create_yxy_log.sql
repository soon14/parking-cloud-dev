-- 日志
drop table if exists yxy_log;
create table yxy_log(
  time timestamp,
  remote_ip varchar(64),
  user_id varchar(32),
  uri varchar(1024),
  ret_code varchar(16),
  query_string text,
  params text,
  ret text,
  executed_time varchar(32),
  level varchar(16),
  message text
);