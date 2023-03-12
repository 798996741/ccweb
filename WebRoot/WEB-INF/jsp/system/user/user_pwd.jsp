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

    <style>
        ul li {
            line-height: 30px;
            list-style-type: none
        }
    </style>
</head>
<body class="no-skin">
<div class="main-container" id="main-container">
    <!-- /section:basics/sidebar -->
    <div class="main-content">
        <div class="modal-header">
            <h4 class="modal-title" id="myModalLabel" style="float: left;">修改密码</h4>
            <div class="new-tb" style="float: right;" data-dismiss="modal" title="关闭"></div>
        </div>
        <form action="user/UpdatePwd.do" name="userForm" id="userForm_add" method="post">
            <input type="hidden" name="USER_ID" id="user_id" value="${pd.USER_ID }"/>
            <div id="zhongxin"
                 style="padding: 20px;display: flex;flex-direction: column;justify-content: center;align-items: center">

                <div class=" flex-row-center-center"
                     style="white-space: nowrap">
								<span class="border-size"
                                      style="width:70px;text-align-last: justify;">用户名</span> <input
                        type="text" name="USERNAME" id="loginname"
                        value="${pd.USERNAME }" maxlength="32" placeholder="这里输入用户名"
                        title="用户名" class="detail-seat-input auto-width-medium"
                        style="width: 170px"/>

                </div>
                <div class="flex-row-center-center"
                     style="white-space: nowrap">
								<span class="border-size"
                                      style="width:70px;text-align-last: justify;">密码</span> <input
                        type="password" name="PASSWORD" id="password" maxlength="32"
                        placeholder="输入密码" title="密码"
                        class="detail-seat-input auto-width-medium"
                        style="width: 170px"/>

                </div>
                <div class="flex-row-center-center"
                     style="white-space: nowrap">
								<span class="border-size"
                                      style="width:70px;text-align-last: justify;">确认密码</span> <input
                        type="password" name="chkpwd" id="chkpwd" maxlength="32"
                        placeholder="确认密码" title="确认密码"
                        class="detail-seat-input auto-width-medium"
                        style="width: 170px"/>

                </div>


                <div class="new-bc" style="padding: 10px;">
                    <a onclick="save();">保存</a> <a class="new-qxbut" onclick="cancel();"
                                                   data-btn-type="cancel" data-dismiss="modal">取消</a>
                </div>
            </div>
        </form>
    </div>
</div>
<%@ include file="../../system/include/incJs_foot.jsp" %>
<script type="text/javascript">


    $(document).ready(function () {
        $.fn.zTree.init($("#treeDemo"), setting, zNodes);
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        treeObj.expandAll(true);
        if ('${pd.AREA_ID}' != "") {
            //treeObj.checkNode(treeObj.getNodeByParam("id", '${pd.AREA_ID}'), true);
        }

        if ($("#user_id").val() != "") {
            $("#loginname").attr("readonly", "readonly");
            $("#loginname").css("color", "gray");
        }
    });

    //保存
    function save() {

    	
    	
        if ($("#password").val() == "") {
            layer.alert("请输入输入密码");
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
            layer.alert("两次密码不相同");

            $("#chkpwd").focus();
            return false;
        }

        if ($("#user_id").val() == "") {

        } else {
            $.ajax({
                //几个参数需要注意一下
                type: "POST",//方法类型
                url: "user/UpdatePwd.do",//url
                data: $('#userForm_add').serialize(),
                success: function (result) {
                    layer.alert("修改成功");
                    parent.closeLayer();
                },
                error: function () {
                    layer.alert("修改成功");
                    parent.closeLayer();
                }
            });

        }
    }

    function cancel() {
        parent.closeLayer();
    }

    function ismail(mail) {
        return (new RegExp(/^(?:[a-zA-Z0-9]+[_\-\+\.]?)*[a-zA-Z0-9]+@(?:([a-zA-Z0-9]+[_\-]?)*[a-zA-Z0-9]+\.)+([a-zA-Z]{2,})+$/).test(mail));
    }


</script>
</body>
</html>
