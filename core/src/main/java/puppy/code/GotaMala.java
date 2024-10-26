package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public class GotaMala extends Gota {

    public GotaMala(Texture imagenGota, float velocidadCaida) {
        super(imagenGota, velocidadCaida);
    }

    @Override
    public void aplicarEfecto(Tarro tarro) {
        tarro.dañar();
    }
}
