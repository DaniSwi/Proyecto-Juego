package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;
import org.w3c.dom.Text;

import java.awt.*;

public class PointsMultiplier implements Boost, ObjetoCaible {

    public Texture texturaBoostXp;
    private boolean activo;
    private float factorMultiplicador;
    private float tiempoRestante;
    private Sound sonido;

    public PointsMultiplier(Texture texturaBoostXp) {
        this.texturaBoostXp = texturaBoostXp;
        this.factorMultiplicador = 1.5f;
        this.activo = false;
        this.tiempoRestante = 0;
        this.sonido = Gdx.audio.newSound(Gdx.files.internal("boostPuntosSonido.mp3"));
    }

    public void boost() {
        this.activo = true;
        this.tiempoRestante = duracionBoost;
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

    public Boolean estaActivo() {
        return activo;
    }

    public float getFactorMultiplicador() {
        return estaActivo() ? factorMultiplicador : 1.0f;
    }

    public void caer(){}

    public Texture getTexture(){
        return texturaBoostXp;
    }

    public void activarSonido() {
        sonido.play();
    }

    public void aplicarEfecto(Tarro tarro) {
        tarro.activarMultiplicador(this);
    }


}
