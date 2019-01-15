

function leave() {
    //获取所有的条数
    queryCount();
    nowpag = 1;
    queryNowPage();
}

function queryNowPage() {
    var param = "?start=" + nowpag + "&rows=10" + "&t=" + Math.random();
    $.ajax({
        url: encodeURI("/blog2/leave/queryByCondition.do" + param),
        type: "GET",
        async: false,//这里表示同步
        dataType: 'json',
        cache: false,
        success: function (result) {
            maxrows = result.total;
            $("#center").html("");
            var data = result.data;
            for (var i = 0; i < maxrows; ++i) {
                var txt = $("<p data-scroll-reveal='enter bottom over 0.5s'></p>");
                txt.append(addmessage("陌生人" + (i + 1) ,data[i].content,data[i].addtime));
                $("#center").append(txt);
            }
            addSendDiv();
            loadMessageForm();
            var txt = $("<div style='text-align: center' class=\"pagec\" id=\"pagearea\" data-scroll-reveal='enter bottom over 0.5s'></div>");
            txt.append("<ul class=\"pagination\"></ul>");
            $("#center").append(txt);
            $(".pagination").createPage({
                totalPage:maxpage,
                currPage:nowpag,
                backFn:function(p){
                    if (nowpag == p)
                        return;
                    nowpag = p;
                    queryNowPage();
                }
            });
            if (result.online_num)
                $("#online").html(result.online_num + "人");
            reset();
        },
        error: function (result) {
            console.log(result);
        }
    });
}

/**
 * 加载评论框
 */
function loadMessageForm() {
    $("#center").append("<div class=\"messageInput\" ><form class=\"bs-example bs-example-form\" role=\"form\" id='commentform'>\n" +
        "                    <div class=\"input-group\">\n" +
        "                        <span class=\"input-group-addon\">留言区</span>\n" +
        "                        <textarea id='words' placeholder='输入留言' style=\"width:185px;\" class=\"form-control\" rows=\"2\"></textarea>\n" +
        "                    </div>\n" +
        "                    <br>\n" +
        "                    <div class=\"input-group\">\n" +
        "                        <span class=\"input-group-addon\">验证码</span>\n" +
        "                        <input id='userStr' type=\"text\" class=\"form-control\" placeholder=\"验证码\" style=\"width:100px;margin-right: 20px;\">\n" +
        "                        <img id='userimg' onclick='changeImg()' src='/blog2/pic/getPic.do' style='border-radius: 2px;vertical-align:middle;'>\n" +
        "                    </div>\n" +
        "                    <br>\n" +
        "                    <div style='display: flex;'>\n" +
        "                        <div id='warning' style=\"width:120px; height: 34px;\" ></div>\n" +
        "                        <button type=\"reset\" class=\"btn btn-info\" style=\"margin-right:25px;\">重置</button>\n" +
        "                        <button type=\"button\" onclick='leaveMessage()' class=\"btn btn-info\">留言</button>\n" +
        "                    </div>\n" +
        "                </form></div>");
    changeImg();
    $("#commentform").slideToggle("slow");
}
/**
 * 添加所有的评论
 * @param user
 * @param data
 * @param time
 * @returns {string}
 */
function addmessage(user,data,time) {
    return "<img src=\"/blog2/static/img/user.png\" style=\"height: 25px; width: 25px\">\n" +
        "                <span class=\"user\"> " + user + " </span>\n" + "<br/><br/><br/>" +
        "                <p class=\"cons\">" + addNbsp(7) + data.replace(/&/g, "&amp").replace(/</g, "&lt").replace(/>/g, "&gt")  +
        "                <span class=\"glyphicon glyphicon-time\"></span>\n" +
        "                <span class=\"commentTime\"> " + longToTime(time) + "</span>\n" +
        "                </p>";
}

/**
 * 留言
 */
function leaveMessage() {
    var content = $("#words").val();
    var str = $("#userStr").val();
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
    var param = "?content=" + content + "&userstr=" + str + "&t=" + Math.random();
    $.ajax({
        url: encodeURI("/blog2/leave/addLeaveMessage.do" + param),
        type: "GET",
        async: false,//这里表示同步
        dataType: 'json',
        cache: false,
        success: function (result) {
            if (result.data[0] == false) {
                lerDiv("验证码错误");
                changeImg();
            }
            if (result.data[0] == true) {
                leave();
            }
            if (result.online_num)
                $("#online").html(result.online_num + "人");
        },
        error: function (result) {
            console.log(result);
        }
    });
}

