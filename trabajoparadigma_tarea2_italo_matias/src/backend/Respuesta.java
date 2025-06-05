package backend;

public class Respuesta {
    public String respuestaUsuario;
    public String estado; // "correcta" o "incorrecta"

    public Respuesta(String respuestaUsuario, String estado) {
        this.respuestaUsuario = respuestaUsuario;
        this.estado = estado;
    }
}