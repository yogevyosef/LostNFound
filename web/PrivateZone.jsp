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
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.concurrent.TimeUnit" %>
<html>
<head>
    <meta charset="windows-1255">
    <title>Private Zone</title>
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

    * {
        box-sizing: border-box
    }

    /* Style the tab */
    .tab {
        margin: auto;
        float: left;
        border: 1px solid black;
        background-color: #cc3b3c;
        width: 20%;
        height: 50%;
    }

    /* Style the buttons inside the tab */
    .tab button {
        display: block;
        background-color: inherit;
        color: black;
        padding: 22px 16px;
        width: 100%;
        border: none;
        outline: none;
        text-align: left;
        cursor: pointer;
        transition: 0.3s;
        font-size: 25px;
    }

    /* Change background color of buttons on hover */
    .tab button:hover {
        background-color: #f2d7d7;
    }

    /* Create an active/current "tab button" class */
    .tab button.active {
        background-color: #f2d7d7;
    }

    /* Style the tab content */
    .tabcontent {
        float: left;
        padding: 0px 12px;
        border: 1px solid black;
        width: 70%;
        border-left: none;
        height: 50%;
    }

    span {
        font-weight: bold;
        font-size: x-large;
        font-style: italic;
        text-decoration: underline;
    }
    label{
        font-size: x-large;
    }

    /*style for manager zone table*/
    table {
        font-family: Shuneet, Helvetica, sans-serif;
        border-collapse: collapse;
        width: 70%;
        height: 70%;
        margin: auto;
    }

    td, th {
        border: 1px solid black;
        padding: 8px;
        font-weight: bold;
    }

    tr:hover {
        background-color: #f2d7d7;
    }

    thead {
        background-color: #cc3b3c;
    }


