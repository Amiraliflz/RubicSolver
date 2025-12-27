package adapter.console;

import java.util.Scanner;

public final class ConsoleIO {
    private final Scanner sc = new Scanner(System.in);

    public void println(String s) { System.out.println(s); }
    public String readLine(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }
}
