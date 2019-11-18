layer.config({
    skin: 'demo-class'//自定义样式demo-class
})

$(".preview").click(function () {
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


    window.indexedDB = window.indexedDB || window.mozIndexedDB || window.webkitIndexedDB || window.msIndexedDB;
    if (!window.indexedDB) {
        alert("请更换浏览器打开");
    } else {
        window.indexedDB.deleteDatabase('preview');
        var request = window.indexedDB.open("preview", 1);//第一个参数是数据库的名称，第二个参数是数据库的版本号
        var db;
        request.onerror = function (event) {
            console.log("打开数据库失败", event);
        }
        request.onupgradeneeded = function (event) {
            console.log("Upgrading");
            db = event.target.result;
            var objectStore;
            if (!db.objectStoreNames.contains('preview')) {
                objectStore = db.createObjectStore('preview', { autoIncrement: true });
            }
        };
        request.onsuccess = function (event) {
            console.log("打开/新建数据库成功");
            console.log('db++' + JSON.stringify(db));

            //添加数据
            var transactions = db.transaction(["preview"], "readwrite");
            transactions.oncomplete = function (event) {
                console.log("Success");
                db.close();
            };
            transactions.onerror = function (event) {
                console.log("Error");
            };
            var objectStore = transactions.objectStore("preview");
            var data = { title: title, content: content }
            var str = objectStore.add(data);
            console.log(data);
            window.open('preview.html');

            //读取数据
            // var request = objectStore.get(num);
            // request.onsuccess = function (event) {
            //     var data = event.target.result;
            //     console.log("Title : " + data.title);
            // console.log(num);
            // };

            // //修改数据
            // var transaction = db.transaction('preview', 'readwrite');
            // var store = transaction.objectStore('preview');
            // var request = store.get(1);
            // request.onsuccess = function (e) {
            //     var data = e.target.result;
            //     data.title = title;
            //     data.abstract = abstract;
            //     data.content = content;
            //     store.put(data);
            // };
        }
    }
});



