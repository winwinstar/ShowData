<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <metahttp-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>manager</title>
    <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="/auth/treeview/css/demo.css" type="text/css">
    <link rel="stylesheet" href="/auth/treeview/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="/auth/treeview/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="/auth/treeview/js/jquery.ztree.excheck.js"></script>
    <script src="http://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.js"></script>
    <SCRIPT type="text/javascript">
        var url = '../GetUserRoleAuth';
        var jsonobj;
        var dataNow;
        if($.cookie("user") == null || $.cookie("user") == "null"){
            top.location.href = "../login.html";
        }
        $.ajax(url, {
            crossDomain: true,
            type:'get',
            success: function(data) {
                jsonobj = eval(data);//通过eval() 函数可以将JSON字符串转化为对象
                $.each(jsonobj, function (n, value) {
                    var content = '<tr>'+
                            '<td>'+value.role+'</td>'+
                            '<td>'+value.createTime+'</td>'+
                            '<td>'+
                            '<button onclick="searchUser('+ n +')" class="btn btn-sm">查看用户</button>'+
                            '<button onclick="delRow('+ n +')" class="btn btn-sm" style="margin-left: 5px">删除角色</button>'+
                            '</td>'+
                            '</tr>';
                    //获取要添加的table、往tbody添加一行
                    $("#table1 tbody").append(content);
                });

            }
        });

        searchUser = function(m){
            $("#table2 tr:not(:first)").remove();
            var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
            treeObj.checkAllNodes(false);
            $.each(jsonobj, function (n, value) {
                if(m==n){
                    dataNow = value;
                    if(value.users != null){
                        $.each(value.users, function(k,user){

                            var content = '<tr>'+
                                    '<td>'+user.userName+'</td>'+
                                    '<td>'+user.roleName+'</td>'+
                                    '<td>'+user.createTime+'</td>'+
                                    '<td>'+
                                    '<button onclick="delUser('+ k +')" class="btn btn-sm">删除用户</button>'+
                                    '<button onclick="changePwd('+ k +')" class="btn btn-sm" style="margin-left: 5px">设置密码</button>'+
                                    '</td>'+
                                    '</tr>';
                            //获取要添加的table、往tbody添加一行
                            $("#table2 tbody").append(content);

                        });
                    }
                    if(value.authItems != null){
                        $.each(value.authItems, function(k,auth){
                            var nodes = treeObj.getNodes();
                            $.each(nodes, function (n, value1) {
                                if(auth.Id == value1.id ){
                                    treeObj.checkNode(value1,true,true,false);
                                }
                                if(value1.children != null){
                                    $.each(value1.children, function (n, value2) {
                                        if(auth.Id == value2.id){
                                            treeObj.checkNode(value2,true,true,false);
                                        }
                                    });
                                }
                            });
                        });
                    }
                }

            });
            $("#addUser").removeAttr("disabled");
        }

        function delRow(m){
            $.each(jsonobj, function (n, value) {
                if (m == n) {
                    con = confirm("确认删除"+value.role+"角色？"); //在页面上弹出对话框
                    if(con == true){
                        $.ajax({
                            //String parentName,String childName,int Signal,int tran,boolean isDelete,boolean Hasresult
                            type: "get",//数据发送的方式（post 或者 get）
                            url: "../RoleUserAction",//要发送的后台地址
                            dataType: "json",
                            data:{action:"delRole",parentName:value.role},
                            success: function () {//ajax请求成功后触发的方法
                                window.location.reload();
                            },
                            error: function (msg) {//ajax请求失败后触发的方法
                                window.location.reload();
                            }
                        });
                    }
                }
            });
        }

        function addRole(){
            var name;
            name=prompt("请输入角色名称！");
            if(name != null){
                $.ajax({
                    //String parentName,String childName,int Signal,int tran,boolean isDelete,boolean Hasresult
                    type: "get",//数据发送的方式（post 或者 get）
                    url: "../RoleUserAction",//要发送的后台地址
                    dataType: "json",
                    data:{action:"addRole",parentName:name},
                    success: function () {//ajax请求成功后触发的方法
                        window.location.reload();
                    },
                    error: function (msg) {//ajax请求失败后触发的方法
                        window.location.reload();
                    }
                });
            }else{

            }
        }

        function addUser(){
            var name;
            name=prompt("请输入用户名称！");
            if(name != null){
                $.ajax({
                    //String parentName,String childName,int Signal,int tran,boolean isDelete,boolean Hasresult
                    type: "get",//数据发送的方式（post 或者 get）
                    url: "../RoleUserAction",//要发送的后台地址
                    dataType: "json",
                    data:{action:"addUser",parentName:dataNow.role,childName:name},
                    success: function () {//ajax请求成功后触发的方法
                        window.location.reload();
                    },
                    error: function (msg) {//ajax请求失败后触发的方法
                        window.location.reload();
                    }
                });
            }else{
            }
        }

        function delUser(m){
            $.each(dataNow.users, function (n, user) {
                if (m == n) {
                    con = confirm("确认删除"+user.roleName+"角色的"+user.userName+"用户？"); //在页面上弹出对话框
                    if(con == true){
                        $.ajax({
                            //String parentName,String childName,int Signal,int tran,boolean isDelete,boolean Hasresult
                            type: "get",//数据发送的方式（post 或者 get）
                            url: "../RoleUserAction",//要发送的后台地址
                            dataType: "json",
                            data:{action:"delUser",parentName:user.roleName,childName:user.userName},
                            success: function () {//ajax请求成功后触发的方法
                                window.location.reload();
                            },
                            error: function (msg) {//ajax请求失败后触发的方法
                                window.location.reload();
                            }
                        });
                    }
                }
            });
        }

        function changePwd(m){
            $.each(dataNow.users, function (n, user) {
                if (m == n) {
                    var con = prompt("请输入要使用的密码！"); //在页面上弹出对话框
                    if(con != null){
                        $.ajax({
                            //String parentName,String childName,int Signal,int tran,boolean isDelete,boolean Hasresult
                            type: "get",//数据发送的方式（post 或者 get）
                            url: "../RoleUserAction",//要发送的后台地址
                            dataType: "json",
                            data:{action:"changePwd",parentName:user.roleName,childName:user.userName,passWord:con},
                            success: function () {//ajax请求成功后触发的方法
                                window.location.reload();
                            },
                            error: function (msg) {//ajax请求失败后触发的方法
                                window.location.reload();
                            }
                        });
                    }
                }
            });
        }

        var setting = {
            check: {
                enable: true
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback: {
                onCheck: zTreeOnCheck
            }
        };

        var code;
        function zTreeOnCheck(event, treeId, treeNode) {
            var parentName = dataNow.role;
            var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
            var childName = "";
            if(treeNode.checked){
                authOperation(parentName,treeNode.name,true);
                if(treeNode.getParentNode()!=null && !treeNode.getParentNode().checked){
                    authOperation(parentName,treeNode.getParentNode().name,true);
                }
                if(treeNode.children != null){
                    $.each(treeNode.children, function (n, value) {
                        childName += value.name +"&"
                    });
                    authOperation(parentName,childName,true);
                    childName = "";
                }
            }else{
                if(treeNode.children != null){
                    authOperation(parentName,treeNode.name,false);
                    $.each(treeNode.children, function (n, value) {
                        childName += value.name +"&"
                    });
                    authOperation(parentName,childName,false);
                    childName = "";
                }
                if(treeNode.children == null){
                    var count = 0;
                    if(treeNode.getParentNode() != null){
                        $.each(treeNode.getParentNode().children, function (n, value) {
                            if(value.checked){
                                count++;
                            }
                        });
                        if(count<1){
                            authOperation(parentName,treeNode.getParentNode().name,false);
                        }
                    }
                    authOperation(parentName,treeNode.name,false);
                }
            }
            window.setTimeout("refreshPage()",500);
        };

        function refreshPage(){
            window.location.reload();
            window.parent.location.reload();
        }

        function authOperation(parentName,childName,action){
            var changeAuth = "";
            if(action){
                changeAuth = "addAuth";
            }else{
                changeAuth = "cancelAuth";
            }
            $.ajax({
                //String parentName,String childName,int Signal,int tran,boolean isDelete,boolean Hasresult
                type: "get",//数据发送的方式（post 或者 get）
                url: "../RoleUserAction",//要发送的后台地址
                dataType: "json",
                data:{action:changeAuth,parentName:parentName,childName:childName},
                success: function () {//ajax请求成功后触发的方法

                },
                error: function (msg) {//ajax请求失败后触发的方法

                }
            });
        }

        function setCheck() {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
                    py = $("#py").attr("checked")? "p":"",
                    sy = $("#sy").attr("checked")? "s":"",
                    pn = $("#pn").attr("checked")? "p":"",
                    sn = $("#sn").attr("checked")? "s":"",
                    type = { "Y" : "", "N" : "" };
            zTree.setting.check.chkboxType = type;
            showCode('setting.check.chkboxType = { "Y" : "' + type.Y + '", "N" : "' + type.N + '" };');
        }
        function showCode(str) {
            if (!code) code = $("#code");
            code.empty();
            code.append("<li>"+str+"</li>");
        }

        $(document).ready(function(){
            var getMenuListUrl = '../GetMenuList';
            $.ajax(getMenuListUrl, {
                crossDomain: true,
                type:'get',
                success: function(data) {
                    var jsonObj = eval("(" + data + ")");
                    $.fn.zTree.init($("#treeDemo"), setting, jsonObj);
                    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                    treeObj.expandAll(true);
                    setCheck();
                    $("#py").bind("change", setCheck);
                    $("#sy").bind("change", setCheck);
                    $("#pn").bind("change", setCheck);
                    $("#sn").bind("change", setCheck);
                }
            });
        });

    </SCRIPT>
</head>
<body>
<div style="width:100%;">
    <div style="width: 50%;height:400px;float:left;overflow:auto;border:1px solid;border-radius: 20px" >
        <table id="table1" class="table table-hover">
            <caption><button type="button" onclick="addRole()" class="btn btn-default">添加角色</button></caption>
            <thead>
            <tr>
                <th>角色</th>
                <th>创建时间</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>

            </tbody>
        </table>
    </div>

    <div  style="width: 50%;height:400px;float:left;" >
        <div style="margin-left: 50px">
            <p>权限列表</p>
            <div class="content_wrap">
                <div class="zTreeDemoBackground left">
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>

    <div style="width: 50%;position:absolute;height:400px;overflow:auto;border:1px solid;border-radius: 20px;margin-top: 420px" >
        <table id="table2" class="table table-hover">
            <caption><button id="addUser" type="button" onclick="addUser()" class="btn btn-default" disabled="disabled">添加用户</button></caption>
            <thead>
            <tr>
                <th>用户</th>
                <th>用户角色</th>
                <th>创建时间</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>

            </tbody>
        </table>
    </div>
</div>
</body>
</html>