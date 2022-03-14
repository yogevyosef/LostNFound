/***********************
 Yogev Yosef - 312273410
 Eldor Shir  - 311362461
 ***********************/

package View;

import Controller.UserInterface;
import Model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/Registration")
public class Registration extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String username = request.getParameter("username");
        System.out.println("-->Registration: username: " + username);

        try {
            String psw = request.getParameter("psw");
            String pswRepeat = request.getParameter("psw-repeat");
            String phoneNumber = request.getParameter("phoneNumber");
            String fullName = request.getParameter("fullname");

            //regexPhoneNumber to check validity of the phone number
            String regexPhoneNumber = "^[0-9]{3}\\-?[0-9]{3}\\-?[0-9]{4}$";

            if(psw.equals(pswRepeat) == false || phoneNumber.matches(regexPhoneNumber) == false || fullName.matches("^[ A-Za-z]+$") == false){
                System.out.println("-->Registration: wrong data");
                response.getWriter().println("<script type=\"text/javascript\">");
                response.getWriter().println("alert('Data is incorrect!!');");
                response.getWriter().println("location='Registration.jsp';");
                response.getWriter().println("</script>");
                return;
            }

            User currUser = UserInterface.getUser(username);
            //case the user is new
            if (currUser == null) {
                currUser = new User(username);
                currUser.setName(fullName);
                currUser.setPhoneNumber(phoneNumber);
                currUser.setPassword(psw);
                currUser.setType(User.UserType.Client);


                UserInterface.insertUser(currUser);

                //saving the user attributes
                HttpSession session = request.getSession();
                session.setAttribute("currUser", currUser);
                //flag for valid entrance
                session.setAttribute("login", true);

                request.getRequestDispatcher("/Home.jsp").forward(request, response);
            }

            // case user already exist
            else {
                System.out.println("-->Registration: username is already taken");
                response.getWriter().println("<script type=\"text/javascript\">");
                response.getWriter().println("alert('Username is already taken');");
                response.getWriter().println("location='Registration.jsp';");
                response.getWriter().println("</script>");
            }

        } catch (IllegalAccessException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
