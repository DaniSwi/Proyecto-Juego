package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class Fogueo implements Boost, ObjetoCaible {

    private final String nombre = "Fogueos";
    private Texture imagenFogueo;
    private boolean activo;
    private int usosRestantes;
    private float tiempoRestante;
    private Sound hado;
    private boolean estaSiendoUsado;

    public Fogueo(Texture imagenFogueo) {
        this.imagenFogueo = imagenFogueo;
        this.activo = false;
        this.usosRestantes = 0;
        this.tiempoRestante = 0;
        this.hado = Gdx.audio.newSound(Gdx.files.internal("kurohitsugi.mp3"));
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
        tarro.activarFogueo(this);
        boost(tarro);
    }

    public void boost(Tarro tarro) {
        this.activo = true;
        this.usosRestantes = 1;
        this.tiempoRestante = duracionBoost + 1f;
        this.estaSiendoUsado = false;
    }

    public void actualizar() {
        if(activo) {
            tiempoRestante -= Gdx.graphics.getDeltaTime();
            if(tiempoRestante <= 0) {
                --this.usosRestantes;
                this.activo = false;
                tiempoRestante = 0;
                estaSiendoUsado = false;
            }
        }
    }

    public void setUso() {
        this.estaSiendoUsado = true;
    }

    public boolean estaActivo() {
        return activo;
    }

    public boolean estaActivoL() {
        return estaSiendoUsado;
    }

    public void reproducirSonido() {
        hado.play(0.5f);
    }

    public void caer() {}

    public boolean estaPorAcabarse() {
        return tiempoRestante <= 0.25f;
    }
}
