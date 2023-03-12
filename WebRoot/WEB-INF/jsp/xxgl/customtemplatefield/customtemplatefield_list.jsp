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

</head>
<style>
	.new-tck-zk{
	    background: #ecf0f5;
	}
	.seat-middle-top{
		margin-top:0!important;
	}
</style>
<body class="no-skin">


	<div class="new-cy-body"  >
		<div class="modal-header">
			<h4 class="modal-title" id="myModalLabel" style="float: left;">	</h4>
               	<div  class="new-tb" style="float: right;"   data-dismiss="modal" title="关闭"></div>
		</div>
		<section class="" >
			<div class="row">
				<div class="col-xs-12">
					<div class="new-tck-zk">
						<div class="seat-middle-top">
			                <div class="seat-middle-top-left">
			                    <div class="seat-middle-top-left-tp">
				                    <c:if test="${QX.add == 1 &&empty pd.action}">
										<a onclick="add();">新增</a>
									</c:if>
			                    </div>
			                </div>
			               <%--  <div class="seat-middle-top-right">
								<input placeholder="搜  索" name="keywords" id="keywords" value="${pd.keywords }"> 
								<a href="javascript:void(0)">
								<img src="static/login/images/icon-search.png" onclick="tosearch()"></a>
							</div> --%>
			            </div>
			            
						<div class="xtyh-middle-r" >

							<table id="example5"
								class="table table-striped table-bordered table-hover"
								style="margin-top:5px;">
								<thead>
									<tr>
										
										
							<th class="center cy_th" style="width:30px;min-width:30px;">序号</th>
							<th id='cy_thk'></th>
							<th class="center">字段名称</th>
							<th class="center">字段</th>
							<th class="center">字段类型</th>
							<th class="center">长度</th>
							<th class="center">操作人</th>
							<th class="center" style="width:50px;">操作</th>
									</tr>
								</thead>

								<tbody>
									<!-- 开始循环 -->
									<c:choose>
										<c:when test="${not empty varList}">
											<c:if test="${QX.cha == 1 }">
												<c:forEach items="${varList}" var="var" varStatus="vs">
													<tr>
														
														<td class='center cy_td' style="width:30px;min-width:30px;">${vs.index+1}</td>
														<td id='cy_thk'></td>
														<td class='center'>${var.FIELDNAME}</td>
														<td class='center'>${var.FIELD}</td>
														<td class='center'>${var.FIELDTYPENAME}</td>
														<td class='center'>${var.FIELDNUM}</td>
														<td class='center'>${var.CREATEMAN}</td>
														<td class="center">
															<c:if test="${QX.edit != 1 && QX.del != 1 }">
															<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
															</c:if>
															<div class="hidden-sm hidden-xs btn-group">
																<c:if test="${QX.edit == 1 }">
																	<a class="cy_bj" title="编辑" onclick="edit('${var.ID}','${var.UID}');">
																		<i title="编辑"></i>
																	</a>
																</c:if>
																
																<c:if test="${QX.del == 1&&empty pd.action }">
																	<a style="margin-left:10px;" class="cy_sc" onclick="del('${var.ID}','${var.UID}');">
																		<i title="删除"></i>
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
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
		//$(top.hangge());//关闭加载状态
		//检索
		function tosearch(){
			//top.jzts();
			//$("#Form").submit();
		}
		$(function() {
			$('#example5').DataTable({
				'paging'      : true,
			     'lengthChange': false,
			     'searching'   : true,
			     'ordering'    : false,
			     'info'        : true,
			     'autoWidth'   : true
		    })
		});
		//删除
		function del(Id,Uid){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>customtemplatefield/delete.do?ID="+Id+"&UID="+Uid+"&tm="+new Date().getTime();
					$.get(url,function(data){
						//location.href="<%=path%>/customtemplatefield/list.do";
						var winId = "userWin";
						if(data=="success"){
							modals.openWin({
					          	winId: winId,
					          	title: '新增',
					          	width: '900px',
					          	height: '400px',
					          	url: '<%=basePath%>customtemplatefield/list.do?CUS_TEMP_ID=${pd.CUS_TEMP_ID}'
					          	/*, hideFunc:function(){
					             	 modals.info("hide me");
					          	},
					          	showFunc:function(){
					              	modals.info("show me");
					          	} */
					      	});
						}else{
							modals.info("该客户模板已经使用，不能删除");
						}
					  
					});
				}
			});
		}
		
		//修改
		function edit(Id,Uid){
			
			var winId = "userWin_child";
		  	modals.openWin({
	          	winId: winId,
	          	title: '模板字段修改',
	          	width: '400px',
	          	height: '400px',
	          	url: '<%=basePath%>customtemplatefield/goEdit.do?action=${pd.action}&UID="+Uid+"&ID='+Id
	          	/*, hideFunc:function(){
	             	 modals.info("hide me");
	          	},
	          	showFunc:function(){
	              	modals.info("show me");
	          	} */
	      	});
			
			
		}
		//新增
		function add(){
			var winId = "userWin_child";
		  	modals.openWin({
	          	winId: winId,
	          	title: '模板-字段新增',
	          	width: '400px',
	          	height: '400px',
	          	url: '<%=basePath%>customtemplatefield/goAdd.do?CUS_TEMP_ID=${pd.CUS_TEMP_ID}'
	          	/*, hideFunc:function(){
	             	 modals.info("hide me");
	          	},
	          	showFunc:function(){
	              	modals.info("show me");
	          	} */
	      	});
			
		}
		/*隐藏第一个row（搜索）  */
		$(function () {
					
			$('#example5_wrapper').find(".row:first").hide();
			
			
		});
		
		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>naire/excel.do';
		}
	</script>
</body>
</html>