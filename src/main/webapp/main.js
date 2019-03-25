var option_line = {
    // 定义样式和数据
    title: {text: '折线图'},
    backgroundColor: '#FFF',
    tooltip: {trigger: 'axis'},
    calculable: true,
    xAxis: [{
        type: 'category',
        boundaryGap: false,
        axisLine: {lineStyle: {color: '#999999'}},
        inverse: true
    }],
    yAxis: [{
        type: 'value',
        axisLine: {lineStyle: {color: '#CECECE'}}
    }],
    series: [{
        type: 'line',
        smooth: 0.3,
        color: ['#66AEDE'],
    }]
};
var option_pie = {
    title: {text: '饼图'},
    backgroundColor: '#FFF',
    tooltip: {trigger: 'item', formatter: "{a} <br/>{b}: {c} ({d}%)"},
    calculable: true,
    roseType: 'radius',
    series: [{
        type: 'pie',
        radius: ['40%', '70%'],
        avoidLabelOverlap: false,
		labelLine:{  
            normal:{  
               length:0  
                }  
        },  
        label: {
            emphasis: {
                show: true,
                textStyle: {fontSize: '30', fontWeight: 'bold'}
            }
        }
    }]
};
var option_bar = {
    title: {text: '柱状图'},
    grid: {containLabel: true},
    xAxis: {name: 'amount'},
    yAxis: {type: 'category', inverse: true},
    visualMap: {
        orient: 'horizontal',
        left: 'center',
        dimension: 0,
        inRange: {color: ['#47bde1', '#2e51a3']}
    }
};

function getSingleLineOption(option, from_what, title) {
    var option_line = JSON.parse(JSON.stringify(option));
    if (title)
        option_line.title.text = "";
    option_line.xAxis[0].data = totalData[from_what][0];
    option_line.series[0].data = totalData[from_what][1];
    return option_line;
}

function getItemPieOption(option, from_what, when, title) {
    var option_pie = JSON.parse(JSON.stringify(option));
    if (title)
        option_pie.title.text ="";
    var pie_data = [];
    var where = totalData[from_what][0].indexOf(when);
    if (where !== -1) {
        for (var item in totalData[from_what][1]) {
            var meta = {name: item, value: totalData[from_what][1][item][where]};
			meta.labelLine = {show: false};
			meta.label = {show: false};
                meta.labelLine = {show: false};
            if (meta.value === '0') {
                meta.label = {show: false};
                meta.labelLine = {show: false};
            }//隐藏0数据
            pie_data.push(meta);
        }
    }
    option_pie.series[0].data = pie_data;
	//option_pie.series.radius=['50%', '90%'];
    return option_pie;
}

function getItemBarOption(option, from_what, when, how_many, title) {
    var bar_option = JSON.parse(JSON.stringify(option));
    if (title)
        bar_option.title.text ="";
    var where = totalData[from_what][0].indexOf(when);
    if (where !== -1) {
        var item_datas = [];
        for (var item in totalData[from_what][1]){
			item_datas.push([item, totalData[from_what][1][item][where]]);
			var meta = {name: item, value: totalData[from_what][1][item][where]};
			meta.name={show: false};
			meta.value="";
		}
            
        item_datas = item_datas.sort((a, b) => (b[1] - a[1]));
        var items = [];
        var datas = [];
        for (var item in item_datas.slice(0, how_many)) {
            items.push(item_datas[item][0]);
            datas.push(item_datas[item][1]);
        }
        bar_option.yAxis.data = items;
		bar_option.yAxis.show = false;
		bar_option.yAxis.axisLabel = {fontSize:0.00001};
		bar_option.tooltip={};
		bar_option.tooltip.trigger="axis";
		bar_option.tooltip.formatter=items;
        bar_option.series = [{type: 'bar', data: datas}];
        bar_option.visualMap.max = parseInt(datas[0]);
        bar_option.visualMap.min = parseInt(datas[datas.length - 1]);
        bar_option.visualMap.text = [datas[0], datas[datas.length - 1]];
    }
    return bar_option;
}

var option_multibar = {
    title: {text: '柱状图'},
    tooltip: {trigger: 'axis', axisPointer: {type: 'shadow'}},
    legend: {data: []},
    grid: {left: '3%', right: '4%', bottom: '3%', containLabel: true},
    yAxis: [{type: 'value'}],
    xAxis: [{type: 'category', axisTick: {show: false}, inverse: true,}],
    series: []
};

function getUserBarOption(option, where_total, where_new, where_active, title) {
    var userline_option = JSON.parse(JSON.stringify(option));
    if (title)
        userline_option.title.text = "";
    var time_line = [];
    var user_total = [];
    var user_new = [];
    var user_active = [];
    var user_noactive = [];
    for (var when in totalData[where_total][0]) {
        when = totalData[where_total][0][when];
        var where_user_total = totalData[where_total][0].indexOf(when);
        var where_user_new = totalData[where_new][0].indexOf(when);
        var where_user_active = totalData[where_active][0].indexOf(when);
        if (where_user_total !== -1 &&
            where_user_new !== -1 &&
            where_user_active !== -1) {
            time_line.push(when);
            var how_many_total = totalData[where_total][1][where_user_total];
            var how_many_active = totalData[where_active][1][where_user_active];
            user_total.push(how_many_total);
            user_new.push(totalData[where_new][1][where_user_new]);
            user_active.push(how_many_active);
            user_noactive.push(parseInt(how_many_total) - parseInt(how_many_active));
        }
    }
    userline_option.xAxis[0].data = time_line;
    userline_option.legend.data = ['新增用户量', '活跃用户量', '沉默用户量', '总用户量'];
    userline_option.series = [{
        name: '新增用户量',
        type: 'bar',
        label: {
            normal: {
                show: true,
                position: 'inside'
            }
        },
        data: user_new
    }, {
        name: '活跃用户量',
        type: 'bar',
        stack: '用户量',
        label: {
            normal: {
                show: true
            }
        },
        data: user_active
    }, {
        name: '沉默用户量',
        type: 'bar',
        stack: '用户量',
        label: {
            normal: {
                show: true
            }
        },
        data: user_noactive
    }, {
        name: '总用户量',
        type: 'line',
        smooth: 0.3,
        color: ['#66AEDE'],
        label: {
            normal: {
                show: true
            }
        },
        data: user_total
    }];
    return userline_option;
}

function getUserPieOption(option, where_total, where_sub, sub_label, rest_label, when, title) {
    var option_pie = JSON.parse(JSON.stringify(option));
    option_pie.roseType = false;
    if (title)
        option_pie.title.text = "";
    var where;

    where = totalData[where_total][0].indexOf(when);
    if (where === -1) return option_pie;
    var total_user = totalData[where_total][1][where];
	
    where = totalData[where_sub][0].indexOf(when);
    if (where === -1) return option_pie;
	//var meta = {name: item, value: totalData[from_what][1][item][where]};
	//meta.labelLine = {show: false};
	//meta.label = {show: false};
    var sub_user = totalData[where_sub][1][where];
    option_pie.series[0].data = [
        {name: sub_label, value: sub_user},
        {name: rest_label, value: parseInt(total_user) - parseInt(sub_user)}];
    return option_pie;

}