</style>
<body>
<%
    //security check
    if (session.getAttribute("login") == null) {
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
    <div class="tab">
        <button class="tablinks" onclick="openAttribute(event, 'Personal Information')" id="defaultOpen">Personal
            Information
        </button>
        <button class="tablinks" onclick="openAttribute(event, 'My Founds')">My Founds</button>
        <button class="tablinks" onclick="openAttribute(event, 'My Lost')">My Lost</button>
    </div>

    <div id="Personal Information" class="tabcontent">
        <br>
        <span>Full Name:</span>&nbsp
        <label><%=currUser.getName()%></label>
        <br><br>
        <span>Username:</span>&nbsp
        <label><%=currUser.getUsername()%></label>
        <br><br>
        <span>Phone Number:</span>&nbsp
        <label><%=currUser.getPhoneNumber()%></label>
    </div>

    <div id="My Founds" class="tabcontent">
        <br>
        <ul>
            <%
                Vector<FoundLostItem> founds = ItemInterface.getAllUserFounds(currUser.getUsername());
                for (int i = 0; i < founds.size(); i++) {%>
            <li>
                <%="The found No. " + founds.elementAt(i).getSerial() + " of " + founds.elementAt(i).getItem().getCategory() + " at " + founds.elementAt(i).getFoundLostDate().toLocalDate().toString()%>
            </li><b>
            <%
                    User foundMatch = MatchInterface.checkLostMatch(founds.elementAt(i).getSerial());

                    if (foundMatch == null)
                        out.println("Status: On Hold");
                    else
                        out.println("Status: Found a match!! contact info: " + foundMatch.getName() + " Phone number: " + foundMatch.getPhoneNumber());
            %></b><%
                }%>

        </ul>
    </div>

    <div id="My Lost" class="tabcontent">
        <br>
        <ul>
            <%
                Vector<FoundLostItem> lost = ItemInterface.getAllUserLost(currUser.getUsername());
                for (int i = 0; i < lost.size(); i++) {%>
            <li>
                <%="The lost No. " + lost.elementAt(i).getSerial() + " of " + lost.elementAt(i).getItem().getCategory() + " at " + lost.elementAt(i).getFoundLostDate().toLocalDate().toString()%>
            </li><b>
            <%
                    User foundMatch = MatchInterface.checkFoundMatch(lost.elementAt(i).getSerial());

                    if (foundMatch == null)
                        out.println("Status: On Hold");
                    else
                        out.println("Status: Found a match!! contact info: " + foundMatch.getName() + " Phone number: " + foundMatch.getPhoneNumber());
            %></b>
               <% }%>
        </ul>
    </div>
    <br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
    <%
        //if its true it means its the manager entrance
        if (session.getAttribute("managerFlag") != null) {%>
    <h1 style="text-align: center; text-decoration: underline">Manager Matches:</h1>
    <table>
        <thead>
        <th style="font-size: 20px;text-align: center">Lost</th>
        <th style="font-size: 20px;text-align: center">Found</th>
        <th style="font-size: 20px;text-align: center">Approve/Deny</th>
        </thead>
        <%
            AlgoInterface.updateMatches();
            Vector<FoundLostItem> lostManager = MatchInterface.getAllLost();
            Vector<FoundLostItem> foundManager = MatchInterface.getAllFounds();
            //both vectors have the same size
            for (int i = 0; i < lostManager.size(); i++) {

            int foundNum = foundManager.elementAt(i).getSerial();
            int lostNum = lostManager.elementAt(i).getSerial();

        %>
        <tr>

            <td>
                <ul>
                    <li><%="Serial No. : " + lostManager.elementAt(i).getSerial()%>
                    </li>
                    <li><%="Name: " + lostManager.elementAt(i).getContact().getName()%>
                    </li>
                    <li><%="Category: " + lostManager.elementAt(i).getItem().getCategory()%>
                    </li>
                    <li><%="Color: " + lostManager.elementAt(i).getItem().getColor()%>
                    </li>
                    <li><%="City: " + lostManager.elementAt(i).getAddress().getCity()%>
                    </li>
                    <li><%="Street: " + lostManager.elementAt(i).getAddress().getStreet()%>
                    </li>
                    <li><%=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(lostManager.elementAt(i).getFoundLostDate())%>
                    </li>
                    <li><%="Details:"%>
                    </li>
                    <%
                        if(lostManager.elementAt(i).getDetails() != null)
                            out.println(lostManager.elementAt(i).getDetails());
                        else
                            out.println("No Details");

                    %>
                </ul>
            </td>

            <td>
                <ul>
                    <li><%="Serial No. : " + foundManager.elementAt(i).getSerial()%>
                    </li>
                    <li><%="Name: " + foundManager.elementAt(i).getContact().getName()%>
                    </li>
                    <li><%="Category: " + foundManager.elementAt(i).getItem().getCategory()%>
                    </li>
                    <li><%="Color: " + foundManager.elementAt(i).getItem().getColor()%>
                    </li>
                    <li><%="City: " + foundManager.elementAt(i).getAddress().getCity()%>
                    </li>
                    <li><%="Street: " + foundManager.elementAt(i).getAddress().getStreet()%>
                    </li>
                    <li><%=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(foundManager.elementAt(i).getFoundLostDate())%>
                    </li>
                    <li><%="Details:"%>
                    </li>
                    <%
                        if(foundManager.elementAt(i).getDetails() != null)
                            out.println(foundManager.elementAt(i).getDetails());
                        else
                            out.println("No Details");
                    %>
                </ul>
            </td>

            <td style="text-align: center">

            <%-- Approve/Deny each has different servlet --%>
            <form action="ApproveMatch" method="POST">

                <label for="foundNum"></label>
                <input type="text" id="foundNum" name="foundNum" value="<%=foundNum%>" style="display: none;">

                <label for="lostNum"></label>
                <input type="text" id="lostNum" name="lostNum" value="<%=lostNum%>" style="display: none;">

                <button type="submit">Approve</button>
            </form>

            <form action="DenyMatch" method="POST">

                <label for="foundNum2"></label>
                <input type="text" id="foundNum2" name="foundNum2" value="<%=foundNum%>" style="display: none;">

                <label for="lostNum2"></label>
                <input type="text" id="lostNum2" name="lostNum2" value="<%=lostNum%>" style="display: none;">

                <button type="submit">Deny</button>
            </form>
            </td>

        </tr>
        <%}%>
    </table>
    <%}%>

    <%--script for the vertical tabs--%>
    <script>


        function openAttribute(evt, cityName) {
            var i, tabcontent, tablinks;
            tabcontent = document.getElementsByClassName("tabcontent");
            for (i = 0; i < tabcontent.length; i++) {
                tabcontent[i].style.display = "none";
            }
            tablinks = document.getElementsByClassName("tablinks");
            for (i = 0; i < tablinks.length; i++) {
                tablinks[i].className = tablinks[i].className.replace(" active", "");
            }
            document.getElementById(cityName).style.display = "block";
            evt.currentTarget.className += " active";
        }

        // Get the element with id="defaultOpen" and click on it
        document.getElementById("defaultOpen").click();

    </script>

</div>

</body>
</html>