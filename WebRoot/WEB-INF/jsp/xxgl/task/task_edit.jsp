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
	
	<!-- 日期框 -->
<link rel="stylesheet" href="static/ace/css/datepicker.css" />
<!-- 日期框 -->
<link rel="stylesheet" href="plugins/ueditor/themes/default/css/ueditor.css"/>
<script>
	 
	</script>
</head>
<body class="no-skin">
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
	<!-- /section:basics/sidebar -->
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">
				<div class="modal-header">
					<h4 class="modal-title" id="myModalLabel" style="float: left;">	</h4>
                 	<div class="new-tb" style="float: right;" data-dismiss="modal" title="关闭"></div>
				</div>
				<div class="row">
					<div class="col-xs-12">
					
					<form action="taskuser/${msg }.do" name="Form_add" id="Form_add" method="post">
						<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
						<div id="zhongxin" >
						<div id="table_report" class="table table-striped table-bordered table-hover new-wkj">
						
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">任务类型:</div>
									<div class="one-wk-r">
										<select id="TYPE" name="TYPE"  >
											<c:forEach items="${dictList}" var="var" varStatus="vs">
												<option value="${var.DICTIONARIES_ID }" <c:if test="${pd.TYPE==var.DICTIONARIES_ID}">selected</c:if>>${var.NAME }</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<span>*</span>
							</div>
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">任务名称:</div>
									<div class="one-wk-r"><input type="text" name="TASKNAME" id="TASKNAME" value="${pd.TASKNAME}" maxlength="30" placeholder="这里输入任务名称" title="任务名称" /></div>
								</div>
								<span>*</span>
							</div>
							
							
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">问卷模板:</div>
									<div class="one-wk-r">
										<select id="NAIRE_TEMPLATE_ID" name="NAIRE_TEMPLATE_ID"  >
											<c:forEach items="${varNList}" var="var" varStatus="vs">
												<option value="${var.ID }" <c:if test="${pd.NAIRE_TEMPLATE_ID==var.ID}">selected</c:if>>${var.NAME }</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<span>*</span>
							</div>
							
							
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">客户模板:</div>
									<div class="one-wk-r">
										<c:if test="${empty isexist}">
											<select id="CUSTOM_TEMPLATE_ID" name="CUSTOM_TEMPLATE_ID" style="width:60%;" >
												<c:forEach items="${varCList}" var="var" varStatus="vs">
													<option value="${var.ID }" <c:if test="${pd.CUSTOM_TEMPLATE_ID==var.ID}">selected</c:if>>${var.NAME }</option>
												</c:forEach>
												
											</select>
											
										</c:if>
										<c:if test="${isexist=='1' }">
											<input type="hidden" name="isexist" id="isexist" value="${isexist}"/>
											<input type="hidden" name="CUSTOM_TEMPLATE_ID" id="CUSTOM_TEMPLATE_ID" value="${pd.CUSTOM_TEMPLATE_ID}"/>
											<select id="CUSTOM_TEMPLATE_ID_h" name="CUSTOM_TEMPLATE_ID_h" style="width:60%;" disabled>
												<c:forEach items="${varCList}" var="var" varStatus="vs">
													<option value="${var.ID }" <c:if test="${pd.CUSTOM_TEMPLATE_ID==var.ID}">selected</c:if>>${var.NAME }</option>
												</c:forEach>
											</select><!-- <span style="color:red">*客户信息已导入不能进行修改！</span> -->
										</c:if>
									</div>
								</div>
								<span>*</span>
							</div>
							
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">完成期限:</div>
									<div class="one-wk-r">
										<input class="span10 date-picker" name="COMPLETEDATE" id="COMPLETEDATE"  value="${pd.COMPLETEDATE}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly"  placeholder="完成期限" title="完成期限"/>
									</div>
								</div>
								<span>*</span>
							</div>
							
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">执行人员:</div>
									<div class="one-wk-r">
										<input type="text" name="TASKMAN" id="TASKMAN" value="${pd.TASKMAN}" maxlength="30" placeholder="这里输入执行人员" title="执行人员" />
									</div>
								</div>
								<span>*</span>
							</div>
							
							
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">任务状态:</div>
									<div class="one-wk-r">
										<select id="STATE" name="STATE"  >
											<option value="0" <c:if test="${pd.STATE==0}">selected</c:if>>未启动</option>
											<option value="1" <c:if test="${pd.STATE==1}">selected</c:if>>开始任务</option>
											<option value="2" <c:if test="${pd.STATE==2}">selected</c:if>>结束任务</option>
										</select>
									</div>
								</div>
								<span>*</span>
							</div>
							
							
							<div class="new-bc">
								<a  onclick="save();">保存</a>
								<!--  <a class="btn btn-mini btn-danger"  data-btn-type="cancel" data-dismiss="modal">取消</a>-->
							</div>
						
						</div>
						</div>
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
					</form>
					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.page-content -->
		</div>
	</div>
	<!-- /.main-content -->
</div>

<!-- /.main-container -->
<!-- 日期框 -->
<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
<script type="text/javascript" charset="utf-8">window.UEDITOR_HOME_URL = "<%=path%>/plugins/ueditor/";</script>
<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.all.js"></script>


		<script type="text/javascript">
		//$(top.hangge());
		//保存
		//var ue = UE.getEditor("DETAIL");
		$('.date-picker').datepicker({
			autoclose: true,
			todayHighlight: true,
			clearBtn: true
		});
		$(function() {
			
			
		});
		
		
		function save(){
			if($("#TASKNAME").val()==""){
				$("#TASKNAME").tips({
					side:3,
		            msg:'请输入任务名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TASKNAME").focus();
				return false;
			}
			if($("#COMPLETEDATE").val()==""){
				$("#COMPLETEDATE").tips({
					side:3,
		            msg:'请输入完成日期',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#COMPLETEDATE").focus();
				return false;
			}
			
			if($("#TASKMAN").val()==""){
				$("#TASKMAN").tips({
					side:3,
		            msg:'请输入执行人员',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TASKMAN").focus();
				return false;
			}
			
			
			$("#zhongxin").hide();
			$("#zhongxin2").show();
			$.ajax({
            //几个参数需要注意一下
                type: "POST",//方法类型
                dataType: "html",//预期服务器返回的数据类型
                url: "taskuser/${msg }.do" ,//url
                data: $('#Form_add').serialize(),
                success: function (result) {
                  // alert(result);//打印服务端返回的数据(调试用)
                    if (result.indexOf("success_")>=0) {
                    	location.href="<%=basePath%>taskuser/list.do";
                    }else{
                    	
                    }
                   
                },
                error : function() {
                    alert("异常！");
                }
            });
		}
		
		
		</script>
</body>
</html>