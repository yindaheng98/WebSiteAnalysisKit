var chart1 = echarts.init(document.getElementById("chart1"));	

	// 指定图表的配置项和数据
	var option = {

	    // 图例
	    legend: [{
	        selectedMode:true,             // 图例选择的模式，控制是否可以通过点击图例改变系列的显示状态。默认开启图例选择，可以设成 false 关闭。
	        top: '10%',
	        left: 'center',
	        textStyle: {                      // 图例的公用文本样式。
	            fontSize: 10,
	            color: '#000'
       		},
	        data: ["Android", "Win10"]
	    }],
	    
	    // 提示框
	    tooltip: {
	        show: true,                 // 是否显示提示框
	        formatter: '{b} </br> 量{c} </br> 占比{d}%'      // 提示框显示内容,此处{b}表示各数据项名称，此项配置为默认显示项，{c}表示数据项的值，默认不显示，({d}%)表示数据项项占比，默认不显示。
	    },
	    
	    // graphic 是原生图形元素组件。可以支持的图形元素包括：image, text, circle, sector, ring, polygon, polyline, rect, line, bezierCurve, arc, group,
	    graphic: {
	        type: 'text',               // [ default: image ]用 setOption 首次设定图形元素时必须指定。image, text, circle, sector, ring, polygon, polyline, rect, line, bezierCurve, arc, group,
	        top: 'center',              // 描述怎么根据父元素进行定位。top 和 bottom 只有一个可以生效。如果指定 top 或 bottom，则 shape 里的 y、cy 等定位属性不再生效。『父元素』是指：如果是顶层元素，父元素是 echarts 图表容器。如果是 group 的子元素，父元素就是 group 元素。
	        left: 'center',             // 同上
	        style: {
	            text: '操作系统',       // 文本块文字。可以使用 \n 来换行。[ default: '' ]
	            fill: '#000',           // 填充色。
	            fontSize: 12,           // 字体大小
	            fontWeight: 'bold'		// 文字字体的粗细，可选'normal'，'bold'，'bolder'，'lighter'
	        }
	    },
	    
	    // 系列列表
	    series: [{
	        name: '圆环图系列名称',         // 系列名称
	        type: 'pie',                    // 系列类型 
	        center:['50%','50%'],           // 饼图的中心（圆心）坐标，数组的第一项是横坐标，第二项是纵坐标。[ default: ['50%', '50%'] ]
	        radius: ['30%', '45%'],         // 饼图的半径，数组的第一项是内半径，第二项是外半径。[ default: [0, '75%'] ]
	        hoverAnimation: true,           // 是否开启 hover 在扇区上的放大动画效果。[ default: true ]
	        label: {                        // 饼图图形上的文本标签，可用于说明图形的一些数据信息，比如值，名称等.
	            normal: {
	                show: true,             // 是否显示标签[ default: false ]
	                position: 'outside',    // 标签的位置。'outside'饼图扇区外侧，通过视觉引导线连到相应的扇区。'inside','inner' 同 'inside',饼图扇区内部。'center'在饼图中心位置。
	                formatter: '{b} : {c}件'  // 标签内容
	            }
	        },
	        labelLine: {                    // 标签的视觉引导线样式,在 label 位置 设置为'outside'的时候会显示视觉引导线。
	            normal: {
	                show: true,             // 是否显示视觉引导线。
	                length: 15,             // 在 label 位置 设置为'outside'的时候会显示视觉引导线。
	                length2: 10,            // 视觉引导项第二段的长度。
	                lineStyle: {            // 视觉引导线的样式
	                    //color: '#000',
	                    //width: 1
	                }
	            }
	        },
	        data: [{"name":"Android","value":1},{"name":"Win10","value":2}]                      // 系列中的数据内容数组。
	    }]
	};
	// 使用刚指定的配置项和数据显示图表
	chart1.setOption(option)

