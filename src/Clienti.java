import java.util.ArrayList;

public abstract class Clienti extends Filiala implements DataTransferingToFile{
 String cod;
 String codFiscal;
 String nume, adresa, telefon;
 int nrColete;
 ArrayList<Colet> colet = new ArrayList<>(nrColete);

 Clienti() {}
 public Clienti(String cod, String nume, String adresa, String telefon, int nrColete, ArrayList<Colet> listaColete) {
  this.cod = cod;
  this.nume = nume;
  this.adresa = adresa;
  this.telefon = telefon;
  this.nrColete = nrColete;
  colet = listaColete;

 }

 public Clienti(String codFiscal, String adresa, String telefon, int nrColete, ArrayList<Colet> listaColete) {
  this.codFiscal = codFiscal;
  this.nume = nume;
  this.adresa = adresa;
  this.telefon = telefon;
  this.nrColete = nrColete;
  colet = listaColete;
 }

 @Override
 public String forFile() {
  return null;
 }

 public String getCod() {
  return cod;
 }

 public void setCod(String cod) {
  this.cod = cod;
 }

 public String getCodFiscal() {
  return codFiscal;
 }

 public void setCodFiscal(String codFiscal) {
  this.codFiscal = codFiscal;
 }

 @Override
 public String getNume() {
  return nume;
 }

 @Override
 public void setNume(String nume) {
  this.nume = nume;
 }

 @Override
 public String getAdresa() {
  return adresa;
 }

 @Override
 public void setAdresa(String adresa) {
  this.adresa = adresa;
 }

 @Override
 public String getTelefon() {
  return telefon;
 }

 @Override
 public void setTelefon(String telefon) {
  this.telefon = telefon;
 }

 public int getNrColete() {
  return nrColete;
 }

 public void setNrColete(int nrColete) {
  this.nrColete = nrColete;
 }

 public ArrayList<Colet> getColet() {
  return colet;
 }

 public void setColet(ArrayList<Colet> colet) {
  this.colet = colet;
 }
}
