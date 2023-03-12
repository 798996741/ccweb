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
<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <%@ include file="../../system/include/incJs_mx.jsp" %>

</head>
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
                <div class="seat-middle-top-left-bt">知识分类管理</div>
                <div class="seat-middle-top-left-tp">
                    <c:if test="${QX.add == 1 }">
                        <a href="javascript:void (0)" onclick="add('${pd.id}');">新增</a>
                    </c:if>
                </div>
            </div>
            <div class="seat-middle-top-right">
                <input placeholder="搜  索" name="keywords" id="keywords"
                       value="${keywords }"> <a href="javascript:void(0)"> <img
                    src="static/login/images/icon-search.png" onclick="tosearch()"></a>
            </div>
        </div>
        <div class="seat-middle">
            <!--  <div class="seat-middle-nr"> -->

            <div class="xtyh-middle-r zxzgl-middle-r">
                <!-- <div class="box-body" > -->

                <table id="example_dic" class="table table-bordered table-hover">
                    <thead>
                    <tr>

                        <th class="center" style="width:50px;">序号</th>
                        <th class="center">分类名称</th>
                        <th class="center">排序</th>
                        <th class="center">状态</th>
                        <th class="center">操作人</th>
                        <th class="center">操作时间</th>
                        <th class="center">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <!-- 开始循环 -->
                    <c:choose>
                        <c:when test="${not empty varList}">
                            <c:if test="${QX.cha == 1 }">
                                <c:forEach items="${varList}" var="var" varStatus="vs">
                                    <tr id="tr${vs.index+1}">
                                        <td class='center' style="width: 30px;">${vs.index+1}</td>
                                        <td class='center'><a href="javascript:;"><i
                                                class="ace-icon fa fa-share bigger-100"></i>&nbsp;${var.name}</a></td>
                                        <td class='center'><a href="javascript:;')">${var.sort}</a></td>
                                        <td class='center'><c:if test="${var.state =='1' }">
                                            启用
                                        </c:if> <c:if test="${var.state =='0'||empty var.state}">
                                            禁用
                                        </c:if></td>
                                        <td class='center'>${var.createman}</td>
                                        <td class='center'>${var.createdate}</td>
                                        <td class="center">
                                            <div class="flex-position">
                                                <div class="flex-row">
                                                    <c:if test="${QX.edit != 1 && QX.del != 1 }">
														<span
                                                                class="label label-large label-grey arrowed-in-right arrowed-in"><i
                                                                class="ace-icon fa fa-lock" title="无权限"></i></span>
                                                    </c:if>
                                                    <c:if test="${QX.edit == 1 }">
                                                        <div class="button-edit" title="编辑"
                                                             onclick="edit('${var.id}');" title="编辑">
                                                            <font class="button-content">编辑</font>
                                                        </div>
                                                        <%--                                                <a class="cy_bj" title="编辑" onclick="edit('${var.id}');">--%>
                                                        <%--                                                    <i title="编辑"></i>--%>
                                                        <%--                                                </a>--%>
                                                    </c:if> <c:if test="${QX.del == 1  and not empty var.parentid}">
                                                    <div class="button-delete" style="margin-left:10px;"
                                                         title="删除"
                                                         onclick="del('${var.id}');" title="删除">
                                                        <font class="button-content">删除</font>
                                                    </div>
                                                    <%--                                            <a style="margin-left:10px;" class="cy_sc"--%>
                                                    <%--                                               onclick="del('${var.id}');"> <i title="删除"></i>--%>
                                                    <%--                                            </a>--%>
                                                </c:if></div>
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

<!-- /.content-wrapper -->
<!-- basic scripts -->
<!-- 页面底部js¨ -->
<!-- 删除时确认窗口 -->
<script src="static/ace/js/bootbox.js"></script>
<!-- ace scripts -->
<script src="static/ace/js/ace/ace.js"></script>
<!-- 下拉框 -->
<script src="static/ace/js/chosen.jquery.js"></script>
<!-- 日期框 -->
<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
<!--提示框-->
<script type="text/javascript" src="static/js/jquery.tips.js"></script>


<script type="text/javascript">
    //$(top.hangge());//关闭加载状态
    //检索
    function tosearch() {
        var keywords = $("#keywords").val();
        //alert(${id});
        location.href = "<%=path%>/doctype/list.do?id=${id}&keywords=" + encodeURI(encodeURI(keywords));
    }

    $(function () {


    });

    //去此ID下子列表
    function goSondict(id) {
        top.jzts();
        window.location.href = "<%=basePath%>doctype/list.do?id=" + id;
    };

    //新增
    function add(id) {
        //alert(id);
        if (id == "") {
            id == "0";
        }
        var winId = "userWin";
        modals.openWin({
            winId: winId,
            title: '新增',
            width: '900px',
            height: '350px',
            url: '<%=basePath%>doctype/goAdd.do?id=' + id
            /*, hideFunc:function(){
                modals.info("hide me");
            },
            showFunc:function(){
                modals.info("show me");
            } */
        });


    }

    //删除
    function del(Id) {
        if (Id == "ce174640544549f1b31c8f66e01c111b") {
            alert("坐席分组不允许修改");
            return false;
        }
        bootbox.confirm("确定要删除吗?", function (result) {
            if (result) {
                var url = "<%=basePath%>doctype/delete.do?id=" + Id + "&tm=" + new Date().getTime();
                $.get(url, function (data) {

                    if ("success" == data.result) {
                        parent.location.href = "<%=basePath%>doctype/listAllDict.do?id=${id}&dnowPage=${page.currentPage}";
                    } else if ("false" == data.result) {
                        //top.hangge();
                        bootbox.dialog({
                            message: "<span class='bigger-110'>删除失败！请先删除子级或删除占用资源.</span>",
                            buttons:
                                {
                                    "button":
                                        {
                                            "label": "确定",
                                            "className": "btn-sm btn-success"
                                        }
                                }
                        });
                    } else if ("false2" == data.result) {
                        //top.hangge();
                        bootbox.dialog({
                            message: "<span class='bigger-110'>删除失败！排查表不存在或其表中没有BIANMA字段.</span>",
                            buttons:
                                {
                                    "button":
                                        {
                                            "label": "确定",
                                            "className": "btn-sm btn-success"
                                        }
                                }
                        });
                    }
                });
            }
        });
    }

    //修改
    function edit(Id) {
        var winId = "userWin";
        modals.openWin({
            winId: winId,
            title: '编辑',
            width: '900px',
            height: '350px',
            url: '<%=basePath%>doctype/goEdit.do?id=' + Id
            /*, hideFunc:function(){
                modals.info("hide me");
            },
            showFunc:function(){
                modals.info("show me");
            } */
        });
    }
</script>
<script>
    $(function () {

        $('#example_dic').DataTable({
            'paging': true,
            'lengthChange': false,
            'searching': true,
            'ordering': true,
            'info': true,
            'autoWidth': true
        })
    })
    $(function () {
        $(".row thead").find("th:first").addClass("cy_th");
        $(".row tbody").find("tr").find("td:first").addClass("cy_td");
        $(".row thead").find("th:first").after("<th id='cy_thk'></th>");
        $(".row tbody").find("tr").find("td:first").after("<td id='cy_thk'></td>");
        $('.seat-middle').find(".row:first").hide();

        $(".xtyh-middle-r").find(".row:last").addClass("row-zg");
        $(".xtyh-middle-r").find(".row:eq(1)").addClass("row-two");
    });

    function home() {
        parent.parent.document.getElementById("mainFrame").src = "<%=basePath%>login_default.do";
    }

</script>