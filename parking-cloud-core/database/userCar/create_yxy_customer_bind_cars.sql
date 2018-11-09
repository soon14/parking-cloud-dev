-- 车辆绑定关系表
create table yxy_customer_bind_cars (
    id bigserial,
    user_id bigint not null,
    car_id bigint not null,
    bind_at timestamp without time zone,
    unbind_at timestamp without time zone default null,
    updated_at timestamp without time zone default now(),
    is_valid boolean not null default TRUE,
    is_certification boolean not null default FALSE,
    auth_image_url text not null default '',
    auth_time timestamp without time zone default null,
    status int2 not null default 0,
    remark varchar(255) not null default '',
    primary key (id)
);