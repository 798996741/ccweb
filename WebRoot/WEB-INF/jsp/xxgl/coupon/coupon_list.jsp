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
.table th {
	font-size: 14px;
}

.table td {
	font-size: 14px;
}
</style>
</head>
<body class="no-skin">
	<div class="content-wrapper" style="width:100%;margin-left:0px;">
		<section class="content-header">
			<h1>
				信息配置 <small>优惠券设置</small>
			</h1>
			<ol class="breadcrumb">
				<li><a href="javascript:;" onclick="home()"> <i
						class="fa fa-dashboard"></i> 主页
				</a></li>
				<li class="active">优惠券设置</li>
			</ol>
		</section>
		<section class="content">
			<div class="row">
				<div class="col-xs-12">
					<div class="box">
						<div class="box-header">
							<c:if test="${QX.add == 1 }">
								<a class="btn btn-mini btn-success" onclick="add();">新增</a>
							</c:if>
						</div>
						<div class="box-body">

							<table id="example2"
								class="table table-striped table-bordered table-hover"
								style="margin-top:5px;">
								<thead>
									<tr>
										<th class="center" style="width:35px;"><label
											class="pos-rel"><input type="checkbox" class="ace"
												id="zcheckbox" /><span class="lbl"></span></label></th>
										<th class="center" style="width:50px;">序号</th>
										<th class="center">优惠卷标题</th>
										<th class="center">有效期</th>
										<th class="center">详情</th>
										<th class="center">使用范围</th>
										<th class="center">费用</th>
										<th class="center">优惠前费用</th>
										<th class="center">创建人</th>
										<th class="center">时间</th>
										<th class="center">操作</th>
									</tr>
								</thead>

								<tbody>
									<!-- 开始循环 -->
									<c:choose>
										<c:when test="${not empty varList}">
											<c:if test="${QX.cha == 1 }">
												<c:forEach items="${varList}" var="var" varStatus="vs">
													<tr>
														<td class='center'><label class="pos-rel"><input
																type='checkbox' name='ids' value="${var.ID}" class="ace" /><span
																class="lbl"></span></label></td>
														<td class='center' style="width: 30px;">${vs.index+1}</td>
														<td class='center'>${var.TITLE}</td>
														<td class='center'>${var.TERM_VALIDITY}天</td>
														<td class='center'><pre>${var.DETAIL}</pre></td>
														<td class='center'>${var.USESCOPE}</td>
														<td class='center'>${var.MONEY}元</td>
														<td class='center'>${var.PRE_MONEY}元</td>
														<td class='center'>${var.CREATEMAN}</td>
														<td class='center'>${var.CREATEDATE}</td>
														<td class="center">
															<c:if test="${QX.edit != 1 && QX.del != 1 }">
																<span
																	class="label label-large label-grey arrowed-in-right arrowed-in"><i
																	class="ace-icon fa fa-lock" title="无权限"></i></span>
															</c:if>
															<div class="hidden-sm hidden-xs btn-group">
																<c:if test="${QX.edit == 1 }">
																	<a class="btn btn-xs btn-success" title="编辑"
																		onclick="edit('${var.ID}');"> <i
																		class="ace-icon fa fa-pencil-square-o bigger-120"
																		title="编辑"></i>
																	</a>
																</c:if>
																<c:if test="${QX.del == 1 }">
																	<a style="margin-left:10px;" class="btn btn-xs btn-danger"
																		onclick="del('${var.ID}');"> <i
																		class="ace-icon fa fa-trash-o bigger-120" title="删除"></i>
																	</a>
																</c:if>
															</div>
														</td>
													</tr>

												</c:forEach>
											</c:if>
											<c:if test="${QX.cha == 0 }">
												<tr>
													<td colspan="100" class="center">您无权查看</td>
												</tr>
											</c:if>
										</c:when>
										<c:otherwise>
											<tr class="main_info">
												<td colspan="100" class="center">没有相关数据</td>
											</tr>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>

							<!-- /.box-body -->
						</div>
						<!-- /.box -->

					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
		</section>
		<!-- /.content -->
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
		//$(top.hangge());//关闭加载状态
		//检索
		function tosearch(){
			//top.jzts();
			//$("#Form").submit();
		}
		$(function() {
			$('#example2').DataTable({
				'paging'      : true,
			      'lengthChange': false,
			      'searching'   : true,
			      'ordering'    : true,
			      'info'        : true,
			      'autoWidth'   : true
		    })
		});
		
		//新增
		function add(){
			var winId = "userWin";
		  	modals.openWin({
	          	winId: winId,
	          	title: '新增',
	          	width: '900px',
	          	height: '400px',
	          	url: '<%=basePath%>coupon/goAdd.do'
	          
	      	});
			
		}
		
		//删除
		function del(Id,Uid){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>coupon/delete.do?ID="+Id+"&UID="+Uid+"&tm="+new Date().getTime();
					$.get(url,function(data){
						location.href="<%=path%>/coupon/list.do";
					});
				}
			});
		}
		
		//修改
		function edit(Id,Uid){
			
			var winId = "userWin";
		  	modals.openWin({
	          	winId: winId,
	          	title: '新增',
	          	width: '900px',
	          	height: '400px',
	          	url: '<%=basePath%>coupon/goEdit.do?UID="+Uid+"&ID='+Id
	          	/*, hideFunc:function(){
	             	 modals.info("hide me");
	          	},
	          	showFunc:function(){
	              	modals.info("show me");
	          	} */
	      	});
			
			
		}
		
		
		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>coupon/excel.do';
		}
	</script>
</body>
</html>