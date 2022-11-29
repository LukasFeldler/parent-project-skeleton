import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


public class PasswordHasher {

    public static void main(String[] args){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        String workerPW = encoder.encode("worker");
        String productionManagerPW = encoder.encode("productionManager");

        System.out.println(workerPW);
        System.out.println(productionManagerPW);
    }
}
