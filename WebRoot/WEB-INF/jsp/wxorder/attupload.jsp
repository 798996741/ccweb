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
    <script src="<%=path%>/static/weixin/js/jquery.form.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/weixin/css/mui.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/weixin/css/mui.picker.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/weixin/css/mui.poppicker.css" rel="stylesheet"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/weixin/css/common.css"/>
    <script src="<%=path%>/static/weixin/js/mui.min.js"></script>
    <script src="<%=path%>/static/weixin/js/mui.picker.min.js"></script>
    <script src="<%=path%>/static/weixin/js/mui.poppicker.js"></script>
</head>

<script type="text/javascript">

    function cl(proc_id, ID_) {
        var userid = '${userid}';
        location.href = "<%=path%>/appWeixin/complandetail_sh?ID_=" + ID_ + "&proc_id=" + proc_id + "&userid=" + userid;
    }
</script>

<body>
<div>
    <form action="<%=path %>/appuser/workorderFileUpload" name="Form" id="Form_upload" method="post">
        <div class="form-block">请输入附件名称</div>
        <div>
            <input type="hidden" id="uid" name="uid" value="${pd.uid }">
            <input type="hidden" id="replay" name="replay" value="${pd.replay }">
            <input type="hidden" id="userid" name="userid" value="${pd.userid }">
            <input type="text" id="filenames" name="filenames" autocomplete="off" class="form-input__inner">
        </div>
        <div class="form-block">选择文件</div>
        <div style="display: flex;flex-direction: row;justify-content: center;align-items: center;background-color: #fff;box-shadow: 4px 4px 6px #c7c7c7;">
            <input type="file" name="file" id="files">
        </div>
    </form>
    <div class="wx-button-btn">
        <div id="bottom-upload" class="wx-button-blue" onclick="saveFile()">保存</div>
        <div id="bottom-upload-wait" style="display:none" class="wx-button-blue">上传中请稍后...</div>
        <div class="wx-button-blue" onclick="javascript:history.go(-1);">返回上一页</div>
    </div>
</div>

<script type="text/javascript">

    function saveFile() {

        //alert('${types}');
        if ($("#filenames").val() == '') {
            mui.alert("请输入附件名称！");
            return false;
        }
        if ($("#files").val() == '') {
            mui.alert("请选择要上传的文件！");
            return false;
        }
        $("#bottom-upload").hide();
        $("#bottom-upload-wait").show();
        //return false;
        var options = {
            success: function (json) {

                var uid = $("#uid").val();
                var userid = $("#userid").val();
                var proc_id = '${pd.proc_id}';
                var ID_ = '${pd.ID_}';
                var id = '${pd.id}';
                var action = '${pd.action}';
                var ishandle = '${pd.ishandle}';
                var code = '${pd.code}';
                if ('${pd.replay}' != '1') {
                    location.href = "<%=path%>/appWeixin/attlist?action=" + ishandle + "&action=" + action + "&ID_=" + ID_ + "&id=" + id + "&proc_id=" + proc_id + "&uid=" + uid + "&userid=" + userid;
                } else {
                    location.href = "<%=path%>/appWeixin/replay?code=" + code + "&uid=" + uid + "&ID_=" + ID_ + "&proc_id=" + proc_id + "&userid=" + userid;
                }

            }
        };
        $("#Form_upload").ajaxSubmit(options);
    }
</script>
</body>

</html>