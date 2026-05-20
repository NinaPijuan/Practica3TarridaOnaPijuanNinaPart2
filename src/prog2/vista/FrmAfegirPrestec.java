package prog2.vista;

import prog2.adaptador.Adaptador;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

public class FrmAfegirPrestec extends JDialog {
    private JPanel contentPane;
    private JButton btnAcceptar;
    private JButton btnCancelar;
    private JComboBox<String> cmbUsuari;
    private JComboBox<String> cmbExemplar;
    private JLabel etUsuari;
    private JLabel etExemplar;
    private JCheckBox chkPrestecLlarg;

    private Adaptador adaptador;


    public FrmAfegirPrestec(JDialog parent, Adaptador adaptador) {
        super(parent);
        this.adaptador = adaptador;
        setContentPane(contentPane);
        setModal(true);
        setSize(800, 600);
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(btnAcceptar);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Afegir préstec");

        btnAcceptar.setEnabled(false);

        DefaultComboBoxModel<String> modelExemplar = (DefaultComboBoxModel<String>) cmbExemplar.getModel();
        modelExemplar.removeAllElements();
        modelExemplar.addElement("...");
        for (String element : adaptador.recuperarExemplars()) {
            modelExemplar.addElement(element);
        }

        DefaultComboBoxModel<String> modelUsuari = (DefaultComboBoxModel<String>) cmbUsuari.getModel();
        modelUsuari.removeAllElements();
        modelUsuari.addElement("...");
        for (String element : adaptador.recuperarUsuaris()) {
            modelUsuari.addElement(element);
        }

        // Listeners per habilitar/deshabilitar botó
        cmbUsuari.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                       actualitzarBotoAcceptar();
            }
        });

        cmbExemplar.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                actualitzarBotoAcceptar();
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
        btnAcceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // afegir prestec + gestionar excepcions amb finestretes
                try {
                    adaptador.afegirPrestec(cmbExemplar.getSelectedIndex() - 1, cmbUsuari.getSelectedIndex() - 1, chkPrestecLlarg.isSelected());
                    JOptionPane.showMessageDialog(parent, "Préstec creat correctament", "", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } catch (BiblioException e) {
                    JOptionPane.showMessageDialog(parent, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }

    // Mètode auxiliar per activar el botó acceptar
    private void actualitzarBotoAcceptar() {
        boolean usuariOk = cmbUsuari.getSelectedIndex() > 0;
        boolean exemplarOk = cmbExemplar.getSelectedIndex() > 0;
        btnAcceptar.setEnabled(usuariOk && exemplarOk);
    }

}
