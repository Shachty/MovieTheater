package cinema.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@Configuration
public class JPAConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean emf(){
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setPersistenceXmlLocation("classpath:META-INF/persistence.xml");
        emf.setPersistenceProviderClass(org.hibernate.ejb.HibernatePersistence.class);
        emf.setPackagesToScan("cinema.jpa");
        emf.setPersistenceUnitName("default");
        return emf;
    }
}
