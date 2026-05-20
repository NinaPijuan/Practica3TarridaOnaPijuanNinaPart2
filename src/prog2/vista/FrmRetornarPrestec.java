package prog2.vista;

import prog2.adaptador.Adaptador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmRetornarPrestec extends JDialog {
    private JPanel contentPane;
    private JButton btnRetornar;
    private JButton btnCancelar;
    private JLabel etPrestecs;
    private JList lstPrestecsNR;

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
            }
        });

    }
}
