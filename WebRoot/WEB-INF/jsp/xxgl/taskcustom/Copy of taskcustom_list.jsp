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
														onclick="getCustom('${var.TEMPLATE_ID}','${var.TABLENAME}','${var.STATE}','${var.CUSTOM_TEMPLATE_ID}');">

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
																			onclick="qidong('1','${var.ID}','${var.TEMPLATE_ID}','${var.TABLENAME}');">执行</a>	
																		<a style="margin-left:5px;" class="btn btn-xs btn-success" onclick="choice_zx('${var.ID}','${var.TEMPLATE_ID}','${var.TABLENAME}');">分配</a>
																	</c:if>
																	
																</c:if>
																<c:if test="${var.STATE == '1' }">
																	<a class="btn btn-xs btn-success"
																			onclick="toExcel('${var.CUSTOM_TEMPLATE_ID}');">下载</a>
																	<a style="margin-left:5px"
																		class="btn btn-xs btn-success"
																		onclick="imp('${var.CUSTOM_TEMPLATE_ID}','${var.TABLENAME}');">导入</a>
																	<a style="margin-left:5px;" class="btn btn-xs btn-success" onclick="choice_zx('${var.ID}','${var.TEMPLATE_ID}','${var.TABLENAME}');">分配</a>
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

			<!-- 坐席人员列表 -->
			<div class="modal" id="myModal">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">关闭
							</button>
							<h4 class="modal-title" id="myModalLabel">
								坐席人员列表
							</h4>
						</div>
						<!-- /.box-header -->
						<div class="modal-body">
							<ul class="todo-list" id="todo-list" style="margin-top:0px;">
								
							</ul>
						</div>
						<!-- /.box-body -->
						<div id="fpid" class="modal-footer">
							
							<button type="button" style="margin-left:10px;"
								class="btn btn-default pull-right" onclick="pjfp()">
								<i class="fa fa-plus"></i> 平均分配
							</button>
							<!--  <button type="button" class="btn btn-default pull-right"
								onclick="fp()">
								<i class="fa fa-plus"></i> 分配
							</button>-->
						</div>
					</div>
				</div>		
			</div>
			<!-- /.box -->
			<!-- 坐席人员列表结束 -->

			<!-- TABLE: 客户信息 -->
			<div class="box box-info" >
			
				<div class="nav-tabs-custom">
						<ul class="nav nav-tabs" >
							<li id="tab_1_li" class="active"><a href="#tab_1" data-toggle="tab">我的任务</a></li>
							<li id="tab_2_li"><a href="#tab_2" data-toggle="tab">执行任务</a></li>
						</ul>
						<div class="tab-content">
							<div class="tab-pane active" id="tab_1">
								<table id="example2" class="table table-bordered table-hover">
									<thead>
										<tr>
											<th class="center" style="width:30px;">序号</th>
											<th class="center">任务类型</th>
											<th class="center">任务名称</th>
											<th class="center">完成期限</th>
											<th class="center">任务数量</th>
											<th class="center">完成任务数</th>
											<th class="center">进度</th>
											<th class="center">任务状态</th>
											<th class="center">执行人员</th>
											<th class="center" style="width:50px;">操作</th>
										</tr>
									</thead>
	
									<tbody>
										<!-- 开始循环 -->
										<c:choose>
											<c:when test="${not empty varList}">
												<c:forEach items="${varList}" var="var" varStatus="vs">
													<tr style="cursor:pointer"
														onclick="getCustom('${var.TEMPLATE_ID}','${var.TABLENAME}','${var.STATE}');">

														<td class='center' style="width: 30px;">${vs.index+1}</td>
														<td class='center'>${var.TYPENAME}</td>
														<td class='center'>${var.TASKNAME}</td>
														<td class='center'>${var.COMPLETEDATE}</td>
														<td class='center'>${var.NUM}</td>
														<td class='center' style="color:blue">${var.WCRW}</td>
														<td class='center'>
															  <div class="clearfix">
											                    <span class="pull-left"></span>
											                    <small class="pull-right">${var.WCJD}%</small>
											                  </div>
											                  <div class="progress xs">
											                    <div class="progress-bar progress-bar-green" style="width: ${var.WCJD}%;"></div>
											                  </div>
															
														</td>
														<td class='center'><c:if test="${var.STATE==0}">待执行任务</c:if>
															<c:if test="${var.STATE==1}">开始任务</c:if> <c:if
																test="${var.STATE==2}">结束任务</c:if></td>
														<td class='center'>${var.TASKMAN}</td>
														<td class="center">
															<div>
																<c:if test="${var.STATE == '1' }">
																	<a style="margin-left:10px" class="btn btn-mini btn-success" onclick="qidong('1','${var.ID}','${var.CUSTOM_TEMPLATE_ID}','${var.NAIRE_TEMPLATE_ID}','${var.TABLENAME}','${var.WCJD}','${var.ZXMAN}');">执行任务</a>
																</c:if>	
															</div>
														</td>
													</tr>

												</c:forEach>
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
							<!-- /.tab-pane -->
							<div class="tab-pane" id="tab_2" style="padding:0px;margin:0px">
							
								<!-- Main content -->
							    <section class="content">
							      <div class="row">
							      	<div style="height:50px;">
							      		<div style="width:60%;float:left;">
							      		 	
						                 	<div class="progress xs" style="width:90%;background:#ccc;height:20px;float:left">
						                   		<div class="progress-bar progress-bar-green" id="progress-bar"></div>
						                 	</div>
						                 	<div class="clearfix" style="float:left">
						                    	<span class="pull-left"></span>
						                    	<small class="pull-right" id="per_num"></small>
						                 	</div>
						                 </div>	
						                 <div style="width:15%;float:left;padding-left:10px;">
							      		 	<a onclick="rwqd()" class="btn btn-block btn-social btn-dropbox" style="width:120px;float:left">
							                	<i class="fa fa-tasks"></i>任务清单
							              	</a>
							             </div> 	
							             <div class="modal" id="myModal" style="background:#fff;width:100%;height:550px;margin-top:10px">
      										<div class="modal-header">
										        
										        <h3 style="float:left">任务清单</h3> <a href="#" style="float:right" class="btn btn-primary" data-dismiss="modal">关闭</a>
										    </div>
									      	<div class="modal-body">
												<table id="customtable" class="table table-bordered table-hover">
									
												</table>	
							             	</div>
									      <div class="modal-footer">
									      <a href="#" class="btn btn-primary" data-dismiss="modal">关闭</a>
									  	</div>
									</div>
									
							             <div style="width:10%;float:left;padding-left:10px;">	
							              	<button type="button" class="btn btn-block btn-info" style="width:100px;float:left;background:#FFA07A;border:#FFA07A" onclick="up()"><i class="fa fa-angle-double-left"></i>上一个</button>
						                 	<input type="hidden" id="TABLENAME" name="TABLENAME">
						                 	<input type="hidden" id="type" name="type">
						                 	<input type="hidden" id="CUSTOM_TEMPLATE_ID" name="CUSTOM_TEMPLATE_ID">
						                 	<input type="hidden" id="NAIRE_TEMPLATE_ID" name="NAIRE_TEMPLATE_ID">
						                 	<input type="hidden" id="ZXMAN" name="ZXMAN">
						                 	<input type="hidden" id="ID" name="ID">
						                 	
						                 </div>	
						                 <div style="width:10%;float:left;padding-left:10px;">	
							              	<button type="button" class="btn btn-block btn-info" style="width:100px;float:left;background:#FFA07A;border:#FFA07A" onclick="next()">下一个<i class="fa fa-angle-double-right"></i></button>
						                 </div>	
							      	</div>
							        <div class="col-md-3">
							          
							          	<!-- Profile Image -->
							          <div class="box box-primary">
							            <div class="box-body box-profile" style="" id="userstr">
							              
							            </div>
							            
							            <!-- /.box-body -->
							          </div>
							          <!-- /.box -->
							         
							        </div>
							        <!-- /.col -->
							        <div class="col-md-9" >
							          <div class="box box-primary">
							            <div class="box-header with-border">
							             
							            </div>
							            <!-- /.box-header -->
							            <div class="box-body no-padding" id="tmstr" style="min-height:450px;width:95%;">
							            	
							            </div>
							           <!--  <div class="box-body box-profile"  style="width:35%;float:left;padding-top:50px;display:none" id="jsstr">
							              	
							              	
							              
							            </div> -->
							             
							          </div>
							          <!-- /. box -->
							        </div>
							        <!-- /.col -->
							      </div>
									
							</div>
							<!-- /.tab-pane -->
							
						</div>
						<!-- /.tab-content -->
					</div>
					<!-- nav-tabs-custom -->
				
					
				<div class="box-header with-border" style="height:50px;">
					<h3 class="box-title" style="width:250px">客户信息</h3>
					<div class="box-tools pull-right" style="margin-left:150px">
						<a class="btn btn-mini btn-success" onclick="tj();">任务完成情况统计</a>
					</div>
				</div>
				<!-- /.box-header -->
				<div class="box-body" style="margin-top:50px;">
					<div class="table-responsive">
						
					</div>
					<!-- /.table-responsive -->
				</div>
				<!-- /.box-body -->
				<div class="box-footer clearfix"></div>
				<!-- /.box-footer -->
			</div>
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
		      'ordering'    : true,
		      'info'        : true,
		      'autoWidth'   : true
		    })
		});
		
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
		
		function choice_zx(){
			$("#myModal").modal("toggle");
			
		}

		function tj(){
			var winId = "userWin";
			var TEMPLATE_ID=$("#TEMPLATE_ID").val();
			var TABLENAME=$("#TABLENAME").val();
			var STATE=$("#STATE").val();
			var CUSTOM_TEMPLATE_ID=$("#CUS_TEMP_ID").val();
			if(TABLENAME==""){
				modals.info("还未选择任务");
				return false;
			}
		  	modals.openWin({
	          	winId: winId,
	          	title: '任务完成情况统计',
	          	width: '900px',
	          	height: '400px',
	          	url: "<%=basePath%>taskcustom/tj.do?TABLENAME="+TABLENAME+"&CUSTOM_TEMPLATE_ID="+CUSTOM_TEMPLATE_ID
	          	, hideFunc:function(){
	          		//location.href="<%=path%>/taskcustom/list.do";
	          	}
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
			
			var url = "<%=basePath%>taskcustom/CustomRandomSave.do?CUSTOM_TEMPLATE_ID="+CUSTOM_TEMPLATE_ID+"&STATE="+STATE+"&type=1&zxid="+zxid+"&TEMPLATE_ID="+TEMPLATE_ID+"&TABLENAME="+TABLENAME+"&tm="+new Date().getTime();
			$.get(url,function(data){
				//alert(data);
				var obj= JSON.parse(data);
				$("#todo-list").html("");
				$("#todo-list").append(obj.zxlbString);
				$("#customtable").html("");
				$("#customtable").append(obj.customString);
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
				$("#todo-list").html("");
				$("#todo-list").append(obj.zxlbString);
				$("#customtable").html("");
				$("#customtable").append(obj.customString);
			});
		}
		
		//删除
		function getCustom(TEMPLATE_ID,TABLENAME,STATE,CUS_TEMP_ID){
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
				
				$("#todo-list").html("");
				$("#todo-list").append(obj.zxlbString);
				$("#customtable").html("");
				$("#customtable").append(obj.customString);
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
							$("#customtable").html("");
							$("#customtable").append(obj.customString);
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