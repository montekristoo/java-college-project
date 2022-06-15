import java.io.Serializable;
import java.util.ArrayList;

public class ClientJuridic extends Clienti {

    String persContact;
    public ClientJuridic(String codFiscal, String nume, String adresa, String telefon, int nrColete, ArrayList<Colet> listaColete, String persContact) {
        super(codFiscal, nume, adresa, telefon, nrColete, listaColete);
        this.persContact = persContact;
    }

    public String getPersContact() {
        return persContact;
    }

    public void setPersContact(String persContact) {
        this.persContact = persContact;
    }

    @Override
    public String toString() {
        String linie = "-------------------------";
        String informatie = linie + '\n' + "Tip client: " + this.getClass().getSimpleName() + '\n' + "Cod: " + this.cod + '\n' + "Nume: " + this.nume + '\n' + "Adresa: " + this.adresa + '\n' + "Telefon: " + this.telefon
                + '\n' + "Nr. colete: " + this.nrColete + '\n' + "Pers. de contact: " + this.persContact + '\n' + linie + '\n';
        for(Colet i : colet) {
            informatie = informatie + i.toString();
            i.getContract();
        }
        return informatie;
    }

    @Override
    public String forFile() {
        String info = this.getClass().getSimpleName() + " " + this.cod + " " + this.nume + " " + this.adresa + " " + this.telefon + " " + this.nrColete + " " + this.persContact + '\n';
        for (Colet i : colet) {
            info = info + i.forFile();
        }
        return info;
    }


}
