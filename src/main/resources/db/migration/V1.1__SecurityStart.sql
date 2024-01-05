drop table if exists users;

drop table if exists roles;


create table roles
(
    id   integer primary key,
    name text unique not null
);

create table users
(
    id       uuid primary key,
    email    text not null,
    password text not null,
    role_id  integer,

    constraint fk_users_roles foreign key (role_id) references roles (id)
);