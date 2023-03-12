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
					
					
					<form action="recordpay/${msg }.do" name="Form_add" id="Form_add" method="post">
						<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">id:</td>
								<td><input type="text" name="ORDER_ID" id="ORDER_ID" value="${pd.ORDER_ID}" maxlength="255" placeholder="这里输入id" title="id" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">订单号:</td>
								<td><input type="text" name="DDH" id="DDH" value="${pd.DDH}" maxlength="255" placeholder="这里输入订单号" title="订单号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">费用:</td>
								<td><input type="text" name="MONEY" id="MONEY" value="${pd.MONEY}" maxlength="255" placeholder="这里输入费用" title="费用" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">创建时间:</td>
								<td><input type="text" name="CREATEDATE" id="CREATEDATE" value="${pd.CREATEDATE}" maxlength="255" placeholder="这里输入创建时间" title="创建时间" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">openid:</td>
								<td><input type="text" name="OPENID" id="OPENID" value="${pd.OPENID}" maxlength="255" placeholder="这里输入openid" title="openid" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">是否支付:</td>
								<td><input type="text" name="ISJF" id="ISJF" value="${pd.ISJF}" maxlength="255" placeholder="这里输入是否支付" title="是否支付" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">缴费时间:</td>
								<td><input type="text" name="JFDATE" id="JFDATE" value="${pd.JFDATE}" maxlength="255" placeholder="这里输入缴费时间" title="缴费时间" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">类型:</td>
								<td><input type="text" name="TYPE" id="TYPE" value="${pd.TYPE}" maxlength="255" placeholder="这里输入类型" title="类型" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">开始时间:</td>
								<td><input type="text" name="STARTDATE" id="STARTDATE" value="${pd.STARTDATE}" maxlength="255" placeholder="这里输入开始时间" title="开始时间" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">结束时间:</td>
								<td><input type="text" name="ENDDATE" id="ENDDATE" value="${pd.ENDDATE}" maxlength="255" placeholder="这里输入结束时间" title="结束时间" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">天数:</td>
								<td><input type="text" name="DAYNUM" id="DAYNUM" value="${pd.DAYNUM}" maxlength="255" placeholder="这里输入天数" title="天数" style="width:98%;"/></td>
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
			if($("#ORDER_ID").val()==""){
				$("#ORDER_ID").tips({
					side:3,
		            msg:'请输入id',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ORDER_ID").focus();
			return false;
			}
			if($("#DDH").val()==""){
				$("#DDH").tips({
					side:3,
		            msg:'请输入订单号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#DDH").focus();
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
			if($("#ISJF").val()==""){
				$("#ISJF").tips({
					side:3,
		            msg:'请输入是否支付',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ISJF").focus();
			return false;
			}
			if($("#JFDATE").val()==""){
				$("#JFDATE").tips({
					side:3,
		            msg:'请输入缴费时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#JFDATE").focus();
			return false;
			}
			if($("#TYPE").val()==""){
				$("#TYPE").tips({
					side:3,
		            msg:'请输入类型',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TYPE").focus();
			return false;
			}
			if($("#STARTDATE").val()==""){
				$("#STARTDATE").tips({
					side:3,
		            msg:'请输入开始时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STARTDATE").focus();
			return false;
			}
			if($("#ENDDATE").val()==""){
				$("#ENDDATE").tips({
					side:3,
		            msg:'请输入结束时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ENDDATE").focus();
			return false;
			}
			if($("#DAYNUM").val()==""){
				$("#DAYNUM").tips({
					side:3,
		            msg:'请输入天数',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#DAYNUM").focus();
			return false;
			}
			$("#zhongxin").hide();
			$("#zhongxin2").show();
			$.ajax({
            //几个参数需要注意一下
                type: "POST",//方法类型
                dataType: "html",//预期服务器返回的数据类型
                url: "recordpay/${msg }.do" ,//url
                data: $('#Form_add').serialize(),
                success: function (result) {
                  //  console.log(result.);//打印服务端返回的数据(调试用)
                    if (result.indexOf("success")>=0) {
                    	location.href="<%=basePath%>recordpay/list.do";
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