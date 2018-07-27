var hints = [];

// 加载热搜榜，热搜榜的id为节点id
$.ajax({
    type: 'get',
    url: "hot_corps",
    dataType: 'json',
    success: function (data) {
        var hot_list_loc = $("#hot-list");
        var hot_corp_template = $("#hot-list-item-template");
        for (var i = 0; i < data.length; i++) {
            var hot_corp = hot_corp_template.clone();
            hot_corp.children("div").text(data[i].name);
            hot_corp.attr("href", "info.html?graphId="+data[i].id+"&name="+data[i].name)
            hot_list_loc.after(hot_corp);
        }
        hot_corp_template.remove();
    }
});

$.ajax({
    type: "get",
    url: "news_preview?N=5",
    dataType: "json",
    success: function (data) {
        var news_preview_loc = $("#news_preview");
        var news_preview_template = $("#news-preview-tempalte");

        for (var i = data.length-1; i >= 0; i--) {
            var news_preview = news_preview_template.clone();
            news_preview.attr("id", data[i].id);

            if (data[i].previewImage == null)
                news_preview.find(".news-preview-image").attr("src", "images/pedaily.jpg");
            else
                news_preview.find(".news-preview-image").attr("src", data[i].previewImage);

            news_preview.find(".news-preview-title").text(data[i].title);
            news_preview.find(".news-preview-title").attr("href", "news_detail.html?id="+data[i].id);

            time = new Date(data[i].time)
            news_preview.find(".news-preview-time").text(time.toLocaleString());
            news_preview_loc.after(news_preview);
        }
        news_preview_template.remove();
    }
});

$(function () {
    var history_loc = $("#history");
    var history = getHistory();
    for (var i = 0; i < history.length; i++) {
        history_loc.append("<option>" + history[i] + "</option>");
    }
});

// 加载搜索历史
function getHistory() {
    var history_loc = $("#history");
    var history = localStorage.getItem("history");
    if (history == null) {
        history = [];
    }
    else {
        history = JSON.parse(history);
    }
    return history;
}

// 获取输入提示
$("#search").keyup(function () {
    var lastKeyword = "";
    var hint_loc = $("#history");
    var curKeyword = $("#search").val();
    var history = getHistory();

    lastKeyword = curKeyword;
    setTimeout(function () {
        // 搜索提示
        if (lastKeyword == curKeyword && curKeyword != '') {
            $.ajax({
                type: 'get',
                url: "hint",
                dataType: 'json',
                data: {keyword: curKeyword},
                success: function (data) {
                    hint_loc.empty();
                    // 历史纪录
                    for (var i = 0; i < history.length; i++) {
                        hint_loc.append("<option>" + history[i] + "</option>");
                    }
                    for(var i = 0; i < data.length; i++) {
                        hint_loc.append("<option>" + data[i].corpName + "</option>");
                        hints.push(data[i]);
                    }
                }
            });
        }
    }, 500);
});

// 添加搜索历史
$("form").submit(function(e){

    var push_flag = true; // 仅当搜索历史不存在该关键词才添加至历史

    var keyword = $("#search").val();
    var history = localStorage.getItem("history");

    // 从本地存储中获取搜索历史
    if (history == null) {
        history = [];
    }
    else {
        history = JSON.parse(history);
    }

    // 检查搜索历史中是否存在该关键词
    for (var i = 0; i < history.length; i++) {
        if (keyword == history[i]) {
            push_flag = false;
        }
    }

    // 添加至搜索历史
    if (push_flag) {
        if (history.length >= 5) {
            history.shift();
        }
        history.push(keyword);
    }

    var modify_flag = true;
    for (var i = 0; i < hints.length; i++) {
        if (keyword  == hints[i].corpName) {
            $("#search").after("<input name='graphId' value='" + hints[i].graphId + "' />");
            modify_flag = false;
            break;
        }
    }
    if (modify_flag) {
        $("#search").after("<input name='graphId' value='' />");
    }

    // localStorage 存储类型仅能为字符串
    localStorage.setItem("history", JSON.stringify(history));

});