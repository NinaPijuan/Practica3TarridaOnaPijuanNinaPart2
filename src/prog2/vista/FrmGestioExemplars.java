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

    public FrmGestioExemplars(JFrame parent, Adaptador adaptador) {
        super(parent);
        this.adaptador = adaptador;
        setContentPane(contentPane);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setTitle("Gestió exemplars");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // els JDialogs no és EXIT_ON_CLOSE sinó això

        btnAfegir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Crea pestanya d'afegir exemplar, la fa modal i la fa visible (en akuest ordre!!)
                FrmAfegirExemplar frmAfegirExemplar = new FrmAfegirExemplar(FrmGestioExemplars.this, adaptador);
                frmAfegirExemplar.setModal(true);
                frmAfegirExemplar.setVisible(true);
            }
        });

        btnVisualitzar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Creem pestanya de visualitzar exemplars(amb llista i títol), la fem modal i la fem visible (en akuest ordre!!)
                FrmVisualitzar visualitzarExemplars = new FrmVisualitzar(adaptador.recuperarExemplars(), "Exemplars");
                visualitzarExemplars.setModal(true);
                visualitzarExemplars.setVisible(true);
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
