package prog2.vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

public class FrmVisualitzar extends JDialog {
    private JPanel contentPane;
    private JButton btnTornar;
    private JList<String> list;

    public FrmVisualitzar(List llista, String nom) {
        setContentPane(contentPane);
        setSize(500, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle(nom);

        // Creem la JList amb la llista (cal convertir-la a DefaultListModel pk és com treballa JList)
        DefaultListModel<String> model = new DefaultListModel<>();
        Iterator<String> it = llista.iterator();
        while (it.hasNext()) {
            String s = it.next();
            model.addElement(s);
        }
        list.setModel(model);


        btnTornar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
    }
}
