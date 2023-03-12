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
				<div class="row">
					<div class="col-xs-12">
					
					<form action="nairetemplate/${msg }.do" name="Form_add" id="Form_add" method="post">
						<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">模板编码:</td>
								<td><input type="text" name="CODE" id="CODE" value="${pd.CODE}" maxlength="255" placeholder="这里输入模板编码" title="模板编码" style="width:60%;"/></td>
							</tr>
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">模板名称:</td>
								<td><input type="text" name="NAME" id="NAME" value="${pd.NAME}" maxlength="255" placeholder="这里输入模板名称" title="模板名称" style="width:60%;"/></td>
							</tr>
							<tr>
								<td style="width:125px;text-align: right;padding-top: 13px;">问卷模板:</td>
								<td>
									<select id="NAIRE_TEMPLATE_ID" name="NAIRE_TEMPLATE_ID" style="width:60%;" >
										<c:forEach items="${varNList}" var="var" varStatus="vs">
											<option value="${var.ID }" <c:if test="${pd.NAIRE_TEMPLATE_ID==var.ID}">selected</c:if>>${var.NAME }</option>
										</c:forEach>
									</select><span style="color:red">*</span>
								</td>
							</tr>
							
							<tr>
								<td style="width:125px;text-align: right;padding-top: 13px;">客户模板:</td>
								<td>
									<select id="CUSTOM_TEMPLATE_ID" name="CUSTOM_TEMPLATE_ID" style="width:60%;" >
										<c:forEach items="${varCList}" var="var" varStatus="vs">
											<option value="${var.ID }" <c:if test="${pd.CUSTOM_TEMPLATE_ID==var.ID}">selected</c:if>>${var.NAME }</option>
										</c:forEach>
									</select><span style="color:red">*</span>
								</td>
							</tr>
							
							<tr>
								<td style="width:125px;text-align: right;padding-top: 13px;">是否启用:</td>
								<td>
									<select id="ISUSE" name="ISUSE" style="width:60%;" >
										<option value="1" <c:if test="${pd.ISUSE==1}">selected</c:if>>启用</option>
										<option value="0" <c:if test="${pd.ISUSE==0}">selected</c:if>>不启用</option>
									</select>
									<span style="color:red">*</span>
								</td>
							</tr>
						
							
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger"  data-btn-type="cancel" data-dismiss="modal">取消</a>
								</td>
							</tr>
						</table>
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
		$(function() {
				//日期框
			//$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
			
			
		});
		
		
		function save(){
			if($("#CODE").val()==""){
				$("#CODE").tips({
					side:3,
		            msg:'请输入模板编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CODE").focus();
				return false;
			}
			if($("#NAME").val()==""){
				$("#NAME").tips({
					side:3,
		            msg:'请输入模板名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#NAME").focus();
				return false;
			}
			
			//$("#zhongxin").hide();
			//$("#zhongxin2").show();
			$.ajax({
            //几个参数需要注意一下
                type: "POST",//方法类型
                dataType: "html",//预期服务器返回的数据类型
                url: "template/${msg }.do" ,//url
                data: $('#Form_add').serialize(),
                success: function (result) {
                  	//alert(result.indexOf("成功"));//打印服务端返回的数据(调试用)
                    if (result.indexOf("成功")>=0) {
                    	location.href="<%=basePath%>template/list.do";
                    }else{
                    	if (result.indexOf("输入的编码已经存在")>=0) {
	                    	$("#CODE").tips({
	        					side:3,
	        		            msg:'模板编码已经存在',
	        		            bg:'#AE81FF',
	        		            time:2
	        		        });
	        				$("#CODE").focus();
                    	}else{
                    		modals.info("操作失败");
                    	}
                    }
                   
                },
                error : function() {
                    alert("异常！");
                }
            });
		}
		//判断用户名是否存在
		function hasCode(){
			var CODE = $("#CODE").val();
			$.ajax({
				type: "POST",
				url: '<%=basePath%>template/hasU.do',
		    	data: {CODE:CODE,tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				success: function(data){
					 if("success" == data.result){
						$("#userForm_add").submit();
						//$("#zhongxin").hide();
						//$("#zhongxin2").show();
					 }else{
						$("#loginname").css("background-color","#D16E6C");
						setTimeout("$('#loginname').val('此用户名已存在!')",500);
					 }
				}
			});
		}
		
	</script>
</body>
</html>