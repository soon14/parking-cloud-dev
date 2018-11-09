/** 停车人（客户）信息表 **/
drop table if exists yxy_customer;
create table yxy_customer(
  id bigserial primary key,
  name varchar(32),
  gender varchar(16),
  birth date,
  avatar varchar(255),
  nickname varchar(32),
  driving_license_image varchar(255),

  created_at timestamp,
  updated_at timestamp
);