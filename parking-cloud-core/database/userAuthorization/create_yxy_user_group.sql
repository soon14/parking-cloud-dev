/** 用户组信息表 **/
drop table if exists yxy_user_group;
create table yxy_user_group(
  id bigserial primary key,
  org_id bigint not null, -- 单位id
  name varchar(64) not null, -- 组名
  remark varchar(255), -- 备注
  status int not null default 1, -- 状态 1：有效 0：无效

  created_at timestamp,
  updated_at timestamp,
  created_by bigint,
  updated_by bigint,

  unique(org_id, name)
);