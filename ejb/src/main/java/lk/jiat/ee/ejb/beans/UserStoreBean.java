package lk.jiat.ee.ejb.beans;

import lk.jiat.ee.core.model.User;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Singleton
public class UserStoreBean {

    private final Map<String, User> users = new HashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    @PostConstruct
    public void loadDemoUsers() {
        addUser(new User("Nicki Minaj", "nickiminaj@gmail.com", "nicki123", "0712345678"));
        addUser(new User("Oshadha Fernando", "oshadhafernando@gmail.com", "oshiii123", "0723456789"));
    }

    public void addUser(User user) {
        lock.writeLock().lock();
        try {
            users.put(user.getEmail(), user);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public User getUser(String email) {
        lock.readLock().lock();
        try {
            return users.get(email);
        } finally {
            lock.readLock().unlock();
        }
    }

    public boolean userExists(String email) {
        lock.readLock().lock();
        try {
            return users.containsKey(email);
        } finally {
            lock.readLock().unlock();
        }
    }
}
