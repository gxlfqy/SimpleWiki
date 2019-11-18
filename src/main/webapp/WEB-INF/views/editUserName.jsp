<%@page contentType="text/html; UTF-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>修改用户名</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/editMessage.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/register.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/layerSkin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/js/layui/css/layui.css">
    <style>
        .bg {
            margin-top: -200px !important;
        }

        body .layui-layer-dialog {
            max-width: 260px;
        }
    </style>
</head>

<body>
    <center>
        <div class="bg">
            <div class="text">修改用户名</div>
            <!-- <div class="msg">${msg}</div> -->
            <!-- <form action="" method="post" onsubmit="return toVaild()"> -->
            <div class="row">
                <img src="${pageContext.request.contextPath}/res/img/login/user.png" alt="">
                <input type="text" name="name" maxlength="12" class="message" id="userName" readonly value="${user.name}">
            </div>
            <div class="row">
                <img src="${pageContext.request.contextPath}/res/img/login/password.png" alt="">
                <input type="text" name="pwd" placeholder="请输入新用户名" maxlength="12" class="message" id="new_userName">
            </div>
            <div class="row">
                <img src="${pageContext.request.contextPath}/res/img/login/password.png" alt="">
                <input type="password" name="password" placeholder="请输入密码" maxlength="12" class="message" id="password">
            </div>
            <div class="row">
                <input type="button" value="提交" id="submit">
                <input type="button" value="取消" id="cancle">
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
    layer.config({
        skin: 'demo-class'//自定义样式demo-class
    });

    //提交
    $("#submit").click(function () {
        //验证汉字正则表达式
        var reg = /.*[\u4e00-\u9fa5]+.*$/;

        var password = $("#password").val();
        var new_userName = $("#new_userName").val();

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
        //新用户名
        if (new_userName == "") {
            layer.open({
                title: '温馨提示',
                content: '用户名不能为空'
            });
            return false;
        }
        if (reg.test(new_userName)) {
            layer.open({
                title: '温馨提示',
                content: '用户名不能含有中文！'
            });
            return false;
        }
        $.ajax({
            type: 'POST',
            data: {
                user_name: new_userName,
                password: password
            },
            dataType: 'json',
            url: '${pageContext.request.contextPath}/user/change/name',
            success: function (data) {
                if (data.succeed == true) {
                    layer.open({
                        title: '温馨提示'
                        , content: '修改成功！'
                    });
                    $.cookie('user_name', new_userName);
                    window.location.href = '${pageContext.request.contextPath}/index';
                }
                if (data.succeed == false) {
                    layer.open({
                        title: '温馨提示'
                        , content: '服务器繁忙！'
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

    // 取消
    $("#cancle").click(function () {
        window.location.href = "${pageContext.request.contextPath}/index";
    });
</script>