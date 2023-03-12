<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.fh.util.Jurisdiction" %>
<%@ page import="com.fh.entity.system.User" %>
<%
    Jurisdiction jurisdiction = new Jurisdiction();
    User user = Jurisdiction.getLoginUser();
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
        div.content_wrap {
            width: 600px;
            height: 380px;
        }

        div.content_wrap div.left {
            float: left;
            width: 250px;
        }

        div.content_wrap div.right {
            float: right;
            width: 340px;
        }

        div.zTreeDemoBackground {
            width: 250px;
            height: 362px;
            text-align: left;
        }

        ul.ztree {
            margin-top: 10px;
            border: 1px solid #617775;
            background: #f0f6e4;
            width: 220px;
            height: 360px;
            overflow-y: scroll;
            overflow-x: auto;
        }

        ul.log {
            border: 1px solid #617775;
            background: #f0f6e4;
            width: 300px;
            height: 170px;
            overflow: hidden;
        }

        ul.log.small {
            height: 45px;
        }

        ul.log li {
            color: #666666;
            list-style: none;
            padding-left: 10px;
        }

        ul.log li.dark {
            background-color: #E3E3E3;
        }

        /* ruler */
        div.ruler {
            height: 20px;
            width: 220px;
            background-color: #f0f6e4;
            border: 1px solid #333;
            margin-bottom: 5px;
            cursor: pointer
        }

        div.ruler div.cursor {
            height: 20px;
            width: 30px;
            background-color: #3C6E31;
            color: white;
            text-align: right;
            padding-right: 5px;
            cursor: pointer
        }

        #menuTree {
            background: #af0000;
        }

        .seat-middle-top {
            margin-top: 0;
        }

        .seat-middle-top-left-tp select {
            height: 30px;
            margin-left: 10px;
            width: 150px;
        }

        .seat-middle-top-left-tp input {
            height: 30px;
            width: 150px;
        }

        .seat-middle-top-left-tp div {
            height: 35px;
        }
    </style>
<body class="no-skin" style="overflow-x:scroll;">
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper" style="margin-left:0px;">
    <section class="container-fluid">
        <c:if test="${empty pd.doaction}">
            <%if (jurisdiction.hasQx("70209")) { %>
            <div class="seat-middle-top flex-row-center-center" style="height:80px;line-height:25px;box-shadow: 4px 4px 6px #c7c7c7;">
                <div class="seat-middle-top-left" style="width:100%;height:80px;">

                    <form action="workorder/editFile.do" name="Form" id="Form_upload" method="post"
                          enctype="multipart/form-data" style="width: 100%">
                        <div class="flex-column">
                            <div class="row" style="line-height: 34px">
                                <div class="col-md-2"></div>
                                <div class="col-md-2" style="text-align: right">输入附件名称：</div>
                                <div class="col-md-2"><input class="detail-seat-input" type="hidden" name="uid" id="uid" value="${pd.uid}">
                                    <input class="detail-seat-input" type="text" name="filenames" id="filenames"></div>
                                <div class="col-md-2" style="text-align: right">选择文件：</div>
                                <div class="col-md-2"><input class="detail-seat-input" type="file" name="file" id="file" multiple="multiple"></div>
                                <div class="col-md-2"></div>
                            </div>
                            <div class="row flex-row-center-center">
								<button type="button" class="btn btn-default-sm button-blue width-120px height-30px"
										style="margin: 5px 5px 5px 15px;" onclick="saveFile();">
									<font>上传此附件</font>
								</button>
								<button type="button" class="btn btn-default-sm button-red width-120px height-30px"
										style="margin: 5px 5px 5px 15px;margin-left: 30px" onclick="parent.closeFileLayer();">
									<font>取消</font>
								</button>
                            </div>
                               
                        </div>
                    </form>

                </div>
                <!--  <div class="seat-middle-top-right">

                </div> -->
            </div>
            <%} %>
        </c:if>
        <div class="seat-middle" style="box-shadow: 4px 4px 6px #c7c7c7;">
            <div class="xtyh-middle-r zxzgl-middle-r">
                <table id="example2" class="table table-striped table-bordered table-hover" style="margin-top:5px;">
                    <thead>
                    <tr style="height:25px">
                        <th style="width:50px;">序号</th>
                        <th class="center">附件名称</th>
                        <th class="center">操作人</th>
                        <th class="center">所属部门</th>
                        <th class="center">操作时间</th>
                        <th class="center" style="width:50px;">操作</th>
                    </tr>
                    </thead>

                    <tbody>
                    <!-- 开始循环 -->
                    <c:choose>
                        <c:when test="${not empty varList}">
                            <c:forEach items="${varList}" var="var" varStatus="vs">
                                <tr>

                                    <td class='center cy_td' style="width: 30px;">${vs.count}</td>
                                    <td class='center'>${var.filename}</td>
                                    <td class='center'>${var.createman}</td>
                                    <td class='center'>${var.deptname}</td>
                                    <td class='center'>${var.createdate}</td>

                                    <td class="center">

                                        <div>
                                            <c:choose>
                                                <c:when test="${var.ext=='png'||var.ext=='PNG'||var.ext=='gif'||var.ext=='GIF'||var.ext=='jpg'||var.ext=='JPG'||var.ext=='JPEG'||var.ext=='jpeg'}">
                                                    <a title="查看"
                                                       onclick="imgShow('<%=path %>/uploadFiles/uploadFile/${var.file}')">
                                                        查看
                                                    </a>
                                                    &nbsp;
                                                    <a title="下载"
                                                       href="<%=path %>/workorder/downLoadFile.do?id=${var.id}">
                                                        下载
                                                    </a>
                                                </c:when>
                                                <c:otherwise>&nbsp;
                                                    <a title="下载"
                                                       href="<%=path %>/workorder/downLoadFile.do?id=${var.id}">
                                                        下载
                                                    </a>
                                                </c:otherwise>
                                            </c:choose>
                                            <c:if test="${empty pd.doaction}">
                                                <a style="margin-left:10px;" onclick="delfile('${var.id}');"> 删除</a>
                                            </c:if>
                                        </div>
                                    </td>
                                </tr>

                            </c:forEach>

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

    <!-- /.content -->
