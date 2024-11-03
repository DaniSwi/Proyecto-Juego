package puppy.code;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class GotaBuena extends Gota {

    public GotaBuena(Texture imagenGota, float velocidadCaida, Sound sonidoEfecto) {
        super(imagenGota, velocidadCaida, sonidoEfecto);
    }

    public GotaBuena(Texture imagenGota, float velocidadCaida) {
        super(imagenGota, velocidadCaida);
    }

    @Override
    public void aplicarEfecto(Tarro tarro) {
        reproducirSonido();
        tarro.aumentarVida();
    }
}
