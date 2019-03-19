/*这个文件用于监测页面上全部可点击项目(包括EventListener、onclick和可点击标签)的“点击”事件*/

document.addEventListener("click", function (event) {//捕获所有点击
    if(event) {//不是ie
        handleClick(event.target);
    }
    else if(window.event) {//ie
        handleClick(window.event.srcElement);
    }
});

Element.ClickEventListenersDOMList = [];//用addEventListener绑定了click事件的元素列表
//重写addEventListener方法
Element.prototype._addEventListener = Element.prototype.addEventListener;
Element.prototype.addEventListener = function (event, func, useCapture) {
    this._addEventListener(event, func, useCapture);
    if(event === "click")
        Element.ClickEventListenersDOMList.push(this);//记下绑定了click事件的元素
};

//重写removeEventListener方法
Element.prototype._removeEventListener=Element.prototype.removeEventListener;
Element.prototype.removeEventListener=function (event, func, useCapture) {
    this._removeEventListener(event, func, useCapture);
    if(event==='click'){
        var index=Element.ClickEventListenersDOMList.indexOf(this);
        if(index>-1)
            Element.ClickEventListenersDOMList.splice(index,1)
    }
};

function handleClick(element) {//处理泛点击事件
    if(canClick(element))//如果点击发生在一个能被点击的元素上面
        WSK_record({event_type:'点击',event_description:element.outerHTML});//就记录下被点击的元素
}

function canClick(element) {//判断这个元素能不能被点击
    var canClicktagList = ['A', 'AUDIO', 'BUTTON', 'INPUT', 'AUDIO'];
    if(canClicktagList.indexOf(element.tagName) !== -1 && element.disabled !== true)
        return true;//是可点击的标签
    if(Element.ClickEventListenersDOMList.indexOf(element) !== -1)
        return true;//有Click型的EventListener
    if(typeof element.onclick !== 'undefined' && element.onclick !== null && element.onclick !== '')
        return true;//加了onclick属性
}

