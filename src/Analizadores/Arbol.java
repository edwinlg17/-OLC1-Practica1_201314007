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

    private void verAnu(NodoArbol nt) {
        if (nt != null) {
            if (nt.obtSim().obtTok().equals("tk_ast") || nt.obtSim().obtTok().equals("tk_cieInt")) {
                nt.estAnu("A");
            }
            boolean hoj = true;
            if (nt.izq != null) {
                hoj = false;
                verAnu(nt.izq);
            }
            if (nt.der != null) {
                hoj = false;
                verAnu(nt.der);
            }
            if (hoj) {
                nt.estNun(ind++);
                nt.estAnu("N");
            }
            if (nt.obtSim().obtTok().equals("tk_pun")) {
                if (nt.izq.obtAnu().equals("A") && nt.der.obtAnu().equals("A")) {
                    nt.estAnu("A");
                } else {
                    nt.estAnu("N");
                }
            } else if (nt.obtSim().obtTok().equals("tk_barVer")) {
                if (nt.izq.obtAnu().equals("A") || nt.der.obtAnu().equals("A")) {
                    nt.estAnu("A");
                } else {
                    nt.estAnu("N");
                }
            } else if (nt.obtSim().obtTok().equals("tk_mas")) {
                if (nt.izq.obtAnu().equals("A")) {
                    nt.estAnu("A");
                } else {
                    nt.estAnu("N");
                }
            }
        }
    }

    // Metodo Imprimir Arbol
    public void impArb() {
        int ind = 0;
        verAnu(raiz);
        System.out.println("///////////\n");
        obtCodArb();
    }

    public void impArb(NodoArbol nt) {
        if (nt != null) {
            System.out.println(nt.obtSim().obtTok() + " anu: " + nt.obtAnu());
            if (nt.izq != null) {
                impArb(nt.izq);
            }
            if (nt.der != null) {
                impArb(nt.der);
            }
        }
    }

    // Metodo obtCodArb
    public void obtCodArb() {
        ind = 0;
        System.out.println("0" + "[label=\"" + raiz.obtSim().obtLex().replace("\"", "\\\"") + "\"];");
        if (raiz.obtNum() != -1) {
            System.out.println("0:s->0:s[color=transparent, taillabel = <<font color=\"green\">" + raiz.obtNum() + "</font>>];");
            System.out.println("0:w->0:w[color=transparent, taillabel = <<font color=\"blue\">" + raiz.obtNum() + "</font>>];");
            System.out.println("0:e->0:e[color=transparent, taillabel = <<font color=\"orange\">" + raiz.obtNum() + "</font>>];");
        }
        obtCodArb(raiz);
    }

    private void obtCodArb(NodoArbol nt) {
        if (nt != null) {
            String pad = String.valueOf(ind);
            System.out.println(ind + ":n->" + ind + ":n[color=transparent, taillabel = <<font color=\"red\"> " + nt.obtAnu() + "</font>>];");
            if (nt.obtNum() != -1) {
                System.out.println(ind + ":s->" + ind + ":s[color=transparent, taillabel = <<font color=\"green\">" + nt.obtNum() + "</font>>];");
                System.out.println(ind + ":w->" + ind + ":w[color=transparent, taillabel = <<font color=\"blue\">" + nt.obtNum() + "</font>>];");
                System.out.println(ind + ":e->" + ind + ":e[color=transparent, taillabel = <<font color=\"orange\">" + nt.obtNum() + "</font>>];");
            }
            if (nt.izq != null) {
                System.out.println(String.valueOf(++ind) + "[label=\"" + nt.izq.obtSim().obtLex().replace("\"", "\\\"") + "\"];");
                System.out.println(pad + "->" + ind + ";");
                obtCodArb(nt.izq);
            }
            if (nt.der != null) {
                System.out.println(String.valueOf(++ind) + "[label=\"" + nt.der.obtSim().obtLex().replace("\"", "\\\"") + "\"];");
                System.out.println(pad + "->" + ind + ";");
                obtCodArb(nt.der);
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
