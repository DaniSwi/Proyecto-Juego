package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Tarro {
    private Rectangle bucket;
    private Texture bucketImage;
    private Sound sonidoHerido;
    private int vidas = 3;
    private int puntos = 0;
    private int velx = 400;
    private boolean herido = false;
    private int tiempoHeridoMax = 80;
    private int tiempoHerido;
    private Paraguas paraguas;
    private PointsMultiplier pointsMultiplier;
    private InvencibilidadPower invencibilidadPower;
    private Dash dash;
    private Fogueo fogueo;

    public Tarro(Texture tex, Sound ss) {
        bucketImage = tex;
        sonidoHerido = ss;
    }

    public int getVidas() {
        return vidas;
    }

    public int getPuntos() {
        return puntos;
    }

    public Rectangle getArea() {
        return bucket;
    }

    public void sumarPuntos(int pp) {
        if(pointsMultiplier != null && pointsMultiplier.estaActivo())
            puntos = puntos + (int)(pp * pointsMultiplier.getFactorMultiplicador());
        else {
            puntos += pp;
        }
    }


    public void crear() {
        bucket = new Rectangle();
        bucket.x = 800 / 2 - 64 / 2;
        bucket.y = 20;
        bucket.width = 64;
        bucket.height = 64;
    }

    public void dañar() {
        if(invencibilidadPower == null || !invencibilidadPower.estaActivo()) {
            if (paraguas != null && paraguas.estaCapturado()) {
                paraguas.dañoParaguas();
                if (paraguas.getDurabilidadParaguas() < 1) {
                    paraguas.destruir();
                    paraguas = null;
                }
            } else {
                vidas--;
                herido = true;
                tiempoHerido = tiempoHeridoMax;
                sonidoHerido.play();
            }
        }
    }

    public void dibujar(SpriteBatch batch) {
        if (!herido)
            batch.draw(bucketImage, bucket.x, bucket.y);
        else {
            batch.draw(bucketImage, bucket.x, bucket.y + MathUtils.random(-5, 5));
            tiempoHerido--;
            if (tiempoHerido <= 0) herido = false;
        }
        if(pointsMultiplier != null && pointsMultiplier.estaActivo())
            pointsMultiplier.actualizar();
        if(invencibilidadPower != null && invencibilidadPower.estaActivo())
            invencibilidadPower.actualizar();
    }

    public void actualizarMovimiento() {
        // movimiento desde mouse/touch
		   /*if(Gdx.input.isTouched()) {
			      Vector3 touchPos = new Vector3();
			      touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			      camera.unproject(touchPos);
			      bucket.x = touchPos.x - 64 / 2;
			}*/
        //movimiento desde teclado
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= velx * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += velx * Gdx.graphics.getDeltaTime();
        // que no se salga de los bordes izq y der
        if (bucket.x < 0) bucket.x = 0;
        if (bucket.x > 800 - 64) bucket.x = 800 - 64;
    }


    public void destruir() {
        bucketImage.dispose();
    }

    public boolean estaHerido() {
        return herido;
    }

    public void setParaguas(Paraguas paraguas) {
        if(this.paraguas == null) {
            this.paraguas = paraguas;
            this.paraguas.setCapturado();
        }
    }

    public void aumentarVida(){
        ++vidas;
    }

    public Paraguas getParaguas() {
        return paraguas;
    }

    public void dibujarParaguas(SpriteBatch batch) {
        batch.draw(paraguas.getImagenParaguas(), bucket.x, bucket.y + 32);
    }

    public void activarMultiplicador(PointsMultiplier multiplicador) {
        this.pointsMultiplier = multiplicador;
        pointsMultiplier.boost();
        pointsMultiplier.activarSonido();
    }

    public Boolean tieneBoostActivo() {
        if((pointsMultiplier != null && pointsMultiplier.estaActivo()) || (invencibilidadPower != null && invencibilidadPower.estaActivo()))
            return true;
        return false;
    }

    public void activarInvencibilidad(InvencibilidadPower invencibilidad) {
        this.invencibilidadPower = invencibilidad;
        invencibilidad.boost();
        invencibilidad.activarSonido();
    }

    public Array getBoostsActivos() {
        Array ar = new Array();
        if(pointsMultiplier != null && pointsMultiplier.estaActivo()) {
            ar.add(pointsMultiplier);
        } if(invencibilidadPower != null && invencibilidadPower.estaActivo()){
            ar.add(invencibilidadPower);
        } if(fogueo != null && fogueo.getTiempoRestante() > 0) {
            ar.add(fogueo);
        }
        return ar;
    }

}
