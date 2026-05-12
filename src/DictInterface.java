import java.util.Map;
public interface DictInterface {
    Map<String, String> getAll();
    String find(String key);
    void add(String key, String value);
    void delete(String key);
    String getName();
}