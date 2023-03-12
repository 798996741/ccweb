<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <!-- 日期框 -->
    <link rel="stylesheet" href="static/ace/css/datepicker.css"/>
    <!-- 日期框 -->
    <link rel="stylesheet" href="plugins/ueditor/themes/default/css/ueditor.css"/>
</head>
<body class="no-skin">
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
    <!-- /section:basics/sidebar -->
    <div class="main-content">
        <div class="main-content-inner">
            <div class="page-content">
                <div class="modal-header">
                    <h4 class="modal-title" id="myModalLabel" style="float: left;"></h4>
                    <div class="new-tb" style="float: right;" data-dismiss="modal" title="关闭"></div>
                </div>

                <div class="row">
                    <div class="col-xs-12">

                        <form action="zxz/${msg }.do" name="Form_add" id="Form_add" method="post">
                            <input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
                            <div id="zhongxin" style="padding-top: 13px;">
                                <div id="table_report" class="table table-striped table-bordered table-hover">

                                    <div class="row" style="margin-top: 10px">
                                        <div class="col-md-6 flex-row-center-center" style="white-space: nowrap">
                                            <span class="border-size"
                                                  style="width:70px;text-align-last: justify;">分组名称</span>
                                            <input class="detail-seat-input auto-width-medium" style="width: 170px;"
                                                   type="text" name="ZMC" id="ZMC"
                                                   value="${pd.ZMC}" maxlength="255"
                                                   placeholder="这里输入分组名称" title="分组名称"/>
                                        </div>
                                        <%--                                        <div class="new-tk-body-one">--%>
                                        <%--                                            <div class="new-tk-body-one-wk">--%>
                                        <%--                                                <div class="one-wk-l">分组名称:</div>--%>
                                        <%--                                                <div class="one-wk-r"><input type="text" name="ZMC" id="ZMC"--%>
                                        <%--                                                                             value="${pd.ZMC}" maxlength="255"--%>
                                        <%--                                                                             placeholder="这里输入分组名称" title="分组名称"/></div>--%>
                                        <%--                                            </div>--%>
                                        <%--                                            <span> </span>--%>
                                        <%--                                        </div>--%>


                                        <div class="col-md-6 flex-row-center-center" style="white-space: nowrap">
                                            <span class="border-size"
                                                  style="width:70px;text-align-last: justify;">分组编码</span>
                                            <input class="detail-seat-input auto-width-medium" style="width: 170px;"
                                                   type="text" name="ZBH" id="ZBH"
                                                   value="${pd.ZBH}" maxlength="255"
                                                   placeholder="这里输入分组编码" title="分组编码"/>
                                        </div>
                                        <%--                                        <div class="new-tk-body-one">--%>
                                        <%--                                            <div class="new-tk-body-one-wk">--%>
                                        <%--                                                <div class="one-wk-l">分组编码:</div>--%>
                                        <%--                                                <div class="one-wk-r"><input type="text" name="ZBH" id="ZBH"--%>
                                        <%--                                                                             value="${pd.ZBH}" maxlength="255"--%>
                                        <%--                                                                             placeholder="这里输入分组编码" title="分组编码"/></div>--%>
                                        <%--                                            </div>--%>
                                        <%--                                            <span> </span>--%>
                                        <%--                                        </div>--%>
                                    </div>

                                    <div class="row">

                                        <div class="col-md-6 flex-row-center-center" style="white-space: nowrap">
                                            <span class="border-size"
                                                  style="width:70px;text-align-last: justify;">组长</span>
                                            <input class="detail-seat-input auto-width-medium" style="width: 170px;"
                                                   type="text" name="GLR" id="GLR"
                                                   value="${pd.GLR}" maxlength="255"
                                                   placeholder="这里输入组长" title="组长"/>
                                        </div>
                                        <%--                                        <div class="new-tk-body-one">--%>
                                        <%--                                            <div class="new-tk-body-one-wk">--%>
                                        <%--                                                <div class="one-wk-l">组长:</div>--%>
                                        <%--                                                <div class="one-wk-r"><input type="text" name="GLR" id="GLR"--%>
                                        <%--                                                                             value="${pd.GLR}" maxlength="255"--%>
                                        <%--                                                                             placeholder="这里输入组长" title="组长"/></div>--%>
                                        <%--                                            </div>--%>
                                        <%--                                            <span> </span>--%>
                                        <%--                                        </div>--%>


                                        <div class="col-md-6 flex-row-center-center" style="white-space: nowrap">
                                            <span class="border-size"
                                                  style="width:70px;text-align-last: justify;">备注</span>
                                            <input class="detail-seat-input auto-width-medium" style="width: 170px;"
                                                   type="text" name="BZ" id="BZ"
                                                   value="${pd.BZ}" maxlength="255"
                                                   placeholder="这里输入备注" title="备注"/>
                                        </div>
                                        <%--                                        <div class="new-tk-body-one">--%>
                                        <%--                                            <div class="new-tk-body-one-wk">--%>
                                        <%--                                                <div class="one-wk-l">备注:</div>--%>
                                        <%--                                                <div class="one-wk-r"><input type="text" name="BZ" id="BZ"--%>
                                        <%--                                                                             value="${pd.BZ}" maxlength="255"--%>
                                        <%--                                                                             placeholder="这里输入备注" title="备注"/></div>--%>
                                        <%--                                            </div>--%>
                                        <%--                                            <span> </span>--%>
                                        <%--                                        </div>--%>
                                    </div>


                                    <div class="row">

                                        <div class="col-md-6 flex-row-center-center" style="white-space: nowrap;height: 35px;line-height: 35px">
                                            <span class="border-size"
                                                  style="width:70px;text-align-last: justify;">组技能</span>
                                            <input class="detail-seat-input auto-width-medium" style="width: 170px;"
                                                   type="hidden" name="ZJN" id="ZJN" value="${pd.ZJN }"/>
                                            <div style="display:flex;flex-wrap:wrap;width: 190px;align-items: center;justify-content: space-around;">
                                                <c:forEach items="${dictList}" var="var" varStatus="vs">
                                                    <div class="flex-row-center-center">
                                                        <input style="vertical-align: middle;margin: 0 !important;cursor: pointer"
                                                               name="ZJNCHECK"
                                                               value="${var.DICTIONARIES_ID}" ${var.checked}
                                                               type="checkbox"
                                                               id="ZJNCHECK">${var.NAME}
                                                    </div>
                                                </c:forEach>
                                            </div>
                                        </div>
                                        <%--                                        <div class="new-left " style="width:100%">--%>

                                        <%--                                            <div class="one-wk-l">组技能:</div>--%>
                                        <%--                                            <div class="new-table-nr" style="width:100%">--%>
                                        <%--                                                <input type="hidden" name="ZJN" id="ZJN" value="${pd.ZJN }">--%>
                                        <%--                                                <c:forEach items="${dictList}" var="var" varStatus="vs">--%>
                                        <%--                                                    <input style="vertical-align: middle;" name="ZJNCHECK"--%>
                                        <%--                                                           value="${var.DICTIONARIES_ID}" ${var.checked} type="checkbox"--%>
                                        <%--                                                           id="ZJNCHECK">&nbsp;&nbsp;${var.NAME}--%>

                                        <%--                                                </c:forEach>--%>
                                        <%--                                            </div>--%>


                                        <%--                                        </div>--%>
                                    </div>


                                    <div class="new-bc" style="padding: 10px">
                                        <a onclick="save();">保存</a>
                                        <a class="new-qxbut" data-btn-type="cancel" data-dismiss="modal">取消</a>
                                    </div>


                                </div>
                            </div>
                            <div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img
                                    src="static/images/jiazai.gif"/><br/><h4 class="lighter block green">提交中...</h4>
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

