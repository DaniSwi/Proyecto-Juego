package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Lluvia {
    private Array<Rectangle> rainDropsPos;
    private Array<ObjetoCaible> rainDropsType;
    private long lastDropTime;
    private final GotaNormal gotaNormal;
    private final GotaBuena gotaBuena;
    private final GotaMala gotaMala;
    private final Music rainMusic;
    private final Texture fondo;
    private final GotaFactory gotafactory;
    private Fogueo fogueo;
    private float tEspera;
    private boolean cond;
    private boolean parar;

    public Lluvia(GotaNormal gotaNormal, GotaBuena gotaBuena, GotaMala gotaMala, Music mm) {
        rainMusic = mm;
        this.gotaNormal = gotaNormal;
        this.gotaBuena = gotaBuena;
        this.gotaMala = gotaMala;
        this.fondo = new Texture(Gdx.files.internal("Fondo.png"));
        this.gotafactory = new GotaFactoryGame();
        this.parar = false;
    }

    public void crear() {
        rainDropsType = new Array<ObjetoCaible>();
        rainDropsPos = new Array<Rectangle>();
        crearGotasDeLluvia();
        crearParaguas();
        crearBoosts();
        this.tEspera = 5f;
        // start the playback of the background music immediately
        rainMusic.setLooping(true);
        rainMusic.play();
    }

    private void crearParaguas() {
        if (MathUtils.random(0, 200) < 1) {
            Texture paraguasT = new Texture(Gdx.files.internal("paraguasSprite.png"));
            Paraguas paraguas = new Paraguas(paraguasT, 20);
            rainDropsPos.add(paraguas.getArea());
            rainDropsType.add(paraguas);
        }
    }

    private void crearGotasDeLluvia() {
        Rectangle objetoLLuvia = new Rectangle();
        objetoLLuvia.x = MathUtils.random(0, 800 - 64);
        objetoLLuvia.y = 480;
        objetoLLuvia.width = 64;
        objetoLLuvia.height = 64;
        rainDropsPos.add(objetoLLuvia);
        int random = MathUtils.random(0, 100);
        if (random >= 0 && random <= 30) {
            Gota gota = gotafactory.crearGotaMala();
            gota.setEstrategiaEfecto(new EfectoGotaMala((GotaMala) gota));
            rainDropsType.add(gota);
        } else if (random >= 31 && random <= 99) {
            Gota gota = gotafactory.crearGotaNormal();
            gota.setEstrategiaEfecto(new EfectoGotaNormal((GotaNormal) gota));
            rainDropsType.add(gota);
        } else {
            Gota gota = gotafactory.crearGotaBuena();
            gota.setEstrategiaEfecto(new EfectoGotaBuena((GotaBuena) gota));
            rainDropsType.add(gota);
        }
        lastDropTime = TimeUtils.nanoTime();
    }

    private void crearBoosts() {
        Rectangle boostCaer = new Rectangle();
        boostCaer.x = MathUtils.random(0, 800 - 64);
        boostCaer.y = 480;
        boostCaer.width = 64;
        boostCaer.height = 64;
        int random = MathUtils.random(0, 1000);
        if (random >= 0 && random <= 20) {
            PointsMultiplier boost = new PointsMultiplier(new Texture(Gdx.files.internal("spriteBoostMultiplicador.png")));
            rainDropsType.add(boost);
            rainDropsPos.add(boostCaer);
        } else if(random > 20 && random <= 25) {
            InvencibilidadPower boost = new InvencibilidadPower(new Texture(Gdx.files.internal("invencibilidad.png")));
            rainDropsType.add(boost);
            rainDropsPos.add(boostCaer);
        } else if(random > 25 && random <= 30) {
            Dash boost = new Dash(new Texture(Gdx.files.internal("dash.png")));
            rainDropsType.add(boost);
            rainDropsPos.add(boostCaer);
        } else if(random > 30 && random <= 35) {
            Fogueo fogueo = new Fogueo(new Texture(Gdx.files.internal("fogueo.png")));
            rainDropsType.add(fogueo);
            rainDropsPos.add(boostCaer);
        }
    }

    public void pararLluvia() {
        parar = true;
        rainDropsPos.clear();
        rainDropsType.clear();
    }

    public Boolean actualizarMovimiento(Tarro tarro) {
        // generar gotas de lluvia
        if (TimeUtils.nanoTime() - lastDropTime > 100000000 && !tarro.congelado()) {
            crearGotasDeLluvia();
            crearParaguas();
            crearBoosts();
        } cond = (fogueo != null && fogueo.estaActivoL());
        if (!tarro.estaHerido() && !cond && !parar) {
            // revisar si las gotas cayeron al suelo o chocaron con el tarro
            for (int i = 0; i < rainDropsPos.size; ++i) {
                Rectangle objetoLluvia = rainDropsPos.get(i);
                objetoLluvia.y -= 300 * Gdx.graphics.getDeltaTime();
                //cae al suelo y se elimina
                if (objetoLluvia.y + 64 < 0) {
                    rainDropsPos.removeIndex(i);
                    rainDropsType.removeIndex(i);
                }
                if (objetoLluvia.overlaps(tarro.getArea())) { //El objeto choca con el tarro
                    rainDropsType.get(i).aplicarEfecto(tarro);
                    if(tarro.getVidas() <= 0)
                        return false;
                    rainDropsType.removeIndex(i);
                    rainDropsPos.removeIndex(i);
                }
            }
        } if(cond) {
            tEspera -= Gdx.graphics.getDeltaTime();
            tarro.getFogueo().setUso();
        } if(cond && tarro.getFogueo().estaPorAcabarse()) {
            tEspera = 5f;
            rainDropsPos.clear();
            rainDropsType.clear();
            fogueo = null;
        }
        return true;
    }

    public void actualizarDibujoLluvia(SpriteBatch batch) {
        for (int i = 0; i < rainDropsPos.size; i++) {
            Rectangle objetoLLuvia = rainDropsPos.get(i);
            batch.draw(rainDropsType.get(i).getTexture(), objetoLLuvia.x, objetoLLuvia.y);
        }
    }

    public void destruir() {
        rainMusic.dispose();
        gotaMala.getImagenGota().dispose();
        gotaNormal.getImagenGota().dispose();
        gotaBuena.getImagenGota().dispose();
    }

    public void setFogueo(Tarro tarro) {
        if(tarro.usoFogueo()) {
            pausar();
        }
        fogueo = tarro.getFogueo();
        tEspera = 5f;
    }

    public Texture getFondo() {
        if(fogueo != null && fogueo.estaActivoL())
            return new Texture(Gdx.files.internal("hadoNo.png"));
        return fondo;
    }

    public void pausar() {
        rainMusic.stop();
    }

    public void continuar() {
        rainMusic.play();
    }

}
