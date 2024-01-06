begin;

delete from users;
delete from user_roles;

alter table users add column username text not null unique default 'Unknown';
alter table users drop column role_id;

commit;