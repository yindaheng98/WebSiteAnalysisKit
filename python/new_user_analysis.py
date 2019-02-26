"""用户数量统计"""
from config import dbc,event_type_name#导入配置文件
cursor=dbc.cursor()

def process(table_name,SQL_count):
    #如果表不存在就要先创建
    SQL='CREATE TABLE if not exists %s'%table_name
    SQL+='(时间 date not null primary key,数量 bigint not null)'
    print(SQL)
    cursor.execute(SQL)
    dbc.commit()
    
    #找出最近一次计算日新增用户量的时间last_count
    SQL="SELECT max(时间) FROM %s"%table_name
    print(SQL)
    cursor.execute(SQL)
    last_count=cursor.fetchall()[0][0]
    if last_count==None:
        last_count='1970-01-01'

    #从原始数据表中选出数据统计并插入
    SQL=SQL_count%{'上一条记录的时间':str(last_count)}
    print(SQL)
    cursor.execute(SQL)
    results=cursor.fetchall()
    SQL_f="INSERT INTO %s(时间, 数量)VALUES('%s','%d')"
    for result in results:
        print(result)
        SQL=SQL_f%(table_name,str(result[0]),result[1])
        print(SQL)
        cursor.execute(SQL)
        dbc.commit()

"""统计日新增用户量"""
SQL_count="SELECT 日期,count(*) FROM(\
SELECT date(时间) AS 日期 FROM 事件记录 WHERE \
事件类型='%s' AND \
Date(时间) BETWEEN date('%%(上一条记录的时间)s')+1 AND date(now())-1\
) AS T GROUP BY 日期"%event_type_name['注册完成']
process('日新增用户数量',SQL_count)

"""统计月新增用户量"""
SQL_count="SELECT 日期,sum(数量) FROM(\
SELECT date(concat(year(时间),'-',month(时间),'-01')) AS 日期,数量 \
FROM 日新增用户数量 WHERE 时间 BETWEEN \
date(concat(year('%(上一条记录的时间)s'),'-',month('%(上一条记录的时间)s')+1,'-01')) \
AND date(concat(year(now()),'-',month(now()),'-01'))-1\
) AS T GROUP BY 日期"
process('月新增用户数量',SQL_count)

"""统计年新增用户量"""
SQL_count="SELECT 日期,sum(数量) FROM(\
SELECT date(concat(year(时间),'-01-01')) AS 日期,数量 \
FROM 月新增用户数量 WHERE 时间 BETWEEN \
date(concat(year('%(上一条记录的时间)s')+1,'-01-01')) AND \
date(concat(year(now()),'-01-01'))-1\
) AS T GROUP BY 日期"
process('年新增用户数量',SQL_count)

dbc.close()
