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

<!-- /.content-wrapper -->
<!-- basic scripts -->

<div class="main-container" id="main-container">
    <!-- Horizontal Form -->
    <!-- <div class="box box-info">
        <div class="box-header with-border">
            <h3 class="box-title">用户添加</h3>
        </div> -->
    <!-- /.box-header -->
    <!-- form start -->
    <div class="main-content" >
        <div class="main-content-inner">
            <div class="page-content"  style="background-color: white;">
<%--                <div class="modal-header">--%>
<%--                    <h4 class="modal-title" id="myModalLabel" style="float: left;"></h4>--%>
<%--                    <div class="new-tb" style="float: right;" data-dismiss="modal" title="关闭"></div>--%>
<%--                </div>--%>
                <div class="row">
                    <div class="col-xs-12">
                        <form action="sendmsg/savemsg.do" name="Form_add" id="Form_add"
                              method="post">
                            <section class="content-header">
                                <div class="seat-middle-top"
                                     style="height:30px;line-height:25px;margin-top: 0px;box-shadow: 4px 4px 6px #c7c7c7;">
                                    <div class="seat-middle-top-left">
                                        <div class="seat-middle-top-left-bt">发送短信</div>
                                    </div>
                                </div>

                            </section>

                    <div id="zhongxin" style="padding: 5px;border-radius: 5px;">
                                <%--                                <div id="table_report" class="table table-striped table-bordered table-hover new-wkj">--%>
                                <div class="row" style="margin-top: 10px">
                                    <div class="col-md-12 flex-row-acenter" style="white-space: nowrap">
                                        <span class="border-size" style="width:70px;text-align-last: justify;">发送方式</span>
                                        <select class="detail-seat-select auto-width-medium" style="width: 100%"
                                                name="way" id="way">
                                            <option value="0" >单发</option>
                                        </select>
                                    </div>

                                </div>
                                <div class="row" style="margin-top: 10px">
                                    <div class="col-md-12 flex-row-acenter" style="white-space: nowrap">
                                    <span class="border-size"
                                          style="width:70px;text-align-last: justify;">短信模板</span>
                                        <select class="detail-seat-select auto-width-medium" style="width: 100%"
                                                name="temp" id="temp" onchange="ctemp();">
                                            <option value="">全部</option>
                                            <c:forEach items="${msgtemp}" var="var" varStatus="vs">
                                                <option value="${var.id }"
                                                >${var.tempname }</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                    <div class="row">
                                        <div class="col-md-12 flex-row-acenter" style="white-space: nowrap">
                                        <span class="border-size"
                                              style="width:70px;text-align-last: justify;">联系方式</span>

                                            <select class="detail-seat-select auto-width-medium" name="phone" id="phone"
                                                    maxlength="32"
                                                    style="width: 100%"/>
                                            <option value="">全部</option>
                                            <c:forEach items="${getphone}" var="var" varStatus="vs">
                                                <option value="${var.phoneid},${var.phone}"
                                                >${var.phone}/${var.name}</option>
                                            </c:forEach>
                                            </select>

                                        </div>
                                    </div>
                                <div class="row">


                                    <div class="col-md-12 flex-row-acenter" style="white-space: nowrap">
                                        <span class="border-size" style="width:70px;text-align-last: justify;">发送内容</span>
                                        <textarea name="content" id="content"
                                                  class="detail-seat-input auto-width-medium" style="width: 100%;height: 100px"> </textarea>

                                    </div>

                                </div>
<%--                                 <div class="row">--%>
<%--                                    <div class="col-md-12  flex-row-acenter" style="white-space: nowrap">--%>
<%--                                        <span class="border-size"--%>
<%--                                                                              style="width:70px;text-align-last: justify;">定时发送</span>--%>

<%--                                        <input class="form-control detail-date-picker "--%>
<%--                                               name="time"--%>
<%--                                               id="time" style="width: 100%"--%>
<%--                                               value="${pd.time}" type="text"--%>
<%--                                               data-date-format="yyyy-mm-dd hh:mm:ss"--%>
<%--                                               />--%>

<%--                                    </div>--%>
<%--                                 </div>--%>
                                <div class="new-bc" style="padding: 50px">
                                    <a onclick="save();">保存</a>
<%--                                    <a class="new-qxbut" data-btn-type="cancel" data-dismiss="modal">取消</a>--%>
                                </div>

                            </div>


                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 页面底部js¨ -->
<%@ include file="../system/include/incJs_foot.jsp" %>
<!-- 日期框 -->
    <!-- jquery.tips.js -->
<!-- 日期框 -->
<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>

<script type="text/javascript">
    //$(top.hangge());//关闭加载状态
    //检索
    function ctemp(){

        var temp=$('#temp option:selected').val();
        console.log(temp);
        <c:forEach items="${msgtemp}" var="var" varStatus="vs">

            if (temp=='${var.id}'){
                var content=`${var.content}`;
                content.replace(new RegExp("\r","gm"),"<br/>")
                $('#content').val(content);
            }
        </c:forEach>
    }

    function save(){

        console.log($('#Form_add').serialize());
        $.ajax({
            type: 'post',
            data: $('#Form_add').serialize(),
            url: '<%=basePath%>sendmsg/savemsg.do',
            cache: false,
            dataType: 'text',
            success: function (data) {
                if ("msg" != data) {
                    confirm("发送成功")
                    location.href = "<%=basePath%>sendmsg/gosendmsg.do"
                } else {
                    confirm("发送失败")
                }
            }
        })
    }


    $('.detail-date-picker').datepicker({
        autoclose: true,
        todayHighlight: true,
        clearBtn: true,
        // format: 'yyyy-mm-dd hh:ii:ss',
        // autoclose: true,
        // /* minView: "month",  *///选择日期后，不会再跳转去选择时分秒
        // language: 'zh-CN',
        // dateFormat: 'yyyy-mm-dd',//日期显示格式
        // timeFormat: 'HH:mm:ss',//时间显示格式
        // todayBtn: 1,
        // autoclose: 1,
        // minView: 0,  //0表示可以选择小时、分钟   1只可以选择小时
        // minuteStep: 1,//分钟间隔1分钟

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