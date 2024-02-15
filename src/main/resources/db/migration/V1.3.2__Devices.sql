alter table devices add column user_id uuid;
alter table devices add constraint fk_devices_users foreign key (user_id) references users(id) on delete cascade