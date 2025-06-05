package frontend;

import backend.GestorPrueba;
import backend.RevisionDePrueba;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class VistaRevisionDePrueba extends JFrame {

    private GestorPrueba gestor;

    public VistaRevisionDePrueba(GestorPrueba gestor) {
        this.gestor = gestor;
        setTitle("Revisión de Prueba");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        RevisionDePrueba revision = new RevisionDePrueba(gestor);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel tituloBloom = new JLabel("Resumen por Nivel de Bloom:");
        panel.add(tituloBloom);
        Map<String, Integer> resumenBloom = gestor.resumenPorBloom();
        for (Map.Entry<String, Integer> entry : resumenBloom.entrySet()) {
            panel.add(new JLabel(entry.getKey() + ": " + entry.getValue() + " respuestas correctas"));
        }

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel tituloTipo = new JLabel("Resumen por Tipo de Pregunta:");
        panel.add(tituloTipo);
        Map<String, Integer> resumenTipo = gestor.resumenPorTipo();
        for (Map.Entry<String, Integer> entry : resumenTipo.entrySet()) {
            panel.add(new JLabel(entry.getKey() + ": " + entry.getValue() + " respuestas correctas"));
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane);
    }
}
