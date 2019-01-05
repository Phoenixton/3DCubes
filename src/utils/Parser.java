package utils;

import entities.worldobjects.WorldObject;
import entities.worldobjects.cube.Cube;
import main.StaticValues;
import maths.Matrix4d;
import maths.Vector4d;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by perri on 04/01/2019.
 */
public class Parser {


    public static Cube[][][] parseWorld(String path){

        Cube[][][] cubes = null;
        int longueur, largeur, profondeur;

        try {

            Scanner sc = new Scanner(new File(path));

            int format = (sc.next().equals("1")?1:2);
            int worldSize = Integer.parseInt(sc.next());
            System.out.println("worldSize " + worldSize);
            cubes = new Cube[worldSize][worldSize][worldSize];
            if(format == 1) {

                for(int i = 0; i < worldSize; i++) {

                    System.out.println("i " + i);
                    for(int j = 0; j < worldSize; j++) {

                        System.out.println("j " + j);
                        for(int k = 0; k < worldSize; k++) {
                            System.out.println("k " + k);
                            if(sc.hasNext()) {
                                String word = sc.next();
                                System.out.println("Word " + word);
                                if(!word.equals("0")) {
                                    cubes[i][j][k] = new Cube(StaticValues.DEFAULT_CUBE_SIZE);
                                    cubes[i][j][k].addTransform(Matrix4d.createTranslation(
                                            new Vector4d(
                                                    i * cubes[i][j][k].getSize(),
                                                    j * cubes[i][j][k].getSize(),
                                                    k * cubes[i][j][k].getSize() - 150)));
                                } else {
                                    cubes[i][j][k] = null;

                                }

                            }
                        }
                    }
                }
            } else {
                //Format numero2
                for(int i = 0; i < worldSize; i++) {

                    for(int j = 0; j < worldSize; j++) {

                        for(int k = 0; k < worldSize; k++) {
                            if(sc.hasNext()) {
                                String couleur1 = sc.next();
                                String couleur2 = sc.next();
                                String couleur3 = sc.next();
                                System.out.println("Couleurs " + couleur1 + "  " + couleur2 + "  "  + couleur3);
                                if(couleur1.equals("-1") && couleur2.equals("-1") && couleur3.equals("-1")) {
                                    cubes[i][j][k] = null;
                                } else {
                                    cubes[i][j][k] = new Cube(
                                            StaticValues.DEFAULT_CUBE_SIZE,
                                            new Color(Integer.parseInt(couleur1),
                                                    Integer.parseInt(couleur2),
                                                    Integer.parseInt(couleur3)));
                                    cubes[i][j][k].addTransform(Matrix4d.createTranslation(
                                            new Vector4d(
                                                    i * cubes[i][j][k].getSize(),
                                                    j * cubes[i][j][k].getSize(),
                                                    k * cubes[i][j][k].getSize() - 150)));

                                }

                            }
                        }
                    }
                }
            }

            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return cubes;
    }
}
