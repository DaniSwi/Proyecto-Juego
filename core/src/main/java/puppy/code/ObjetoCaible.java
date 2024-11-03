package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public interface ObjetoCaible {
    public float velocidadCaida = 30;
    public void caer();
    public void aplicarEfecto(Tarro tarro);
    public Texture getTexture();
}
