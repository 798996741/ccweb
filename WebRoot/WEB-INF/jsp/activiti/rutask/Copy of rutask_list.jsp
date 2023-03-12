<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<%@ include file="../../system/include/incJs_mx.jsp"%>




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
			<div class="seat-middle-top-x"></div>
			<div class="seat-middle-top">
				<div class="seat-middle-top-left">
					<div class="seat-middle-top-left-bt">工单待办</div>
					<div class="seat-middle-top-left-tp">
						
					</div>
				</div>
				<div class="seat-middle-top-right">
					<input placeholder="搜  索" name="keywords" id="keywords"
						value="${pd.keywords }"> <a href="javascript:void(0)">
						<img src="static/login/images/icon-search.png"
						onclick="tosearch()">
					</a>
				</div>
			</div>
			<div class="seat-middle">
				<!--  <div class="seat-middle-nr"> -->

				<div class="xtyh-middle-r zxzgl-middle-r">
					<!-- <div class="box-body" > -->
						<table id="example2" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									
									<th class="center" style="width:50px;">序号</th>
									<th class="center">流程名称</th>
									<th class="center">申请人</th>
									<th class="center">当前节点(待办人)</th>
									<th class="center">当前任务</th>
									<th class="center"><i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>创建时间</th>
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
											<td class='center'><a onclick="viewUser('${var.INITATOR}')" style="cursor:pointer;"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i>${var.INITATOR}</a></td>
											<td class='center'><a onclick="viewUser('${var.ASSIGNEE_}')" style="cursor:pointer;"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i>${var.ASSIGNEE_}</a></td>
											<td class='center'>${var.NAME_}</td>
											<td class='center'>${var.CREATE_TIME}</td>
											<td class="center">
											<c:if test="${var.SUSPENSION_STATE_ == 1 }">
												<c:if test="${QX.edit != 1 && QX.del != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</c:if>
												<div class="hidden-sm hidden-xs btn-group">
													<c:if test="${QX.Delegate == 1 }">
													<a class="btn btn-xs btn-purple" title="委派" onclick="delegate('${var.ID_}');">
														<i class="ace-icon glyphicon glyphicon-user bigger-120" title="委派" style="float: left;"></i><div style="float: right;">委派</div>
													</a>
													</c:if>
													<a class="cy_bj" title="办理" onclick="handle('${var.PROC_INST_ID_}','${var.ID_}','${var.DGRM_RESOURCE_NAME_}');">
														<i title="办理"></i>
													</a>
												</div>
												
											</c:if>
											<c:if test="${var.SUSPENSION_STATE_ == 2 }">
												<h7 class="red">已挂起</h7>
											</c:if>
											</td>
										</tr>
									</c:forEach>
									
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="100" class="center" >没有需要办理的任务</td>
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
								<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
							</tr>
						</table>
						
				</div>

			</div>
		</section>
	</div>

	<!-- /.main-container -->

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<script type="text/javascript">
		//检索
		function tosearch(){
			$("#Form").submit();
		}
		$(function() {
			$('#example2').DataTable({
				'paging'      : true,
			     'lengthChange': false,
			     'searching'   : false,
			     'ordering'    : true,
			     'info'        : true,
			     'autoWidth'   : true
		    })
			//日期框
			$('.date-picker').datepicker({
				autoclose: true,
				todayHighlight: true
			});
			
		});
		
		//查看用户
		function viewUser(USERNAME){
			if('admin' == USERNAME){
				bootbox.dialog({
					message: "<span class='bigger-110'>不能查看admin用户!</span>",
					buttons: 			
					{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
				});
				return;
			}
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Modal = false;				//有无遮罩窗口
			 diag.Drag=true;
			 diag.Title ="资料";
			 diag.URL = '<%=basePath%>user/view.do?USERNAME='+USERNAME;
			 diag.Width = 469;
			 diag.Height = 380;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}
		
		//委派他人办理
		function delegate(ID_){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="委派对象";
			 diag.URL = '<%=basePath%>ruprocdef/goDelegate.do?ID_='+ID_;
			 diag.Width = 500;
			 diag.Height = 130;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 tosearch();
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//办理任务
		function handle(PROC_INST_ID_,ID_,FILENAME){
			var winId = "userWin";
		  	modals.openWin({
	          	winId: winId,
	          	title: '办理任务',
	          	width: '900px',
	          	height: '400px',
	          	url: '<%=basePath%>rutask/goHandle.do?PROC_INST_ID_='+PROC_INST_ID_+"&ID_="+ID_+'&FILENAME='+encodeURI(encodeURI(FILENAME))
	      	});
			
			<%--  top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="办理任务";
			 diag.URL = '<%=basePath%>rutask/goHandle.do?PROC_INST_ID_='+PROC_INST_ID_+"&ID_="+ID_+'&FILENAME='+encodeURI(encodeURI(FILENAME));
			 diag.Width = 1100;
			 diag.Height = 750;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 $("#simple-table").tips({
							side:3,
				            msg:'审批完毕',
				            bg:'#AE81FF',
				            time:3
				     });
					 top.topTask();//刷新顶部任务列表
					 setTimeout('tosearch()',1000);//3秒后刷新当前任务列表
				}
				diag.close();
			 };
			 diag.show(); --%>
		}
	</script>


</body>
</html>