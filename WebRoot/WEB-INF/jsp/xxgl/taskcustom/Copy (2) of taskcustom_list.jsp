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
<link rel="stylesheet" href="static/login/css/common.css">
<link rel="stylesheet" href="static/login/css/main.css">
<%@ include file="../../system/include/incJs_mx.jsp"%>
<!-- iCheck for checkboxes and radio inputs -->
<link rel="stylesheet" href="static/ace/plugins/iCheck/all.css">
<style>
td {
	width: 100px;
	max-width: 100px;
	white-space: nowrap;
	text-overflow: ellipsis;
	overflow: hidden;
}

th {
	width: 100px;
	max-width: 100px;
	white-space: nowrap;
	text-overflow: ellipsis;
	overflow: hidden;
}

ul li {
	list-style-type: none
}
</style>
<style>
body {
	background: none;
}
</style>
</head>
<body class="no-skin">
	<section class="content container-fluid">
		<div class="seat-middle-top-x"></div>
		<div class="seat-middle-top">
			<div class="seat-middle-top-left">
				<div class="seat-middle-top-left-bt">客户回访</div>
				<div class="seat-middle-top-left-tp">
					<c:if test="${QX.add == 1 }">
						<a onclick="add_task();" href="javascript:void (0)">新增</a>
					</c:if>
				</div>
			</div>
			<form action="#" method="get">
				<div class="seat-middle-top-right">
					<input placeholder="搜  索"> <a href="javascript:void(0)"><img
						src="static/login/images/icon-search.png"></a>
				</div>
			</form>
		</div>
		<div class="seat-middle">
			<div class="seat-middle-row">
				<c:forEach items="${varList}" var="var" varStatus="vs">
					<div class="seat-middle-one">
						<div class="seat-middle-one-top">
							<div class="seat-middle-one-top-l">
								<canvas id="myCanvas" width="150" height="150">您的浏览器不支持canvas！</canvas>
							</div>
							<div class="seat-middle-one-top-m">
								<h3>东南2</h3>
								<p>任务类型：任务类型1</p>
								<p>完成期限：2019-01-05</p>
								<p>分配模式：配额分配</p>
								<p>配额数：68 执行：1人 总记录:200</p>
							</div>
							<div class="seat-middle-one-top-r">
								<div class="seat-t1">
									<a href="javascript:void(0)">导入</a>
								</div>
								<div class="seat-t2">
									<a href="javascript:void(0)">导出</a>
								</div>
								<div class="seat-t3">
									<a href="javascript:void(0)">分配</a>
								</div>
								<div class="seat-t4">
									<a href="javascript:void(0)">执行</a>
								</div>
							</div>
						</div>
						<div class="seat-middle-one-bottom">
							<a href="javascript:void(0)">未执行</a>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</section>


	<div class="content-wrapper" style="margin-left:0px;">

		<section class="content">
			<div class="row">
				<div class="col-xs-12">
					<div class="box">
						<div class="box-header">
							<c:if test="${QX.add == 1&&pd.STATE=='0' }">
								<a class="btn btn-mini btn-success" onclick="add();">新增</a>
							</c:if>
						</div>
						<!-- /.box-header -->
						<div class="box-body">
							<table id="example2" class="table table-bordered table-hover">
								<thead>
									<tr>

										<th class="center">任务类型</th>
										<th class="center">任务名称</th>
										<th class="center" style="width:80px;">完成期限</th>
										<th class="center" style="width:50px;">总记录</th>
										<th class="center" style="width:150px;">已分配任务</th>
										<th class="center" style="width:60px;">分配模式</th>
										<th class="center" style="width:70px;">进度</th>
										<th class="center" style="width:60px;">任务状态</th>

										<th class="center" style="width:160px;">操作</th>
									</tr>
								</thead>

								<tbody>
									<!-- 开始循环 -->
									<c:choose>
										<c:when test="${not empty varList}">
											<c:if test="${QX.cha == 1 }">
												<c:forEach items="${varList}" var="var" varStatus="vs">
													<tr style="cursor:pointer"
														onclick="getCustom('${var.TEMPLATE_ID}','${var.TABLENAME}','${var.STATE}','${var.CUSTOM_TEMPLATE_ID}','${var.FPTYPE}','${var.ID}');">


														<td class='center'>${var.TYPENAME}</td>
														<td class='center'>${var.TASKNAME}</td>
														<td class='center' style="width:80px;">${var.COMPLETEDATE}</td>
														<td class='center' style="width:50px;">${var.ALLNUM}</td>
														<td class='center' style="width:150px;"
															title="${var.ZXQK}">${var.ZXQK}</td>
														<th class="center" style="width:60px;">
															<c:if test="${var.FPTYPE==1}">
																随机分配
															</c:if> <c:if test="${var.FPTYPE==2}">
																配额分配
															</c:if> <c:if test="${var.FPTYPE==3}">
																定额分配
															</c:if>
														</th>

														<td class='center' style="width:70px;"><c:if
																test="${var.NUM!=0}">
																<div class="clearfix">
																	<span class="pull-left"></span> <small
																		class="pull-right">${var.WCJD}%</small>
																</div>
																<div class="progress xs">
																	<div class="progress-bar progress-bar-green"
																		style="width: ${var.WCJD}%;"></div>
																</div>
															</c:if></td>
														<td class='center' style="width:60px;">
															<c:if test="${var.STATE==0}">未执行</c:if> 
															<c:if test="${var.STATE==1}">开始任务</c:if> 
															<c:if test="${var.STATE==2}">结束任务</c:if>
														</td>
														<td class="left">
															<div>
																<c:if test="${var.STATE == '0' }">
																	<c:if test="${QX.edit == 1 }">
																		<a class="btn btn-xs btn-success"
																			onclick="toExcel('${var.CUSTOM_TEMPLATE_ID}');">下载</a>
																		<a style="margin-left:5px"
																			class="btn btn-xs btn-success"
																			onclick="imp('${var.CUSTOM_TEMPLATE_ID}','${var.TABLENAME}');">导入</a>

																		<a style="margin-left:5px;"
																			class="btn btn-xs btn-success"
																			onclick="qidong('1','${var.ID}','${var.TEMPLATE_ID}','${var.TABLENAME}','${var.FPTYPE}');">执行</a>
																		<a style="margin-left:5px;"
																			class="btn btn-xs btn-success"
																			onclick="choice_zx('${var.ID}','${var.TEMPLATE_ID}','${var.TABLENAME}','${var.FPTYPE}');">分配</a>
																	</c:if>

																</c:if>
																<c:if test="${var.STATE == '1' }">
																	<a class="btn btn-xs btn-success"
																		onclick="toExcel('${var.CUSTOM_TEMPLATE_ID}');">下载</a>
																	<a style="margin-left:5px"
																		class="btn btn-xs btn-success"
																		onclick="imp('${var.CUSTOM_TEMPLATE_ID}','${var.TABLENAME}');">导入</a>
																	<a style="margin-left:5px;"
																		class="btn btn-xs btn-success"
																		onclick="choice_zx('${var.ID}','${var.TEMPLATE_ID}','${var.TABLENAME}','${var.FPTYPE}','${var.CUSTOM_TEMPLATE_ID}');">分配</a>
																	<a style="margin-left:5px;"
																		class="btn btn-xs btn-success"
																		onclick="qidong('2','${var.ID}','${var.TEMPLATE_ID}','${var.TABLENAME}');">结束</a>
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

						</div>
						<!-- /.box-body -->
					</div>
					<!-- /.box -->

				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->

			<input type="hidden" value="" id="TABLENAME" name="TABLENAME">
			<input type="hidden" value="" id="TEMPLATE_ID" name="TEMPLATE_ID">
			<input type="hidden" value="" id="STATE" name="STATE"> <input
				type="hidden" value="" id="CUS_TEMP_ID" name="CUS_TEMP_ID">
			<input type="hidden" value="" id="FPTYPE_HID" name="FPTYPE_HID">
			<input type="hidden" value="" id="TASKID" name="TASKID">

			<!-- 坐席人员列表 -->
			<div class="modal" id="myModal">
				<div class="modal-dialog" style="width:900px;min-height:650px">
					<div class="modal-content">
						<div class="modal-header">
							<h4 class="modal-title" id="myModalLabel" style="float: left;">任务分配</h4>
							<button type="button" class="btn btn-info btn-sm"
								style="float: right;" data-dismiss="modal" title="关闭">
								<i class="fa fa-times"></i>
							</button>
						</div>
						<!-- /.box-header -->
						<div class="form-group"
							style="padding-top:20px;padding-left:20px;padding-bottom:10px;">
							<label id="zxfp" style="float:left;margin-left:20px;"> <input
								type="radio" name="FPTYPE" id="FPTYPE1" class="flat-red"
								onclick="choice_fp(1)" value="1">&nbsp;&nbsp;&nbsp;&nbsp;随机分配
							</label> <label id="defp" style="float:left;margin-left:20px;"> <input
								type="radio" name="FPTYPE" id="FPTYPE2" class="flat-red"
								onclick="choice_fp(2)" value="2">&nbsp;&nbsp;&nbsp;&nbsp;配额分配
							</label> <label id="pefp" style="float:left;margin-left:20px;"> <input
								type="radio" name="FPTYPE" id="FPTYPE3" class="flat-red"
								onclick="choice_fp(3)" value="3">&nbsp;&nbsp;&nbsp;&nbsp;定额分配
							</label>
						</div>
						<div style="text-align:left;width:100%;border:1px #FFF solid">
							<div id="todo-zxz" style="margin:10px;text-align:left;width:80%"></div>
						</div>
						<div class="modal-body" id="zxfpStr" style="min-height:250px">
							<input type="hidden" value="" id="zxid" name="zxid">
							<ul class="todo-list" id="zx" style="margin-top:0px;">

							</ul>
						</div>
						<div class="modal-body" id="defpStr"
							style="display:none;min-height:250px">
							<input type="hidden" value="" id="dezxid" name="dezxid">
							<ul class="todo-list" id="de" style="text-align:left;width:100%">
							</ul>
							<ul class="todo-list" id="defield"
								style="text-align:left;width:100%;margin-top:10px;">
								<div id="divselect" style="text-align:left"></div>
							</ul>
							<ul class="todo-list" id="divgroup"
								style="text-align:left;width:100%;margin-top:10px;">
							</ul>
						</div>
						<div class="modal-body" id="pefpStr"
							style="display:none;min-height:250px">
							<input type="hidden" value="" id="pezxid" name="pezxid">
							<ul class="todo-list" id="pe" style="text-align:left;width:100%">

							</ul>
						</div>
						<!-- /.box-body -->
						<div id="fpid" class="modal-footer">
							<button type="button" class="btn btn-default" onclick="fp()">
								<i class="fa fa-plus"></i> 分配
							</button>
						</div>
					</div>
				</div>
			</div>
			<!-- 坐席人员选 -->
			<div class="modal" id="zxryModal">
				<div class="modal-dialog" style="width:700px;height:auto">
					<div class="modal-content">
						<div class="modal-header">
							<h4 class="modal-title" id="myModalLabel" style="float: left;">坐席人员选择</h4>
							<button type="button" class="btn btn-info btn-sm"
								style="float: right;" data-dismiss="modal" title="关闭">
								<i class="fa fa-times"></i>
							</button>
						</div>
						<!-- /.box-header -->
						<div class="modal-body" style="height:auto">
							<div class="todo-list" id="zxrystr"
								style="margin-top:0px;margin-right:10px;"></div>
						</div>
						<div class="modal-footer">
							<button type="button" onclick="qrchoice()"
								class="btn btn-primary">确定选择</button>
						</div>
					</div>
				</div>
			</div>
			<div class="modal" id="mytmModal">
				<div class="modal-dialog" style="width:900px;">
					<div class="modal-content">
						<div class="modal-header">
							<h4 class="modal-title" id="myModalLabel" style="float: left;">题目回答情况</h4>
							<button type="button" class="btn btn-info btn-sm"
								style="float: right;" data-dismiss="modal" title="关闭">
								<i class="fa fa-times"></i>
							</button>
						</div>
						<!-- /.box-header -->
						<div class="modal-body">
							<div class="todo-list" id="tmstr"
								style="margin-top:0px;margin-right:10px;"></div>
						</div>
					</div>
				</div>
			</div>

			<div class="modal" id="impFieldModal">
				<div class="modal-dialog" style="width:500px;">
					<div class="modal-content">
						<form action="" name="Form_Field" id="Form_Field"
							enctype="multipart/form-data" method="post"
							onsubmit="return save(this)">

							<div class="modal-header">
								<h4 class="modal-title" id="myModalLabel" style="float: left;">导入配额模板</h4>
								<button type="button" class="btn btn-info btn-sm"
									style="float: right;" data-dismiss="modal" title="关闭">
									<i class="fa fa-times"></i>
								</button>
							</div>
							<!-- /.box-header -->
							<div class="modal-body">
								<div id="zhongxin" style="padding-top: 13px;">
									<table id="table_report"
										class="table table-striped table-bordered table-hover">
										<tr>
											<td style="width:100px;text-align: right;padding-top: 13px;">配额模板导入:</td>
											<td><input id="fileField" type="file" name="fileField"
												accept="xlsx" size="130" /></td>
										</tr>
									</table>
								</div>
								<div id="zhongxin2" class="center" style="display:none">
									<br />
									<br />
									<br />
									<br />
									<br />
									<img src="static/images/jiazai.gif" /><br />
									<h4 class="lighter block green">提交中...</h4>
								</div>

							</div>
							<div class="modal-footer">
								<button type="button" onclick="impFieldSave()" id="buttonField"
									class="btn btn-primary">导入</button>
							</div>
						</form>
					</div>
				</div>
			</div>
			<!-- /.box -->
			<!-- 坐席人员列表结束 -->

			<!-- TABLE: 客户信息 -->
			<div class="nav-tabs-custom">
				<ul class="nav nav-tabs">
					<li id="tab_1_li" class="active"><a href="#tab_1"
						data-toggle="tab">任务完成情况统计</a></li>
					<li id="tab_2_li"><a href="#tab_2" data-toggle="tab">客户信息</a></li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane active" id="tab_1">
						<table id="tjtable" class="table table-bordered table-hover">

						</table>
					</div>
					<!-- /.tab-pane -->
					<div class="tab-pane" id="tab_2" style="padding:0px;margin:0px">

						<div id="customdiv"></div>
					</div>
					<!-- /.tab-pane -->

				</div>
				<!-- /.tab-content -->
			</div>
			<!-- nav-tabs-custom -->

			<!-- /.box -->
	</div>
	<!-- /.col -->
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
	<!-- iCheck 1.0.1 -->
	<script src="static/ace/plugins/iCheck/icheck.min.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript" src="static/js/jquery.form.js"></script>
	<script type="text/javascript">
		//$(top.hangge());//关闭加载状态
		//检索
		function tosearch(){
			//top.jzts();
			$("#Form").submit();
		}
		function checkall(obj){
			//alert(obj);
			if($(obj).is(':checked')){
				$('input[name="customcheck"]').each(function(){
					$(this).prop("checked",true);
				});
			}else{
				$('input[name="customcheck"]').each(function(){
					$(this).prop("checked",false);
				});
			}
		}
		
		
		function changeTab(action) {
     		//alert(action);
     		var tab_1_li = document.getElementById('tab_1_li');
     		var tab_2_li = document.getElementById('tab_2_li');
     		var tab_1 = document.getElementById('tab_1');
     		var tab_2 = document.getElementById('tab_2');
     		if(action==2){
     			tab_1_li.className='';
     			tab_1.className='tab-pane';
     			tab_2_li.className='active';
     			tab_2.className='tab-pane active';
     		}
     		if(action==1){
     			tab_2_li.className='';
     			tab_2.className='tab-pane';
     			tab_1_li.className='active';
     			tab_1.className='tab-pane active';
     		}
     		//alert(action);
     	}
		
		
		$(function() {
			
			$('input[name="checkall"]').on("click",function(){
				//alert("d");
				if($(this).is(':checked')){
					$('input[name="customcheck"]').each(function(){
						$(this).prop("checked",true);
					});
				}else{
					$('input[name="customcheck"]').each(function(){
						$(this).prop("checked",false);
					});
				}
			});
			$('#example2').DataTable({
		      'paging'      : true,
		      'lengthChange': false,
		      'searching'   : true,
		      'ordering'    : false,
		      'info'        : true,
		      'autoWidth'   : true,
		      'iDisplayLength': 3 //每页初始显示5条记录
		    });
			$('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
		     	checkboxClass: 'icheckbox_minimal-blue',
		      	radioClass: 'iradio_minimal-blue'
		    });
		    //Red color scheme for iCheck
		    $('input[type="checkbox"].minimal-red, input[type="radio"].minimal-red').iCheck({
		      	checkboxClass: 'icheckbox_minimal-red',
		      	radioClass: 'iradio_minimal-red'
		    });
		    //Flat red color scheme for iCheck
		    $('input[type="checkbox"].flat-red, input[type="radio"].flat-red').iCheck({
		      	checkboxClass: 'icheckbox_flat-green',
		      	radioClass: 'iradio_flat-green'
		    });
		    $("input:radio[name='FPTYPE']").on('ifChecked', function(event){
		    	var FPTYPE_HID=$('#FPTYPE_HID').val();
		    	var FPTYPE=$('input[name="FPTYPE"]:checked').val();
		    	//alert(FPTYPE_HID);
		    	//alert(FPTYPE);
		    	if(FPTYPE_HID==""){
					if(FPTYPE=="1"){
						document.getElementById("defpStr").style.display="none";
						document.getElementById("zxfpStr").style.display="block";
						document.getElementById("pefpStr").style.display="none";
					}
					if(FPTYPE=="2"){
						//alert(FPTYPE);
						document.getElementById("defpStr").style.display="block";
						document.getElementById("zxfpStr").style.display="none";
						document.getElementById("pefpStr").style.display="none";
						//获取任务
						var CUS_TEMP_ID=$("#CUS_TEMP_ID").val();
						var TASKID=$("#TASKID").val();
						
						//alert(CUS_TEMP_ID);
						var url = "<%=basePath%>taskcustom/getFpfield.do?CUS_TEMP_ID="+CUS_TEMP_ID+"&TASKID="+TASKID+"&tm="+new Date().getTime();
						$.get(url,function(data){
							var obj= JSON.parse(data);
							$("#divselect").html("");
							$("#divselect").append(obj.selString);
							
						});
					}
					if(FPTYPE=="3"){
						document.getElementById("defpStr").style.display="none";
						document.getElementById("zxfpStr").style.display="none";
						document.getElementById("pefpStr").style.display="block";
					}
				}	
		    });
			
		});
		
		function changefield(){
			var FIELD=$("#FIELD").val();
			var TABLENAME=$("#TABLENAME").val();
			var TASKID=$("#TASKID").val();
			//alert(TASKID);
			if(FIELD!=""){
				var url = "<%=basePath%>taskcustom/getMsgFpfield.do?TABLENAME="+TABLENAME+"&FIELD="+FIELD+"&TASKID="+TASKID+"&tm="+new Date().getTime();
				//alert(url);
				$.get(url,function(data){
					//alert(data);
					var obj= JSON.parse(data);
					$("#divgroup").html("");
					$("#divgroup").append(obj.groupString);
				});
			}else{
				$("#divgroup").html("");
			}
		}
		
		
		
		function add(){
			var winId = "userWin";
		  	modals.openWin({
	          	winId: winId,
	          	title: '新增',
	          	width: '900px',
	          	height: '400px',
	          	url: '<%=basePath%>taskuser/goAdd.do'
	          	/*, hideFunc:function(){
	             	 modals.info("hide me");
	          	},
	          	showFunc:function(){
	              	modals.info("show me");
	          	} */
	      	});
			
		}
		
		function choice_zx(ID,TEMPLATE_ID,TABLENAME,FPTYPE,CUS_TEMP_ID){
			//alert(FPTYPE);
			//$("#FPTYPE1").attr("checked",true); 
			//FPTYPE_HID
			//alert(ID);
			$("#TASKID").val(ID);
			$('#FPTYPE_HID').val(FPTYPE);
			$('#CUS_TEMP_ID').val(CUS_TEMP_ID);
			//alert(FPTYPE);
			if(FPTYPE=="1"){
				$("#FPTYPE1").iCheck("check"); 
				document.getElementById("defp").style.display="none";
				document.getElementById("zxfp").style.display="block";
				document.getElementById("pefp").style.display="none";
				document.getElementById("defpStr").style.display="none";
				document.getElementById("zxfpStr").style.display="block";
				document.getElementById("pefpStr").style.display="none";
				/*获取分配内容*/
				var url = "<%=basePath%>taskcustom/getFpCont.do?ID="+ID+"&TASKID="+ID+"&FPTYPE="+FPTYPE+"&TABLENAME="+TABLENAME+"&tm="+new Date().getTime();
				$.get(url,function(data){
					//alert(data);
					var obj= JSON.parse(data);
					$("#zx").html("");
					$("#zx").append(obj.zxlbstr);
					$("#zxid").val(obj.ZXIDSTR);
				});
			}
			if(FPTYPE=="2"){
				$("#FPTYPE2").iCheck("check"); 
				document.getElementById("defp").style.display="block";
				document.getElementById("zxfp").style.display="none";
				document.getElementById("pefp").style.display="none";
				document.getElementById("defpStr").style.display="block";
				document.getElementById("zxfpStr").style.display="none";
				document.getElementById("pefpStr").style.display="none";
				var CUS_TEMP_ID=$("#CUS_TEMP_ID").val();
				//alert(CUS_TEMP_ID);
				//alert(ID);
				var iszx="0";
				var url = "<%=basePath%>taskcustom/getFpCont.do?ID="+ID+"&TASKID="+ID+"&FPTYPE="+FPTYPE+"&TABLENAME="+TABLENAME+"&tm="+new Date().getTime();
				$.get(url,function(data){
					//alert(data);
					var obj= JSON.parse(data);
					$("#de").html("");
					$("#de").append(obj.zxlbstr);
					$("#dezxid").val(obj.ZXIDSTR);
					iszx="1";
				});
				
				var url = "<%=basePath%>taskcustom/getFpfield.do?TASKID="+ID+"&CUS_TEMP_ID="+CUS_TEMP_ID+"&tm="+new Date().getTime();
				$.get(url,function(data){
					//alert(data);
					var obj= JSON.parse(data);
					var isField=obj.isField;
					$("#divselect").html("");
					$("#divselect").append(obj.selString);
					if(isField=="1"){
						changefield();	
					}
				});
				
			}
			if(FPTYPE=="3"){
				$("#FPTYPE3").iCheck("check"); 
				document.getElementById("defp").style.display="none";
				document.getElementById("zxfp").style.display="none";
				document.getElementById("pefp").style.display="block";
				document.getElementById("defpStr").style.display="none";
				document.getElementById("zxfpStr").style.display="none";
				document.getElementById("pefpStr").style.display="block";
				var url = "<%=basePath%>taskcustom/getFpCont.do?ID="+ID+"&TASKID="+ID+"&FPTYPE="+FPTYPE+"&TABLENAME="+TABLENAME+"&tm="+new Date().getTime();
				$.get(url,function(data){
					//alert(data);
					var obj= JSON.parse(data);
					//alert(obj.zxlbstr);
					$("#pe").html("");
					$("#pe").append(obj.zxlbstr);
					$("#pezxid").val(obj.ZXIDSTR);
				});
			}
			$("#myModal").modal("toggle");
		}

	
		
		function tj(){
			
			var winId = "userWin";
			var TEMPLATE_ID=$("#TEMPLATE_ID").val();
			var TABLENAME=$("#TABLENAME").val();
			var STATE=$("#STATE").val();
			var TASKID=$("#TASKID").val();
			var CUSTOM_TEMPLATE_ID=$("#CUS_TEMP_ID").val();
			if(TABLENAME==""){
				modals.info("还未选择任务");
				return false;
			}
		  
		  	
		  	var url = "<%=basePath%>taskcustom/tj.do?ID="+TASKID+"&CUSTOM_TEMPLATE_ID="+CUSTOM_TEMPLATE_ID+"&TABLENAME="+TABLENAME+"&tm="+new Date().getTime();
			
		  	$.get(url,function(data){
				//alert(data);
				var obj= JSON.parse(data);
				$("#tjtable").html("");
				$("#tjtable").append(obj.tjString);
			});
		  	
		  	
		}
		
		//新增
		function imp(CUSTOM_TEMPLATE_ID,TABLENAME){
			var winId = "userWin";
			//alert(TEMPLATE_ID);
			//alert(TABLENAME);
		  	modals.openWin({
	          	winId: winId,
	          	title: '导入客户信息',
	          	width: '900px',
	          	height: '400px',
	          	url: "<%=basePath%>taskcustom/goImp.do?TABLENAME="+TABLENAME+"&CUSTOM_TEMPLATE_ID="+CUSTOM_TEMPLATE_ID
	          	, hideFunc:function(){
	          		location.href="<%=path%>/taskcustom/list.do";
	          	}
	      	});
			
		}
		
		//新增
		function impField(){
			var FIELD=$("#FIELD").val();
			var TABLENAME=$("#TABLENAME").val();
			if(FIELD==""){
				modals.info("请选择要分配的字段！");
				return false;
			}
			if(TABLENAME==""){
				modals.info("请选择分配的客户模板信息！");
				return false;
			}
			$("#impFieldModal").modal("toggle");
		}
		
		function impFieldSave(){
			if($("#fileField").val()==""){
				$("#fileField").tips({
					side:3,
		            msg:'请上传配额模板导入',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#fileField").focus();
				return false;
			}
			document.getElementById("buttonField").setAttribute("disabled", true);;
			document.getElementById("buttonField").value="正在导入中,请稍后...";
			var FIELD=$("#FIELD").val();
			var TABLENAME=$("#TABLENAME").val();
			var TASKID=$("#TASKID").val();
			if(FIELD==""){
				modals.info("请选择要分配的字段！");
				return false;
			}
			if(TABLENAME==""){
				modals.info("请选择分配的客户模板信息！");
				return false;
			}
			var url = "<%=basePath%>taskcustom/importexcelField.do?TABLENAME="+TABLENAME+"&FIELD="+FIELD+"&TASKID="+TASKID+"&tm="+new Date().getTime();
			$('#Form_Field').ajaxSubmit({
	            url: url,
	            type: 'post',
	            async: true,
	            dataType: "html",
	            success: function (data) {
	            	document.getElementById("buttonField").removeAttribute("disabled");
	            	document.getElementById("buttonField").value="导入Excel";
	            	//var obj= JSON.parse(data);
					$("#divgroup").html("");
					$("#divgroup").append(data);
					$("#impFieldModal").modal("hide");
	            },
	            error: function (data) {
	            	document.getElementById("buttonField").removeAttribute("disabled");
	            	document.getElementById("buttonField").value="导入Excel";
	            	var obj= JSON.parse(data);
	            	$("#divgroup").html("");
					$("#divgroup").append(data);
					$("#impFieldModal").modal("hide");
					
	            },
			 });
			//alert('${pd.TEMPLATE_ID}');
			
			
		}
		
		
		//删除
		function del(Id,Uid){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>taskuser/delete.do?ID="+Id+"&UID="+Uid+"&tm="+new Date().getTime();
					$.get(url,function(data){
						location.href="<%=path%>/taskcustom/list.do";
					});
				}
			});
		}
		
		function qidong(type,ID,TEMPLATE_ID,TABLENAME){
			//删除
				//alert(TEMPLATE_ID);
			var url = "<%=basePath%>taskcustom/qidong.do?TABLENAME="+TABLENAME+"&TEMPLATE_ID="+TEMPLATE_ID+"&ID="+ID+"&type="+type+"&tm="+new Date().getTime();
			$.get(url,function(data){
				//alert(data);
				if(data=="success_"){
					location.href="<%=path%>/taskcustom/list.do";	
				}else if(data=="error1"){
					modals.info("还未导入客户");
				}else if(data=="error2"){
					modals.info("客户还未分配坐席人员");
				}
				
			});
		}
		function changeZxz(){
			
		}
		
		//删除
		function fp(){
			//alert(TEMPLATE_ID);
			var FPTYPE=$('input[name="FPTYPE"]:checked').val();
			if(FPTYPE==""){
				modals.info("还未选择分配方式！");
				return false;
			}
			var TEMPLATE_ID=$("#TEMPLATE_ID").val();
			var TABLENAME=$("#TABLENAME").val();
			var STATE=$("#STATE").val();
			var CUSTOM_TEMPLATE_ID=$("#CUS_TEMP_ID").val();
			var kfprs=$("#kfprs").val();
			if(kfprs==0){
				modals.info("可以分配的人数为0，无法分配");
				return false;
			}
			var zxid="",zxnumstr="";
			var zxnum=0,zxallnum=0;
			if(FPTYPE=="1"){
				zxid=$("#zxid").val();
			}
			if(FPTYPE=="2"){
				zxid=$("#dezxid").val();
			}
			if(FPTYPE=="3"){
				zxid=$("#pezxid").val();
				var arr=zxid.split(",");
				for(var i=0;i<arr.length;i++){
					zxnum=$("#NUM"+arr[i]+"").val();
					if(zxnum!=""){
						if(isNaN(zxnum)){
							modals.info("第"+(i+1)+"个坐席人员分配的人数应为数字！");
							return false;
					    }
					}else{
						if(isNaN(zxnum)){
							modals.info("第"+(i+1)+"个坐席人员分配的人数不能为空！");
							return false;
					    }
					}
					zxallnum=parseInt(zxallnum)+parseInt(zxnum);
			    	if(zxnumstr!=""){
			    		zxnumstr=zxnumstr+",";
			    	}
			    	zxnumstr=zxnumstr+zxnum;
				}
				//alert(zxallnum+"kfprs:"+kfprs);
				if(zxallnum>kfprs){
					modals.info("分配人数为<span style=\"color:red\">"+zxallnum+"</span>人，不能大于可以分配的人数<span style=\"color:red\">"+kfprs+"</span>人！");
					return false;
				}
			}
			//return false;
			var ZXZ=$("#ZXZ").val();
			
			if(ZXZ==""){
				modals.info("请选择坐席组");
				return false;
			}
			
			if(zxid==""){
				modals.info("请选择坐席人员！");
				return false;
			}
			//alert(zxnumstr);
			if(FPTYPE=="3"||FPTYPE=="1"){
				var TASKID=$("#TASKID").val();
				var url = "<%=basePath%>taskcustom/CustomRandomSave.do?zxnumstr="+zxnumstr+"&ZXIDSTR="+zxid+"&ZXZ="+ZXZ+"&ID="+TASKID+"&CUSTOM_TEMPLATE_ID="+CUSTOM_TEMPLATE_ID+"&STATE="+STATE+"&type=1&TEMPLATE_ID="+TEMPLATE_ID+"&TABLENAME="+TABLENAME+"&FPTYPE="+FPTYPE+"&tm="+new Date().getTime();
				$.get(url,function(data){
					location.href="<%=path%>/taskcustom/list.do";
				});
			}else if(FPTYPE=="2"){
				//zxid=$("#dezxid").val();
				defp(zxid);
			}
			
		}
		
		//配额的分配
		function defp(zxid){
			
			var FPTYPE=$('input[name="FPTYPE"]:checked').val();
			if(FPTYPE==""){
				modals.info("还未选择分配方式！");
				return false;
			}
			var TASKID=$("#TASKID").val();
			
			//alert(TASKID);
			var FIELD=$("#FIELD").val();
			var ZXZ=$("#ZXZ").val();
			var TABLENAME=$("#TABLENAME").val();
			var STATE=$("#STATE").val();
			var CUSTOM_TEMPLATE_ID=$("#CUS_TEMP_ID").val();
			
			
			var groupstr=$("#groupstr").val();
			//alert(groupstr);
			var arr=groupstr.split(",");
			if(groupstr==""){
				modals.info("没有可分配的信息！");
				return false;
			}
			var num=0;
			var groupid="",group="";
			for(var i=0;i<arr.length;i++){
				group=$("#group"+i+"").val();
			    if(group!=""){
			    	if(groupid!=""){
			    		groupid=groupid+",";
			    	}
			    	groupid=groupid+group;
			    }else{
			    	modals.info(arr[i]+"无分配人员");
					return false;
			    }
			}
			
			var url = "<%=basePath%>taskcustom/CustomGroupSave.do?ZXIDSTR="+zxid+"&FIELD="+FIELD+"&ZXZ="+ZXZ+"&groupstr="+encodeURI(encodeURI(groupstr))+"&CUSTOM_TEMPLATE_ID="+CUSTOM_TEMPLATE_ID+"&STATE="+STATE+"&type=1&groupid="+groupid+"&TASKID="+TASKID+"&ID="+TASKID+"&TABLENAME="+TABLENAME+"&FPTYPE="+FPTYPE+"&tm="+new Date().getTime();
			$.get(url,function(data){
				modals.info("操作成功！");
				location.href="<%=path%>/taskcustom/list.do";
				return false;
			});
		}
		
		
		
		
		function getZxman(){
			var TEMPLATE_ID=$("#TEMPLATE_ID").val();
			var TABLENAME=$("#TABLENAME").val();
			var STATE=$("#STATE").val();
			var ZXZ=$("#ZXZ").val();
			var FPTYPE=$('input[name="FPTYPE"]:checked').val();
			if(FPTYPE==""||typeof(FPTYPE)=="undefined"){
				modals.info("还未选择分配方式！");
				return false;
			}
			var CUS_TEMP_ID=$("#CUS_TEMP_ID").val();
			if(TABLENAME==""){
				modals.info("还未选择任务");
				return false;
			}
			if(ZXZ==""){
				modals.info("请选择坐席组");
				return false;
			}
			
			var url = "<%=basePath%>taskcustom/getZxlb.do?CUSTOM_TEMPLATE_ID="+CUS_TEMP_ID+"&ZXZ="+ZXZ+"&TEMPLATE_ID="+TEMPLATE_ID+"&TABLENAME="+TABLENAME+"&tm="+new Date().getTime();
			$.get(url,function(data){
				//alert(data);
				var obj= JSON.parse(data);
				$("#zxryModal").modal("toggle");
				//alert(obj.zxlbString);
				$("#zxrystr").html("");
				$("#zxrystr").append(obj.zxlbstr);
			});
		}
		
		function qrchoice(){
			var FPTYPE=$('input[name="FPTYPE"]:checked').val();
			if(FPTYPE==""){
				modals.info("还未选择分配方式！");
				return false;
			}
			var TEMPLATE_ID=$("#TEMPLATE_ID").val();
			var TABLENAME=$("#TABLENAME").val();
			var STATE=$("#STATE").val();
			var CUSTOM_TEMPLATE_ID=$("#CUS_TEMP_ID").val();
			//var kfprs=$("#kfprs").val();
			//if(kfprs==0){
				//modals.info("可以分配的人数为0，不需要分配");
				//return false;
			//}
			
			var conditions=document.getElementsByName("zxlbcheck");
			if(typeof conditions == 'undefined'||conditions==""){
				modals.info("无客户信息");
				return false;
			}
			var num=0;
			var zxid="";
			var zxxm="";
			var zxlbstr="";
			var zxidhid="";
			if(FPTYPE=="1"){
				//$("#zx").html("");
				 zxidhid=$("#zxid").val();
			}
			if(FPTYPE=="2"){
				zxidhid=$("#dezxid").val();
			}
			if(FPTYPE=="3"){
				zxidhid=$("#pezxid").val();
			}
			//alert(zxidhid);
			var zxidarry="";
			if(zxidhid!=""){
				zxidarry=zxidhid.split(",")
			}
			var iscz="";
			for(var i=0;i<conditions.length;i++){
			    if(conditions[i].checked==true){
			    	iscz="";
			    	num++;
			    	
			    	
			    	for(var m=0;m<zxidarry.length;m++){
			    		//alert(conditions[i].value+"zxidarry[i]"+zxidarry[i]);
			    		if(conditions[i].value==zxidarry[m]){
			    			iscz="1";
			    			break;
			    		}
			    	}
			    	//alert(iscz);
			    	if(iscz==""){  //已经存在则不加入
			    		if(zxxm!=""){
				    		//zxid=zxid+",";
				    		zxxm=zxxm+",";
				    	}
				    	zxxm=$("#ZXXM"+conditions[i].value+"").val();
			    		if(zxidhid!=""){
			    			zxidhid=zxidhid+",";
				    	}
			    		zxidhid=zxidhid+conditions[i].value;
			    		
			    		if(FPTYPE=="1" || FPTYPE=="2"){
					    	zxlbstr=zxlbstr+"<li style=\"float:left;margin-left:5px;width:150px;\">";
							zxlbstr=zxlbstr+"<span class=\"text\">"+zxxm+"</span>";
							zxlbstr=zxlbstr+"</li>";
				    	}
				    	if(FPTYPE=="3"){
					    	zxlbstr=zxlbstr+"<li style=\"margin-left:5px;width:24%;float:left;\">";
					    	zxlbstr=zxlbstr+"<table style=\"width:100%;\">";
					    	zxlbstr=zxlbstr+"<tr style=\"height:30px;\">";
							zxlbstr=zxlbstr+"<td style=\"width:30%;text-align:right;font-size:14px;\">"+zxxm+":</td><td style=\"font-size:14px;\"><input type=\"number\" min=\"0\" style=\"width:60px;font-size:14px\" value=\"0\" id=\"NUM"+conditions[i].value+"\">人</td>";
							zxlbstr=zxlbstr+"</tr>";
							zxlbstr=zxlbstr+"</table>";
							zxlbstr=zxlbstr+"</li>";
				    	}	
			    	}
			    	zxid=zxidhid;
			    }
			}
			if(num<=0){
				modals.info("请选择坐席人员！");
				return false;
			}
			
			if(FPTYPE=="1"){
				//$("#zx").html("");
				$("#zx").append(zxlbstr);
				$("#zxid").val(zxid);
			}
			if(FPTYPE=="2"){
				//$("#de").html("");
				$("#de").append(zxlbstr);
				$("#dezxid").val(zxid);
			}
			if(FPTYPE=="3"){
				//$("#pe").html("");
				$("#pe").append(zxlbstr);
				$("#pezxid").val(zxid);
			}
			//alert(num);
			$("#zxryModal").modal("hide");
			
		}
		
		
		//删除
		function getCustom(TEMPLATE_ID,TABLENAME,STATE,CUS_TEMP_ID,FPTYPE,ID){
			$("#TASKID").val(ID);
			$("#FPTYPE_HID").val(FPTYPE);
			//alert(TEMPLATE_ID);
			$("#TEMPLATE_ID").val(TEMPLATE_ID);
			$("#CUS_TEMP_ID").val(CUS_TEMP_ID);
			$("#TABLENAME").val(TABLENAME);
			$("#STATE").val(STATE);
			if(STATE=='0'){
				//document.getElementById("fpid").style.display="block";
			}else{
				//document.getElementById("fpid").style.display="none";
			}
			var url = "<%=basePath%>taskcustom/getCustomList.do?CUSTOM_TEMPLATE_ID="+CUS_TEMP_ID+"&STATE="+STATE+"&TEMPLATE_ID="+TEMPLATE_ID+"&TABLENAME="+TABLENAME+"&tm="+new Date().getTime();
			$.get(url,function(data){
				//alert(data);
				var obj= JSON.parse(data);
				//alert(obj.zxlbString);
				$("#todo-zxz").html("");
				$("#todo-zxz").append(obj.zxlbString);
				$("#customdiv").html("");
				$("#customdiv").append(obj.customString);
				settable();
				tj();
				var ZXZ=$("#ZXZ").val();
				if(ZXZ!=""){
					changeZxz();
				}
			
			});
		}
		
		function choice(str){
			//$("input[name='customcheck']").attr("checked", false);
			//$("[name=customcheck]:checkbox").attr("checked",false);
			
			var arr=str.split(",");
			$("[name=customcheck]:checkbox").each(function(){
				//$(this).attr("checked",true);
				//alert($(this).val());
				boo=false;
				for(var i=0;i<arr.length;i++){
					if($(this).val()==arr[i]){
						boo=true;
					}
				}
				if(boo){
					//alert($(this).val());
					$("#"+$(this).val()+"").prop("checked", "checked");
					//alert($(this).val());
				}else{
					$("#"+$(this).val()+"").prop("checked", false);
				}
			});
			
		}
		//修改
		function edit(Id){
			
			var winId = "userWin";
			//alert(Id);
		  	modals.openWin({
	          	winId: winId,
	          	title: '新增',
	          	width: '900px',
	          	height: '400px',
	          	url: '<%=basePath%>taskuser/goEdit.do?ID='+Id
	          	/*, hideFunc:function(){
	             	 modals.info("hide me");
	          	},
	          	showFunc:function(){
	              	modals.info("show me");
	          	} */
	      	});
			
			
		}
		function settable(){
			 $('#customtable').DataTable({
		      'paging'      : true,
		      'lengthChange': false,
		      'searching'   : true,
		      'ordering'    : false,
		      'info'        : true,
		      'autoWidth'   : true
		    }) 
		}
		
		
		function search_cus(ID){
			var winId = "userWin";
			//alert(Id);
			var TEMPLATE_ID=$("#TEMPLATE_ID").val();
			var CUS_TEMP_ID=$("#CUS_TEMP_ID").val();
			var TABLENAME=$("#TABLENAME").val();
			var STATE=$("#STATE").val();
			//alert(TABLENAME);
		  	modals.openWin({
	          	winId: winId,
	          	title: '客户信息',
	          	width: '900px',
	          	height: '400px',
	          	url: "<%=basePath%>taskcustom/goSearch.do?CUSTOM_TEMPLATE_ID="+CUS_TEMP_ID+"&STATE="+STATE+"&TEMPLATE_ID="+TEMPLATE_ID+"&TABLENAME="+TABLENAME+"&ID="+ID
	          	/*, hideFunc:function(){
	             	 modals.info("hide me");
	          	},
	          	showFunc:function(){
	              	modals.info("show me");
	          	} */
	      	});
		}
		
		function search_answer(ID){
			var winId = "userWin";
			//alert(Id);
			
			
			var TEMPLATE_ID=$("#TEMPLATE_ID").val();
			var TABLENAME=$("#TABLENAME").val();
			var STATE=$("#STATE").val();
			var CUSTOM_TEMPLATE_ID=$("#CUS_TEMP_ID").val();
			if(TABLENAME==""){
				modals.info("还未选择任务");
				return false;
			}
		  	
		  	var url = "<%=basePath%>exetask/getAnswer.do?CUSTOM_TEMPLATE_ID="+CUSTOM_TEMPLATE_ID+"&TABLENAME="+TABLENAME+"&CUS_ID="+ID+"&tm="+new Date().getTime();
			$.get(url,function(data){
				//alert(data);
				var obj= JSON.parse(data);
				$("#tmstr").html("");
				$("#tmstr").append(obj.tmstr);
				$("#mytmModal").modal("toggle");
			});
			
			
		}
		
		//删除
		function del_cus(ID){
			var TEMPLATE_ID=$("#TEMPLATE_ID").val();
			var CUS_TEMP_ID=$("#CUS_TEMP_ID").val();
			var TABLENAME=$("#TABLENAME").val();
			var STATE=$("#STATE").val();
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					var url = "<%=basePath%>taskcustom/delete.do?ID="+ID+"&TABLENAME="+TABLENAME+"&tm="+new Date().getTime();
					$.get(url,function(data){
						
						if(STATE=='0'){
							//document.getElementById("fpid").style.display="block";
						}else{
							//document.getElementById("fpid").style.display="none";
						}
						var url = "<%=basePath%>taskcustom/getCustomList.do?CUSTOM_TEMPLATE_ID="+CUS_TEMP_ID+"&STATE="+STATE+"&TEMPLATE_ID="+TEMPLATE_ID+"&TABLENAME="+TABLENAME+"&tm="+new Date().getTime();
						$.get(url,function(data){
							//alert(data);
							var obj= JSON.parse(data);
							
							$("#todo-list").html("");
							$("#todo-list").append(obj.zxlbString);
							$("#customdiv").html("");
							$("#customdiv").append(obj.customString);
							
							settable();
						});
					});
				}
			});
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
								url: '<%=basePath%>taskuser/deleteAll.do?tm='+new Date().getTime(),
						    	data: {DATA_IDS:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
										 location.href="<%=path%>/taskuser/list.do";
									 });
								}
							});
						}
					}
				}
			});
		};
		
		//导出excel
		function toExcel(CUSTOM_TEMPLATE_ID){
			window.location.href='<%=basePath%>taskcustom/excel.do?CUSTOM_TEMPLATE_ID='+ CUSTOM_TEMPLATE_ID;
		}
		
		
		function toExcelField(TABLENAME){
			var FIELD=$("#FIELD").val();
			var TABLENAME=$("#TABLENAME").val();
			if(FIELD==""){
				modals.info("请选择要分配的字段！");
				return false;
			}
			if(TABLENAME==""){
				modals.info("请选择分配的客户模板信息！");
				return false;
			}
			window.location.href="<%=basePath%>taskcustom/excelField.do?FIELD="+FIELD+"&TABLENAME="+ TABLENAME;
		}
		
	</script>


</body>
</html>