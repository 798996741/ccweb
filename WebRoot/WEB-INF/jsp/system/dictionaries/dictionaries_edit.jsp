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
                <div class="row flex-row-center-center">
                    <div class="col-xs-12">

                        <form action="dictionaries/${msg }.do" name="Form" id="Form_add" method="post">
                            <input type="hidden" name="DICTIONARIES_ID" id="DICTIONARIES_ID"
                                   value="${pd.DICTIONARIES_ID}"/>

                            <c:if test="${msg=='edit' }">
                                <input type="hidden" name="PARENT_ID" id="PARENT_ID"
                                       value="${null == pd.PARENT_ID ?pd.DICTIONARIES_ID:pd.PARENT_ID}"/>
                            </c:if>
                            <c:if test="${msg=='save' }">
                                <input type="hidden" name="PARENT_ID" id="PARENT_ID"
                                       value="${null == DICTIONARIES_ID ? '0':DICTIONARIES_ID}"/>
                            </c:if>
                            <div id="zhongxin">
                                <div id="table_report" class="table table-striped table-bordered table-hover flex-column">

									<div class="row flex-row-center-center">上级:<b>${null == pds.NAME ?'(无) 此项为顶级':pds.NAME}</b></div>
<%--                                    <div class="new-block">--%>
<%--                                        上级:<b>${null == pds.NAME ?'(无) 此项为顶级':pds.NAME}</b>--%>
<%--                                    </div>--%>


                                    <div class="row flex-row-center-center">

										<div class="col-md-6 flex-row-acenter" style="white-space: nowrap">
											<span class="border-size" style="width:70px;text-align-last: justify;">名称</span>
											<input class="detail-seat-input auto-width-small" style="width: 170px" type="text" name="NAME" id="NAME"
												   value="${pd.NAME}" maxlength="50"
												   placeholder="这里输入名称" title="名称"/>
										</div>
<%--                                        <div class="new-tk-body-one">--%>
<%--                                            <div class="new-tk-body-one-wk">--%>
<%--                                                <div class="one-wk-l">名称:</div>--%>
<%--                                                <div class="one-wk-r"><input type="text" name="NAME" id="NAME"--%>
<%--                                                                             value="${pd.NAME}" maxlength="50"--%>
<%--                                                                             placeholder="这里输入名称" title="名称"/></div>--%>
<%--                                            </div>--%>
<%--                                            <span> </span>--%>
<%--                                        </div>--%>



										<div class="col-md-6 flex-row-acenter" style="white-space: nowrap">
											<span class="border-size" style="width:70px;text-align-last: justify;"><c:if test="${DICTIONARIES_ID=='6b272abc46a34105ac98bb03dd71a549' or pd.PARENT_ID=='6b272abc46a34105ac98bb03dd71a549'}">字段</c:if>
                                                    <c:if test="${DICTIONARIES_ID!='6b272abc46a34105ac98bb03dd71a549' and pd.PARENT_ID!='6b272abc46a34105ac98bb03dd71a549'}">英文</c:if></span>
											<input class="detail-seat-input auto-width-small" style="width: 170px" type="text" name="NAME_EN" id="NAME_EN"
												   value="${pd.NAME_EN}" maxlength="50"
												   placeholder="这里输入英文" title="英文"/>
										</div>
<%--                                        <div class="new-tk-body-one">--%>
										<%--                                            <div class="new-tk-body-one-wk">--%>
										<%--                                                <div class="one-wk-l">--%>
										<%--                                                    <c:if test="${DICTIONARIES_ID=='6b272abc46a34105ac98bb03dd71a549' or pd.PARENT_ID=='6b272abc46a34105ac98bb03dd71a549'}">字段:</c:if>--%>
										<%--                                                    <c:if test="${DICTIONARIES_ID!='6b272abc46a34105ac98bb03dd71a549' and pd.PARENT_ID!='6b272abc46a34105ac98bb03dd71a549'}">英文:</c:if>--%>
										<%--                                                </div>--%>
										<%--                                                <div class="one-wk-r"><input type="text" name="NAME_EN" id="NAME_EN"--%>
										<%--                                                                             value="${pd.NAME_EN}" maxlength="50"--%>
										<%--                                                                             placeholder="这里输入英文" title="英文"/></div>--%>
										<%--                                            </div>--%>
										<%--                                            <span> </span>--%>
										<%--                                        </div>--%>
                                    </div>
                                    <%--
                                    <tr>
                                        <td style="width:110px;text-align: right;padding-top: 13px;">

                                            <c:if test="${DICTIONARIES_ID=='6b272abc46a34105ac98bb03dd71a549' or pd.PARENT_ID=='6b272abc46a34105ac98bb03dd71a549'}">字段:</c:if>
                                            <c:if test="${DICTIONARIES_ID!='6b272abc46a34105ac98bb03dd71a549' and pd.PARENT_ID!='6b272abc46a34105ac98bb03dd71a549'}">英文:</c:if>

                                        </td>
                                        <td><input type="text" name="NAME_EN" id="NAME_EN" value="${pd.NAME_EN}" maxlength="50" placeholder="这里输入英文" title="英文" style="width:98%;"/></td>
                                    </tr> --%>

                                    <div class="row flex-row-center-center">

										<div class="col-md-6 flex-row-acenter" style="white-space: nowrap">
											<span class="border-size" style="width:70px;text-align-last: justify;">编码</span>
											<input class="detail-seat-input auto-width-small" style="width: 170px" type="text" name="BIANMA" id="BIANMA"
												   value="${pd.BIANMA}" maxlength="32"
												   placeholder="这里输入编码 (不重复, 禁止修改)" title="编码"
												   <c:if test="${null != pd.BIANMA}">readonly="readonly"</c:if>
												   <c:if test="${null == pd.BIANMA}">onblur="hasBianma();"</c:if>/>
										</div>
