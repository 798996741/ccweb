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
.tdleft{
	width:20%;
	background:#ccc;
	text-align:right;
	height:30px;
	padding-right:10px;
	font-size:14px;
}
.tdright{
	width:76%;
	text-align:left;
	height:30px;
	padding-left:10px;
	font-size:14px;
}
table{border-collapse: collapse;width:100%;}
tr{border: 1px solid #ccc;}
td{border: 1px solid #ccc;}
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
							<form action="doc/doccomment.do" name="Form_add" id="Form_add"method="post" >

								<input type="hidden" name="doc_id" id="doc_id" value="${pd.id}" />
								<input type="hidden" name="uid" id="uid" value="${pd.uid}" />
								<div id="zhongxin" style="padding-top: 13px;padding-left:10px">
									<table id="table_report" style="width:80%">
										<tr>
											<td class="tdleft">知识分类:</td>
											<td class="tdright">${pd.doctype_name}</td>
										</tr>
										<tr>
											<td class="tdleft">标题:</td>
											<td class="tdright">${pd.doctitle}</td>
										</tr>
										<tr>
											<td class="tdleft">来源:</td>
											<td class="tdright">${pd.docsource}</td>
										</tr>
										<tr>
											<td class="tdleft">作者:</td>
											<td class="tdright">${pd.docauthor}</td>
										</tr>
										<tr>
											<td class="tdleft">详细内容:</td>
											<td class="tdright">${pd.doccont}</td>
										</tr>	
										
										<tr>
											<td class="tdleft">附件:</td>
											<td class="tdright">
												<ul id="fileid">
												</ul>
											</td>
										</tr>	
									</table>
								</div>
								<div>
									<div class="new-tk-body-one" style="width:80%;height:150px;">
										<div class="new-tk-body-one-wk"
											style="width:100%;height:150px;">
											<div class="one-wk-l">评论内容:</div>
											<div class="one-wk-r" style="width:100%;padding-left:5px;">
												<textarea name="comment" id="comment"
													style="width:92%; height: 100px;" placeholder="这里输入评论内容" title="评论内容" ></textarea>
											</div>
										</div>
									</div>
									
									
									<div class="new-bc">
										<a onclick="save();">发表评论</a> 
										<a class="new-qxbut" data-btn-type="cancel" data-dismiss="modal">取消</a>
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
	
	var tree;
    var demoIframe;
    var uid='${pd.uid}';
    getFile();
    clicknum();
	
	function getFile(){
		//alert(uid);
		
		if(uid!=""){
			$.ajax({
				//几个参数需要注意一下
				type : "POST",//方法类型
				dataType : "json",//预期服务器返回的数据类型
				url : "doc/getFile.do?uid="+uid,//url
				success : function(result) {
					//alert(result.list);
					var str="";
					$.each(result.list, function(i, list){
						str=str+"<li>"+(i+1)+"、<a style =\"color:blue;line-height:30px;\" href=\"<%=path%>/uploadFiles/uploadFile/"+list.doc_file+"\">"+list.doc_name+"</a>&nbsp;&nbsp;<span style=\"color:red\" </li>";
					});
					$("#fileid").html('');
					//alert(str);
					$("#fileid").append(str);
				}
			 });
		}else{
			
		}
	}
	
	function clicknum(){
		//alert(uid);
		var doc_id='${pd.id}';
		if(doc_id!=""){
			$.ajax({
				//几个参数需要注意一下
				type : "POST",//方法类型
				dataType : "json",//预期服务器返回的数据类型
				url : "doc/clicknum.do?id="+doc_id,//url
				success : function(result) {
					
					
				}
			 });
		}else{
			
		}
	}
	
	
	
	function delfile(id){
		bootbox.confirm("确定要删除文件吗?", function(result) {
			if(result) {
				var url = "<%=basePath%>doc/deleteFile.do?id="+id+"&tm="+new Date().getTime();
				$.get(url,function(data){
					getFile();
				});
			}
		});
	}
	
	
	function save() {
		if ($("#doc_id").val() == "") {
			$("#comment").tips({
				side : 3,
				msg : '评论的文章不存在',
				bg : '#AE81FF',
				time : 2
			});
			return false;
		}
		if ($("#comment").val() == "") {
			$("#comment").tips({
				side : 3,
				msg : '评论的内容不能为空',
				bg : '#AE81FF',
				time : 2
			});
			return false;
		}
	
		
		$.ajax({
			//几个参数需要注意一下
			type : "POST",//方法类型
			dataType : "html",//预期服务器返回的数据类型
			url : "doc/doccomment.do",//url
			data : $('#Form_add').serialize(),
			success : function(result) {
				// alert(result);//打印服务端返回的数据(调试用)
				if (result=="success") {
					$("#comment").val('');
					modals.info("评论成功！");
                  	return false;
                 }else if (result.indexOf("error1")>=0){
                  	modals.info("标题已存在,请重新输入！");
                  	return false;
                 }
                 
	          }
	       }); 
	}
	
	
	</script>
</body>
</html>