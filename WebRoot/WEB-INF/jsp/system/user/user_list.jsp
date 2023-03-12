<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.fh.util.Jurisdiction" %>
<%
    Jurisdiction jurisdiction = new Jurisdiction();
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <!-- 下拉框 -->
    <!-- jsp文件头和头部 --><%--
<%@ include file="../index/top.jsp"%> --%>
    <%@ include file="../../system/include/incJs_mx.jsp" %>

    <link rel="stylesheet" href="static/login/css/common.css">
    <link rel="stylesheet" href="static/login/css/main.css">

    <!-- 日期框 -->
</head>
<body class="no-skin">


<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper" style="width:100%;margin-left:0px;">


    <section class="container-fluid">
                <div class="seat-middle-top" style="box-shadow: 4px 4px 6px #c7c7c7;">
          <div class="seat-middle-top-left">
                <div class="seat-middle-top-left-bt">系统用户</div>
                <div class="seat-button">
                    <%if (jurisdiction.hasQx("990101")) { %>
                    <a href="javascript:void (0)" onclick="add();">新增</a>
                    <%} %>
                </div>
                <div class="seat-button" style="margin-left: 20px">
                    <%if (jurisdiction.hasQx("990101")) { %>
                    <a  style="width: 130px" href="javascript:void (0)" onclick="adduser();">同步微信用户</a>
                    <%} %>
                </div>
            </div>
            <div class="seat-middle-top-right">
                <input placeholder="搜  索" name="keywords" id="keywords" value="${pd.keywords }">
                <a href="javascript:void(0)">
                    <img src="static/login/images/icon-search.png" onclick="tosearch()"></a>
                     <a href="javascript:void(0)">
                    <img src="static/login/images/icon-clean.png" style="width:20px;margin-top:3px;margin-right:5px" onclick="toclean()"></a>
            </div>
        </div>
        <div class="seat-middle">
            <!--  <div class="seat-middle-nr"> -->

            <div class="xtyh-middle-r zxzgl-middle-r">
                <!-- <div class="box-body" > -->

                <table id="example2" class="table table-bordered table-hover">
                    <thead>
                    <tr>

                        <th class="center cy_th">编号</th>
                        <th id='cy_thk'></th>
                        <th class="center">所属单位</th>
                        <th class="center">用户名</th>
                        <th class="center">姓名</th>
                        <th class="center">角色</th>
                        <th class="center"><i class="ace-icon fa fa-envelope-o"></i>邮箱</th>
                        <th class="center"><i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>最近登录</th>
                        <th class="center">上次登录IP</th>
                        <th class="center">关联微信用户</th>
                        <th class="center">操作</th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:choose>
                        <c:when test="${not empty userList}">
                            <c:forEach items="${userList}" var="user" varStatus="vs">

                                <tr>
                                    <td class='center cy_td'>${user.NUM }</td>
                                    <td id='cy_thk'></td>
                                    <td class="center">${user.AREA_NAME }</td>
                                    <td class="center"><a onclick="viewUser('${user.USERNAME}')"
                                                          style="cursor:pointer;">${user.USERNAME }</a></td>
                                    <td class="center">${user.NAME }</td>
                                    <td class="center">${user.ROLE_NAME }</td>
                                    <td class="center"><a title="发送电子邮件" style="text-decoration:none;cursor:pointer;"
                                                          <c:if test="${QX.email == 1 }">onclick="sendEmail('${user.EMAIL }');"</c:if>>${user.EMAIL }&nbsp;<i
                                            class="ace-icon fa fa-envelope-o"></i></a></td>
                                    <td class="center">${user.LAST_LOGIN}</td>
                                    <td class="center">${user.IP}</td>
                                    <td class="center">${user.wxid}</td>
                                    <td class="center">

                                        <div class="flex-position">
                                            <div class="flex-row">
                                                <%if (jurisdiction.hasQx("990102")) { %>
<%--                                                <div class="button-edit" title="编辑"--%>
<%--                                                     onclick="editUser('${user.USER_ID}');" title="编辑">--%>
<%--                                                    <font class="button-content">编辑</font>--%>
<%--                                                </div>--%>
                                                <div class="content-edit home-img cy_bj" title="编辑"
                                                     onclick="editUser('${user.USER_ID}');">
                                                    <font class="home-img-size">编辑</font>
                                                </div>
                                                    <%--										<a class="cy_bj" title="编辑" onclick="editUser('${user.USER_ID}');">--%>
                                                    <%--											<i title="编辑"></i>--%>
                                                    <%--										</a>--%>
                                                <%} %>&nbsp;&nbsp;&nbsp;&nbsp;
                                                <%if (jurisdiction.hasQx("990103")) { %>
<%--                                                <div class="button-delete" style="margin-left:10px;"--%>
<%--                                                     title="删除"--%>
<%--                                                     onclick="delUser('${user.USER_ID }','${user.USERNAME }');"--%>
<%--                                                     title="删除">--%>
<%--                                                    <font class="button-content">删除</font>--%>
<%--                                                </div>--%>
                                                <div class="content-delete home-img cy_bj" title="删除"
                                                     onclick="delUser('${user.USER_ID }','${user.USERNAME }');"
                                                     style="margin-right: 10%">
                                                    <font class="home-img-size">删除</font>
                                                </div>
                                                    <%--										<a class="cy_sc"  style="margin-left:10px" onclick="delUser('${user.USER_ID }','${user.USERNAME }');">--%>
                                                    <%--											<i  title="删除"></i>--%>
                                                    <%--										</a>--%>
                                                <%} %>
                                            </div>
                                        </div>

                                    </td>
                                </tr>

                            </c:forEach>

                        </c:when>
                        <c:otherwise>
                            <tr class="main_info">
                                <td colspan="10" class="center">没有相关数据</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>

                    </tbody>

                </table>


            </div>
        </div>
    </section>

    <!-- /.content -->
