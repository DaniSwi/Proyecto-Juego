package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public abstract class Gota implements ObjetoCaible {

    private Texture imagenGota;
    private Rectangle area;
    private float velocidadCaida;
    private Sound sonidoEfecto;
    private EstrategiaEfecto estrategiaEfecto;

    public Gota(Texture imagenGota, float velocidadCaida, Sound sonidoEfecto) {
        this.imagenGota = imagenGota;
        this.velocidadCaida = velocidadCaida;
        this.area = new Rectangle();
        this.area.x = MathUtils.random(0, 800-64);
        this.area.y = 480;
        this.area.width = 58;
        this.area.height = 58;
        this.sonidoEfecto = sonidoEfecto;
    }

    public Gota(Texture imagenGota, float velocidadCaida) {
        this.imagenGota = imagenGota;
        this.velocidadCaida = velocidadCaida;
        this.area = new Rectangle();
        this.area.x = MathUtils.random(0, 800-64);
        this.area.y = 480;
        this.area.width = 58;
        this.area.height = 58;
    }

    public void caer() {
        area.y -= velocidadCaida * Gdx.graphics.getDeltaTime();
    }

    public abstract void aplicarEfecto(Tarro tarro);

    public Texture getImagenGota() {return imagenGota;}

    public void reproducirSonido(){
        sonidoEfecto.play();
    }

    public Sound getSonidoEfecto(){
        return sonidoEfecto;
    }

    public Texture getTexture() {
        return imagenGota;
    }

    public void setEstrategiaEfecto(EstrategiaEfecto estrategiaEfecto) {
        this.estrategiaEfecto = estrategiaEfecto;
    }

    public EstrategiaEfecto getEstrategiaEfecto() {
        return estrategiaEfecto;
    }
}
