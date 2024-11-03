package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public interface Boost {
    public void boost();
    public Texture getImagenBoost();
    public static final float duracionBoost = 5.0f;
}
