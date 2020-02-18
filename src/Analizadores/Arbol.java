package Analizadores;

import java.util.Collections;
import java.util.LinkedList;

public class Arbol {

    //////////////// ATRIBUTOS
    private NodoArbol raiz;
    private LinkedList<Siguiente> lisSig;
    private LinkedList<Transicion> lisTra;
    private Transicion tra;
    private String nom;
    private int ind;

    //////////////// CONSTRUCTOR
    public Arbol(String nom) {
        this.lisSig = new LinkedList<>();
        this.lisTra = new LinkedList<>();
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

    public void analizar() {
        lisSig = new LinkedList<>();
        lisTra = new LinkedList<>();
        tra = new Transicion("", new LinkedList<>());
        ind = 1;
        anaArb(raiz);
        genTabSig(raiz);
        genTabTra();

        System.out.println("///////");
        obtCodTabSig();
        System.out.println("///////");
        obtCodTabTra();
    }

    //////////////// Aanlisis Arbol
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
                tra.agrTer(nt.obtSim().obtLex());
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

    // Metodo que combina los siguientes
    private LinkedList<Integer> agrAntSig(LinkedList<Integer> la, LinkedList<Integer> ls) {
        for (Integer i : ls) {
            if (!la.contains(i)) {
                la.add(i);
            }
        }
        return la;
    }

    //////////////// Analisis Tabla Siguientes
    // Metodo Generar Tabla de Siguientes
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

    //////////////// Analisis Tabla de Transiciones
    // Metodo Generar Tabla Transiciones
    private void genTabTra() {
        ind = 0;
        lisTra.add(tra);
        lisTra.add(new Transicion("S" + ind++, raiz.obtAnt(), genEspTra()));

        LinkedList<Integer> con;
        // ciclo que recorre las transiciones
        for (int i = 1; i < lisTra.size(); i++) {
            con = lisTra.get(i).obtCon();
            LinkedList<Integer> rep;
            // ciclo que recorre el conjunto de la transicion
            for (int j = 0; j < con.size(); j++) {
                rep = new LinkedList<>();
                rep.add(con.get(j));
                // ciclo que busca duplisidad en el conjunto
                for (int k = 0; k < con.size(); k++) {
                    if (!con.get(j).equals(con.get(k))) {
                        if (busSim(con.get(j)).equals(busSim(con.get(k)))) {
                            rep.add(con.get(k));
                        }
                    }
                }

                // ciclo que combina conjuntos
                LinkedList<Integer> nc = new LinkedList<>();
                for (Integer r : rep) {
                    LinkedList<Integer> ct = obtConSig(r);
                    for (Integer e : ct) {
                        if (!nc.contains(e)) {
                            nc.add(e);
                        }
                    }
                }

                // creo la nueva transicion si no existe
                int pt = obtPosTran(nc);
                if (pt == -1 && !nc.isEmpty()) {
                    lisTra.add(new Transicion("S" + ind++, nc, genEspTra()));
                }

                // inserto la transicion con el simbolo
                pt = obtPosTran(nc);
                int ps = obtPosSim(busSim(rep.get(0)));
                if (pt != -1 & ps != -1) {
                    lisTra.get(i).obtTra().set(ps, lisTra.get(pt).obtNom());
                }
            }
        }

//        System.out.println("\t\t\t\t" + lisTra.get(0).obtTra());
//        for (int i = 1; i < lisTra.size(); i++) {
//            System.out.println(lisTra.get(i).obtNom() + " - \t" + lisTra.get(i).obtCon() + " - \t\t\t" + lisTra.get(i).obtTra());
//        }
    }

    // obtiene posicion transicion
    private int obtPosSim(String sim) {
        LinkedList<String> ls = lisTra.get(0).obtTra();
        for (int i = 0; i < ls.size(); i++) {
            if (sim.equals(ls.get(i))) {
                return i;
            }
        }
        return -1;
    }

    // obtiene el conjunto de sig
    private LinkedList<Integer> obtConSig(Integer num) {
        for (Siguiente s : lisSig) {
            if (num.equals(s.obtSim().obtNum())) {
                return s.obtLisSig();
            }
        }
        return new LinkedList<>();
    }

    // verifica si el Transicion ya existe
    private int obtPosTran(LinkedList<Integer> rep) {
        int r;
        LinkedList<Integer> con;
        for (int i = 1; i < lisTra.size(); i++) {
            con = lisTra.get(i).obtCon();
            if (con.size() == rep.size()) {
                Collections.sort(con);
                Collections.sort(rep);
                r = i;
                for (int j = 0; j < con.size(); j++) {
                    if (!con.get(j).equals(rep.get(j))) {
                        r = -1;
                    }
                }

                if (r != -1) {
                    return i;
                }
            }
        }
        return -1;

    }

    // obtiene el simbolo a partir del numero
    private String busSim(Integer num) {
        String sim = "";
        for (Siguiente s : lisSig) {
            if (s.obtSim().obtNum() == num) {
                return s.obtSim().obtSim().obtLex();
            }
        }
        return sim;
    }

    // genera una lista con los espacios para las transiciones
    private LinkedList<String> genEspTra() {
        LinkedList<String> ll = new LinkedList<>();
        for (String s : tra.obtTra()) {
            ll.add("");
        }
        return ll;
    }

    //////////////// Metodos de Generacion de Codigo
    // Metodo obtener codigo arbol
    public void obtCodArb() {
        ind = 1;
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

    // Metodo obtener codigo tabla Siguientes
    private void obtCodTabSig() {
        System.out.println("////////////////// Tabla de Siguientes");
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
        System.out.println("//////////////////");
    }

    // Metodo obtener codigo tabla de Transiciones
    private void obtCodTabTra() {
        System.out.println("////////////////// Tabla Transiciones");
        int a = lisTra.get(0).obtTra().size() + 1; // titulo
        int b = lisTra.get(0).obtTra().size(); // titulo terminales
        String cod = "<TR>\n\t<TD COLSPAN=\"" + a + "\">Tabla de Transiciones</TD>\n</TR>\n";
        cod += "<TR>\n\t<TD ROWSPAN=\"2\">Estados</TD>\n\t<TD COLSPAN=\"" + b + "\">Terminales</TD>\n</TR>\n";

        cod += "<TR>\n";
        for (int j = 0; j < lisTra.get(0).obtTra().size(); j++) {
            String t = lisTra.get(0).obtTra().get(j);
            if (!t.equals("")) {
                cod += "\t<TD>" + lisTra.get(0).obtTra().get(j) + "</TD>\n";
            }
        }
        cod += "</TR>\n";

        for (int i = 1; i < lisTra.size(); i++) {
            cod += "<TR>\n";
            // estado
            cod += "\t<TD>" + lisTra.get(i).obtNom() + " = {";
            for (int j = 0; j < lisTra.get(i).obtCon().size(); j++) {
                cod += lisTra.get(i).obtCon().get(j);
                if (j < lisTra.get(i).obtCon().size() - 1) {
                    cod += ", ";
                }
            }
            cod += "}</TD>\n";
            // transiciones
            for (int j = 0; j < lisTra.get(i).obtTra().size(); j++) {
                String t = lisTra.get(i).obtTra().get(j);
                if (!t.equals("")) {
                    cod += "\t<TD>" + lisTra.get(i).obtTra().get(j) + "</TD>\n";
                } else {
                    cod += "\t<TD>--</TD>\n";
                }
            }

            cod += "</TR>\n";
        }
        System.out.println(cod);
        System.out.println("//////////////////");
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
