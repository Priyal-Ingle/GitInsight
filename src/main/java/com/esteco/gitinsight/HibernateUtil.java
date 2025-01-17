package com.esteco.gitinsight;

import com.esteco.gitinsight.model.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;
    private static final Configuration configuration = new Configuration();

    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml)
            // config file.
            InputStream appPropertiesUrl = HibernateUtil.class.getResourceAsStream("/application.properties");
            Properties properties = new Properties();
            properties.load(appPropertiesUrl);
            configuration.setProperty("jakarta.persistence.jdbc.url", properties.getProperty("spring.datasource.url"));
            configuration.setProperty("spring.datasource.driverClassName", properties.getProperty("spring.datasource.driverClassName"));
            configuration.setProperty("jakarta.persistence.jdbc.user", properties.getProperty("spring.datasource.username"));
            configuration.setProperty("jakarta.persistence.jdbc.password", properties.getProperty("spring.datasource.password"));
            configuration.setProperty("jakarta.persistence.schema-generation.database.action", properties.getProperty("spring.jpa.hibernate.ddl-auto"));
            configuration.setProperty("hibernate.show-sql", properties.getProperty("spring.jpa.show-sql"));
            configuration.setProperty("hibernate.format_sql", properties.getProperty("spring.jpa.properties.hibernate.format_sql"));
            configuration.addAnnotatedClass(Comment.class);
            configuration.addAnnotatedClass(Commit.class);
            configuration.addAnnotatedClass(GitRepo.class);
            configuration.addAnnotatedClass(GitUser.class);
            configuration.addAnnotatedClass(Issue.class);
            configuration.addAnnotatedClass(Label.class);
            configuration.addAnnotatedClass(Language.class);
            configuration.addAnnotatedClass(PullRequest.class);
            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
