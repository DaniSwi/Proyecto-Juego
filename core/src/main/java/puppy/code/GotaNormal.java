package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class GotaNormal extends Gota {

    private final Texture eg = new Texture(Gdx.files.internal("omero.png"));

    public GotaNormal(Texture imagenGota, float velocidadCaida, Sound sonidoEfecto){
        super(imagenGota, velocidadCaida, sonidoEfecto);
    }

    @Override
    public void aplicarEfecto(Tarro tarro) {
        EstrategiaEfecto estrategiaEfecto = this.getEstrategiaEfecto();
        estrategiaEfecto.aplicarEfecto(tarro);
    }

}
