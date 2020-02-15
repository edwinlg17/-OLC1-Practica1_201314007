package Analizadores;

import java.util.LinkedList;
import java.util.ListIterator;

public class AnalizadorSintactico {

    //////////////// ATRIBUTOS
    private LinkedList<Conjunto> lisCon;
    private ListIterator ite;
    private Conjunto con;
    private Token tk;

    //////////////// CONSTRUCTOR
    public AnalizadorSintactico() {
        lisCon = new LinkedList<>();
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
            estE();
        }
    }

    private void est2() {
        if (tk.obtTok().equals("tk_id")) {
            con.estNom(tk.obtLex());
            sigIte();
            est3();
        } else {
            estE();
        }
    }

    private void est3() {
        if (tk.obtTok().equals("tk_gui")) {
            sigIte();
            est4();
        } else {
            estE();
        }
    }

    private void est4() {
        if (tk.obtTok().equals("tk_may")) {
            sigIte();
            est5();
        } else {
            estE();
        }
    }

    private void est5() {
        if (tk.obtTok().equals("tk_let") | tk.obtTok().equals("tk_numero")) {
            con.agrEle(tk.obtLex());
            sigIte();
            est6();
        } else {
            estE();
        }
    }

    private void est6() {
        if (tk.obtTok().equals("tk_com")) {
            sigIte();
            est7();
        } else if (tk.obtTok().equals("tk_til")) {
            sigIte();
            est9();
        } else if (tk.obtTok().equals("tk_punCom")) {
            lisCon.add(con);
            sigIte();
            est0();
        } else {
            estE();
        }
    }

    private void est7() {
        if (tk.obtTok().equals("tk_let") | tk.obtTok().equals("tk_numero")) {
            con.agrEle(tk.obtLex());
            sigIte();
            est8();
        } else {
            estE();
        }
    }

    private void est8() {
        if (tk.obtTok().equals("tk_com")) {
            sigIte();
            est7();
        } else if (tk.obtTok().equals("tk_punCom")) {
            lisCon.add(con);
            if (ite.hasNext()) {
                tk = (Token) ite.next();
            }
            est0();
        } else {
            estE();
        }
    }

    private void est9() {
        if (tk.obtTok().equals("tk_let") | tk.obtTok().equals("tk_numero")) {
            con.agrEle(tk.obtLex());
            sigIte();
            est10();
        } else {
            estE();
        }
    }

    private void est10() {
        if (tk.obtTok().equals("tk_punCom")) {
            lisCon.add(con);
            sigIte();
            est0();
        } else {
            estE();
        }
    }

    /////////////////////// Expreciones Regulares
    // Expresion1 -> .
    private void est11() {
        if (tk.obtTok().equals("tk_gui")) {
            sigIte();
            est12();
        } else {
            estE();
        }
    }

    private void est12() {
        if (tk.obtTok().equals("tk_may")) {
            sigIte();
            // est
        } else {
            estE();
        }
    }

    /////////////////////// Metodo de Error
    private void estE() {
        if (tk.obtTok().equals("tk_punCom")) {
            sigIte();
            est0();
        } else if (tk.obtTok().equals("tk_gui")) {
            antIte();
            est0();
        } else if (tk.obtTok().equals("tk_por")) {
            est0();
        } else if (tk.obtTok().equals("tk_con")) {
            est0();
        } else {
            sigIte();
            estE();
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

}
