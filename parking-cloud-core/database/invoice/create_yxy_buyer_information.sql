--购方信息表
CREATE TABLE "yxy_buyer_information" (
  "id" BIGSERIAL,
  "invoice_header" varchar(100) COLLATE "default",
  "taxpayer_identification_number" varchar(100) COLLATE "default",
  "location" varchar(100) COLLATE "default",
  "mobile" varchar(100) COLLATE "default",
  "bank" varchar(100) COLLATE "default",
  "bank_account" varchar(100) COLLATE "default",
  "used_time" timestamp(6) NULL,
  "un_used_time" timestamp(6) NULL,
  "customer_id" int4,
  "in_use" int4 DEFAULT 0,
  "created_at" timestamp(6) NULL,
  "updated_at" timestamp(6) NULL,
  "email" varchar(20) COLLATE "default"
);