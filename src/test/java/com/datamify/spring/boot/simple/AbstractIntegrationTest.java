package com.datamify.spring.boot.simple;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ActiveProfiles("test")
@ContextConfiguration(initializers = {
        AbstractIntegrationTest.DatabaseContextInitializer.class,
        AbstractIntegrationTest.RedisContextInitializer.class
})
@SpringBootTest(classes = SpringBootSimpleApplication.class, webEnvironment = RANDOM_PORT)
public abstract class AbstractIntegrationTest {

    private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER;
    private static final GenericContainer<?> REDIS_CONTAINER;

    static {
        POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:13.3");
        POSTGRESQL_CONTAINER.start();

        REDIS_CONTAINER = new GenericContainer<>("redis:6.2.3").withExposedPorts(6379);
        REDIS_CONTAINER.start();
    }

    @LocalServerPort
    private int port;

    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected CacheManager cacheManager;

    protected String url(String path) {
        return "http://localhost:" + port + path;
    }

    public static class DatabaseContextInitializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext context) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + POSTGRESQL_CONTAINER.getJdbcUrl(),
                    "spring.datasource.username=" + POSTGRESQL_CONTAINER.getUsername(),
                    "spring.datasource.password=" + POSTGRESQL_CONTAINER.getPassword()
            ).applyTo(context.getEnvironment());
        }

    }

    public static class RedisContextInitializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext context) {
            TestPropertyValues.of(
                    "spring.redis.host=" + REDIS_CONTAINER.getHost(),
                    "spring.redis.port=" + REDIS_CONTAINER.getFirstMappedPort()
            ).applyTo(context.getEnvironment());
        }

    }

}
