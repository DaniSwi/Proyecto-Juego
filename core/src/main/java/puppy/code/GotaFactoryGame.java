package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class GotaFactoryGame implements GotaFactory {

    private final GotaNormal gotaNormal;
    private final GotaBuena gotaBuena;
    private final GotaMala gotaMala;

    public GotaFactoryGame(GotaNormal gotaNormal, GotaBuena gotaBuena, GotaMala gotaMala) {
        this.gotaNormal = gotaNormal;
        this.gotaBuena = gotaBuena;
        this.gotaMala = gotaMala;
    }

    public Gota crearGotaBuena() {
        return gotaBuena;
    }

    public Gota crearGotaMala(boolean b) {
        if(b)
            gotaMala.activarEG();
        return gotaMala;
    }

    public Gota crearGotaNormal() {
        return gotaNormal;
    }

}
