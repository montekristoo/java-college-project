import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

public class Contract implements ConverterValutar, DataTransferingToFile {

    public static final String GREEN_BOLD = "\033[1;32m";
    public static final String ANSI_RESET = "\u001B[0m";
    static Map<String, Double> valuta = new HashMap<String, Double>(ConverterValutar.getMap());
    int nr;
    String data, termen;
    double cost, asigurare, comissionIncasare;
    NumberFormat formatter = new DecimalFormat("#0.00");


    public Contract(int nr, String data, String termen, double asigurare) {
        this.nr = nr;
        this.data = data;
        this.termen = termen;
        this.asigurare = asigurare;
    }


    @Override
    public double mdlToEuro() {
        return (cost / valuta.get("EUR"));
    }

    @Override
    public double mdlToUSD() {
        return (cost / valuta.get("USD"));
    }

    public double comissionIncome() {
        double incasare = 0;
        double procentaj = 0.01 / 100;
        incasare = incasare + (procentaj * cost);
        setComissionIncasare(incasare);
        return comissionIncasare;
    }

    @Override
    public String toString() {
        String linie = "-------------------------";
        return (GREEN_BOLD + "Nr. contract: " + this.nr + '\n' + "Data: " + this.data + '\n' + "Termen: " + this.termen + '\n' + "Cost: " + "| MDL = " + this.cost + " | EURO = " + formatter.format(mdlToEuro()) + " | USD = " + formatter.format(mdlToUSD()) + '\n' + "Asigurarea: " + this.asigurare + " MDL" + '\n'
                + linie + '\n' + "Comisionul angajatului: " + formatter.format(comissionIncome()) + " MDL" + '\n' + ANSI_RESET);
    }

    @Override
    public String forFile() {
        return (this.nr + " " + this.data + " " + this.termen + " " + this.asigurare);
    }

    public double getComissionIncasare() {
        return comissionIncasare;
    }

    public void setComissionIncasare(double comissionIncasare) {
        this.comissionIncasare = comissionIncasare;
    }

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTermen() {
        return termen;
    }

    public void setTermen(String termen) {
        this.termen = termen;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getAsigurare() {
        return asigurare;
    }

    public void setAsigurare(double asigurare) {
        this.asigurare = asigurare;
    }
}