</div>

<div id="outerdiv"
     style="position:fixed;top:0;left:0;background:rgba(0,0,0,0.7);z-index:2;width:100%;height:100%;display:none;">
    <div id="innerdiv" style="position:absolute;">
        <img id="max_img" style="border:5px solid #fff;" src=""/>
    </div>
</div>

<!-- /.main-container -->

<!-- basic scripts -->
<!-- 页面底部js¨ -->
<%@ include file="../../system/include/incJs_foot.jsp" %>
<!-- 删除时确认窗口 -->
<script src="static/ace/js/bootbox.js"></script>
<!-- ace scripts -->
<script src="static/ace/js/ace/ace.js"></script>
<!-- 下拉框 -->
<script src="static/ace/js/chosen.jquery.js"></script>
<!--提示框-->
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
<script type="text/javascript">
    //$(top.hangge());//关闭加载状态
    //检索
    
    var msg='${msg}';
    if("filetypeError"==msg){
    	alert("上传的文件格式类型不正确，请检查");
    }
    
    function search() {
        $("#Form").submit();
    }

    function delfile(id) {
        bootbox.confirm("确定要删除文件吗?", function (result) {
            if (result) {
                var url = "<%=basePath%>workorder/deleteFile.do?id=" + id + "&tm=" + new Date().getTime();
                $.get(url, function (data) {
                    location.href = window.location;
                });
            }
        });
    }

    function imgShow(src) {
        outerdiv = "#outerdiv";
        innerdiv = "#innerdiv";
        max_img = "#max_img";
        //var src = _this.attr("src");//获取当前点击的min_img元素中的src属性
        $("#max_img").attr("src", src);//设置#max_img元素的src属性

        /*获取当前点击图片的真实大小，并显示弹出层及大图*/
        $("<img/>").attr("src", src).load(function () {
            var windowW = $(window).width();//获取当前窗口宽度
            var windowH = $(window).height();//获取当前窗口高度
            var realWidth = this.width;//获取图片真实宽度
            var realHeight = this.height;//获取图片真实高度
            var imgWidth, imgHeight;
            var scale = 0.9;//缩放尺寸，当图片真实宽度和高度大于窗口宽度和高度时进行缩放

            if (realHeight > windowH * scale) {//判断图片高度
                imgHeight = windowH * scale;//如大于窗口高度，图片高度进行缩放
                imgWidth = imgHeight / realHeight * realWidth;//等比例缩放宽度
                if (imgWidth > windowW * scale) {//如宽度扔大于窗口宽度
                    imgWidth = windowW * scale;//再对宽度进行缩放
                }
            } else if (realWidth > windowW * scale) {//如图片高度合适，判断图片宽度
                imgWidth = windowW * scale;//如大于窗口宽度，图片宽度进行缩放
                imgHeight = imgWidth / realWidth * realHeight;//等比例缩放高度
            } else {//如果图片真实高度和宽度都符合要求，高宽不变
                imgWidth = realWidth;
                imgHeight = realHeight;
            }
            $("#max_img").css("width", imgWidth);//以最终的宽度对图片缩放

            var w = (windowW - imgWidth) / 2 - 100;//计算图片与窗口左边距
            var h = (windowH - imgHeight) / 2 + 100;//计算图片与窗口上边距
            $(innerdiv).css({"top": h, "left": w});//设置#innerdiv的top和left属性
            $(outerdiv).fadeIn("fast");//淡入显示#outerdiv及.pimg
        });

        $(outerdiv).click(function () {//再次点击淡出消失弹出层
            $(this).fadeOut("fast");
        });
    }

    function saveFile() {
        //alert('${types}');
        var str = "";
        var filetypes =[".jpg",".gif",".jpeg",".png",".rar",".txt",".zip",".doc",".ppt",".xls",".pdf",".docx",".xlsx"];   
        var filepath =$("#file").val();
        if ($("#filenames").val() == '') {
            layer.alert("请输入附件名称！");
            return false;
        }
        if ($("#file").val() == '') {
            layer.alert("请选择要上传的文件！");
            return false;
        }
        if(filepath){   
        	var isnext = false;   
        	var fileend = filepath.substring(filepath.indexOf("."));   
        	if(filetypes && filetypes.length>0){   
        		for(var i =0; i<filetypes.length;i++){   
        			if(filetypes[i]==fileend){   
        				isnext = true;   
        				break;   
        			}   
        		}   
        	}   
        	if(!isnext){   
        		alert("文件格式不正确！");    
        		return false;   
        	}   
        }  

        $('#Form_upload').submit();
    }
</script>
</body>
</html>