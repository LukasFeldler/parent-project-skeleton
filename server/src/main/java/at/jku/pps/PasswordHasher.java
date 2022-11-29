package at.jku.pps;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class PasswordHasher {

    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        String workerPW = encoder.encode("worker");
        String productionManagerPW = encoder.encode("productionManager");
        System.out.println(workerPW);
        System.out.println(productionManagerPW);

        UserDetails worker = User.builder().username("worker")
                .password("{bcrypt}" + workerPW)
                .roles("Worker").build();

        UserDetails productionManager = User.builder().username("productionManager")
                .password("{bcrypt}" + productionManagerPW)
                .roles("ProductionManager").build();

        return new InMemoryUserDetailsManager(worker, productionManager);
    }
}
