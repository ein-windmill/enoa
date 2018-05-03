


drop table if exists t_binary;

create table t_binary(
id int,
bin blob
);


insert into t_binary values (1, (select convert('123', binary)));
insert into t_binary values (2, (select convert('456', binary)));
insert into t_binary values (3, (select convert('hr3', binary)));
insert into t_binary values (4, (select convert('w9jh', binary)));
insert into t_binary values (null, (select convert('ash3', binary)));


