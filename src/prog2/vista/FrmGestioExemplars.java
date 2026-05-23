package prog2.vista;

import prog2.adaptador.Adaptador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Finestra de diàleg per a la gestió dels exemplars de la biblioteca.
 */
public class FrmGestioExemplars extends JDialog {
    private JPanel contentPane;
    private JButton btnTornar;
    private JButton btnAfegir;
    private JList<String> lstExemplars;
    private JScrollPane scrollPane;
    private JPanel panelInferior;
    private JPanel panelSuperior;
    private JPanel panelBtns;
    private Adaptador adaptador;

    /**
     * Crea i configura la finestra de gestió d'exemplars.
     * @param parent el JFrame principal de l'aplicació
     * @param adaptador l'adaptador que proporciona accés al model
     */
    public FrmGestioExemplars(JFrame parent, Adaptador adaptador) {
        super(parent);
        this.adaptador = adaptador;

        setContentPane(contentPane);
        setSize(900, 620);
        setLocationRelativeTo(null);
        setTitle("Gestió d'Exemplars — BiblioUB");

        // DISPOSE_ON_CLOSE: en tancar el diàleg s'allibera memòria però
        // no s'atura l'aplicació (a diferència de EXIT_ON_CLOSE dels JFrame).
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // ESTÈTICA
        // ── Tema ─────────────────────────────────────────────────────────────
        contentPane.setBackground(AppBiblioUB.C_PANEL);
        contentPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(3, 0, 0, 0, AppBiblioUB.C_OR),
                BorderFactory.createEmptyBorder(16, 20, 16, 20)
        ));

        panelSuperior.setBackground(AppBiblioUB.C_PANEL);
        panelInferior.setBackground(AppBiblioUB.C_PANEL);
        panelBtns.setBackground(AppBiblioUB.C_PANEL);

        AppBiblioUB.estilitzarLlista(lstExemplars);
        AppBiblioUB.estilitzarScrollPane(scrollPane);
        AppBiblioUB.estilitzarBotoPrimari(btnAfegir, "+ Afegir exemplar");
        AppBiblioUB.estilitzarBotoCancel(btnTornar);
        btnTornar.setText("← Tornar");

        // Mostrar llista des del primer moment abans que l'usuari faci res
        refrescarLlista();

        // Obre el subdiàleg FrmAfegirExemplar de forma modal
        // Quan l'usuari el tanca (tant si ha afegit un exemplar com si no),
        // es refresca la llista d'exemplars que es mostra
        btnAfegir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Creem el diàleg passant "this" (FrmGestioExemplars) com a pare,
                // perquè així quedi centrat sobre aquesta finestra
                FrmAfegirExemplar frmAfegirExemplar = new FrmAfegirExemplar(FrmGestioExemplars.this, adaptador);
                frmAfegirExemplar.setModal(true);
                frmAfegirExemplar.setVisible(true);

                // Refrescar llista
                refrescarLlista();
            }
        });

        // Tanca el diàleg i torna a la finestra principal
        btnTornar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
    }

    // MÈTODE AUXILIAR

    /**
     * Recarrega la llista d'exemplars des de l'adaptador i actualitza
     * el model del JList.
     */
    private void refrescarLlista() {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (String element : adaptador.recuperarExemplars()) {
            model.addElement(element);
        }
        lstExemplars.setModel(model);
    }

}
