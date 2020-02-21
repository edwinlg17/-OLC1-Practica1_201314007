package Analizadores;

import java.util.LinkedList;

public class Conjunto {

    //////////////// ATRIBUTOS
    private String nom, des;
    private LinkedList<Token> lisEle;
    private String lisCar;

    //////////////// CONSTRUCTOR
    public Conjunto() {
        this.nom = "";
        this.des = "";
        this.lisEle = new LinkedList<>();
        this.lisCar = "";
    }

    public Conjunto(String nom) {
        this.nom = nom;
        this.lisEle = new LinkedList<>();
    }

    public Conjunto(String nom, String des, LinkedList<Token> ele) {
        this.nom = nom;
        this.des = des;
        this.lisEle = ele;
        this.lisCar = "";
    }

    //////////////// METODOS
    public void estNom(String nom) {
        this.nom = nom;
    }

    public void estEle(LinkedList<Token> ele) {
        this.lisEle = ele;
    }

    public void estLisCar(String lisCar) {
        this.lisCar = lisCar;
    }

    public void estDes(String des) {
        this.des = des;
    }

    public String obtNom() {
        return nom;
    }

    public LinkedList<Token> obtEle() {
        return lisEle;
    }

    public String obtLisCar() {
        return lisCar;
    }

    public String obtDes() {
        return des;
    }

    public void agrEle(Token ele) {
        boolean ver = true;
        for (Token s : this.lisEle) {
            if (s.obtLex().equals(ele)) {
                ver = false;
            }
        }
        if (ver) {
            this.lisEle.add(ele);
        }
    }

}
