package prog2.vista;

import prog2.adaptador.Adaptador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmGestioUsuaris extends JDialog {
    private JPanel contentPane;
    private JButton btnTornar;
    private JButton btnAfegir;
    private JList<String> lstUsuaris;
    private JScrollPane scrollPane;
    private Adaptador adaptador;

    public FrmGestioUsuaris(JFrame parent, Adaptador adaptador) {
        super(parent);
        this.adaptador = adaptador;
        setContentPane(contentPane);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setTitle("Gestió usuaris");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // els JDialogs no és EXIT_ON_CLOSE sinó això

        // mostrar llista des del primer moment
        refrescarLlista();

        btnAfegir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Creem pestanya d'afegir usuari, la fem modal i la fem visible (en akuest ordre!!)
                FrmAfegirUsuari frmAfegirUsuari = new FrmAfegirUsuari(FrmGestioUsuaris.this, adaptador);
                frmAfegirUsuari.setModal(true);
                frmAfegirUsuari.setVisible(true);

                // quan torna (el dialog s'ha tancat), refrescar llista:
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

    // Mètode per refrescar la llista
    private void refrescarLlista() {
        DefaultListModel<String> model = new DefaultListModel<>();
        for(String element : adaptador.recuperarUsuaris()) {
            model.addElement(element);
        }
        lstUsuaris.setModel(model);
    }
}
