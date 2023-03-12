<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <!-- jsp文件头和头部 -->
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

        .redState {
            font-weight: bolder;
            color: #ff7776;
        }

        .orangeState {
            font-weight: bolder;
            color: #f8b62d;
        }

        .blueState {
            font-weight: bolder;
            color: #5fade3;
        }

        .greenState {
            font-weight: bolder;
            color: #8fc31f;
        }

        body .demo-class .layui-layer-title {
            background: #4a9ed8;
            color: #fff;
            border: none;
            border-top-left-radius: 15px;
            border-top-right-radius: 15px;
        }

        body .demo-class .layui-layer-btn {
            border-top: 1px solid #E9E7E7
        }

        body .demo-class .layui-layer-btn a {
            background: #fff;
        }

        body .demo-class .layui-layer-btn .layui-layer-btn1 {
            background: #999;
        }

        body .demo-class .layui-layer-setwin {
            background: url(static/login/images/new-gb.png) no-repeat center;
            background-size: 85%;
            top: 5px !important;
            width: 35px;
            height: 35px;
        }

        body .demo-class .layui-layer-setwin a {
            background: rgba(0, 0, 0, 0);
            width: 35px;
            height: 35px;
            top: 5px;
        }
        .border-style{
        	margin:5px;
        }
    </style>
