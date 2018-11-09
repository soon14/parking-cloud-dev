--销方信息
--organization_id 改过
CREATE TABLE "yxy_sales_information" (
  "id" BIGSERIAL,
  "invoice_header" varchar(100) COLLATE "default",
  "taxpayer_identification_number" varchar(100) COLLATE "default",
  "location" varchar(100) COLLATE "default",
  "mobile" varchar(100) COLLATE "default",
  "bank" varchar(100) COLLATE "default",
  "bank_account" varchar(100) COLLATE "default",
  "in_use" int4,
  "used_time" timestamp(6) NULL,
  "un_used_time" timestamp(6) NULL,
  "organization_id" int4,
  "bill_sevice_code" varchar(100) COLLATE "default",
  "search_service_code" varchar(100) COLLATE "default",
  "bill_channel" int2,
  "bill_code" varchar(100) COLLATE "default",
  "payee" varchar(100) COLLATE "default",
  "issuer" varchar(100) COLLATE "default",
  "manager" varchar(100) COLLATE "default",
  "tax_rate" numeric(10,2),
  "vat_tax_special_manage" varchar(100) COLLATE "default",
  "created_at" timestamp(6) NULL,
  "updated_at" timestamp(6) NULL
);