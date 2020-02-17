package Analizadores;

import java.util.Collections;
import java.util.LinkedList;

public class Arbol {

    //////////////// ATRIBUTOS
    private NodoArbol raiz;
    private LinkedList<Siguiente> lisSig;
    private String nom;
    private int ind;

    //////////////// CONSTRUCTOR
    public Arbol(String nom) {
        this.lisSig = new LinkedList<>();
        this.nom = nom;
        this.raiz = null;
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

    // Metodo Analizar Arbol
    private void anaArb(NodoArbol nt) {
        if (nt != null) {
            //////////////////// Anulables
            // Anulable para * y ?
            if (nt.obtSim().obtTok().equals("tk_ast") || nt.obtSim().obtTok().equals("tk_cieInt")) {
                nt.estAnu("A");
            }

            boolean hoj = true;
            if (nt.izq != null) {
                hoj = false;
                anaArb(nt.izq);
            }
            if (nt.der != null) {
                hoj = false;
                anaArb(nt.der);
            }

            // Anulable para hoja
            if (hoj) {
                lisSig.add(new Siguiente(nt));
                nt.agrAnt(ind);
                nt.agrSig(ind);
                nt.estNun(ind++);
                nt.estAnu("N");
            }

            // Anulable para . | y +
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
            //////////////////// Siguientes
            if (nt.obtSim().obtTok().equals("tk_pun")) {
                // anteriores
                nt.estAnt(agrAntSig(nt.obtAnt(), nt.izq.obtAnt()));
                if (nt.izq.obtAnu().equals("A")) {
                    nt.estAnt(agrAntSig(nt.obtAnt(), nt.der.obtAnt()));
                }
                // siguientes
                if (nt.der.obtAnu().equals("A")) {
                    nt.estSig(agrAntSig(nt.obtSig(), nt.izq.obtSig()));
                }
                nt.estSig(agrAntSig(nt.obtSig(), nt.der.obtSig()));
            } else if (nt.obtSim().obtTok().equals("tk_barVer")) {
                // anteriores
                nt.estAnt(agrAntSig(nt.obtAnt(), nt.izq.obtAnt()));
                nt.estAnt(agrAntSig(nt.obtAnt(), nt.der.obtAnt()));
                // siguientes
                nt.estSig(agrAntSig(nt.obtSig(), nt.izq.obtSig()));
                nt.estSig(agrAntSig(nt.obtSig(), nt.der.obtSig()));
            } else if (nt.obtSim().obtTok().equals("tk_ast") || nt.obtSim().obtTok().equals("tk_mas") || nt.obtSim().obtTok().equals("tk_cieInt")) {
                nt.estAnt(agrAntSig(nt.obtAnt(), nt.izq.obtAnt()));
                nt.estSig(agrAntSig(nt.obtSig(), nt.izq.obtSig()));
            }
        }
    }

    // Otros Metodo de Analizar Arbol
    private LinkedList<Integer> agrAntSig(LinkedList<Integer> la, LinkedList<Integer> ls) {
        for (Integer i : ls) {
            la.add(i);
        }
        return la;
    }

    // Metodo Generar Tabla de Siguientes
    private void genTabSig() {
        lisSig = new LinkedList<>();
        ind = 1;
        anaArb(raiz);
        genTabSig(raiz);
        String cod = "<TR>\n\t<TD COLSPAN=\"3\">Tabla de Siguientes</TD>\n</TR>\n";
        cod += "<TR>\n\t<TD>#</TD>\n\t<TD>Simbolo</TD>\n\t<TD>Siguientes</TD>\n</TR>\n";
        for (Siguiente s : lisSig) {
            cod += "<TR>\n";
            cod += "\t<TD>" + s.obtSim().obtNum() + "</TD>\n";
            cod += "\t<TD>" + s.obtSim().obtSim().obtLex() + "</TD>\n";
            cod += "\t<TD>";
            Collections.sort(s.obtLisSig());
            for (int i = 0; i < s.obtLisSig().size(); i++) {
                cod += s.obtLisSig().get(i);
                if (i < s.obtLisSig().size() - 1) {
                    cod += ", ";
                }
            }
            cod += "</TD>\n";
            cod += "</TR>\n";
        }
        System.out.println(cod);
    }

    private void genTabSig(NodoArbol nt) {
        if (nt != null) {
            if (nt.obtSim().obtTok().equals("tk_pun")) {
                for (Integer s : nt.izq.obtSig()) {
                    for (Integer a : nt.der.obtAnt()) {
                        for (Siguiente na : lisSig) {
                            if (s == na.obtSim().obtNum()) {
                                na.agrLisSig(a);
                                break;
                            }
                        }
                    }
                }
            } else if (nt.obtSim().obtTok().equals("tk_ast") || nt.obtSim().obtTok().equals("tk_mas")) {
                for (Integer s : nt.obtSig()) {
                    for (Integer a : nt.obtAnt()) {
                        for (Siguiente na : lisSig) {
                            if (s == na.obtSim().obtNum()) {
                                na.agrLisSig(a);
                                break;
                            }
                        }
                    }
                }
            }
            if (nt.izq != null) {
                genTabSig(nt.izq);
            }
            if (nt.der != null) {
                genTabSig(nt.der);
            }
        }
    }

    // Metodo Imprimir Arbol
    public void impArb() {
        genTabSig();
        System.out.println("///////////\n");
        //obtCodArb();
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
        System.out.println(ind + "[label=\"" + raiz.obtSim().obtLex().replace("\"", "\\\"") + "\"];");
        if (raiz.obtNum() != -1) {
            System.out.println(ind + ":s->" + ind + ":s[color=transparent, taillabel = <<font color=\"green\">" + raiz.obtNum() + "</font>>];");
        }
        obtCodArb(raiz);
    }

    private void obtCodArb(NodoArbol nt) {
        if (nt != null) {
            String pad = String.valueOf(ind);
            System.out.println(ind + ":n->" + ind + ":n[color=transparent, taillabel = <<font color=\"red\">" + nt.obtAnu() + "</font>>];");
            if (nt.obtNum() != -1) {
                System.out.println(ind + ":s->" + ind + ":s[color=transparent, taillabel = <<font color=\"green\">" + nt.obtNum() + "</font>>];");
            }
            if (!nt.obtAnt().isEmpty()) {
                System.out.println(ind + ":w->" + ind + ":w[color=transparent, taillabel = <<font color=\"blue\">" + obtLisSigAnt(nt.obtAnt()) + "</font>>];");
            }
            if (!nt.obtSig().isEmpty()) {
                System.out.println(ind + ":e->" + ind + ":e[color=transparent, taillabel = <<font color=\"orange\">" + obtLisSigAnt(nt.obtSig()) + "</font>>];");
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

    // Otros Metodos de obtCodArb
    private String obtLisSigAnt(LinkedList<Integer> lis) {
        String cad = "";
        for (int i = 0; i < lis.size(); i++) {
            cad += lis.get(i);
            if (i < lis.size() - 1) {
                cad += ", ";
            }
        }
        return cad;
    }

    // Metodos Establecer y Obtener
    public void estNom(String nom) {
        this.nom = nom;
    }

    public String obtNom() {
        return nom;
    }

}
