package djankcraft.minecraft54.generator;

import djankcraft.minecraft54.Chunk;

public interface GeneratorCallback{

    void generate(Chunk chunk,long seed);

}
