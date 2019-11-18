<%@ page contentType="text/html; UTF-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>主页</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/index.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/layerSkin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/js/layui/css/layui.css">
</head>

<body>
    <!-- 顶部内容 -->
    <div class="bg_img">
        <!-- 顶部 -->
        <div class="header">
            <div class="left">
                <img src="${pageContext.request.contextPath}/res/img/index/weibo.svg" alt="">
                <img src="${pageContext.request.contextPath}/res/img/index/wechat.svg" alt="">
                <img src="${pageContext.request.contextPath}/res/img/index/bilibili.svg" alt="">
            </div>

            <div class="right exit_btn">
                <span class="welcome">
                    <span class="user">用户名</span>,欢迎您
                </span>
                <span class="exit">退出</span>
            </div>

            <div class="right login_btn">
                <span>登录</span>
            </div>

            <div class="middle">
                <input type="text" class="input_search">
                <img src="${pageContext.request.contextPath}/res/img/index/search-new.svg" alt="">
            </div>
        </div>

        <!-- 主页内容  -->
        <div class="content">
            <img src="${pageContext.request.contextPath}/res/img/index/logo-pet.png" alt="" class="logo_img">
            <div class="logo_text">
                SimpleWiki是一本涵盖了日常生活所涉及方方面面的百科手册，你可以试着检索一个条目
                <br><br>
                <span style="float: right">——  Easy Wiki,Simple life</span>
            </div>
            <!-- <div style="display:flex;">
                <div class="recommad">推荐阅读：</div>
                <div class="logo_title"> 怎么科学地养猪？</div>
            </div> -->
        </div>

        <img src="${pageContext.request.contextPath}/res/img/index/pig.jpg" alt="" class="pig">
    </div>

    <!-- 条目内容 -->
    <div class="main_content">
        <div class="main_title">
            <span>全部条目</span>
            <span>添加条目</span>
        </div>
        <!-- 添加条目 -->
        <div class="addTitle">
            <textarea type="text" maxlength="30" id="addTitle" placeholder="请输入新的条目名称(不多于30字)"></textarea>
            <div class="btn_bg">
                <div class="btn submit">提交</div>
                <div class="btn cancel">取消</div>
            </div>
        </div>
        <!-- 目录 -->
        <div class="title_bg">
            <div class="title_content">
                <c:forEach var="entry" items="${list}">
                <div class="title">
                    <span class="word">${entry.title}</span>
<%--                    <span class="add">添加文章</span>--%>
                </div>
                </c:forEach>
<%--                <div class="title">--%>
<%--                    <span class="word">农业专业及辅助性活动</span>--%>
<%--                    <!-- <span class="article_type">[科幻类]</span> -->--%>
<%--                    <span class="add">添加文章</span>--%>
<%--                </div>--%>
            </div>
        </div>
    </div>

    <!-- 底部 -->
    <div id="footer">
        <iframe src="${pageContext.request.contextPath}/res/html/footer.html" align="center" width="100%" height="80" frameborder="no" border="0" marginwidth="0" marginheight="0"
                scrolling="no"></iframe>
    </div>
</body>

</html>

