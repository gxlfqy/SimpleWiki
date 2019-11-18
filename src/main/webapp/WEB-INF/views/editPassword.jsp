<%@ page contentType="text/html; UTF-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>修改用户登录密码</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/editMessage.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/register.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/layerSkin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/js/layui/css/layui.css">
</head>

<body>
    <center>
        <div class="bg">
            <div class="text">修改密码</div>
            <!-- <div class="msg">${msg}</div> -->
            <!-- <form action="" method="post" onsubmit="return toVaild()"> -->
            <div class="row">
                <img src="${pageContext.request.contextPath}/res/img/login/user.png" alt="">
                <input type="text" name="name" maxlength="12" class="message" id="userName" readonly value="${user.name}">
            </div>
            <div class="row">
                <img src="${pageContext.request.contextPath}/res/img/login/password.png" alt="">
                <input type="password" name="password" placeholder="请输入原密码" maxlength="12" class="message" id="password">
            </div>
            <div class="row">
                <img src="${pageContext.request.contextPath}/res/img/login/password.png" alt="">
                <input type="password" name="pwd" placeholder="请输入新密码" maxlength="12" class="message" id="new_password">
            </div>
            <div class="row">
                <img src="${pageContext.request.contextPath}/res/img/login/password.png" alt="">
                <input type="password" name="pwd" placeholder="确认新密码" maxlength="12" class="message" id="certain_password">
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

        var old_password = $("#password").val();
        var new_password = $("#new_password").val();
        var certain_password = $("#certain_password").val();

        //密码
        if (old_password == "") {
            layer.open({
                title: '温馨提示',
                content: '密码不能为空'
            });
            return false;
        }
        if (new_password == "") {
            layer.open({
                title: '温馨提示',
                content: '新密码不能为空'
            });
            return false;
        }
        if (new_password.length < 6 || new_password.length > 12 || old_password.length < 6 || old_password.length > 12) {
            layer.open({
                title: '温馨提示',
                content: '密码长度需在6到12位之间'
            });
            return false;
        }
        if (reg.test(new_password)) {
            layer.open({
                title: '温馨提示',
                content: '密码不能含有中文'
            });
            return false;
        }
        if (certain_password != new_password) {
            layer.open({
                title: '温馨提示',
                content: '密码不一致'
            });
            return false;
        }
        $.ajax({
            type: 'POST',
            data: {
                'password': old_password,
                'new_password': new_password,
            },
            dataType: 'json',
            url: '${pageContext.request.contextPath}/user/change/password',
            success: function (data) {
                console.log(data);
                if (data.succeed == true) {
                    layer.open({
                        title: '温馨提示'
                        , content: '修改成功！'
                    });
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