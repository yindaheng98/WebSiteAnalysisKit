#下面这个是各种事件类型在数据库“事件记录”表中对应的“事件类型”字段名
#例如如果数据库中“点击”事件的“事件类型”字段名是'普通点击'
#则要设置使event_type_name['点击']='普通点击'
event_type_name={
    '点击':'点击',
    '访问页面':'访问页面'
    }

#下面这个是数据库连接设置
import mysql.connector
dbc=mysql.connector.connect(
    host="localhost",
    user="MoniterData",
    passwd="MoniterData",
    database="MoniterData"
    )
