package prog2.vista;

import prog2.adaptador.Adaptador;
import java.awt.*;
import javax.swing.*;

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
    }
}
