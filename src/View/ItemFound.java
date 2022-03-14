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

@WebServlet("/ItemFound")
public class ItemFound extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        System.out.println("-->ItemFound: ");

        try {
            // create item
            String color = request.getParameter("color");
            String city = request.getParameter("city");
            String street = request.getParameter("street");
            String category = request.getParameter("category");
            String regex = "^[ A-Za-z]+$";
            //check that only letters were entered
            if(category.matches(regex) == false ||color.matches(regex) == false || city.matches(regex) == false || street.matches(regex) == false)
            {
                System.out.println("-->ItemFound: wrong data");
                response.getWriter().println("<script type=\"text/javascript\">");
                response.getWriter().println("alert('Data is incorrect!!');");
                response.getWriter().println("location='FoundForm.jsp';");
                response.getWriter().println("</script>");
                return;
            }
            FoundLostItem found = new FoundLostItem();
            FoundLostItem.increaseInstanceCounter();//update the new amount
            found.setSerial(FoundLostItem.instanceCounter);
            Item item = new Item(category, color);
            found.setItem(item);
            found.setContact((User) session.getAttribute("currUser"));
            Address address = new Address(city, street);
            found.setAddress(address);
            LocalDateTime ldt = LocalDateTime.parse(request.getParameter("dateNtime"));
            found.setFoundLostDate(ldt);

            found.setItemType(FoundLostItem.ItemType.Found);
            found.setDetails(request.getParameter("details"));
            System.out.println("-->ItemFound: item number " + found.getSerial() + " created successfully");

            // add to DB
            ItemInterface.insertItem(found);

            System.out.println("-->ItemFound.java: item number " + found.getSerial() + " added");
            response.getWriter().println("<script type=\"text/javascript\">");
            response.getWriter().println("alert('Found form successfully received');");
            response.getWriter().println("location='Home.jsp';");
            response.getWriter().println("</script>");
            return;

            
        } catch (IllegalAccessException | ClassNotFoundException |
                SQLException e) {
            e.printStackTrace();
        }
    }

}
