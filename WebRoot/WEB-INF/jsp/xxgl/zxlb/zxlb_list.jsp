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

    <%@ include file="../../system/include/incJs_mx.jsp" %>
    <style>

    </style>
</head>
<body class="no-skin">


<div class="content-wrapper" style="width:100%;margin-left:0px;">
    <section class="container-fluid">
        <div class="seat-middle-top" style="box-shadow: 4px 4px 6px #c7c7c7;">
            <div class="seat-middle-top-left">
                <div class="seat-middle-top-left-bt">坐席人员</div>
                <div class="seat-button">
                    <c:if test="${QX.add == 1 }">
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
                    <tr style="white-space: nowrap">
                        <th class="center cy_th" style="width:30px;">序号</th>
                        <th id='cy_thk'></th>
                        <th class="center">所属单位</th>
                        <th class="center">角色</th>
                        <th class="center">坐席ID</th>
                        <th class="center">坐席角色</th>
                        <th class="center">坐席姓名</th>
                        <th class="center">分机号码</th>
                        <th class="center">坐席状态</th>
                      	<th class="center">坐席分组</th>
                        <th class="center">坐席类型</th>

                        <th class="center">坐席工号</th>
                      <!--   <th class="center">系统用户</th> -->
                        <th class="center">登记日期</th>

                        <th class="center" style="width:50px;">操作</th>
                    </tr>
                    </thead>

                    <tbody>
                    <!-- 开始循环 -->
                    <c:choose>
                        <c:when test="${not empty varList}">
                            <c:if test="${QX.cha == 1 }">
                                <c:forEach items="${varList}" var="var" varStatus="vs">
                                    <tr style="white-space: nowrap">

                                        <td class='center cy_td' style="width: 30px;">${vs.index+1}</td>
                                        <td id='cy_thk'></td>

                                        <td class='center'>${var.areaname}</td>
                                        <td class='center'>${var.ROLE_NAME}</td>
                                        <td class='center'>${var.ZXID}</td>
                                        <td class='center'>${var.ZXJSNAME}</td>
                                        <td class='center'>${var.ZXXM}</td>
                                        <td class='center'>${var.FJHM}</td>
                                        <td class='center'>
                                            <c:if test="${var.ZT == 0}">
                                                正常
                                            </c:if>
                                            <c:if test="${var.ZT ==-1}">
                                                冻结
                                            </c:if>
                                        </td>
                                       	<td class='center'>${var.ZMC}</td>
                                        <td class='center'>
                                                ${var.ZXLXNAME}
                                        </td>
                                        <td class='center'>${var.ZXGH}</td>
                                        <%-- <td class='center'>
                                        	<c:if test="${not empty var.ZXYH}">
                                        		${var.ZXYH}(${var.NAME})
                                        	</c:if>
                                        </td> --%>

                                        <td class='center'>${var.DJRQ}</td>

                                        <td class="center">
                                            <c:if test="${QX.edit != 1 && QX.del != 1 }">
																<span
                                                                        class="label label-large label-grey arrowed-in-right arrowed-in"><i
                                                                        class="ace-icon fa fa-lock"
                                                                        title="无权限"></i></span>
                                            </c:if>
                                            <div class="hidden-sm hidden-xs flex-position btn-group">
                                                <div class="flex-row">
                                                    <c:if test="${QX.edit == 1 }">
                                                        <div class="button-edit" title="编辑"
                                                             onclick="edit('${var.ID}');" title="编辑">
                                                            <font class="button-content">编辑</font>
                                                        </div>
                                                        <%--                                                    <a class="cy_bj" title="编辑" &ndash;%&gt;--%>
                                                        <%--                                                       onclick="edit('${var.ID}');"> <i--%>
                                                        <%--                                                            title="编辑"></i>--%>
                                                        <%--                                                    </a>--%>
                                                    </c:if>
                                                    <c:if test="${QX.del == 1 }">
                                                        <div class="button-delete" style="margin-left:10px;"
                                                             title="删除"
                                                             onclick="del('${var.ID}');" title="删除">
                                                            <font class="button-content">删除</font>
                                                        </div>
                                                        <%--                                                        <a style="margin-left:10px;" class="cy_sc"--%>
                                                        <%--                                                           onclick="del('${var.ID}');"> <i--%>
                                                        <%--                                                                title="删除"></i>--%>
                                                        <%--                                                        </a>--%>
                                                    </c:if>
                                                </div>
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
    <%-- <section class="content">
        <div class="row">
            <div class="col-xs-12">
                <div class="box">
                    <div class="box-header" >
                        <c:if test="${QX.add == 1 }">
                            <a class="btn btn-mini btn-success" onclick="add();">新增</a>
                        </c:if>
                    </div>
                    <div class="box-body" >

                        <table id="example2"
                            class="table table-striped table-bordered table-hover"
                            style="margin-top:5px;">
                            <thead>
                                <tr>
                                    <th class="center" style="width:30px;">序号</th>
                                    <th class="center">登记日期</th>
                                    <th class="center">坐席分机</th>
                                    <th class="center">坐席ID</th>
                                    <th class="center">坐席日期</th>
                                    <th class="center">坐席姓名</th>
                                    <th class="center">状态 </th>
                                    <th class="center">坐席分组</th>
                                    <th class="center">坐席类型</th>
                                    <th class="center">坐席用户</th>
                                    <th class="center">坐席工号</th>
                                    <th class="center" style="width:50px;">操作</th>
                                </tr>
                            </thead>

                            <tbody>
                                <!-- 开始循环 -->
                                <c:choose>
                                    <c:when test="${not empty varList}">
                                        <c:if test="${QX.cha == 1 }">
                                            <c:forEach items="${varList}" var="var" varStatus="vs">
                                                <tr>

                                                    <td class='center' style="width: 30px;">${vs.index+1}</td>
                                                    <td class='center'>${var.DJRQ}</td>
                                                    <td class='center'>${var.FJHM}</td>
                                                    <td class='center'>${var.ZXID}</td>
                                                    <td class='center'>${var.ZXRQ}</td>
                                                    <td class='center'>${var.ZXXM}</td>
                                                    <td class='center'>
                                                        <c:if test="${var.ZT == 0}">
                                                            正常
                                                        </c:if>
                                                        <c:if test="${var.ZT ==-1}">
                                                            冻结
                                                        </c:if>
                                                    </td>
                                                    <td class='center'>${var.ZMC}</td>
                                                    <td class='center'>
                                                        ${var.ZXLXNAME}
                                                    </td>
                                                    <td class='center'>${var.ZXYH}(${var.NAME})</td>
                                                    <td class='center'>${var.ZXGH}</td>
                                                    <td class="center">
                                                        <c:if test="${QX.edit != 1 && QX.del != 1 }">
                                                            <span
                                                                class="label label-large label-grey arrowed-in-right arrowed-in"><i
                                                                class="ace-icon fa fa-lock" title="无权限"></i></span>
                                                        </c:if>
                                                        <div class="hidden-sm hidden-xs btn-group">
                                                            <c:if test="${QX.edit == 1 }">
                                                                <a class="btn btn-xs btn-success" title="编辑"
                                                                    onclick="edit('${var.ID}');"> <i
                                                                    class="ace-icon fa fa-pencil-square-o bigger-120"
                                                                    title="编辑"></i>
                                                                </a>
                                                            </c:if>
                                                            <c:if test="${QX.del == 1 }">
                                                                <a style="margin-left:10px;" class="btn btn-xs btn-danger"
                                                                    onclick="del('${var.ID}');"> <i
                                                                    class="ace-icon fa fa-trash-o bigger-120" title="删除"></i>
                                                                </a>
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

                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->

                </div>
                <!-- /.col -->
            </div>
            <!-- /.row -->
    </section> --%>
    <!-- /.content -->
