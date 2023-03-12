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


<div class="content-wrapper" style="width:100%;margin-left:0px;overflow: auto;">


    <section class="container-fluid" style="overflow: scroll;">
        <div class="flex-column" style="background-color: #fff;box-shadow: 4px 4px 6px #c7c7c7;">
            <div style="padding: 5px" style="height: 30px">
                <div class="seat-header">
                    <div class="seat-header-line"></div>
                    <div class="seat-header-size">工单总览</div>
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

        <div class="flex-row" style="margin:15px 0 20px 0">
            <!--  <div class="seat-middle-nr"> -->

            <div class="xtyh-middle-r zxzgl-middle-r flex-column"
                 style="background-color: #ffffff;box-shadow: 4px 4px 6px #c7c7c7;">
                <div style="background-color: #ffffff;box-shadow: 4px 4px 6px #c7c7c7;">
                    <div style="background-color: #ffffff;padding: 5px;">
                        <div class="seat-header">
                            <div class="seat-header-line"></div>
                            <div class="seat-header-size">工单总趋势</div>
                        </div>
                    </div>
                    <div id="main" style="width: 100%;height:350px;background-color: #ffffff"></div>
                    <div id="main3" style="width: 100%;height:350px;background-color: #ffffff;margin-top: 10px;"></div>
                </div>
            </div>

            <div class="xtyh-middle-r zxzgl-middle-r flex-column" style="margin: 0 0 0 10px;">

                <div style="background-color: #ffffff;box-shadow: 4px 4px 6px #c7c7c7;">
                    <div style="background-color: #ffffff;padding: 5px">
                        <div class="seat-header">
                            <div class="seat-header-line"></div>
                            <div class="seat-header-size">工单来源</div>
                        </div>
                    </div>
                    <div id="main2" style="width: 100%;height:330px;background-color: #ffffff"></div>
                </div>


                <div style="background-color: #ffffff;box-shadow: 4px 4px 6px #c7c7c7;margin-top: 10px">
                    <div style="background-color: #ffffff;padding: 5px">
                        <div class="seat-header">
                            <div class="seat-header-line"></div>
                            <div class="seat-header-size">部门快速处理统计</div>
                        </div>
                    </div>
                    <div id="main4" style="width:100%;height:330px;background-color: #ffffff"></div>
                </div>
            </div>

            <div class="xtyh-middle-r zxzgl-middle-r flex-column" style="margin: 0 0 0 10px;">

                <div style="background-color: #ffffff;box-shadow: 4px 4px 6px #c7c7c7;">
                    <div style="background-color: #ffffff;padding: 5px">
                        <div class="seat-header">
                            <div class="seat-header-line"></div>
                            <div class="seat-header-size">客户姓名快速处理统计</div>
                        </div>
                    </div>
                    <div id="main5" style="width: 100%;height:330px;background-color: #ffffff"></div>
                </div>


                <div style="background-color: #ffffff;box-shadow: 4px 4px 6px #c7c7c7;margin-top: 10px">
                    <div style="background-color: #ffffff;padding: 5px">
                        <div class="seat-header">
                            <div class="seat-header-line"></div>
                            <div class="seat-header-size">投诉分类快速处理统计</div>
                        </div>
                    </div>
                    <div id="main6" style="width:100%;height:330px;background-color: #ffffff"></div>
                </div>
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
<script src="./static/ace/js/jquery.js"></script>
<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));
    var myChart2 = echarts.init(document.getElementById('main2'));
    var myChart3 = echarts.init(document.getElementById('main3'));
    var myChart4 = echarts.init(document.getElementById('main4'));
    var myChart5 = echarts.init(document.getElementById('main5'));
    var myChart6 = echarts.init(document.getElementById('main6'));

    window.onload = function () {


        var array1 = [${judgetime}];
        var str = '${judgetime}';
        var array11 = str.substring(0, str.length - 1).substring(1).split(",");
        var array2 = [];
        for (var i = 0; i < array11.length; i++) {
            <c:forEach items="${getgdzl}" var="var" varStatus="vs">
            if (Number('${var.MONTH}') == array11[i]) {
                array2.push('${var.num}');
            }
            </c:forEach>
        }
        console.log(array2 + "array2");
        //折现图
        option = {
            xAxis: {
                name: '时间',
                type: 'category',
                data: ${judgetime}
            },
            yAxis: {
                name: '总数',
                type: 'value'
            },
            series: [{
                name: '工单量',
                data: array2,
                type: 'line'
            }],
            // 提示框
            tooltip: {
                trigger: 'axis',
            },
            color: [
                '#60A5D7'
            ]
        };
        myChart.setOption(option);
        myChart.resize();
        //饼图
        var array3 = [];
        var array4 = [];
        <c:forEach items="${getcfts}" var="var" varStatus="vs">
        array3.push('${var.key}');
        array4.push({value: '${var.value}', name: '${var.key}'});
        </c:forEach>
        option2 = {
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b}: {c} ({d}%)'
            },
            legend: {
                orient: 'vertical',
                left: 10,
                data: array3
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
                    data: array4

                }
            ],
            color: [
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
        var array5 = [];
        var array6 = [];
        <c:forEach items="${getkscl}" var="var1" varStatus="vs1">
        <c:forEach items="${area}" var="var2" varStatus="vs2">
        <c:if test="${var1.key==var2.AREA_CODE}">
        array5.push('${var2.NAME}');
        array6.push({value: '${var1.value}', name: '${var2.NAME}'});
        </c:if>
        </c:forEach>
        </c:forEach>
        option4 = {
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b}: {c} ({d}%)'
            },
            legend: {
                orient: 'vertical',
                left: 10,
                data: array5
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
                    data: array6

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
        myChart4.setOption(option4);
        myChart4.resize();
        //12345热线量、民航总局量、315投诉电话量、重复投诉柱状图

        var array7 = [['product', '快速处理量', '重复投诉量']];
        for (var i = 0; i < array11.length; i++) {
            var array = [];
            <c:forEach items="${getkscltime}" var="var8" varStatus="vs8">
            if (array11[i] == Number('${var8.MONTH}')) {
                array.push(array11[i]);
                array.push('${var8.num}');
            }
            </c:forEach>
            <%--            <c:forEach items="${getsywtime}" var="var7" varStatus="vs7">--%>
            <%--            if (array11[i] == Number('${var7.MONTH}')) {--%>
            <%--                array.push('${var7.num}');--%>
            <%--            }--%>
            <%--            </c:forEach>--%>

            <%--            <c:forEach items="${getmhzjtime}" var="var9" varStatus="vs9">--%>
            <%--            if (array11[i] == Number('${var9.MONTH}')) {--%>
            <%--                array.push('${var9.num}');--%>
            <%--            }--%>
            <%--            </c:forEach>--%>
            <%--            <c:forEach items="${getrxtime}" var="var10" varStatus="vs10">--%>
            <%--            if (array11[i] == Number('${var10.MONTH}')) {--%>
            <%--                array.push('${var10.num}');--%>
            <%--            }--%>
            <%--            </c:forEach>--%>
            <c:forEach items="${getcftstime}" var="var10" varStatus="vs10">
            if (array11[i] == Number('${var10.MONTH}')) {
                array.push('${var10.num}');
            }
            </c:forEach>
            array7.push(array);
        }
        console.log("array7" + array7);

        option3 = {
            legend: {},
            tooltip: {},
            dataset: {
                source: array7
            },
            xAxis: {
                name: '时间',
                type: 'category',
                axisLabel: {
                    rotate: 0, // 旋转角度
                    interval: 0  //设置X轴数据间隔几个显示一个，为0表示都显示
                }
            },
            yAxis: {
                name: '总数'
            },
            // Declare several bar series, each will be mapped
            // to a column of dataset.source by default.
            series: [
                {type: 'bar'},
                {type: 'bar'}
            ],
            color: [
                '#ED7372',
                '#60A5D7',
                '#EF9028',
                '#53217C',
            ]
        };

        myChart3.setOption(option3);
        myChart3.resize();


        var array8 = '${getkscltsman.name}'.split(',');
        var array9 = [${getkscltsman.data}];
        option5 = {
            xAxis: {
                name: '姓名',
                type: 'category',
                data: array8
            },
            yAxis: {
                name: '快速处理数',
                type: 'value'
            },
            series: [{
                data: array9,
                type: 'bar',
                showBackground: true,
                backgroundStyle: {
                    color: 'rgba(220, 220, 220, 0.8)'
                }
            }]
        };
        myChart5.setOption(option5);
        myChart5.resize();

        var array10 = '${getkscltsclassify.name}'.split(',');
        var array11 = [${getkscltsclassify.value}];
        console.log(array10)
        console.log(array11)


        option6 = {
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: [
                {
                    name: '投诉分类',
                    type: 'category',
                    data: array10,
                    axisTick: {
                        alignWithLabel: true
                    }
                }
            ],
            yAxis: [
                {
                    name: '快速处理数',
                    type: 'value'
                }
            ],
            series: [
                {
                    name: '快速处理数',
                    type: 'bar',
                    barWidth: '50%',
                    data: array11
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

        myChart6.setOption(option6);
        myChart6.resize();
    }

    function search() {
        var starttime = $("#starttime").val();
        var endtime = $("#endtime").val();
        location.href = "<%=path%>/complain/findGDZL.do?starttime=" + starttime +
            "&endtime=" + endtime;
    }


    function searchmon(mon) {
        if (mon == '1') {
            //本周
            location.href = "<%=path%>/complain/findGDZL.do?starttime=" + getWeekStartDate() +
                "&endtime=" + getWeekEndDate();
        } else if (mon == '2') {
            //本月
            location.href = "<%=path%>/complain/findGDZL.do?starttime=" + getMonthStartDate() +
                "&endtime=" + getMonthEndDate();
        } else if (mon == '3') {
            //本季
            location.href = "<%=path%>/complain/findGDZL.do?starttime=" + getQuarterStartDate() +
                "&endtime=" + getQuarterEndDate();
        } else if (mon == '4') {
            //本年
            location.href = "<%=path%>/complain/findGDZL.do?starttime=" + getYearStartDate() +
                "&endtime=" + getYearEndDate();
        }
    }

    $(function () {
        $('.seat-middle').find(".row:first").hide();
        $(".xtyh-middle-r").find(".row:last").addClass("row-zg");
        $(".xtyh-middle-r").find(".row:eq(1)").addClass("row-two");

    });
    $('.date-picker').datepicker({
        autoclose: true,
        todayHighlight: true,
        clearBtn: true
    });
</script>
</body>
</html>