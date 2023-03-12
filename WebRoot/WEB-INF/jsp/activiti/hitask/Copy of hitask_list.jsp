<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <!-- 下拉框 -->
    <!-- jsp文件头和头部 -->
    <%--
    <%@ include file="../index/top.jsp"%> --%>
    <%@ include file="../../system/include/incJs_mx.jsp" %>


    <style>
        @media screen and (max-width: 1700px) {
            .xtyh-middle-r {
                width: 100% !important;
            }
        }

        .zxzgl-middle-r {
            width: 100% !important;
        }
    </style>
</head>
<body class="no-skin">


<div class="content-wrapper" style="width:100%;margin-left:0px;">


    <section class="container-fluid">
        <%--		表格--%>
        <div class="seat-middle-top">
            <div class="seat-middle-top-left-bt">工单已办</div>
            <form action="hitask/list.do" method="post" name="Form" id="Form">
                <div class="row">
                    <div class="col-md-3">
                        <span class="border-size">工单来源</span>
                        <select class="detail-seat-select" style="width: 170px" id="tssources" name="tssources">
                            <option value="">全部</option>
                            <c:forEach items="${tssourceList}" var="var" varStatus="vs">
                                <option value="${var.DICTIONARIES_ID }"
                                        <c:if test="${pd.tssource==var.DICTIONARIES_ID}">selected</c:if>>${var.NAME }</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-md-3">
                        <span class="border-size">开始日期</span>
                        <input style="margin:0px" class="detail-date-picker" name="starttime" id="starttime"
                               autoComplete="off" title="开始时间"
                               placeholder="开始时间" value="${pd.starttime}" type="text"
                               data-date-format="yyyy-mm-dd"/>
                    </div>

                    <div class="col-md-3">
                        <span class="border-size">结束日期</span>
                        <input style="margin:0px" class="detail-date-picker" name="endtime" id="endtime"
                               autoComplete="off"
                               title="开始时间"
                               placeholder="结束时间" value="${pd.endtime}" type="text"
                               data-date-format="yyyy-mm-dd"/>
                    </div>

                    <div class="col-md-3">
                        <span class="border-size">投诉人</span>
                        &nbsp;<input class="detail-seat-input" placeholder="投诉人" name="tsmans" id="tsmans"
                                     value="${pd.tsman }">
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-3">
                        <span class="border-size">投诉部门</span>
                        <select class="detail-seat-select" id="tsdepts" name="tsdepts">
                            <option value="">全部</option>
                            <c:forEach items="${tsdeptList}" var="var" varStatus="vs">
                                <option value="${var.DICTIONARIES_ID }"
                                        <c:if test="${pd.tsdept==var.DICTIONARIES_ID}">selected</c:if>>${var.NAME }</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-md-3">
                        <span class="border-size">工单状态</span>
                        <select class="detail-seat-select" id="types" name="types">
                            <option value="">全部</option>
                            <option value="0" <c:if test="${pd.type=='0'}">selected</c:if>>待反馈</option>
                            <option value="1" <c:if test="${pd.type=='1'}">selected</c:if>>待确认</option>
                            <option value="2" <c:if test="${pd.type=='2'}">selected</c:if>>工单已分派</option>
                            <option value="3" <c:if test="${pd.type=='3'}">selected</c:if>>正常为您处理</option>
                            <option value="4" <c:if test="${pd.type=='4'}">selected</c:if>>工单已关闭</option>
                        </select>
                    </div>
                    <input type="hidden" name="tstypes" id="tstypes" value="${pd.tstypes}" maxlength="30"/>

                    <div class="col-md-3">
                        <span class="border-size">投诉类别</span>
                        &nbsp;<input class="detail-seat-input" type="text" name="tstypes_name"
                                     onclick="showMenus();return false" id="tstypes_name"
                                     value="${pd.tstypes_name}"
                                     value="${pd.tstypesname}" maxlength="30" placeholder="选择" readonly/>
                    </div>
				</div>
                    <div id="menuContents" class="menuContent"
                         style="position: absolute;display:none;z-index:9999;left:655px">
                        <ul id="treeDemos" class="ztree" style="margin-top:0; width:160px;"></ul>
                    </div>
                    <input type="button" style="width:60px" class="btn btn-mini btn-success"
                           onclick="search();"
                           value="查询">
                </div>

            </form>

        </div>
        <%--		列表--%>
        <div class="seat-middle">
            <!--  <div class="seat-middle-nr"> -->

            <div class="xtyh-middle-r zxzgl-middle-r">
                <!-- <div class="box-body" > -->
                <table id="example2" class="table table-striped table-bordered table-hover" style="margin-top:5px;">
                    <thead>
                    <tr>
                        <th class="center cy_th" style="width:30px;">投诉编号</th>
                        <th id='cy_thk'></th>
                        <th class="center">日期</th>
                        <th class="center">投诉来源</th>

                        <th class="center">投诉人</th>
                        <th class="center">联系方式</th>
                        <th class="center">投诉内容</th>
                        <th class="center">投诉等级</th>
                        <th class="center">投诉部门</th>
                        <!-- <th class="center">投诉类别（大项）</th> -->
                        <th class="center">投诉类别（细项）</th>
                        <th class="center">投诉分类</th>
                        <th class="center">受理人</th>
                        <th class="center">顾客回访情况</th>
                        <th class="center">结束时间</th>
                        <th class="center">处理节点</th>
                        <th class="center">工单状态</th>
                        <th class="center"> 结束原因</th>
                        <th class="center">操作</th>
                    </tr>
                    </thead>

                    <tbody>
                    <!-- 开始循环 -->
                    <c:choose>
                        <c:when test="${not empty varList}">
                            <c:forEach items="${varList}" var="var" varStatus="vs">
                                <tr>
                                    <td class='center' style="width: 30px;">${vs.index+1}</td>
                                    <td class='center'>${var.PNAME_}</td>
                                    <td class='center'><a onclick="viewUser('${var.INITATOR}')"
                                                          style="cursor:pointer;"><i
                                            id="nav-search-icon"
                                            class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i>${var.INITATOR}
                                    </a></td>
                                    <td class='center'><a onclick="viewUser('${var.ASSIGNEE_}')"
                                                          style="cursor:pointer;"><i id="nav-search-icon"
                                                                                     class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i>${var.ASSIGNEE_}
                                    </a></td>
                                    <td class='center'>${var.NAME_}</td>
                                    <td class='center'>${var.START_TIME_}</td>
                                    <td class='center'>${var.END_TIME_}</td>
                                    <td class='center'>${var.ZTIME}</td>
                                    <td class="center">
                                        <c:if test="${QX.edit != 1 && QX.del != 1 }">
                                            <span class="label label-large label-grey arrowed-in-right arrowed-in"><i
                                                    class="ace-icon fa fa-lock" title="无权限"></i></span>
                                        </c:if>
                                        <div class="hidden-sm hidden-xs btn-group">


                                            <a class="cy_bj" title="流程信息"
                                               onclick="view('${var.PROC_INST_ID_}','${var.DEPLOYMENT_ID_ }','${var.DGRM_RESOURCE_NAME_}');">
                                                <i title="流程信息"></i>
                                            </a>
                                        </div>

                                    </td>
                                </tr>
                            </c:forEach>

                        </c:when>
                        <c:otherwise>
                            <tr class="main_info">
                                <td colspan="100" class="center">没有已经办理的任务</td>
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
                            <td style="vertical-align:top;">
                                <div class="pagination"
                                     style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
                            </td>
                        </tr>
                    </table>
                </div>
                </form>

            </div>
            <!-- /.col -->
        </div>
        <!-- /.row -->
        <!-- /.page-content -->
        <!-- /.main-content -->

        <!-- 返回顶部 -->
        <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
            <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
        </a>

    </section>
    <!-- /.main-container -->
