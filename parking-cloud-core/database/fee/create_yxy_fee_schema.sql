CREATE TABLE "yxy_fee_schema" (
"id" bigserial,
"name" varchar(255) DEFAULT NULL::character varying,
"start_time" int8,
"end_time" int8,
"version" int8,
"fee_desc" varchar(500) DEFAULT NULL::character varying,
"created_at" timestamp(6),
"updated_at" timestamp(6),
"created_by" int8,
"updated_by" int8,
"note" varchar(500) DEFAULT NULL::character varying
,primary key (id)
);