<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.fh.util.Jurisdiction" %>
<%
    Jurisdiction jurisdiction = new Jurisdiction();
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <%@ include file="../system/include/incJs_mx.jsp" %>

</head>
<style>
    .seat-middle-top {
        margin-top: 0;
    }
    .table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th, .table>thead>tr>td, .table>thead>tr>th{
        padding: 0;
    }
    .border-style{
    	margin:5px;
    }
</style>
<body class="no-skin">


<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper" style="width:100%;margin-left:0px;">
    <section class="container-fluid">
                <div class="seat-middle-top" style="box-shadow: 4px 4px 6px #c7c7c7;">
          <div class="seat-middle-top-left">
                <div class="seat-middle-top-left-bt"><a style="width: 60px">单位管理</a></div>
                <div class="seat-button">
                    <%if (jurisdiction.hasQx("990501")) { %>
                    <a class="" onclick="add('${AREA_ID}');" style="margin-left: 0px;margin-top:5px">新增</a>
                    <%} %>
                </div>
                
                <div class="border-style">
                    <span class="border-size">部门编码</span>
                    <div class="border-line"></div>
                    <select class="seat-select width-110px" id="area_codes" name="area_codes">
                        <option value="">全部</option>
                        <c:forEach items="${varLists}" var="vars" >
	                        <option value="${vars.AREA_CODE }" <c:if test="${pd.area_codes==vars.AREA_CODE}">selected</c:if>>
	                           ${vars.AREA_CODE }-${vars.NAME }
	                        </option>
                        </c:forEach>
                       
                    </select>
                </div>
                <div class="border-style">
                    <span class="border-size">部门名称</span>
                    <div class="border-line"></div>
                    <input class="seat-input  width-110px" placeholder="部门名称" name="area_names" id="area_names"
                           value="${pd.names }">
                    <input type="hidden" name="AREA_IDS" id="AREA_IDS" value="${pd.AREA_ID }">
                </div>
                
                <div class="border-style">
                    <span class="border-size">综合查询</span>
                    <div class="border-line"></div>
                    <input class="seat-input width-110px" placeholder="综合查询" name="keywords" id="keywords"
                           value="${pd.keywords }">
                </div>
                <button type="button" class="btn btn-default-sm button-blue width-45px"
                        style="margin: 5px 5px 5px 5px;" onclick="tosearch();">
                    <font>查&nbsp;询</font>
                </button>
                <button type="button" class="btn btn-default-sm button-blue width-45px"
                        style="margin: 5px 5px 5px 5px;" onclick="toclean();">
                    <font>重&nbsp;置</font>
                </button> 
            </div>
        </div>
        <div class="seat-middle">
            <!--  <div class="seat-middle-nr"> -->

            <div class="xtyh-middle-r zxzgl-middle-r">
                <!-- <div class="box-body" > -->

                <table id="example_dic" class="table table-bordered table-hover">
                    <thead>
                    <tr style="height: 38px !important;">

                        <th class="center" style="width:50px;padding: 0;line-height: 38px">序号</th>
                        <th class="center" style="padding: 0;line-height: 38px">名称</th>
                        <th class="center" style="padding: 0;line-height: 38px">地区编码</th>
                        <th class="center" style="padding: 0;line-height: 38px">地区级别</th>
                        <th class="center" style="padding: 0;line-height: 38px">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <!-- 开始循环 -->
                    <c:choose>
                        <c:when test="${not empty varList}">
                            <c:forEach items="${varList}" var="var" varStatus="vs">
                                <tr id="tr${vs.index+1}" style="height: 35px !important;">
                                    <td class='center' style="width: 30px;">${vs.index+1}</td>
                                    <td class='center'><a
                                            href="javascript:goSondict('${var.AREA_ID}')"><i
                                            class="ace-icon fa fa-share bigger-100"></i>&nbsp;${var.NAME}</a></td>
                                    <td class='center'>${var.AREA_CODE}</td>
                                    <td class='center'>${var.AREA_LEVEL}</td>
                                    <td class="center">
                                        <div class="flex-row-center-center">
                                            <%if (jurisdiction.hasQx("990502")) { %>
                                            <div class="button-edit"
                                                 title="编辑"
                                                 onclick="edit('${var.AREA_ID}');">
                                                <font class="button-content">编辑</font>
                                            </div>
<%--                                            <a class="cy_bj" title="编辑"--%>
<%--                                               onclick="edit('${var.AREA_ID}');"> <i title="编辑"></i>--%>
<%--                                            </a>--%>
                                            <%} %>
                                            <%if (jurisdiction.hasQx("990503")) { %>
                                            <div class="button-delete" style="margin-left:10px;"
                                                 title="删除"
                                                 onclick="del('${var.AREA_ID}');">
                                                <font class="button-content">删除</font>
                                            </div>
<%--                                            <a style="margin-left:10px;" class="cy_sc"--%>
<%--                                               onclick="del('${var.AREA_ID}');"> <i title="删除"></i>--%>
<%--                                            </a>--%>
                                            <%} %>

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

    <c:if test="${not empty action}">
        <div style="width:100%;text-align:center">
            <a class="btn btn-mini btn-danger" id="alink" data-btn-type="cancel"
               data-dismiss="modal">确认</a>
        </div>
    </c:if>
