package practica1olc1;

import Analizadores.Token;
import Analizadores.AnalizadorLexico;
import Analizadores.AnalizadorSintactico;
import com.sun.prism.image.ViewPort;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.JViewport;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class Ventana extends JFrame {

    //////////////// ATRIBUTOS
    // Variables
    private String rutAbr;

    // Componentes 
    private GridBagConstraints gbc;

    private JMenuBar jmbBar;
    private JMenu jmArc, jmAna;
    private JMenuItem jmiAbr, jmiGua, jmiGuaCom, jmiGenXML;
    private JMenuItem jmiAna, jmiGenAut;

    private JPanel jpEnt, jpVis, jpSal;

    private JTextArea jtaEnt;
    private JScrollPane jspEnt;

    private JScrollPane jspImg, jspArb;
    private JTree jtArb;

    private JTextArea jtaSal;
    private JScrollPane jspSal;

    //////////////// CONTRUCTOR
    public Ventana() {
        formatoFormulario();
        formatoComponentes();
        this.pack();
        this.setSize(1000, 600);
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
        // variables
        rutAbr = "";

        //////////////////////////// MENU
        jmbBar = new JMenuBar();

        // Menu Archivo
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

        // Menu Analisis
        jmAna = new JMenu("Analisis");

        jmiAna = new JMenuItem("Analizar");
        jmiAna.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accionBotonAnalizar(e);
            }
        });
        jmAna.add(jmiAna);

        jmiGenAut = new JMenuItem("Generar Automata");
        jmiGenAut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accionBotonGenerarAutomata(e);
            }
        });
        jmAna.add(jmiGenAut);

        jmbBar.add(jmArc);
        jmbBar.add(jmAna);
        this.setJMenuBar(jmbBar);

        //////////////////////////// Panel Entrada
        // panel entrada
        jpEnt = new JPanel();
        jpEnt.setLayout(new GridBagLayout());
        jpEnt.setBorder(new TitledBorder("Entrada"));

        // text area entrada
        jtaEnt = new JTextArea();
        jspEnt = new JScrollPane(jtaEnt);
        establecerGBC(0, 0, 2, 1, 0.7, 1.0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        jpEnt.add(jspEnt, gbc);

        // inserto panel entrada
        establecerGBC(0, 0, 1, 1, 0.5, 1.0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 5, 5);
        this.add(jpEnt, gbc);

        //////////////////////////// Panel Visualizacion
        // panel Visualizacion
        jpVis = new JPanel();
        jpVis.setLayout(new GridBagLayout());
        jpVis.setBorder(new TitledBorder("Grafico"));

        // Arbol de directorios
        jtArb = new JTree(crearArbol());
        jtArb.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                accionArbol(e);
            }
        });

        jspArb = new JScrollPane(jtArb);
        establecerGBC(0, 0, 1, 1, 0.3, 1.0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 5);
        jpVis.add(jspArb, gbc);

        // Panel Imagen
        JLabel jl = new JLabel();

        ImageIcon fot = new ImageIcon("src\\Imagenes\\default.jpg");
        double w = fot.getIconHeight() * 0.95;
        double h = fot.getIconWidth() * 0.95;
        Icon icono = new ImageIcon(fot.getImage().getScaledInstance((int) h, (int) w, Image.SCALE_DEFAULT));
        jl.setIcon(icono);

        jspImg = new JScrollPane(jl);
        jspImg.setViewportView(jl);

        establecerGBC(1, 0, 1, 1, 1.0, 1.0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 5, 10, 10);
        jpVis.add(jspImg, gbc);

        // inserto panel Visualizacion
        establecerGBC(1, 0, 1, 1, 0.5, 1.0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 5, 5, 10);
        this.add(jpVis, gbc);

        //////////////////////////// Panel Salida
        // panel salida
        jpSal = new JPanel();
        jpSal.setLayout(new GridBagLayout());
        jpSal.setBorder(new TitledBorder("Salida"));

        // text area salida
        jtaSal = new JTextArea();
