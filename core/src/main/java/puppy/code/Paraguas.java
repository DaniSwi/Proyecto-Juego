package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

//Paraguas que aparezca del cielo
//Que lo recoja el tarro, evitando así gotas malas
//Durabilidad de 3 gotas malas

public class Paraguas {
    private Rectangle paraguas;
    private Texture imagenParaguas;
    private Sound sonidoGotaTapada;
    private int durabilidadParaguas = 3;
    private boolean capturado;
    private float velocidadCaida = 200;

    public Paraguas(Texture imagenParaguas, Sound sonidoGota){
        this.imagenParaguas = imagenParaguas;
        this.sonidoGotaTapada = sonidoGota;
        this.capturado = false;
    }

    public int getDurabilidadParaguas() {return durabilidadParaguas;}

    public void dañoParaguas() {
        if(capturado) {
            --durabilidadParaguas;
            sonidoGotaTapada.play();
        }
    }

    public void crear() {
        paraguas = new Rectangle();
        paraguas.x = MathUtils.random(0, 800-64);
        paraguas.y = 480;
        paraguas.width = 64;
        paraguas.height = 64;
    }

    public boolean estaCapturado() {return capturado;}

    public void caer() {
        if(!capturado) {
            paraguas.y -= velocidadCaida * Gdx.graphics.getDeltaTime();
        }
    }

    public boolean capturar(Rectangle tarro) {
        if(paraguas.overlaps(tarro)) {
            capturado = true;
            return true;
        }
        return false;
    }

    public void dibujar(SpriteBatch batch) {
        if(!capturado) batch.draw(imagenParaguas, paraguas.x, paraguas.y);
    }

}
