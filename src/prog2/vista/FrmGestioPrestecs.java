package prog2.vista;

import prog2.adaptador.Adaptador;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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

    public FrmGestioPrestecs(JFrame parent, Adaptador adaptador) {
        super(parent);
        this.adaptador = adaptador;
        setContentPane(contentPane);
        setSize(680, 540);
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

        btnRetornar.setEnabled(false);

        // mostrar llista des del primer moment
        refrescarLlista();

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
        btnRetornar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int indexVisual = lstPrestecs.getSelectedIndex();
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
        lstPrestecs.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                btnRetornar.setEnabled(!lstPrestecs.isSelectionEmpty());
            }
        });
        chkNoRetornats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                refrescarLlista();
            }
        });
    }

    // Mètode auxiliar
    private void refrescarLlista() {
        DefaultListModel<String> model = new DefaultListModel<>();
        indexosReals = new ArrayList<>();
        List<String> prestecs = adaptador.recuperarPrestecs();
        List<String> prestecsNR = adaptador.recuperarPrestecsNoRetornats();

        if (chkNoRetornats.isSelected()) {
            // Mostrem només no retornats, però guardem l'índex real de cada un
            for (int i = 0; i < prestecs.size(); i++) {
                if (prestecsNR.contains(prestecs.get(i))) {
                    model.addElement(prestecs.get(i));

                    // aquest és l'índex real dins de la llista de tots els préstecs
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

        // Desactivar quan es refresca
        btnRetornar.setEnabled(false);
    }
}
