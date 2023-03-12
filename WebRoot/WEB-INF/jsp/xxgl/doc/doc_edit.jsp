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

							<form action="doc/${msg }.do" name="Form_add" id="Form_add"method="post" enctype="multipart/form-data" >
								<input type="hidden" name="id" id="id" value="${pd.id}" />
								<input type="hidden" name="uid" id="uid" value="${pd.uid}" />
								<div id="zhongxin" style="padding-top: 13px;">
									<div id="table_report"
										class="table table-striped table-bordered table-hover new-wkj">

										<div class="new-tk-body-one">
											<div class="new-tk-body-one-wk">
												<div class="one-wk-l">知识分类:</div>
												<div class="one-wk-r">
													<input type="hidden" name="doctype_id" id="doctype_id"
														value="${pd.doctype_id}" maxlength="30" placeholder="选择" />
													<input type="text" name="doctype_name"
														onclick="showMenu();return false" id="doctype_name"
														value="${pd.doctype_name}" maxlength="30" placeholder="选择" readonly />
													<div id="menuContent" class="menuContent"
														style="position: absolute;display:none">
														<ul id="treeDemo" class="ztree"
															style="margin-top:0; width:160px;"></ul>
													</div>

												</div>
											</div>
											<span>*</span>
										</div>


										<div class="new-tk-body-one" style="width:100%">
											<div class="new-tk-body-one-wk" style="width:100%">
												<div class="one-wk-l">标题:</div>
												<div class="one-wk-r" style="width:100%">
													<input type="text" name="doctitle" id="doctitle"
														value="${pd.doctitle}" maxlength="30" placeholder="这里输入标题"
														title="标题" />
												</div>
											</div>
											<span>*</span>
										</div>


										<div class="new-tk-body-one">
											<div class="new-tk-body-one-wk">
												<div class="one-wk-l">来源:</div>
												<div class="one-wk-r">
													<input name="docsource" id="docsource"
														value="${pd.docsource}" type="text" placeholder="来源"
														title="来源" />
												</div>
											</div>
											<span>*</span>
										</div>


										<div class="new-tk-body-one">
											<div class="new-tk-body-one-wk">
												<div class="one-wk-l">作者:</div>
												<div class="one-wk-r">
													<input type="text" name="docauthor" id="docauthor"
														value="${pd.docauthor}" maxlength="50"
														placeholder="这里输入作者" title="作者" />
												</div>
											</div>
											<span>*</span>
										</div>



										<div class="new-tk-body-one">
											<div class="new-tk-body-one-wk">
												<div class="one-wk-l">状态:</div>
												<div class="one-wk-r">
													<select id="doctype" name="doctype">
														<option value="0"
															<c:if test="${pd.doctype==0}">selected</c:if>>待审核</option>
														<option value="1"
															<c:if test="${pd.doctype==1}">selected</c:if>>已激活</option>
														<option value="2"
															<c:if test="${pd.doctype==2}">selected</c:if>>已激活</option>
													</select>
												</div>
											</div>
											<span>*</span>
										</div>


										<div class="new-tk-body-one" style="width:100%;height:300px;">
											<div class="new-tk-body-one-wk"
												style="width:100%;height:300px;">
												<div class="one-wk-l">详细内容:</div>
												<div class="one-wk-r" style="width:100%;padding-left:5px;">
													<textarea name="doccont" id="doccont"
														style="width:92%; height: 100px;">${pd.doccont}</textarea>
												</div>
											</div>
										</div>
										
										<div class="new-tk-body-one"  style="width:100%">
											<div class="new-tk-body-one-wk" style="width:60%;float:left">
												<div class="one-wk-l">附件上传:</div>
												<div class="one-wk-r" >
													<input type="file" name="file" multiple="multiple">
												</div>
											</div>
											<div style="padding-left:10px;width:38%;float:left">
												<ul id="fileid">
												</ul>
											</div>
										</div>
										<div class="new-bc">
											<a onclick="save();">保存</a> 
											<a class="new-qxbut" data-btn-type="cancel" data-dismiss="modal">取消</a>
										</div>

									</div>
								</div>
								<div id="zhongxin2" class="center" style="display:none">
									<br />
									<br />
									<br />
									<br />
									<br />
									<img src="static/images/jiazai.gif" /><br />
									<h4 class="lighter block green">提交中...</h4>
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
    var setting = {
        view: {
            showIcon: true,//是否显示节点的图标
            showLine: true,//显示节点之间的连线。
            expandSpeed: "slow",//节点展开、折叠时的动画速度
            selectedMulti: false//不允许同时选中多个节点。
        },
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pId",
				rootPId : "",
				highlight: false
			}
		},
		check : {
			enable : false
		},
		callback : {
		
			onClick: onClick
		}
	};
	var zNodes = eval('${zTreeNodes}');
	//alert(zNodes);

	
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
						str=str+"<li>"+list.doc_name+"&nbsp;&nbsp;<span style=\"color:red\" onclick=\"delfile('"+list.id+"')\">删除文件</span></li>";
					});
					$("#fileid").html('');
					//alert(str);
					$("#fileid").append(str);
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
	
	console.log("进入编辑器！！！！");
	KindEditor.create('#doccont',{
		uploadJson :'<%=request.getContextPath()%>/admin/upload_json.jsp',
		fileManagerJson : '<%=request.getContextPath()%>/admin/file_manager_json.jsp',
		allowFileManager : true,
		resizeType : 1,
		width : "99%",
		height : "250px",
		urlType :'domain',
		afterCreate : function() {
			this.sync();
		}, //kindeditor创建后，将编辑器的内容设置到原来的textarea控件里
		afterChange : function() {
			this.sync();
		},//编辑器内容发生变化后，将编辑器的内容设置到原来的textarea控件里 
		afterBlur : function() {
			this.sync();
		} //编辑器聚焦后，将编辑器的内容设置到原来的textarea控件里

	});
	var t = $("#treeDemo");
	t = $.fn.zTree.init(t, setting, zNodes);
	function onClick(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo"), nodes = zTree.getSelectedNodes(), v = "";
		nodes.sort(function compare(a, b) {
			return a.id - b.id;
		});
		for (var i = 0, l = nodes.length; i < l; i++) {
			v += nodes[i].name + ",";
		}
		//alert(treeId);
		//alert(treeNode.id);
		$("#doctype_id").val(treeNode.id);
		if (v.length > 0)
			v = v.substring(0, v.length - 1);
		var cityObj = $("#doctype_name");
		cityObj.attr("value", v);
		$("#menuContent").hide();
	}
	
	function showMenu() {
		var cityObj = $("#doctype_id");
		var cityOffset = $("#doctype_id").offset();
		$("#menuContent").slideDown("fast");
	
		//$("body").bind("mousedown", onBodyDown);
	}
	
	function save() {
		if ($("#doctype_id").val() == "") {
			$("#doctype_name").tips({
				side : 3,
				msg : '请选择知识分类',
				bg : '#AE81FF',
				time : 2
			});
			$("#doctype_name").focus();
			return false;
		}
		if ($("#doctitle").val() == "") {
			$("#doctitle").tips({
				side : 3,
				msg : '请输入标题',
				bg : '#AE81FF',
				time : 2
			});
			$("#doctitle").focus();
			return false;
		}
		if ($("#docauthor").val() == "") {
			$("#docauthor").tips({
				side : 3,
				msg : '请输入作者',
				bg : '#AE81FF',
				time : 2
			});
			$("#docauthor").focus();
			return false;
		}
	
		if ($("#docsource").val() == "") {
			$("#docsource").tips({
				side : 3,
				msg : '请输入来源',
				bg : '#AE81FF',
				time : 2
			});
			$("#docsource").focus();
			return false;
		}
	
		var msg = '${msg }';
	
		//$("#zhongxin").hide();
		//$("#zhongxin2").show();
		
		$('#Form_add').submit();
		
		
		<%-- $.ajax({
			//几个参数需要注意一下
			type : "POST",//方法类型
			dataType : "html",//预期服务器返回的数据类型
			url : "doc/${msg }.do",//url
			data : $('#Form_add').serialize(),
			success : function(result) {
				// alert(result);//打印服务端返回的数据(调试用)
				if (result.indexOf("success_add") >= 0) {
					location.href = "<%=basePath%>doc/list.do";
	                  }else if (result.indexOf("error1")>=0){
	                  	modals.info("标题已存在,请重新输入！");
	                  	return false;
	                  }
	                 
	              },
	              error : function() {
	                  alert("异常！");
	              }
	          }); --%>
	}
	
	
	</script>
</body>
</html>