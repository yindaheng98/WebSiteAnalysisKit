"""站点总访问量统计"""
from config import dbc,event_type_name#导入配置文件
import datetime
cursor=dbc.cursor()

def process(table_name,key_type,SQL_count):
    #如果表不存在就要先创建
    SQL='create table if not exists %s'%table_name
    SQL+='(时间 %s not null primary key,访问量 bigint not null)'%key_type
    print(SQL)
    cursor.execute(SQL)
    dbc.commit()

    #找出最近一次计算日访问量的时间last_count
    SQL="SELECT max(时间) FROM %s"%table_name
    print(SQL)
    cursor.execute(SQL)
    last_count=cursor.fetchall()[0][0]
    if last_count==None:
        last_count='1970-01-01'

    #从原始数据表中选出数据统计并插入
    SQL=SQL_count%(event_type_name['访问页面'],str(last_count))
    print(SQL)
    cursor.execute(SQL)
    results=cursor.fetchall()
    SQL_f="INSERT INTO %s(时间, 访问量)VALUES('%s','%d')"
    for result in results:
        SQL=SQL_f%(table_name,str(result[0]),result[1])
        print(SQL)
        cursor.execute(SQL)
        dbc.commit()


"""统计月访问量"""
SQL_count="SELECT 日期,count(*) FROM(\
SELECT date(concat(year(时间),'-',month(时间),'-01')) AS 日期 FROM 事件记录 WHERE \
事件类型='%s' AND \
Date(时间) BETWEEN date_add('%s',interval 1 month) AND \
date_add(concat(year(now()),'-',month(now()),'-01 00:00:00'),interval -1 second)\
) AS T GROUP BY 日期 ORDER BY 日期 ASC"
process('月访问量','date',SQL_count)

"""统计日访问量"""
SQL_count="SELECT 日期,count(*) FROM(\
SELECT date(时间) AS 日期 FROM 事件记录 WHERE \
事件类型='%s' AND \
Date(时间) BETWEEN date('%s')+1 AND date(now())-1\
) AS T GROUP BY 日期 ORDER BY 日期 ASC"
process('日访问量','date',SQL_count)


"""统计小时访问量"""
SQL_count="SELECT 小时,count(*) FROM(\
SELECT concat(date(时间),' ',hour(时间),':','00') AS 小时 FROM 事件记录 WHERE \
事件类型='%s' AND \
时间 BETWEEN date_add('%s',interval 1 hour) AND \
concat(date(now()),' ',hour(now()),':','00')\
) AS T GROUP BY 小时 ORDER BY 小时 ASC"
process('小时访问量','datetime',SQL_count)

dbc.close()
