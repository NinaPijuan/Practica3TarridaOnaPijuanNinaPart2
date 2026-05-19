package prog2.vista;

import prog2.adaptador.Adaptador;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmAfegirExemplar extends JDialog {
    private JPanel contentPane;
    private JButton btnAcceptar;
    private JButton btnCancelar;
    private JTextField txtId;
    private JTextField txtTitol;
    private JTextField txtAutor;
    private JCheckBox chkAdmetLlarg;
    private JLabel etId;
    private JLabel etTitol;
    private JLabel etAutor;

    private Adaptador adaptador;

    public FrmAfegirExemplar(Adaptador adaptador) {
        this.adaptador = adaptador;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnAcceptar);
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
                //
            }
        });
        btnAcceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        chkAdmetLlarg.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {

            }
        });
    }
}
