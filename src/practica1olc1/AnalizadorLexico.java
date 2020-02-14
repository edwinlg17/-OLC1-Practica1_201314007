package practica1olc1;

import java.util.LinkedList;

public class AnalizadorLexico {

    //////////////// ATRIBUTOS
    LinkedList<Token> lisTok;

    //////////////// CONSTRUCTOR
    public AnalizadorLexico() {
        lisTok = new LinkedList<>();

    }

    //////////////// METODOS
    //public LinkedList analizar(String tex) {
    public void analizar(String tex) {
        System.out.println("/////////// Inicio Analisis");
        lisTok = new LinkedList<>();
        int ite = 0, est = 0, fil = 1, col = 1;
        String lex = "";

        int fl = 0, cl = 0;

        // caracter de finalizacion
        tex += "  ";
        while (ite < tex.length()) {
            char car = tex.charAt(ite);

            switch (est) {
                // estado 0
                case 0:
                    // ID
                    if (verLetra(car)) {
                        est = 1;
                        lex += car;
                        cl = col;
                        fl = fil;
                        col++;
                    } // Numeros
                    else if (verNumero(car)) {
                        est = 2;
                        lex += car;
                        cl = col;
                        fl = fil;
                        col++;
                    } // Cadenas
                    else if (car == '"') {
                        est = 3;
                        lex += car;
                        cl = col;
                        fl = fil;
                        col++;
                    } // Comentario
                    else if (car == '/') {
                        est = 4;
                        lex += car;
                        cl = col;
                        fl = fil;
                        col++;
                    } // Comentario Multilinea
                    else if (car == '<') {
                        est = 5;
                        lex += car;
                        cl = col;
                        fl = fil;
                        col++;
                    } // Simbolo
                    else if (verSimbolo(car)) {
                        est = 0;
                        lex += car;
                        cl = col;
                        fl = fil;
                        col++;
                        //System.out.println("SIMBOLO: " + lex + " - " + fl + " - " + cl);
                        lex = "";
                    } // Caracteres Omitibles
                    else if (car == '\r' | car == '\t') {
                        // caracteres omitibles
                    } // Salto de linea
                    else if (car == '\n') {
                        fil++;
                        col = 1;
                    } else if (car == ' ') {
                        col++;
                    } else {
                        est = 0;
                        lex += car;
                        cl = col;
                        fl = fil;
                        System.out.println("ERROR LEXICO: " + car + " - " + fl + " - " + cl);
                        lex = "";
                        col++;
                    }
                    ite++;
                    break;
                // estado 1
                case 1:
                    if (verLetra(car) | verNumero(car) | car == '_') {
                        lex += car;
                        est = 1;
                        ite++;
                        col++;
                    } else {
                        //System.out.println("ID:" + lex + " - " + fl + " - " + cl);
                        lex = "";
                        est = 0;
                    }
                    break;
                // estado 2
                case 2:
                    if (verNumero(car)) {
                        lex += car;
                        est = 2;
                        ite++;
                        col++;
                    } else {
                        //System.out.println("NUMERO:" + lex + " - " + fl + " - " + cl);
                        lex = "";
                        est = 0;
                    }
                    break;
                // estado 3
                case 3:
                    if (car != '"') {
                        lex += car;
                        est = 3;
                        ite++;
                        col++;
                    } else {
                        lex += car;
                        ite++;
                        col++;
                        //System.out.println("TEXTO:" + lex + " - " + fl + " - " + cl);
                        lex = "";
                        est = 0;
                    }
                    break;
                // estado 4
                case 4:
                    if (car == '/') {
                        lex += car;
                        est = 7;
                        ite++;
                        col++;
                    } else {
                        lex += car;
                        ite++;
                        col++;
                        //System.out.println("ERROR SINTACTICO:" + lex + " - " + fl + " - " + cl);
                        lex = "";
                        est = 0;
                    }
                    break;
                // estado 5
                case 5:
                    if (car == '!') {
                        lex += car;
                        est = 8;
                        ite++;
                        col++;
                    } else {
                        lex += car;
                        ite++;
                        col++;
                        //System.out.println("ERROR SINTACTICO:" + lex + " - " + fl + " - " + cl);
                        lex = "";
                        est = 0;
                    }
                    break;
                // estado 6
                case 6:
                    // estado de finalizacion
                    break;
                // estado 7
                case 7:
                    if (car != '\n') {
                        lex += car;
                        est = 7;
                        ite++;
                        col++;
                    } else {
                        est = 0;
                        ite++;
                        col++;
                        //System.out.println("COMENTARIO:" + lex + " - " + fl + " - " + cl);
                        lex = "";
                        fil++;
                        col = 1;
                    }
                    break;
                // estado 8
                case 8:
                    if (car != '!') {
                        if(car == '\n'){
                            fil++;
                        }
                        lex += car;
                        est = 8;
                        ite++;
                        col++;
                    } else {
                        lex += car;
                        est = 9;
                        ite++;
                        col++;
                    }
                    break;
                // estado 9
                case 9:
                    if (car == '>') {
                        lex += car;
                        est = 8;
                        ite++;
                        col++;
                        System.out.println("COMENTARIO MULTILINEA:" + lex + " - " + fl + " - " + cl);
                        lex = "";
                        est = 0;
                    } else {
                        lex += car;
                        est = 9;
                        ite++;
                        col++;
                        //System.out.println("ERROR SINTACTICO:" + lex + " - " + fl + " - " + cl);
                        lex = "";
                        est = 0;
                    }
                    break;
                // estado ERROR
                case 10:

                    break;
            }
        }

        System.out.println("/////////// Fin Analisis");
        //return lisTok;
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

    private boolean verSimbolo(char car) {
        boolean ver = false;

        if (car >= '!' & car <= '/') {
            ver = true;
        }

        if (car >= ':' & car <= '@') {
            ver = true;
        }

        if (car >= '[' & car <= '`') {
            ver = true;
        }

        if (car >= '{' & car <= '}') {
            ver = true;
        }

        return ver;
    }

}
