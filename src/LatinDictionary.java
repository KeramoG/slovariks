public class LatinDictionary extends BaseDictionary {
    private static final String KEY_PATTERN = "[a-zA-Z]{4}";

    public LatinDictionary(String filePath) {
        super(filePath, "Латинский словарь (4 буквы)");
    }

    @Override
    protected boolean isValid(String key) {
        return key != null && key.matches(KEY_PATTERN);
    }
}