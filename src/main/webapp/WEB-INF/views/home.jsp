<%@page contentType="text/html; UTF-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>个人中心</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/index.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/home.css">
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
        <div class="nav_bg">
            <div class="layui-tab">
                <ul class="layui-tab-title">
<%--                    <li>最近编辑</li>--%>
                    <li class="layui-this">我的条目</li>
                    <li>个人信息</li>
                </ul>
                <div class="layui-tab-content">
                    <!-- 最近编辑 -->
<%--                    <div class="layui-tab-item">--%>
<%--                        <div class="edit_record_content">--%>
<%--                            <div class="item">--%>
<%--                                <div class="theme">条目:--%>
<%--                                    <span>还没有小伙伴对你的条目内容进行编辑~</span>--%>
<%--                                </div>--%>
<%--                                <div class="editTitle">标题:--%>
<%--                                    <span>阴影水平偏移值（可取正负值）； 阴影垂直偏移值（可取正负值）；阴影模糊值；阴影颜色</span>--%>
<%--                                </div>--%>
<%--                                <div class="editTitle">标题:--%>
<%--                                    <span>阴影水平偏移值（可取正负值）； 阴影垂直偏移值（可取正负值）；阴影模糊值；阴影颜色</span>--%>
<%--                                </div>--%>
<%--                            </div>--%>
<%--                            <div class="item">--%>
<%--                                <div class="theme">条目:--%>
<%--                                    <span>还没有小伙伴对你的条目内容进行编辑~</span>--%>
<%--                                </div>--%>
<%--                                <div class="editTitle">标题:--%>
<%--                                    <span>阴影水平偏移值（可取正负值）； 阴影垂直偏移值（可取正负值）；阴影模糊值；阴影颜色</span>--%>
<%--                                </div>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                    </div>--%>

                    <!-- 我的条目 -->
                    <div class="layui-tab-item layui-show">
                        <div class="edit_record_content">
                            <c:forEach var="entry" items="${entrys}">
                            <div class="item">
                                <div class="theme">条目:
                                    <span id="theme_name">${entry.title}</span>
                                    <span class="add">添加文章</span>
                                    <span class="edit_text" id="edit_text">编辑条目</span>
                                </div>
                                <c:forEach var="page" items="${entry.page}">
                                        <div class="editTitle" id="${page.id}">
                                            标题:
                                            <span>${page.title}</span>
                                        </div>
                                </c:forEach>
                            </div>
                            </c:forEach>
                        </div>
                    </div>

                    <!-- 个人信息 -->
                    <div class="layui-tab-item">
                        <div class="text">个人信息
                            <span class="edit_btn" id="edit_pwd">修改密码</span>
                            <span class="edit_btn" id="edit_user">修改用户名</span>
                        </div>
                        <div class="message">用户名：${user.name}</div>
                    </div>

                </div>
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
<%--<script src="${pageContext.request.contextPath}/res/js/index.js"></script>--%>
<script src="${pageContext.request.contextPath}/res/js/layui/layui.all.js"></script>
<script language=javascript src='${pageContext.request.contextPath}/res/js/jquery.cookie.js'></script>
<script>
    //注意：选项卡 依赖 element 模块，否则无法进行功能性操作
    layui.use('element', function () {
        var element = layui.element;
    });

    $(function () {
        layer.config({
        skin: 'demo-class'//自定义样式demo-class
        });
        //修改密码
        $("#edit_pwd").click(function () {
            window.location.href = "${pageContext.request.contextPath}/chgpwd";
        });
        //修改用户名
        $("#edit_user").click(function () {
            window.location.href = "${pageContext.request.contextPath}/chgname";
        });

        //编辑条目
        $(".edit_text").click(function () {
        window.open("${pageContext.request.contextPath}/editEntry/" + $($(this).prev().prev()).html());
        });

        //标题短文详细信息
        $(".editTitle").click(function () {
        window.location.href = "${pageContext.request.contextPath}/editPage?page_id="+ $(this).attr("id");
        });

        //添加文章
        $(".add").click(function () {
            window.open("${pageContext.request.contextPath}/addPage/" + $($(this).prev()).html());
        });
});
</script>