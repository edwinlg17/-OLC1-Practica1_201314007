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

        while (ite < tex.length()) {
            char car = tex.charAt(ite);

            switch (est) {
                // estado 0
                case 0:
                    if (verLetra(car)) {
                        lex += car;
                        est = 1;
                        cl = col;
                        fl = fil;
                        col++;
                    } else if (verNumero(car)) {
                        lex += car;
                        est = 2;
                        cl = col;
                        fl = fil;
                        col++;
                    } else if (car == '"') {
                        col++;
                    } else if (car == '\r' | car == '\t') {
                        // caracteres omitibles
                    } else if (car == '\n') {
                        fil++;
                        col = 1;
                    } else if (car == ' ') {
                        col++;
                    } else {
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
                        System.out.println(lex + " - " + fl + " - " + cl);
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
                    }else{
                        System.out.println(lex + " - " + fl + " - " + cl);
                        lex = "";
                        est = 0;
                    }
                    break;
                // estado 3
                case 3:

                    break;
                // estado 4
                case 4:

                    break;
                // ERROR 5
                case 5:

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
}
