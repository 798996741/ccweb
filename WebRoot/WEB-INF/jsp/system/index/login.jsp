<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>${pd.SYSNAME}</title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <link rel="stylesheet" href="static/login/bootstrap.min.css"/>
    <link rel="stylesheet" href="static/login/css/camera.css"/>
    <link rel="stylesheet" href="static/login/bootstrap-responsive.min.css"/>
    <link rel="stylesheet" href="static/login/matrix-login.css"/>
    <link href="static/login/font-awesome.css" rel="stylesheet"/>

    <link rel="stylesheet" href="static/login/css/common.css"/>
    <link rel="stylesheet" href="static/login/css/main.css"/>
    <link rel="stylesheet" href="static/css/tree.css"/>

    <script type="text/javascript" src="static/login/js/jquery-1.5.1.min.js"></script>
    <!-- 软键盘控件start -->
    <link href="static/login/keypad/css/framework/form.css" rel="stylesheet" type="text/css"/>
    <!-- 软键盘控件end -->
    <style>
        .cavs {
            z-index: 1;
            position: fixed;
            width: 95%;
            margin-left: 20px;
            margin-right: 20px;
        }

        body {
            background: url("static/login/images/back.jpg") no-repeat center;
            background-size: 100% 100%;
            position: relative
        }
        .dr {
		    width: 100%;
		    height: 12%;
		    font-size: 16px;
		    text-align: center;
		    display: flex;
		    justify-content: center;
		    align-items: center;
		    
		    margin-top: 7%;
		   
		}
		.input-btn{
		 	width: 100%;
		 	min-width: 100%;
			border-radius: 15px;
		 	height:100%;
			color: #fff;
			border:0px;
		 	background: -webkit-linear-gradient(left, #0a89ff, #3d5cfa);
		    background: -o-linear-gradient(right, #0a89ff, #3d5cfa);
		    background: -moz-linear-gradient(right, #0a89ff, #3d5cfa);
		    background: linear-gradient(to right, #0a89ff, #3d5cfa);
		}
    </style>
    <script>
        //window.setTimeout(showfh,3000);
        var timer;

        function showfh() {
            fhi = 1;
            //关闭提示晃动屏幕，注释掉这句话即可
            timer = setInterval(xzfh2, 10);
        };
        var current = 0;

        function xzfh() {
            current = (current) % 360;
            document.body.style.transform = 'rotate(' + current + 'deg)';
            current++;
            if (current > 360) {
                current = 0;
            }
        };
        var fhi = 1;
        var current2 = 1;

        function xzfh2() {
            if (fhi > 50) {
                document.body.style.transform = 'rotate(0deg)';
                clearInterval(timer);
                return;
            }
            current = (current2) % 360;
            document.body.style.transform = 'rotate(' + current + 'deg)';
            current++;
            if (current2 == 1) {
                current2 = -1;
            } else {
                current2 = 1;
            }
            fhi++;
        };
    </script>
</head>
<body>


<%-- 	<canvas class="cavs"></canvas> --%>
<!--new-登入页面-->
<!--  <div id="windows1">
<div id="loginbox" > -->
<div class="index-middle">
    <form action="" method="post" name="loginForm" id="loginForm" style="height: 88%;width: 100%;">
        <div class="drjm">
            <div class="drjm-logo">
                <div style="display: flex;flex-direction: row;align-items: center;justify-content: space-between">
                    <img src="static/login/images/logo.png" >
<%--                    <div style="display: flex;flex-direction: column;justify-content: center">--%>
<%--                        <span style="text-align:center;color: #2373FD;font-size: 20px;font-weight: bolder;font-family: 'Microsoft YaHei'">美兰机场客户意见统一管理平台</span>--%>
<%--                    </div>--%>
                </div>
            </div>
            <div class="username">
                <div class="yhm-left"></div>
                <div class="user"><input type="text" name="loginname" id="loginname" value="" placeholder="请输入用户名"/>
                </div>
            </div>
            <div class="username">
                <div class="yhm-left yhm-bjt"></div>
                <div class="user"><input type="password" name="password" id="password" placeholder="请输入密码"
                                         keypadMode="full" allowKeyboard="true" value=""/></div>
            </div>
            <div class="username">
                <div class="yhm-left yzm-bjt"></div>
                <div class="user">
                    <input placeholder="请输入验证码" value="" name="code" id="code">
                    <div class="index-yzm"><img id="codeImg" alt="点击更换" title="点击更换" src=""/></div>
                </div>
            </div>
            <div class="wjmm-q">


                <div class="jzmm">
                    <!-- <div id="check">
                        <span><input type="checkbox" class="input_check" onclick="savePaw();" id="saveid"><label for="check1"></label></span><div class="sb" >记住用户名</div>
                    </div> -->
                    <div id="check">
                        <span><input type="checkbox" class="input_check saveid" onclick="savePaw();" id="check1"><label
                                for="check1"></label></span>
                        <div class="sb">记住用户名</div>
                    </div>
                </div>


            </div>
            <div class="dr"><input class="input-btn" type="button" onclick="severCheck();" id="to-recover" value="登录"></div>
        </div>
    </form>
</div>


<script type="text/javascript">
    //服务器校验
    function severCheck() {
    	
        if (check()) {
        	//$("#to-recover").disabled = true;
        	$("#to-recover").attr("disabled",true);
            var loginname = $("#loginname").val();
            var password = $("#password").val();
            var code = "qq351412933fh" + loginname + ",fh," + password + "QQ351412933fh" + ",fh," + $("#code").val();
            $.ajax({
                type: "POST",
                url: 'login_login',
                data: {KEYDATA: code, tm: new Date().getTime()},
                dataType: 'json',
                cache: false,
                success: function (data) {
                    //alert(data.result);
                    if ("success" == data.result) {
                        saveCookie();
                        
                        window.location.href = "main/index";
                    } else if ("usererror" == data.result) {
                        $("#loginname").tips({
                            side: 1,
                            msg: "用户名或密码有误",
                            bg: '#FF5080',
                            time: 15
                        });
                        //showfh();
                       	$("#to-recover").attr("disabled",false);
                        $("#loginname").focus();
                    } else if ("codeerror" == data.result) {
                        $("#code").tips({
                            side: 1,
                            msg: "验证码输入有误",
                            bg: '#FF5080',
                            time: 15
                        });
                        //showfh();
                        $("#to-recover").attr("disabled",false);
                        $("#code").focus();
                    } else {
                        $("#loginname").tips({
                            side: 1,
                            msg: "缺少参数",
                            bg: '#FF5080',
                            time: 15
                        });
                        //showfh();
                        $("#to-recover").attr("disabled",false);
                        $("#loginname").focus();
                    }
                }
            });
        }
    }

    $(document).ready(function () {
        changeCode1();
        $("#codeImg").bind("click", changeCode1);
        $("#zcodeImg").bind("click", changeCode2);
    });

    $(document).keyup(function (event) {
        if (event.keyCode == 13) {
            $("#to-recover").trigger("click");
        }
    });

    function genTimestamp() {
        var time = new Date();
        return time.getTime();
    }

    function changeCode1() {
        $("#codeImg").attr("src", "code.do?t=" + genTimestamp());
    }

    function changeCode2() {
        $("#zcodeImg").attr("src", "code.do?t=" + genTimestamp());
    }

    //客户端校验
    function check() {

        if ($("#loginname").val() == "") {
            $("#loginname").tips({
                side: 2,
                msg: '用户名不得为空',
                bg: '#AE81FF',
                time: 3
            });
            showfh();
            $("#loginname").focus();
            return false;
        } else {
            $("#loginname").val(jQuery.trim($('#loginname').val()));
        }
        if ($("#password").val() == "") {
            $("#password").tips({
                side: 2,
                msg: '密码不得为空',
                bg: '#AE81FF',
                time: 3
            });
            showfh();
            $("#password").focus();
            return false;
        }
        if ($("#code").val() == "") {
            $("#code").tips({
                side: 1,
                msg: '验证码不得为空',
                bg: '#AE81FF',
                time: 3
            });
            showfh();
            $("#code").focus();
            return false;
        }
        $("#loginbox").tips({
            side: 1,
            msg: '正在登录 , 请稍后 ...',
            bg: '#68B500',
            time: 10
        });

        return true;
    }

    function savePaw() {
        if (!$(".saveid").attr("checked")) {
            $.cookie('loginname', '', {
                expires: -1
            });
            $.cookie('password', '', {
                expires: -1
            });
            $("#loginname").val('');
            $("#password").val('');
        }
    }

    function saveCookie() {
        if ($(".saveid").attr("checked")) {
            $.cookie('loginname', $("#loginname").val(), {
                expires: 7
            });
            $.cookie('password', $("#password").val(), {
                expires: 7
            });
        }
    }

    jQuery(function () {
        var loginname = $.cookie('loginname');
        var password = $.cookie('password');
        if (typeof (loginname) != "undefined"
            && typeof (password) != "undefined") {
            $("#loginname").val(loginname);
            $("#password").val(password);
            $(".saveid").attr("checked", true);
            $("#code").focus();
        }
    });

    //登录注册页面切换
    function changepage(value) {
        if (value == 1) {
            $("#windows1").hide();
            $("#windows2").show();
            changeCode2();
        } else {
            $("#windows2").hide();
            $("#windows1").show();
            changeCode1();
        }
    }

    //注册
    function rcheck() {
        if ($("#USERNAME").val() == "") {
            $("#USERNAME").tips({
                side: 3,
                msg: '输入用户名',
                bg: '#AE81FF',
                time: 2
            });
            $("#USERNAME").focus();
            $("#USERNAME").val('');
            return false;
        } else {
            $("#USERNAME").val(jQuery.trim($('#USERNAME').val()));
        }
        if ($("#PASSWORD").val() == "") {
            $("#PASSWORD").tips({
                side: 3,
                msg: '输入密码',
                bg: '#AE81FF',
                time: 2
            });
            $("#PASSWORD").focus();
            return false;
        }
        if ($("#PASSWORD").val() != $("#chkpwd").val()) {
            $("#chkpwd").tips({
                side: 3,
                msg: '两次密码不相同',
                bg: '#AE81FF',
                time: 3
            });
            $("#chkpwd").focus();
            return false;
        }
        if ($("#name").val() == "") {
            $("#name").tips({
                side: 3,
                msg: '输入姓名',
                bg: '#AE81FF',
                time: 3
            });
            $("#name").focus();
            return false;
        }
        if ($("#EMAIL").val() == "") {
            $("#EMAIL").tips({
                side: 3,
                msg: '输入邮箱',
                bg: '#AE81FF',
                time: 3
            });
            $("#EMAIL").focus();
            return false;
        } else if (!ismail($("#EMAIL").val())) {
            $("#EMAIL").tips({
                side: 3,
                msg: '邮箱格式不正确',
                bg: '#AE81FF',
                time: 3
            });
            $("#EMAIL").focus();
            return false;
        }
        if ($("#rcode").val() == "") {
            $("#rcode").tips({
                side: 1,
                msg: '验证码不得为空',
                bg: '#AE81FF',
                time: 3
            });
            $("#rcode").focus();
            return false;
        }
        return true;
    }

    //提交注册
    function register() {
        if (rcheck()) {
            var nowtime = date2str(new Date(), "yyyyMMdd");
            $.ajax({
                type: "POST",
                url: 'appSysUser/registerSysUser.do',
                data: {
                    USERNAME: $("#USERNAME").val(),
                    PASSWORD: $("#PASSWORD").val(),
                    NAME: $("#name").val(),
                    EMAIL: $("#EMAIL").val(),
                    rcode: $("#rcode").val(),
                    FKEY: $.md5('USERNAME' + nowtime + ',fh,'),
                    tm: new Date().getTime()
                },
                dataType: 'json',
                cache: false,
                success: function (data) {
                    if ("00" == data.result) {
                        $("#windows2").hide();
                        $("#windows1").show();
                        $("#loginbox").tips({
                            side: 1,
                            msg: '注册成功,请登录',
                            bg: '#68B500',
                            time: 3
                        });
                        changeCode1();
                    } else if ("04" == data.result) {
                        $("#USERNAME").tips({
                            side: 1,
                            msg: "用户名已存在",
                            bg: '#FF5080',
                            time: 15
                        });
                        showfh();
                        $("#USERNAME").focus();
                    } else if ("06" == data.result) {
                        $("#rcode").tips({
                            side: 1,
                            msg: "验证码输入有误",
                            bg: '#FF5080',
                            time: 15
                        });
                        showfh();
                        $("#rcode").focus();
                    }
                }
            });
        }
    }

    //邮箱格式校验
    function ismail(mail) {
        return (new RegExp(/^(?:[a-zA-Z0-9]+[_\-\+\.]?)*[a-zA-Z0-9]+@(?:([a-zA-Z0-9]+[_\-]?)*[a-zA-Z0-9]+\.)+([a-zA-Z]{2,})+$/).test(mail));
    }

    //js  日期格式
    function date2str(x, y) {
        var z = {
            y: x.getFullYear(),
            M: x.getMonth() + 1,
            d: x.getDate(),
            h: x.getHours(),
            m: x.getMinutes(),
            s: x.getSeconds()
        };
        return y.replace(/(y+|M+|d+|h+|m+|s+)/g, function (v) {
            return ((v.length > 1 ? "0" : "") + eval('z.' + v.slice(-1))).slice(-(v.length > 2 ? v.length : 2))
        });
    };
</script>
<script>
    //TOCMAT重启之后 点击左侧列表跳转登录首页
    if (window != top) {
        top.location.href = location.href;
    }
</script>
<c:if test="${'1' == pd.msg}">
    <script type="text/javascript">
        $(tsMsg());

        function tsMsg() {
            alert('此用户在其它终端已经早于您登录,您暂时无法登录');
        }
    </script>
</c:if>
<c:if test="${'2' == pd.msg}">
    <script type="text/javascript">
        $(tsMsg());

        function tsMsg() {
            alert('您被系统管理员强制下线或您的帐号在别处登录');
        }
    </script>
</c:if>
<script src="static/login/js/bootstrap.min.js"></script>
<script src="static/js/jquery-1.7.2.js"></script>
<script src="static/login/js/jquery.easing.1.3.js"></script>
<script src="static/login/js/jquery.mobile.customized.min.js"></script>
<script src="static/login/js/camera.min.js"></script>
<script src="static/login/js/templatemo_script.js"></script>
<script src="static/login/js/ban.js"></script>
<script type="text/javascript" src="static/js/jQuery.md5.js"></script>
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<script type="text/javascript" src="static/js/jquery.cookie.js"></script>

</body>

</html>