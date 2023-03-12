
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
    <script src="<%=path%>/static/weixin/js/mui.min.js"></script>
    <script src="<%=path%>/static/weixin/js/mui.picker.min.js"></script>
    <script src="<%=path%>/static/weixin/js/mui.poppicker.js"></script>
</head>

<script type="text/javascript">
	var ishandle='${ishandle}';
	var userid='${userid}';
	function loadWork(){
		
		if(userid!=""){
			var data="";
			if(ishandle=='1'){
				data="{\"api\":\"com.yulun.WorkorderWeb\",\"data\":{\"tokenid\":\"3d0fd800244f48fd9d648724c099528\",\"code\":\"\",\"starttime\":\"\",\"endtime\":\"\",\"tsman\":\"\",\"tssource\":\"\",\"tsdept\":\"\",\"tstype\":\"\",\"pageIndex\":\"1\",\"pageSize\":\"100000\",\"doaction\":\"0\",\"username\":\""+userid+"\"},\"cmd\":\"WorkorderYb\"}";
			}else{
				data="{\"api\":\"com.yulun.WorkorderWeb\",\"data\":{\"tokenid\":\"3d0fd800244f48fd9d648724c099528\",\"code\":\"\",\"starttime\":\"\",\"endtime\":\"\",\"tsman\":\"\",\"tssource\":\"\",\"tsdept\":\"\",\"tstype\":\"\",\"pageIndex\":\"1\",\"pageSize\":\"100000\",\"doaction\":\"0\",\"username\":\""+userid+"\"},\"cmd\":\"WorkorderDb\"}";
			}
	   		//alert(data);
			$.ajax({
	           	type: 'post',
	          	// url: "http://127.0.0.1:8080/ylxxxtnew/sys/sypzpreAction!getPz.xhtml?endpoint_id=1023&module=home&r="+Math.random(),
				url:"<%=path%>/api",
	            dataType: 'json',
	            data:{"data":data}, //参数值
	            cache: false,
	            success: function (obj) {
	               
	               //	$("#yc").val(obj);
	               //	obj='{"home":{"迎宾讲解欢迎语":[{"cont":"欢迎各位领导光临我公司参观指导","descript":"开始进行迎宾讲解欢迎语","lx":"2","remark":"开始进行迎宾讲解欢迎语"}],"首页视频":[{"cont":"/6/11/1515061089456.mp4","descript":"首页视频","lx":"3","remark":"首页视频"}],"首页顶部条":[{"cont":"/5/13/1514556553439.png","descript":"首页顶部条","lx":"1","remark":"首页顶部条"}]}}';
	               //	var zobj=JSON.parse(obj);
	               //	alert(obj.data);
	               	var str="";
	                var data =eval(obj.data);
	                //alert(obj.taskname);
	                //alert(data.length);
	                //alert(data);
	                for(var o in data){ 
	                	var tslevelname="";
	                	if(typeof(data[o].tslevelname)=="undefined"){
	                		tslevelname="";
	                	}else{
	                		tslevelname=data[o].tslevelname;
	                	}
	                	str=str+"<tr>";
	                	str=str+"<td><div class=\"cell\">"+data[o].tsdate+"</div></td>";
	                	if(typeof(data[o].tssourcename)=="undefined"){
	                		 data[o].tssourcename="";   
	              	   	}
	                	str=str+"<td><div class=\"cell\">"+data[o].tssourcename+"</div></td>";
	                	str=str+"<td><div class=\"cell\">"+tslevelname+"</div></td>";
	                	str=str+"<td><div class=\"cell\">"+data[o].clsx+"</div></td>";
	                	
	                	if(ishandle=='1'){
	                		str=str+"<td><div class=\"cell\"><span class=\"btn-opt bg-blue\" onclick=\"cl('"+data[o].proc_id+"','"+data[o].ID_+"')\">查看</span></div></td>";	
	                	}else{
	                		if(data[o].islx=="dpf"){
		                		str=str+"<td><div class=\"cell\"><span class=\"btn-opt bg-green\" onclick=\"pf('"+data[o].id+"','"+data[o].uid+"')\">派发</span></div></td>";
		                	}else if(data[o].islx=="cl"){
		                		if(data[o].isreceive=="1"){
			                		str=str+"<td><div class=\"cell\"><span class=\"btn-opt bg-blue\" onclick=\"cl('"+data[o].proc_id+"','"+data[o].ID_+"')\">处理</span></div></td>";		
		                		}else{
			                		str=str+"<td><div class=\"cell\"><span class=\"btn-opt bg-blue\" onclick=\"receive('"+data[o].ID_+"')\">接收</span></div></td>";	
		                		}
		                	}else if(data[o].islx=="sh"){
		                		if(data[o].isreceive=="1"){
		                			str=str+"<td><div class=\"cell\"><span class=\"btn-opt bg-orange\" onclick=\"cl('"+data[o].proc_id+"','"+data[o].ID_+"')\">审核</span></div></td>";
		                		}else{
			                		str=str+"<td><div class=\"cell\"><span class=\"btn-opt bg-blue\" onclick=\"receive('"+data[o].ID_+"')\">接收</span></div></td>";	
		                		}
		                	}
	                	}
	                	str=str+"</tr>";	
	                }
	                $("#dbtable tbody").html("");
	                if(str==""){
	                	str="<tr><td colspan=\"5\">暂无数据</td></tr>";
	                	$("#dbtable").find('tbody').append(str);
	                }else{
	                	$("#dbtable").find('tbody').append(str);
	                }
	                
	            },error: function (jqXHR, textStatus, errorThrown) {
	               	alert(jqXHR.responseText);
	                return false;
	           	}
			});
		}else{
			 $("#dbtable tbody").html("");
			str="<tr><td colspan=\"5\">暂无数据</td></tr>";
        	$("#dbtable").find('tbody').append(str);
		}
		
   	}
	
	  
    function receive(ID_){
    	
    	
		var data="{\"api\":\"com.yulun.WorkorderWeb\",\"data\":{\"tokenid\":\"3d0fd800244f48fd9d648724c099528\",\"ID_\":\""+ID_+"\",\"ISRECEIVE\":\"1\",\"username\":\""+userid+"\"},\"cmd\":\"WorkorderReceive\"}";
 
    	//alert(data);
          //return false;
        //$("#bottom-bc").attr("disabled", "disabled");
        $.ajax({
            type: 'post',
            url:"<%=path%>/api",
            dataType: 'json',
            data:{"data":data}, //参数值
            success: function (obj) {
                var str = "";
                var data = eval(obj.data);
                if (obj.success == "true") {
                	mui.alert('确认接收成功');
                } else {
                	mui.alert('确认接收失败');
                }
                location.href = "<%=path%>/appWeixin/getUserid?ishandle=${pd.ishandle}";

            }, error: function (jqXHR, textStatus, errorThrown) {
            	mui.alert('提交异常');
                return false;
            }
        });
    }
	
	function cl(proc_id,ID_){
		var userid='${userid}';
		location.href="<%=path%>/appWeixin/complandetail_sh?ID_="+ID_+"&proc_id="+proc_id+"&ishandle="+ishandle+"&userid="+userid;
	}
	function pf(id,uid){
		var userid='${userid}';
		location.href="<%=path%>/appWeixin/distribute?id="+id+"&uid="+uid+"&userid="+userid;
	}
	</script>

<body onload="loadWork()">
    <div>
        <div class="ada-table ada-table-bluebg">
            <table id="dbtable" cellspacing="0" cellpadding="0" border="0" class="ada-table__header">
                <colgroup>
                    <col width="300" />
                    <col width="180" />
                    <col width="180" />
                    <col width="180" />
                    <col width="180" />
                    <col name="gutter" width="0" />
                </colgroup>
                <thead class="has-gutter">
                    <tr>
                        <th>
                            <div class="cell">日期</div>
                        </th>
                        <th>
                            <div class="cell">投诉来源</div>
                        </th> 
                        <th>
                            <div class="cell">投诉等级</div>
                        </th>
                        <th>
                            <div class="cell">处理时限</div>
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
        </div>
    </div>
</body>

</html>