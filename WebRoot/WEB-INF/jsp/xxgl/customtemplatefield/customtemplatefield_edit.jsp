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
					
					<form action="customtemplate/${msg }.do" name="Form_add" id="Form_add" method="post">
						<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
						<input type="hidden" name="CUS_TEMP_ID" id="CUS_TEMP_ID" value="${pd.CUS_TEMP_ID}"/>
						<div id="zhongxin" >
						<div id="table_report" class="table table-striped table-bordered table-hover new-wkj">
							
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">字段:</div>
									<div class="one-wk-r">
										<select id="FIELD_ID" name="FIELD_ID" >
											<c:forEach items="${varList}" var="var" varStatus="vs">
												<option value="${var.ID }" <c:if test="${pd.FIELD_ID==var.ID}">selected</c:if>>${var.FIELDNAME }</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<span>*</span>
							</div>
							
							<div class="new-bc">
								<c:if test="${empty action}">
									<a onclick="save();">保存</a>
								</c:if>
							
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
			//alert($("#FIELD_ID").val());
			if($("#FIELD_ID").val()==""||$("#FIELD_ID").val()==null){
				$("#FIELD_ID").tips({
					side:3,
		            msg:'请输入选择字段',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#FIELD_ID").focus();
				return false;
			}
			//$("#zhongxin").hide();
			//$("#zhongxin2").show();
			$.ajax({
            //几个参数需要注意一下
                type: "POST",//方法类型
                dataType: "html",//预期服务器返回的数据类型
                url: "customtemplatefield/${msg }.do" ,//url
                data: $('#Form_add').serialize(),
                success: function (result) {
                  //alert(result);//打印服务端返回的数据(调试用)
                  //modals.info('dd');
                  	//modals.info('保存成功');
                  	//alert(result.indexOf("error2"));
                  	if (result.indexOf("success_")>=0) {
	                  	modals.closeWin("userWin_child");
	                  	var winId = "userWin";
	        		  	modals.openWin({
	        	          	winId: winId,
	        	          	title: '新增',
	        	          	width: '900px',
	        	          	height: '400px',
	        	          	url: '<%=basePath%>customtemplatefield/list.do?CUS_TEMP_ID=${pd.CUS_TEMP_ID}'
	        	          	/*, hideFunc:function(){
	        	             	 modals.info("hide me");
	        	          	},
	        	          	showFunc:function(){
	        	              	modals.info("show me");
	        	          	} */
	        	      	});
                  	}else if (result.indexOf("error1")>=0) {
                  		//alert("选择的字段已经存在");
                  		modals.info('选择的字段已经存在');
                  	}else if (result.indexOf("error2")>=0) {
                  		//alert("该客户模板已创建，不能新增");
                  		modals.info('该客户模板已在使用中，不能新增');
                  	} 	 	
                	//location.href="<%=basePath%>customtemplate/list.do";
                },
                error : function() {
                    alert("异常！");
                }
            });
		}
		
		
		</script>
</body>
</html>