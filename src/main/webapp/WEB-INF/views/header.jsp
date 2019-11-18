<%@page contentType="text/html; UTF-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>顶部</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/layerSkin.css">
</head>

<body>
    <div class="head_bg">
        <div class="header">
            <div class="left">
                <div>
                    <img src="${pageContext.request.contextPath}/res/img/home/brand_white.png" alt="" class="logo">
                </div>
                <img src="${pageContext.request.contextPath}/res/img/index/weibo.svg" alt="">
                <img src="${pageContext.request.contextPath}/res/img/index/wechat.svg" alt="">
                <img src="${pageContext.request.contextPath}/res/img/index/bilibili.svg" alt="">
            </div>
            <div class="right logined">
                <span class="welcome">
                    <span class="user">用户名</span>,欢迎您
                </span>
                <span class="exit">退出</span>
            </div>

            <div class="right no_login">
                <div class="login_btn">
                    <span>登录</span>
                </div>
            </div>
        </div>
    </div>
</body>

</html>
<script src="${pageContext.request.contextPath}/res/js/jquery-3.2.1.min.js"></script>
<script language=javascript src='${pageContext.request.contextPath}/res/js/jquery.cookie.js'></script>
<script src="${pageContext.request.contextPath}/res/js/layui/layui.all.js"></script>
<script type="text/javascript">
    function clr_cookie() {
        $.cookie("user_name", "", {expires: -1});
        $.cookie("user_id", "", {expires: -1});
        $.cookie("access_id", "", {expires: -1});
        $.cookie("ACCESS", "", {expires: -1});
    }

    $(function () {
        $(".logo").click(function () {
            window.parent.window.location.href = "${pageContext.request.contextPath}/";
        });

        //登录
        $(".login_btn").click(function () {
            window.parent.window.location.href = "${pageContext.request.contextPath}/signin";
        });

        $(".user").click(function () {
            window.parent.window.location.href = "${pageContext.request.contextPath}/home";
        });

        //退出
        $(".exit").click(function () {
            clr_cookie();
            window.parent.window.location.href = "${pageContext.request.contextPath}/signin";
        });
    });

    if ($.cookie('ACCESS') === "true") {
        $(".no_login").hide();
        $(".logined").show();
        $(".user").text($.cookie('user_name'));
    } else {
        $(".no_login").show();
        $(".logined").hide();
    }
</script>