</div>

<script src="static/ace/js/bootbox.js"></script>
<!-- ace scripts -->
<script src="static/ace/js/ace/ace.js"></script>
<!-- 日期框 -->
<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
<!--提示框-->
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<script type="text/javascript">
    ///$(top.hangge());//关闭加载状态
    //检索
    function tosearch() {
        //top.jzts();
        $("#Form").submit();
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
        //日期框
        $('.date-picker').datepicker({
            autoclose: true,
            todayHighlight: true
        });

    });

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

    //流程信息
    function view(PROC_INST_ID_, DEPLOYMENT_ID_, FILENAME) {

        var winId = "userWin";
        modals.openWin({
            winId: winId,
            title: '办理任务',
            width: '900px',
            height: '400px',
            url: '<%=basePath%>hiprocdef/view.do?PROC_INST_ID_=' + PROC_INST_ID_ + "&DEPLOYMENT_ID_=" + DEPLOYMENT_ID_ + '&FILENAME=' + encodeURI(encodeURI(FILENAME))

        });
        <%-- top.jzts();
        var diag = new top.Dialog();
        diag.Drag=true;
        diag.Title ="流程信息";
        diag.URL = '<%=basePath%>hiprocdef/view.do?PROC_INST_ID_='+PROC_INST_ID_+"&DEPLOYMENT_ID_="+DEPLOYMENT_ID_+'&FILENAME='+encodeURI(encodeURI(FILENAME));
        diag.Width = 1100;
        diag.Height = 500;
        diag.Modal = true;				//有无遮罩窗口
        diag. ShowMaxButton = true;	//最大化按钮
        diag.ShowMinButton = true;		//最小化按钮
        diag.CancelEvent = function(){ //关闭事件
           diag.close();
        };
        diag.show(); --%>
    }
</script>


</body>
</html>