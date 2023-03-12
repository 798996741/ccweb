<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<!-- jsp文件头和头部 -->
		<link type="text/css" rel="stylesheet" href="plugins/zTree/2.6/zTreeStyle.css"/>
	<script type="text/javascript" src="plugins/zTree/2.6/jquery.ztree-2.6.min.js"></script>
	<style type="text/css">
	footer{height:50px;position:fixed;bottom:0px;left:0px;width:100%;text-align: center;}
	</style>
<body>

</head>
<body class="no-skin">

	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="modal-header">
						<h4 class="modal-title" id="myModalLabel" style="float: left;">权限</h4>
						<div class="new-tb" style="float: right;" data-dismiss="modal" title="关闭"></div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<div id="zhongxin">
								<div style="overflow: scroll; scrolling: yes;height:415px;width: 309px;">
								<ul id="tree" class="tree" style="overflow:auto;"></ul>
								</div>
							</div>
							<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">正在保存...</h4></div>
							</div>
						<div class="new-bc" style="margin: 10px 0">
							<a onclick="save();">保存</a>
							<a class="new-qxbut" data-btn-type="cancel" data-dismiss="modal" style="margin-left: 20px">取消</a>
						</div>
						<!-- /.col -->
						</div>
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->



<%--		<div style="width: 100%;padding-top: 5px;text-align:center" >--%>
	<%--			<button type="button" class="btn btn-default-sm button-blue width-65px"--%>
	<%--					style="margin: 5px 5px 5px 15px;" onclick="save();">--%>
	<%--				<font>保&nbsp;存</font>--%>
	<%--			</button>--%>
	<%--			<button type="button" class="btn btn-default-sm button-red width-65px"--%>
	<%--					style="margin: 5px 5px 5px 15px;" data-btn-type="cancel" data-dismiss="modal">--%>
	<%--				<font>取&nbsp;消</font>--%>
	<%--			</button>--%>
	<%--			--%>
	<%--&lt;%&ndash;			<a class="btn btn-mini btn-primary" onclick="save();">保存</a>&ndash;%&gt;--%>
	<%--&lt;%&ndash;			<a class="btn btn-mini btn-danger" data-btn-type="cancel" data-dismiss="modal">取消</a>&ndash;%&gt;--%>
	<%--		</div>--%>
	
	<script type="text/javascript">
		var zTree;
		$(document).ready(function(){
			
			var setting = {
			    showLine: true,
			    checkable: true
			};
			var zn = '${zTreeNodes}';
			var zTreeNodes = eval(zn);
			zTree = $("#tree").zTree(setting, zTreeNodes);
		});
	
		//保存
		 function save(){
			var nodes = zTree.getCheckedNodes();
			var tmpNode;
			var ids = "";
			for(var i=0; i<nodes.length; i++){
				tmpNode = nodes[i];
				if(i!=nodes.length-1){
					ids += tmpNode.id+",";
				}else{
					ids += tmpNode.id;
				}
			}
			var ROLE_ID = "${ROLE_ID}";
			var url = "<%=basePath%>role/saveMenuqx.do";
			var postData;
			postData = {"ROLE_ID":ROLE_ID,"menuIds":ids};
			$("#zhongxin").hide();
			$("#zhongxin2").show();
			$.post(url,postData,function(data){
				location.href="<%=basePath%>role/listRoles.do";
				//top.Dialog.close();
			});
		 }
	
	</script>
</body>
</html>