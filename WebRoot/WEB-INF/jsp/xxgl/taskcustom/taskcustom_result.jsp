<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- 下拉框 -->
<!-- jsp文件头和头部 -->
<%-- 
<%@ include file="../index/top.jsp"%> --%>
<%@ include file="../../system/include/incJs_mx.jsp"%>
  <!-- iCheck for checkboxes and radio inputs -->
<link rel="stylesheet" href="static/ace/plugins/iCheck/all.css">
<style>
td{
	width: 95px; 
	max-width: 95px; 
	white-space: nowrap; 
	text-overflow: ellipsis; 
	overflow: hidden; 
}
th{
	width: 95px; 
	max-width: 95px; 
	white-space: nowrap; 
	text-overflow: ellipsis; 
	overflow: hidden; 
}
.close{
	line-height: initial;
	    font-weight: normal;
	        margin-top: 0!important;
	    opacity:1;
	        color: #fff;
}
.modal-footer{
	border-top:0!important;
}
.close:focus, .close:hover {
    color: #fff;

    opacity: 1;
}
</style>
</head>
<body class="no-skin" >
	<div class="content-wrapper" style="margin-left:0px;">
		<section class="container-fluid">
            <div class="seat-middle-top-x"></div>
            <div class="box-header">
							任务名称：<select id="TASKID" name="TASKID" style="height:30px;width:160px;" >
								<option value="">--请选择任务--</option>
								<c:forEach items="${varList}" var="var" varStatus="vs">
									<option value="${var.ID}">${var.TASKNAME}</option>
								</c:forEach>
							</select>
							&nbsp;&nbsp;&nbsp;&nbsp;坐席组:<select id="ZXZ" name="ZXZ"  onchange="getZxman()" style="height:30px;width:160px;">
								<option value="">--请选择坐席组--</option>
								<c:forEach items="${zxzList}" var="var" varStatus="vs">
									<option value="${var.ID}" <c:if test="${var.ID==ZXZ }">selected</c:if>>${var.ZMC}</option>
								</c:forEach>
							</select>
							&nbsp;&nbsp;&nbsp;&nbsp;坐席人员：<select id="ZXMAN" name="ZXMAN" style="height:30px;width:160px;" ><option value="">--请选择坐席组--</option></select>
							<a class="btn btn-mini btn-success" style="margin-left: 15px;height: 28px;line-height: 13px;margin-bottom: 5px;" onclick="getCustom();">查询</a>
						</div>
            <div class="seat-middle">
               <!--  <div class="seat-middle-nr"> -->
                    
                    <div class="xtyh-middle-r zxzgl-middle-r">
                        <!-- <div class="box-body" > -->
				
							<div id="customdiv">
								<div style="margin-top:40px;">
									<table id="example_t" class="table table-bordered table-hover">
										<thead>
											<tr>
												<th class="center" style="width:30px;">序号</th>
												<th class="center">坐席人员</th>
												<th class="center">回访结果</th>
												<th class="center">回访情况</th>
												<th class="center" style="width:150px;">操作</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td align="center" colspan="5">无数据</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
					
                    </div>
              
            </div>
        </section>
        
        
		<%-- <section class="content">
			<div class="row">
				<div class="col-xs-12">
					<div class="box">
						<div class="box-header">
							任务名称：<select id="TASKID" name="TASKID" style="height:30px;width:160px;" >
								<option value="">--请选择任务--</option>
								<c:forEach items="${varList}" var="var" varStatus="vs">
									<option value="${var.ID}">${var.TASKNAME}</option>
								</c:forEach>
							</select>
							&nbsp;&nbsp;&nbsp;&nbsp;坐席组:<select id="ZXZ" name="ZXZ"  onchange="getZxman()" style="height:30px;width:160px;">
								<option value="">--请选择坐席组--</option>
								<c:forEach items="${zxzList}" var="var" varStatus="vs">
									<option value="${var.ID}" <c:if test="${var.ID==ZXZ }">selected</c:if>>${var.ZMC}</option>
								</c:forEach>
							</select>
							&nbsp;&nbsp;&nbsp;&nbsp;坐席人员：<select id="ZXMAN" name="ZXMAN" style="height:30px;width:160px;" ><option value="">--请选择坐席组--</option></select>
							<a class="btn btn-mini btn-success" onclick="getCustom();">查询</a>
						</div>
						<!-- /.box-header -->
						<div class="box-body">
							
							<div id="customdiv">
								<div style="margin-top:40px;">
									<table id="example_t" class="table table-bordered table-hover">
										<thead>
											<tr>
												<th class="center" style="width:30px;">序号</th>
												<th class="center">坐席人员</th>
												<th class="center">回访结果</th>
												<th class="center">回访情况</th>
												<th class="center" style="width:150px;">操作</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td align="center" colspan="5">无数据</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>

						</div>
						<!-- /.box-body -->
					</div>
					<!-- /.box -->

				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
			<div class="modal" id="mytmModal">
				<div class="modal-dialog" style="width:900px;">
					<div class="modal-content">
						<div class="modal-header">
							<h4 class="modal-title" id="myModalLabel" style="float: left;">题目回答情况</h4>
							<button type="button" class="btn btn-info btn-sm" style="float: right;"   data-dismiss="modal" title="关闭">
		                 	<i class="fa fa-times"></i></button>
						</div>
						<!-- /.box-header -->
						<div class="modal-body">
							<div class="todo-list" id="tmstr" style="margin-top:0px;margin-right:10px;">
								
							</div>
						</div>
					</div>
				</div>
			</div>
			
	</div>
	<!-- /.col -->
	</section> --%>
	<!-- /.content -->
	</div>

	<!-- /.main-container -->

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!-- iCheck 1.0.1 -->
	<script src="static/ace/plugins/iCheck/icheck.min.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script  type="text/javascript"  src="static/js/jquery.form.js"></script>
	<script type="text/javascript">
	
		$(function() {
			var ZXZSTR='${ZXZ}';
			//alert(ZXZSTR);
			if(ZXZSTR!=""){
				getZxman();
				//getCustom();
			}
			
			$('#example_t').DataTable({
		      'paging'      : true,
		      'lengthChange': false,
		      'searching'   : false,
		      'ordering'    : false,
		      'info'        : true,
		      'autoWidth'   : true
		    });
		});	
		//删除
		function getCustom(){
			var TASKID=$("#TASKID").val();
			var ZXZ=$("#ZXZ").val();
			var ZXMAN=$("#ZXMAN").val();
			if(TASKID==""){
				modals.info("请选择任务！");
				return false;
			}
			
			
			var url = "<%=basePath%>taskcustom/getCustomList.do?ID="+TASKID+"&TASKID="+TASKID+"&ZXZ="+ZXZ+"&ZXMAN="+ZXMAN+"&action=result&tm="+new Date().getTime();
			$.get(url,function(data){
				//alert(data);
				var obj= JSON.parse(data);
				$("#customdiv").html("");
				
				//if(obj.customString.indexOf("暂无数据")>=0){
					$("#customdiv").append("<div style=\"margin-top:40px;\">"+obj.customString+"</div>");
				//}else{
					//$("#customdiv").append(obj.customString);
				//}
				settable();
				//tj();
			});
		}
		
		function getZxman(){
			var ZXZ=$("#ZXZ").val();
			if(ZXZ!=""){
				var url = "<%=basePath%>taskcustom/getZxlb.do?ZXZ="+ZXZ+"&action=result&tm="+new Date().getTime();
				$.get(url,function(data){
					//alert(data);
					var obj= JSON.parse(data);
					$("#ZXMAN").html("");
					$("#ZXMAN").append("<option value=\"\">请选择坐席人员</option>"+obj.zxlbstr);
				});
			}
			
		}
		
		function settable(){
			 $('#customtable').DataTable({
		      'paging'      : true,
		      'lengthChange': false,
		      'searching'   : false,
		      'ordering'    : false,
		      'info'        : true,
		      'autoWidth'   : true
		    }) 
		}
		
		
		function search_cus(ID,TASKID){
			var winId = "userWin";
			//alert(Id);
			
			//alert(TABLENAME);
		  	modals.openWin({
	          	winId: winId,
	          	title: '客户信息',
	          	width: '900px',
	          	height: '400px',
	          	url: "<%=basePath%>taskcustom/goSearch.do?TASKID="+TASKID+"&action=result&ID="+ID
	          	/*, hideFunc:function(){
	             	 modals.info("hide me");
	          	},
	          	showFunc:function(){
	              	modals.info("show me");
	          	} */
	      	});
		}
		
		function search_answer(ID,TASKID){
			var winId = "userWin";
			//alert(Id);
			
		  	var url = "<%=basePath%>exetask/getAnswer.do?TASKID="+TASKID+"&action=result&ID="+ID
			$.get(url,function(data){
				//alert(data);
				var obj= JSON.parse(data);
				$("#tmstr").html("");
				$("#tmstr").append(obj.tmstr);
				$("#mytmModal").modal("toggle");
			});
			
			
		}
		
		
	</script>


</body>
</html>