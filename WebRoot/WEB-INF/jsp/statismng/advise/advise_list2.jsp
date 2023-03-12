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
        @media screen and (max-width: 1700px) {
            .xtyh-middle-r {
                width: 100% !important;
            }
        }

        .zxzgl-middle-r {
            width: 100% !important;
        }

        table {
            /*设置相邻单元格的边框间的距离*/
            /*border-spacing: 0;*/
            /*表格设置合并边框模型*/
            /*border-collapse: collapse;*/
            /*text-align: center;*/
        }
        /*关键设置 tbody出现滚动条*/
        table tbody {
            /*display: block;*/
            /*height: 400px;*/
            /*overflow-y: scroll;*/
            /*overflow-x: hidden;*/
        }
        table thead,
        tbody tr {
            /*display: table;*/
            /*width: 100%;*/
            /*table-layout: fixed;*/
        }
        /*关键设置：滚动条默认宽度是16px 将thead的宽度减16px*/
        /*table thead {*/
        /*    width: calc( 100% - 1em)*/
        /*}*/
        /*table thead th {*/
        /*    background: #ccc;*/
        /*}*/

    </style>
    <script src="static/js/echarts.min.js"></script>
</head>
<body class="no-skin">


<div class="content-wrapper" style="width:100%;margin-left:0px;overflow: auto">

    <section class="container-fluid">
        <div class="flex-column" style="background-color: #fff;box-shadow: 4px 4px 6px #c7c7c7;">
            <div style="padding: 5px" style="height: 30px">
                <div class="seat-header">
                    <div class="seat-header-line"></div>
                    <div class="seat-header-size">投诉统计</div>
                </div>
            </div>
            <div class="between-center">
                <div class="seat-middle-top-left-tp flex-row-center-center">
                    <div class="border-style">
                        <span class="border-size">开始日期</span>
                        <div class="border-line"></div>
                        <input class="date-picker" name="starttime" id="starttime" autoComplete="off"
                               title="开始时间"
                               placeholder="开始时间" value="${pd.starttime}" type="text"
                               data-date-format="yyyy-mm-dd"/>
                    </div>
                    <div class="border-style">
                        <span class="border-size">结束日期</span>
                        <div class="border-line"></div>
                        <input class="date-picker" name="endtime" id="endtime" autoComplete="off"
                               title="开始时间"
                               placeholder="结束时间" value="${pd.endtime}" type="text"
                               data-date-format="yyyy-mm-dd"/>
                    </div>
                    <input value="${pd.tsdept}" name="tsdepts" id="tsdepts" type="hidden"/>
                    <input value="${pd.tstypenamecode}" name="tstypenamecode" id="tstypenamecode" type="hidden"/>
                    <button type="button" class="btn btn-default-sm button-blue width-65px"
                            style="margin: 5px 5px 5px 15px;" onclick="search();">
                        <font>查&nbsp;询</font>
                    </button>
                </div>
                <div class="seat-middle-top" style="margin-top: 0px">
                    <button type="button" class="btn btn-default-sm button-blue width-65px"
                            style="margin: 5px 5px 5px 15px;" onclick="searchmon('1');">
                        <font>本&nbsp;周</font>
                    </button>
                    <button type="button" class="btn btn-default-sm button-blue width-65px"
                            style="margin: 5px 5px 5px 15px;" onclick="searchmon('2');">
                        <font>本&nbsp;月</font>
                    </button>
                    <button type="button" class="btn btn-default-sm button-blue width-65px"
                            style="margin: 5px 5px 5px 15px;" onclick="searchmon('3');">
                        <font>本&nbsp;季</font>
                    </button>
                    <button type="button" class="btn btn-default-sm button-blue width-65px"
                            style="margin: 5px 5px 5px 15px;" onclick="searchmon('4');">
                        <font>本&nbsp;年</font>
                    </button>
                </div>
            </div>
        </div>

        <div class="seat-middle flex-column">
            <!--  <div class="seat-middle-nr"> -->

            <div class="xtyh-middle-r zxzgl-middle-r flex-row">
                <!-- <div class="box-body" > -->
                <div class="automain"
                     style="width:70%;background-color:#ffffff;box-shadow:  4px 4px 6px #c7c7c7;margin-bottom: 10px">
                    <div style="background-color: #ffffff;padding: 5px">
                        <div class="seat-header">
                            <div class="seat-header-line"></div>
                            <div class="seat-header-size">投诉类别(大项)</div>
                        </div>
                    </div>
                    <div id="main" style="width: 100%;height:430px;"></div>
                </div>

                <div class="autotable"
                     style="margin-left: 10px;margin-bottom:10px;width:30%;background-color: #ffffff;box-shadow:  4px 4px 6px #c7c7c7;height:470px;overflow: auto">
                    <div style="background-color: #ffffff;padding: 5px">
                        <div class="seat-header">
                            <div class="seat-header-line"></div>
                            <div class="seat-header-size">投诉类别(大项)排行</div>
                        </div>
                    </div>
                    <table>
                        <thead>
                        <tr style="white-space: nowrap">
                            <th class="center">序号</th>
                            <th class="center">名称</th>
                            <th class="center">投诉量</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${list1}" var="var1" varStatus="vs1">
                            <c:if test="${vs1.count <11}">
                                <tr>
                                    <td class="" style="width: 20px;height: 20px" align="center">
                                        <c:if test="${vs1.count == 1 }"><span class="count-number"
                                                                              style="background-color: #ED7372;">${vs1.count}</span></c:if>
                                        <c:if test="${vs1.count == 2 }"><span class="count-number"
                                                                              style="background-color: #EFB134;">${vs1.count}</span></c:if>
                                        <c:if test="${vs1.count == 3 }"><span class="count-number"
                                                                              style="background-color: #8ABA2A;">${vs1.count}</span></c:if>
                                        <c:if test="${vs1.count !=1&&vs1.count !=2&&vs1.count !=3}"><span
                                                class="count-number"
                                                style="background-color: #60A5D7;">${vs1.count}</span></c:if>
                                    </td>
                                    <td width="50%" align="center">${var1.key}</td>
                                    <td align="center">${var1.value}</td>
                                </tr>
                            </c:if>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="xtyh-middle-r zxzgl-middle-r flex-row" style="margin-bottom: 20px">
                <div class="automain1"
                     style="width:70%;background-color:#ffffff;box-shadow:  4px 4px 6px #c7c7c7;margin-bottom: 10px">
                    <div style="background-color: #ffffff;padding: 5px">
                        <div class="seat-header">
                            <div class="seat-header-line"></div>
                            <div class="seat-header-size">投诉类别(细项)</div>
                        </div>
                    </div>
                    <div id="main1" style="width: 100%;height:430px;"></div>
                </div>

                <div class="autotable"
                     style="margin-left:10px;width:30%;background-color: #ffffff;box-shadow:  4px 4px 6px #c7c7c7;height: 470px;overflow: auto">
                    <div style="background-color: #ffffff;padding: 5px">
                        <div class="seat-header">
                            <div class="seat-header-line"></div>
                            <div class="seat-header-size">投诉类别(细项)排行</div>
                        </div>
                    </div>
                    <table>
                        <thead>
                        <tr style="white-space: nowrap">
                            <th class="center">序号</th>
                            <th class="center">名称</th>
                            <th class="center">投诉量</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${list2}" var="var1" varStatus="vs1">
                            <c:if test="${vs1.count <11}">
                                <tr style="white-space: nowrap">
                                    <td class="" style="width: 20px;height: 20px" align="center">
                                        <c:if test="${vs1.count == 1 }"><span class="count-number"
                                                                              style="background-color: #ED7372;">${vs1.count}</span></c:if>
                                        <c:if test="${vs1.count == 2 }"><span class="count-number"
                                                                              style="background-color: #EFB134;">${vs1.count}</span></c:if>
                                        <c:if test="${vs1.count == 3 }"><span class="count-number"
                                                                              style="background-color: #8ABA2A;">${vs1.count}</span></c:if>
                                        <c:if test="${vs1.count !=1&&vs1.count !=2&&vs1.count !=3}"><span
                                                class="count-number"
                                                style="background-color: #60A5D7;">${vs1.count}</span></c:if>
                                    </td>
                                    <td>${var1.key}</td>
                                    <td>${var1.value}</td>
                                </tr>
                            </c:if>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </section>

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
<!-- 获取本周、本月、本季度、本年、时间戳格式化方法 -->
<script src="./static/js/GetTimeUtil.js"></script>

