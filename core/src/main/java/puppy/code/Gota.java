package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public abstract class Gota implements ObjetoCaible {
    private Texture imagenGota;
    private Rectangle area;
    private float velocidadCaida;

    public Gota(Texture imagenGota, float velocidadCaida) {
        this.imagenGota = imagenGota;
        this.velocidadCaida = velocidadCaida;
        this.area = new Rectangle();
        this.area.x = MathUtils.random(0, 800-64);
        this.area.y = 480;
        this.area.width = 64;
        this.area.height = 64;
    }

    public void caer(float deltaTime) {
        area.y = velocidadCaida * deltaTime;
    }

    public void dibujar(SpriteBatch batch) {
        batch.draw(imagenGota, area.x, area.y);
    }

    public Rectangle getArea() {return area;}

    public abstract void aplicarEfecto(Tarro tarro);



}
