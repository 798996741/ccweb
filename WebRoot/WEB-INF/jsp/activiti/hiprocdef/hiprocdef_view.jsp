<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <%@ include file="../../system/include/incJs_mx.jsp" %>
    <!-- zTreeStyle -->
    <!-- 日期框 -->
    <link rel="stylesheet" href="static/ace/css/datepicker.css"/>
    <link rel="stylesheet" href="plugins/kindeditor/themes/default/default.css"/>
    <link rel="stylesheet" href="plugins/kindeditor/plugins/code/prettify.css"/>
    <!-- 日期框 -->
    <!-- 日期框 (带小时分钟)-->
    <link rel="stylesheet" href="static/ace/css/bootstrap-datetimepicker.css"/>
    <style>
        .btable, .btable tr th, .btable tr td {
            border: 1px solid #ccc;
            padding: 5px;
        }

        .btable {
            width: 100%;
            min-height: 25px;
            line-height: 25px;
            text-align: left;
            border-collapse: collapse;
        }

        .tdclass {
            text-align: right;
        }

        .tdtitle {
            text-align: center;
            background: #dedede
        }

        body .demo-class .layui-layer-title {
            background: #4a9ed8;
            color: #fff;
            border: none;
            border-top-left-radius: 15px;
            border-top-right-radius: 15px;
        }

        body .demo-class .layui-layer-btn {
            border-top: 1px solid #E9E7E7
        }

        body .demo-class .layui-layer-btn a {
            background: #fff;
        }

        body .demo-class .layui-layer-btn .layui-layer-btn1 {
            background: #999;
        }

        body .demo-class .layui-layer-setwin {
            background: url(static/login/images/new-gb.png) no-repeat center;
            background-size: 85%;
            top: 5px !important;
            width: 35px;
            height: 35px;
        }

        body .demo-class .layui-layer-setwin a {
            background: rgba(0, 0, 0, 0);
            width: 35px;
            height: 35px;
            top: 5px;
        }
    </style>
