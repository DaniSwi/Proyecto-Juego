package puppy.code;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class GotaBuena extends Gota {

    public GotaBuena(Texture imagenGota, float velocidadCaida, Sound sonidoEfecto) {
        super(imagenGota, velocidadCaida, sonidoEfecto);
    }

    @Override
    public void aplicarEfecto(Tarro tarro) {
        EstrategiaEfecto estrategiaEfecto = this.getEstrategiaEfecto();
        estrategiaEfecto.aplicarEfecto(tarro);
    }
}
