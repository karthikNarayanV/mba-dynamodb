package com.fse.moviebooking.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.context.ShutdownEndpoint;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fse.moviebooking.service.UserCredentialServiceImpl;

@SuppressWarnings("deprecation")

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserCredentialServiceImpl userCredentialService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	@Autowired
	private UnauthorizedEntryPoint unauthorizedEntryPoint;
	
	private static final Logger log=LoggerFactory.getLogger(WebSecurityConfig.class);

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		log.info("Start: Auth Configure");
		super.configure(auth);
		auth.userDetailsService(userCredentialService);
		log.info("End: Auth Configure");
	}	

	@Override
	public void configure(WebSecurity web) throws Exception {
		log.info("Start: Web Configure");
		web.ignoring().antMatchers("/register","/login","/forgot","/validate","/reset","/{movieName}/update/{theatreName}","/{movieName}/delete/{theatreName}");
		
		log.info("End: Web Configure");
	}
	

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.info("Start: Http Configure");
		http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
		http.authorizeRequests().requestMatchers(EndpointRequest.to(ShutdownEndpoint.class)).hasRole("ACTUATOR_ADMIN").requestMatchers(EndpointRequest.toAnyEndpoint())
                .permitAll()
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .permitAll()
            .antMatchers("/")
                .permitAll()
            .antMatchers("/**")
                .authenticated()
        .and()
        .httpBasic();
		http.csrf().disable().authorizeRequests().antMatchers("/login").permitAll().anyRequest().authenticated().and().exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint).and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtRequestFilter,UsernamePasswordAuthenticationFilter.class );
		log.info("Start: End Configure");
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		log.info("In: AuthenticationManagerBean");
		return super.authenticationManagerBean();
	}
	
	@Bean
	public PasswordEncoder encoder() {
		log.info("In: Encoder");
		return NoOpPasswordEncoder.getInstance();
	}
	
	
	
	
	

}
