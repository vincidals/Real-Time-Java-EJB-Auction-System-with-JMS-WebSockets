package lk.jiat.ee.ejb.beans;

import jakarta.ejb.Singleton;
import lk.jiat.ee.core.model.Bid;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Singleton
public class BidStoreBean {

    private final List<Bid> bids = new ArrayList<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public void addBid(Bid bid) {
        lock.writeLock().lock();
        try {
            bids.add(bid);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public List<Bid> getAllBids() {
        lock.readLock().lock();
        try {
            return new ArrayList<>(bids);
        } finally {
            lock.readLock().unlock();
        }
    }

    public List<Bid> getBidsByAuctionId(String auctionId) {
        lock.readLock().lock();
        try {
            List<Bid> result = new ArrayList<>();
            for (Bid b : bids) {
                if (b.getAuctionItemId().equals(auctionId)) {
                    result.add(b);
                }
            }
            return result;
        } finally {
            lock.readLock().unlock();
        }
    }
}
