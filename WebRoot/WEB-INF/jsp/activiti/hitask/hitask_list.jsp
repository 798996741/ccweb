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

    <%@ include file="../../system/include/incJs_mx.jsp" %>
    <!-- zTreeStyle -->


    <style>
        @media screen and (max-width: 1700px) {
            .xtyh-middle-r {
                width: 100% !important;
            }
        }

        .zxzgl-middle-r {
            width: 100% !important;
        }

        .seat-middle-top {
            margin-top: 0;
        }

        /*.seat-middle-top-left-tp select {*/
        /*    height: 30px;*/
        /*    margin-left: 10px;*/
        /*    width: 150px;*/

        /*}*/

        /*.seat-middle-top-left-tp input {*/
        /*    height: 30px;*/
        /*    width: 150px;*/
        /*}*/

        /*.seat-middle-top-left-tp div {*/
        /*    height: 35px;*/
        /*}*/
        .menu-top {
            display: flex;
            flex-direction: row;
            justify-content: center;
            align-items: center;
            width: 1111px;
            margin: 10px 0;
        }

        .redState {
            font-weight: bolder;
            color: #ff7776;
        }

        .orangeState {
            font-weight: bolder;
            color: #f8b62d;
        }

        .blueState {
            font-weight: bolder;
            color: #5fade3;
        }

        .greenState {
            font-weight: bolder;
            color: #8fc31f;
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
<body class="no-skin">
<div class="content-wrapper" style="width:100%;margin-left:0px;">
    <section class="container-fluid">
        <div class="seat-middle-top-x"></div>
        <div class="seat-middle-top" id="autoheight"
             style="height: 120px;line-height: 25px;box-shadow: 4px 4px 6px #c7c7c7;">
            <div class="seat-middle-top-left">
                <div class="seat-middle-top-left-bt" style="white-space: nowrap;">工单已办</div>
                <div class="seat-middle-top-left-tp menu-top">
                    <form action="hitask/list.do" method="post" name="Form" id="Form">
                        <div class="flex-row-wrap">
                            <div class="border-style">
                                <span class="border-size">工单来源</span>
                                <div class="border-line"></div>
                                <select class="seat-select" id="tssources" name="tssources">
                                    <option value="">全部</option>
                                    <c:forEach items="${tssourceList}" var="var" varStatus="vs">
                                        <option value="${var.DICTIONARIES_ID }"
                                                <c:if test="${pd.tssource==var.DICTIONARIES_ID}">selected</c:if>>${var.NAME }</option>
                                    </c:forEach>
                                </select>
                            </div>
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
                                <span class="border-size">投&nbsp;诉&nbsp;人</span>
                                <div class="border-line"></div>
                                <input class="seat-input width-111px" placeholder="投诉人" name="tsmans" id="tsmans"
                                       value="${pd.tsman }">
                            </div>
                            <div class="border-style">
                                <span class="border-size">投诉部门</span>
                                <div class="border-line"></div>
                                <select class="seat-select" id="tsdepts" name="tsdepts">
                                    <option value="">全部</option>
                                    <c:forEach items="${tsdeptList}" var="var" varStatus="vs">
                                        <option value="${var.AREA_CODE }"
                                                <c:if test="${pd.tsdept==var.AREA_CODE}">selected</c:if>>${var.NAME }</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="border-style">
                                <span class="border-size">投诉类别(大项)</span>
                                <div class="border-line"></div>
                                <select class="seat-select width-200px" id="bigtstypes" name="bigtstypes"
                                        onchange="tstypechanges()">
                                    <option value="">请选择投诉类别</option>
                                    <c:forEach items="${tstypeLists}" var="var" varStatus="vs">
                                        <option value="${var.DICTIONARIES_ID }"
                                                <c:if test="${pd.bigtstypes==var.DICTIONARIES_ID}">selected</c:if>>${var.NAME }</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="border-style">
                                <span class="border-size">投诉类别(细项)</span>
                                <div class="border-line"></div>
                                <%--                                <span id="tstypespans"></span>--%>
                                <select class="seat-select width-200px" id="tstypes" name="tstypes"
                                        onchange="">
                                    <%--                                        <option value="">请选择投诉类别</option>--%>
                                    <%--                                        <c:forEach items="${tstypeLists}" var="var" varStatus="vs">--%>
                                    <%--                                            <option value="${var.DICTIONARIES_ID }"--%>
                                    <%--                                                    <c:if test="${pd.bigtstypes==var.DICTIONARIES_ID}">selected</c:if>>${var.NAME }</option>--%>
                                    <%--                                        </c:forEach>--%>
                                </select>
                            </div>
                            <div class="border-style">
                                <span class="border-size">工单状态</span>
                                <div class="border-line"></div>
                                <select class="seat-select" id="types" name="types">
                                    <option value="">全部</option>
                                    <option value="0" <c:if test="${pd.type=='0'}">selected</c:if>>待反馈</option>
                                    <option value="1" <c:if test="${pd.type=='1'}">selected</c:if>>待确认</option>
                                    <option value="2" <c:if test="${pd.type=='2'}">selected</c:if>>工单已分派</option>
                                    <option value="3" <c:if test="${pd.type=='3'}">selected</c:if>>正常为您处理</option>
                                    <option value="4" <c:if test="${pd.type=='4'}">selected</c:if>>工单已关闭</option>
                                </select>
                            </div>
                            <div class="border-style">
                                <span class="border-size">投诉分类</span>
                                <div class="border-line"></div>
                                <select class="seat-select width-110px" id="tsclassify" name="tsclassify">
                                    <option value="">全部</option>
                                    <c:forEach items="${tsclassifyList}" var="var" varStatus="vs">
                                        <option value="${var.DICTIONARIES_ID }"
                                                <c:if test="${pd.tsclassify==var.DICTIONARIES_ID}">selected</c:if>>${var.NAME }</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="border-style">
                                <span class="border-size">综合查询</span>
                                <div class="border-line"></div>
                                <input class="seat-input width-110px" placeholder="综合查询" name="keywords" id="keywords"
                                       value="${pd.keywords }">
                            </div>
                            <button type="button" class="btn btn-default-sm button-blue width-65px"
                                    style="margin: 5px 5px 5px 15px;" onclick="tosearch();">
                                <font>查&nbsp;询</font>
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="seat-middle">
            <!--  <div class="seat-middle-nr"> -->

            <div class="xtyh-middle-r zxzgl-middle-r">
                <!-- <div class="box-body" > -->
                <table id="example2" class="table table-striped table-bordered table-hover"
                       style="margin-top:5px;display: block;overflow-x: auto;overflow-y: auto;">
                    <thead>
                    <tr style="white-space: nowrap">
                        <th class="center cy_th" style="width:110px;">投诉编号</th>
                        <th id='cy_thk'></th>
                        <th class="center" style="width: 100px">日期</th>
                        <th class="center" style="width: 100px">投诉来源</th>

                        <th class="center" style="width: 100px">投诉人</th>
                        <th class="center">联系方式</th>
                        <th class="center" style="width: 150px">投诉内容</th>
                        <th class="center">投诉等级</th>
                        <th class="center">投诉部门</th>
                        <!-- <th class="center">投诉类别（大项）</th> -->
                        <th class="center" style="width: 150px">投诉类别（细项）</th>
                        <th class="center">投诉分类</th>
                        <th class="center">受理人</th>
                        <th class="center">顾客回访情况</th>
                        <th class="center" style="width: 160px">结束时间</th>
                        <th class="center">处理节点</th>
                        <th class="center">工单状态</th>
                        <th class="center" style="width: 150px">结束原因</th>
                        <th class="center">操作</th>
                    </tr>
                    </thead>

                    <tbody>
                    <!-- 开始循环 -->
                    <c:choose>
                        <c:when test="${not empty varList}">
                            <c:forEach items="${varList}" var="var" varStatus="vs">
                                <tr style="white-space: nowrap">
                                    <td class='center cy_td' style="width:110px;">${var.code}</td>
                                    <td id='cy_thk'></td>
                                    <td class='center'>${var.tsdate}</td>
                                    <td class='center'>${var.tssourcename}</td>
                                    <td class='center'>${var.tsman}</td>
                                    <td class='center'>${var.tstel}</td>

                                    <td class='center'
                                        style="width: 150px;max-width: 150px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">
                                        <span class="td-content" title="${var.tscont}"
                                              style="width: 150px;max-width: 150px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">${var.tscont}</span>
                                    </td>
                                    <td class='center'>
                                            <%--                                            ${var.tslevelname}--%>
                                       <%--  <c:if test="${var.iscs == '0' or empty var.iscs}"> --%>
                                            <c:if test="${var.tslevelname == '高级' }">
                                                <img src="static/img/workorder/high.png" class="complaintGrade"/>
                                            </c:if>
                                            <c:if test="${var.tslevelname == '紧急' }">
                                                <img src="static/img/workorder/center_urgent.png"
                                                     class="urgentGrade"/>
                                            </c:if>
                                            <c:if test="${var.tslevelname == '普通' }">
                                                <img src="static/img/workorder/low.png" class="complaintGrade"/>
                                            </c:if>
                                        <%-- </c:if>  --%>
                                        <%-- <c:if test="${var.iscs == '1' }">  
                                            <img src="static/img/workorder/center_delay.png" class="urgentGrade"/>
                                        </c:if> --%>
                                    </td>
                                    <td class='center'>${var.tsdeptname}</td>
                                        <%-- <td class='center'>${var.tsbigtype}</td> --%>
                                    <td class='center'>${var.tstypename}</td>

                                    <td class='center'>${var.tsclassifyname}</td>
                                    <td class='center'>${var.dealman}</td>
                                    <td class='center'>
                                        <c:if test="${var.ishf == '0' ||empty var.ishf}">否</c:if>
                                        <c:if test="${var.ishf == '1' }">是</c:if>
                                    </td>
                                    <td class='center'>${var.endtime}</td>
                                    <td class='center'>${var.cljdname}</td>
                                    <td class='center'>
                                        <c:if test="${var.type == 0 }"><span class="orangeState">待反馈</span></c:if>
                                        <c:if test="${var.type == 1||var.type ==2 }"><span
                                                class="blueState">工单已分派</span></c:if>
                                        <c:if test="${var.type == 3 }"><span class="greenState">正常为您处理</span></c:if>
                                        <c:if test="${var.type == 4 }"><span class="redState">工单已关闭</span></c:if>
                                        <c:if test="${var.type == 5 }"><span class="redState">工单退回</span></c:if>
                                    </td>

                                    <td class='center'
                                        style="width: 150px;max-width: 150px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">
                                        <c:if test="${not empty var.endreason}">
                                            ${var.endreason}
                                        </c:if>
                                        <c:if test="${empty var.endreason&&var.type == 4}">
                                            闭环
                                        </c:if>

                                    </td>
                                    <td class="center">
                                        
                                       	<c:if test="${var.ISRECEIVE=='0'}">
	                                       	<div class="content-edit home-img cy_bj" title="撤回"
	                                             onclick="returnProc('${var.PROC_INST_ID_}','${var.id }');">
	                                             <font class="home-img-size">撤回</font>
	                                        </div>
                                        </c:if>
                                        <div class="content-edit home-img cy_bj" title="流程信息"
                                             onclick="view('${var.PROC_INST_ID_}','${var.DEPLOYMENT_ID_ }','${var.DGRM_RESOURCE_NAME_}','${var.ID_ }');">
                                             <font class="home-img-size">查看</font>
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

                </form>

            </div>
            <!-- /.col -->
        </div>
        <!-- /.row -->
</div>
<!-- /.page-content -->
<!-- /.main-content -->
<!-- 返回顶部 -->
<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
    <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
</a>
<!-- bootbox&ace -->
<!-- jquery.tips.js -->
<!-- 页面底部js¨ -->
<%@ include file="../../system/include/incJs_foot.jsp" %>
<script type="text/javascript">
    //获取浏览页面可视区域的宽度改变菜单栏宽度
    window.onload = function () {
        document.getElementsByClassName('menu-top')[0].style.width = document.documentElement.clientWidth - 160 + 'px'
        if (document.documentElement.clientWidth > 1400) {
            document.getElementById('autoheight').style.height = Number(document.getElementById('autoheight').style.height.substring(0, 3)) - 40 + 'px'
        } else {
            document.getElementsByClassName('menu-top')[0].style.width = "1023px"
        }
    }
    ///$(top.hangge());//关闭加载状态
    //检索
    function tosearch() {
        //top.jzts();
        $("#Form").submit();
    }

    $('.date-picker').datepicker({
        autoclose: true,
        todayHighlight: true,
        clearBtn: true
    });

    function tstypechanges() {
        var bigtstypes = $("#bigtstypes").val();
        if (bigtstypes == "") {
            $("#tstypes").html('');
            return false;
        }
        var tstypes = '${pd.tstypes}';
        //alert(bigtstype);
        $.ajax({
            //几个参数需要注意一下
            type: "POST",//方法类型
            dataType: "json",//预期服务器返回的数据类型
            url: "workorder/getTstype.do?bigtstype=" + bigtstypes,//url
            success: function (result) {

                var str = str + "<option value=\"\">请选择投诉分类细项</option>";
                $.each(result.list, function (i, list) {

                    if (tstypes == list.DICTIONARIES_ID) {
                        str = str + "<option value=\"" + list.DICTIONARIES_ID + "\" selected>" + list.NAME + "</option>";
                    } else {
                        str = str + "<option value=\"" + list.DICTIONARIES_ID + "\">" + list.NAME + "</option>";
                    }

                });
                str = str + "</select>";
                $("#tstypes").html('');
                $("#tstypes").html(str);
            }
        });
    }

    tstypechanges();
    // $(function () {
    //     $('#example2').DataTable({
    //         'paging': true,
    //         'lengthChange': false,
    //         'searching': false,
    //         'ordering': false,
    //         'info': true,
    //         'autoWidth': true
    //     })
    //     //日期框
    //     $('.date-picker').datepicker({
    //         autoclose: true,
    //         todayHighlight: true
    //     });
    //
    // });

    $(function () {
        $('.date-picker').datepicker({
            autoclose: true,
            todayHighlight: true
        });
        if (screen.width > 1600) {
            $('#example2').DataTable({
                'paging': true,
                'lengthChange': false,
                'searching': false,
                'ordering': false,
                'info': true,
                'autoWidth': true,
                "iDisplayStart": 0 * 13,  //用于指定从哪一条数据开始显示到表格中去 num为20的时候，第一页是0，第二页是20.....
                "iDisplayLength": 13,
                // 'sScrollY': 'calc(100vh - 190px)',
            })
        } else if (screen.width > 1366) {
            $('#example2').DataTable({
                'paging': true,
                'lengthChange': false,
                'searching': false,
                'ordering': false,
                'info': true,
                'autoWidth': true,
                "iDisplayStart": 0 * 11,  //用于指定从哪一条数据开始显示到表格中去 num为20的时候，第一页是0，第二页是20.....
                "iDisplayLength": 11,
                // 'sScrollY': 'calc(100vh - 230px)',
            })
        } else {
            $('#example2').DataTable({
                'paging': true,
                'lengthChange': false,
                'searching': false,
                'ordering': false,
                'info': true,
                'autoWidth': true,
                "iDisplayStart": 0 * 10,  //用于指定从哪一条数据开始显示到表格中去 num为20的时候，第一页是0，第二页是20.....
                "iDisplayLength": 10,
                // 'sScrollY': 'calc(100vh - 230px)',
            })
        }
    })

    $(function () {
        $('.seat-middle').find(".row:first").hide();
        $(".xtyh-middle-r").find(".row:last").addClass("row-zg");
        $(".xtyh-middle-r").find(".row:eq(1)").addClass("row-two");
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


    function view(PROC_INST_ID_, DEPLOYMENT_ID_, FILENAME, ID_) {

        var winId = "userWin";
        var type = '${pd.type}';
        var title = "";
        // if (action != "") {
        //     title = "工单查看";
        //
        // } else {
        //     title = "工单处理";
        //     action = "";
        // }
        //alert(ID_);
        modals.openWin({
            winId: winId,
            title: '办理任务',
            width: $(".container-fluid").width() + 'px',
            height: $(".container-fluid").height() + "px",
            url: "<%=basePath%>hiprocdef/view.do?PROC_INST_ID_=" + PROC_INST_ID_ + "&DEPLOYMENT_ID_=" + DEPLOYMENT_ID_ + "&ID_=" + ID_ + "&FILENAME=" + encodeURI(encodeURI(FILENAME))
        });
        <%--gdlayer = layer.open({--%>
        <%--    type: 2,--%>
        <%--    title: title,--%>
        <%--    shade: 0.5,--%>
        <%--    skin: 'demo-class',--%>
        <%--    area: ['1150px', '570px'],--%>
        <%--    content: '<%=basePath%>hiprocdef/view.do?PROC_INST_ID_=' + PROC_INST_ID_ + "&DEPLOYMENT_ID_=" + DEPLOYMENT_ID_ + '&FILENAME=' + encodeURI(encodeURI(FILENAME))--%>
        <%--});--%>
    }
    
    
    function returnProc(proc_id,id) {
    	
    	layer.confirm("确认要撤回吗", { title: "确认撤回" }, function (index) {
	        $.ajax({
	            //几个参数需要注意一下
	            type: "POST",//方法类型
	            dataType: "json",//预期服务器返回的数据类型
	            url: "workorder/returnProc.do?id="+id+"&proc_id=" + proc_id,//url
	            success: function (result) {
	            	 //var data = eval(result.data);
	                 if (result.success == "true") {
	                     layer.alert('操作成功');
	                 } else {
	                     layer.alert('操作失败');
	                 }
	                 tosearch();
	            }
	        });
    	});    
    }

</script>


</body>
</html>