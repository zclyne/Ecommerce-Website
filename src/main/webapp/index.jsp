<html>
<head>
    <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
</head>
<body>
    <h2>Hello World!</h2>

<%-- 登录 --%>
    <p>用户登录</p>
    <form name="login_form" action="/user/login.do" method="post">
        <input type="text" name="username">
        <input type="password" name="password">
        <input type="submit" value="Login">
    </form>

    spring mvc上传文件
    <form name="form1" action="/manage/product/upload.do" method="post", enctype="multipart/form-data">
        <input type="file" name="upload_file">
        <input type="submit" value="springmvc上传文件">
    </form>

    富文本图片上传
    <form name="form1" action="/manage/product/richtext_img_upload.do" method="post", enctype="multipart/form-data">
        <input type="file" name="upload_file">
        <input type="submit" value="富文本图片上传">
    </form>
</body>
</html>
