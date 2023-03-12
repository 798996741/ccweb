<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>系统后台管理</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <jsp:include page="../include/incJs.jsp"></jsp:include>

    <!--
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
    -->

    <link rel="stylesheet" href="static/login/css/common.css"/>
    <link rel="stylesheet" href="static/login/css/main.css"/>
    <script>
        function siMenu(id, fid, MENU_NAME, MENU_URL) {
            //alert(id);

            $("#" + fid).attr("class", "active open");
            $("#" + id).attr("class", "active");
            //document.getElementById("mainFrame").src = MENU_URL;

            //有标签的
            top.mainFrame.tabAddHandler(id, MENU_NAME, MENU_URL);
            //if(MENU_URL != "druid/index.html"){
            //jzts();
            //}
        };

        $(function () {
            $(".cy-bg-zlm>li").click(function () {
                // $(this).addClass("active").siblings("li").removeClass("active")
            })
        });

        $(function () {
            $("#but").click(function () {
                fu();
                $(this).addClass("sidebar-collapse")
            });
            $(".xtyh-page-r-li>li").click(function () {
                $(this).addClass("xtyh-page-r-li-activ").siblings("li").removeClass("xtyh-page-r-li-activ")
            })
        });

        /* function fu() {
            var width = $('.main-sidebar').width();
            console.log(width);
            if( width === 195){
                $('.seat-img').css("padding","20px 0 10px 0");
                $('.sidebar-menu>li>a').css("padding-left","15px");
                $(".img-circle").css("width","100%");
                $(".logo-mini").css("display","none");
                $("#but").css({"position":"relative","left":"-50px","top":"0"});
                $(".treeview-menu > li").css("line-height","30px");
                $(".treeview-menu").css("padding-left","10px");
                $(".js-rw").css("width","183px");
            }
            else{
                $('.seat-img').css("padding","10px");
                $('.sidebar-menu>li>a').css("padding-left","50px");
                $(".img-circle").css("width","104px");
                $("#but").css({"position":"relative","left":"0","top":"0"});
                $(".treeview-menu > li").css("line-height","54px");
                $(".treeview-menu").css("padding-left","50px");
                $(".js-rw").css("width","180px");
            }
        } */

    </script>

    <style type="text/css">
        .loading {
            width: 160px;
            height: 56px;
            position: absolute;
            top: 50%;
            left: 50%;
            line-height: 56px;
            color: #fff;
            padding-left: 60px;
            font-size: 15px;
            background: #000 url(static/images/load.gif) no-repeat 10px 50%;
            opacity: 0.7;
            z-index: 9999;
            -moz-border-radius: 20px;
            -webkit-border-radius: 20px;
            border-radius: 20px;
            filter: progid:DXImageTransform.Microsoft.Alpha(opacity=70);
        }
    </style>
</head>
<body class="hold-transition skin-blue sidebar-mini"
      style="background:#ecf0f5;">

