import javax.xml.crypto.Data;
import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;
import java.io.*;
import java.sql.Date;
import java.text.*;
import java.util.*;
import java.util.function.Predicate;

import static java.lang.System.out;

public class WorkingSpace {

    private static final Scanner cin = new Scanner(System.in);
    private static ArrayList<String> filialeText = new ArrayList<>();
    private static LinkedHashMap<Filiala, ArrayList<Clienti>> informations = new LinkedHashMap<>();
    private static String displayMessage = "Afiseaza optiunile pentru filiala ";
    private static ArrayList<String> branchesDisplayMessages = new ArrayList<>();
    static ObjectOutputStream oos = null;
    static ObjectInputStream ois = null;
    private static File branchesList = new File ("BranchesList.txt");
    private static File cacheBranches = new File ("cacheBranches.txt");
    private boolean starting = true;
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    private final String linie = "----------------------------------";
    static NumberFormat formatter = new DecimalFormat("#0.0000");

    private static int readOption() {
        int option;
        try {
            option = cin.nextInt();
            return option;
        } catch (InputMismatchException e) {
            out.println("Introduceti doar cifre/numere...");
            return readOption();
        }
    }

    public void start() throws IOException, ClassNotFoundException, ParseException {
        citireFiliale();
        //CacheRead();
        while (starting) {
            displayInitialMenu();
        }
    }

    private void CacheRead() throws IOException, ClassNotFoundException {
     if (branchesList.isFile()) {
         ois = new ObjectInputStream(new FileInputStream(branchesList));
         filialeText = (ArrayList<String>) ois.readObject();
         out.println(filialeText);


     }
    }

