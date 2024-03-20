package Util;

import java.io.*;
import java.util.*;

public class HighScoreWriter implements Serializable {
    private List<HighScore> highScores = new ArrayList<>();
    private Comparator<HighScore> highScoreComparator = new HighScoreComparator();
    private Scanner scanner = new Scanner("Eindopdracht/src/Util/HighScores.txt");
    //todo filewriter fixen, wordt soms dubbel er in gezet en uitlezen gaat niet helemaal goed
    public HighScoreWriter() {
        this.highScores = getHighScores();
    }

    public void addHighScore(String name, int score) {
        HighScore newScore = new HighScore(name, score);
        highScores.add(newScore);

        highScores.sort(highScoreComparator);

        if (highScores.size() > 5) {
            highScores.remove(5);
        }

        writeToFile();
    }

    public List<HighScore> getHighScores() {
        try {
            FileInputStream fileInputStream = new FileInputStream("Eindopdracht/src/Util/HighScores.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            highScores.clear();
            for (int i = 0; i < 5; i++) {
                highScores.add((HighScore) objectInputStream.readObject());
            }

            fileInputStream.close();
        } catch (Exception e) {
            System.out.println("Bestand niet gevonden :(");
            e.printStackTrace();
        }

        return highScores;
    }

    private void writeToFile() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("Eindopdracht/src/Util/HighScores.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            for (HighScore highScore : highScores) {
                objectOutputStream.writeObject(highScore);
            }

            objectOutputStream.close();
        } catch (Exception e) {
            System.out.println("Bestand niet gevonden :(");
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        HighScoreWriter f = new HighScoreWriter();
        f.addHighScore("bob", 11);
        f.addHighScore("bob", 9);
        f.addHighScore("bob", 11);
        f.addHighScore("bob", 23);
        f.addHighScore("bob", 411);
        f.addHighScore("bob", 100);
        for (HighScore highScore : f.getHighScores()) {
            System.out.println(highScore.getName() + " : " + highScore.getScore());
        }
    }

    public class HighScoreComparator implements Comparator<HighScore> {
        @Override
        public int compare(HighScore o1, HighScore o2) {
            return o2.getScore() - o1.getScore();
        }
    }
}
