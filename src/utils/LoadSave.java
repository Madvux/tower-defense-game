package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
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

    public static void createFile() {

        File txtFile = new File("res/testTextFile.txt");

        try {
            txtFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void createLevel(String name, int[] idArray){
        File newLevel = new File("res/"+name+".txt");
        if (newLevel.exists()) {
            System.out.println("File: "+name+" already exists");
            return;
        }
        else{
            try {
                newLevel.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            writeToFile(newLevel,idArray);
        }
    }

    private static void writeToFile(File file, int[] idArray) {

        try {
            PrintWriter printWriter = new PrintWriter(file);

            for (Integer i: idArray) {
                printWriter.println(i);
            }

            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public static void readFromFile() {

        File txtFile = new File("res/testTextFile.txt");

        try {
            Scanner scanner = new Scanner(txtFile);
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



}
