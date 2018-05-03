
drop table if exists t_user;

create table t_user(
 id serial not null,
 name varchar(50) not null,
 passwd varchar(100) not null,
 ctime timestamp default now(),
 primary key(id)
);
comment on table t_user is 'User table';
comment on column t_user.id is 'pk';
comment on column t_user.name is 'User name';
comment on column t_user.passwd is 'User password';

insert into t_user(name, passwd) values
('Join', '123'),
('Ken', 'abe'),
('Kuknn', '3456'),
('Blue Kin', 'zxv');
