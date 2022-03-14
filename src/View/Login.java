/***********************
 Yogev Yosef - 312273410
 Eldor Shir  - 311362461
 ***********************/

package View;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import Controller.*;
import Model.*;

@WebServlet("/Login")
public class Login extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("psw");
        System.out.println("-->Login: username: " + username + " user password: " + password);

        try {
            User currUser = UserInterface.getUser(username, password);
            //case its a valid user
            if (currUser != null) {
                HttpSession session = request.getSession();
                session.setAttribute("currUser", currUser);

                //flag for valid entrance
                session.setAttribute("login", true);
                if (currUser.getType() == User.UserType.Manager)
                    session.setAttribute("managerFlag", true);
                System.out.println("-->Login: valid user");
                request.getRequestDispatcher("/Home.jsp").forward(request, response);
            }
            //case its an invalid user
            else {
                System.out.println("-->Login: invalid currUser");
                response.getWriter().println("<script type=\"text/javascript\">");
                response.getWriter().println("alert('Username or password incorrect');");
                response.getWriter().println("location='index.jsp';");
                response.getWriter().println("</script>");
            }
        } catch (IllegalAccessException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
