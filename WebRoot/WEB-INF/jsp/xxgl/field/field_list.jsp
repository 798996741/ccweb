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
    <!-- jsp文件头和头部 --><%--
<%@ include file="../index/top.jsp"%> --%>
    <%@ include file="../../system/include/incJs_mx.jsp" %>
    <style>

    </style>
</head>
<body class="no-skin">
<div class="content-wrapper" style="width:100%;margin-left:0px;">
    <section class="container-fluid">
        <div class="seat-middle-top-x"></div>
        <div class="seat-middle-top">
            <div class="seat-middle-top-left">
                <div class="seat-middle-top-left-bt">客户信息设置</div>
                <div class="seat-middle-top-left-tp">
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

                <table id="example2" class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th class="center cy_th" style="width:30px;min-width:30px;">序号</th>
                        <th id='cy_thk'></th>
                        <th class="center">字段名称</th>
                        <th class="center">字段</th>
                        <th class="center">类型</th>
                        <th class="center">长度</th>
                        <th class="center">基础字段</th>
                        <th class="center">电话</th>
                        <th class="center">展示</th>
                        <th class="center">排序号</th>
                        <th class="center">操作人</th>
                        <th class="center">操作时间</th>
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

                                        <td class='center cy_td' style="width:30px;min-width:30px;">${vs.index+1}</td>
                                        <td id='cy_thk'></td>
                                        <td class='center'>${var.FIELDNAME}</td>
                                        <td class='center'>${var.FIELD}</td>
                                        <td class='center'>${var.FIELDTYPENAME}</td>
                                        <td class='center'>${var.FIELDNUM}</td>
                                        <td class='center'>
                                            <c:if test="${var.ISBASIC==0}"><i><img
                                                    src="static/login/images/icon_86.png"></i></c:if>
                                            <c:if test="${var.ISBASIC==1}"> <i><img
                                                    src="static/login/images/icon_83.png"></i></c:if>
                                        </td>
                                        <td class='center'>
                                            <c:if test="${var.ISTEL==0}"><i><img src="static/login/images/icon_86.png"></i></c:if>
                                            <c:if test="${var.ISTEL==1}"> <i><img src="static/login/images/icon_83.png"></i>
                                            </c:if>
                                        </td>

                                        <td class='center'>
                                            <c:if test="${var.ISSHOW==0}"><i><img src="static/login/images/icon_86.png"></i></c:if>
                                            <c:if test="${var.ISSHOW==1}"> <i><img
                                                    src="static/login/images/icon_83.png"></i></c:if>
                                        </td>
                                        <td class='center'>${var.PX}</td>
                                        <td class='center'>${var.CREATEMAN}</td>
                                        <td class='center'>${var.CREATEDATE}</td>
                                        <td class="center">
                                            <c:if test="${QX.edit != 1 && QX.del != 1 }">
                                                <span class="label label-large label-grey arrowed-in-right arrowed-in"><i
                                                        class="ace-icon fa fa-lock" title="无权限"></i></span>
                                            </c:if>
                                            <div class="flex-position">
                                                <div class="flex-row">
                                                    <c:if test="${QX.edit == 1 }">
                                                        <div class="button-edit" title="编辑"
                                                             onclick="edit('${var.ID}');" title="编辑">
                                                            <font class="button-content">编辑</font>
                                                        </div>
                                                        <%--                                                    <a class="cy_bj" title="编辑"--%>
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
                                                        <%--                                                    <a style="margin-left:10px;" class="cy_sc"--%>
                                                        <%--                                                       onclick="del('${var.ID}');"> <i--%>
                                                        <%--                                                            title="删除"></i>--%>
                                                        <%--                                                    </a>--%>
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
                <!-- /.box-header -->
                <div class="box-body" >
                  <table id="example2" class="table table-bordered table-hover">
                        <thead>
                            <tr>
                                <th class="center" style="width:30px;">序号</th>
                                <th class="center">字段名称</th>
                                <th class="center">字段</th>
                                <th class="center">字段类型</th>
                                <th class="center">长度</th>
                                <th class="center">是否基础字段</th>
                                <th class="center">是否电话</th>
                                <th class="center">是否展示</th>
                                <th class="center">排序号</th>
                                <th class="center">操作人</th>
                                <th class="center">操作时间</th>
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
                                        <td class='center'>${var.FIELDNAME}</td>
                                        <td class='center'>${var.FIELD}</td>
                                        <td class='center'>${var.FIELDTYPENAME}</td>
                                        <td class='center'>${var.FIELDNUM}</td>
                                        <td class='center'>
                                            <c:if test="${var.ISBASIC==0}">否</c:if>
                                            <c:if test="${var.ISBASIC==1}">是</c:if>
                                        </td>
                                        <td class='center'>
                                            <c:if test="${var.ISTEL==0}">否</c:if>
                                            <c:if test="${var.ISTEL==1}">是</c:if>
                                        </td>

                                        <td class='center'>
                                            <c:if test="${var.ISSHOW==0}">否</c:if>
                                            <c:if test="${var.ISSHOW==1}">是</c:if>
                                        </td>
                                        <td class='center'>${var.PX}</td>
                                        <td class='center'>${var.CREATEMAN}</td>
                                        <td class='center'>${var.CREATEDATE}</td>
                                        <td class="center">
                                            <c:if test="${QX.edit != 1 && QX.del != 1 }">
                                            <span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
                                            </c:if>
                                            <div>
                                                <c:if test="${QX.edit == 1 }">
                                                    <a class="btn btn-xs btn-success" title="编辑" onclick="edit('${var.ID}');">
                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120" title="编辑"></i>
                                                    </a>
                                                </c:if>

                                                <c:if test="${QX.del == 1&&var.ISUSE!='1' }">
                                                    <a style="margin-left:10px;" class="btn btn-xs btn-danger" onclick="del('${var.ID}');">
                                                        <i class="ace-icon fa fa-trash-o bigger-120" title="删除"></i>
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
                                    <td colspan="100" class="center" >没有相关数据</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>

                    </tbody>

                  </table>





                </div>
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
        //top.jzts();
        var keywords = $("#keywords").val();
        location.href = "<%=path%>/field/list.do?keywords=" + encodeURI(encodeURI(keywords));
        //$("#Form_s").submit();
    }

    $(function () {
        $('#example2').DataTable({
            'paging': true,
            'lengthChange': false,
            'searching': true,
            'ordering': false,
            'info': true,
            'autoWidth': true
        })
    });

    //新增
    function add() {
        var winId = "userWin";
        modals.openWin({
            winId: winId,
            title: '字段新增',
            width: '900px',
            height: '350px',
            backdrop: 'static',
            keyboard: false,
            url: '<%=basePath%>field/goAdd.do'
            /*, hideFunc:function(){
                modals.info("hide me");
            },
            showFunc:function(){
                modals.info("show me");
            } */
        });

    }

    //删除
    function del(Id, Uid) {
        bootbox.confirm("确定要删除吗?", function (result) {
            if (result) {
                top.jzts();
                var url = "<%=basePath%>field/delete.do?ID=" + Id + "&UID=" + Uid + "&tm=" + new Date().getTime();
                $.get(url, function (data) {
                    location.href = "<%=path%>/field/list.do";
                });
            }
        });
    }

    //修改
    function edit(Id) {

        var winId = "userWin";
        //alert(Id);
        modals.openWin({
            winId: winId,
            title: '字段修改',
            width: '900px',
            height: '400px',
            url: '<%=basePath%>field/goEdit.do?ID=' + Id
            /*, hideFunc:function(){
                modals.info("hide me");
            },
            showFunc:function(){
                modals.info("show me");
            } */
        });


    }

    //批量操作
    function makeAll(msg) {
        bootbox.confirm(msg, function (result) {
            if (result) {
                var str = '';
                for (var i = 0; i < document.getElementsByName('ids').length; i++) {
                    if (document.getElementsByName('ids')[i].checked) {
                        if (str == '') str += document.getElementsByName('ids')[i].value;
                        else str += ',' + document.getElementsByName('ids')[i].value;
                    }
                }
                if (str == '') {
                    bootbox.dialog({
                        message: "<span class='bigger-110'>您没有选择任何内容!</span>",
                        buttons:
                            {"button": {"label": "确定", "className": "btn-sm btn-success"}}
                    });
                    $("#zcheckbox").tips({
                        side: 1,
                        msg: '点这里全选',
                        bg: '#AE81FF',
                        time: 8
                    });
                    return;
                } else {
                    if (msg == '确定要删除选中的数据吗?') {
                        top.jzts();
                        $.ajax({
                            type: "POST",
                            url: '<%=basePath%>field/deleteAll.do?tm=' + new Date().getTime(),
                            data: {DATA_IDS: str},
                            dataType: 'json',
                            //beforeSend: validateData,
                            cache: false,
                            success: function (data) {
                                $.each(data.list, function (i, list) {
                                    location.href = "<%=path%>/field/list.do";
                                });
                            }
                        });
                    }
                }
            }
        });
    };

    //导出excel
    function toExcel() {
        window.location.href = '<%=basePath%>field/excel.do';
    }

    $(function () {

        /* $(".row thead").find("th:first").addClass("cy_th");
        $(".row tbody").find("tr").find("td:first").addClass("cy_td"); */
        /* 			$(".row thead").find("th:first").after("<th id='cy_thk'></th>");
                    $(".row tbody").find("tr").find("td:first").after("<td id='cy_thk'></td>"); */

        $('.seat-middle').find(".row:first").hide();
        $(".xtyh-middle-r").find(".row:last").addClass("row-zg");
        $(".xtyh-middle-r").find(".row:eq(1)").addClass("row-two");

        /* $(document).on("click",".paginate_button",function () {
            $(".row thead").find("th:first").addClass("cy_th");
            $(".row tbody").find("tr").find("td:first").addClass("cy_td");
        }); */


    });
</script>


</body>
</html>