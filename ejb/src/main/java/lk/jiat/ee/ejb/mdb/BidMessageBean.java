package lk.jiat.ee.ejb.mdb;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.*;
import lk.jiat.ee.core.websocket.BidNotificationSocket;

@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Topic"),
                @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/BidTopic")
        }
)
public class BidMessageBean implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String body = textMessage.getText();
                String auctionId = textMessage.getStringProperty("auctionId");
                System.out.println("[Real-time Bid Topic] " + body + " for Auction ID: " + auctionId);

                BidNotificationSocket.broadcast(body);

            } else {
                System.err.println("Received non-text message.");
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
