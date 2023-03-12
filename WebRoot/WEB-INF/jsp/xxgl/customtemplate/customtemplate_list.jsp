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

</style>
</head>
<body class="no-skin">


	<div class="content-wrapper" style="width:100%;margin-left:0px;">
		
		
		
		<section class="container-fluid">
            <div class="seat-middle-top-x"></div>
            <div class="seat-middle-top">
                <div class="seat-middle-top-left">
                    <div class="seat-middle-top-left-bt">客户模板设置</div>
                    <div class="seat-middle-top-left-tp">
                    <c:if test="${QX.add == 1 }">
                        <a href="javascript:void (0)" onclick="add_t();">新增</a>
                    </c:if>
                    </div>
                </div>
                <div class="seat-middle-top-right">
					<input placeholder="搜  索" name="keywords" id="keywords" value="${pd.keywords }"> 
					<a href="javascript:void(0)">
					<img src="static/login/images/icon-search.png" onclick="tosearch()"></a>
				</div>
            </div>
            <div class="seat-middle">
               <!--  <div class="seat-middle-nr"> -->
                    
                    <div class="xtyh-middle-r zxzgl-middle-r">
                        <!-- <div class="box-body" > -->
				
							<table id="example2"
								class="table table-striped table-bordered table-hover"
								style="margin-top:5px;">
								<thead>
									<tr>
									
										<th class="center" style="width:50px;">序号</th>
										<th class="center">模板名称</th>
										<th class="center">创建人</th>
										<th class="center">创建时间</th>
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
														
														<td class='center' style="width: 30px;">${vs.index+1}</td>
														<td class='center'>${var.NAME}</td>
														<td class='center'>${var.CREATEMAN}</td>
														<td class='center'>${var.CREATEDATE}</td>
														<td class="center">
															<c:if test="${QX.edit != 1 && QX.del != 1 }">
																<span
																	class="label label-large label-grey arrowed-in-right arrowed-in"><i
																	class="ace-icon fa fa-lock" title="无权限"></i></span>
															</c:if>
															<div class="flex-position">
																<div class="flex-row">
																	<c:if test="${QX.edit == 1 }">
																		<div class="button-edit" title="编辑"
																			 onclick="edit_t('${var.ID}');" title="编辑">
																			<font class="button-content">编辑</font>
																		</div>
																		<%--																	<a class="cy_bj" title="编辑"--%>
																		<%--																		onclick="edit_t('${var.ID}');"> <i--%>
																		<%--																		--%>
																		<%--																		title="编辑"></i>--%>
																		<%--																	</a>--%>
																	</c:if>
																	<c:if test="${QX.edit == 1 }">
																		<div class="button-setting" style="margin-left:10px;"
																			 title="配置字段"
																			 onclick="setzd('${var.ID}');" title="配置字段">
																			<font class="button-content">配置</font>
																		</div>
																		<%--                                                        <a style="margin-left:10px;" title="配置字段" class="cy-pzd"--%>
																		<%--                                                           onclick="setzd('${var.ID}');"> <i--%>
																		<%--                                                                title="配置字段"></i>--%>
																		<%--                                                        </a>--%>
																	</c:if>
																	<c:if test="${QX.del == 1 }">
																		<div class="button-delete" style="margin-left:10px;"
																			 title="删除"
																			 onclick="del_t('${var.ID}');" title="删除">
																			<font class="button-content">删除</font>
																		</div>
																		<%--																	<a style="margin-left:10px;" class="cy_sc"--%>
																		<%--																		onclick="del_t('${var.ID}');"> <i--%>
																		<%--																		 title="删除"></i>--%>
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

					
                    </div>
              
            </div>
        </section>
        
        
		<%-- <section class="content">
			<div class="row">
				<div class="col-xs-12">
					<div class="box">
						<div class="box-header" >
							<c:if test="${QX.add == 1 }">
								<a class="btn btn-mini btn-success" onclick="add_t();">新增</a>
							</c:if>
						</div>
						<div class="box-body" >

							<table id="example2"
								class="table table-striped table-bordered table-hover"
								style="margin-top:5px;">
								<thead>
									<tr>
										<th class="center" style="width:35px;"><label
											class="pos-rel"><input type="checkbox" class="ace"
												id="zcheckbox" /><span class="lbl"></span></label></th>
										<th class="center" style="width:50px;">序号</th>
										<th class="center">模板名称</th>
										<th class="center">创建人</th>
										<th class="center">创建时间</th>
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
														<td class='center'>${var.NAME}</td>
														<td class='center'>${var.CREATEMAN}</td>
														<td class='center'>${var.CREATEDATE}</td>
														<td class="center">
															<c:if test="${QX.edit != 1 && QX.del != 1 }">
																<span
																	class="label label-large label-grey arrowed-in-right arrowed-in"><i
																	class="ace-icon fa fa-lock" title="无权限"></i></span>
															</c:if>
															<div>
																<c:if test="${QX.edit == 1 }">
																	<a class="btn btn-xs btn-success" title="编辑"
																		onclick="edit_t('${var.ID}');"> <i
																		class="ace-icon fa fa-pencil-square-o bigger-120"
																		title="编辑"></i>
																	</a>
																</c:if>
																<c:if test="${QX.edit == 1 }">
																	<a style="margin-left:10px;" class="btn btn-xs btn-primary" title="配置字段"
																		onclick="setzd('${var.ID}');"> <i
																		class="ace-icon fa fa-pencil-square-o bigger-120"
																		title="配置字段"></i>
																	</a>
																</c:if>
																<c:if test="${QX.del == 1 }">
																	<a style="margin-left:10px;" class="btn btn-xs btn-danger"
																		onclick="del_t('${var.ID}');"> <i
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
		</section> --%>
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
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
		//$(top.hangge());//关闭加载状态
		//检索
		function tosearch(){
			//top.jzts();
			var keywords=$("#keywords").val();
			location.href="<%=path%>/customtemplate/list.do?keywords="+encodeURI(encodeURI(keywords));
			//$("#Form_s").submit();
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
		function add_t(){
			var winId = "userWin_edit";
		  	modals.openWin({
	          	winId: winId,
	          	title: '新增',
	          	width: '600px',
	          	height: '200px',
	          	url: '<%=basePath%>customtemplate/goAdd.do'
	          
	      	});
			
		}
		
		//删除
		function del_t(Id,Uid){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>customtemplate/delete.do?ID="+Id+"&UID="+Uid+"&tm="+new Date().getTime();
					$.get(url,function(data){
						if(data=="success"){
							location.href="<%=path%>/customtemplate/list.do";	
						}else 	if(data=="error1"){
							modals.info("该模板已使用，不能删除");
						}
						
					});
				}
			});
		}
		
		//修改
		function edit_t(Id,Uid){
			
			var winId = "userWin_edit";
		  	modals.openWin({
	          	winId: winId,
	          	title: '客户模板修改',
	          	width: '600px',
	          	height: '200px',
	          	url: '<%=basePath%>customtemplate/goEdit.do?UID="+Uid+"&ID='+Id
	          	/*, hideFunc:function(){
	             	 modals.info("hide me");
	          	},
	          	showFunc:function(){
	              	modals.info("show me");
	          	} */
	      	});
			
			
		}
		
		//修改
		function setzd(Id){
			
			var winId = "userWin";
		  	modals.openWin({
	          	winId: winId,
	          	title: '客户模板-字段设置',
	          	width: '800px',
	          	height: '400px',
	          	url: '<%=basePath%>customtemplatefield/list.do?CUS_TEMP_ID='+Id
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
			window.location.href='<%=basePath%>customtemplate/excel.do';
		}
		$(function () {
			$(".row thead").find("th:first").addClass("cy_th");
			$(".row tbody").find("tr").find("td:first").addClass("cy_td");
			$(".row thead").find("th:first").after("<th id='cy_thk'></th>");
			$(".row tbody").find("tr").find("td:first").after("<td id='cy_thk'></td>");
			$('.seat-middle').find(".row:first").hide();
			
			$(".xtyh-middle-r").find(".row:last").addClass("row-zg");
			$(".xtyh-middle-r").find(".row:eq(1)").addClass("row-two");
		});
	</script>
</body>
</html>