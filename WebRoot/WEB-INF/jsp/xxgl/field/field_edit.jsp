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
					<div class="new-tb" style="float: right;" data-dismiss="modal" title="关闭"></div>
				</div>
				<div class="row">
					<div class="col-xs-12">
					
					<form action="field/${msg }.do" name="Form_add" id="Form_add" method="post">
						<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
						<div id="zhongxin">
						<div id="table_report" class="table table-striped table-bordered table-hover new-wkj">
							
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">字段名称:</div>
									<div class="one-wk-r"><input type="text" name="FIELDNAME" id="FIELDNAME" value="${pd.FIELDNAME}" maxlength="30" placeholder="这里输入字段名称" title="字段名称" /></div>
								</div>
								<span>*</span>
							</div>
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">字段:</div>
									<div class="one-wk-r"><input type="text" name="FIELD" id="FIELD" value="${pd.FIELD}" maxlength="30" placeholder="这里输入字段" title="字段" /></div>
								</div>
								<span>*</span>
							</div>
							
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">字段类型:</div>
									<div class="one-wk-r">
										<select id="FIELDTYPE" name="FIELDTYPE" >
											<c:forEach items="${dictList}" var="var" varStatus="vs">
												<option value="${var.NAME_EN }" <c:if test="${pd.FIELDTYPE==var.NAME_EN}">selected</c:if>>${var.NAME }</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<span>*</span>
							</div>
							
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">长度:</div>
									<div class="one-wk-r"><input type="number" name="FIELDNUM" id="FIELDNUM" value="${pd.FIELDNUM}" maxlength="50" placeholder="这里输入长度" title="长度" /></div>
								</div>
								<span>*</span>
							</div>
							
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">是否基础字段:</div>
									<div class="one-wk-r">
										<select  name="ISBASIC" id="ISBASIC">
											<option value="0" <c:if test="${pd.ISBASIC==0}">selected</c:if>>否</option>
											<option value="1" <c:if test="${pd.ISBASIC==1}">selected</c:if>>是</option>
										</select>
									</div>
								</div>
								<span> </span>
							</div>
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">是否电话:</div>
									<div class="one-wk-r">
										<select  name="ISTEL" id="ISTEL">
											<option value="0" <c:if test="${pd.ISTEL==0}">selected</c:if>>否</option>
											<option value="1" <c:if test="${pd.ISTEL==1}">selected</c:if>>是</option>
										</select>
									</div>
								</div>
								<span> </span>
							</div>
							
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">是否启用:</div>
									<div class="one-wk-r">
										<select  name="ISUSE" id="ISUSE">
											<option value="1" <c:if test="${pd.ISUSE==1}">selected</c:if>>是</option>
											<option value="0" <c:if test="${pd.ISUSE==0}">selected</c:if>>否</option>
										</select>
									</div>
								</div>
								<span> </span>
							</div>
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk new-gbkd">
									<div class="one-wk-l">是否展示:</div>
									<div class="one-wk-r">
										<select  name="ISSHOW" id="ISSHOW">
											<option value="1" <c:if test="${pd.ISSHOW==1}">selected</c:if>>是</option>
											<option value="0" <c:if test="${pd.ISSHOW==0}">selected</c:if>>否</option>
										</select>
									</div>
								</div>
								<p>展示在给前端列表的字段</p>
							</div>
							
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk new-gbkd">
									<div class="one-wk-l">是否配额:</div>
									<div class="one-wk-r">
										<select  name="ISPE" id="ISPE">
											<option value="0" <c:if test="${pd.ISPE==0}">selected</c:if>>否</option>
											<option value="1" <c:if test="${pd.ISPE==1}">selected</c:if>>是</option>
										</select>
									</div>
								</div>
								<p>用于在分配任务时指定字段</p>
							</div>
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk new-gbkd">
									<div class="one-wk-l">排序:</div>
									<div class="one-wk-r">
										<input type="number" name="PX" id="PX" value="${pd.PX}" maxlength="50" placeholder="这里输入排序号" title="排序" />
									
									</div>
								</div>
								<p>排序为1排在最前面一位</p>
							</div>
						
							<div class="new-bc">
								<a  onclick="save();">保存</a>
								<!--  <a class="btn btn-mini btn-danger"  data-btn-type="cancel" data-dismiss="modal">取消</a>-->
							</div>
							
						</div>
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
			if($("#FIELDNAME").val()==""){
				$("#FIELDNAME").tips({
					side:3,
		            msg:'请输入字段名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#FIELDNAME").focus();
				return false;
			}
			if($("#FIELD").val()==""){
				$("#FIELD").tips({
					side:3,
		            msg:'请输入字段',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#FIELD").focus();
				return false;
			}
			var FIELDTYPE=$("#FIELDTYPE").val();
			var FIELDNUM=$("#FIELDNUM").val();
			if($("#FIELDNUM").val()==""&&FIELDTYPE!="date"){
				$("#FIELDNUM").tips({
					side:3,
		            msg:'请输入长度',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#FIELDNUM").focus();
				return false;
			}
			
			
			
			if(FIELDTYPE=="number"||FIELDTYPE=="float"){
				if(FIELDNUM>10){
					//modals.info("数字型，长度不能超过20");
					$("#FIELDNUM").tips({
						side:3,
			            msg:'数字型，长度不能超过10',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#FIELDNUM").focus();
					return false;
				}
			}
			
			
			////$("#zhongxin").hide();
			//$("#zhongxin2").show();
			$.ajax({
            //几个参数需要注意一下
                type: "POST",//方法类型
                dataType: "html",//预期服务器返回的数据类型
                url: "field/${msg }.do" ,//url
                data: $('#Form_add').serialize(),
                success: function (result) {
                  // alert(result);//打印服务端返回的数据(调试用)
                    if (result.indexOf("success_save")>=0) {
                    	location.href="<%=basePath%>field/list.do";
                    }else if(result.indexOf("error1")>=0){
                    	modals.info("字段已存在不能重复添加");
                    }else{
                    	modals.info("操作失败");
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