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
    private Array<Boost> boosts;


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
        boosts = new Array<Boost>();
        crearGotaDeLluvia();
        crearParaguas();
        //crearBoosts();
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
            GotaMala gota = new GotaMala(gotaMala.getImagenGota(), 30);
            rainDropsType.add(gota);
        } else if (random >= 31 && random <= 99) {
            GotaNormal gota = new GotaNormal(gotaNormal.getImagenGota(), 30);
            rainDropsType.add(gota);
        } else {
            GotaBuena gota = new GotaBuena(gotaBuena.getImagenGota(), 30);
            rainDropsType.add(gota);
        }
        lastDropTime = TimeUtils.nanoTime();
    }

    /* private void crearBoosts() {
        Rectangle boostCaer = new Rectangle();
        boostCaer.x = MathUtils.random(0, 800 - 64);
        boostCaer.y = 480;
        boostCaer.width = 64;
        boostCaer.height = 64;
        rainDropsPos.add(boostCaer);
        int random = MathUtils.random(0, 100);
        if(random >= 0 && random <= 2) {
            PointsMultiplier boost = new PointsMultiplier(new Texture(Gdx.files.internal("spriteBoostMultiplicador.png")));
            boosts.add(boost);
        }
    } */

    public Boolean actualizarMovimiento(Tarro tarro) {
        // generar gotas de lluvia
        if (TimeUtils.nanoTime() - lastDropTime > 100000000) {
            crearGotaDeLluvia();
            crearParaguas();
           // crearBoosts();
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
                    if (rainDropsType.get(i) instanceof Gota) {  //El objeto es una gota
                        if (rainDropsType.get(i) instanceof GotaMala) { // gota dañina
                            gotaMala.aplicarEfecto(tarro);
                            if (tarro.getVidas() <= 0)
                                return false;
                            rainDropsPos.removeIndex(i);
                            rainDropsType.removeIndex(i);
                        } else if (rainDropsType.get(i) instanceof GotaBuena) {
                            gotaBuena.aplicarEfecto(tarro);
                            rainDropsPos.removeIndex(i);
                            rainDropsType.removeIndex(i);
                        } else { // gota a recolectar
                            gotaNormal.aplicarEfecto(tarro);
                            rainDropsPos.removeIndex(i);
                            rainDropsType.removeIndex(i);
                        }
                    } else { //El objeto es un paraguas
                        tarro.setParaguas((Paraguas) rainDropsType.get(i));
                        rainDropsPos.removeIndex(i);
                        rainDropsType.removeIndex(i);
                    }
                }
            }
        }
        return true;
    }

    public void actualizarDibujoLluvia(SpriteBatch batch) {
        for (int i = 0; i < rainDropsPos.size; i++) {
            Rectangle objetoLLuvia = rainDropsPos.get(i); //Puede ser, gota normal, gota mala o un paraguas! careful
            if (rainDropsType.get(i) instanceof Gota) { // Gota dañina y gota normal (y gota buena)
                Gota gota = (Gota) rainDropsType.get(i);
                batch.draw(gota.getImagenGota(), objetoLLuvia.x, objetoLLuvia.y);
            } else{ //paraguas
                Paraguas paraguas1 = (Paraguas) rainDropsType.get(i);
                batch.draw(paraguas1.getImagenParaguas(), objetoLLuvia.x, objetoLLuvia.y);
            }
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
