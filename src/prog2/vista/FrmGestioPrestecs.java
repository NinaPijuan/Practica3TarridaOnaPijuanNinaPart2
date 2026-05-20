package prog2.vista;

import prog2.adaptador.Adaptador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmGestioPrestecs extends JDialog {
    private JPanel contentPane;
    private JButton btnTornar;
    private JButton btnAfegir;
    private JButton btnRetornar;
    private JButton btnVisualitzar;
    private JButton btnVisualitzarNoRetornats;
    private Adaptador adaptador;

    public FrmGestioPrestecs(JFrame parent, Adaptador adaptador) {
        super(parent);
        this.adaptador = adaptador;
        setContentPane(contentPane);
        setModal(true);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setTitle("Gestió préstecs");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        btnAfegir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FrmAfegirPrestec frmAfegirPrestec = new FrmAfegirPrestec(FrmGestioPrestecs.this, adaptador);
                frmAfegirPrestec.setModal(true);
                frmAfegirPrestec.setVisible(true);
            }
        });
        btnRetornar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // FEM KUE OBRI ALGO KUE VISUALITZI PRESTECS I ES SELECCIONI?
            }
        });
        btnVisualitzar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FrmVisualitzar visualitzarPrestecs = new FrmVisualitzar(adaptador.recuperarPrestecs(), "Préstecs");
                visualitzarPrestecs.setModal(true);
                visualitzarPrestecs.setVisible(true);
            }
        });
        btnVisualitzarNoRetornats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FrmVisualitzar visualitzarNoRetornats = new FrmVisualitzar(adaptador.recuperarPrestecsNoRetornats(), "Préstecs no retornats");
                visualitzarNoRetornats.setModal(true);
                visualitzarNoRetornats.setVisible(true);
            }
        });
        btnTornar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
    }
}
