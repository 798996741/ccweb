
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String userid=request.getParameter("userid");
	String uid=request.getParameter("uid");
	String proc_id=request.getParameter("proc_id");
%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8" />
    <meta name="App-Config" content="fullscreen=yes,useHistoryState=yes,transition=yes" />
    <meta content="yes" name="apple-mobile-web-app-capable" />
    <meta content="yes" name="apple-touch-fullscreen" />
    <meta content="telephone=no,email=no" name="format-detection" />
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no" />
    <script src="http://g.tbcdn.cn/mtb/lib-flexible/0.3.4/??flexible_css.js,flexible.js"></script>
    <script src="<%=path%>/static/weixin/js/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/weixin/css/mui.min.css" />
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/weixin/css/mui.picker.min.css" />
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/weixin/css/mui.poppicker.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/weixin/css/common.css" />

</head>

<script type="text/javascript">
	function loadWork(){
		var userid='${pd.userid}';
		var uid='${pd.uid}';
		var data="{\"api\":\"com.yulun.WorkorderWeb\",\"data\":{\"username\":\""+userid+"\",\"uid\":\""+uid+"\"},\"cmd\":\"WorkorderFilelist\"}";
			//alert(data);
		$.ajax({
	       	type: 'post',
	      	// url: "http://127.0.0.1:8080/ylxxxtnew/sys/sypzpreAction!getPz.xhtml?endpoint_id=1023&module=home&r="+Math.random(),
			url:"<%=path%>/api",
	        dataType: 'json',
	        data:{"data":data}, //参数值
	        cache: false,
	        success: function (obj) {
	           	var str="";
	            var data =eval(obj.data);
	            var work =eval(obj.work);
	            if(obj.success=="true"){
	            	 //alert(data.length);
					$("#cptemphasis").val(work.cptemphasis);
	              	$("#surveyprocess").val(work.surveyprocess);
	              	$("#isreconciliation").val(work.isreconciliation);
	              	$("#disposeending").val(work.disposeending);
	               /*  for(var o in data){
	                	//alert(o);

	                	str=str+"<tr>";
	                	if(data[o].replay=="1"){
		                	str=str+"<td ><input type=\"checkbox\" id=\"filecheck\" checked name=\"filecheck\" value=\""+data[o].id+"\"></td>";
	                	}else{
		                	str=str+"<td ><input type=\"checkbox\" id=\"filecheck\" name=\"filecheck\" value=\""+data[o].id+"\"></td>";
	                	}
	                	str=str+"<td><div class=\"cell\">"+data[o].filename+"</div></td>";
	                	str=str+"<td><div class=\"cell\"><span class=\"wx-button-blue\" onclick=\"downfile("+data[o].id+")\">下载</span>&nbsp;&nbsp;<span class=\"wx-button-red\" onclick=\"delfile("+data[o].id+")\">删除</span> </div></td>";
	                	str=str+"</tr>";
	                } */
	            }

	           /*  $("#dbtable tbody").html("");
	            if(str==""){
	            	str="<tr><td colspan=\"2\">暂无数据</td></tr>";
	            	$("#dbtable").find('tbody').append(str);
	            }else{
	            	$("#dbtable").find('tbody').append(str);
	            } */

	        },error: function (jqXHR, textStatus, errorThrown) {
	           	alert(jqXHR.responseText);
	            return false;
	       	}
		});
	}

	function downfile(id){
		location.href="<%=path %>/appuser/downLoadWorkorderFile?id="+id;
	}

	function delfile(id){
		var userid='${pd.userid}';
		var uid='${pd.uid}';
		var data="{\"api\":\"com.yulun.WorkorderWeb\",\"data\":{\"username\":\""+userid+"\",\"id\":\""+id+"\"},\"cmd\":\"WorkorderFileDel\"}";
		$.ajax({
           	type: 'post',
			url:"<%=path%>/api",
            dataType: 'json',
            data:{"data":data}, //参数值
            cache: false,
            success: function (obj) {
            	//console.log(obj);
				location.href="<%=path%>/appWeixin/replay?code=${pd.code}&uid=${pd.uid}&ID_=${pd.ID_}&proc_id=${pd.proc_id}&userid="+userid;
            },error: function (jqXHR, textStatus, errorThrown) {
               	alert(jqXHR.responseText);
                return false;
           	}
		});
   	}


	function getCheckBoxValueThree() {
        //获取input类型是checkBox并且 name="box"选中的checkBox的元素
        var data = $('input:checkbox[name="filecheck"]:checked').map(function () {
            return $(this).val();
        }).get().join(",");
      //弹出结果
      	return data;
    }

	function msgalsert(val,msg){
		if(val!=""){
			mui.alert(msg);
			return false;
		}
	}

	function replay(){
		//alert(pid);
		var userid='${pd.userid}';
		var code='${pd.code}';
		var uid='${pd.uid}';
		//var fileIds=getCheckBoxValueThree();
		var cptemphasis=$("#cptemphasis").val();
		if(cptemphasis==""){
			mui.alert("请输入投诉事由及述求");
			return false;
		}
		var surveyprocess=$("#surveyprocess").val();
		if(surveyprocess==""){
			mui.alert("请输入确定的基本事实与证据");
			return false;
		}
		var isreconciliation=$("#isreconciliation").val();
		if(isreconciliation==""){
			mui.alert("请输入调查结果、企业责任以及是否与消费者达成和解");
			return false;
		}
		var disposeending=$("#disposeending").val();
		if(disposeending==""){
			mui.alert("请输入给消费者的实质性回复（消费者可查询）");
			return false;
		}
		var data="{\"api\":\"com.yulun.WorkorderWeb\",\"data\":{\"cptemphasis\":\""+cptemphasis+"\",\"username\":\""+userid+"\",\"surveyprocess\":\""+surveyprocess+"\",\"isreconciliation\":\""+isreconciliation+"\",\"disposeending\":\""+disposeending+"\",\"code\":\""+code+"\",\"uid\":\""+uid+"\"},\"cmd\":\"WorkorderReplay\"}";

		$.ajax({
        	//几个参数需要注意一下
           	type: 'post',
	      	// url: "http://127.0.0.1:8080/ylxxxtnew/sys/sypzpreAction!getPz.xhtml?endpoint_id=1023&module=home&r="+Math.random(),
			url:"<%=path%>/api",
	        dataType: 'json',
	        data:{"data":data}, //参数值
            success: function (result) {
            	location.href="<%=path%>/appWeixin/complandetail_sh?ID_=${pd.ID_}&proc_id=${pd.proc_id}&ishandle=${pd.ishandle}&userid="+userid;
            },
            error : function() {
                alert("异常！");
            }
        });

	}
	function attupload(){
		var uid='${pd.uid}';
		var userid='${pd.userid}';
		var code='${pd.code}';
		var replay='1';
		location.href="<%=path%>/appWeixin/attupload?ID_=${pd.ID_}&proc_id=${pd.proc_id}&ishandle=${pd.ishandle}&replay="+replay+"&code="+code+"&uid="+uid+"&userid="+userid;
	}
	</script>

