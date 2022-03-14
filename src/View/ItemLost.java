/***********************
 Yogev Yosef - 312273410
 Eldor Shir  - 311362461
 ***********************/

package View;

import Controller.ItemInterface;
import Model.Address;
import Model.FoundLostItem;
import Model.Item;
import Model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

@WebServlet("/ItemLost")
public class ItemLost extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        System.out.println("-->ItemLost: ");

        try {
            // create an item
            String color = request.getParameter("color");
            String city = request.getParameter("city");
            String street = request.getParameter("street");
            String category = request.getParameter("category");
            String regex = "^[ A-Za-z]+$";

            //check that only letters were entered
            if(category.matches(regex) == false ||color.matches(regex) == false || city.matches(regex) == false || street.matches(regex) == false)
            {
                System.out.println("-->ItemLost: wrong data");
                response.getWriter().println("<script type=\"text/javascript\">");
                response.getWriter().println("alert('Data is incorrect!!');");
                response.getWriter().println("location='LostForm.jsp';");
                response.getWriter().println("</script>");
                return;
            }
            FoundLostItem lost = new FoundLostItem();
            FoundLostItem.increaseInstanceCounter();//update the new amount
            lost.setSerial(FoundLostItem.instanceCounter);
            Item item = new Item(request.getParameter("category"), color);
            lost.setItem(item);
            lost.setContact((User) session.getAttribute("currUser"));
            Address address = new Address(city, street);
            lost.setAddress(address);
            LocalDateTime ldt = LocalDateTime.parse(request.getParameter("dateNtime"));
            lost.setFoundLostDate(ldt);

            lost.setItemType(FoundLostItem.ItemType.Lost);
            lost.setDetails(request.getParameter("details"));
            System.out.println("-->ItemLost: item number " + lost.getSerial() + " created successfully");

            // add to DB
            ItemInterface.insertItem(lost);

            System.out.println("-->ItemLost: item number " + lost.getSerial() + " added");
            response.getWriter().println("<script type=\"text/javascript\">");
            response.getWriter().println("alert('Lost form successfully received');");
            response.getWriter().println("location='Home.jsp';");
            response.getWriter().println("</script>");
            return;

        } catch (IllegalAccessException | ClassNotFoundException |
                SQLException e) {
            e.printStackTrace();
        }
    }

}
