package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

//Paraguas que aparezca del cielo
//Que lo recoja el tarro, evitando así gotas malas
//Durabilidad de 3 gotas malas

public class Paraguas implements ObjetoCaible {
    private Rectangle area;
    private Texture imagenParaguas;
    private int durabilidadParaguas = 3;
    private boolean capturado;
    private float velocidadCaida;

    public Paraguas(Texture imagenParaguas, float velocidadCaida) {
        this.imagenParaguas = imagenParaguas;
        this.velocidadCaida = velocidadCaida;
        this.capturado = false;
        this.area = new Rectangle();
        this.area.x = MathUtils.random(0, 69);
        this.area.y = 480;
        this.area.width = 69;
        this.area.height = 64;
    }

    public int getDurabilidadParaguas() {return durabilidadParaguas;}

    public void dañoParaguas() {
        if(capturado) {
            --durabilidadParaguas;
            Sound s = Gdx.audio.newSound(Gdx.files.internal("umbrellaSfx.mp3"));
            s.play();
        }
    }

    public boolean estaCapturado() {return capturado;}

    public void caer() {
        if(!capturado) {
            area.y -= velocidadCaida * Gdx.graphics.getDeltaTime();
        }
    }

    public boolean capturar(Rectangle tarro) {
        if(area.overlaps(tarro)) {
            capturado = true;
            return true;
        }
        return false;
    }

    public void dibujar(SpriteBatch batch) {
        if(capturado) batch.draw(imagenParaguas, area.x, area.y);
    }

    public void actualizarPosicion(float x, float y) {
        if(capturado) {
            area.x = x;
            area.y = y;
        }
    }

    public Texture getImagenParaguas() {return imagenParaguas;}

    public Rectangle getArea() {return area;}

    public void setCapturado() {
        this.capturado = true;
    }
}
