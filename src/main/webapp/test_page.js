window.onload=function () {
    document.getElementById("hhh").addEventListener("click",function () {
        alert("执行了通过addEventListener加的点击函数");
        alert("ClickEventListenersDOMList第一个元素的outerHTML是\n"
            +Element.ClickEventListenersDOMList[0].outerHTML);
    });
    $('#h').click(function () {
        alert("执行了通过jquery的click加的点击函数")
    });
};