package at.jku.pps;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .mvcMatchers(HttpMethod.GET,"/orders").authenticated()
                .mvcMatchers(HttpMethod.GET,"/sortedOrders").authenticated()
                .mvcMatchers(HttpMethod.POST,"/newOrderFirst").hasRole("ProductionManager")
                .mvcMatchers(HttpMethod.POST,"/newOrderLast").hasRole("ProductionManager")
                .mvcMatchers(HttpMethod.POST,"/newOrderAt").hasRole("ProductionManager")
                .mvcMatchers(HttpMethod.PUT,"/changeOrderDescription").authenticated()
                .mvcMatchers(HttpMethod.PUT,"/changeOrderPriority").authenticated()
                .mvcMatchers(HttpMethod.DELETE,"/deleteOrders").hasRole("ProductionManager")
                .mvcMatchers(HttpMethod.DELETE,"/deleteAllOrder").hasRole("ProductionManager")
        ).httpBasic(Customizer.withDefaults());
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.csrf().disable();
        return http.build();
    }
}
