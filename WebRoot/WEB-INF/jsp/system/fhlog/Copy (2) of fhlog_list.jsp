<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" 	+ request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<%@ include file="../../system/include/incJs_mx.jsp"%>
	<style>
	 .pagination input{
	 	height:33px;
	 }
	 .pagination select{
	 	height:32px;
	 }
	 div.dataTables_wrapper div.dataTables_filter label{
	 	padding:15px 0;
	 }
	</style>
</head>
<body class="no-skin">


<!-- Content Wrapper. Contains page content -->
  <div class="wrapper" >
    <!-- Content Header (Page header) -->
    

    <!-- Main content -->
    <section class="">
      <div class="row">
        <div class="">
          <div class="main-padding-t-15">
            
            <!-- /.box-header -->
            <div class="box-body cbk-middle-one-lb ">
            <div class="col-lg-12">
              <table id="example2" class="table table-bordered table-hover">
                <thead>
                <tr>
                  	
					<th class="center" style="width:50px;">序号</th>
					<th class="center">用户名</th>
					<th class="center">事件</th>
					<th class="center">操作时间</th>
					<th class="center" style="width:50px;">操作</th>
                </tr>
                </thead>
                <tbody>
                
                <c:choose>
						<c:when test="${not empty varList}">
							<c:forEach items="${varList}" var="var" varStatus="vs">
								<tr>
									
									<td class='center' style="width: 30px;">${vs.index+1}</td>
									<td class='center' style="width: 200px;">${var.name}</td>
									<td class='center'>${var.cont}</td>
									<td class='center' style="width: 150px;">${var.czdate}</td>
									<td class="center" style="width: 50px;">
									
									</td>
								</tr>
							
							</c:forEach>
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
  <!-- /.content-wrapper -->

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
			$("#Form").submit();
		}
		$(function() {
		
		
		});
		
		
	</script>
<script>
  $(function () {
  
    $('#example2').DataTable({
    	'paging'      : true,
	      'lengthChange': false,
	      'searching'   : false, //搜索框
	      'ordering'    : true, //排序总开关
	      'info'        : true,
	      'autoWidth'   : true, //自动宽度
	     /*  'pageLength':18, */  //默认显示多少条
	     /*  'aLengthMenu':[10,18,30,50,100,300], */   //可修改条数
	      bLengthChange:true,//每页多少条框体
	      dom: '<t><lfip>',
			infoCallback: function(settings,start,end,max,total,pre) {
		        var api = this.api();
		        var pageInfo = api.page.info();
		        return "共"+pageInfo.pages +"页,当前显示"+ start + "到" + end + "条记录" + ",共有"+ total + "条记录";
		    }
      
    })
  })
  function home(){
	parent.document.getElementById("mainFrame").src="<%=basePath%>login_default.do";
}
</script>

</body>
</html>