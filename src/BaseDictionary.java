import java.io.*;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseDictionary implements DictInterface {
    protected final String filePath;
    protected final String name;
    protected final Map<String, String> data = new HashMap<>();
    public BaseDictionary(String filePath, String name) {
        this.filePath = filePath;
        this.name = name;
        ensureFileExists();
        load();
    }
    private void ensureFileExists() {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("Создан новый файл словаря: " + filePath);
                }
            } catch (IOException e) {
                System.err.println("Ошибка при создании файла словаря: " + e.getMessage());
            }
        }
    }
    protected void load() {
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    String key = parts[0];
                    String value = parts[1];
                    data.put(key, value);
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при загрузке словаря: " + e.getMessage());
        }
    }
    protected void save() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, String> entry : data.entrySet()) {
                writer.println(entry.getKey() + "=" + entry.getValue());
            }
        } catch (IOException e) {
            System.err.println("Ошибка сохранения словаря: " + e.getMessage());
        }
    }
    @Override
    public Map<String, String> getAll() {
        Map<String, String> filteredData = new HashMap<>();
        for (Map.Entry<String, String> entry : data.entrySet()) {
            if (isValid(entry.getKey())) {
                filteredData.put(entry.getKey(), entry.getValue());
            }
        }
        return filteredData;
    }
    @Override
    public String find(String key) {
        return data.get(key);
    }
    @Override
    public void add(String key, String value) {
        if (!isValid(key)) {
            throw new IllegalArgumentException("Неверный формат ключа для словаря: " + name);
        }
        data.put(key, value);
        save();
    }
    @Override
    public void delete(String key) {
        if (data.containsKey(key)) {
            data.remove(key);
            save();
        }
    }
    @Override
    public String getName() {
        return name;
    }
    protected abstract boolean isValid(String key);
}

