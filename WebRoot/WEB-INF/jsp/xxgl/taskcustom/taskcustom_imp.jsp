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
<script  type="text/javascript"  src="static/js/jquery.form.js"></script>

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
					<button type="button" class="btn btn-info btn-sm" style="float: right;"   data-dismiss="modal" title="关闭">
                 	<i class="fa fa-times"></i></button>
				</div>
				<div class="row">
					<div class="col-xs-12">
					
					<form action="" name="Form_add" id="Form_add" enctype="multipart/form-data" method="post" onsubmit="return save(this)">
						<input type="hidden" name="TABLENAME" id="TABLENAME" value="${pd.TABLENAME}"/>
						<input type="hidden" name="CUSTOM_TEMPLATE_ID" id="CUSTOM_TEMPLATE_ID" value="${pd.CUSTOM_TEMPLATE_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:10%;text-align: right;padding-top:5px">任务类型:</td>
								<td>
									<input id="excel_file" type="file" name="files" accept="xlsx" size="80"/>
								</td>
							</tr>
							
							<tr>
								<td style="text-align: center;" colspan="2">
									<input id="excel_button" type="button" value="导入Excel"  class="btn btn-mini btn-primary" onclick="save()"/>
									<!-- <a class="btn btn-mini btn-danger"  data-btn-type="cancel" data-dismiss="modal">取消</a> -->
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
			
			
		});
		
		
		function save(){
			if($("#excel_file").val()==""){
				$("#excel_file").tips({
					side:3,
		            msg:'请输入任务名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#excel_file").focus();
				return false;
			}
			document.getElementById("excel_button").setAttribute("disabled", true);;
			document.getElementById("excel_button").value="正在导入中,请稍后...";
			var CUSTOM_TEMPLATE_ID=$("#CUSTOM_TEMPLATE_ID").val();
			var TABLENAME=$("#TABLENAME").val();
			 $('#Form_add').ajaxSubmit({
	            url: 'taskcustom/${msg }.do?CUSTOM_TEMPLATE_ID='+CUSTOM_TEMPLATE_ID+'&TABLENAME='+TABLENAME,
	            type: 'post',
	            async: true,
	            dataType: "html",
	            success: function (data) {
	            	document.getElementById("excel_button").removeAttribute("disabled");
	            	document.getElementById("excel_button").value="导入Excel";
	            	modals.info(data);
	            },
	            error: function (data) {
	            	document.getElementById("excel_button").removeAttribute("disabled");
	            	document.getElementById("excel_button").value="导入Excel";
	            	modals.info(data);
	            },
			 });
			//alert('${pd.TEMPLATE_ID}');
			
			
		}
		
		
		</script>
</body>
</html>