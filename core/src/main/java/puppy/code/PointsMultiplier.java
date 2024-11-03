package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;

import java.awt.*;

public class PointsMultiplier implements Boost, ObjetoCaible {

    public Texture texturaBoostXp;
    public Rectangle area;
    private float factorMultiplicador;
    private long tiempoInicio;

    public PointsMultiplier(Texture texturaBoostXp) {
        this.texturaBoostXp = texturaBoostXp;
        this.area = area;
        this.factorMultiplicador = 1.5f;
    }

    public void boost() {
        tiempoInicio = TimeUtils.nanoTime();
    }

    public Boolean estaActivo() {
        return TimeUtils.timeSinceNanos(tiempoInicio) < 1_000_000_000; //La duración del boost es 10s
    }

    public float getFactorMultiplicador() {
        return estaActivo() ? factorMultiplicador : 1.0f;
    }



    public void caer(){}


}