<script src="./static/ace/js/jquery.js"></script>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));
    var myChart2 = echarts.init(document.getElementById('main1'));
    window.onload = function () {
        var array1 = [];
        var array2 = [];
        <c:forEach items="${data1}" var="var" varStatus="vs">
        array1.push('${var.key}');
        array2.push('${var.value}');
        </c:forEach>


        //柱状图
        option = {
            color: ['#3398DB'],
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            grid: {
                left: '3%',
                right: '10%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: [
                {
                    name: '投诉类别(大项)',
                    type: 'category',
                    data: array1,
                    axisTick: {
                        alignWithLabel: true
                    },
                    axisLabel: {
                        rotate: 40, // 旋转角度
                        interval: 0  //设置X轴数据间隔几个显示一个，为0表示都显示
                    }
                },
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: [
                {
                    name: '建议量',
                    type: 'bar',
                    barWidth: '50%',
                    data: array2
                }
            ],
            color: ['#60A5D7']
        };
        myChart.setOption(option);
        myChart.resize();
        myChart.on('click', function (params) {
            var starttime = $("#starttime").val();
            var endtime = $("#endtime").val();
            var tsdepts = $("#tsdepts").val();
            console.log(params.name);
            location.href = "<%=path%>/complain/jytypedata.do?starttime=" + starttime +
                "&endtime=" + endtime +
                "&tsdept=" + tsdepts
                + "&tstypenamecode=" + params.name;
        });

        var array3 = [];
        var array4 = [];
        <c:forEach items="${data2}" var="var" varStatus="vs">
        array3.push('${var.key}');
        array4.push('${var.value}');
        </c:forEach>
        //柱状图
        option2 = {
            color: ['#3398DB'],
            tooltip: {
                trigger: 'axis'
            },
            grid: {
                left: '3%',
                right: '10%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: [
                {
                    name: '投诉类别(细项)',
                    type: 'category',
                    data: array3,
                    axisTick: {
                        alignWithLabel: true
                    },
                    axisLabel: {
                        rotate: 40, // 旋转角度
                        interval: 0  //设置X轴数据间隔几个显示一个，为0表示都显示
                    }
                }
            ],
            yAxis: [
                {
                    name: '建议量',
                    type: 'value'
                }
            ],
            series: [
                {
                    name: '建议量',
                    type: 'bar',
                    barWidth: '50%',
                    data: array4
                }
            ],
            color: [
                '#ED7372'
            ]
        };
        myChart2.setOption(option2);
    }

    $(function () {
        $('.seat-middle').find(".row:first").hide();
        $(".xtyh-middle-r").find(".row:last").addClass("row-zg");
        $(".xtyh-middle-r").find(".row:eq(1)").addClass("row-two");

    });

    function search() {
        var starttime = $("#starttime").val();
        var endtime = $("#endtime").val();
        var tsdepts = $("#tsdepts").val();
        var tstypenamecode = $("#tstypenamecode").val();
        location.href = "<%=path%>/complain/jytypedata.do?starttime=" + starttime +
            "&endtime=" + endtime + "&tsdept=" + tsdepts + "&tstypenamecode=" + tstypenamecode;
    }

    function searchmon(mon) {
        var tsdepts = $("#tsdepts").val();
        var tstypenamecode = $("#tstypenamecode").val();
        if (mon == '1') {
            //本周
            location.href = "<%=path%>/complain/jytypedata.do?starttime=" + getWeekStartDate() +
                "&endtime=" + getWeekEndDate() + "&tsdept=" + tsdepts + "&tstypenamecode=" + tstypenamecode;
        } else if (mon == '2') {
            //本月
            location.href = "<%=path%>/complain/jytypedata.do?starttime=" + getMonthStartDate() +
                "&endtime=" + getMonthEndDate() + "&tsdept=" + tsdepts + "&tstypenamecode=" + tstypenamecode;
        } else if (mon == '3') {
            //本季
            location.href = "<%=path%>/complain/jytypedata.do?starttime=" + getQuarterStartDate() +
                "&endtime=" + getQuarterEndDate() + "&tsdept=" + tsdepts + "&tstypenamecode=" + tstypenamecode;
        } else if (mon == '4') {
            //本年
            location.href = "<%=path%>/complain/jytypedata.do?starttime=" + getYearStartDate() +
                "&endtime=" + getYearEndDate() + "&tsdept=" + tsdepts + "&tstypenamecode=" + tstypenamecode;
        }
    }

    $('.date-picker').datepicker({
        autoclose: true,
        todayHighlight: true,
        clearBtn: true
    });

</script>
</body>
</html>