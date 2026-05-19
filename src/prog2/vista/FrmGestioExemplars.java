package prog2.vista;

import prog2.adaptador.Adaptador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmGestioExemplars extends JDialog {
    private JPanel contentPane;
    private JButton btnTornar;
    private JButton btnAfegir;
    private JButton btnVisualitzar;
    private Adaptador adaptador;

    public FrmGestioExemplars(Adaptador adaptador) {
        this.adaptador = adaptador;
        setContentPane(contentPane);
        setSize(500, 400);
        setTitle("Gestió exemplars");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // els JDialogs no és EXIT_ON_CLOSE sinó això

        btnAfegir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Crea pestanya d'afegir exemplar, la fa modal i la fa visible (en akuest ordre!!)
                FrmAfegirExemplar frmAfegirExemplar = new FrmAfegirExemplar(adaptador);
                frmAfegirExemplar.setModal(true);
                frmAfegirExemplar.setVisible(true);
                frmAfegirExemplar.setLocationRelativeTo(null); // això la posa al centre de la pantalla
            }
        });

        btnVisualitzar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Creem pestanya de visualitzar exemplars(amb llista i títol), la fem modal i la fem visible (en akuest ordre!!)
                FrmVisualitzar visualitzarExemplars = new FrmVisualitzar(adaptador.recuperarExemplars(), "Exemplars");
                visualitzarExemplars.setModal(true);
                visualitzarExemplars.setVisible(true);
                visualitzarExemplars.setLocationRelativeTo(null); // això la posa al centre de la pantalla
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
