package Analizadores;

import java.util.LinkedList;

public class AnalizadorLexico {

    //////////////// ATRIBUTOS
    private LinkedList<Token> lisTok;
    private LinkedList<Token> lisErr;

    //////////////// CONSTRUCTOR
    public AnalizadorLexico() {
        lisTok = new LinkedList<>();
        lisErr = new LinkedList<>();
    }

    //////////////// METODOS
    
    // Metodo analizar
    public void analizar(String tex) {
        lisTok = new LinkedList<>();
        lisErr = new LinkedList<>();
        
        int ite = 0, est = 0, fil = 1, col = 1;
        String lex = "";

        int fl = 0, cl = 0;

        // caracter de finalizacion
        tex += "  ";

        while (ite < tex.length()) {
            char car = tex.charAt(ite);

            switch (est) {
                ////////////////////////////////// estado 0
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
                        lisTok.add(new Token(verTkSim(lex), lex, fil, col));
                        col++;
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
                        lisErr.add(new Token("Caracter Desconocido", lex, fl, cl));
                        //System.out.println("ERROR LEXICO: " + car + " - " + fl + " - " + cl);
                        lex = "";
                        col++;
                    }
                    ite++;
                    break;
                ////////////////////////////////// estado 1
                case 1:
                    if (verLetra(car) | verNumero(car) | car == '_') {
                        est = 1;
                        lex += car;
                        ite++;
                        col++;
                    } else {
                        est = 0;
                        lisTok.add(new Token(verTkPalRes(lex), lex, fl, cl));
                        //System.out.println("ID:" + lex + " - " + fl + " - " + cl);
                        lex = "";
                    }
                    break;
                ////////////////////////////////// estado 2
                case 2:
                    if (verNumero(car)) {
                        est = 2;
                        lex += car;
                        ite++;
                        col++;
                    } else {
                        est = 0;
                        lisTok.add(new Token("tk_numero", lex, fl, cl));
                        //System.out.println("NUMERO:" + lex + " - " + fl + " - " + cl);
                        lex = "";
                    }
                    break;
                ////////////////////////////////// estado 3
                case 3:
                    if (car != '"') {
                        est = 3;
                        lex += car;
                        ite++;
                        col++;
                    } else {
                        est = 0;
                        lex += car;
                        ite++;
                        col++;
                        lisTok.add(new Token("tk_texto", lex, fl, cl));
                        //System.out.println("TEXTO:" + lex + " - " + fl + " - " + cl);
                        lex = "";
                    }
                    break;
                ////////////////////////////////// estado 4
                case 4:
                    if (car == '/') {
                        est = 7;
                        lex += car;
                        ite++;
                        col++;
                    } else {
                        est = 0;
                        lex += car;
                        ite++;
                        col++;
                        //System.out.println("ERROR SINTACTICO:" + lex + " - " + fl + " - " + cl);
                        lex = "";
                    }
                    break;
                ////////////////////////////////// estado 5
                case 5:
                    if (car == '!') {
                        est = 8;
                        lex += car;
                        ite++;
                        col++;
                    } else {
                        est = 0;
                        lex += car;
                        ite++;
                        col++;
                        //System.out.println("ERROR SINTACTICO:" + lex + " - " + fl + " - " + cl);
                        lex = "";
                    }
                    break;
                ////////////////////////////////// estado 6
                case 6:
                    // estado de finalizacion
                    break;
                ////////////////////////////////// estado 7
                case 7:
                    if (car != '\n') {
                        est = 7;
                        lex += car;
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
                ////////////////////////////////// estado 8
                case 8:
                    if (car != '!') {
                        est = 8;
                        if (car == '\n') {
                            fil++;
                            col = 0;
                        }
                        lex += car;

                        ite++;
                        col++;
                    } else {
                        est = 9;
                        lex += car;
                        ite++;
                        col++;
                    }
                    break;
                ////////////////////////////////// estado 9
                case 9:
                    if (car == '>') {
                        est = 8;
                        lex += car;
                        ite++;
                        col++;
                        //System.out.println("COMENTARIO MULTILINEA:" + lex + " - " + fl + " - " + cl);
                        lex = "";
                        est = 0;
                    } else {
                        est = 0;
                        lex += car;
                        ite++;
                        col++;
                        //System.out.println("ERROR SINTACTICO:" + lex + " - " + fl + " - " + cl);
                        lex = "";
                    }
                    break;
            }
        }
        
    }

    // Metodo para obtener tokens
    public LinkedList<Token> obtenerTokens(){
        return lisTok;
    }
    
    // Metodo para obtener errores
    public LinkedList<Token> obtenerErrores(){
        return lisErr;
    }
    
    // Metodos de verificacion de caracteres
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

        if (car == '~') {
            ver = true;
        }

        return ver;
    }

    // Metodos de verificacion de lexemas
    private String verTkPalRes(String lex) {
        String tok = "tk_id";
        
        lex = lex.toLowerCase();
        switch (lex) {
            case "conj":
                tok = "tk_con";
                break;
            default:
                if (lex.length() == 1) {
                    tok = "tk_let";
                }
                break;
        }

        return tok;
    }

    private String verTkSim(String lex) {
        String tok = "tk_sim";

        switch (lex) {
            case "{":
                tok = "tk_llaAbr";
                break;
            case "}":
                tok = "tk_llaCie";
                break;
            case "-":
                tok = "tk_gui";
                break;
            case "<":
                tok = "tk_men";
                break;
            case ">":
                tok = "tk_may";
                break;
            case ";":
                tok = "tk_punCom";
                break;
            case "%":
                tok = "tk_por";
                break;
            case ":":
                tok = "tk_dosPun";
                break;
            case ".":
                tok = "tk_pun";
                break;
            case "|":
                tok = "tk_barVer";
                break;
            case "?":
                tok = "tk_cieInt";
                break;
            case "*":
                tok = "tk_ast";
                break;
            case "+":
                tok = "tk_mas";
                break;
            case ",":
                tok = "tk_com";
                break;
            case "~":
                tok = "tk_til";
                break;
        }

        return tok;
    }
}
