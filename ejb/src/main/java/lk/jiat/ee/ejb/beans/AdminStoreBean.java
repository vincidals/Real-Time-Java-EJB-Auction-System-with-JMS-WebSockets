package lk.jiat.ee.ejb.beans;

import lk.jiat.ee.core.model.Admin;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Singleton
public class AdminStoreBean {

    private final Map<String, Admin> admins = new HashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    @PostConstruct
    public void loadDemoAdmins() {
        addAdmin(new Admin("adminone", "plastic123"));
        addAdmin(new Admin("admintwo", "tiara123"));
    }

    public void addAdmin(Admin admin) {
        lock.writeLock().lock();
        try {
            admins.put(admin.getAdminUsername(), admin);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public Admin getAdmin(String username) {
        lock.readLock().lock();
        try {
            return admins.get(username);
        } finally {
            lock.readLock().unlock();
        }
    }

    public boolean adminExists(String username) {
        lock.readLock().lock();
        try {
            return admins.containsKey(username);
        } finally {
            lock.readLock().unlock();
        }
    }
}
