<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>">

    <!-- 日期框 -->
    <link rel="stylesheet" href="static/ace/css/datepicker.css"/>
    <!-- 日期框 -->
    <link rel="stylesheet" href="plugins/ueditor/themes/default/css/ueditor.css"/>
    <link type="text/css" rel="stylesheet" href="plugins/zTree/3.5/zTreeStyle.css"/>
    <script type="text/javascript" src="plugins/zTree/3.5/jquery.ztree.core.js"></script>
    <style>
        ul li {
            line-height: 30px;
            list-style-type: none
        }
    </style>
</head>
<body class="no-skin">
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
    <!-- /section:basics/sidebar -->
    <div class="main-content">
        <div class="main-content-inner">
            <div class="page-content">

                <div class="modal-header">
                    <h4 class="modal-title" id="myModalLabel" style="float: left;">操作</h4>
                    <div class="new-tb" style="float: right;" data-dismiss="modal" title="关闭"></div>
                </div>

                <div class="row">
                    <div class="col-xs-12">

                        <form action="zxlb/${msg }.do" name="Form_add" id="Form_add" method="post">
                            <input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
                            <div id="zhongxin" style="padding-top: 13px;">
                                <div id="table_report" class="table table-striped table-bordered table-hover">

                                    <div class="row" style="margin-top: 10px">


                                        <div class="col-md-6 flex-row-center-center" style="white-space: nowrap">
                                            <span style="width:7px;"></span><span class="border-size"
                                                                                  style="width:70px;text-align-last: justify;">角色</span>
                                            <select class="detail-seat-select auto-width-medium" style="width: 170px"
                                                    name="ROLE_ID" id="role_id1" data-placeholder="请选择角色">
                                                <c:forEach items="${rolefrontList}" var="role" varStatus="vs">
                                                    <option value="${role.ROLE_ID}"
                                                            <c:if test="${role.ROLE_ID == pd.ROLE_ID}">selected</c:if>>${role.ROLE_NAME}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                  

                                        <div class="col-md-6 flex-row-center-center" style="white-space: nowrap">
                                            <span style="width:7px;"></span><span class="border-size"
                                                                                  style="width:70px;text-align-last: justify;">所属单位</span>

                                            <input type="hidden" id="dept" name="dept" value="${pd.dept }"
                                                   class="detail-seat-input auto-width-medium" style="width: 170px"/>
                                            <input class="detail-seat-input auto-width-medium" style="width: 170px;"
                                                   id="citySel" name="" type="text" readonly
                                                   value="${pd.areaname }"
                                                   onclick="showMenu(); return false;"/>
                                        </div>
                                    </div>
                                    

                                    <div class="row">
                                        <div class="col-md-6 flex-row-center-center" style="white-space: nowrap">
                                            <span style="width:7px;color: red">*</span><span class="border-size"
                                                                                             style="width:70px;text-align-last: justify;">登录账户</span>
                                            <input type="hidden" name="chk_zxid" id="chk_zxid"
                                                   class="detail-seat-input auto-width-medium" style="width: 170px">
                                            <input type="text" name="ZXID" id="ZXID" value="${pd.ZXID}"
                                                   maxlength="30" placeholder="这里输入登录账户" title="登录账户"
                                                   onblur="hasU()"
                                                   class="detail-seat-input auto-width-medium" style="width: 170px"/>

                                        </div>
                                       

                                        <div class="col-md-6 flex-row-center-center" style="white-space: nowrap">
                                            <span style="width:7px;color: red">*</span><span class="border-size"
                                                                                             style="width:70px;text-align-last: justify;">坐席分机</span>
                                            <input type="text" name="FJHM" id="FJHM"
                                                   value="${pd.FJHM}" maxlength="30"
                                                   placeholder="这里输入坐席分机" title="坐席分机"
                                                   class="detail-seat-input auto-width-medium" style="width: 170px"/>

                                        </div>
                                    </div>
                                
                                    <div class="row">
                                        <div class="col-md-6 flex-row-center-center" style="white-space: nowrap">
                                            <span style="width:7px;color: red">*</span><span class="border-size"
                                                                                             style="width:70px;text-align-last: justify;">坐席角色</span>
                                            <select class="detail-seat-select auto-width-medium" style="width: 170px"
                                                    id="ZXJS" name="ZXJS">
                                                <c:forEach items="${roleList}" var="var" varStatus="vs">
                                                    <option value="${var.DICTIONARIES_ID }"
                                                            <c:if test="${pd.ZXJS==var.DICTIONARIES_ID}">selected</c:if>>${var.NAME }</option>
                                                </c:forEach>
                                            </select>
                                        </div>


                                        <div class="col-md-6 flex-row-center-center" style="white-space: nowrap">
                                            <span style="width:7px;color: red">*</span><span class="border-size"
                                                                                             style="width:70px;text-align-last: justify;">坐席姓名</span>
                                            <input type="text" name="ZXXM" id="ZXXM"
                                                   value="${pd.ZXXM}" maxlength="50"
                                                   placeholder="这里输入坐席姓名" title="坐席姓名"
                                                   class="detail-seat-input auto-width-medium" style="width: 170px"/>

                                        </div>
                                    </div>


                                    <div class="row">
                                        <div class="col-md-6 flex-row-center-center" style="white-space: nowrap">
                                            <span style="width:7px;color: red">*</span><span class="border-size"
                                                                                             style="width:70px;text-align-last: justify;">登录密码</span>
                                            <input type="password" name="PWD" id="PWD"
                                                   value=""
                                                   maxlength="50" placeholder="这里输入登录密码"
                                                   title="登录密码" class="detail-seat-input auto-width-medium"
                                                   style="width: 170px"/>

                                        </div>


                                        <div class="col-md-6 flex-row-center-center" style="white-space: nowrap">
                                            <span style="width:7px;color: red">*</span><span class="border-size"
                                                                                             style="width:70px;text-align-last: justify;">确认密码</span>
                                            <input type="password" name="CHKPWD" id="CHKPWD"
                                                   value="" maxlength="50"
                                                   placeholder="这里输入登录密码"
                                                   title="登录密码" class="detail-seat-input auto-width-medium"
                                                   style="width: 170px"/>

                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6 flex-row-center-center" style="white-space: nowrap">
                                            <span style="width:7px;"></span><span class="border-size"
                                                                                  style="width:70px;text-align-last: justify;text-align: center;line-height: 13px">工单<br/>系统用户</span>
                                            <select class="detail-seat-select auto-width-medium" style="width: 170px"
                                                    id="ZXYH" name="ZXYH">

                                            </select>
                                        </div>

                                        <div class="col-md-6 flex-row-center-center" style="white-space: nowrap">
                                            <span style="width:7px;color: red">*</span><span class="border-size"
                                                                                             style="width:70px;text-align-last: justify;text-align: center;line-height: 13px">状态</span>
                                            <select class="detail-seat-select auto-width-medium" style="width: 170px"
                                                    id="ZT" name="ZT">
                                                <option value="0" <c:if test="${pd.ZT==0}">selected</c:if>>正常
                                                </option>
                                                <option value="-1" <c:if test="${pd.ZT==-1}">selected</c:if>>冻结
                                                </option>
                                            </select>
                                        </div>

                                    </div>

    
						             <div class="col-md-6 flex-row-center-center" style="white-space: nowrap">        
                                            <span style="width:7px;color: red">*</span><span class="border-size"
                                                                                             style="width:70px;text-align-last: justify;text-align: center;line-height: 13px">坐席组<br/>类&nbsp;&nbsp;型&nbsp;&nbsp;</span>


                                            <select id="ZXZ" name="ZXZ" class="detail-seat-select auto-width-medium"
                                                    style="width: 170px">
                                                <c:forEach items="${fzList}" var="var" varStatus="vs">
                                                    <option value="${var.ID }"
                                                            <c:if test="${pd.ZXZ==var.ID}">selected</c:if>>${var.ZMC }</option>
                                                </c:forEach>
                                            </select>
                                        </div>

                                        <div class="col-md-6 flex-row-center-center" style="white-space: nowrap">
                                            <span style="width:7px;color: red">*</span><span class="border-size"
                                                                                             style="width:70px;text-align-last: justify;">坐席工号</span>
                                            <input type="text" name="ZXGH" id="ZXGH"
                                                   value="${pd.ZXGH}" maxlength="50"
                                                   placeholder="这里输入坐席工号" title="坐席工号"
                                                   class="detail-seat-input auto-width-medium"
                                                   style="width: 170px"/>

                                        </div>

                                    </div>
                                    <div class="row">
                                        <div class="col-md-6 flex-row-center-center" style="white-space: nowrap">
                                            <span style="width:7px;color: red">*</span><span class="border-size"
                                                                                             style="width:70px;text-align-last: justify;text-align: center;">坐席类型</span>
                                            <%-- <select class="detail-seat-select auto-width-medium" style="width: 170px"
                                                    id="ZXLX" name="ZXLX">
                                                <c:forEach items="${dictList}" var="var" varStatus="vs">
                                                    <option value="${var.DICTIONARIES_ID }"
                                                            <c:if test="${pd.ZXLX==var.DICTIONARIES_ID}">selected</c:if>>${var.NAME }</option>
                                                </c:forEach>
                                            </select>
                                             --%>
                                            <div style="display:flex;flex-wrap:wrap;align-items: center;justify-content: flex-start;width: 190px;">
                                                <input type="hidden" name="ZXLX" id="ZXLX" >
                                                <c:forEach items="${dictList }" var="var">
                                                    <div class="flex-row-center-center" style="margin: 0 15px">
                                                        <input style="vertical-align: middle;margin: 0 !important;cursor: pointer"
                                                               name="ZXLXCHECK"
                                                               value="${var.DICTIONARIES_ID}"
                                                               type="checkbox"
                                                               id="ZXLXCHECK">${var.NAME}
                                                    </div>
                                                </c:forEach>
                                            </div>
                                           
                                        </div>
                                    </div>


                                    <div class="new-bc" style="padding: 10px">
                                            <a onclick="save();">保存</a>
                                            <a class="new-qxbut" data-btn-type="cancel" data-dismiss="modal">取消</a>
                                        </div>


                                    </div>
                                </div>
                                <div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img
                                        src="static/images/jiazai.gif"/><br/><h4 class="lighter block green">提交中...</h4>
                                </div>
                        </form>
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
<div id="menuContent" class="menuContent"
     style="min-height:250px;display:none; position: absolute;background:rgb(238,242,245);border:1px #ccc solid">
    <ul id="treeDemo" class="ztree" style="margin-top:0; width:200px;"></ul>
