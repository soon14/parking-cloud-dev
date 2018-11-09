/** 平台用户账号表 **/
drop table if exists yxy_user_account;
create table yxy_user_account(
  id bigserial primary key,
  user_id bigint not null,
  org_id bigint not null,
  employee_number varchar(32),
  mobile varchar(16) not null,
  email varchar(64),
  password varchar(128),
  is_active boolean default true,

  last_login_at timestamp,
  register_ip varchar(64),
  last_login_ip varchar(64),

  created_at timestamp,
  updated_at timestamp,
  created_by bigint,
  updated_by bigint,

  unique(user_id),
  unique(org_id, employee_number)
);