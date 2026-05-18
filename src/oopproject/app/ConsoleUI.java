package oopproject.app;

public final class ConsoleUI {
    public static final String RESET = "\u001B[0m";
    public static final String BOLD = "\u001B[1m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";

    private static final int WIDTH = 70;
    private static final int CARD_WIDTH = 30;

    private ConsoleUI() {
    }

    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void title(String text) {
        System.out.println();
        line("=");
        System.out.println(BOLD + CYAN + center(text) + RESET);
        line("=");
    }

    public static void section(String text) {
        System.out.println();
        System.out.println(BOLD + BLUE + "--- " + text + " ---" + RESET);
    }

    public static void success(String text) {
        System.out.println(GREEN + "[OK] " + text + RESET);
    }

    public static void error(String text) {
        System.out.println(RED + "[ERROR] " + text + RESET);
    }

    public static void warning(String text) {
        System.out.println(YELLOW + "[WARN] " + text + RESET);
    }

    public static void info(String text) {
        System.out.println(CYAN + "[INFO] " + text + RESET);
    }

    public static void option(int number, String text) {
        System.out.println(YELLOW + "  [" + number + "] " + RESET + text);
    }

    public static void exitOption() {
        System.out.println(RED + "  [0] " + RESET + "Back / Exit");
    }

    public static void exitOption(String text) {
        System.out.println(RED + "  [0] " + RESET + text);
    }

    public static void prompt() {
        System.out.print(CYAN + "> " + RESET);
    }

    public static void pause() {
        System.out.print(PURPLE + "\n  Press Enter to continue..." + RESET);
    }

    public static void dashboard(String[][] cards) {
        for (int i = 0; i < cards.length; i += 2) {
            String[] left = cards[i];
            String[] right = i + 1 < cards.length ? cards[i + 1] : null;
            printCardRow(cardBorder(), right == null ? "" : cardBorder());
            printCardRow(cardLine(left[0], CYAN + BOLD), right == null ? "" : cardLine(right[0], CYAN + BOLD));
            printCardRow(cardLine(left[1], GREEN + BOLD), right == null ? "" : cardLine(right[1], GREEN + BOLD));
            printCardRow(cardBorder(), right == null ? "" : cardBorder());
        }
    }

    public static void startupBanner() {
        System.out.println();
        line("=");
        System.out.println(BOLD + CYAN + center("Team: Dukes") + RESET);
        System.out.println(BOLD + center("Research-Oriented University System") + RESET);
        System.out.println();
        System.out.println(CYAN + center("Students:") + RESET);
        System.out.println(center("1. Team Leader: Ishutin Nikolay"));
        System.out.println(center("2. Zhazykbayeva Tomiris"));
        System.out.println(center("3. Sabalakov Iskander"));
        System.out.println(center("4. Shointai Aigerim"));
        System.out.println(center("5. Zhalgasbek Meirambek"));
        line("=");
    }

    public static void line(String symbol) {
        System.out.println(CYAN + symbol.repeat(WIDTH) + RESET);
    }

    private static String center(String text) {
        int padding = Math.max(0, (WIDTH - text.length()) / 2);
        return " ".repeat(padding) + text;
    }

    private static void printCardRow(String left, String right) {
        if (right.isBlank()) {
            System.out.println(left);
        } else {
            System.out.println(left + "  " + right);
        }
    }

    private static String cardBorder() {
        return CYAN + "+" + "-".repeat(CARD_WIDTH) + "+" + RESET;
    }

    private static String cardLine(String text, String color) {
        String value = text == null ? "" : text;
        if (value.length() > CARD_WIDTH - 2) {
            value = value.substring(0, CARD_WIDTH - 5) + "...";
        }
        String padding = " ".repeat(CARD_WIDTH - value.length() - 1);
        return CYAN + "| " + RESET + color + value + RESET + padding + CYAN + "|" + RESET;
    }
}
