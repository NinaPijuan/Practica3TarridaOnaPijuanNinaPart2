package prog2.vista;

import prog2.adaptador.Adaptador;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class FrmAfegirPrestec extends JDialog {
    private JPanel contentPane;
    private JButton btnAcceptar;
    private JButton btnCancelar;
    private JComboBox cmbUsuari;
    private JComboBox cmbExemplar;
    private JLabel etUsuari;
    private JLabel etExemplar;
    private JCheckBox chkPrestecLlarg;

    private Adaptador adaptador;


    public FrmAfegirPrestec(Adaptador adaptador) {

        this.adaptador = adaptador;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnAcceptar);
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
            }
        });
        chkPrestecLlarg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        cmbUsuari.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {

            }
        });
        cmbExemplar.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {

            }
        });
        chkPrestecLlarg.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {

            }
        });
    }
}
