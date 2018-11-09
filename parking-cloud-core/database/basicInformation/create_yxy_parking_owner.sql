--产权关系信息表
create table yxy_parking_owner(
    id bigserial primary key,
    owner_org_id bigint not null,
    parking_id bigint not null,
    approve_status int2 not null default 0,
    prove_images text,
    is_using boolean not null default false,
    created_by bigint,
    created_at timestamp,
    updated_by bigint,
    updated_at timestamp
);