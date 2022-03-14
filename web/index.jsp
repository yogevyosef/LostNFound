<%-----------------------
Yogev Yosef - 312273410
Eldor Shir  - 311362461
-----------------------%>

<%@ page import="Controller.StartInterface" %>
<%@ page import="java.time.LocalDateTime" %>
<!DOCTYPE html>
<html>
<head>
  <title>Login Page</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- link for animation style -->
  <link rel="stylesheet" type="text/css" href="css/animations.css"/>

  <!-- link for font -->
  <link href='http://serve.fontsproject.com/css?family=Shuneet:400' rel='stylesheet' type='text/css'>
</head>
<style>
  body{
    text-align: center;
    font-family: Shuneet, Helvetica, sans-serif;
  }
  form {
    display: inline-block;
  }
  button, input[type=button]{
    background-color: #4B8ECF;
    font-size: 22px;
    height: 47px;
    width: 132px;
    border-radius: 8px;
    border: 1px solid #4B8ECF;
    transition: all 0.5s;
    cursor: pointer;
  }
  button:hover , input[type=button]:hover{
    box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24), 0 17px 50px 0 rgba(0,0,0,0.19);
  }
  input[type=text], input[type=password] {
    padding: 10px 10px;
    margin: 8px 0;
    border-radius: 8px;
    display: inline-block;
    border: 1px solid #ccc;
    box-sizing: border-box;
    font-size: 20px;
    font-family: Shuneet, Helvetica, sans-serif;
  }
  input::-webkit-input-placeholder{
    font-family: Shuneet, Helvetica, sans-serif;
  }
</style>
<body>
    <%
          // remove the last session
          if(!session.isNew())
            session.invalidate();
          StartInterface.checkDatabase();
    %>

    <form action="Login" method="POST">

        <div class="fadeIn">
          <img src="images/logo2.png" style="width:600px;height:250px;position:sticky;" class="center">
        </div><br><br>

        <div class="container">

            <label for="username"; style="font-size:30px"><b>Username:</b></label>&nbsp&nbsp&nbsp&nbsp&nbsp
            <input type="text" placeholder="Enter Username" id="username" name="username" size="30" title="Required field" required
                 oninvalid="this.setCustomValidity('Username must be entered correctly')" oninput="this.setCustomValidity('')">
            <br><br>

            <label for="psw"; style="font-size:30px"><b>Password:</b></label>&nbsp&nbsp&nbsp&nbsp&nbsp
            <input type="password" placeholder="Enter Password" id="psw" name="psw" size="30" title="Required field" required
                   oninvalid="this.setCustomValidity('Password must be entered correctly')" oninput="this.setCustomValidity('')"><br>

            <div class="btn-group"><br>
                <button type="submit" id="myBtn">Login</button>
                <input type="button" value="Sign-in" onclick="document.location.href='Registration.jsp'">
            </div>
        </div>
    </form>

    <script>
        /*define enter as submit*/
        var input = document.getElementById("myInput");
        input.addEventListener("keyup", function(event) {
            if (event.keyCode === 13) {
                event.preventDefault();
                document.getElementById("myBtn").click();
            }
        });
    </script>
</body>
</html>