package facades;

import com.example.pokoponsproj.beans.Coupon;
import com.example.pokoponsproj.beans.Seller;
import com.example.pokoponsproj.beans.Types;
import com.example.pokoponsproj.repositories.CouponRepository;
import com.example.pokoponsproj.repositories.CustomerRepository;
import com.example.pokoponsproj.repositories.SellerRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@Scope("Prototype")
public class SellerFacade extends ClientFacade {

    private int sellerId;

    public SellerFacade(CustomerRepository customerRepo, SellerRepository sellerRepo, CouponRepository couponRepo) {
        super(customerRepo, sellerRepo, couponRepo);
    }

    @Override
    public int login(String email, String password) throws SQLException {
        Seller currSeller = sellerRepo.findByEmailAndPassword(email, password);
        if (currSeller != null) {
            sellerId = currSeller.getIdSeller();
            return sellerId;
        }
        return -1;
    }


    public void addCoupon(Coupon coup) throws SQLException, RuntimeException {
        ArrayList<Coupon> sellerCoupons = getSellerCoupons();
        // https://howtodoinjava.com/java8/stream-anymatch-example/
        if (sellerCoupons.stream()
                .anyMatch(coupon -> coupon.getPokemonName().equals(coup.getPokemonName()))) {
            throw new RuntimeException("this pokopon name already exist");
        }
//        sellerCoupons.stream().forEach(coupon -> coupon.getPokemonName().equals(coup.getPokemonName()));
        couponRepo.save(coup);
    }

    public void updateCoupon(Coupon coup) {
        if (couponRepo.existsById(coup.getIdPokopon()))
            couponRepo.save(coup);
    }

    public void deleteCoupon(int idPokopon) {
        couponRepo.deleteById(idPokopon);
    }

    public Coupon getOneCoupon(int idPokopon) {
        return couponRepo.findById(idPokopon).orElseThrow();
    }

    public List<Coupon> getAllCoupons() {
        return couponRepo.findAll();
    }


    public ArrayList<Coupon> getSellerCoupons() throws SQLException, RuntimeException {
        Seller seller = sellerRepo.findById(sellerId).orElseThrow(() -> new RuntimeException("seller not exist"));
        List<Coupon> sellerCoupons = seller.getCoupons();
        if (sellerCoupons == null) throw new RuntimeException("seller without coupons");
        return (ArrayList<Coupon>) sellerCoupons;
    }

    public ArrayList<Coupon> getSellerCoupons(Types type) throws SQLException, RuntimeException {
        Seller seller = sellerRepo.findById(sellerId).orElseThrow(() -> new RuntimeException("seller not exist"));
        List<Coupon> sellerCoupons = seller.getCoupons();
        if (sellerCoupons == null) throw new RuntimeException("seller without coupons");


        ArrayList<Coupon> sameTypeCoupons = new ArrayList<>();
        for (Coupon coup : sellerCoupons) {
            if (coup.getIdType().equals(type)) {
                sameTypeCoupons.add(coup);
            }
        }
        return sameTypeCoupons;
    }

    public ArrayList<Coupon> getSellerCoupons(double maxPrice) throws SQLException, RuntimeException {
        Seller seller = sellerRepo.findById(sellerId).orElseThrow(() -> new RuntimeException("seller not exist"));
        List<Coupon> sellerCoupons = seller.getCoupons();
        if (sellerCoupons == null) throw new RuntimeException("seller without coupons");

        ArrayList<Coupon> belowMaxCoupons = new ArrayList<>();
        for (Coupon coup : sellerCoupons) {
            if (coup.getPrice() <= maxPrice) {
                belowMaxCoupons.add(coup);
            }
        }
        return belowMaxCoupons;
    }

    private Seller getSellerDetails() throws SQLException, RuntimeException {
        return sellerRepo.findById(sellerId).orElseThrow(() -> new RuntimeException("seller not exist"));
    }
}
