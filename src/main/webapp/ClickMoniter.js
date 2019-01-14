Date.prototype.getFullMonth = function () {
    return this.getMonth() + 1
};
Date.prototype.Format = function (formatStr) {
    var str = formatStr;
    var Week = ['日', '一', '二', '三', '四', '五', '六'];

    str = str.replace(/yyyy|YYYY/, this.getFullYear());
    str = str.replace(/yy|YY/, (this.getYear() % 100) > 9 ? (this.getYear() % 100).toString() : '0' + (this.getYear() % 100));

    str = str.replace(/MM/, this.getFullMonth() > 9 ? this.getFullMonth().toString() : '0' + this.getFullMonth());
    str = str.replace(/M/g, this.getFullMonth());

    str = str.replace(/w|W/g, Week[this.getDay()]);

    str = str.replace(/dd|DD/, this.getDate() > 9 ? this.getDate().toString() : '0' + this.getDate());
    str = str.replace(/d|D/g, this.getDate());

    str = str.replace(/hh|HH/, this.getHours() > 9 ? this.getHours().toString() : '0' + this.getHours());
    str = str.replace(/h|H/g, this.getHours());
    str = str.replace(/mm/, this.getMinutes() > 9 ? this.getMinutes().toString() : '0' + this.getMinutes());
    str = str.replace(/m/g, this.getMinutes());

    str = str.replace(/ss|SS/, this.getSeconds() > 9 ? this.getSeconds().toString() : '0' + this.getSeconds());
    str = str.replace(/s|S/g, this.getSeconds());

    return str;
};

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

function handleClick(element) {//处理泛点击事件
    if(canClick(element))//如果点击发生在一个能被点击的元素上面
        recordClick(element);//就记录下被点击的元素
}

function canClick(element) {//判断这个元素能不能被点击
    var canClicktagList = ['A', 'AUDIO', 'BUTTON', 'INPUT', 'AUDIO'];
    if(canClicktagList.indexOf(element.tagName) !== -1 && element.disabled !== true)
        return true;
    if(Element.ClickEventListenersDOMList.indexOf(element) !== -1)
        return true;
    if(typeof element.onclick !== 'undefined' && element.onclick !== null && element.onclick !== '')
        return true;
}

function recordClick(element) {//记录下被点击的元素
    var date = new Date();

    var data = {
        baseURI: element.baseURI,
        device: navigator.userAgent,
        date: date.Format('YYYY-MM-DD HH:mm:SS'),
        element:element.outerHTML
    };
    if(typeof servlet_url === 'undefined' || servlet_url === null || servlet_url === '' || !servlet_url)
        servlet_url = 'postdata';
    ajax(servlet_url, data);
}

function ajax(url, data) {
    var data_str = '';
    for(var key in data)
        data_str += '&' + key + '=' + encodeURIComponent(data[key]);

    var xmlhttp;
    if(window.XMLHttpRequest) {
        //IE7+,Firefox, Chrome, Opera, Safari 浏览器执行代码
        xmlhttp = new XMLHttpRequest();
    }
    else {
        //IE6, IE5 浏览器执行代码
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function () {
        if(xmlhttp.readyState === 4 && xmlhttp.status === 200) {
            console.log(xmlhttp.responseText);
        }
    };
    xmlhttp.open("POST", "/WebSiteAnalysisKit/" + url, true);
    xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlhttp.send(data_str.substr(1));
}