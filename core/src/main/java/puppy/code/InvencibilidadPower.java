package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class InvencibilidadPower implements Boost, ObjetoCaible{

    public Texture imagenBoost;
    private boolean activo;
    private float tiempoRestante;
    private Sound sonido;
    private final String nombre = "Inv";

    public InvencibilidadPower(Texture imagenBoost) {
        this.imagenBoost = imagenBoost;
        this.activo = false;
        this.tiempoRestante = 0;
        this.sonido = Gdx.audio.newSound(Gdx.files.internal("boostInvencible.mp3"));
    }

    public void boost() {
        this.activo = true;
        this.tiempoRestante = duracionBoost + 3;
    }

    public void actualizar() {
        if(activo) {
            tiempoRestante -= Gdx.graphics.getDeltaTime();
            if(tiempoRestante <= 0) {
                activo = false;
                tiempoRestante = 0;
            }
        }
    }

    public String getNombreBoost() {
        return nombre;
    }

    public int getTiempoRestante() {
        return (int)tiempoRestante;
    }

    public void caer(){

    }

    public Texture getTexture() {
        return imagenBoost;
    }

    public void aplicarEfecto(Tarro tarro) {
        tarro.activarInvencibilidad(this);
    }

    public boolean estaActivo() {
        return activo;
    }

    public void activarSonido() {
        sonido.play(0.5f);
    }


}
