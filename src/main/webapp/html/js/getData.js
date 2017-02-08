$(document).ready(
	function(){
		$("#WIPByLine").click(function(){
			var url = 'http://192.168.30.106:8080/showdata/ExposeData?getDataWay=WIPByLine';  
			$.ajax(url, {    
				crossDomain: true,
				type:'get',
				success: function(data) {  
					var jsonobj = eval(data);
					var data1=[];
					var data2=[];
					$.each(jsonobj, function (n, value) {
					   data1.push(value.name);
					   data2.push(value.num);
					});
				  

					var myChart = echarts.init(document.getElementById('main'));	
					

					var option = {
						title: {
							text: 'ECharts'
						},
						tooltip: {},
						legend: {
							data:['数量']
						},
						xAxis: {
							data: data1
						},
						yAxis: {},
						series: [{
							name: '数量',
							type: 'bar',
							data: data2
						}]
					};


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
					var jsonobj = eval(data);
					var data1=[];
					var data2=[];
					$.each(jsonobj, function (n, value) {
					   data1.push(value.name);
					   data2.push(value.num);
					});
				  

					var myChart = echarts.init(document.getElementById('main'));	
					

					var option = {
						title: {
							text: 'ECharts '
						},
						tooltip: {},
						legend: {
							data:['数量']
						},
						xAxis: {
							data: data1
						},
						yAxis: {},
						series: [{
							name: '数量',
							type: 'bar',
							data: data2
						}]
					};

					myChart.setOption(option);  
				}  
			});
		});
	}
);
		
		

