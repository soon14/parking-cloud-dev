/* 单位角色关系表 */
drop table if exists yxy_org_role;
create table yxy_org_role(
  id bigserial primary key,
  org_id bigint not null,
  role_id bigint not null,

  created_at timestamp,
  updated_at timestamp,
  created_by bigint,
  updated_by bigint,

  unique(org_id, role_id)
);