    private void writeToFile(Filiala f) throws IOException {

        BufferedWriter writer = null;
        for (int i = 0; i < filialeText.size(); i++) {
            String name = filialeText.get(i).replaceFirst("[.][^.]+$", "");
            out.println(name);
            String path = "src\\Fisiere\\" + filialeText.get(i) + ".txt";
            for (Map.Entry<Filiala, ArrayList<Clienti>> entry : informations.entrySet()) {
                Filiala key = entry.getKey();
                ArrayList<Clienti> value = entry.getValue();
                if (key.equals(f) && key.getNume().equals(name)) {
                   writer =  new BufferedWriter(new FileWriter(path));
                   writer.write(f.getNume() + " " + f.getOras() + " " + f.getAdresa() + " " + f.getTelefon() + '\n');
                    BufferedWriter finalWriter = writer;
                    value.stream().forEach(c -> {
                        try {
                            finalWriter.write(c.forFile());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            }
        }
        writer.close();
    }

    private void citireFiliale() throws IOException {

        String tipClient = null, cod = null, nume = null, strada = null, telefon = null;
        int nrColete = 0, nrContract;
        String reached = null;
        String numeColet, tipColet, dateSended, dateRequested, persContact = null;
        double dimensiune, kg, asigurare;
        String numeFiliala = null, stradaFiliala = null, orasFiliala = null, telefonFiliala = null;
        ///Citirea filialelor
        Scanner reader = new Scanner(new FileReader("src\\ListeFiliale.txt"));
        while(reader.hasNext()) {
            filialeText.add(reader.next());
        }

        for (String s : filialeText) {
            String option = displayMessage + s;
            branchesDisplayMessages.add(option);
        }

        for (int i = 0; i < filialeText.size(); i++) {
            ArrayList<Colet> colete = new ArrayList<>();
            ArrayList<Clienti> clienti = new ArrayList<Clienti>();
            String name = filialeText.get(i);
            String path = "src\\Fisiere\\" + filialeText.get(i) + ".txt";
            BufferedReader brTest = new BufferedReader(new FileReader(path));
            String text = brTest.readLine();
            String[] parts = text.split(" ");
            Filiala filiala = new Filiala(parts[0], parts[1], parts[2], parts[3]);
                    Scanner scan = new Scanner(new FileReader(path));
                    if (scan.nextLine() == null) {
                        informations.put(filiala, clienti);
                        continue;
                    }
                    while (scan.hasNext()) {
                        colete = new ArrayList<>();
                        int j = 0;
                        tipClient = scan.next();
                        cod = scan.next();
                        nume = scan.next();
                        strada = scan.next();
                        telefon = scan.next();
                        nrColete = scan.nextInt();
                        switch (tipClient) {
                            case "ClientJuridic" -> {
                                persContact = scan.next();
                            }
                        }
                        while (scan.hasNext() && j < nrColete) {
                            numeColet = scan.next();
                            tipColet = scan.next();
                            dimensiune = scan.nextDouble();
                            kg = scan.nextDouble();
                            reached = scan.next();
                            nrContract = scan.nextInt();
                            dateSended = scan.next();
                            dateRequested = scan.next();
                            asigurare = scan.nextDouble();
                            colete.add(new Colet(numeColet, tipColet, dimensiune, kg, reached, new Contract(nrContract, dateSended, dateRequested, asigurare)));
                            j++;
                        }
                        switch (tipClient) {
                            case "ClientFizic" -> {
                                clienti.add(new ClientFizic(cod, nume, strada, telefon, nrColete, colete));
                                informations.put(filiala, clienti);
                            }
                            case "ClientJuridic" -> {
                                clienti.add(new ClientJuridic(cod, nume, strada, telefon, nrColete, colete, persContact));
                                informations.put(filiala, clienti);
                            }
                        }
                    }
                }
         //   }
      //  }
    }

    private void displayOptions(ArrayList<String> options, int number) {
        int i;
        String align = null;
        align = (number == 1) ? " ".repeat(3) : (number == 2) ? " ".repeat(6) : (number == 3) ? " ".repeat(9) : (number == 4) ? " ".repeat(12) : "X";
        for (i = 1; i <= options.size(); i++) {
            String optiune = i + ". " + options.get(i-1);
            out.println(align + optiune);
        }
        out.println(align + i + ". Back");
        out.println(align + "0. Iesire");
    }

    public void displayInitialMenu() throws IOException, ParseException {
        displayOptions(new ArrayList(List.of(
                "Afisare filiale...",
                "Adauga filiala...",
                "Sterge filiala...",
                "Venitul total al companiei...",
                "Despagubirile pentru o anumita perioada de timp."
        )), 1);
        handleInitialMenu(readOption());
    }

    private void handleInitialMenu(int optiune) throws IOException, ParseException {
        switch (optiune) {
            case 1 -> displayBranches();
            case 2 -> addBranche();
            case 3 -> deleteBranche();
            case 4 -> out.println(linie + '\n' + "Venitul total al companiei = " + totalIncomeOfCompany() + " MDL" + '\n' + linie + '\n');
            case 5 -> compensationByDate();
            case 0 -> starting = false;
            default -> displayInitialMenu();
        }
    }

    public void displayBranches() throws IOException, ParseException {
        displayOptions(branchesDisplayMessages, 2);
        BranchesHandle(readOption());
    }

    private void BranchesHandle(int optiune) throws IOException, ParseException {
        if (optiune == branchesDisplayMessages.size()+1)
            displayInitialMenu();
        else if (optiune == 0)
            starting = false;
        else {
            //out.println(informations);
            Filiala f = (Filiala) informations.keySet().toArray()[optiune-1];
            settingsBranches(f);
        }
    }

    private void addBranche() throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter("src\\ListeFiliale.txt", true));
        out.println("Introdu numele filialei: ");
        String nume = Exceptions.verifyLetters();
        ArrayList<Clienti> client = new ArrayList<Clienti>();
        ArrayList<Colet> colet = new ArrayList<Colet>();

        while (filialeText.contains(nume)) {
            out.println(ANSI_RED + "Filiala deja exista." + ANSI_RESET);
            nume = Exceptions.verifyLetters();
        }

        out.println("Introdu orasul filialei: ");
        String oras = Exceptions.verifyLetters();
        out.println("Introdu adresa filialei: ");
        String adresa = cin.next();
        out.println("Telefonul filialei: ");
        String telefon = Exceptions.verifyNumbers();

        Filiala f = new Filiala(nume, oras, adresa, telefon);

        writer.write('\n' + nume);
        String filename = nume + ".txt";
        String workingDirectory = ("src\\Fisiere\\");
        String absoluteFilePath = "";
        absoluteFilePath = workingDirectory + File.separator + filename;
        BufferedWriter w = new BufferedWriter(new FileWriter(absoluteFilePath));
        w.write(nume + " " + oras + " " + adresa + " " + telefon + '\n');

        try {
            File myObj = new File(absoluteFilePath);
            myObj.createNewFile();
                branchesDisplayMessages.add(displayMessage + nume);
                filialeText.add(nume);
                informations.put(f, client);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        writer.close();
        w.close();
    }

    private void deleteBranche() throws IOException {

        out.println("Introdu numele filialei: ");
        String filiala = cin.next();
        boolean ok = false;

        for (Map.Entry<Filiala, ArrayList<Clienti>> entry : informations.entrySet()) {
            Filiala key = entry.getKey();
            if (key.getNume().equals(filiala)) {
                informations.remove(key);
                ok = true;
                break;
            }
        }
        if (ok) {
            File file = new File("src\\Fisiere\\" + filiala + ".txt");
            file.delete();
            filialeText.remove(filiala);
            BufferedWriter writer = new BufferedWriter(new FileWriter("src\\ListeFiliale.txt"));
            for (String s : filialeText) {
                writer.write(s + '\n');
            }
            for(Iterator<String> iterator = branchesDisplayMessages.iterator(); iterator.hasNext(); ) {
                if(iterator.next().contains(filiala)) {
                    iterator.remove();
                    break;
                }
            }
            writer.close();
        } else {
            out.println("Filiala tastata nu exista.");
        }

    }

    public void settingsBranches(Filiala filiala) throws IOException {

        Filiala fil = filiala;
        displayOptions(new ArrayList<>(List.of(
                "Afisarea informatiilor despre clienti si coletele acestora.",
                "Venitul total al angajatilor de la comisionul coletelor.",
                "Coletele repartizate dupa modul de transport [TO, TF, TA, TR].",
                "Adaugare client.",
                "Stergere client.",
                "Adaugare colet.",
                "Stergere colet."
        )), 3);

        setOption(readOption(), fil);

    }


    private void setOption(int option, Filiala f) throws IOException {
        switch (option) {
            case 1 -> afisareInformatii(f);
            case 2 -> out.println(linie + '\n' + "Venitul total al angajatilor de la comisii: " + totalIncomeFromComission(f) + '\n' + linie);
            case 3 -> settingsTypePackage();
            case 4 -> addClient(f);
            case 5 -> removeClient(f);
            case 6 -> addPackage(f);
            case 7 -> removePackage(f);
        }
    }

    private void addClient(Filiala f) throws IOException {
        String tipClient = null, cod = null, nume = null, strada = null, telefon = null, persContact = null;
        String numeColet = null, tipColet = null, reached = null, dateSended = null, dateRequested = null;
        double asigurare = 0, kg = 0, dimensiune = 0;
        int nrColete = 0;
        int nrContract = 0;
        Clienti client = null;
        ArrayList<Colet> colet = new ArrayList<>();
        out.println("-> Tipul clientului: ");
        tipClient = Exceptions.verifyLetters();
        out.println("-> Codul: ");
        cod = Exceptions.verifyNumbers();
        out.println("-> Numele: ");
        nume = Exceptions.verifyLetters();
        out.println("-> Strada: ");
        strada = cin.next();
        out.println("-> Telefonul: ");
        telefon = Exceptions.verifyPhone();
        out.println("-> Nr. colete: ");
        nrColete = Integer.parseInt(Exceptions.verifyNumbers());
        for (int i = 0; i<nrColete; i++) {
            out.println("Nume colet: "); numeColet = Exceptions.verifyLetters();
            out.println("Tip colet [TO - Transport Oceanic, TA - Transport aerian, TF - Transport feroviar, TR - Transport rutier]: "); tipColet = Exceptions.verifyLetters();
            out.println("Dimensiune [m]: "); dimensiune = Double.parseDouble(Exceptions.verifyDouble());
            out.println("Greutate [kg]: "); kg = Double.parseDouble(Exceptions.verifyDouble());
            out.println("Starea coletului [false - nu a ajuns | true - a ajuns | null - in curs de trimitere]: "); reached = Exceptions.verifyStare();
            out.println("Nr.contract: "); nrContract = Integer.parseInt(Exceptions.verifyNumbers());
            out.println("Data trimiterii: "); dateSended = Exceptions.verifyDate();
            out.println("Data primirii: "); dateRequested = Exceptions.verifyDate();
            out.println("Asigurare: "); asigurare = Integer.parseInt(Exceptions.verifyNumbers());
            colet.add(new Colet(numeColet, tipColet, dimensiune, kg, reached, new Contract(nrContract, dateSended, dateRequested, asigurare)));
        }
        switch (tipClient) {
            case "ClientJuridic" -> {
                out.println("Persoana de contact: ");
                persContact = Exceptions.verifyLetters();
                client = new ClientJuridic(cod, nume, strada, telefon, nrColete, colet, persContact);
            }
            case "ClientFizic" -> {
                client = new ClientFizic(cod, nume, strada, telefon, nrColete, colet);
            }
        }
        for (Map.Entry<Filiala, ArrayList<Clienti>> entry : informations.entrySet()) {
            Filiala key = entry.getKey();
            ArrayList<Clienti> value = entry.getValue();
            if (key.equals(f)) {
                value.add(client);
            }
        }
        writeToFile(f);
    }

    private void removeClient(Filiala f) throws IOException {
        String nume, telefon;
        out.println("-> Numele: "); nume = Exceptions.verifyLetters();
        out.println("-> Telefon: "); telefon = Exceptions.verifyPhone();
        for (Map.Entry<Filiala, ArrayList<Clienti>> entry : informations.entrySet()) {
            Filiala key = entry.getKey();
            ArrayList<Clienti> value = entry.getValue();
            if (key.equals(f)) {
               Clienti forRemove = value.stream().
                       filter(c -> c.nume.equals(nume) && c.telefon.equals(telefon))
                       .findFirst()
                       .orElse(null);
               try {
                   if (forRemove != null) {
                       value.remove(forRemove);
                   } else throw new IllegalArgumentException("Clientul dat a fost sters deja sau nu exista");
               }
               catch (IllegalArgumentException e) {
                   e.printStackTrace();
               }
            }
        }
    }

    private void addPackage(Filiala f) {
        boolean ok = false;
        String reached, nume, telefon;
        String numeColet, tipColet, dateSended, dateRequested, persContact = null;
        int nrContract = 0;
        double dimensiune, kg, asigurare;
        out.println("-> Numele clientului: "); nume = Exceptions.verifyLetters();
        out.println("-> Telefonul clientului: "); telefon = Exceptions.verifyPhone();
        
        out.println("Nume colet: "); numeColet = Exceptions.verifyLetters();
        out.println("Tip colet [TO - Transport Oceanic, TA - Transport aerian, TF - Transport feroviar, TR - Transport rutier]: "); tipColet = Exceptions.verifyTypePackage();
        out.println("Dimensiune[m]: "); dimensiune = Double.parseDouble(Exceptions.verifyDouble());
        out.println("Greutate[kg]: "); kg = Double.parseDouble(Exceptions.verifyDouble());
        out.println("Starea coletului [false - nu a ajuns | true - a ajuns | null - in curs de trimitere]: "); reached = Exceptions.verifyStare();
        out.println("Nr.contract: "); nrContract = Integer.parseInt(Exceptions.verifyNumbers());
        out.println("Data trimiterii: "); dateSended = cin.next();
        out.println("Data primirii: "); dateRequested = cin.next();
        out.println("Asigurare: "); asigurare = Integer.parseInt(Exceptions.verifyNumbers());

        for (Map.Entry<Filiala, ArrayList<Clienti>> entry : informations.entrySet()) {
            Filiala key = entry.getKey();
            ArrayList<Clienti> clienti = entry.getValue();
            if (key.equals(f)) {
                for (Clienti i : clienti) {
                    for (Colet j : i.colet) {
                        if (j.getDenumire().equals(numeColet) && j.getContract().getNr() == nrContract) {
                            ok = true;
                            break;
                        }
                    }
                }
            }
        }

        if (ok) {
            out.println("Acest colet deja exista.");
            addPackage(f);
        }
        else {

            Colet colet = new Colet(numeColet, tipColet, dimensiune, kg, reached, new Contract(nrContract, dateSended, dateRequested, asigurare));

            for (Map.Entry<Filiala, ArrayList<Clienti>> entry : informations.entrySet()) {
                Filiala key = entry.getKey();
                ArrayList<Clienti> clienti = entry.getValue();
                if (key.equals(f)) {
                    for (Clienti i : clienti) {
                        if (i.getNume().equals(nume)) {
                            i.colet.add(colet);
                            i.setNrColete(i.colet.size());
                        }
                    }
                }
            }
        }
    }

    private void removePackage(Filiala f) throws IOException {
        String numeColet;
        int nrContract;
        out.println("Numele coletului: "); numeColet = Exceptions.verifyLetters();
        out.println("Nr. contractului: "); nrContract = Integer.parseInt(Exceptions.verifyNumbers());
        for (Map.Entry<Filiala, ArrayList<Clienti>> entry : informations.entrySet()) {
            Filiala key = entry.getKey();
            ArrayList<Clienti> clienti = entry.getValue();
            if (key.equals(f)) {
               for (Clienti i : clienti) {
                   for (Colet j : i.colet) {
                       if (j.getDenumire().equals(numeColet) && j.getContract().getNr() == nrContract) {
                           i.colet.remove(j);
                           i.setNrColete(i.colet.size());
                       }
                   }
               }
            }
            }
        writeToFile(f);
        }

    private void settingsTypePackage() {
        displayOptions(new ArrayList<String>(List.of(
                "Coletele trimise prin transport rutier.",
                "Coletele trimise prin transport aerian",
                "Coletele trimise prin transport oceanic",
                "Coletele trimise prin transport feroviar"
        )), 4);
        packageType(readOption());
    }

    private void packageType(int option) {
        switch (option) {
            case 1 -> groupByTransport("TR");
            case 2 -> groupByTransport("TA");
            case 3 -> groupByTransport("TO");
            case 4 -> groupByTransport("TF");
        }
    }

    private void afisareInformatii(Filiala filiala) {
        for (Map.Entry<Filiala, ArrayList<Clienti>> entry : informations.entrySet()) {
            Filiala key = entry.getKey();
            ArrayList<Clienti> value = entry.getValue();
            if (key.equals(filiala)) {
                value.stream().forEach(out::println);
            }
        }
    }

    private double totalIncomeFromComission(Filiala filiala) {
        double total = 0;
        for (Map.Entry<Filiala, ArrayList<Clienti>> entry : informations.entrySet()) {
            Filiala key = entry.getKey();
            ArrayList<Clienti> value = entry.getValue();
            if (key.equals(filiala)) {
                for (Clienti i : value) {
                    total = i.colet.stream().filter(c-> c.getReached().equals("true")).map(Colet::getContract).mapToDouble(Contract::getComissionIncasare).sum();
                }
            }
        }
        return total;
    }

    private double totalIncomeOfCompany() {
        double total = 0;
        for (Map.Entry<Filiala, ArrayList<Clienti>> entry : informations.entrySet()) {
            Filiala key = entry.getKey();
            ArrayList<Clienti> value = entry.getValue();
            for (Clienti i : value) {
                total = total + i.colet.stream().map(Colet::getContract).mapToDouble(Contract::getCost).sum();
            }
        }
        return total;
    }

    private void groupByTransport(String tip) {
        List<Colet> typesColete = new ArrayList<>();
        for (Map.Entry<Filiala, ArrayList<Clienti>> entry : informations.entrySet()) {
            Filiala key = entry.getKey();
            ArrayList<Clienti> value = entry.getValue();
            for (Clienti i : value) {
                i.colet.stream().filter(c -> c.getTip().equals(tip)).forEach(c -> out.println(c));
            }
        }
        typesColete.forEach(f -> out.println(f.toString()));
    }

    private void compensationByDate () throws ParseException {

        double total = 0;

        out.println("Data 1: "); String firstDate = Exceptions.verifyDate();
        out.println("Data 2: "); String secondDate = Exceptions.verifyDate();

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date fD = df.parse(firstDate);
        java.util.Date sD = df.parse(secondDate);

        for (Map.Entry<Filiala, ArrayList<Clienti>> entry : informations.entrySet()) {
            Filiala key = entry.getKey();
            ArrayList<Clienti> value = entry.getValue();
            for (Clienti i : value) {
                total = total + i.colet.stream().filter(c -> {
                    try {
                        return fD.before(df.parse(c.getContract().data)) && sD.after(df.parse(c.getContract().data)) && c.getReached().equals("false");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return false;
                }).map(Colet::getContract).mapToDouble(Contract::getAsigurare).sum();
            }
        }
        out.println(linie);
        out.println("Despagubirile companiei intre perioada " + firstDate + " si " + secondDate + " = " + total + " MDL");
        out.println(linie);
    }
}
