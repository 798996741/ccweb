<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <!-- 下拉框 -->
    <!-- jsp文件头和头部 -->
    <%--
    <%@ include file="../index/top.jsp"%> --%>
    <%@ include file="../../system/include/incJs_mx.jsp" %>
    <style>
        .seat-middle-top {
            margin-top: 0;
        }
    </style>
<body class="no-skin">


<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper" style="width:100%;margin-left:0px;">
    <section class="container-fluid">
        <div class="seat-middle-top-x"></div>
        <div class="seat-middle-top">
            <div class="seat-middle-top-left">
                <div class="seat-middle-top-left-bt"><c:if test="${pd.action!='search' }">评分</c:if></div>
<%--                <div class="seat-middle-top-left-tp">--%>
<%--                        <a href="javascript:void (0)" onclick="add();">新增</a>--%>
<%--                </div>--%>
            </div>
<%--            <div class="seat-middle-top-right">--%>
<%--                <input placeholder="搜  索" name="keywords" id="keywords" value="${pd.keywords }">--%>
<%--                <a href="javascript:void(0)">--%>
<%--                    <img src="static/login/images/icon-search.png" onclick="tosearch()"></a>--%>
<%--            </div>--%>
        </div>
        <div class="seat-middle">
            <!--  <div class="seat-middle-nr"> -->

            <div class="xtyh-middle-r zxzgl-middle-r">
                <!-- <div class="box-body" > -->

                <table id="example2" class="table table-bordered table-hover">
                    <thead>
                    <tr>

                        <th class="center">编号</th>
                        <th class="center">星星个数</th>
                        <th class="center">问题是否解决</th>
                        <th class="center">建议</th>
                        <th class="center">操作</th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:choose>
                        <c:when test="${not empty valuate}">
                            <c:forEach items="${valuate}" var="var" varStatus="vs">

                                <tr>

                                    <td class="center">${vs.count }</td>
                                    <td class="center">${var.start }</td>
                                    <td class="center">${var.issolve }</td>
                                    <td class="center">${var.suggest }</td>
                                    <td class="center">

<%--                                        <div class="flex-position">--%>
<%--                                            <div class="flex-row">--%>
<%--                                                <div class="button-edit" title="编辑"--%>
<%--                                                     onclick="edit('${var.id}');" title="编辑">--%>
<%--                                                    <font class="button-content">编辑</font>--%>
<%--                                                </div>--%>

<%--                                                <div class="button-delete" style="margin-left:10px;"--%>
<%--                                                     title="删除"--%>
<%--                                                     onclick="del('${var.id }');" title="删除">--%>
<%--                                                    <font class="button-content">删除</font>--%>
<%--                                                </div>--%>
<%--                                            </div>--%>
<%--                                        </div>--%>

                                    </td>
                                </tr>

                            </c:forEach>

                        </c:when>
                        <c:otherwise>
                            <tr class="main_info">
                                <td colspan="10" class="center">没有相关数据</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>

                    </tbody>

                </table>


            </div>
        </div>
    </section>

    <!-- /.content -->
</div>

<!-- /.main-container -->

<!-- basic scripts -->
<!-- 页面底部js¨ -->
<!-- 删除时确认窗口 -->
<script src="static/ace/js/bootbox.js"></script>
<!-- ace scripts -->
<script src="static/ace/js/ace/ace.js"></script>
<!-- 下拉框 -->
<script src="static/ace/js/chosen.jquery.js"></script>
<!--提示框-->
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<script type="text/javascript">
    //$(top.hangge());//关闭加载状态
    //检索
    function tosearch() {
        var keywords = $("#keywords").val();
        location.href = "<%=path%>/doc/list.do?action=${pd.action}&doctype_id=${pd.doctype_id}&keywords=" + encodeURI(encodeURI(keywords));
        //$("#Form_s").submit();
    }

    $(function () {
        $('#example2').DataTable({
            'paging': true,
            'lengthChange': false,
            'searching': true,
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
            width: '900px',
            height: '400px',
            url: '<%=basePath%>oftenword/goWordAdd.do'

        });

    }

    //删除
    function del(Id) {
        bootbox.confirm("确定要删除吗?", function (result) {
            if (result) {
                top.jzts();
                var url = "<%=basePath%>oftenword/deleteWord.do?id=" + Id ;
                $.get(url, function (data) {
                    location.href = "<%=path%>/oftenword/oftenwordlist.do";
                });
            }
        });
    }

    //修改
    function edit(Id) {

        var winId = "userWin";
        modals.openWin({
            winId: winId,
            title: '新增',
            width: '900px',
            height: '400px',
            url: '<%=basePath%>oftenword/findWordById.do?id=' + Id
            /*, hideFunc:function(){
                modals.info("hide me");
            },
            showFunc:function(){
                modals.info("show me");
            } */
        });


    }

    function docsearch(id) {

        var winId = "userWin";
        modals.openWin({
            winId: winId,
            title: '查看',
            width: '900px',
            height: '400px',
            url: '<%=basePath%>doc/goEdit.do?action=search&id=' + id
            , hideFunc: function () {
                tosearch();
            },
        });

    }


    $(function () {

        $('.seat-middle').find(".row:first").hide();

        $(".xtyh-middle-r").find(".row:last").addClass("row-zg");
        $(".xtyh-middle-r").find(".row:eq(1)").addClass("row-two");
    });
</script>
</body>
</html>