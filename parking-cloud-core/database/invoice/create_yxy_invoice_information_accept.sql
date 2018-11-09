--发票信息接收表
CREATE TABLE "yxy_invoice_information_accept" (
  "invoice_id" int4,
  "invoice_flow_number" varchar(100) COLLATE "default",
  "bill_date" timestamp(6) NULL,
  "invocie_code" varchar(100) COLLATE "default",
  "invoice_number" int4,
  "platform_invoice_url" TEXT COLLATE "default",
  "tax_invoice_url" TEXT COLLATE "default",
  "cloud_platform_invoice_url" TEXT COLLATE "default",
  "created_at" timestamp(6) NULL,
  "updated_at" timestamp(6) NULL,
  "id" BIGSERIAL
);