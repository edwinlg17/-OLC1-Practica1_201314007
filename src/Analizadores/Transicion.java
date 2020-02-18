package Analizadores;

import java.util.LinkedList;

public class Transicion {

    //////////////// ATRIBUTOS
    private String nom;
    private LinkedList<Integer> con;
    private LinkedList<String> tra;

    //////////////// CONSTRUCTOR
    public Transicion(String nom, LinkedList<Integer> con) {
        this.nom = nom;
        this.con = con;
        this.tra = new LinkedList<>();
    }

    public Transicion(String nom, LinkedList<Integer> con, LinkedList<String> tra) {
        this.nom = nom;
        this.con = con;
        this.tra = tra;
    }

    //////////////// METODOS
    // Metodo Obtener
    public String obtNom() {
        return nom;
    }

    public LinkedList<Integer> obtCon() {
        return con;
    }

    public LinkedList<String> obtTra() {
        return tra;
    }

    // Metodo Establecer
    public void estNom(String nom) {
        this.nom = nom;
    }

    public void estCon(LinkedList<Integer> con) {
        this.con = con;
    }

    public void estTra(LinkedList<String> tra) {
        this.tra = tra;
    }

    public void agrTer(String ter) {
        if (!tra.contains(ter) && !ter.equals("#")) {
            tra.add(ter);
        }
    }

}