</div>
<!-- /.content-wrapper -->

<!-- basic scripts -->
<!-- 页面底部js¨ -->
<%@ include file="../../system/include/incJs_foot.jsp" %>
<!-- 日期框 -->
    <!-- jquery.tips.js -->
<script type="text/javascript">

    function adduser() {

        $.ajax({
            type: "get",
            url: '<%=basePath%>user/addweixinuser',
            dataType: 'json',
            //beforeSend: validateData,
            cache: false,
            success: function (data) {
                console.log(data);
                if (data.success) {
                    alert("同步成功");
                } else {
                    alert("同步失败");
                }
            }
        });

    }

    function tosearch() {
        //top.jzts();

        var keywords = $("#keywords").val();
        location.href = "<%=path%>/user/listUsers.do?keywords=" + encodeURI(encodeURI(keywords));
        //$("#Form_s").submit();
    }
    function toclean() {
        $("#keywords").val('');
        var keywords ="";
        location.href = "<%=path%>/user/listUsers.do?keywords=" + encodeURI(encodeURI(keywords));
    }

    $(function () {

        $('#example2').DataTable({
            'paging': true,
            'lengthChange': false,
            'searching': false,
            'ordering': true,
            'info': true,
            'autoWidth': true

        })
    })

    function home() {
        parent.document.getElementById("mainFrame").src = "<%=basePath%>login_default.do";
    }

    //$(top.hangge());

    //检索
    function searchs() {
        //top.jzts();
        //alert($("#lastLoginStart").val());
        $("#userForm").submit();
    }

    //删除
    function delUser(userId, msg) {
        if (confirm("确定要删除[" + msg + "]吗?")) {

            var url = "<%=basePath%>user/deleteU.do?USER_ID=" + userId + "&USERNAME=" + msg + "&tm=" + new Date().getTime();
            $.get(url, function (data) {
                //nextPage(${page.currentPage});
                if (data == "error") {
                    modals.info("已关联坐席用户，请先删除坐席用户！");
                } else {
                    setTimeout("self.location.reload()", 100);
                }
            });
        }
    }

    //新增
    function add() {
        //top.jzts();
        var winId = "userWin";
        modals.openWin({
            winId: winId,
            title: '新增',
            width: '700px',
            url: '<%=basePath%>user/goAddU.do'
            /*, hideFunc:function(){
                modals.info("hide me");
            },
            showFunc:function(){
                modals.info("show me");
            } */
        });

    }

    //修改
    function editUser(user_id) {
        // top.jzts();
        var winId = "userWin";
        modals.openWin({
            winId: winId,
            title: '编辑',
            width: '700px',
            url: '<%=basePath%>user/goEditU.do?USER_ID=' + user_id
            /*, hideFunc:function(){
                modals.info("hide me");
            },
            showFunc:function(){
                modals.info("show me");
            } */
        });

    }


    //去发送短信页面
    function sendSms(phone) {
        //top.jzts();
        var diag = new top.Dialog();
        diag.Drag = true;
        diag.Title = "发送短信";
        diag.URL = '<%=basePath%>head/goSendSms.do?PHONE=' + phone + '&msg=appuser';
        diag.Width = 600;
        diag.Height = 265;
        diag.CancelEvent = function () { //关闭事件
            diag.close();
        };
        diag.show();
    }

    //去发送电子邮件页面
    function sendEmail(EMAIL) {
        // top.jzts();
        var diag = new top.Dialog();
        diag.Drag = true;
        diag.Title = "发送电子邮件";
        diag.URL = '<%=basePath%>head/goSendEmail.do?EMAIL=' + EMAIL;
        diag.Width = 660;
        diag.Height = 486;
        diag.CancelEvent = function () { //关闭事件
            diag.close();
        };
        diag.show();
    }

    //发站内信
    function sendFhsms(username) {
        //top.jzts();
        var diag = new top.Dialog();
        diag.Drag = true;
        diag.Title = "站内信";
        diag.URL = '<%=basePath%>fhsms/goAdd.do?username=' + username;
        diag.Width = 660;
        diag.Height = 444;
        diag.CancelEvent = function () { //关闭事件
            diag.close();
        };
        diag.show();
    }

    $(function () {

    });

    //导出excel
    function toExcel() {
        var keywords = $("#nav-search-input").val();
        var lastLoginStart = $("#lastLoginStart").val();
        var lastLoginEnd = $("#lastLoginEnd").val();
        var ROLE_ID = $("#role_id").val();
        window.location.href = '<%=basePath%>user/excel.do?keywords=' + keywords + '&lastLoginStart=' + lastLoginStart + '&lastLoginEnd=' + lastLoginEnd + '&ROLE_ID=' + ROLE_ID;
    }

    //打开上传excel页面
    function fromExcel() {
        //top.jzts();
        var diag = new top.Dialog();
        diag.Drag = true;
        diag.Title = "EXCEL 导入到数据库";
        diag.URL = '<%=basePath%>user/goUploadExcel.do';
        diag.Width = 300;
        diag.Height = 150;
        diag.CancelEvent = function () { //关闭事件
            if (diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none') {
                if ('${page.currentPage}' == '0') {
                    // top.jzts();
                    setTimeout("self.location.reload()", 100);
                } else {
                    nextPage(${page.currentPage});
                }
            }
            diag.close();
        };
        diag.show();
    }

    //查看用户
    function viewUser(USERNAME) {
        if ('admin' == USERNAME) {
            bootbox.dialog({
                message: "<span class='bigger-110'>不能查看admin用户!</span>",
                buttons:
                    {"button": {"label": "确定", "className": "btn-sm btn-success"}}
            });
            return;
        }
        // top.jzts();
        var diag = new top.Dialog();
        diag.Drag = true;
        diag.Title = "资料";
        diag.URL = '<%=basePath%>user/view.do?USERNAME=' + USERNAME;
        diag.Width = 469;
        diag.Height = 380;
        diag.CancelEvent = function () { //关闭事件
            diag.close();
        };
        diag.show();
    }

</script>
</body>
</html>
