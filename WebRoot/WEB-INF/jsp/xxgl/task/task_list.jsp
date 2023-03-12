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
<%@ include file="../../system/include/incJs_mx.jsp"%>
<style>
	
</style>
</head>
<body class="no-skin">
<div class="content-wrapper" style="width:100%;margin-left:0px;">
    <section class="container-fluid">
            <div class="seat-middle-top-x"></div>
            <div class="seat-middle-top">
                <div class="seat-middle-top-left">
                    <div class="seat-middle-top-left-bt">任务管理</div>
                    <div class="seat-middle-top-left-tp">
                    <c:if test="${QX.add == 1 }">
                        <a href="javascript:void (0)" onclick="add_task();">新增</a>
                    </c:if>
                    </div>
                </div>
                 <div class="seat-middle-top-right">
					<input placeholder="搜  索" name="keywords" id="keywords" value="${pd.keywords }"> 
					<a href="javascript:void(0)">
					<img src="static/login/images/icon-search.png" onclick="tosearch()"></a>
				</div>
            </div>
            <div class="seat-middle">
               <!--  <div class="seat-middle-nr"> -->
                    
                    <div class="xtyh-middle-r zxzgl-middle-r">
                        <!-- <div class="box-body" > -->
				
							<table id="example2" class="table table-bordered table-hover">
                	<thead>
						<tr>
							<th class="center cy_th" style="width:30px;">序号</th>
								<th id='cy_thk'></th>
							<th class="center">任务类型</th>
							<th class="center">任务名称</th>
							<th class="center">问卷模板名称</th><!-- 
							<th class="center">客户信息名称</th> -->
							<th class="center">完成期限</th>
							<th class="center">任务状态</th>
							<!--  <th class="center">执行人员</th>-->
							<th class="center">操作人</th>
							<th class="center">操作时间</th>
							<th class="center" style="width:60px;">操作</th>
						</tr>
					</thead>
											
					<tbody>
					<!-- 开始循环 -->	
					<c:choose>
						<c:when test="${not empty varList}">
							<c:if test="${QX.cha == 1 }">
							<c:forEach items="${varList}" var="var" varStatus="vs">
								<tr>
									<td class='center cy_td' style="width: 30px;">${vs.index+1}</td>
									<td id='cy_thk'></td>
									<td class='center'>${var.TYPENAME}</td>
									<td class='center'>${var.TASKNAME}</td>
									
									<td class='center new-ys'>
										<span style="cursor:pointer" onclick="searchwjmb('${var.NAIRE_TEMPLATE_ID}')">
											${var.NAIRE_TEMPLATE_NAME}
										</span>
									</td>
									<%-- <td class='center new-ys'>
										<span style="cursor:pointer" onclick="searchkhmb('${var.CUSTOM_TEMPLATE_ID}')">
											${var.CUSTOM_TEMPLATE_NAME}
										</span>
									</td> --%>
									<td class='center'>${var.COMPLETEDATE}</td>
									<td class='center'>
										<c:if test="${var.STATE==0}">待执行任务</c:if>
										<c:if test="${var.STATE==1}">开始任务</c:if>
										<c:if test="${var.STATE==2}">结束任务</c:if>
									</td>
									<!-- <td class='center'>${var.TASKMAN}</td>-->
									<td class='center'>${var.CREATEMAN}</td>
									<td class='center'>${var.CREATEDATE}</td>
									<td class="center">
										<c:if test="${QX.edit != 1 && QX.del != 1 }">
										<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
										</c:if>
										<div class="hidden-sm hidden-xs flex-position btn-group">
											<%-- <c:if test="${QX.edit == 1 }">
												<a class="btn btn-xs btn-success" title="编辑" onclick="edit_task('${var.ID}');">
													<i class="ace-icon fa fa-pencil-square-o bigger-120" title="编辑"></i>
												</a>
											</c:if>
											
											<c:if test="${QX.del == 1 }">
												<a style="margin-left:10px;" class="btn btn-xs btn-danger" onclick="del_task('${var.ID}');">
													<i class="ace-icon fa fa-trash-o bigger-120" title="删除"></i>
												</a>
											</c:if> --%>
											<div class="flex-row">
											<c:if test="${QX.edit == 1 }">
												<div class="button-edit" title="编辑"
													 onclick="edit_task('${var.ID}');" title="编辑">
													<font class="button-content">编辑</font>
												</div>
<%--												<a class="cy_bj" title="编辑"--%>
<%--													onclick="edit_task('${var.ID}');"> <i--%>
<%--													--%>
<%--													title="编辑"></i>--%>
<%--												</a>--%>
											</c:if>
											<c:if test="${QX.del == 1 }">
												<div class="button-delete" style="margin-left:10px;"
													 title="删除"
													 onclick="del_task('${var.ID}');" title="删除">
													<font class="button-content">删除</font>
												</div>
<%--												<a style="margin-left:10px;" class="cy_sc"--%>
<%--													onclick="del_task('${var.ID}');"> <i--%>
<%--													 title="删除"></i>--%>
<%--												</a>--%>
											</c:if>
										</div>
										</div>
									</td>
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
              
            </div>
        </section>
