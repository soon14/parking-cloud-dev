--经营关系信息表
create table yxy_parking_operator(
    id bigserial primary key,
    operator_org_id bigint not null,
    parking_id bigint not null,
    manage_prove text,
    is_using boolean default false,
    approve_status int2 not null default 0,
    created_by bigint,
    created_at timestamp,
    updated_by bigint,
    updated_at timestamp
);