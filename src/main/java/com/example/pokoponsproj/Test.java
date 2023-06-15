package com.example.pokoponsproj;

import com.example.pokoponsproj.services.auth.LoginManager.LoginManager;
import com.example.pokoponsproj.beans.Coupon;
import com.example.pokoponsproj.beans.Seller;
import com.example.pokoponsproj.enums.Types;
import com.example.pokoponsproj.enums.ClientType;
import com.example.pokoponsproj.services.facades.SellerFacade;
import com.example.pokoponsproj.repositories.CouponRepository;
import com.example.pokoponsproj.services.facades.AdminFacade;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
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
            // login with administrator and call the admin methods (business logic)
            // ********************************************************************
            AdminFacade adminFacade = (AdminFacade) loginManager.login("admin@admin.com", "admin", ClientType.administrator);

            // admin methods on customers
            // adding a customer
//            adminFacade.addCustomer(new Customer("Nir", "Shuk", "niros@example.com", "nironir"));
//            adminFacade.addCustomer(new Customer("Yinon", "Les", "yinonos@example.com", "yinyang"));

            // get the customers
//            System.out.println(adminFacade.getAllCustomers());

            // delete customer by his id
//            adminFacade.deleteCustomer(1);

            // get one customer by his id
//            System.out.println(adminFacade.getOneCustomer(2));

            // update a customer (change customer password)
//            Customer tempCustomer = adminFacade.getOneCustomer(2);
//            tempCustomer.setPassword("blah");
//            adminFacade.updateCustomer(tempCustomer);
//            System.out.println(adminFacade.getAllCustomers());


            // admin methods on sellers
            // add a seller

            //            adminFacade.addSeller(new Seller("Best seller on earth", "thebest@example.com",
            //                    "thebestpasswordintheworld!"));
            Seller tempSeller = new Seller("second Best seller", "pokoponer@example.com",
                    "password");

            adminFacade.addSeller(tempSeller);


            // get the sellers
            //            System.out.println(adminFacade.getAllSellers());

            // get one seller
            //            System.out.println(adminFacade.getOneSeller(1));


            // update a seller
            // tempSeller.setEmail("blabiz@example.com");
            // adminFacade.updateSeller(tempSeller);

            // delete seller
            //            adminFacade.deleteSeller(1);

            // login with seller
            SellerFacade sellerFacade = (SellerFacade) loginManager.login("pokoponer@example.com", "password", ClientType.seller);
            sellerFacade.addCoupon(new Coupon("pika", Types.Electric, 10, 1050.9, "pika.. pika.. chu!!!", Date.valueOf("2023-04-20"), Date.valueOf("2023-04-21"), null, tempSeller));



            // login with customer
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

}
