
/** 停车人（客户）账号表 **/
drop table if exists yxy_customer_account;
create table yxy_customer_account (
  id bigserial primary key,
  user_id bigint not null,
  mobile varchar(16) not null,
  email varchar(64),
  password varchar(128),
  is_active boolean default true,
  is_authorized boolean default false,
  last_login_at timestamp,

  created_at timestamp,
  updated_at timestamp,

  unique(user_id)

);
