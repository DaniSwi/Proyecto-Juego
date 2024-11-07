package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public class Dash implements ObjetoCaible, Boost {

    private Texture imagenDash;
    private boolean activo;
    private int usosRestantes;
    private final String nombre = "dashes";

    public Dash(Texture texture) {
        this.imagenDash = texture;
        this.activo = false;
        this.usosRestantes = 0;
    }

    public Texture getTexture() {
        return imagenDash;
    }

    public int getTiempoRestante() {
        return usosRestantes;
    }

    public String getNombreBoost() {
        return nombre;
    }

    public void aplicarEfecto(Tarro tarro) {
        aplicarBoost(tarro);
    }

    public void aplicarBoost(Tarro tarro) {}

    public void caer() {}

    public void boost() {
        this.activo = true;
        this.usosRestantes = 3;
    }
}
