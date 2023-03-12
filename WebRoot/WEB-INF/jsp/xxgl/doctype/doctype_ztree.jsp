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
	<meta charset="utf-8" />
	<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		<link type="text/css" rel="stylesheet" href="plugins/zTree/2.6/zTreeStyle.css"/>
	<script type="text/javascript" src="plugins/zTree/2.6/jquery.ztree-2.6.min.js"></script>
<body>
	
<table style="width:100%;" border="0">
	<tr>
		<td style="width:15%;" style="padding-top:10px;" valign="top" bgcolor="#F9F9F9">
			<div style="width:15%;">
				<ul id="leftTree" class="tree" ></ul>
			</div>
		</td>
		<td style="width:85%;" valign="top" >
			<c:if test="${action=='search' }">
				<iframe name="treeFrame" id="treeFrame" frameborder="0" src="<%=basePath%>/doc/list.do?action=search&currentPage=${null == pd.dnowPage || '' == pd.dnowPage?'10':pd.dnowPage}" style="margin:0 auto;width:100%;height:100%;"></iframe>
			</c:if>
			<c:if test="${action!='search' }">
				<iframe name="treeFrame" id="treeFrame" frameborder="0" src="<%=basePath%>/doctype/list.do?id=${'' == id?'0':id}&currentPage=${null == pd.dnowPage || '' == pd.dnowPage?'10':pd.dnowPage}" style="margin:0 auto;width:100%;height:100%;"></iframe>
			</c:if>
		</td>
	</tr>
</table>
		
<script type="text/javascript">
		//$(top.hangge());
		var zTree;
		$(document).ready(function(){
			var setting = {
			    showLine: true,
			    checkable: false
			};
			var zTreeNodes = eval(${zTreeNodes});
			zTree = $("#leftTree").zTree(setting, zTreeNodes);
			if('${pd.id}'!=""){
				var node = zTree.getNodeByParam('id', '${pd.id}');//获取id为1的点
				zTree.selectNode(node);//选择点
				zTree.expandNode(node, true, false, true);
			}
		});
	
		function treeFrameT(){
			var hmainT = document.getElementById("treeFrame");
			var bheightT = document.documentElement.clientHeight;
			hmainT .style.width = '100%';
			hmainT .style.height = (bheightT-26) + 'px';
		}
		treeFrameT();
		window.onresize=function(){  
			treeFrameT();
		};
</SCRIPT>
</body>
</html>