</head>
<!-- /section:basics/navbar.layout -->
<body class="no-skin" style="background-color: #ecf0f5">
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container" style="overflow-x: auto;overflow-y: auto;height: 100%">
    <!-- /section:basics/sidebar -->
    <div class="main-content">
        <%--		<div class="main-content-inner">--%>
        <%--			<div class="page-content">--%>
        <%--			--%>
        <%--				<div class="modal-header">--%>
        <%--					<h4 class="modal-title" id="myModalLabel" style="float: left;">	</h4>--%>
        <%--					<div class="new-tb" style="float: right;" data-dismiss="modal" title="关闭"></div>--%>
        <%--				</div>--%>
        <%--				<div class="row">--%>
        <%--					<div class="col-xs-12">--%>
        <%--					<div class="span6">--%>
        <%--						<div class="tabbable">--%>
        <!-- <ul class="nav nav-tabs" id="myTab" style="margin-top: 10px;">
          <li class="active"><a data-toggle="tab" href="#home"><i class="green icon-home bigger-110"></i>申请事项</a></li>
          <li><a data-toggle="tab" href="#profile"><i class="green icon-cog bigger-110"></i>审批过程</a></li>
          <li><a data-toggle="tab" href="#png"><i class="green icon-cog bigger-110"></i>流程图</a></li>
        </ul> -->
        <div class="modal-header">
            <h4 class="modal-title" id="myModalLabel" style="float: left;"></h4>
            <div class="new-tb" style="float: right;" data-dismiss="modal" title="关闭"></div>
        </div>
        <div class="tab-content">
            <%--								<div id="home" class="tab-pane in active">--%>
            <form action="rutask/handle.do" name="Form_add" id="Form_add" method="post">
                <input type="hidden" name="ID_" id="ID_" value="${pd.ID_}"/>
                <input type="hidden" name="ASSIGNEE_" id="ASSIGNEE_" value=""/>
                <input type="hidden" name="PROC_INST_ID_" id="PROC_INST_ID_" value="${pd.PROC_INST_ID_}"/>
                <input type="hidden" name="tsdept" id="tsdept" value="${pd_s.tsdept}"/>

                <section class="content-header">
                    <div class="seat-middle-top" style="height:30px;line-height:25px;margin-top: 0px;box-shadow: 4px 4px 6px #c7c7c7;">
                        <div class="seat-middle-top-left">
                            <div class="seat-middle-top-left-bt">工单处理状况</div>
                        </div>
                    </div>
                </section>


                <div id="zhongxin" style="padding: 10px">
                    <div class="table table-striped table-bordered table-hover"
                         style="padding: 0px;width:100%;background-color: #ffffff;margin-bottom:0px;box-shadow: 4px 4px 6px #c7c7c7;border-radius: 5px;padding: 5px 0;">
                        <div class="row" style="margin-top: 10px">

                            <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                                <span class="border-size" style="text-align-last: right;display: inline-block;">投诉编号</span>
                                <select class="detail-seat-select"
                                        disabled="disabled">
                                    <option value="">${pd_s.code}</option>
                                </select>
                            </div>

                            <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                                <span class="border-size" style="text-align-last: right;display: inline-block;">投诉来源</span>
                                <select class="detail-seat-select"
                                        disabled="disabled">
                                    <option value="">${pd_s.tssourcename}</option>
                                </select>
                            </div>

                            <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                                <span class="border-size" style="text-align-last: right;display: inline-block;">投诉渠道</span>
                                <select class="detail-seat-select"
                                        disabled="disabled">

                                </select>
                            </div>

                            <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                                <span class="border-size" style="text-align-last: right;display: inline-block;">投诉日期</span>
                                <input class="detail-date-picker"
                                       value="${pd_s.czdate}" type="text" data-date-format="yyyy-mm-dd"
                                       readonly="readonly" title="投诉时间" disabled="disabled"/>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-12 flex-row-acenter" style="white-space: nowrap">
                                <span class="border-size" style="text-align-last: right;display: inline-block;">投诉诉求</span>
                                <input class="detail-seat-input" type="text"
                                       disabled="disabled">
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-12 flex-row-acenter" style="white-space: nowrap">
                                <span class="border-size" style="text-align-last: right;display: inline-block;">投诉内容</span>
                                <input class="detail-seat-input" type="text"
                                       value="${pd_s.tscont}" title="投诉内容" disabled="disabled">
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                                <span class="border-size"
                                      style="text-align-last: right;display: inline-block;text-align: center;line-height: 12px;">投诉类别<br/>(大项)</span>
                                <select class="detail-seat-select"
                                        disabled="disabled">
                                    <option value="">${pd_s.tsclassifyname}</option>
                                </select>
                            </div>

                            <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                                <span class="border-size"
                                      style="text-align-last: right;display: inline-block;text-align: center;line-height: 12px;">投诉类别<br/>(细项)</span>
                                <%--                                <span id="tstypespans"></span>--%>
                                <select class="detail-seat-select"
                                        disabled="disabled">
                                </select>
                            </div>


                            <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                                <span class="border-size" style="text-align-last: right;display: inline-block;">投诉分类</span>
                                <select class="detail-seat-select"
                                        disabled="disabled">
                                    <option value="">${pd_s.tstypename}</option>
                                </select>
                            </div>

                            <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                                <span class="border-size" style="text-align-last: right;display: inline-block;">投诉等级</span>
                                <select class="detail-seat-select"
                                        disabled="disabled">
                                    <option>${pd_s.tslevelname}</option>
                                </select>
                            </div>
                        </div>

                        <div class="row" style="margin-bottom: 10px">
                            <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                                <span class="border-size" style="text-align-last: right;display: inline-block;">投诉部门</span>
                                <input class="detail-seat-input"
                                       value="${pd_s.tsdeptname}" disabled="disabled"/>
                            </div>

                            <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                                <span class="border-size" style="text-align-last: right;display: inline-block;">是否回访</span>
                                <select class="detail-seat-select"
                                        disabled="disabled">
                                    <option value="1" <c:if test="${pd_s.ishf=='1'}">checked</c:if>>是</option
                                    <option value="0" <c:if test="${pd_s.ishf=='0' || empty pd_s.ishf}">checked</c:if>>否
                                    </option>
                                </select>
                            </div>

                            <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                                <span class="border-size" style="text-align-last: right;display: inline-block;">处理时限</span>
                                <select class="detail-seat-select"
                                        disabled="disabled">
                                    <option>${cltime}</option>
                                </select>
                            </div>

                            <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                                <span class="border-size" style="text-align-last: right;display: inline-block;">受理人</span>
                                <input class="detail-seat-input" value="${sessionUser.NAME}"
                                       disabled="disabled"/>
                            </div>
                        </div>
                    </div>
                </div>


                <section class="content-header">
                    <div class="seat-middle-top" style="height:30px;line-height:25px;margin-top: 0px;box-shadow: 4px 4px 6px #c7c7c7;box-shadow: 4px 4px 6px #c7c7c7;">
                        <div class="seat-middle-top-left">
                            <div class="seat-middle-top-left-bt">客户信息</div>
                        </div>
                    </div>
                </section>

                <div
                        class="table table-striped table-bordered table-hover"
                        style="padding: 10px;width:100%;margin-bottom:0px;">
                    <div style="background-color: #ffffff;box-shadow: 4px 4px 6px #c7c7c7;width: 100%;border-radius: 5px;padding: 5px 0;">
                        <div class="between-center" style="margin-top: 10px;">
                            <div class="flex-row-acenter" style="white-space: nowrap">
                                <span class="border-size" style="text-align-last: right;display: inline-block;">投诉人</span>
                                <input class="detail-seat-input"
                                       value="${pd_s.tsman}" type="text"
                                       title="投诉人" disabled="disabled"/>
                            </div>

                            <div class="flex-row-acenter" style="white-space: nowrap">
                                <span class="border-size" style="text-align-last: right;display: inline-block;">联系电话</span>
                                <input class="detail-seat-input" type="text"
                                       value="${pd_s.tstel}" maxlength="50"
                                       title="联系电话" disabled="disabled"/>
                            </div>

                            <div class="flex-row-acenter" style="white-space: nowrap">
                                <span class="border-size" style="text-align-last: right;display: inline-block;">邮箱</span>
                                <input class="detail-seat-input"
                                       disabled="disabled"/>
                            </div>

                            <div class="flex-row-acenter" style="white-space: nowrap">
                                <span class="border-size" style="text-align-last: right;display: inline-block;">证件类型</span>
                                <input class="detail-seat-input"
                                       disabled="disabled"/>
                            </div>

                            <div class="flex-row-acenter" style="white-space: nowrap">
                                <span class="border-size" style="text-align-last: right;display: inline-block;">证件号码</span>
                                <input class="detail-seat-input" type="text"
                                       value="${pd_s.cardid}"
                                       title="投证件号码" disabled="disabled"/>
                            </div>
                        </div>

                        <div class="row" style="margin-bottom: 10px;">
                            <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                                <span class="border-size" style="text-align-last: right;display: inline-block;">乘机时间</span>

                                <input  class="detail-date-picker"
                                       value="${pd_s.cjdate}" type="text" data-date-format="yyyy-mm-dd"
                                       readonly="readonly" title="乘机时间" disabled="disabled"/>
                            </div>

                            <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                                <span class="border-size" style="text-align-last: right;display: inline-block;">航班号/航程</span>
                                <input class="detail-seat-input" type="text"
                                       value="${pd_s.hbh}"
                                       title="航班号/航程" disabled="disabled"/>
                            </div>

                            <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                                <span class="border-size" style="text-align-last: right;display: inline-block;">出发机场</span>
                                <input class="detail-seat-input" 
                                       disabled="disabled"/>
                            </div>

                            <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                                <span class="border-size" style="text-align-last: right;display: inline-block;">目的机场</span>
                                <input class="detail-seat-input" 
                                       disabled="disabled"/>
                            </div>
                        </div>


                    </div>
                </div>

                <section class="content-header padbottom15">
                    <div class="seat-middle-top"
                         style="height:30px;line-height:25px;margin-top: 0px;box-shadow: 4px 4px 6px #c7c7c7;">
                        <div class="seat-middle-top-left">
                            <div class="seat-middle-top-left-bt">处理记录</div>
                        </div>
                    </div>
                </section>

                <div style="padding:10px;">
                    <table class="btable"
                           style="background-color:#ffffff;box-shadow: 4px 4px 6px #c7c7c7;border-radius: 5px;padding: 5px 0;">
                        <c:forEach items="${clList}" var="var" varStatus="vs">
                            <c:if test="${vs.index==0}">
                                <tr>
                                    <td class="tdtitle">处理部门</td>
                                    <td class="tdtitle">处理人</td>
                                    <td class="tdtitle">处理时间</td>
                                    <td colspan="2" class="tdtitle">处理记录</td>
                                </tr>
                            </c:if>
                            <tr>
                                <td align="center">${var.areaname}</td>
                                <td align="center">${var.clman}(${var.name})</td>
                                <td align="center">${var.cldate}</td>
                                <td colspan="2" align="center">${var.clcont}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>

                <section class="content-header padbottom15">
                    <div class="seat-middle-top" style="height:30px;line-height:25px;margin-top: 0px;box-shadow: 4px 4px 6px #c7c7c7;">
                        <div class="seat-middle-top-left">
                            <div class="seat-middle-top-left-bt">工单处理</div>
                        </div>
                    </div>
                </section>

                <div style="padding: 10px">
                    <div style="background-color: #ffffff;box-shadow: 4px 4px 6px #c7c7c7;border-radius: 5px;padding: 5px 0;">
                        <div class="row">
                            <c:if test="${empty clList || not empty pd.endreason}">
                                <div class="col-md-12 flex-row-acenter" style="white-space: nowrap;margin-top: 10px">
                                    <span class="border-size" style="text-align-last: right;display: inline-block;">快速处理</span>
                                    <textarea class="detail-seat-textarea"
                                              name="endreason" id="endreason"
                                              title="快速处理">${pd.endreason}</textarea>
                                </div>
                            </c:if>
                        </div>
                        <div class="row flex-row-center-center" style="margin-bottom: 10px">
                            <div class="col-md-3 between-center  " style="white-space: nowrap">
                                <span class="border-size" style="text-align-last: right;display: inline-block;">附件上传</span>
