package Analizadores;

public class Arbol {

    //////////////// ATRIBUTOS
    private NodoArbol raiz;
    private String nom;
    private int ind;

    //////////////// CONSTRUCTOR
    public Arbol(String nom) {
        this.nom = nom;
        raiz = null;
    }

    //////////////// METODOS
    // Metodo Agregar
    public void agregar(String pos, Token sim) {
        NodoArbol nue = new NodoArbol(sim);
        if (raiz != null) {
            NodoArbol nt = raiz;
            String[] r = pos.split("-");
            for (int i = 0; i < r.length - 1; i++) {
                if (r[i].equals("izq")) {
                    nt = nt.izq;
                } else if (r[i].equals("der")) {
                    nt = nt.der;
                }
            }
            if (r[r.length - 1].equals("izq")) {
                nt.izq = nue;
            }
            if (r[r.length - 1].equals("der")) {
                nt.der = nue;
            }
        } else {
            raiz = nue;
        }
    }

    // Metodo Imprimir
    public void impArb() {
        ind = 0;
        NodoArbol nt = raiz;
        if (nt != null) {
            //System.out.print(nt.obtSim());
            String pad = String.valueOf(ind);
            System.out.println(pad + "[label=\"" + nt.obtSim().obtLex().replace("\"", "") + "\"];");
            if (nt.izq != null) {
                System.out.println(String.valueOf(++ind) + "[label=\"" + nt.izq.obtSim().obtLex().replace("\"", "") + "\"];");
                System.out.println(pad + "->" + ind + ";");
                impArb(nt.izq);
            }
            if (nt.der != null) {
                System.out.println(String.valueOf(++ind) + "[label=\"" + nt.der.obtSim().obtLex().replace("\"", "") + "\"];");
                System.out.println(pad + "->" + ind + ";");
                impArb(nt.der);
            }
        }
        System.out.println("");
    }

    private void impArb(NodoArbol nt) {
        if (nt != null) {
            String pad = String.valueOf(ind);
            if (nt.izq != null) {
                System.out.println(String.valueOf(++ind) + "[label=\"" + nt.izq.obtSim().obtLex().replace("\"", "") + "\"];");
                System.out.println(pad + "->" + ind + ";");
                impArb(nt.izq);
            }
            if (nt.der != null) {
                System.out.println(String.valueOf(++ind) + "[label=\"" + nt.der.obtSim().obtLex().replace("\"", "") + "\"];");
                System.out.println(pad + "->" + ind + ";");
                impArb(nt.der);
            }
        }
    }


    // Metodos Establecer y Obtener
    public void estNom(String nom) {
        this.nom = nom;
    }

    public String obtNom() {
        return nom;
    }

}
