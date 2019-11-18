<%@page contentType="text/html; UTF-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>编辑修改文章</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/addArticle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/editArticle.css">
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
        <div class="title">
            <div class="article_title">文章标题</div>
            <textarea rows="3" placeholder="请输入文章标题(不大于80字)" maxlength="80" id="title-textarea" readonly>${page.title}</textarea>
        </div>

        <div class="article_content">
            <div class="content">正文内容</div>
            <div class="word">
                <div id="editor">${page.content}</div>
            </div>
        </div>

        <div class="btn_bg">
            <div class="publish">发布</div>
            <div class="cancle">取消</div>
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
<script src="${pageContext.request.contextPath}/res/js/editArticle.js"></script>
<script src="${pageContext.request.contextPath}/res/js/wangEditor.min.js"></script>
<script src="${pageContext.request.contextPath}/res/js/layui/layui.all.js"></script>
<script language=javascript src='${pageContext.request.contextPath}/res/js/jquery.cookie.js'></script>

<script>
    // 富文本编辑器
    var E = window.wangEditor;
    var editor = new E('#editor');
    editor.customConfig.menus = [
        'head',  // 标题
        'bold',  // 粗体
        'fontSize',  // 字号
        'fontName',  // 字体
        'italic',  // 斜体
        'underline',  // 下划线
        'strikeThrough',  // 删除线
        'foreColor',  // 文字颜色
        'backColor',  // 背景颜色
        'link',  // 插入链接
        'list',  // 列表
        'justify',  // 对齐方式
        'quote',  // 引用
        'emoticon',  // 表情
        // 'image',  // 插入图片
        'table',  // 表格
        //'video',  // 插入视频
        'code',  // 插入代码
        'undo',  // 撤销
        'redo'  // 重复
    ]
    // 下面两个配置，使用其中一个即可显示“上传图片”的tab。但是两者不要同时使用！！！
    editor.customConfig.uploadImgShowBase64 = true;   // 使用 base64 保存图片
    // editor.customConfig.uploadImgServer = '/upload'  // 上传图片到服务器
    // 隐藏“网络图片”tab
    editor.customConfig.showLinkImg = false;
    editor.create();

    layer.config({
        skin: 'demo-class'//自定义样式demo-class
    })

    // 发布
    $(".publish").click(function () {
        var title = $("#title-textarea").val();
        var content = $(".w-e-text").html();
        if (title == "") {
            layer.open({
                title: '温馨提示'
                , content: '文章标题不能为空！'
            });
            return false;
        }
        if (content == "<p><br></p>") {
            layer.open({
                title: '温馨提示'
                , content: '文章内容不能为空！'
            });
            return false;
        }

        // 提交数据到后台
        $.ajax({
            type: 'POST',
            data: {
                page_id: '${page.id}',
                title: title,
                content: content
            },
            dataType: 'json',
            url: '${pageContext.request.contextPath}/page/edit',
            success: function (data) {
                if (data.succeed == true) {
                    layer.open({
                        title: '温馨提示'
                        , content: '发布成功！'
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
                    , content: '发布失败！'
                });
            }
        })
    });
    //取消
    $(".cancle").click(function () {
        window.location.href = "${pageContext.request.contextPath}/wiki/${page.entry_title}";
    });

</script>