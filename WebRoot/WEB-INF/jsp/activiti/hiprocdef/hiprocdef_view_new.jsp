<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/index/top.jsp"%>
	<!-- 日期框 (带小时分钟)-->
	<link rel="stylesheet" href="static/ace/css/bootstrap-datetimepicker.css" />
</head>
<body class="no-skin">
<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><li class="fa fa-remove"></li></button>
		<h5 class="modal-title">流程信息查看</h5>
	</div>
	
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
											<c:if test="${var.NAME_ == '附件'}">
												<td style="width:80px;text-align: right;padding-top: 10px;">${var.NAME_}</td>
												<td style="padding-top: 10px;">
													<!-- 切割字符串 -->
    												<c:set value="${ fn:split(var.TEXT_, ',') }" var="strList" />
    												<!-- 遍历 -->
    												<a href="javascript:void(0);" style="color: blue;cursor: pointer;" onclick="preSee(
												    <c:forEach items="${strList}" var="strname" varStatus="i"> 
												        <!-- EL表达式中的if  else -->
												        <c:choose>
												            <c:when test="${i.index == 0}">
												                '${strname}',
												            </c:when>
												            <c:when test="${i.index == 1}">
												            	'${strname}'
												            </c:when>
												            <c:otherwise>
												                );">${strname}</a>
												            </c:otherwise>
												        </c:choose>   
												    </c:forEach>
												    <!-- &emsp;&emsp;<span style="color: red;">请先填写附件后再进行审批</span> -->
												</td>
											</c:if>
											<c:if test="${var.NAME_ != 'RESULT' && var.NAME_ != '附件'}">
												<td style="width:80px;text-align: right;padding-top: 10px;">${var.NAME_ == 'USERNAME'?'登录用户':var.NAME_}</td>
												<td style="padding-top: 10px;">${var.TEXT_}</td>
											</c:if>
										</tr>
									</c:forEach>
									</table>
									<div id="zhongxin" style="padding-top: 0px;"></div>
									<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
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
											<td style="padding-top: 10px;"><!-- class='center'  -->
												<c:choose>
													<c:when test="${empty var.ASSIGNEE_}">
														<c:if test="${var.USERNAME != NULL}">
															【个人】
															<c:set value="${fn:split(var.USERNAME, ',') }" var="names" /> 
															<c:forEach items="${names}" var="mName"> 
															    <a onclick="viewUser('${mName}')" style="cursor:pointer;">${mName}</a>
															</c:forEach>
														</c:if>
														<c:if test="${not empty var.ROLE_NAME && not empty var.ROLE_NAME}">
															，
														</c:if>
														<c:if test="${var.ROLE_NAME != NULL}">
															【角色】${var.ROLE_NAME}
														</c:if>
													</c:when>
													<c:otherwise>
														<c:if test="${var.ASSIGNEE_ != NULL}">
															【个人】
															<a onclick="viewUser('${var.ASSIGNEE_}')" style="cursor:pointer;">
																${var.ASSIGNEE_}
															</a>
														</c:if>
													</c:otherwise>
												</c:choose>
											</td>
											<td class='center' style="padding-top: 10px;">${fn:substring(var.START_TIME_ ,0,19)}</td>
											<td class='center' style="padding-top: 10px;">
												<c:if test="${var.END_TIME_ == NULL}">正在审批……</c:if>
												<c:if test="${var.END_TIME_ != NULL}">${fn:substring(var.END_TIME_ ,0,19)}</c:if>
											</td>
											<td class='center'>${var.ZTIME}</td>
											<td style="padding-top: 10px;">
												<c:if test="${vs.index > 0 && var.TEXT_ == null}">
							                    	${var.DELETE_REASON_}
							                    </c:if>
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


	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- 日期框(带小时分钟) -->
	<script src="static/ace/js/date-time/moment.js"></script>
	<script src="static/ace/js/date-time/locales.js"></script>
	<script src="static/ace/js/date-time/bootstrap-datetimepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		
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
		
		//预览
		function preSee(fileId, filePath){
			//alert(fileId+","+filePath);
			$.ajax({
				url : "http://192.168.3.252:9090/api.do?fileId=" + fileId,
				data : {
					jsonParams : encodeURIComponent(JSON.stringify({
						method : 3, //api.do接口中的打开文档的方法编号
						params : {
							// userId: "1073", //userId如果为空则默认值为本机ip地址
							fileId : fileId,
							filePath : filePath
						//此路径是相对父路径（配置文件filePath）下的子路径
						}
					}))
				},
				type : "POST",
				dataType : "json",
				general : false,
				async : false,
				success : function(data) {
					console.log(data);
					if (data) {
						if (data.errorCode === "0") {
							rst = data.result;
							if (rst && rst.urls) {
								openUrls(rst.urls);
							} else {
								console.warn("ajax响应内容data.result有问题：" + rst);
							}
						} else {
							alert(data.errorMessage);
						}
					} else {
						console.warn("ajax响应内容为空!");
					}
				},
			});
		}
		
		/**
	     * 根据返回的url打开文件
	     * @param urls
	     * @param fileId
	     */
	    
		function openUrls(urls, fileId) {
			var mobileFlag;
			if ($.isArray(urls)) {
				if (mobileFlag || window.browserFlag === 3) {
					openUrl(urls[0]);
				} else {
					urls.forEach(function(url) {
						openUrl(url);
					});
				}
			} else if ($.type(urls) === "string") {
				openUrl(urls);
			} else {
				console.error("urls格式错误：");
				console.error(urls);
			}

			function openUrl(url) {
				if (mobileFlag || window.browserFlag === 3) {
					window.location.href = url;
				} else {
					window.open(url, "_blank");
				}
			}
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
		
		</script>
</body>
</html>