<%-- <section class="content">
      <div class="row">
        <div class="col-xs-12">
          <div class="box">
            <div class="box-header">
              	<c:if test="${QX.add == 1 }">
					<a class="btn btn-mini btn-success" onclick="add_task();">新增</a>
				</c:if>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
              <table id="example2" class="table table-bordered table-hover">
                	<thead>
						<tr>
							<th class="center" style="width:30px;">序号</th>
							<th class="center">任务类型</th>
							<th class="center">任务名称</th>
							<th class="center">问卷模板名称</th>
							<th class="center">客户信息名称</th>
							<th class="center">完成期限</th>
							<th class="center">任务状态</th>
							<!--  <th class="center">执行人员</th>-->
							<th class="center">操作人</th>
							<th class="center">操作时间</th>
							<th class="center" style="width:60px;">操作</th>
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
									<td class='center'>${var.TYPENAME}</td>
									<td class='center'>${var.TASKNAME}</td>
									
									<td class='center'>
										<span style="cursor:pointer" onclick="searchwjmb('${var.NAIRE_TEMPLATE_ID}')">
											${var.NAIRE_TEMPLATE_NAME}(<span style="color:red">查看</span>)
										</span>
									</td>
									<td class='center'>
										<span style="cursor:pointer" onclick="searchkhmb('${var.CUSTOM_TEMPLATE_ID}')">
											${var.CUSTOM_TEMPLATE_NAME}(<span style="color:red">查看</span>)
										</span>
									</td>
									<td class='center'>${var.COMPLETEDATE}</td>
									<td class='center'>
										<c:if test="${var.STATE==0}">待执行任务</c:if>
										<c:if test="${var.STATE==1}">开始任务</c:if>
										<c:if test="${var.STATE==2}">结束任务</c:if>
									</td>
									<!-- <td class='center'>${var.TASKMAN}</td>-->
									<td class='center'>${var.CREATEMAN}</td>
									<td class='center'>${var.CREATEDATE}</td>
									<td class="center">
										<c:if test="${QX.edit != 1 && QX.del != 1 }">
										<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
										</c:if>
										<div class="hidden-sm hidden-xs btn-group">
											<c:if test="${QX.edit == 1 }">
												<a class="btn btn-xs btn-success" title="编辑" onclick="edit_task('${var.ID}');">
													<i class="ace-icon fa fa-pencil-square-o bigger-120" title="编辑"></i>
												</a>
											</c:if>
											
											<c:if test="${QX.del == 1 }">
												<a style="margin-left:10px;" class="btn btn-xs btn-danger" onclick="del_task('${var.ID}');">
													<i class="ace-icon fa fa-trash-o bigger-120" title="删除"></i>
												</a>
											</c:if>
										</div>
										
									</td>
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
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
		//$(top.hangge());//关闭加载状态
		//检索
		function tosearch(){
			//top.jzts();
			
			var keywords=$("#keywords").val();
			location.href="<%=path%>/taskuser/list.do?keywords="+encodeURI(encodeURI(keywords));
			//$("#Form_s").submit();
		}
		//修改
		function searchwjmb(Id){
			
			var winId = "userWin";
		  	modals.openWin({
	          	winId: winId,
	          	title: '查看题目问卷',
	          	width: '900px',
	          	height: '500px',
	          	url: '<%=basePath%>naire/list.do?action=search&NAIRE_TEMPLATE_ID='+Id
	      	});
		}
		
		function searchkhmb(Id){
			var winId = "userWin";
		  	modals.openWin({
	          	winId: winId,
	          	title: '查看客户模板',
	          	width: '900px',
	          	height: '500px',
	          	url: '<%=basePath%>customtemplatefield/list.do?action=search&CUS_TEMP_ID='+Id
	      	});
		}
		
		$(function() {
			$('#example2').DataTable({
		      'paging'      : true,
		      'lengthChange': false,
		      'searching'   : true,
		      'ordering'    : false,
		      'info'        : true,
		      'autoWidth'   : true
		    })
		});
		
		//新增
		function add_task(){
			var winId = "userWin";
		  	modals.openWin({
	          	winId: winId,
	          	title: '新增任务',
	          	width: '900px',
	          	height: '400px',
	          	url: '<%=basePath%>taskuser/goAdd.do'
	          	/*, hideFunc:function(){
	             	 modals.info("hide me");
	          	},
	          	showFunc:function(){
	              	modals.info("show me");
	          	} */
	      	});
			
		}
		
		//删除
		function del_task(Id,Uid){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>taskuser/delete.do?ID="+Id+"&UID="+Uid+"&tm="+new Date().getTime();
					$.get(url,function(data){
						if(data=="success"){
							location.href="<%=path%>/taskuser/list.do";	
						}else{
							modals.info("删除失败，任务已启动不能删除");
						}
						
					});
				}
			});
		}
		
		//修改
		function edit_task(Id){
			
			var winId = "userWin";
			//alert(Id);
		  	modals.openWin({
	          	winId: winId,
	          	title: '修改任务',
	          	width: '900px',
	          	height: '400px',
	          	url: '<%=basePath%>taskuser/goEdit.do?ID='+Id
	          	/*, hideFunc:function(){
	             	 modals.info("hide me");
	          	},
	          	showFunc:function(){
	              	modals.info("show me");
	          	} */
	      	});
			
			
		}
		
		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>taskuser/excel.do';
		}
		
		
		$(function () {
			
			$('.seat-middle').find(".row:first").hide();
			
			$(".xtyh-middle-r").find(".row:last").addClass("row-zg");
			$(".xtyh-middle-r").find(".row:eq(1)").addClass("row-two");
		});
		
	</script>


</body>
</html>