/** 平台用户信息表 **/
drop table if exists yxy_user;
create table yxy_user (
  id bigserial primary key,
  name varchar(32),
  gender varchar(16),
  birth date,
  id_type varchar(64),
  id_number varchar(64),

  created_at timestamp,
  updated_at timestamp,
  created_by bigint,
  updated_by bigint

);