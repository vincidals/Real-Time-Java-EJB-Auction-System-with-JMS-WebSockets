package lk.jiat.ee.ejb.beans;

import lk.jiat.ee.core.model.AuctionItem;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Singleton
public class AuctionStoreBean {

    private final Map<String, AuctionItem> auctionItems = new HashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final AtomicInteger auctionIdGenerator = new AtomicInteger(1);

    @PostConstruct
    public void loadDemoAuctions() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.JUNE, 30, 23, 59, 59);
        Date endDateA1 = calendar.getTime();
        calendar.set(2025, Calendar.JULY, 31, 23, 59, 59);
        Date endDateA2 = calendar.getTime();

        addAuctionItem(new AuctionItem(null, "Vintage Wristwatch", "80s Casio 69 420 series", 87000, 98000, new Date(), endDateA1, 1));
        addAuctionItem(new AuctionItem(null, "Grandfather Clock", "50s Mahogany clock", 150000, 169000, new Date(), endDateA2, 1));
    }

    public String addAuctionItem(AuctionItem item) {
        lock.writeLock().lock();
        try {
            String id = "A" + auctionIdGenerator.getAndIncrement();
            item.setId(id);
            auctionItems.put(id, item);
            return id;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public boolean updateAuctionItem(AuctionItem item) {
        lock.writeLock().lock();
        try {
            String id = item.getId();
            auctionItems.put(id, item);
            return true;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public boolean updateAuctionBid(String id, double newBid) {
        lock.writeLock().lock();
        try {
            AuctionItem item = auctionItems.get(id);
            if (item != null && newBid > item.getCurrentBid()) {
                item.setCurrentBid((int) newBid);
                return true;
            }
            return false;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public AuctionItem getAuctionItem(String id) {
        lock.readLock().lock();
        try {
            return auctionItems.get(id);
        } finally {
            lock.readLock().unlock();
        }
    }

    public Map<String, AuctionItem> getAllAuctionItems() {
        lock.readLock().lock();
        try {
            return new HashMap<>(auctionItems);
        } finally {
            lock.readLock().unlock();
        }
    }

    public Map<String, AuctionItem> getActiveAuctionItems() {
        lock.readLock().lock();
        try {
            Map<String, AuctionItem> activeItems = new HashMap<>();
            for (Map.Entry<String, AuctionItem> entry : auctionItems.entrySet()) {
                if (entry.getValue().getStatus() == 1) {
                    activeItems.put(entry.getKey(), entry.getValue());
                }
            }
            return activeItems;
        } finally {
            lock.readLock().unlock();
        }
    }

}
