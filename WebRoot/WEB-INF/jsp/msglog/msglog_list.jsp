<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <%@ include file="../system/include/incJs_mx.jsp" %>

</head>
<style>
    .seat-middle-top {
        margin-top: 0;
    }
    .table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th, .table>thead>tr>td, .table>thead>tr>th{
        padding: 0;
    }
</style>
<body class="no-skin">


<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper" style="width:100%;margin-left:0px;">
    <section class="container-fluid">
                <div class="seat-middle-top" style="box-shadow: 4px 4px 6px #c7c7c7;">
          <div class="seat-middle-top-left">
                <div class="seat-middle-top-left-bt"><a style="width: 100px">短信纪录</a></div>
              <div class="flex-row-center">
                  <div class="border-style" style="margin-right: 0px;">
                      <span class="border-size">开始日期</span>
                      <div class="border-line"></div>
                      <input style="margin:0px" class="date-picker" name="starttime" id="starttime"
                             autoComplete="off" title="开始时间"
                             placeholder="开始时间" value="${pd.starttime}" type="text"
                             data-date-format="yyyy-mm-dd"/>
                  </div>
                  <span class="date-line">—</span>
                  <div class="border-style" style="margin-left: 0px;">
                      <span class="border-size">结束日期</span>
                      <div class="border-line"></div>
                      <input style="margin:0px" class="date-picker" name="endtime" id="endtime"
                             autoComplete="off"
                             title="结束时间"
                             placeholder="结束时间" value="${pd.endtime}" type="text"
                             data-date-format="yyyy-mm-dd"/>
                  </div>

              </div>
              <div class="border-style">
                  <span class="border-size">联系方式</span>
                  <div class="border-line"></div>
                  <input class="seat-input" placeholder="联系方式" name="phone" id="phone"
                         value="${pd.phone }">
              </div>
              <button type="button" class="btn btn-default-sm button-blue width-65px"
                      style="margin: 5px 5px 5px 15px;" onclick="tosearch();">
                  <font>查&nbsp;询</font>
              </button>
            </div>

             <div class="seat-middle-top-right">
                <input placeholder="搜  索" name="keywords" id="keywords" value="${keywords }">
                <a href="javascript:void(0)">
                <img src="static/login/images/icon-search.png" onclick="tosearch()"></a>
            </div>

        </div>
        <div class="seat-middle">
            <!--  <div class="seat-middle-nr"> -->

            <div class="xtyh-middle-r zxzgl-middle-r">
                <!-- <div class="box-body" > -->

                <table id="example_dic" class="table table-bordered table-hover">
                    <thead>
                    <tr style="height: 38px !important;">

                        <th class="center" style="width:50px;padding: 0;line-height: 38px">序号</th>
                        <th class="center" style="padding: 0;line-height: 38px">发送人</th>
                        <th class="center" style="padding: 0;line-height: 38px">短信模板</th>
                        <th class="center" style="padding: 0;line-height: 38px">联系方式</th>
                        <th class="center" style="padding: 0;line-height: 38px">发送时间</th>
                        <th class="center" style="padding: 0;line-height: 38px">发送结果</th>
                        <th class="center" style="padding: 0;line-height: 38px">短信内容</th>
                        <th class="center" style="padding: 0;line-height: 38px">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <!-- 开始循环 -->
                    <c:choose>
                        <c:when test="${not empty data}">
                            <c:forEach items="${data}" var="var" varStatus="vs">
                                <tr id="tr${vs.index+1}" style="height: 35px !important;">
                                    <td class='center' style="width: 30px;">${vs.index+1}</td>
                                    <td class='center'>${var.sendman}</td>
                                    <td class='center'>${var.tempname}</td>
                                    <td class='center'>${var.phone}</td>
                                    <td class='center'>${var.sendtime}</td>
                                    <td class='center'>
                                        <c:if test="${var.state==0}">待发送</c:if>
                                        <c:if test="${var.state==1}">已发送</c:if>
                                        <c:if test="${var.state==-1}">发送失败</c:if>
                                    </td>
                                    <td class='center'
                                        style="width: 400px;max-width: 400px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">
                                        <span class="td-content" title="${var.content}"
                                              style="width: 400px;max-width: 400px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">${var.content}</span>
                                    </td>
<%--                                    <td class='center'>${var.content}</td>--%>
                                    <td class="center">
                                        <div class="flex-row-center-center">
                                            <div class="button-edit"
                                                 title="详情"
                                                 onclick="edit('${var.id}');">
                                                <font class="button-content">详情</font>
                                            </div>
<%--                                            <a class="cy_bj" title="编辑"--%>
<%--                                               onclick="edit('${var.AREA_ID}');"> <i title="编辑"></i>--%>
<%--                                            </a>--%>

                                        </div>

                                    </td>

                                </tr>

                            </c:forEach>

                        </c:when>
                        <c:otherwise>
                            <tr class="main_info">
                                <td colspan="100" class="center">没有相关数据</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>

                    </tbody>

                </table>


            </div>

        </div>
    </section>

    <c:if test="${not empty action}">
        <div style="width:100%;text-align:center">
            <a class="btn btn-mini btn-danger" id="alink" data-btn-type="cancel"
               data-dismiss="modal">确认</a>
        </div>
    </c:if>
</div>

<!-- /.content-wrapper -->
<!-- basic scripts -->
<!-- 页面底部js¨ -->
<%@ include file="../system/include/incJs_foot.jsp" %>
<!-- 日期框 -->
    <!-- jquery.tips.js -->
<!-- 日期框 -->
<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>

<script type="text/javascript">
    //$(top.hangge());//关闭加载状态
    //检索
    function tosearch() {
        var keywords = $("#keywords").val();
        var starttime = $("#starttime").val();
        var endtime = $("#endtime").val();
        var phone = $("#phone").val();
        location.href = "<%=path%>/sendmsg/gomsglist.do?keywords=" + encodeURI(encodeURI(keywords))
            +"&starttime="+encodeURI(encodeURI(starttime))
            +"&endtime="+encodeURI(encodeURI(endtime))
            +"&phone="+encodeURI(encodeURI(phone));
    }
    function edit(id) {
        // top.jzts();
        var winId = "userWin";
        modals.openWin({
            winId: winId,
            title: '编辑',
            width: '700px',
            url: '<%=basePath%>sendmsg/gomsgdatils.do?id=' + id
            /*, hideFunc:function(){
                modals.info("hide me");
            },
            showFunc:function(){
                modals.info("show me");
            } */
        });

    }
    $('.date-picker').datepicker({
        autoclose: true,
        todayHighlight: true,
        clearBtn: true
    });

</script>
<script>
    $(function () {

        $('#example_dic').DataTable({
            'paging': true,
            'lengthChange': false,
            'searching': true,
            'ordering': true,
            'info': true,
            'autoWidth': true
        })
    })
    $(function () {
        $(".row thead").find("th:first").addClass("cy_th");
        $('.seat-middle').find(".row:first").hide();

        $(".xtyh-middle-r").find(".row:last").addClass("row-zg");
        $(".xtyh-middle-r").find(".row:eq(1)").addClass("row-two");
    });

    function home() {
        parent.parent.document.getElementById("mainFrame").src = "<%=basePath%>login_default.do";
    }

</script>