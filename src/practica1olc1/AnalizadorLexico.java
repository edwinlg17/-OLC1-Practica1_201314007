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
        int ite = 0, est = 0, fil = 0, col = 0;
        String lex = "";
        
        
       
//        while (ite < tex.length()) {
//            char car = tex.charAt(ite);
//            
//            switch (est) {
//                // estado 0
//                case 0:
//                    if (verLetra(car)) {
//                        lex += car;
//                        est = 1;
//                        ite++;
//                    } else {
//                        ite++;
//                    }
//                    break;
//                // estado 1
//                case 1:
//                    if (verLetra(car) | verNumero(car) | car == '_') {
//                        lex += car;
//                        est = 1;
//                        ite++;
//                    }else{
//                        System.out.println(lex);
//                        lex = "";
//                        est = 0;
//                    }
//                    break;
//                // estado 2
//                case 2:
//
//                    break;
//                // estado 3
//                case 3:
//
//                    break;
//                // estado 4
//                case 4:
//
//                    break;
//                // ERROR 5
//                case 5:
//
//                    break;
//            }
//        }
        
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
