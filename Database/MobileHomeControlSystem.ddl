create table rooms (room_id bigserial not null, name varchar(50) not null unique, primary key (room_id));
create table switch_types (switch_type_id bigserial not null, name varchar(50) not null unique, primary key (switch_type_id));
create table users (user_id bigserial not null, username varchar(50) not null unique, password varchar(255) not null, role char(10) not null, primary key (user_id));
create table module_configurations (module_id int8 not null, switch_no int2 not null, room_id int8 not null, switch_type_id int8 not null, name varchar(50) not null unique, primary key (module_id, switch_no));
create table modules (module_id bigserial not null, name varchar(50) not null unique, primary key (module_id));
alter table module_configurations add constraint FKmodule_con628920 foreign key (switch_type_id) references switch_types (switch_type_id);
alter table module_configurations add constraint FKmodule_con585979 foreign key (room_id) references rooms (room_id);
alter table module_configurations add constraint FKmodule_con829456 foreign key (module_id) references modules (module_id);
