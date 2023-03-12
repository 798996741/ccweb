<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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


</head>
<body class="no-skin">
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
    <!-- /section:basics/sidebar -->
    <div class="main-content">
        <div class="main-content-inner">
            <div class="page-content">
                <div class="modal-header">
                    <h4 class="modal-title" id="myModalLabel" style="float: left;"></h4>
                    <div class="new-tb" style="float: right;" data-dismiss="modal"
                         title="关闭"></div>
                </div>
                <div class="row flex-row-center-center">
                    <div class="col-xs-12">

                        <form action="areamanage/${msg }.do" name="Form_add"
                              id="Form_add" method="post">
                            <div id="zhongxin">
                                <div id="table_report" class="table table-striped table-bordered table-hover">
                                  
                                    <div class="row flex-row-center-center">
                                        <div class="col-md-6 flex-row-center-center" style="white-space: nowrap">
                                            <span class="border-size"
                                                  style="width:70px;text-align-last: justify;">发送人</span>
                                            <input type="text" name="sendman" id="sendman" value="${pd.sendman}"
                                                   maxlength="40" readonly="readonly"
                                                   class="detail-seat-input auto-width-medium"
                                                   style="width: 170px"/>
                                        </div>
                                        <div class="col-md-6 flex-row-center-center" style="white-space: nowrap">
                                            <span class="border-size"
                                                  style="width:70px;text-align-last: justify;">短信模板</span>
                                            <input type="text" name="tempname" id="tempname" value="${pd.tempname}"
                                                   maxlength="40" readonly="readonly"
                                                   class="detail-seat-input auto-width-medium"
                                                   style="width: 170px"/>
                                        </div>
                                       
                                    </div>


                                    <div class="row flex-row-center-center">

                                        <div class="col-md-6 flex-row-center-center" style="white-space: nowrap">
                                            <span class="border-size"
                                                  style="width:70px;text-align-last: justify;">联系方式</span>
                                            <input type="text" name="phone" id="phone" readonly="readonly"
                                                   value="${pd.phone}" class="detail-seat-input auto-width-medium"
                                                   style="width: 170px"/>

                                        </div>
                                        
                                        <div class="col-md-6 flex-row-center-center" style="white-space: nowrap">
                                            <span class="border-size"
                                                  style="width:70px;text-align-last: justify;">发送时间</span>
                                            <input type="text" name="sendtime" id="sendtime"
                                                   value="${pd.sendtime}" maxlength="100" readonly="readonly"
                                                    class="detail-seat-input auto-width-medium"
                                                   style="width: 170px"/>

                                        </div>
                                       
                                    </div>

                                    <div class="row flex-row-center-center">
                                        <div class="col-md-12 flex-row-center-center" style="white-space: nowrap">
                                             <span class="border-size"
                                                   style="width:70px;text-align-last: justify;">发送内容</span>
                                            <textarea name="content" id="content" readonly="readonly" style="width: 300px;height: 100px">${pd.content} </textarea>
                                        </div>

                                    </div>

                                   <div class="new-bc" style="padding: 10px">
<%--                                       <a onclick="save();">保存</a>--%>
<%--                                       <a class="new-qxbut" data-btn-type="cancel" data-dismiss="modal">取消</a>--%>
                                   </div>

                                </div>
                            </div>

                        <!--    <div id="zhongxin2" class="center" style="display:none">
                                <br/>
                                <br/>
                                <br/>
                                <br/>
                                <br/>
                                <img src="static/images/jiazai.gif"/><br/>
                                <h4 class="lighter block green">提交中...</h4>
                            </div>
                        -->
                        </form>

<%--                        <div id="zhongxin2" class="center" style="display:none">--%>
<%--                            <img src="static/images/jzx.gif" style="width: 50px;"/><br/>--%>
<%--                            <h4 class="lighter block green"></h4>--%>
<%--                        </div>--%>
                    </div>
                    <!-- /.col -->
                </div>
                <!-- /.row -->
            </div>
            <!-- /.page-content -->
        </div>
    </div>
    <!-- /.main-content -->
</div>
<!-- /.main-container -->


<!--提示框-->
<script type="text/javascript">



</script>
</body>
</html>