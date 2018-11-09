create table yxy_fee_festival(
  id bigserial primary key,
  name varchar(64),
  start_time timestamp,
  end_time timestamp,
  created_at timestamp,
  updated_at timestamp,
  created_by int4,
  updated_by int4,
  note varchar(500)
);