<body class="no-skin">
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper"
     <c:if test="${empty pd.doaction }">style="width:100%;margin-left:0px;"</c:if>
     <c:if test="${not empty pd.doaction }">style="width:100%;margin-left:0px;"</c:if>>
    <section class="container-fluid">
        <div class="seat-middle-top-x"></div>
        <div class="seat-middle-top" id="autoheight"
             style="height: 120px;line-height: 25px;box-shadow: 4px 4px 6px #c7c7c7;">
            <div class="seat-middle-top-left">
                <div class="seat-middle-top-left-tp menu-top"
                     <c:if test="${empty pd.doaction }">style="width:1111px;margin: 10px 0"</c:if>>
                    <form action="workorder/list.do?doaction=${pd.doaction}&type=${pd.type}" method="post" name="Form"
                          id="Form">
                        <div class="flex-row-between" style="position:relative;">
                            <div class="flex-row-wrap">
                                <div class="border-style">
                                    <span class="border-size">投诉来源</span>
                                    <div class="border-line"></div>
                                    <select class="seat-select" id="tssources" name="tssources">
                                        <option value="">全部</option>
                                        <c:forEach items="${tssourceList}" var="var" varStatus="vs">
                                            <option value="${var.DICTIONARIES_ID }"
                                                    <c:if test="${pd.tssource==var.DICTIONARIES_ID}">selected</c:if>>${var.NAME }</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="flex-row-center">
                                    <div class="border-style" style="margin-right: 0px;">
                                        <span class="border-size">开始日期</span>
                                        <div class="border-line"></div>
                                        <input style="margin:0px" class="date-picker" name="starttime" id="starttime"
                                               autoComplete="off" title="开始时间"
                                               placeholder="开始时间" value="${pd.starttime}" type="text"
                                               data-date-format="yyyy-mm-dd"/>
                                    </div>
                                    <span class="date-line">—</span>
                                    <div class="border-style" style="margin-left: 0px;">
                                        <span class="border-size">结束日期</span>
                                        <div class="border-line"></div>
                                        <input style="margin:0px" class="date-picker" name="endtime" id="endtime"
                                               autoComplete="off"
                                               title="结束时间"
                                               placeholder="结束时间" value="${pd.endtime}" type="text"
                                               data-date-format="yyyy-mm-dd"/>
                                    </div>
                                </div>
                                <div class="border-style">
                                    <span class="border-size">投&nbsp;诉&nbsp;人</span>
                                    <div class="border-line"></div>
                                    <input class="seat-input" placeholder="投诉人" name="tsmans" id="tsmans"
                                           value="${pd.tsman }">
                                </div>
                                <div class="border-style">
                                    <span class="border-size">投诉部门</span>
                                    <div class="border-line"></div>
                                    <select class="seat-select" id="tsdepts" name="tsdepts">
                                        <option value="">全部</option>
                                        <c:forEach items="${tsdeptList}" var="var" varStatus="vs">
                                            <option value="${var.AREA_CODE }"
                                                    <c:if test="${pd.tsdept==var.AREA_CODE}">selected</c:if>>${var.NAME }</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="border-style">
                                    <span class="border-size">投诉类别(大项)</span>
                                    <div class="border-line"></div>
                                    <select class="seat-select width-160px" id="bigtstypes" name="bigtstypes"
                                            onchange="tstypechanges()">
                                        <option value="">请选择投诉类别</option>
                                        <c:forEach items="${tstypeLists}" var="var" varStatus="vs">
                                            <option value="${var.DICTIONARIES_ID }"
                                                    <c:if test="${pd.bigtstypes==var.DICTIONARIES_ID}">selected</c:if>>${var.NAME }</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="border-style">
                                    <span class="border-size">投诉类别(细项)</span>
                                    <div class="border-line"></div>
                                    <%--                                <span id="tstypespans"></span>--%>
                                    <select class="seat-select width-160px" id="tstypes" name="tstypes"
                                            onchange="">
                                        <%--                                        <option value="">请选择投诉类别</option>--%>
                                        <%--                                        <c:forEach items="${tstypeLists}" var="var" varStatus="vs">--%>
                                        <%--                                            <option value="${var.DICTIONARIES_ID }"--%>
                                        <%--                                                    <c:if test="${pd.bigtstypes==var.DICTIONARIES_ID}">selected</c:if>>${var.NAME }</option>--%>
                                        <%--                                        </c:forEach>--%>
                                    </select>
                                </div>
                                <div class="border-style">
                                    <span class="border-size">投诉分类</span>
                                    <div class="border-line"></div>
                                    <select class="seat-select width-110px" id="tsclassifys" name="tsclassifys">
                                        <option value="">全部</option>
                                        <c:forEach items="${tsclassifyList}" var="var" varStatus="vs">
                                            <option value="${var.DICTIONARIES_ID }"
                                                    <c:if test="${pd.tsclassify==var.DICTIONARIES_ID}">selected</c:if>>${var.NAME }</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="border-style">
                                    <span class="border-size">工单状态</span>
                                    <div class="border-line"></div>
                                    <select class="seat-select width-110px" id="types" name="types">
                                        <option value="">全部</option>
                                        <option value="0"
                                                <c:if test="${pd.types=='0'}">selected</c:if>>
                                            待反馈
                                        </option>
                                        <option value="1"
                                                <c:if test="${pd.types=='1'}">selected</c:if>>
                                            待确认
                                        </option>
                                        <option value="2"
                                                <c:if test="${pd.types=='2'}">selected</c:if>>
                                            工单已分派
                                        </option>
                                        <option value="3"
                                                <c:if test="${pd.types=='3'}">selected</c:if>>
                                            正常为您处理
                                        </option>
                                        <option value="4" <c:if test="${pd.types=='4'}">selected</c:if>>
                                            工单已关闭
                                        </option>
                                        <option value="5" <c:if test="${pd.types=='5'}">selected</c:if>>
                                            工单退回
                                        </option>
                                    </select>
                                </div>
                                <div class="border-style">
                                    <span class="border-size">是否计入指标</span>
                                    <div class="border-line"></div>
                                    <select class="seat-select width-110px" id="istargets" name="istargets">
                                        <option value="">全部</option>
                                        <option value="0" <c:if test="${pd.istargets=='0'}">selected</c:if>>
                                           	否
                                        </option>
                                        <option value="1" <c:if test="${pd.istargets=='1'}">selected</c:if>>
                                          	是
                                        </option>
                                    </select>
                                </div>
                                <div class="border-style">
                                    <span class="border-size">投诉编号</span>
                                    <div class="border-line"></div>
                                    <input class="seat-input  width-110px" placeholder="投诉编号" name="codes" id="codes"
                                           value="${pd.code }">
                                    <input type="hidden" name="type" id="type" value="${pd.type }">
                                    <input type="hidden" name="dpf" id="dpf" value="${pd.dpf }">
                                </div>
                                <div class="border-style">
                                    <span class="border-size">结束原因</span>
                                    <div class="border-line"></div>
                                    <input class="seat-input width-110px" placeholder="结束原因" name="endreasons" id="endreasons"
                                           value="${pd.endreason }">
                                </div>
                                <div class="border-style">
                                    <span class="border-size">综合查询</span>
                                    <div class="border-line"></div>
                                    <input class="seat-input width-110px" placeholder="综合查询" name="keywords" id="keywords"
                                           value="${pd.keywords }">
                                </div>
                                <button type="button" class="btn btn-default-sm button-blue width-65px"
                                        style="margin: 5px 5px 5px 15px;" onclick="search();">
                                    <font>查&nbsp;询</font>
                                </button>
                            </div>
                            <div class="flex-row-center" style="position: absolute;right: 0px;top:5px">
                                <c:if test="${empty pd.doaction }">
                                    <%if (jurisdiction.hasQx("70201")) { %>
                                    <div class="top-add top-img" onclick="add();">
                                        <font class="top-img-size">新增</font>
                                    </div>
                                    <%} %>
                                    <%if (jurisdiction.hasQx("70204")) { %>
                                    <div class="top-export top-img" onclick="toExcel();">
                                        <font class="top-img-size">导出</font>
                                    </div>
                                    <%} %>
                                </c:if>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <!--  <div class="seat-middle-top-right">

            </div> -->
        </div>
        <div class="seat-middle">
        <div class="xtyh-middle-r zxzgl-middle-r">
            <table id="example2" class="table table-striped table-bordered table-hover"
                   style="margin-top:5px;display: block;overflow: auto">
                <thead>
                <tr style="white-space: nowrap;">
                    <th class="center cy_th" style="width:110px;">投诉编号</th>
                    <th id='cy_thk'></th>
                    <th class="center" style="width: 100px">日期</th>
                    <th class="center" style="width: 100px">投诉来源</th>
                    <th class="center" style="width: 100px">投诉人</th>
                    <c:if test="${empty pd.doaction }">
                        <th class="center">联系方式</th>
                    </c:if>
                    <th class="center" style="width: 150px">投诉内容
                    </th>
                    <c:if test="${empty pd.doaction }">
                        <th class="center">投诉等级</th>
                    </c:if>
                    <th class="center">投诉部门</th>
                    <!-- <th class="center">投诉类别（大项）</th> -->
                    <th class="center" style="width: 150px">投诉类别（细项）</th>
                    <c:if test="${empty pd.doaction }">
                        <th class="center">投诉分类</th>
                        <th class="center">受理人</th>
                        <th class="center">顾客回访情况</th>
                        <th class="center" style="width: 160px">结束时间</th>
                        <th class="center">处理节点</th>
                        <th class="center">工单状态</th>
                        <th class="center" style="width: 150px !important;">结束原因</th>
                        <th class="center">是否计入指标</th>
                    </c:if>
                    <th class="center" style="width:50px;">操作</th>
                </tr>
                </thead>
                <tbody>
                <!-- 开始循环 -->
                <c:choose>
                    <c:when test="${not empty varList}">
                        <c:forEach items="${varList}" var="var" varStatus="vs">
                            <tr style="white-space: nowrap">

                                <td class='center cy_td' style="width:110px;">${var.code}</td>
                                <td id='cy_thk'></td>
                                <td class='center'>${fn:substring(var.tsdate,0,10)}</td>
                                <td class='center'>${var.tssourcename}</td>
                                <td class='center'>${var.tsman}</td>
                                <c:if test="${empty pd.doaction }">
                                    <td class='center'>${var.tstel}</td>
                                </c:if>
                                <td class='center'
                                    style="width: 150px;max-width: 150px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">
                                        <span class="td-content" title="${var.tscont}"
                                              style="width: 150px;max-width: 150px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">${var.tscont}</span>
                                </td>
                                <c:if test="${empty pd.doaction }">

                                    <td class="flex-row-center-center">
                                        <c:if test="${var.iscs == '1' }">  
                                            <img src="static/img/workorder/center_delay.png" class="urgentGrade"/>
                                        </c:if>
                                           <c:if test="${var.tslevelname == '高级' }">
                                               <img src="static/img/workorder/high.png" class="complaintGrade"/>
                                           </c:if>
                                           <c:if test="${var.tslevelname == '紧急' }">
                                               <img src="static/img/workorder/center_urgent.png"
                                                    class="urgentGrade"/>
                                           </c:if>
                                           <c:if test="${var.tslevelname == '普通' }">
                                               <img src="static/img/workorder/low.png" class="complaintGrade"/>
                                           </c:if>

                                    </td>
                                </c:if>
                                <td class='center'>${var.tsdeptname}</td>
                                <td class='center'>${var.tstypename}</td>
                                <c:if test="${empty pd.doaction }">
                                    <td class='center'>${var.tsclassifyname}</td>
                                    <td class='center'>${var.dealman}</td>
                                    <td class='center'>
                                        <c:if test="${var.ishf == '0' ||empty var.ishf}">否</c:if>
                                        <c:if test="${var.ishf == '1' }">是</c:if>
                                    </td>
                                    <td class='center'>${var.endtime}</td>
                                    <td class='center'>${var.cljdname}</td>
                                    <td class='center'>
                                        <c:if test="${var.type == 0 }"><span class="orangeState">待反馈</span></c:if>
                                        <c:if test="${var.type == 1 }"><span
                                                class="blueState">工单已分派</span></c:if>
                                        <c:if test="${var.type == 3||var.type ==2 }"><span class="greenState">正常为您处理</span></c:if>
                                        <c:if test="${var.type == 4 }"><span class="redState">工单已关闭</span></c:if>
                                        <c:if test="${var.type == 5 }"><span class="redState">工单退回</span></c:if>
                                    </td>
                                    <td class='center'
                                        style="width: 150px;max-width: 150px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">
                                        <c:if test="${not empty var.endreason}">
                                            ${var.endreason}
                                        </c:if>
                                        <c:if test="${empty var.endreason&&var.type == 4}">
                                            闭环
                                        </c:if>
                                    </td>
                                    <td class='center'>
                                        <c:if test="${empty var.istarget || var.istarget=='0'}">
                                            否
                                        </c:if>
                                        <c:if test="${var.istarget == '1'}">
                                            是
                                        </c:if>
                                    </td>
                                </c:if>
                                <td class="center">

                                    <div class="flex-row-center-center">
                                    
                                        <c:if test="${empty pd.doaction }">
	                                        	<%if (jurisdiction.hasQx("70202")) { %>
												<c:if test="${pd.dpf=='1'&&var.isreceive=='0'}"> 
													<div class="content-look flex-row-acenter" title="接收"
		                                                 onclick="receive('${var.id}');">
		                                                <img src="static/img/workorder/look.png" width="20px"
		                                                     height="20px"/>
		                                                <font class="content-size">接收</font>
		                                            </div>
												
												</c:if>
												<c:if test="${var.isreceive=='1'&&pd.dpf=='1'}"> 
		                                            <div class="content-nedit flex-row-acenter" title="编辑"
		                                                 onclick="edit('${var.id}','');">
		                                                <img src="static/img/workorder/nedit.png" width="20px"
		                                                     height="20px"/>
		                                                <c:if test="${empty pd.dpf}">     
		                                                	<font class="content-size">编辑</font>
		                                                </c:if>
		                                                 <c:if test="${pd.dpf=='1'}">     
		                                                	<font class="content-size">派发</font>
		                                                </c:if>
		                                            </div>
	                                            </c:if>
	                                            <c:if test="${empty pd.dpf}"> 
		                                            <div class="content-nedit flex-row-acenter" title="编辑"
		                                                 onclick="edit('${var.id}','');">
		                                                <img src="static/img/workorder/nedit.png" width="20px"
		                                                     height="20px"/>
		                                                <c:if test="${empty pd.dpf}">     
		                                                	<font class="content-size">编辑</font>
		                                                </c:if>
		                                                 
		                                            </div>
	                                            </c:if>
                                            <%} %>
                                            <c:if test="${empty pd.dpf}">  
	                                            <%if (jurisdiction.hasQx("70208")) { %>
	                                            <div class="content-look flex-row-acenter" title="查看"
	                                                 onclick="edit('${var.id}','search');">
	                                                <img src="static/img/workorder/look.png" width="20px"
	                                                     height="20px"/>
	                                                <font class="content-size">查看</font>
	                                            </div>
	                                            <%} %>
	
	
	                                            <%if (jurisdiction.hasQx("70203")) { %>
	                                            <div class="content-ndelete flex-row-acenter" title="删除"
	                                                 onclick="del('${var.id}');">
	                                                <img src="static/img/workorder/ndelete.png" width="20px"
	                                                     height="20px"/>
	                                                <font class="content-size">删除</font>
	                                            </div>
	                                            <%} %>
	                                            <%if (jurisdiction.hasQx("70203")) { %>
	                                            <c:if test="${empty var.istarget || var.istarget=='0'}">
	                                                <div class="content-nedit flex-row-acenter" title="纳入指标"
	                                                     onclick="target('${var.id}','1');">
	                                                    <img src="static/img/workorder/nedit.png" width="20px"
	                                                         height="20px"/>
	                                                    <font class="content-size">纳入指标</font>
	                                                </div>
	                                            </c:if>
	                                            <c:if test="${var.istarget == '1'}">
	                                                <div class="content-ndelete flex-row-acenter" title="取消指标"
	                                                     onclick="target('${var.id}','0');">
	                                                    <img src="static/img/workorder/ndelete.png" width="20px"
	                                                         height="20px"/>
	                                                    <font class="content-size">取消指标</font>
	                                                </div>
	                                            </c:if>
	                                            <%} %>
	                                        </c:if>
                                        </c:if>
                                        <c:if test="${not empty pd.doaction}">

                                            <div class="content-nedit flex-row-acenter" title="选择"
                                                 onclick="choicecode('${var.code}');">
                                                <img src="static/img/workorder/nedit.png" width="20px"
                                                     height="20px"/>
                                                <font class="content-size">选择</font>
                                            </div>
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

