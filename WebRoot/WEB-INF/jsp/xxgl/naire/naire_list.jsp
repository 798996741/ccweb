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
	.box{
		    border-top:0;
	}
	.seat-middle-top{
	    margin: 15px 0 0 0!important; 
	}
</style>
</head>
<body class="no-skin">


	<div  style="width:100%;margin-left:0px;height:auto;">
		<div class="modal-header">
			<h4 class="modal-title" id="myModalLabel" style="float: left;">	</h4>
			<div  class="new-tb" style="float: right;"   data-dismiss="modal" title="关闭"></div>
		</div>
		<section class="xtyh-middle-r" style="background: #ecf0f5; border-radius: 0 0 15px 15px; ">
			<div class="row">
				<div class="col-xs-12">
					<div class="">
						<div class="box-header" >
							<c:if test="${QX.add == 1 &&empty pd.action}">
								<a class="btn btn-mini btn-success" onclick="add();">新增</a>
							</c:if>
							<%-- <c:if test="${not empty pd.action}">
								<a class="btn btn-mini btn-danger"  data-btn-type="cancel" data-dismiss="modal">取消</a>
							</c:if> --%>
						</div>
						<div style="margin:0 10px;">
						<div class="seat-middle-top">
			                <!-- <div class="seat-middle-top-left">
			                    <div class="seat-middle-top-left-tp smtl">
									<div class="" id="addtm">
										<a onclick="add();">新增题目</a>
									</div>
			                    </div>
			                </div> -->
			                <div class="seat-middle-top-right">
								<input placeholder="搜  索" name="keywords" id="keywords" value=""> 
								<a href="javascript:void(0)">
								<img src="static/login/images/icon-search.png" onclick="tosearch()"></a>
							</div>
			            </div>
			            </div>
			            
			            
						<div class="box-body xtyh-middle-r" style="background:#ecf0f5;    border-radius: 0 0 15px 15px;">

							<table id="example5"
								class="table table-striped table-bordered table-hover"
								style="margin-top:5px;">
								<thead>
									<tr>
										
										
										<th class="center cy_th">编号</th>
										<th id='cy_thk'></th>
										<th class="center">题目</th>
										<th class="center">答案</th>
										<th class="center" style="min-width:80px">备注信息</th>
										
										<c:if test="${empty pd.action}">
											<th class="center">创建人</th>
											<th class="center">创建时间</th>
										</c:if>
										<th class="center" style="min-width:80px">操作</th>
									</tr>
								</thead>

								<tbody>
									<!-- 开始循环 -->
									<c:choose>
										<c:when test="${not empty varList}">
											<c:if test="${QX.cha == 1 }">
												<c:forEach items="${varList}" var="var" varStatus="vs">
													<tr>
														
														
														<td class='center cy_td'>${var.CODE}</td>
														<td id='cy_thk'></td>
														<td class='center'>${var.SUBJECT}</td>
														<td class='center'>${var.ANSWER}</td>
														<td class='center'>${var.REMARK}</td>
														
														<c:if test="${empty pd.action}">
															<td class='center'>${var.CREATEMAN}</td>
															<td class='center'>${var.CREATEDATE}</td>
														</c:if>
														<td class="center">
															<c:if test="${QX.edit != 1 && QX.del != 1 }">
																<span
																	class="label label-large label-grey arrowed-in-right arrowed-in"><i
																	class="ace-icon fa fa-lock" title="无权限"></i></span>
															</c:if>
															<div class="hidden-sm hidden-xs flex-position btn-group">
																<div class="flex-row">
																<c:if test="${QX.edit == 1 }">
																	<div class="button-edit" title="编辑"
																		 onclick="edit('${var.ID}');" title="编辑">
																		<font class="button-content">编辑</font>
																	</div>
<%--																	<a class="cy_bj" title="编辑"--%>
<%--																		onclick="edit('${var.ID}');"> <i--%>
<%--																		title="编辑"></i>--%>
<%--																	</a>--%>
																	<div class="button-setting" style="margin-left:10px;"
																		 title="查看下一题"
																		 onclick="setnext('${var.ID}');" title="查看下一题">
																		<font class="button-content">查看</font>
																	</div>
<%--																	<a style="margin-left:10px;" class="cy-pzd" onclick="setnext('${var.ID}','${var.CODE}');"> <i title="查看下一题"></i></a>--%>
																</c:if>
																<c:if test="${QX.del == 1 &&empty pd.action}">
																	<div class="button-delete" style="margin-left:10px;"
																		 title="删除"
																		 onclick="del('${var.ID}');" title="删除">
																		<font class="button-content">删除</font>
																	</div>
<%--																	<a style="margin-left:10px;" class="cy_sc"--%>
																	<%--																		onclick="del('${var.ID}');"> <i--%>
																	<%--																		title="删除"></i>--%>
																	<%--																	</a>--%>
																</c:if>
																</div>
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
		function setnext(ID,CODE){
			var winId = "userWin_next";
		  	modals.openWin({
	          	winId: winId,
	          	title: '下一题设置',
	          	width: '800px',
	          	height: '400px',
	          	url: '<%=basePath%>naire/goNext.do?action=search&CODE='+CODE+'&ID='+ID
	      	});
		}
		
		//新增
		function add(){
			var winId = "userWin_child";
		  	modals.openWin({
	          	winId: winId,
	          	title: '问卷-题目新增',
	          	width: '900px',
	          	height: '400px',
	          	url: '<%=basePath%>naire/goAdd.do?NAIRE_TEMPLATE_ID=${pd.NAIRE_TEMPLATE_ID}'
	          
	      	});
			
		}
		
		//删除
		function del(Id,Uid){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>naire/delete.do?ID="+Id+"&NAIRE_TEMPLATE_ID=${pd.NAIRE_TEMPLATE_ID}&tm="+new Date().getTime();
					$.get(url,function(data){
						var winId = "userWin";
					  	modals.openWin({
				          	winId: winId,
				          	title: '新增',
				          	width: '900px',
				          	height: '400px',
				          	url: '<%=basePath%>naire/list.do?NAIRE_TEMPLATE_ID=${pd.NAIRE_TEMPLATE_ID}'
				          	/*, hideFunc:function(){
				             	 modals.info("hide me");
				          	},
				          	showFunc:function(){
				              	modals.info("show me");
				          	} */
				      	});
					});
				}
			});
		}
		
		//修改
		function edit(Id,Uid){
			//alert('${pd.action}');
			var winId = "userWin_child";
		  	modals.openWin({
	          	winId: winId,
	          	title: '问卷题目修改',
	          	width: '900px',
	          	height: '400px',
	          	url: '<%=basePath%>naire/goEdit.do?action=${pd.action}&NAIRE_TEMPLATE_ID=${pd.NAIRE_TEMPLATE_ID}&ID='+Id
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