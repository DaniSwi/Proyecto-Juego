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
    private int durabilidadParaguas;
    private boolean capturado;
    private float velocidadCaida;

    public Paraguas(Texture imagenParaguas, float velocidadCaida) {
        this.imagenParaguas = imagenParaguas;
        this.velocidadCaida = velocidadCaida;
        this.capturado = false;
        this.area = new Rectangle();
        this.area.x = MathUtils.random(0, 800-69);
        this.area.y = 480;
        this.area.width = 69;
        this.area.height = 64;
        this.durabilidadParaguas = 3;
    }

    public int getDurabilidadParaguas() {return durabilidadParaguas;}

    public void dañoParaguas() {
        --durabilidadParaguas;
    }

    public boolean estaCapturado() {return capturado;}

    public void caer() {
        if(!capturado) {
            area.y -= velocidadCaida * Gdx.graphics.getDeltaTime();
        }
    }
    public Texture getImagenParaguas() {return imagenParaguas;}

    public Rectangle getArea() {return area;}

    public void setCapturado() {
        this.capturado = true;
    }
}
