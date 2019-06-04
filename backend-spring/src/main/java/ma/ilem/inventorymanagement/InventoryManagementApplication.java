package ma.ilem.inventorymanagement;

import ma.ilem.inventorymanagement.entity.User;
import ma.ilem.inventorymanagement.service.PrivilegeService;
import ma.ilem.inventorymanagement.service.RoleService;
import ma.ilem.inventorymanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
public class InventoryManagementApplication extends SpringBootServletInitializer implements CommandLineRunner {

    @Autowired
    private PrivilegeService authorityService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Value("${admin.email}")
    private String username;

    @Value("${admin.password}")
    private String password;

    @Value("${admin.first_name}")
    private String firstName;

    @Value("${admin.last_name}")
    private String lastName;

    @Value("${admin.address}")
    private String address;

    @Value("${admin.function}")
    private String function;


    public static void main(String[] args) {
        SpringApplication.run(InventoryManagementApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(InventoryManagementApplication.class);
    }

    @Override
    public void run(String... args) {
        authorityService.init();
        roleService.init();
        init();
    }

    private void init() {
        User admin = userService.findByEmail(username);

        if (admin == null) {
            admin = new User();
            admin.setFirstName(firstName);
            admin.setLastName(lastName);
            admin.setEmail(username);
            admin.setPassword(password);
            admin.setAddress(address);
            admin.setFunction(function);
            admin.getRoles().add(roleService.findByName("ADMIN"));

            userService.save(admin);
        }
    }
}