<script src="${pageContext.request.contextPath}/res/js/jquery-3.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/res/js/layui/layui.all.js"></script>
<script src="${pageContext.request.contextPath}/res/js/jquery.cookie.js"></script>
<script type="text/javascript">
    // 使用许可证登录
    function access() {
        // 清除cookie中的许可信息
        function clr_cookie() {
            $.cookie("user_name", "", {expires: -1});
            $.cookie("user_id", "", {expires: -1});
            $.cookie("access_id", "", {expires: -1});
            $.cookie("ACCESS", "", {expires: -1});
        }

        let user_id = $.cookie('user_id');
        let access_id = $.cookie('access_id');
        let user_name = $.cookie('user_name');
        if (user_id == null || user_id === '' || access_id == null || access_id === '' || user_name == null || user_name === '') {
            clr_cookie();
            return;
        }

        $.ajax({
            type: 'POST',
            dataType: 'json',
            data: {
                user_id: user_id,
                access_id: access_id
            },
            url: "${pageContext.request.contextPath}/user/access",
            success: function (data) {
                if (data.succeed) {
                    $.cookie('ACCESS', true, {expires: 1});
                    return;
                }

                if (data.obj.errcode === 0x34000000) {
                    clr_cookie();
                    layer.confirm('登录信息失效, 请重新登录', {
                        title: "温馨提示",
                        btn: ['确认'],
                    }, function() {
                        window.location = "${pageContext.request.contextPath}/signin";
                    });
                }
            }
        });
    }

    $(function () {
        if ($.cookie('ACCESS') === "true") {
            // $(".exit_btn").show();
            $(".exit_btn").show();
            $(".login_btn").hide();
            $(".user").text($.cookie('user_name'));
        } else {
            // $(".exit").hide();
            // $(".logined").hide();
            $(".exit_btn").hide();
            $(".login_btn").show();
        }
        access();

        <%--// 请求词条信息--%>
        <%--$.ajax({--%>
        <%--    type: 'POST',--%>
        <%--    dataType: 'json',--%>
        <%--    data: "",--%>
        <%--    url: '${pageContext.request.contextPath}/entry/list',--%>
        <%--    success: function (data) {--%>
        <%--        if (data.succeed) {--%>
        <%--            console.log(data);--%>
        <%--        }--%>

        <%--        if (!data.succeed) {--%>
        <%--            layer.open({--%>
        <%--                title: '温馨提示'--%>
        <%--                , content: data.obj.errmsg--%>
        <%--            });--%>
        <%--        }--%>
        <%--    },--%>
        <%--    error: function (e) {--%>
        <%--        layer.open({--%>
        <%--            title: '温馨提示'--%>
        <%--            , content: '服务器繁忙！'--%>
        <%--        });--%>
        <%--    }--%>
        <%--});--%>

        // 判断用户是否登录
        function isLogin() {
            layer.config({
                skin: 'demo-class'//自定义样式demo-class
            });
            if (!$.cookie('ACCESS')) {
                layer.confirm('请先登录', {
                    title: "温馨提示",
                    btn: ['稍后登录', '前往登录'], //可以无限个按钮
                }, function (index, layero) {
                    layer.close(index);
                }, function (index) {
                    window.location.href = '${pageContext.request.contextPath}/signin';
                });
                return false;
            }
            return true;
        }

        $(".user").click(function () {
            window.open("${pageContext.request.contextPath}/home");
        });

        //登录
        $(".login_btn").click(function () {
            window.open("${pageContext.request.contextPath}/signin");
        });

        //退出
        $(".exit_btn").click(function () {
            clr_cookie();

            $(".exit_btn").hide();
            $(".login_btn").show();
        });

        <%--//添加文章--%>
        <%--$(".add").click(function () {--%>
        <%--    if (isLogin()) {--%>
        <%--        window.open("${pageContext.request.contextPath}/addPage/" + $(this).closest("span").innerText);--%>
        <%--    }--%>
        <%--});--%>

        //条目文章详情
        $(".word").click(function () {
            // alert("" + $(this).val());
            window.open("${pageContext.request.contextPath}/wiki/" + $(this).html());
        });

        //添加条目
        $(".main_title span:last-child").click(function () {
            if (isLogin()) {
                $(".addTitle").show();
            }
        });
        $(".cancel").click(function () {
            $(".addTitle").hide();
        });
        $(".submit").click(function () {
            var titleMsg = $("#addTitle").val();
            if (titleMsg == "") {
                layer.open({
                    title: '温馨提示'
                    , content: '不能为空！'
                });
                return false;
            }
            $.ajax({
                type: 'POST',
                dataType: 'json',
                data: {
                    entry_title: titleMsg
                },
                url: '${pageContext.request.contextPath}/entry/create',
                success: function (data) {
                    if (data.succeed) {
                        console.log(data);
                        $(".addTitle").hide();
                        location.reload();
                        window.open("${pageContext.request.contextPath}/addPage/" + titleMsg);
                    }
                    if (!data.succeed) {
                        layer.open({
                            title: '温馨提示'
                            , content: data.obj.errmsg
                        });
                    }
                },
                error: function (e) {
                    layer.open({
                        title: '温馨提示'
                        , content: '出错了！'
                    });
                }
            });
        });
    });
</script>