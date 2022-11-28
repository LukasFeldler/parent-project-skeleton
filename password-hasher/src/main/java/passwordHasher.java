import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class passwordHasher {
    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        String workerPW = encoder.encode("worker");
        String productionManagerPW = encoder.encode("productionManager");
        System.out.println(workerPW);
        System.out.println(productionManagerPW);

        UserDetails worker = User.builder().username("worker")
                .password("{bcrypt}" + )
                .roles("Worker").build();

        UserDetails productionManager = User.builder().username("productionManager")
                .password("{bcrypt}" + )
                .roles("ProductionManager").build();

        return new InMemoryUserDetailsManager(worker, productionManager);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .mvcMatchers(HttpMethod.GET,"/orders").permitAll()
                .mvcMatchers(HttpMethod.GET,"/sortedOrders").permitAll()
                .mvcMatchers(HttpMethod.POST,"/newOrderFirst").hasRole("ProductionManager")
                .mvcMatchers(HttpMethod.POST,"/newOrderLast").hasRole("ProductionManager")
                .mvcMatchers(HttpMethod.POST,"/newOrderAt").hasRole("ProductionManager")
                .mvcMatchers(HttpMethod.PUT,"/changeOrderDescription").permitAll()
                .mvcMatchers(HttpMethod.PUT,"/changeOrderPriority").permitAll()
                .mvcMatchers(HttpMethod.DELETE,"/deleteOrders").hasRole("ProductionManager")
                .mvcMatchers(HttpMethod.DELETE,"/deleteAllOrder").hasRole("ProductionManager")
        ).httpBasic(Customizer.withDefaults());
        http.csrf().disable();
        return http.build();
    }
}
