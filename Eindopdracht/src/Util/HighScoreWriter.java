package Util;

import java.io.*;
import java.util.*;

public class HighScoreWriter {
    private List<HighScore> highScores = new ArrayList<>();
    private Comparator<HighScore> highScoreComparator = new HighScoreComparator();

    public HighScoreWriter() {
        this.highScores = getHighScores();
    }

    public boolean checkForNewHighScore(int score) {
        for (HighScore highScore : highScores) {
            if (score > highScore.getScore()) {
                return true;
            }
        }
        return false;
    }

    public void addHighScore(String name, int score) {
        HighScore newScore = new HighScore(correctedName(name), score);
        highScores.add(newScore);
        highScores.sort(highScoreComparator);

        if (highScores.size() > 5) {
            highScores.remove(5);
        }

        writeToFile();
    }

    public List<HighScore> getHighScores() {
        try {
            FileInputStream fileInputStream = new FileInputStream("Eindopdracht/src/Util/HighScores.obj");
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
            FileOutputStream fileOutputStream = new FileOutputStream("Eindopdracht/src/Util/HighScores.obj");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            for (HighScore highScore : highScores) {
                objectOutputStream.writeObject(highScore);
            }
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (Exception e) {
            System.out.println("Bestand niet gevonden :(");
            e.printStackTrace();
        }
    }
    //methode voor evenredige spacing op highscore lijst
    private String correctedName(String name) {
        if (name.length() == 4) {
            return name;
        }

        int correctionLength = 4 - name.length();

        for (int i = 0; i < correctionLength; i++) {
            name += " ";
        }
        return name;
    }

    public void printScores() {
        for (HighScore highScore : highScores) {
            System.out.println(highScore);
        }
    }


    public static void main(String[] args) {//fixme weghalen
        HighScoreWriter f = new HighScoreWriter();
//        f.addHighScore("bob", 11);
//        f.addHighScore("bob", 9);
//        f.addHighScore("bob", 11);
//        f.addHighScore("bob", 23);
//        f.addHighScore("bob", 411);
//        f.addHighScore("bob", 100);
        f.addHighScore("-", 0);
        f.addHighScore("-", 0);
        f.addHighScore("-", 0);
        f.addHighScore("-", 0);
        f.addHighScore("-", 0);
        for (HighScore highScore : f.getHighScores()) {
            System.out.println(highScore.toString());
        }
    }

    public class HighScoreComparator implements Comparator<HighScore> {
        @Override
        public int compare(HighScore o1, HighScore o2) {
            return o2.getScore() - o1.getScore();
        }
    }
}
