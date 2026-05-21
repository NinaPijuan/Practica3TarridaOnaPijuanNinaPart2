package prog2.vista;

import prog2.adaptador.Adaptador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmGestioExemplars extends JDialog {
    private JPanel contentPane;
    private JButton btnTornar;
    private JButton btnAfegir;
    private JList<String> lstExemplars;
    private JScrollPane scrollPane;
    private Adaptador adaptador;

    public FrmGestioExemplars(JFrame parent, Adaptador adaptador) {
        super(parent);
        this.adaptador = adaptador;
        setContentPane(contentPane);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setTitle("Gestió exemplars");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // els JDialogs no és EXIT_ON_CLOSE sinó això

        // mostrar llista des del primer moment
        refrescarLlista();

        btnAfegir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Crea pestanya d'afegir exemplar, la fa modal i la fa visible (en akuest ordre!!)
                FrmAfegirExemplar frmAfegirExemplar = new FrmAfegirExemplar(FrmGestioExemplars.this, adaptador);
                frmAfegirExemplar.setModal(true);
                frmAfegirExemplar.setVisible(true);

                // Refrescar llista
                refrescarLlista();
            }
        });

        btnTornar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
    }

    // Mètode per refrescar
    private void refrescarLlista() {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (String element : adaptador.recuperarExemplars()) {
            model.addElement(element);
        }
        lstExemplars.setModel(model);
    }

}
