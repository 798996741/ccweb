<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.fh.util.Jurisdiction" %>
<%@ page import="com.fh.entity.system.User" %>
<%
	Jurisdiction jurisdiction=new Jurisdiction();
	User user =Jurisdiction.getLoginUser();
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!tstype html>
<html lang="en">
<head>
<base href="<%=basePath%>">

<!-- 日期框 -->
<link rel="stylesheet" href="static/ace/css/datepicker.css" />
<link rel="stylesheet"
	href="plugins/kindeditor/themes/default/default.css" />
<link rel="stylesheet"
	href="plugins/kindeditor/plugins/code/prettify.css" />
<!-- 日期框 -->

<style>
	 .btnbm{
		 width:50px;height:25px;line-height:25px;padding:0px;padding-left:5px;
	 }
	</style>

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

							<form action="workorder/${msg }.do" name="Form_add" id="Form_add"method="post" enctype="multipart/form-data" >
								<input type="hidden" name="id" id="id" value="${pd.id}" />
								<input type="hidden" name="doaction" id="doaction" />
								<input type="hidden" name="code" id="code" value="${pd.code}" />
								<input type="hidden" name="uid" id="uid" value="${pd.uid}" />
								<input type="hidden" name="type" id="type" value="${pd.type}" />
								<input type="hidden" name="cljd" id="cljd" value="${pd.cljd}" />
								<div id="zhongxin" style="padding-top: 0px;">
									<div id="table_report"
										class="table table-striped table-bordered table-hover new-wkj">

										<div class="new-tk-body-one">
											<div class="new-tk-body-one-wk">
												<div class="one-wk-l">投诉来源:</div>
												<div class="one-wk-r">
													<select id="tssource" name="tssource"  >
														<c:forEach items="${tssourceList}" var="var" varStatus="vs">
															<option value="${var.DICTIONARIES_ID }" <c:if test="${pd.tssource==var.DICTIONARIES_ID}">selected</c:if>>${var.NAME }</option>
														</c:forEach>
													</select>
												</div>
											</div>
											<span>*</span>
										</div>
										
										<div class="new-tk-body-one">
											<div class="new-tk-body-one-wk">
												<div class="one-wk-l">投诉日期:</div>
												<div class="one-wk-r">
													<input  name="tsdate" id="tsdate" class="span10 date-picker" value="${pd.tsdate}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly"  placeholder="投诉时间" title="投诉时间"/>
														
												</div>	
											</div>
											<span>*</span>
										</div>
										<div class="new-tk-body-one" style="width:100%">
											<div class="new-tk-body-one-wk" style="width:100%">
												<div class="one-wk-l">投诉内容:</div>
												<div class="one-wk-r" style="width:100%">
													<input type="text" name="tscont" id="tscont"
														value="${pd.tscont}"  style="width:90%"
														placeholder="这里输入投诉内容" title="投诉内容" />
												
												</div>
											</div>
											<span>*</span>
										</div>
										<div class="new-tk-body-one">
											<div class="new-tk-body-one-wk">
												<div class="one-wk-l">投诉类别（大项）:</div>
												<div class="one-wk-r">
													<select id="tstype" name="tstype"  >
														<option value="">请选择投诉类别（大项）</option>
														<c:forEach items="${tstypeList}" var="var" varStatus="vs">
															<option value="${var.DICTIONARIES_ID }" <c:if test="${pd.bigtstype==var.DICTIONARIES_ID}">selected</c:if>>${var.NAME }</option>
														</c:forEach>
													</select>
													
													<%-- <input type="hidden" name="tstype" id="tstype" value="${pd.tstype}" maxlength="30"  />
													<input type="text" name="tstype_name"
														onclick="showMenu();return false" id="tstype_name"
														value="${pd.tstypename}" maxlength="30" placeholder="选择" readonly />
													<div id="menuContent" class="menuContent"
														style="position: absolute;display:none">
														<ul id="treeDemo" class="ztree" style="margin-top:0; width:260px;"></ul>
													</div> --%>
												</div>
											</div>
											<span>*</span>
										</div>
										<div class="new-tk-body-one">
											<div class="new-tk-body-one-wk">
												<div class="one-wk-l">投诉类别（细项）:</div>
												<div class="one-wk-r">
													<input type="hidden" name="tstype" id="tstype" value="${pd.tstype}" maxlength="30"  />
													<input type="text" name="tstype_name"
														onclick="showMenu();return false" id="tstype_name"
														value="${pd.tstypename}" maxlength="30" placeholder="选择" readonly />
													<div id="menuContent" class="menuContent"
														style="position: absolute;display:none">
														<ul id="treeDemo" class="ztree" style="margin-top:0; width:260px;"></ul>
													</div>
												</div>
											</div>
											<span>*</span>
										</div>
										<div class="new-tk-body-one">
											<div class="new-tk-body-one-wk">
												<div class="one-wk-l">投诉部门:</div>
												<div class="one-wk-r">
													<input type="hidden" name="tsdept" id="tsdept" value="${pd.tsdept }">
													<input id="tsdeptSel" style="width:100%" name="" type="text" readonly  onclick="showTsdept(); return false;"/>	
													<div id="menuContent_dept" class="menuContent_dept"
														style="position: absolute;display:none;background:rgb(240,246,228);">
														<div class="new-bc" style=" height:35px;padding-top:5px;border-radius:5px;border-left:1px #000 solid;border-right:1px #000 solid;border-top:1px #000 solid ">
															<a  style=" width:50px;height:25px;line-height:25px;padding:0px;padding-left:10px;" class="btnbm" onclick="hideDept()" >确定</a>&nbsp;&nbsp;&nbsp;&nbsp;<a  style=" width:50px;height:25px;line-height:25px;padding:0px;padding-left:10px;" onclick="hideDept()" >取消</a>
														</div>
														<ul id="treeDemo_dept" class="ztree" style="margin-top:0; width:250px;"></ul>
														
													</div>
												</div>
												
											</div>
											<span>*</span>
										</div>
										<div class="new-tk-body-one" >
											<div class="new-tk-body-one-wk">
												<div class="one-wk-l">投诉分类:</div>
												<div class="one-wk-r">
													<select id="tsclassify" name="tsclassify"  >
														<c:forEach items="${tsclassifyList}" var="var" varStatus="vs">
															<option value="${var.DICTIONARIES_ID }" <c:if test="${pd.tsclassify==var.DICTIONARIES_ID}">selected</c:if>>${var.NAME }</option>
														</c:forEach>
													</select>
												</div>
											</div>
										</div>
										<div class="new-tk-body-one">
											<div class="new-tk-body-one-wk" >
												<div class="one-wk-l">投诉等级:</div>
												<div class="one-wk-r" >
													<select id="tslevel" name="tslevel"  >
														<c:forEach items="${tslevelList}" var="var" varStatus="vs">
															<option value="${var.DICTIONARIES_ID }" <c:if test="${pd.tslevel==var.DICTIONARIES_ID}">selected</c:if>>${var.NAME }</option>
														</c:forEach>
													</select>
												</div>
											</div>
											<span>*</span>
										</div>
										<div class="new-tk-body-one">
											<div class="new-tk-body-one-wk">
												<div class="one-wk-l">是否回访:</div>
												<div class="one-wk-r" >
													&nbsp;&nbsp;<input type="radio"  name="ishf" id="ishf" style="vertical-align: middle;width:15px;min-width:15px" value="1" <c:if test="${pd.ishf=='1'}">checked</c:if>>是
													<input type="radio"  name="ishf" id="ishf" style="vertical-align: middle;width:15px;min-width:15px" value="0" <c:if test="${pd.ishf=='0'}">checked</c:if>>否
													
												</div>
											</div>
											<span>*</span>
										</div>
										
										<div class="new-tk-body-one">
											<div class="new-tk-body-one-wk">
												<div class="one-wk-l">投诉人:</div>
												<div class="one-wk-r">
													<input name="tsman" id="tsman"
														value="${pd.tsman}" type="text" placeholder="投诉人"
														title="投诉人" />
												</div>
											</div>
											<span>*</span>
										</div>


										<div class="new-tk-body-one">
											<div class="new-tk-body-one-wk">
												<div class="one-wk-l">联系电话:</div>
												<div class="one-wk-r">
													<input type="text" name="tstel" id="tstel"
														value="${pd.tstel}" maxlength="50"
														placeholder="这里输入联系电话" title="联系电话" />
												</div>
											</div>
											<span>*</span>
										</div>

										
										<div class="new-tk-body-one">
											<div class="new-tk-body-one-wk">
												<div class="one-wk-l">证件号码:</div>
												<div class="one-wk-r">
													<input type="text" name="cardid" id="cardid"
														value="${pd.cardid}" 
														placeholder="这里输入证件号码" title="投证件号码" />
												</div>
											</div>
											<span>*</span>
										</div>
										<div class="new-tk-body-one">
											<div class="new-tk-body-one-wk">
												<div class="one-wk-l">乘机日期:</div>
												<div class="one-wk-r">
													<input class="span10 date-picker" name="cjdate" id="cjdate"  value="${pd.cjdate}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly"  placeholder="乘机时间" title="乘机时间"/>
														
												</div>	
											</div>
											<span>*</span>
										</div>
										<div class="new-tk-body-one">
											<div class="new-tk-body-one-wk" >
												<div class="one-wk-l">航班号/航程:</div>
												<div class="one-wk-r">
													<input type="text" name="hbh" id="hbh"
														value="${pd.hbh}" 
														placeholder="这里输入航班号/航程" title="航班号/航程" />
												
												</div>
											</div>
										</div>
										
										<div class="new-tk-body-one">
											<div class="new-tk-body-one-wk">
												<div class="one-wk-l">受理人:</div>
												<div class="one-wk-r" >
													&nbsp;&nbsp;<%=user.getNAME()%>
												</div>
											</div>
										</div>
										<div class="new-tk-body-one">
											<div class="new-tk-body-one-wk" >
												<div class="one-wk-l">处理时限:</div>
												<div class="one-wk-r">
													
													<select id="clsx" name="clsx"  >
														<option value="24H" <c:if test="${pd.clsx=='24H'}">selected</c:if>>24H</option>
														<option value="48H" <c:if test="${pd.clsx=='48H'}">selected</c:if>>48H</option>
														<option value="3D" <c:if test="${pd.clsx=='3D'}">selected</c:if>>3D</option>
														<option value="7D" <c:if test="${pd.clsx=='7D'}">selected</c:if>>7D</option>
														<option value="0" <c:if test="${pd.clsx=='0'}">selected</c:if>>其他</option>
													</select>
												</div>
											</div>
										</div>
										<div class="new-tk-body-one" style="width:100%;height:80px;">
											<div class="new-tk-body-one-wk" style="width:100%;height:80px;">
												<div class="one-wk-l">快速处理:</div>
												<div class="one-wk-r" style="width:100%;height:80px;">
													<textarea  style="height:80px;padding-left:10px;line-height:80px" name="endreason" id="endreason" placeholder="这里输入快速处理" title="快速处理" >${pd.endreason}</textarea>
												</div>
											</div>
										</div>
										
										
										<div class="new-tk-body-one"  style="width:100%">
											<div class="new-tk-body-one-wk" style="float:left">
												<div class="one-wk-l">附件上传:</div>
												<div class="one-wk-r" >
													<input type="file" name="file" multiple="multiple">
												</div>
											</div>
											<div style="padding-left:10px;width:38%;float:left">
												<ul id="fileid">
												</ul>
											</div>
										</div>
									
										<div class="new-bc">
											<a onclick="save('0');">结束工单</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<a onclick="save('1');">派发工单</a> 
											<a class="new-qxbut" data-btn-type="cancel" data-dismiss="modal">取消</a>
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
	<div id="outerdiv" style="position:fixed;top:0;left:0;background:rgba(0,0,0,0.7);z-index:2;width:100%;height:100%;display:none;">
		<div id="innerdiv" style="position:absolute;">
			<img id="max_img" style="border:5px solid #fff;" src="" />
		</div>
	</div>
	<!-- /.main-container -->
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<script type="text/javascript">
        $(document).ready(function() {
            $("#min_img").click(function(){
                var _this = $(this);//将当前的min_img元素作为_this传入函数
                imgShow("#outerdiv", "#innerdiv", "#max_img", _this);
            });
        });
        function imgShow(src){
        	outerdiv="#outerdiv"; innerdiv="#innerdiv";max_img="#max_img";
            //var src = _this.attr("src");//获取当前点击的min_img元素中的src属性
            $("#max_img").attr("src", src);//设置#max_img元素的src属性

            /*获取当前点击图片的真实大小，并显示弹出层及大图*/
            $("<img/>").attr("src", src).load(function(){
                var windowW = $(window).width();//获取当前窗口宽度
                var windowH = $(window).height();//获取当前窗口高度
                var realWidth = this.width;//获取图片真实宽度
                var realHeight = this.height;//获取图片真实高度
                var imgWidth, imgHeight;
                var scale = 0.9;//缩放尺寸，当图片真实宽度和高度大于窗口宽度和高度时进行缩放

                if(realHeight>windowH*scale) {//判断图片高度
                    imgHeight = windowH*scale;//如大于窗口高度，图片高度进行缩放
                    imgWidth = imgHeight/realHeight*realWidth;//等比例缩放宽度
                    if(imgWidth>windowW*scale) {//如宽度扔大于窗口宽度
                        imgWidth = windowW*scale;//再对宽度进行缩放
                    }
                } else if(realWidth>windowW*scale) {//如图片高度合适，判断图片宽度
                    imgWidth = windowW*scale;//如大于窗口宽度，图片宽度进行缩放
                    imgHeight = imgWidth/realWidth*realHeight;//等比例缩放高度
                } else {//如果图片真实高度和宽度都符合要求，高宽不变
                    imgWidth = realWidth;
                    imgHeight = realHeight;
                }
                $("#max_img").css("width",imgWidth);//以最终的宽度对图片缩放

                var w = (windowW-imgWidth)/2-100;//计算图片与窗口左边距
                var h = (windowH-imgHeight)/2+100;//计算图片与窗口上边距
                $(innerdiv).css({"top":h, "left":w});//设置#innerdiv的top和left属性
                $(outerdiv).fadeIn("fast");//淡入显示#outerdiv及.pimg
            });

            $(outerdiv).click(function(){//再次点击淡出消失弹出层
                $(this).fadeOut("fast");
            });
        }
    </script>
	<script type="text/javascript">
	$('.date-picker').datepicker({
		autoclose: true,
		todayHighlight: true,
		clearBtn: true
	});
	var day1 = new Date();
	var s1 = day1.toLocaleDateString().split('/').join('-');
	
	$("#tsdate").val(s1);
	
	var tree;
    var demoIframe;
    var setting = {
        view: {
            showIcon: true,//是否显示节点的图标
            showLine: true,//显示节点之间的连线。
            expandSpeed: "slow",//节点展开、折叠时的动画速度
            selectedMulti: false//不允许同时选中多个节点。
        },
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pId",
				rootPId : "",
				highlight: false
			}
		},
		check : {
			enable : false
		},
		callback : {
		
			onClick: onClick
		}
	};
	var zNodes = eval('${zTreeNodes}');
	//alert(zNodes);

	
	console.log("进入编辑器！！！！");
	
	var t = $("#treeDemo");
	t = $.fn.zTree.init(t, setting, zNodes);
	t.expandAll(true);
	function onClick(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo"), nodes = zTree.getSelectedNodes(), v = "";
		nodes.sort(function compare(a, b) {
			return a.id - b.id;
		});
		for (var i = 0, l = nodes.length; i < l; i++) {
			v += nodes[i].name + ",";
		}
		//alert(treeId);
		//alert(treeNode.id);
		$("#tstype").val(treeNode.id);
		if (v.length > 0)
			v = v.substring(0, v.length - 1);
		var cityObj = $("#tstype_name");
		cityObj.attr("value", v);
		$("#menuContent").hide();
	}
	
	
	
	function showMenu() {
		var cityObj = $("#tstype_id");
		var cityOffset = $("#tstype_id").offset();
		$("#menuContent").slideDown("fast");
	}
	
	var setting_dept = {
		check: {
			enable: true
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeClick: beforeClick_dept,
			//onClick: onClick_dept,
			onCheck: onClick_dept
		}
	};
	var zn_dept = '${zTreeNodes_dept}';
	var zNodes_dept = eval(zn_dept);
	//alert('${zTreeNodes_dept}');

	var t_dept = $("#treeDemo_dept");
	t_dept = $.fn.zTree.init(t_dept, setting_dept, zNodes_dept);
	t_dept.expandAll(true);
	
	 var pid= '${pd.tsdept}'; /**此处数据前后必须拼接;*/
	 //alert(pid);
	 var zTree = t_dept.getCheckedNodes(false);
     for (var i = 0; i < zTree.length; i++) {
        if (pid.indexOf(zTree[i].AREA_CODE) != -1) {
        	//alert(zTree[i]);
        	t_dept.expandNode(zTree[i], true); //展开选中的
        	t_dept.checkNode(zTree[i], true);					
        }
     }
     if(pid!=""){
    	 onClick_dept();
     }
	 function beforeClick_dept(treeId, treeNode) {
		var check = (treeNode && !treeNode.isParent);
		if (!check) ;
		return check;
	}
	
	function onClick_dept(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo_dept"),
		nodes = zTree.getCheckedNodes(true),
		v = "",vid="";
		for (var i=0, l=nodes.length; i<l; i++) {
			if(nodes[i].AREA_LEVEL=='3'){
				if(v!=""){
					v += ",";
				}
				v += nodes[i].name;
				if(vid!=""){
					vid += ",";
				}
				vid += nodes[i].AREA_CODE;	
			}
		}
		if (v.length > 0 ) v = v.substring(0, v.length-1);
		var cityObj = $("#tsdeptSel");
		cityObj.attr("value", v);
		$("#tsdept").val(vid);
		//hideDept();
	}

	
	function showTsdept() {
		var cityObj = $("#tsdept");
		var cityOffset = $("#tsdept").offset();
		$("#menuContent_dept").slideDown("fast");
		$("body").bind("mousedown", onBodyDown);
	}
	
	function hideMenu() {
		$("#menuContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	function hideDept() {
		$("#menuContent_dept").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	function onBodyDown(event) {
		//alert(event);
		if (!(event.target.id == "menuBtn" || event.target.id == "menuContent"|| event.target.id == "menuContent_dept" || $(event.target).parents("#menuContent").length>0)) {
			hideMenu();
			//hideDept();
		}
	}
	var uid='${pd.uid}';
	function getFile(){
		//alert(uid);
		if(uid!=""){
			$.ajax({
				//几个参数需要注意一下
				type : "POST",//方法类型
				dataType : "json",//预期服务器返回的数据类型
				url : "workorder/getFile.do?uid="+uid,//url
				success : function(result) {
					
					var str="";
					var path="<%=path%>";
					$.each(result.list, function(i, list){
						
						var wfile=list.file;
						if(wfile.indexOf(".png")>=0||wfile.indexOf(".PNG")>=0||wfile.indexOf(".gif")>=0||wfile.indexOf(".GIF")>=0||wfile.indexOf(".JPG")>=0||wfile.indexOf(".jpg")>=0||wfile.indexOf(".jpeg")>=0||wfile.indexOf(".JPEG")>=0){
							str=str+"<li><a onclick=\"imgShow('"+path+"/uploadFiles/uploadFile/"+list.file+"')\">"+list.name+"&nbsp;&nbsp;<span style=\"color:red\" onclick=\"delfile('"+list.id+"')\">删除文件</span></li>";	
						}else{
							str=str+"<li><a href=\""+path+"/uploadFiles/uploadFile/"+list.file+"\">"+list.name+"&nbsp;&nbsp;<span style=\"color:red\" onclick=\"delfile('"+list.id+"')\">删除文件</span></li>";
						}
						
					});
					$("#fileid").html('');
					//alert(str);
					$("#fileid").append(str);
				}
			 });
		}else{
			
		}
	}
	
	
	
	function delfile(id){
		bootbox.confirm("确定要删除文件吗?", function(result) {
			if(result) {
				var url = "<%=basePath%>workorder/deleteFile.do?id="+id+"&tm="+new Date().getTime();
				$.get(url,function(data){
					getFile();
				});
			}
		});
	}
	
	function save(doaction) {
		var str="";
		if(doaction=="0"){
			str="结束工单";
		}
		if(doaction=="1"){
			str="派发工单";
		}
		bootbox.confirm("确定要"+str+"吗?", function(result) {
			if(result) {
				$("#doaction").val(doaction);
				if ($("#tstype_id").val() == "") {
					$("#tstype_name").tips({
						side : 3,
						msg : '请选择投诉类型',
						bg : '#AE81FF',
						time : 2
					});
					$("#tstype_name").focus();
					return false;
				}
				
				
				if ($("#tsdate").val() == "") {
					$("#tsdate").tips({
						side : 3,
						msg : '请输入投诉时间',
						bg : '#AE81FF',
						time : 2
					});
					$("#tsdate").focus();
					return false;
				}else{
					var day2 = new Date();
					day2.setTime(day2.getTime());
					var s2 = day2.getFullYear()+"-" + (day2.getMonth()+1) + "-" + day2.getDate();
					if(s2  < $("#tsdate").val()){
						//alert("投诉日期不能大于当前日期");
						$("#tsdate").tips({
							side : 3,
							msg : '投诉日期不能大于当前日期',
							bg : '#AE81FF',
							time : 2
						});
						$("#tsdate").focus();
						return false;
					}
				}
				if ($("#tssource").val() == "") {
					$("#tssource").tips({
						side : 3,
						msg : '请选择投诉来源',
						bg : '#AE81FF',
						time : 2
					});
					$("#tssource").focus();
					return false;
				}
				
				if ($("#tsman").val() == "") {
					$("#tsman").tips({
						side : 3,
						msg : '请输入投诉人',
						bg : '#AE81FF',
						time : 2
					});
					$("#tsman").focus();
					return false;
				}
				
			
				if ($("#tstel").val() == "") {
					$("#tstel").tips({
						side : 3,
						msg : '请输入投诉电话',
						bg : '#AE81FF',
						time : 2
					});
					$("#tstel").focus();
					return false;
				}
				
				if ($("#cardid").val() == "") {
					$("#cardid").tips({
						side : 3,
						msg : '请输入证件号码',
						bg : '#AE81FF',
						time : 2
					});
					$("#cardid").focus();
					return false;
				}
				
				if ($("#cjdate").val() == "") {
					$("#cjdate").tips({
						side : 3,
						msg : '请输入乘机日期',
						bg : '#AE81FF',
						time : 2
					});
					$("#cjdate").focus();
					return false;
				}
				
				
				var tsdept=$("#tsdept").val();
		        if(tsdept==""){
					alert("请选择投诉的部门");
					return false;
				}else{
					 $("#tsdept").val(tsdept);
				}
			
				var msg = '${msg }';
				$('#Form_add').submit();
			}	
		});	
		
		
		<%-- $.ajax({
			//几个参数需要注意一下
			type : "POST",//方法类型
			dataType : "html",//预期服务器返回的数据类型
			url : "workorder/${msg }.do",//url
			data : $('#Form_add').serialize(),
			success : function(result) {
				// alert(result);//打印服务端返回的数据(调试用)
				if (result.indexOf("success_add") >= 0) {
					location.href = "<%=basePath%>workorder/list.do";
	             }else if (result.indexOf("error1")>=0){
                  	modals.info("标题已存在,请重新输入！");
                  	return false;
                  }
                 
              },
              error : function() {
                  alert("异常！");
              }
          }); --%>
	}
	getFile();
	
	</script>
</body>
</html>