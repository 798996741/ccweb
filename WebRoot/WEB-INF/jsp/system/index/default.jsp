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

    <!-- jsp文件头和头部 -->
    <jsp:include page="../include/incJs.jsp"></jsp:include>
</head>
<body class="no-skin" style="background:rgb(236,240,245)">

<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">

    <section class="content" style="background:rgb(236,240,245)">

        <div class="row">

            <section class="content-header padbottom15">
                <h1><font><font>关注任务 </font></font></h1>
                <ol class="breadcrumb">
                    <li class="active"><font><font></font></font></li>
                </ol>
            </section>
            <div class="row card-row" style="padding-left:10px;">
                ${rwstr }
            </div>
            <%-- <div class="row padtop25">
              <div class="col-md-6">
                <!-- Line chart -->
                <div class="box box-primary">
                  <div class="box-header with-border">
                    <i class="fa fa-bar-chart-o"></i>

                    <h3 class="box-title">当日时段呼入</h3>
                    <div class="box-tools pull-right">
                      <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                      </button>
                      <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
                    </div>
                  </div>
                 <div class="box-body">
                    <div class="chart">
                      <canvas id="areaChart" style="height: 250px; width: 509px;" height="250" width="509"></canvas>
                    </div>
                  </div>
                  <!-- /.box-body-->
                </div>
              </div>
              <div class="col-md-6">
                <!-- Bar chart -->
                <div class="box box-primary">
                  <div class="box-header with-border">
                    <i class="fa fa-bar-chart-o"></i>

                    <h3 class="box-title">当日地区呼入</h3>

                    <div class="box-tools pull-right">
                      <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                      </button>
                      <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
                    </div>
                  </div>
                  <div class="box-body">
                    <div id="bar-chart" style="height: 300px;"></div>
                  </div>
                  <!-- /.box-body-->
                </div>

              </div>
               <div class="col-md-6">
                <!-- Line chart -->
                <div class="box box-primary">
                  <div class="box-header with-border">
                    <i class="fa fa-bar-chart-o"></i>

                    <h3 class="box-title">当日时段呼入</h3>

                    <div class="box-tools pull-right">
                      <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                      </button>
                      <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
                    </div>
                  </div>
                  <div class="box-body">
                    <div id="line-chart" style="height: 300px;"></div>
                  </div>
                  <!-- /.box-body-->
                </div>
              </div>
              <div class="col-md-6">
                <!-- Bar chart -->
                <div class="box box-primary">
                  <div class="box-header with-border">
                    <i class="fa fa-bar-chart-o"></i>

                    <h3 class="box-title">当日地区呼入</h3>

                    <div class="box-tools pull-right">
                      <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                      </button>
                      <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
                    </div>
                  </div>
                  <div class="box-body">
                    <div id="bar-chart" style="height: 300px;"></div>
                  </div>
                  <!-- /.box-body-->
                </div>

              </div> --%>
            <!-- /.col -->
        </div>

    </section>
</div>
<!-- /.main-container -->

<!-- basic scripts -->
<!-- 页面底部js¨ -->
<%@ include file="../index/foot.jsp" %>
<!-- ace scripts -->
<script src="static/ace/js/ace/ace.js"></script>
<!-- inline scripts related to this page -->

<script type="text/javascript" src="static/ace/js/jquery.js"></script>
</body>
</html>