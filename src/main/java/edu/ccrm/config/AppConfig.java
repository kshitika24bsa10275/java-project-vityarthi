package edu.ccrm.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Simple singleton for application configuration.
 */
public final class AppConfig {
    private static AppConfig instance;

    private Path dataFolderPath;

    private AppConfig() { }

    public static synchronized AppConfig getInstance() {
        if (instance == null) instance = new AppConfig();
        return instance;
    }

    public Path getDataFolderPath() {
        return dataFolderPath;
    }

    public void loadFromProperties(String resourceName) throws IOException {
        Properties p = new Properties();
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(resourceName)) {
            if (in == null) throw new IOException("Properties resource not found: " + resourceName);
            p.load(in);
        }
        String path = p.getProperty("dataFolderPath");
        if (path != null) dataFolderPath = Path.of(path);
    }
}
