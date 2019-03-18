from user_agents import parse
import json
from config import dbc
cursor=dbc.cursor()
from urllib import request
import json
from user_agents import parse
import time
def DiliWeiZhi(ip):
    #处理地理位置
    html = request.Request(r'https://ip.tianqiapi.com/?ip='+ip)
    with request.urlopen(html) as f:     # 打开url请求（如同打开本地文件一样）
        hjson=json.loads(f.read().decode('utf-8'))
    province=hjson['province']
    city=hjson['city']
    isp=hjson['isp']
    return province,city,isp
def UserAgent(str1):
    ua_string = str1
    user_agent = parse(ua_string) #解析成user_agent
    bw = user_agent.browser.family #浏览器
    s = user_agent.os.family #操作系统
    juge_pc = user_agent.is_pc #判断是不是桌面系统
    phone = user_agent.device.family#手机型号
    return bw,s,juge_pc,phone

SQL='SELECT `IP地址`, `设备型号` FROM `事件记录` WHERE 1  '
cursor.execute(SQL)
message=cursor.fetchall()
for m in message:
    time.sleep(0.3)
    province,city,isp=DiliWeiZhi(m[0])
    browser,OS,is_pc,phone = UserAgent(m[1])
    if not is_pc:
        ji_xing = phone
    else:
        ji_xing = "PC"
    SQL='INSERT INTO `终端分析` \
    (`编号`, `省`, `市`, `操作系统`, `浏览器`, `浏览机型`, `运营商`) \
    VALUES (NULL, "'+province+'", "'+city+'", "'+OS+'", "'+browser+'",\
    "'+ji_xing+'", "'+isp+'")'
    print(SQL)
    cursor.execute(SQL)
    dbc.commit()
