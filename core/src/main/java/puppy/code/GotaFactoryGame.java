package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class GotaFactoryGame implements GotaFactory {

    public Gota crearGotaBuena() {
        return new GotaBuena(new Texture(Gdx.files.internal("tapsin.png")), 30, Gdx.audio.newSound(Gdx.files.internal("sonidoVidaAumentada.mp3")));
    }
    public Gota crearGotaMala(boolean b) {
        if(b)
            return new GotaMala(new Texture(Gdx.files.internal("dosecat.png")), 30, Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")));
        return new GotaMala(new Texture(Gdx.files.internal("dropBad.png")), 30, Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")));
    }
    public Gota crearGotaNormal() {
        return new GotaNormal(new Texture(Gdx.files.internal("drop.png")), 30, Gdx.audio.newSound(Gdx.files.internal("drop.wav")));
    }

}
