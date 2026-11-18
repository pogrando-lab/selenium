package com.studio.pro;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.open;

public abstract class TestsBase {
    protected static final Properties PROPERTIES = loadProperties();

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = TestsBase.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IllegalStateException("config.properties not found in test resources");
            }
            properties.load(input);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to load config.properties", exception);
        }
        return properties;
    }

    @BeforeEach
    public void setUp() {
        Configuration.baseUrl = PROPERTIES.getProperty("base.url");
        Configuration.browser = PROPERTIES.getProperty("browser", "chrome");
        Configuration.headless = Boolean.parseBoolean(
                PROPERTIES.getProperty("headless", "false"));
        Configuration.timeout = Long.parseLong(
                PROPERTIES.getProperty("timeout", "10000"));

        open("/");
    }
}
