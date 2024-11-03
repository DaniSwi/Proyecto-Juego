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
        //Se le suma 10 puntos por agarrar la gota correctamente
        reproducirSonido();
        tarro.sumarPuntos(10);
    }
}
