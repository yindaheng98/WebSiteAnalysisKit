# WebSiteAnalysisKit
A series of JavaScript, servlets, and a database used for monitoring and analysing web accessing.

## 页面访问情况统计功能使用方法

1. 在服务器上用MoniterDatabase.sql建立数据库

2. 在服务器上运行Analyser.Servlet

3. 在要进行统计的页面引入JavaScript文件，将Analyser.Servlet的url赋值给WSK_servlet_url

4. (Optional)在要进行特殊事件统计的元素上添加点击函数(用addEventListener、onclick或者jQuery等均可)recordEvent(event_type,event_description)。其中event_type是事件的名称(类型)，event_description是事件上要附加的一些具体信息。
event_description可以留空。

Javascript文件引入后会自动监听并向后端发送两种类型的事件：“点击”和“访问页面”。

“点击”事件将监听页面上所有的可点击元素的点击事件(包括可点击的标签和用addEventListener、onclick或者jQuery添加过点击函数的页面元素，具体可见WSK_ClickMoniter.js)。
“点击”事件向后端发送的数据中event_type="点击"，event_description是被点击标签的outerHTML。

“访问页面”事件在页面载入时触发，向后端发送的数据中event_type="访问页面"，没有event_description(具体可见WSK_EventMoniter.js)。
