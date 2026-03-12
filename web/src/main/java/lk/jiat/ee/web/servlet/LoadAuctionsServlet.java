package lk.jiat.ee.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.jiat.ee.core.model.AuctionItem;
import lk.jiat.ee.ejb.beans.AuctionStoreBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/LoadAuctionsServlet")
public class LoadAuctionsServlet extends HttpServlet {

    @EJB
    private AuctionStoreBean itemStore;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<AuctionItem> auctionItems = new ArrayList<>(itemStore.getActiveAuctionItems().values());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        new com.fasterxml.jackson.databind.ObjectMapper().writeValue(response.getWriter(), auctionItems);
    }
}
