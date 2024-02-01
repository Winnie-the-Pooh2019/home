drop table if exists user_roles;
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
    username text not null unique,
    email    text not null unique,
    password text not null
);

create table user_roles(
                           user_id uuid,
                           role_id integer,

                           constraint pk_user_roles primary key (user_id, role_id),
                           constraint fk_user_roles_users foreign key (user_id) references users(id) on delete cascade,
                           constraint fk_user_roles_roles foreign key (role_id) references roles(id) on delete cascade
);

insert into roles(id, name) values (1, 'ROLE_ADMIN');
insert into roles(id, name) values (2, 'ROLE_USER');
