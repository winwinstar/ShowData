<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<html>
<head>
	<metahttp-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<title>首页</title>

	<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
	<link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="http://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.js"></script>
	<link rel=stylesheet type=text/css href="css/lrtk.css">
	<script type="text/javascript">
		$(document).ready(function(){
			$("body").on("click","#manager",function(){
				$("#iframe").attr("src","auth/manager.jsp");
			});

			$("body").on("click","#WIP_By_Line",function(){
				$("#iframe").attr("src","charts/WIP_By_Line.html");
			});

			$("body").on("click","#WIPByProject",function(){
				$("#iframe").attr("src","charts/WIPByProject.html");
			});

			$("body").on("click","#RTY",function(){
				$("#iframe").attr("src","charts/RTY.html");
			});

			$("body").on("click","#loginOut",function(){
				if($.cookie("user") != null){
					$.cookie("user",null);
				}
				top.location.href = "../login.html";
			});

			$("#firstpane .menu_body:eq(0)").show();
			$("#firstpane p.menu_head").click(function(){
				$(this).addClass("current").next("div.menu_body").slideToggle(300).siblings("div.menu_body").slideUp("slow");
				$(this).siblings().removeClass("current");
			});
			$("#secondpane .menu_body:eq(0)").show();
			$("#secondpane p.menu_head").mouseover(function(){
				$(this).addClass("current").next("div.menu_body").slideDown(500).siblings("div.menu_body").slideUp("slow");
				$(this).siblings().removeClass("current");
			});

			$('li.dropdown').mouseover(function() {
				$(this).addClass('open');}).mouseout(function() {$(this).removeClass('open');});
		});
	</script>
</head>
<body>
<nav class="navbar navbar-default navbar-fixed-top" style="background-color: #efefef;" role="navigation">
	<div class="container-fluid">
		<div>
			<ul id="headContent" class="nav navbar-nav">
				<script>
					var param = '<%=request.getAttribute("param")%>';
					var user = '<%=request.getAttribute("user")%>';
					if(user == "null" || user == ""){
						window.location.href = "login.html";
					}else{
						var time = new Date();
						$.cookie("user",user,time.getTime()+(1000*60*60));
					}

					$("#headContent").append("<li class='dropdown' style='position:absolute;float:right;right:20px;'>"
                            + "<a href='#' class='dropdown-toggle' data-toggle='dropdown'>"+user+"<b class='caret'></b></a> "
                            +"	<ul class='dropdown-menu'> "
                            + "<li><a href='#' id='loginOut'>"+"退出"+"</a></li> "
                            +"	</ul> "
                            +"</li>");

                    if(param !=null && param!==""){
                        var strs = new Array();
                        strs = param.split(";");
                        var head = "";
                        var body = "";
                        for(i=0;i<strs.length;i++){
                            var strChild = strs[i].split("&");
                            if(strChild.length <= 2){
                                var parentName = strChild[0];
                                if(parentName==="权限管理") $("#headContent").append("<li><a href='#' id='manager'>"+parentName+"</a></li>");
                                else $("#headContent").append("<li><a href='#' id="+parentName+">"+parentName+"</a></li>");
                            }else{
                                for(j=0;j<strChild.length-1;j++){

                                    if(j==0) {
                                        head = "<a href='#' class='dropdown-toggle' data-toggle='dropdown'>"+strChild[j]+"<b class='caret'></b></a> ";
                                    }else if(j==strChild.length-2){
                                        body += "<li><a href='#' id="+strChild[j]+">"+strChild[j]+"</a></li> "
                                    }else{
                                        body += "<li><a href='#' id="+strChild[j]+">"+strChild[j]+"</a></li> "
                                                +"		<li class='divider'></li> ";
                                    }
                                }
                                $("#headContent").append("<li class='dropdown'>"
                                        + head
                                        +"	<ul class='dropdown-menu'> "
                                        + body
                                        +"	</ul> "
                                        +"</li>");
                                body = "";
                            }
                        }
                    }
				</script>
			</ul>
		</div>
	</div>
</nav>

<!--
<div style="margin-top:70px;width:100%;float:left;margin-right: 20px">
	<div id="firstpane" class="menu_list" style="float:left;width:15%">
		<p class="menu_head current">哲学</p>
		<div style="display:block" class=menu_body >
			<a href="#">科学技术哲学</a>
			<a href="#">宗教学</a>
			<a href="#">美学</a>
			<a href="#">伦理学</a>
			<a href="#">逻辑学</a>
			<a href="#">外国哲学</a>
			<a href="#">中国哲学</a>
			<a href="#">马克思主义哲学</a>
		</div>
		<p class="menu_head">经济学</p>
		<div style="display:none" class=menu_body >
			<a href="#">应用经济学</a>
			<a href="#">理论经济学</a>
			<a href="#">国民经济学</a>
			<a href="#">区域经济学</a>
			<a href="#">产业经济学</a>
			<a href="#">国际贸易学</a>
			<a href="#">劳动经济学</a>
			<a href="#">政治经济学</a>
		</div>
		<p class="menu_head">法学</p>
		<div style="display:none" class=menu_body >
			<a href="#">马克思主义基本原理</a>
			<a href="#">马克思主义发展史</a>
			<a href="#">马克思主义中国化研究</a>
			<a href="#">国外马克思主义研究</a>
			<a href="#">思想政治教育</a>
		</div>
		<p class="menu_head">教育学</p>
		<div style="display:none" class=menu_body >
			<a href="#">体育人文社会学</a>
			<a href="#">体育教育训练学</a>
			<a href="#">民族传统体育学</a>
			<a href="#">发展与教育心理学</a>
			<a href="#">应用心理学</a>
			<a href="#">教育学原理</a>
			<a href="#">课程与教学论</a>
			<a href="#">比较教育学</a>
			<a href="#">学前教育学</a>
			<a href="#">高等教育学</a>
			<a href="#">成人教育学</a>
			<a href="#">职业技术教育学</a>
			<a href="#">特殊教育学</a>
			<a href="#">教育技术学</a>
		</div>
		<p class="menu_head">工学</p>
		<div style="display:none" class=menu_body >
			<a href="#">一般力学与力学基础</a>
			<a href="#">固体力学</a>
			<a href="#">流体力学</a>
			<a href="#">工程力学</a>
			<a href="#">机械制造及其自动化</a>
			<a href="#">机械电子工程</a>
			<a href="#">机械设计及理论</a>
			<a href="#">仪器科学与技术</a>
			<a href="#">精密仪器及机械</a>
			<a href="#">测试计量技术及仪器</a>
		</div>
	</div>

	-->

	<iframe id="iframe" style="width:80%;height:900px;float:left;margin-left: 50px;margin-top:70px" frameborder="0"></iframe>
</div>

</body>
</html>