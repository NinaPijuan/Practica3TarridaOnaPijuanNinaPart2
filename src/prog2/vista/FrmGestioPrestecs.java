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

    public FrmGestioPrestecs(Adaptador adaptador) {
        this.adaptador = adaptador;
        setContentPane(contentPane);
        setModal(true);
        btnAfegir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FrmAfegirPrestec frmAfegirPrestec = new FrmAfegirPrestec(adaptador);
                setModal(true);
                // ALGO MÉS?
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
                // KUÈ OBREEEEEEE
            }
        });
        btnVisualitzarNoRetornats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // KUÈ OBRE
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
