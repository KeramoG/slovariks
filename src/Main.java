import java.util.Scanner;
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static DictInterface currentDict = null;
    private static DictInterface latinDict = null;
    private static DictInterface digitDict = null;
    private static String latinPath = null;
    private static String digitPath = null;

    public static void main(String[] args) {
        selectDictionary();

        boolean exit = false;
        while (!exit) {
            printMenu();
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    viewBothDictionaries();
                    break;
                case "2":
                    selectDictionary();
                    break;
                case "3":
                    performOperation();
                    break;
                case "0":
                    exit = true;
                    break;
                default:
                    System.out.println("Неизвестная команда. Попробуйте еще раз.");
            }
        }
        System.out.println("До свидания!");
    }

    private static void printMenu() {
        System.out.println("\n Сервис словарей ");
        System.out.println("Выбранный словарь: " + (currentDict == null ? "Нет" : currentDict.getName()));
        System.out.println("1. Просмотреть оба словаря");
        System.out.println("2. Выбрать словарь");
        System.out.println("3. Операции со словарем (Поиск, Добавить, Удалить)");
        System.out.println("0. Выход");
        System.out.print("Выберите опцию: ");
    }

    private static void viewBothDictionaries() {
        System.out.println("\n Латинский словарь ");
        if (latinDict == null) {
            System.out.println("Словарь не инициализирован (путь не задан).");
        } else {
            if (latinDict.getAll().isEmpty()) System.out.println("Пусто");
            latinDict.getAll().forEach((k, v) -> System.out.println(k + ": " + v));
        }

        System.out.println("\nЦифровой словарь");
        if (digitDict == null) {
            System.out.println("Словарь не инициализирован (путь не задан).");
        } else {
            if (digitDict.getAll().isEmpty()) System.out.println("Пусто");
            digitDict.getAll().forEach((k, v) -> System.out.println(k + ": " + v));
        }
    }

    private static void selectDictionary() {
        System.out.println("\nВыбор словаря");
        System.out.println("1. Латинский словарь (4 буквы)");
        System.out.println("2. Цифровой словарь (5 цифр)");
        System.out.print("Ваш выбор: ");
        String choice = scanner.nextLine();
        if (choice.equals("1")) {
            if (latinDict == null) {
                System.out.print("Введите путь для латинского словаря (по умолчанию latin.txt): ");
                latinPath = scanner.nextLine();
                if (latinPath.isEmpty()) latinPath = "latin.txt";
                latinDict = new LatinDictionary(latinPath);
            }
            currentDict = latinDict;
            System.out.println("Выбран Латинский словарь.");
        } else if (choice.equals("2")) {
            if (digitDict == null) {
                System.out.print("Введите путь для цифрового словаря (по умолчанию digit.txt): ");
                digitPath = scanner.nextLine();
                if (digitPath.isEmpty()) digitPath = "digit.txt";
                digitDict = new DigitDictionary(digitPath);
            }
            currentDict = digitDict;
            System.out.println("Выбран Цифровой словарь.");
        } else {
            System.out.println("Неверный выбор.");
        }
    }

    private static void performOperation() {
        if (currentDict == null) {
            System.out.println("Сначала выберите словарь!");
            return;
        }

        System.out.println("\n " + currentDict.getName() + " ");
        System.out.println("1. Поиск по ключу");
        System.out.println("2. Добавить запись");
        System.out.println("3. Удалить запись");
        System.out.print("Действие: ");
        String action = scanner.nextLine();

        switch (action) {
            case "1":
                System.out.print("Введите ключ: ");
                String searchKey = scanner.nextLine();
                String result = currentDict.find(searchKey);
                System.out.println(result != null ? "Найдено: " + result : "Запись не найдена.");
                break;
            case "2":
                System.out.print("Введите ключ: ");
                String addKey = scanner.nextLine();
                System.out.print("Введите перевод: ");
                String addValue = scanner.nextLine();
                try {
                    currentDict.add(addKey, addValue);
                    System.out.println("Запись успешно добавлена.");
                } catch (IllegalArgumentException e) {
                    System.out.println("Ошибка: " + e.getMessage());
                }
                break;
            case "3":
                System.out.print("Введите ключ: ");
                String delKey = scanner.nextLine();
                currentDict.delete(delKey);
                System.out.println("Запись удалена (если она существовала).");
                break;
            default:
                System.out.println("Неверное действие.");
        }
    }
}
