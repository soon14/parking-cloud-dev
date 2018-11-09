-- 停车时长表
create table yxy_parking_time_length_statistics (
  id bigserial primary key,
  parking_id bigint not null,
  parking_name varchar(100) not null,
  lt15 integer not null default 0,
  gt15lt30 integer not null default 0,
  gt30lt45 integer not null default 0,
  gt45lt60 integer not null default 0,
  gt60lt90 integer not null default 0,
  gt90lt120 integer not null default 0,
  gt120lt150 integer not null default 0,
  gt150lt180 integer not null default 0,
  gt180lt240 integer not null default 0,
  gt240 integer not null default 0,
  datetime date not null default now(),
  in_times integer not null default 0,
  out_times integer not null default 0,
  type int2 not null default 0,
  month smallint not null default 0
);
CREATE INDEX yxy_parking_time_length_statistics_datetime_index ON yxy_parking_time_length_statistics (datetime);