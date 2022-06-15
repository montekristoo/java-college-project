import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.*;

import static java.lang.System.out;

public class Main {

    static Scanner cin = new Scanner(System.in);
    static byte optiune = 0;

    public static void main(String[] args) throws IOException, ClassNotFoundException, ParseException {

        WorkingSpace application = new WorkingSpace();
        application.start();

    }
}
