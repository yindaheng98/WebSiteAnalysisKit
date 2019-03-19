#下面这个是各种事件类型在数据库“事件记录”表中对应的“事件类型”字段名
#例如如果数据库中“点击”事件的“事件类型”字段名是'普通点击'
#则要设置使event_type_name['点击']='普通点击'
event_type_name={
    '点击':'点击',
    '访问页面':'访问页面',
    '注册完成':'注册完成',
    '查看产品':'查看产品'
    }

#下面这个是数据库连接设置
import mysql.connector
dbc=mysql.connector.connect(
    host="localhost",
    user="MoniterData",
    passwd="MoniterData",
    database="MoniterData"
    )

#月用户活跃度(每月有多少天访问网站)达到多少算月活跃用户?
daily_active_threshold=10
#日用户活跃度(每天有多少小时访问网站)达到多少算日活跃用户?
monthly_active_threshold=2
