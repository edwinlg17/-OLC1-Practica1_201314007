package Analizadores;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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

    public void generarGraficos() {

        lisSig = new LinkedList<>();
        lisTra = new LinkedList<>();
        tra = new Transicion("", new LinkedList<>());
        ind = 1;
        anaArb(raiz);
        genTabSig(raiz);
        genTabTra();

//        System.out.println("///////");
        obtCodArb();
//        System.out.println("///////");
        obtCodTabSig();
//        System.out.println("///////");
        obtCodTabTra();
//        System.out.println("///////");
        obtCodAut();

        obtAut();
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

    private LinkedList<Estado> obtAut() {
        LinkedList<Estado> na = new LinkedList<>();
        for (int i = 1; i < lisTra.size(); i++) {
            //na.add(new Estado(t.obtNom(), t.o, nom))
            for (int j = 0; j < lisTra.get(i).obtTra().size(); j++) {
                if (!lisTra.get(i).obtTra().get(j).equals("")) {
                    na.add(new Estado(lisTra.get(i).obtNom(), lisTra.get(0).obtTra().get(j), lisTra.get(i).obtTra().get(j)));
                    System.out.println(lisTra.get(i).obtNom() + " -- " + lisTra.get(0).obtTra().get(j) + " -> " + lisTra.get(i).obtTra().get(j));
                }
                
            }
        }
        return na;
    }

    //////////////// Metodos de Generacion de Codigo
    // Metodo obtener codigo arbol
    public void obtCodArb() {
        ind = 1;
        String cod = "graph [label=\"Arbol --  " + nom + "\", labelloc=t, fontsize=30]; ";
        cod += ind + "[label=\"" + raiz.obtSim().obtLex().replace("\"", "\\\"") + "\"];\n";
        if (raiz.obtNum() != -1) {
            cod += ind + ":s->" + ind + ":s[color=transparent, taillabel = <<font color=\"green\">" + raiz.obtNum() + "</font>>];\n";
        }
        cod = obtCodArb(raiz, cod);

        cod = "digraph G {\n"
                + "node[fixedsize=true, shape=circle];\n"
                + "graph [nodesep=0.9];\n"
                + "edge [dir=none];\n\n"
                + cod
                + "\n\n}";

        creArc("arbTem_" + nom, System.getProperty("user.home") + "/desktop/Diagramas/Arboles/" + nom, cod);
    }

    private String obtCodArb(NodoArbol nt, String cod) {
        if (nt != null) {
            String pad = String.valueOf(ind);
            cod += ind + ":n->" + ind + ":n[color=transparent, taillabel = <<font color=\"red\">" + nt.obtAnu() + "</font>>];\n";
            if (nt.obtNum() != -1) {
                cod += ind + ":s->" + ind + ":s[color=transparent, taillabel = <<font color=\"green\">" + nt.obtNum() + "</font>>];\n";
            }
            if (!nt.obtAnt().isEmpty()) {
                cod += ind + ":w->" + ind + ":w[color=transparent, taillabel = <<font color=\"blue\">" + obtLisSigAnt(nt.obtAnt()) + "</font>>];\n";
            }
            if (!nt.obtSig().isEmpty()) {
                cod += ind + ":e->" + ind + ":e[color=transparent, taillabel = <<font color=\"orange\">" + obtLisSigAnt(nt.obtSig()) + "</font>>];\n";
            }

            if (nt.izq != null) {
                cod += String.valueOf(++ind) + "[label=\"" + nt.izq.obtSim().obtLex().replace("\"", "\\\"") + "\"];\n";
                cod += pad + "->" + ind + ";\n";
                cod = obtCodArb(nt.izq, cod);
            }
            if (nt.der != null) {
                cod += String.valueOf(++ind) + "[label=\"" + nt.der.obtSim().obtLex().replace("\"", "\\\"") + "\"];\n";
                cod += pad + "->" + ind + ";\n";
                cod = obtCodArb(nt.der, cod);
            }
        }
        return cod;
    }

    //Otros Metodos de codigo arbol
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

    // Metodo obtener codigo tabla Siguientes
    private void obtCodTabSig() {
        String cod = "<TR>\n\t<TD COLSPAN=\"3\">Tabla de Siguientes -- " + nom + "</TD>\n</TR>\n";
        cod += "<TR>\n\t<TD>#</TD>\n\t<TD>Simbolo</TD>\n\t<TD>Siguientes</TD>\n</TR>\n";
        for (Siguiente s : lisSig) {
            cod += "<TR>\n";
            cod += "\t<TD>" + s.obtSim().obtNum() + "</TD>\n";
            if (s.obtSim().obtSim().obtLex().contains("<")) {
                cod += "\t<TD>&lt;</TD>\n";
            } else if (s.obtSim().obtSim().obtLex().contains(">")) {
                cod += "\t<TD>&gt;</TD>\n";
            } else if (s.obtSim().obtSim().obtLex().contains("&")) {
                cod += "\t<TD>&amp;</TD>\n";
            } else {
                cod += "\t<TD>" + s.obtSim().obtSim().obtLex() + "</TD>\n";
            }
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

        cod = "digraph G {\n"
                + "node [shape=plaintext];\n"
                + "tabla[label=<\n"
                + "<TABLE BORDER=\"0\" CELLBORDER=\"1\" CELLSPACING=\"0\" CELLPADDING=\"4\">\n"
                + cod
                + "</TABLE>>];\n"
                + "\n\n}";

        creArc("sigTem_" + nom, System.getProperty("user.home") + "/desktop/Diagramas/Siguientes/" + nom, cod);
    }

    // Metodo obtener codigo tabla de Transiciones
    private void obtCodTabTra() {
        int a = lisTra.get(0).obtTra().size() + 1; // titulo
        int b = lisTra.get(0).obtTra().size(); // titulo terminales
        String cod = "<TR>\n\t<TD COLSPAN=\"" + a + "\">Tabla de Transiciones -- " + nom + "</TD>\n</TR>\n";
        cod += "<TR>\n\t<TD ROWSPAN=\"2\">Estados</TD>\n\t<TD COLSPAN=\"" + b + "\">Terminales</TD>\n</TR>\n";

        cod += "<TR>\n";
        for (int j = 0; j < lisTra.get(0).obtTra().size(); j++) {
            String t = lisTra.get(0).obtTra().get(j);
            if (!t.equals("")) {
                if (lisTra.get(0).obtTra().get(j).contains("<")) {
                    cod += "\t<TD>&lt;</TD>\n";
                } else if (lisTra.get(0).obtTra().get(j).contains(">")) {
                    cod += "\t<TD>&gt;</TD>\n";
                } else if (lisTra.get(0).obtTra().get(j).contains("&")) {
                    cod += "\t<TD>&amp;</TD>\n";
                } else {
                    cod += "\t<TD>" + lisTra.get(0).obtTra().get(j) + "</TD>\n";
                }
            }
        }
        cod += "</TR>\n";

        for (int i = 1; i < lisTra.size(); i++) {
            cod += "<TR>\n";
            // estado
            int u = lisTra.get(i).obtCon().getLast();

            if (!busSim(u).equals("#")) {
                cod += "\t<TD>" + lisTra.get(i).obtNom() + " = {";
            } else {
                cod += "\t<TD> * " + lisTra.get(i).obtNom() + " = {";
            }

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

        cod = "digraph G {\n"
                + "node [shape=plaintext];\n"
                + "tabla[label=<\n"
                + "<TABLE BORDER=\"0\" CELLBORDER=\"1\" CELLSPACING=\"0\" CELLPADDING=\"4\">\n"
                + cod
                + "</TABLE>>];\n"
                + "\n\n}";

        creArc("traTem_" + nom, System.getProperty("user.home") + "/desktop/Diagramas/Transiciones/" + nom, cod);

//        System.out.println("////////////////// Tabla Transiciones");
//        System.out.println(cod);
//        System.out.println("//////////////////");
    }

    // Metodo obtener codigo Automata
    private void obtCodAut() {
        String tra = "", ran = "{rank=same S0 };\n";
        String cod = "graph [label=\"Automata --  " + nom + "\", labelloc=t, fontsize=30]; ";
        for (int i = 1; i < lisTra.size(); i++) {

            int u = lisTra.get(i).obtCon().getLast();
            if (busSim(u).equals("#")) {
                cod += lisTra.get(i).obtNom() + "[shape=doublecircle];\n";
            }

            // codigo transiciones graphviz
            for (String s : lisTra.get(i).obtTra()) {
                if (!s.equals("") & !tra.contains(lisTra.get(i).obtNom() + "->" + s)) {
                    String la = "";
                    for (int j = 0; j < lisTra.get(i).obtTra().size(); j++) {
                        if (s.equals(lisTra.get(i).obtTra().get(j))) {
                            la += lisTra.get(0).obtTra().get(j) + ", ";
                        }
                    }
                    la = "[label=\"" + la.replace("\"", "\\\"") + "\"];\n";
                    la = la.replace(", \"];", "\"];");
                    tra += lisTra.get(i).obtNom() + "->" + s + la;
                }
            }

            // codigo de rank graphviz
            String tr = "";
            for (String s : lisTra.get(i).obtTra()) {
                if (!ran.contains(s) & !tr.contains(s)) {
                    tr += s + " ";
                }
            }
            if (!tr.equals("")) {
                ran += "{rank=same " + tr + "};\n";
            }

        }

        cod = "digraph G {\n"
                + "node[fixedsize=true, shape=circle];\n"
                + "graph [layout = dot, rankdir = LR, rank= same];\n"
                + ran + tra + cod
                + "\n\n}";

        creArc("autTem_" + nom, System.getProperty("user.home") + "/desktop/Diagramas/Automatas/" + nom, cod);

//        System.out.println("////////////////// Automata");
//        System.out.println(ran);
//        System.out.println(tra);
//        System.out.println(cod);
//        System.out.println("//////////////////");
    }

    //////////////// Otros metodos
    // Metodos Establecer y Obtener
    public void estNom(String nom) {
        this.nom = nom;
    }

    public String obtNom() {
        return nom;
    }

    private void creArc(String nom, String rut, String tex) {
        try {
            String rt = System.getProperty("user.home") + "/desktop/Diagramas/Codigo/" + nom + ".dot";
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(rt)));
            bw.write(tex);
            bw.close();

            Process proceso = Runtime.getRuntime().exec("dot " + rt + " -o " + rut + ".png -Tpng -Gcharset=utf8");
        } catch (Exception e) {
        }
    }

}
