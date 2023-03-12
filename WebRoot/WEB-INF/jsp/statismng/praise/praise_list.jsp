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
        @media screen and (max-width: 1700px) {
            .xtyh-middle-r {
                width: 100% !important;
            }
        }

        .zxzgl-middle-r {
            width: 100% !important;
        }

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
                    <div class="seat-header-size">表扬统计</div>
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
                    <button type="button" class="btn btn-default-sm button-blue width-65px"
                            style="margin: 5px 5px 5px 15px;" onclick="search();">
                        <font>查&nbsp;询</font>
                    </button>
                </div>
                <input value="" name="" id="" type="hidden">
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

        <div class="flex-row" style="margin-bottom: 20px">
            <div class="seat-middle flex-column" style="box-shadow: 4px 4px 6px #c7c7c7;">

                <div id="automain"
                     style="height:30%;box-shadow:  4px 4px 6px #c7c7c7;margin-bottom: 10px">
                    <div style="background-color: #ffffff;padding: 5px">
                        <div class="seat-header">
                            <div class="seat-header-line"></div>
                            <div class="seat-header-size">表扬趋势</div>
                        </div>
                    </div>
                    <div id="main" style="width: 100%;height:400px;background-color: #ffffff;"></div>
                </div>

                <div class="automain1" style="width:100%;height:30%;box-shadow:  4px 4px 6px #c7c7c7;">
                    <div style="background-color: #ffffff;padding: 5px">
                        <div class="seat-header">
                            <div class="seat-header-line"></div>
                            <div class="seat-header-size">表扬占比</div>
                        </div>
                    </div>
                    <div id="main1" style="width: 100%;height:250px;background-color: #ffffff;"></div>
                </div>

            </div>

            <div class="xtyh-middle-r zxzgl-middle-r"
                 style="margin: 15px 0 0 10px;box-shadow:  4px 4px 6px #c7c7c7;width:100%;background-color: #ffffff;">
                <div style="width:100%;background-color: #ffffff;padding: 5px;">
                    <div class="seat-header">
                        <div class="seat-header-line"></div>
                        <div class="seat-header-size">表扬排行</div>
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
                    <c:forEach items="${data1}" var="var1" varStatus="vs1">
                        <c:forEach items="${positon}" var="var2" varStatus="vs2">
                            <c:if test="${var1.key==var2.AREA_CODE&&vs1.count <11}">
                                <tr style="white-space: nowrap">
                                        <%--										<td>${vs1.count}</td>--%>
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
                                    <td>${var2.NAME}</td>
                                    <td>${var1.value}</td>
                                </tr>
                            </c:if>
                        </c:forEach>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

        </div>
    </section>
</div>

<!-- /.main-container -->

<!-- basic scripts -->
<!-- 页面底部js¨ -->
<%@ include file="../../system/include/incJs_foot.jsp" %>
<!-- 日期框 -->
<!-- 获取本周、本月、本季度、本年、时间戳格式化方法 -->
<script src="./static/js/GetTimeUtil.js"></script>
<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));
    var myChart2 = echarts.init(document.getElementById('main1'));
    window.onload = function () {
        document.getElementById('automain').style.width = ((document.documentElement.clientWidth) / 7) * 5
        var array1 = [];
        <c:forEach items="${positon}" var="var" varStatus="vs">
        array1.push('${var.NAME}');
        </c:forEach>
        var array2 = [];
        <c:forEach items="${positon}" var="var1" varStatus="vs">
        <c:forEach items="${data}" var="var2" varStatus="vs">
        <c:if test="${var2.key==var1.AREA_CODE}">
        array2.push('${var2.value}');
        </c:if>
        </c:forEach>
        </c:forEach>
        //柱状图
        option = {
            color: ['#60A5D7'],
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            grid: {
                left: '3%',
                right: '5%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: [
                {
                    name: '部门',
                    type: 'category',
                    data: array1,
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
                    name: '表扬量',
                    type: 'value'
                }
            ],
            series: [
                {
                    name: '投诉量',
                    type: 'bar',
                    barWidth: '50%',
                    data: array2
                }
            ]
        };
        myChart.setOption(option);
        myChart.resize();
        myChart.on('click', function (params) {
            var starttime = $("#starttime").val();
            var endtime = $("#endtime").val();
            console.log(params.name);
            location.href = "<%=path%>/complain/bytypedata.do?starttime=" + starttime +
                "&endtime=" + endtime +
                "&tsdept=" + params.name;
        });
        //扇形图
        var array3 = [];
        var array4 = [];
        <c:forEach items="${positon}" var="var1" varStatus="vs">
        <c:forEach items="${data}" var="var2" varStatus="vs">
        <c:if test="${var2.key==var1.AREA_CODE}">
        array3.push({value: ${var2.value}, name: '${var1.NAME}'});
        array4.push('${var1.NAME}')
        </c:if>
        </c:forEach>
        </c:forEach>

        option2 = {
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b}: {c} ({d}%)'
            },
            legend: {
                orient: 'vertical',
                left: 10,
                data: array4
            },
            series: [
                {
                    name: '投诉量',
                    type: 'pie',
                    radius: ['50%', '70%'],
                    avoidLabelOverlap: false,
                    label: {
                        normal: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            show: true,
                            textStyle: {
                                fontSize: '30',
                                fontWeight: 'bold'
                            }
                        }
                    },
                    labelLine: {
                        normal: {
                            show: false
                        }
                    },
                    data: array3

                }
            ],
            color: [ //自定义的颜色
                '#86157C',
                '#C20F6B',
                '#E02426',
                '#E45827',
                '#EF9028',
                '#F1AE40',
                '#F4E734',
                '#88BD49',
                '#0B8D47',
                '#096AB0',
                '#2A338A',
                '#53217C',
            ]

        };

        myChart2.setOption(option2);
        myChart2.resize();
    }

    function search() {
        var starttime = $("#starttime").val();
        var endtime = $("#endtime").val();
        location.href = "<%=path%>/complain/gopraiselist.do?starttime=" + starttime +
            "&endtime=" + endtime;
    }

    function searchmon(mon) {

        if (mon == '1') {
            //本周
            location.href = "<%=path%>/complain/gopraiselist.do?starttime=" + getWeekStartDate() +
                "&endtime=" + getWeekEndDate();
        } else if (mon == '2') {
            //本月
            location.href = "<%=path%>/complain/gopraiselist.do?starttime=" + getMonthStartDate() +
                "&endtime=" + getMonthEndDate();
        } else if (mon == '3') {
            //本季
            location.href = "<%=path%>/complain/gopraiselist.do?starttime=" + getQuarterStartDate() +
                "&endtime=" + getQuarterEndDate();
        } else if (mon == '4') {
            //本年
            location.href = "<%=path%>/complain/gopraiselist.do?starttime=" + getYearStartDate() +
                "&endtime=" + getYearEndDate();
        }
    }

    $('.date-picker').datepicker({
        autoclose: true,
        todayHighlight: true,
        clearBtn: true
    });
    $(function () {
        $('.seat-middle').find(".row:first").hide();
        $(".xtyh-middle-r").find(".row:last").addClass("row-zg");
        $(".xtyh-middle-r").find(".row:eq(1)").addClass("row-two");

    });

</script>
</body>
</html>