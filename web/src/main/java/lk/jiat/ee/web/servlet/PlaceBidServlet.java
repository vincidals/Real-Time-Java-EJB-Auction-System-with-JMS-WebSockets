package lk.jiat.ee.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.jms.*;
import lk.jiat.ee.core.model.User;
import lk.jiat.ee.ejb.remote.AuctionService;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;

@WebServlet("/PlaceBidServlet")
public class PlaceBidServlet extends HttpServlet {

    @jakarta.ejb.EJB
    private AuctionService auctionService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String auctionId = req.getParameter("auctionId");
        String bidAmountStr = req.getParameter("userBid");

        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("userSession");

        if (user == null) {
            resp.sendRedirect("index.jsp");
            return;
        }

        try {
            int bidAmount = Integer.parseInt(bidAmountStr);

            boolean success = auctionService.placeBid(auctionId, user.getEmail(), bidAmount);

            if (!success) {
                resp.sendRedirect("user/UserAuctions.jsp?error=invalidBidAmount");
                return;
            }

            InitialContext ctx = new InitialContext();
            ConnectionFactory cf = (ConnectionFactory) ctx.lookup("jms/BidConnectionFactory");
            Topic topic = (Topic) ctx.lookup("jms/BidTopic");

            try (JMSContext jmsContext = cf.createContext()) {
                TextMessage msg = jmsContext.createTextMessage();
                msg.setText("Bid placed: " + bidAmount + " on item: " + auctionId + " by: " + user.getEmail());
                msg.setStringProperty("auctionId", auctionId);
                jmsContext.createProducer().send(topic, msg);
            }

            resp.sendRedirect("user/UserAuctions.jsp");

        } catch (NumberFormatException e) {
            resp.sendRedirect("user/UserAuctions.jsp?error=invalidBid");
        } catch (NamingException e) {
            throw new ServletException("JMS resources not found.", e);
        } catch (Exception e) {
            throw new ServletException("Error placing bid", e);
        }
    }

}