/**
 * 加载评论框
 * @returns {string}
 */
function addSendDiv() {
    $("#center").append("<ul class=\"pager\">\n" +
        "\t<li class=\"center-block\"><a onclick='commentForm()'><label>我要留言</label></a></li>\n" +
        "</ul>");
}

function addNbsp(n) {
    var txt = "";
    for (var i = 0; i < n; i++)
        txt += "&nbsp;";
    return txt;
}
/**
 * 所有的留言条数
 */
function queryCount() {
    //获取所有的条数
    var param = "?t=" + Math.random();
    $.ajax({
        url: encodeURI("/blog2/leave/queryCount.do" + param),
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
}

function login(){
    $.ajax({
        url: encodeURI("/blog2/user/checkOnline.do?t=" + Math.random()),
        type: "GET",
        async: true,//这里表示同步
        dataType: 'json',
        cache: false,
        success: function (result) {
            if (result.total == 0) {
                loginForm();
            }
            else {
                $("#center").html("");
                $("#center").html("<table class=\"table\">\n" +
                    "                <caption>用户信息</caption>\n" +
                    "                <tbody>\n" +
                    "                <tr>\n" +
                    "                    <td>帐号</td>\n" +
                    "                    <td>" + result.data[0].username +"</td>\n" +
                    "                </tr>\n" +
                    "                <tr>\n" +
                    "                    <td>个性签名</td>\n" +
                    "                    <td>" + result.data[0].content +"</td>\n" +
                    "                </tr>\n" +
                    "                <tr>\n" +
                    "                    <td></td>\n" +
                    "                    <td>\n" +
                    "                        <button type=\"button\" onclick='exitUser()' class=\"btn btn-info\">注销</button>\n" +
                    "                    </td>\n" +
                    "                </tr>\n" +
                    "                </tbody>\n" +
                    "            </table>");
            }
        },
        error: function (result) {
            console.log(result);
        }
    });
}

/**
 * 注销用户
 */
function exitUser() {
    $.ajax({
        url: encodeURI("/blog2/user/exit.do?t=" + Math.random()),
        type: "GET",
        async: false,//这里表示同步
        dataType: 'json',
        cache: false,
        success: function (result) {
            login();
        },
        error: function (result) {
            console.log(result);
        }
    });
}
/**
 * 登录框
 */
function loginForm() {
    $("#center").html("<div class=\"messageInput\">\n" +
        "                <form class=\"bs-example bs-example-form\" role=\"form\">\n" +
        "                    <div class=\"input-group\">\n" +
        "                        <span class=\"input-group-addon\">帐 号</span>\n" +
        "                        <input id='username' type=\"text\" style=\"width:200px;\" class=\"form-control\" placeholder=\"username\">\n" +
        "                    </div>\n" +
        "                    <br>\n" +
        "                    <div class=\"input-group\">\n" +
        "                        <span class=\"input-group-addon\">密 码</span>\n" +
        "                        <input id='password' type=\"password\" style=\"width:200px;\" class=\"form-control\" placeholder=\"password\">\n" +
        "                    </div>\n" +
        "                    <br>\n" +
        "                    <div class=\"input-group\">\n" +
        "                        <span class=\"input-group-addon\">验证码</span>\n" +
        "                        <input id='userStr' type=\"text\" class=\"form-control\" placeholder=\"验证码\" style=\"width:100px;margin-right: 20px;\">\n" +
        "                        <img id='userimg' onclick='changeImg()' src='/blog2/pic/getPic.do' style='border-radius: 2px;vertical-align:middle;'>\n" +
        "                    </div>\n" +
        "                    <br>\n" +
        "                    <div style='display: flex;'>\n" +
        "                        <div id='warning' style=\"width:120px; height: 34px;\" ></div>\n" +
        "                        <button type=\"reset\" class=\"btn btn-info\" style=\"margin-right:20px;\">重置</button>\n" +
        "                        <button type=\"button\" onclick='toLogin()' class=\"btn btn-info\">登录</button>\n" +
        "                    </div>\n" +
        "                </form>\n" +
        "            </div>");
    changeImg();
}

/**
 * 登录请求
 */
function toLogin() {
    var username = $("#username").val();
    var password = $("#password").val();
    var userStr = $("#userStr").val();
    if (username == null || username.trim() == "") {
        lerDiv("请输入帐号");
        return;
    }
    if (password == null || password.trim() == "") {
        lerDiv("请输入密码");
        return;
    }
    if (userStr == null || userStr.trim() == "") {
        lerDiv("输入验证码");
        return;
    }
    //登录
    var param = "?username=" + username +
        "&password=" + password +
        "&str=" + userStr +
        "&t=" + Math.random();
    $.ajax({
        url: encodeURI("/blog2/user/login.do" + param),
        type: "GET",
        async: false,//这里表示同步
        dataType: 'json',
        cache: false,
        success: function (result) {
            if (result.online_num)
                $("#online").html(result.online_num + "人");
            if (result.data[0] == 0) {
                lerDiv("用户不存在");
                changeImg();
                return;
            }
            if (result.data[0] == 2) {
                lerDiv("密码错误");
                changeImg();
                return;
            }
            if (result.data[0] == 3) {
                lerDiv("验证码错误");
                changeImg();
                return;
            }
            login();
            changeImg();
        },
        error: function (result) {
            console.log(result);
        }
    });
}
/**
 * 注册界面
 */
function regForm() {
    $("#center").html("<div class=\"messageInput\">\n" +
        "                <form class=\"bs-example bs-example-form\" role=\"form\">\n" +
        "                    <div class=\"input-group\">\n" +
        "                        <span class=\"input-group-addon\">帐 号</span>\n" +
        "                        <input id='username' type=\"text\" style=\"width:200px;\" class=\"form-control\" placeholder=\"username\">\n" +
        "                    </div>\n" +
        "                    <br>\n" +
        "                    <div class=\"input-group\">\n" +
        "                        <span class=\"input-group-addon\">密 码</span>\n" +
        "                        <input id='password' type=\"password\" style=\"width:200px;\" class=\"form-control\" placeholder=\"password\">\n" +
        "                    </div>\n" +
        "                    <br>\n" +
        "                    <div class=\"input-group\">\n" +
        "                        <span class=\"input-group-addon\" >签 名</span>\n" +
        "                        <input id='userContent' type=\"text\" style=\"width:200px;\" class=\"form-control\" placeholder=\"个性签名\">\n" +
        "                    </div>\n" +
        "                    <br>\n" +
        "                    <div class=\"input-group\">\n" +
        "                        <span class=\"input-group-addon\">验证码</span>\n" +
        "                        <input id='userStr' type=\"text\" class=\"form-control\" placeholder=\"验证码\" style=\"width:100px;margin-right: 20px;\">\n" +
        "                        <img id='userimg' onclick='changeImg()' src='/blog2/pic/getPic.do' style='border-radius: 2px;vertical-align:middle;'>\n" +
        "                    </div>\n" +
        "                    <br>\n" +
        "                    <div style='display: flex;'>\n" +
        "                        <div id='warning' style=\"width:120px; height: 34px;\" ></div>\n" +
        "                        <button type=\"reset\" class=\"btn btn-info\" style=\"margin-right:20px;\">重置</button>\n" +
        "                        <button type=\"button\" onclick='toreg()' class=\"btn btn-info\">注册</button>\n" +
        "                    </div>\n" +
        "                </form>\n" +
        "            </div>");
    changeImg();
}

/**
 * 注册请求
 */
function toreg() {
    var username = $("#username").val();
    var password = $("#password").val();
    var usercontent = $("#userContent").val();
    var userStr = $("#userStr").val();
    if (username == null || username.trim() == "") {
        lerDiv("请输入帐号");
        return;
    }
    if (password == null || password.trim() == "") {
        lerDiv("请输入密码");
        return;
    }
    if (usercontent == null || usercontent.trim() == "") {
        lerDiv("请输入签名");
        return;
    }
    if (userStr == null || userStr.trim() == "") {
        lerDiv("输入验证码");
        return;
    }
    //注册
    var param = "?username=" + username +
        "&password=" + password +
        "&content=" + usercontent +
        "&str=" + userStr +
        "&t=" + Math.random();
    $.ajax({
        url: encodeURI("/blog2/user/reg.do" + param),
        type: "GET",
        async: false,//这里表示同步
        dataType: 'json',
        cache: false,
        success: function (result) {
            if (result.online_num)
                $("#online").html(result.online_num + "人");
            if (result.data[0] == 0) {
                lerDiv("用户已注册");
                changeImg();
                return;
            }
            if (result.data[0] == 2) {
                lerDiv("验证码错误");
                changeImg();
                return;
            }
            loginForm();
            changeImg();
        },
        error: function (result) {
            console.log(result);
        }
    });
}