</div>
<!-- /.main-container -->
<!-- 日期框 -->
<%@ include file="../../system/include/incJs_foot.jsp" %>
<script type="text/javascript">


    var setting = {
        view: {
            dblClickExpand: false
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            beforeClick: beforeClick,
            onClick: onClick
        }
    };
    var zn = '${zTreeNodes}';
    var zNodes = eval(zn);


    
    
    function beforeClick(treeId, treeNode) {
        //var check = (treeNode && !treeNode.isParent);
        //if (!check) alert("只能选择城市...");
        //return check;
    }
    
    
	function getUsers(code){
		if(code!=""){
			$.ajax({
              type: 'post',
              url: "<%=path%>/zxlb/getUsers.do",
              dataType: 'json',
              data: {"DWBM": code,"ID": '${pd.ID}',"ZXYH": '${pd.ZXYH}'}, //参数值
              cache: false,
              success: function (result) {

                
			 var ZXYH='${pd.ZXYH}';
                  //var str = "<select id=\"tstypes\" name=\"tstypes\" >";
                  var str = str + "<option value=\"\">请选择系统用户</option>";
                  $.each(result.list, function (i, list) {
                      if (ZXYH == list.USERNAME) {
                          str = str + "<option value=\"" + list.USERNAME + "\" selected>" + list.NAME + "</option>";
                      } else {
                          str = str + "<option value=\"" + list.USERNAME + "\">" + list.NAME + "</option>";
                      }
                  });
                  $("#ZXYH").html('');
                  $("#ZXYH").html(str);

              }, error: function (jqXHR, textStatus, errorThrown) {
                  alert(jqXHR.responseText);
                  return false;
              }
          });
       }
		
	}
    
    
    
    function onClick(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
            nodes = zTree.getSelectedNodes(),
            v = "";
        nodes.sort(function compare(a, b) {
            return a.id - b.id;
        });
        for (var i = 0, l = nodes.length; i < l; i++) {
            v += nodes[i].name + ",";
        }
        if (v.length > 0) v = v.substring(0, v.length - 1);
        var cityObj = $("#citySel");
        cityObj.attr("value", v);
        $("#dept").val(treeNode.AREA_CODE);
        hideMenu();
        getUsers(treeNode.AREA_CODE);
    }

    function showMenu() {

        var cityObj = $("#citySel");
        var cityOffset = $("#citySel").offset();
        $("#menuContent").css({
            left: (cityOffset.left - 250) + "px",
            top: (cityOffset.top - 110) + cityObj.outerHeight() + "px"
        }).slideDown("fast");
        $("body").bind("mousedown", onBodyDown);
    }

    function hideMenu() {
        $("#menuContent").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDown);
    }

    function onBodyDown(event) {
        if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length > 0)) {
            hideMenu();
        }
    }

    $(document).ready(function () {
        $.fn.zTree.init($("#treeDemo"), setting, zNodes);
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        treeObj.expandAll(true);
        if ('${pd.AREA_ID}' != "") {
            //treeObj.checkNode(treeObj.getNodeByParam("id", '${pd.AREA_ID}'), true);
        }
        
        var str = '${pd.ZXLX}';
    	$(str.split(",")).each(function (i,dom){
    	    $(":checkbox[value='"+dom+"']").prop("checked",true);
    	});
    	if('${pd.dept }'!=""){
    		getUsers('${pd.dept }');
    	}


    });
    //$(top.hangge());
    //保存
    //var ue = UE.getEditor("DETAIL");
    $('.date-picker').datepicker({
        autoclose: true,
        todayHighlight: true,
        clearBtn: true
    });


    //判断用户名是否存在
    function hasU() {
        var ZXID = $.trim($("#ZXID").val());
        var ID = $.trim($("#ID").val());
        $.ajax({
            type: "POST",
            url: '<%=basePath%>zxlb/hasU.do',
            data: {ZXID: ZXID, ID: ID, tm: new Date().getTime()},
            dataType: 'json',
            cache: false,
            success: function (data) {
                if ("success" == data.result) {
                    //$("#userForm_add").submit();
                    //$("#zhongxin").hide();
                    //$("#zhongxin2").show();
                    $("#chk_zxid").val('');
                } else {
                    $("#chk_zxid").val('1');
                    $("#ZXID").tips({
                        side: 3,
                        msg: '您输入的登录账户不正确，登录账户已存在，请重新输入！',
                        bg: '#AE81FF',
                        time: 2
                    });
                }
            }
        });
    }


    function save() {
        var chk_zxid = $("#chk_zxid").val();

        if ($("#dept").val() == "") {
            layer.alert("请选择单位");
            $("#dept").focus();
            return false;
        }
        if ($("#ZXID").val() == "") {
            layer.alert("请输入登录账户");
            $("#ZXID").focus();
            return false;
        } else {
            if (isNaN($("#ZXID").val())) {
                layer.alert("您输入的登录账户不正确，登录账户为数字！");
                $("#ZXID").focus();
                return false;
            }

            if (chk_zxid == '1') {
                layer.alert("您输入的登录账户不正确，登录账户已存在，请重新输入！");
                $("#ZXID").focus();
                $("#ZXID").val('');
                return false;
            }

        }


        if ($("#FJHM").val() == "") {

            layer.alert("请输入坐席分机");
            $("#FJHM").focus();
            return false;
        } else {
            if (isNaN($("#FJHM").val())) {
                layer.alert("您输入的分机号码不正确，分机号码数字！");
                $("#FJHM").focus();
                return false;
            }
        }

        if ($("#ZXXM").val() == "") {
            layer.alert("请输入坐席姓名");
            $("#ZXXM").focus();
            return false;
        }


        var msg = '${msg }';
        if (msg == "save") {
            if ($("#PWD").val() == "") {
                layer.alert("请输入密码，密码不能为空");
                $("#PWD").focus();
                return false;
            }
        }
        var userPwd=$("#PWD").val();
        if (userPwd.length < 8) {
        	layer.alert("密码长度不能小于8位!");
            return false;
        }
		var reg7 = /\d*\D*((\d+[a-zA-Z]+[^0-9a-zA-Z]+)|(\d+[^0-9a-zA-Z]+[a-zA-Z]+)|([a-zA-Z]+\d+[^0-9a-zA-Z]+)|([a-zA-Z]+[^0-9a-zA-Z]+\d+)|([^0-9a-zA-Z]+[a-zA-Z]+\d+)|([^0-9a-zA-Z]+\d+[a-zA-Z]+))\d*\D*/; //数字字母字符任意组合
		if (!reg7.test(userPwd)) {
			layer.alert("您输入的密码太简单，需要数字、字母、字符进行组合!");
           	return false;
        }

        if ($("#PWD").val() != "") {
            if ($("#PWD").val() != $("#CHKPWD").val()) {

                layer.alert("两次输入的密码不一样");
                $("#PWD").focus();
                return false;
            }

        }


        if ($("#ZXGH").val() == "") {

            layer.alert("请输入坐席工号");
            $("#ZXGH").focus();
            return false;
        } else {
            if (isNaN($("#ZXGH").val())) {
                layer.alert("您输入的坐席工号不正确，坐席工号应为数字！");
                $("#ZXGH").focus();
                return false;
            }
        }
		//alert($("#ZXYH").val());
		if($("#ZXZ").val()=='6731267390714f179c2d6f005078c2c3'){  //如果是坐席组需要增加增加工单用户信息
			 if($("#ZXYH").val()==""){
	        	layer.alert("请选择工单系统用户");
	         	$("#ZXYH").focus();
	         	return false;
	        } 
		}
		
		
		var ZJN = "";
	    var conditions = document.getElementsByName("ZXLXCHECK");
	    for (var i = 0; i < conditions.length; i++) {
            if (conditions[i].checked == true) {
                if (ZJN != "") {
                    ZJN = ZJN + ",";
                }
                ZJN = ZJN + conditions[i].value;
            }
        }
	    if(ZJN!=""){
	    	$("#ZXLX").val(ZJN);
	    }
	        
        if ($("#ZXZ").val() == '4a3c4a68997043f8a8deb12f7f124e7a') {  //如果是坐席组需要增加增加工单用户信息
            if ($("#ZXYH").val() == "") {
                layer.alert("请选择工单系统用户");
                $("#ZXYH").focus();
                return false;
            }
        }

        //alert($("#ZXYH").val());
        //$("#zhongxin").hide();
        //$("#zhongxin2").show();
        $.ajax({
            //几个参数需要注意一下
            type: "POST",//方法类型
            dataType: "html",//预期服务器返回的数据类型
            url: "zxlb/${msg }.do",//url
            data: $('#Form_add').serialize(),
            success: function (result) {
                // alert(result);//打印服务端返回的数据(调试用)
                if (result.indexOf("success_add") >= 0) {
                    location.href = "<%=basePath%>zxlb/list.do";
                } else if (result.indexOf("error1") >= 0) {
                    layer.alert("坐席用户已存在，请重新选择！");
                    return false;
                }

            },
            error: function () {
                layer.alert("异常！");
            }
        });
    }


</script>
</body>
</html>