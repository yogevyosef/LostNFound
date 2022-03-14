<%-----------------------
Yogev Yosef - 312273410
Eldor Shir  - 311362461
-----------------------%>

<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="Controller.*"
         import="Model.*"
         import="DB.*"
         import="java.util.Vector"
%>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.util.Iterator" %>
<html>
<head>
    <title>Founds</title>
    <!-- link for font -->
    <link href='http://serve.fontsproject.com/css?family=Shuneet:400' rel='stylesheet' type='text/css'>
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
    table {
        font-family: Shuneet, Helvetica, sans-serif;
        border-collapse: collapse;
        width: 50%;
        margin: auto;
    }

    td, th {
        border: 1px solid black;
        text-align: center;
        padding: 8px;
        font-weight: bold;
    }
     tr:hover {
         background-color: #f2d7d7;
     }
    thead{
        background-color: #cc3b3c;
    }
    .container {
        text-align: center;
        border-radius: 5px;
        border-color: black;
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
    input[list]::-webkit-input-placeholder {
        color: black;
        font-size: 12px;
        font-family: Shuneet, Helvetica, sans-serif;
    }
    fieldset{
        border-color: #c61a1a;
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
        <h4 style="text-align: center">*At the bottom there is a claim form</h4>

        <input type="text" style="font-size: 20px;text-align: center" id="myInput" onkeyup="searchFunc()" placeholder="Search a type" title="Type in a name">
        <table id="foundsTable">
            <thead>
                    <th style="font-size: 20px;text-align: center">Found serial number</th>
                    <th style="font-size: 20px;text-align: center">Type</th>
                    <th style="font-size: 20px;text-align: center">Location</th>
                    <th style="font-size: 20px;text-align: center">Date of found</th>
            </thead>
                <%
                Vector<FoundLostItem> founds = ItemInterface.getAllFounds();
                    //removing all the lost that has a match
                    for (Iterator<FoundLostItem> iterator = founds.iterator(); iterator.hasNext(); ) {
                        FoundLostItem tempFound = iterator.next();
                        if (MatchInterface.isApproved(tempFound.getSerial()))
                            iterator.remove();
                    }

                for (int i=0; i<founds.size(); i++)
                {%>
                    <tr>
                        <td><%=founds.elementAt(i).getSerial()%></td>
                        <td><%=founds.elementAt(i).getItem().getCategory()%></td>
                        <td><%=founds.elementAt(i).getAddress().getCity()%></td>
                        <td><%=founds.elementAt(i).getFoundLostDate().toLocalDate().toString()%></td>
                    </tr>
                <%}%>
        </table>
    </div>
    <br><br>
    <div class="container">
        <form action="ClaimForm" method="POST">
            <h1 style="font-size: 35px; margin: 0px;text-decoration: underline">Claim Item Form</h1>
            <p><b>Please fill in this form to claim an item.</b></p>
            <p><b>Please fill in all the details you remember.</b></p>
            <hr>

            <label for="claimed"><b>Please choose the serial number of the claimed item</b></label><br>
            <input type="number" id="claimed" name="claimed" title="Required field" required min="0"
                   oninvalid="this.setCustomValidity('Serial number must be entered correctly')" oninput="this.setCustomValidity('')"><br>
            <br><hr>

            <%-- a label to ask if he already posted a found/lost post - with a blank to enter the number of his post
            if yes: he should write in the blank his post's serial number and no need to fill the form
            if no: he shuld fill this form--%>

            <label for="posted"><b>if you already posted an opposite post enter his serial number here and submit (o.w. enter 0 and fill the form)</b></label><br>
            <input type="number" id="posted" name="posted" title="Required field" value="0" min="0"
                   oninvalid="this.setCustomValidity('Serial number must be entered correctly')"><br><br>

            <fieldset>
                <legend>Add your details</legend>

                <label for="dateNtime">Please select the date and time</label><br><br>
                <input type="datetime-local" id="dateNtime" name="dateNtime" value="" max="<%=LocalDateTime.now().withNano(0)%>"
                       oninvalid="this.setCustomValidity('Date and time must be entered correctly')"><br><br>

                <label for="street">Please enter the street</label>
                <input type="text" placeholder="Enter Street" id="street" name="street">

                <br><br>

                <label for="color">Please enter the color</label>
                <input type="text" placeholder="Enter Color" id="color" name="color">

            </fieldset>
            <br><br>

            <label for="details"> Please enter any notes here </label><br><br>
            <textarea id="details" name="details" rows="15" cols="50"></textarea>
            <br><br>

            <label for="type"></label>
            <input type="hidden" id="type" name="type" value="Found">

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
        function searchFunc() {
            // Declare variables
            var input, filter, table, tr, td, i, txtValue;
            input = document.getElementById("myInput");
            filter = input.value.toUpperCase();
            table = document.getElementById("foundsTable");
            tr = table.getElementsByTagName("tr");

            // Loop through all table rows, and hide those who don't match the search query
            for (i = 0; i < tr.length; i++) {
                td = tr[i].getElementsByTagName("td")[1];
                if (td) {
                    txtValue = td.textContent || td.innerText;
                    if (txtValue.toUpperCase().indexOf(filter) > -1) {
                        tr[i].style.display = "";
                    } else {
                        tr[i].style.display = "none";
                    }
                }
            }
        }
    </script>

</body>
</html>
