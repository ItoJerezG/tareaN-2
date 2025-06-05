package frontend;

import javax.swing.*;

import backend.*;

public class VentanaPrincipal extends JFrame implements Observador {
    private GestorPrueba gestor;
    private PanelItem panelItem;

    public VentanaPrincipal(GestorPrueba gestor) {
        this.gestor = gestor;
        this.gestor.agregarObservador(this);
        setTitle("Prueba - Taxonomía de Bloom");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        panelItem = new PanelItem(gestor);
        add(panelItem);
        setVisible(true);
    }

    public void actualizar() {
        panelItem.actualizarContenido();
    }
}
