/** 权限资源关系表 **/
drop table if exists yxy_permission_resource;
create table yxy_permission_resource(
  id bigserial primary key,
  permission_id bigint not null, -- 权限id
  resource_id bigint not null, -- 资源id
  unique(permission_id, resource_id)
);