//        jtaSal.setEnabled(false);
        jspSal = new JScrollPane(jtaSal);
        establecerGBC(0, 0, 1, 1, 1.0, 1.0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        jpSal.add(jspSal, gbc);

        // inserto panel salida
        establecerGBC(0, 1, 2, 1, 1.0, 0.2);
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
    // METODOS BOTONES MENU
    private void accionBotonAbrir(ActionEvent evt) {
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("Archivo", "er", "er"));

        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            String tex = leeArchivo(fc.getSelectedFile());
            jtaEnt.setText(tex);
            rutAbr = fc.getSelectedFile().getPath();
        }
    }

    private void accionBotonGuardar(ActionEvent evt) {
        if (rutAbr.equals("")) {
            guardarArchivoComo();
        } else {
            guardarArchivo();
        }
    }

    private void accionBotonGuardarComo(ActionEvent evt) {
        guardarArchivoComo();
    }

    private void accionBotonGenerarXML(ActionEvent evt) {
        AnalizadorSintactico as = new AnalizadorSintactico();
        LinkedList<Token> l = new LinkedList<>();
        
        l.add(new Token("H","H",0,0));
        l.add(new Token("O","O",0,0));
        l.add(new Token("L","L",0,0));
        l.add(new Token("A","A",0,0));
        
        as.analizar(l);
        
        as.analizar(l);
        
    }

    // METODOS BOTONES Entrada
    private void accionBotonAnalizar(ActionEvent evt) {
        AnalizadorLexico a = new AnalizadorLexico();
        a.analizar(jtaEnt.getText());

        LinkedList<Token> lisTok = a.obtenerTokens();
        
        System.out.println("/////////// Inicio Analisis");

        for (Token l : lisTok) {
            System.out.println("tk: " + l.obtTok() + " lex: " + l.obtLex() + " fil: " + l.obtFil() + " col: " + l.obtCol());
        }

        System.out.println("/////////// Fin Analisis");
    }

    private void accionBotonGenerarAutomata(ActionEvent evt) {

        DefaultTreeModel dtm = new DefaultTreeModel(crearArbol());
        jtArb.setModel(dtm);
    }

    // METODOS JARBOL
    private void accionArbol(TreeSelectionEvent e) {
        TreePath tp = e.getPath();
        Object[] lis = tp.getPath();
        String rut = "";

        for (int i = 0; i < lis.length; i++) {
            rut += lis[i].toString() + "\\";
        }

        File f = new File(System.getProperty("user.home") + "\\Desktop\\" + rut);

        if (f.exists() && (f.getPath().endsWith(".jpg") || f.getPath().endsWith(".png"))) {
            JLabel jl = new JLabel();

            ImageIcon fot = new ImageIcon(f.getPath());
            double w = fot.getIconHeight() * 0.25;
            double h = fot.getIconWidth() * 0.25;
            Icon icono = new ImageIcon(fot.getImage().getScaledInstance((int) h, (int) w, Image.SCALE_DEFAULT));
            jl.setIcon(icono);

            jspImg.setViewportView(jl);
            jspImg.updateUI();

        } else {
            DefaultTreeModel dtm = new DefaultTreeModel(crearArbol());
            jtArb.setModel(dtm);
        }

    }

    // Otros
    //metodo que verifica los directorios~
    private void verDirectorios() {
        File dd1 = new File(System.getProperty("user.home") + "/desktop/Diagramas");
        File da2 = new File(System.getProperty("user.home") + "/desktop/Diagramas/Arboles");
        File ds3 = new File(System.getProperty("user.home") + "/desktop/Diagramas/Siguientes");
        File dt4 = new File(System.getProperty("user.home") + "/desktop/Diagramas/Transiciones");
        File da5 = new File(System.getProperty("user.home") + "/desktop/Diagramas/Automatas");

        if (!dd1.exists()) {
            dd1.mkdir();
        }
        if (!da2.exists()) {
            da2.mkdir();
        }
        if (!ds3.exists()) {
            ds3.mkdir();
        }
        if (!dt4.exists()) {
            dt4.mkdir();
        }
        if (!da5.exists()) {
            da5.mkdir();
        }
    }

    //metodo que crea el arbol de archivos
    private DefaultMutableTreeNode crearArbol() {
        verDirectorios();

        DefaultMutableTreeNode dmtnDia = new DefaultMutableTreeNode("Diagramas");
        DefaultMutableTreeNode dmtnArb = new DefaultMutableTreeNode("Arboles");
        DefaultMutableTreeNode dmtnSig = new DefaultMutableTreeNode("Siguientes");
        DefaultMutableTreeNode dmtnTra = new DefaultMutableTreeNode("Transiciones");
        DefaultMutableTreeNode dmtnAut = new DefaultMutableTreeNode("Automatas");

        dmtnDia.add(dmtnArb);
        dmtnDia.add(dmtnSig);
        dmtnDia.add(dmtnTra);
        dmtnDia.add(dmtnAut);

        File fRai = new File(System.getProperty("user.home") + "/desktop/Diagramas/Arboles");
        File[] fArc = fRai.listFiles();

        for (int i = 0; i < fArc.length; i++) {
            if (fArc[i].getName().endsWith(".jpg") || fArc[i].getName().endsWith(".png")) {
                dmtnArb.add(new DefaultMutableTreeNode(fArc[i].getName()));
            }
        }

        fRai = new File(System.getProperty("user.home") + "/desktop/Diagramas/Siguientes");
        fArc = fRai.listFiles();

        for (int i = 0; i < fArc.length; i++) {
            if (fArc[i].getName().endsWith(".jpg") || fArc[i].getName().endsWith(".png")) {
                dmtnSig.add(new DefaultMutableTreeNode(fArc[i].getName()));
            }
        }

        fRai = new File(System.getProperty("user.home") + "/desktop/Diagramas/Transiciones");
        fArc = fRai.listFiles();

        for (int i = 0; i < fArc.length; i++) {
            if (fArc[i].getName().endsWith(".jpg") || fArc[i].getName().endsWith(".png")) {
                dmtnTra.add(new DefaultMutableTreeNode(fArc[i].getName()));
            }
        }

        fRai = new File(System.getProperty("user.home") + "/desktop/Diagramas/Automatas");
        fArc = fRai.listFiles();

        for (int i = 0; i < fArc.length; i++) {
            if (fArc[i].getName().endsWith(".jpg") || fArc[i].getName().endsWith(".png")) {
                dmtnAut.add(new DefaultMutableTreeNode(fArc[i].getName()));
            }
        }
        return dmtnDia;
    }

    // metodo para leer archivos
    private String leeArchivo(File arc) {
        String tex = "";
        try {
            FileReader fr = new FileReader(arc);
            BufferedReader br = new BufferedReader(fr);

            while (br.ready()) {
                tex += br.readLine() + "\n";
            }

            br.close();
            fr.close();
        } catch (Exception e) {
        }
        tex = tex.toLowerCase();
        return tex;
    }

    // metodo para guardar archivos
    private void guardarArchivo() {
        try {
            File f = new File(rutAbr);
            BufferedWriter bw;
            bw = new BufferedWriter(new FileWriter(f));
            bw.write(jtaEnt.getText());
            bw.close();
        } catch (Exception e) {
        }
    }

    // metodo para guardar archivos como
    private void guardarArchivoComo() {
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("Archivo", "er", "er"));

        if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            rutAbr = fc.getSelectedFile().getPath();

            if (!rutAbr.endsWith(".er")) {
                rutAbr += ".er";
            }
            guardarArchivo();
        }
    }
}
