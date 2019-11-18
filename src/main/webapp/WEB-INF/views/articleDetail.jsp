<%@page contentType="text/html; UTF-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>文章详情</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/articleDetail.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/preview.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/layerSkin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/js/layui/css/layui.css">
</head>

<body>
    <!-- 顶部内容 -->
    <div id="header">
        <iframe src="${pageContext.request.contextPath}/view/header" align="center" width="100%" height="100" frameborder="no" border="0" marginwidth="0" marginheight="0"
                scrolling="no"></iframe>
    </div>

    <!-- 条目内容 -->
    <div class="main_content">
        <div class="title_top">${entry_title}</div>
        <c:forEach var="page" items="${pages}">
            <div class="container">
                <h2>
                    ${page.title}
                    <span id="${page.id}"class="edit_content">编辑全文</span>
                </h2>
                <div class="article_content" id="article_content${page.number}">
                    ${page.content}
                </div>
                <div class="readall_box" id="readall_box${page.number}">
                    <div class="read_more_mask"></div>
                    <span class="read_more_btn" target="_self">阅读全文</span>
                </div>

                <div class="hide_btn" id="hide_btn${page.number}">收起</div>
            </div>
        </c:forEach>
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
    layer.config({
        skin: 'demo-class'//自定义样式demo-class
    });

    var widHeight = $(window).height();
    var content = document.getElementsByClassName("article_content");
    for (let i = 0; i < content.length; i++) {
        //获取文章高度
        var artHeight = $('#' + content[i].id).height();
        $(".hide_btn").hide();

        //阅读全文
        if (artHeight > (widHeight * 0.7)) {
            $('#' + content[i].id).height(widHeight * 0.7 - 285).css({ 'overflow': 'hidden' });

            $('.read_more_btn').click(function () {
                $($(this).parent().prev()).height("").css({ 'overflow': 'hidden' });//article_content
                $($(this).parent()).hide();	//readall_box
                $($(this).parent().next()).show();	//hide_btn
            });
        } else {
            $("#readall_box" + i).hide();
        }

        //收起
        $('#hide_btn' + i).click(function () {
            $('#' + content[i].id).height(widHeight * 0.7 - 285).css({ 'overflow': 'hidden' });//article_content
            $("#readall_box" + i).show();	//readall_box
            $('#hide_btn' + i).hide();	//hide_btn
        });
    }

    $(".edit_content").click(function () {
        var pageID = $(this).attr("id");
        var userCookie = $.cookie('user_name');
        if (userCookie == null || userCookie == "" || userCookie == 'null') {
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

        window.location.href = "${pageContext.request.contextPath}/editPage?entry_title=${entry_title}&page_id=" + pageID;
    });
</script>