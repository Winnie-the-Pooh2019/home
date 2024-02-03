create table verification_tokens (
    token uuid primary key ,
    user_id uuid,

    constraint fk_verification_tokens_users foreign key (user_id) references users(id)
);