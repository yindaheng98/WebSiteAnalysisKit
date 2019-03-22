"""活跃用户统计"""
from config import dbc,event_type_name,monthly_active_threshold,daily_active_threshold#导入配置文件
from common import query_fetch,execute_commit,get_last_count,insert_datas

def user_access_table(table_name,time_type,SQL_s):
    SQL='CREATE TABLE if not exists %s('%table_name
    SQL+='用户 varchar(255),时间 %s not null,'%time_type
    SQL+='primary key(用户,时间))'
    execute_commit(SQL)
    SQL='SELECT max(时间) FROM %s'%table_name
    last_count=get_last_count(SQL)
    SQL=SQL_s%last_count
    results=query_fetch(SQL)
    SQL_f="INSERT INTO %s(时间,用户)VALUES('%%s','%%s')"%table_name
    insert_datas(SQL_f,results)

SQL_s="SELECT DISTINCT date(时间),用户 FROM 事件记录 \
WHERE 时间 BETWEEN date('%s')+1 AND date(now()) ORDER BY 时间 ASC"
user_access_table('用户访问日','date',SQL_s)
SQL_s="SELECT DISTINCT \
concat(date(时间),' ',hour(时间),':00:00'),用户 FROM 事件记录 \
WHERE 时间 BETWEEN date_add('%s',interval 1 hour) \
AND concat(date(now()),' ',hour(now()),':00:00') ORDER BY 时间 ASC"
user_access_table('用户访问时','datetime',SQL_s)

def user_active_table(table_name,SQL_s):
    SQL='CREATE TABLE if not exists %s('%table_name
    SQL+='用户 varchar(255),时间 date not null,访问数 tinyint not null,'
    SQL+='primary key(用户,时间))'
    execute_commit(SQL)
    SQL='SELECT max(时间) FROM %s'%table_name
    last_count=get_last_count(SQL)
    SQL=SQL_s%last_count
    results=query_fetch(SQL)
    SQL_f="INSERT INTO %s(用户,时间,访问数)VALUES('%%s','%%s','%%d')"%table_name
    insert_datas(SQL_f,results)

SQL_s="SELECT 用户,月份,count(月份) FROM (\
SELECT 用户,date(concat(year(时间),'-',month(时间),'-01')) \
AS 月份 FROM 用户访问日\
) AS T WHERE 月份 BETWEEN \
date_add(date('%s'),interval 1 month) AND \
date(concat(year(now()),'-',month(now()),'-01'))\
GROUP BY 用户,月份 ORDER BY 月份 ASC"
user_active_table('月用户活跃度',SQL_s)
SQL_s="SELECT 用户,日期,count(日期) FROM (\
SELECT 用户,date(时间) AS 日期 FROM 用户访问时) AS T \
WHERE 日期 BETWEEN date_add('%s',interval 1 day) AND date(now()) \
GROUP BY 用户,日期 ORDER BY 日期 ASC"
user_active_table('日用户活跃度',SQL_s)

def user_active_count_table(table_name,SQL_s):
    SQL='CREATE TABLE if not exists %s('%table_name
    SQL+='时间 date not null,数量 bigint,'
    SQL+='primary key(时间))'
    execute_commit(SQL)
    SQL='SELECT max(时间) FROM %s'%table_name
    last_count=get_last_count(SQL)
    SQL=SQL_s%last_count
    results=query_fetch(SQL)
    SQL_f="INSERT INTO %s(时间,数量)VALUES('%%s','%%d')"%table_name
    insert_datas(SQL_f,results)


SQL_s="SELECT 时间,count(用户) FROM 月用户活跃度 WHERE \
访问数>=%d AND \
时间 BETWEEN date_add(date('%%s'),interval 1 month) AND \
date_add(concat(year(now()),'-',month(now()),'-01 00:00:00'),interval -1 second) \
GROUP BY 时间 ORDER BY 时间 ASC"%(monthly_active_threshold)
user_active_count_table('月活跃用户量',SQL_s)
SQL_s="SELECT 时间,count(用户) FROM 日用户活跃度 WHERE \
访问数>=%d AND 时间 \
BETWEEN date_add(date('%%s'),interval 1 day) AND date(now())-1 \
GROUP BY 时间 ORDER BY 时间 ASC"%(daily_active_threshold)
user_active_count_table('日活跃用户量',SQL_s)

dbc.close()
