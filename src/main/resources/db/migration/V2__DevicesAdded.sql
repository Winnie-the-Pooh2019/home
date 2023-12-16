drop table if exists users;

create table users (
                       id uuid primary key,
                       email text not null,
                       password text not null
);

drop table if exists types;

create table types (
    id serial primary key,
    name text unique not null
);

drop table if exists devices;

create table devices (
    id uuid primary key,
    name text not null,
    type_id integer not null,
    user_id uuid,

    constraint fk_devices_types foreign key (type_id) references types(id),
    constraint fk_devices_users foreign key (user_id) references users(id)
);