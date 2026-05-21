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
    private JPanel panelInferior;
    private JPanel panelSuperior;
    private JPanel panelBtns;
    private Adaptador adaptador;

    public FrmGestioExemplars(JFrame parent, Adaptador adaptador) {
        super(parent);
        this.adaptador = adaptador;
        setContentPane(contentPane);
        setSize(600, 540);
        setLocationRelativeTo(null);
        setTitle("Gestió d'Exemplars — BiblioUB");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // els JDialogs no és EXIT_ON_CLOSE sinó això

        // ESTÈTICA
        // ── Tema ─────────────────────────────────────────────────────────────
        contentPane.setBackground(AppBiblioUB.C_PANEL);
        contentPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(3, 0, 0, 0, AppBiblioUB.C_OR),
                BorderFactory.createEmptyBorder(16, 20, 16, 20)
        ));

        panelSuperior.setBackground(AppBiblioUB.C_PANEL);
        panelInferior.setBackground(AppBiblioUB.C_PANEL);
        panelBtns.setBackground(AppBiblioUB.C_PANEL);

        AppBiblioUB.estilitzarLlista(lstExemplars);
        AppBiblioUB.estilitzarScrollPane(scrollPane);
        AppBiblioUB.estilitzarBotoPrimari(btnAfegir, "+ Afegir exemplar");
        AppBiblioUB.estilitzarBotoCancel(btnTornar);
        btnTornar.setText("← Tornar");

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
