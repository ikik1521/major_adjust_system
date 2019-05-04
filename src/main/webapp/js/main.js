var now = new Date;
var fullYear = now.getFullYear();
var year = fullYear - 2000;
var month = now.getMonth() + 1;
var day = now.getDate();

var time = document.getElementsByClassName('time');
time[0].innerHTML += fullYear + "-" + month + "-" + day;

//$(document).ready(function () { //用户ID的获取
//	alert(fullYear);
//    $.getJSON("../ceshi.json", function (result, status) {
//        v.index = result;
//   })
//});

var v = new Vue({
    el: '#app',
    data: {
        fullYear: fullYear,
        year: year,
        month: month,
        day: day,

        isActive: false,
        isReason: false,

        index: Object,
        tables: Object,
        reasons: Object
    },
    methods: {
        biaoge: function () {
            $.getJSON("./ceshi.json", function (result, status) {
                if (v.isActive == false && v.isReason == false) {
                    v.isActive = true;
                    v.tables = result;
                }
                if (v.isActive == false && v.isReason == true) {
                    v.isActive = true;
                    v.isReason = false;
                }
            })
        },
        shanchu: function (aaa, bbb) {
            console.log(typeof aaa);
            $.ajax({
                url: "/major_adjust_system/xxxxxx",
                type: 'POST',
                dataType: 'JSON',
                data: {
                    majorID: aaa,
                    nianfen: bbb
                },
                success: function () {
                    $.getJSON("./ceshi1.json", function (result, status) {
                        v.tables = result;
                    })
                },
                errorr: function () {
                    alert("删除失败");
                }

            })
        },
        reason: function () {
            $.getJSON("./ceshi.json", function (result, status) {
                if (v.isActive == true && v.isReason == false) {
                    v.isReason = true;
                    v.isActive = false;
                    v.reasons = result;
                }
            })
        },
        shangchuan: function () {

        },
        xiazai: function () {

        },
        qingkong: function () {

        },
        logout: function () {
            $.ajax({
                url: "/major_adjust_system/logout",
                type: 'POST',
                dataType: 'JSON',
                data: 1,
                success: function (data) {
                    console.log(data);
                    alert("已退出登录,即将返回登录界面");
                    setTimeout(() => {
                        location.href = "/major_adjust_system/login"
                    }, 2000);
                },
                errorr: function () {
                    alert("退出登录失败");
                }

            })
        },
    }
})