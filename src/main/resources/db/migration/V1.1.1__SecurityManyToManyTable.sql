drop table if exists user_roles;

create table user_roles
(
    user_id uuid,
    role_id integer,

    constraint pk_user_roles primary key (user_id, role_id),
    constraint fk_user_roles_users foreign key (user_id) references users (id),
    constraint fk_user_roles_roles foreign key (role_id) references roles (id)
);