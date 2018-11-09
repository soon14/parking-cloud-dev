/** 角色信息表 **/
drop table if exists yxy_role;
create table yxy_role(
  id bigserial primary key,
  name varchar(64) not null, -- 角色名称
  code varchar(64) not null, -- 角色code
  status int not null default 1, -- 状态 1：有效 0：无效
  remark varchar(255), -- 备注

  created_at timestamp,
  updated_at timestamp,
  created_by bigint,
  updated_by bigint,

  unique(name),
  unique(code)
);