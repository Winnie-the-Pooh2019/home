drop table if exists devices;
drop table if exists actions;
drop table if exists device_actions;

create table devices (
    id uuid primary key ,
    type text not null
);

create table actions (
    id uuid primary key ,
    name text not null
);

create table device_actions (
    device_id uuid,
    action_id uuid,

    constraint pk_device_actions primary key (device_id, action_id),
    constraint fk_device_actions_devices foreign key (device_id) references devices(id) on delete cascade ,
    constraint fk_device_actions_actions foreign key (action_id) references actions(id) on delete cascade
);