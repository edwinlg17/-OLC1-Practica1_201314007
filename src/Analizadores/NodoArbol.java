package Analizadores;

import java.util.LinkedList;

public class NodoArbol {

    //////////////// ATRIBUTOS
    private Token sim;
    private String anu;
    private LinkedList<Integer> ant, sig;
    private int num;
    NodoArbol izq, der;
//    private NodoArbol izq, der;

    //////////////// CONSTRUCTOR
    public NodoArbol() {
        this.sim = new Token();
        this.anu = "";
        this.ant = new LinkedList<>();
        this.sig = new LinkedList<>();
        this.num = -1;
        this.izq = null;
        this.der = null;
    }

    public NodoArbol(Token sim) {
        this.sim = sim;
        this.anu = "";
        this.ant = new LinkedList<>();
        this.sig = new LinkedList<>();
        this.num = -1;
        this.izq = null;
        this.der = null;
    }

    //////////////// METODOS
    // metodos establecer
    public void estSim(Token sim) {
        this.sim = sim;
    }

    public void estAnu(String anu) {
        this.anu = anu;
    }

    public void estAnt(LinkedList<Integer> ant) {
        this.ant = ant;
    }

    public void estSig(LinkedList<Integer> sig) {
        this.sig = sig;
    }

    public void estNun(int num) {
        this.num = num;
    }

    // metodos obtener
    public Token obtSim() {
        return sim;
    }

    public String obtAnu() {
        return anu;
    }

    public LinkedList<Integer> obtAnt() {
        return ant;
    }

    public LinkedList<Integer> obtSig() {
        return sig;
    }

    public int obtNum() {
        return num;
    }

}
