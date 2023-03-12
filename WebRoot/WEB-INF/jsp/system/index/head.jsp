<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<link rel="stylesheet" href="static/login/css/common.css"/>
<link rel="stylesheet" href="static/login/css/main.css"/>
<link rel="stylesheet" href="static/layer/theme/default/layer.css" media="all">
<script src="static/ace/js/base-modal.js"></script>
<script src="static/layer/layer.js"></script>
<script>
    window.onload = function () {
        //alert($("[data-toggle='offcanvas']"));
        $("[data-toggle='offcanvas']").click();

    }

    function fu() {
        var width = $('.main-sidebar').width();
        console.log(width);
        if (width === 195) {
            $('.seat-img').css("padding", "20px 0 10px 0");
            $('.sidebar-menu>li>a').css("padding-left", "15px");
            $(".img-circle").css("width", "100%");
            $(".logo-mini").css("display", "none");
            $("#but").css({"position": "relative", "left": "-41px", "top": "0"});
            $(".treeview-menu > li").css("line-height", "30px");
            $(".treeview-menu").css("padding-left", "10px");
            $(".js-rw").css("width", "183px");
        } else {
            $('.seat-img').css("padding", "10px");
            $('.sidebar-menu>li>a').css("padding-left", "40px");
            $(".img-circle").css("width", "60px");
            $("#but").css({"position": "relative", "left": "0", "top": "0"});
            $(".treeview-menu > li").css("line-height", "54px");
            $(".treeview-menu").css("padding-left", "40px");
            $(".js-rw").css("width", "180px");
        }
    }
    
    var gdlayer;

    function closeLayer() {
        layer.close(gdlayer);//关闭当前页
    }
    
    
    
  //修改个人资料
    function editUserH(){
        var winId = "userWin";
        var title = '';
        //alert($("#mr-background").height());
        modals.openWin({
            winId: winId,
            title: '密码修改',
            width: '450px',
            height:'250px',
            url: '<%=basePath%>/user/goEditMyU.do'
        });
    	<%--gdlayer = layer.open({--%>
        <%--    type: 2,--%>
        <%--    title: "密码修改",--%>
        <%--    shade: 0.5,--%>
        <%--    skin: 'demo-class',--%>
        <%--    area: ['450px', '250px'],--%>
        <%--    content: "<%=basePath%>/user/goEditMyU.do"--%>
        <%--});--%>
    	
    }
</script>


<header class="main-header">

    <!-- Logo -->
    <a href="javascript:void(0)" class="logo">
        <span class="logo-mini"><b>A</b>LT</span>
        <span class="logo-lg" style="padding-left: 20px;line-height: 18px;margin-top: 3px;">美兰机场<br/>客户服务平台</span>
    </a>

    <!-- 头部导航 -->
    <nav class="navbar navbar-static-top" role="navigation">
        <!-- 侧边栏切换按钮 -->
        <a href="#" class="sidebar-toggle" id="but" data-toggle="push-menu" role="button">
            <span class="sr-only">Toggle navigation</span>
        </a>
        <!--侧边搜索框-->
        <form action="#" method="get">
            <div class="seat-sk">
                <input placeholder="请输入电话号码">
                <a href="javascript:void(0)"><img src="static/login/images/icon_09.png"></a>
            </div>
        </form>
        <!-- 右边导航菜单 -->
        <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
                
                <li class="dropdown dropdown-sz seat-li-rt">
                    <a onclick="editUserH();" href="javascript:;"  class="dropdown-toggle dropdown-sz-tp" data-toggle="dropdown">密码修改</a>
                </li>
                <li class="dropdown dropdown-tc seat-li-rt">
                    <a href="logout.do" class="dropdown-toggle dropdown-tc-tp">退出</a>
                </li>
            </ul>
        </div>
    </nav>
</header>