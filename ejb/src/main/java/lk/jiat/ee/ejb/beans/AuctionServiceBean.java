package lk.jiat.ee.ejb.beans;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import lk.jiat.ee.core.model.AuctionItem;
import lk.jiat.ee.core.model.Bid;
import lk.jiat.ee.ejb.remote.AuctionService;

@Stateless
public class AuctionServiceBean implements AuctionService {

    @EJB
    private AuctionStoreBean store;

    @EJB
    private BidStoreBean bidStore;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean placeBid(String auctionId, String userEmail, int bidAmount) {
        AuctionItem item = store.getAuctionItem(auctionId);

        if (item == null) {
            System.out.println("No item found with ID: " + auctionId);
            return false;
        }

        synchronized (item) {
            int currentBid = item.getCurrentBid();
            int minValidBid = currentBid + 1000;

            if (bidAmount < minValidBid) {
                System.out.println("Bid too low! Current: " + currentBid + ", Entered: " + bidAmount);
                return false;
            }

            item.setCurrentBid(bidAmount);
            store.updateAuctionItem(item);

            Bid bid = new Bid(String.valueOf(bidAmount), userEmail, auctionId);
            bidStore.addBid(bid);
            System.out.println("Bid placed: " + bidAmount + " by " + userEmail + " on " + auctionId);
        }

        return true;
    }

    @Override
    public AuctionItem getAuctionDetails(String auctionId) {
        return store.getAuctionItem(auctionId);
    }
}

