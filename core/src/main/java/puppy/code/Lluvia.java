package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
    private GotaNormal gotaNormal;
    private GotaBuena gotaBuena;
    private GotaMala gotaMala;
    private Music rainMusic;
    private Texture fondo;


    public Lluvia(GotaNormal gotaNormal, GotaBuena gotaBuena, GotaMala gotaMala, Music mm) {
        rainMusic = mm;
        this.gotaNormal = gotaNormal;
        this.gotaBuena = gotaBuena;
        this.gotaMala = gotaMala;
        this.fondo = new Texture(Gdx.files.internal("Fondo.png"));
    }

    public void crear() {
        rainDropsType = new Array<ObjetoCaible>();
        //rainDropsType = new Array<Integer>();
        rainDropsPos = new Array<Rectangle>();
        crearGotaDeLluvia();
        crearParaguas();
        crearBoosts();
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

    private void crearGotaDeLluvia() {
        Rectangle objetoLLuvia = new Rectangle();
        objetoLLuvia.x = MathUtils.random(0, 800 - 64);
        objetoLLuvia.y = 480;
        objetoLLuvia.width = 64;
        objetoLLuvia.height = 64;
        rainDropsPos.add(objetoLLuvia);
        int random = MathUtils.random(0, 100);
        if (random >= 0 && random <= 30) {
            GotaMala gota = new GotaMala(gotaMala.getImagenGota(), 30, gotaMala.getSonidoEfecto());
            rainDropsType.add(gota);
        } else if (random >= 31 && random <= 99) {
            GotaNormal gota = new GotaNormal(gotaNormal.getImagenGota(), 30, gotaNormal.getSonidoEfecto());
            rainDropsType.add(gota);
        } else {
            GotaBuena gota = new GotaBuena(gotaBuena.getImagenGota(), 30, gotaBuena.getSonidoEfecto());
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
        }
    }

    public Boolean actualizarMovimiento(Tarro tarro) {
        // generar gotas de lluvia
        if (TimeUtils.nanoTime() - lastDropTime > 100000000) {
            crearGotaDeLluvia();
            crearParaguas();
            crearBoosts();
        }
        if (!tarro.estaHerido()) {
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

    public Texture getFondo() {
        return fondo;
    }

    public void pausar() {
        rainMusic.stop();
    }

    public void continuar() {
        rainMusic.play();
    }

}
