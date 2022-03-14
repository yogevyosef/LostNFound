<%-----------------------
Yogev Yosef - 312273410
Eldor Shir  - 311362461
-----------------------%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Registration Page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- link for font -->
    <link href='http://serve.fontsproject.com/css?family=Shuneet:400' rel='stylesheet' type='text/css'>
</head>
<style>
    body{
        background: white;
        font-family: Shuneet, Helvetica, sans-serif;
    }
    label, input::-webkit-input-placeholder{
        font-size: 20px;
        border-radius: 8px;
        font-family: Shuneet, Helvetica, sans-serif;
    }
    p{
        font-size: 20px;
        margin: 0px;
    }
    button {
        background-color: #4B8ECF;
        font-size: 20px;
        padding: 12px 28px;
        border-radius: 8px;
        border: 1px solid #4B8ECF;
        transition: all 0.5s;
        cursor: pointer;
        margin-top: 8px;
        width: 100%;
    }
    /* Full-width input fields */
    input[type=text], input[type=password] {
        width: 100%;
        padding: 10px;
        border-radius: 8px;
        margin: 5px 0 10px 0;
        display: inline-block;
        border: none;
        font-size: 20px;
        background: #f1f1f1;
        font-family: Shuneet, Helvetica, sans-serif;
    }
    * {
        box-sizing: border-box;
    }


    /* Add padding to containers */
    .container {
        padding: 16px;
        background-color: white;
    }

    /* Add a blue text color to links */
    a {
        color: dodgerblue;
    }
    input[type=text]:focus, input[type=password]:focus {
        background-color: #ddd;
        outline: none;
    }

    /* Overwrite default styles of hr */
    hr {
        border: 1px solid #f1f1f1;
        margin-bottom: 25px;
    }

</style>
<body>

    <form action="Registration" method="POST">
        <div class="container">
            <h1 style="font-size: 35px; margin: 0px;">Registration</h1>
            <p>Please fill in this form to create an account.</p>
            <hr>

            <label for="fullname"><b>Full Name</b></label><br>
            <input type="text" placeholder="Enter Full Name" id="fullname" name="fullname" title="Required field" required
                   oninvalid="this.setCustomValidity('Full name must be entered correctly')" oninput="this.setCustomValidity('')"><br>

            <label for="username"><b>Username</b></label><br>
            <input type="text" placeholder="Enter User Name" id="username" name="username"  title="Required field" required
                   oninvalid="this.setCustomValidity('Username must be entered correctly')" oninput="this.setCustomValidity('')"><br>

            <label for="phoneNumber"><b>Phone Number</b></label><br>
            <input type="text" placeholder="Enter Phone Number" id="phoneNumber" name="phoneNumber" title="Required field" required
                   oninvalid="this.setCustomValidity('Phone number must be entered correctly')" oninput="this.setCustomValidity('')"><br>

            <label for="psw"><b>Password</b></label><br>
            <input type="password" placeholder="Enter Password" id="psw" name="psw" title="Required field" required=""
                   oninvalid="this.setCustomValidity('Password must be entered correctly')" oninput="setCustomValidity('')"><br>

            <label for="psw-repeat"><b>Repeat Password</b></label><br>
            <input type="password" placeholder="Repeat Password" id="psw-repeat" name="psw-repeat" title="Required field" required=""
                   oninvalid="this.setCustomValidity('Password must be repeated correctly')" oninput="setCustomValidity('')"><br>
            <hr>

            <label>
                <input type="checkbox" checked="checked" name="approveTerms" required>
                By creating an account you agree to our <a href="Terms&Privacy.jsp" target="_blank">Terms & Privacy</a>.
            </label>
            <button type="submit" id="myBtn">Register</button>
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
