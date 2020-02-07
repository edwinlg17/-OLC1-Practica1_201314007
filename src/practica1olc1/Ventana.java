package practica1olc1;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class Ventana extends JFrame{
    //////////////// ATRIBUTOS
    private GridBagConstraints gbc;
    
    //////////////// CONTRUCTOR
    public Ventana(){
        formatoFormulario();
        formatoComponentes();
        this.pack();
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
    }
    
    // Formato del Formulario
    private void formatoFormulario() {
        this.setTitle("Practica 1");
        this.setLayout(new GridBagLayout());
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    // Formato de los Componentes
    private void formatoComponentes() {
        
    }
    
    // Funcion que establece los parametros para el grid bag constraints
    public void establecerGBC(int x, int y, int w, int h, double wx, double wy) {
        gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.gridheight = h;
        gbc.weightx = wx;
        gbc.weighty = wy;
    }
    
    //////////////// METODOS
    
}
