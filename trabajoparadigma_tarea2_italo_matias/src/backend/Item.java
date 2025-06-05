package backend;

import java.util.*;

public class Item {
    public String pregunta;
    public String respuestaCorrecta;
    public String nivelBloom;
    public int tiempoEstimado;
    public String tipo;
    public List<String> opciones;

    public Item(String tipo, String pregunta, List<String> opciones, String respuestaCorrecta, String nivelBloom, int tiempoEstimado) {
        this.tipo = tipo;
        this.pregunta = pregunta;
        this.opciones = opciones;
        this.respuestaCorrecta = respuestaCorrecta;
        this.nivelBloom = nivelBloom;
        this.tiempoEstimado = tiempoEstimado;
    }
}
