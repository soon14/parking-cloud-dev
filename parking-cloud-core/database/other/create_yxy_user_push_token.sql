--pushToken
CREATE TABLE yxy_user_push_token (
    "id" BIGSERIAL primary key,
    "push_token" text,
    "user_id" bigint,
    "created_at" timestamp(6),
    "updated_at" timestamp(6),
    "in_use" int2 DEFAULT 1,
    "used_time" timestamp(6),
    "unused_time" timestamp(6)
);