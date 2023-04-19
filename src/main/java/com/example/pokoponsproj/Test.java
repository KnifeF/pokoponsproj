package com.example.pokoponsproj;

import LoginManager.LoginManager;
import com.example.pokoponsproj.repositories.CouponRepository;
import facades.AdminFacade;
import org.springframework.context.ApplicationContext;

import java.sql.SQLException;

public class Test {

    private ApplicationContext ctx;
    private LoginManager loginManager;
    private CouponRepository couponRepo;

    public Test(ApplicationContext ctx, LoginManager loginManager, CouponRepository couponRepo) {
        this.ctx = ctx;
        this.loginManager = loginManager;
        this.couponRepo = couponRepo;
    }

    public void pokoponSysTest() {

        try {
            // login with administrator
            AdminFacade adminFacade = (AdminFacade) loginManager.login("admin@admin.com", "admin", ClientType.administrator);


            // login with seller

            // login with customer
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

}
