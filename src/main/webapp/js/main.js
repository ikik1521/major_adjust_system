var now = new Date;
var fullYear = now.getFullYear();
var year = fullYear - 2000;
var month = now.getMonth() + 1;
var day = now.getDate();

var time = document.getElementsByClassName('time');
time[0].innerHTML += fullYear + "-" + month + "-" + day;

/*$(document).ready(function () { //用户ID的获取
    $.getJSON("./ceshi.json", function (result, status) {
        v.index = result;
    })
});
*/
var v = new Vue({
    el: '#app',
    data: {
        fullYear: fullYear,
        year: year,
        month: month,
        day: day,

        isActive: false,
        isReason: false,
        isDelete: false,
        isWangnian: false,

        isYujing: false,
        isTuichu: false,

        index: Object,
        tables: Object,
        reasons: Object
    },
    methods: {
        biaoge: function (url) {
        	
            v.isWangnian = false;
            v.isDelete = false;
            $.getJSON(url, function (result, status) {
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
        yujing: function () {
            if (v.isYujing == false) {
                v.isYujing = true;
            } else {
                v.isYujing = false;
            }

        },
        tuichu: function () {
            if (v.isTuichu == false) {
                v.isTuichu = true;
            } else {
                v.isTuichu = false;
            }
        },
        shanchu: function (aaa, bbb) { //点击删除按钮后表格里面的删除
            console.log(typeof aaa);
            $.ajax({
                url: "deleteOne",
                type: 'POST',
                dataType: 'JSON',
                data: {
                	warningYear: aaa,
                	majorCode: bbb
                },
                success: function (data) {
                    console.log(data);
                    $.getJSON("lastYear", function (result, status) {
                        v.tables = result;
                    })
                },
                errorr: function () {
                    alert("删除失败");
                }

            })
        },
        reason: function () { //预警原因
        
                if (v.isActive == true && v.isReason == false) {
                    v.isReason = true;
                    v.isActive = false;
                    v.reasons = result;
                }
            
        },
        wangnian: function (url) {
            v.biaoge(url);
            v.isWangnian = true;
        },
        deleteButton: function () { //往年预警下面的删除按钮
            v.isDelete = true;
        },
        tiaoZhuan: function (url) { //退出 清空数据 下载

            switch (url) {
                case 'download':
                    var newWindow = window.open(url);
                    setTimeout(function () {
                        newWindow.close();
                    }, 1000);
                    break;
                case 'deleteAll':
                    alert("清除数据中");

                    var newWindow = window.open(url);
                    setTimeout(function () {
                        newWindow.close();
                    }, 1000);
                    window.location.href = 'index.html'
                    break;
                case 'logout':
                    var newWindow = window.open(url);
                    setTimeout(function () {
                        newWindow.close();
                    }, 1000);
                    window.location.href = 'login.html'
                    break;
                default:
                    break;
            }
        },
        shangchuan: function () {

            $('#FileUpload').click();
            $('#FileUpload').change(function () {
                $('#upload').click();
            })
            
        }
    }
})