package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public class GotaBuena extends Gota {

    public GotaBuena(Texture imagenGota, float velocidadCaida) {
        super(imagenGota, velocidadCaida);
    }

    @Override
    public void aplicarEfecto(Tarro tarro) {
        tarro.aumentarVida();
    }
}