<!-- /.main-container -->
<!-- 日期框 -->
<script type="text/javascript" charset="utf-8">window.UEDITOR_HOME_URL = "<%=path%>/plugins/ueditor/";</script>
<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.all.js"></script>


<script type="text/javascript">
    //$(top.hangge());
    //保存
    var ue = UE.getEditor("DETAIL");
    $(function () {
        //日期框
        //$('.date-picker').datepicker({autoclose: true,todayHighlight: true});


    });


    function save() {
        if ($("#ZMC").val() == "") {
            $("#ZMC").tips({
                side: 3,
                msg: '请输入分组名称',
                bg: '#AE81FF',
                time: 2
            });
            $("#ZMC").focus();
            return false;
        }
        if ($("#ZBH").val() == "") {
            $("#ZBH").tips({
                side: 3,
                msg: '请输入分组编码',
                bg: '#AE81FF',
                time: 2
            });
            $("#ZBH").focus();
            return false;
        }
        if ($("#GLR").val() == "") {
            $("#GLR").tips({
                side: 3,
                msg: '请输入组长',
                bg: '#AE81FF',
                time: 2
            });
            $("#GLR").focus();
            return false;
        }

        var ZJN = "";
        var conditions = document.getElementsByName("ZJNCHECK");
        //alert(conditions);

        for (var i = 0; i < conditions.length; i++) {
            if (conditions[i].checked == true) {
                if (ZJN != "") {
                    ZJN = ZJN + ",";
                }
                ZJN = ZJN + conditions[i].value;
            }
        }
        // $("#ZXZ").val(ZXZ);
        //alert(ZXZ);
        if (ZJN == "") {
            alert("请选择组技能");
            return false;
        } else {
            $("#ZJN").val(ZJN);
        }


        $("#zhongxin").hide();
        $("#zhongxin2").show();
        $.ajax({
            //几个参数需要注意一下
            type: "POST",//方法类型
            dataType: "html",//预期服务器返回的数据类型
            url: "zxz/${msg }.do",//url
            data: $('#Form_add').serialize(),
            success: function (result) {
                // alert(result);//打印服务端返回的数据(调试用)
                if (result.indexOf("success") >= 0) {
                    location.href = "<%=basePath%>zxz/list.do";
                } else {

                }

            },
            error: function () {
                alert("异常！");
            }
        });
    }


</script>
</body>
</html>