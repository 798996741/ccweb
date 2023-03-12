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
                    <div class="seat-header-size">部门处理总览</div>
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
            <div class="xtyh-middle-r zxzgl-middle-r" style="background-color: #fff;box-shadow: 4px 4px 6px #c7c7c7;">
                <div style="background-color: #ffffff;padding: 5px">
                    <div class="seat-header">
                        <div class="seat-header-line"></div>
                        <div class="seat-header-size">部门处理趋势</div>
                    </div>
                </div>
                <div id="main" style="width: 100%;height:700px;background-color: #ffffff" ></div>
            </div>

            <div class="xtyh-middle-r zxzgl-middle-r" style="background-color: #ffffff;margin: 0 0 0 10px;box-shadow: 4px 4px 6px #c7c7c7;overflow: auto" >
                <div style="background-color: #ffffff;padding: 5px">
                    <div class="seat-header">
                        <div class="seat-header-line"></div>
                        <div class="seat-header-size">处理时效统计</div>
                    </div>
                </div>
                <table>
                    <thead>
                    <tr style="white-space: nowrap">
                        <th class="center">排行</th>
                        <th class="center">部门</th>
                        <th class="center">平均处理时效</th>
                        <th class="center">最长处理时效</th>
                        <th class="center">最短处理时效</th>
                    </tr>
                    </thead>

                    <tbody>

						<c:forEach items="${area}" var="var1" varStatus="vs1">
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
								<td>${var1.NAME}</td>
								<c:forEach items="${getpjsx}" var="var2" varStatus="vs2">
									<c:if test="${var1.AREA_CODE==var2.dept}">
										<td>${var2.pjsj}</td>
									</c:if>
								</c:forEach>
                                <c:forEach items="${getmaxclsj}" var="var3" varStatus="vs2">
                                    <c:if test="${var1.AREA_CODE==var3.dept}">
                                        <td>${var3.pjsj}</td>
                                    </c:if>
                                </c:forEach>
                                <c:forEach items="${getminclsj}" var="var4" varStatus="vs2">
                                    <c:if test="${var1.AREA_CODE==var4.dept}">
                                        <td>${var4.pjsj}</td>
                                    </c:if>
                                </c:forEach>

							</tr>
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
<script src="./static/ace/js/jquery.js"></script>
<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    window.onload = function () {
        var array1=[['product', '工单处理总量', '待处理工单量', '超时工单量']];
        <c:forEach items="${area}" var="var1" varStatus="vs1">
        var array=[];
        <c:forEach items="${getzcll}" var="var6" varStatus="vs2">
        <c:if test="${var1.AREA_CODE==var6.dept}">
                array.push('${var1.NAME}');
                array.push('${var6.num}');
        </c:if>
        </c:forEach>
        <c:forEach items="${getdcll}" var="var7" varStatus="vs2">
        <c:if test="${var1.AREA_CODE==var7.dept}">
                array.push('${var7.num}');
        </c:if>
        </c:forEach>
        <c:forEach items="${getcsgd}" var="var5" varStatus="vs2">
        <c:if test="${var1.AREA_CODE==var5.dept}">
                array.push('${var5.num}');
        </c:if>
        </c:forEach>
        array1.push(array);
        </c:forEach>
        console.log(array1);
        option = {
            legend: {},
            tooltip: {},
            dataset: {
                source: array1

            },
            xAxis: {
                name:'部门',
                type: 'category',
                axisLabel: {
                    rotate: 40, // 旋转角度
                    interval: 0  //设置X轴数据间隔几个显示一个，为0表示都显示
                }
            },
            yAxis: {
                name:'工单量'
            },
            grid: {
                left: '3%',
                right: '6%',
                bottom: '3%',
                containLabel: true
            },
            // Declare several bar series, each will be mapped
            // to a column of dataset.source by default.
            series: [
                {type: 'bar'},
                {type: 'bar'},
                {type: 'bar'}
            ],
            color:[
                '#76BCE2',
                '#F0CC3F',
                '#E88E21'
            ]
        };

        myChart.setOption(option);
        myChart.resize();
    }

    function search() {
        var starttime = $("#starttime").val();
        var endtime = $("#endtime").val();
        location.href = "<%=path%>/complain/findGDCLZL.do?starttime=" + starttime +
            "&endtime=" + endtime;
    }


    function searchmon(mon) {
        if (mon=='1'){
            //本周
            location.href="<%=path%>/complain/findGDCLZL.do?starttime="+getWeekStartDate()+
                "&endtime="+getWeekEndDate();
        }else if (mon=='2'){
            //本月
            location.href="<%=path%>/complain/findGDCLZL.do?starttime="+getMonthStartDate()+
                "&endtime="+getMonthEndDate();
        }else if (mon == '3') {
            //本季
            location.href = "<%=path%>/complain/findGDCLZL.do?starttime=" + getQuarterStartDate() +
                "&endtime=" + getQuarterEndDate();
        } else if (mon == '4') {
            //本年
            location.href = "<%=path%>/complain/findGDCLZL.do?starttime=" + getYearStartDate() +
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