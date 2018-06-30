

#section('list')
select * from t_binary
#end

#section('page0')
select id, bin from t_binary where id<?
order by id desc
#end

#section('page1')
select id, bin from t_binary where id<#p(max)
order by id desc
#end