<div class="wrapper">

    <%@ include file="head.jsp" %>
    <aside class="main-sidebar cyx-lm-bg">

        <!-- sidebar: style can be found in sidebar.less -->
        <section class="sidebar">


            <div class="user-panel">
                <div class="pull-left info seat-xtgly">
                    ${sessionUser.NAME}
                </div>
                <div class="pull-left image seat-img">
                    <img src="static/login/images/icon-touxiang.png" class="img-circle" alt="User Image">
                </div>
            </div>

            <!-- search form (Optional) -->

            <!-- /.search form -->

            <!-- Sidebar Menu -->
            <ul class="sidebar-menu cy-bs" data-widget="tree">
                <!-- <li class="header cy-cdl">菜单栏>><span style="font-size:12px;color:#5bb973;cursor:pointer" onclick="back_home()">首页</span></li> -->
                <!-- Optionally, you can add icons to the links -->

                <c:forEach items="${menuList}" var="menu1" varStatus="sta">
                    <c:if test="${menu1.hasMenu && '1' == menu1.MENU_STATE}">

                        <li
                                <c:if test="${sta.index==0}">class="active treeview"</c:if>
                                <c:if test="${sta.index!=0}"></c:if> >
                            <a style="cursor:pointer" class="cy-g-a">
                                <i class="${menu1.MENU_ICON == null ? 'menu-icon fa fa-leaf black' : menu1.MENU_ICON} cy-tb-bh"></i>
                                <span>${menu1.MENU_NAME }</span>
                                <c:if test="${'[]' != menu1.subMenu}">
                                    <i class="fa fa-angle-left pull-right"></i>
                                </c:if>
                            </a>
                            <ul class="treeview-menu  cy-bg-zlm">
                                <c:forEach items="${menu1.subMenu}" var="menu2">
                                    <c:if test="${menu2.hasMenu && '1' == menu2.MENU_STATE}">
                                        <li>
                                            <a onclick="siMenu('z${menu2.MENU_ID }','lm${menu1.MENU_ID }','${menu2.MENU_NAME }','${menu2.MENU_URL }')">
                                                <!-- <i class="fa fa-circle-o"></i> -->
                                                    ${menu2.MENU_NAME }</a>
                                        </li>
                                    </c:if>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:if>
                </c:forEach>
            </ul>
            <!-- /.sidebar-menu -->
        </section>
        <!-- /.sidebar -->
    </aside>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper" style="position: relative;">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1></h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
                <li class="active">Here</li>
            </ol>
        </section>

        <!-- Main content -->

        <div id="loading" class="loading">加载中...</div>
        <section
                style="margin:0px;padding:0px;position:absolute;top:0px;left:0px;right:0px;bottom:0px;background:#ecf0f5;overflow:hidden">
            <iframe name="mainFrame" id="mainFrame" onload="document.getElementById('loading').style.display='none'"
                    frameborder="0" src="<%=basePath%>tab.do"
                    style="margin:0px;padding:0px;width:100%;height:100%;display:block;background:#ecf0f5;scroll:none;overflow:hidden;"></iframe>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->


    <!-- Control Sidebar -->
    <aside class="control-sidebar control-sidebar-dark">
        <!-- Create the tabs -->
        <ul class="nav nav-tabs nav-justified control-sidebar-tabs">
            <li class="active">
                <a href="#control-sidebar-home-tab" data-toggle="tab">
                    <i class="fa fa-home"></i>
                </a>
            </li>
            <li>
                <a href="#control-sidebar-settings-tab" data-toggle="tab">
                    <i class="fa fa-gears"></i>
                </a>
            </li>
        </ul>
        <!-- Tab panes -->
        <div class="tab-content">
            <!-- Home tab content -->
            <div class="tab-pane active" id="control-sidebar-home-tab">
                <h3 class="control-sidebar-heading">Recent Activity</h3>
                <ul class="control-sidebar-menu">
                    <li>
                        <a href="javascript:;">
                            <i class="menu-icon fa fa-birthday-cake bg-red"></i>

                            <div class="menu-info">
                                <h4 class="control-sidebar-subheading">Langdon's Birthday</h4>
                                <p>Will be 23 on April 24th</p>
                            </div>
                        </a>
                    </li>
                </ul>
                <!-- /.control-sidebar-menu -->

                <h3 class="control-sidebar-heading">Tasks Progress</h3>
                <ul class="control-sidebar-menu">
                    <li>
                        <a href="javascript:;">
                            <h4 class="control-sidebar-subheading">
                                Custom Template Design
                                <span class="pull-right-container">
										<span class="label label-danger pull-right">70%</span>
									</span>
                            </h4>
                            <div class="progress progress-xxs">
                                <div class="progress-bar progress-bar-danger" style="width: 70%">
                                </div>
                            </div>
                        </a>
                    </li>
                </ul>
                <!-- /.control-sidebar-menu -->

            </div>
            <!-- /.tab-pane -->
            <!-- Stats tab content -->
            <div class="tab-pane" id="control-sidebar-stats-tab">Stats TabContent</div>
            <!-- /.tab-pane -->
            <!-- Settings tab content -->
            <div class="tab-pane" id="control-sidebar-settings-tab">
                <form method="post">
                    <h3 class="control-sidebar-heading">General Settings</h3>
                    <div class="form-group">
                        <label class="control-sidebar-subheading">
                            Report panel usage
                            <input type="checkbox" class="pull-right" checked>
                        </label>

                        <p>Some information about this general settings option</p>
                    </div>
                    <!-- /.form-group -->
                </form>
            </div>
            <!-- /.tab-pane -->
        </div>
    </aside>
    <!-- /.control-sidebar -->
    <div class="control-sidebar-bg"></div>
</div>
<!-- ./wrapper -->


<script>
    window.onload = function () {
        $(".treeview").removeClass("treeview")
    }
    $(".sidebar-menu > li>a").click(function () {
        menu_modify();
        $(this).parent().removeClass("menu-open").addClass("menu-open")
        // $(this).removeClass("active").addClass("active")
        $(this).children("ul").style.display = "block"
        // document.getElementsByClassName("sidebar-menu > li>ul")[0]
        // $(this).children("ul").css("display","block")
        // if($('.skin-blue .sidebar-menu > li:hover > a').css('background')==="#ff7875 !important"){
        // $(this).removeClass("menu-open active")
        // }

        // if ($('.sidebar-menu > li> ul').css('display') === "none") {
        //     $(this).css("display", "block")
        // } else {
        //     console.log()
        // }
            })

    function menu_modify() {
        $(".sidebar-menu > li").removeClass("menu-open active")
    }

    $(".sidebar-menu > li > a").click(function () {
        menu_modify();
        $(this).removeClass("menu-open active").addClass("menu-open active")
    })
    $(function () {
        $('.cy-bg-zlm a').click(function () {
            $(this).addClass('new-active').parent('li').siblings('li').children('a').removeClass('new-active');
        });
    });

    function back_home() {
        //top.mainFrame.tabAddHandler(id,MENU_NAME,MENU_URL);
        //top.mainFrame.tabAddHandler("tab1_index1","主页","<%=basePath%>tab.do");
        document.getElementById("mainFrame").src = "<%=basePath%>tab.do";
    }

    function jzts() {

    }
</script>
</body>
</html>