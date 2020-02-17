package Analizadores;

import java.util.LinkedList;

public class Conjunto {

    //////////////// ATRIBUTOS
    private String nom;
    private LinkedList<Token> ele;

    //////////////// CONSTRUCTOR
    public Conjunto() {
        this.nom = "";
        this.ele = new LinkedList<>();
    }

    public Conjunto(String nom) {
        this.nom = nom;
        this.ele = new LinkedList<>();
    }

    public Conjunto(String nom, LinkedList<Token> ele) {
        this.nom = nom;
        this.ele = ele;
    }

    //////////////// METODOS
    public void estNom(String nom) {
        this.nom = nom;
    }

    public void estEle(LinkedList<Token> ele) {
        this.ele = ele;
    }

    public String obtNom() {
        return nom;
    }

    public LinkedList<Token> obtEle() {
        return ele;
    }

    public void agrEle(Token ele) {
        boolean ver = true;
        for (Token s : this.ele) {
            if (s.obtLex().equals(ele)) {
                ver = false;
            }
        }
        if (ver) {
            this.ele.add(ele);
        }
    }

}
