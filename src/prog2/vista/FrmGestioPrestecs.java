package prog2.vista;

import prog2.adaptador.Adaptador;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Finestra de diàleg per a la gestió dels préstecs de la biblioteca.
 */
public class FrmGestioPrestecs extends JDialog {
    private JPanel contentPane;
    private JButton btnTornar;
    private JButton btnAfegir;
    private JButton btnRetornar;
    private JList<String> lstPrestecs;
    private JCheckBox chkNoRetornats;
    private JScrollPane scrollPane;
    private JPanel panelInferior;
    private JPanel panelBtns;
    private JPanel panelSuperior;
    private JPanel panelChk;
    private Adaptador adaptador;

    /* Relació entre posició visual i posició real, ja que el retornar préstec agafa la posició del préstec a la llista
    de tots els préstecs però quan es mostra la llista dels no retornats, el getSelectedIndex és un índex diferent al
    que realment s'ha de passar a la funció de retornar préstec
     */
    private List<Integer> indexosReals;

    /**
     * Crea i configura la finestra de gestió de préstecs.
     * @param parent el JFrame principal de l'aplicació
     * @param adaptador l'adaptador que proporciona accés al model
     */
    public FrmGestioPrestecs(JFrame parent, Adaptador adaptador) {
        super(parent);
        this.adaptador = adaptador;

        setContentPane(contentPane);
        setSize(950, 640);
        setLocationRelativeTo(null);
        setTitle("Gestió de Préstecs — BiblioUB");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // ESTÈTICA
        // ── Tema ─────────────────────────────────────────────────────────────
        contentPane.setBackground(AppBiblioUB.C_PANEL);
        contentPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(3, 0, 0, 0, AppBiblioUB.C_OR),
                BorderFactory.createEmptyBorder(16, 20, 16, 20)
        ));

        panelInferior.setBackground(AppBiblioUB.C_PANEL);
        panelBtns.setBackground(AppBiblioUB.C_PANEL);
        panelSuperior.setBackground(AppBiblioUB.C_PANEL);
        panelChk.setBackground(AppBiblioUB.C_PANEL);

        AppBiblioUB.estilitzarLlista(lstPrestecs);
        AppBiblioUB.estilitzarScrollPane(scrollPane);
        AppBiblioUB.estilitzarBotoPrimari(btnAfegir, "+ Afegir préstec");
        AppBiblioUB.estilitzarBotoPrimari(btnRetornar, "- Retornar préstec");
        AppBiblioUB.estilitzarBotoCancel(btnTornar);
        AppBiblioUB.estilitzarCheckBox(chkNoRetornats);
        btnTornar.setText("← Tornar");

        // El botó "Retornar" comença desactivat fins que s'escull un préstec a la llista
        btnRetornar.setEnabled(false);

        // Mostrar llista des del primer moment
        refrescarLlista();

        // El botó "Afegir" obre el subdiàleg FrmAfegirPrestec de forma modal
        // Quan es tanca, es refresca la llista
        btnAfegir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FrmAfegirPrestec frmAfegirPrestec = new FrmAfegirPrestec(FrmGestioPrestecs.this, adaptador);
                frmAfegirPrestec.setModal(true);
                frmAfegirPrestec.setVisible(true);

                // Refresca quan torna
                refrescarLlista();
            }
        });

        /* El botó "Retornar":
            Obté l'índex visual seleccionat, el tradueix a índex real gràcies a
            indexosReals, i crida l'adaptador per marcar el préstec com a retornat.

            En cas de poder, mostra la confirmació, refresca la llista i desactiva el botó
            En cas d'error, mostra el missatge corresponent
         */
        btnRetornar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int indexVisual = lstPrestecs.getSelectedIndex();

                // Traducció de posició visual -> índex real dins la llista completa
                int indexReal = indexosReals.get(indexVisual);

                try {
                    adaptador.retornarPrestec(indexReal);
                    JOptionPane.showMessageDialog(FrmGestioPrestecs.this, "Préstec retornat",
                            "", JOptionPane.INFORMATION_MESSAGE);

                    // Tornem a refrescar
                    refrescarLlista();

                    // Desactivem boto retornar
                    btnRetornar.setEnabled(false);

                } catch (BiblioException ex) {
                    JOptionPane.showMessageDialog(FrmGestioPrestecs.this, ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnTornar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });

        // Cada vegada que l'usuari canvia la selecció a la llista, s'activa
        // o desactiva el botó "Retornar" en funció de si hi ha algun element
        // seleccionat
        lstPrestecs.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                // isSelectionEmpty() és false si hi ha algun element seleccionat
                btnRetornar.setEnabled(!lstPrestecs.isSelectionEmpty());
            }
        });

        // Cada vegada que l'usuari marca o desmarca la casella "Només no retornats",
        // es refresca la llista per aplicar o eliminar el filtre
        chkNoRetornats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                refrescarLlista();
            }
        });
    }

    // MÈTODE AUXILIAR

    /**
     * Recarrega la llista de préstecs des de l'adaptador, aplicant el filtre
     * de "no retornats" si la casella chkNoRetornats està marcada, i
     * reconstrueix la llista indexosReals.
     */
    private void refrescarLlista() {
        DefaultListModel<String> model = new DefaultListModel<>();
        indexosReals = new ArrayList<>();
        List<String> prestecs = adaptador.recuperarPrestecs();
        List<String> prestecsNR = adaptador.recuperarPrestecsNoRetornats();

        if (chkNoRetornats.isSelected()) {
            // Mostrem només no retornats, però guardem l'índex real de cada un dins la llista completa
            for (int i = 0; i < prestecs.size(); i++) {
                if (prestecsNR.contains(prestecs.get(i))) {
                    model.addElement(prestecs.get(i));

                    // Aquest és l'índex real dins de la llista de tots els préstecs
                    indexosReals.add(i);
                }
            }
        } else {
            // Mostrem tots, l'índex visual = índex real
            for (int i = 0; i < prestecs.size(); i++) {
                model.addElement(prestecs.get(i));
                indexosReals.add(i);
            }
        }

        lstPrestecs.setModel(model);

        // Desactivar quan es refresca perquè el nou model elimina la selecció anterior
        btnRetornar.setEnabled(false);
    }
}
