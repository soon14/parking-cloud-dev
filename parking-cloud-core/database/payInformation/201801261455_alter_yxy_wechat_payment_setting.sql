-- 支持退款
alter table yxy_wechat_payment_setting add column certification_file varchar(255) not null default '';
alter table yxy_wechat_payment_setting add column password varchar(50) not null default '';