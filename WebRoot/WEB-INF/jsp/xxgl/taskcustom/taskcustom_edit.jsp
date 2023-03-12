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
					
					<form action="<%=basePath%>taskcustom/customMsgSave.do" name="Form_add_cus" id="Form_add_cus" method="post">
						<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
						<input type="hidden" name="CUSTOM_TEMPLATE_ID" id="CUSTOM_TEMPLATE_ID" value="${pd.CUSTOM_TEMPLATE_ID}"/>
						<input type="hidden" name="TABLENAME" id="TABLENAME" value="${pd.TABLENAME}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							${str }
							<!-- <tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-danger"  data-btn-type="cancel" data-dismiss="modal">取消</a>
								</td>
							</tr> -->
							<c:if test="${pd.action=='edit'}">
								<tr>
									<td style="text-align: center;" colspan="10">
										<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
										<a class="btn btn-mini btn-danger"  data-btn-type="cancel" data-dismiss="modal">取消</a>
									</td>
								</tr>
							</c:if>
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
		$('.date-picker').datepicker({
			autoclose: true,
			todayHighlight: true,
			clearBtn: true
		});
		$(function() {
			
			
		});
		
		
		function save(){
			if($("#ID").val()==""){
				$("#ID").tips({
					side:3,
		            msg:'未获取修改人员信息',
		            bg:'#AE81FF',
		            time:2
		        });
				//$("#ID").focus();
				return false;
			}
			//$("#zhongxin").hide();
			//$("#zhongxin2").show();
			$.ajax({
            //几个参数需要注意一下
                type: "POST",//方法类型
                dataType: "html",//预期服务器返回的数据类型
                url: "<%=basePath%>taskcustom/customtablesave.do" ,//url
                data: $('#Form_add_cus').serialize(),
                success: function (result) {
                  	// alert(result);//打印服务端返回的数据(调试用)
                    if (result.indexOf("success_")>=0) {
                    	modals.info("修改成功！");
                    	$('#userWin').modal('hide');
                    	var type=$("#type").val();
            			var ID=$("#ID").val();
            			var CUSTOM_TEMPLATE_ID=$("#CUSTOM_TEMPLATE_ID").val();
            			var NAIRE_TEMPLATE_ID=$("#NAIRE_TEMPLATE_ID").val();
            			var ZXMAN=$("#ZXMAN").val();
            			var ROWNO=$("#cusnum").val();
            			var TABLENAME=$("#TABLENAME").val();
            			var last=$("#last").val();
            			var WCJD="";
            			
           				var url = "<%=basePath%>exetask/getTask.do?ROWNO="+(Number(ROWNO))+"&TABLENAME="+TABLENAME+"&CUSTOM_TEMPLATE_ID="+CUSTOM_TEMPLATE_ID+"&NAIRE_TEMPLATE_ID="+NAIRE_TEMPLATE_ID+"&ID="+ID+"&type="+type+"&ZXMAN="+ZXMAN+"&tm="+new Date().getTime();
           				$.get(url,function(data){
           					//alert(data);
           					var obj= JSON.parse(data);
           					
           					$("#userstr").html("");
           					$("#userstr").append(obj.userstr);
           					//alert("d");
           					if(obj.userstr!=""){
           						//alert("d");
           						//getTmByNaire(type,ID,CUSTOM_TEMPLATE_ID,NAIRE_TEMPLATE_ID,TABLENAME,WCJD,ZXMAN);
           					}
           					
           				});
            			//}
                    }else{
                    	modals.info("修改失败");
                    }
                   
                },
                error : function(result) {
                	alert(result);
                    alert("异常！");
                }
            });
		}
		
		
		</script>
</body>
</html>