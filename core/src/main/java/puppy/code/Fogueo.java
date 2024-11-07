package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public class Fogueo implements Boost, ObjetoCaible {

    private final String nombre = "Fogueos";
    private Texture imagenFogueo;
    private boolean activo;
    private int usosRestantes;

    public Fogueo(Texture imagenFogueo) {
        this.imagenFogueo = imagenFogueo;
        this.activo = false;
        this.usosRestantes = 0;
    }

    public Texture getTexture() {
        return imagenFogueo;
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

    public void aplicarBoost(Tarro tarro) {

    }

    public void boost(Tarro tarro) {
        this.activo = true;
        this.usosRestantes = 3;
    }

    public void caer() {}
}
