package lk.jiat.ee.ejb.remote;

import jakarta.ejb.Remote;
import lk.jiat.ee.core.model.AuctionItem;

@Remote
public interface AuctionService {
    boolean placeBid(String auctionId, String userEmail, int bidAmount);
    AuctionItem getAuctionDetails(String auctionId);
}
