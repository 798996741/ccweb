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
                <div class="seat-middle-top-left-bt"><c:if test="${pd.action!='search' }">知识库管理</c:if><c:if
                        test="${pd.action=='search' }">知识库检索</c:if></div>
                <div class="seat-middle-top-left-tp">
                    <c:if test="${pd.action!='search' }">
                        <a href="javascript:void (0)" onclick="add();">新增</a>
                    </c:if>
                </div>
            </div>
            <div class="seat-middle-top-right">
                <input placeholder="搜  索" name="keywords" id="keywords" value="${pd.keywords }">
                <a href="javascript:void(0)">
                    <img src="static/login/images/icon-search.png" onclick="tosearch()"></a>
            </div>
        </div>
        <div class="seat-middle">
            <!--  <div class="seat-middle-nr"> -->
            <div class="xtyh-middle-r zxzgl-middle-r">
                <!-- <div class="box-body" > -->
                <table id="example2"
                       class="table table-striped table-bordered table-hover"
                       style="margin-top:5px;">
                    <thead>
                    <tr>
                        <th class="center cy_th" style="width:30px;">序号</th>
                        <th id='cy_thk'></th>
                        <th class="center">分类名称</th>
                        <th class="center">标题</th>
                        <th class="center">来源</th>
                        <th class="center">作者</th>
                        <c:if test="${pd.action!='search' }">
                            <th class="center">知识状态</th>
                            <th class="center">创建人</th>
                            <th class="center">创建时间</th>
                            <th class="center">最后点击时间</th>
                            <th class="center">最后修改时间</th>
                            <th class="center" style="width:50px;">操作</th>
                        </c:if>
                        <c:if test="${pd.action=='search' }">
                            <th class="center">阅读数</th>
                            <th class="center">评论数</th>
                            <th class="center">最后点击时间</th>
                            <th class="center" style="width:50px;">查看</th>
                        </c:if>

                    </tr>
                    </thead>

                    <tbody>
                    <!-- 开始循环 -->
                    <c:choose>
                        <c:when test="${not empty varList}">
                                <c:forEach items="${varList}" var="var" varStatus="vs">
                                    <tr>

                                        <td class='center cy_td' style="width: 30px;">${vs.index+1}</td>
                                        <td id='cy_thk'></td>
                                        <td class='center'>${var.doctypename}</td>
                                        <td class='center'>${var.doctitle}</td>
                                        <td class='center'>${var.docsource}</td>
                                        <td class='center'>${var.docauthor}</td>

                                        <c:if test="${pd.action!='search' }">
                                            <td class='center'>
                                                <c:if test="${var.doctype == 0 or empty var.doctype}">
                                                    待审核
                                                </c:if>
                                                <c:if test="${var.doctype ==1}">
                                                    已激活
                                                </c:if>
                                                <c:if test="${var.doctype ==2}">
                                                    已归档
                                                </c:if>
                                            </td>

                                            <td class='center'></td>
                                            <td class='center'>
                                                    ${var.createdate}
                                            </td>
                                            <td class='center'>${var.lastclickdate}</td>
                                            <td class='center'>${var.lastmoddate}</td>
                                        </c:if>
                                        <c:if test="${pd.action=='search' }">
                                            <td class='center'>${var.clicknum}</td>
                                            <td class='center'>${var.plnum}</td>
                                            <td class='center'>${var.lastclickdate}</td>
                                        </c:if>
                                        <td class="center">
                                            <c:if test="${QX.edit != 1 && QX.del != 1 }">
																<span
                                                                        class="label label-large label-grey arrowed-in-right arrowed-in"><i
                                                                        class="ace-icon fa fa-lock"
                                                                        title="无权限"></i></span>
                                            </c:if>
                                            <div class="flex-position">
                                                <div class="flex-row">
                                                    <c:if test="${pd.action!='search'  }">

                                                        <div class="button-edit" title="编辑"
                                                             onclick="edit('${var.id}');" title="编辑">
                                                            <font class="button-content">编辑</font>
                                                        </div>
                                                        <%--																	<a class="cy_bj" title="编辑"--%>
                                                        <%--																		onclick="edit('${var.id}');"> <i--%>
                                                        <%--																		--%>
                                                        <%--																		title="编辑"></i>--%>
                                                        <%--																	</a>--%>
                                                    </c:if>
                                                    <c:if test="${QX.del == 1 &&pd.action!='search' }">
                                                        <div class="button-delete" style="margin-left:10px;"
                                                             title="删除"
                                                             onclick="del('${var.id}');" title="删除">
                                                            <font class="button-content">删除</font>
                                                        </div>
                                                        <%--																	<a style="margin-left:10px;" class="cy_sc"--%>
                                                        <%--																		onclick="del('${var.id}');"> <i--%>
                                                        <%--																		 title="删除"></i>--%>
                                                        <%--																	</a>--%>
                                                    </c:if>

                                                    <c:if test="${pd.action=='search'  }">
                                                        <div class="button-edit" title="查看"
                                                             onclick="docsearch('${var.id}');" title="查看">
                                                            <font class="button-content">查看</font>
                                                        </div>
                                                        <%--																	<a class="cy_bj" title="查看"--%>
                                                        <%--																		onclick="docsearch('${var.id}');"> --%>
                                                        <%--																		<i title="查看"></i>--%>
                                                        <%--																	</a>--%>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>

                                </c:forEach>
                           
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
            url: '<%=basePath%>doc/goAdd.do'

        });

    }

    //删除
    function del(Id, Uid) {
        bootbox.confirm("确定要删除吗?", function (result) {
            if (result) {
                top.jzts();
                var url = "<%=basePath%>doc/delete.do?id=" + Id + "&UID=" + Uid + "&tm=" + new Date().getTime();
                $.get(url, function (data) {
                    location.href = "<%=path%>/doc/list.do";
                });
            }
        });
    }

    //修改
    function edit(Id, Uid) {

        var winId = "userWin";
        modals.openWin({
            winId: winId,
            title: '新增',
            width: '900px',
            height: '400px',
            url: '<%=basePath%>doc/goEdit.do?UID="+Uid+"&id=' + Id
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


    //导出excel
    function toExcel() {
        window.location.href = '<%=basePath%>doc/excel.do';
    }

    $(function () {

        $('.seat-middle').find(".row:first").hide();

        $(".xtyh-middle-r").find(".row:last").addClass("row-zg");
        $(".xtyh-middle-r").find(".row:eq(1)").addClass("row-two");
    });
</script>
</body>
</html>