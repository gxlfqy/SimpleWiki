document.write("<script language=javascript src='../js/jquery.cookie.js'></script>");
$(function () {
    layer.config({
        skin: 'demo-class'//自定义样式demo-class
    })
    // 判断登录
    // $(".mid_content, #header, #footer").hide();
    // var userCookie = $.cookie('userName');
    // console.log(userCookie);
    // if (userCookie == null || userCookie == "" || userCookie == 'null') {
    //     layer.confirm('请先登录', {
    //         title: "温馨提示",
    //         btn: ['稍后登录', '前往登录'], //可以无限个按钮
    //         closeBtn: 0,
    //     }, function (index, layero) {
    //         window.location.href = "index.html";
    //         layer.close(index);
    //     }, function (index) {
    //         window.location.href = 'login.html';
    //     });
    //     return false;
    // } else {
    //     $(".mid_content, #header, #footer").show();
    // }

    //修改密码
    $("#edit_pwd").click(function () {
        window.location.href = "editPassword.jsp"
    });
    //修改用户名
    $("#edit_user").click(function () {
        window.location.href = "editUserName.jsp"
    });

    //编辑条目
    $("#edit_text").click(function () {
        // $.ajax({
        //     type: 'POST',
        //     dataType: 'json',
        //     data: {
        //         titleID: titleID //条目id
        //     },
        //     url: '',
        //     success: function (data) {
        //         if (data.code == 1) {
        //             console.log(data);
        //         }
        //         if (data.code == 0) {
        //             layer.open({
        //                 title: '温馨提示'
        //                 , content: '出错了！'
        //             });
        //         }
        //     },
        //     error: function (e) {
        //         layer.open({
        //             title: '温馨提示'
        //             , content: '出错了！'
        //         });
        //     }
        // });
        window.open("editTitle.jsp");
    });

    //标题短文详细信息
    $(".editTitle").click(function () {
        // $.ajax({
        //     type: 'POST',
        //     dataType: 'json',
        //     data: {
        //         ArticleID: ArticleID //文章id
        //     },
        //     url: '',
        //     success: function (data) {
        //         if (data.code == 1) {
        //             console.log(data);
        //         }
        //         if (data.code == 0) {
        //             layer.open({
        //                 title: '温馨提示'
        //                 , content: '服务器繁忙！'
        //             });
        //         }
        //     },
        //     error: function (e) {
        //         layer.open({
        //             title: '温馨提示'
        //             , content: '服务器繁忙！'
        //         });
        //     }
        // });
        window.location.href = "preview.jsp"
    });

    //添加文章
    $(".add").click(function () {
        window.open("${pageContext.request.contextPath}/addPage");
    });
});