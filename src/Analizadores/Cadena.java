package Analizadores;

public class Cadena {

    //////////////// ATRIBUTOS
    private String nom;
    private Token cad;

    //////////////// CONSTRUCTOR
    public Cadena(String nom) {
        this.nom = nom;
        this.cad = new Token();
    }

    //////////////// METODOS
    public String obtNom() {
        return nom;
    }

    public Token obtCad() {
        return cad;
    }

    public void estNom(String nom) {
        this.nom = nom;
    }

    public void estCad(Token cad) {
        this.cad = cad;
    }

}
