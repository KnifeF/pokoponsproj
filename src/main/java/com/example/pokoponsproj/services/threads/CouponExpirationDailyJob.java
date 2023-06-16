package com.example.pokoponsproj.services.threads;

import com.example.pokoponsproj.beans.Coupon;
import com.example.pokoponsproj.beans.Customer;
import com.example.pokoponsproj.repositories.CouponRepository;
import com.example.pokoponsproj.repositories.CustomerRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

/**
 * Service to remove expired coupons
 */
@Service
public class CouponExpirationDailyJob implements Runnable{

    private CouponRepository couponRepo;
    private CustomerRepository customerRepo;

    public boolean running = true;

    /**
     * CouponExpirationDailyJob constructor
     * @param couponRepo component for coupon in the DAO layer
     * @param customerRepo component for customer in the DAO layer
     */
    public CouponExpirationDailyJob(CouponRepository couponRepo, CustomerRepository customerRepo) {
        this.couponRepo = couponRepo;
        this.customerRepo = customerRepo;
    }

    // https://www.baeldung.com/spring-scheduled-tasks
    //schedule a task at a fixed interval of time, and with a delay

    /**
     * delete expired coupons logic
     */
//    @Scheduled(fixedRate = 24 * 60 * 60 * 1000, initialDelay = 20 * 1000)
//    public void delExpiredCoupons() {
//        Date currDate = new Date(System.currentTimeMillis());
//
//        List<Coupon> coupons = couponRepo.findAll();
//        List<Customer> customers = customerRepo.findAll();
//
//        for (Coupon coupon: coupons) {
//            if (coupon.getEndDate().before(currDate)){
//                for (Customer customer : customers) {
//                    List<Coupon> customerCoupons = customer.getCoupons();
//
//                    // remove elements from the coupons list if they have the id of the expired coupon
//                    // https://www.techiedelight.com/remove-all-occurrences-element-from-list-java/
//                    customerCoupons.removeIf(coup -> coup.getIdPokopon() == coupon.getIdPokopon());
//
//                    // update customer after deleting his expired coupons from the list
//                    customerRepo.save(customer);
//                }
//
//                // remove coupon purchase history
//                couponRepo.deleteCouponPurchaseByCouponId(coupon.getIdPokopon());
//                // remove the coupon
//                couponRepo.deleteById(coupon.getIdPokopon());
//                System.out.println("deleted coupon with id: " + coupon.getIdPokopon());
//            }
//
//        }
//    }

    @Override
    public void run() {
        while (running){
            Date currDate = new Date(System.currentTimeMillis());

            List<Coupon> coupons = couponRepo.findAll();
            List<Customer> customers = customerRepo.findAll();

            for (Coupon coupon: coupons) {
                if (coupon.getEndDate().before(currDate)){
                    for (Customer customer : customers) {
                        List<Coupon> customerCoupons = customer.getCoupons();

                        // remove elements from the coupons list if they have the id of the expired coupon
                        // https://www.techiedelight.com/remove-all-occurrences-element-from-list-java/
                        customerCoupons.removeIf(coup -> coup.getIdPokopon() == coupon.getIdPokopon());

                        // update customer after deleting his expired coupons from the list
                        customerRepo.save(customer);
                    }

                    // remove coupon purchase history
                    couponRepo.deleteCouponPurchaseByCouponId(coupon.getIdPokopon());
                    // remove the coupon
                    couponRepo.deleteById(coupon.getIdPokopon());
                    System.out.println("deleted coupon with id: " + coupon.getIdPokopon());
                }

            }

            //    @Scheduled(fixedRate = 24 * 60 * 60 * 1000, initialDelay = 20 * 1000)
            try { Thread.sleep(24 * 60 * 60 * 1000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

        }

    }
}
