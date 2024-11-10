package puppy.code;

public class PlayerStats { //Patron singleton
    private static PlayerStats instance;
    private int highScore;
    private int intentos;
    private int scoreActual;

    private PlayerStats() {
        highScore = 0;
        intentos = 0;
        scoreActual = 0;
    }

    public static PlayerStats getInstance() {
        if (instance == null)
            instance = new PlayerStats();
        return instance;
    }

    public void setScoreActual(int scoreActual) {
        this.scoreActual = scoreActual;
    }

    public int getScoreActual() {
        return scoreActual;
    }

    public void updateHighScore(int score) {
        if(score > highScore)
            highScore = score;
    }

    public int getHighScore() {
        return highScore;
    }

    public void updateIntentos() {
        ++intentos;
    }

    public int getIntentos() {
        return intentos;
    }

}
