--订单发票关联关系表
CREATE TABLE "yxy_order_invoice" (
        "order_id" int4,
        "invoice_id" int4,
        "invoice_amout" numeric(10,2),
        "whether_invoice" int2,
        "whether_together" int2,
        "id" bigserial primary key
);