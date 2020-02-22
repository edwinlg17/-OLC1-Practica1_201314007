package Analizadores;

import java.util.LinkedList;
import java.util.ListIterator;

public class AnalizadorSintactico {

    //////////////// ATRIBUTOS
    private ListIterator ite;
    private LinkedList<Conjunto> lisCon;
    private Conjunto con;
    private LinkedList<Arbol> lisExp;
    private Arbol exp;
    private LinkedList<Cadena> lisCad;
    private Cadena cad;
    private LinkedList<Token> lisErr;
    private Token tk;

    //////////////// CONSTRUCTOR
    public AnalizadorSintactico() {
        lisCon = new LinkedList<>();
        lisExp = new LinkedList<>();
        lisCad = new LinkedList<>();
        lisErr = new LinkedList<>();
    }

    ////////////////METODOS
    public void analizar(LinkedList<Token> lisTok) {
        lisTok.add(new Token("", "", 0, 0));
        ite = lisTok.listIterator();
        con = new Conjunto();
        tk = new Token();

        if (ite.hasNext()) {
            tk = (Token) ite.next();
            est0();
        }

    }

    private void est0() {
        con = new Conjunto();
        if (tk.obtTok().equals("tk_con")) {
            sigIte();
            est1();
        } else if (tk.obtTok().equals("tk_id")) {
            exp = new Arbol(tk.obtLex());
            exp.agregar("rai", new Token("tk_pun", ".", 0, 0));
            cad = new Cadena(tk.obtLex());
            sigIte();
            est11();
        } else if (tk.obtTok().equals("tk_llaAbr") || tk.obtTok().equals("tk_llaCie") || tk.obtTok().equals("tk_por")) {
            sigIte();
            est0();
        } else if (tk.obtTok().equals("")) {
            // archivo vacio
        } else {
            Token e = new Token(tk.obtTok(), tk.obtLex(), tk.obtFil(), tk.obtCol(), " No especificado");
            estE(e);
        }
    }

    /////////////////////// Conjuntos
    private void est1() {
        if (tk.obtTok().equals("tk_dosPun")) {
            sigIte();
            est2();
        } else {
            Token e = new Token(tk.obtTok(), tk.obtLex(), tk.obtFil(), tk.obtCol(), "Se esperaba -> :");
            estE(e);
        }
    }

    private void est2() {
        if (tk.obtTok().equals("tk_id") || tk.obtTok().equals("tk_let")) {
            con.estNom(tk.obtLex());
            sigIte();
            est3();
        } else {
            Token e = new Token(tk.obtTok(), tk.obtLex(), tk.obtFil(), tk.obtCol(), "Se esperaba -> IDENTIFICADOR");
            estE(e);
        }
    }

    private void est3() {
        if (tk.obtTok().equals("tk_gui")) {
            sigIte();
            est4();
        } else {
            Token e = new Token(tk.obtTok(), tk.obtLex(), tk.obtFil(), tk.obtCol(), "Se esperaba -> -");
            estE(e);
        }
    }

    private void est4() {
        if (tk.obtTok().equals("tk_may")) {
            sigIte();
            est5();
        } else {
            Token e = new Token(tk.obtTok(), tk.obtLex(), tk.obtFil(), tk.obtCol(), "Se esperaba -> >");
            estE(e);
        }
    }

    private void est5() {
        if (tk.obtTok().equals("tk_let") | tk.obtTok().equals("tk_numero") | tk.obtTok().equals("tk_llaAbr") | tk.obtTok().equals("tk_llaCie") | tk.obtTok().equals("tk_gui") | tk.obtTok().equals("tk_men") | tk.obtTok().equals("tk_may") | tk.obtTok().equals("tk_punCom") | tk.obtTok().equals("tk_por") | tk.obtTok().equals("tk_dosPun") | tk.obtTok().equals("tk_pun") | tk.obtTok().equals("tk_barVer") | tk.obtTok().equals("tk_cieInt") | tk.obtTok().equals("tk_ast") | tk.obtTok().equals("tk_mas") | tk.obtTok().equals("tk_com") | tk.obtTok().equals("tk_sim")) {
            con.agrEle(tk);
            sigIte();
            est6();
        } else {
            Token e = new Token(tk.obtTok(), tk.obtLex(), tk.obtFil(), tk.obtCol(), "Se esperaba -> DIGITO/NUMERO/SIMBOLO");
            estE(e);
        }
    }

