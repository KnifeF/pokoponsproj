package com.example.pokoponsproj.services.threads;

import com.example.pokoponsproj.beans.Coupon;
import com.example.pokoponsproj.beans.Customer;
import com.example.pokoponsproj.repositories.CouponRepository;
import com.example.pokoponsproj.repositories.CustomerRepository;
import com.example.pokoponsproj.services.auth.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SessionExpirationJob implements Runnable {
    private Map<String, Session> sessions;
    public boolean running = true;

    public SessionExpirationJob() {
        sessions = new HashMap<String, Session>();
    }

    public SessionExpirationJob(Map<String, Session> sessions) {
        this.sessions = sessions;
    }


//    @Scheduled(initialDelay = 60 * 1000)
//    public void delExpiredSessions() {
//        long currTime = System.currentTimeMillis();
//
//        sessions.values().removeIf(session -> session.getLastActivity() + 30 * 60 * 1000 <= currTime);
//    }

    /**
     * delete expired sessions logic
     */
    @Override
    public void run() {
        while (running){
            long currTime = System.currentTimeMillis();
            sessions.values().removeIf(session -> session.getLastActivity() + 30 * 60 * 1000 <= currTime);

            try {
                Thread.sleep(60*1000);
            } catch (InterruptedException e){
                System.out.println(e.getMessage());
            }
        }

    }
}

