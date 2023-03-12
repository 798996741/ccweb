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
<body class="no-skin" style="overflow:scroll;">


<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper" style="width:100%;margin-left:0px;">

<form action="<%=path%>/fhlog/operatelog_list.do" method="post" name="userForm" id="userForm">
    <section class="container-fluid">
        <div class="seat-middle-top-x"></div>
        <div class="seat-middle-top" id="autoheight"
             style="height: 60px;line-height: 25px;box-shadow: 4px 4px 6px #c7c7c7;">
            <div class="seat-middle-top-left">
                <div class="seat-middle-top-left-tp menu-top">
                         
                     <div class="flex-row-between" style="position:relative;">
                         <div class="flex-row-wrap">
                             <div class="border-style">
                                 <span class="border-size">模块名称</span>
                                 <div class="border-line"></div>
                                 <select class="seat-select" id="modulename" name="modulename">
                                     <option value="">全部</option>
                                     <c:forEach items="${list}" var="var" varStatus="vs">
                                         <option value="${var.modulename }" <c:if test="${pd.modulename==var.modulename}">selected</c:if>>${var.modulename }</option>
                                     </c:forEach>
                                 </select>
                             </div>
                             <div class="flex-row-center">
                                 <div class="border-style" style="margin-right: 0px;">
                                     <span class="border-size">开始日期</span>
                                     <div class="border-line"></div>
                                     <input style="margin:0px" class="date-picker" name="lastStart" id="lastStart"
                                            autoComplete="off" title="开始时间"
                                            placeholder="开始时间" value="${pd.lastStart}" type="text"
                                            data-date-format="yyyy-mm-dd"/>
                                 </div>
                                 <span class="date-line">—</span>
                                 <div class="border-style" style="margin-left: 0px;">
                                     <span class="border-size">结束日期</span>
                                     <div class="border-line"></div>
                                     <input style="margin:0px" class="date-picker" name="lastEnd" id="lastEnd"
                                            autoComplete="off"
                                            title="结束时间"
                                            placeholder="结束时间" value="${pd.lastEnd}" type="text"
                                            data-date-format="yyyy-mm-dd"/>
                                 </div>
                             </div>
                             <div class="border-style">
                                 <span class="border-size">关键字</span>
                                 <div class="border-line"></div>
                                 <input class="seat-input" placeholder="关键字" name="keywords" id="keywords"
                                        value="${pd.keywords }">
                             </div>
                             
                             <button type="button" class="btn btn-default-sm button-blue width-65px"
                                     style="margin: 5px 5px 5px 15px;" onclick="search();">
                                 <font>查&nbsp;询</font>
                             </button>
                         </div>
                         
                     </div>
                </div>
            </div>
        </div>
        <div class="seat-middle">
            <!--  <div class="seat-middle-nr"> -->

            <div class="xtyh-middle-r zxzgl-middle-r">
                <!-- <div class="box-body" > -->

                <table id="example2" class="table table-bordered table-hover">
                     <thead>
                 <tr>
                  	
					<th class="center" style="width:50px;">序号</th>
					<th class="center">操作人</th>
					<th class="center">操作模块</th>
					
					<th class="center">操作类型</th>
					<th class="center">IP</th>
					<th class="center">操作内容</th>
					<th class="center">操作时间</th>
                </tr>
                </thead>
                <tbody>
                
                <c:choose>
						<c:when test="${not empty varList}">
							<c:forEach items="${varList}" var="var" varStatus="vs">
								<tr>
									
									<td class='center' style="width: 30px;">${vs.index+1}</td>
									<td class='center' style="width: 200px;">${var.name}</td>
									<td class='center'>${var.modulename}</td>
									<td class='center'>
										${var.remarks }
									</td>
									<td class='center'>${var.ip}</td>
									<td class='center' style="min-width:150px;max-width:150px;word-wrap:break-word;word-break:break-all;width:150px;word-break:break-all;white-space: nowrap;overflow: hidden;text-overflow: ellipsis" title="${var.operatestr}">${var.operatestr}</td>
									<td class='center' style="width: 150px;">${var.operatedate}</td>
									
								</tr>
							
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr class="main_info">
								<td colspan="100" class="center" >没有相关数据</td>
							</tr>
						</c:otherwise>
					</c:choose>
                
                </tbody>
               

                </table>
				<div class="page-header position-relative">
					<table style="width:100%;">
						<tr>
							<td style="vertical-align:top;">
								
							</td>
							<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;padding-right: 10px;margin-top: 0px;">${page.pageStr}</div></td>
						</tr>
					</table>
				</div>

            </div>
        </div>
    </section>
    </form>
</div>
<!-- /.content-wrapper -->

<!-- basic scripts -->
<!-- 页面底部js¨ -->
<%@ include file="../../system/include/incJs_foot.jsp" %>
<!-- 日期框 -->
    <!-- jquery.tips.js -->
<script type="text/javascript">
    function tosearch() {
        //top.jzts();

        var keywords = $("#keywords").val();
        location.href = "<%=path%>/fhlog/list.do?keywords=" + encodeURI(encodeURI(keywords));
        //$("#Form_s").submit();
    }
    
    $('.date-picker').datepicker({
        autoclose: true,
        todayHighlight: true,
        clearBtn: true
    });

    $(function () {

       /*  $('#example2').DataTable({
            'paging': true,
            'lengthChange': false,
            'searching': false,
            'ordering': true,
            'info': true,
            'autoWidth': true

        }) */
    })

    function home() {
        parent.document.getElementById("mainFrame").src = "<%=basePath%>login_default.do";
    }

    //$(top.hangge());

    //检索
    function search() {
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

</script>
</body>
</html>
