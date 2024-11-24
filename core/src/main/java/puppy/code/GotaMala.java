package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class GotaMala extends Gota {

    private final Texture eg = new Texture(Gdx.files.internal("dosecat.png"));

    public GotaMala(Texture imagenGota, float velocidadCaida, Sound sonidoEfecto) {
        super(imagenGota, velocidadCaida, sonidoEfecto);
    }

    public void activarEG() {
        this.setImagenGota(eg);
    }

    @Override
    public void aplicarEfecto(Tarro tarro) {
        EstrategiaEfecto estrategiaEfecto = this.getEstrategiaEfecto();
        estrategiaEfecto.aplicarEfecto(tarro);
    }

}
