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

$(function () {
    var history_loc = $("#history");
    var history = getHistory();
    for (var i = 0; i < history.length; i++) {
        history_loc.append("<option>" + history[i] + "</option>");
    }
})

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
                    }
                }
            });
        }
    }, 500);
});

// 添加搜索历史
$("#search-btn").click(function(){
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

    // localStorage 存储类型仅能为字符串
    localStorage.setItem("history", JSON.stringify(history));
});