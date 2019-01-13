document.addEventListener("click",function (event) {//捕获所有点击
    if (event) {//不是ie
        handleClick(event.target);
    } else if (window.event) {//ie
        handleClick(window.event.srcElement);
    }
});

Element.ClickEventListenersDOMList=[];//用addEventListener绑定了click事件的元素列表
//重写addEventListener方法
Element.prototype._addEventListener=Element.prototype.addEventListener;
Element.prototype.addEventListener = function (event, func, useCapture) {
    this._addEventListener(event, func, useCapture);
    if(event==="click")
        Element.ClickEventListenersDOMList.push(this);//记下绑定了click事件的元素
};

function handleClick(element) {//处理泛点击事件
    if(canClick(element))//如果点击发生在一个能被点击的元素上面
        recordClick(element);//就记录下被点击的元素
}

function canClick(element) {//判断这个元素能不能被点击
    var canClicktagList=['A','AUDIO','BUTTON','INPUT','AUDIO'];
    if(canClicktagList.indexOf(element.tagName)!==-1&&element.disabled!==true)
        return true;
    if(Element.ClickEventListenersDOMList.indexOf(element)!==-1)
        return true;
    if(element.onclick!==null&&element.onclick!==undefined&&element.onclick!=='')
        return true;
}

function recordClick(element) {//记录下被点击的元素
    var data={baseURI:element.baseURI};
    alert('这个元素可以点：\n'+JSON.stringify(data)+"\n"+JSON.stringify(element));
}