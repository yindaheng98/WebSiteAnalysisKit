/*这个文件定义了专门用于监测特殊的点击事件函数*/
function recordEvent(event_name,event_description) {
    WSK_record({event:event_name,event_description:event_description})
}
(function recordVisit() {
    WSK_record({event:'访问页面'})
})();//在页面加载时记下访问信息
