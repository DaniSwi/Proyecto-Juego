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
    private boolean usoFogueo;
    private boolean aux;
    private String input = "";
    private boolean lol = false;

    public Tarro(Texture tex, Sound ss) {
        bucketImage = tex;
        sonidoHerido = ss;
        this.usoFogueo = false;
        this.aux = false;
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
        if(fogueo != null && fogueo.estaActivo())
            fogueo.actualizar();
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
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            bucket.x -= velx * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            bucket.x += velx * Gdx.graphics.getDeltaTime();

        if(Gdx.input.isKeyJustPressed(Input.Keys.Q) && !herido && dash != null && dash.estaActivo()) {
            bucket.x -= dash.getVelocidadDash() * 0.3f;
            dash.quitarUso();
            if(dash.getTiempoRestante() <= 0)
                dash = null;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.E) && dash != null && dash.estaActivo()) {
            bucket.x += dash.getVelocidadDash() * 0.3f;
            dash.quitarUso();
            if(dash.getTiempoRestante() <= 0)
                dash = null;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            if(input.isEmpty())
                input += "D";
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            if(input.equals("D"))
                input += "A";
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.N)) {
            if(input.equals("DA"))
                input += "N";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            if(input.equals("DAN"))
                input += "I";
        }

        if(input.equals("DANI")){
            aux = true;
            if(!lol) {
                Sound s = Gdx.audio.newSound(Gdx.files.internal("kyokaSuigetsu.mp3"));
                s.play();
                lol = true;
            }
        }

        if(fogueo != null && fogueo.estaActivo() && Gdx.input.isKeyJustPressed(Input.Keys.F) && !fogueo.estaActivoL()) {
            this.usoFogueo = true;
            fogueo.boost(this);
            fogueo.setUso();
            fogueo.reproducirSonido();
        }
        // que no se salga de los bordes izq y der
        if (bucket.x < 0) bucket.x = 0;
        if (bucket.x > 800 - 64) bucket.x = 800 - 64;
    }

    public boolean usoFogueo() {
        return usoFogueo;
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
        pointsMultiplier.boost(this);
        pointsMultiplier.activarSonido();
    }

    public Boolean tieneBoostActivo() {
        if((pointsMultiplier != null && pointsMultiplier.estaActivo()) || (invencibilidadPower != null && invencibilidadPower.estaActivo()) || (dash != null && dash.estaActivo()) || (fogueo != null && fogueo.estaActivo()))
            return true;
        return false;
    }

    public void activarInvencibilidad(InvencibilidadPower invencibilidad) {
        if(invencibilidadPower != null && invencibilidadPower.estaActivo()) {
            this.invencibilidadPower = invencibilidad;
            invencibilidad.boost(this);
        } else {
            this.invencibilidadPower = invencibilidad;
            invencibilidad.boost(this);
            invencibilidad.activarSonido();
        }
    }

    public void activarFogueo(Fogueo fogueo) {
        this.fogueo = fogueo;
    }

    public void activarFogueoL(Lluvia lluvia) {
        lluvia.setFogueo(this);
    }

    public Array getBoostsActivos() {
        Array ar = new Array();
        if(pointsMultiplier != null && pointsMultiplier.estaActivo()) {
            ar.add(pointsMultiplier);
        } if(invencibilidadPower != null && invencibilidadPower.estaActivo()){
            ar.add(invencibilidadPower);
        } if(fogueo != null && fogueo.getTiempoRestante() > 0) {
            ar.add(fogueo);
        } if(dash != null && dash.getTiempoRestante() > 0) {
            ar.add(dash);
        }
        return ar;
    }

    public void activarDash(Dash dash) {
        this.dash = dash;
    }

    public boolean tieneFogueoActivo() {
        return fogueo != null && fogueo.estaActivo();
    }

    public Fogueo getFogueo() {
        return fogueo;
    }

    public void setUsoFogueo(boolean b) {
        this.usoFogueo = b;
    }

    public boolean congelado() {
        return herido || usoFogueo;
    }

    public boolean getAux() {
        return aux;
    }

}
