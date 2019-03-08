import json
from config import dbc
cursor=dbc.cursor()
from urllib import request
import json

SQL='CREATE TABLE IF NOT EXISTS `终端分析`( `编号` INT(50) NOT NULL PRIMARY KEY AUTO_INCREMENT, `省` VARCHAR(50) NOT NULL, `市` VARCHAR(50) NOT NULL, `操作系统` VARCHAR(50) NOT NULL, `浏览器` VARCHAR(50)NOT NULL, `浏览机型` VARCHAR(50) , `运营商` VARCHAR(50) NOT NULL )'
cursor.execute(SQL)
dbc.commit()
SQL='SELECT `IP地址`, `设备型号` FROM `访问记录` WHERE 1 '
cursor.execute(SQL)
message=cursor.fetchall()
for m in message:
    ip=m[0]
    device=m[1]

    #处理地理位置
    html = request.Request(r'https://ip.tianqiapi.com/?ip='+ip)
    with request.urlopen(html) as f:     # 打开url请求（如同打开本地文件一样）
        hjson=json.loads(f.read().decode('utf-8'))
    province=hjson['province']
    city=hjson['city']
    isp=hjson['isp']
    #处理设备
    #操作系统
    if device.find("Windows NT 10.0")>0:
        caozuo_xitong="Win10"
    elif device.find("Android")>0:
        caozuo_xitong="Android"
    #浏览器    
    if device.find("Firefox")>0:
        liulan_qi="Firefox"
    else:
        liulan_qi="Edge"
    #浏览机型
    if device.find("MI")>0:
        ji_xing='小米'
    else:
        ji_xing="PC"
    print(caozuo_xitong+liulan_qi+ji_xing)
    SQL='INSERT INTO `终端分析` \
    (`编号`, `省`, `市`, `操作系统`, `浏览器`, `浏览机型`, `运营商`) \
    VALUES (NULL, "'+province+'", "'+city+'", "'+caozuo_xitong+'", "'+liulan_qi+'","'+ji_xing+'", "'+isp+'")'
    print(SQL)
dbc.commit()

cursor.execute(SQL)
dbc.commit()
