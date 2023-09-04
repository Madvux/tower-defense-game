package utils;

import objects.PathPoint;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class LoadSave {
    public static BufferedImage getSpriteAtlas() {
        BufferedImage image = null;
        InputStream inputStream = LoadSave.class.getClassLoader().getResourceAsStream("spriteatlas.png");

        try {
            image = ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }


    public static void createLevel(String name, int[] idArray) {
        File newLevel = new File("res/" + name + ".txt");
        if (newLevel.exists()) {
            System.out.println("File: " + name + " already exists");
            return;
        } else {
            try {
                newLevel.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            writeToFile(newLevel, idArray, new PathPoint(0,0), new PathPoint(0,0) );
        }
    }

    private static void writeToFile(File file, int[] idArray, PathPoint start, PathPoint end) {

        try {
            PrintWriter printWriter = new PrintWriter(file);

            for (Integer i : idArray) {
                printWriter.println(i);
            }
            printWriter.println(start.getX());
            printWriter.println(start.getY());
            printWriter.println(end.getX());
            printWriter.println(end.getY());

            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    private static ArrayList<Integer> readFromFile(File file) {

        ArrayList<Integer> list = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                list.add(Integer.parseInt(scanner.nextLine()));
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void saveLevel(String name, int[][] idArray, PathPoint start,PathPoint end) {

        File levelFile = new File("res/" + name + ".txt");
        if (levelFile.exists()) {
            writeToFile(levelFile,twoDTo1DintArray(idArray),start,end);
        } else {
            System.out.println("File: " + name + " does not exist");
            return;
        }
    }

    public static int[][] arrayListTo2DInt(ArrayList<Integer> list, int ySize, int xSize) {
        int[][] newArray = new int[ySize][xSize];

        for (int y = 0; y < newArray.length; y++) {
            for (int x = 0; x < newArray[y].length; x++) {
                int index = y * ySize + x;
                newArray[y][x] = list.get(index);
            }
        }

        return newArray;
    }

    public static int[] twoDTo1DintArray(int[][] twoDArray) {
        int[] oneDArray = new int[twoDArray.length * twoDArray[0].length];
        for (int y = 0; y < twoDArray.length; y++) {
            for (int x = 0; x < twoDArray[y].length; x++) {
                int index = y * twoDArray.length + x;
                oneDArray[index] = twoDArray[y][x];
            }
        }
        return oneDArray;
    }

    public static int[][] getLevelData(String name) {
        File lvlFile = new File("res/" + name + ".txt");
        if (lvlFile.exists()) {
            ArrayList<Integer> list = readFromFile(lvlFile);
            return arrayListTo2DInt(list, 20, 20);

        } else {
            System.out.println("File: " + name + " does not exist");
            return null;
        }

    }
    public static ArrayList<PathPoint> getLevelPathPoint(String name) {
        File lvlFile = new File("res/" + name + ".txt");
        if (lvlFile.exists()) {
            ArrayList<Integer> list = readFromFile(lvlFile);
            ArrayList<PathPoint> points = new ArrayList<>();
            points.add(new PathPoint(list.get(400),list.get(401)));
            points.add(new PathPoint(list.get(402),list.get(403)));

            return points;
        } else {
            System.out.println("File: " + name + " does not exist");
            return null;
        }

    }


}
