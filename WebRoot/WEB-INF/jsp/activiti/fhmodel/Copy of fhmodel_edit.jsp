<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
</head>
<body class="no-skin" onload="getLeves()">
	<!-- /section:basics/navbar.layout -->
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">
			<li class="fa fa-remove"></li>
		</button>
		<h5 class="modal-title">模型管理</h5>
	</div>

	<div class="modal-body">

		<form action="fhmodel/${msg }.do" name="Form_add" id="Form_add"
			method="post">
			<div id="zhongxin" style="padding-top: 13px;">
				<table id="table_report"
					class="table table-striped table-bordered table-hover">
					<tr>
						<td style="width:120px;text-align: right;padding-top: 13px;">模型名称:</td>
						<td><input type="text" name="modelname" id="modelname"
							value="" maxlength="255" placeholder="这里输入模型名称" title="模型名称"
							style="width:98%;" /></td>
					</tr>
					<tr>
						<td style="width:120px;text-align: right;padding-top: 13px;">模型分类:</td>
						<td><select name="category" id="category" title="模型分类"
							style="width:98%;">
						</select></td>
					</tr>
					<tr>
						<td style="width:120px;text-align: right;padding-top: 13px;">模型描述:</td>
						<td><textarea name="description" id="description"
								style="width:98%;" placeholder="这里输入模型描述" title="模型描述"></textarea>
						</td>
					</tr>
					<tr>
						<td style="width:120px;text-align: right;padding-top: 13px;">流程名称:</td>
						<td><input type="text" name="name" id="name" value=""
							maxlength="255" placeholder="这里输入流程名称" title="流程名称"
							style="width:98%;" /></td>
					</tr>
					<tr>
						<td style="width:120px;text-align: right;padding-top: 13px;">流程标识:</td>
						<td><select name="process_id" id="process_id" title="流程标识"
							style="width:98%;">
						</select></td>
					</tr>
					<tr>
						<td style="width:120px;text-align: right;padding-top: 13px;">流程作者:</td>
						<td><input type="text" name="process_author"
							id="process_author" value="${pd.process_author }" maxlength="255"
							placeholder="这里输入流程作者" title="流程作者" style="width:98%;" /></td>
					</tr>
					<tr>
						<td style="text-align: center;" colspan="10"><a
							class="btn btn-mini btn-primary" onclick="save();">保存</a> <a
							class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
						</td>
					</tr>
				</table>
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
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
	
		function getLeves(){
			
		}
		
		$.ajax({
			type: "POST",
			url: '<%=basePath%>dictionaries/getLevels.do?tm='+new Date().getTime(),
	    	data: {DICTIONARIES_ID:'act001'},//act001 为工作流分类
			dataType:'json',
			cache: false,
			success: function(data){
				$("#category").html('<option value="" >请选择分类</option>');
				$.each(data.list, function(i, dvar){
					$("#category").append("<option value="+dvar.BIANMA+">"+dvar.NAME+"</option>");
				});
			}
		});
		//为流程标识
		$.ajax({
			type: "POST",
			url: '<%=basePath%>dictionaries/getLevels.do?tm='+new Date().getTime(),
	    	data: {DICTIONARIES_ID:'act002'},//act002为流程标识
			dataType:'json',
			cache: false,
			success: function(data){
				$("#process_id").html('<option value="" >请选择流程标识</option>');
				 $.each(data.list, function(i, dvar){
						$("#process_id").append("<option value="+dvar.BIANMA+">"+dvar.NAME+"("+dvar.BIANMA+")"+"</option>");
				 });
			}
		});
		
		//保存
		function save(){
			if($("#modelname").val()==""){
				$("#modelname").tips({
					side:3,
		            msg:'请输入模型名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#modelname").focus();
			return false;
			}
			if($("#category").val()==""){
				$("#category").tips({
					side:3,
		            msg:'请输入模型分类',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#category").focus();
			return false;
			}
			if($("#description").val()==""){
				$("#description").tips({
					side:3,
		            msg:'请输入模型描述',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#description").focus();
			return false;
			}
			if($("#name").val()==""){
				$("#name").tips({
					side:3,
		            msg:'请输入流程名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#name").focus();
			return false;
			}
			if($("#process_id").val()==""){
				$("#process_id").tips({
					side:3,
		            msg:'请选择流程标识',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#process_id").focus();
			return false;
			}
			if($("#process_author").val()==""){
				$("#process_author").tips({
					side:3,
		            msg:'请输入流程作者',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#process_author").focus();
			return false;
			}
			//$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
			
			$.ajax({
	            //几个参数需要注意一下
                type: "POST",//方法类型
                dataType: "html",//预期服务器返回的数据类型
                url: "fhmodel/${msg}.do" ,//url
                data: $('#Form_add').serialize(),
                success: function (result) {
                  //  console.log(result.);//打印服务端返回的数据(调试用)
                    if (result.indexOf("success")>=0) {
                    	location.href="<%=path%>/fhmodel/list.do";
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