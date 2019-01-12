var maxpage = 1;//最大的页面数
var maxrows = 10;//当前页面的最大行数
var nowtag = "-";//当前的标签
var nowpag = 1;//当前页
var nowBlogIndex;
var runstart = 0;
var dataOfNowPage;
var topinfo;
$(document).ready(function(){
    //初始化blog的信息
    loadBlogInfo();
    loadTags();
    $("#tags").slideToggle("slow");
    //初始化时间
    setInterval(displayTime,1000);
    //初始化页面
    queryCountByCondition();
    weblog();

    $("#blog").click(function(){
        nowtag="-";
        nowpag = 1;
        queryCountByCondition();
    });

    $("#about").click(function () {
        $("#center").html(topinfo);
    });

    $("#say").click(function () {
        leave();
    })
    
    $("#tag").click(function () {
        $("#tags").slideToggle("slow");
    });

    $("#login").click(function () {
        login();
    })

    $("#reg").click(function () {
        regForm();
    })
});
function weblog() {
    $.ajax({
        url: encodeURI("/blog2/user/log.do?t=" + Math.random()),
        type: "GET",
        async: true,//这里表示同步
        dataType: 'json',
        cache: false,
        success: function (result) {
            ;
        },
        error: function (result) {
            console.log(result);
        }
    });
}
/**
 * 加载主页面时加载blog信息
 */
function loadBlogInfo() {
    $.ajax({
        url: encodeURI("/blog2/blog/getInfo.do?t=" + Math.random()),
        type: "GET",
        async: true,//这里表示同步
        dataType: 'json',
        cache: false,
        success: function (result) {
            topinfo = result.data[0].top_info;
            $("#total").html(result.data[0].lookNum + "人");
            var now = new Date().getTime();
            runstart = result.data[0].start;
            var runtime = now - runstart;
            var rundate=ToTime(runtime);
            $("#time").html(rundate);
            if (result.online_num != null)
                $("#online").html(result.online_num + "人");
        },
        error: function (result) {
            console.log(result);
        }
    });
}

/**
 * 加载标签
 */
function loadTags() {
    $.ajax({
        url: encodeURI("/blog2/blog/getAllTags.do?t=" + Math.random()),
        type: "GET",
        async: true,//这里表示同步
        dataType: 'json',
        cache: false,
        success: function (result) {
            $("#tags").html("");
            for (var i = 0; i < result.total; ++i) {
                var txt=$("<a href='#'></a>");
                txt.click(function () {
                    nowtag = this.innerHTML;
                    nowpag = 1;
                    queryCountByCondition();
                });
                txt.html(result.data[i].tagName);
                $("#tags").append("<br/>",txt,"<br/>");
            }
            if (result.online_num != null)
                $("#online").html(result.online_num + "人");
        },
        error: function (result) {
            console.log(result);
        }
    });
}
/**
 * 时间效果
 */
function displayTime() {
    var now = new Date().getTime();
    var runtime = now - runstart;
    var rundate=ToTime(runtime);
    $("#time").html(rundate);
}

/**
 * 通过blog的参数查询条数
 */
function queryCountByCondition() {
    if (nowtag != "-")
        var para = "?tag=" + nowtag + "&t=" + Math.random();
    else
        var para = "?t=" + Math.random();
    $.ajax({
        url: encodeURI("/blog2/blog/queryCountByCondition.do" + para),
        type: "GET",
        async: false,//这里表示同步
        dataType: 'json',
        cache: false,
        success: function (result) {
            maxpage = Math.floor(result.data[0] / 10) + 1;
            if (result.online_num)
                $("#online").html(result.online_num + "人");
        },
        error: function (result) {
            console.log(result);
        }
    });
    queryByCondition();
}
/**
 * 通过blog的场景查询
 */
