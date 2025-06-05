package frontend;

import javax.swing.*;
import java.awt.*;
import backend.*;

public class PanelItem extends JPanel {
    private GestorPrueba gestor;
    private JLabel etiquetaPregunta;
    private ButtonGroup grupoOpciones;
    private JRadioButton[] botones;
    private JButton botonAnterior, botonSiguiente;

    public PanelItem(GestorPrueba gestor) {
        this.gestor = gestor;
        setLayout(new BorderLayout());
        etiquetaPregunta = new JLabel();
        add(etiquetaPregunta, BorderLayout.NORTH);
        grupoOpciones = new ButtonGroup();
        JPanel opcionesPanel = new JPanel(new GridLayout(0, 1)); // Filas flexibles, 1 columna
        botones = new JRadioButton[4]; // Asumiendo un máximo de 4 opciones según tu bucle
        for (int i = 0; i < 4; i++) {
            botones[i] = new JRadioButton();
            grupoOpciones.add(botones[i]);
            opcionesPanel.add(botones[i]);
            botones[i].setVisible(false); // Inicialmente ocultar todos
        }
        add(opcionesPanel, BorderLayout.CENTER);

        JPanel navegacion = new JPanel();
        botonAnterior = new JButton("Atrás");
        botonAnterior.addActionListener(e -> {
            // Opcional: Guardar respuesta incluso al retroceder
            // guardarRespuestaActual();
            gestor.retroceder();
        });

        botonSiguiente = new JButton("Siguiente");
        botonSiguiente.addActionListener(e -> {
            guardarRespuestaActual(); // Guardar la respuesta para la pregunta actual

            if (gestor.indiceActual == gestor.items.size() - 1) {
                // Este fue el clic en "Enviar respuestas"
                mostrarRevision();
            } else {
                gestor.avanzar();
            }
        });
        navegacion.add(botonAnterior);
        navegacion.add(botonSiguiente);
        add(navegacion, BorderLayout.SOUTH);

        actualizarContenido(); // Carga inicial del contenido
    }

    private void guardarRespuestaActual() {
        String respuestaSeleccionada = null;
        for (JRadioButton b : botones) {
            if (b.isSelected() && b.isVisible()) { // Verificar también la visibilidad
                respuestaSeleccionada = b.getText();
                break;
            }
        }
        if (respuestaSeleccionada != null) {
            gestor.guardarRespuesta(respuestaSeleccionada);
        } else {
            // Manejar el caso donde no se selecciona ninguna opción, si es necesario.
            // Por ejemplo, guardar una respuesta vacía o un marcador específico de "no respondido".
            // El GestorPrueba actual se inicializa con "sin responder", por lo que no guardar
            // aquí significa efectivamente que permanece "sin responder" a menos que se haya respondido previamente.
            // Si deseas marcarlo explícitamente como "omitido" ahora, agregarías:
            // gestor.guardarRespuesta(""); // O algún otro marcador
        }
    }

    private void mostrarRevision() {
        // Ocultar o desechar la ventana de prueba actual
        SwingUtilities.getWindowAncestor(this).dispose(); // 'this' se refiere a PanelItem

        // Mostrar la ventana de revisión
        VistaRevisionDePrueba vistaRevision = new VistaRevisionDePrueba(gestor);
        vistaRevision.setVisible(true);
    }

    public void actualizarContenido() {
        Item actual = gestor.items.get(gestor.indiceActual);
        etiquetaPregunta.setText("<html>" + (gestor.indiceActual + 1) + ". " + actual.pregunta + "</html>"); // Numeración y HTML para posible ajuste de línea

        // Limpiar selección previa y establecer opciones de radio button
        grupoOpciones.clearSelection(); // Importante para evitar selecciones persistentes

        for (int i = 0; i < botones.length; i++) { // Iterar sobre todos los radio buttons
            if (i < actual.opciones.size()) {
                botones[i].setText(actual.opciones.get(i));
                botones[i].setVisible(true);
                // Reseleccionar respuesta guardada previamente si existe
                Respuesta respuestaGuardada = gestor.respuestas.get(gestor.indiceActual);
                if (respuestaGuardada != null && botones[i].getText().equals(respuestaGuardada.respuestaUsuario)) {
                    botones[i].setSelected(true);
                }
            } else {
                botones[i].setVisible(false); // Ocultar radio buttons no utilizados
                botones[i].setText(""); // Limpiar texto de botones no utilizados
            }
        }

        botonAnterior.setEnabled(gestor.indiceActual > 0);
        if (gestor.indiceActual == gestor.items.size() - 1) {
            botonSiguiente.setText("Enviar respuestas");
        } else {
            botonSiguiente.setText("Siguiente");
        }
    }
}