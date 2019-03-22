"""用户数量统计"""
from config import dbc,event_type_name#导入配置文件
from common import query_fetch,execute_commit,get_last_count,insert_datas

def local_create_table(table_name):
    #如果表不存在就要先创建
    SQL='CREATE TABLE if not exists %s'%table_name
    SQL+='(时间 date not null primary key,数量 bigint not null)'
    execute_commit(SQL)

def local_get_last_count(table_name):
    #找出最近一次计算日新增用户量的时间last_count
    SQL="SELECT max(时间) FROM %s"%table_name
    return get_last_count(SQL)
    
def local_insert_datas(table_name,SQL_count):
    last_count=local_get_last_count(table_name)
    #从原始数据表中选出数据统计并插入
    SQL=SQL_count%str(last_count)
    results=query_fetch(SQL)
    SQL_f="INSERT INTO %s(时间, 数量)VALUES('%%s','%%d')"%table_name
    insert_datas(SQL_f,results)

def process(table_name,SQL_count):
    local_create_table(table_name)
    local_insert_datas(table_name,SQL_count)
    

"""统计日新增用户量"""
SQL_count="SELECT 日期,count(*) FROM(\
SELECT date(时间) AS 日期 FROM 事件记录 WHERE \
事件类型='%s' AND \
Date(时间) BETWEEN date('%%s')+1 AND date(now())-1\
) AS T GROUP BY 日期  ORDER BY 日期 ASC"%event_type_name['注册完成']
process('日新增用户数量',SQL_count)

"""统计月新增用户量"""
SQL_count="SELECT 日期,sum(数量) FROM(\
SELECT date(concat(year(时间),'-',month(时间),'-01')) AS 日期,数量 \
FROM 日新增用户数量 WHERE 时间 BETWEEN \
date_add(date('%s'),interval 1 month) AND \
date(concat(year(now()),'-',month(now()),'-01'))-1\
) AS T GROUP BY 日期  ORDER BY 日期 ASC"
process('月新增用户数量',SQL_count)

"""统计年新增用户量"""
SQL_count="SELECT 日期,sum(数量) FROM(\
SELECT date(concat(year(时间),'-01-01')) AS 日期,数量 \
FROM 月新增用户数量 WHERE 时间 BETWEEN \
date_add(date('%s'),interval 1 year) AND \
date(concat(year(now()),'-01-01'))-1\
) AS T GROUP BY 日期  ORDER BY 日期 ASC"
process('年新增用户数量',SQL_count)


def process_total(table_total,table_diff):
    local_create_table(table_total)
    last_count=local_get_last_count(table_total)
    if type(last_count) is str:#如果此前不曾统计过总用户量
        SQL="SELECT min(时间),数量+1 FROM %s"%table_diff
        first_count=query_fetch(SQL)[0]
        #就在开头先插一个数据
        SQL="INSERT INTO "+table_total+"(时间,数量)VALUES('%s','%d')"%first_count
        execute_commit(SQL)

    SQL="SELECT max(时间),数量 FROM %s"%table_total
    last_count,user_total=query_fetch(SQL)[0]
    SQL="SELECT 时间,数量 FROM %s \
    WHERE 时间<date(now()) AND 时间>'%s' order by 时间 ASC"%(table_diff,last_count)
    useradd_everyday=query_fetch(SQL)
    usertotal_everyday=[]
    for date,useradd in useradd_everyday:
        user_total+=useradd
        usertotal_everyday.append((date,user_total))
    SQL_f="INSERT INTO %s(时间,数量)VALUES('%%s','%%d')"%table_total
    insert_datas(SQL_f,usertotal_everyday)

"""每天每月每年都统计用户总量"""
process_total('日用户总量','日新增用户数量')
process_total('月用户总量','月新增用户数量')
process_total('年用户总量','年新增用户数量')
dbc.close()
