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
<style>

ul li{
	list-style-type:none;
	min-height:45px;
	line-height:40px;
	height:auto;
}
.content{
padding-top:7px!important;
}
::-webkit-scrollbar{width:10px;background-color:#e1e5ea}
::-webkit-scrollbar-thumb{background-color:#c3cad4;border-radius:10px;border:2px solid #e1e5ea;}
::-webkit-scrollbar-thumb:hover{background-color:#aab1bc;}
::-webkit-scrollbar-thumb:active{border:0;border-radius:0;background-color:#aab1bc}
::-webkit-scrollbar-thumb:window-inactive{background-color:#4c97da}

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
#myModal{
	    padding-left: 0!important;
}
.table>tbody+tbody{
border-top: 0!important;
}
.table-bordered>thead>tr>th,.table-bordered>tbody>tr>td{
    border: 0!important;
}
.col-md-3{
	padding:0;
}
/* .list-group{
padding: 30px 12px;
} */
.content{
	height:auto!important;
}
.col-md-9{
	    padding-right: 0!important;
}
.list-group li {
    line-height: 40px!important;
    padding-top: 15px!important;
    border-bottom: #e6e6e6 solid 1px!important;

    padding-left: 10px!important;
    padding-right: 10px!important;
    font-size: 18px!important;
}
.list-group li b{
	 
    min-width: 150px;
      font-weight: normal!important;
        
}
.list-group li a{
    color: #5eaee3;
}
.box{
	border-top:0;
}
/* #userstr{
	    background: #5eaee3;
} */
.box-body{
	padding:0!important;
}
.profile-username{
	color: #fff;
	font-size: 24px;

}
.btn b{
color: #fff;
}
.profile-user-img{
	border:0!important;
}
.list-group{
	padding: 0px 15px;
	    height: 350px;
    overflow-y: auto;
}
ul li{
	    padding: 0 40px;
}
.infomation-r-nr-one-bz {
    padding: 4px 10px;
    background: #e6e6e6;
    border-radius: 5px;
    line-height: 30px;
    width: 100%;
    border: 0;
    outline: none;
    font-size: 18px;
        color: #333;
            margin-top: 40px;
}
.list-group li b{
    padding-right: 10px;
        font-size: 18px;
    color: #4a4a4a;
}
.col-md-3>.box-primary{
	    height: auto!important;
}
/* .ex-pd{
font-size:18px;
} */
.infomation-r li{
	font-size:18px;
}
.modal-open .modal{
	    border-radius: 15px;
}
body{background:#ecf0f5!important;}
@media screen and (max-width:1700px) {
	.infomation-r-kcb{
		    line-height: 22px;
	}
	.infomation-r li{
		    font-size: 15px;
	}
	.profile-username{
		    font-size: 22px;
	}
	.list-group li b{
		    font-size: 15px;
	}
	.list-group li a{
		font-size: 15px;
	}
	.infomation-r-nr-one-bz{
	font-size: 15px;
	}

}
li{
	line-height:15px;
	height:20px;
}
@media screen and (max-width:1400px) {
	.list-group {

    height: 270px;
    overflow-y: auto;
}
}

</style>
</head>
<body class="no-skin">
	<div class="content-wrapper" style="width:100%;margin-left:0px;">
		
		<section class="content">
			<div class="row">
				<div class="col-md-6" style="width:100%">
					
							
				<!-- Main content -->
			    <section class="content">
			      <div class="row">
			      
			      	<div class="infomation-top">
			      		<div class="ex-gd">
			      		 	
		                 	<div class="progress xs ex-gd-n" >
		                   		<div class="progress-bar progress-bar-green" id="progress-bar"></div>
		                 	</div>
		                 	<div class="clearfix" style="float:left">
		                    	<span class="pull-left"></span>
		                    	<small class="pull-right" id="per_num"></small>
		                 	</div>
		                 </div>	
		                 
		                  <!-- <div class="rwqd"><a onclick="rwqd()"></a> </div>
                <div class="last"><a href="javascript:void(0)"></a> </div>
                <div style="width:10%;float:left;padding-left:10px;">	
			              	<button type="button" class="btn btn-block btn-info" style="width:100px;float:left;background:#FFA07A;border:#FFA07A" onclick="up()"><i class="fa fa-angle-double-left"></i>上一个</button>
		                 	<input type="hidden" id="TABLENAME" name="TABLENAME">
		                 	<input type="hidden" id="type" name="type">
		                 	<input type="hidden" id="CUSTOM_TEMPLATE_ID" name="CUSTOM_TEMPLATE_ID">
		                 	<input type="hidden" id="ZXMAN" name="ZXMAN">
		                 	<input type="hidden" id="ID" name="ID">
		                 	
		                 </div>	
		                 
                <div class="next"><a onclick="next()"></a> </div> -->
		                 
		                  <!-- <div style="width:15%;float:left;padding-left:10px;">
			      		 	<a onclick="rwqd()" class="btn btn-block btn-social btn-dropbox" style="width:120px;float:left">
			                	<i class="fa fa-tasks"></i>任务清单
			              	</a>
			             </div>  -->
			             
			             <div class="rwqd"><a onclick="rwqd()">任务清单</a> </div>
			             
			             
                		 
			             	
			            <div class="modal" id="myModal" style="background:#fff;width:100%;height:550px;margin-top:10px">
  								
						    <div class="modal-header">
								<h4 class="modal-title" id="myModalLabel" style="float: left;">任务清单</h4>
								<div  class="new-tb" style="float: right;"   data-dismiss="modal" title="关闭"></div>
							</div>
					      	<div class="modal-body">
					      	<div class="xtyh-middle-r" >
								<table id="customtable" class="table table-bordered table-hover">
							
								</table>	
							</div>
			             	</div>
					 <!--      <div class="modal-footer">
						      <a href="#" class="btn btn-primary" data-dismiss="modal">关闭</a>
						  	</div> -->
						  	<div class="new-bc">
								<a class="new-qxbut"  data-btn-type="cancel" data-dismiss="modal">取消</a>
							</div>
						</div> 
					
			             <div >	
			              	<!-- <button type="button" class="btn btn-block btn-info" style="width:100px;float:left;background:#FFA07A;border:#FFA07A" ><i class="fa fa-angle-double-left"></i>上一个</button> -->
		                 	<div class="last" onclick="up()"><a >上一页</a> </div>
		                 	<input type="hidden" id="TABLENAME" name="TABLENAME">
		                 	<input type="hidden" id="type" name="type">
		                 	<input type="hidden" id="CUSTOM_TEMPLATE_ID" name="CUSTOM_TEMPLATE_ID">
		                 	<input type="hidden" id="NAIRE_TEMPLATE_ID" name="NAIRE_TEMPLATE_ID">
		                 	<input type="hidden" id="ZXMAN" name="ZXMAN">
		                 	<input type="hidden" id="ID" name="ID"> 
		                 	
		                 </div>	
		                 <div class="next"  onclick="next()"><a>下一页</a> </div>
		                <!--  <div style="width:10%;float:left;padding-left:10px;">	
			              	<button type="button" class="btn btn-block btn-info" style="width:100px;float:left;background:#FFA07A;border:#FFA07A" onclick="next()">下一个<i class="fa fa-angle-double-right"></i></button>
		                 </div>	  -->
			      	</div>
			        
			        
			        <div class="col-md-3">
			          
			          	<!-- Profile Image -->
			          <div class="box box-primary" style="min-height:453px;height:453px;overflow: auto" >
			            <div class="box-body box-profile" style="" id="userstr">
			    
			            </div>
			            
			            <!-- /.box-body -->
			          </div>
			          <!-- /.box -->
			         
			        </div>
			        <!-- /.col -->
			        <div class="col-md-9" >
			          <div class="box box-primary">
			            <div class="box-header with-border">
			             
			            </div>
			            <!-- /.box-header -->
			            <div class="box-body no-padding" id="tmstr" style="padding-bottom:20px!important;width:100%;">
			            	
			            </div>
          
			            <div id="nexttmstr" style="min-height:150px;width:100%;">
			            	
			            </div>

			             
			          </div>
			          <!-- /. box -->
			        </div>
			        <!-- /.col -->
			      </div>
				</section>
			</div>
			</div>	
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
		function tosearch(){
			//top.jzts();
			$("#Form").submit();
		}
		function checkall(obj){
			//alert(obj);
			if($(obj).is(':checked')){
				$('input[name="customcheck"]').each(function(){
					$(this).prop("checked",true);
				});
			}else{
				$('input[name="customcheck"]').each(function(){
					$(this).prop("checked",false);
				});
			}
		}
		
		$(function() {
			
			/* 
			$('#example2').DataTable({
		      'paging'      : true,
		      'lengthChange': false,
		      'searching'   : true,
		      'ordering'    : true,
		      'info'        : true,
		      'autoWidth'   : true
		    }); 
		    $('#customtable').DataTable({
		      'paging'      : true,
		      'lengthChange': false,
		      'searching'   : true,
		      'ordering'    : true,
		      'info'        : true,
		      'autoWidth'   : true
		    });*/
		    qidong();
		});
		
		 

     	function changeTab(action) {
     		//alert(action);
     		var tab_1_li = document.getElementById('tab_1_li');
     		var tab_2_li = document.getElementById('tab_2_li');
     		var tab_1 = document.getElementById('tab_1');
     		var tab_2 = document.getElementById('tab_2');
     		if(action==2){
     			tab_1_li.className='';
     			tab_1.className='tab-pane';
     			tab_2_li.className='active';
     			tab_2.className='tab-pane active';
     		}
     		if(action==1){
     			tab_2_li.className='';
     			tab_2.className='tab-pane';
     			tab_1_li.className='active';
     			tab_1.className='tab-pane active';
     		}
     		//alert(action);
     	}
		
		
		function add(){
			var winId = "userWin";
		  	modals.openWin({
	          	winId: winId,
	          	title: '新增',
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
		
		function choice_zx(){
			$("#myModal").modal("toggle");
			
		}
		function up(){
			var type=$("#type").val();
			var ID=$("#ID").val();
			var CUSTOM_TEMPLATE_ID=$("#CUSTOM_TEMPLATE_ID").val();
			var NAIRE_TEMPLATE_ID=$("#NAIRE_TEMPLATE_ID").val();
			var ZXMAN=$("#ZXMAN").val();
			var ROWNO=$("#cusnum").val();
			var TABLENAME=$("#TABLENAME").val();
			var WCJD="";
			if(ROWNO==1){
				modals.info("已经是第一个");
			}else{
				var url = "<%=basePath%>exetask/getTask.do?ROWNO="+(Number(ROWNO)-1)+"&TABLENAME="+TABLENAME+"&CUSTOM_TEMPLATE_ID="+CUSTOM_TEMPLATE_ID+"&NAIRE_TEMPLATE_ID="+NAIRE_TEMPLATE_ID+"&ID="+ID+"&type="+type+"&ZXMAN="+ZXMAN+"&tm="+new Date().getTime();
				$.get(url,function(data){
					//alert(data);
					var obj= JSON.parse(data);
					
					$("#userstr").html("");
					$("#userstr").append(obj.userstr);
					//alert("d");
					if(obj.userstr!=""){
						//alert("d");
						getTmByNaire(type,ID,CUSTOM_TEMPLATE_ID,NAIRE_TEMPLATE_ID,TABLENAME,WCJD,ZXMAN);
					}
					
				});
			}
			
		}
		
		
		function next(){
			var type=$("#type").val();
			var ID=$("#ID").val();
			var CUSTOM_TEMPLATE_ID=$("#CUSTOM_TEMPLATE_ID").val();
			var NAIRE_TEMPLATE_ID=$("#NAIRE_TEMPLATE_ID").val();
			var ZXMAN=$("#ZXMAN").val();
			var ROWNO=$("#cusnum").val();
			var TABLENAME=$("#TABLENAME").val();
			var last=$("#last").val();
			var WCJD="";
			if(last=="1"){
				modals.info("已经是最后一个任务！");
			}else if(last=="2"){
				modals.info("请您先完成当前任务,或检查任务清单中是否全部完成任务！");
			}else{
				var url = "<%=basePath%>exetask/getTask.do?ROWNO="+(Number(ROWNO)+1)+"&TABLENAME="+TABLENAME+"&CUSTOM_TEMPLATE_ID="+CUSTOM_TEMPLATE_ID+"&NAIRE_TEMPLATE_ID="+NAIRE_TEMPLATE_ID+"&ID="+ID+"&type="+type+"&ZXMAN="+ZXMAN+"&tm="+new Date().getTime();
				$.get(url,function(data){
					//alert(data);
					var obj= JSON.parse(data);
					
					$("#userstr").html("");
					$("#userstr").append(obj.userstr);
					//alert("d");
					if(obj.userstr!=""){
						//alert("d");
						getTmByNaire(type,ID,CUSTOM_TEMPLATE_ID,NAIRE_TEMPLATE_ID,TABLENAME,WCJD,ZXMAN);
					}
					
				});
			}
			
		}
		
		
		function zxrw(ROWNO,CUS_ID){
			//alert(ROWNO);
			var type=$("#type").val();
			var ID=$("#ID").val();
			var CUSTOM_TEMPLATE_ID=$("#CUSTOM_TEMPLATE_ID").val();
			var NAIRE_TEMPLATE_ID=$("#NAIRE_TEMPLATE_ID").val();
			var ZXMAN=$("#ZXMAN").val();
			//var ROWNO=$("#cusnum").val();
			var TABLENAME=$("#TABLENAME").val();
			var WCJD="";
			
			var url = "<%=basePath%>exetask/getTask.do?ROWNO="+Number(ROWNO)+"&TABLENAME="+TABLENAME+"&CUSTOM_TEMPLATE_ID="+CUSTOM_TEMPLATE_ID+"&NAIRE_TEMPLATE_ID="+NAIRE_TEMPLATE_ID+"&ID="+ID+"&CUS_ID="+CUS_ID+"&type="+type+"&ZXMAN="+ZXMAN+"&tm="+new Date().getTime();
			$.get(url,function(data){
				$("#myModal").modal("hide");
				//alert(data);
				var obj= JSON.parse(data);
				
				$("#userstr").html("");
				$("#userstr").append(obj.userstr);
				//alert("d");
				if(obj.userstr!=""){
					//alert("d");
					getTmByNaire(type,ID,CUSTOM_TEMPLATE_ID,NAIRE_TEMPLATE_ID,TABLENAME,WCJD,ZXMAN);
				}
				
			});
			
		}
		
		function changeHFWJ(){
			var HFWJ=$("#HFWJ").val();
			if(HFWJ=="2"){
				document.getElementById("failid").style.display="block";
			}else{
				document.getElementById("failid").style.display="none";
			}
		}
		
		function rwqd(){
			//alert("dd");
			
			var TABLENAME=$("#TABLENAME").val();
			var type=$("#type").val();
			var ID=$("#ID").val();
			var CUSTOM_TEMPLATE_ID=$("#CUSTOM_TEMPLATE_ID").val();
			var NAIRE_TEMPLATE_ID=$("#NAIRE_TEMPLATE_ID").val();
			var ZXMAN=$("#ZXMAN").val();
			
			var url = "<%=basePath%>exetask/getCustomtable.do?TABLENAME="+TABLENAME+"&CUSTOM_TEMPLATE_ID="+CUSTOM_TEMPLATE_ID+"&NAIRE_TEMPLATE_ID="+NAIRE_TEMPLATE_ID+"&ID="+ID+"&type="+type+"&ZXMAN="+ZXMAN+"&tm="+new Date().getTime();
			$.get(url,function(data){
				//alert(data);
				var obj= JSON.parse(data);
				$("#customtable").html("");
				$("#customtable").append(obj.customString);
				$("#myModal").modal("toggle");
				settable();
				
			});
			//document.getElementById("rwqd").style.display="block";
		}
		function settable(){
			/* $('#customtable').DataTable({
			      'paging'      : true,
			      'lengthChange': false,
			      'searching'   : true,
			      'ordering'    : true,
			      'info'        : true,
			      'autoWidth'   : true
			    }); */
		}
		function editCus(CUSTOM_TEMPLATE_ID,ID,TABLENAME){
			var winId = "userWin";
			//alert(Id);
			//alert(TABLENAME);
		  	modals.openWin({
	          	winId: winId,
	          	title: '客户信息',
	          	width: '900px',
	          	height: '400px',
	          	url: "<%=basePath%>taskcustom/goSearch.do?action=edit&CUSTOM_TEMPLATE_ID="+CUSTOM_TEMPLATE_ID+"&TABLENAME="+TABLENAME+"&ID="+ID
	          	/*, hideFunc:function(){
	             	 modals.info("hide me");
	          	},
	          	showFunc:function(){
	              	modals.info("show me");
	          	} */
	      	});
		}
		
		function qidong(){
			//删除
			
				//alert(NAIRE_TEMPLATE_ID);
			//changeTab('2');
			var  type=1;
			var ID='${pd.ID}';
			var CUSTOM_TEMPLATE_ID='${pd.CUSTOM_TEMPLATE_ID}';
			var NAIRE_TEMPLATE_ID='${pd.NAIRE_TEMPLATE_ID}';
			var TABLENAME='${pd.TABLENAME}';
			var FPTYPE='${pd.FPTYPE}';
			var WCJD='${pd.WCJD}';
			var ZXMAN='${pd.ZXMAN}';
			$("#per_num").html(WCJD+"%");
			$("#TABLENAME").val(TABLENAME);
			$("#type").val(type);
			$("#ID").val(ID);
			$("#CUSTOM_TEMPLATE_ID").val(CUSTOM_TEMPLATE_ID);
			$("#NAIRE_TEMPLATE_ID").val(NAIRE_TEMPLATE_ID);
			$("#ZXMAN").val(ZXMAN);
			
			document.getElementById("progress-bar").style.width=WCJD+"%";
			var url = "<%=basePath%>exetask/getTask.do?TABLENAME="+TABLENAME+"&CUSTOM_TEMPLATE_ID="+CUSTOM_TEMPLATE_ID+"&NAIRE_TEMPLATE_ID="+NAIRE_TEMPLATE_ID+"&ID="+ID+"&type="+type+"&ZXMAN="+ZXMAN+"&FPTYPE="+FPTYPE+"&tm="+new Date().getTime();
			//alert(url);
			$.get(url,function(data){
				//alert(data);
				var obj= JSON.parse(data);
				
				$("#userstr").html("");
				$("#userstr").append(obj.userstr);
				$("#customtable").html("");
				$("#customtable").append(obj.customString);
				//alert("d");
				if(obj.userstr!=""){
					//alert("d");
					getTmByNaire(type,ID,CUSTOM_TEMPLATE_ID,NAIRE_TEMPLATE_ID,TABLENAME,WCJD,ZXMAN);
				}
				
			});
		}
		
		
		
		
		function hfresult(){
			var TABLENAME=$("#TABLENAME").val();
			var HFWJ=$("#HFWJ").val();
			var ZXMAN=$("#ZXMAN").val();
			var HFRESULT=$("#HFRESULT").val();
			var HFREMARK=$("#HFREMARK").val();
			
			var HFWJ=$("#HFWJ").val();
			if(HFWJ=="2"){
				if(HFRESULT==""){
					modals.info("请选择失败原因");
					return false;
				}
			}else{
				HFRESULT="";
			}
			
			var CUS_ID=$("#CUS_ID").val();
			if(CUS_ID==""){
				modals.info("无客户信息");
				return false;
			}
			var url = "<%=basePath%>taskcustom/editHF.do?ZXMAN="+ZXMAN+"&ID="+CUS_ID+"&TABLENAME="+TABLENAME+"&HFWJ="+HFWJ+"&HFRESULT="+HFRESULT+"&HFREMARK="+encodeURI(encodeURI(HFREMARK))+"&tm="+new Date().getTime();
			$.get(url,function(data){
				//alert(data);
				//$("#per_num").html("");
				///document.getElementById("progress-bar").style.width="0%";
				//$("#per_num").html("50%");
				//document.getElementById("progress-bar").style.width="50%";
				//alert("d");
				if (data.indexOf("success")>=0) {
					var str=data.split(":");
					modals.info("回访结果保存成功！");
					$("#per_num").html(str[1]+"%");
					document.getElementById("progress-bar").style.width=str[1]+"%";
					$("#last").val("0");
                	return false;
                }else{
                	modals.info("保存失败！");
                	return false;
                }
			});
			
		}
		function  save_answer(NAIRE_TEMPLATE_ID,ROWNO,type,ID,CUSTOM_TEMPLATE_ID,TABLENAME,ZXMAN,NAIRE_ID,CODE,TYPENAME){
			
			var ANSWER="";
			if(TYPENAME=="多选题"){
				 $('input[name="ANSWER"]:checked').each(function(){ 
					 if(ANSWER!=""){ANSWER=ANSWER+",";}
					 ANSWER=ANSWER+$(this).val();
				     //chk_value.push($(this).val()); 
				}); 
			}else{
				ANSWER=$('input:radio[name="ANSWER"]:checked').val();
			}
			//alert(ANSWER);
			var CUS_ID=$("#CUS_ID").val();
			if(CUS_ID==""){
				modals.info("无客户信息");
				return false;
			}
			if(ANSWER==""){
				modals.info("请选择答案");
				return false;
			}
			var url = "<%=basePath%>exetask/saveAnswer.do?CUS_ID="+CUS_ID+"&TABLENAME="+TABLENAME+"&CUSTOM_TEMPLATE_ID="+CUSTOM_TEMPLATE_ID+"&NAIRE_TEMPLATE_ID="+NAIRE_TEMPLATE_ID+"&ID="+ID+"&type="+type+"&ZXMAN="+ZXMAN+"&NAIRE_ID="+NAIRE_ID+"&NUM="+CODE+"&ANSWER="+ANSWER+"&tm="+new Date().getTime();
			$.get(url,function(data){
				var url = "<%=basePath%>exetask/getTmByNaire.do?ROWNO="+ROWNO+"&CUS_ID="+CUS_ID+"&TABLENAME="+TABLENAME+"&CUSTOM_TEMPLATE_ID="+CUSTOM_TEMPLATE_ID+"&NAIRE_TEMPLATE_ID="+NAIRE_TEMPLATE_ID+"&ID="+ID+"&type="+type+"&ZXMAN="+ZXMAN+"&NAIRE_ID="+NAIRE_ID+"&NUM="+CODE+"&ANSWER="+ANSWER+"&tm="+new Date().getTime();
				$.get(url,function(data){
					//alert(data);
					var obj= JSON.parse(data);
					
					var isjs=obj.isjs;
					$("#tmstr").html("");
					$("#nexttmstr").html("");
					$("#tmstr").append(obj.tmstr);
					
					
					$("#nexttmstr").html("");
					$("#nexttmstr").append(obj.nexttmstr);
					//alert(isjs);
					if(isjs=='1'){
						//alert(obj.custom);
						$("#HFRESULT").val(obj.HFRESULT);
						$("#HFWJ").val(obj.HFWJ);
						//document.getElementById("jsstr").style.display="block";
					}
				});
			});	
		}
		
		
		function  nextNaire(NAIRE_TEMPLATE_ID,ROWNO,type,ID,CUSTOM_TEMPLATE_ID,TABLENAME,ZXMAN,NAIRE_ID,CODE,TYPENAME){
			
			var ANSWER="";
			if(TYPENAME=="多选题"){
				 $('input[name="ANSWER"]:checked').each(function(){ 
					 if(ANSWER!=""){ANSWER=ANSWER+",";}
					 ANSWER=ANSWER+$(this).val();
				     //chk_value.push($(this).val()); 
				}); 
			}else{
				ANSWER=$('input:radio[name="ANSWER"]:checked').val();
			}
			//alert(ANSWER);
			var CUS_ID=$("#CUS_ID").val();
			if(CUS_ID==""){
				modals.info("无客户信息");
				return false;
			}
			if(ANSWER==""){
				modals.info("请选择答案");
				return false;
			}
			
			var url = "<%=basePath%>exetask/getTmByNaire.do?ROWNO="+ROWNO+"&CUS_ID="+CUS_ID+"&TABLENAME="+TABLENAME+"&CUSTOM_TEMPLATE_ID="+CUSTOM_TEMPLATE_ID+"&NAIRE_TEMPLATE_ID="+NAIRE_TEMPLATE_ID+"&ID="+ID+"&type="+type+"&ZXMAN="+ZXMAN+"&NAIRE_ID="+NAIRE_ID+"&NUM="+CODE+"&ANSWER="+ANSWER+"&tm="+new Date().getTime();
			$.get(url,function(data){
				//alert(data);
				var obj= JSON.parse(data);
				var isjs=obj.isjs;
				$("#nexttmstr").html("");
				$("#nexttmstr").append(obj.nexttmstr);
			});
		}
		
		
		
		function  end_answer(NAIRE_TEMPLATE_ID,ROWNO,type,ID,CUSTOM_TEMPLATE_ID,TABLENAME,ZXMAN,NAIRE_ID,CODE,TYPENAME){
			var CUS_ID=$("#CUS_ID").val();
			if(CUS_ID==""){
				modals.info("无客户信息");
				return false;
			}		
			var url = "<%=basePath%>exetask/getTmByNaire.do?ROWNO="+ROWNO+"&CUS_ID="+CUS_ID+"&TABLENAME="+TABLENAME+"&CUSTOM_TEMPLATE_ID="+CUSTOM_TEMPLATE_ID+"&NAIRE_TEMPLATE_ID="+NAIRE_TEMPLATE_ID+"&ID="+ID+"&type="+type+"&ZXMAN="+ZXMAN+"&NAIRE_ID="+NAIRE_ID+"&NUM="+CODE+"&tm="+new Date().getTime();
			$.get(url,function(data){
				//alert(data);
				var obj= JSON.parse(data);
				
				var isjs=obj.isjs;
				$("#tmstr").html("");
				$("#tmstr").append(obj.tmstr);
				//alert(isjs);
				if(isjs=='1'){
					//alert(obj.custom);
					$("#HFRESULT").val(obj.HFRESULT);
					$("#HFWJ").val(obj.HFWJ);
					//document.getElementById("jsstr").style.display="block";
				}
			});
		}
		
		function getTmByNaire(type,ID,CUSTOM_TEMPLATE_ID,NAIRE_TEMPLATE_ID,TABLENAME,WCJD,ZXMAN){
			//删除
				//alert(CUS_ID);
		
			var CUS_ID=$("#CUS_ID").val();
			if(CUS_ID==""){
				modals.info("无客户信息");
				return false;
			}
			var url = "<%=basePath%>exetask/getTmByNaire.do?CUS_ID="+CUS_ID+"&TABLENAME="+TABLENAME+"&CUSTOM_TEMPLATE_ID="+CUSTOM_TEMPLATE_ID+"&NAIRE_TEMPLATE_ID="+NAIRE_TEMPLATE_ID+"&ID="+ID+"&type="+type+"&ZXMAN="+ZXMAN+"&tm="+new Date().getTime();
			$.get(url,function(data){
				//alert(data);
				var obj= JSON.parse(data);
				var isjs=obj.isjs;
				$("#tmstr").html("");
				$("#tmstr").append(obj.tmstr);
				//alert(isjs);
				if(isjs=='1'){
					$("#HFRESULT").val(obj.custom.HFRESULT);
					$("#HFWJ").val(obj.custom.HFWJ);
					
				}
			});
		}
		
		
		//删除
		function getCustom(TEMPLATE_ID,TABLENAME,STATE){
			
		}
		
		function choice(str){
			//$("input[name='customcheck']").attr("checked", false);
			//$("[name=customcheck]:checkbox").attr("checked",false);
			
			var arr=str.split(",");
			$("[name=customcheck]:checkbox").each(function(){
				//$(this).attr("checked",true);
				//alert($(this).val());
				boo=false;
				for(var i=0;i<arr.length;i++){
					if($(this).val()==arr[i]){
						boo=true;
					}
				}
				if(boo){
					//alert($(this).val());
					$("#"+$(this).val()+"").prop("checked", "checked");
					//alert($(this).val());
				}else{
					$("#"+$(this).val()+"").prop("checked", false);
				}
			});
			
		}
		//修改
		function edit(Id){
			
			var winId = "userWin";
			//alert(Id);
		  	modals.openWin({
	          	winId: winId,
	          	title: '新增',
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
								url: '<%=basePath%>taskuser/deleteAll.do?tm='+new Date().getTime(),
						    	data: {DATA_IDS:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
										 location.href="<%=path%>/taskuser/list.do";
									 });
								}
							});
						}
					}
				}
			});
		};
		
		//导出excel
		function toExcel(CUSTOM_TEMPLATE_ID){
			window.location.href='<%=basePath%>taskcustom/excel.do?CUSTOM_TEMPLATE_ID='+ CUSTOM_TEMPLATE_ID;
		}
		
		
		
	</script>


</body>
</html>