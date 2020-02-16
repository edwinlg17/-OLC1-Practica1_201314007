package Analizadores;

import java.util.LinkedList;

public class NodoArbol {

    //////////////// ATRIBUTOS
    private String sim, anu;
    private LinkedList<Integer> ant, sig;
    private int num;
    private NodoArbol izq, der;

    //////////////// CONSTRUCTOR
    public NodoArbol() {
        this.sim = "";
        this.anu = "";
        this.ant = new LinkedList<>();
        this.sig = new LinkedList<>();
        this.num = -1;
        this.izq = null;
        this.der = null;
    }

    //////////////// METODOS
    // metodos establecer
    public void estSim(String sim) {
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

    public void estIzq(NodoArbol izq) {
        this.izq = izq;
    }

    public void estDer(NodoArbol der) {
        this.der = der;
    }

    // metodos obtener
    public String obtSim() {
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

    public NodoArbol obtIzq() {
        return izq;
    }

    public NodoArbol obtDer() {
        return der;
    }

}
