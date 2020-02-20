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
        if (tk.obtTok().equals("tk_id")) {
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
        //| tk.obtTok().equals("tk_llaAbr") | tk.obtTok().equals("tk_llaCie") | tk.obtTok().equals("tk_gui") | tk.obtTok().equals("tk_men") | tk.obtTok().equals("tk_may") | tk.obtTok().equals("tk_punCom") | tk.obtTok().equals("tk_por") | tk.obtTok().equals("tk_dosPun") | tk.obtTok().equals("tk_pun") | tk.obtTok().equals("tk_barVer") | tk.obtTok().equals("tk_cieInt") | tk.obtTok().equals("tk_ast") | tk.obtTok().equals("tk_mas") | tk.obtTok().equals("tk_com") | tk.obtTok().equals("tk_sim")
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
            if (con.obtDes().equals("ran")) {
                LinkedList<Token> le = con.obtEle();
                Token ti = le.getFirst();
                Token tf = le.getLast();
                if (ti.obtLex().length() == 1 & tf.obtLex().length() == 1) {
                    if (ti.obtLex().charAt(0) < tf.obtLex().charAt(0)) {
                        lisCon.add(con);
                    }
                }
            } else {
                lisCon.add(con);
            }
            sigIte();
            est0();
        } else {
            Token e = new Token(tk.obtTok(), tk.obtLex(), tk.obtFil(), tk.obtCol(), "Se esperaba -> , ~ ;");
            estE(e);
        }
    }
    
    private void est7() {
        //
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
            if (con.obtDes().equals("ran")) {
                LinkedList<Token> le = con.obtEle();
                Token ti = le.getFirst();
                Token tf = le.getLast();
                if (ti.obtLex().length() == 1 & tf.obtLex().length() == 1) {
                    if (ti.obtLex().charAt(0) < tf.obtLex().charAt(0)) {
                        lisCon.add(con);
                    }
                }
            } else {
                lisCon.add(con);
            }
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
            if (con.obtDes().equals("ran")) {
                LinkedList<Token> le = con.obtEle();
                Token ti = le.getFirst();
                Token tf = le.getLast();
                if (ti.obtLex().length() == 1 & tf.obtLex().length() == 1) {
                    if (ti.obtLex().charAt(0) < tf.obtLex().charAt(0)) {
                        lisCon.add(con);
                    }
                }
            } else {
                lisCon.add(con);
            }
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
        if (tk.obtTok().equals("tk_numero") || tk.obtTok().equals("tk_texto")) {
            exp.agregar("rai-izq", tk);
            est14();
        } else if (tk.obtTok().equals("tk_llaAbr")) {
            est16();
        } else if (tk.obtTok().equals("tk_pun") || tk.obtTok().equals("tk_barVer") || tk.obtTok().equals("tk_ast") || tk.obtTok().equals("tk_mas") || tk.obtTok().equals("tk_cieInt")) {
            if (est19("rai-izq")) {
                est15();
            }
        } else {
            Token e = new Token(tk.obtTok(), tk.obtLex(), tk.obtFil(), tk.obtCol(), "Se esperaba -> NUMERO/TEXTO { . | * + ?");
            estE(e);
        }
    }

    // estados de solo 1 texto o numero
    private void est14() {
        if (tk.obtTok().equals("tk_numero") || tk.obtTok().equals("tk_texto")) {
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
        if (tk.obtTok().equals("tk_numero") || tk.obtTok().equals("tk_texto")) {
            exp.agregar(pos, tk);
            sigIte();
            return true;
        } else if (tk.obtTok().equals("tk_llaAbr")) {
            sigIte();
            return est21(pos);
        } else if (tk.obtTok().equals("tk_pun") || tk.obtTok().equals("tk_barVer") || tk.obtTok().equals("tk_ast") || tk.obtTok().equals("tk_mas") || tk.obtTok().equals("tk_cieInt")) {
            return est19(pos);
        } else {
            Token e = new Token(tk.obtTok(), tk.obtLex(), tk.obtFil(), tk.obtCol(), "Se esperaba -> NUMERO/TEXTO { . | * + ?");
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
        LinkedList<Conjunto> lc = lisCon;
        for (Conjunto c : lc) {
            System.out.println(c.obtNom() + " tip: " + c.obtDes());
            if (c.obtDes().equals("lis")) {
                for (Token t : c.obtEle()) {
                    System.out.print(t.obtLex() + " ");
                }
                System.out.println("");
            } else {
                if (c.obtEle().getFirst().obtLex().length() == 1 & c.obtEle().getLast().obtLex().length() == 1) {
                    char p = c.obtEle().getFirst().obtLex().charAt(0);
                    char u = c.obtEle().getLast().obtLex().charAt(0);
                    if (p < u) {
                        if (verNumero(p) & verNumero(u)) {
                            for (int i = (int) p; i <= (int) u; i++) {
                                System.out.print((char) i + " ");
                            }
                            System.out.println("");
                        } else if (verLetra(p) & verLetra(u)) {
                            for (int i = (int) p; i <= (int) u; i++) {
                                System.out.print((char) i + " ");
                            }
                            System.out.println("");
                        } else {
                            for (int i = (int) p; i <= (int) u; i++) {
                                if (!verLetra((char) i) && !verNumero((char) i)) {
                                    System.out.print((char) i + " ");
                                }
                                
                            }
                            System.out.println("");
                        }
                    }
                }
            }
        }
        
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
}
