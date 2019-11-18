<%@page contentType="text/html; UTF-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>注册</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/register.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/layerSkin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/js/layui/css/layui.css">
</head>

<body>
    <center>
        <div class="bg">
            <div class="text">欢迎注册</div>
            <!-- <div class="msg">${msg}</div> -->
            <!-- <form action="" method="post" onsubmit="return toVaild()"> -->
            <div class="row">
                <img src="${pageContext.request.contextPath}/res/img/login/user.png" alt="">
                <input type="text" name="name" placeholder="请输入用户名" maxlength="12" class="message" id="userName">
            </div>
            <div class="row">
                <img src="${pageContext.request.contextPath}/res/img/login/password.png" alt="">
                <input type="password" name="password" placeholder="请输入密码" maxlength="12" class="message" id="password">
            </div>
            <div class="row">
                <img src="${pageContext.request.contextPath}/res/img/login/password.png" alt="">
                <input type="password" name="pwd" placeholder="确认密码" maxlength="12" class="message" id="certain_password">
            </div>
            <div class="row">
                <img src="${pageContext.request.contextPath}/res/img/login/code.jpg" alt="">
                <input type="text" placeholder="请输入验证码" maxlength="5" class="message" id="code">
                <div id="v_container" style="width: 120px;height: 45px;"></div>
            </div>
            <div class="goLogin">
                <span id="goLogin">已有账号？前往登录</span>
            </div>
            <div class="row">
                <input type="button" value="注册" id="submit">
            </div>
            <!-- </form> -->
        </div>
    </center>
</body>

</html>

<script src="${pageContext.request.contextPath}/res/js/jquery-3.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/res/js/gVerify.js"></script>
<script src="${pageContext.request.contextPath}/res/js/jquery.cookie.js"></script>
<script src="${pageContext.request.contextPath}/res/js/layui/layui.all.js"></script>
<script>
    layer.config({
        skin: 'demo-class'//自定义样式demo-class
    })
    var verifyCode = new GVerify("v_container");
    $("#submit").click(function () {
        //验证汉字正则表达式
        var reg = /.*[\u4e00-\u9fa5]+.*$/;

        var userName = $("#userName").val();
        var password = $("#password").val();
        var certain_password = $("#certain_password").val();
        var code = $("#code").val();
        var codeStr = verifyCode.validate(code);

        //用户名
        if (userName == "") {
            layer.open({
                title: '温馨提示',
                content: '用户名不能为空'
            });
            return false;
        }
        if (userName.length > 12) {
            layer.open({
                title: '温馨提示',
                content: '用户名不能大于12位'
            });
            return false;
        }
        if (reg.test(userName)) {
            layer.open({
                title: '温馨提示',
                content: '用户名不能含有中文！'
            });
            return false;
        }
        //密码
        if (password == "") {
            layer.open({
                title: '温馨提示',
                content: '密码不能为空'
            });
            return false;
        }
        if (password.length < 6 || password.length > 12) {
            layer.open({
                title: '温馨提示',
                content: '密码长度需在6到12位之间'
            });
            return false;
        }
        if (reg.test(password)) {
            layer.open({
                title: '温馨提示',
                content: '密码不能含有中文'
            });
            return false;
        }
        if (certain_password != password) {
            layer.open({
                title: '温馨提示',
                content: '密码不一致'
            });
            return false;
        }
        //验证码
        if (!codeStr) {
            layer.open({
                title: '温馨提示',
                content: '验证码错误'
            });
            return false;
        }
        $.ajax({
            type: 'POST',
            data: {
                'user_name': userName,
                'password': password,
                'email': ''
            },
            dataType: 'json',
            url: '${pageContext.request.contextPath}/user/signup',
            success: function (data) {
                console.log(data);
                if (data.succeed == true) {
                    window.location.href = '${pageContext.request.contextPath}/signin';
                }
                if (data.succeed == false) {
                    layer.open({
                        title: '温馨提示'
                        , content: data.obj.errmsg
                    });
                }
            },
            error: function (e) {
                console.log(e);
                layer.open({
                    title: '温馨提示'
                    , content: '服务器繁忙！'
                });
            }
        })
    });

    $("#goLogin").click(function () {
        window.location.href = '${pageContext.request.contextPath}/signin';
    });
</script>