<%--                                        <div class="new-tk-body-one">--%>
<%--                                            <div class="new-tk-body-one-wk">--%>
<%--                                                <div class="one-wk-l">编码:</div>--%>
<%--                                                <div class="one-wk-r"><input type="text" name="BIANMA" id="BIANMA"--%>
<%--                                                                             value="${pd.BIANMA}" maxlength="32"--%>
<%--                                                                             placeholder="这里输入编码 (不重复, 禁止修改)" title="编码"--%>
<%--                                                                             <c:if test="${null != pd.BIANMA}">readonly="readonly"</c:if>--%>
<%--                                                                             <c:if test="${null == pd.BIANMA}">onblur="hasBianma();"</c:if> />--%>
<%--                                                </div>--%>
<%--                                            </div>--%>
<%--                                            <span> </span>--%>
<%--                                        </div>--%>

                                        <%-- <tr>
                                            <td style="width:110px;text-align: right;padding-top: 13px;">编码:</td>
                                            <td><input type="text" name="BIANMA" id="BIANMA" value="${pd.BIANMA}" maxlength="32" placeholder="这里输入编码 (不重复, 禁止修改)" title="编码" style="width:76%;" <c:if test="${null != pd.BIANMA}">readonly="readonly"</c:if> <c:if test="${null == pd.BIANMA}">onblur="hasBianma();"</c:if> /></td>
                                        </tr> --%>


										<div class="col-md-6 flex-row-acenter" style="white-space: nowrap">
											<span class="border-size" style="width:70px;text-align-last: justify;">排序</span>
											<input class="detail-seat-input auto-width-small" style="width: 170px" type="number" name="ORDER_BY" id="ORDER_BY"
												   value="${pd.ORDER_BY}" maxlength="32"
												   placeholder="这里输入排序" title="排序"/>
										</div>
<%--                                        <div class="new-tk-body-one">--%>
<%--                                            <div class="new-tk-body-one-wk">--%>
<%--                                                <div class="one-wk-l">排序:</div>--%>
<%--                                                <div class="one-wk-r"><input type="number" name="ORDER_BY" id="ORDER_BY"--%>
<%--                                                                             value="${pd.ORDER_BY}" maxlength="32"--%>
<%--                                                                             placeholder="这里输入排序" title="排序"/></div>--%>
<%--                                            </div>--%>
<%--                                            <span> </span>--%>
<%--                                        </div>--%>
                                    </div>

                                    <div class="row flex-row-center-center">

										<div class="col-md-6 flex-row-acenter" style="white-space: nowrap">
											<span class="border-size" style="width:70px;text-align-last: justify;"><c:if test="${DICTIONARIES_ID=='6b272abc46a34105ac98bb03dd71a549' or pd.PARENT_ID=='6b272abc46a34105ac98bb03dd71a549'}">单位</c:if>
                                                    <c:if test="${DICTIONARIES_ID!='6b272abc46a34105ac98bb03dd71a549' and pd.PARENT_ID!='6b272abc46a34105ac98bb03dd71a549'}">备注</c:if></span>
											<input class="detail-seat-input auto-width-small" style="width: 170px" type="text" name="BZ" id="BZ"
												   placeholder="这里输入备注" title="备注"
												   value="${pd.BZ}"/>
										</div>
