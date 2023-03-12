<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.fh.util.Jurisdiction" %>
<%
	Jurisdiction jurisdiction=new Jurisdiction();
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="../../system/include/incJs_mx.jsp"%>
</head>
<style>
@media screen and (max-width: 1700px) {
	.xtyh-middle-r {
		width: 100% !important;
	}
}

.zxzgl-middle-r {
	width: 100% !important;
}
</style>
<body class="no-skin">


	<div class="content-wrapper" style="width:100%;margin-left:0px;">
		
		<section class="container-fluid">
			<div class="seat-middle-top-x"></div>
			<div class="seat-middle-top">
				<div class="seat-middle-top-left">
                    <div class="seat-middle-top-left-bt">流程管理</div>
                    <div class="seat-button">
                   	<%if(jurisdiction.hasQx("990602")){ %>
						<a href="javascript:void (0)" onclick="add();">新增</a>
					<%} %>
                    </div>
                </div>
                
            </div>
			
			<div class="seat-middle">
				<div class="xtyh-middle-r zxzgl-middle-r">
					<table id="example2" class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								
								<th class="center cy_th" style="width:50px;">序号</th>
								<th id='cy_thk'></th>
								<th class="center">模型名称</th>
								<th class="center">分类</th>
								<th class="center"><i
									class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>创建时间</th>
								<th class="center"><i
									class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>最后更新时间</th>
								<th class="center">版本</th>
								<th class="center">操作</th>
							</tr>
						</thead>
	
						<tbody>
							<!-- 开始循环 -->
							<c:choose>
								<c:when test="${not empty varList}">
									<%if(jurisdiction.hasQx("990601")){ %>
										<c:forEach items="${varList}" var="var" varStatus="vs">
											<tr>
												<td class='center cy_td' id="#${var.ID}" style="width: 30px;">${vs.index+1}</td>
												<td id='cy_thk'></td>
												<td class='center'>${var.NAME_}</td>
												<td class='center'>${var.DNAME}<span class="green" style="cursor: pointer;"> 
													<i class="ace-icon fa fa-pencil-square-o bigger-100" title="修改" onclick="goEditType('${var.ID_}');"></i>
												</span>
												</td>
												<td class='center'>${fn:substring(var.CREATE_TIME_ ,0,19)}</td>
												<td class='center'>${fn:substring(var.LAST_UPDATE_TIME_ ,0,19)}</td>
												<td class='center'>v.${var.VERSION_}</td>
												<td class="center">
													<div>
														<%if(jurisdiction.hasQx("990605")){ %>
															<a title="流程设计器" onclick="editor('${var.ID_}');">流程设计器 </a>
															<a style="margin-left:5px;" title="部署" onclick="deployment('${var.ID_}','F${var.ID_}');"> 部署</a>
															<%-- <a class="btn btn-xs btn-warning" title="预览" onclick="view('${var.ID_}','F${var.ID_}');">
															预览
														</a> --%>
															<a style="margin-left:5px;" title="导出" onclick="exportXml('${var.ID_}','F${var.ID_}');"> 导出 </a>
														<%} %>
														<%if(jurisdiction.hasQx("990604")){ %>
															<a style="margin-left:5px;" onclick="del('${var.ID_}');">删除 </a>
														<%} %>
													</div>
												</td>
											</tr>
	
										</c:forEach>
									<%} %>
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="100" class="center">没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				</div>
			</div>
		</section>
	</div>

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<!-- 删除时确认窗口 -->
	<%@ include file="../../system/include/incJs_foot.jsp" %>
	<script src="static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!--引入弹窗组件2start-->
	<script type="text/javascript" src="plugins/attention/drag/drag.js"></script>
	<script type="text/javascript" src="plugins/attention/drag/dialog.js"></script>
	<link type="text/css" rel="stylesheet" href="plugins/attention/drag/style.css" />
	<!--引入弹窗组件2end-->
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script>
	  $(function () {
		$('#example2').DataTable({
				'paging' : true,
				'lengthChange' : false,
				'searching' : true,
				'ordering' : true,
				'info' : true,
				'autoWidth' : true
			})
		})
	</script>
	<script type="text/javascript">
		//$(top.hangge());//关闭加载状态
		//检索
		function tosearch(){
			location.href="<%=path%>/fhmodel/list.do";
			//$("#Form").submit();
		}
		$(function() {
			
			$('.seat-middle').find(".row:first").hide();
			$(".xtyh-middle-r").find(".row:last").addClass("row-zg");
			$(".xtyh-middle-r").find(".row:eq(1)").addClass("row-two");
			
			//复选框全选控制
			var active_class = 'active';
			$('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function(){
				var th_checked = this.checked;//checkbox inside "TH" table header
				$(this).closest('table').find('tbody > tr').each(function(){
					var row = this;
					if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
					else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
				});
			});
			
			var category = "${pd.category}";
			$.ajax({
				type: "POST",
				url: '<%=basePath%>dictionaries/getLevels.do?tm='+new Date().getTime(),
		    	data: {DICTIONARIES_ID:'act001'},//act001 为工作流分类
				dataType:'json',
				cache: false,
				success: function(data){
					$("#category").html('<option value="" >请选择分类</option>');
					 $.each(data.list, function(i, dvar){
						 if(category == dvar.BIANMA){
							 $("#category").append("<option value="+dvar.BIANMA+" selected='selected'>"+dvar.NAME+"</option>");
						 }else{
							 $("#category").append("<option value="+dvar.BIANMA+">"+dvar.NAME+"</option>");
						 }
					 });
				}
			});
			
		});
		
		//新增
		function add(){
			 var winId = "userWin";
			  modals.openWin({
		          winId: winId,
		          title: '新增',
		          width: '700px',
		          url: '<%=basePath%>fhmodel/goAdd.do'
		      }); 
		
		}
		
		//打开流程设计器
		function editor(modelId){
			
			  
			 var diag = new Dialog();
			 diag.Drag=true;
			 diag.Title ="流程设计器";
			 diag.URL = '<%=basePath%>fhmodel/editor.do?modelId='+modelId;
			 diag.Width = 1230;
			 diag.Height = 700;
			 diag.Modal = false;			//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}
		
		//打开修改类型页面
		function goEditType(ID_){
			 
			 var diag = new Dialog();
			 diag.Drag=true;
			 diag.Title ="修改类型";
			 diag.URL = '<%=basePath%>fhmodel/goEdit.do?ID_='+ID_;
			 diag.Width = 300;
			 diag.Height = 130;
			 diag.Modal = false;			//有无遮罩窗口
			 diag. ShowMaxButton = false;	//最大化按钮
		     diag.ShowMinButton = false;	//最小化按钮
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					//tosearch();
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//部署流程定义
		function deployment(modelId,id){
			
			$.ajax({
				type: "POST",
				url: '<%=basePath%>fhmodel/deployment.do?tm='+new Date().getTime(),
		    	data: {modelId:modelId},
				dataType:'json',
				cache: false,
				success: function(data){
					if("error" == data.result){
						alert("预览失败! 检查下是否已经画正确的流程图了?");
					}else{
						alert("部署成功! 可到流程管理中查看");
						/* $("#"+id).tips({
							side:2,
				            msg:'部署成功! 可到流程管理中查看',
				            bg:'#87B87F',
				            time:15
				        }); */
					}
				}
			});
		}
		
		//导出模型xml
		function exportXml(modelId,id){
			
			$.ajax({
				type: "POST",
				url: '<%=basePath%>fhmodel/isCanexportXml.do?tm='+new Date().getTime(),
		    	data: {modelId:modelId},
				dataType:'json',
				cache: false,
				success: function(data){
					if("error" == data.result){
						
						alert("导出失败! 检查下是否已经画正确的流程图了?");
					}else{
						window.location.href='<%=basePath%>fhmodel/exportXml.do?modelId='+modelId;
					}
				}
			});
		}
		
		//预览
		function view(modelId,id){
			
			$.ajax({
				type: "POST",
				url: '<%=basePath%>fhmodel/isCanexportXml.do?tm='+new Date().getTime(),
		    	data: {modelId:modelId},
				dataType:'json',
				cache: false,
				success: function(data){
					if("error" == data.result){
						alert("预览失败! 检查下是否已经画正确的流程图了?");
					}else{
						var diag = new Dialog();
						diag.Drag=true;
						diag.Title ="预览XML";
						diag.URL = '<%=basePath%>fhmodel/goView.do?modelId='+modelId;
						diag.Width = 1000;
						diag.Height = 608;
						diag.Modal = false;				//有无遮罩窗口
						diag.ShowMinButton = true;		//最小化按钮
						diag.CancelEvent = function(){ 	//关闭事件
						diag.close();
						};
						diag.show();
					}
				}
			});
		}
		
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					
					var url = "<%=basePath%>fhmodel/delete.do?ID_="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						tosearch();
					});
				}
			});
		}
		
		//批量操作
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++){
					  if(document.getElementsByName('ids')[i].checked){
					  	if(str=='') str += document.getElementsByName('ids')[i].value;
					  	else str += ',' + document.getElementsByName('ids')[i].value;
					  }
					}
					if(str==''){
						bootbox.dialog({
							message: "<span class='bigger-110'>您没有选择任何内容!</span>",
							buttons: 			
							{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
						});
						$("#zcheckbox").tips({
							side:1,
				            msg:'点这里全选',
				            bg:'#AE81FF',
				            time:8
				        });
						return;
					}else{
						if(msg == '确定要删除选中的数据吗?'){
							
							$.ajax({
								type: "POST",
								url: '<%=basePath%>fhmodel/deleteAll.do?tm='+new Date().getTime(),
						    	data: {DATA_IDS:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
											tosearch();
									 });
								}
							});
						}
					}
				}
			});
		};
		
		
	</script>


</body>
</html>