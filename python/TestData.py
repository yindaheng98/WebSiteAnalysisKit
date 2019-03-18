from config import dbc,event_type_name#导入配置文件
from TestLists import user_agents,pages,users,products
import time
from datetime import datetime
from random import random
cursor=dbc.cursor()
SQL=''
SQL+='insert into 事件记录'
SQL+='(时间,IP地址,设备型号,`SESSION编号`,所在页面,用户,事件类型, 事件描述)'
SQL+='values'

SQL_f="("
SQL_f+="'%(时间)s',"
SQL_f+="'%(IP地址)s',"
SQL_f+="'%(设备型号)s',"
SQL_f+="'%(SESSION编号)s',"
SQL_f+="'%(所在页面)s',"
SQL_f+="%(用户)s,"
SQL_f+="'%(事件类型)s',"
SQL_f+="%(事件描述)s"
SQL_f+="),"
SQL_d={}

def cat_data(SQL_d):
    global SQL
    global SQL_f
    SQL+=SQL_f%SQL_d
    

def getRandomTime():
    timestamp=datetime.now()
    timestamp=time.mktime(timestamp.timetuple())
    timestamp-=random()*30*24*60*60
    timestamp=datetime.fromtimestamp(timestamp)
    return timestamp.strftime('%Y-%m-%d %H:%M:%S')

def getRandomIP():
    ip=(202,113+int(random()*8),int(random()*255),int(random()*255))
    return "%d.%d.%d.%d"%ip

def getRandomList(lists):
    return lists[int(random()*len(lists))]

import hashlib
md5=hashlib.md5()
def getRandomSession():
    md5.update(str(random()).encode('utf-8'))
    return md5.hexdigest()

def user_cat(reg_time,user,event_name,event_desc):
    time=getRandomTime()
    while(datetime.strptime(time,'%Y-%m-%d %H:%M:%S')<=reg_time):
        time=getRandomTime()
    SQL_d={}
    SQL_d['时间']=time
    SQL_d['IP地址']=getRandomIP()
    SQL_d['设备型号']=getRandomList(user_agents)
    SQL_d['SESSION编号']=getRandomSession()
    SQL_d['所在页面']=getRandomList(pages)
    SQL_d['用户']=user
    SQL_d['事件类型']=event_name
    SQL_d['事件描述']=event_desc
    cat_data(SQL_d)

def no_user_cat(event_name,event_desc):
    SQL_d={}
    SQL_d['时间']=getRandomTime()
    SQL_d['IP地址']=getRandomIP()
    SQL_d['设备型号']=getRandomList(user_agents)
    SQL_d['SESSION编号']=getRandomSession()
    SQL_d['所在页面']=getRandomList(pages)
    SQL_d['用户']='null'
    SQL_d['事件类型']=event_name
    SQL_d['事件描述']=event_desc
    cat_data(SQL_d)

#100次无用户点击
for i in range(0,100):
    no_user_cat(event_type_name['点击'],'null')
    
#100次无用户访问
for i in range(0,100):
    no_user_cat(event_type_name['访问页面'],'null')

#100次无用户产品点击
for i in range(0,100):
    no_user_cat(event_type_name['查看产品'],getRandomList(products))

#用户注册
for user in users:
    SQL_d['时间']=getRandomTime()
    SQL_d['IP地址']=getRandomIP()
    SQL_d['设备型号']=getRandomList(user_agents)
    SQL_d['SESSION编号']=getRandomSession()
    SQL_d['所在页面']='yindaheng98.top/register.html'
    SQL_d['用户']=user
    SQL_d['事件类型']=event_type_name['注册完成']
    SQL_d['事件描述']='null'
    cat_data(SQL_d)
    reg_time=datetime.strptime(SQL_d['时间'],'%Y-%m-%d %H:%M:%S')
    #每个用户10次点击
    for i in range(0,50):
        user_cat(reg_time,user,event_type_name['点击'],'null')
    #每个用户10次访问
    for i in range(0,50):
        user_cat(reg_time,user,event_type_name['访问页面'],'null')
    #每个用户10次产品点击
    for i in range(0,50):
        user_cat(reg_time,user,event_type_name['查看产品'],getRandomList(products))

print(SQL)
cursor.execute(SQL[0:-1])
dbc.commit()
