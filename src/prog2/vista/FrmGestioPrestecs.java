package prog2.vista;

import prog2.adaptador.Adaptador;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
        setSize(800, 600);
        setLocationRelativeTo(null);
        setTitle("Gestió préstecs");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

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
