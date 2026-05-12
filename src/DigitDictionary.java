public class DigitDictionary extends BaseDictionary {
    private static final String KEY_PATTERN = "[0-9]{5}";

    public DigitDictionary(String filePath) {
        super(filePath, "Цифровой словарь (5 цифр)");
    }
    @Override
    protected boolean isValid(String key) {
        return key != null && key.matches(KEY_PATTERN);
    }
}