function queryByCondition() {
    if (nowtag != "-")
        var para = "?start=" + nowpag + "&rows=10" +
        "&tag=" + nowtag + "&t=" + Math.random();
    else
        var para = "?start=" + nowpag + "&rows=10" +
             "&t=" + Math.random();
    $.ajax({
        url: encodeURI("/blog2/blog/queryByCondition.do" + para),
        type: "GET",
        async: false,//这里表示同步
        dataType: 'json',
        cache: false,
        success: function (data) {
            dataOfNowPage = new Array(data.total);
            maxrows = data.total;
            $("#center").html("<br/>");
            for (var i = 0; i < data.total; i++) {
                dataOfNowPage[i] = data.data[i].id;
                var txt = $("<div class='article' data-scroll-reveal='enter right over 0.5s'></div>");
                var title = $("<p class='title'></p>");
                title.html(addNbsp(2) + data.data[i].title);
                title.attr("id",i);
                txt.append(title);
                txt.append(data.data[i].blog_content);
                txt.append(addbottom(data.data[i].writetime,data.data[i].tag
                    ,data.data[i].lookNum,data.data[i].commentNum));
                title.click(function () {
                    lookContent(this.id);
                });
                $("#center").append(txt);
            }
            var txt = $("<div style='text-align: center' data-scroll-reveal='enter right over 0.5s'></div>");
            txt.append("<ul class=\"pagination\"></ul>");
            $("#center").append(txt);
            $(".pagination").createPage({
                totalPage:maxpage,
                currPage:nowpag,
                backFn:function(p){
                    if (nowpag == p)
                        return;
                    nowpag = p;
                    queryCountByCondition();
                }
            });
            if (data.online_num)
                $("#online").html(data.online_num + "人");
            reset();
        },
        error: function (result) {
            console.log(result);
        }
    });
}

/**
 * 获取详情.
 * @param i
 */
function lookContent(i) {
    nowBlogIndex = i;
    var param = "?id=" + dataOfNowPage[i] + "&t=" + Math.random();
    $.ajax({
        url: encodeURI("/blog2/blog/queryById.do" + param),
        type: "GET",
        async: true,//这里表示同步
        dataType: 'json',
        cache: false,
        success: function (result) {
            $("#center").html("<br/><ul class=\"pagination\">\n" +
                "\t<li style=\"vertical-align:middle;\"><a href=\"#\" onclick='queryByCondition()'>&laquo;返回</a></li>\n" +
                "</ul>");
            //头部信息
            var txt = $("<div style='text-align: center' data-scroll-reveal='enter right over 0.5s'></div>");
            var title = $("<p class='title'></p>");
            title.html("" + result.data[0].title);
            txt.append(title,addbottom(result.data[0].writetime,result.data[0].tag
                ,result.data[0].lookNum,result.data[0].commentNum));
            $("#center").append(txt);
            //blog详情
            $("#center").append(result.data[0].blog_content);
            $("#center").append(addNextAndpre());
            $("#comment").click(function () {
                $("#comments").slideToggle("slow");
            });
            //blog评论区
            loadCommentForm();
            loadComments();
            uParse('#center',{rootPath:'../ueditor'});
            reset();
        },
        error: function (result) {
            console.log(result);
        }
    });
}

/**
 * 评论的表单
 */
function loadCommentForm() {
    $("#center").append("<div style=\"padding-left: 35%; \"><form class=\"bs-example bs-example-form\" role=\"form\" id='commentform'>\n" +
        "                    <div class=\"input-group\">\n" +
        "                        <span class=\"input-group-addon\">评论区</span>\n" +
        "                        <textarea id='words' placeholder='输入评论' style=\"width:185px;margin-right: 50px;\" class=\"form-control\" rows=\"2\"></textarea>\n" +
        "                    </div>\n" +
        "                    <br>\n" +
        "                    <div class=\"input-group\">\n" +
        "                        <span class=\"input-group-addon\">验证码</span>\n" +
        "                        <input id='userstr' type=\"text\" class=\"form-control\" placeholder=\"验证码\" style=\"width:100px;\">\n" +
        "                        <img id='userimg' onclick='changeImg()' src='/blog2/pic/getPic.do' style='border-radius: 2px;margin-left: 20px;vertical-align:middle;'>\n" +
        "                    </div>\n" +
        "                    <br>\n" +
        "                    <div style='display: flex;'>\n" +
        "                        <div id='warning' style=\"width:120px; height: 34px;\" ></div>\n" +
        "                        <button type=\"reset\" class=\"btn btn-info\" style=\"margin-right:25px;\">重置</button>\n" +
        "                        <button type=\"button\" onclick='comment()' class=\"btn btn-info\">留言</button>\n" +
        "                    </div>\n" +
        "                </form></div>");
    changeImg();
    $("#commentform").slideToggle("slow");
}

function changeImg() {
    $("#userimg").attr('src',"/blog2/pic/getPic.do?t=" + Math.random());
}
/**
 * 评论blog
 */
