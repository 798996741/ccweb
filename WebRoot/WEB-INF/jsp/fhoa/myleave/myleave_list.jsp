<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="../../system/include/incJs_mx.jsp"%>
<!-- 日期框 -->
<link rel="stylesheet" href="static/ace/css/datepicker.css" />
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<!-- Main content -->
		<div class="row">
			<div class="col-xs-12">
				<div class="cbk-middle">

					<div class="cbk-middle-one-bg-top"
						style="margin-bottom: 20px!important;">
						<div class="title">
							<div class="title-left">
								<form id="Form" action="<%=basePath%>myleave/list.do"
									method="post">
									<div class="cbk-middle-one-bg-top-lb">
										<span class="search-span">关键词:</span> <input type="text"
											name="keywords" value="${param.keywords}" id="keywords"
											class="search-input" placeholder="请输关键词检索条件"> 
										<span class="search-span">请假类型:</span>
										<select
											name="TYPE" id="TYPE" title="请假类型" style="width:100px;">
										</select>
										&nbsp;&nbsp;&nbsp;
										<div class="cbk-middle-one-bg-top-but">
											<div class="cbk-middle-one-bg-top-but-cx but-active">
												<a onclick="tosearch()">查询</a>
											</div>
											<c:if test="${QX.add == 1 }">
												<div class="cbk-middle-one-bg-top-but-cx but-active">
													<a onclick="add()">申请</a>
												</div>
											</c:if>

										</div>

									</div>
								</form>
							</div>

						</div>
					</div>
					<div class="cbk-middle-one-lb">

						<div class="box-body">

							<table id="example2" border="0" cellspacing="0" cellpadding="0">
								<thead>
									<tr>
										<th class="center" style="width:35px;">
											<label class="pos-rel">
												<input type="checkbox" class="ace"id="zcheckbox" /><span class="lbl"></span>
											</label>
										</th>
										<th class="center" style="width:50px;">序号</th>
										<th class="center">类型</th>
										<th class="center">开始时间</th>
										<th class="center">结束时间</th>
										<th class="center">时长</th>
										<th class="center">申请人</th>
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
																type='checkbox' name='ids' value="${var.MYLEAVE_ID}"
																class="ace" /><span class="lbl"></span></label></td>
														<td class='center' style="width: 30px;">${page.showCount*(page.currentPage-1)+vs.index+1}</td>
														<td class='center'>${var.TYPE}</td>
														<td class='center'>${var.STARTTIME}</td>
														<td class='center'>${var.ENDTIME}</td>
														<td class='center'>${var.WHENLONG}</td>
														<td class='center'><a
															onclick="viewUser('${var.USERNAME}')"
															style="cursor:pointer;"><i id="nav-search-icon"
																class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i>${var.USERNAME}</a></td>
														<td class="center"><c:if
																test="${QX.edit != 1 && QX.del != 1 }">
																<span
																	class="label label-large label-grey arrowed-in-right arrowed-in"><i
																	class="ace-icon fa fa-lock" title="无权限"></i></span>
															</c:if>
															<div class="hidden-sm hidden-xs btn-group">
																<c:if test="${QX.del == 1 }">
																	<a class="btn btn-xs btn-danger"
																		onclick="del('${var.MYLEAVE_ID}');"> <i
																		class="ace-icon fa fa-trash-o bigger-120" title="删除"></i>
																	</a>
																</c:if>
															</div>
															<div class="hidden-md hidden-lg">
																<div class="inline pos-rel">
																	<button
																		class="btn btn-minier btn-primary dropdown-toggle"
																		data-toggle="dropdown" data-position="auto">
																		<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
																	</button>

																	<ul
																		class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
																		<c:if test="${QX.del == 1 }">
																			<li><a style="cursor:pointer;"
																				onclick="del('${var.MYLEAVE_ID}');"
																				class="tooltip-error" data-rel="tooltip" title="删除">
																					<span class="red"> <i
																						class="ace-icon fa fa-trash-o bigger-120"></i>
																				</span>
																			</a></li>
																		</c:if>
																	</ul>
																</div>
															</div></td>
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

					<!-- /.box-header -->
					<!-- /.box-body -->
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
			<!-- /.content -->
		</div>
		<!-- /.main-container -->

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
			top.jzts();
			$("#Form").submit();
		}
		
		$(function() {
			
			var TYPE = "${pd.TYPE}";
			$.ajax({
				type: "POST",
				url: '<%=basePath%>dictionaries/getLevels.do?tm='+new Date().getTime(),
		    	data: {DICTIONARIES_ID:'283c69fdfeb34a71b2246784bb37dcc6'},//ce174640544549f1b31c8f66e01c111b 为请假类型ID
				dataType:'json',
				cache: false,
				success: function(data){
					$("#TYPE").html('<option value="" >类型</option>');
					 $.each(data.list, function(i, dvar){
						 if(TYPE == dvar.BIANMA){
							 $("#TYPE").append("<option value="+dvar.BIANMA+" selected='selected'>"+dvar.NAME+"</option>");
						 }else{
							 $("#TYPE").append("<option value="+dvar.BIANMA+">"+dvar.NAME+"</option>");
						 }
					 });
				}
		    	
			});
			$('#example2').DataTable({
		      'paging'      : true,
		      'lengthChange': false,
		      'searching'   : false,
		      'ordering'    : true,
		      'info'        : true,
		      'autoWidth'   : true
		    });
		});
		
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="请假申请";
			 diag.URL = '<%=basePath%>myleave/goAdd.do';
			 diag.Width = 600;
			 diag.Height = 360;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 $("#example2").tips({
							side:3,
				            msg:'已创建请假单,请到待办任务中提交申请',
				            bg:'#AE81FF',
				            time:3
				     });
					 setTimeout('tosearch()',3000);
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>myleave/delete.do?MYLEAVE_ID="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						tosearch();
					});
				}
			});
		}
		
		//修改
		function edit(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>myleave/goEdit.do?MYLEAVE_ID='+Id;
			 diag.Width = 600;
			 diag.Height = 360;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 tosearch();
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//批量操作
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++){
					  if(document.getElementsByName('ids')[i].checked){
					  	if(str=='') str += document.getElementsByName('ids')[i].value;
					  	else str += ',' + document.getElementsByName('ids')[i].value;
					  }
					}
					if(str==''){
						bootbox.dialog({
							message: "<span class='bigger-110'>您没有选择任何内容!</span>",
							buttons: 			
							{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
						});
						$("#zcheckbox").tips({
							side:1,
				            msg:'点这里全选',
				            bg:'#AE81FF',
				            time:8
				        });
						return;
					}else{
						if(msg == '确定要删除选中的数据吗?'){
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>myleave/deleteAll.do?tm='+ new Date().getTime(),
								data : {
									DATA_IDS : str
								},
								dataType : 'json',
								//beforeSend: validateData,
								cache : false,
								success : function(data) {
									$.each(data.list,function(i,list) {
										tosearch();
									});
								}
							});
						}
					}
				}
			});
			};

			//查看用户
			function viewUser(USERNAME) {
				if ('admin' == USERNAME) {
					bootbox
							.dialog({
								message : "<span class='bigger-110'>不能查看admin用户!</span>",
								buttons : {
									"button" : {
										"label" : "确定",
										"className" : "btn-sm btn-success"
									}
								}
							});
					return;
				}
				top.jzts();
				var diag = new top.Dialog();
				diag.Modal = false; //有无遮罩窗口
				diag.Drag = true;
				diag.Title = "资料";
				diag.URL = '<%=basePath%>user/view.do?USERNAME='+USERNAME;
				 diag.Width = 469;
				 diag.Height = 380;
				 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}
		
	</script>
</body>
</html>