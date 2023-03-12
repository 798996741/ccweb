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

							<form action="doctype/${msg }.do" name="Form" id="Form_add"
								method="post">
								<input type="hidden" name="id" id="id" value="${pd.id}" />

								<c:if test="${msg=='edit' }">
									<input type="hidden" name="parentid" id="parentid"
										value="${null == pd.parentid ?pd.id:pd.parentid}" />
								</c:if>
								<c:if test="${msg=='save' }">
									
									<input type="hidden" name="parentid" id="parentid"
										value="${null == id ? '0':id}" />
								</c:if>
								<div id="zhongxin">
									<div id="table_report"
										class="table table-striped table-bordered table-hover new-wkj">

										<div class="new-block">
											上级:<b>${null == pds.name ?'(无) 此项为顶级':pds.name}</b>
										</div>

										<div class="new-tk-body-one">
											<div class="new-tk-body-one-wk">
												<div class="one-wk-l">分类名称:</div>
												<div class="one-wk-r">
													<input type="text" name="name" id="name" value="${pd.name}"
														maxlength="50" placeholder="这里输入分类名称" title="分类名称" />
												</div>
											</div>
											<span> </span>
										</div>



										<div class="new-tk-body-one">
											<div class="new-tk-body-one-wk">
												<div class="one-wk-l">排序:</div>
												<div class="one-wk-r">
													<input type="number" name="sort" id="sort"
														value="${pd.sort}" maxlength="32" placeholder="这里输入排序"
														title="排序" />
												</div>
											</div>
											<span> </span>
										</div>
										<div class="new-tk-body-one">
											<div class="new-tk-body-one-wk">
												<div class="one-wk-l">状态:</div>
												<div class="one-wk-r">
														<select id="state" name="state">
															<option value="1"
																<c:if test="${pd.state=='1'}">selected</c:if>>启用</option>
															<option value="0"
																<c:if test="${pd.state=='0'}">selected</c:if>>禁用</option>
														</select>
													
												</div>
											</div>
											<span> </span>
										</div>

										<div class="new-tk-body-one" style="width:100%">
											<div class="new-tk-body-one-wk" style="width:100%">
												<div class="one-wk-l">备注:</div>
												<div class="one-wk-r" style="width:100%">
													<input type="text" name="cont" id="cont" value="${pd.cont}"
														maxlength="1000" style="width:100%" placeholder="这里输入备注" title="备注"  />
												</div>
											</div>
											<span> </span>
										</div>






										<div class="new-bc">
											<a onclick="save();">保存</a> <a class="new-qxbut"
												data-btn-type="cancel" data-dismiss="modal">取消</a>
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

							<div id="zhongxin2" class="center" style="display:none">
								<img src="static/images/jzx.gif" style="width: 50px;" /><br />
								<h4 class="lighter block green"></h4>
							</div>
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


	<!--提示框-->
	<script type="text/javascript">
		//$(top.hangge());
		//保存
		function save(){
			if($("#name").val()==""){
				$("#name").tips({
					side:3,
		            msg:'请输入分类名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#name").focus();
			return false;
		}
			if($("#sort").val()==""){
				$("#sort").tips({
					side:3,
		            msg:'请输入排序',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#sort").focus();
			return false;
		}
			
			//$("#Form_add").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
			$.ajax({
            	//几个参数需要注意一下
                type: "POST",//方法类型
                dataType: "html",//预期服务器返回的数据类型
                url: "doctype/${msg }.do" ,//url
                data: $('#Form_add').serialize(),
                success: function (result) {
                  //  console.log(result.);//打印服务端返回的数据(调试用)
                    if (result.indexOf("success")>=0) {
                    	parent.location.href="<%=basePath%>doctype/listAllDict.do?id=${id}";
                    }},
                    error : function() {
                        alert("异常！");
                    }
                });
			
			
		}
		
		//判断编码是否存在
		function hasBianma(){
			var BIANMA = $.trim($("#BIANMA").val());
			if("" == BIANMA)return;
			$.ajax({
				type: "POST",
				url: '<%=basePath%>doctype/hasBianma.do',
				data : {
					BIANMA : BIANMA,
					tm : new Date().getTime()
				},
				dataType : 'json',
				cache : false,
				success : function(data) {
					if ("success" == data.result) {
					} else {
						$("#BIANMA").tips({
							side : 1,
							msg : '编码' + BIANMA + '已存在,重新输入',
							bg : '#AE81FF',
							time : 5
						});
						$('#BIANMA').val('');
					}
				}
			});
		}
	</script>
</body>
</html>