$(document).ready(
	function(){
		$("#WIPByLine").click(function(){
			var url = 'http://192.168.30.106:8080/showdata/ExposeData?getDataWay=WIPByLine';
			$.ajax(url, {
				crossDomain: true,
				type:'get',
				success: function(data) {
					var jsonobj = eval(data);//通过eval() 函数可以将JSON字符串转化为对象
					var data1=[];
					var data2=[];
					$.each(jsonobj, function (n, value) {
						data1.push(value.name);
						data2.push(value.num);
					});

					// 基于准备好的dom，初始化echarts实例
					var myChart = echarts.init(document.getElementById('main'));
					var colors = ['#5793f3', '#d14a61', '#675bba'];

					option = {
						color: colors,

						title: {
							text: '图表管理'
						},
						tooltip: {
							trigger: 'axis'
						},
						grid: {
							left: '10%',
							top: '20%',
							right: '10%',
							bottom: '15%'
						},
						toolbox: {
							feature: {
								dataView: {show: true, readOnly: false},
								restore: {show: true},
								saveAsImage: {show: true}
							}
						},
						legend: {
							data:['ACTUALQTY','LINE']
						},
						xAxis: [
							{
								type: 'category',
								axisTick: {
									alignWithLabel: true
								},
								data: data1,
								axisLabel:{
									interval: 0
								}
							}
						],
						yAxis: [
							{
								show:false,
							},
							{
								show:false,
							},
							{
								type: 'value',
								name: 'ACTUALQTY',
								min: 0,
								position: 'left',
								axisLine: {
									lineStyle: {
										color: colors[2]
									}
								},
								axisLabel: {
									formatter: '{value}'
								}
							}
						],
						dataZoom: [
							{
								type: 'inside',
								start: 0,
								end: 100
							},
							{
								show: true,
								height: 20,
								type: 'slider',
								top: '90%',
								xAxisIndex: [0],
								start: 0,
								end: 100
							}
						],
						series: [
							{
								name:'ACTUALQTY',
								type:'bar',
								data:data2
							},
							{
								name:'LINE',
								type:'line',
								yAxisIndex: 2,
								data:data2
							}
						]
					};

					// 使用刚指定的配置项和数据显示图表。
					myChart.setOption(option);
				}
			});
		});

		$("#WIPByProject").click(function(){
			var url = 'http://192.168.30.106:8080/showdata/ExposeData?getDataWay=WIPByProject';
			$.ajax(url, {
				crossDomain: true,
				type:'get',
				success: function(data) {
					var jsonobj = eval(data);//通过eval() 函数可以将JSON字符串转化为对象
					var data1=[];
					var data2=[];
					$.each(jsonobj, function (n, value) {
						data1.push(value.name);
						data2.push(value.num);
					});

					// 基于准备好的dom，初始化echarts实例
					var myChart = echarts.init(document.getElementById('main'));

					// 指定图表的配置项和数据
					var option = {
						title: {
							text: '图表管理'
						},
						tooltip: {},
						grid: {
							left: '10%',
							top: '20%',
							right: '10%',
							bottom: '15%'
						},
						legend: {
							data:['ACQUALQTY']
						},
						xAxis: {
							data: data1
						},
						yAxis: {},
						dataZoom: [
							{
								type: 'inside',
								start: 0,
								end: 100
							},
							{
								show: true,
								height: 20,
								type: 'slider',
								top: '90%',
								xAxisIndex: [0],
								start: 0,
								end: 100
							}
						],
						series: [{
							name: 'ACQUALQTY',
							type: 'bar',
							data: data2
						}]
					};

					// 使用刚指定的配置项和数据显示图表。
					myChart.setOption(option);
				}
			});
		});
	}
);
