<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; UTF-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>编辑修改条目</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/editTitle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/layerSkin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/js/layui/css/layui.css">
</head>

<body>
    <!-- 顶部 -->
    <div id="header">
        <iframe src="${pageContext.request.contextPath}/view/header" align="center" width="100%" height="100" frameborder="no" border="0" marginwidth="0" marginheight="0"
                scrolling="no"></iframe>
    </div>

    <!-- 中间内容 -->
    <div class="mid_content">
        <div class="edit_title">
            <span>修改条目</span>
        </div>
        <div class="origion_title">
            <span>原 条 目 为 ：</span>
            <span id="old_title">${entry.title}</span>
        </div>
        <div class="new_title">
            <span>新 条 目 为 ：</span>
            <textarea type="text" maxlength="30" id="addTitle" placeholder="请输入新的条目名称(不多于30字)"></textarea>
        </div>
        <div class="title_list">
            <span>条目所属内容：</span>
            <c:forEach var="page" items="${entry.page}">
            <div class="title">
                <span class="word">${page.title}</span>
                <!-- <span class="article_type">[科幻类]</span> -->
            </div>
            </c:forEach>
        </div>

        <div class="btn_bg">
            <div class="btn submit">确认修改</div>
            <div class="btn cancel">取消</div>
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
<script type="text/javascript">
    layer.config({
        skin: 'demo-class'//自定义样式demo-class
    });

    //修改
    $(".submit").click(function () {
        var titleMsg = $("#addTitle").val();
        var oldTitle = $("#old_title").html();
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
                entry_title: oldTitle,
                new_title: titleMsg
            },
            url: '${pageContext.request.contextPath}/entry/edit/title',
            success: function (data) {
                if (data.succeed == true) {
                    console.log(data);
                    layer.open({
                        title: '温馨提示'
                        , content: '修改成功。'
                    });
                }

                if (data.succeed == false) {
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

    $(".cancel").click(function () {
        window.location.href = "${pageContext.request.contextPath}/home";
    });

</script>