var chart2 = echarts.init(document.getElementById("chart2"));	
	// 圆环图各环节的名称和值(系列中各数据项的名称和值)
	// 指定图表的配置项和数据
	var option = {

	    // 图例
	    legend: [{
	        selectedMode:true,             // 图例选择的模式，控制是否可以通过点击图例改变系列的显示状态。默认开启图例选择，可以设成 false 关闭。
	        top: '10%',
	        left: 'center',
	        textStyle: {                      // 图例的公用文本样式。
	            fontSize: 10,
	            color: '#000'
       		},
	        data: ['Firefox','chrome','IE','Edge']
	    }],
	    
	    // 提示框
	    tooltip: {
	        show: true,                 // 是否显示提示框
	        formatter: '{b} </br> 销量{c}件 </br> 占比{d}%'      // 提示框显示内容,此处{b}表示各数据项名称，此项配置为默认显示项，{c}表示数据项的值，默认不显示，({d}%)表示数据项项占比，默认不显示。
	    },
	    
	    // graphic 是原生图形元素组件。可以支持的图形元素包括：image, text, circle, sector, ring, polygon, polyline, rect, line, bezierCurve, arc, group,
	    graphic: {
	        type: 'text',               // [ default: image ]用 setOption 首次设定图形元素时必须指定。image, text, circle, sector, ring, polygon, polyline, rect, line, bezierCurve, arc, group,
	        top: 'center',              // 描述怎么根据父元素进行定位。top 和 bottom 只有一个可以生效。如果指定 top 或 bottom，则 shape 里的 y、cy 等定位属性不再生效。『父元素』是指：如果是顶层元素，父元素是 echarts 图表容器。如果是 group 的子元素，父元素就是 group 元素。
	        left: 'center',             // 同上
	        style: {
	            text: '浏览器',       // 文本块文字。可以使用 \n 来换行。[ default: '' ]
	            fill: '#000',           // 填充色。
	            fontSize: 12,           // 字体大小
	            fontWeight: 'bold'		// 文字字体的粗细，可选'normal'，'bold'，'bolder'，'lighter'
	        }
	    },
	    
	    // 系列列表
	    series: [{
	        type: 'pie',                    // 系列类型 
	        center:['50%','50%'],           // 饼图的中心（圆心）坐标，数组的第一项是横坐标，第二项是纵坐标。[ default: ['50%', '50%'] ]
	        radius: ['30%', '45%'],         // 饼图的半径，数组的第一项是内半径，第二项是外半径。[ default: [0, '75%'] ]
	        hoverAnimation: true,           // 是否开启 hover 在扇区上的放大动画效果。[ default: true ]
	        label: {                        // 饼图图形上的文本标签，可用于说明图形的一些数据信息，比如值，名称等.
	            normal: {
	                show: true,             // 是否显示标签[ default: false ]
	                position: 'outside',    // 标签的位置。'outside'饼图扇区外侧，通过视觉引导线连到相应的扇区。'inside','inner' 同 'inside',饼图扇区内部。'center'在饼图中心位置。
	                formatter: '{b} : {c}件'  // 标签内容
	            }
	        },
	        labelLine: {                    // 标签的视觉引导线样式,在 label 位置 设置为'outside'的时候会显示视觉引导线。
	            normal: {
	                show: true,             // 是否显示视觉引导线。
	                length: 15,             // 在 label 位置 设置为'outside'的时候会显示视觉引导线。
	                length2: 10,            // 视觉引导项第二段的长度。
	                lineStyle: {            // 视觉引导线的样式
	                    //color: '#000',
	                    //width: 1
	                }
	            }
	        },
	        data: [{
				name: 'Firefox',
				value: 320
			},{
				name: 'chrome',
				value: 586
			},{
				name: 'IE',
				value: 874
			},{
				name: 'Edge',
				value: 725
			}]                      // 系列中的数据内容数组。
	    }]
	};
	// 使用刚指定的配置项和数据显示图表
	chart2.setOption(option)



