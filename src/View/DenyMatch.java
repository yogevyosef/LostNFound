/***********************
 Yogev Yosef - 312273410
 Eldor Shir  - 311362461
 ***********************/

package View;

import Controller.MatchInterface;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/DenyMatch")
public class DenyMatch extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        System.out.println("-->DenyMatch: ");
        try {
            int foundSerialNum = Integer.parseInt(request.getParameter("foundNum2"));
            int lostSerialNum = Integer.parseInt(request.getParameter("lostNum2"));
            MatchInterface.denyMatch(foundSerialNum,lostSerialNum);

            request.getRequestDispatcher("/PrivateZone.jsp").forward(request, response);
        } catch (IllegalAccessException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
