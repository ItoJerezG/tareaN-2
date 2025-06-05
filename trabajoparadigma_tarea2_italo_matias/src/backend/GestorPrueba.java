package backend;

import java.io.*;
import java.util.*;
import java.nio.charset.StandardCharsets;

public class GestorPrueba {
    public List<Item> items;
    public List<Respuesta> respuestas;
    public int indiceActual;
    public List<Observador> observadores;

    public GestorPrueba() {
        this.items = new ArrayList<>();
        this.respuestas = new ArrayList<>();
        this.observadores = new ArrayList<>();
        this.indiceActual = 0;
    }

    public void cargarDesdeCSV(File archivo) throws Exception {
        BufferedReader lector = new BufferedReader(new InputStreamReader(new FileInputStream(archivo), StandardCharsets.UTF_8));
        String linea;
        while ((linea = lector.readLine()) != null) {
            String[] partes = linea.split(",");
            if (partes.length < 6) continue;
            String tipo = partes[0];
            String pregunta = partes[1];
            List<String> opciones = Arrays.asList(partes[2].split(";"));
            String correcta = partes[3];
            String nivel = partes[4];
            int tiempo = Integer.parseInt(partes[5]);
            Item item = new Item(tipo, pregunta, opciones, correcta, nivel, tiempo);
            items.add(item);
            respuestas.add(new Respuesta("", "sin responder"));
        }
        lector.close();
        notificarObservadores();
    }

    public void avanzar() {
        if (indiceActual < items.size() - 1) {
            indiceActual++;
            notificarObservadores();
        }
    }

    public void retroceder() {
        if (indiceActual > 0) {
            indiceActual--;
            notificarObservadores();
        }
    }

    public void guardarRespuesta(String respuesta) {
        Item actual = items.get(indiceActual);
        String estado = (respuesta.equals(actual.respuestaCorrecta)) ? "correcta" : "incorrecta";
        respuestas.set(indiceActual, new Respuesta(respuesta, estado));
    }

    public void agregarObservador(Observador o) {
        observadores.add(o);
    }

    public void notificarObservadores() {
        for (Observador o : observadores) {
            o.actualizar();
        }
    }

    public Map<String, Integer> resumenPorBloom() {
        Map<String, Integer> resumen = new HashMap<>();
        for (int i = 0; i < items.size(); i++) {
            String nivel = items.get(i).nivelBloom;
            String estado = respuestas.get(i).estado;
            if (estado.equals("correcta")) {
                resumen.put(nivel, resumen.getOrDefault(nivel, 0) + 1);
            }
        }
        return resumen;
    }

    public Map<String, Integer> resumenPorTipo() {
        Map<String, Integer> resumen = new HashMap<>();
        for (int i = 0; i < items.size(); i++) {
            String tipo = items.get(i).tipo;
            String estado = respuestas.get(i).estado;
            if (estado.equals("correcta")) {
                resumen.put(tipo, resumen.getOrDefault(tipo, 0) + 1);
            }
        }
        return resumen;
    }
}