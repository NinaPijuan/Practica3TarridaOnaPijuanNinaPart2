package prog2.vista;

import prog2.adaptador.Adaptador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmAfegirUsuari extends JDialog {
    private JPanel contentPane;
    private JButton btnAcceptar;
    private JButton btnCancelar;
    private JTextField txtNom;
    private JTextField txtEmail;
    private JTextField txtAdreca;
    private JCheckBox chkEstudiant;
    private JLabel etNom;
    private JLabel etEmail;
    private JLabel etAdreca;
    private Adaptador adaptador;

    public FrmAfegirUsuari(Adaptador adaptador) {
        this.adaptador = adaptador;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnAcceptar);

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
                // Tornar a FrmGestioUsuari??

                // Fer que el botó no estigui actiu fins que no s'afegeixin les dades mínimes per afegir un usuari
                // (opcional)
            }
        });
    }
}
