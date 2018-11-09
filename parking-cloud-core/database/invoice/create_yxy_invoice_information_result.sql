--发票信息结果表
CREATE TABLE "yxy_invoice_information_result" (
  "id" BIGSERIAL,
  "invoice_id" int4,
  "invoice_flow_number" varchar(100) COLLATE "default",
  "bill_date" timestamp(6) NULL,
  "invocie_code" varchar(100) COLLATE "default",
  "invoice_number" int4,
  "platform_invoice_url" text COLLATE "default",
  "tax_invoice_url" text COLLATE "default",
  "cloud_platform_invoice_url" text COLLATE "default",
  "created_at" timestamp(6) NULL,
  "updated_at" timestamp(6) NULL,
  "cloud_platform_invoice_image" varchar(200) COLLATE "default"
);