</div>

<!-- /.main-container -->

<!-- basic scripts -->
<!-- 页面底部js¨ -->
<%@ include file="../../system/include/incJs_foot.jsp" %>
    <!-- jquery.tips.js -->
<script type="text/javascript">
    //$(top.hangge());//关闭加载状态
    //检索
    function tosearch() {
        var keywords = $("#keywords").val();
        location.href = "<%=path%>/zxlb/list.do?keywords=" + encodeURI(encodeURI(keywords));
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
            width: '700px',
            url: '<%=basePath%>zxlb/goAdd.do'

        });

    }

    //删除
    function del(Id, Uid) {
        bootbox.confirm("确定要删除吗?", function (result) {
            if (result) {
                top.jzts();
                var url = "<%=basePath%>zxlb/delete.do?ID=" + Id + "&UID=" + Uid + "&tm=" + new Date().getTime();
                $.get(url, function (data) {
                    location.href = "<%=path%>/zxlb/list.do";
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
            url: '<%=basePath%>zxlb/goEdit.do?UID="+Uid+"&ID=' + Id
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
        window.location.href = '<%=basePath%>zxlb/excel.do';
    }

    $(function () {

        $('.seat-middle').find(".row:first").hide();

        $(".xtyh-middle-r").find(".row:last").addClass("row-zg");
        $(".xtyh-middle-r").find(".row:eq(1)").addClass("row-two");
    });
</script>
</body>
</html>