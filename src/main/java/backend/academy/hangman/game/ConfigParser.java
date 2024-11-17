package backend.academy.hangman.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ConfigParser {
    private final Map<String, String> properties = new HashMap<>();

    public ConfigParser(String filePath) {
        loadConfig(filePath);
    }

    private void loadConfig(String filePath) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        if (inputStream == null) {
            throw new IllegalArgumentException("Файл не найден в ресурсах");
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    properties.put(parts[0].trim(), parts[1].trim().replace("\"", ""));
                }
            }
        } catch (IOException e) {
            log.error("IOException error", e);
        }
    }

    public <T> T get(String key, Class<T> type) {
        String value = properties.get(key);
        if (value == null) {
            throw new IllegalArgumentException("Key not found: " + key);
        }

        if (type == String.class) {
            return type.cast(value);
        } else if (type == Integer.class) {
            return type.cast(Integer.parseInt(value));
        } else if (type == Boolean.class) {
            return type.cast(Boolean.parseBoolean(value));
        }

        throw new IllegalArgumentException("Unsupported type: " + type);
    }
}
