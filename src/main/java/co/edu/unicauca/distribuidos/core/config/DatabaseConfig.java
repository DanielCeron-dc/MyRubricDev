package co.edu.unicauca.distribuidos.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Configuración de base de datos para la aplicación
 * Configura DataSource, EntityManagerFactory, y TransactionManager
 * Las anotaciones de escaneo de entidades y repositorios se manejan en la clase principal de la aplicación
 */
@Configuration
public class DatabaseConfig {

    /**
     * Configura la fuente de datos para la aplicación
     * 
     * @return DataSource configurado para H2
     */
    @Bean
    @Description("Configures H2 embedded database with schema and data initialization")
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
    }
    
    /**
     * Configura el entity manager factory con todos los paquetes de entidades
     * 
     * @return Entity Manager Factory configurado
     */
    @Bean
    @Description("Configures EntityManagerFactory with Hibernate properties")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);
        vendorAdapter.setShowSql(true);
        
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan(
            "co.edu.unicauca.distribuidos.core.usuarios.accesoADatos.modelos",
            "co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos",
                "co.edu.unicauca.distribuidos.core.programa.accesoADatos.modelos",
                "co.edu.unicauca.distribuidos.core.rubrica.dominio"
        );
        factory.setDataSource(dataSource());
        
        Properties jpaProperties = new Properties();
        // Dialect configuration
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        
        // SQL logging
        jpaProperties.put("hibernate.show_sql", "true");
        jpaProperties.put("hibernate.format_sql", "true");
        jpaProperties.put("hibernate.use_sql_comments", "true");
        
        // Schema generation
        jpaProperties.put("hibernate.hbm2ddl.auto", "update");
        jpaProperties.put("hibernate.globally_quoted_identifiers", "true");
        
        // Performance settings
        jpaProperties.put("hibernate.jdbc.batch_size", "50");
        jpaProperties.put("hibernate.order_inserts", "true");
        jpaProperties.put("hibernate.order_updates", "true");
        jpaProperties.put("hibernate.jdbc.batch_versioned_data", "true");
        
        // Connection pool settings
        jpaProperties.put("hibernate.connection.provider_class", "org.hibernate.hikaricp.internal.HikariCPConnectionProvider");
        jpaProperties.put("hibernate.hikari.minimumIdle", "5");
        jpaProperties.put("hibernate.hikari.maximumPoolSize", "20");
        jpaProperties.put("hibernate.hikari.idleTimeout", "30000");
        
        // Second-level cache settings
        jpaProperties.put("hibernate.cache.use_second_level_cache", "true");
        jpaProperties.put("hibernate.cache.region.factory_class", "org.hibernate.cache.jcache.JCacheRegionFactory");
        jpaProperties.put("hibernate.javax.cache.provider", "org.ehcache.jsr107.EhcacheCachingProvider");
        
        // H2 specific settings
        jpaProperties.put("hibernate.h2.console.enabled", "true");
        jpaProperties.put("hibernate.h2.console.path", "/h2-console");
        
        factory.setJpaProperties(jpaProperties);
        
        return factory;
    }
    
    /**
     * Configura el gestor de transacciones para JPA
     * 
     * @param entityManagerFactory Factory para la creación de EntityManagers
     * @return Transaction Manager configurado
     */
    @Bean
    @Description("Configures the transaction manager")
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }
}

