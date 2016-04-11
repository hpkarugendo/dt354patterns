package ie.dit.dt354patters.configs;

import static com.stormpath.spring.config.StormpathWebSecurityConfigurer.stormpath;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebScurityConfig extends WebSecurityConfigurerAdapter{
    @Override
    protected void configure(HttpSecurity http) throws Exception {
	http
        .apply(stormpath()).and()
        .authorizeRequests()
        .antMatchers("/").permitAll();
    }
    
}
