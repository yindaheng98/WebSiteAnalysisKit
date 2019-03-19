/*这个文件定义了专门用于监测特殊的点击事件函数*/
function WSK_recordEvent(event_type,event_description) {
    if (typeof WSK_servlet_url === 'undefined' ||
        WSK_servlet_url === null ||
        WSK_servlet_url === '' ||
        !WSK_servlet_url) {
        WSK_record({event_type:event_type});
    }
    else WSK_record({event_type:event_type,event_description:event_description})
}
(function recordVisit() {
    WSK_record({event_type:'访问页面',event_description:'访问页面'})
})();//在页面加载时记下访问信息