    private void est6() {
        if (tk.obtTok().equals("tk_com")) {
            con.estDes("lis");
            sigIte();
            est7();
        } else if (tk.obtTok().equals("tk_til")) {
            con.estDes("ran");
            sigIte();
            est9();
        } else if (tk.obtTok().equals("tk_punCom")) {
            genLisCarCon();
            lisCon.add(con);
            sigIte();
            est0();
        } else {
            Token e = new Token(tk.obtTok(), tk.obtLex(), tk.obtFil(), tk.obtCol(), "Se esperaba -> , ~ ;");
            estE(e);
        }
    }

    private void est7() {
        if (tk.obtTok().equals("tk_let") | tk.obtTok().equals("tk_numero") | tk.obtTok().equals("tk_llaAbr") | tk.obtTok().equals("tk_llaCie") | tk.obtTok().equals("tk_gui") | tk.obtTok().equals("tk_men") | tk.obtTok().equals("tk_may") | tk.obtTok().equals("tk_punCom") | tk.obtTok().equals("tk_por") | tk.obtTok().equals("tk_dosPun") | tk.obtTok().equals("tk_pun") | tk.obtTok().equals("tk_barVer") | tk.obtTok().equals("tk_cieInt") | tk.obtTok().equals("tk_ast") | tk.obtTok().equals("tk_mas") | tk.obtTok().equals("tk_com") | tk.obtTok().equals("tk_sim")) {
            con.agrEle(tk);
            sigIte();
            est8();
        } else {
            Token e = new Token(tk.obtTok(), tk.obtLex(), tk.obtFil(), tk.obtCol(), "Se esperaba -> NUMERO");
            estE(e);
        }
    }

    private void est8() {
        if (tk.obtTok().equals("tk_com")) {
            con.estDes("lis");
            sigIte();
            est7();
        } else if (tk.obtTok().equals("tk_punCom")) {
            genLisCarCon();
            lisCon.add(con);
            if (ite.hasNext()) {
                tk = (Token) ite.next();
            }
            est0();
        } else {
            Token e = new Token(tk.obtTok(), tk.obtLex(), tk.obtFil(), tk.obtCol(), "Se esperaba -> , ;");
            estE(e);
        }
    }

    private void est9() {
        if (tk.obtTok().equals("tk_let") | tk.obtTok().equals("tk_numero") | tk.obtTok().equals("tk_llaAbr") | tk.obtTok().equals("tk_llaCie") | tk.obtTok().equals("tk_gui") | tk.obtTok().equals("tk_men") | tk.obtTok().equals("tk_may") | tk.obtTok().equals("tk_punCom") | tk.obtTok().equals("tk_por") | tk.obtTok().equals("tk_dosPun") | tk.obtTok().equals("tk_pun") | tk.obtTok().equals("tk_barVer") | tk.obtTok().equals("tk_cieInt") | tk.obtTok().equals("tk_ast") | tk.obtTok().equals("tk_mas") | tk.obtTok().equals("tk_com") | tk.obtTok().equals("tk_sim")) {
            con.agrEle(tk);
            sigIte();
            est10();
        } else {
            Token e = new Token(tk.obtTok(), tk.obtLex(), tk.obtFil(), tk.obtCol(), "Se esperaba -> LETRA/NUMERO");
            estE(e);
        }
    }

    private void est10() {
        if (tk.obtTok().equals("tk_punCom")) {
            genLisCarCon();
            lisCon.add(con);
            sigIte();
            est0();
        } else {
            Token e = new Token(tk.obtTok(), tk.obtLex(), tk.obtFil(), tk.obtCol(), "Se esperaba -> ;");
            estE(e);
        }
    }

    /////////////////////// Expreciones Regulares
    private void est11() {
        if (tk.obtTok().equals("tk_gui")) {
            sigIte();
            est12();
        } else if (tk.obtTok().equals("tk_dosPun")) {
            sigIte();
            est23();
        } else {
            Token e = new Token(tk.obtTok(), tk.obtLex(), tk.obtFil(), tk.obtCol(), "Se esperaba -> - :");
            estE(e);
        }
    }

