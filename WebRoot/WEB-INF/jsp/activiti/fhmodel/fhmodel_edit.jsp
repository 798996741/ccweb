<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
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
    <!-- jsp文件头和头部 -->
</head>
<body class="no-skin" onload="getLeves()">


<div class="main-container" id="main-container">
    <!-- /section:basics/sidebar -->
    <div class="main-content">
        <div class="main-content-inner">
            <div class="page-content">

                <div class="modal-header">
                    <h4 class="modal-title" id="myModalLabel" style="float: left;"></h4>
                    <div class="new-tb" style="float: right;" data-dismiss="modal" title="关闭"></div>
                </div>

                <div class="row flex-row-center-center">
                    <div class="col-xs-12">

                        <form action="fhmodel/${msg }.do" name="Form_add" id="Form_add" method="post">
                            <input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
                            <div id="zhongxin" style="padding-top: 13px;">
                                <div id="table_report" class="table table-striped table-bordered table-hover">


                                    <div class="row flex-row-center-center">

                                        <div class="col-md-6 flex-row-center-center" style="white-space: nowrap">
                                            <span class="border-size"
                                                  style="width:75px;text-align-last: justify;">模型名称</span>
                                            <input class="detail-seat-input auto-width-small" style="width: 170px"
                                                   type="text" name="modelname" id="modelname" value="${pd.modelname}"
                                                   maxlength="255" placeholder="这里输入模型名称" title="模型名称"/>
                                        </div>
                                        <%--							<div class="new-tk-body-one">--%>
                                        <%--								<div class="new-tk-body-one-wk">--%>
                                        <%--									<div class="one-wk-l">模型名称:</div>--%>
                                        <%--									<div class="one-wk-r"><input type="text" name="modelname" id="modelname" value="${pd.modelname}" maxlength="255" placeholder="这里输入模型名称" title="模型名称" /></div>--%>
                                        <%--								</div>--%>
                                        <%--								<span> </span>--%>
                                        <%--							</div>--%>

                                        <div class="col-md-6 flex-row-center-center" style="white-space: nowrap">
                                        <span class="border-size"
                                              style="width:75px;text-align-last: justify;text-align: center;">工作流分类</span>
                                            <select class="detail-seat-select auto-width-medium" style="width: 170px"
                                                    name="category" id="category">
                                            </select>
                                        </div>
                                        <%--							<div class="new-tk-body-one">--%>
                                        <%--								<div class="new-tk-body-one-wk">--%>
                                        <%--									<div class="one-wk-l">工作流分类:</div>--%>
                                        <%--									<div class="one-wk-r"><select  name="category" id="category"></select></div>--%>
                                        <%--								</div>--%>
                                        <%--								<span> </span>--%>
                                        <%--							</div>--%>
                                    </div>

                                    <div class="row flex-row-center-center">
                                        <div class="col-md-6 flex-row-center-center" style="white-space: nowrap">
                                            <span class="border-size"
                                                  style="width:75px;text-align-last: justify;">模型描述</span>
                                            <input class="detail-seat-input auto-width-small" style="width: 170px"
                                                   type="text" name="description" id="description"
                                                   value="${pd.description}" maxlength="255" placeholder="这里输入模型描述"
                                                   title="模型描述"/>
                                        </div>
                                        <%--							<div class="new-tk-body-one">--%>
                                        <%--								<div class="new-tk-body-one-wk">--%>
                                        <%--									<div class="one-wk-l">模型描述:</div>--%>
                                        <%--									<div class="one-wk-r"><input type="text" name="description" id="description" value="${pd.description}" maxlength="255" placeholder="这里输入模型描述" title="模型描述" /></div>--%>
                                        <%--								</div>--%>
                                        <%--								<span> </span>--%>
                                        <%--							</div>--%>

                                        <div class="col-md-6 flex-row-center-center" style="white-space: nowrap">
                                            <span class="border-size"
                                                  style="width:75px;text-align-last: justify;">流程名称</span>
                                            <input class="detail-seat-input auto-width-small" style="width: 170px"
                                                   type="text" name="name" id="name" value="${pd.name}" maxlength="255"
                                                   placeholder="这里输入流程名称" title="流程名称"/>
                                        </div>
                                        <%--							<div class="new-tk-body-one">--%>
                                        <%--								<div class="new-tk-body-one-wk">--%>
                                        <%--									<div class="one-wk-l">流程名称:</div>--%>
                                        <%--									<div class="one-wk-r"><input type="text" name="name" id="name" value="${pd.name}" maxlength="255" placeholder="这里输入流程名称" title="流程名称" /></div>--%>
                                        <%--								</div>--%>
                                        <%--								<span> </span>--%>
                                        <%--							</div>--%>
                                    </div>


                                    <div class="row flex-row-center-center">
                                        <div class="col-md-6 flex-row-center-center" style="white-space: nowrap">
                                        <span class="border-size"
                                              style="width:75px;text-align-last: justify;text-align: center;">流程标识</span>
                                            <select class="detail-seat-select auto-width-medium" style="width: 170px"
                                                    name="process_id" id="process_id">
                                            </select>
                                        </div>
                                        <%--							<div class="new-tk-body-one">--%>
                                        <%--								<div class="new-tk-body-one-wk">--%>
                                        <%--									<div class="one-wk-l">流程标识:</div>--%>
                                        <%--									<div class="one-wk-r"><select name="process_id" id="process_id"  /></select></div>--%>
                                        <%--								</div>--%>
                                        <%--								<span> </span>--%>
                                        <%--							</div>--%>

										<div class="col-md-6 flex-row-center-center" style="white-space: nowrap">
                                            <span class="border-size"
												  style="width:75px;text-align-last: justify;">流程标识</span>
											<input class="detail-seat-input auto-width-small" style="width: 170px"
												   type="text" name="process_id" id="process_id"
												   value="${pd.process_id}" maxlength="255"
												   placeholder="这里输入流程标识" title="流程标识"/>
											<input class="detail-seat-input auto-width-small" style="width: 170px" type="hidden" name="process_author" id="process_author"/>
										</div>
