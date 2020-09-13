package main.security;

import main.security.jwt.JwtSecurityConfigurer;
import main.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/auth/signin").permitAll()
                .antMatchers("/auth/signup").permitAll()
                .antMatchers(HttpMethod.POST, "/goods").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/goods/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/goods/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/sales/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/warehouse1").hasRole("ADMIN")
                .antMatchers("/warehouse1/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/warehouse2").hasRole("ADMIN")
                .antMatchers("/warehouse2/{id}").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtSecurityConfigurer(jwtTokenProvider));
    }
}
