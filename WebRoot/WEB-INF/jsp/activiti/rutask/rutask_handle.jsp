<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.fh.util.Jurisdiction" %>
<%
    Jurisdiction jurisdiction = new Jurisdiction();
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <%@ include file="../../system/include/incJs_mx.jsp" %>
    <!-- jsp文件头和头部 -->
    <!-- 日期框 (带小时分钟)-->
    <link rel="stylesheet" href="static/ace/css/bootstrap-datetimepicker.css"/>
    <link type="text/css" rel="stylesheet" href="plugins/zTree/v3/css/zTreeStyle/zTreeStyle.css"/>
    <script type="text/javascript" src="plugins/zTree/v3/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="plugins/zTree/v3/js/jquery.ztree.excheck.js"></script>
    <style>
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
        }

        .tdclass {
            text-align: right;
        }

        .tdtitle {
            text-align: center;
            background: #dedede
        }

        body .demo-class {
            border-top-left-radius: 15px;
            border-top-right-radius: 15px;
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
<!-- /section:basics/navbar.layout -->
<body class="no-skin">
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container"
     style="overflow-x: auto;overflow-y: auto;height: 100%;background-color: #ecf0f5">
    <!-- /section:basics/sidebar -->
    <div class="main-content">
        <div class="modal-header">
            <h4 class="modal-title" id="myModalLabel" style="float: left;">办理任务</h4>
            <div class="new-tb" style="float: right;" data-dismiss="modal" title="关闭"></div>
        </div>
        <form action="rutask/handle.do" name="Form_add" id="Form_add" method="post">
            <input type="hidden" name="ID_" id="ID_" value="${pd.ID_}"/>
            <input type="hidden" name="ASSIGNEE_" id="ASSIGNEE_" value=""/>
            <input type="hidden" name="PROC_INST_ID_" id="PROC_INST_ID_"
                   value="${pd.PROC_INST_ID_}"/>

            <section class="content-header">
                <div class="seat-middle-top"
                     style="height:30px;line-height:25px;margin-top: 0px;box-shadow: 4px 4px 6px #c7c7c7;">
                    <div class="seat-middle-top-left">
                        <div class="seat-middle-top-left-bt">工单处理状况</div>
                    </div>
                </div>
            </section>


            <div id="zhongxin" style="padding: 10px">
                <div class="table table-striped table-bordered table-hover "
                     style="padding: 0px;width:100%;background-color: #ffffff;margin-bottom:0px;box-shadow: 4px 4px 6px #c7c7c7;border-radius: 5px;padding: 5px 0;">
                    <div class="row" style="margin-top: 10px">

                        <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                            <span class="border-size" style="text-align-last: right;display: inline-block;">投诉编号</span>
                            <select class="detail-seat-select" disabled="disabled"
                            >
                                <option value="">${pd_s.code}</option>
                            </select>
                        </div>

                        <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                            <span class="border-size" style="text-align-last: right;display: inline-block;">投诉来源</span>

                            <select class="detail-seat-select" disabled="disabled">
                                <option value="">${pd_s.tssourcename}</option>
                            </select>
                        </div>

                        <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                            <span class="border-size" style="text-align-last: right;display: inline-block;">投诉渠道</span>

                            <select class="detail-seat-select" disabled="disabled">
                                <option value="">${pd_s.tsqdname}</option>
                            </select>
                        </div>

                        <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                            <span class="border-size" style="text-align-last: right;display: inline-block;">投诉日期</span>

                            <input class="detail-date-picker"
                                   value="${pd_s.tsdate_format}" type="text" data-date-format="yyyy-mm-dd"
                                   disabled="disabled" title="投诉时间"/>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 flex-row-acenter" style="white-space: nowrap">
                            <span class="border-size" style="text-align-last: right;display: inline-block;">投诉诉求</span>
                            <input class="detail-seat-input" type="text" disabled="disabled"
                                   value="${pd_s.tssq}">
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 flex-row-acenter" style="white-space: nowrap">
                            <span class="border-size" style="text-align-last: right;display: inline-block;">投诉内容</span>

                            <input class="detail-seat-input" type="text" disabled="disabled"
                                   value="${pd_s.tscont}" title="投诉内容">
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                                <span class="border-size"
                                      style="text-align: center;line-height: 13px;">投诉类别<br/>(大项)</span>

                            <select class="detail-seat-select" disabled="disabled"
                            >
                                <option value="">${pd_s.tsbigtype}</option>
                            </select>
                        </div>


                        <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                                <span class="border-size"
                                      style="text-align: center;line-height: 13px;">投诉类别<br/>(细项)</span>

                            <%--                                <span id="tstypespans"></span>--%>
                            <select class="detail-seat-select" disabled="disabled"
                            >
                                <option> ${pd_s.tsclassifyname}</option>
                            </select>
                        </div>


                        <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                            <span class="border-size" style="text-align-last: right;display: inline-block;">投诉分类</span>

                            <select class="detail-seat-select" disabled="disabled"
                            >
                                <option value="">${pd_s.tstypename}</option>
                            </select>
                        </div>

                        <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                            <span class="border-size" style="text-align-last: right;display: inline-block;">投诉等级</span>

                            <select class="detail-seat-select" disabled="disabled"
                            >
                                <option>${pd_s.tslevelname}</option>
                            </select>
                        </div>
                    </div>

                    <div class="row" style="margin-bottom: 10px">
                        <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                            <span class="border-size" style="text-align-last: right;display: inline-block;">投诉部门</span>
			
                            <c:if test="${taskname!='工单专员审批'}">
                                <input class="detail-seat-input" disabled="disabled" value="${pd_s.tsdeptname}"/>
                                <input type="hidden" name="tsdept"  id="tsdept"
                                       value="${pd_s.tsdept}"/>
                            </c:if>
                            <c:if test="${taskname=='工单专员审批'}">
                                <input class="detail-seat-input" type="hidden" name="tsdept"
                                       id="tsdept" 
                                       value="${pd_s.tsdept }">
                                <input type="hidden" name="oldtsdept" id="oldtsdept"
                                       value="${pd_s.tsdept}"/>
                                <input class="detail-seat-input" id="tsdeptSel"
                                       name=""
                                       type="text" readonly
                                       onclick="showTsdept(); return false;"/>
                                <div id="menuContent_dept" class="menuContent_dept"
                                     style="position: absolute;display:none;background:#ffffff;z-index: 1;">
                                    <div class="new-bc"
                                         style=" height:35px;padding-top:5px;border-top-left-radius:5px;border-top-right-radius:5px;border-left:1px #ccc solid;border-right:1px #ccc solid;border-top:1px #ccc solid ">
                                        <a style="height:25px;line-height:25px;padding:0px;padding-left:10px;"
                                           class="btnbm" onclick="hideDept()">确定</a>&nbsp;&nbsp;&nbsp;&nbsp;<a
                                            style=" width:50px;height:25px;line-height:25px;padding:0px;padding-left:10px;"
                                            onclick="hideDept()">取消</a>
                                    </div>
                                    <ul id="treeDemo_dept" class="ztree"
                                        style="margin-top:0; width:250px;border:1px #ccc solid"></ul>
                                </div>

                            </c:if>
                        </div>

                        <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                            <span class="border-size" style="text-align-last: right;display: inline-block;">是否回访</span>


                            <select class="detail-seat-select" disabled="disabled">
                                <option value="1" <c:if test="${pd_s.ishf=='1'}">checked</c:if>>是</option>
                                <option value="0" <c:if test="${pd_s.ishf=='0' || empty pd_s.ishf}">checked</c:if>>否
                                </option>
                            </select>

                        </div>
                        <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                            <span class="border-size" style="text-align-last: right;display: inline-block;">处理时限</span>

                            <select class="detail-seat-select" disabled="disabled">
                                <option id="xclsj">${cltime}</option>
                            </select>
                           
                        </div>
 						<input type="hidden" name="hours" id="hours">
                        <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                            <span class="border-size" style="text-align-last: right;display: inline-block;">受理人</span>
                            <input class="detail-seat-input" disabled="disabled" value="${sessionUser.NAME}"
                            />
                        </div>
                    </div>
                </div>
            </div>
            <section class="content-header">
                <div class="seat-middle-top"
                     style="height:30px;line-height:25px;margin-top: 0px;box-shadow: 4px 4px 6px #c7c7c7;">
                    <div class="seat-middle-top-left">
                        <div class="seat-middle-top-left-bt">客户信息</div>
                    </div>
                </div>
            </section>

            <div id="table_report"
                 class="table table-striped table-bordered table-hover "
                 style="padding: 10px;width:100%;margin-bottom:0px;">
                <div style="background-color: #ffffff;box-shadow: 4px 4px 6px #c7c7c7;width: 100%;border-radius: 5px">
                    <div class="between-center" style="margin-top: 10px;">
                        <div class="flex-row-acenter" style="white-space: nowrap">
                            <span class="border-size" style="text-align-last: right;display: inline-block;">投诉人</span>
                            <input class="detail-seat-input" disabled="disabled"
                                   value="${pd_s.tsman}" type="text"
                                   title="投诉人"/>
                        </div>

                        <div class="flex-row-acenter" style="white-space: nowrap">
                            <span class="border-size" style="text-align-last: right;display: inline-block;">联系电话</span>
                            <input class="detail-seat-input" type="text" disabled="disabled"
                                   value="${pd_s.tstel}" maxlength="50"
                                   title="联系电话"/>
                        </div>

                        <div class="flex-row-acenter" style="white-space: nowrap">
                            <span class="border-size" style="text-align-last: right;display: inline-block;">邮箱</span>

                            <input class="detail-seat-input" disabled="disabled"
                                   value="${pd_s.email}"/>
                        </div>

                        <div class="flex-row-acenter" style="white-space: nowrap">
                            <span class="border-size" style="text-align-last: right;display: inline-block;">证件类型</span>

                            <input class="detail-seat-input" disabled="disabled"
                                   value="${pd_s.cardtypename}"/>
                        </div>

                        <div class="flex-row-acenter" style="white-space: nowrap">
                            <span class="border-size" style="text-align-last: right;display: inline-block;">证件号码</span>

                            <input class="detail-seat-input" type="text"
                                   value="${pd_s.cardid}" disabled="disabled"
                                   title="投证件号码"/>
                        </div>
                    </div>

                    <div class="row" style="margin-bottom: 10px;">
                        <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                            <span class="border-size" style="text-align-last: right;display: inline-block;">乘机时间</span>

                            <input class="detail-date-picker"
                                   value="${pd_s.cjdate}" type="text" data-date-format="yyyy-mm-dd"
                                   readonly="readonly" title="乘机时间"/>
                        </div>

                        <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                            <span class="border-size"
                                  style="text-align-last: right;display: inline-block;">航班号/航程</span>
                            <input class="detail-seat-input" type="text"
                                   disabled="disabled"
                                   value="${pd_s.hbh}"
                                   title="航班号/航程"/>
                        </div>

                        <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                            <span class="border-size" style="text-align-last: right;display: inline-block;">出发机场</span>

                            <input class="detail-seat-input" disabled="disabled"
                                   value="${pd_s.deport}"/>
                        </div>

                        <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                            <span class="border-size" style="text-align-last: right;display: inline-block;">目的机场</span>

                            <input class="detail-seat-input" disabled="disabled"
                                   value="${pd_s.arrport}"/>
                        </div>
                    </div>


                </div>
            </div>

            <section class="content-header padbottom15">
                <div class="seat-middle-top"
                     style="height:30px;line-height:25px;margin-top: 0px;box-shadow: 4px 4px 6px #c7c7c7;">
                    <div class="seat-middle-top-left">
                        <div class="seat-middle-top-left-bt">处理记录</div>
                    </div>
                </div>
            </section>

            <div style="padding:10px;">
                <table class="btable"
                       style="background-color:#ffffff;box-shadow: 4px 4px 6px #c7c7c7;border-radius: 5px;padding: 5px 0;">
                    <c:forEach items="${clList}" var="varcl" varStatus="vs">
                        <c:if test="${vs.index==0}">
                            <tr>
                                <td class="tdtitle">处理部门</td>
                                <td class="tdtitle">处理人</td>
                                <td class="tdtitle">处理时间</td>
                                <td colspan="2" style="width:40%" class="tdtitle">处理记录</td>
                            </tr>
                        </c:if>
                        <tr>
                            <td align="center">${varcl.areaname}</td>
                            <td align="center">${varcl.clman}(${varcl.name})</td>
                            <td align="center">${varcl.cldate}</td>
                            <td colspan="2" align="center">
                                <c:if test="${not empty varcl.clcont&&varcl.clcont!='null'}">
                                    ${varcl.clcont}
                                </c:if>
                            </td>
                        </tr>

                    </c:forEach>
                </table>
            </div>

            <section class="content-header padbottom15">
                <div class="seat-middle-top"
                     style="height:30px;line-height:25px;margin-top: 0px;box-shadow: 4px 4px 6px #c7c7c7;">
                    <div class="seat-middle-top-left">
                        <div class="seat-middle-top-left-bt">工单处理</div>
                    </div>
                </div>
            </section>

            <div style="padding: 10px">
                <div style="background-color: #ffffff;box-shadow: 4px 4px 6px #c7c7c7;border-radius: 5px;padding: 5px 0;">
                    <div class="row">
                        <c:if test="${empty clList || not empty pd.endreason}">
                            <div class="col-md-12 flex-row-acenter" style="white-space: nowrap;margin-top: 10px">
                                <span class="border-size"
                                      style="text-align-last: right;display: inline-block;">快速处理</span>
                                <textarea class="detail-seat-textarea"

                                          name="endreason" id="endreason"
                                          title="快速处理">${pd.endreason}</textarea>
                            </div>
                        </c:if>
                    </div>
                    <div class="row flex-row-center-center">
                        <div class="col-md-3 between-center" style="white-space: nowrap;">
                            <span class="border-size" style="text-align-last: right;display: inline-block;">附件上传</span>
                            <div class="detail-seat-input"
                                 style="border: 0px solid rgba(0,0,0,0)">
                                <%if (jurisdiction.hasQx("70209")) { %>
                                <div class="content-upload home-img cy_bj " style="padding-left: 8px;" title="上传"
                                     onclick="uploadfile('${pd_s.uid}','');">
                                    <font class="home-img-size">上传</font>
                                </div>
                                <%} %>
                                <%if (!jurisdiction.hasQx("70209")) { %>
                                <div class="content-look flex-row-acenter" title="查看"
                                     onclick="uploadfile('${pd_s.uid}','4');">
                                    <img src="static/img/workorder/look.png" width="20px"
                                         height="20px"/>
                                    <font class="content-size">查看</font>
                                </div>
                                <%} %>
                                </a>
                            </div>
                        </div>
                        <div class="col-md-3 flex-row-acenter" style="white-space: nowrap">
                       		
                            <c:if test="${taskname=='单部门领导审批'||taskname=='部门领导审批'||taskname=='工单专员审批'||taskname=='部门处理人员处理'||taskname=='多部门处理'}">

                                <div class="flex-row-acenter" style="white-space: nowrap">
                                    <span class="border-size"
                                          style="text-align-last: right;display: inline-block;">是否同意</span>

                                    <select class="detail-seat-select " name="msg" id="msg">
                                        <option value="yes" checked>同意</option>
                                        <option value="no">不同意</option>
                                        <c:if test="${taskname=='工单专员审批'}">
                                            <option value="retsdept">重新选择部门</option>
                                        </c:if>
                                        <c:if test="${taskname=='部门处理人员处理'||taskname=='多部门处理'}">
                                            <option value="reoffice">重新选择科室</option>
                                        </c:if>
                                    </select>
                                </div>
                            </c:if>
                        </div>

                        <div class="col-md-6 flex-row-acenter" style="white-space: nowrap;">
                            <c:if test="${taskname!='单部门领导审批'&&taskname!='部门领导审批'&&taskname!='工单专员审批'&&taskname!='部门处理人员处理'&&taskname!='多部门处理'&&taskname!='多部门多科室处理'&&taskname!='多部门工单处理'}">
                                <input type="text" name="msg" id="msg" value="yes"/>
                            </c:if>
                           
                            <span class="border-size" style="text-align-last: right;display: inline-block;">处理意见</span>
                            <textarea class="detail-seat-textarea" name="OPINION" id="OPINION"
                                      style="width:80%">${pd.DESCRIPTION}</textarea>
                        </div>
                       
                       
                        
                    </div>
                    <c:if test="${taskname!='单部门工单处理'}">
                        <input class="detail-seat-input" type="hidden"  <c:if
                                test="${not empty pd_s.cfbm}"> onclick="search_cfbm('${pd_s.cfbm}')"</c:if> readonly
                               name="cfbm" id="cfbm" value="${pd_s.cfbm}"/>
                    </c:if>

                    <c:if test="${taskname=='工单专员审批' and not empty pd_s.cfbm}">
                        <div class="row flex-row-center-center">
                            <div class="col-md-3 flex-row-acenter" style="white-space: nowrap;">
                                <span class="border-size"
                                      style="text-align-last: right;display: inline-block;">重复投诉</span>
                                <input class="detail-seat-input" type="text" readonly name="cfbm" id="cfbm"
                                        <c:if test="${not empty pd_s.cfbm}"> onclick="search_cfbm('${pd_s.cfbm}')"</c:if>
                                       value="${pd_s.cfbm}"/>
                            </div>
                            <div class="col-md-3 flex-row">

                            </div>
                            <div class="col-md-6"></div>
                        </div>
                    </c:if>
                    
                     <c:if test="${taskname=='部门处理人员处理'||taskname=='单部门工单处理'||taskname=='多部门工单处理'||taskname=='多部门处理'}">
                      	<%if (jurisdiction.hasQx("70402")) { %>
                    	  	 <div class="row flex-row-center-center">
	                            <div class="col-md-6 flex-row-acenter" style="white-space: nowrap;">
	                           		
	                           		<input type="hidden" name="office" id="office">
	                           		<input type="hidden" name="kstime" id="kstime">
	                           		<input type="hidden" name="oldoffice" id="oldoffice" value="${ksoffice.office }">
	                           		<input type="hidden" name="oldkstime" id="oldkstime" value="${ksoffice.kstime }">
	                           		
	                           		<span class="border-size"
                                      style="text-align-last: right;display: inline-block;">选择处理的科室：</span>
                                      
		                           <c:forEach items="${ksList}" var="varKs" varStatus="vsks">
	                                  	&nbsp;&nbsp;&nbsp; <input type="checkbox" name="kschoice" id="kschoice" value="${varKs.AREA_CODE }" style="vertical-align: middle;">&nbsp;${varKs.NAME} 
	                                  	<c:if test="${not empty pd_s.clsx}">&nbsp;&nbsp;&nbsp;处理时限：</c:if><input  <c:if test="${empty pd_s.clsx}">type="hidden"</c:if> <c:if test="${not empty pd_s.clsx}">type="text"</c:if> name="kstime${varKs.AREA_CODE }" id="kstime${varKs.AREA_CODE }" style="width:50px;" class="detail-seat-input">
	                                  	<c:if test="${not empty pd_s.clsx}">时</c:if>
	                               </c:forEach>
		                        </div> 
		                         <div class="col-md-3 flex-row">
	
	                            </div>
	                            <div class="col-md-6"></div>
		                       </div>  
	                        <%} %>
	                    </c:if>    
                    
                        
                    
                    <c:if test="${taskname=='单部门工单处理'}">
                        <div class="row flex-row-center-center">
	                      
                            <div class="col-md-3 flex-row-acenter" style="white-space: nowrap;">
                                <span class="border-size"
                                      style="text-align-last: right;display: inline-block;">重复投诉</span>
                                <input class="detail-seat-input" type="text" readonly name="cfbm" id="cfbm"
                                        <c:if test="${not empty pd_s.cfbm}"> onclick="search_cfbm('${pd_s.cfbm}')"</c:if>
                                       value="${pd_s.cfbm}"/>
                            </div>
                            <div class="col-md-3 flex-row">

                                <div class="content-edit home-img cy_bj" style="padding-left: 8px;" title="选择"
                                     onclick="getCfts('${pd_s.uid}');">
                                    <font class="home-img-size">选择</font>
                                </div>
                                <div class="content-delete home-img cy_bj" style="padding-left: 8px;" title="删除"
                                     onclick="delCfts('${pd_s.uid}');">
                                    <font class="home-img-size">删除</font>
                                </div>

                            </div>
                            <div class="col-md-6"></div>
                        </div>
                    </c:if>

                </div>
            </div>

            <div class="flex-row-center-center new-bc" style="text-align: center;padding: 10px">
                <c:if test="${taskname=='单部门领导审批'||taskname=='部门领导审批'||taskname=='工单专员审批'}">
                    <input id="bton" type="button" class="button-blue1" onclick="handle('2');" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;
                </c:if>
                <c:if test="${taskname!='单部门领导审批'&&taskname!='部门领导审批'&&taskname!='工单专员审批'}">
                    <input id="bton" type="button" class="button-blue1" onclick="handle('1');" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;
                </c:if>

                <c:if test="${taskname=='工单专员审批'&&pd_s.source=='2'}">
                    <a style="margin-left: 80px"
                       onclick="replay('${pd_s.code}','${pd_s.uid}','${pd.ID_}','${pd.PROC_INST_ID_}')">回复原系统</a>
                </c:if>
                <a class="new-qxbut" data-btn-type="cancel" data-dismiss="modal">取消</a>
            </div>

            <div id="msgts" class="center" style="display:none"><img
                    src="static/images/jiazai.gif"/><br/><h4 class="lighter block green">提交中,请稍后...</h4></div>


        </form>
    </div>
    <!-- /.page-content -->
    <%--        </div>--%>
    <%--    </div>--%>
    <!-- /.main-content -->
</div>

<!-- /.main-container -->

<!-- 页面底部js¨ -->
<%@ include file="../../system/include/incJs_foot.jsp" %>
<!-- 页面底部js¨ -->
<!-- 百度富文本编辑框-->
<script type="text/javascript" charset="utf-8">window.UEDITOR_HOME_URL = "<%=path%>/plugins/ueditor_full/";</script>
<script type="text/javascript" charset="utf-8" src="plugins/ueditor_full/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="plugins/ueditor_full/ueditor.all.min.js"></script>
<script type="text/javascript" charset="utf-8" src="plugins/ueditor_full/lang/zh-cn/zh-cn.js"></script>


<!-- 百度富文本编辑框-->
<!-- 确认窗口 -->
<script src="static/ace/js/bootbox.js"></script>
<!-- 日期框(带小时分钟) -->
<script src="static/ace/js/date-time/moment.js"></script>
<script src="static/ace/js/date-time/locales.js"></script>
<script src="static/ace/js/date-time/bootstrap-datetimepicker.js"></script>
<!--提示框-->
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<script type="text/javascript">
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

    var pid = '${pd_s.tsdept}';
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
    var taskname = '${taskname}';
    if (pid != "" && taskname == '工单专员审批') {
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
</script>

<script type="text/javascript">


    //文件上传查看
    function uploadfile(uid, type) {
        //alert(uid);
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

    //工单回复
    function replay(code, uid, ID_, proc_id) {
        var userid = '${pd.userid}';
        var winId = "userWin";
        var title = '';
        //var type='${pd.type}';
        <%--    modals.openWin({--%>
        <%--        winId: winId,--%>
        <%--        title: '工单回复',--%>
        <%--        width: '700px' ,--%>
        <%--        url:"<%=path%>/appWeixin/replay?code=" + code + "&uid=" + uid + "&ID_=" + ID_ + "&proc_id=" + proc_id + "&action=1"--%>
        <%--});--%>
        filelayer = layer.open({
            type: 2,
            title: "工单回复",
            shade: 0.5,
            skin: 'demo-class',
            area: ['700px', '300px'],
            content: "<%=path%>/appWeixin/replay?code=" + code + "&uid=" + uid + "&ID_=" + ID_ + "&proc_id=" + proc_id + "&action=1"
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

    var uid = '${pd_s.uid}';

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
                    $.each(result.list, function (i, list) {
                        str = str + "<li>" + list.name + "&nbsp;&nbsp;</li>";
                    });
                    $("#fileid").html('');
                    //alert(str);
                    $("#fileid").append(str);
                }
            });
        } else {

        }
    }

    var timer = setInterval(function () {
        getCltime();
    }, 1000);

    function getCltime() {
        var xclsj = '${xclsj}';
        //alert(xclsj);
        if ('${xclsj}' != "") {
            $.ajax({
                //几个参数需要注意一下
                type: "POST",//方法类型
                dataType: "html",//预期服务器返回的数据类型
                url: "rutask/getCltime.do?xclsj=" + xclsj,//url
                success: function (result) {
                    result = result.replace("day", "天").replace("hour", "时").replace("min", "分").replace("sec", "秒");
                    var hours=result.substring(result.indexOf("秒")+1,result.length);
                    //alert(result);
                   // alert(hours);
                    if(result.indexOf("-")>=0){
                    	 $("#hours").val("0"); 	
                    }else{
                    	 $("#hours").val(hours); 	
                    }
                   
                    $("#xclsj").html(result);
                }
            });
        } else {

        }
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

    //getFile();

    //办理任务
    function handle(action) {
        //alert("1");
        var taskname = "${taskname}";
        var msg = $("#msg").val();
        if (msg == "no" && action == "2") {
            var tsdept = $("#tsdept").val();
            if (tsdept == "") {
                layer.alert("请选择投诉的部门");
                return false;
            } else {
                $("#tsdept").val(tsdept);
            }


        }

        if (taskname == '工单专员审批' && msg == 'retsdept') {
            var tsdept = $("#tsdept").val();
            var oldtsdept = $("#oldtsdept").val();
            if (tsdept == oldtsdept) {
                layer.alert("您选择了重选部门选项，请重新选择投诉的部门！");
                return false;
            }
        }
        var isch="";
        var hours=$("#hours").val();
        if ((taskname == '部门处理人员处理'||taskname == '多部门处理') && msg == 'reoffice') {
        	var office="";
        	
        	$("input[name='kschoice']:checked").each(function(i){
         		//ids.push($(this).val())
         		if(office!=""){office=office+",";}
         		office=office+$(this).val();
				if($("#kstime"+$(this).val()+"").val()!=""){
					
					if($("#kstime"+$(this).val()+"").val()>hours){
						layer.alert("处理时限不能大于剩余时间！");
						isch="1";
					}
					if(kstime!=""){kstime=kstime+",";}
					kstime=kstime+$("#kstime"+$(this).val()+"").val();
        		}else{
        			if(kstime!=""){kstime=kstime+",";}
					kstime=kstime+"none";
        		}
         	})
         	$("#office").val(office);
        	$("#kstime").val(kstime);
            var oldoffice = $("#oldoffice").val();
            if (office == oldoffice) {
                layer.alert("您选择了重新选择科室，请重新选择处理的科室！");
                return false;
            }
        }
        
        if ((taskname == '部门处理人员处理'||taskname == '多部门处理') && msg == 'no') {
         	$("#office").val($("#oldoffice").val());
         	$("#kstime").val($("#oldkstime").val());
        }
        
        
        
        if (taskname == '单部门工单处理'||taskname == '多部门工单处理') {
        	var office="",kstime="";
        	$("input[name='kschoice']:checked").each(function(i){
         		//ids.push($(this).val())
         		if(office!=""){office=office+",";}
         		office=office+$(this).val();
				if($("#kstime"+$(this).val()+"").val()!=""){
					if($("#kstime"+$(this).val()+"").val()>hours){
						layer.alert("处理时限不能大于剩余时间！");
						isch="1";
					}
					if(kstime!=""){kstime=kstime+",";}
					kstime=kstime+$("#kstime"+$(this).val()+"").val();
        		}else{
        			if(kstime!=""){kstime=kstime+",";}
					kstime=kstime+"none";
        		}
         	})
         	$("#office").val(office);
        	$("#kstime").val(kstime);
        }
        
        if(isch=="1"){
        	return false;
        }
        
        if ($("#OPINION").val() == "") {
            layer.alert("请输入意见");
            $("#OPINION").focus();
            return false;
        }
        // $("#msgts").show();
        $("#bton").attr("disabled", "true");

        openLoading();
        var msg = '${msg }';
        //$('#Form_add').submit();
        $.ajax({
            //几个参数需要注意一下
            type: "POST",//方法类型
            dataType: "html",//预期服务器返回的数据类型
            url: "rutask/handle.do",//url
            data: $('#Form_add').serialize(),
            success: function (result) {
                //  console.log(result.);//打印服务端返回的数据(调试用)
				tosearch();
                closeLoading();
            },
            error: function () {
                //alert("1");

            }
        });

        //$("#Form_add").submit();
        //closeLoading();
    }

    //作废
    function del(Id) {
        bootbox.prompt("请输入作废缘由?", function (result) {
            if (result != null) {
                if ('' == result) result = "未写作废缘由";
                top.jzts();
                var url = "<%=basePath%>ruprocdef/delete.do?PROC_INST_ID_=" + Id + "&reason=" + encodeURI(encodeURI(result)) + "&tm=" + new Date().getTime();
                $.get(url, function (data) {
                    $("#zhongxin").hide();
                    $("#zhongxin2").show();
                    top.Dialog.close();
                });
            }
        });
    }

    //查看用户
    function viewUser(USERNAME) {
        if ('admin' == USERNAME) {
            bootbox.dialog({
                message: "<span class='bigger-110'>不能查看admin用户!</span>",
                buttons:
                    {"button": {"label": "确定", "className": "btn-sm btn-success"}}
            });
            return;
        }
        top.jzts();
        var diag = new top.Dialog();
        diag.Modal = false;				//有无遮罩窗口
        diag.Drag = true;
        diag.Title = "资料";
        diag.URL = '<%=basePath%>user/view.do?USERNAME=' + USERNAME;
        diag.Width = 469;
        diag.Height = 380;
        diag.CancelEvent = function () { //关闭事件
            diag.close();
        };
        diag.show();
    }

    //审批意见详情页
    function details(htmlId) {
        var content = $("#" + htmlId).val().split(',fh,');
        top.handleDetails(content[1]);
        top.jzts();
        var diag = new top.Dialog();
        diag.Modal = false;			//有无遮罩窗口
        diag.Drag = true;
        diag.Title = "审批意见";
        diag.ShowMaxButton = true;	//最大化按钮
        diag.ShowMinButton = true;		//最小化按钮
        diag.URL = '<%=basePath%>rutask/details.do';
        diag.Width = 760;
        diag.Height = 500;
        diag.CancelEvent = function () { //关闭事件
            diag.close();
        };
        diag.show();
    }

    //选择办理人
    function getUser() {
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag = true;
        diag.Title = "选择办理人";
        diag.URL = '<%=basePath%>user/listUsersForWindow.do';
        diag.Width = 700;
        diag.Height = 545;
        diag.Modal = true;				//有无遮罩窗口
        diag.ShowMaxButton = true;	//最大化按钮
        diag.ShowMinButton = true;		//最小化按钮
        diag.CancelEvent = function () { //关闭事件
            var USERNAME = diag.innerFrame.contentWindow.document.getElementById('USERNAME').value;
            if ("" != USERNAME) {
                $("#ASSIGNEE_").val(USERNAME);
                $("#ASSIGNEE_2").val(USERNAME);
            }
            diag.close();
        };
        diag.show();
    }

    //选择角色
    function getRole() {
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag = true;
        diag.Title = "选择角色";
        diag.URL = '<%=basePath%>role/roleListWindow.do?ROLE_ID=1';
        diag.Width = 700;
        diag.Height = 545;
        diag.Modal = true;				//有无遮罩窗口
        diag.ShowMaxButton = true;	//最大化按钮
        diag.ShowMinButton = true;		//最小化按钮
        diag.CancelEvent = function () { //关闭事件
            var RNUMBER = diag.innerFrame.contentWindow.document.getElementById('RNUMBER').value;
            if ("" != RNUMBER) {
                $("#ASSIGNEE_").val(RNUMBER);
                $("#ASSIGNEE_2").val(RNUMBER);
            }
            diag.close();
        };
        diag.show();
    }

    //清空下一任务对象
    function clean() {
        $("#ASSIGNEE_").val("");
        $("#ASSIGNEE_2").val("");
    }

</script>
<c:if test="${null == pd.msg or pd.msg != 'admin' }">
    <script type="text/javascript">
        //百度富文本
        setTimeout("ueditor()", 500);

        function ueditor() {
            //UE.getEditor('editor');
        }

        //ueditor有标签文本
        function getContent() {
            var arr = [];
            arr.push(UE.getEditor('editor').getContent());
            return arr.join("");
        }
    </script>

</c:if>
</body>
</html>