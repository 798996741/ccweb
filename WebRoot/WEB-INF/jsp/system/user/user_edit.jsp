<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <!-- 下拉框 -->
    <jsp:include page="../include/incJs_mx.jsp"></jsp:include>
    
    <link type="text/css" rel="stylesheet" href="plugins/zTree/3.5/zTreeStyle.css"/>
    <style>
        ul li {
            line-height: 30px;
            list-style-type: none
        }
    </style>
</head>
<body class="no-skin">
<div class="main-container" id="main-container">
    <!-- Horizontal Form -->
    <!-- <div class="box box-info">
        <div class="box-header with-border">
            <h3 class="box-title">用户添加</h3>
        </div> -->
    <!-- /.box-header -->
    <!-- form start -->
    <div class="main-content">
        <div class="main-content-inner">
            <div class="page-content">
                <div class="modal-header">
                    <h4 class="modal-title" id="myModalLabel" style="float: left;"></h4>
                    <div class="new-tb" style="float: right;" data-dismiss="modal" title="关闭"></div>
                </div>
                <div class="row">
                    <div class="col-xs-12">
                        <form action="user/${msg }.do" name="userForm" id="userForm_add" method="post">
                            <input type="hidden" name="USER_ID" id="user_id" value="${pd.USER_ID }"/>
                            <div id="zhongxin" style="padding: 5px;border-radius: 5px">
                                <div class="row" style="margin-top: 10px">
                                    <div class="col-md-6 flex-row-center-center" style="white-space: nowrap">
                                        <span class="border-size" style="width:70px;text-align-last: justify;">角色</span>
                                        <select class="detail-seat-select auto-width-medium" style="width: 170px"
                                                name="ROLE_ID" id="role_id1" data-placeholder="请选择角色">
                                            <option value=""></option>
                                            <c:forEach items="${roleList}" var="role">
                                                <option value="${role.ROLE_ID }"
                                                        <c:if test="${role.ROLE_ID == pd.ROLE_ID }">selected</c:if>>${role.ROLE_NAME }</option>

                                            </c:forEach>
                                        </select>
                                        <input type="hidden" id="ROLE_NAME" name="ROLE_NAME"
                                               value="${role.ROLE_NAME }">
                                    </div>
                
                                    <div class="col-md-6 flex-row-center-center" style="white-space: nowrap">
                                        <span class="border-size"
                                              style="width:70px;text-align-last: justify;">所属单位</span>

                                        <input type="hidden" id="DWBM" name="DWBM" value="${pd.DWBM }"
                                               class="detail-seat-input auto-width-medium" style="width: 170px"/>
                                        <input class="detail-seat-input auto-width-medium" style="width: 170px;"
                                               id="citySel" name="" type="text" readonly
                                               value="${pd.AREA_NAME }"
                                               onclick="showMenu(); return false;"/>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-md-6 flex-row-center-center" style="white-space: nowrap">
                                        <span class="border-size"
                                              style="width:70px;text-align-last: justify;">用户名</span>

                                        <input type="text" name="USERNAME" id="loginname"
                                               value="${pd.USERNAME }" maxlength="32"
                                               placeholder="这里输入用户名" title="用户名"
                                               class="detail-seat-input auto-width-medium" style="width: 170px"/>

                                    </div>
                                   
                                    <div class="col-md-6 flex-row-center-center" style="white-space: nowrap">
                                        <span class="border-size" style="width:70px;text-align-last: justify;">工号</span>

                                        <input type="text" name="NUM" id="NUM"
                                               value="${pd.NUM }" maxlength="32"
                                               placeholder="这里输入工号" title="工号"
                                               onblur="hasN('${pd.USERNAME }')"
                                               class="detail-seat-input auto-width-medium" style="width: 170px"/>

                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 flex-row-center-center" style="white-space: nowrap">
                                        <span class="border-size" style="width:70px;text-align-last: justify;">密码</span>
                                        <input type="password" name="PASSWORD"
                                               id="password" maxlength="32"
                                               placeholder="输入密码" title="密码" class="detail-seat-input auto-width-medium"
                                               style="width: 170px"/>

                                    </div>
                                   
                                    <div class="col-md-6 flex-row-center-center" style="white-space: nowrap">
                                        <span class="border-size"
                                              style="width:70px;text-align-last: justify;">确认密码</span>
                                        <input type="password" name="chkpwd" id="chkpwd"
                                               maxlength="32" placeholder="确认密码"
                                               title="确认密码" class="detail-seat-input auto-width-medium"
                                               style="width: 170px"/>

                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 flex-row-center-center" style="white-space: nowrap">
                                        <span class="border-size" style="width:70px;text-align-last: justify;">姓名</span>
                                        <input type="text" name="NAME" id="name"
                                               value="${pd.NAME }" maxlength="32"
                                               placeholder="这里输入姓名" title="姓名"
                                               class="detail-seat-input auto-width-medium" style="width: 170px"/>

                                    </div>
                                  
                                    <div class="col-md-6 flex-row-center-center" style="white-space: nowrap">
                                        <span class="border-size"
                                              style="width:70px;text-align-last: justify;">手机号</span>
                                        <input type="NUMBER" name="PHONE" id="PHONE"
                                               value="${pd.PHONE }" maxlength="32"
                                               placeholder="这里输入手机号" title="手机号"
                                               class="detail-seat-input auto-width-medium" style="width: 170px"/>

                                    </div>
                                   
                                </div>
                                <div class="row">
                                    <div class="col-md-6 flex-row-center-center" style="white-space: nowrap">
                                        <span class="border-size" style="width:70px;text-align-last: justify;">邮箱</span>
                                        <input type="email" name="EMAIL" id="EMAIL"
                                               value="${pd.EMAIL }" maxlength="32"
                                               placeholder="这里输入邮箱" title="邮箱"
                                               onblur="hasE('${pd.USERNAME }')"
                                               class="detail-seat-input auto-width-medium" style="width: 170px"/>

                                    </div>
                                   

                                    <div class="col-md-6 flex-row-center-center" style="white-space: nowrap">
                                        <span class="border-size" style="width:70px;text-align-last: justify;">状态</span>
	                                    <select class="detail-seat-select auto-width-medium" style="width: 170px"
                                                id="STATUS" name="STATUS">
                                            <option value="0" <c:if test="${pd.STATUS==0}">selected</c:if>>正常
                                            </option>
                                            <option value="-1" <c:if test="${pd.STATUS==-1}">selected</c:if>>冻结
                                            </option>
                                        </select>
                                    </div>
                                </div>
                                <div class="row">
                                    
                                    <div class="col-md-6 flex-row-center-center" style="white-space: nowrap">
                                        <span class="border-size"
                                              style="width:70px;text-align-last: justify;text-align: center;line-height: 13px">关联<br/>微信用户</span>
                                        <select class="detail-seat-select auto-width-medium" style="width: 170px"
                                                name="wxid" id="wxid" data-placeholder="关联微信用户"
                                                style="width:98%;height:25px">
                                            <option value=""></option>
                                            <c:forEach items="${wxuser}" var="var" varStatus="vs">
                                                <option value="${var.userid}"
                                                        <c:if test="${var.userid==pd.wxid}">selected</c:if>
                                                > ${var.name}</option>

                                            </c:forEach>
                                        </select>

                                    </div>

                                  
                                    <div class="col-md-6 flex-row-center-center" style="white-space: nowrap">
                             </div>
                                </div>
                                <div class="new-bc" style="padding: 10px">
                                    <a onclick="save();">保存</a>
                                    <a class="new-qxbut" data-btn-type="cancel" data-dismiss="modal">取消</a>
                                </div>

                            </div>
                    </div>

                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- </div> -->
