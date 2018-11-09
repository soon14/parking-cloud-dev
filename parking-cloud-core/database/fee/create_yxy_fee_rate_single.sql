CREATE TABLE "yxy_fee_rate_single" (
  "id" serial primary key,
  "fee_rate_id" int4 NOT NULL,
  "price" numeric(10,2) NOT NULL,
  "pricing_unit" int4
);