    private void est12() {
        if (tk.obtTok().equals("tk_may")) {
            sigIte();
            est13();
        } else {
            Token e = new Token(tk.obtTok(), tk.obtLex(), tk.obtFil(), tk.obtCol(), "Se esperaba -> >");
            estE(e);
        }
    }

    private void est13() {
        if (tk.obtTok().equals("tk_numero") || tk.obtTok().equals("tk_texto") || tk.obtTok().equals("tk_sim")) {
            exp.agregar("rai-izq", tk);
            est14();
        } else if (tk.obtTok().equals("tk_llaAbr")) {
            est16();
        } else if (tk.obtTok().equals("tk_pun") || tk.obtTok().equals("tk_barVer") || tk.obtTok().equals("tk_ast") || tk.obtTok().equals("tk_mas") || tk.obtTok().equals("tk_cieInt")) {
            if (est19("rai-izq")) {
                est15();
            }
        } else {
            Token e = new Token(tk.obtTok(), tk.obtLex(), tk.obtFil(), tk.obtCol(), "Se esperaba -> NUMERO/TEXTO { . | * + ? hola1");
            estE(e);
        }
    }

    // estados de solo 1 texto o numero
    private void est14() {
        if (tk.obtTok().equals("tk_numero") || tk.obtTok().equals("tk_texto") || tk.obtTok().equals("tk_sim")) {
            sigIte();
            est15();
        } else {
            Token e = new Token(tk.obtTok(), tk.obtLex(), tk.obtFil(), tk.obtCol(), "Se esperaba -> NUMERO/TEXTO");
            estE(e);
        }
    }

    private void est15() {
        if (tk.obtTok().equals("tk_punCom")) {
            exp.agregar("rai-der", new Token("tk_num", "#", 0, 0));
            lisExp.add(exp);
            sigIte();
            est0();
        } else {
            Token e = new Token(tk.obtTok(), tk.obtLex(), tk.obtFil(), tk.obtCol(), "Se esperaba -> ;");
            estE(e);
        }
    }

    // estados de solo 1 conjunto
    private void est16() {
        if (tk.obtTok().equals("tk_llaAbr")) {
            sigIte();
            est17();
        } else {
            Token e = new Token(tk.obtTok(), tk.obtLex(), tk.obtFil(), tk.obtCol(), "Se esperaba -> {");
            estE(e);
        }
    }

    private void est17() {
        if (tk.obtTok().equals("tk_id") || tk.obtTok().equals("tk_let")) {
            sigIte();
            est18();
        } else {
            Token e = new Token(tk.obtTok(), tk.obtLex(), tk.obtFil(), tk.obtCol(), "Se esperaba -> IDENTIFICADOR");
            estE(e);
        }
    }

    private void est18() {
        if (tk.obtTok().equals("tk_llaCie")) {
            sigIte();
            est15();
        } else {
            Token e = new Token(tk.obtTok(), tk.obtLex(), tk.obtFil(), tk.obtCol(), "Se esperaba -> }");
            estE(e);
        }
    }

    // estados recurcivos
    private boolean est19(String pos) {
        if (tk.obtTok().equals("tk_pun") || tk.obtTok().equals("tk_barVer")) {
            exp.agregar(pos, tk);
            sigIte();
            return est20(pos + "-izq") & est20(pos + "-der");
        } else if (tk.obtTok().equals("tk_ast") || tk.obtTok().equals("tk_mas") || tk.obtTok().equals("tk_cieInt")) {
            exp.agregar(pos, tk);
            sigIte();
            return est20(pos + "-izq");
        } else {
            Token e = new Token(tk.obtTok(), tk.obtLex(), tk.obtFil(), tk.obtCol(), "Se esperaba -> . | * + ?");
            estE(e);
            return false;
        }
    }

    private boolean est20(String pos) {
        if (tk.obtTok().equals("tk_numero") || tk.obtTok().equals("tk_texto") || tk.obtTok().equals("tk_sim")) {
            exp.agregar(pos, tk);
            sigIte();
            return true;
        } else if (tk.obtTok().equals("tk_llaAbr")) {
            sigIte();
            return est21(pos);
        } else if (tk.obtTok().equals("tk_pun") || tk.obtTok().equals("tk_barVer") || tk.obtTok().equals("tk_ast") || tk.obtTok().equals("tk_mas") || tk.obtTok().equals("tk_cieInt")) {
            return est19(pos);
        } else {
            Token e = new Token(tk.obtTok(), tk.obtLex(), tk.obtFil(), tk.obtCol(), "Se esperaba -> NUMERO/TEXTO { . | * + ? hola 2");
            estE(e);
            return false;
        }
    }

