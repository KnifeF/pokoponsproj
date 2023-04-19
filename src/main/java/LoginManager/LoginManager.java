package LoginManager;

import com.example.pokoponsproj.ClientType;
import facades.AdminFacade;
import facades.ClientFacade;
import facades.CustomerFacade;
import facades.SellerFacade;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class LoginManager {
    private ApplicationContext ctx;

    public LoginManager(ApplicationContext ctx) {
        this.ctx = ctx;
    }


    public ClientFacade login(String email, String password, ClientType clientType) throws RuntimeException, SQLException {

        if (clientType == ClientType.administrator) {
            AdminFacade adminFacade = ctx.getBean(AdminFacade.class);
            if (adminFacade.login(email, password) != -1) {
                System.out.println("Logged in to administrator");
                return adminFacade;
            }
        } else if (clientType == ClientType.seller) {
            SellerFacade sellerFacade = ctx.getBean(SellerFacade.class);
            if (sellerFacade.login(email, password) != -1) {
                System.out.println("Logged in to seller");
                return sellerFacade;
            }
        } else if (clientType == ClientType.customer) {
            CustomerFacade customerFacade = ctx.getBean(CustomerFacade.class);
            if (customerFacade.login(email, password) != -1) {
                System.out.println("Logged in to customer");
                return customerFacade;
            }
        }

        return null;
    }

}