<%--                                        <div class="new-tk-body-one">--%>
<%--                                            <div class="new-tk-body-one-wk">--%>
<%--                                                <div class="one-wk-l">流程标识:</div>--%>
<%--                                                <div class="one-wk-r">--%>

<%--                                                    <input type="text" name="process_id" id="process_id"--%>
<%--                                                           value="${pd.process_id}" maxlength="255"--%>
<%--                                                           placeholder="这里输入流程标识" title="流程标识"/>--%>
<%--                                                    <input type="hidden" name="process_author" id="process_author"/>--%>
<%--                                                </div>--%>
<%--                                            </div>--%>
<%--                                            <span> </span>--%>
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


<!--提示框-->
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<script type="text/javascript">

    function getLeves() {

    }

    $.ajax({
        type: "POST",
        url: '<%=basePath%>dictionaries/getLevels.do?tm=' + new Date().getTime(),
        data: {DICTIONARIES_ID: 'act001'},//act001 为工作流分类
        dataType: 'json',
        cache: false,
        success: function (data) {
            $("#category").html('<option value="" >请选择分类</option>');
            $.each(data.list, function (i, dvar) {
                $("#category").append("<option value=" + dvar.BIANMA + ">" + dvar.NAME + "</option>");
            });
        }
    });
    //为流程标识
    $.ajax({
        type: "POST",
        url: '<%=basePath%>dictionaries/getLevels.do?tm=' + new Date().getTime(),
        data: {DICTIONARIES_ID: 'act002'},//act002为流程标识
        dataType: 'json',
        cache: false,
        success: function (data) {
            $("#process_id").html('<option value="" >请选择流程标识</option>');
            $.each(data.list, function (i, dvar) {
                $("#process_id").append("<option value=" + dvar.BIANMA + ">" + dvar.NAME + "(" + dvar.BIANMA + ")" + "</option>");
            });
        }
    });

    //保存
    function save() {
        if ($("#modelname").val() == "") {
            $("#modelname").tips({
                side: 3,
                msg: '请输入模型名称',
                bg: '#AE81FF',
                time: 2
            });
            $("#modelname").focus();
            return false;
        }
        if ($("#category").val() == "") {
            $("#category").tips({
                side: 3,
                msg: '请输入模型分类',
                bg: '#AE81FF',
                time: 2
            });
            $("#category").focus();
            return false;
        }
        if ($("#description").val() == "") {
            $("#description").tips({
                side: 3,
                msg: '请输入模型描述',
                bg: '#AE81FF',
                time: 2
            });
            $("#description").focus();
            return false;
        }
        if ($("#name").val() == "") {
            $("#name").tips({
                side: 3,
                msg: '请输入流程名称',
                bg: '#AE81FF',
                time: 2
            });
            $("#name").focus();
            return false;
        }
        if ($("#process_id").val() == "") {
            $("#process_id").tips({
                side: 3,
                msg: '请选择流程标识',
                bg: '#AE81FF',
                time: 2
            });
            $("#process_id").focus();
            return false;
        }

        //$("#Form").submit();
        //$("#zhongxin").hide();
        //$("#zhongxin2").show();

        $.ajax({
            //几个参数需要注意一下
            type: "POST",//方法类型
            dataType: "html",//预期服务器返回的数据类型
            url: "fhmodel/${msg}.do",//url
            data: $('#Form_add').serialize(),
            success: function (result) {
                //  console.log(result.);//打印服务端返回的数据(调试用)
                if (result.indexOf("success") >= 0) {
                    location.href = "<%=path%>/fhmodel/list.do";
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