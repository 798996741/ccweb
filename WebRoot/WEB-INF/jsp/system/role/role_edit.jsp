<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <!-- jsp文件头和头部 -->

</head>
<body class="no-skin">
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
    <!-- /section:basics/sidebar -->
    <div class="main-content">
        <div class="main-content-inner">
            <div class="page-content">
                <div class="modal-header">
                    <h4 class="modal-title" id="myModalLabel" style="float: left;">编辑</h4>
                    <div class="new-tb" style="float: right;" data-dismiss="modal" title="关闭"></div>
                </div>
                <div class="row">
                    <div class="col-xs-12">
                        <form action="role/${msg}.do" name="form1" id="form1_add" method="post">
                            <input type="hidden" name="ROLE_ID" id="id" value="${pd.ROLE_ID}"/>
                            <input name="PARENT_ID" id="parent_id" value="1" type="hidden">
                            <div id="zhongxin">
                                <div class="table table-striped table-bordered table-hover">

	                                <div class="row flex-row-center-center" style="white-space: nowrap;margin: 15px">
                                        <span class="border-size" style="letter-spacing: 0.5em">类型</span>

                                         <select class="detail-seat-input auto-width-medium" style="width: 170px" id="ROLE_TYPE" name="ROLE_TYPE">
	                                        <option value="">全部</option>
	                                        <c:forEach items="${roletypeList}" var="var" varStatus="vs">
	                                            <option value="${var.DICTIONARIES_ID }"
	                                                    <c:if test="${pd.ROLE_TYPE==var.DICTIONARIES_ID}">selected</c:if>>${var.NAME }</option>
	                                        </c:forEach>
	                                    </select>
                                    </div>
									
									<div class="row flex-row-center-center" style="white-space: nowrap;margin: 15px">
                                        <span class="border-size" style="letter-spacing: 0.5em">编码</span>

                                        <input type="text" name="ROLE_CODE" id="roleCode" placeholder="这里输入编码"
                                               value="${pd.ROLE_CODE}" title="编码"
                                               class="detail-seat-input auto-width-medium" style="width: 170px"/>
                                    </div>
                                    <div class="row flex-row-center-center" style="white-space: nowrap;margin: 15px">
                                        <span class="border-size" style="letter-spacing: 0.5em">名称</span>

                                        <input type="text" name="ROLE_NAME" id="roleName" placeholder="这里输入名称"
                                               value="${pd.ROLE_NAME}" title="名称"
                                               class="detail-seat-input auto-width-medium" style="width: 170px"/>
                                    </div>
                                 
                                    <div class="new-bc">
                                        <a onclick="save();">保存</a>
                                        <a class="new-qxbut" data-btn-type="cancel" data-dismiss="modal">取消</a>
                                    </div>

                                </div>
                            </div>
                        </form>

                        <div id="zhongxin2" class="center" style="display:none"><img src="static/images/jzx.gif"
                                                                                     style="width: 50px;"/><br/><h4
                                class="lighter block green"></h4></div>

                    </div>
                    <!-- /.col -->
                </div>
                <!-- /.row -->
            </div>
            <!-- /.page-content -->
        </div>
    </div>
    <!-- /.main-content -->
</div>
<!-- /.main-container -->

<!-- 页面底部js¨ -->
<%@ include file="../../system/include/incJs_foot.jsp" %>
<script type="text/javascript">
    //top.hangge();
    //保存
    function save() {
        if ($("#roleName").val() == "") {
            $("#roleName").tips({
                side: 3,
                msg: '请输入',
                bg: '#AE81FF',
                time: 2
            });
            $("#roleName").focus();
            return false;
        }

        $.ajax({
            //几个参数需要注意一下
            type: "POST",//方法类型
            dataType: "html",//预期服务器返回的数据类型
            url: "role/${msg}.do",//url
            data: $('#form1_add').serialize(),
            success: function (result) {
                //  console.log(result.);//打印服务端返回的数据(调试用)
                if (result.indexOf("success") >= 0) {
                    document.location.reload();
                }
            },
            error: function () {
                alert("异常！");
            }
        });
        //$("#form1_add").submit();

    }
</script>
</body>
</html>

