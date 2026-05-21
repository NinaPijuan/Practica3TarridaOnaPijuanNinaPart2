package prog2.vista;

import prog2.adaptador.Adaptador;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;

/*
POSAR ADAPTADOR COM A ATRIBUT A TOT ARREU I PASSAR-LO PER PARAMETRE (FET)
MOSTRAR LLISTES A ALGUN LLOC + REFRESCAR LLISTES
BOTO CANCELAR NOMÉS FA DISPOSE
BOTO ACCPETAR NOMÉS ENABLED QUAN ELS CAMPS ESTAN TOTS PLENS
BOTO ACCEPTAR GESTIONA EXCEPCIONS (JOPTIONPANE: JDIALOG SUPERMEGAPETIT)
FER GUARDAR I CARREGAR

 */
public class AppBiblioUB extends JFrame {
    private JPanel panel;
    private JButton btnGestioUsuaris;
    private JButton btnGestioExemplars;
    private JButton btnGestioPrestecs;
    private JButton btnGuardar;
    private JButton btnCarregar;

    private Adaptador adaptador;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AppBiblioUB app = new AppBiblioUB();
            app.setVisible(true);
        });
    }

    public AppBiblioUB() {
        adaptador = new Adaptador();
        // setModal(true) ??
        // Quan obres una finestra no pots tocar l'altre??

        // Utilitzar adaptador en els JDialog, passar-li l'adaptador
        // Quan es crea el frame, passes l'adaptador

        // JDialog AfegirPrestec
        /*
            JComboBox i JList
            La persona selecciona l'element de la llista (ja no cal passar posició)
            Els objectes ja tenen mètodes que quan se selecciona l'element, tenen el getSelectedIndex().

            Mirar mètodes de cada element.

            JComboBoxUsuari, JComboBoxExemplar
            BotoCancelar, BotoOk
         */

        /*
            Quan afegim usuari o exemplar, el botó afegir ha d'actualitzar les llistes.
         */

        setTitle("AppBiblioUB");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(panel);
        setSize(800, 600);
        setLocationRelativeTo(null);

        btnGestioUsuaris.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // creem pestanya, la fem modal i la fem visible (en akuest ordre!!)
                FrmGestioUsuaris frmGestioUsuaris = new FrmGestioUsuaris(AppBiblioUB.this, adaptador);
                frmGestioUsuaris.setModal(true);
                frmGestioUsuaris.setVisible(true);
            }
        });

        btnGestioExemplars.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FrmGestioExemplars frmGestioExemplars = new FrmGestioExemplars(AppBiblioUB.this, adaptador); // QUE PASSEM DE PARÀMETRES
                frmGestioExemplars.setModal(true);
                frmGestioExemplars.setVisible(true);
            }
        });

        btnGestioPrestecs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FrmGestioPrestecs frmGestioPrestecs = new FrmGestioPrestecs(AppBiblioUB.this, adaptador); // QUE PASSEM DE PARÀMETRES
                frmGestioPrestecs.setModal(true);
                frmGestioPrestecs.setVisible(true);
            }
        });


        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                File fitxer;
                JFileChooser seleccio = new JFileChooser();
                int resultat = seleccio.showOpenDialog(AppBiblioUB.this);
                if (resultat == JFileChooser.APPROVE_OPTION) {
                    fitxer = seleccio.getSelectedFile();
                    try {
                        adaptador.guardaDades(fitxer.toString());
                        JOptionPane.showMessageDialog(null, "Dades guardades", "", JOptionPane.INFORMATION_MESSAGE);
                    } catch (BiblioException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        btnCarregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                File fitxer;
                JFileChooser seleccio = new JFileChooser();
                int resultat = seleccio.showOpenDialog(AppBiblioUB.this);
                if (resultat == JFileChooser.APPROVE_OPTION) {
                    fitxer = seleccio.getSelectedFile();
                    try {
                        adaptador.carregaDades(fitxer.toString());
                        JOptionPane.showMessageDialog(null, "Dades carergades", "", JOptionPane.INFORMATION_MESSAGE);
                    } catch (BiblioException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }
}
