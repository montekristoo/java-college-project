import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Boolean.TRUE;

public class Colet implements DataTransferingToFile {

     String denumire;
     double dimensiuneM, greutateKg, cost;
     String tip;
     public Contract contract;
     String reached;
     public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";

    static Map<String, Integer> prices = new HashMap<String, Integer>() {{
        put("TA", 1000);
        put("TF", 500);
        put("TO", 1500);
        put("TR", 250);
    }};

    static double pretTotal = 0;

    public Colet(String denumire, String tip, double dimensiuneM, double greutateKg, String reached, Contract contract) {
        this.denumire = denumire;
        this.tip = tip;
        this.dimensiuneM = dimensiuneM;
        this.greutateKg = greutateKg;
        pretTotal = prices.get(tip);
        this.cost = pretTotal;
        this.reached = reached;
        if (dimensiuneM <= 1) this.cost = this.cost + 100;
        else if (dimensiuneM > 1 && dimensiuneM <=2) this.cost = this.cost + 250;
        else if (dimensiuneM > 2 && dimensiuneM <=3) this.cost = this.cost + 400;
        else throw new IllegalArgumentException("Dimensiunea coletului trece de limita necesara.");
        this.contract = contract;
        contract.setCost(cost);
    }

    public String toString() {
        String linie = "-------------------------";

        String stare = (this.reached.equals("true")) ? "A ajuns" : (this.reached.equals("false")) ? "Nu a ajuns" : "E in curs de trimitere.";

        return (ANSI_BLUE + "Denumire colet: " + this.denumire + "\n" + "Tip colet: " + this.tip + '\n' + "Dimensiune [m]: " + this.dimensiuneM + '\n' + "Greutate [kg]: " + this.greutateKg + '\n'
                + "Stare: " + stare +  '\n' + contract.toString() + linie + '\n' + ANSI_RESET);
    }

    @Override
    public String forFile() {
        return (this.denumire + " " + this.tip + " " + this.dimensiuneM + " " + this.greutateKg + " " + this.reached + " " + contract.forFile() + " " + '\n');
    }

    public String getReached() {
        return reached;
    }

    public void setReached(String reached) {
        this.reached = reached;
    }

    public double getDimensiuneM() {
        return dimensiuneM;
    }

    public void setDimensiuneM(double dimensiuneM) {
        this.dimensiuneM = dimensiuneM;
    }

    public double getGreutateKg() {
        return greutateKg;
    }

    public void setGreutateKg(double greutateKg) {
        this.greutateKg = greutateKg;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public void afisareDate() {
        System.out.println(prices.get("TR"));
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }


}
