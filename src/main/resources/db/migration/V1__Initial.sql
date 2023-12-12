drop table if exists tasks;

create table users (
    id uuid primary key,
    email text not null,
    password text not null
);