    private boolean est21(String pos) {
        if (tk.obtTok().equals("tk_id") || tk.obtTok().equals("tk_let")) {
            exp.agregar(pos, tk);
            sigIte();
            return est22();
        } else {
            Token e = new Token(tk.obtTok(), tk.obtLex(), tk.obtFil(), tk.obtCol(), "Se esperaba -> IDENTIFICADOR");
            estE(e);
            return false;
        }
    }

    private boolean est22() {
        if (tk.obtTok().equals("tk_llaCie")) {
            sigIte();
            return true;
        } else {
            Token e = new Token(tk.obtTok(), tk.obtLex(), tk.obtFil(), tk.obtCol(), "Se esperaba -> }");
            estE(e);
            return false;
        }
    }

    /////////////////////// Cadenas de entrada
    private void est23() {
        if (tk.obtTok().equals("tk_texto")) {
            cad.estCad(tk);
            lisCad.add(cad);
            sigIte();
            est24();
        } else {
            Token e = new Token(tk.obtTok(), tk.obtLex(), tk.obtFil(), tk.obtCol(), "Se esperaba -> TEXTO");
            estE(e);
        }
    }

    private void est24() {
        if (tk.obtTok().equals("tk_punCom")) {
            sigIte();
            est0();
        } else {
            Token e = new Token(tk.obtTok(), tk.obtLex(), tk.obtFil(), tk.obtCol(), "Se esperaba -> ;");
            estE(e);
        }
    }

    /////////////////////// Metodo de Error
    private void estE(Token err) {
        if (tk.obtTok().equals("tk_punCom")) {
            lisErr.add(err);
            sigIte();
            est0();
        } else if (tk.obtTok().equals("tk_por")) {
            lisErr.add(err);
            est0();
        } else if (tk.obtTok().equals("tk_con")) {
            lisErr.add(err);
            est0();
        } else if (tk.obtTok().equals("")) {
            // termina la ejecucion
        } else {
            sigIte();
            estE(err);
        }
    }

    private void sigIte() {
        if (ite.hasNext()) {
            tk = (Token) ite.next();
        }
    }

    private void antIte() {
        if (ite.hasPrevious()) {
            tk = (Token) ite.previous();
        }
    }

    // Otros Metodos
    public LinkedList<Conjunto> obtLisCon() {
        return lisCon;
    }

    public LinkedList<Arbol> obtLisExp() {
        return lisExp;
    }

    public LinkedList<Cadena> obtLisCad() {
        return lisCad;
    }

    public LinkedList<Token> obtLisErr() {
        return lisErr;
    }

    private boolean verLetra(char car) {
        boolean ver = false;
        if (car >= 'a' & car <= 'z') {
            ver = true;
        }
        if (car >= 'A' & car <= 'Z') {
            ver = true;
        }
        if (car == 'ñ' | car == 'Ñ') {
            ver = true;
        }
        return ver;
    }

    private boolean verNumero(char car) {
        boolean ver = false;
        if (car >= '0' & car <= '9') {
            ver = true;
        }
        return ver;
    }

    private void genLisCarCon() {
        String st = "";
        if (con.obtDes().equals("lis")) {
            for (Token t : con.obtEle()) {
                st += t.obtLex();
            }
            con.estLisCar(st);
        } else {
            if (con.obtEle().getFirst().obtLex().length() == 1 & con.obtEle().getLast().obtLex().length() == 1) {
                char p = con.obtEle().getFirst().obtLex().charAt(0);
                char u = con.obtEle().getLast().obtLex().charAt(0);
                if (p < u) {
                    if (verNumero(p) & verNumero(u)) {
                        for (int i = (int) p; i <= (int) u; i++) {
                            st += (char) i;
                        }
                        con.estLisCar(st);
                    } else if (verLetra(p) & verLetra(u)) {
                        for (int i = (int) p; i <= (int) u; i++) {
                            st += (char) i;
                        }
                        con.estLisCar(st);
                    } else {
                        for (int i = (int) p; i <= (int) u; i++) {
                            if (!verLetra((char) i) && !verNumero((char) i)) {
                                st += (char) i;
                            }
                        }
                        con.estLisCar(st);
                    }
                }
            }
        }
    }

