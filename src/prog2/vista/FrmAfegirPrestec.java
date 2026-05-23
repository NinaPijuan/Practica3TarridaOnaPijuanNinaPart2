package prog2.vista;

import prog2.adaptador.Adaptador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Finestra de diàleg per afegir un nou préstec a la biblioteca
 */
public class FrmAfegirPrestec extends JDialog {
    private JPanel contentPane;
    private JButton btnAcceptar;
    private JButton btnCancelar;
    private JComboBox<String> cmbUsuari;
    private JComboBox<String> cmbExemplar;
    private JLabel etUsuari;
    private JLabel etExemplar;
    private JCheckBox chkPrestecLlarg;
    private JPanel panelInferior;
    private JPanel panelBtns;
    private JPanel panelSuperior;
    private JPanel panelUsuari;
    private JPanel panelExemplar;
    private JPanel panelChk;
    private JScrollPane scrollUsuari;
    private JScrollPane scrollExemplar;

    private Adaptador adaptador;


    /**
     * Crea i configura el diàleg per afegir un préstec.
     * @param parent el JDialog pare que llança aquest diàleg
     * @param adaptador l'adaptador que proporciona accés al model
     */
    public FrmAfegirPrestec(JDialog parent, Adaptador adaptador) {
        super(parent);
        this.adaptador = adaptador;
        setContentPane(contentPane);

        // Modal bloqueja la finestra pare fins que es tanqui
        setModal(true);

        // Amplada més gran per mostrar tot el toString (i que es vegin el botó d'acceptar)
        setSize(1200, 380);
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(btnAcceptar);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Afegir Préstec — BiblioUB");

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
        panelExemplar.setBackground(AppBiblioUB.C_PANEL);
        panelUsuari.setBackground(AppBiblioUB.C_PANEL);
        panelChk.setBackground(AppBiblioUB.C_PANEL);

        AppBiblioUB.estilitzarScrollPane(scrollExemplar);
        AppBiblioUB.estilitzarScrollPane(scrollUsuari);
        AppBiblioUB.estilitzarEtiqueta(etExemplar);
        AppBiblioUB.estilitzarEtiqueta(etUsuari);
        AppBiblioUB.estilitzarComboBox(cmbExemplar);
        AppBiblioUB.estilitzarComboBox(cmbUsuari);
        AppBiblioUB.estilitzarCheckBox(chkPrestecLlarg);
        AppBiblioUB.estilitzarBotoAcceptar(btnAcceptar);
        AppBiblioUB.estilitzarBotoCancel(btnCancelar);

        //

        // Botó Acceptar desactivat al principi fins que es trien els dos elements
        btnAcceptar.setEnabled(false);

        /* Per crear els ComboBox:
            Primer s'obtenen els exemplars o usuaris de l'adaptador i s'afegeixen al model del JComboBox.
            El primer element és "..." que indica que l'usuari encara no ha fet cap selecció (índex 0 = no vàlid).
         */
        DefaultComboBoxModel<String> modelExemplar = (DefaultComboBoxModel<String>) cmbExemplar.getModel();
        modelExemplar.removeAllElements();
        modelExemplar.addElement("..."); // Marcador de selecció buida
        for (String element : adaptador.recuperarExemplars()) {
            modelExemplar.addElement(element);
        }

        DefaultComboBoxModel<String> modelUsuari = (DefaultComboBoxModel<String>) cmbUsuari.getModel();
        modelUsuari.removeAllElements();
        modelUsuari.addElement("...");
        for (String element : adaptador.recuperarUsuaris()) {
            modelUsuari.addElement(element);
        }

        // Listeners per habilitar/deshabilitar botó
        // Cada vegada que canvia la selecció, es recalcula si els dos tenen una selecció vàlida (índex > 0)
        cmbUsuari.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                       actualitzarBotoAcceptar();
            }
        });

        cmbExemplar.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                actualitzarBotoAcceptar();
            }
        });

        // Tanca el diàleg sense fer cap canvi
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });

        // Transmet a l'adaptador els índexs de l'exemplar i l'usuari seleccionats
        // (es resta 1 per compensar l'element "...")
        // Mostra un missatge informatiu o d'error segons el resultat
        btnAcceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    // getSelectedIndex() - 1 perquè l'índex 0 és "..."
                    adaptador.afegirPrestec(cmbExemplar.getSelectedIndex() - 1, cmbUsuari.getSelectedIndex() - 1, chkPrestecLlarg.isSelected());
                    JOptionPane.showMessageDialog(FrmAfegirPrestec.this, "Préstec creat correctament", "", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    // FrmGestioPrestecs refresca la llista

                } catch (BiblioException e) {
                    JOptionPane.showMessageDialog(FrmAfegirPrestec.this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }

    // MÈTODE AUXILIAR

    /** Activa o desactiva el botó "Acceptar" en funció de si s'ha seleccionat
     * un element vàlid (índex > 0) en els dos JComboBox.
     */
    private void actualitzarBotoAcceptar() {
        boolean usuariOk = cmbUsuari.getSelectedIndex() > 0;
        boolean exemplarOk = cmbExemplar.getSelectedIndex() > 0;
        btnAcceptar.setEnabled(usuariOk && exemplarOk);
    }

}
