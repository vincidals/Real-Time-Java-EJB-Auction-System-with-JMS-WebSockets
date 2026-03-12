package lk.jiat.ee.core.model;

import java.io.Serializable;

public class Bid implements Serializable {
    private String bid;
    private String userEmail;
    private String AuctionItemId;

    public Bid() {}

    public Bid(String bid, String userEmail, String auctionItemId) {
        this.bid = bid;
        this.userEmail = userEmail;
        AuctionItemId = auctionItemId;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getAuctionItemId() {
        return AuctionItemId;
    }

    public void setAuctionItemId(String auctionItemId) {
        AuctionItemId = auctionItemId;
    }
}
