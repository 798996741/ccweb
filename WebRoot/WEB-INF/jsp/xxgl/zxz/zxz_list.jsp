<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";%>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <!-- jsp文件头和头部 -->
    <%@ include file="../../system/include/incJs_mx.jsp" %>
    <style>
        @media screen and (max-width: 1700px) {
            .xtyh-middle-r {
                width: 100% !important;
            }
        }

        .zxzgl-middle-r {
            width: 100% !important;
        }
    </style>
</head>
<body class="no-skin">
<div class="content-wrapper" style="width:100%;margin-left:0px;">
    <section class="container-fluid">
        <div class="seat-middle-top" style="box-shadow: 4px 4px 6px #c7c7c7;">
            <div class="seat-middle-top-left">
                <div class="seat-middle-top-left-bt">坐席组</div>
                <div class="seat-button">
                    <c:if test="${QX.add == 1 }">
                        <a href="javascript:void (0)" onclick="add();">新增</a>
                    </c:if>
                </div>
            </div>
            <div class="seat-middle-top-right">
                <input placeholder="搜  索" name="keywords" id="keywords"
                       value="${pd.keywords }"> <a href="javascript:void(0)">
                <img src="static/login/images/icon-search.png"
                     onclick="tosearch()">
            </a>
            </div>
        </div>
        <div class="seat-middle">
            <div class="xtyh-middle-r zxzgl-middle-r">
                <table id="example2"
                       class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <!-- <th class="center" style="width:35px;"><label
                                    class="pos-rel"><input type="checkbox" class="ace"
                                        id="zcheckbox" /><span class="lbl"></span></label></th> -->
                        <th class="center cy_th" style="width:50px;">序号</th>
                        <th id='cy_thk'></th>
                        <th class="center">分组名称</th>
                        <th class="center">分组编码</th>
                        <th class="center">组长</th>
                        <th class="center">组技能</th>
                        <th class="center">备注</th>
                        <th class="center">操作</th>
                    </tr>
                    </thead>

                    <tbody>
                    <!-- 开始循环 -->
                    <c:choose>
                        <c:when test="${not empty varList}">
                            <c:if test="${QX.cha == 1 }">
                                <c:forEach items="${varList}" var="var" varStatus="vs">
                                    <tr>

                                        <td class='center cy_td' style="width: 30px;">${vs.index+1}</td>
                                        <td id='cy_thk'></td>
                                        <td class='center'>${var.ZMC}</td>
                                        <td class='center'>${var.ZBH}</td>
                                        <td class='center'>${var.GLR}</td>
                                        <td class='center'>${var.ZJNNAME}</td>
                                        <td class='center'>${var.BZ}</td>
                                        <td class="center"><c:if
                                                test="${QX.edit != 1 && QX.del != 1 }">
														<span
                                                                class="label label-large label-grey arrowed-in-right arrowed-in"><i
                                                                class="ace-icon fa fa-lock" title="无权限"></i></span>
                                        </c:if>
                                            <div class="hidden-sm hidden-xs btn-group flex-row-center-center">
                                                <c:if test="${QX.edit == 1 }">
                                                    <div class="content-edit home-img cy_bj" title="编辑"
                                                         onclick="edit('${var.ID}');">
                                                        <font class="home-img-size">编辑</font>
                                                    </div>
                                                    <%--															<a class="cy_bj" title="编辑" onclick="edit('${var.ID}');">--%>
                                                    <%--																<i title="编辑"></i>--%>
                                                    <%--															</a>--%>
                                                </c:if>
                                                <c:if test="${QX.del == 1 }">
                                                    <div class="content-delete home-img cy_bj" title="删除"
                                                         onclick="del('${var.ID}');">
                                                        <font class="home-img-size">删除</font>
                                                    </div>
                                                    <%--															<a style="margin-left:10px;" class="cy_sc"--%>
                                                    <%--																onclick="del('${var.ID}');"> <i title="删除"></i>--%>
                                                    <%--															</a>--%>
                                                </c:if>
                                            </div>
                                        </td>
                                    </tr>

                                </c:forEach>
                            </c:if>
                            <c:if test="${QX.cha == 0 }">
                                <tr>
                                    <td colspan="100" class="center">您无权查看</td>
                                </tr>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <tr class="main_info">
                                <td colspan="100" class="center">没有相关数据</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                    </tbody>
                </table>
            </div>
        </div>
    </section>
</div>
<!-- 页面底部js¨ -->
<%@ include file="../../system/include/incJs_foot.jsp" %>
<!-- 日期框 -->
<script type="text/javascript">
    //$(top.hangge());//关闭加载状态
    //检索
    function tosearch() {
        var keywords = $("#keywords").val();
        location.href = "<%=path%>/zxz/list.do?keywords=" + encodeURI(encodeURI(keywords));
        //$("#Form_s").submit();
    }

    $(function () {
        $('#example2').DataTable({
            'paging': true,
            'lengthChange': false,
            'searching': false,
            'ordering': true,
            'info': true,
            'autoWidth': true
        })
    });

    //新增
    function add() {
        var winId = "userWin";
        modals.openWin({
            winId: winId,
            title: '新增',
            width: '700px',
            url: '<%=basePath%>zxz/goAdd.do'
        });
    }

    //删除
    function del(Id, Uid) {
        bootbox.confirm("确定要删除吗?", function (result) {
            if (result) {
                top.jzts();
                var url = "<%=basePath%>zxz/delete.do?ID=" + Id + "&UID=" + Uid + "&tm=" + new Date().getTime();
                $.get(url, function (data) {
                    location.href = "<%=path%>/zxz/list.do";
                });
            }
        });
    }

    //修改
    function edit(Id, Uid) {
        var winId = "userWin";
        modals.openWin({
            winId: winId,
            title: '编辑',
            width: '700px',
            url: '<%=basePath%>zxz/goEdit.do?UID="+Uid+"&ID=' + Id
            /*, hideFunc:function(){
                modals.info("hide me");
            },
            showFunc:function(){
                modals.info("show me");
            } */
        });
    }

    //导出excel
    function toExcel() {
        window.location.href = '<%=basePath%>zxz/excel.do';
    }

    $(function () {
        $('.seat-middle').find(".row:first").hide();
        $(".xtyh-middle-r").find(".row:last").addClass("row-zg");
        $(".xtyh-middle-r").find(".row:eq(1)").addClass("row-two");
    });
</script>
</body>
</html>