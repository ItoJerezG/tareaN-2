package backend;

import java.util.Map;

public class RevisionDePrueba {

    private GestorPrueba gestor;

    public RevisionDePrueba(GestorPrueba gestor) {
        this.gestor = gestor;
    }

    public void mostrarResumen() {
        System.out.println("Resumen por Nivel de Bloom:");
        Map<String, Integer> bloom = gestor.resumenPorBloom();
        for (String nivel : bloom.keySet()) {
            System.out.println("- " + nivel + ": " + bloom.get(nivel) + " respuestas correctas");
        }

        System.out.println("\nResumen por Tipo de Pregunta:");
        Map<String, Integer> tipo = gestor.resumenPorTipo();
        for (String t : tipo.keySet()) {
            System.out.println("- " + t + ": " + tipo.get(t) + " respuestas correctas");
        }
    }
}
