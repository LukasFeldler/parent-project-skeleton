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
                .roles("PRODUCTIONMANAGER")
                .build();

        return new InMemoryUserDetailsManager(worker, manager);
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .mvcMatchers(HttpMethod.GET,"/orders").authenticated()
                .mvcMatchers(HttpMethod.GET,"/sortedOrders").authenticated()
                .mvcMatchers(HttpMethod.POST,"/newOrderFirst").hasRole("PRODUCTIONMANAGER")
                .mvcMatchers(HttpMethod.POST,"/newOrderLast").hasRole("PRODUCTIONMANAGER")
                .mvcMatchers(HttpMethod.POST,"/newOrderAt").hasRole("PRODUCTIONMANAGER")
                .mvcMatchers(HttpMethod.PUT,"/changeOrderDescription").authenticated()
                .mvcMatchers(HttpMethod.PUT,"/changeOrderPriority").authenticated()
                .mvcMatchers(HttpMethod.DELETE,"/deleteOrders").hasRole("PRODUCTIONMANAGER")
                .mvcMatchers(HttpMethod.DELETE,"/deleteAllOrder").hasRole("PRODUCTIONMANAGER")
        ).httpBasic(Customizer.withDefaults());
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.csrf().disable();
        return http.build();
    }
}
