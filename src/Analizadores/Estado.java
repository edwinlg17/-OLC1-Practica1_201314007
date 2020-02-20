package Analizadores;

public class Estado {

    //////////////// ATRIBUTOS
    String estO, sim, estD;

    //////////////// CONSTRUCTOR
    public Estado(String estO, String sim, String estD) {
        this.estO = estO;
        this.sim = sim;
        this.estD = estD;
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
