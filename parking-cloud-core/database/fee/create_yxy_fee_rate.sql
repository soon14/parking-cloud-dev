CREATE TABLE "yxy_fee_rate" (
  "id" serial primary key,
  "fee_rule_id" int4 NOT NULL,
  "type" char(1) NOT NULL,
  "is_multistep" bool DEFAULT false,
  "is_cycled" bool DEFAULT false,
  "cycle" int4,
  "cycle_type" varchar(4) DEFAULT NULL::character varying,
  "cycle_start" int4,
  "cycle_fee_type" char(1),
  "max_fee" numeric(10,2) DEFAULT NULL::numeric,
  "fee_start_time" int4,
  "fee_end_time" int4
);