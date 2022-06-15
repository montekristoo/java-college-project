import java.util.ArrayList;

public class ClientFizic extends Clienti{
    public static final String BLUE_BOLD = "\033[1;34m";
    public ClientFizic(String cod, String nume, String adresa, String telefon, int nrColete, ArrayList<Colet> listaColete) {
        super(cod, nume, adresa, telefon, nrColete, listaColete);
    }

    @Override
    public String toString() {
        String linie = "-------------------------";
        String informatie =  linie + '\n' + "Tip client: " + this.getClass().getSimpleName() + '\n' + "Cod: " + this.cod + '\n' + "Nume: " + this.nume + '\n' + "Adresa: " + this.adresa + '\n' + "Telefon: " + this.telefon
                + '\n' + "Nr. colete: " + this.nrColete + '\n' + linie + '\n';
        for(Colet i : colet) {
            informatie = informatie + i.toString();
            i.getContract();
        }
        return informatie;
    }

    @Override
    public String forFile() {
        String info = this.getClass().getSimpleName() + " " + this.cod + " " + this.nume + " " + this.adresa + " " + this.telefon + " " + this.nrColete + '\n';
        for (Colet i : colet) {
            info = info + i.forFile();
        }
        return info;
    }
}
