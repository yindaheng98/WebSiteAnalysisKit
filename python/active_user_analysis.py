"""活跃用户统计"""
from config import dbc,event_type_name,active_threshold#导入配置文件
from common import query_fetch,execute_commit,get_last_count,insert_datas

SQL='CREATE TABLE if not exists 用户访问日('
SQL+='用户 varchar(255),日期 date not null,'
SQL+='primary key(用户,日期))'
execute_commit(SQL)
SQL='SELECT max(日期) FROM 用户访问日'
last_count=get_last_count(SQL)
SQL="SELECT DISTINCT date(时间),用户 FROM 事件记录 \
WHERE 时间 BETWEEN date('%s')+1 AND date(now())"%last_count
results=query_fetch(SQL)
SQL_f="INSERT INTO 用户访问日(日期,用户)VALUES('%s','%s')"
insert_datas(SQL_f,results)

SQL='CREATE TABLE if not exists 月用户活跃度('
SQL+='用户 varchar(255),月份 date not null,访问天数 tinyint not null,'
SQL+='primary key(用户,月份))'
execute_commit(SQL)
SQL='SELECT max(月份) FROM 月用户活跃度'
last_count=get_last_count(SQL)
SQL="SELECT 用户,月份,count(月份) FROM (\
SELECT 用户,date(concat(year(日期),'-',month(日期),'-01')) \
AS 月份 FROM 用户访问日\
) AS T WHERE 月份 BETWEEN \
date_add(date('%s'),interval 1 month) AND \
date(concat(year(now()),'-',month(now()),'-01'))\
GROUP BY 用户,月份"%last_count
results=query_fetch(SQL)
SQL_f="INSERT INTO 月用户活跃度(用户,月份,访问天数)VALUES('%s','%s','%d')"
insert_datas(SQL_f,results)

SQL='CREATE TABLE if not exists 月活跃用户数('
SQL+='月份 date not null,活跃用户数 bigint,'
SQL+='primary key(月份))'
execute_commit(SQL)
SQL='SELECT max(月份) FROM 月活跃用户数'
last_count=get_last_count(SQL)
SQL="SELECT 月份,count(用户) FROM 月用户活跃度 WHERE \
访问天数>=%d AND \
月份 BETWEEN date_add(date('%s'),interval 1 month) AND \
date(concat(year(now()),'-',month(now()),'-01')) \
GROUP BY 月份"%(active_threshold,last_count)
results=query_fetch(SQL)
SQL_f="INSERT INTO 月活跃用户数(月份,活跃用户数)VALUES('%s','%d')"
insert_datas(SQL_f,results)

dbc.close()
