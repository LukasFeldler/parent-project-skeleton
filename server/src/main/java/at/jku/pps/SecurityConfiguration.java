package at.jku.pps;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public InMemoryUserDetailsManager userDetailsService (){

        UserDetails worker = User.builder()
                .username("worker")
                .password("{bcrypt}" + "$2a$16$c6HKq6qdo00ljQGgYDI5BOzGVRn1R3UpEFy7Bav/eqLk/nUfNMu7q")
                .roles("WORKER")
                .build();

        UserDetails manager = User.builder()
                .username("manager")
                .password("{bcrypt}" + "$2a$16$jlSfPHQCMYsC40mfeibi9eK0N7QTHivVbLBy4a66qyYJaPCImW9j6")
                .roles("MANAGER")
                .build();

        return new InMemoryUserDetailsManager(worker, manager);
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .mvcMatchers(HttpMethod.GET,"/orders").permitAll()
                .mvcMatchers(HttpMethod.GET,"/sortedOrders").permitAll()
                .mvcMatchers(HttpMethod.POST,"/newOrderFirst").hasRole("MANAGER")
                .mvcMatchers(HttpMethod.POST,"/newOrderLast").hasRole("MANAGER")
                .mvcMatchers(HttpMethod.POST,"/newOrderAt").hasRole("MANAGER")
                .mvcMatchers(HttpMethod.PUT,"/changeOrderDescription").permitAll()
                .mvcMatchers(HttpMethod.PUT,"/changeOrderPriority").permitAll()
                .mvcMatchers(HttpMethod.DELETE,"/deleteOrders").hasRole("MANAGER")
                .mvcMatchers(HttpMethod.DELETE,"/deleteAllOrder").hasRole("MANAGER")
        ).httpBasic(Customizer.withDefaults());
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.csrf().disable();
        return http.build();
    }
}
