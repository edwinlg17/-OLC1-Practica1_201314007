package practica1olc1;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class Ventana extends JFrame {

    //////////////// ATRIBUTOS
    private GridBagConstraints gbc;

    private JMenuBar jmbBar;
    private JMenu jmArc;
    private JMenuItem jmiAbr;
    private JMenuItem jmiGua;
    private JMenuItem jmiGuaCom;
    private JMenuItem jmiGenXML;

    private JPanel jpEnt;
    private JPanel jpVis;
    private JPanel jpSal;

    //////////////// CONTRUCTOR
    public Ventana() {
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
        //////////////////////////// MENU
        jmbBar = new JMenuBar();

        jmArc = new JMenu("Archivo");

        jmiAbr = new JMenuItem("Abrir");
        jmiAbr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accionBotonAbrir(e);
            }
        });
        jmArc.add(jmiAbr);

        jmiGua = new JMenuItem("Guardar");
        jmiGua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accionBotonGuardar(e);
            }
        });
        jmArc.add(jmiGua);

        jmiGuaCom = new JMenuItem("Guardar Como");
        jmiGuaCom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accionBotonGuardarComo(e);
            }
        });
        jmArc.add(jmiGuaCom);

        jmiGenXML = new JMenuItem("Generar XML");
        jmiGenXML.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accionBotonGenerarXML(e);
            }
        });
        jmArc.add(jmiGenXML);

        jmbBar.add(jmArc);
        this.setJMenuBar(jmbBar);

        //////////////////////////// Panel Entrada
        jpEnt = new JPanel();
        jpEnt.setLayout(new GridBagLayout());
        jpEnt.setBorder(new TitledBorder("Entrada"));
        
        establecerGBC(0, 0, 1, 1, 1.0, 1.0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 5, 5);
        this.add(jpEnt, gbc);
        
        //////////////////////////// Panel Visualizacion
        jpVis = new JPanel();
        jpVis.setLayout(new GridBagLayout());
        jpVis.setBorder(new TitledBorder("Grafico"));
        
        establecerGBC(1, 0, 1, 1, 1.0, 1.0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 5, 5, 10);
        this.add(jpVis, gbc);
        
        //////////////////////////// Panel Salida
        jpSal = new JPanel();
        jpSal.setLayout(new GridBagLayout());
        jpSal.setBorder(new TitledBorder("Salida"));
        
        establecerGBC(0, 1, 2, 1, 1.0, 0.25);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 10, 10, 10);
        this.add(jpSal, gbc);
        
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
    // METODOS MENU
    private void accionBotonAbrir(ActionEvent evt) {

    }

    private void accionBotonGuardar(ActionEvent evt) {

    }

    private void accionBotonGuardarComo(ActionEvent evt) {

    }

    private void accionBotonGenerarXML(ActionEvent evt) {

    }

}
