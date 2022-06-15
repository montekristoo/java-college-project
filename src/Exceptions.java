import java.util.Scanner;

import static java.lang.System.out;

public class Exceptions {
    static Scanner cin = new Scanner(System.in);
    static String valoare = null;
    static String streetAdressPattern = "[a-zA-Z0-9/']+";
    static String phonePattern = "^[0-9]{9}$";
    static String datePattern = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static String verifyLetters() {
        String valoare = cin.next();
        while (!valoare.matches("[a-zA-Z]+")) {
            out.println(ANSI_RED + "Introdu doar litere." + ANSI_RESET);
            valoare = cin.next();
        }
        return valoare;
    }

    public static String verifyNumbers() {
        String valoare = cin.next();
        while (!valoare.matches("[0-9]+")) {
            out.println(ANSI_RED + "Introdu doar cifre." + ANSI_RESET);
            valoare = cin.next();
        }
        return valoare;
    }

    public static String verifyDouble() {
        String valoare = cin.next();
        while (!valoare.matches("^[+]?(([1-9]\\d*)|0)(\\.\\d+)?")) {
            out.println(ANSI_RED + "Introdu doar cifre." + ANSI_RESET);
            valoare = cin.next();
        }
        return valoare;
    }

    public static String verifyStreetName() {
        String valoare = cin.next();
        while (!valoare.matches(streetAdressPattern)) {
            out.println(ANSI_RED + "Introdu caractere specifice doar denumirii strazilor." + ANSI_RESET);
            valoare = cin.next();
        }
        return valoare;
    }

    public static String verifyDate() {
        String valoare = cin.next();
        while (!valoare.matches(datePattern)) {
            out.println(ANSI_RED + "Introdu formatul pentru data corect." + ANSI_RESET);
            valoare = cin.next();
        }
        return valoare;
    }

    public static String verifyPhone() {
        String valoare = cin.next();
        while (!valoare.matches(phonePattern)) {
            out.println(ANSI_RED + "Introdu un numar de telefon din 9 cifre." + ANSI_RESET);
            valoare = cin.next();
        }
        return valoare;
    }

    public static String verifyTypePackage() {
        String valoare = cin.next();
        while (!(valoare.equals("TO") || valoare.equals("TA") || valoare.equals("TF") || valoare.equals("TR"))) {
            out.println(ANSI_RED + "Introdu corect modul de transport." + ANSI_RESET);
            valoare = cin.next();
        }
        return valoare;
    }

    public static String verifyStare() {
        String valoare = cin.next();
        while (!(valoare.equals("true") || valoare.equals("false") || valoare.equals("null") || valoare.equals("NULL") || valoare.equals("TRUE") || valoare.equals("FALSE"))) {
            out.println(ANSI_RED + "Introdu corect modul de transport." + ANSI_RESET);
            valoare = cin.next();
        }
        return valoare.toLowerCase();
    }

}