<!-- /.main-container -->

<!-- basic scripts -->
<!-- 页面底部js¨ -->
<%@ include file="../../system/include/incJs_foot.jsp" %>
<script type="text/javascript">
    //获取浏览页面可视区域的宽度改变菜单栏宽度
    window.onload = function () {
        document.getElementsByClassName('menu-top')[0].style.width = document.documentElement.clientWidth - 60 + 'px'
        if (document.documentElement.clientWidth > 1600) {
            document.getElementById('autoheight').style.height = Number(document.getElementById('autoheight').style.height.substring(0, 3)) - 40 + 'px'
        } else {
            if (document.documentElement.clientWidth > 1400) {
                document.getElementsByClassName('menu-top')[0].style.width = "1345px"
            } else {
                document.getElementsByClassName('menu-top')[0].style.width = "1111px"
            }
        }
    }
    //$(top.hangge());//关闭加载状态
    //检索
    var tables;

    function sendorder(id, code, uid, type, cljd, tssource, tsqd, tsdate, tssq, tscont, bigtstype, tstype, tsclassify, tslevel, tsdept, clsx, tsman, tstel, email, cardtype, cardid, cjdate, hbh, deport, arrport, endreason, cfbm) {

        var doaction = $("#doaction").val('1');
        $.ajax({
            //几个参数需要注意一下
            type: "POST",//方法类型
            dataType: "json",//预期服务器返回的数据类型
            url: "workorder/save.do",//url
            processData: false,
            data: {
                id: id,
                doaction: doaction,
                code: code,
                uid: uid,
                type: type,
                cljd: cljd,
                tssource: tssource,
                tsqd: tsqd,
                tsdate: tsdate,
                tssq: tssq,
                tscont: tscont,
                bigtstype: bigtstype,
                tstype: tstype,
                tsclassify: tsclassify,
                tslevel: tslevel,
                tsdept: tsdept,
                clsx: clsx,
                tsman: tsman,
                tstel: tstel,
                email: email,
                cardtype: cardtype,
                cardid: cardid,
                cjdate: cjdate,
                hbh: hbh,
                deport: deport,
                arrport: arrport,
                endreason: endreason,
                cfbm: cfbm
            },
            success: function (result) {
                console.log(result);//打印服务端返回的数据(调试用)
                if (result.success == 'true') {
                    console.log('执行成功')
                    // if (result.data != "" && typeof (result.data) != "undefined") {
                    //     handle(result.data);
                    //
                    // } else {
                    //     parent.search();
                    //     // closeLoading();
                    // }

                }
            },
            error: function () {
                alert("异常！");
            }
        });
    }

    function search() {
        //alert(tables.page.info().page);
        $("#Form").submit();
    }

    function choicecode(code) {
        parent.document.getElementById("cfbm").value = code;
        parent.closeCftsLayer();
    }

    $('.date-picker').datepicker({
        autoclose: true,
        todayHighlight: true,
        clearBtn: true
    });

    function tstypechanges() {
        var bigtstypes = $("#bigtstypes").val();
        if (bigtstypes == "") {
            $("#tstypes").html('');
            return false;
        }
        var tstypes = '${pd.tstypes}';
        //alert(tstypes);
        $.ajax({
            //几个参数需要注意一下
            type: "POST",//方法类型
            dataType: "json",//预期服务器返回的数据类型
            url: "workorder/getTstype.do?bigtstype=" + bigtstypes,//url
            success: function (result) {

                //var str = "<select id=\"tstypes\" name=\"tstypes\" >";
                var str = str + "<option value=\"\">请选择投诉分类细项</option>";
                $.each(result.list, function (i, list) {

                    if (tstypes == list.DICTIONARIES_ID) {
                        str = str + "<option value=\"" + list.DICTIONARIES_ID + "\" selected>" + list.NAME + "</option>";
                    } else {
                        str = str + "<option value=\"" + list.DICTIONARIES_ID + "\">" + list.NAME + "</option>";
                    }

                });
                //str = str + "</select>";
                $("#tstypes").html('');
                $("#tstypes").html(str);
            }
        });
    }

    tstypechanges();

    function tosearch() {

        var keywords = $("#keywords").val();
        location.href = "<%=path%>/workorder/list.do?action=${pd.action}&doctype_id=${pd.doctype_id}&keywords=" + encodeURI(encodeURI(keywords));
        //$("#Form_s").submit();
    }

    
    $(function () {
        if (screen.width > 1600) {
            $('#example2').DataTable({
                'paging': true,
                'lengthChange': false,
                'searching': false,
                'ordering': false,
                'info': true,
                'autoWidth': true,
                "iDisplayStart": 0 * 13,  //用于指定从哪一条数据开始显示到表格中去 num为20的时候，第一页是0，第二页是20.....
                "iDisplayLength": 13,
                // 'sScrollY': 'calc(100vh - 190px)',
            })
        } else if (screen.width > 1366) {
            $('#example2').DataTable({
                'paging': true,
                'lengthChange': false,
                'searching': false,
                'ordering': false,
                'info': true,
                'autoWidth': true,
                "iDisplayStart": 0 * 11,  //用于指定从哪一条数据开始显示到表格中去 num为20的时候，第一页是0，第二页是20.....
                "iDisplayLength": 11,
                // 'sScrollY': 'calc(100vh - 230px)',
            })
        } else {
            $('#example2').DataTable({
                'paging': true,
                'lengthChange': false,
                'searching': false,
                'ordering': false,
                'info': true,
                'autoWidth': true,
                "iDisplayStart": 0 * 10,  //用于指定从哪一条数据开始显示到表格中去 num为20的时候，第一页是0，第二页是20.....
                "iDisplayLength": 10,
                // 'sScrollY': 'calc(100vh - 230px)',
            })
        }
    })

    $(function () {
        $('.seat-middle').find(".row:first").hide();
        $(".xtyh-middle-r").find(".row:last").addClass("row-zg");
        $(".xtyh-middle-r").find(".row:eq(1)").addClass("row-two");
    });

    var gdlayer;

    function closeLayer() {
        layer.close(gdlayer);//关闭当前页
    }

    //新增
    function add() {
        var winId = "userWin";
        var type = '${pd.type}';
        gdlayer = layer.open({
            type: 2,
            title: "工单新增",
            shade: 0.5,
            skin: 'demo-class',
            area: ['100%', '100%'],
            content: '<%=basePath%>workorder/goAdd.do?type=' + type
        });
    }

    //删除
    function del(Id, Uid) {
        bootbox.confirm("确定要删除吗?", function (result) {
            if (result) {
                top.jzts();
                var url = "<%=basePath%>workorder/delete.do?id=" + Id + "&UID=" + Uid + "&tm=" + new Date().getTime();
                $.get(url, function (data) {
                    search();
                });
            }
        });
    }


    //修改
    function edit(Id, action) {

        var winId = "userWin";
        var type = '${pd.type}';
        var title = "";
        if (action != "") {
            title = "工单查看";

        } else {
            title = "工单处理";
            action = "";
        }
        gdlayer = layer.open({
            type: 2,
            title: title,
            shade: 0.5,
            skin: 'demo-class',
            area: ['100%', '100%'],
            content: "<%=basePath%>workorder/goEdit.do?search=" + action + "&type=" + type + "&id=" + Id
        });
    }


    function target(id, istarget) {
        $.ajax({
            type: 'post',
            url: "<%=basePath%>workorder/target.do?id=" + id + "&istarget=" + istarget,
            dataType: 'json',
            cache: false,
            success: function (obj) {
                var str = "";
                var data = eval(obj.data);
                if (obj.success == "true") {
                    layer.alert('操作成功');
                } else {
                    layer.alert('操作失败');
                }
                search();

            }, error: function (jqXHR, textStatus, errorThrown) {
                layer.alert('操作异常');
                return false;
            }
        });
    }
    
    
    function receive(id) {
    	bootbox.confirm("确定要接收吗?", function (result) {
	        $.ajax({
	            type: 'post',
	            url: "<%=basePath%>workorder/workReceive.do?id=" + id ,
	            dataType: 'json',
	            cache: false,
	            success: function (obj) {
	                if (obj.success == "true") {
	                    layer.alert('操作成功');
	                } else {
	                    layer.alert('操作失败');
	                }
	                search();
	
	            }, error: function (jqXHR, textStatus, errorThrown) {
	                layer.alert('操作异常');
	                return false;
	            }
	        });
    	});
    }

    function docsearch(id) {

        var winId = "userWin";

        gdlayer = layer.open({
            type: 2,
            title: title,
            shade: 0.5,
            skin: 'demo-class',
            area: ['100%', '100%'],
            content: "<%=basePath%>workorder/goEdit.do?action=search&id=" + id
        });

    }


    //导出excel
    function toExcel() {
        //$("#Form").action="<%=basePath%>workorder/excel.do";
        //$("#Form").submit();
        //var path = "<%=basePath%>workorder/excel.do";
        //$('#Form').attr("action", path).submit();
        window.location.href = '<%=basePath%>workorder/excel.do';
    }

    $(function () {
        $('.seat-middle').find(".row:first").hide();
    });
</script>
</body>
</html>