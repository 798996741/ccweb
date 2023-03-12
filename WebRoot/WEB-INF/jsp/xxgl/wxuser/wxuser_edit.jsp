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
	<!-- 下拉框 -->
	<link rel="stylesheet" href="static/ace/css/chosen.css" />
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/index/top.jsp"%>
	<!-- 日期框 -->
	<link rel="stylesheet" href="static/ace/css/datepicker.css" />
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
					
					<form action="wxuser/${msg }.do" name="Form_add" id="Form_add" method="post">
						<input type="hidden" name="WXUSER_ID" id="WXUSER_ID" value="${pd.WXUSER_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">openid:</td>
								<td><input type="text" name="OPENID" id="OPENID" value="${pd.OPENID}" maxlength="255" placeholder="这里输入openid" title="openid" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">昵称:</td>
								<td><input type="text" name="NICKNAME" id="NICKNAME" value="${pd.NICKNAME}" maxlength="255" placeholder="这里输入昵称" title="昵称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">头像:</td>
								<td><input type="text" name="AVATARURL" id="AVATARURL" value="${pd.AVATARURL}" maxlength="255" placeholder="这里输入头像" title="头像" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">性别:</td>
								<td><input type="text" name="GENDER" id="GENDER" value="${pd.GENDER}" maxlength="255" placeholder="这里输入性别" title="性别" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">城市:</td>
								<td><input type="text" name="CITY" id="CITY" value="${pd.CITY}" maxlength="255" placeholder="这里输入城市" title="城市" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">省会:</td>
								<td><input type="text" name="PROVINCE" id="PROVINCE" value="${pd.PROVINCE}" maxlength="255" placeholder="这里输入省会" title="省会" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">所属国家:</td>
								<td><input type="text" name="COUNTY" id="COUNTY" value="${pd.COUNTY}" maxlength="255" placeholder="这里输入所属国家" title="所属国家" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">创建时间:</td>
								<td><input type="text" name="CREATEDATE" id="CREATEDATE" value="${pd.CREATEDATE}" maxlength="255" placeholder="这里输入创建时间" title="创建时间" style="width:98%;"/></td>
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


	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		//$(top.hangge());
		//保存
		function save(){
			if($("#OPENID").val()==""){
				$("#OPENID").tips({
					side:3,
		            msg:'请输入openid',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#OPENID").focus();
			return false;
			}
			if($("#NICKNAME").val()==""){
				$("#NICKNAME").tips({
					side:3,
		            msg:'请输入昵称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#NICKNAME").focus();
			return false;
			}
			if($("#AVATARURL").val()==""){
				$("#AVATARURL").tips({
					side:3,
		            msg:'请输入头像',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#AVATARURL").focus();
			return false;
			}
			if($("#GENDER").val()==""){
				$("#GENDER").tips({
					side:3,
		            msg:'请输入性别',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#GENDER").focus();
			return false;
			}
			if($("#CITY").val()==""){
				$("#CITY").tips({
					side:3,
		            msg:'请输入城市',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CITY").focus();
			return false;
			}
			if($("#PROVINCE").val()==""){
				$("#PROVINCE").tips({
					side:3,
		            msg:'请输入省会',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PROVINCE").focus();
			return false;
			}
			if($("#COUNTY").val()==""){
				$("#COUNTY").tips({
					side:3,
		            msg:'请输入所属国家',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#COUNTY").focus();
			return false;
			}
			if($("#CREATEDATE").val()==""){
				$("#CREATEDATE").tips({
					side:3,
		            msg:'请输入创建时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CREATEDATE").focus();
			return false;
			}
			$("#zhongxin").hide();
			$("#zhongxin2").show();
			$.ajax({
            //几个参数需要注意一下
                type: "POST",//方法类型
                dataType: "html",//预期服务器返回的数据类型
                url: "wxuser/${msg }.do" ,//url
                data: $('#Form_add').serialize(),
                success: function (result) {
                  //  console.log(result.);//打印服务端返回的数据(调试用)
                    if (result.indexOf("success")>=0) {
                    	location.href="<%=basePath%>wxuser/list.do";
                    }
                   
                },
                error : function() {
                    alert("异常！");
                }
            });
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>