<%--                                <div class="detail-seat-input"--%>
<%--                                     style="border: 0px solid rgba(0,0,0,0)">--%>
<%--                                    &nbsp;&nbsp;<a onclick="uploadfile('${pd_s.uid}','4');">查看</a>--%>
<%--                                </div>--%>
                                <div class="content-look flex-row-acenter" title="查看"
                                     onclick="uploadfile('${pd_s.uid}','4');">
                                    <img src="static/img/workorder/look.png" width="20px"
                                         height="20px"/>
                                    <font class="content-size">查看</font>
                                </div>
                            </div>

                            <div class="col-md-9 flex-row-acenter" style="white-space: nowrap">

                            </div>
                        </div>


                    </div>
                </div>


                <div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img
                        src="static/images/jiazai.gif"/><br/><h4 class="lighter block green">提交中...</h4></div>
            </form>

            <%--								</div>--%>


            <div id="profile" class="tab-pane">
                <table class="table table-striped table-bordered table-hover"
                       style="margin-top: 10px;">
                    <tr>
                        <th colspan="10" class='center'>审批过程</th>
                    </tr>
                    <tr>
                        <th class="center" style="width:50px;">步骤</th>
                        <th class="center" style="width:150px;">任务节点</th>
                        <th class="center" style="width:150px;">办理人</th>
                        <th class="center" style="width:150px;"><i
                                class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>审批开始时间
                        </th>
                        <th class="center" style="width:150px;"><i
                                class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>审批结束时间
                        </th>
                        <th class="center" style="width:120px;">用时</th>
                        <th class="center">审批意见</th>
                    </tr>
                    <c:forEach items="${hitaskList}" var="var" varStatus="vs">
                        <tr>
                            <td class='center' style="width: 30px;">${vs.index+1}</td>
                            <td class='center' style="padding-top: 10px;">${var.ACT_NAME_}</td>
                            <td class='center' style="padding-top: 10px;">
                                <c:if test="${var.ASSIGNEE_ != NULL}"><a onclick="viewUser('${var.ASSIGNEE_}')"
                                                                         style="cursor:pointer;"><i id="nav-search-icon"
                                                                                                    class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i>${var.ASSIGNEE_}
                                </a></c:if>
                            </td>
                            <td class='center' style="padding-top: 10px;">${fn:substring(var.START_TIME_ ,0,19)}</td>
                            <td class='center' style="padding-top: 10px;">
                                <c:if test="${var.END_TIME_ == NULL}">正在审批……</c:if>
                                <c:if test="${var.END_TIME_ != NULL}">${fn:substring(var.END_TIME_ ,0,19)}</c:if>
                            </td>
                            <td class='center'>${var.ZTIME}</td>
                            <td style="padding-top: 10px;" class="center">
                                <c:forEach items="${fn:split(var.TEXT_,',fh,')}" var="val" varStatus="dvs">
                                <c:if test="${dvs.index == 0 }">${val}</c:if>
                                <c:if test="${dvs.index == 1 }">
                                <a onclick="details('d${vs.index+1}')" style="cursor:pointer;" title="详情"><i
                                        id="nav-search-icon"
                                        class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i>
                                    </c:if>
                                    </c:forEach>
                                    <textarea id="d${vs.index+1}" style="display: none;">${var.TEXT_}</textarea>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <div id="png" class="tab-pane">
                <table class="table table-striped table-bordered table-hover"
                       style="margin-top: 10px;">
                    <tr>
                        <th colspan="10" class='center'>流程图</th>
                    </tr>
                    <tr>
                        <td class='center'><img alt="${pd.FILENAME }" src="${pd.imgSrc }"></td>
                    </tr>
                    <tr>
                        <td class='center'><p class="text-warning bigger-110 orange" style="padding-top: 5px;">
                            如果图片显示不全,点击最大化按钮</p></td>
                    </tr>
                </table>
            </div>
        </div>
        <%--						</div>--%>
        <%--					</div>--%>
        <%--					</div>--%>
        <%--					<!-- /.col -->--%>
        <%--				</div>--%>
        <!-- /.row -->
        <%--			</div>--%>
        <!-- /.page-content -->
        <%--		</div>--%>
    </div>
    <!-- /.main-content -->
