package prog2.vista;

import prog2.adaptador.Adaptador;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmRetornarPrestec extends JDialog {
    private JPanel contentPane;
    private JButton btnRetornar;
    private JButton btnCancelar;
    private JLabel etPrestecs;
    private JList<String> lstPrestecsNR;

    private Adaptador adaptador;

    public FrmRetornarPrestec(JDialog parent, Adaptador adaptador) {
        super(parent);
        this.adaptador = adaptador;
        setContentPane(contentPane);
        setModal(true);
        setSize(800, 600);
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(btnRetornar);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Retornar préstec");

        btnRetornar.setEnabled(false);

        // POSAR JSCROLLPANEL A LA LLISTA
        DefaultListModel<String> model = new DefaultListModel<>();
        model.clear();
        for (String element : adaptador.recuperarPrestecs()) {
            model.addElement(element);
        }
        lstPrestecsNR.setModel(model);

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
        btnRetornar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // retornar prestec + gestionar excepcions amb finestretes
                try {
                    adaptador.retornarPrestec(lstPrestecsNR.getSelectedIndex());
                    JOptionPane.showMessageDialog(parent, "Préstec creat correctament", "", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } catch (BiblioException e) {
                    JOptionPane.showMessageDialog(parent, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        lstPrestecsNR.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                actualitzarBotoRetornar();
            }
        });
    }
    // Mètode auxiliar per activar el botó acceptar
    private void actualitzarBotoRetornar() {
        btnRetornar.setEnabled(!lstPrestecsNR.isSelectionEmpty());
    }
}
