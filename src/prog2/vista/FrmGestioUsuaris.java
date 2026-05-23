package prog2.vista;

import prog2.adaptador.Adaptador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Finestra de diàleg per a la gestió dels usuaris de la biblioteca.
 */
public class FrmGestioUsuaris extends JDialog {
    private JPanel contentPane;
    private JButton btnTornar;
    private JButton btnAfegir;
    private JList<String> lstUsuaris;
    private JScrollPane scrollPane;
    private JPanel panelInferior;
    private JPanel panelBtns;
    private JPanel panelLst;
    private Adaptador adaptador;

    /**
     * Crea i configura la finestra de gestió d'usuaris.
     * @param parent el JFrame principal de l'aplicació
     * @param adaptador l'adaptador que proporciona accés al model
     */
    public FrmGestioUsuaris(JFrame parent, Adaptador adaptador) {
        super(parent);
        this.adaptador = adaptador;

        setContentPane(contentPane);
        setSize(900, 620);
        setLocationRelativeTo(null);
        setTitle("Gestió d'Usuaris — BiblioUB");

        // DISPOSE_ON_CLOSE: tanca el diàleg sense aturar l'aplicació
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        contentPane.setBackground(AppBiblioUB.C_PANEL);
        // Vora decorativa al contentPane
        contentPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(3, 0, 0, 0, AppBiblioUB.C_OR), // franja superior
                BorderFactory.createEmptyBorder(16, 20, 16, 20) // marges interiors
        ));

        panelBtns.setBackground(AppBiblioUB.C_PANEL);
        panelInferior.setBackground(AppBiblioUB.C_PANEL);
        panelLst.setBackground(AppBiblioUB.C_PANEL);

        AppBiblioUB.estilitzarLlista(lstUsuaris);
        AppBiblioUB.estilitzarScrollPane(scrollPane);
        AppBiblioUB.estilitzarBotoPrimari(btnAfegir, "+ Afegir usuari");
        AppBiblioUB.estilitzarBotoCancel(btnTornar);
        btnTornar.setText("← Tornar");



        // Mostrar llista des del primer moment
        refrescarLlista();

        /* Bóto "Afegir
            Obre el subdiàleg FrmAfegirUsuari de forma modal, passant "this"
            (FrmGestioUsuaris) com a pare per centrar-lo sobre aquesta finestra.
            Quan l'usuari el tanca (hagi afegit un usuari o no), es refresca
            la llista d'usuaris.
         */
        btnAfegir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // L'ordre importa!
                FrmAfegirUsuari frmAfegirUsuari = new FrmAfegirUsuari(FrmGestioUsuaris.this, adaptador);
                frmAfegirUsuari.setModal(true);
                frmAfegirUsuari.setVisible(true);

                // Quan torna (el dialog s'ha tancat), refrescar llista:
                refrescarLlista();
            }
        });

        // El boto "Tornar" tanca el diàleg i torna a la finestra principal
        btnTornar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
    }

    // MÈTODE AUXILIAR

    /**
     * Recarrega la llista d'usuaris des de l'adaptador i actualitza
     * el model del JList.
     */
    private void refrescarLlista() {
        DefaultListModel<String> model = new DefaultListModel<>();
        for(String element : adaptador.recuperarUsuaris()) {
            model.addElement(element);
        }
        lstUsuaris.setModel(model);
    }
}
