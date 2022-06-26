package engine;

import engine.surface.AbstractSurface;
import engine.surface.ArraySurface;
import engine.utils.Block;

public class Planet {
    String planet_name;
    ArraySurface surface;

    public Planet(String planet_name) {
        this.planet_name = planet_name;

        this.surface = new ArraySurface("file_name.txt");

        System.out.println(this.surface.toString());
    }
}
