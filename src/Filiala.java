import java.io.Serializable;

public class Filiala implements Serializable {
  String nume, oras, adresa, telefon;

  public Filiala(String nume, String oras, String adresa, String telefon) {
    this.nume = nume;
    this.oras = oras;
    this.adresa = adresa;
    this.telefon = telefon;
  }

  Filiala() {
    this.nume = null;
    this.oras = null;
    this.adresa = null;
    this.telefon = null;
  }

  public String getNume() {
    return nume;
  }

  public void setNume(String nume) {
    this.nume = nume;
  }

  public String getOras() {
    return oras;
  }

  public void setOras(String oras) {
    this.oras = oras;
  }

  public String getAdresa() {
    return adresa;
  }

  public void setAdresa(String adresa) {
    this.adresa = adresa;
  }

  public String getTelefon() {
    return telefon;
  }

  public void setTelefon(String telefon) {
    this.telefon = telefon;
  }
}
