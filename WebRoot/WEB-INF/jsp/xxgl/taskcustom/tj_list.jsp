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
<div style="width:100%;margin-left:0px;">
    
<section class="content">
      <div class="row">
        <div class="col-xs-12">
          <div class="box">
          
            <!-- /.box-header -->
            <div class="box-body">
              <table id="example_tj" class="table table-bordered table-hover">
                	<thead>
						<tr>
							<th class="center" style="width:30px;">序号</th>
							<th class="center">坐席人姓名</th>
							<th class="center">总任务数</th>
							<th class="center">已回访</th>
							<th class="center">回访成功</th>
							<th class="center">下次回访</th>
							<th class="center">回访失败</th>
							<th class="center">未回访任务</th>
							<th class="center">进度</th>
							
						</tr>
					</thead>
											
					<tbody>
					<!-- 开始循环 -->	
					<c:choose>
						<c:when test="${not empty varList}">
							<c:if test="${QX.cha == 1 }">
							<c:forEach items="${varList}" var="var" varStatus="vs">
								<tr>
									<td class='center' style="width: 35px;">${vs.index+1}</td>
									<td class='center'>${var.ZXXM}</td>
									<td class='center'>${var.COUNTER}</td>
									
									
									<!-- <td class='center'>${var.TASKMAN}</td>-->
									<td class='center'>${var.YHFRW}</td>
									
									<td class='center'>${var.HFWC}</td>
									<td class='center'>${var.XCHFRW}</td>
									<td class='center'>${var.HFSB}</td>
									<td class='center'>${var.WWC}</td>
									<td class='center'>
										<div class="clearfix">
					                    	<span class="pull-left"></span>
					                    	<small class="pull-right">${var.WCJD}%</small>
					                  	</div>
					                  	<div class="progress xs">
					                    	<div class="progress-bar progress-bar-green" style="width: ${var.WCJD}%;"></div>
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
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
		//$(top.hangge());//关闭加载状态
		//检索
		
		$(function() {
			$('#example_tj').DataTable({
		      'paging'      : true,
		      'lengthChange': false,
		      'searching'   : true,
		      'ordering'    : false,
		      'info'        : true,
		      'autoWidth'   : true
		    })
		});
		
		
	</script>


</body>
</html>