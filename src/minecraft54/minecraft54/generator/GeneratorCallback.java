package minecraft54.minecraft54.generator;

import minecraft54.minecraft54.Chunk;

public interface GeneratorCallback{

    void generate(Chunk chunk,long seed);

}
