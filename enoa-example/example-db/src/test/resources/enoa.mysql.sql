
drop table if exists t_user;

create table t_user(
 id int not null auto_increment,
 name varchar(50) not null comment 'User name',
 passwd varchar(100) not null comment 'User password',
 ctime timestamp default now(),
 primary key(id)
) engine=InnoDB default charset utf8 comment 'User table';

insert into t_user(name, passwd) values
('Join', '123'),
('Ken', 'abe'),
('Kuknn', '3456'),
('Blue Kin', 'zxv');
