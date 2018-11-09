/** 资源信息表 **/
drop table if exists yxy_resource;
create table yxy_resource(
  id bigserial primary key,
  name varchar(64) not null, -- 资源名称
  code varchar(64) not null, -- 资源code
  res_type varchar(64) not null, -- 资源分类 0：菜单， 1：按钮
  module_id bigint not null, -- 模块编号
  parent_id bigint not null default -1, -- 上级资源
  level int not null, -- 资源层级
  depth varchar(64), -- 资源深度，[上一级深度+|+]当前id
  is_hidden boolean not null,
  is_leaf boolean not null, -- 是否叶子节点
  url text, -- 访问路径
  icon varchar(32), -- 图标字符
  status int not null default 1, -- 状态 1：有效 0：无效
  created_at timestamp,
  updated_at timestamp,
  sort int,
  unique(module_id, name),
  unique(module_id, code)
);