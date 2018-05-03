



drop table if exists t_binary;

create table t_binary(
id int,
bin bytea
);


insert into t_binary values (1, E'\\123'::bytea);
insert into t_binary values (2, E'\\456'::bytea);
insert into t_binary values (3, E'\\235'::bytea);
insert into t_binary values (4, E'\\236'::bytea);



