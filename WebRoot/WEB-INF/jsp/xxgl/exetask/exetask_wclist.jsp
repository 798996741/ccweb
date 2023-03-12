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

ul li{
	list-style-type:none;
	min-height:45px;
	line-height:40px;
	height:auto;
}
</style>
</head>
<body class="no-skin">
	<div class="content-wrapper" style="width:100%;margin-left:0px;">
		
		<section class="content">
			<div class="row">
				<div class="col-md-6" style="width:100%">
					<!-- Custom Tabs -->
					<div class="nav-tabs-custom">
						<ul class="nav nav-tabs" >
							<li id="tab_1_li" class="active"><a href="#tab_1" data-toggle="tab">我的任务</a></li>
							<li id="tab_2_li"><a href="#tab_2" data-toggle="tab">详情</a></li>
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
															<a style="margin-left:10px" class="btn btn-mini btn-success" onclick="qidong('1','${var.ID}','${var.CUSTOM_TEMPLATE_ID}','${var.NAIRE_TEMPLATE_ID}','${var.TABLENAME}','${var.WCJD}','${var.ZXMAN}');">查看任务</a>
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
							<input type="hidden" id="TABLENAME" name="TABLENAME">
		                 	<input type="hidden" id="type" name="type">
		                 	<input type="hidden" id="CUSTOM_TEMPLATE_ID" name="CUSTOM_TEMPLATE_ID">
		                 	<input type="hidden" id="NAIRE_TEMPLATE_ID" name="NAIRE_TEMPLATE_ID">
		                 	<input type="hidden" id="ZXMAN" name="ZXMAN">
		                 	<input type="hidden" id="ID" name="ID">
		                 	<input type="hidden" id="STATE" name="STATE" value="${pd.STATE }">
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
												<h4 class="modal-title" id="myModalLabel" style="float: left;">任务清单</h4>
												<button type="button" class="btn btn-info btn-sm" style="float: right;"   data-dismiss="modal" title="关闭">
							                 	<i class="fa fa-times"></i></button>
											</div>
									      	<div class="modal-body">
												<table id="customtable" class="table table-bordered table-hover">
									
												</table>	
							             	</div>
									      <div class="modal-footer">
									  	</div>
									</div>
									
							             <div style="width:10%;float:left;padding-left:10px;">	
							              	<button type="button" class="btn btn-block btn-info" style="width:100px;float:left;background:#FFA07A;border:#FFA07A" onclick="up()"><i class="fa fa-angle-double-left"></i>上一个</button>
						                 	
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
			
			
			$('#example2').DataTable({
		      'paging'      : true,
		      'lengthChange': false,
		      'searching'   : true,
		      'ordering'    : true,
		      'info'        : true,
		      'autoWidth'   : true
		    });
		    $('#customtable').DataTable({
		      'paging'      : true,
		      'lengthChange': false,
		      'searching'   : true,
		      'ordering'    : true,
		      'info'        : true,
		      'autoWidth'   : true
		    })
		    
		});
		
		 

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
		function up(){
			var type=$("#type").val();
			var ID=$("#ID").val();
			var CUSTOM_TEMPLATE_ID=$("#CUSTOM_TEMPLATE_ID").val();
			var NAIRE_TEMPLATE_ID=$("#NAIRE_TEMPLATE_ID").val();
			var ZXMAN=$("#ZXMAN").val();
			var ROWNO=$("#cusnum").val();
			var TABLENAME=$("#TABLENAME").val();
			var WCJD="";
			if(ROWNO==1){
				modals.info("已经是第一个");
			}else{
				var url = "<%=basePath%>exetask/getTask.do?ROWNO="+(Number(ROWNO)-1)+"&TABLENAME="+TABLENAME+"&CUSTOM_TEMPLATE_ID="+CUSTOM_TEMPLATE_ID+"&NAIRE_TEMPLATE_ID="+NAIRE_TEMPLATE_ID+"&ID="+ID+"&type="+type+"&ZXMAN="+ZXMAN+"&tm="+new Date().getTime();
				$.get(url,function(data){
					//alert(data);
					var obj= JSON.parse(data);
					
					$("#userstr").html("");
					$("#userstr").append(obj.userstr);
					//alert("d");
					if(obj.userstr!=""){
						//alert("d");
						getTmByNaire(type,ID,CUSTOM_TEMPLATE_ID,NAIRE_TEMPLATE_ID,TABLENAME,WCJD,ZXMAN);
					}
					
				});
			}
			
		}
		
		
		function next(){
			var type=$("#type").val();
			var ID=$("#ID").val();
			var CUSTOM_TEMPLATE_ID=$("#CUSTOM_TEMPLATE_ID").val();
			var NAIRE_TEMPLATE_ID=$("#NAIRE_TEMPLATE_ID").val();
			var ZXMAN=$("#ZXMAN").val();
			var ROWNO=$("#cusnum").val();
			var TABLENAME=$("#TABLENAME").val();
			var last=$("#last").val();
			var WCJD="";
			if(last=="1"){
				modals.info("已经是最后一个任务");
			}else{
				var url = "<%=basePath%>exetask/getTask.do?ROWNO="+(Number(ROWNO)+1)+"&TABLENAME="+TABLENAME+"&CUSTOM_TEMPLATE_ID="+CUSTOM_TEMPLATE_ID+"&NAIRE_TEMPLATE_ID="+NAIRE_TEMPLATE_ID+"&ID="+ID+"&type="+type+"&ZXMAN="+ZXMAN+"&tm="+new Date().getTime();
				$.get(url,function(data){
					//alert(data);
					var obj= JSON.parse(data);
					
					$("#userstr").html("");
					$("#userstr").append(obj.userstr);
					//alert("d");
					if(obj.userstr!=""){
						//alert("d");
						getTmByNaire(type,ID,CUSTOM_TEMPLATE_ID,NAIRE_TEMPLATE_ID,TABLENAME,WCJD,ZXMAN);
					}
					
				});
			}
			
		}
		
		
		function zxrw(ROWNO){
			//alert(ROWNO);
			var type=$("#type").val();
			var ID=$("#ID").val();
			var CUSTOM_TEMPLATE_ID=$("#CUSTOM_TEMPLATE_ID").val();
			var NAIRE_TEMPLATE_ID=$("#NAIRE_TEMPLATE_ID").val();
			var ZXMAN=$("#ZXMAN").val();
			//var ROWNO=$("#cusnum").val();
			var TABLENAME=$("#TABLENAME").val();
			var WCJD="";
			//alert(ID);
			var url = "<%=basePath%>exetask/getTask.do?ROWNO="+Number(ROWNO)+"&TABLENAME="+TABLENAME+"&CUSTOM_TEMPLATE_ID="+CUSTOM_TEMPLATE_ID+"&NAIRE_TEMPLATE_ID="+NAIRE_TEMPLATE_ID+"&ID="+ID+"&type="+type+"&ZXMAN="+ZXMAN+"&tm="+new Date().getTime();
			$.get(url,function(data){
				$("#myModal").modal("hide");
				//alert(data);
				var obj= JSON.parse(data);
				
				$("#userstr").html("");
				$("#userstr").append(obj.userstr);
				//alert("d");
				if(obj.userstr!=""){
					//alert("d");
					getTmByNaire(type,ID,CUSTOM_TEMPLATE_ID,NAIRE_TEMPLATE_ID,TABLENAME,WCJD,ZXMAN);
				}
				
			});
			
		}
		
		function changeHFWJ(){
			var HFWJ=$("#HFWJ").val();
			//alert(HFWJ);
			if(HFWJ=="2"){
				document.getElementById("failid").style.display="block";
			}else{
				document.getElementById("failid").style.display="none";
			}
		}
		
		function rwqd(){
			
			
			var TABLENAME=$("#TABLENAME").val();
			var type=$("#type").val();
			var ID=$("#ID").val();
			var CUSTOM_TEMPLATE_ID=$("#CUSTOM_TEMPLATE_ID").val();
			var NAIRE_TEMPLATE_ID=$("#NAIRE_TEMPLATE_ID").val();
			var ZXMAN=$("#ZXMAN").val();
			
			var url = "<%=basePath%>exetask/getCustomtable.do?TABLENAME="+TABLENAME+"&CUSTOM_TEMPLATE_ID="+CUSTOM_TEMPLATE_ID+"&NAIRE_TEMPLATE_ID="+NAIRE_TEMPLATE_ID+"&ID="+ID+"&type="+type+"&ZXMAN="+ZXMAN+"&tm="+new Date().getTime();
			$.get(url,function(data){
				//alert(data);
				var obj= JSON.parse(data);
				$("#customtable").html("");
				$("#customtable").append(obj.customString);
				$("#myModal").modal("toggle");
				
				
			});
			//document.getElementById("rwqd").style.display="block";
		}
		function qidong(type,ID,CUSTOM_TEMPLATE_ID,NAIRE_TEMPLATE_ID,TABLENAME,WCJD,ZXMAN){
			//删除
				//alert(NAIRE_TEMPLATE_ID);
			changeTab('2');
			$("#per_num").html(WCJD+"%");
			$("#TABLENAME").val(TABLENAME);
			$("#type").val(type);
			$("#ID").val(ID);
			$("#CUSTOM_TEMPLATE_ID").val(CUSTOM_TEMPLATE_ID);
			$("#NAIRE_TEMPLATE_ID").val(NAIRE_TEMPLATE_ID);
			$("#ZXMAN").val(ZXMAN);
			
			document.getElementById("progress-bar").style.width=WCJD+"%";
			var url = "<%=basePath%>exetask/getTask.do?TABLENAME="+TABLENAME+"&CUSTOM_TEMPLATE_ID="+CUSTOM_TEMPLATE_ID+"&NAIRE_TEMPLATE_ID="+NAIRE_TEMPLATE_ID+"&ID="+ID+"&type="+type+"&ZXMAN="+ZXMAN+"&tm="+new Date().getTime();
			$.get(url,function(data){
				//alert(data);
				var obj= JSON.parse(data);
				
				$("#userstr").html("");
				$("#userstr").append(obj.userstr);
				$("#customtable").html("");
				$("#customtable").append(obj.customString);
				//alert("d");
				if(obj.userstr!=""){
					//alert("d");
					getTmByNaire(type,ID,CUSTOM_TEMPLATE_ID,NAIRE_TEMPLATE_ID,TABLENAME,WCJD,ZXMAN);
				}
				
			});
		}
		
		
		
		
		function hfresult(){
			var TABLENAME=$("#TABLENAME").val();
			var HFWJ=$("#HFWJ").val();
			var ZXMAN=$("#ZXMAN").val();
			var HFRESULT=$("#HFRESULT").val();
			var HFREMARK=$("#HFREMARK").val();
			
			var HFWJ=$("#HFWJ").val();
			if(HFWJ=="2"){
				if(HFRESULT==""){
					modals.info("请选择失败原因");
					return false;
				}
			}else{
				HFRESULT="";
			}
			
			var CUS_ID=$("#CUS_ID").val();
			if(CUS_ID==""){
				modals.info("无客户信息");
				return false;
			}
			var url = "<%=basePath%>taskcustom/editHF.do?ZXMAN="+ZXMAN+"&ID="+CUS_ID+"&TABLENAME="+TABLENAME+"&HFWJ="+HFWJ+"&HFRESULT="+HFRESULT+"&HFREMARK="+encodeURI(encodeURI(HFREMARK))+"&tm="+new Date().getTime();
			$.get(url,function(data){
				//alert(data);
				//$("#per_num").html("");
				///document.getElementById("progress-bar").style.width="0%";
				//$("#per_num").html("50%");
				//document.getElementById("progress-bar").style.width="50%";
				//alert("d");
				if (data.indexOf("success")>=0) {
					var str=data.split(":");
					modals.info("回访结果保存成功！");
					$("#per_num").html(str[1]+"%");
					document.getElementById("progress-bar").style.width=str[1]+"%";
                	return false;
                }else{
                	modals.info("保存失败！");
                	return false;
                }
			});
			
		}
		function  save_answer(NAIRE_TEMPLATE_ID,ROWNO,type,ID,CUSTOM_TEMPLATE_ID,TABLENAME,ZXMAN,NAIRE_ID,CODE,TYPENAME){
			
			var ANSWER="";
			if(TYPENAME=="多选题"){
				 $('input[name="ANSWER"]:checked').each(function(){ 
					 if(ANSWER!=""){ANSWER=ANSWER+",";}
					 ANSWER=ANSWER+$(this).val();
				     //chk_value.push($(this).val()); 
				}); 
			}else{
				ANSWER=$('input:radio[name="ANSWER"]:checked').val();
			}
			//alert(ANSWER);
			var CUS_ID=$("#CUS_ID").val();
			if(CUS_ID==""){
				modals.info("无客户信息");
				return false;
			}
			if(ANSWER==""){
				modals.info("请选择答案");
				return false;
			}
			var url = "<%=basePath%>exetask/saveAnswer.do?CUS_ID="+CUS_ID+"&TABLENAME="+TABLENAME+"&CUSTOM_TEMPLATE_ID="+CUSTOM_TEMPLATE_ID+"&NAIRE_TEMPLATE_ID="+NAIRE_TEMPLATE_ID+"&ID="+ID+"&type="+type+"&ZXMAN="+ZXMAN+"&NAIRE_ID="+NAIRE_ID+"&NUM="+CODE+"&ANSWER="+ANSWER+"&tm="+new Date().getTime();
			$.get(url,function(data){
				var url = "<%=basePath%>exetask/getTmByNaire.do?ROWNO="+ROWNO+"&CUS_ID="+CUS_ID+"&TABLENAME="+TABLENAME+"&CUSTOM_TEMPLATE_ID="+CUSTOM_TEMPLATE_ID+"&NAIRE_TEMPLATE_ID="+NAIRE_TEMPLATE_ID+"&ID="+ID+"&type="+type+"&ZXMAN="+ZXMAN+"&NAIRE_ID="+NAIRE_ID+"&NUM="+CODE+"&ANSWER="+ANSWER+"&tm="+new Date().getTime();
				$.get(url,function(data){
					//alert(data);
					var obj= JSON.parse(data);
					
					var isjs=obj.isjs;
					$("#tmstr").html("");
					$("#tmstr").append(obj.tmstr);
					//alert(isjs);
					if(isjs=='1'){
						//alert(obj.custom);
						$("#HFRESULT").val(obj.HFRESULT);
						$("#HFWJ").val(obj.HFWJ);
						//document.getElementById("jsstr").style.display="block";
					}
				});
			});	
		}
		
		
		function  end_answer(NAIRE_TEMPLATE_ID,ROWNO,type,ID,CUSTOM_TEMPLATE_ID,TABLENAME,ZXMAN,NAIRE_ID,CODE,TYPENAME){
			var CUS_ID=$("#CUS_ID").val();
			if(CUS_ID==""){
				modals.info("无客户信息");
				return false;
			}		
			var url = "<%=basePath%>exetask/getTmByNaire.do?ROWNO="+ROWNO+"&CUS_ID="+CUS_ID+"&TABLENAME="+TABLENAME+"&CUSTOM_TEMPLATE_ID="+CUSTOM_TEMPLATE_ID+"&NAIRE_TEMPLATE_ID="+NAIRE_TEMPLATE_ID+"&ID="+ID+"&type="+type+"&ZXMAN="+ZXMAN+"&NAIRE_ID="+NAIRE_ID+"&NUM="+CODE+"&tm="+new Date().getTime();
			$.get(url,function(data){
				//alert(data);
				var obj= JSON.parse(data);
				
				var isjs=obj.isjs;
				$("#tmstr").html("");
				$("#tmstr").append(obj.tmstr);
				//alert(isjs);
				if(isjs=='1'){
					//alert(obj.custom);
					$("#HFRESULT").val(obj.HFRESULT);
					$("#HFWJ").val(obj.HFWJ);
					//document.getElementById("jsstr").style.display="block";
				}
			});
		}
		
		function getTmByNaire(type,ID,CUSTOM_TEMPLATE_ID,NAIRE_TEMPLATE_ID,TABLENAME,WCJD,ZXMAN){
			//删除
				//alert(CUS_ID);
			var CUS_ID=$("#CUS_ID").val();
			if(CUS_ID==""){
				modals.info("无客户信息");
				return false;
			}
			var url = "<%=basePath%>exetask/getTmByNaire.do?CUS_ID="+CUS_ID+"&TABLENAME="+TABLENAME+"&CUSTOM_TEMPLATE_ID="+CUSTOM_TEMPLATE_ID+"&NAIRE_TEMPLATE_ID="+NAIRE_TEMPLATE_ID+"&ID="+ID+"&type="+type+"&ZXMAN="+ZXMAN+"&tm="+new Date().getTime();
			$.get(url,function(data){
				//alert(data);
				var obj= JSON.parse(data);
				var isjs=obj.isjs;
				$("#tmstr").html("");
				$("#tmstr").append(obj.tmstr);
				//alert(isjs);
				if(isjs=='1'){
					$("#HFRESULT").val(obj.custom.HFRESULT);
					$("#HFWJ").val(obj.custom.HFWJ);
				}
			});
		}
		
		
		//删除
		function getCustom(TEMPLATE_ID,TABLENAME,STATE){
			
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