    public String analizarCadenas() {
        LinkedList<Cadena> lc = lisCad;

        String log = "";
        // obtengo la cadena a analizar
        for (Cadena c : lc) {
            String cad = c.obtCad().obtLex().replace("\"", "");
            int pos = 0;

            // obtengo la exprecion regular
            for (Arbol a : lisExp) {
                if (c.obtNom().equals(a.obtNom())) {
                    LinkedList<Estado> aut = a.genAut();

                    String est = "S0";
                    pos = 0;
                    boolean ver;
                    // recorro la cadena
                    do {
                        ver = false;
                        // obtengo el estado actual con transicion
                        for (Estado e : aut) {
                            // verifico que encontre un posible estado para la transicion
                            if (est.equals(e.obtEstO())) {

                                if (pos >= cad.length() - 1 & e.obtFin()) {
                                    log += "ER: " + c.obtNom() + " La cadena >>" + cad + "<< es una cadena valida... \n";
                                    c.estVal(true);
                                    ver = false;
                                    //System.out.println("La cadena >>" + cad + "<< Es una cadena Invalida... pos:" + pos + " car:" + ct);
                                    break;
                                }

                                String tip = verTip(e.obtSim());
                                // verifico que tipo de transicion
                                String st = e.obtSim().replace("\"", "");
                                if (tip.equals("tex") || tip.equals("num")) {
                                    int fin = pos + st.length();
                                    // verifico que el tamñano de la transicion no sea mas grande que la cadena
                                    if (fin <= cad.length()) {
                                        String ct = cad.substring(pos, fin);
                                        // verifico la transicion con la cadena
                                        if (st.equals(ct)) {
                                            pos += st.length();
                                            ver = true;
                                            est = e.obtEstD();
                                            //System.out.println(e.obtEstO() + " -- " + e.obtSim() + " -- " + e.obtEstD() + " >> " + ct);
                                            break; // si el estado es aceptado
                                        } else {
                                            //System.out.println(e.obtEstO() + " -- " + e.obtSim() + " -- " + e.obtEstD() + " >>" + ct + "<< pos: " + pos + " ERROR");
                                        }
                                    }
                                } else if (tip.equals("id")) {
                                    for (Conjunto co : lisCon) {
                                        if (co.obtNom().equals(st)) {
                                            if (pos <= cad.length() - 1) {
                                                String ct = String.valueOf(cad.charAt(pos)); // caracter a verifivar
                                                String b = co.obtLisCar(); // caracteres permitidos
                                                if (b.contains(ct)) {
                                                    pos++;
                                                    ver = true;
                                                    //System.out.println(e.obtEstO() + " -- " + e.obtSim() + " -- " + e.obtEstD() + " >> " + ct);
                                                    est = e.obtEstD();
                                                } else {
                                                    //System.out.println(e.obtEstO() + " -- " + e.obtSim() + " -- " + e.obtEstD() + " >>" + ct + "<< pos: " + pos + " ERROR");
                                                }
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    } while (ver);
                    break; // si el ID de la Cadena corresponde con ID del la ER
                }
            }
            if (!c.obtVal()) {
                //System.out.println(c.obtNom() + " " + pos + " " + cad.length());
                if (pos > cad.length() - 1) {
                    pos = cad.length() - 1;
                }
                String ct = String.valueOf(cad.charAt(pos));
                log += "ER: " + c.obtNom() + " La cadena >>" + cad + "<< es una cadena invalida... Error car:>>" + ct + "<< pos:" + ++pos + "\n";

            }
        }
        return log;
    }

    private String verTip(String l) {
        if (!l.equals("")) {
            if (l.length() >= 2 & l.charAt(0) == '"' & l.charAt(l.length() - 1) == '"') {
                return "tex";
            } else if (l.length() >= 1 & verNumero(l.charAt(0))) {
                return "num";
            } else if (l.length() >= 1 & verLetra(l.charAt(0))) {
                return "id";
            }
        }
        return "";
    }
}