<%--                                        <div class="new-tk-body-one">--%>
<%--                                            <div class="new-tk-body-one-wk">--%>
<%--                                                <div class="one-wk-l">--%>
<%--                                                    <c:if test="${DICTIONARIES_ID=='6b272abc46a34105ac98bb03dd71a549' or pd.PARENT_ID=='6b272abc46a34105ac98bb03dd71a549'}">单位:</c:if>--%>
<%--                                                    <c:if test="${DICTIONARIES_ID!='6b272abc46a34105ac98bb03dd71a549' and pd.PARENT_ID!='6b272abc46a34105ac98bb03dd71a549'}">备注:</c:if>--%>
<%--                                                </div>--%>
<%--                                                <div class="one-wk-r"><input type="text" name="BZ" id="BZ"--%>
<%--                                                                             placeholder="这里输入备注" title="备注"--%>
<%--                                                                             value="${pd.BZ}"></div>--%>
<%--                                            </div>--%>
<%--                                            <span> </span>--%>
<%--                                        </div>--%>

                                        <%-- <tr>
                                            <td style="width:110px;text-align: right;padding-top: 13px;">

                                                <c:if test="${DICTIONARIES_ID=='6b272abc46a34105ac98bb03dd71a549' or pd.PARENT_ID=='6b272abc46a34105ac98bb03dd71a549'}">单位:</c:if>
                                                <c:if test="${DICTIONARIES_ID!='6b272abc46a34105ac98bb03dd71a549' and pd.PARENT_ID!='6b272abc46a34105ac98bb03dd71a549'}">备注:</c:if>


                                            </td>
                                            <td>
                                                <input type="text" name="BZ" id="BZ" placeholder="这里输入备注" title="备注"  style="width:98%;" value="${pd.BZ}">
                                            </td>
                                        </tr> --%>


										<div class="col-md-6 flex-row-acenter" style="white-space: nowrap">
											<span class="border-size" style="width:70px;text-align-last: justify;">排查表</span>
											<input class="detail-seat-input auto-width-small" style="width: 170px" type="text" name="TBSNAME" id="TBSNAME"
												   value="${pd.TBSNAME}" maxlength="100"
												   placeholder="这里输入表名, 多个用逗号分隔(非必录)"
												   title="排查表"/>
										</div>
<%--                                        <div class="new-tk-body-one">--%>
<%--                                            <div class="new-tk-body-one-wk">--%>
<%--                                                <div class="one-wk-l">排查表:</div>--%>
<%--                                                <div class="one-wk-r"><input type="text" name="TBSNAME" id="TBSNAME"--%>
<%--                                                                             value="${pd.TBSNAME}" maxlength="100"--%>
<%--                                                                             placeholder="这里输入表名, 多个用逗号分隔(非必录)"--%>
<%--                                                                             title="排查表"/></div>--%>
<%--                                            </div>--%>
<%--                                            <span> </span>--%>
<%--                                        </div>--%>
                                    </div>

                                    <div class="row flex-row-center"><p>排查表：删除此条数据时会去此表查询是否被占用(是:禁止删除)</p></div>
<%--                                    <div class="new-block">--%>
<%--                                        <p>排查表：删除此条数据时会去此表查询是否被占用(是:禁止删除)</p>--%>
<%--                                    </div>--%>


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

                        <div id="zhongxin2" class="center" style="display:none"><img src="static/images/jzx.gif"
                                                                                     style="width: 50px;"/><br/><h4
                                class="lighter block green"></h4></div>
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


<!--提示框-->
<script type="text/javascript">
    //$(top.hangge());
    //保存
    function save() {
        if ($("#NAME").val() == "") {
            $("#NAME").tips({
                side: 3,
                msg: '请输入名称',
                bg: '#AE81FF',
                time: 2
            });
            $("#NAME").focus();
            return false;
        }
        if ($("#NAME_EN").val() == "") {
            $("#NAME_EN").tips({
                side: 3,
                msg: '请输入英文',
                bg: '#AE81FF',
                time: 2
            });
            $("#NAME_EN").focus();
            return false;
        }
        if ($("#BIANMA").val() == "") {
            $("#BIANMA").tips({
                side: 3,
                msg: '请输入编码',
                bg: '#AE81FF',
                time: 2
            });
            $("#BIANMA").focus();
            return false;
        }
        if ($("#ORDER_BY").val() == "") {
            $("#ORDER_BY").tips({
                side: 3,
                msg: '请输入数字',
                bg: '#AE81FF',
                time: 2
            });
            $("#ORDER_BY").focus();
            return false;
        }

        //$("#Form_add").submit();
        $("#zhongxin").hide();
        $("#zhongxin2").show();
        $.ajax({
            //几个参数需要注意一下
            type: "POST",//方法类型
            dataType: "html",//预期服务器返回的数据类型
            url: "dictionaries/${msg }.do",//url
            data: $('#Form_add').serialize(),
            success: function (result) {
                //  console.log(result.);//打印服务端返回的数据(调试用)
                if (result.indexOf("success") >= 0) {
                    parent.location.href = "<%=basePath%>dictionaries/listAllDict.do?DICTIONARIES_ID=${DICTIONARIES_ID}";
                }
            },
            error: function () {
                alert("异常！");
            }
        });


    }

    //判断编码是否存在
    function hasBianma() {
        var BIANMA = $.trim($("#BIANMA").val());
        if ("" == BIANMA) return;
        $.ajax({
            type: "POST",
            url: '<%=basePath%>dictionaries/hasBianma.do',
            data: {BIANMA: BIANMA, tm: new Date().getTime()},
            dataType: 'json',
            cache: false,
            success: function (data) {
                if ("success" == data.result) {
                } else {
                    $("#BIANMA").tips({
                        side: 1,
                        msg: '编码' + BIANMA + '已存在,重新输入',
                        bg: '#AE81FF',
                        time: 5
                    });
                    $('#BIANMA').val('');
                }
            }
        });
    }
</script>
</body>
</html>