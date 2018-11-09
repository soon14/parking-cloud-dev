/** 微信用户信息表 **/
drop table if exists yxy_wechat_user;
create table yxy_wechat_user(
  id bigserial primary key,
  customer_id bigint not null,
  openid varchar(128) not null,
  nickname varchar(128),
  sex int,
  province varchar(64),
  city varchar(64),
  country varchar(64),
  headimgurl text,
  unionid varchar(128),
  access_token text,
  created_at timestamp,
  updated_at timestamp
);