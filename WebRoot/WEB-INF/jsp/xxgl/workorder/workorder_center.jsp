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

    <!-- jsp文件头和头部 -->
    <%@ include file="../../system/include/incJs_mx.jsp" %>
    <style>
        .col-md-4 {
            width: 250px
        }

        .seat-middle-top {
            margin-top: 0;
            box-shadow: 4px 4px 6px #c7c7c7;
        }

        .seat-middle-top-left-tp select {
            height: 30px;
            margin-left: 10px;
            width: 150px;

        }

        body .demo-class {
            border-top-left-radius: 15px;
            border-top-right-radius: 15px;
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
    <script>
        function add() {
            location.href = "rutask/list.do?type=1";
        }
    </script>
</head>

<body class="no-skin" style="overflow:hidden" id="mr-background">

<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container" style="height: 100%">
    <section class="content" style="background:rgb(236,240,245);padding: 0;overflow: visible;height: 100%">

        <div class="content-menu" style="width: 100%;height:100%;">

            <!--  <section class="content-header">
                 <div class="seat-middle-top" id="autoheight" style="height:30px;line-height:25px;margin-top:5px">
                     <div class="seat-middle-top-left">
                         <div class="seat-middle-top-left-bt">工单处理状况</div>
                     </div>
                 </div>
             </section> -->

            <%--            <c:if test="${allruCount>0}">--%>
            <%--                <section class="content-header">--%>
            <%--                    <div class="seat-middle-top" id="autoheight" style="height:30px;line-height:25px;margin-top: 15px">--%>
            <%--                        <div class="seat-middle-top-left">--%>
            <%--                            <div class="seat-middle-top-left-bt">工单列表</div>--%>
            <%--                        </div>--%>
            <%--                    </div>--%>
            <%--                </section>--%>

            <%--                <div class="row card-row" style="padding-left:10px;">--%>
            <%--                    <div class="col-md-4 col-sm-6 col-xs-12" style="cursor:pointer;width:300px;">--%>
            <%--                        <div class="info-box card-box" style="height:150px">--%>
            <%--                            <div class="info-box-top">--%>
            <%--                                <span class="info-box-title">您有${allruCount}个工单即将超时，请速处理！</span><span--%>
            <%--                                    class="info-icon"><i--%>
            <%--                                    class="icon icon-chengse font20 icon-guanzhu"></i></span>--%>
            <%--                            </div>--%>
            <%--                            <div class="info-box-bottom" style="text-align:center">--%>
            <%--                                <input type="button" style="width:70px" class="btn btn-mini btn-success"--%>
            <%--                                       onclick="add();" value="去处理">--%>
            <%--                            </div>--%>
            <%--                        </div>--%>
            <%--                    </div>--%>
            <%--                </div>--%>
            <%--            </c:if>--%>


            <div class="row card-row between-center" style="margin: 0;width: 100%">
                <div class="workorder-status">
                    <div class="workorder-status-left" style="background: #2ea7e0;">
                    </div>
                    <div class="workorder-status-middle" style="border: 2px solid #2ea7e0">
                        <img src="static/img/workorder/pending.png" class="workorder-status-img"/>
                    </div>
                    <div class="workorder-status-right">
                        <div class="flex-row-center-center" style="align-items:flex-end"><span
                                class="workorder-status-number" style="color: #2ea7e0">${wclCount}</span>
                            <span class="workorder-status-title">个</span>
                        </div>
                        <div class="workorder-status-title">待处理工单</div>
                    </div>
                </div>

                <div class="workorder-status">
                    <div class="workorder-status-left" style="background: #ff7776;">
                    </div>
                    <div class="workorder-status-middle" style="border: 2px solid #ff7776">
                        <img src="static/img/workorder/processing.png" class="workorder-status-img"/>
                    </div>
                    <div class="workorder-status-right">
                        <div class="flex-row-center-center" style="align-items:flex-end"><span
                                class="workorder-status-number" style="color: #ff7776">${clzCount}</span>
                            <span class="workorder-status-title">个</span>
                        </div>
                        <div class="workorder-status-title">处理中工单</div>
                    </div>
                </div>

                <div class="workorder-status">
                    <div class="workorder-status-left" style="background: #f8b62d;">
                    </div>
                    <div class="workorder-status-middle" style="border: 2px solid #f8b62d">
                        <img src="static/img/workorder/audit.png" class="workorder-status-img"/>
                    </div>
                    <div class="workorder-status-right">
                        <div class="flex-row-center-center" style="align-items:flex-end"><span
                                class="workorder-status-number" style="color: #f8b62d">${dshCount}</span>
                            <span class="workorder-status-title">个</span>
                        </div>
                        <div class="workorder-status-title">待审核工单</div>
                    </div>
                </div>

                <div class="workorder-status">
                    <div class="workorder-status-left" style="background: #8fc31f;">
                    </div>
                    <div class="workorder-status-middle" style="border: 2px solid #8fc31f">
                        <img src="static/img/workorder/processed.png" class="workorder-status-img"/>
                    </div>
                    <div class="workorder-status-right">
                        <div class="flex-row-center-center" style="align-items:flex-end"><span
                                class="workorder-status-number" style="color: #8fc31f">${yclCount}</span>
                            <span class="workorder-status-title">个</span>
                        </div>
                        <div class="workorder-status-title">已处理工单</div>
                    </div>
                </div>
            </div>


            <div class="content-wrapper"
                 <c:if test="${empty pd.doaction }">style="width:100%;margin-left:0px;"</c:if>
                 <c:if test="${not empty pd.doaction }">style="width:100%;margin-left:0px;"</c:if>>
                <section class="container-fluid">
                    <div class="seat-middle-top-x"></div>

                    <div class="seat-middle">
                        <div class="xtyh-middle-r zxzgl-middle-r">
                            <table id="example2" class="table table-striped table-bordered table-hover"
                                   style="margin-top:5px;display: block;overflow: auto;table-layout:fixed">
                                <thead>
                                <tr style="white-space: nowrap">
                                    <th class="center cy_th" style="width:110px;">编号</th>
                                    <th id='cy_thk'></th>
                                    <th class="center">投诉等级</th>
                                    <th class="center">处理时限</th>
                                    <th class="center" style="width: 100px">日期</th>
                                    <th class="center" style="width: 100px">投诉来源</th>
                                    <th class="center" style="width: 100px">投诉人</th>
                                    <th class="center">联系方式</th>
                                    <th class="center">投诉部门</th>
                                    <th class="center" style="width: 200px">投诉类别（大项）</th>
                                    <th class="center" style="width: 150px">投诉类别（细项）</th>
                                    <th class="center">投诉分类</th>
                                    <th class="center">受理人</th>
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

                                                <td class='center cy_td' style="width: 110px;">${var.code}</td>
                                                <td id='cy_thk'></td>
                                                <td class='center flex-row-center-center'>
                                                   <c:if test="${var.iscs == '1' }">  
			                                            <img src="static/img/workorder/center_delay.png" class="urgentGrade"/>
			                                        </c:if>
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
                                                </td>
                                                <td class='center'>${var.clsx}</td>
                                                <td class='center'>${var.tsdate_format}</td>
                                                <td class='center'>${var.tssourcename}</td>
                                                <td class='center'>${var.tsman}</td>
                                                <td class='center'>${var.tstel}</td>

                                                <td class='center'>${var.tsdeptname}</td>
                                                <td class='center'>${var.tsbigtype}</td>
                                                <td class='center'>${var.tstypename}</td>

                                                <td class='center'>${var.tsclassifyname}</td>
                                                <td class='center'>${var.dealman}</td>


                                                <td class='center'>${var.cljdname}</td>
                                                <td class='center'>

                                                    <c:if test="${var.type == 0 }"><span
                                                            class="orangeState">待反馈</span></c:if>
                                                    <c:if test="${var.type == 1||var.type ==2 }"><span
                                                            class="blueState">工单已分派</span></c:if>
                                                    <c:if test="${var.type == 3 }"><span
                                                            class="greenState">正常为您处理</span></c:if>
                                                    <c:if test="${var.type == 4 }"><span
                                                            class="redState">工单已关闭</span></c:if>
                                                    <c:if test="${var.type == 5 }"><span
                                                            class="redState">工单退回</span></c:if>
                                                </td>

                                                <td class='center'>
                                                    <span class="td-content"
                                                          title="${var.endreason}">${var.endreason}</span>
                                                </td>
                                                <td class="center">
                                                    <div class="flex-row-center-center">
                                                        <%if (jurisdiction.hasQx("70208")) { %>

	                                                        <div class="content-look flex-row-acenter" title="查看"
	                                                             onclick="edit('${var.id}','search');">
	                                                            <img src="static/img/workorder/look.png" width="20px"
	                                                                 height="20px"/>
	                                                            <font class="content-size">查看</font>
	                                                        </div>
                                                        <%} %>
                                                        <c:if test="${var.islx =='dpf' }">
                                                            <%if (jurisdiction.hasQx("70207")) { %>
                                                          
                                                            <div class="content-send flex-row-acenter" title="派发"
                                                                 onclick="edit('${var.id}','');">
                                                                <img src="static/img/workorder/send.png" width="20px"
                                                                     height="20px"/>
                                                                <font class="content-size">派发</font>
                                                            </div>
                                                            <%} %>
                                                        </c:if>
                                                        
                                                        <c:if test="${var.isreceive != '1' }">
                                                        	<c:if test="${var.islx == 'cl' || var.islx == 'sh' }">
					                                            <div class="content-nedit flex-row-acenter" title="办理"
	                                                                 onclick="receive('${var.PROC_INST_ID_}','${var.ID_}','${var.DGRM_RESOURCE_NAME_}');">
	                                                                <img src="static/img/workorder/nedit.png" width="20px"
	                                                                     height="20px"/>
	                                                                <font class="content-size">接收</font>
	                                                            </div>
                                                            </c:if>
                                        				</c:if>
                                        				<c:if test="${var.isreceive == '1' }">
	                                                        <c:if test="${var.islx =='cl' }">
	                                                            <%if (jurisdiction.hasQx("70401")) { %>
	                                                            <div class="content-nedit flex-row-acenter" title="办理"
	                                                                 onclick="handle_add('${var.PROC_INST_ID_}','${var.ID_}','${var.DGRM_RESOURCE_NAME_}');">
	                                                                <img src="static/img/workorder/nedit.png" width="20px"
	                                                                     height="20px"/>
	                                                                <font class="content-size">办理</font>
	                                                            </div>
	                                                            <%} %>
	                                                        </c:if>
	                                                        <c:if test="${var.islx =='sh' }">
	                                                            <%if (jurisdiction.hasQx("70401")) { %>
	                                                           
	                                                            <div class="content-review flex-row-acenter" title="审核"
	                                                                 onclick="handle_add('${var.PROC_INST_ID_}','${var.ID_}','${var.DGRM_RESOURCE_NAME_}');">
	                                                                <img src="static/img/workorder/review.png" width="20px"
	                                                                     height="20px"/>
	                                                                <font class="content-size">审核</font>
	                                                            </div>
	                                                            <%} %>
	                                                        </c:if>
														</c:if>
                                                    </div>
                                                </td>
                                            </tr>
                                        </c:forEach>

                                    </c:when>
                                    <c:otherwise>
                                        <tr class="main_info">
                                            <td colspan="100" class="center">没有需要办理的任务</td>
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

        </div>

    </section>

    <div class="modal fade" id="workorderUrgent" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true" data-backdrop="static">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title" id="myModalLabel" style="float: left;"></h4>
                    <div class="new-tb" style="float: right;" data-dismiss="modal" title="关闭"></div>
                </div>
                <div class="modal-body"
                     style="background-color: #EEF2F5;border-bottom-left-radius: 20px;border-bottom-right-radius: 20px">
                    <div class="flex-row-center-center">
                        <div class="flex-column" style="width: 100%;height: 250px">
                            <div class="flex-row-center-center" style="margin-top: 40px">
                                <img src="static/img/workorder/urgent.png" width="100px" height="101px"/>
                            </div>
                            <span style="text-align: center;font-size: 15px;width: 100%;height: 100px;line-height: 100px;color: #4A4A4A;font-weight: bold">您有一个紧急工单，请立即确认处理</span>
                        </div>

                    </div>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
    <div class="modal fade" id="workorderDelay" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true" data-backdrop="static">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title" id="myModalLabel1" style="float: left;"></h4>
                    <div class="new-tb" style="float: right;" data-dismiss="modal" title="关闭"></div>
                </div>
                <div class="modal-body"
                     style="background-color: #EEF2F5;border-bottom-left-radius: 20px;border-bottom-right-radius: 20px">
                    <div class="flex-row-center-center">
                        <div class="flex-column" style="width: 100%;height: 250px">
                            <div class="flex-row-center-center" style="margin-top: 40px">
                                <img src="static/img/workorder/delay.png" width="100px" height="101px"/>
                            </div>
                            <span style="text-align: center;font-size: 15px;width: 100%;height: 100px;line-height: 100px;color: #4A4A4A;font-weight: bold">您有${csnum}条超时的工单，请及时处理</span>
                        </div>

                    </div>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
    <%--    <div class="workorder-delay"></div>--%>
</div>


<%@ include file="../../system/include/incJs_foot.jsp" %>
<script>
	function tosearch(){
		location.href=window.location;
	}
    window.onload = function () {
        //$('#workorderUrgent').modal('show')
        //alert(${csnum});
        if (${csnum}>
	        0
	    )
        {
            $('#workorderDelay').modal('show');
        }
        //$('#workorderDelay').modal('show')
        for (i = 0; i < 4; i++) {
            var autowidth = document.getElementsByClassName('workorder-status')[i]
            var automiddle = document.getElementsByClassName('workorder-status-middle')[i]
            var autoright = document.getElementsByClassName('workorder-status-right')[i]
            autowidth.style.width = document.documentElement.clientWidth / 4.2 + 'px'
            automiddle.style.left = (document.documentElement.clientWidth / 4.5) * 0.3 - 30 + 'px'
            autoright.style.width = (document.documentElement.clientWidth / 4.5) * 0.7 + 'px'
        }
    }
    var tables;

    $(function () {
        if (screen.width > 1600) {
            $('#example2').DataTable({
                'paging': true,
                'lengthChange': false,
                'searching': false,
                'ordering': false,
                'info': true,
                'autoWidth': true,
                "iDisplayStart": 0 * 12,  //用于指定从哪一条数据开始显示到表格中去 num为20的时候，第一页是0，第二页是20.....
                "iDisplayLength": 12,
                // 'sScrollY': 'calc(100vh - 240px)',
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

    function dcl(type) {
        location.href = "<%=path%>/workorder/list.do?type=" + type;
    }

    function add(type) {
        location.href = "<%=path%>/rutask/list.do";
    }

    function edit(Id, action) {

        var winId = "userWin";
        var type = '${pd.type}';
        var title = "";
        if (action != "") {
            title = "工单查看";

        } else {
            title = "工单处理";
            action = "";
        }
        gdlayer = layer.open({
            type: 2,
            title: title,
            shade: 0.5,
            skin: 'demo-class',
            area: ['100%', '100%'],
            content: "<%=basePath%>workorder/goEdit.do?search=" + action + "&type=" + type + "&id=" + Id
        });
    }

    
    function receive(PROC_INST_ID_, ID_,FILENAME){
        $.ajax({
            type: 'post',
            url: "<%=basePath%>rutask/receive.do?ID_=" + ID_ + "&ISRECEIVE=1",
            dataType: 'json',
            cache: false,
            success: function (obj) {

                var str = "";
                var data = eval(obj.data);
                if (obj.success == "true") {
                	layer.alert('确认接收成功');
                } else {
                	layer.alert('确认接收失败');
                }
                window.location.href=location.href;

            }, error: function (jqXHR, textStatus, errorThrown) {
            	layer.alert('提交异常');
                return false;
            }
        });
    }

    //弹窗
    function editRights(ROLE_ID) {
        var winId = "userWin";
        modals.openWin({
            winId: winId,
            title: '权限',
            width: '320px',
            height: '490px',
            url: '<%=basePath%>role/menuqx.do?ROLE_ID=' + ROLE_ID
        });
    }

    //办理任务
    function handle_add(PROC_INST_ID_, ID_, FILENAME) {
        //alert("d");
        var winId = "userWin";
        var title = '';
        //alert($("#mr-background").height());
        modals.openWin({
            winId: winId,
            title: '办理任务',
            width: $("#mr-background").width(),
            height: $("#mr-background").height() + "px",
            url: '<%=basePath%>rutask/goHandle.do?PROC_INST_ID_=' + PROC_INST_ID_ + "&ID_=" + ID_ + '&FILENAME=' + encodeURI(encodeURI(FILENAME))
        });
    }
</script>
</body>
</html>
</html>