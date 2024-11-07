package puppy.code;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class GotaNormal extends Gota {

    public GotaNormal(Texture imagenGota, float velocidadCaida, Sound sonidoEfecto){
        super(imagenGota, velocidadCaida, sonidoEfecto);
    }

    public GotaNormal(Texture imagenGota, float velocidadCaida){
        super(imagenGota, velocidadCaida);
    }

    @Override
    public void aplicarEfecto(Tarro tarro) {
        EstrategiaEfecto estrategiaEfecto = this.getEstrategiaEfecto();
        estrategiaEfecto.aplicarEfecto(tarro);
    }

}
