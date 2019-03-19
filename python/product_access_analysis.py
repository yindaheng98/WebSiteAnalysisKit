"""站点总访问量统计"""
from config import dbc,event_type_name#导入配置文件
cursor=dbc.cursor()

def process(table_name,key_type,SQL_count):
    #如果表不存在就要先创建
    SQL='create table if not exists %s'%table_name
    SQL+='(产品 varchar(255) not null,'
    SQL+='时间 %s not null,'%key_type
    SQL+='访问量 bigint not null,'
    SQL+='primary key(产品,时间))'
    print(SQL)
    cursor.execute(SQL)
    dbc.commit()

    SQL="SELECT DISTINCT 事件描述 FROM 事件记录 WHERE 事件类型='%s'"%event_type_name['查看产品']
    print(SQL)
    cursor.execute(SQL)
    pages=cursor.fetchall()
    for page in pages:
        page=page[0]
        #找出最近一次计算日访问量的时间last_count
        SQL="SELECT max(时间) FROM %s WHERE 产品='%s'"%(table_name,page)
        print(SQL)
        cursor.execute(SQL)
        last_count=cursor.fetchall()[0][0]
        if last_count==None:
            last_count='1970-01-01'

        #从原始数据表中选出数据统计并插入
        SQL=SQL_count%(event_type_name['查看产品'],page,str(last_count))
        print(SQL)
        cursor.execute(SQL)
        results=cursor.fetchall()
        SQL_f="INSERT INTO %s(产品,时间,访问量)VALUES('%s','%s','%d')"
        for result in results:
            print(result)
            SQL=SQL_f%(table_name,page,str(result[0]),result[1])
            print(SQL)
            cursor.execute(SQL)
            dbc.commit()

"""统计日访问量"""
SQL_count="SELECT 日期,count(*) FROM(\
SELECT date(时间) AS 日期 FROM 事件记录 WHERE \
事件类型='%s' AND 事件描述='%s' AND \
Date(时间) BETWEEN date('%s')+1 AND date(now())-1\
) AS T GROUP BY 日期 ORDER BY 日期 ASC"
process('日产品访问量','date',SQL_count)

"""统计月访问量"""
SQL_count="SELECT 月份,count(*) FROM(\
SELECT concat(year(时间),'-',month(时间),'-','01') AS 月份 FROM 事件记录 WHERE \
事件类型='%s' AND 事件描述='%s' AND \
时间 BETWEEN date_add('%s',interval 1 month) AND \
concat(year(now()),'-',month(now()),'-','01')\
) AS T GROUP BY 月份 ORDER BY 月份 ASC"
process('月产品访问量','date',SQL_count)

dbc.close()