var chart3 = echarts.init(document.getElementById("chart3"));	
	// 圆环图各环节的名称和值(系列中各数据项的名称和值)
	// 指定图表的配置项和数据
	var option = {

	    // 图例
	    legend: [{
	        selectedMode:true,             // 图例选择的模式，控制是否可以通过点击图例改变系列的显示状态。默认开启图例选择，可以设成 false 关闭。
	        top: '10%',
	        left: 'center',
	        textStyle: {                      // 图例的公用文本样式。
	            fontSize: 10,
	            color: '#000'
       		},
	        data: ['小米','苹果','诺基亚','华为']
	    }],
	    
	    // 提示框
	    tooltip: {
	        show: true,                 // 是否显示提示框
	        formatter: '{b} </br> 销量{c}件 </br> 占比{d}%'      // 提示框显示内容,此处{b}表示各数据项名称，此项配置为默认显示项，{c}表示数据项的值，默认不显示，({d}%)表示数据项项占比，默认不显示。
	    },
	    
	    // graphic 是原生图形元素组件。可以支持的图形元素包括：image, text, circle, sector, ring, polygon, polyline, rect, line, bezierCurve, arc, group,
	    graphic: {
	        type: 'text',               // [ default: image ]用 setOption 首次设定图形元素时必须指定。image, text, circle, sector, ring, polygon, polyline, rect, line, bezierCurve, arc, group,
	        top: 'center',              // 描述怎么根据父元素进行定位。top 和 bottom 只有一个可以生效。如果指定 top 或 bottom，则 shape 里的 y、cy 等定位属性不再生效。『父元素』是指：如果是顶层元素，父元素是 echarts 图表容器。如果是 group 的子元素，父元素就是 group 元素。
	        left: 'center',             // 同上
	        style: {
	            text: '浏览机型',       // 文本块文字。可以使用 \n 来换行。[ default: '' ]
	            fill: '#000',           // 填充色。
	            fontSize: 12,           // 字体大小
	            fontWeight: 'bold'		// 文字字体的粗细，可选'normal'，'bold'，'bolder'，'lighter'
	        }
	    },
	    
	    // 系列列表
	    series: [{
	        name: '圆环图系列名称',         // 系列名称
	        type: 'pie',                    // 系列类型 
	        center:['50%','50%'],           // 饼图的中心（圆心）坐标，数组的第一项是横坐标，第二项是纵坐标。[ default: ['50%', '50%'] ]
	        radius: ['30%', '45%'],         // 饼图的半径，数组的第一项是内半径，第二项是外半径。[ default: [0, '75%'] ]
	        hoverAnimation: true,           // 是否开启 hover 在扇区上的放大动画效果。[ default: true ]
	        label: {                        // 饼图图形上的文本标签，可用于说明图形的一些数据信息，比如值，名称等.
	            normal: {
	                show: true,             // 是否显示标签[ default: false ]
	                position: 'outside',    // 标签的位置。'outside'饼图扇区外侧，通过视觉引导线连到相应的扇区。'inside','inner' 同 'inside',饼图扇区内部。'center'在饼图中心位置。
	                formatter: '{b} : {c}件'  // 标签内容
	            }
	        },
	        labelLine: {                    // 标签的视觉引导线样式,在 label 位置 设置为'outside'的时候会显示视觉引导线。
	            normal: {
	                show: true,             // 是否显示视觉引导线。
	                length: 15,             // 在 label 位置 设置为'outside'的时候会显示视觉引导线。
	                length2: 10,            // 视觉引导项第二段的长度。
	                lineStyle: {            // 视觉引导线的样式
	                    //color: '#000',
	                    //width: 1
	                }
	            }
	        },
	        data: [{
				name: '小米',
				value: 320
			},{
				name: '苹果',
				value: 586
			},{
				name: '诺基亚',
				value: 874
			},{
				name: '华为',
				value: 725
			}]                      // 系列中的数据内容数组。
	    }]
	};
	// 使用刚指定的配置项和数据显示图表
	chart3.setOption(option)



