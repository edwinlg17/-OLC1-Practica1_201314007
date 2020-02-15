package Analizadores;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class AnalizadorSintactico {

    //////////////// ATRIBUTOS
    private LinkedList<Conjunto> lisCon;

    //////////////// CONSTRUCTOR
    public AnalizadorSintactico() {
        lisCon = new LinkedList<>();
    }

    ////////////////METODOS
    public void analizar(LinkedList<Token> lisTok) {
        lisCon = new LinkedList<>();

        //Token de finalizacion
        lisTok.add(new Token("", "", 0, 0));
        ListIterator ite = lisTok.listIterator();

        int est = 0;

        if (ite.hasNext()) {
            Token tk = (Token) ite.next();

            boolean ver = true;

            Conjunto con = new Conjunto();
            while (ver) {
                switch (est) {
                    ///////////////////////////////// Estado Inicial
                    //estado 0
                    case 0:
                        if (tk.obtTok().equals("tk_con")) {
                            est = 1;
                            con = new Conjunto();
                            if (ite.hasNext()) {
                                tk = (Token) ite.next();
                            }
                        } else if (tk.obtTok().equals("")) {
                            ver = false;
                        } else {
                            est = 0;
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
                            con.estNom(tk.obtLex());
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
                            con.agrEle(tk.obtLex());
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
                            est = 9;
                            if (ite.hasNext()) {
                                tk = (Token) ite.next();
                            }
                        } else if (tk.obtTok().equals("tk_punCom")) {
                            est = 0;
                            lisCon.add(con);
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
                            con.agrEle(tk.obtLex());
                            if (ite.hasNext()) {
                                tk = (Token) ite.next();
                            }
                        } else {
                            est = 50;
                        }
                        break;
                    //estado 8
                    case 8:
                        if (tk.obtTok().equals("tk_com")) {
                            est = 7;
                            if (ite.hasNext()) {
                                tk = (Token) ite.next();
                            }
                        } else if (tk.obtTok().equals("tk_punCom")) {
                            est = 0;
                            lisCon.add(con);
                            if (ite.hasNext()) {
                                tk = (Token) ite.next();
                            }
                        } else {
                            est = 50;
                        }
                        break;
                    //estado 9
                    case 9:
                        if (tk.obtTok().equals("tk_let") | tk.obtTok().equals("tk_numero")) {
                            est = 10;
                            con.agrEle(tk.obtLex());
                            if (ite.hasNext()) {
                                tk = (Token) ite.next();
                            }
                        } else {
                            est = 50;
                        }
                        break;
                    //estado 10
                    case 10:
                        if (tk.obtTok().equals("tk_punCom")) {
                            est = 0;
                            lisCon.add(con);
                            if (ite.hasNext()) {
                                tk = (Token) ite.next();
                            }
                        } else {
                            est = 50;
                        }
                        break;

                    ///////////////////////////////// Experciones Regulares
                    //estado Error
                    case 50:
                        if (tk.obtTok().equals("tk_punCom")) {
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
                        } else if (tk.obtTok().equals("tk_con")) {
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

    public LinkedList<Conjunto> obtLisCon() {
        return lisCon;
    }
}
