package lk.jiat.ee.core.model;

import java.io.Serializable;
import java.util.Date;

public class AuctionItem implements Serializable {
    private String id;
    private String title;
    private String description;
    private int startingBid;
    private int currentBid;
    private Date startDate;
    private Date EndDate;
    private int Status;

    public AuctionItem() {}

    public AuctionItem(String id, String title, String description, int startingBid,int currentBid, Date startDate, Date endDate, int status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startingBid = startingBid;
        this.currentBid = currentBid;
        this.startDate = startDate;
        EndDate = endDate;
        Status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStartingBid() {
        return startingBid;
    }

    public void setStartingBid(int startingBid) {
        this.startingBid = startingBid;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return EndDate;
    }

    public void setEndDate(Date endDate) {
        EndDate = endDate;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(int currentBid) {
        this.currentBid = currentBid;
    }
}
