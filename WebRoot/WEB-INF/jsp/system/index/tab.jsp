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
    <script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="plugins/tab/js/framework.js"></script>
    <link href="plugins/tab/css/import_basic.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" id="skin" prePath="plugins/tab/"/>
    <!--默认相对于根目录路径为../，可添加prePath属性自定义相对路径，如prePath="<%=request.getContextPath()%>"-->
    <script type="text/javascript" charset="utf-8" src="plugins/tab/js/tab.js"></script>
    <style>
        .rightmenu {
            position: absolute;
            width: 80px;
            z-index: 9999;
            display: none;
            background-color: #fff;
            padding: 2px;
            color: #333;
            border: 1px solid #eee;
            border-radius: 2px;
            cursor: pointer;
        }

        .rightmenu li {
            text-align: center;
            display: block;
            height: 25px;
            line-height: 25px;
        }

        .rightmenu li:hover {
            background-color: #666;
            color: #fff;
        }
    </style>
</head>
<body style="background:rgb(236,240,245);padding:0px;margin:0px;">
<div id="tab_menu" style="margin:0px;padding:0px;"></div>
<div class="layui-tab" lay-filter="demo" lay-allowclose="true" style="margin-left: 10px;">
    <ul class="layui-tab-title"></ul>
    <ul class="rightmenu">
        <!--<li data-type="closeOther">关闭其他</li>-->
        <li data-type="closeall">关闭所有</li>
    </ul>
    <div class="layui-tab-content"></div>
</div>
<div style="width:100%;">
    <div id="page" style="width:100%;height:100%;"></div>
</div>
</body>
<script type="text/javascript">

    $(document).on('click', '.tab_item', function () {

        $(this).addClass('tab-active').siblings('table').removeClass('tab-active');
    })


    /* $(document).ready(function () {
        $('.tab_item').on('click', function(e){

        })
    }) */
    function tabAddHandler(mid, mtitle, murl) {
        //alert(mid);
        tab.update({
            id: mid,
            title: mtitle,
            url: murl,
            isClosed: true
        });
        tab.add({
            id: mid,
            title: mtitle,
            url: murl,
            isClosed: true
        });

        tab.activate(mid);
        //CustomRightClick(mid);
    }

    var tab;
    $(function () {
        tab = new TabView({
            containerId: 'tab_menu',
            pageid: 'page',
            cid: 'tab1',
            position: "top"
        });
        tab.add({
            id: 'tab1_index1',
            title: "主页",
            url: "<%=basePath%>workorder/gdcenter.do?type=all",
            isClosed: false
        });
        /**tab.add( {
		id :'tab1_index1',
		title :"主页",
		url :"/per/undoTask!gettwo",
		isClosed :false
	});
         **/



        //就需要给右键添加功能
        $(".rightmenu li").click(function () {
            if ($(this).attr("data-type") == "closeOther") {//关闭其他

            } else if ($(this).attr("data-type") == "closeall") {//关闭所有
                parent.document.getElementById("mainFrame").src = "<%=basePath%>tab.do";
            }
            $('.rightmenu').hide();
        });

    });
    window.oncontextmenu = function (e) {
        //取消默认的浏览器自带右键 很重要！！
        e.preventDefault();

        var popupmenu = $(".rightmenu");
        popupmenu.find("li").attr("data-id", "d");
        l = ($(document).width() - e.clientX) < popupmenu.width() ? (e.clientX - popupmenu.width()) : e.clientX;
        t = ($(document).height() - e.clientY) < popupmenu.height() ? (e.clientY - popupmenu.height()) : e.clientY;
        popupmenu.css({left: l, top: t}).show();
    }
    //关闭右键菜单，很简单
    window.onclick = function (e) {

//用户触发click事件就可以关闭了，因为绑定在window上，按事件冒泡处理，不会影响菜单的功能
        $('.rightmenu').hide();
    }


    function cmainFrameT() {
        var hmainT = document.getElementById("page");
        var bheightT = document.documentElement.clientHeight;
        hmainT.style.width = '100%';
        hmainT.style.height = (bheightT - 41) + 'px';
    }

    cmainFrameT();
    window.onresize = function () {
        cmainFrameT();
    };

</script>
</html>

