package ma.ilem.inventorymanagement.configuration;

import ma.ilem.inventorymanagement.security.JwtAuthenticationFilter;
import ma.ilem.inventorymanagement.security.JwtAuthorizationFilter;
import ma.ilem.inventorymanagement.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Resource(name = "userService")
    private UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfiguration(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .cors().disable()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/static/**").permitAll()
                /*.antMatchers(HttpMethod.GET, "/api/categories").hasAnyAuthority(PrivilegeName.SHOW_CATEGORIES.name(), PrivilegeName.ALL_PRIVILEGES.name())
                .antMatchers(HttpMethod.GET, "/api/categories/{\\d+}").hasAnyAuthority(PrivilegeName.SHOW_CATEGORIES.name(), PrivilegeName.ALL_PRIVILEGES.name())
                .antMatchers(HttpMethod.POST, "/api/categories").hasAnyAuthority(PrivilegeName.ADD_CATEGORY.name(), PrivilegeName.ALL_PRIVILEGES.name())
                .antMatchers(HttpMethod.PUT, "/api/categories/{\\d+}").hasAnyAuthority(PrivilegeName.UPDATE_CATEGORY.name(), PrivilegeName.ALL_PRIVILEGES.name())
                .antMatchers(HttpMethod.DELETE, "/api/categories/{\\d+}").hasAnyAuthority(PrivilegeName.DELETE_CATEGORY.name(), PrivilegeName.ALL_PRIVILEGES.name())
                .antMatchers(HttpMethod.GET, "/api/items").hasAnyAuthority(PrivilegeName.SHOW_ITEMS.name(), PrivilegeName.ALL_PRIVILEGES.name())
                .antMatchers(HttpMethod.GET, "/api/items/{\\d+}").hasAnyAuthority(PrivilegeName.SHOW_ITEMS.name(), PrivilegeName.ALL_PRIVILEGES.name())
                .antMatchers(HttpMethod.POST, "/api/items").hasAnyAuthority(PrivilegeName.ADD_ITEM.name(), PrivilegeName.ALL_PRIVILEGES.name())
                .antMatchers(HttpMethod.PUT, "/api/items/{\\d+}").hasAnyAuthority(PrivilegeName.UPDATE_ITEM.name(), PrivilegeName.ALL_PRIVILEGES.name())
                .antMatchers(HttpMethod.DELETE, "/api/items/{\\d+}").hasAnyAuthority(PrivilegeName.DELETE_ITEM.name(), PrivilegeName.ALL_PRIVILEGES.name())
                .antMatchers(HttpMethod.GET, "/api/users").hasAnyAuthority(PrivilegeName.SHOW_USERS.name(), PrivilegeName.ALL_PRIVILEGES.name())
                .antMatchers(HttpMethod.GET, "/api/users/{\\d+}").hasAnyAuthority(PrivilegeName.SHOW_USERS.name(), PrivilegeName.ALL_PRIVILEGES.name())
                .antMatchers(HttpMethod.POST, "/api/users").hasAnyAuthority(PrivilegeName.ADD_USER.name(), PrivilegeName.ALL_PRIVILEGES.name())
                .antMatchers(HttpMethod.PUT, "/api/users/{\\d+}").hasAnyAuthority(PrivilegeName.UPDATE_USER.name(), PrivilegeName.ALL_PRIVILEGES.name())
                .antMatchers(HttpMethod.DELETE, "/api/users/{\\d+}").hasAnyAuthority(PrivilegeName.DELETE_USER.name(), PrivilegeName.ALL_PRIVILEGES.name())
                .antMatchers(HttpMethod.PUT, "/api/users/{\\d+}/roles").hasAnyAuthority(PrivilegeName.UPDATE_USER_ROLE.name(), PrivilegeName.ALL_PRIVILEGES.name())
                .antMatchers(HttpMethod.GET, "/api/roles").hasAnyAuthority(PrivilegeName.SHOW_ROLES.name(), PrivilegeName.ALL_PRIVILEGES.name())
                .antMatchers(HttpMethod.POST, "/api/roles").hasAnyAuthority(PrivilegeName.ADD_ROLE.name(), PrivilegeName.ALL_PRIVILEGES.name())
                .antMatchers(HttpMethod.PUT, "/api/roles/{\\d+}").hasAnyAuthority(PrivilegeName.UPDATE_ROLE.name(), PrivilegeName.ALL_PRIVILEGES.name())
                .antMatchers(HttpMethod.DELETE, "/api/roles/{\\d+}").hasAnyAuthority(PrivilegeName.DELETE_ROLE.name(), PrivilegeName.ALL_PRIVILEGES.name())
                .antMatchers(HttpMethod.GET, "/api/privileges").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/privileges/{\\d+}").hasAnyAuthority(PrivilegeName.UPDATE_PRIVILEGE.name(), PrivilegeName.ALL_PRIVILEGES.name())
                .antMatchers(HttpMethod.PUT, "/api/privileges/{\\d+}/enable").hasAnyAuthority(PrivilegeName.ENABLE_PRIVILEGE.name(), PrivilegeName.ALL_PRIVILEGES.name())
                .antMatchers(HttpMethod.PUT, "/api/privileges/{\\d+}/disable").hasAnyAuthority(PrivilegeName.DISABLE_PRIVILEGE.name(), PrivilegeName.ALL_PRIVILEGES.name())
                .antMatchers(HttpMethod.GET, "/api/profile").hasAnyAuthority(PrivilegeName.SHOW_PROFILE.name(), PrivilegeName.ALL_PRIVILEGES.name())
                .antMatchers(HttpMethod.POST, "/api/profile/change_password").hasAnyAuthority(PrivilegeName.UPDATE_PASSWORD.name(), PrivilegeName.ALL_PRIVILEGES.name())
                .antMatchers(HttpMethod.POST, "/api/profile/change_image").hasAnyAuthority(PrivilegeName.UPDATE_PROFILE_IMAGE.name(), PrivilegeName.ALL_PRIVILEGES.name())
                .antMatchers(HttpMethod.GET, "/api/users_items").hasAnyAuthority(PrivilegeName.SHOW_USER_ITEMS.name(), PrivilegeName.ALL_PRIVILEGES.name())
                .antMatchers(HttpMethod.POST, "/api/users_items").hasAnyAuthority(PrivilegeName.REQUEST_ITEM.name(), PrivilegeName.ALL_PRIVILEGES.name())
                .antMatchers(HttpMethod.PUT, "/api/users_items/{\\d+}/accept").hasAnyAuthority(PrivilegeName.ACCEPT_ITEM.name(), PrivilegeName.ALL_PRIVILEGES.name())
                .antMatchers(HttpMethod.PUT, "/api/users_items/{\\d+}/refuse").hasAnyAuthority(PrivilegeName.REFUSE_ITEM.name(), PrivilegeName.ALL_PRIVILEGES.name())
                .antMatchers(HttpMethod.PUT, "/api/users_items/{\\d+}/deliver").hasAnyAuthority(PrivilegeName.DELIVER_ITEM.name(), PrivilegeName.ALL_PRIVILEGES.name())
                .antMatchers(HttpMethod.PUT, "/api/users_items/{\\d+}/returning").hasAnyAuthority(PrivilegeName.RETURN_ITEM.name(), PrivilegeName.ALL_PRIVILEGES.name())
                .antMatchers(HttpMethod.PUT, "/api/users_items/{\\d+}/return").hasAnyAuthority(PrivilegeName.TAKE_ITEM.name(), PrivilegeName.ALL_PRIVILEGES.name())*/
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll();
        //.anyRequest().denyAll();

        http.addFilter(new JwtAuthenticationFilter(authenticationManagerBean(), jwtUtil()));
        http.addFilterBefore(new JwtAuthorizationFilter(jwtUtil()), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }

    @Override
    @Bean(name = "myAuthManager")
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
