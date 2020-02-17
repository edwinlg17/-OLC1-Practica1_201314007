package Analizadores;

import java.util.LinkedList;

public class Conjunto {

    //////////////// ATRIBUTOS
    private String nom, des;
    private LinkedList<Token> ele;

    //////////////// CONSTRUCTOR
    public Conjunto() {
        this.nom = "";
        this.des = "";
        this.ele = new LinkedList<>();
    }

    public Conjunto(String nom) {
        this.nom = nom;
        this.ele = new LinkedList<>();
    }

    public Conjunto(String nom, String des, LinkedList<Token> ele) {
        this.nom = nom;
        this.des = des;
        this.ele = ele;
    }

    //////////////// METODOS
    public void estNom(String nom) {
        this.nom = nom;
    }

    public void estEle(LinkedList<Token> ele) {
        this.ele = ele;
    }

    public void estDes(String des) {
        this.des = des;
    }

    public String obtNom() {
        return nom;
    }

    public LinkedList<Token> obtEle() {
        return ele;
    }

    public String obtDes() {
        return des;
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
