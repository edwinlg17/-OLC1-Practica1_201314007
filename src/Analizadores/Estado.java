package Analizadores;

public class Estado {

    //////////////// ATRIBUTOS
    private String estO, sim, estD;
    //private Token sim;
    private boolean fin;

    //////////////// CONSTRUCTOR
    // estO origen estD destino
    public Estado(String estO, String sim, String estD, boolean fin) {
        this.estO = estO;
        this.sim = sim;
        this.estD = estD;
        this.fin = fin;
    }

    //////////////// METODOS
    public String obtEstO() {
        return estO;
    }

    public String obtSim() {
        return sim;
    }

    public String obtEstD() {
        return estD;
    }

    public boolean obtFin(){
        return fin;
    }
    
    public void estEstO(String estO) {
        this.estO = estO;
    }

    public void estSim(String sim) {
        this.sim = sim;
    }

    public void estEstD(String estD) {
        this.estD = estD;
    }
    
}