<body onload="loadWork()">
	<input type="hidden" id="uid" name="uid">
	<input type="hidden" id="id" name="id">
    <div class="detail-bg">
        <section>
            <div class="white-block clearfix">
                <span class="color-red">| </span>投诉函转发编号：
                <span class="fr">
                </span>
				<div class="form-input">
					${pd.code }
                </div>
            </div>
            <div class="white-block clearfix">
                <span class="color-red">| </span>投诉事由及述求：
                <span class="fr">
                </span>
				<div class="form-input">
					<input type="text" autocomplete="off" id="cptemphasis" name="cptemphasis" class="form-input__inner">
                </div>
            </div>
            <div class="white-block clearfix">
                <span class="color-red">| </span>确定的基本事实与证据：
                <span class="fr">
                </span>
				<div class="form-input">
					<input type="text" autocomplete="off" id="surveyprocess" name="surveyprocess" class="form-input__inner">
                </div>
            </div>
            <div class="white-block clearfix">
                <span class="color-red">| </span>调查结果、企业责任以及是否与消费者达成和解：
                <span class="fr">
                </span>
				<div class="form-input">
					<input type="text" autocomplete="off" id="isreconciliation" name="isreconciliation" class="form-input__inner">
                </div>
            </div>
            <div class="white-block clearfix">
                <span class="color-red">| </span>给消费者的实质性回复<br>（消费者可查询）：
                <span class="fr">
                </span>
				<div class="form-input">
					<input type="text" autocomplete="off" id="disposeending" name="disposeending" class="form-input__inner">
                </div>
            </div>
           <%-- 	<div class="ada-table ada-table-bluebg">
	            <table cellspacing="0" id="dbtable" cellpadding="0" border="0" class="ada-table__header">
					<colgroup>
						<col width="150" />
						<col width="400" />
						<col width="200" />
						<col name="gutter" width="0" />
					</colgroup>
					<thead class="has-gutter">
						<tr>
							<th>
								<div class="cell">请选择</div>
							</th>
							<th>
								<div class="cell">附件名称</div>
							</th>
							<th>
								<div class="cell">操作</div>
							</th>
							<th class="gutter" style="width: 0px; display: none;"></th>
						</tr>
					</thead>
					<tbody>

					</tbody>
				</table>
			</div> --%>
            <div class="wx-button-btn">
            	<!-- <div class="bottom-upload" onclick="attupload()">上传附件</div> -->
            	<div class="wx-button-green" onclick="replay()">提交</div>
				<div class="wx-button-blue" onclick="javascript:history.go(-1);">返回上一页</div>
            </div>
        </section>
    </div>
    <script src="<%=path%>/static/weixin/js/mui.min.js"></script>
    <script src="<%=path%>/static/weixin/js/mui.picker.min.js"></script>
    <script src="<%=path%>/static/weixin/js/mui.poppicker.js"></script>
	<script>

    </script>
</body>

</html>