package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class Dash implements ObjetoCaible, Boost {

    private final Texture imagenDash;
    private boolean activo;
    private int usosRestantes;
    private final String nombre = "Dashes";
    private float duracionDash;
    private final int velocidadDash = 1600;
    private final Sound sonido;
    private final String teclaActivacion = "Q|E";

    public Dash(Texture texture) {
        this.imagenDash = texture;
        this.activo = false;
        this.usosRestantes = 0;
        this.sonido = Gdx.audio.newSound(Gdx.files.internal("tp.mp3"));
    }

    public Texture getTexture() {
        return imagenDash;
    }

    public int getTiempoRestante() {
        return usosRestantes;
    }

    public void reproducirSonido() {
        sonido.play();
    }

    public void quitarUso() {
        reproducirSonido();
        usosRestantes--;
    }

    public String getNombreBoost() {
        return nombre;
    }
    @Override
    public void aplicarEfecto(Tarro tarro) {
        this.activo = true;
        this.usosRestantes = 3;
        this.duracionDash = 0.2f;
        tarro.activarDash(this);
    }
    @Override
    public void caer() {}
    @Override
    public void boost(Tarro tarro) {
        aplicarEfecto(tarro);
    }

    public boolean estaActivo() {
        if(duracionDash > 0) {
            return true;
        } else {
            activo = false;
            return false;
        }
    }

    public float getVelocidadDash() {
        return velocidadDash;
    }

    public String getTeclaActivacion() {
        return teclaActivacion;
    }
}