function comment() {
    var blogId = dataOfNowPage[nowBlogIndex];
    var content = $("#words").val();
    var str = $("#userstr").val();
    if (content.length > 250) {
        lerDiv("字数太多了");
        return;
    }
    if (content.length == 0 || content.trim() == "") {
        lerDiv("请输入文字");
        return;
    }
    if (str.length == 0 || str.trim() == "") {
        lerDiv("输入验证码");
        return;
    }
    //开始评论
    var param = "?blogId=" + blogId + "&content=" + content + "&str=" + str + "&t=" + Math.random();
    $.ajax({
        url: encodeURI("/blog2/blog/comment.do" + param),
        type: "GET",
        async: false,//这里表示同步
        dataType: 'json',
        cache: false,
        success: function (result) {
            if (result.online_num)
                $("#online").html(result.online_num + "人");
            if (result.data[0] == 3) {
                lerDiv("请登录用户");
                return;
            }
            if (result.data[0] == 2) {
                lerDiv("验证码错误");
                changeImg();
            }
            if (result.data[0] == 1) {
                lookContent(nowBlogIndex);
                changeImg();
            }
        },
        error: function (result) {
            console.log(result);
        }
    });
}

/**
 * 添加提示框
 * @param info
 */
function lerDiv(info) {
    $("#warning").html("<div class=\"alert alert-warning\" style='padding-top:5px;padding-bottom:5px;'>\n" +
        "    <a href=\"#\" class=\"close\" data-dismiss=\"alert\">\n" +
        "        &times;\n" +
        "    </a>\n" +
        "    <strong>" + info + "</strong>" +
        "</div>");
}
function displayComment() {
    $("#comments").slideToggle("slow");
}
function commentForm() {
    $("#commentform").slideToggle("slow");
}
/**
 * 加载当前的所有评论
 */
function loadComments() {
    var param = "?blogId=" + dataOfNowPage[nowBlogIndex] + "&t=" + Math.random();
    $.ajax({
        url: encodeURI("/blog2/message/queryComments.do" + param),
        type: "GET",
        async: false,//这里表示同步
        dataType: 'json',
        cache: false,
        success: function (result) {
            var comments = $("<div id='comments'></div>");
            var data = result.data;
            for (var i = 0; i < result.total; ++i) {
                var txt = $("<p data-scroll-reveal='enter right over 0.5s'></p>");
                txt.append(adduser(data[i].username,data[i].userContent,data[i].content,data[i].addtime));
                comments.append(txt);
            }
            if (result.total == 0)
                comments.append("<p class=\"cons\">还没有人来评论，赶快评论一下...</p>")
            $("#center").append(comments);
            $("#comments").slideToggle("slow");
            if (result.online_num)
                $("#online").html(result.online_num + "人");
        },
        error: function (result) {
            console.log(result);
        }
    });
}

/**
 * 用户评论列表
 * @param user
 * @param content
 * @param data
 * @param time
 * @returns {string}
 */
function adduser(user,content,data,time) {
    return "<img src=\"/blog2/static/img/user.png\" style=\"height: 25px; width: 25px\">\n" +
        "                <span class=\"user\"> " + user + " </span>\n" +
        "                <span class=\"uContent\"> &nbsp; -个性签名- ：" + content.replace(/&/g, "&amp").replace(/</g, "&lt").replace(/>/g, "&gt")+ "</span><br/>\n" +
        "                <p class=\"cons\">" + data.replace(/&/g, "&amp").replace(/</g, "&lt").replace(/>/g, "&gt") +"&nbsp; &nbsp; &nbsp; &nbsp;\n" +
        "                <span class=\"glyphicon glyphicon-time\"></span>\n" +
        "                <span class=\"commentTime\"> " + longToTime(time) + "</span>\n" +
        "                </p>";
}
/**
 * 添加blog信息栏
 * @param time
 * @param tag
 * @param looks
 * @param comments
 * @returns {string}
 */
function addbottom(time, tag, looks, comments) {
   return "<p>"
       + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"glyphicon glyphicon-time\"></span> &nbsp;"
       + longToTime(time)
       + "&nbsp;&nbsp;&nbsp;<span class=\"glyphicon glyphicon-tags\"></span> &nbsp;"
       + tag
       + "&nbsp;&nbsp;&nbsp;<span class=\"glyphicon glyphicon-eye-open\"></span> &nbsp;"
       + looks
       + "&nbsp;&nbsp;&nbsp;<span class=\"glyphicon glyphicon-comment\"></span> &nbsp;"
       + comments
       + "</p>";
}

/**
 * 添加底部按钮
 * @returns {string}
 */
function addNextAndpre() {
    return "<ul class=\"pager\">\n" +
        "\t<li class=\"previous\"><a onclick='pre()' href='#'>&larr; Older</a></li>\n" +
        "\t<li class=\"center-block\"><a onclick='displayComment()'><label>用户评论</label></a></li>\n" +
        "\t<li class=\"center-block\"><a onclick='commentForm()'><label>我要评论</label></a></li>\n" +
        "\t<li class=\"next\"><a onclick='nex()' href='#'>Newer &rarr;</a></li>\n" +
        "</ul>";
}


