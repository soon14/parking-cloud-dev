CREATE TABLE "yxy_fee_rate_multistep" (
"id" serial,
"fee_rate_id" int8 NOT NULL,
"price" numeric(10,2) NOT NULL,
"pricing_unit" int4,
"start_time" int4,
"end_time" int4,
"step_duration" int4,
"sort_number" int
,primary key (id)
);