/*这个文件里面存的是WSK页面监控组件中通用的一些函数以及WSK载入前的一些操作*/

/*以下是获取日期时间的组件*/
Date.prototype.getFullMonth = function () {
    return this.getMonth() + 1
};
/**
 * @return {string}
 */
Date.prototype.Format = function (formatStr) {
    var str = formatStr;
    var Week = ['日', '一', '二', '三', '四', '五', '六'];

    str = str.replace(/yyyy|YYYY/, this.getFullYear());
    str = str.replace(/yy|YY/, (this.getYear() % 100) > 9 ? (this.getYear() % 100).toString() : '0' + (this.getYear() % 100));

    str = str.replace(/MM/, this.getFullMonth() > 9 ? this.getFullMonth().toString() : '0' + this.getFullMonth());
    str = str.replace(/M/g, this.getFullMonth());

    str = str.replace(/[wW]/g, Week[this.getDay()]);

    str = str.replace(/dd|DD/, this.getDate() > 9 ? this.getDate().toString() : '0' + this.getDate());
    str = str.replace(/[dD]/g, this.getDate());

    str = str.replace(/hh|HH/, this.getHours() > 9 ? this.getHours().toString() : '0' + this.getHours());
    str = str.replace(/[hH]/g, this.getHours());
    str = str.replace(/mm/, this.getMinutes() > 9 ? this.getMinutes().toString() : '0' + this.getMinutes());
    str = str.replace(/m/g, this.getMinutes());

    str = str.replace(/ss|SS/, this.getSeconds() > 9 ? this.getSeconds().toString() : '0' + this.getSeconds());
    str = str.replace(/[sS]/g, this.getSeconds());

    return str;
};

/*以下是记录事件用的组件*/
function WSK_record(data) {//输入data是一个JSON包含要记录的数据
    var data_str = '';

    data_str += '?' + 'baseURI=' + encodeURIComponent(document.baseURI);
    data_str += '&' + 'device=' + encodeURIComponent(navigator.userAgent);
    data_str += '&' + 'date=' + encodeURIComponent(new Date().Format('YYYY-MM-DD HH:mm:SS'));
    if (typeof data !== 'undefined' && data &&
        Object.getOwnPropertyNames(data).length !== 0) {//如果有要传的数据
        for (var key in data) {
            data_str += '&' + key + '=' + encodeURIComponent(data[key]);
        }
    }
    if (typeof WSK_servlet_url === 'undefined' ||
        WSK_servlet_url === null ||
        WSK_servlet_url === '' ||
        !WSK_servlet_url) {
        WSK_servlet_url = 'postdata';
    }

    var xmlhttp = window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
            console.log(xmlhttp.responseText);
            console.log(data_str)
        }
    };
    xmlhttp.open("GET", "/WebSiteAnalysisKit/" + WSK_servlet_url+data_str, true);
    xmlhttp.send(null);
}