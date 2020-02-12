package practica1olc1;

public class AnalizadorLexico {
    //////////////// ATRIBUTOS
    private String lex;
    private String tok;
    private int fil;
    private int col;
    
    //////////////// CONSTRUCTOR
    public AnalizadorLexico(){
        this.lex = "";
        this.tok = "";
        this.fil = 0;
        this.col = 0;
    }
    
    public AnalizadorLexico(String lex, String tok, int fil, int col){
        this.lex = lex;
        this.tok = tok;
        this.fil = fil;
        this.col = col;
    }
    
    //////////////// METODOS
    public String obtLex() {
        return lex;
    }
    public String obtTok() {
        return tok;
    }
    public int obtFil() {
        return fil;
    }
    public int obtCol() {
        return col;
    }

    public void estLex(String lex) {
        this.lex = lex;
    }
    public void estTok(String tok) {
        this.tok = tok;
    }
    public void estFil(int fil) {
        this.fil = fil;
    }
    public void estCol(int col) {
        this.col = col;
    }
    
}
