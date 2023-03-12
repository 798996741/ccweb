
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
                if(obj.success=="true"){
                	 //alert(data.length);
                    console.log(obj);
                    for(var o in data){ 
                    	//alert(o);
                    	
                    	str=str+"<tr>";
                    	str=str+"<td><div class=\"cell\">"+data[o].filename+"</div></td>";
                    	
                    	str=str+"<td><div class=\"cell\"><span class=\"wx-button-blue\" onclick=\"downfile("+data[o].id+")\">下载</span>&nbsp;&nbsp;<span class=\"wx-button-red\" onclick=\"delfile("+data[o].id+")\">删除</span> </div></td>";
                    	str=str+"</tr>";	
                    }
                }
               
                $("#dbtable tbody").html("");
                if(str==""){
                	str="<tr><td colspan=\"2\">暂无数据</td></tr>";
                	$("#dbtable").find('tbody').append(str);
                }else{
                	$("#dbtable").find('tbody').append(str);
                }
                
            },error: function (jqXHR, textStatus, errorThrown) {
               	alert(jqXHR.responseText);
                return false;
           	}
		});
   	}
	function downfile(id){
		location.href="<%=path %>/appuser/downLoadWorkorderFile?id="+id;
	}
	//downLoadWorkorderFile
	
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
            	console.log(obj);
				location.href="<%=path%>/appWeixin/attlist?uid="+uid+"&userid="+userid;
            },error: function (jqXHR, textStatus, errorThrown) {
               	alert(jqXHR.responseText);
                return false;
           	}
		});
   	}
	
	
	function cl(proc_id,ID_){
		var userid='${userid}';
		location.href="<%=path%>/appWeixin/complandetail_sh?ID_="+ID_+"&proc_id="+proc_id+"&userid="+userid;
	}
	
	function attupload(){
		var uid='${pd.uid}';
		var userid='${pd.userid}';
		var id='${pd.id}';
		var proc_id='${pd.proc_id}';
		var action='${pd.action}';
		var ID_='${pd.ID_}';
		location.href="<%=path%>/appWeixin/attupload?id="+id+"&action="+action+"&ID_="+ID_+"&proc_id="+proc_id+"&uid="+uid+"&userid="+userid;
	}
	function back(){
		var proc_id='${pd.proc_id}';
		var ID_='${pd.ID_}';
		var userid='${pd.userid}';
		var action='${pd.action}';
		if(action=='pf'){
			var id='${pd.id}';
			location.href="<%=path%>/appWeixin/distribute?ishandle=${pd.ishandle}&id="+id+"&userid="+userid+"";
		}else{
			location.href="<%=path%>/appWeixin/complandetail_sh?ishandle=${pd.ishandle}&ID_="+ID_+"&proc_id="+proc_id+"&userid="+userid+"";
		}
	}
	
	</script>

<body onload="loadWork()">
	<div class="ada-table ada-table-bluebg">
		<table cellspacing="0" id="dbtable" cellpadding="0" border="0" class="ada-table__header">
			<colgroup>
				<col width="500" />
				<col width="500" />
				<col name="gutter" width="0" />
			</colgroup>
			<thead class="has-gutter">
				<tr>
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
		<div class="wx-button-btn">
		<div class="wx-button-blue" onclick="attupload()">上传附件</div>
		<div class="wx-button-blue" onclick="back();">返回上一页</div>
		</div>
	</div>
</body>

</html>