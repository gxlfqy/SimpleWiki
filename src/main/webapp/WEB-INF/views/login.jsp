<%@page contentType="text/html; UTF-8" pageEncoding="utf-8" %>

    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>登录</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/login.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/layerSkin.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/res/js/layui/css/layui.css">
    </head>

    <body>
        <center>
            <div class="bg">
                <div class="text">用户登录</div>
                <div class="msg">${msg}</div>
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
                    <img src="${pageContext.request.contextPath}/res/img/login/code.jpg" alt="">
                    <input type="text" placeholder="请输入验证码" maxlength="5" class="message" id="code">
                    <div id="v_container" style="width: 120px;height: 44px;">
                        <img src="" id="code_img" alt="" style="width: 120px;height: 44px;">
                    </div>
                </div>
                <div class="goLogin">
                    <span id="goLogin">没有账号？前往注册</span>
                </div>
                <div class="row">
                    <input type="button" value="登录" id="submit">
                </div>
                <!-- </form> -->
            </div>
        </center>
    </body>

    </html>

    <script src="${pageContext.request.contextPath}/res/js/jquery-3.2.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/res/js/jquery.cookie.js"></script>
    <script src="${pageContext.request.contextPath}/res/js/layui/layui.all.js"></script>
    <script>
        $("#code").focus(function () {
            if ($("#code_img").attr("src") == "") {
                getVerifyCode();
            }
        });

        $("#code_img").click(function () {
            getVerifyCode();
        });

        //获取验证码
        function getVerifyCode() {
            document.getElementById("code_img").src = "${pageContext.request.contextPath}/user/verifycode?" + Math.random();
        }

        $("#submit").click(function () {
            //验证汉字正则表达式
            var reg = /.*[\u4e00-\u9fa5]+.*$/;

            var userName = $("#userName").val();
            var password = $("#password").val();
            var code = $("#code").val();
            //用户名
            if (userName == "") {
                layer.open({
                    title: '温馨提示',
                    content: '用户名不能为空'
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
            //验证码
            if (code == "") {
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
                    'verifycode': code
                },
                dataType: 'json',
                url: '${pageContext.request.contextPath}/user/signin',
                success: function (data) {
                    if (!data.succeed) {
                        layer.open({
                            title: '温馨提示'
                            , content: data.obj.errmsg
                        });
                        getVerifyCode();
                        return;
                    }

                    $.cookie('user_name', userName, { expires: 14 });
                    $.cookie('user_id', data.obj.user.id, { expires: 14 });
                    $.cookie('access_id', data.obj.id, { expires: 14 });
                    $.cookie('ACCESS', true, { expires: 14 });
                    window.location.href = '${pageContext.request.contextPath}/';
                },
                error: function (e) {
                    layer.open({
                        title: '温馨提示'
                        , content: '服务器繁忙！'
                    });
                }
            })
        });

        $("#goLogin").click(function () {
            window.location.href = '${pageContext.request.contextPath}/signup';
        });
    </script>