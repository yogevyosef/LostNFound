<%-----------------------
Yogev Yosef - 312273410
Eldor Shir  - 311362461
-----------------------%>

<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="Controller.*"
         import="Model.*"
         import="DB.*"
%>
<%@ page import="java.time.LocalDateTime" %>
<html>
<head>
    <title>Lost Form</title>

    <!-- link for font -->
    <link href='http://serve.fontsproject.com/css?family=Shuneet:400' rel='stylesheet' type='text/css'>
</head>
<style>
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
        font-family: Shuneet, Helvetica, sans-serif;
    }
    input[list]{
        font-family: Shuneet, Helvetica, sans-serif;
        border: 2px black;
        border-radius: 10px 10px 10px 10px;
        font-size: 18px;
        padding: 5px;
        height: 35px;
        width: 300px;
    }
    /* Full-width input fields */
    input[type=text], input[type=password] {
        width: 100%;
        padding: 10px;
        margin: 5px 0 10px 0;
        display: inline-block;
        border: none;
        font-size: 12px;
        background: #f1f1f1;
        font-family: Shuneet, Helvetica, sans-serif;
    }

    input[type=text]:focus, input[type=password]:focus {
        background-color: #ddd;
        outline: none;
    }

    /* Overwrite default styles of hr */
    hr {
        border: 2px solid #c61a1a;
        margin-bottom: 25px;
    }
    fieldset{
        border-color: #c61a1a;
    }
    input[list]::-webkit-input-placeholder{
        color: black;
        font-size: 12px;
        font-family: Shuneet, Helvetica, sans-serif;
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
        <form action="ItemLost" method="POST">
            <h1 style="font-size: 35px; margin: 0px;">Lost Form</h1>
            <p><b>Please fill in this form to post a lost.</b></p>
            <hr>

            <fieldset>
                <legend>Time & Position</legend>

                <label for="dateNtime">Please select the date and time of the lost</label><br><br>
                <input type="datetime-local" id="dateNtime" name="dateNtime" title="Required field" required max="<%=LocalDateTime.now().withNano(0)%>"
                       oninvalid="this.setCustomValidity('Date and time must be entered correctly')" oninput="this.setCustomValidity('')"><br><br>

                <label for="city">Please enter the city you found the lost</label>
                <input type="text" placeholder="Enter City" id="city" name="city" title="Required field" required
                       oninvalid="this.setCustomValidity('City must be entered correctly')" oninput="this.setCustomValidity('')">

                <label for="street">Please enter the street you found the lost</label>
                <input type="text" placeholder="Enter Street" id="street" name="street" title="Required field" required
                       oninvalid="this.setCustomValidity('Area must be entered correctly')" oninput="this.setCustomValidity('')">
            </fieldset>
            <br><br>

            <fieldset>
                <legend>The item</legend>

                <label for="category">Please enter the object of the lost</label><br><br>
                <input list="objects" placeholder="Enter Object" id="category" name="category" title="Required field" required
                       oninvalid="this.setCustomValidity('Object must be entered correctly')" oninput="this.setCustomValidity('')"><br>

                <datalist id="objects">
                    <option value="Wallet">
                    <option value="Phone">
                    <option value="Bag">
                    <option value="Bottle">
                    <option value="Dog">
                    <option value="Cat">
                    <option value="Book">
                    <option value="Hat">
                    <option value="Jacket">
                </datalist><br>

                <label for="color">Please enter the color of the lost</label>
                <input type="text" placeholder="Enter Color" id="color" name="color" title="Required field" required
                       oninvalid="this.setCustomValidity('Color must be entered correctly')" oninput="this.setCustomValidity('')">
            </fieldset>
            <br>

            <label for="details"> Please enter any notes here </label><br><br>
            <textarea id="details" name="details" rows="15" cols="50"></textarea>
            <br><br>

            <hr>
            <button type="submit" id="myBtn">Submit</button>
        </form>
    </div>

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
