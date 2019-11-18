<%@page contentType="text/html; UTF-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>预览</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/preview.css">
</head>

<body>
    <div class="text"></div>
</body>

<script src="${pageContext.request.contextPath}/res/js/jquery-3.2.1.min.js"></script>
<script>
    $(function () {
        var db;
        var request = window.indexedDB.open("preview", 1);
        // request.onupgradeneeded = function (event) {
        //     console.log("Upgrading");
        //     db = event.target.result;
        //     var objectStore;
        //     if (!db.objectStoreNames.contains('preview')) {
        //         objectStore = db.createObjectStore('preview', { autoIncrement: true });
        //     }
        // };
        request.onsuccess = function (event) {
            // 读取数据
            db = this.result;
            var request = db.transaction(["preview"], "readwrite").objectStore("preview").get(1);
            request.onsuccess = function (event) {
                console.log("Title : " + request.result.content);
                var contentStr = `
                        <div class="title">
                            <p>
                                ${request.result.title}
                            </p>
                        </div>

                        <div class="content" ruant="server">
                            ${request.result.content}
                        </div>
                    `
                $(".text").html(contentStr);
                db.close();
            };
        }

    })
</script>

</html>