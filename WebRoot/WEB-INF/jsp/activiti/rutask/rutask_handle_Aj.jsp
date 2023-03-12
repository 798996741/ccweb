<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<!-- 日期框 (带小时分钟)-->
	<link rel="stylesheet" href="static/ace/css/bootstrap-datetimepicker.css" />
	<%@ include file="../../system/include/incJs_mx.jsp"%>
</head>
<body class="no-skin">
	<!-- /section:basics/navbar.layout -->
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><li class="fa fa-remove"></li></button>
		<h5 class="modal-title">流程办理</h5>
	</div>
	
	<input type="hidden" id="pg" value="${pg}" />
	<div class="modal-body">
		<div class="tabbable">
							<ul class="nav nav-tabs" id="myTab" style="margin-top: 10px;">
				              <li class="active"><a data-toggle="tab" href="#home"><i class="green icon-home bigger-110"></i>申请事项</a></li>
				              <li><a data-toggle="tab" href="#profile"><i class="green icon-cog bigger-110"></i>审批过程</a></li>
				              <li><a data-toggle="tab" href="#png"><i class="green icon-cog bigger-110"></i>流程图</a></li>
				            </ul>
							<div class="tab-content">
								<div id="home" class="tab-pane in active">
									<table id="table_report" class="table table-striped table-bordered table-hover" style="margin-top: 10px;">
									<th class="center" colspan="10">申请事项</th>
									<c:forEach items="${varList}" var="var" varStatus="vs">
										<tr>
											<c:if test="${var.NAME_ != 'RESULT'}">
												<td style="width:75px;text-align: right;padding-top: 10px;">${var.NAME_ == 'USERNAME'?'登录用户':var.NAME_}</td>
												<td style="padding-top: 10px;">${var.TEXT_}</td>
											</c:if>
										</tr>
									</c:forEach>
									</table>
									
									<c:if test="${null == pd.msg or pd.msg != 'admin' }">
									<form action="rutask/handleAj.do" name="Form" id="Form" method="post">
										<input type="hidden" name="ID_" id="ID_" value="${pd.ID_}"/>
										<input type="hidden" name="ASSIGNEE_" id="ASSIGNEE_" value=""/>
										<input type="hidden" name="PROC_INST_ID_" id="PROC_INST_ID_" value="${pd.PROC_INST_ID_}"/>
										<input type="hidden" name="msg" id="msg" value="yes"/>
										<div id="zhongxin" style="padding-top: 0px;">
										<table id="table_report" class="table table-striped table-bordered table-hover">
											<tr>
												<td style="width:60px;text-align: right;padding-top: 13px;">审批<br>&nbsp;意见:</td>
												<td colspan="10" id="omsg">
													<textarea  name="OPINION" id="OPINION" maxlength="4000" style="display:none" ></textarea>
													<script id="editor" type="text/plain" style="width:90%;height:130px;">${pd.DESCRIPTION}</script>
												</td>
											</tr>
											<tr>
												<td colspan="2" style="text-align: center;"><b>快捷输入:</b>
												<a class='btn btn-xs btn-primary' title='快捷输入:同意' onclick='doSet("同意 ")'>同意</a>&nbsp;&nbsp;
												<a class='btn btn-xs btn-primary' title='快捷输入:批准' onclick='doSet("批准 ")'>批准</a>&nbsp;&nbsp;
												<a class='btn btn-xs btn-primary' title='快捷输入:已阅' onclick='doSet("已阅 ")'>已阅</a>&nbsp;&nbsp;
												<a class='btn btn-xs btn-primary' title='快捷输入:不同意' onclick='doSet("不同意")'>不同意</a>&nbsp;&nbsp;
												</td>
												
											</tr>
										</table>
										<table id="table_report" class="table table-striped table-bordered table-hover">
											<tr>
												<td style="text-align: center;" colspan="10">
													<a class="btn btn-mini btn-success" onclick="handle('yes');"><i class="ace-icon glyphicon glyphicon-ok"></i>批准</a>
													<c:if test="${QX.add == 1 }">
														<a class="btn btn-mini btn-primary" onclick="handle('no');"><i class="ace-icon glyphicon glyphicon-remove"></i>驳回</a>
													</c:if>
													<c:if test="${QX.add == 1 }">
													<a class="btn btn-mini btn-xs btn-danger" onclick="del('${pd.PROC_INST_ID_}');">
														<i class="ace-icon fa fa-trash-o bigger-130" title="作废" style="float: left;"></i>作废
													</a>
													</c:if>
													<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
												</td>
												<c:if test="${QX.add == 1 }">
												<td width="320">
													指定下一办理对象：
													<input type="text" name="ASSIGNEE_2" id="ASSIGNEE_2" placeholder="不指定则默认对象" value=""  title="指定下一办理对象" style="width:150px;" readonly="readonly"/>
													<a class="btn btn-light btn-xs" onclick="clean();"  title="清空" style="margin-top: -3px;">清</a>
												</td>
												<td width="90">
													<a class="btn btn-xs btn-info" title="指定下一办理人(单人)" onclick="getUser1();">
														<i class="ace-icon glyphicon glyphicon-user"></i>
													</a>
													<a class="btn btn-xs btn-info" title="指定下一办理角色(此角色下所有人都可以办理)" onclick="getRole();">
														<i class="ace-icon fa fa-users"></i>
													</a>
												</td>
												</c:if>
											</tr>
										</table>
										</div>
										<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
									</form>
									</c:if>
									
								</div>
								<div id="profile" class="tab-pane">
									<table id="table_report" class="table table-striped table-bordered table-hover" style="margin-top: 10px;">
									<th colspan="10" class='center'>审批过程</th>
									<tr>
										<th class="center" style="width:50px;">步骤</th>
										<th class="center" style="width:150px;">任务节点</th>
										<th class="center" style="width:150px;">办理人</th>
										<th class="center" style="width:150px;"><i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>审批开始时间</th>
										<th class="center" style="width:150px;"><i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>审批结束时间</th>
										<th class="center" style="width:120px;">用时</th>
										<th class="center">审批意见</th>
									</tr>
									<c:forEach items="${hitaskList}" var="var" varStatus="vs">
										<tr>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center' style="padding-top: 10px;">${var.ACT_NAME_}</td>
											<td class='center' style="padding-top: 10px;">
												<c:if test="${var.ASSIGNEE_ != NULL}"><a onclick="viewUser('${var.ASSIGNEE_}')" style="cursor:pointer;"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i>${var.ASSIGNEE_}</a></c:if>
											</td>
											<td class='center' style="padding-top: 10px;">${fn:substring(var.START_TIME_ ,0,19)}</td>
											<td class='center' style="padding-top: 10px;">
												<c:if test="${var.END_TIME_ == NULL}">正在审批……</c:if>
												<c:if test="${var.END_TIME_ != NULL}">${fn:substring(var.END_TIME_ ,0,19)}</c:if>
											</td>
											<td class='center'>${var.ZTIME}</td>
											<td style="padding-top: 10px;" class="center">
												<c:forEach items="${fn:split(var.TEXT_,',fh,')}"   var="val" varStatus="dvs"  >
							                            <c:if test="${dvs.index == 0 }">${val}</c:if>
							                            <c:if test="${dvs.index == 1 }">
							                            	<a onclick="details('d${vs.index+1}')" style="cursor:pointer;" title="详情"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i>
							                            </c:if>
							                    </c:forEach>
							                    <textarea id="d${vs.index+1}" style="display: none;">${var.TEXT_}</textarea>
											</td>
										</tr>
									</c:forEach>
									</table>
								</div>
								<div id="png" class="tab-pane">
									<table id="table_report" class="table table-striped table-bordered table-hover" style="margin-top: 10px;">
									<th colspan="10" class='center'>流程图</th>
									<tr>
										<td class='center'><img alt="${pd.FILENAME }" src="${pd.imgSrc }"></td>
									</tr>
									<tr>
										<td class='center'><p class="text-warning bigger-110 orange" style="padding-top: 5px;">如果图片显示不全,点击最大化按钮</p></td>
									</tr>
									</table>
								</div>
							</div>
						</div>

	</div>
	
	<!-- 百度富文本编辑框-->
	<script type="text/javascript" charset="utf-8">window.UEDITOR_HOME_URL = "<%=path%>/plugins/ueditor/";</script>
	<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="plugins/ueditor/lang/zh-cn/zh-cn.js"></script>
	
	
	<!-- 百度富文本编辑框-->
	<!-- 确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- 日期框(带小时分钟) -->
	<script src="static/ace/js/date-time/moment.js"></script>
	<script src="static/ace/js/date-time/locales.js"></script>
	<script src="static/ace/js/date-time/bootstrap-datetimepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript" src="static/js/jquery.form.js"></script>
		<script type="text/javascript">
		<%--$(top.hangge());--%>
		$("#jzts").hide();
		//办理任务
		function handle(msg){
			$("#msg").val(msg);
			$("#OPINION").val(getContent());
			if($("#OPINION").val()==""){
				$("#omsg").tips({
					side:3,
		            msg:'请输入审批意见',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#OPINION").focus();
				return false;
			}else{
				$("#zhongxin").hide();
				$("#zhongxin2").show();
				var options  = {
			            url:"rutask/handleAj.do", 
			            type:'post',
			            beforeSend:function(xhr){},
			            success:function(data){
			　　　　　　		 var jsd = eval("("+data+")");
			　　　　　　		 if(jsd.error=="0"){
			　　　　　　			 $("#zhongxin").show();
			　					$("#zhongxin2").hide();
			　					modals.closeWin("userTaskWin");
			　					if($("#pg").val()=="plan"){
			　						location.href="<%=path%>/plan/olist.do";
			　					}else{
			　						location.href="<%=path%>/topic/olist.do";
			　					}
			　　　　　　		 }
			            },
			　　			complete:function(xhr){},
			    		error: function(xhr,status,msg){}
			　　	};
			    $("#Form").ajaxSubmit(options);
			}
			
		}
		
		//作废
		function del(Id){
			bootbox.prompt("请输入作废缘由?", function(result) {
				if(result != null){
					if('' == result)result = "未写作废缘由";
					top.jzts();
					var url = "<%=basePath%>ruprocdef/delete.do?PROC_INST_ID_="+Id+"&reason="+encodeURI(encodeURI(result))+"&tm="+new Date().getTime();
					$.get(url,function(data){
						$("#zhongxin").hide();
						$("#zhongxin2").show();
						top.Dialog.close();
					});
				}
			});
		}
		
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
		
		//审批意见详情页
		function details(htmlId){
			 var content = $("#"+htmlId).val().split(',fh,');
			 top.handleDetails(content[1]);
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Modal = false;			//有无遮罩窗口
			 diag.Drag=true;
			 diag.Title ="审批意见";
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.URL = '<%=basePath%>rutask/details.do';
			 diag.Width = 760;
			 diag.Height = 500;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}
		
		//选择办理人
		function getUser(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="选择办理人";
			 diag.URL = '<%=basePath%>user/listUsersForWindow.do';
			 diag.Width = 700;
			 diag.Height = 545;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.CancelEvent = function(){ //关闭事件
				 var USERNAME = diag.innerFrame.contentWindow.document.getElementById('USERNAME').value;
				 if("" != USERNAME){
					 $("#ASSIGNEE_").val(USERNAME);
					 $("#ASSIGNEE_2").val(USERNAME);
				 }
				diag.close();
			 };
			 diag.show();
		}
		
		function getUser1(){
			var winId = "user1Win";
			modals.openWin({
	          	winId: winId,
	          	title: '新增',
	          	width: '900px',
	          	height: '450px',
	          	url: '<%=basePath%>user/listUsersForWindow.do'
	          	/*, hideFunc:function(){
	             	 modals.info("hide me");
	          	},
	          	showFunc:function(){
	              	modals.info("show me");
	          	} */
	      	});
		}
		
		//选择角色
		function getRole(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="选择角色";
			 diag.URL = '<%=basePath%>role/roleListWindow.do?ROLE_ID=1';
			 diag.Width = 700;
			 diag.Height = 545;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.CancelEvent = function(){ //关闭事件
				 var RNUMBER = diag.innerFrame.contentWindow.document.getElementById('RNUMBER').value;
				 if("" != RNUMBER){
					 $("#ASSIGNEE_").val(RNUMBER);
					 $("#ASSIGNEE_2").val(RNUMBER);
				 }
				diag.close();
			 };
			 diag.show();
		}
		
		//清空下一任务对象
		function clean(){
		 	$("#ASSIGNEE_").val("");
		 	$("#ASSIGNEE_2").val("");
		}
		
		</script>
		<c:if test="${null == pd.msg or pd.msg != 'admin' }">
		<script type="text/javascript">
		//百度富文本
		setTimeout("ueditor()",500);
		function ueditor(){
			UE.getEditor('editor');
		}
		
		function doSet(text){
			UE.getEditor('editor').setContent(text);
		}
		
		//ueditor有标签文本
		function getContent() {
		    var arr = [];
		    arr.push(UE.getEditor('editor').getContent());
		    return arr.join("");
		}
		</script>
		</c:if>
</body>

</html>