package facades;

import com.example.pokoponsproj.beans.Coupon;
import com.example.pokoponsproj.beans.Customer;
import com.example.pokoponsproj.beans.Seller;
import com.example.pokoponsproj.repositories.CouponRepository;
import com.example.pokoponsproj.repositories.CustomerRepository;
import com.example.pokoponsproj.repositories.SellerRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Scope("Prototype")
public class AdminFacade extends ClientFacade {
    public AdminFacade(CustomerRepository customerRepo, SellerRepository sellerRepo, CouponRepository couponRepo) {
        super(customerRepo, sellerRepo, couponRepo);
    }

    // add custom exception
    @Override
    public int login(String email, String password) throws SQLException, RuntimeException {
        if (email.equals("admin@admin.com") && password.equals("admin")) {
            System.out.println("Login Success!");
            return 1;
        }
//        throw new CustomException("login failed");
        throw new RuntimeException("failed login");
    }


    public void addSeller(Seller seller) throws SQLException, RuntimeException {
        //check if seller exist
        if (sellerRepo.existsByEmail(seller.getEmail()) || sellerRepo.existsByName(seller.getName())) {
//        if(sellerRepo.isSellerExists(seller.getEmail(), seller.getPassword()) == -1){
            throw new RuntimeException("seller is already exist");
        }
        sellerRepo.save(seller);
    }

    public void updateSeller(Seller seller) throws SQLException, RuntimeException {
        //        if (sellerRepo.existsById(seller.getIdSeller())) {
        Seller oldSeller = sellerRepo.findById(seller.getIdSeller()).orElseThrow(() ->
                new RuntimeException("seller not found, you need existing seller in order to update"));
        if (oldSeller.getName().equals(seller.getName())) {
            sellerRepo.save(seller);
        } else throw new RuntimeException("Replacing a seller name is not allowed");
    }

    public void deleteSeller(int idSeller) throws SQLException, RuntimeException {
        Seller currSeller = sellerRepo.findById(idSeller).orElseThrow(() -> new RuntimeException("seller not found"));

        List<Coupon> sellerCoupons = currSeller.getCoupons();

        if(!sellerCoupons.isEmpty()) {
            // deleting purchase history of this seller's coupons
            sellerCoupons.stream().forEach(coupon -> couponRepo.deleteCouponPurchaseByCouponId(coupon.getIdPokopon()));
            // sellerCoupons.forEach(coupon -> couponRepo.deleteCouponPurchaseByCouponId(coupon.getIdPokopon()));
        }

        // update seller to be without coupons
        currSeller.setCoupons(null);
        sellerRepo.save(currSeller);

        // delete seller
        sellerRepo.deleteById(idSeller);
    }

    public ArrayList<Seller> getAllSellers() throws SQLException {
        List<Seller> sellerList = sellerRepo.findAll();
        return (ArrayList<Seller>) sellerList;
    }

    public Seller getOneSeller(int sellerId) throws SQLException, RuntimeException {
        return sellerRepo.findById(sellerId).orElseThrow(() -> new RuntimeException("seller not found"));
    }

    public void addCustomer(Customer customer) throws SQLException, RuntimeException {
        //check if customer exist
        if (customerRepo.existsByEmail(customer.getEmail())) {
//        if(sellerRepo.isSellerExists(seller.getEmail(), seller.getPassword()) == -1){
            throw new RuntimeException("customer is already exist");
        }
        customerRepo.save(customer);
    }

    public void updateCustomer(Customer customer) throws SQLException, RuntimeException {
        if (customerRepo.existsById(customer.getIdCustomer())) {
            customerRepo.save(customer);
        } else throw new RuntimeException("customer not found, you need existing customer in order to update");
    }

    // Ask Nir
    public void deleteCustomer(int idCustomer) throws SQLException, RuntimeException {

        // deleting related coupons for deleted customer, and update customer record
        Customer currCustomer = customerRepo.findById(idCustomer).orElseThrow(() -> new RuntimeException("customer not found"));

        List<Coupon> customerCoupons = currCustomer.getCoupons();
        if(!customerCoupons.isEmpty()) {
            // deleting purchase history of this customer coupons
            customerCoupons.stream().forEach(coupon -> couponRepo.deleteCouponPurchaseByCouponId(coupon.getIdPokopon()));
            // sellerCoupons.forEach(coupon -> couponRepo.deleteCouponPurchaseByCouponId(coupon.getIdPokopon()));
        }


        // update customer to be without coupons
        currCustomer.setCoupons(null);
        customerRepo.save(currCustomer);

        // delete customer
        customerRepo.deleteById(idCustomer);

    }

    public ArrayList<Customer> getAllCustomers() throws SQLException {
        List<Customer> customerList = customerRepo.findAll();
        return (ArrayList<Customer>) customerList;
    }

    public Customer getOneCustomer(int idCustomer) throws SQLException, RuntimeException {
        return customerRepo.findById(idCustomer).orElseThrow(() -> new RuntimeException("customer not found"));
    }


}
