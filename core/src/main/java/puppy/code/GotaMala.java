package puppy.code;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class GotaMala extends Gota {

    public GotaMala(Texture imagenGota, float velocidadCaida, Sound sonidoEfecto) {
        super(imagenGota, velocidadCaida, sonidoEfecto);
    }

    public GotaMala(Texture imagenGota, float velocidadCaida) {
        super(imagenGota, velocidadCaida);
    }

    @Override
    public void aplicarEfecto(Tarro tarro) {
        reproducirSonido();
        tarro.dañar();
    }
}
