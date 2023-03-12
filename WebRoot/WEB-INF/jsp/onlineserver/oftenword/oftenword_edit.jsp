<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">

<!-- 日期框 -->
<link rel="stylesheet" href="static/ace/css/datepicker.css" />
<link rel="stylesheet"
	href="plugins/kindeditor/themes/default/default.css" />
<link rel="stylesheet"
	href="plugins/kindeditor/plugins/code/prettify.css" />
<!-- 日期框 -->
<link type="text/css" rel="stylesheet"
	href="plugins/zTree/v3/css/zTreeStyle/zTreeStyle.css" />
<script type="text/javascript"
	src="plugins/zTree/v3/js/jquery.ztree.core.js"></script>
<script type="text/javascript"
	src="plugins/zTree/v3/js/jquery.ztree.excheck.js"></script>
<script>
	 
	</script>
<style>
div.content_wrap {
	width: 600px;
	height: 380px;
}

div.content_wrap div.left {
	float: left;
	width: 250px;
}

div.content_wrap div.right {
	float: right;
	width: 340px;
}

div.zTreeDemoBackground {
	width: 250px;
	height: 362px;
	text-align: left;
}

ul.ztree {
	margin-top: 10px;
	border: 1px solid #617775;
	background: #f0f6e4;
	width: 220px;
	height: 360px;
	overflow-y: scroll;
	overflow-x: auto;
}

ul.log {
	border: 1px solid #617775;
	background: #f0f6e4;
	width: 300px;
	height: 170px;
	overflow: hidden;
}

ul.log.small {
	height: 45px;
}

ul.log li {
	color: #666666;
	list-style: none;
	padding-left: 10px;
}

ul.log li.dark {
	background-color: #E3E3E3;
}

/* ruler */
div.ruler {
	height: 20px;
	width: 220px;
	background-color: #f0f6e4;
	border: 1px solid #333;
	margin-bottom: 5px;
	cursor: pointer
}

div.ruler div.cursor {
	height: 20px;
	width: 30px;
	background-color: #3C6E31;
	color: white;
	text-align: right;
	padding-right: 5px;
	cursor: pointer
}

#menuTree {
	background: #af0000;
}
</style>
</head>
<body class="no-skin">
	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">

					<div class="modal-header">
						<h4 class="modal-title" id="myModalLabel" style="float: left;">
						</h4>
						<div class="new-tb" style="float: right;" data-dismiss="modal"
							title="关闭"></div>
					</div>

					<div class="row">
						<div class="col-xs-12">

							<form action="oftenword/${msg }.do" name="Form_add" id="Form_add"method="post">
								<input type="hidden" name="id" id="id" value="${pd.id}" />
								<div id="zhongxin" style="padding-top: 13px;">
									<div id="table_report"
										class="table table-striped table-bordered table-hover new-wkj">

										<div class="new-tk-body-one" style="width:100%;height:300px;">
											<div class="new-tk-body-one-wk"
												style="width:100%;height:300px;">
												<div class="one-wk-l">话术内容:</div>
												<div class="one-wk-r" style="width:100%;padding-left:5px;">
													<textarea name="content" id="content"
														style="width:92%; height: 100px;">${pd.content}</textarea>
												</div>
											</div>
										</div>

										<div class="new-bc">
											<a onclick="save();">保存</a> 
											<a class="new-qxbut" data-btn-type="cancel" data-dismiss="modal">取消</a>
										</div>

									</div>
								</div>
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
	<script charset="utf-8" type="text/javascript" src="plugins/kindeditor/kindeditor-all.js"></script>
	<script charset="utf-8" type="text/javascript" src="plugins/kindeditor/lang/zh-CN.js"></script>
	<script charset="utf-8" src="plugins/kindeditor/plugins/code/prettify.js"></script>

	<script type="text/javascript">
	

	
	function save() {

		if ($("#content").val() == "") {
			$("#content").tips({
				side : 3,
				msg : '请输入话术内容',
				bg : '#AE81FF',
				time : 2
			});
			$("#content").focus();
			return false;
		}
	
		var msg = '${msg }';
	
		//$("#zhongxin").hide();
		//$("#zhongxin2").show();
		
		$('#Form_add').submit();

	}
	
	
	</script>
</body>
</html>