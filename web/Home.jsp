<%-----------------------
Yogev Yosef - 312273410
Eldor Shir  - 311362461
-----------------------%>

<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="Controller.*"
         import="Model.*"
         import="DB.*"
%>
<html>
<head>=
                  <meta charset="windows-1255">
    <title>Lost & Found</title>
    <!-- link for font -->
    <link href'http://serve.fontsproject.com/css?family=Shuneet:400' rel='stylesheet' type='text/css'>
</head>
<style>
    .navbar {
        overflow: hidden;
        background-color: white;
        position: fixed;
        top: 0;
        width: 100%;
    }
    .main {
        padding: 16px;
        margin-top: 100px;
    }
    body{
       background-color: white;
        font-family: Shuneet, Helvetica, sans-serif;
    }
    button, input[type=button], input[type=text], input[type=password]{
        background-color: #4B8ECF;
        font-size: 22px;
        padding: 15px 28px;
        border-radius: 8px;
        border: 1px solid #4B8ECF;
        transition: all 0.5s;
        cursor: pointer;
        margin-top: 14px;
    }
    button:hover{
        box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24), 0 17px 50px 0 rgba(0,0,0,0.19);
    }
    footer{
        position: fixed;
        text-align: center;
        bottom: 0px;
        width: 100%;
    }
    .center {
        position: absolute;
        left: 0;
        top: 50%;
        width: 100%;
        text-align: center;
    }
</style>
<body>
    <%
        //security check
        if(session.getAttribute("login") == null){
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }
        User currUser = (User) session.getAttribute("currUser");
    %>

    <!--navigation bar of the page -->
    <div class="navbar">
        <a href="Home.jsp">
            <img src="images/logo2.png" style="float:left;width:26%; height: 90px">
        </a>
        <input type="button" value="<%=currUser.getName()%>" onclick="document.location.href='PrivateZone.jsp'">
        <input type="button" value="I've Lost" onclick="document.location.href='LostForm.jsp'">
        <input type="button" value="I've Found" onclick="document.location.href='FoundForm.jsp'">
        <input type="button" value="Terms & Privacy" onclick="window.open('Terms&Privacy.jsp','_blank')">
        <a href="index.jsp">
            <img src="images/exit1.jfif" style="float:right;width:5%; margin-top: 8px;margin-right: 8px">
        </a>
    </div>

    <div class="main">
        <div style="text-align: center">
            <h1>Who are we?</h1>
            <h2>The Social Lost & Found Website</h2><br>
            <h3>In Lost & Found you can post your lost, and we will take care for you and find it.</h3>
            <h3>You can also post a found and we will help you to return it to it's owners.</h3>
        </div>

        <div class="center"><br><br>
            <input type="button" style="font-size: 30px; padding: 25px 28px;margin:50px" value="Search Lost" onclick="document.location.href='Lost.jsp'">
            <input type="button" style="font-size: 30px; padding: 25px 28px;margin:50px" value="Search Founds" onclick="document.location.href='Founds.jsp'">
        </div>

        <footer>
            <h5>All Rights Reserved to Lost & Found</h5>
        </footer>
    </div>

</body>
</html>