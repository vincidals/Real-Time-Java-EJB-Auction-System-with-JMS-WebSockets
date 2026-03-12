package lk.jiat.ee.web.servlet;
;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.jiat.ee.core.model.AuctionItem;
import lk.jiat.ee.ejb.beans.AuctionStoreBean;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@WebServlet("/AddAuctionItemServlet")
public class AddAuctionItemServlet extends HttpServlet {

    @jakarta.ejb.EJB
    private AuctionStoreBean itemStore;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String itemName = request.getParameter("itemName");
        String description = request.getParameter("description");
        String startingBidStr = request.getParameter("startingBid");
        String endTime = request.getParameter("endTime");

        if (itemName == null || itemName.isEmpty() ||
                description == null || description.isEmpty() ||
                startingBidStr == null || startingBidStr.isEmpty() ||
                endTime == null || endTime.isEmpty()) {

            response.sendRedirect("admin/AdminDashboard.jsp?error=missingFields");
            return;
        }

        if (itemName.length() > 50) {
            response.sendRedirect("admin/AdminDashboard.jsp?error=nameTooLong");
            return;
        }

        if (description.length() > 300) {
            response.sendRedirect("admin/AdminDashboard.jsp?error=descriptionTooLong");
            return;
        }

        int startingBid;
        try {
            startingBid = Integer.parseInt(startingBidStr);
            if (startingBid < 0) {
                response.sendRedirect("admin/AdminDashboard.jsp?error=invalidBid");
                return;
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("admin/AdminDashboard.jsp?error=invalidBidFormat");
            return;
        }

        Date endDate;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            endDate = formatter.parse(endTime);

            if (endDate.before(new Date())) {
                response.sendRedirect("admin/AdminDashboard.jsp?error=endDatePast");
                return;
            }
        } catch (ParseException e) {
            response.sendRedirect("admin/AdminDashboard.jsp?error=invalidDateFormat");
            return;
        }

        String id = UUID.randomUUID().toString();
        Date startDate = new Date();
        AuctionItem newItem = new AuctionItem(id, itemName, description, startingBid, startingBid, startDate, endDate, 1);

        itemStore.addAuctionItem(newItem);
        response.sendRedirect("admin/AdminDashboard.jsp?success=itemUpdated");
    }
}