</div>
<div id="menuContent" class="menuContent"
     style="min-height:250px;display:none; position: absolute;background:rgb(238,242,245);border:1px #ccc solid;left: 65%;
    top: 30%;">
    <ul id="treeDemo" class="ztree" style="margin-top:0; width:200px;"></ul>
</div>

<script type="text/javascript">

    var setting = {
       
   		check: {
			enable: true,
			chkStyle: "radio",
			radioType: "level"
		},
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            beforeClick: beforeClick,
            //onClick: onClick,
            onCheck: onCheck
        }
    };
    var zn = '${zTreeNodes}';
    var zNodes = eval(zn);


    function beforeClick(treeId, treeNode) {
        //var check = (treeNode && !treeNode.isParent);
        //if (!check) alert("只能选择城市...");
        //return check;
    }

    function onCheck(e, treeId, treeNode) {
    	 var zTreeObj = $.fn.zTree.getZTreeObj(treeId);
        // optParOrSon(treeId, treeNode,zTreeObj);
    	
        var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
            nodes = zTree.getCheckedNodes(true),
            v = "";
        nodes.sort(function compare(a, b) {
            return a.id - b.id;
        });
        var v_code="";
        for (var i = 0, l = nodes.length; i < l; i++) {
        	if(nodes[i].AREA_LEVEL=='3'||nodes[i].AREA_LEVEL=='4'){
        		v += nodes[i].name + ",";
        		v_code+= nodes[i].AREA_CODE + ",";
        	}
            
        }
        if (v.length > 0) v = v.substring(0, v.length - 1);
        if (v_code.length > 0) v_code = v_code.substring(0, v_code.length - 1);
        var cityObj = $("#citySel");
        cityObj.attr("value", v);
        $("#DWBM").val(v_code);
        //hideMenu();
    }
    
    
    var optParOrSon = function(treeId,treeNode,zTreeObj){
        if(treeNode.checked){
        	alert(treeNode.checked);
            //取消全部后代节点的选中
            var childNodes = getChildsByTreeNode(treeNode);
            for(var i in childNodes){
                zTreeObj.checkNode(childNodes[i], false, false);
            }
            //同时全部祖先节点也不能被选中
            if(treeNode.getParentNode()){
                var parNodes = getParsByTreeNode(treeNode);
                for(var i in parNodes){
                    zTreeObj.checkNode(parNodes[i], false, false);
                }
            }
        }
    };
    
    function showMenu() {

        var cityObj = $("#citySel");
        var cityOffset = $("#citySel").offset();
        $("#menuContent").css({
            left: (cityOffset.left - 250) + "px",
            top: (cityOffset.top - 120) + cityObj.outerHeight() + "px"
        }).slideDown("fast");
        $("body").bind("mousedown", onBodyDown);
    }

    function hideMenu() {
        $("#menuContent").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDown);
    }

    function onBodyDown(event) {
        if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length > 0)) {
            hideMenu();
        }
    }

    $(document).ready(function () {
        $.fn.zTree.init($("#treeDemo"), setting, zNodes);
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        treeObj.expandAll(true);
        if ('${pd.AREA_ID}' != "") {
            //treeObj.checkNode(treeObj.getNodeByParam("id", '${pd.AREA_ID}'), true);
            //alert('${pd.AREA_ID}');
        	var ids = '${pd.AREA_ID}'.split(',');
        	//var cnode1 =treeObj.getNodeByParam("AREA_CODE", 0);
        	//treeObj.checkNode(cnode1,true);
        	for(var i in ids){
        		var cnode = treeObj.getNodeByParam("id", ids[i]);
        		treeObj.checkNode(cnode,true);
        	}
        }

        if ($("#user_id").val() != "") {
            $("#loginname").attr("readonly", "readonly");
            $("#loginname").css("color", "gray");
        }
    });

    //保存
    function save() {
        //alert($("#role_id1").val());
        if ($("#role_id1").val() == "") {
            $("#juese").tips({
                side: 3,
                msg: '选择角色',
                bg: '#AE81FF',
                time: 2
            });
            $("#role_id1").focus();
            return false;
        }
        if ($("#DWBM").val() == "") {
            $("#citySel").tips({
                side: 3,
                msg: '请选择单位编码',
                bg: '#AE81FF',
                time: 0
            });
            $("#citySel").focus();
            return false;
        }
        if ($("#loginname").val() == "" || $("#loginname").val() == "此用户名已存在!") {
            $("#loginname").tips({
                side: 3,
                msg: '输入用户名',
                bg: '#AE81FF',
                time: 2
            });
            $("#loginname").focus();
            $("#loginname").val('');
            $("#loginname").css("background-color", "white");
            return false;
        } else {
            $("#loginname").val(jQuery.trim($('#loginname').val()));
        }

        if ($("#NUM").val() == "") {
            $("#NUM").tips({
                side: 3,
                msg: '输入工号',
                bg: '#AE81FF',
                time: 3
            });
            $("#NUM").focus();
            return false;
        } else {
            $("#NUM").val($.trim($("#NUM").val()));
        }
        if ($("#user_id").val() == "" && $("#password").val() == "") {
            $("#password").tips({
                side: 3,
                msg: '输入密码',
                bg: '#AE81FF',
                time: 2
            });
            $("#password").focus();
            return false;
        }
        
        var userPwd=$("#password").val();
        if (userPwd.length < 8) {
        	layer.alert("密码长度不能小于8位!");
            return false;
        }
		var reg7 = /\d*\D*((\d+[a-zA-Z]+[^0-9a-zA-Z]+)|(\d+[^0-9a-zA-Z]+[a-zA-Z]+)|([a-zA-Z]+\d+[^0-9a-zA-Z]+)|([a-zA-Z]+[^0-9a-zA-Z]+\d+)|([^0-9a-zA-Z]+[a-zA-Z]+\d+)|([^0-9a-zA-Z]+\d+[a-zA-Z]+))\d*\D*/; //数字字母字符任意组合
		if (!reg7.test(userPwd)) {
			layer.alert("您输入的密码太简单，需要数字、字母、字符进行组合!");
           	return false;
        }

        if ($("#password").val() != $("#chkpwd").val()) {

            $("#chkpwd").tips({
                side: 3,
                msg: '两次密码不相同',
                bg: '#AE81FF',
                time: 3
            });
            $("#chkpwd").focus();
            return false;
        }
        if ($("#name").val() == "") {
            $("#name").tips({
                side: 3,
                msg: '输入姓名',
                bg: '#AE81FF',
                time: 3
            });
            $("#name").focus();
            return false;
        }
        var myreg = /^(((13[0-9]{1})|159)+\d{8})$/;
        if ($("#PHONE").val() == "") {

            $("#PHONE").tips({
                side: 3,
                msg: '输入手机号',
                bg: '#AE81FF',
                time: 3
            });
            $("#PHONE").focus();
            return false;
        } else if ($("#PHONE").val().length != 11 && !myreg.test($("#PHONE").val())) {
            $("#PHONE").tips({
                side: 3,
                msg: '手机号格式不正确',
                bg: '#AE81FF',
                time: 3
            });
            $("#PHONE").focus();
            return false;
        }
        if ($("#user_id").val() == "") {
            hasU();
        } else {
            $("#userForm_add").submit();
            $("#zhongxin").hide();
            $("#zhongxin2").show();
        }
    }

    function ismail(mail) {
        return (new RegExp(/^(?:[a-zA-Z0-9]+[_\-\+\.]?)*[a-zA-Z0-9]+@(?:([a-zA-Z0-9]+[_\-]?)*[a-zA-Z0-9]+\.)+([a-zA-Z]{2,})+$/).test(mail));
    }

    //判断用户名是否存在
    function hasU() {
        var USERNAME = $.trim($("#loginname").val());
        $.ajax({
            type: "POST",
            url: '<%=basePath%>user/hasU.do',
            data: {USERNAME: USERNAME, tm: new Date().getTime()},
            dataType: 'json',
            cache: false,
            success: function (data) {
                if ("success" == data.result) {
                    $("#userForm_add").submit();
                    //$("#zhongxin").hide();
                    //$("#zhongxin2").show();
                } else {
                    $("#loginname").css("background-color", "#D16E6C");
                    setTimeout("$('#loginname').val('此用户名已存在!')", 500);
                }
            }
        });
    }

    //判断邮箱是否存在
    function hasE(USERNAME) {
        var EMAIL = $.trim($("#EMAIL").val());
        $.ajax({
            type: "POST",
            url: '<%=basePath%>user/hasE.do',
            data: {EMAIL: EMAIL, USERNAME: USERNAME, tm: new Date().getTime()},
            dataType: 'json',
            cache: false,
            success: function (data) {
                if ("success" != data.result) {
                    $("#EMAIL").tips({
                        side: 3,
                        msg: '邮箱 ' + EMAIL + ' 已存在',
                        bg: '#AE81FF',
                        time: 3
                    });
                    $("#EMAIL").val('');
                }
            }
        });
    }

    //判断编码是否存在
    function hasN(USERNAME) {
        var NUM = $.trim($("#NUM").val());
        $.ajax({
            type: "POST",
            url: '<%=basePath%>user/hasN.do',
            data: {NUM: NUM, USERNAME: USERNAME, tm: new Date().getTime()},
            dataType: 'json',
            cache: false,
            success: function (data) {
                if ("success" != data.result) {
                    $("#NUM").tips({
                        side: 3,
                        msg: '工号 ' + NUM + ' 已存在',
                        bg: '#AE81FF',
                        time: 3
                    });
                    $("#NUM").val('');
                }
            }
        });
    }

</script>
</body>
</html>
