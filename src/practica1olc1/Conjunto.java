package practica1olc1;

import java.util.LinkedList;

public class Conjunto {
    //////////////// ATRIBUTOS
    private String nom;
    private LinkedList<String> ele;
    
    //////////////// CONSTRUCTOR
    public Conjunto() {
        this.nom = "";
        this.ele = new LinkedList<>();
    }
    
    public Conjunto(String nom) {
        this.nom = nom;
        this.ele = new LinkedList<>();
    }
    
    public Conjunto(String nom, LinkedList<String> ele) {
        this.nom = nom;
        this.ele = ele;
    }

    //////////////// METODOS

    public void estNom(String nom) {
        this.nom = nom;
    }

    public void estEle(LinkedList<String> ele) {
        this.ele = ele;
    }

    public String obtNom() {
        return nom;
    }

    public LinkedList<String> obtEle() {
        return ele;
    }
    
    public void agrEle(String ele){
        this.ele.add(ele);
    }
    
}
