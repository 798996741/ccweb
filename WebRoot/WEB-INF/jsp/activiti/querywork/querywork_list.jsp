<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<%@ include file="../../system/include/incJs_mx.jsp"%>

	<script src="static/js/echarts.min .js"></script>
</head>
<body class="no-skin" style="overflow-x:scroll;">

<div id="main" style="width: 600px;height:400px;"></div>

<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '投诉趋势'
        },
        tooltip: {},
        legend: {
            data:['投诉量']
        },
        xAxis: {
            data: [
                <c:forEach items="${map}" var="var" varStatus="vs">
                "${var.key}",
                </c:forEach>
				""
            ]
        },
        yAxis: {},
        series: [{
            name: '销量',
            type: 'bar',
            data: [
                <%--<c:forEach items="${map}" var="var" varStatus="vs">--%>
                <%--<c:if test="${var.value!=null}">--%>
                <%--"${var.value}"--%>
                <%--</c:if>--%>
                <%--</c:forEach>--%>
            ]
        }]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
</script>


	
	<script src="static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>


</body>
</html>