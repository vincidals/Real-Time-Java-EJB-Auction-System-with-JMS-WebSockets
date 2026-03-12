package lk.jiat.ee.web.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.jiat.ee.core.model.Admin;
import lk.jiat.ee.ejb.beans.AuctionStoreBean;
import lk.jiat.ee.core.model.AuctionItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/AdminDashboard")
public class AdminDashboardServlet extends HttpServlet {

    @jakarta.ejb.EJB
    private AuctionStoreBean itemStore;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Admin admin = (Admin) request.getSession().getAttribute("adminSession");

        if (admin == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        List<AuctionItem> auctionItems = new ArrayList<>(itemStore.getAllAuctionItems().values());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        new com.fasterxml.jackson.databind.ObjectMapper().writeValue(response.getWriter(), auctionItems);
    }
}
