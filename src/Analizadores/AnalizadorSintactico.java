package Analizadores;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class AnalizadorSintactico {

    //////////////// ATRIBUTOS
    private LinkedList<Token> lisTok;

    //////////////// CONSTRUCTOR
    public AnalizadorSintactico() {
        lisTok = new LinkedList<>();
    }

    ////////////////METODOS
    public void analizar(LinkedList<Token> lisTok) {
        ListIterator ite = lisTok.listIterator();

        int est = 0;

        if (ite.hasNext()) {
            Token tk = (Token) ite.next();

            while (ite.hasNext()) {

                switch (est) {
                    ///////////////////////////////// Estado Inicial
                    //estado 0
                    case 0:
                        if (tk.obtTok().equals("tk_con")) {
                            est = 1;
                            if (ite.hasNext()) {
                                tk = (Token) ite.next();
                            }
                        }
                        break;
                    ///////////////////////////////// Conjuntos
                    //estado 1
                    case 1:
                        if (tk.obtTok().equals("tk_dosPun")) {
                            est = 2;
                            if (ite.hasNext()) {
                                tk = (Token) ite.next();
                            }
                        } else {
                            est = 50;
                        }
                        break;
                    //estado 2
                    case 2:
                        if (tk.obtTok().equals("tk_id")) {
                            est = 3;
                            if (ite.hasNext()) {
                                tk = (Token) ite.next();
                            }
                        } else {
                            est = 50;
                        }
                        break;
                    //estado 3
                    case 3:
                        if (tk.obtTok().equals("tk_gui")) {
                            est = 4;
                            if (ite.hasNext()) {
                                tk = (Token) ite.next();
                            }
                        } else {
                            est = 50;
                        }
                        break;
                    //estado 4
                    case 4:
                        if (tk.obtTok().equals("tk_may")) {
                            est = 5;
                            if (ite.hasNext()) {
                                tk = (Token) ite.next();
                            }
                        } else {
                            est = 50;
                        }
                        break;
                    //estado 5
                    case 5:
                        if (tk.obtTok().equals("tk_let") | tk.obtTok().equals("tk_numero")) {
                            est = 6;
                            if (ite.hasNext()) {
                                tk = (Token) ite.next();
                            }
                        } else {
                            est = 50;
                        }
                        break;
                    //estado 6
                    case 6:
                        if (tk.obtTok().equals("tk_com")) {
                            est = 7;
                            if (ite.hasNext()) {
                                tk = (Token) ite.next();
                            }
                        } else if (tk.obtTok().equals("tk_til")) {

                        } else if (tk.obtTok().equals("tk_punCom")) {
                            est = 0;
                            if (ite.hasNext()) {
                                tk = (Token) ite.next();
                            }
                        } else {
                            est = 50;
                        }
                        break;
                    //estado 7
                    case 7:
                        if (tk.obtTok().equals("tk_let") | tk.obtTok().equals("tk_numero")) {
                            est = 8;
                            if (ite.hasNext()) {
                                tk = (Token) ite.next();
                            }
                        } else {
                            est = 8;
                        }
                        break;

                    //conj: vocales -> a,e,i,o,u;
                    ///////////////////////////////// Experciones Regulares
                    //estado Error
                    case 50:
                        if (tk.obtTok().equals("tk_dosPun")) {
                            est = 0;
                            if (ite.hasNext()) {
                                tk = (Token) ite.next();
                            }
                        } else if (tk.obtTok().equals("tk_gui")) {
                            est = 0;
                            if (ite.hasPrevious()) {
                                ite.previous();
                            }
                        } else if (tk.obtTok().equals("tk_por")) {
                            est = 0;
                        } else {
                            est = 50;
                            if (ite.hasNext()) {
                                tk = (Token) ite.next();
                            }
                        }
                        break;
                }

            }
        }
    }
}