var chart4 = echarts.init(document.getElementById("chart4"));	
	// 圆环图各环节的名称和值(系列中各数据项的名称和值)
	// 指定图表的配置项和数据
	var option = {

	    // 图例
	    legend: [{
	        selectedMode:true,             // 图例选择的模式，控制是否可以通过点击图例改变系列的显示状态。默认开启图例选择，可以设成 false 关闭。
	        top: '10%',
	        left: 'center',
	        textStyle: {                      // 图例的公用文本样式。
	            fontSize: 10,
	            color: '#000'
       		},
	        data: ['联通','移动','电信']
	    }],
	    
	    // 提示框
	    tooltip: {
	        show: true,                 // 是否显示提示框
	        formatter: '{b} </br> 销量{c}件 </br> 占比{d}%'      // 提示框显示内容,此处{b}表示各数据项名称，此项配置为默认显示项，{c}表示数据项的值，默认不显示，({d}%)表示数据项项占比，默认不显示。
	    },
	    
	    // graphic 是原生图形元素组件。可以支持的图形元素包括：image, text, circle, sector, ring, polygon, polyline, rect, line, bezierCurve, arc, group,
	    graphic: {
	        type: 'text',               // [ default: image ]用 setOption 首次设定图形元素时必须指定。image, text, circle, sector, ring, polygon, polyline, rect, line, bezierCurve, arc, group,
	        top: 'center',              // 描述怎么根据父元素进行定位。top 和 bottom 只有一个可以生效。如果指定 top 或 bottom，则 shape 里的 y、cy 等定位属性不再生效。『父元素』是指：如果是顶层元素，父元素是 echarts 图表容器。如果是 group 的子元素，父元素就是 group 元素。
	        left: 'center',             // 同上
	        style: {
	            text: '运营商',       // 文本块文字。可以使用 \n 来换行。[ default: '' ]
	            fill: '#000',           // 填充色。
	            fontSize: 12,           // 字体大小
	            fontWeight: 'bold'		// 文字字体的粗细，可选'normal'，'bold'，'bolder'，'lighter'
	        }
	    },
	    
	    // 系列列表
	    series: [{
	        type: 'pie',                    // 系列类型 
	        center:['50%','50%'],           // 饼图的中心（圆心）坐标，数组的第一项是横坐标，第二项是纵坐标。[ default: ['50%', '50%'] ]
	        radius: ['30%', '45%'],         // 饼图的半径，数组的第一项是内半径，第二项是外半径。[ default: [0, '75%'] ]
	        hoverAnimation: true,           // 是否开启 hover 在扇区上的放大动画效果。[ default: true ]
	        label: {                        // 饼图图形上的文本标签，可用于说明图形的一些数据信息，比如值，名称等.
	            normal: {
	                show: true,             // 是否显示标签[ default: false ]
	                position: 'outside',    // 标签的位置。'outside'饼图扇区外侧，通过视觉引导线连到相应的扇区。'inside','inner' 同 'inside',饼图扇区内部。'center'在饼图中心位置。
	                formatter: '{b} : {c}件'  // 标签内容
	            }
	        },
	        labelLine: {                    // 标签的视觉引导线样式,在 label 位置 设置为'outside'的时候会显示视觉引导线。
	            normal: {
	                show: true,             // 是否显示视觉引导线。
	                length: 15,             // 在 label 位置 设置为'outside'的时候会显示视觉引导线。
	                length2: 10,            // 视觉引导项第二段的长度。
	                lineStyle: {            // 视觉引导线的样式
	                    //color: '#000',
	                    //width: 1
	                }
	            }
	        },
	        data: [{
				name: '移动',
				value: 320
			},{
				name: '联通',
				value: 586
			},{
				name: '电信',
				value: 874
			}]                      // 系列中的数据内容数组。
	    }]
	};
	// 使用刚指定的配置项和数据显示图表
	chart4.setOption(option)