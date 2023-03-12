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
<!-- jsp文件头和头部 --><%-- 
<%@ include file="../index/top.jsp"%> --%>
<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<%@ include file="../../system/include/incJs_mx.jsp"%>
<!-- 日期框 -->
<link rel="stylesheet" href="static/ace/css/datepicker.css" />
<style>
	.table th{
		font-size:14px;
	}
	.table td{
		font-size:14px;
	}
	.box-header td{
		width:190px;
	}
	.box-header input{
		height:35px;
		width:170px;
		font-size:14px;
	}
	.box-header select{
		height:35px;
		width:170px;
		font-size:14px;
	}
</style>
</head>
<body class="no-skin">
<div class="content-wrapper" style="width:100%;margin-left:0px;">
    <section class="content-header">
      <h1>
        	信息管理
        <small>预警管理</small>
      </h1>
      <ol class="breadcrumb">
        <li>
			<a href="javascript:;" onclick="home()">
				<i class="fa fa-dashboard"></i> 主页
			</a>
		</li>
		<li class="active">预警管理</li>
      </ol>
    </section>
<section class="content">
      <div class="row">
        <div class="col-xs-12">
          <div class="box">
            <div class="box-header">
	              	<!-- 检索  -->
				<form action="warn/list.do" method="post" name="Form" id="Form">
					<table style="margin-top:5px;">
						<tr>
							<td style="padding-left:2px;"><input class="span10 date-picker" name="lastLoginStart" id="lastLoginStart"  value="${pd.lastLoginStart}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly"  placeholder="开始日期" title="预警开始"/></td>
							<td style="padding-left:2px;"><input class="span10 date-picker" name="lastLoginEnd" name="lastLoginEnd"  value="${pd.lastLoginEnd}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="结束日期" title="预警结束"/></td>
							<td style="padding-left:2px;">
								<div class="nav-search">
								<span class="input-icon">
									<input class="nav-search-input" autocomplete="off" id="endpoint" type="text" name="endpoint" value="${pd.endpoint }" placeholder="这里输入设备编号" />
								</span>
								</div>
							</td>
							<td style="vertical-align:top;padding-left:2px;">
							 	<select class="chosen-select form-control" name="caseid" id="caseid" data-placeholder="请选择所属实例" style="vertical-align:top;">
								
									<option value="">请选择实例</option>
									<c:forEach items="${listcase_s}" var="caseBean" varStatus="vs">
										<option value="${caseBean.id }" <c:if test="${pd.caseid==caseBean.id}">selected</c:if>>${caseBean.case_name }</option>
									</c:forEach>
							  	</select>
							</td>
							<td style="vertical-align:top;padding-left:2px;">
							 	<select class="chosen-select form-control" name="field" id="field" data-placeholder="请选择所属实例" style="vertical-align:top;">
									<option value="">请选择数据项目</option>
									<c:forEach items="${dictList_zd}" var="zdBean" varStatus="vs">
										<option value="${zdBean.NAME_EN }" <c:if test="${pd.field==zdBean.NAME_EN}">selected</c:if>>${zdBean.NAME }(${zdBean.NAME_EN })</option>
									</c:forEach>
							  	</select>
							</td>
							<td style="vertical-align:top;padding-left:2px;">
								<a class="btn btn-mini btn-success" onclick="tosearch()">查询</a>
							</td>
						</tr>
					</table>
				</form>	
            </div>
            <!-- /.box-header -->
            <div class="box-body" style="margin-top:40px">
              <table id="example2" class="table table-bordered table-hover">
                	<thead>
						<tr>
							
							<th class="center" style="width:30px;">序号</th>
							<th class="center">实例名称</th>
							<th class="center">设备</th>
							<th class="center">数据项目</th>
							<th class="center">值</th>
							<th class="center">预警信息</th>
							<th class="center">预警时间</th>
						</tr>
					</thead>
											
					<tbody>
					<!-- 开始循环 -->	
					<c:choose>
						<c:when test="${not empty varList}">
							<c:if test="${QX.cha == 1 }">
							<c:forEach items="${varList}" var="var" varStatus="vs">
								<tr>
									
									<td class='center' style="width: 30px;">${vs.index+1}</td>
									<td class='center'>${var.CASE_NAME}</td>
									<td class='center'>${var.ENDPOINT_NAME}(${var.ENDPOINT})</td>
									<td class='center'>${var.FIELDNAME}(${var.FIELD})</td>
									<td class='center'>${var.NUM}</td>
									<td class='center'>${var.REMARK}</td>
									<td class='center'>${var.CREATEDATE}</td>
									
								</tr>
							
							</c:forEach>
							</c:if>
							<c:if test="${QX.cha == 0 }">
								<tr>
									<td colspan="100" class="center">您无权查看</td>
								</tr>
							</c:if>
						</c:when>
						<c:otherwise>
							<tr class="main_info">
								<td colspan="100" class="center" >没有相关数据</td>
							</tr>
						</c:otherwise>
					</c:choose>
                
                </tbody>
               
              </table>
           
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /.box -->

        </div>
        <!-- /.col -->
      </div>
      <!-- /.row -->
    </section>
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
	<script type="text/javascript">
		//$(top.hangge());//关闭加载状态
		//检索
		function tosearch(){
			$("#Form").submit();
		}
		$(function() {
				//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
				
			$('#example2').DataTable({
		      'paging'      : true,
		      'lengthChange': false,
		      'searching'   : false,
		      'ordering'    : true,
		      'info'        : true,
		      'autoWidth'   : true
		    })
		});
		
		//新增
		function add(){
			var winId = "userWin";
		  	modals.openWin({
	          	winId: winId,
	          	title: '新增',
	          	width: '900px',
	          	height: '400px',
	          	url: '<%=basePath%>field/goAdd.do'
	          	/*, hideFunc:function(){
	             	 modals.info("hide me");
	          	},
	          	showFunc:function(){
	              	modals.info("show me");
	          	} */
	      	});
			
		}
		
		//删除
		function del(Id,Uid){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>field/delete.do?ID="+Id+"&UID="+Uid+"&tm="+new Date().getTime();
					$.get(url,function(data){
						location.href="<%=path%>/field/list.do";
					});
				}
			});
		}
		
		//修改
		function edit(Id,Uid){
			
			var winId = "userWin";
		  	modals.openWin({
	          	winId: winId,
	          	title: '新增',
	          	width: '900px',
	          	height: '400px',
	          	url: '<%=basePath%>field/goEdit.do?UID="+Uid+"&ID='+Id
	          	/*, hideFunc:function(){
	             	 modals.info("hide me");
	          	},
	          	showFunc:function(){
	              	modals.info("show me");
	          	} */
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
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>field/deleteAll.do?tm='+new Date().getTime(),
						    	data: {DATA_IDS:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
										 location.href="<%=path%>/field/list.do";
									 });
								}
							});
						}
					}
				}
			});
		};
		
		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>field/excel.do';
		}
	</script>


</body>
</html>