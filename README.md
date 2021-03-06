# WebSiteAnalysisKit
A series of JavaScript, servlets, and a database with some python script for data analysis, used for monitoring and analysing web accessing.

## 页面访问情况统计功能使用方法

1. 在服务器上用MoniterDatabase.sql建立数据库

2. 在服务器上项目目录内运行./gradlew.bat appRun

    或是运行./gradlew.bat war生成war包后将./build/libs中生成的war包部署到服务器端jetty容器内

3. 在要进行统计的页面引入JavaScript文件，将Analyser.Servlet的url赋值给WSK_servlet_url

4. (Optional)在要进行特殊事件统计的元素上添加点击函数recordEvent(event_type,event_description)，用addEventListener、onclick或者jQuery等均可。其中event_type是事件的名称(类型)，event_description是事件上要附加的一些具体信息。
event_description可以留空

5. 等收集到一定量数据时候就可以在服务器端运行/python文件夹下的.py脚本，
该脚本将对基础数据库进行数据分析并进一步构建数据库(如何使用请见/python/run.txt)。

Javascript文件引入后会自动监听并向后端发送两种类型的事件：“点击”和“访问页面”。

“点击”事件将监听页面上所有的可点击元素的点击事件(包括可点击的标签和用addEventListener、onclick或者jQuery添加过点击函数的页面元素，具体可见WSK_ClickMoniter.js)。
“点击”事件向后端发送的数据中event_type="点击"，event_description是被点击标签的outerHTML。

“访问页面”事件在页面载入时触发，向后端发送的数据中event_type="访问页面"，没有event_description(具体可见WSK_EventMoniter.js)。

在.py数据分析脚本中有一个有关于“访问产品”的分析，对应的埋点函数写法为recordEvent(“产品点击”,产品名称或编号)。

./src/main/webapp/test_pages中是一个埋点页面示例，具体埋点方法可以参考此页面。