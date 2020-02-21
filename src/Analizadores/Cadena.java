package Analizadores;

public class Cadena {

    //////////////// ATRIBUTOS
    private String nom;
    private Token cad;
    private boolean val;

    //////////////// CONSTRUCTOR
    public Cadena(String nom) {
        this.nom = nom;
        this.cad = new Token();
        this.val = false;
    }

    //////////////// METODOS
    public String obtNom() {
        return nom;
    }

    public Token obtCad() {
        return cad;
    }

    public boolean obtVal(){
        return val;
    }
    
    public void estNom(String nom) {
        this.nom = nom;
    }

    public void estCad(Token cad) {
        this.cad = cad;
    }
    
    public void estVal(boolean val){
        this.val = val;
    }

}
