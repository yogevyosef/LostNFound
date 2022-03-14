/***********************
 Yogev Yosef - 312273410
 Eldor Shir  - 311362461
 ***********************/

package View;

import Controller.ItemInterface;
import Controller.MatchInterface;
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

@WebServlet("/ClaimForm")
public class ClaimForm extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        System.out.println("-->ClaimForm: ");

        try {
            User user = (User) session.getAttribute("currUser");
            int serialClaimed = Integer.parseInt(request.getParameter("claimed"));

            FoundLostItem itemClaimed = ItemInterface.getItem(serialClaimed);

            //case there is no item with this serial number
            if (itemClaimed == null || MatchInterface.isApproved(itemClaimed.getSerial())) {
                System.out.println("-->ClaimForm: claim item that doesn't exist");
                response.getWriter().println("<script type=\"text/javascript\">");
                response.getWriter().println("alert('The serial number doesnt exist');");
                response.getWriter().println("location='Home.jsp';");
                response.getWriter().println("</script>");
                return;
            }

            int serialPosted = Integer.parseInt(request.getParameter("posted"));
            FoundLostItem foundLostItem;
            if (serialPosted > 0) { // already posted a form
                //check if this is really his form and he claimed a good item

                foundLostItem = ItemInterface.getItem(serialPosted, user);
                if (foundLostItem != null && itemClaimed != null && foundLostItem.getItemType() != itemClaimed.getItemType()) { // the form exist and found & the item claimed is exist
                    if (itemClaimed.getItemType() == FoundLostItem.ItemType.Found)
                        MatchInterface.insertMatch(itemClaimed.getSerial(), serialPosted);
                    else
                        MatchInterface.insertMatch(serialPosted, itemClaimed.getSerial());

                } else {  // its not his form or there is no such form etc
                    System.out.println("-M1->ClaimForm.java: wrong serial number");
                    response.getWriter().println("<script type=\"text/javascript\">");
                    response.getWriter().println("alert('Wrong serial number');");
                    response.getWriter().println("location='Home.jsp';");
                    response.getWriter().println("</script>");
                    return;
                }

            } else { // posted a form now (partial form)
                String street = request.getParameter("street");
                String color = request.getParameter("color");
                String regex = "^[ A-Za-z]+$";

                //check that only letters were entered
                if (color.matches(regex) == false || street.matches(regex) == false) {
                    System.out.println("-->ClaimForm: wrong data");
                    response.getWriter().println("<script type=\"text/javascript\">");
                    response.getWriter().println("alert('Data is incorrect!!');");
                    response.getWriter().println("location='Home.jsp';");
                    response.getWriter().println("</script>");
                    return;
                }

                LocalDateTime ldt;

                //check if date wasn't entered
                if (request.getParameter("dateNtime") != "")
                    ldt = LocalDateTime.parse(request.getParameter("dateNtime"));
                else
                    ldt = null;
                System.out.println(color);
                System.out.println(street);
                if (color == "" || street == "" || ldt == null) {
                    System.out.println("-->ClaimForm: new form but not all the details entered ");
                    response.getWriter().println("<script type=\"text/javascript\">");
                    response.getWriter().println("alert('You must fill all the form if you didnt fill');");
                    response.getWriter().println("location='Home.jsp';");
                    response.getWriter().println("</script>");
                    return;
                }
                foundLostItem = new FoundLostItem();
                FoundLostItem.increaseInstanceCounter();  //update the new amount
                foundLostItem.setSerial(FoundLostItem.instanceCounter);
                Item item = new Item(itemClaimed.getItem().getCategory(), color);
                foundLostItem.setItem(item);
                foundLostItem.setContact(user);
                Address address = new Address(itemClaimed.getAddress().getCity(), street);
                foundLostItem.setAddress(address);

                foundLostItem.setFoundLostDate(ldt);

                // check if lost or found
                FoundLostItem.ItemType claimType;

                if ((request.getParameter("type")).equals("Found"))
                    claimType = FoundLostItem.ItemType.Lost;
                else
                    claimType = FoundLostItem.ItemType.Found;


                if (claimType.equals(itemClaimed.getItemType()) == true) {  // try to claim found with found form / lost with lost form
                    System.out.println("-->ClaimForm: claim wrong item");
                    response.getWriter().println("<script type=\"text/javascript\">");
                    response.getWriter().println("alert('Wrong serial number claimed');");
                    response.getWriter().println("location='Home.jsp';");
                    response.getWriter().println("</script>");
                    return;
                }


                foundLostItem.setItemType(claimType);
                foundLostItem.setDetails(request.getParameter("details"));
                System.out.println("-M2->ClaimForm.java: item number " + foundLostItem.getSerial() + " created successfully");

                // add to DB
                ItemInterface.insertItem(foundLostItem);
                if (claimType == FoundLostItem.ItemType.Lost)
                    MatchInterface.insertMatch(itemClaimed.getSerial(), foundLostItem.getSerial());
                else
                    MatchInterface.insertMatch(foundLostItem.getSerial(), itemClaimed.getSerial());

            }

            System.out.println("-E->ClaimForm.java: item number " + foundLostItem.getSerial() + " added");
            response.getWriter().println("<script type=\"text/javascript\">");
            response.getWriter().println("alert('Claim form successfully received');");
            response.getWriter().println("location='Home.jsp';");
            response.getWriter().println("</script>");
            return;


        } catch (IllegalAccessException | ClassNotFoundException |
                SQLException e) {
            e.printStackTrace();
        }
    }
}
