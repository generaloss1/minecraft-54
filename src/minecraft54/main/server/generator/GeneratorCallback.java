package minecraft54.main.server.generator;

import minecraft54.main.client.world.Chunk;

@FunctionalInterface
public interface GeneratorCallback{

    void generate(Chunk chunk,long seed);

}
