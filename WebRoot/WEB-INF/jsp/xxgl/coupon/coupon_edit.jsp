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
					
					<form action="coupon/${msg }.do" name="Form_add" id="Form_add" method="post">
						<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">优惠卷标题:</td>
								<td><input type="text" name="TITLE" id="TITLE" value="${pd.TITLE}" maxlength="255" placeholder="这里输入优惠卷标题" title="优惠卷标题" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">有效期:</td>
								<td><input type="number" name="TERM_VALIDITY" id="TERM_VALIDITY" value="${pd.TERM_VALIDITY}" maxlength="255" placeholder="这里输入有效期" title="有效期" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">详情:</td>
								<td>
									<textarea name="DETAIL" id="DETAIL" maxlength="255" placeholder="这里输入详情" title="详情" style="width:98%;height:120px">${pd.DETAIL}</textarea>
									
								</td>
							</tr>
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">使用范围:</td>
								<td><input type="text" name="USESCOPE" id="USESCOPE" value="${pd.USESCOPE}" maxlength="255" placeholder="这里输入使用范围" title="使用范围" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">费用:</td>
								<td><input type="text" name="MONEY" id="MONEY" value="${pd.MONEY}" maxlength="255" placeholder="这里输入费用" title="费用" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">优惠前费用:</td>
								<td><input type="text" name="PRE_MONEY" id="PRE_MONEY" value="${pd.PRE_MONEY}" maxlength="255" placeholder="这里输入优惠前费用" title="优惠前费用" style="width:98%;"/></td>
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



		<script type="text/javascript">
		//$(top.hangge());
		//保存
		function save(){
			if($("#TITLE").val()==""){
				$("#TITLE").tips({
					side:3,
		            msg:'请输入优惠卷标题',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TITLE").focus();
			return false;
			}
			if($("#TERM_VALIDITY").val()==""){
				$("#TERM_VALIDITY").tips({
					side:3,
		            msg:'请输入有效期',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TERM_VALIDITY").focus();
			return false;
			}
			if($("#DETAIL").val()==""){
				$("#DETAIL").tips({
					side:3,
		            msg:'请输入详情',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#DETAIL").focus();
			return false;
			}
			if($("#USESCOPE").val()==""){
				$("#USESCOPE").tips({
					side:3,
		            msg:'请输入使用范围',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#USESCOPE").focus();
			return false;
			}
			if($("#MONEY").val()==""){
				$("#MONEY").tips({
					side:3,
		            msg:'请输入费用',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#MONEY").focus();
			return false;
			}
			if($("#PRE_MONEY").val()==""){
				$("#PRE_MONEY").tips({
					side:3,
		            msg:'请输入优惠前费用',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PRE_MONEY").focus();
			return false;
			}
			if($("#CREATEMAN").val()==""){
				$("#CREATEMAN").tips({
					side:3,
		            msg:'请输入创建人',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CREATEMAN").focus();
			return false;
			}
			if($("#CREATEDATE").val()==""){
				$("#CREATEDATE").tips({
					side:3,
		            msg:'请输入时间',
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
                url: "coupon/${msg }.do" ,//url
                data: $('#Form_add').serialize(),
                success: function (result) {
                  // alert(result);//打印服务端返回的数据(调试用)
                    if (result.indexOf("success")>=0) {
                    	location.href="<%=basePath%>coupon/list.do";
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