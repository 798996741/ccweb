<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    String userid = request.getParameter("userid");
    String proc_id = request.getParameter("proc_id");
%>
<!DOCTYPE html>
<html>

<head>
    <title>工单处理</title>
    <meta charset="utf-8"/>
    <meta name="App-Config" content="fullscreen=yes,useHistoryState=yes,transition=yes"/>
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="yes" name="apple-touch-fullscreen"/>
    <meta content="telephone=no,email=no" name="format-detection"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no"/>
    <script src="http://g.tbcdn.cn/mtb/lib-flexible/0.3.4/??flexible_css.js,flexible.js"></script>
    <script src="<%=path%>/static/weixin/js/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/weixin/css/mui.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/weixin/css/mui.picker.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/weixin/css/mui.poppicker.css" rel="stylesheet"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/weixin/css/common.css"/>
    <script src="<%=path%>/static/weixin/js/mui.min.js"></script>
    <script src="<%=path%>/static/weixin/js/mui.picker.min.js"></script>
    <script src="<%=path%>/static/weixin/js/mui.poppicker.js"></script>
</head>

<script type="text/javascript">
    var taskname = '${taskname}';

    function loadWork() {
        var userid = '${pd.userid}';
        var proc_id = '${pd.proc_id}';

        console.log(taskname);
        var id = '${pd.id}';
        var data = "{\"api\":\"com.yulun.WorkorderWeb\",\"data\":{\"tokenid\":\"3d0fd800244f48fd9d648724c099528\",\"code\":\"\",\"starttime\":\"\",\"endtime\":\"\",\"tsman\":\"\",\"tssource\":\"\",\"tsdept\":\"\",\"tstype\":\"\",\"pageIndex\":\"1\",\"pageSize\":\"1000\",\"doaction\":\"0\",\"username\":\"" + userid + "\",\"proc_id\":\"" + proc_id + "\",\"id\":\"" + id + "\"},\"cmd\":\"WorkorderFindById\"}";
        //alert(data);
        $.ajax({
            type: 'post',
            url: "<%=path%>/api",
            dataType: 'json',
            data: {"data": data}, //参数值
            cache: false,
            success: function (obj) {

                //	$("#yc").val(obj);
                //	obj='{"home":{"迎宾讲解欢迎语":[{"cont":"欢迎各位领导光临我公司参观指导","descript":"开始进行迎宾讲解欢迎语","lx":"2","remark":"开始进行迎宾讲解欢迎语"}],"首页视频":[{"cont":"/6/11/1515061089456.mp4","descript":"首页视频","lx":"3","remark":"首页视频"}],"首页顶部条":[{"cont":"/5/13/1514556553439.png","descript":"首页顶部条","lx":"1","remark":"首页顶部条"}]}}';
                //	var zobj=JSON.parse(obj);
                //	alert(obj.data);
                var str = "";
                var data = eval(obj.data);
                //alert(data.length);
                //alert(data);

                if (obj.success == "true") {
                    $("#code").html(data.code);
                    $("#hidcode").val(data.code);
                    $("#id").val(data.id);
                    if (typeof (data.tssourcename) != "undefined") {
                        $("#tssourcename").html(data.tssourcename);
                    }

                    $("#tsdate").html(data.tsdate);
                    $("#tsbigtype").html(data.tsbigtype);
                    $("#tscont").html(data.tscont);
                    $("#tstypename").html(data.tstypename);

                    $("#tsclassifyname").html(data.tsclassifyname);
                    $("#tslevelname").html(data.tslevelname);
                    var ishf = data.ishf;
                    if (ishf == "1") {
                        ishf = "是";
                    } else {
                        ishf = "否";
                    }
                    $("#ishf").html(ishf);
                    $("#tsman").html(data.tsman);
                    $("#cardid").html(data.cardid);
                    $("#tstel").html(data.tstel);
                    $("#cjdate").html(data.cjdate);
                    $("#hbh").html(data.hbh);
                    $("#clsx").html(data.clsx);

                    $("#proc_id").val(data.proc_id);
                    $("#uid").val(data.uid);
                    $("#tsdept").val(data.tsdept);
                    if ('${pd.cfbm}' == "") {
                        $("#cfbm").val(data.cfbm);
                    }

                    $("#email").html(data.email);
                    $("#tsqd").html(data.tsqdname);
                    $("#cardtype").html(data.cardtypename);
                    $("#tssq").html(data.tssq);
                    $("#deport").html(data.deport);
                    $("#arrport").val(data.arrport);

                    // alert(data.uid);
                    if (taskname == '工单专员审批') {
                    	$("#oldtsdept").val(data.tsdept);
                        $("#tsdeptnamegd").show();
                        var str = data.tsdept;
                        $(str.split(",")).each(function (i, dom) {
                            $(":checkbox[value='" + dom + "']").prop("checked", true);
                        });
                    } else {
                        $("#tsdept").val(data.tsdept);
                        $("#tsdeptname").html(data.tsdeptname);
                    }
                }

            }, error: function (jqXHR, textStatus, errorThrown) {
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


    function sh(action) {
        var userid = '${pd.userid}';
        var proc_id = '${pd.proc_id}';
        var OPINION = $("#OPINION").val();
        var cfbm = $("#newcfbm").val();
        if (typeof (cfbm) == "undefined") {
        	cfbm = "";
        }
        var ID_ = '${pd.ID_}';
        var tsdept ="";
        var msg = $("input[name='msg']:checked").val();
        if (typeof (msg) == "undefined") {
            msg = "";
        }
        if (msg == "no" && action == "2") {
            tsdept = $("#tsdept").val();
            if (taskname == '工单专员审批') {
                tsdept = getCheckBoxValueThree();
            }

            if (tsdept == "") {
                mui.alert("请选择投诉的部门");
                return false;
            } else {
                $("#tsdept").val(tsdept);
            }
        }
        
        if(taskname=='工单专员审批'&&msg=='retsdept'){
        	tsdept =getCheckBoxValueThree();;
       		var oldtsdept = $("#oldtsdept").val();
       		//alert(oldtsdept);
       	 	if (tsdept == oldtsdept) {
       	 		mui.alert("您选择了重选部门选项，请重新选择投诉的部门！");
                return false;
            }
       }
      
        //alert(tsdept);
       // return false;

        //$("#msg").val(msg);
        //$("#OPINION").val(getContent());
        if (OPINION == "") {
            mui.alert('请输入处理内容');
            return false;
        }


        var data = "{\"api\":\"com.yulun.WorkorderWeb\",\"data\":{\"username\":\"" + userid + "\",\"PROC_INST_ID_\":\"" + proc_id + "\",\"OPINION\":\"" + OPINION + "\",\"ID_\":\"" + ID_ + "\",\"tsdept\":\"" + tsdept + "\",\"msg\":\"" + msg + "\",\"cfbm\":\"" + cfbm + "\"},\"cmd\":\"WorkorderSh\"}";
        //alert(data);
        //return false;
        $("#bottom-bc").attr("disabled", "disabled");

        $.ajax({
            type: 'post',
            url: "<%=path%>/api",
            dataType: 'json',
            data: {"data": data}, //参数值
            cache: false,
            success: function (obj) {

                var str = "";
                var data = eval(obj.data);
                if (obj.success == "true") {
                    mui.alert('提交成功');
                    $("#bottom-bc").attr("disabled", true);
                } else {
                    $("#bottom-bc").attr("disabled", false);
                }
                location.href = "<%=path%>/appWeixin/getUserid?ishandle=${pd.ishandle}";

            }, error: function (jqXHR, textStatus, errorThrown) {
            	 mui.alert('提交异常');
                return false;
            }
        });
    }

    function attlist() {
        var uid = $("#uid").val();
        var userid = '${pd.userid}';
        var proc_id = '${pd.proc_id}';
        var ID_ = '${pd.ID_}';
        var ishandle = '${pd.ishandle}';

        if (ishandle == '1') {
            location.href = "<%=path%>/appWeixin/attlist?ishandle=${pd.ishandle}&proc_id=" + proc_id + "&ID_=" + ID_ + "&action=1&uid=" + uid + "&userid=" + userid;
        } else {
            if (taskname == '工单提交' || taskname == '') {
                location.href = "<%=path%>/appWeixin/attlist?proc_id=" + proc_id + "&ID_=" + ID_ + "&uid=" + uid + "&userid=" + userid;
            } else {
                location.href = "<%=path%>/appWeixin/attlist?proc_id=" + proc_id + "&ID_=" + ID_ + "&action=1&uid=" + uid + "&userid=" + userid;
            }
        }

    }

    function deallist() {
        var userid = '${pd.userid}';
        var ID_ = '${pd.ID_}';
        var proc_id = '${pd.proc_id}';
        //var ishandle='${pd.ishandle}';
        if (proc_id == "") {
            proc_id = $("#proc_id").val();
        }
        location.href = "<%=path%>/appWeixin/deallist?ishandle=${pd.ishandle}&ID_=" + ID_ + "&proc_id=" + proc_id + "&userid=" + userid;
    }

    function complain() {
        var userid = '${pd.userid}';
        var proc_id = '${pd.proc_id}';
        var id = $("#id").val();
        var uid = $("#uid").val();
        var ID_ = '${pd.ID_}';
        var ishandle = '${pd.ishandle}';
        location.href = "<%=path%>/appWeixin/complainlist?ishandle=" + ishandle + "&ID_=" + ID_ + "&id=" + id + "&uid=" + uid + "&proc_id=" + proc_id + "&action=deal&userid=" + userid;
    }

    function replay(code, uid, ID_, proc_id) {
        var userid = '${pd.userid}';
        location.href = "<%=path%>/appWeixin/replay?code=" + code + "&uid=" + uid + "&ID_=" + ID_ + "&proc_id=" + proc_id + "&userid=" + userid;
    }

    function choice() {
        var userid = '${pd.userid}';
        var action = '${pd.action}';
        var hidcode = $("#hidcode").val();
        var oid = '${pd.oid}';
        var uid = '${pd.ouid}';
        //var code='${pd.code}';
        if(action=='deal'){
        	location.href = "<%=path%>/appWeixin/complandetail_sh?id=" + oid + "&ID_=${pd.oID_}&proc_id=${pd.oproc_id}&userid="+userid+"&cfbm=" + hidcode + "";
        }else if(action=='dis'){
        	location.href = "<%=path%>/appWeixin/distribute?id=" + oid + "&uid=" + uid + "&cfbm=" + hidcode + "&userid=" + userid;
        }
    }

    function back() {
        location.href = "<%=path%>/appWeixin/getUserid?ishandle=${pd.ishandle}";
    }
</script>

<body onload="loadWork()">
<input type="hidden" id="uid">
<input type="hidden" id="id">
<input type="hidden" id="proc_id">
<input type="hidden" id="cfbm" name="cfbm" value="${pd.cfbm }">
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
            <div style="word-break:break-word;" id="tscont">

            </div>
        </div>

        <div class="form-item">
            <label class="form-label">
                <span class="color-red">| </span>投诉类别(大项)</label>
            <div class="form-content form-date" id="tsbigtype">

            </div>
        </div>
        <div class="form-item">
            <label class="form-label">
                <span class="color-red">| </span>投诉类别(细项)</label>
            <div class="form-content form-date" id="tsclassifyname">

            </div>
        </div>
        <div class="form-item">
            <label class="form-label">
                <span class="color-red">| </span>
                投诉分类</label>
            <div class="form-content form-date" id="tstypename">

            </div>
        </div>
        <div class="form-item">
            <label class="form-label">
                <span class="color-red">| </span>
                投诉等级</label>
            <div class="form-content form-date" id="tslevelname">

            </div>
        </div>
        <div class="form-item">
            <label class="form-label">
                <span class="color-red">| </span>
                是否回访</label>
            <div class="form-content" id="ishf">

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
            <div class="form-content" id="tsman">

            </div>
        </div>
        <div class="form-item">
            <label class="form-label">
                <span class="color-red">| </span>证件类型
            </label>
            <div class="form-content" id="cardidtype">

            </div>
        </div>
        <div class="form-item">
            <label class="form-label">
                <span class="color-red">| </span>
                证件号码</label>
            <div class="form-content">
                <div class="form-input" id="cardid">
                    <input type="text" autocomplete="off" class="form-input__inner">
                </div>
            </div>
        </div>
        <div class="form-item">
            <label class="form-label">
                <span class="color-red">| </span>联系电话
            </label>
            <div class="form-content" id="tstel">

            </div>
        </div>

        <div class="form-item">
            <label class="form-label">
                <span class="color-red">| </span>邮箱
            </label>
            <div class="form-content" id="email">

            </div>
        </div>

        <div class="form-item">
            <label class="form-label">
                <span class="color-red">| </span>投诉渠道
            </label>
            <div class="form-content" id="tsqd">

            </div>
        </div>
        <div class="form-item">
            <label class="form-label">
                <span class="color-red">| </span>投诉诉求
            </label>
            <div class="form-content" id="tssq">

            </div>
        </div>
        <div class="form-item">
            <label class="form-label">
                <span class="color-red">| </span>
                乘机日期</label>
            <div class="form-content" id="cjdate">
                <div class="btn date selectPicker" id="dateResult" data-options='{"type":"date"}'>请选择航班日期
                    <div class="down-icon"></div>
                </div>
            </div>
        </div>
        <div class="form-item">
            <label class="form-label">
                <span class="color-red">| </span>
                航班号/航程</label>
            <div class="form-content" id="hbh">

            </div>
        </div>
        <div class="form-item">
            <label class="form-label">
                <span class="color-red">| </span>出发机场
            </label>
            <div class="form-content" id="deport">

            </div>
        </div>
        <div class="form-item">
            <label class="form-label">
                <span class="color-red">| </span>目的机场
            </label>
            <div class="form-content" id="arrport">

            </div>
        </div>

        <div class="blue-block">
            <i></i>
            <p>处理信息</p>
        </div>
        <div class="form-item">
            <label class="form-label">
                <span class="color-red">| </span>处理时限
            </label>
            <div class="form-content" id="clsx">

            </div>
        </div>

        <div class="form-item">
            <label class="form-label">
                <span class="color-red">| </span>
                附件</label>
            <div class="form-content">
                <span class="fr color-orange" onclick="attlist()">查看></span>
            </div>
        </div>

        <c:if test="${not empty pd.action}">
            <div class="form-block-blue" onclick="deallist()">处理记录</div>

        </c:if>


        <div class="form-item">
            <label class="form-label">
                <span class="color-red">| </span> 投诉部门</label>
            <div class="form-content form-date">
                <input type="hidden" name="tsdept" id="tsdept" value="${pd_s.tsdept}"/>
				<input type="hidden" name="oldtsdept" id="oldtsdept" />      
                <div id="tsdeptname">

                </div>
                <ul style="width:100%;display:none" id="tsdeptnamegd">
                    <c:forEach items="${areaList }" var="area">
                        <li><input type="checkbox" name="tsdept" id="tsdept" value="${area.AREA_CODE }"/> ${area.NAME }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
       <c:if test="${empty pd.ishandle||pd.ishandle!='1'}">
           <c:if test="${taskname=='单部门工单处理'}">
                <div class="form-item">
	                <label class="form-label">
	                    <span class="color-red">| </span>重复投诉编号
	                </label>
	                <div class="form-content">
	                    <div class="form-input">
	                        <input type="text" value="${pd.cfbm}"  id="newcfbm" autocomplete="off" class="form-input__inner">
	                    </div>
	                </div>
	            </div>    
 			</c:if>
       </c:if>
       
        <c:if test="${empty pd.ishandle||pd.ishandle!='1'}">
            <c:if test="${taskname=='单部门领导审批'||taskname=='部门领导审批'||taskname=='工单专员审批'}">
                <div class="form-item">
                    <label class="form-label"> <span class="color-red">| </span> 是否同意</label>
                    <div class="form-content form-date">
                        <input type="radio" name="msg" id="msg" value="yes" checked style="vertical-align:middle"> 同意
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="radio" name="msg" id="msg" value="no"style="vertical-align:middle"> 不同意
                    	<c:if test="${taskname=='工单专员审批'}">
                    		&nbsp;&nbsp;&nbsp;&nbsp;
                        	<input type="radio" name="msg" id="msg" value="retsdept" style="vertical-align:middle"> 重新选择部门
         
                        </c:if> 
                    </div>
                </div>
            </c:if>
            <c:if test="${empty pd.action}">
                <div class="form-block-blue">处理意见:</div>

                <c:if test="${taskname!='单部门领导审批'&&taskname!='部门领导审批'&&taskname!='工单专员审批'}">
                    <input type="hidden" name="msg" id="msg" value="yes"/>
                </c:if>
                <textarea autocomplete="off" id="OPINION" placeholder="请输入内容" class="form-input__inner"
                          style="min-height: 54px; height: 54px;"></textarea>
            </c:if>
        </c:if>
        <div class="wx-button-btn">
            <c:if test="${empty pd.action}">
                <c:if test="${empty pd.ishandle||pd.ishandle!='1'}">
                    <c:if test="${taskname=='单部门工单处理'}">
                        <div class="wx-button-blue" onclick="complain()"">
                            重复投诉
                        </div>
                    </c:if>
                </c:if>
                <div class="wx-button-red" onclick="deallist()">
                    处理记录
                </div>

                <c:if test="${empty pd.ishandle||pd.ishandle!='1'}">
                    <c:if test="${taskname=='单部门领导审批'||taskname=='部门领导审批'||taskname=='工单专员审批'}">
                        <div class="wx-button-blue" onclick="sh('2')">
                            审核
                        </div>
                    </c:if>
                    <c:if test="${taskname!='单部门领导审批'&&taskname!='部门领导审批'&&taskname!='工单专员审批'}">
                        <div class="wx-button-blue" onclick="sh('1')">
                            提交
                        </div>
                    </c:if>
                    <c:if test="${taskname=='工单专员审批'&&pdDoc.source=='2'}">
                        <div class="wx-button-blue"
                             onclick="replay('${pdDoc.code}','${pdDoc.uid}','${pd.ID_}','${pd.proc_id}')">
                            回复原系统
                        </div>
                    </c:if>
                </c:if>
                <c:if test="${pd.ishandle=='1'}">
                    <div class="wx-button-blue" onclick="back();">返回上一页</div>
                </c:if>
                <c:if test="${pd.ishandle!='1'}">
                    <div class="wx-button-blue" onclick="back();">返回上一页</div>
                </c:if>
            </c:if>

            <c:if test="${not empty pd.action}">
                <div class="wx-button-blue" onclick="choice()">选择</div>
            </c:if>


        </div>
    </section>
</div>

</body>

</html>