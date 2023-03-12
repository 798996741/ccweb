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
  <!-- iCheck for checkboxes and radio inputs -->
<link rel="stylesheet" href="static/ace/plugins/iCheck/all.css">
<style>
td{
	width: 100px; 
	max-width: 100px; 
	white-space: nowrap; 
	text-overflow: ellipsis; 
	overflow: hidden; 
}
th{
	width: 100px; 
	max-width: 100px; 
	white-space: nowrap; 
	text-overflow: ellipsis; 
	overflow: hidden; 
}
</style>
</head>
<body class="no-skin" >
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
										<th class="center" style="width:30px;">序号</th>
										<th class="center">任务类型</th>
										<th class="center">任务名称</th>
										<th class="center">完成期限</th>
										<th class="center">任务数量</th>
										<th class="center">进度</th>
										<th class="center">任务状态</th>
										
										<th class="center" style="width:50px;">操作</th>
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

														<td class='center' style="width: 30px;">${vs.index+1}</td>
														<td class='center'>${var.TYPENAME}</td>
														<td class='center'>${var.TASKNAME}</td>
														<td class='center'>${var.COMPLETEDATE}</td>
														<td class='center'>${var.NUM}</td>
														<td class='center'>
															<c:if test="${var.NUM!=0}">
															  <div class="clearfix">
											                    <span class="pull-left"></span>
											                    <small class="pull-right">${var.WCJD}%</small>
											                  </div>
											                  <div class="progress xs">
											                    <div class="progress-bar progress-bar-green" style="width: ${var.WCJD}%;"></div>
											                  </div>
															</c:if>
														</td>
														<td class='center'><c:if test="${var.STATE==0}">未执行</c:if>
															<c:if test="${var.STATE==1}">开始任务</c:if> <c:if
																test="${var.STATE==2}">结束任务</c:if></td>
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
																		<a style="margin-left:5px;" class="btn btn-xs btn-success" onclick="choice_zx('${var.ID}','${var.TEMPLATE_ID}','${var.TABLENAME}','${var.FPTYPE}');">分配</a>
																	</c:if>
																	
																</c:if>
																<c:if test="${var.STATE == '1' }">
																	<a class="btn btn-xs btn-success"
																			onclick="toExcel('${var.CUSTOM_TEMPLATE_ID}');">下载</a>
																	<a style="margin-left:5px"
																		class="btn btn-xs btn-success"
																		onclick="imp('${var.CUSTOM_TEMPLATE_ID}','${var.TABLENAME}');">导入</a>
																	<a style="margin-left:5px;" class="btn btn-xs btn-success" onclick="choice_zx('${var.ID}','${var.TEMPLATE_ID}','${var.TABLENAME}','${var.FPTYPE}','${var.CUSTOM_TEMPLATE_ID}');">分配</a>
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
			<input type="hidden" value="" id="STATE" name="STATE">
			<input type="hidden" value="" id="CUS_TEMP_ID" name="CUS_TEMP_ID">
			<input type="hidden" value="" id="FPTYPE_HID" name="FPTYPE_HID">
			<input type="hidden" value="" id="TASKID" name="TASKID">

			<!-- 坐席人员列表 -->
			<div class="modal" id="myModal">
				<div class="modal-dialog" style="width:900px;height:650px">
					<div class="modal-content">
						
					
						<div class="modal-header">
							<h4 class="modal-title" id="myModalLabel" style="float: left;">任务分配</h4>
							<button type="button" class="btn btn-info btn-sm" style="float: right;"   data-dismiss="modal" title="关闭">
		                 	<i class="fa fa-times"></i></button>
						</div>
						<!-- /.box-header -->
						<div class="form-group" style="padding-top:20px;padding-left:20px;padding-bottom:10px;">
			                <label id="zxfp" style="float:left;margin-left:20px;">
			                  	<input type="radio" name="FPTYPE" id="FPTYPE1" class="flat-red" onclick="choice_fp(1)"  value="1">&nbsp;&nbsp;&nbsp;&nbsp;按坐席人员随机分配
			                </label>
			                <label id="defp" style="float:left;margin-left:20px;">
			                  	<input type="radio" name="FPTYPE" id="FPTYPE2" class="flat-red" onclick="choice_fp(2)" value="2">&nbsp;&nbsp;&nbsp;&nbsp;定额分配
			                </label>
			            </div>
						<div class="modal-body" id="zxfpStr">
							<div id="todo-zxz" style="text-align:left;margin-bottom:5px;"></div>
							<ul class="todo-list" id="todo-list" style="margin-top:0px;">
								
							</ul>
							<button type="button" style="margin-left:10px;margin-top:20px;"
								class="btn btn-default pull-right" onclick="pjfp()">
								<i class="fa fa-plus"></i> 平均分配
							</button>
						</div>
						<div class="modal-body" id="defpStr" style="display:none">
							<div id="todo-zxzde" style="text-align:left;margin-bottom:5px;"></div>
							<div class="todo-list" id="de" style="text-align:left;width:100%">
								<div id="divselect" style="text-align:left"></div>
								<div id="divgroup" style="text-align:left"></div>
							</div>
							<button type="button" style="margin-left:10px;margin-top:20px;"
								class="btn btn-default pull-right" onclick="defp()">
								<i class="fa fa-plus"></i> 定额分配
							</button>
						</div>
						<!-- /.box-body -->
						<div id="fpid" class="modal-footer">
							<!--  <button type="button" class="btn btn-default pull-right"
								onclick="fp()">
								<i class="fa fa-plus"></i> 分配
							</button>-->
						</div>
					</div>
				</div>
				
			</div>
			<div class="modal" id="mytmModal">
				<div class="modal-dialog" style="width:900px;">
					<div class="modal-content">
						<div class="modal-header">
							<h4 class="modal-title" id="myModalLabel" style="float: left;">题目回答情况</h4>
							<button type="button" class="btn btn-info btn-sm" style="float: right;"   data-dismiss="modal" title="关闭">
		                 	<i class="fa fa-times"></i></button>
						</div>
						<!-- /.box-header -->
						<div class="modal-body">
							<div class="todo-list" id="tmstr" style="margin-top:0px;margin-right:10px;">
								
							</div>
						</div>
					</div>
				</div>
				
			</div>
			<!-- /.box -->
			<!-- 坐席人员列表结束 -->

			<!-- TABLE: 客户信息 -->
			<div class="nav-tabs-custom">
				<ul class="nav nav-tabs" >
					<li id="tab_1_li" class="active"><a href="#tab_1" data-toggle="tab">任务完成情况统计</a></li>
					<li id="tab_2_li"><a href="#tab_2" data-toggle="tab">客户信息</a></li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane active" id="tab_1">
						<table id="tjtable" class="table table-bordered table-hover">

						</table>
					</div>
					<!-- /.tab-pane -->
					<div class="tab-pane" id="tab_2"  style="padding:0px;margin:0px">
						
						<div id="customdiv">
							
						</div>
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
		      'autoWidth'   : true
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
					}
					if(FPTYPE=="2"){
						//alert(FPTYPE);
						document.getElementById("defpStr").style.display="block";
						document.getElementById("zxfpStr").style.display="none";
						//获取任务
						var CUS_TEMP_ID=$("#CUS_TEMP_ID").val();
						var TASKID=$("#TASKID").val();
						
						//alert(CUS_TEMP_ID);
						var url = "<%=basePath%>taskcustom/getFpfield.do?CUS_TEMP_ID="+CUS_TEMP_ID+"&TASKID="+TASKID+"&tm="+new Date().getTime();
						$.get(url,function(data){
							//alert(data);
							var obj= JSON.parse(data);
							$("#todo-zxzde").html("");
							$("#todo-zxzde").append(obj.zxlbstr);
							$("#divselect").html("");
							$("#divselect").append(obj.selString);
						});
						
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
				document.getElementById("defpStr").style.display="none";
				document.getElementById("zxfpStr").style.display="block";
			}
			if(FPTYPE=="2"){
				$("#FPTYPE2").iCheck("check"); 
				document.getElementById("defp").style.display="block";
				document.getElementById("zxfp").style.display="none";
				document.getElementById("defpStr").style.display="block";
				document.getElementById("zxfpStr").style.display="none";
				var CUS_TEMP_ID=$("#CUS_TEMP_ID").val();
				//alert(CUS_TEMP_ID);
				//alert(ID);
				var url = "<%=basePath%>taskcustom/getFpfield.do?TASKID="+ID+"&CUS_TEMP_ID="+CUS_TEMP_ID+"&tm="+new Date().getTime();
				$.get(url,function(data){
					//alert(data);
					var obj= JSON.parse(data);
					$("#divselect").html("");
					$("#divselect").append(obj.selString);
					$("#todo-zxzde").html("");
					$("#todo-zxzde").append(obj.zxlbstr);
					var isField=obj.isField;
					if(isField=="1"){
						changefield();	
					}
					
				});
			}
			if(FPTYPE==""){
				document.getElementById("defp").style.display="block";
				document.getElementById("zxfp").style.display="block";
				document.getElementById("defpStr").style.display="none";
				document.getElementById("zxfpStr").style.display="block";
			}
			$("#myModal").modal("toggle");
		}

		function choice_fp(FPTYPE){
			//alert("q");
			FPTYPE=$('input[name="FPTYPE"]:checked').val(); 
			//alert(FPTYPE);
			if(FPTYPE==""){
				FPTYPE=$('input[name="FPTYPE"]:checked').val(); 
				//alert(FPTYPE);
				if(FPTYPE=="1"){
					//document.getElementById("defp").style.display="none";
					//document.getElementById("zxfp").style.display="block";
					document.getElementById("defpStr").style.display="none";
					document.getElementById("zxfpStr").style.display="block";
				}
				if(FPTYPE=="2"){
					//$("#FPTYPE2").iCheck("check"); 
					//document.getElementById("defp").style.display="block";
					//document.getElementById("zxfp").style.display="none";
					document.getElementById("defpStr").style.display="block";
					document.getElementById("zxfpStr").style.display="none";
				}
			}
			
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
		  	<%-- modals.openWin({
	          	winId: winId,
	          	title: '任务完成情况统计',
	          	width: '900px',
	          	height: '400px',
	          	url: "<%=basePath%>taskcustom/tj.do?TABLENAME="+TABLENAME+"&CUSTOM_TEMPLATE_ID="+CUSTOM_TEMPLATE_ID
	          	, hideFunc:function(){
	          		//location.href="<%=path%>/taskcustom/list.do";
	          	}
	      	}); --%>
		  	
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
		
		//删除
		function pjfp(){
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
				modals.info("可以分配的人数为0，不需要分配");
				return false;
			}
			
			
			var conditions=document.getElementsByName("zxlbcheck");
			if(typeof conditions == 'undefined'||conditions==""){
				modals.info("无客户信息");
				return false;
			}
			var num=0;
			var zxid="";
			for(var i=0;i<conditions.length;i++){
			    if(conditions[i].checked==true){
			    	num++;
			    	if(zxid!=""){
			    		zxid=zxid+",";
			    	}
			    	zxid=zxid+conditions[i].value;
			    }
			}
			//alert(num);
			if(num<=0){
				modals.info("请选择坐席人员！");
				return false;
			}
			
		/* 	var customcheck=document.getElementsByName("customcheck");
			var numc=0;
			var cid="";
			for(var i=0;i<customcheck.length;i++){
			    if(customcheck[i].checked==true){
			    	numc++;
			    	if(cid!=""){
			    		cid=cid+",";
			    	}
			    	cid=cid+customcheck[i].value;
			    }
			}
			if(numc==0){
				modals.info("请选择一个客户！");
				return false;
			}
			 */
			var TASKID=$("#TASKID").val();
			var url = "<%=basePath%>taskcustom/CustomRandomSave.do?ID="+TASKID+"&CUSTOM_TEMPLATE_ID="+CUSTOM_TEMPLATE_ID+"&STATE="+STATE+"&type=1&zxid="+zxid+"&TEMPLATE_ID="+TEMPLATE_ID+"&TABLENAME="+TABLENAME+"&FPTYPE="+FPTYPE+"&tm="+new Date().getTime();
			$.get(url,function(data){
				//alert(data);
				//var obj= JSON.parse(data);
			/* 	$("#todo-list").html("");
				$("#todo-list").append(obj.zxlbString);
				$("#customdiv").html("");
				$("#customdiv").append(obj.customString);
				settable(); */
				location.href="<%=path%>/taskcustom/list.do";
			});
		}
		
		//删除
		function defp(){
			//alert(TEMPLATE_ID);
			var FPTYPE=$('input[name="FPTYPE"]:checked').val();
			if(FPTYPE==""){
				modals.info("还未选择分配方式！");
				return false;
			}
			var TASKID=$("#TASKID").val();
			var ZXZ=$("#ZXZDE").val();
			if(ZXZ==""){
				modals.info("请选择坐席分组！");
				return false;
			}
			//alert(TASKID);
			var FIELD=$("#FIELD").val();
			var TABLENAME=$("#TABLENAME").val();
			var STATE=$("#STATE").val();
			var CUSTOM_TEMPLATE_ID=$("#CUS_TEMP_ID").val();
			
			
			var groupstr=$("#groupstr").val();
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
			//alert(groupid);
			//alert(num);
			
			var url = "<%=basePath%>taskcustom/CustomGroupSave.do?FIELD="+FIELD+"&ZXZ="+ZXZ+"&groupstr="+encodeURI(encodeURI(groupstr))+"&CUSTOM_TEMPLATE_ID="+CUSTOM_TEMPLATE_ID+"&STATE="+STATE+"&type=1&groupid="+groupid+"&TASKID="+TASKID+"&ID="+TASKID+"&TABLENAME="+TABLENAME+"&FPTYPE="+FPTYPE+"&tm="+new Date().getTime();
			$.get(url,function(data){
				//alert(data);
				//var obj= JSON.parse(data);
				modals.info("操作成功！");
				location.href="<%=path%>/taskcustom/list.do";
				return false;
				//$("#todo-list").html("");
				//$("#todo-list").append(obj.zxlbString);
				//$("#customdiv").html("");
				//$("#customdiv").append(obj.customString);
				//settable();
			});
		}
		
		//删除
		function fp(){
			//alert(TEMPLATE_ID);
			var TEMPLATE_ID=$("#TEMPLATE_ID").val();
			var TABLENAME=$("#TABLENAME").val();
			var STATE=$("#STATE").val();
			
			var conditions=document.getElementsByName("zxlbcheck");
			if(typeof conditions == 'undefined'||conditions==""){
				modals.info("无客户信息");
				return false;
			}
			var num=0;
			var zxid="";
			for(var i=0;i<conditions.length;i++){
			    if(conditions[i].checked==true){
			    	num++;
			    	zxid=conditions[i].value;
			    }
			}
			if(num==0){
				modals.info("请选择一个坐席人员！");
				return false;
			}
			if(num>=2||num==0){
				modals.info("坐席人员只能选择一个！");
				return false;
			}
			
			var customcheck=document.getElementsByName("customcheck");
			var numc=0;
			var cid="";
			for(var i=0;i<customcheck.length;i++){
			    if(customcheck[i].checked==true){
			    	numc++;
			    	if(cid!=""){
			    		cid=cid+",";
			    	}
			    	cid=cid+customcheck[i].value;
			    }
			}
			if(numc==0){
				modals.info("请选择一个客户！");
				return false;
			}
			
			
			var url = "<%=basePath%>taskcustom/CustomSave.do?STATE="+STATE+"&type=1&zxid="+zxid+"&cid="+cid+"&TEMPLATE_ID="+TEMPLATE_ID+"&TABLENAME="+TABLENAME+"&tm="+new Date().getTime();
			$.get(url,function(data){
				//alert(data);
				var obj= JSON.parse(data);
				$("#todo-zxz").html("");
				$("#todo-zxz").append(obj.zxlbString);
				$("#customdiv").html("");
				$("#customdiv").append(obj.customString);
				settable();
			});
		}
		
		function changeZxz(){
			var TEMPLATE_ID=$("#TEMPLATE_ID").val();
			var TABLENAME=$("#TABLENAME").val();
			var STATE=$("#STATE").val();
			var ZXZ=$("#ZXZ").val();
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
				//alert(obj.zxlbString);
				$("#todo-list").html("");
				$("#todo-list").append(obj.zxlbstr);
			});
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
			//	alert(obj.zxlbString);
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
	</script>


</body>
</html>