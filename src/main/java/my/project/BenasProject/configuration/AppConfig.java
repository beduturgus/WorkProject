package my.project.BenasProject.configuration;

import my.project.BenasProject.services.CamelContextService;
import org.apache.camel.CamelContext;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;


@Configuration
@PropertySource({"classpath:application.properties"})
//@EnableJpaRepositories(
//        basePackages = "my.project.BenasProject.domain",
//        entityManagerFactoryRef = "contactsInfoEntityManager",
//        transactionManagerRef = "contactsInfoTransactionManager"
//)
public class AppConfig {

    @Autowired
    Environment env;

    @Autowired
    CamelContext camelContext;

    @Bean
    CamelContextService getCamelContextService() throws Exception {
        return new CamelContextService(camelContext);
    }

    @Value("${baeldung.api.path}")
    String contextPath;

    @Bean
    CamelHttpTransportServlet camelHttpTransportServlet(){
        CamelHttpTransportServlet camelServlet = new CamelHttpTransportServlet();
        camelServlet.setServletName("CamelServlet");
        return camelServlet;
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean contactsInfoEntityManager(){
        LocalContainerEntityManagerFactoryBean em =
                new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(contactsInfoDataSource());
        em.setPackagesToScan(new String[] {"my.project.BenasProject.domain"});

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    @Primary
    public DataSource contactsInfoDataSource(){
        DriverManagerDataSource datasource = new DriverManagerDataSource();
        datasource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        datasource.setUrl(env.getProperty("database.url"));
        datasource.setUsername(env.getProperty("db.user"));
        datasource.setPassword(env.getProperty("db.password"));

        return datasource;
    }
    @Primary
    @Bean
    public PlatformTransactionManager contactsInfoTransactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(contactsInfoEntityManager().getObject());
        return transactionManager;

    }
}
