drop table if exists yxy_advertisement;
CREATE TABLE yxy_advertisement(
  id bigserial PRIMARY KEY ,
  title VARCHAR (128) unique not null,
  cover text not NULL ,
  link text default '',
  position INT not null,
  remarks VARCHAR (512),
  status bool not null,
  sort int not null,
  content text,
  created_at timestamp,
  update_at timestamp,
  created_by bigint,
  update_by bigint
);