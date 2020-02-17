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
            cad = new Cadena(tk.obtLex());
            sigIte();
            est11();
        } else if (tk.obtTok().equals("")) {
        } else {
            sigIte();
            est0();
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
        if (tk.obtTok().equals("tk_let") | tk.obtTok().equals("tk_numero")) {
            con.agrEle(tk);
            sigIte();
            est6();
        } else {
            Token e = new Token(tk.obtTok(), tk.obtLex(), tk.obtFil(), tk.obtCol(), "Se esperaba -> DIGITO/NUMERO");
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
                    if (ti.obtLex().charAt(0) < tf.obtLex().charAt(0) ) {
                        lisCon.add(con);
                    }
                }
            }else{
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
        if (tk.obtTok().equals("tk_let") | tk.obtTok().equals("tk_numero")) {
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
                    if (ti.obtLex().charAt(0) < tf.obtLex().charAt(0) ) {
                        lisCon.add(con);
                    }
                }
            }else{
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
        if (tk.obtTok().equals("tk_let") | tk.obtTok().equals("tk_numero")) {
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
                    if (ti.obtLex().charAt(0) < tf.obtLex().charAt(0) ) {
                        lisCon.add(con);
                    }
                }
            }else{
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
            exp.agregar("rai", tk);
            est14();
        } else if (tk.obtTok().equals("tk_llaAbr")) {
            est16();
        } else if (tk.obtTok().equals("tk_pun") || tk.obtTok().equals("tk_barVer") || tk.obtTok().equals("tk_ast") || tk.obtTok().equals("tk_mas") || tk.obtTok().equals("tk_cieInt")) {
            if (est19("rai")) {
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
        if (tk.obtTok().equals("tk_id")) {
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
        if (tk.obtTok().equals("tk_id")) {
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
        } else if (tk.obtTok().equals("tk_gui")) {
            antIte();
            est0();
        } else if (tk.obtTok().equals("tk_por")) {
            est0();
        } else if (tk.obtTok().equals("tk_con")) {
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
            ite.previous();
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
    
    public LinkedList<Token> obtLisErr(){
        return lisErr;
    }
}
