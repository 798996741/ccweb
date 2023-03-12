
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


	function tstypechange(){
		var bigtstype=$("#bigtstype").val();
		if(bigtstype==""){
			$("#tstypespan").html('');
			return false;
		}
		var tstype='${pd.tstype}';
		//alert(bigtstype);
		$.ajax({
			//几个参数需要注意一下
			type : "POST",//方法类型
			dataType : "json",//预期服务器返回的数据类型
			url : "workorder/getTstype.do?bigtstype="+bigtstype,//url
			success : function(result) {
				
				var str="<select id=\"tstype\" name=\"tstype\" >";
				
				$.each(result.list, function(i, list){
					if(tstype==list.DICTIONARIES_ID){
						str=str+"<option value=\""+list.DICTIONARIES_ID+"\" selected>"+list.NAME+"</option>";	
					}else{
						str=str+"<option value=\""+list.DICTIONARIES_ID+"\">"+list.NAME+"</option>";
					}
					
				});
				str=str+"</select>";
				$("#tstypespan").html('');
				
				$("#tstypespan").html(str);
			}
		 });
	}
	
	
	function loadWork(){
		var userid='${pd.userid}';
		var proc_id='${pd.proc_id}';
		var id='${pd.id}';
		var data="{\"api\":\"com.yulun.WorkorderWeb\",\"data\":{\"code\":\"\",\"starttime\":\"\",\"endtime\":\"\",\"tsman\":\"\",\"tssource\":\"\",\"tsdept\":\"\",\"tstype\":\"\",\"pageIndex\":\"1\",\"pageSize\":\"10\",\"doaction\":\"0\",\"username\":\""+userid+"\",\"proc_id\":\""+proc_id+"\",\"id\":\""+id+"\"},\"cmd\":\"WorkorderFindById\"}";
   		//alert(data);
		$.ajax({
           	type: 'post',
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
                //alert(data.length);
                //alert(data);
                console.log(obj);
               if(obj.success=="true"){
            	   $("#code").html(data.code);
            	   $("#hidcode").val(data.code);
            	   $("#id").val(data.id);
            	   $("#tssourcename").html(data.tssourcename);
            	   $("#tssource").val(data.tssource);
            	   $("#tsdate").html(data.tsdate);
            	   
            	   $("#tscont").val(data.tscont);
            	   if(data.tsbigtype!=""){
            		   
            		   	$('#showtsbigtypePicker').html(data.tsbigtype);
   						$("#bigtype").val(data.bigtype);
   						tstypechange();
            	   }
            	   if(data.tstype!=""){
           		   		$('#showtstypePicker').html(data.tstypename);
  						$("#tstype").val(data.tstype);
           	   	   }
            	   if(data.tsclassify!=""){
          		   		$('#showtsclassifyPicker').html(data.tsclassifyname);
 						$("#tsclassify").val(data.tsclassify);
          	   	   }
            	   if(data.tslevel!=""){
         		   		$('#showtslevelPicker').html(data.tslevelname);
						$("#tslevel").val(data.tslevel);
         	   	   }
            	   
            	   if(data.cardtype!=""){
        		   		$('#showcardtypePicker').html(data.cardtypename);
						$("#cardtype").val(data.cardtype);
        	   	   }
            	   
            	   if(data.tsqd!=""){
       		   			$('#showtsqdPicker').html(data.tsqdname);
						$("#tsqd").val(data.tsqd);
       	   	   	   }
            	   
            	   $("#email").val(data.email);
            	   $("#tssq").val(data.tssq);
            	   $("#deport").val(data.deport);
            	   $("#arrport").val(data.arrport);
            	   
            	   if(data.clsx!=""){
        		   		$('#showclsxPicker').html(data.clsx);
						$("#clsx").val(data.clsx);
        	   	   }
            	   
            	   //alert(data.ishf);
            	   var ishf=data.ishf;
            	   if(ishf=="1"){
            		   $("input[name='ishf'][value=1]").attr("checked",true); 
            	   }else{
            		   $("input[name='ishf'][value=0]").attr("checked",true); 
            	   }
            	   
            	   $("#tsman").val(data.tsman);
            	   $("#cardid").val(data.cardid);
            	   $("#tstel").val(data.tstel);
            	   if(data.cjdate!=""){
            		   $('#dateResult').html(data.cjdate);
    				   $("#cjdate").val(data.cjdate);   
            	   }
            	   
            	   $("#hbh").val(data.hbh);
            	   
            	   $("#tsdeptname").html(data.tsdeptname);
            	   $("#proc_id").val(data.proc_id);
            	   $("#uid").val(data.uid);
            	   
            	   var str = data.tsdept;
            	   $(str.split(",")).each(function (i,dom){
            	       $(":checkbox[value='"+dom+"']").prop("checked",true);
            	   });
            	  // alert(data.uid);
               }
                
            },error: function (jqXHR, textStatus, errorThrown) {
               	alert(jqXHR.responseText);
                return false;
           	}
		});
   	}
	
	function getCheckBoxValueThree() {
        //获取input类型是checkBox并且 name="box"选中的checkBox的元素
        var data = $('input:checkbox[name="tsdept"]:checked').map(function () {
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
	
	function handle(pid){
		//alert(pid);
		var userid='${pd.userid}';
		var id=$("#id").val();
		var data="{\"api\":\"com.yulun.WorkorderWeb\",\"data\":{\"username\":\""+userid+"\",\"doaction\":\"azb\",\"msg\":\"yes\",\"OPINION\":\"派发工单\",\"PROC_INST_ID_\":\""+pid+"\",\"id\":\""+id+"\"},\"cmd\":\"WorkorderSh\"}";
		//alert(data);
		//return false;
		$.ajax({
        	//几个参数需要注意一下
            type: "POST",//方法类型
            dataType: "json",//预期服务器返回的数据类型
            url: "<%=path%>/api" ,//url
            data:{"data":data}, //参数值
            success: function (result) {
            	 location.href="<%=path%>/appWeixin/getUserid";
            },
            error : function(result) {
                alert("异常");
            }
        });
		
	}
	
	function sh(doaction){
		var userid='${pd.userid}';
		var proc_id='${pd.proc_id}';
		
		
		var id=$("#id").val();
		var code=$("#code").html();
		var tssource=$("#tssource").val();
		//msgalsert(tssource,"请选择投诉来源");
		
		var tsdate=$("#tsdate").html();
		if(tsdate==""){
			mui.alert("请选择投诉时间");
			return false;
		}
		
		var tsman=$("#tsman").val();
		var tstel=$("#tstel").val();
		var tscont=$("#tscont").val();
		if(tscont==""){
			mui.alert("请输入投诉内容");
			return false;
		}
		
		var tslevel=$("#tslevel").val();
		if(tslevel==""){
			mui.alert("请选择投诉等级");
			return false;
		}
		
		var tsdept=getCheckBoxValueThree();
		if(tsdept==""){
			mui.alert("请选择投诉部门");
			return false;
		}
		var tstype=$("#tstype").val();
		if(tstype==""){
			mui.alert("请选择投诉分类");
			return false;
		}
		var tsclassify=$("#tsclassify").val();
		if(tsclassify==""){
			mui.alert("请选择投诉类型");
			return false;
		}
		//msgalsert(tsclassify,"请选择投诉类型");
		
		
		var ishf= $('input:radio[name="ishf"]:checked').val();
		if(ishf==""){
			mui.alert("请选择是否回复");
			return false;
		}
		var type="0";
		var cardid=$("#cardid").val();
		var cjdate=$("#cjdate").val();
		var hbh=$("#hbh").val();
		var clsx=$("#clsx").val();
		var endreason=$("#endreason").val();
		
		var cardtype=$("#cardtype").val();
		var tsdq=$("#tsqd").val();
		var email=$("#email").val();
		var tssq=$("#tssq").val();
		var arrport=$("#arrport").val();
		var deport=$("#deport").val();
		
		var reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
		//alert(reg.test(email));
		if(email!=""){
			if(!reg.test(email)){
				mui.alert("邮箱格式不正确");
				return false;	
			}
		}
		
		if(tstel!=""){
			if(!(/^1[3456789]\d{9}$/.test(tstel))){ 
				mui.alert("联系电话不正确");
				return false;
			} 
		}
		
		var cfbm=$("#cfbm").val();
		if(cfbm!=""){
			doaction='0';
			if(endreason==""){
				endreason="重复工单";
			}
		}
		
		if(doaction=="0"){
			if(endreason==""){
				endreason="快速处理";
				//mui.alert("请输入结束原因");
				//return false;
			}
		}
		var uid=$("#uid").val();
		
   		var data="{\"api\":\"com.yulun.WorkorderWeb\",\"data\":{\"username\":\""+userid+"\",\"id\":\""+id+"\",\"code\":\""+code+"\",\"tssource\":\""+tssource+"\",\"tsdate\":\""+tsdate+"\",\"tsman\":\""+tsman+"\",\"tstel\":\""+tstel+"\",\"tscont\":\""+tscont+"\",\"tslevel\":\""+tslevel+"\",\"tsdept\":\""+tsdept+"\",\"cfbm\":\""+cfbm+"\",\"tstype\":\""+tstype+"\",\"ishf\":\""+ishf+"\",\"type\":\""+type+"\",\"cardid\":\""+cardid+"\",\"cjdate\":\""+cjdate+"\",\"hbh\":\""+hbh+"\",\"clsx\":\""+clsx+"\",\"endreason\":\""+endreason+"\",\"doaction\":\""+doaction+"\",\"uid\":\""+uid+"\",\"tsclassify\":\""+tsclassify+"\",\"cardtype\":\""+cardtype+"\",\"tsdq\":\""+tsdq+"\",\"email\":\""+email+"\",\"tssq\":\""+tssq+"\",\"arrport\":\""+arrport+"\",\"deport\":\""+deport+"\"},\"cmd\":\"WorkorderEdit\"}";
   		//alert(data);
   		//return false;
   		
   		$("#bottom-bc").attr("disabled", "disabled"); 
   		$("#bottom-pf").attr("disabled", "disabled"); 
   		
   	 	$.ajax({
           	type: 'post',
			url:"<%=path%>/appuser/workorderSave.do",
            dataType: 'json',
            data:{"data":data}, //参数值
            cache: false,
            success: function (obj) {
               if(obj.success=="true"){
            	   location.href="<%=path%>/appWeixin/getUserid";
            	   <%-- if(doaction=="0"){
            		   location.href="<%=path%>/appWeixin/getUserid";
            	   }
            	   $("#bottom-bc").attr("disabled", false); 
            	   $("#bottom-pf").attr("disabled", false); 
            	   if(doaction=="1"){
            	   		$("#bottom-pf").attr("disabled", true); 
            	   		
            	   		if(obj.proc_id!=""&&typeof(obj.proc_id)!="undefined"){
            	   			//handle(obj.proc_id);
            	   		}
            	   }else{
            		   mui.alert(obj.msg);
            	   } --%>
               }else{
            	   mui.alert(obj.msg);
            	   $("#bottom-bc").attr("disabled", false); 
            	   $("#bottom-pf").attr("disabled", false); 
               }
                
            },error: function (jqXHR, textStatus, errorThrown) {
               	alert(jqXHR.responseText);
                return false;
           	}
		});
   	}
	
	function attlist(){
		var uid=$("#uid").val();
		var userid='${pd.userid}';
		var id='${pd.id}';
		location.href="<%=path%>/appWeixin/attlist?action=pf&uid="+uid+"&id="+id+"&userid="+userid;
	}
	
	function deallist(){
		var userid='${pd.userid}';
		var ID_='${pd.ID_}';
		var proc_id='${pd.proc_id}';
		var id='${pd.id}';
		if(proc_id==""){
			proc_id=$("#proc_id").val();
		}
		location.href="<%=path%>/appWeixin/deallist?action=pf&ID_="+ID_+"&id="+id+"&proc_id="+proc_id+"&userid="+userid;
	}
	
	function complain(){
		var userid='${pd.userid}';
		var id='${pd.id}';
		var uid='${pd.uid}';
		location.href="<%=path%>/appWeixin/complainlist?action=dis&uid="+uid+"&id="+id+"&userid="+userid;
	}
	
	function choice(){
		var userid='${pd.userid}';
		var hidcode=$("#hidcode").val();
		location.href="<%=path%>/appWeixin/complainlist?userid="+userid;
	}
	
	</script>

<body >
	<input type="hidden" id="uid" name="uid">
	<input type="hidden" id="id" name="id">
	<input type="hidden" id="proc_id" name="proc_id">
	<input type="hidden" id="tssource" name="tssource">
    <div class="detail-bg">
        <header class="detail-header">
            <div class="header-content">
                <div>投诉编码: <input type="hidden" id="hidcode"><span class="color-yellow" id="code"></span></div>
                <div>投诉日期: <span class="color-yellow" id="tsdate"></span></div>
                <div>投诉来源: <span class="color-yellow" id="tssourcename"></span></div>
            </div>
        </header>
        <section>
            <div class="blue-block">
                <i></i>
                <p>投诉详情</p>
            </div>
            <div class="white-block clearfix">
                <span class="color-red">| </span>投诉内容：
                <span class="fr">
                </span>
				<div class="form-input">
                    <input type="text" autocomplete="off" id="tscont" class="form-input__inner">
                </div>
            </div>
             
            <div class="form-item">
                <label class="form-label">
                    <span class="color-red">| </span>投诉类别(大项)
               	</label>
               	<div class="form-content form-date">
		         	<div id='showtsbigtypePicker' class="selectPicker">投诉类别(大项)
		          		<div class="down-icon"></div>
		         	</div>
		        </div>
		        <input type="hidden" id="bigtype" name="bigtype" >
		       
            </div>
            <div class="form-item">
                <label class="form-label">
                    <span class="color-red">| </span>投诉类别(细项)
                </label>
                <div class="form-content form-date">
		         	<div id='showtstypePicker' class="selectPicker">投诉类别(细项)
		          		<div class="down-icon"></div>
		         	</div>
		        </div>
		       	<input type="hidden" id="tstype" name="tstype" >
            </div>
            <div class="form-item">
                <label class="form-label">
                    <span class="color-red">| </span>投诉分类
                </label>
                <div class="form-content form-date">
		         	<div id='showtsclassifyPicker' class="selectPicker">投诉分类
		          		<div class="down-icon"></div>
		         	</div>
		        </div>
		       	<input type="hidden" id="tsclassify" name="tsclassify" >
            </div>
            <div class="form-item">
                <label class="form-label">
                    <span class="color-red">| </span>
                    投诉等级</label>
                
                <div class="form-content form-date">
		         	<div id='showtslevelPicker' class="selectPicker"> 投诉等级
		          		<div class="down-icon"></div>
		         	</div>
		        </div>
		       <input type="hidden" id="tslevel" name="tslevel" >
            </div>
            <div class="form-item">
                <label class="form-label">
                    <span class="color-red">| </span>
                    是否回访</label>
               <div class="form-content">
                    <input type="radio" name="ishf" id="ishf" value="1"/> 是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="radio" name="ishf" id="ishf" value="0"/> 否
                </div>
            </div>
            <div class="blue-block">
                <i></i>
                <p>旅客信息</p>
            </div>
            <div class="form-item">
                <label class="form-label">
                    <span class="color-red">| </span>
                    投诉人</label>
                <div class="form-content">
                    <div class="form-input">
                        <input type="text" id="tsman" autocomplete="off" class="form-input__inner">
                    </div>
                </div>
            </div>
             <div class="form-item">
                <label class="form-label">
                    <span class="color-red">| </span>证件类型
                </label>
                <div class="form-content form-date">
		         	<div id='showcardtypePicker' class="selectPicker"> 证件类型
		          		<div class="down-icon"></div>
		         	</div>
		        </div>
		       <input type="hidden" id="cardtype" name="cardtype" >
            </div>
            <div class="form-item">
                <label class="form-label">
                    <span class="color-red">| </span>
                    证件号码</label>
                <div class="form-content">
                    <div class="form-input">
                        <input type="text" id="cardid" autocomplete="off" class="form-input__inner">
                    </div>
                </div>
            </div>
            <div class="form-item">
                <label class="form-label">
                    <span class="color-red">| </span>
                    联系电话</label>
                <div class="form-content">
                    <div class="form-input">
                        <input type="text" id="tstel" autocomplete="off" class="form-input__inner">
                    </div>
                </div>
            </div>
            
            
            <div class="form-item">
                <label class="form-label">
                    <span class="color-red">| </span>邮箱
                </label>
                <div class="form-content">
                     <div class="form-input">
                        <input type="text" id="email" autocomplete="off" class="form-input__inner">
                    </div>
                </div>
            </div>
            
            <div class="form-item">
                <label class="form-label">
                    <span class="color-red">| </span>投诉渠道
                </label>
                 <div class="form-content form-date">
		         	<div id='showtsqdPicker' class="selectPicker"> 投诉渠道
		          		<div class="down-icon"></div>
		         	</div>
		        </div>
		       <input type="hidden" id="tsqd" name="tsqd" >
            </div>
             <div class="form-item">
                <label class="form-label">
                    <span class="color-red">| </span>投诉诉求
                </label>
                <div class="form-content">
                     <div class="form-input">
                        <input type="text" id="tssq" autocomplete="off" class="form-input__inner">
                    </div>
                </div>
            </div>
            <div class="form-item">
                <label class="form-label">
                    <span class="color-red">| </span>
                    乘机日期</label>
                <div class="form-content" >
                    <div class="btn date selectPicker"  id="dateResult" data-options='{"type":"date"}'>请选择航班日期
                        <div class="down-icon"></div>
                    </div>
                </div>
                <input type="hidden" id="cjdate" name="cjdate" >
            </div>
            <div class="form-item">
                <label class="form-label">
                    <span class="color-red">| </span>
                    航班号/航程</label>
               <div class="form-content">
                    <div class="form-input">
                        <input type="text" id="hbh" autocomplete="off" class="form-input__inner">
                    </div>
                </div>
            </div>
            <div class="form-item">
                <label class="form-label">
                    <span class="color-red">| </span>出发机场
                </label>
                <div class="form-content">
                   <div class="form-input">
                        <input type="text" id="deport" autocomplete="off" class="form-input__inner">
                    </div>
                   
                </div>
            </div>
            <div class="form-item">
                <label class="form-label">
                    <span class="color-red">| </span>目的机场
                </label>
                <div class="form-content">
                    <div class="form-input">
                        <input type="text" id="arrport" autocomplete="off" class="form-input__inner">
                    </div>
                </div>
            </div>
            <div class="blue-block">
                <i></i>
                <p>处理信息</p>
            </div>
            <div class="form-item">
                <label class="form-label">
                    <span class="color-red">| </span>
                    处理时限</label>
                <div class="form-content form-date">
		         	<div id='showclsxPicker' class="selectPicker">处理时限
		          		<div class="down-icon"></div>
		         	</div>
		        </div>
		       	<input type="hidden" id="clsx" name="clsx" >
            </div>

            <div class="form-item">
                <label class="form-label">
                    <span class="color-red">| </span>
                    附件</label>
                <div class="form-content">
                    <span class="fr color-orange" onclick="attlist()">查看></span>
                </div>
            </div>
            <div class="form-item">
                <label class="form-label">
                    <span class="color-red">| </span>
                    投诉部门</label>
                <div class="form-content selectPicker">
                	<ul style="width:100%">
	                	<c:forEach items="${areaList }" var="area">
	                   		<li><input type="checkbox" name="tsdept" id="tsdept" value="${area.AREA_CODE }"/> ${area.NAME }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
	                    </c:forEach>
                    </ul>
                </div>
            </div>
            
            <div class="white-block clearfix" id="end" style="display:none">
                <span class="color-red">| </span>请输入快速处理原因：
                <span class="fr">
                </span>
				<div class="form-input">
                    <input type="text" autocomplete="off" id="endreason" name="endreason" class="form-input__inner">
                </div>
            </div>
            <div class="form-item">
                <label class="form-label">
                    <span class="color-red">| </span>重复投诉编号
                </label>
                <div class="form-content">
                    <div class="form-input">
                        <input type="text" value="${pd.cfbm}"  id="cfbm" autocomplete="off" class="form-input__inner">
                    </div>
                </div>
            </div>

            <div class="wx-button-btn">
            	<div class="wx-button-green" onclick="kscl()" id="kscl">
             		快速处理
                </div>
                <div class="wx-button-red" id="jsgd" onclick="sh('0')" style="display:none">
             		结束工单
                </div>
                <div class="wx-button-red"  onclick="complain()">重复投诉</div>
                <div class="wx-button-blue" id="bottom-bc" onclick="sh('2')">保存</div>
<%--                <div class="bottom-bc" style="right: 20%;">--%>
<%--                	<input type="button" style="background: #5face2;border:0px;color:#fff;padding:0 5px;padding:0px" id="bottom-bc" onclick="sh('2')" value="保存">--%>
<%--                </div>--%>
                <div class="wx-button-blue" id="bottom-bc" onclick="sh('1')">派发</div>
<%--                <div class="bottom-pf" style="right: 5%;">--%>
<%--                	<input type="button" style="background: #5face2;border:0px;color:#fff;padding:0 5px;padding:0px" id="bottom-pf" onclick="sh('1')" value="派发">--%>
<%--                </div>--%>
            </div>
        </section>
    </div>
    <script src="<%=path%>/static/weixin/js/mui.min.js"></script>
    <script src="<%=path%>/static/weixin/js/mui.picker.min.js"></script>
    <script src="<%=path%>/static/weixin/js/mui.poppicker.js"></script>
	<script>
		function kscl(){
			$("#kscl").hide();
			$("#jsgd").show();
			$("#end").show();
		}
		
		function back(){
			$("#kscl").show();
			$("#jsgd").hide();
			$("#end").hide();
		}
		var tstypePicker ;
		function tstypechange(){
			var bigtstype=$("#bigtype").val();
			//alert(bigtstype);
			if(bigtstype==""){
				return false;
			}
			$.ajax({
				//几个参数需要注意一下
				type : "POST",//方法类型
				dataType : "json",//预期服务器返回的数据类型
				url : "<%=path%>/appWeixin/getTstype.do?bigtstype="+bigtstype,//url
				success : function(result) {
					
					tstypePicker.setData(result.list);
			        
				}
			 });
		}
		
	
        (function ($) {
        	
            $.init();
            var btns = $('.btn');
            var dateResult = document.getElementById('dateResult');
            btns.each(function (i, btn) {
                btn.addEventListener('tap', function () {
                    var _self = this;
                    if (_self.picker) {
                        _self.picker.show(function (rs) {
                            console.log(rs.text);
                            dateResult.innerText = rs.text;
                        	document.getElementById("cjdate").value=rs.text;
                            _self.picker.dispose();
                            _self.picker = null;
                        });
                    } else {
                        var optionsJson = this.getAttribute('data-options') || '{}';
                        var options = JSON.parse(optionsJson);
                        _self.picker = new $.DtPicker(options);
                        _self.picker.show(function (rs) {
                            dateResult.innerText = rs.text;
                            document.getElementById("cjdate").value=rs.text;
                            _self.picker.dispose();
                            _self.picker = null;
                        });
                    }

                }, false);
            });
            var _getParam = function (obj, param) {
                return obj[param] || '';
            };
            tstypePicker = new $.PopPicker();
            var showtstypePickerButton = document.getElementById('showtstypePicker');
            showtstypePickerButton.addEventListener('tap', function (event) {
            	tstypePicker.show(function (items) {
	              	console.log(items[0])
	              	showtstypePickerButton.innerText = _getParam(items[0], 'text');
	              //返回 false 可以阻止选择框的关闭
	              //return false;
	              	document.getElementById("tstype").value=items[0].value;
             	});
            }, false);
            
            var tsbigtypePicker = new $.PopPicker();
            tsbigtypePicker.setData(${tstypejson});
            var showtsbigtypePickerButton = document.getElementById('showtsbigtypePicker');
            showtsbigtypePickerButton.addEventListener('tap', function (event) {
            	tsbigtypePicker.show(function (items) {
	              	console.log(items[0])
	              	showtsbigtypePickerButton.innerText = _getParam(items[0], 'text');
	              	document.getElementById("bigtype").value=items[0].value;
	              	tstypechange();
	              	//返回 false 可以阻止选择框的关闭
	              //return false;
	              	//
             	});
            }, false);
            
            
            var tsclassifyPicker = new $.PopPicker();
            tsclassifyPicker.setData(${tsclassifyjson});
            var showtsclassifyPickerButton = document.getElementById('showtsclassifyPicker');
            showtsclassifyPickerButton.addEventListener('tap', function (event) {
            	tsclassifyPicker.show(function (items) {
	              	console.log(items[0])
	              	showtsclassifyPickerButton.innerText = _getParam(items[0], 'text');
	              //返回 false 可以阻止选择框的关闭
	              //return false;
	              	document.getElementById("tsclassify").value=items[0].value;
             	});
            }, false);
            
            var tslevelPicker = new $.PopPicker();
            tslevelPicker.setData(${tsleveljson});
            var showtslevelPickerButton = document.getElementById('showtslevelPicker');
            showtslevelPickerButton.addEventListener('tap', function (event) {
            	tslevelPicker.show(function (items) {
	              	console.log(items[0])
	              	showtslevelPickerButton.innerText = _getParam(items[0], 'text');
	              //返回 false 可以阻止选择框的关闭
	              //return false;
	              	document.getElementById("tslevel").value=items[0].value;
             	});
            }, false);
            
            
            var cardtypePicker = new $.PopPicker();
            cardtypePicker.setData(${cardtypejson});
            var showcardtypePickerButton = document.getElementById('showcardtypePicker');
            showcardtypePickerButton.addEventListener('tap', function (event) {
            	cardtypePicker.show(function (items) {
	              	console.log(items[0])
	              	showcardtypePickerButton.innerText = _getParam(items[0], 'text');
	              //返回 false 可以阻止选择框的关闭
	              //return false;
	              	document.getElementById("cardtype").value=items[0].value;
             	});
            }, false);
            
            
            var tsqdPicker = new $.PopPicker();
            tsqdPicker.setData(${tsqdjson});
            var showtsqdPickerButton = document.getElementById('showtsqdPicker');
            showtsqdPickerButton.addEventListener('tap', function (event) {
            	tsqdPicker.show(function (items) {
	              	console.log(items[0])
	              	showtsqdPickerButton.innerText = _getParam(items[0], 'text');
	              //返回 false 可以阻止选择框的关闭
	              //return false;
	              	document.getElementById("tsqd").value=items[0].value;
             	});
            }, false);
            
            // 学历
            var clsxPicker = new $.PopPicker();
            clsxPicker.setData([{
                value: '24H',
                text: '24H'
            }, {
                value: '48H',
                text: '48H'
            }, {
                value: '3D',
                text: '3D'
            }, {
                value: '7D',
                text: '7D'
            }, {
                value: '0',
                text: '其他'
            }]);
            var showclsxPickerButton = document.getElementById('showclsxPicker');
            showclsxPickerButton.addEventListener('tap', function (event) {
                clsxPicker.show(function (items) {
                    console.log(items[0])
                    showclsxPickerButton.innerText = _getParam(items[0], 'text');
                    document.getElementById("clsx").value=items[0].value;
                    //返回 false 可以阻止选择框的关闭
                    //return false;
                });
            }, false);
            loadWork();
        })(mui);
    </script>
</body>

</html>