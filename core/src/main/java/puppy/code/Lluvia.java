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
	private Array<ObjetoCaible> rainDropsPos;
	private Array<Gota> rainDropsType;
    private long lastDropTime;
    private GotaNormal gotaBuena;
    private GotaMala gotaMala;
    private Sound dropSound;
    private Music rainMusic;

    //private Array<ObjetoCaible> objetosQueCaen;

	public Lluvia(GotaNormal gotaBuena, GotaMala gotaMala, Sound ss, Music mm) {
		rainMusic = mm;
		dropSound = ss;
		this.gotaBuena = gotaBuena;
		this.gotaMala = gotaMala;
	}

	public void crear() {
		rainDropsType = new Array<Gota>();
		//rainDropsType = new Array<Integer>();
        rainDropsPos = new Array<ObjetoCaible>();
		crearGotaDeLluvia();
	      // start the playback of the background music immediately
	      rainMusic.setLooping(true);
	      rainMusic.play();
	}

	private void crearGotaDeLluvia() {
        if(MathUtils.random(1,10)<3){
            GotaMala gota = new GotaMala(gotaMala.getImagenGota(), 10);
            rainDropsPos.add(gota);
            rainDropsType.add(gota);
        } else {
            GotaNormal gota = new GotaNormal(gotaBuena.getImagenGota(), 10);
            rainDropsPos.add(gota);
            rainDropsType.add(gota);
        }
        lastDropTime = TimeUtils.nanoTime();
    }

   public void actualizarMovimiento(Tarro tarro) {
	   // generar gotas de lluvia
	   if(TimeUtils.nanoTime() - lastDropTime > 100000000) crearGotaDeLluvia();


	   // revisar si las gotas cayeron al suelo o chocaron con el tarro
	   for (int i=0; i < rainDropsPos.size; ++i) {
		  Gota gota = rainDropsType.get(i);
	      gota.getArea().y -= 300 * Gdx.graphics.getDeltaTime();
	      //cae al suelo y se elimina
	      if(gota.getArea().y + 64 < 0) {
	    	  rainDropsPos.removeIndex(i);
	    	  rainDropsType.removeIndex(i);
	      }
	      if(gota.getArea().overlaps(tarro.getArea())) { //la gota choca con el tarro
	    	if(rainDropsType.get(i) instanceof GotaMala) { // gota dañina
	    	  tarro.dañar();
	    	  rainDropsPos.removeIndex(i);
	          rainDropsType.removeIndex(i);
	      	} else { // gota a recolectar
	    	  tarro.sumarPuntos(10);
	          dropSound.play();
	          rainDropsPos.removeIndex(i);
	          rainDropsType.removeIndex(i);
	      	}
	      }
	   }
   }

   public void actualizarDibujoLluvia(SpriteBatch batch) {
	  for (int i=0; i < rainDropsPos.size; i++ ) {
		  ObjetoCaible objeto = rainDropsPos.get(i); //Puede ser, gota buena, gota mala o un paraguas! careful
		  if(rainDropsType.get(i) instanceof GotaMala){ // gota dañina
              GotaMala gota = (GotaMala)rainDropsType.get(i);
              batch.draw(gotaMala.getImagenGota(), gota.getArea().x, gota.getArea().y);
          } else {
              GotaNormal gota = (GotaNormal)rainDropsType.get(i);
              batch.draw(gotaBuena.getImagenGota(), gota.getArea().x, gota.getArea().y);
          }
	   }
   }
   public void destruir() {
	      dropSound.dispose();
          rainMusic.dispose();
   }

}
