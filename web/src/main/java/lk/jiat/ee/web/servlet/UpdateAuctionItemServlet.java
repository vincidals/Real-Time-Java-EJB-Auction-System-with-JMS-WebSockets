package lk.jiat.ee.web.servlet;

import lk.jiat.ee.core.model.AuctionItem;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.jiat.ee.ejb.beans.AuctionStoreBean;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/UpdateAuctionItemServlet")
public class UpdateAuctionItemServlet extends HttpServlet {

    @jakarta.ejb.EJB
    private AuctionStoreBean itemStore;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String auctionId = request.getParameter("auctionId");
        String itemName = request.getParameter("itemName");
        String description = request.getParameter("description");
        String startingBidStr = request.getParameter("startingBid");
        String statusStr = request.getParameter("status");
        String endTime = request.getParameter("endTime");

        if (auctionId == null || auctionId.isEmpty() ||
                itemName == null || itemName.isEmpty() ||
                description == null || description.isEmpty() ||
                startingBidStr == null || startingBidStr.isEmpty() ||
                statusStr == null || statusStr.isEmpty() ||
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

        int status;
        try {
            status = Integer.parseInt(statusStr);
            if (status < 0 || status > 1) {
                response.sendRedirect("admin/AdminDashboard.jsp?error=invalidStatus");
                return;
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("admin/AdminDashboard.jsp?error=invalidStatusFormat");
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

        AuctionItem item = itemStore.getAuctionItem(auctionId);
        if (item == null) {
            response.sendRedirect("admin/AdminDashboard.jsp?error=itemNotFound");
            return;
        }

        item.setTitle(itemName);
        item.setDescription(description);
        item.setStartingBid(startingBid);
        item.setEndDate(endDate);
        item.setStatus(status);

        boolean updated = itemStore.updateAuctionItem(item);
        if (!updated) {
            response.sendRedirect("admin/AdminDashboard.jsp?error=updateFailed");
        } else {
            response.sendRedirect("admin/AdminDashboard.jsp?success=itemUpdated");
        }
    }
}