function pre() {
    nowBlogIndex--;
    var now = nowBlogIndex >= 0 ? nowBlogIndex : maxrows - 1;
    lookContent(now);
}
function nex() {
    nowBlogIndex++;
    var now = nowBlogIndex < maxrows ? nowBlogIndex : 0;
    lookContent(now);
}
function longToTime(timestamp) {
    var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
    Y = date.getFullYear() + '-';
    M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    D = date.getDate() + ' ';
    h = date.getHours() + ':';
    m = date.getMinutes() + ':';
    s = date.getSeconds();
    return Y+M+D+h+m+s;
}

function ToTime(timestamp) {
    var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
    Y = date.getFullYear() - 1970;
    if (Y == 0)
        Y ="";
    else
        Y = Y + '年';
    M = (date.getMonth()+1 < 10 ? (date.getMonth()+1) : date.getMonth()+1) + '月';
    D = date.getDate() + '天 ';
    h = date.getHours() + ':';
    m = date.getMinutes() + ':';
    s = date.getSeconds();
    return Y+M+D+h+m+s;
}

/**
 * 重置特效
 */
function reset() {
    var config = {
        enter: 'bottom',
        move: '40px',
        over: '0.16s',
        reset: false,
        init: true
    };
    scrollReveal.init();
}
/**
 * 分页按钮
 */
(function($){
    var ms = {
        init:function(totalsubpageTmep,args){
            return (function(){
                ms.fillHtml(totalsubpageTmep,args);
                ms.bindEvent(totalsubpageTmep,args);
            })();
        },
        //填充html
        fillHtml:function(totalsubpageTmep,args){
            return (function(){
                totalsubpageTmep="";
                // 页码大于等于4的时候，添加第一个页码元素
                if(args.currPage!=1 && args.currPage>=4 && args.totalPage!=4) {
                    totalsubpageTmep += "<li class='ali'><a href='javascript:void(0);' class='geraltTb_pager' data-go='' >"+1+"</a></li>";
                }
                /* 当前页码>4, 并且<=总页码，总页码>5，添加“···”*/
                if(args.currPage-2>2 && args.currPage<=args.totalPage && args.totalPage>5) {
                    totalsubpageTmep += "<li class='ali'><a href='javascript:void(0);' class='geraltTb_' data-go='' >...</a></li>";
                }
                /* 当前页码的前两页 */
                var start = args.currPage-2;
                /* 当前页码的后两页 */
                var end = args.currPage+2;

                if((start>1 && args.currPage<4) || args.currPage==1) {
                    end++;
                }
                if(args.currPage>args.totalPage-4 && args.currPage>=args.totalPage) {
                    start--;
                }
                for(; start<=end; start++) {
                    if(start<=args.totalPage && start>=1) {
                        if (start != nowpag)
                            totalsubpageTmep += "<li class='ali'><a href='javascript:void(0);' class='geraltTb_pager' data-go='' >"+start+"</a></li>";
                        else
                            totalsubpageTmep += "<li class='ali'><a href='javascript:void(0);' style='background-color: #F4F4F4' class='geraltTb_pager' data-go='' >"+start+"</a></li>";
                    }
                }
                if(args.currPage+2<args.totalPage-1 && args.currPage>=1 && args.totalPage>5) {
                    totalsubpageTmep += "<li class='ali'><a href='javascript:void(0);' class='geraltTb_' data-go='' >...</a></li>";
                }

                if(args.currPage!=args.totalPage && args.currPage<args.totalPage-2 && args.totalPage!=4) {
                    totalsubpageTmep += "<li class='ali'><a href='javascript:void(0);' class='geraltTb_pager' data-go='' >"+args.totalPage+"</a></li>";
                }
                $(".pagination").html(totalsubpageTmep);
            })();
        },
        //绑定事件
        bindEvent:function(totalsubpageTmep,args){
            return (function(){
                totalsubpageTmep.on("click","a.geraltTb_pager",function(event){
                    var current = parseInt($(this).text());
                    ms.fillHtml(totalsubpageTmep,{"currPage":current,"totalPage":args.totalPage,"turndown":args.turndown});
                    if(typeof(args.backFn)=="function"){
                        args.backFn(current);
                    }
                });
            })();
        }
    }
    $.fn.createPage = function(options){
        ms.init(this,options);
    }
})(jQuery);