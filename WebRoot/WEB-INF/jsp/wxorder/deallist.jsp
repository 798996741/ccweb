<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>

<head>
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
</head>

<script type="text/javascript">
    function loadWork() {
        var userid = '${pd.userid}';
        var proc_id = '${pd.proc_id}';
        var ID_ = '${pd.ID_}';
        
        var data = "{\"api\":\"com.yulun.WorkorderWeb\",\"data\":{\"tokenid\":\"3d0fd800244f48fd9d648724c099528\",\"code\":\"\",\"starttime\":\"\",\"endtime\":\"\",\"tsman\":\"\",\"tssource\":\"\",\"tsdept\":\"\",\"tstype\":\"\",\"pageIndex\":\"1\",\"pageSize\":\"10\",\"doaction\":\"0\",\"username\":\"" + userid + "\",\"proc_id\":\"" + proc_id + "\",\"ID_\":\"" + ID_ + "\"},\"cmd\":\"WorkorderFindById\"}";
        //alert(data);
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
                    //alert(data.length);
                    console.log(obj);
                    var clList = eval(data.clList);
                    for (var o in clList) {
                        str = str + "<tr>";
                        str = str + "<td><div class=\"cell\">" + clList[o].areaname + "</div></td>";
                        str = str + "<td><div class=\"cell\">" + clList[o].USERNAME + "</div></td>";
                        str = str + "<td><div class=\"cell\">" + clList[o].cldate + "</div></td>";

                        str = str + "<td><div class=\"cell\">" + clList[o].clcont + "</div></td>";

                        str = str + "</tr>";
                    }
                }

                $("#dbtable tbody").html("");
                if (str == "") {
                    str = "<tr><td colspan=\"4\">暂无数据</td></tr>";
                    $("#dbtable").find('tbody').append(str);
                } else {
                    $("#dbtable").find('tbody').append(str);
                }

            }, error: function (jqXHR, textStatus, errorThrown) {
                alert(jqXHR.responseText);
                return false;
            }
        });
    }

</script>

<body onload="loadWork()">
<div class="ada-table ada-table-bluebg">
    <table cellspacing="0" id="dbtable" style="width:100%" cellpadding="0" border="0" class="ada-table__header">

        <thead class="has-gutter">
        <tr>
            <th style="width:25%">
                <div class="cell">处理部门</div>
            </th>
            <th style="width:25%">
                <div class="cell">处理人</div>
            </th>
            <th style="width:25%">
                <div class="cell">处理时间</div>
            </th>
            <th style="width:35%">
                <div class="cell">处理记录</div>
            </th>
        </tr>
        </thead>
        <tbody>

        </tbody>
    </table>
    <div class="wx-button-btn">
        <div class="wx-button-blue" onclick="javascript:history.go(-1);">返回上一页</div>
    </div>
</div>
</body>

</html>