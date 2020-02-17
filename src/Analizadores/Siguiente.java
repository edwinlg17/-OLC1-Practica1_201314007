package Analizadores;

import java.util.LinkedList;

public class Siguiente {

    //////////////// ATRIBUTOS
    private NodoArbol sim;
    private LinkedList<Integer> lisSig;

    //////////////// CONSTRUCTOR
    public Siguiente() {
        this.sim = new NodoArbol();
        this.lisSig = new LinkedList<>();
    }

    public Siguiente(NodoArbol sim) {
        this.sim = sim;
        this.lisSig = new LinkedList<>();
    }

    //////////////// METODOS
    public NodoArbol obtSim() {
        return sim;
    }

    public LinkedList<Integer> obtLisSig() {
        return lisSig;
    }

    public void estSim(NodoArbol sim) {
        this.sim = sim;
    }

    public void estLisSig(LinkedList<Integer> lisSig) {
        this.lisSig = lisSig;
    }

    public void agrLisSig(Integer num) {
        lisSig.add(num);
    }

}
