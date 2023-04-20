package com.example.pokoponsproj;

import com.example.pokoponsproj.LoginManager.LoginManager;
import com.example.pokoponsproj.beans.Customer;
import com.example.pokoponsproj.beans.Seller;
import com.example.pokoponsproj.repositories.CouponRepository;
import com.example.pokoponsproj.facades.AdminFacade;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

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
            AdminFacade adminFacade = (AdminFacade) loginManager.login("admin@admin.com", "admin", ClientType.administrator);

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



            adminFacade.addSeller(new Seller("Best seller on earth", "thebest@example.com",
                    "thebestpasswordintheworld!"));



            // login with seller

            // login with customer
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

}
