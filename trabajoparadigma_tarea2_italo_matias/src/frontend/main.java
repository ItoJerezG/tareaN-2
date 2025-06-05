import backend.*;
import frontend.*;

import javax.swing.*;
import java.io.File;

public class main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GestorPrueba gestor = new GestorPrueba();
            JFileChooser selector = new JFileChooser();
            int resultado = selector.showOpenDialog(null);
            if (resultado == JFileChooser.APPROVE_OPTION) {
                File archivo = selector.getSelectedFile();
                try {
                    gestor.cargarDesdeCSV(archivo);
                    new VentanaPrincipal(gestor);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error al cargar el archivo: " + e.getMessage());
                }
            }
        });
    }
}
