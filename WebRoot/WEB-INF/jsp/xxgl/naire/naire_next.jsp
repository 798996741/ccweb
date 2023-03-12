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
	.table th{
		font-size:14px;
	}
	.table td{
		font-size:14px;
	}
	#example_next thead thead tr th{
		border-left: 0!important;
		border-right: 0!important;
	}
	#example_next tbody tr td{
		border-left: 0!important;
	}
	
	@media screen and (max-width:1400px) {
		.modal-dialog{margin: 20px auto!important;}
	}
</style>
</head>
<body class="no-skin">
<div  style="width:100%;margin-left:0px;">
<div class="page-content">
	<div class="modal-header">
		<h4 class="modal-title" id="myModalLabel" style="float: left;">	</h4>
		<div  class="new-tb" style="float: right;"   data-dismiss="modal" title="关闭"></div>
	</div>
	<form action="naire/${msg }.do" name="Form_next" id="Form_next" method="post">
	   <input type="hidden" name="NAIRE_ID" id="NAIRE_ID" value="${pd.ID}"/>
	   <input type="hidden" name="ID" id="ID" value=""/>
	   <div id="table_report" class="table table-striped table-bordered table-hover new-wkj">
		
			<div class="new-tk-body-one new-tx">
				<div class="new-tk-body-one-wk">
					<div class="one-wk-l">答案:</div>
					<div class="one-wk-r">
						<select id="ANSWER" name="ANSWER"  >
							<c:forEach items="${pdList}" var="var" varStatus="vs">
								<option value="${var.answer }" >${var.answer }</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<span>*</span>
			</div>
			<div class="new-tk-body-one new-tx">
				<div class="new-tk-body-one-wk">
					<div class="one-wk-l">下一题:</div>
					<div class="one-wk-r">
						<select id="NEXT_ID" name="NEXT_ID"  >
							<c:forEach items="${varOList}" var="var" varStatus="vs">
								<option value="${var.ID }" >${var.SUBJECT }(${var.CODE })</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<span>*</span>
			</div>
			
			<tr>
				<td style="text-align: center;" colspan="2">
					<div class="new-c-wk">
						<c:if test="${empty pd.action }">
							<div class="new-bc">
								<a  onclick="save_next();" id="savevalue">新增</a>
							</div>
							<!-- <a class="btn btn-mini btn-primary" onclick="save_next();" id="savevalue">新增</a> -->
						</c:if>
						<div class="new-bc">
							<a class="new-qxbut1" data-btn-type="cancel" data-dismiss="modal">取消</a>
						</div>
					</div>
					<div style="width:35%;text-align:left;float:left;padding-left:5px;">
						<c:if test="${empty pd.action }">
							<a class="btn btn-mini btn-danger" onclick="back_save();" id="backvalue" style="width:90px;display:none">返回新增</a>
						</c:if>
					</div>	
				</td>
			</tr>
		</div>
	</form>	
	<section class="new-tck-zk">
      <div class="row">
        <div class="col-xs-12">
          <div class="">
            <div class="box-header" >
              	<c:if test="${QX.add == 1 }">
					<a class="btn btn-mini btn-success" onclick="add();">新增</a>
				</c:if>
            </div>
            <!-- /.box-header -->
            <div class="xtyh-middle-r" >
              <table id="example_next" class="table table-bordered table-hover ">
               
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
		
		getNext();
		//新增
		function getNext(Id){
			//alert('${pd.ID}');
			var Id='${pd.ID}';
			
			var url = "<%=basePath%>naire/getNaireNext.do?action=${empty pd.action }&NAIRE_ID="+Id+"&tm="+new Date().getTime();
			$.get(url,function(data){
				//alert(data);
				var obj= JSON.parse(data);
				$("#example_next").html("");
				$("#example_next").append(obj.naireString);
				
			});
			
		}
		
		function back_save(){
			$("#ID").val("");
			document.getElementById("backvalue").style.display="none";
			$("#savevalue").html("新增");
		}
		
		//删除
		function del_next(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>naire/deleteNext.do?ID="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						getNext();
					});
				}
			});
		}
		
		function edit_next(ANSWER,ID,NEXT_ID){
			
			$("#ANSWER").val(ANSWER);
			$("#ID").val(ID);
			$("#NEXT_ID").val(NEXT_ID);
			var action='${empty pd.action }';
			if(action==""){
				document.getElementById("backvalue").style.display="block";
				$("#savevalue").html("修改");	
			}
			
		}
		
		function save_next(){
			if($("#ANSWER").val()==""){
				$("#ANSWER").tips({
					side:3,
		            msg:'请选择答案',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ANSWER").focus();
				return false;
			}
			
			
			//$("#zhongxin").hide();
			//$("#zhongxin2").show();
			$.ajax({
            //几个参数需要注意一下
                type: "POST",//方法类型
                dataType: "html",//预期服务器返回的数据类型
                url: "naire/saveNext.do" ,//url
                data: $('#Form_next').serialize(),
                success: function (result) {
                  // alert(result);//打印服务端返回的数据(调试用)
                	if (result.indexOf("success_")>=0) {
                		modals.info("保存成功");
                		getNext();
                  	}else{
                  		modals.info("修改失败,选择的答案已设置下一题");
                  	} 
                	//$("#zhongxin").hide();
        			//$("#zhongxin2").hide();
                },
                error : function() {
                    alert("异常！");
                }
            });
		}
	
		
		
	</script>


</body>
</html>