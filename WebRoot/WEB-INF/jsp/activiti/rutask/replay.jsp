<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    String userid = request.getParameter("userid");
    String uid = request.getParameter("uid");
    String proc_id = request.getParameter("proc_id");
%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8"/>
    <base href="<%=basePath%>">
    <%@ include file="../../system/include/incJs_mx.jsp" %>
</head>

<script type="text/javascript">
    function loadWork() {
        var userid = '${sessionUser.USERNAME}';
        var uid = '${pd.uid}';
        var data = "{\"api\":\"com.yulun.WorkorderWeb\",\"data\":{\"username\":\"" + userid + "\",\"uid\":\"" + uid + "\"},\"cmd\":\"WorkorderFilelist\"}";
        //alert(data);
        $.ajax({
            type: 'post',
            // url: "http://127.0.0.1:8080/ylxxxtnew/sys/sypzpreAction!getPz.xhtml?endpoint_id=1023&module=home&r="+Math.random(),
            url: "<%=path%>/api",
            dataType: 'json',
            data: {"data": data}, //参数值
            cache: false,
            success: function (obj) {
                var str = "";
                var data = eval(obj.data);
                var work = eval(obj.work);
                if (obj.success == "true") {
                    //alert(data.length);
                    $("#cptemphasis").val(work.cptemphasis);
                    $("#surveyprocess").val(work.surveyprocess);
                    $("#isreconciliation").val(work.isreconciliation);
                    $("#disposeending").val(work.disposeending);

                }


            }, error: function (jqXHR, textStatus, errorThrown) {
                alert(jqXHR.responseText);
                return false;
            }
        });
    }


    function msgalsert(val, msg) {
        if (val != "") {
            layer.alert(msg);
            return false;
        }
    }

    function replay() {
        //alert(pid);
        var userid = '${sessionUser.USERNAME}';
        var code = '${pd.code}';
        var uid = '${pd.uid}';
        //var fileIds=getCheckBoxValueThree();
        var cptemphasis = $("#cptemphasis").val();
        if (cptemphasis == "") {
            layer.alert("请输入投诉事由及述求");
            return false;
        }
        var surveyprocess = $("#surveyprocess").val();
        if (surveyprocess == "") {
            layer.alert("请输入确定的基本事实与证据");
            return false;
        }
        var isreconciliation = $("#isreconciliation").val();
        if (isreconciliation == "") {
            layer.alert("请输入调查结果、企业责任以及是否与消费者达成和解");
            return false;
        }
        var disposeending = $("#disposeending").val();
        if (disposeending == "") {
            layer.alert("请输入给消费者的实质性回复（消费者可查询）");
            return false;
        }
        var data = "{\"api\":\"com.yulun.WorkorderWeb\",\"data\":{\"cptemphasis\":\"" + cptemphasis + "\",\"username\":\"" + userid + "\",\"surveyprocess\":\"" + surveyprocess + "\",\"isreconciliation\":\"" + isreconciliation + "\",\"disposeending\":\"" + disposeending + "\",\"code\":\"" + code + "\",\"uid\":\"" + uid + "\"},\"cmd\":\"WorkorderReplay\"}";

        $.ajax({
            //几个参数需要注意一下
            type: 'post',
            // url: "http://127.0.0.1:8080/ylxxxtnew/sys/sypzpreAction!getPz.xhtml?endpoint_id=1023&module=home&r="+Math.random(),
            url: "<%=path%>/api",
            dataType: 'json',
            data: {"data": data}, //参数值
            success: function (result) {
                if (result.success == "true") {
                	layer.alert("回复成功");
                    formClose();
                } else {
                	layer.alert("回复失败");
                }
            },
            error: function () {
            	layer.alert("异常！");
            }
        });

    }

</script>

<body onload="loadWork()">

<%--<input type="hidden" id="uid" name="uid">--%>
<%--<input type="hidden" id="id" name="id">--%>
<div class="main-container" id="main-container">
    <div class="main-content">
        <div class="row" style="margin-top: 10px">
            <div class="col-md-12 flex-row-acenter" style="white-space: nowrap;">
                <span class="border-size"
                      style="width:230px;text-align-last: right;display: inline-block;">投诉函转发编号</span>
                <input class="detail-seat-input"
                       value=" ${pd.code }"/>
            </div>
          
        </div>
        <div class="row">
            <div class="col-md-12 flex-row-acenter" style="white-space: nowrap">
                <span class="border-size"
                      style="width:230px;text-align-last: right;display: inline-block;">投诉事由及述求</span>
                <input class="detail-seat-input"
                       type="text" autocomplete="off" id="cptemphasis" name="cptemphasis"/>
            </div>
          
        </div>
        <div class="row">
            <div class="col-md-12 flex-row-acenter" style="white-space: nowrap">
                <span class="border-size"
                      style="width:230px;text-align-last: right;display: inline-block;">确定的基本事实与证据</span>
                <input class="detail-seat-input"
                       type="text" autocomplete="off" id="surveyprocess" name="surveyprocess"/>
            </div>
            
        </div>
        <div class="row">
            <div class="col-md-12 flex-row-acenter" style="white-space: nowrap">
                <span class="border-size"
                      style="width:230px;text-align-last: right;display: inline-block;">调查结果、企业责任以及<br/>是否与消费者达成和解</span>
                <input class="detail-seat-input"
                       type="text" autocomplete="off" id="isreconciliation" name="isreconciliation"/>
            </div>
           
        </div>
        <div class="row">
            <div class="col-md-12 flex-row-acenter" style="white-space: nowrap">
                <span class="border-size"
                      style="width:230px;text-align-last: right;display: inline-block;">给消费者的实质性回复<br>（消费者可查询）</span>
                <input class="detail-seat-input"
                       type="text" autocomplete="off" id="disposeending" name="disposeending"/>
            </div>
            
        </div>
       
        <div class="new-bc" style="padding: 10px">
            <a onclick="replay()">提交</a>
            <a class="new-qxbut" data-btn-type="cancel" data-dismiss="modal" onclick="formClose()">取消</a>
        </div>
    </div>
</div>
</div>
<%@ include file="../../system/include/incJs_foot.jsp" %>
<script>
        window.formClose = function(){
            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            parent.layer.close(index); //再执行关闭
        }
</script>
</body>

</html>