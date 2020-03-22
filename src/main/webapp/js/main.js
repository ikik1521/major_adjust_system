var now = new Date;
var fullYear = now.getFullYear();
var year = fullYear - 2000;
var month = now.getMonth() + 1;
var day = now.getDate();

var time = document.getElementsByClassName('time');
time[0].innerHTML += fullYear + "-" + month + "-" + day;

// $(document).ready(function () { //用户ID的获取
//     $.getJSON("./ceshi.json", function (result, status) {
//         v.index = result;
//     })
// });

var v = new Vue({
    el: '#app',
    data: {
        fullYear: fullYear,
        year: year,
        month: month,
        day: day,
        counter: 0,

        isActive1: false,
        isActive2: false,
        isActive3: false,
        isReason: false,

        isDeleteButton: false,
        isDeleting: false,

        isYujing: true,
        isTuichu: true,

        index: Object,
        tables: Object,
        // newTable: Object,
        reasons: Array,
        search: fullYear
    },
    methods: {
        yujing: function () { //二级菜单
            if (v.isYujing == false) {
                v.isYujing = true;
            } else {
                v.isYujing = false;
            }

        },
        tuichu: function () { //二级菜单
            if (v.isTuichu == false) {
                v.isTuichu = true;
            } else {
                v.isTuichu = false;
            }
        },
        biaoge: function (url) {
            v.tables = '';
            // console.log(v.tables);

            v.isDeleteButton = false;
            v.isDeleting = false;

            $.ajax({
                type: 'GET',
                url: url,
                dataType: 'json',
                success: function (result, status) {
                    v.tables = result;

                    if (url == 'thisYear' || url == 'lastYear') {

                        // console.log("表格ajax的状态:" + status);
                        if (v.isActive1 == false && v.isReason == false) {
                            v.isActive2 = false;
                            v.isActive3 = false;

                            v.isActive1 = true;
                            // console.log("A:" + v.isActive1);
                            // console.log("R:" + v.isReason);
                            // console.log(v.tables);
                        } else if (v.isActive1 == false && v.isReason == true) {
                            v.isActive2 = false;
                            v.isActive3 = false;

                            v.isActive1 = true;
                            v.isReason = false;
                            // console.log("A:" + v.isActive1);
                            // console.log("R:" + v.isReason);
                            // console.log(v.tables);
                        }
                    } else if (url == 'stopMajor') {

                        if (v.isActive2 == false) {
                            v.isReason = false;
                            v.isActive1 = false;
                            v.isActive3 = false;

                            v.isActive2 = true;
                        }
                    } else if (url == 'cancleMajor') {

                        if (v.isActive3 == false) {
                            v.isReason = false;
                            v.isActive1 = false;
                            v.isActive2 = false;

                            v.isActive3 = true;
                        }
                    }
                    v.tihuan();
                },
                error: function (xhr, status, error) {
                    alert("暂无数据");
                    window.location.reload();

                    console.log(xhr);
                    console.log("表格ajax的状态:" + status);
                    console.log(error);
                },
                complete: function (XMLHttpRequest, textStatus) {
                    // 通过XMLHttpRequest取得响应头，sessionstatus
                    var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
                    if (sessionstatus == "TIMEOUT") {
                        var win = window;
                        while (win != win.top) {
                            win = win.top;
                        }
                        win.location.href = XMLHttpRequest.getResponseHeader("CONTEXTPATH");
                    }
                }
            });

            
        },
        wangnian: function (url) {
            v.biaoge(url);
            v.isDeleteButton = true;
        },
        shanchu: function (aaa, bbb) { //单项删除

            $.ajax({ //单项删除请求
                type: 'POST',
                url: "deleteOne",
                dataType: 'JSON',
                data: {
                    warningYear: aaa, //年份是数值
                    majorCode: bbb //专业代码是字符串
                },
                success: function () {
                },
                errorr: function () {
                    alert("删除失败");
                },
                complete: function (XMLHttpRequest, textStatus) {

                    // 通过XMLHttpRequest取得响应头，sessionstatus
                    var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
                    if (sessionstatus == "TIMEOUT") {
                        var win = window;
                        while (win != win.top) {
                            win = win.top;
                        }
                        win.location.href = XMLHttpRequest.getResponseHeader("CONTEXTPATH");
                    }

                    alert("删除成功");
                    $.ajax({
                        type: 'GET',
                        url: 'lastYear',
                        dataType: 'json',
                        success: function (result, status) {
                            //视图需要重新渲染
                            //console.log(result);
                            v.tables = '';
                            v.tables = result;

                            v.tihuan();
                            v.counter++;
                        },
                        error: function (xhr, status, error) {
                            alert("暂无数据");
                            window.location.reload();

                            console.log(xhr);
                            console.log("原因ajax的状态:" + status);
                            console.log(error);
                        }
                    });
                }

            })


        },
        reason: function (qqq) { //预警原因
            v.reasons = [];
            //console.log(qqq);
            var bbb = qqq.split("#");
            bbb.shift();

            //console.log(bbb);

            if (v.isActive1 == true && v.isReason == false) {
                v.isReason = true;
                v.isActive1 = false;
                v.reasons = bbb;
            }
        },
        tiaoZhuan: function (url) { //退出 清空数据 下载

            switch (url) {
                case 'download':
                    $('iframe').attr('src', url);

                    break;
                case 'deleteAll':
                    alert("清除数据中");
                    $('iframe').attr('src', url);
                    alert("清除成功");
                    v.isActive1 = false;
                    v.isReason = false;

                    break;
                case 'logout':
                    $('iframe').attr('src', url);
                    alert("已退出登录");
                    window.location.href = 'index.html';

                    break;
                default:
                    break;
            }
        },
        shangchuan: function () {

            $('#FileUpload').click();
            $('#FileUpload').change(function () {
                $('#upload').click();
                $('#FileUpload').off('change');
                console.log(event);
            })

        },
        sousuo: function (www) {
          
            $.ajax({
                type: 'POST',
                url: "searchByYear",
                dataType: 'JSON',
                data: {
                    searchYear: www
                },
                success: function (result, status) {
                	v.tables = '';
                    v.tables = result;

                    v.tihuan();
                    v.counter++;
                },
                errorr: function () {
                     alert("查找失败");
                },
            })
        },
        tihuan: function () {
            7
        	for (let i = 0; i < v.tables.length; i++) {
        		for(var k in v.tables[i]){
        			
        		console.log((v.tables[i])[k]);
        		
        			if((v.tables[i])[k] == '900.0'||(v.tables[i])[k] =='10000' ){
        			Vue.set(v.tables[i],k,'/');
        		}
        		
        		}
            }
        }
    }
})