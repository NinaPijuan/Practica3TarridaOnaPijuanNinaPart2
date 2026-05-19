package prog2.vista;

import prog2.adaptador.Adaptador;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/*
POSAR ADAPTADOR COM A ATRIBUT A TOT ARREU I PASSAR-LO PER PARAMETRE
MOSTRAR LLISTES A ALGUN LLOC + REFRESCAR LLISTES
BOTO CANCELAR NOMÉS FA DISPOSE
BOTO ACCPETAR NOMÉS ENABLED QUAN ELS CAMPS ESTAN TOTS PLENS
BOTO ACCEPTAR GESTIONA EXCEPCIONS (JOPTIONPANE: JDIALOG SUPERMEGAPETIT)

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
        setSize(500, 400);
        btnGestioUsuaris.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FrmGestioUsuaris gestioUsuaris = new FrmGestioUsuaris(); // QUE PASSEM DE PARÀMETRES
                gestioUsuaris.setVisible(true);

                // Què més?
            }
        });

        btnGestioExemplars.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FrmGestioExemplars gestioExemplars = new FrmGestioExemplars(); // QUE PASSEM DE PARÀMETRES
                gestioExemplars.setVisible(true);

                // Què més?
            }
        });

        btnGestioPrestecs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FrmGestioPrestecs gestioPrestecs = new FrmGestioPrestecs(); // QUE PASSEM DE PARÀMETRES
                gestioPrestecs.setVisible(true);

                // Què més?
            }
        });

    }
}
