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
<!tstype html>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <%@ include file="../../system/include/incJs_mx.jsp" %>
    <!-- zTreeStyle -->
    <!-- 日期框 -->
    <link rel="stylesheet" href="static/ace/css/datepicker.css"/>
    <link rel="stylesheet" href="plugins/kindeditor/themes/default/default.css"/>
    <link rel="stylesheet" href="plugins/kindeditor/plugins/code/prettify.css"/>
    <style>
        .btnbm {
            width: 50px;
            height: 25px;
            line-height: 25px;
            padding: 0px;
            padding-left: 5px;
        }

        .btable, .btable tr th, .btable tr td {
            border: 1px solid #ccc;
            padding: 5px;
        }

        .btable {
            width: 100%;
            min-height: 25px;
            line-height: 25px;
            text-align: left;
            border-collapse: collapse;
            background: #fff;
        }

        .tdclass {
            text-align: right;
        }

        .tdtitle {
            text-align: center;
            background: #ffffff;
            border-bottom: 1px solid #5fade3 !important;
        }

        .content-header {
            height: 30px;
        }

        .seat-middle-top {
            margin-top: 0;
            box-shadow: 4px 4px 6px #c7c7c7;
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
    </style>

</head>
<body class="no-skin" style="background-color: #ecf0f5">
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container" style="overflow-x: auto;overflow-y: auto;height: 100%">
    <!-- /section:basics/sidebar -->
    <div class="flex-row-wrap-sbetween">
        <form action="workorder/save.do" name="Form_add" id="Form_add" method="post"
              enctype="multipart/form-data">
            <input type="hidden" name="id" id="id" value="${pd.id}"/>
            <input type="hidden" name="doaction" id="doaction"/>
            <input type="hidden" name="code" id="code" value="${pd.code}"/>
            <input type="hidden" name="uid" id="uid" value="${pd.uid}"/>
            <input type="hidden" name="type" id="type" value="${pd.type}"/>
            <input type="hidden" name="cljd" id="cljd" value="${pd.cljd}"/>


            <section class="content-header">
                <div class="seat-middle-top"
                     style="height:30px;line-height:25px;margin-top: 0px;box-shadow: 4px 4px 6px #c7c7c7;">
                    <div class="seat-middle-top-left">
                        <div class="seat-middle-top-left-bt">工单处理状况</div>
                    </div>
                </div>
    </div>
    </section>

    <div id="zhongxin" style="padding: 10px">
        <div class="table table-striped table-bordered table-hover "
             style="padding: 0px;width:100%;background-color: #ffffff;margin-bottom:0px;box-shadow: 4px 4px 6px #c7c7c7;border-radius: 5px;padding: 5px 0;">
            <div class="row" style="margin: 20px 0 10px 0">

                <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                    <span style="width:7px;">&nbsp;</span><span class="border-size"
                                                                style="text-align-last: right;display: inline-block;">投诉编号</span>
                    <input class="detail-seat-input" type="text"
                           name="code" id="code" value="${pd.code}" readonly title="投诉编码">
                </div>

                <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                    <span style="width:7px;color: red">*</span><span class="border-size"
                                                                     style="text-align-last: right;display: inline-block;">投诉来源</span>

                    <select class="detail-seat-select " id="tssource"
                            name="tssource">
                        <option value="">全部</option>
                        <c:forEach items="${tssourceList}" var="var" varStatus="vs">
                            <option value="${var.DICTIONARIES_ID }"
                                    <c:if test="${pd.tssource==var.DICTIONARIES_ID}">selected</c:if>>${var.NAME }</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                    <span style="width:7px;color: red"></span><span class="border-size"  style="text-align-last: right;display: inline-block;">投诉渠道</span>

                    <select class="detail-seat-select " id="tsqd"
                            name="tsqd">
                        <option value="">全部</option>
                        <c:forEach items="${tsqdList}" var="var" varStatus="vs">
                            <option value="${var.DICTIONARIES_ID }"
                                    <c:if test="${pd.tsqd==var.DICTIONARIES_ID}">selected</c:if>>${var.NAME }</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                    <span style="width:7px;color: red">*</span><span class="border-size"
                                                                     style="text-align-last: right;display: inline-block;">投诉日期</span>

                    <input class="detail-date-picker "
                           name="tsdate"
                           id="tsdate"
                           value="${pd.tsdate}" type="text" data-date-format="yyyy-mm-dd"
                           readonly="readonly" placeholder="投诉时间" title="投诉时间"/>
                </div>
            </div>

            <div class="row" style="margin: 10px 0">
                <div class="col-md-12 flex-row-acenter" style="white-space: nowrap">
                    <span style="width:7px;color: red"></span><span class="border-size"
                                                                    style="text-align-last: right;display: inline-block;">投诉诉求</span>
                    <input class="detail-seat-input "
                           name="tssq"
                           id="tssq"
                           value="${pd.tssq}" placeholder="这里输入投诉诉求" title="投诉诉求"
                           type="text">
                </div>
            </div>

            <div class="row" style="margin: 10px 0">
                <div class="col-md-12 flex-row-acenter" style="white-space: nowrap">
                    <span style="width:7px;color: red">*</span><span class="border-size"
                                                                     style="text-align-last: right;display: inline-block;">投诉内容</span>

                    <input class="detail-seat-input " type="textarea"
                           name="tscont"  rows="5"
                           id="tscont" value="${pd.tscont}" title="投诉内容" placeholder="请输入投诉内容">
                </div>
            </div>

            <div class="row" style="margin: 10px 0">
                <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                    <span style="width:7px;color: red">*</span><span class="border-size"
                                                                     style="text-align: center;line-height: 12px;text-align-last: right;display: inline-block;">投诉类别<br/>(大项)</span>

                    <select class="detail-seat-select " id="bigtstype"
                            name="bigtstype"
                            onchange="tstypechange()">
                        <option value="">请选择投诉类别</option>
                        <c:forEach items="${tstypeList}" var="var" varStatus="vs">
                            <option value="${var.DICTIONARIES_ID }"
                                    <c:if test="${pd.tsbigtype==var.NAME}">selected</c:if>>${var.NAME }</option>
                        </c:forEach>
                    </select>
                </div>


                <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                    <span style="width:7px;color: red">*</span><span class="border-size"
                                                                     style="text-align: center;line-height: 12px;text-align-last: right;display: inline-block;">投诉类别<br/>(细项)</span>

                    <%--                                <span id="tstypespans"></span>--%>
                    <select class="detail-seat-select " id="tstype"
                            name="tstype"
                    >
                        <option value="" >请选择投诉类别</option>
                    </select>
                    <%--                            <span  id="tstypespan"--%>
                    <%--                                    name="tstypespan">--%>
                    <%--                            </span>--%>
                </div>


                <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                    <span style="width:7px;"></span><span class="border-size"
                                                          style="text-align-last: right;display: inline-block;">投诉分类</span>

                    <select class="detail-seat-select " id="tsclassify"
                            name="tsclassify">
                        <c:forEach items="${tsclassifyList}" var="var" varStatus="vs">
                            <option value="${var.DICTIONARIES_ID }"
                                    <c:if test="${pd.tsclassify==var.DICTIONARIES_ID}">selected</c:if>>${var.NAME }</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                    <span style="width:7px;color: red">*</span><span class="border-size"
                                                                     style="text-align-last: right;display: inline-block;">投诉等级</span>

                    <select class="detail-seat-select " id="tslevel"
                            name="tslevel">
                        <c:forEach items="${tslevelList}" var="var" varStatus="vs">
                            <option value="${var.DICTIONARIES_ID }"
                                    <c:if test="${pd.tslevel==var.DICTIONARIES_ID}">selected</c:if>>${var.NAME }</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="row" style="margin: 10px 0 20px 0">
                <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                    <span style="width:7px;color: red">*</span><span class="border-size"
                                                                     style="text-align-last: right;display: inline-block;">投诉部门</span>

                    <div class="detail-seat-select ">
                        <input class="detail-seat-input" type="hidden" name="tsdept"
                               id="tsdept"
                               value="${pd.tsdept }">
                        <input class="detail-seat-input" style="border: 0;" id="tsdeptSel"
                               name=""
                               type="text" readonly
                               onclick="showTsdept(); return false;"/>
                        <div id="menuContent_dept" class="menuContent_dept"
                             style="position: absolute;display:none;background:#ffffff;z-index: 1;">
                            <div class="new-bc"
                                 style=" height:35px;padding-top:5px;border-top-left-radius:5px;border-top-right-radius:5px;border-left:1px #ccc solid;border-right:1px #ccc solid;border-top:1px #ccc solid ">
                                <a style=" width:50px;height:25px;line-height:25px;padding:0px;padding-left:10px;"
                                   class="btnbm" onclick="hideDept()">确定</a>&nbsp;&nbsp;&nbsp;&nbsp;<a
                                    style=" width:50px;height:25px;line-height:25px;padding:0px;padding-left:10px;"
                                    onclick="hideDept()">取消</a>
                            </div>
                            <ul id="treeDemo_dept" class="ztree"
                                style="margin-top:0; width:250px;border:1px #ccc solid"></ul>
                        </div>
                    </div>
                </div>
                
                <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                    <span style="width:7px;color: red">*</span>
                    <span class="border-size" style="text-align-last: right;display: inline-block;">部门分类</span>


                      <input class="detail-seat-input " type="text"
                           name="depttype"
                           id="depttype" value="${pd.depttype}" title="部门类型" placeholder="请输入部门类型">

                </div>

                <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                    <span style="width:7px;color: red">*</span><span class="border-size"
                                                                     style="text-align-last: right;display: inline-block;">是否回访</span>


                    <select class="detail-seat-select " id="tslevel"
                            name="tslevel">
                        <option value="1" <c:if test="${pd.ishf=='1'}">checked</c:if>>是</option>
                        <option value="0" <c:if test="${pd.ishf=='0'}">checked</c:if>>否</option>
                    </select>

                </div>

                <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                    <span style="width:7px;"></span><span class="border-size"
                                                          style="text-align-last: right;display: inline-block;">处理时限</span>

                    <select class="detail-seat-select " id="clsx"
                            name="clsx">
                        <option value="">请选择</option>
                        <option value="24H" <c:if test="${pd.clsx=='24H'}">selected</c:if>>
                            24H
                        </option>
                        <option value="48H" <c:if test="${pd.clsx=='48H'}">selected</c:if>>
                            48H
                        </option>
                        <option value="3D" <c:if test="${pd.clsx=='3D'}">selected</c:if>>
                            3D
                        </option>
                        <option value="7D" <c:if test="${pd.clsx=='7D'}">selected</c:if>>
                            7D
                        </option>
                        <option value="0" <c:if test="${pd.clsx=='0'}">selected</c:if>>其他
                        </option>
                    </select>
                </div>

                <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                    <span style="width:7px;"></span><span class="border-size"
                                                          style="text-align-last: right;display: inline-block;"
                >受理人</span>

                    <div class="detail-seat-input ">
                        &nbsp;&nbsp;<%=user.getNAME()%>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <section class="content-header">
        <div class="seat-middle-top"
             style="height:30px;line-height:25px;margin-top: 20px;box-shadow: 4px 4px 6px #c7c7c7;">
            <div class="seat-middle-top-left">
                <div class="seat-middle-top-left-bt">客户信息</div>
            </div>
        </div>
    </section>

    <div class="table table-striped table-bordered table-hover "
         style="padding: 10px;width:100%;margin-bottom:0px;">
        <div style="background-color: #ffffff;box-shadow: 4px 4px 6px #c7c7c7;width: 100%;border-radius: 5px;padding: 5px 0;">
            <div class="between-center" style="margin: 20px 0 10px 0;padding: 0 15px 0 15px">
                <div class="flex-row-acenter" style="white-space: nowrap">
                    <span style="width:7px;color: red">*</span><span class="border-size"
                                                                     style="text-align-last: right;display: inline-block;">投诉人</span>
                    <input class="detail-seat-input " name="tsman"
                           id="tsman"
                           value="${pd.tsman}" type="text" placeholder="投诉人"
                           title="投诉人"/>
                </div>

                <div class="flex-row-acenter" style="white-space: nowrap">
                            <span class="border-size"
                                  style="text-align-last: right;display: inline-block;">联系电话</span>
                    <input class="detail-seat-input " type="text"
                           name="tstel" id="tstel"
                           value="${pd.tstel}" maxlength="50"
                           placeholder="这里输入联系电话" title="联系电话"/>
                </div>

                <div class="flex-row-acenter" style="white-space: nowrap">
                    <span style="width:7px;color: red"></span><span class="border-size"
                                                                    style="text-align-last: right;display: inline-block;">邮箱</span>

                    <input class="detail-seat-input "
                           name="email"
                           id="email"
                           value="${pd.email}" placeholder="这里输入邮箱" title="邮箱"/>
                </div>

                <div class="flex-row-acenter" style="white-space: nowrap">
                    <span style="width:7px;color: red"></span><span class="border-size" style="text-align-last: right;display: inline-block;">证件类型</span>
                    <select class="detail-seat-select " id="cardtype"
                            name="cardtype">
                        <option value="">全部</option>
                        <c:forEach items="${cardtypeList}" var="var" varStatus="vs">
                            <option value="${var.DICTIONARIES_ID }"
                                    <c:if test="${pd.cardtype==var.DICTIONARIES_ID}">selected</c:if>>${var.NAME }</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="flex-row-acenter" style="white-space: nowrap">
                    <span style="width:7px;"></span><span class="border-size" style="text-align-last: right;display: inline-block;">证件号码</span>

                    <input class="detail-seat-input " type="text"  name="cardid" id="cardid"
                           value="${pd.cardid}"
                           placeholder="这里输入证件号码" title="投证件号码"/>
                    <c:if test="${pd.cardid != ''}">
                        <div class="content-edit home-img cy_bj" style="padding-left: 8px;" title="查看"
                             onclick="search_cardid('${pd.uid}',${pd.id});">
                            <font class="home-img-size">查看</font>
                        </div>
                    </c:if>
                </div>
            </div>

            <div class="row" style="margin: 10px 0 20px 0">
                <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                    <span style="width:7px;"></span><span class="border-size"
                                                          style="text-align-last: right;display: inline-block;">乘机时间</span>

                    <input class="detail-date-picker "
                           name="cjdate"
                           id="cjdate"
                           value="${pd.cjdate}" type="text" data-date-format="yyyy-mm-dd"
                           readonly="readonly" placeholder="乘机时间" title="乘机时间"/>
                </div>

                <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                    <span style="width:7px;"></span><span class="border-size"
                                                          style="text-align-last: right;display: inline-block;">航班号/航程</span>
                    <input class="detail-seat-input " type="text"
                           name="hbh" id="hbh"
                           value="${pd.hbh}"
                           placeholder="这里输入航班号/航程" title="航班号/航程"/>
                </div>

                <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                    <span style="width:7px;"></span><span class="border-size"
                                                          style="text-align-last: right;display: inline-block;">出发机场</span>

                    <input class="detail-seat-input "
                           name="deport"
                           id="deport"
                           value="${pd.deport}" placeholder="这里输入出发机场" title="出发机场"/>
                </div>

                <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                    <span style="width:7px;"></span><span class="border-size"
                                                          style="text-align-last: right;display: inline-block;">目的机场</span>

                    <input class="detail-seat-input "
                           name="arrport"
                           id="arrport"
                           value="${pd.arrport}" placeholder="这里输入目的机场" title="目的机场"/>
                </div>
            </div>


        </div>
    </div>
    <%--            <c:if test="${vs.index==0}">--%>
    <c:forEach items="${clList}" var="varcl" varStatus="vs">
        <c:if test="${vs.index==0}">
            <section class="content-header padbottom15">
                <div class="seat-middle-top"
                     style="height:30px;line-height:25px;margin-top: 20px;box-shadow: 4px 4px 6px #c7c7c7;">
                    <div class="seat-middle-top-left">
                        <div class="seat-middle-top-left-bt">处理记录</div>
                    </div>
                </div>
            </section>
        </c:if>
    </c:forEach>
    <c:forEach items="${clList}" var="varcl" varStatus="vs">
        <c:if test="${vs.index==0}">
            <div style="padding:10px;">
                <table class="btable"
                       style="box-shadow: 4px 4px 6px #c7c7c7;border-radius: 5px;padding: 5px 0;">
                    <c:forEach items="${clList}" var="varcl" varStatus="vs">
                        <c:if test="${vs.index==0}">
                            <tr>
                                    <%--                                <td style="width:50px;font-weight:bold" rowspan="${clCount+1 }"--%>
                                    <%--                                    class="tdclass">处理<br>记录--%>
                                    <%--                                </td>--%>
                                <td class="tdtitle">处理部门</td>
                                <td class="tdtitle">处理人</td>
                                <td class="tdtitle">处理时间</td>
                                <td class="tdtitle">处理记录</td>
                            </tr>
                        </c:if>
                        <tr>
                            <td align="center">${varcl.areaname}</td>
                            <td align="center">${varcl.clman}(${varcl.name})</td>
                            <td align="center">${varcl.cldate}</td>
                            <td align="center">
                                <c:if test="${not empty varcl.clcont&&varcl.clcont!='null'}">
                                    ${varcl.clcont}
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </c:if>
    </c:forEach>

    <section class="content-header padbottom15">
        <div class="seat-middle-top"
             style="height:30px;line-height:25px;margin-top: 20px;box-shadow: 4px 4px 6px #c7c7c7;">
            <div class="seat-middle-top-left">
                <div class="seat-middle-top-left-bt">工单处理</div>
            </div>
        </div>
    </section>

    <div style="padding: 10px">
        <div style="background-color: #ffffff;box-shadow: 4px 4px 6px #c7c7c7;border-radius: 5px;padding: 5px 0;">
            <div class="row" style="margin: 10px 0">
                <c:if test="${empty clList || not empty pd.endreason}">
                    <div class="col-md-12 flex-row-acenter" style="white-space: nowrap;margin-top: 10px">
                        <span style="width:7px;"></span><span class="border-size"
                                                              style="text-align-last: right;display: inline-block;">快速处理</span>
                        <textarea class="detail-seat-textarea "
                                  name="endreason" id="endreason" placeholder="这里输入快速处理"
                                  title="快速处理">${pd.endreason}</textarea>
                    </div>
                </c:if>
            </div>
            <div class="row flex-row-center-center" style="margin: 10px 0 20px 0">
                <div class="col-md-3 between-center  " style="white-space: nowrap">
                    <span style="width:7px;"></span><span class="border-size"
                                                          style="text-align-last: right;display: inline-block;">附件上传</span>

                    <div class="detail-seat-input " style="border: 0px solid rgba(0,0,0,0)">
                        <c:if test="${empty search&&pd.type!='4'}">
                            <%if (jurisdiction.hasQx("70209")) { %>
                            <div class="content-upload home-img cy_bj " style="padding-left: 8px;" title="上传"
                                 onclick="uploadfile('${pd.uid}','');">
                                <font class="home-img-size">上传</font>
                            </div>
                            <%--                                    &nbsp;&nbsp;<a onclick="uploadfile('${pd.uid}','');" >上传</a>--%>
                            <%} %>
                        </c:if>
                        <c:if test="${not empty search||pd.type=='4'}">
                            <div class="content-look flex-row-acenter" title="查看"
                                 onclick="uploadfile('${pd.uid}','4');">
                                <img src="static/img/workorder/look.png" width="20px"
                                     height="20px"/>
                                <font class="content-size">查看</font>
                            </div>
                            <%--                                    &nbsp;&nbsp;<a onclick="uploadfile('${pd.uid}','4');" >查看</a>--%>
                        </c:if>
                    </div>
                </div>

                <div class="col-md-3 flex-row-center-center" style="white-space: nowrap">
                    <c:if test="${empty search&&pd.type!='4'}">
                        <span style="width:7px;"></span>
                        <span class="border-size" style="text-align-last: right;display: inline-block;">重复投诉</span>
                        <input class="detail-seat-input " type="text" readonly name="cfbm" id="cfbm" onclick="search_cfbm('${pd.cfbm}')" value="${pd.cfbm}"/>
                    </c:if>
                    <c:if test="${(not empty search||pd.type=='4')&&not empty pd.cfbm}">
                        <span style="width:7px;"></span>
                        <span class="border-size"  style="text-align-last: right;display: inline-block;">重复投诉</span>
                        <input class="detail-seat-input " type="text" readonly name="cfbm" id="cfbm" style="width:120px" onclick="search_cfbm('${pd.cfbm}')" value="${pd.cfbm}"/>
                        <div class="content-look flex-row-acenter" title="查看" onclick="search_cfbm('${pd.cfbm}')">
                            <img src="static/img/workorder/look.png" width="20px"  height="20px"/>
                            <font class="content-size">查看</font>
                        </div>
                    </c:if>
                </div>

                <div class="col-md-3 flex-row"><c:if test="${empty search&&pd.type!='4'}">
                    <div class="content-edit home-img cy_bj" style="padding-left: 8px;" title="选择"
                         onclick="getCfts('${pd.uid}');">
                        <font class="home-img-size">选择</font>
                    </div>

                    <div class="content-delete home-img cy_bj" style="padding-left: 8px;" title="删除"
                         onclick="delCfts('${pd.uid}');">
                        <font class="home-img-size">删除</font>
                    </div>
                </c:if></div>
                <div class="col-md-3"></div>
            </div>
            <c:if test="${empty search&&pd.type!='4'}">
                <div class="new-bc" style="padding: 30px 0">
                    <c:if test="${empty pd.doaction}">
                        <c:if test="${empty clList}">
                            <%if (jurisdiction.hasQx("70206")) { %>
                            <c:if test="${not empty pd.caseCode}">
                                <a onclick="save('5');">工单退回</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            </c:if>
                            <%} %>
                            <%if (jurisdiction.hasQx("70206")) { %>
                            <a onclick="save('0');">结束工单</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <%} %>
                            <%if (jurisdiction.hasQx("70207")) { %>
                            <a onclick="save('1');">派发工单</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <%} %>
                        </c:if>
                        <%if (jurisdiction.hasQx("70201")) { %>
                        <c:if test="${pd.type!='4'}">
                            <a onclick="save('2');">保存</a>
                        </c:if>
                        <%} %>
                    </c:if>
                    <a class="new-qxbut" style="margin-left: 25px;" data-btn-type="cancel"
                       onclick="formClose()">取消</a>
                </div>
            </c:if>


        </div>
    </div>
    <div id="zhongxin2" class="center" style="display:none">
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <img src="static/images/jiazai.gif"/><br/>
        <h4 class="lighter block green">提交中...</h4>
    </div>
    </form>
</div>

</div>
<div id="outerdiv"
     style="position:fixed;top:0;left:0;background:rgba(0,0,0,0.7);z-index:2;width:100%;height:100%;display:none;">
    <div id="innerdiv" style="position:absolute;">
        <img id="max_img" style="border:5px solid #fff;" src=""/>
    </div>
</div>


<!-- 页面底部js¨ -->
<%@ include file="../../system/include/incJs_foot.jsp" %>
<script type="text/javascript">
    window.formClose = function(){
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.layer.close(index); //再执行关闭
    }
    //alert('${pd.tsbigtype}');
    //获取浏览页面可视区域的宽度改变菜单栏宽度
    // window.onload = function () {
    //     var autoWidthSmall = document.getElementsByClassName('');
    //     var autoWidthMedium = document.getElementsByClassName('');
    //     var autoWidthLarge = document.getElementsByClassName('');
    //     var i;
    //     for (i = 0; i < autoWidthSmall.length; i++) {
    //         autoWidthSmall[i].style.width = (document.documentElement.clientWidth - 20 - 150 - 360) / 5 + 'px';
    //     }
    //     for (i = 0; i < autoWidthMedium.length; i++) {
    //         autoWidthMedium[i].style.width = (document.documentElement.clientWidth - 20 - 160 - 288) / 4 + 'px';
    //     }
    //     for (i = 0; i < autoWidthLarge.length; i++) {
    //         autoWidthLarge[i].style.width = (document.documentElement.clientWidth - 20 - 30 - 72 - 80) + 'px';
    //     }
    //
    //     // autoWidth = (document.documentElement.clientWidth-20-120-288)/4+'px'
    //     // if (document.documentElement.clientWidth > 1400) {
    //     //     document.getElementById('autoheight').style.height = Number(document.getElementById('autoheight').style.height.substring(0, 3)) - 40 + 'px'
    //     // } else {
    //     //     document.getElementsByClassName('menu-top')[0].style.width = "1023px"
    //     // }
    // }

    function tstypechange() {
        var bigtstype = $("#bigtstype").val();
        if (bigtstype == "") {
            $("#tstype").html('');
            return false;
        }
        var tstype = '${pd.tstype}';
        //alert(bigtstype);
        $.ajax({
            //几个参数需要注意一下
            type: "POST",//方法类型
            dataType: "json",//预期服务器返回的数据类型
            url: "workorder/getTstype.do?bigtstype=" + bigtstype,//url
            success: function (result) {

                //var str = "<select id=\"tstype\" class=\"detail-seat-select \" style=\"width: 170px\" name=\"tstype\" >";
                var str = "";

                $.each(result.list, function (i, list) {

                    if (tstype == list.DICTIONARIES_ID) {
                        str = str + "<option value=\"" + list.DICTIONARIES_ID + "\" selected>" + list.NAME + "</option>";
                    } else {
                        str = str + "<option value=\"" + list.DICTIONARIES_ID + "\">" + list.NAME + "</option>";
                    }

                });
                $("#tstype").html('');
                $("#tstype").append(str);
            }
        });
    }

    tstypechange();

    $(document).ready(function () {

        $("#min_img").click(function () {
            var _this = $(this);//将当前的min_img元素作为_this传入函数
            imgShow("#outerdiv", "#innerdiv", "#max_img", _this);
        });
    });

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
</script>
<script type="text/javascript">

	
    $('.detail-date-picker').datepicker({
        autoclose: true,
        todayHighlight: true,
        clearBtn: true
    });
    var day1 = new Date();
    var s1 = new Date().Format("yyyy-mm-dd");

    if ('${pd.tsdate}' == "") {
        $("#tsdate").val(s1);
    }


    var cftslayer;

    function closeCftsLayer() {
        layer.close(cftslayer);//关闭当前页
    }

    function delCfts() {
        $("#cfbm").val('');
    }

    //文件上传查看
    function getCfts(id) {
        var winId = "userWin";
        cftslayer = layer.open({
            type: 2,
            title: "重复投诉",
            shade: 0.5,
            skin: 'demo-class',
            area: ['90%', '100%'],
            content: "<%=basePath%>workorder/list.do?id=" + id + "&type=4&doaction=cfts"
        });
    }


    var setting_dept = {
        check: {
            enable: true
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            beforeClick: beforeClick_dept,
            //onClick: onClick_dept,
            onCheck: onClick_dept
        }
    };
    var zn_dept = '${zTreeNodes_dept}';
    var zNodes_dept = eval(zn_dept);
    //alert('${zTreeNodes_dept}');

    var t_dept = $("#treeDemo_dept");
    t_dept = $.fn.zTree.init(t_dept, setting_dept, zNodes_dept);
    t_dept.expandAll(true);

    var pid = '${pd.tsdept}';
    /**此处数据前后必须拼接;*/
        //alert(pid);
    var zTree = t_dept.getCheckedNodes(false);
    for (var i = 0; i < zTree.length; i++) {
        if (pid.indexOf(zTree[i].AREA_CODE) != -1) {
            //alert(zTree[i]);
            t_dept.expandNode(zTree[i], true); //展开选中的
            t_dept.checkNode(zTree[i], true);
        }
    }
    if (pid != "") {
        onClick_dept();
    }

    function beforeClick_dept(treeId, treeNode) {
        var check = (treeNode && !treeNode.isParent);
        if (!check) ;
        return check;
    }

    function onClick_dept(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo_dept"),
            nodes = zTree.getCheckedNodes(true),
            v = "", vid = "";
        for (var i = 0, l = nodes.length; i < l; i++) {
            if (nodes[i].AREA_LEVEL == '3') {
                if (v != "") {
                    v += ",";
                }
                v += nodes[i].name;
                if (vid != "") {
                    vid += ",";
                }
                vid += nodes[i].AREA_CODE;
            }
        }
        //if (v.length > 0 ) v = v.substring(0, v.length-1);
        var cityObj = $("#tsdeptSel");
        //alert(v);
        cityObj.attr("value", v);

        $("#tsdept").val(vid);
        //hideDept();
    }


    function showTsdept() {
        var cityObj = $("#tsdept");
        var cityOffset = $("#tsdept").offset();
        $("#menuContent_dept").slideDown("fast");
        $("body").bind("mousedown", onBodyDown);
    }

    function hideMenu() {
        $("#menuContent").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDown);
    }

    function hideDept() {
        $("#menuContent_dept").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDown);
    }

    function onBodyDown(event) {
        //alert(event);
        if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || event.target.id == "menuContent_dept" || $(event.target).parents("#menuContent").length > 0)) {
            hideMenu();
            //hideDept();
        }
    }

    var uid = '${pd.uid}';

    function getFile() {
        //alert(uid);
        if (uid != "") {
            $.ajax({
                //几个参数需要注意一下
                type: "POST",//方法类型
                dataType: "json",//预期服务器返回的数据类型
                url: "workorder/getFile.do?uid=" + uid,//url
                success: function (result) {
                    var str = "";
                    var path = "<%=path%>";
                    $.each(result.list, function (i, list) {

                        var wfile = list.file;
                        if (wfile.indexOf(".png") >= 0 || wfile.indexOf(".PNG") >= 0 || wfile.indexOf(".gif") >= 0 || wfile.indexOf(".GIF") >= 0 || wfile.indexOf(".JPG") >= 0 || wfile.indexOf(".jpg") >= 0 || wfile.indexOf(".jpeg") >= 0 || wfile.indexOf(".JPEG") >= 0) {
                            str = str + "<li><a onclick=\"imgShow('" + path + "/uploadFiles/uploadFile/" + list.file + "')\">" + list.name + "&nbsp;&nbsp;<span style=\"color:#ff0000\" onclick=\"delfile('" + list.id + "')\">删除文件</span></li>";
                        } else {
                            str = str + "<li><a href=\"" + path + "/uploadFiles/uploadFile/" + list.file + "\">" + list.name + "&nbsp;&nbsp;<span style=\"color:red\" onclick=\"delfile('" + list.id + "')\">删除文件</span></li>";
                        }

                    });
                    $("#fileid").html('');
                    //alert(str);
                    $("#fileid").append(str);
                }
            });
        } else {

        }
    }

    <%--

    function delfile(id){
        bootbox.confirm("确定要删除文件吗?", function(result) {
            if(result) {
                var url = "<%=basePath%>workorder/deleteFile.do?id="+id+"&tm="+new Date().getTime();
                $.get(url,function(data){
                    getFile();
                });
            }
        });
    }
     --%>
    var filelayer;

    function closeFileLayer() {
        layer.close(filelayer);//关闭当前页
    }

    //文件上传查看
    function uploadfile(uid, type) {

        var winId = "userWin";
        //var type='${pd.type}';
        filelayer = layer.open({
            type: 2,
            title: "文件上传",
            shade: 0.5,
            skin: 'demo-class',
            area: ['90%', '100%'],
            content: "<%=basePath%>workorder/filelist.do?doaction=" + type + "&uid=" + uid
        });

    }

    //alert('${pd.type}');
    function save(doaction) {
        //alert('${types}');
        var str = "";
        var cfbm=$("#cfbm").val();
        if(cfbm!=""){
            doaction='0';
        }

        if (doaction == "0") { //快速处理、结束工单
            str = "结束工单";
            /* if ($("#cfbm").val() != ""&&$("#endreason").val()=="") { //选择重复投诉
            	$("#endreason").val("重复投诉");
            }
            if ($("#endreason").val() == "") {
                $("#endreason").val("快速处理");
            } */
        }
        if (doaction == "1") {
            str = "派发工单";
            if ($("#endreason").val() == "") {
                //$("#endreason").val("派发工单");
            }
        }

        if (doaction == "2") {
            str = "工单保存";
        }

        if (doaction == "5") {
            str = "工单退回";
        }

        $("#doaction").val(doaction);
        if ($("#tstype_id").val() == "") {

            layer.alert("请选择投诉类型");
            $("#tstype_name").focus();
            return false;
        }


        if ($("#tsdate").val() == "") {

            layer.alert("请输入投诉时间");
            $("#tsdate").focus();
            return false;
        } else {
            var day2 = new Date();
            day2.setTime(day2.getTime());
            var s2 = day2.getFullYear() + "-" + (day2.getMonth() + 1) + "-" + day2.getDate();
            if (s2 < $("#tsdate").val()) {

                layer.alert("投诉日期不能大于当前日期");
                $("#tsdate").focus();
                return false;
            }
        }
        if ($("#tssource").val() == "") {
            layer.alert("请选择投诉来源");
            $("#tssource").focus();
            return false;
        }

        if ($("#tsman").val() == "") {

            layer.alert("请输入投诉人");
            $("#tsman").focus();
            return false;
        }


        /* if ($("#tstel").val() == "") {

            layer.alert("请输入投诉电话");
            $("#tstel").focus();
            return false;
        } */

        if ($("#tstel").val() != "") {
            if (!(/^1[3456789]\d{9}$/.test($("#tstel").val()))) {
                layer.alert("投诉电话有误，请重填");

                $("#tstel").focus();
                return false;
            }
        }


        var tsdept = $("#tsdept").val();
        if (tsdept == "") {
            layer.alert("请选择投诉的部门");
            return false;
        } else {
            $("#tsdept").val(tsdept);
        }
        
        var depttype = $("#depttype").val();
        if (depttype == "") {
            layer.alert("请选择部门类型");
            return false;
        } 
        bootbox.confirm("确定要" + str + "吗?", function (result) {
            if (result) {
                openLoading();
                var msg = '${msg }';
                //$('#Form_add').submit();
                $.ajax({
                    //几个参数需要注意一下
                    type: "POST",//方法类型
                    dataType: "json",//预期服务器返回的数据类型
                    url: "workorder/save.do",//url
                    data: $('#Form_add').serialize(),
                    success: function (result) {
                        //  console.log(result.);//打印服务端返回的数据(调试用)
                        if (result.success == 'true') {
                            parent.search();
                            closeLoading();
                            //if (result.data != "" && typeof (result.data) != "undefined") {
                            //  handle(result.data);

                            // } else {
                            //parent.search();
                            // closeLoading();
                            // }

                        }
                    },
                    error: function () {
                        alert("异常！");
                    }
                });

            }
        });
    }

    function handle(pid) {
        //alert(pid);
        openLoading();
        $.ajax({
            //几个参数需要注意一下
            type: "POST",//方法类型
            dataType: "html",//预期服务器返回的数据类型
            url: "rutask/handle.do",//url
            data: {
                PROC_INST_ID_: pid,
                doaction: "azb"
            },
            success: function (result) {

                parent.search();
                closeLoading();
            },
            error: function () {
                alert("异常！");
            }
        });

    }

    function search_cardid(uid, id){
        $.ajax({
            //几个参数需要注意一下
            type: "POST",//方法类型
            dataType: "json",//预期服务器返回的数据类型
            url: "workorder/getCardId.do",//url
            data: {
                uid:uid,
                id:id
            },
            success: function (result) {
                $("#cardid").val(result.cardid);

            },
            error: function () {
                alert("异常！");
            }
        });
    }

    function search_cfbm(cfbm) {
        //alert(uid);
        var cfbm = $("#cfbm").val();
        if (cfbm == "") {
            return false;
        }
        var winId = "userWin";
        var type = '${pd.type}';
        filelayer = layer.open({
            type: 2,
            title: "详情",
            shade: 0.5,
            skin: 'demo-class',
            area: ['90%', '100%'],
            content: '<%=basePath%>workorder/goEdit.do?doaction=search&code=' + cfbm
        });

    }


</script>
</body>
</html>