package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {

    final GameLluviaMenu game;
    private OrthographicCamera camera;
    private SpriteBatch batchPr;
    private BitmapFont font;
    private Tarro tarro;
    private Lluvia lluvia;

    public GameScreen(final GameLluviaMenu game) {
        this.game = game;
        this.batchPr = game.getBatch();
        this.font = game.getFont();
        // load the images for the droplet and the bucket, 64x64 pixels each
        Sound hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
        tarro = new Tarro(new Texture(Gdx.files.internal("bucket.png")),hurtSound);

        // load the drop sound effect and the rain background "music"
        Texture gota = new Texture(Gdx.files.internal("drop.png"));
        Texture gotaMalaT = new Texture(Gdx.files.internal("dropBad.png"));
        Texture gotaB = new Texture(Gdx.files.internal("gotaVerde.png"));

        //Cargar los sonidos
        Sound s1 = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        Sound s2 = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
        Sound s3 = Gdx.audio.newSound(Gdx.files.internal("sonidoVidaAumentada.mp3"));


        GotaNormal gotaNormal = new GotaNormal(gota, 30, s1);
        GotaMala gotaMala = new GotaMala(gotaMalaT, 30, s2);
        GotaBuena gotaBuena = new GotaBuena(gotaB, 30, s3);

        Music rainMusic = Gdx.audio.newMusic(Gdx.files.internal("doudou.mp3"));
        rainMusic.setVolume(0.25f);

        lluvia = new Lluvia(gotaNormal, gotaBuena ,gotaMala, rainMusic);

        // camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batchPr = new SpriteBatch();
        // creacion del tarro
        tarro.crear();

        // creacion de la lluvia
        lluvia.crear();
    }

    @Override
    public void render(float delta) {
        //limpia la pantalla con color azul obscuro.
        ScreenUtils.clear(0, 0, 0.2f, 1);
        //actualizar matrices de la cámara
        camera.update();
        //actualizar
        batchPr.setProjectionMatrix(camera.combined);
        batchPr.begin();
        batchPr.draw(lluvia.getFondo(), 0, 0, camera.viewportWidth, camera.viewportHeight);
        //dibujar textos
        int highScore = PlayerStats.getInstance().getHighScore();
        font.draw(batchPr, "Gotas totales: " + tarro.getPuntos(), 5, 475);
        font.draw(batchPr, "Vidas : " + tarro.getVidas(), 670, 475);
        font.draw(batchPr, "HighScore : " + highScore, camera.viewportWidth/2-50, 475);

        if (!tarro.estaHerido()) {
            // movimiento del tarro desde teclado
            tarro.actualizarMovimiento();
        }

        // caida de la lluvia
        if (tarro.getParaguas() != null && tarro.getParaguas().estaCapturado())
            tarro.dibujarParaguas(batchPr);

        if (!lluvia.actualizarMovimiento(tarro)) {
            PlayerStats.getInstance().setScoreActual(tarro.getPuntos());
            //Actualizar intentos
            PlayerStats.getInstance().updateIntentos();
            //actualizar HigherScore
            if (PlayerStats.getInstance().getHighScore() < tarro.getPuntos())
                PlayerStats.getInstance().updateHighScore(tarro.getPuntos());
             //   game.setHigherScore(tarro.getPuntos());
            //ir a la ventana de finde juego y destruir la actual
            game.setScreen(new GameOverScreen(game));
            dispose();
        }

        tarro.dibujar(batchPr);

        if(tarro.getFogueo() != null && tarro.tieneFogueoActivo()){
            tarro.activarFogueoL(lluvia);
            if(tarro.getFogueo().estaPorAcabarse()) {
                tarro.setUsoFogueo(false);
                lluvia.continuar();
            }
        }

        // aca implementar boost activo lololol
        if(tarro.tieneBoostActivo()) {
            Array<Boost> boostActivos = tarro.getBoostsActivos();
            float y = 400f;
            for(Boost boost : boostActivos) {
                if(boost instanceof Dash || boost instanceof Fogueo)
                    font.draw(batchPr,   "("+boost.getTeclaActivacion()+")" + boost.getNombreBoost() + ": " + boost.getTiempoRestante(), 580, y);
                else
                    font.draw(batchPr, boost.getNombreBoost() + ": " + boost.getTiempoRestante() + "s", 600, y);
                y -= 20;
            }
        }


        lluvia.actualizarDibujoLluvia(batchPr);

        batchPr.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // continuar con sonido de lluvia
        lluvia.continuar();
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {
        lluvia.pausar();
        game.setScreen(new PausaScreen(game, this));
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        tarro.destruir();
        lluvia.destruir();

    }

}