</div>

<!-- /.content-wrapper -->
<!-- basic scripts -->
<!-- 页面底部js¨ -->
<%@ include file="../system/include/incJs_foot.jsp" %>
<!-- 日期框 -->
    <!-- jquery.tips.js -->


<script type="text/javascript">
    //$(top.hangge());//关闭加载状态
    //检索
    function tosearch() {
        var area_code = $("#area_codes").val();
        var name = $("#area_names").val();
        var keywords = $("#keywords").val();
        var AREA_ID = $("#AREA_IDS").val();
        location.href = "<%=path%>/areamanage/list.do?keywords="+encodeURI(encodeURI(keywords))+"&name=" + encodeURI(encodeURI(name))+"&AREA_CODE=" +area_code+"&id=" +AREA_ID+"";
    }
    
    function toclean() {
        $("#area_codes").val('');
       	$("#area_names").val('');
        $("#keywords").val('');
        $("#AREA_IDS").val('');
        tosearch();
   }

    //去此ID下子级列表
    function goSondict(AREA_ID) {
        //top.jzts();
        window.location.href = "<%=basePath%>areamanage/list.do?AREA_ID=" + AREA_ID;
    };

    //去此ID下子列表
    function goSondict(DICTIONARIES_ID) {
        top.jzts();
        window.location.href = "<%=basePath%>dictionaries/list.do?DICTIONARIES_ID=" + DICTIONARIES_ID;
    };

    //新增
    function add(AREA_ID) {

        var winId = "userWin";
        modals.openWin({
            winId: winId,
            title: '新增',
            width: '700px',
            url: '<%=basePath%>areamanage/goAdd.do?AREA_ID=' + AREA_ID
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
        bootbox.confirm("确定要删除吗?", function (result) {
            if (result) {
                var url = "<%=basePath%>areamanage/delete.do?AREA_ID=" + Id + "&tm=" + new Date().getTime();
                $.get(url, function (data) {
                    tosearch();
                });
            }
        });
    }

    //删除
    function del(Id) {
        bootbox.confirm("确定要删除吗?", function (result) {
            if (result) {
                top.jzts();
                var url = "<%=basePath%>areamanage/delete.do?AREA_ID=" + Id + "&tm=" + new Date().getTime();
                $.get(url, function (data) {
                    if ("success" == data.result) {
                        parent.location.href = "<%=basePath%>areamanage/listTree.do?AREA_ID=${AREA_ID}&dnowPage=${page.currentPage}";
                    } else if ("false" == data.result) {
                        bootbox.dialog({
                            message: "<span class='bigger-110'>删除失败！请先删除子级.</span>",
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

    function addweixin() {

        $.ajax({
            type: "POST",
            url: '<%=basePath%>areamanage/addweixindept',
            dataType: 'json',
            //beforeSend: validateData,
            cache: false,
            success: function (data) {
                console.log(data.success);
                if (data.success) {
                    alert("同步成功");
                } else {
                    alert("同步失败");
                }
            }
        });

    }

    //修改
    function edit(Id) {

        var winId = "userWin";
        modals.openWin({
            winId: winId,
            title: '编辑',
            width: '700px',
            url: '<%=basePath%>areamanage/goEdit.do?AREA_ID=' + Id
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
        $('.seat-middle').find(".row:first").hide();

        $(".xtyh-middle-r").find(".row:last").addClass("row-zg");
        $(".xtyh-middle-r").find(".row:eq(1)").addClass("row-two");
    });

    function home() {
        parent.parent.document.getElementById("mainFrame").src = "<%=basePath%>login_default.do";
    }

</script>