</div>

<!-- /.main-container -->


<!-- 页面底部js¨ -->
<!-- 百度富文本编辑框-->
<script type="text/javascript" charset="utf-8">window.UEDITOR_HOME_URL = "<%=path%>/plugins/ueditor_full/";</script>
<script type="text/javascript" charset="utf-8" src="plugins/ueditor_full/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="plugins/ueditor_full/ueditor.all.min.js"></script>
<script type="text/javascript" charset="utf-8" src="plugins/ueditor_full/lang/zh-cn/zh-cn.js"></script>


<!-- 百度富文本编辑框-->
<!-- 确认窗口 -->
<script src="static/ace/js/bootbox.js"></script>
<!-- 日期框(带小时分钟) -->
<script src="static/ace/js/date-time/moment.js"></script>
<script src="static/ace/js/date-time/locales.js"></script>
<script src="static/ace/js/date-time/bootstrap-datetimepicker.js"></script>
<!-- jquery.tips.js -->
<!-- ace scripts -->
<script src="static/ace/js/ace/ace.js"></script>
<!-- 下拉框 -->
<script src="static/ace/js/chosen.jquery.js"></script>
<!-- jquery.tips.js -->
<script type="text/javascript">


    //文件上传查看
    function uploadfile(uid, type) {
        //alert(uid);
        var winId = "userWin";
        //var type='${pd.type}';
        filelayer = layer.open({
            type: 2,
            title: "文件上传",
            shade: 0.5,
            skin: 'demo-class',
            area: ['90%', '100%'],
            content: "<%=basePath%>workorder/filelist.do?doaction=" + type + "&uid=" + uid
        });

    }

    var timer = setInterval(function () {
        getCltime();
    }, 1000);

    function getCltime() {
        var xclsj = '${xclsj}';
        if ('${xclsj}' != "") {
            $.ajax({
                //几个参数需要注意一下
                type: "POST",//方法类型
                dataType: "html",//预期服务器返回的数据类型
                url: "rutask/getCltime.do?xclsj=" + xclsj,//url
                success: function (result) {
                    result = result.replace("day", "天").replace("hour", "时").replace("min", "分").replace("sec", "秒");
                    $("#xclsj").html(result);
                }
            });
        } else {

        }
    }

    //办理任务
    function handle(msg) {
        var tsdept = '${pd_s.tsdept }';
        var dept = tsdept.split(",");
        if (dept.length == 1) {
            msg = "yes";
        } else {
            msg = "no";
        }
        //$("#msg").val(msg);
        //$("#OPINION").val(getContent());
        if ($("#OPINION").val() == "") {
            $("#omsg").tips({
                side: 3,
                msg: '请输入审批意见',
                bg: '#AE81FF',
                time: 2
            });
            $("#OPINION").focus();
            return false;
        }
        $("#Form_add").submit();
        $("#zhongxin").hide();
        $("#zhongxin2").show();
    }

    //作废
    function del(Id) {
        bootbox.prompt("请输入作废缘由?", function (result) {
            if (result != null) {
                if ('' == result) result = "未写作废缘由";
                top.jzts();
                var url = "<%=basePath%>ruprocdef/delete.do?PROC_INST_ID_=" + Id + "&reason=" + encodeURI(encodeURI(result)) + "&tm=" + new Date().getTime();
                $.get(url, function (data) {
                    $("#zhongxin").hide();
                    $("#zhongxin2").show();
                    top.Dialog.close();
                });
            }
        });
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
        top.jzts();
        var diag = new top.Dialog();
        diag.Modal = false;				//有无遮罩窗口
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

    //审批意见详情页
    function details(htmlId) {
        var content = $("#" + htmlId).val().split(',fh,');
        top.handleDetails(content[1]);
        top.jzts();
        var diag = new top.Dialog();
        diag.Modal = false;			//有无遮罩窗口
        diag.Drag = true;
        diag.Title = "审批意见";
        diag.ShowMaxButton = true;	//最大化按钮
        diag.ShowMinButton = true;		//最小化按钮
        diag.URL = '<%=basePath%>rutask/details.do';
        diag.Width = 760;
        diag.Height = 500;
        diag.CancelEvent = function () { //关闭事件
            diag.close();
        };
        diag.show();
    }

    //选择办理人
    function getUser() {
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag = true;
        diag.Title = "选择办理人";
        diag.URL = '<%=basePath%>user/listUsersForWindow.do';
        diag.Width = 700;
        diag.Height = 545;
        diag.Modal = true;				//有无遮罩窗口
        diag.ShowMaxButton = true;	//最大化按钮
        diag.ShowMinButton = true;		//最小化按钮
        diag.CancelEvent = function () { //关闭事件
            var USERNAME = diag.innerFrame.contentWindow.document.getElementById('USERNAME').value;
            if ("" != USERNAME) {
                $("#ASSIGNEE_").val(USERNAME);
                $("#ASSIGNEE_2").val(USERNAME);
            }
            diag.close();
        };
        diag.show();
    }

    //选择角色
    function getRole() {
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag = true;
        diag.Title = "选择角色";
        diag.URL = '<%=basePath%>role/roleListWindow.do?ROLE_ID=1';
        diag.Width = 700;
        diag.Height = 545;
        diag.Modal = true;				//有无遮罩窗口
        diag.ShowMaxButton = true;	//最大化按钮
        diag.ShowMinButton = true;		//最小化按钮
        diag.CancelEvent = function () { //关闭事件
            var RNUMBER = diag.innerFrame.contentWindow.document.getElementById('RNUMBER').value;
            if ("" != RNUMBER) {
                $("#ASSIGNEE_").val(RNUMBER);
                $("#ASSIGNEE_2").val(RNUMBER);
            }
            diag.close();
        };
        diag.show();
    }

    //清空下一任务对象
    function clean() {
        $("#ASSIGNEE_").val("");
        $("#ASSIGNEE_2").val("");
    }

</script>
<c:if test="${null == pd.msg or pd.msg != 'admin' }">
    <script type="text/javascript">
        //百度富文本
        setTimeout("ueditor()", 500);

        function ueditor() {
            //UE.getEditor('editor');
        }

        //ueditor有标签文本
        function getContent() {
            var arr = [];
            arr.push(UE.getEditor('editor').getContent());
            return arr.join("");
        }
    </script>
</c:if>
</body>
</html>