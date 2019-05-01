<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Index</title>
</head>
<body>

	<h2>文件上传</h2>
    <form action="upload" enctype="multipart/form-data" method="post">
        <table>
            <tr>
                <td>请选择文件:</td>
                <td><input type="file" name="file"></td>
            </tr>
            <tr>
                <td><input type="submit" value="上传"></td>
            </tr>
        </table>
    </form>
    
    <a href="download">文件下载</a>
    
    <form action="login"  method="post">
        <table>
            <tr>
                <td>名字</td>
                <td><input type="text" name="username"></td>
            </tr>
            <tr>
            <td>密码</td>
                <td><input type="password" name="password"></td>
            </tr>
            <tr>
                <td><input type="submit" value="登陆"></td>
            </tr>
        </table>
    </form>
    <a href="logout">退出</a>
    
    <br/><br/><br/>
    <a href="zhuanyefenxi">开始进行专业分析</a>
    
    <br/>
    <a href="chakanjinnianyujing">查看今年预警专业</a>
    
    <br/>
    <a href="chakanwangnianyujing">查看往年预警专业</a>
    
    <br/>
    <a href="chaKanZanTingZhuanYe">查看暂停招生专业</a>
    
    <br/>
    <a href="chaKanZhuanYeCheXiao">查看专业撤销名单</a>
    
    <br/>
    <a href="qingkongshuju">清空数据</a>
    
    
    
    
</body>
</html>