package agh.cs.evolution;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Genome {
  public static final int GENOME_SIZE = 32;
  private final List<Byte> genes;

  public Genome() {
    this.genes = getRandomGenes();
  }

  public Genome(List<Byte> genes) {
    if (genes.size() != GENOME_SIZE) {
      throw new IllegalArgumentException(
          String.format("Genome must have %d genes, given %d.", GENOME_SIZE, genes.size())
      );
    }
    this.genes = genes;
  }

  @Override
  public String toString() {
    String genesString = this.genes.stream()
        .map(String::valueOf)
        .collect(Collectors.joining(" "));
    return String.format("Genome{%s}", genesString);
  }

  public Genome combine(Genome other) {
    int[] splitIndices = RandomUtils.random.ints(1, GENOME_SIZE - 1)
        .distinct().limit(2).sorted().toArray();
    List<Byte> combinedGenes = new ArrayList<>();
    combinedGenes.addAll(this.genes.subList(0, splitIndices[0]));
    combinedGenes.addAll(other.genes.subList(splitIndices[0], splitIndices[1]));
    combinedGenes.addAll(this.genes.subList(splitIndices[1], GENOME_SIZE));
    Genome.normalizeGenes(combinedGenes);
    return new Genome(combinedGenes);
  }

  private static List<Byte> getRandomGenes() {
    List<Byte> randomGenes = RandomUtils.random.ints(0, 8)
        .limit(GENOME_SIZE)
        .boxed()
        .map(Integer::byteValue)
        .collect(Collectors.toCollection(ArrayList::new));
    Genome.normalizeGenes(randomGenes);
    return randomGenes;
  }

  /**
   * Ensures there's at least one gene for each value from [0, 7] by placing missing genes randomly.
   * Sorts the genes.
   */
  private static void normalizeGenes(List<Byte> genes) {
    IntStream.range(0, 8).boxed()
        .map(Integer::byteValue)
        .filter(gene -> !genes.contains(gene))
        .forEach(missingGene -> {
          int[] availableIndices = IntStream.range(0, GENOME_SIZE)
              .filter(index -> Collections.frequency(genes, genes.get(index)) > 1)
              .toArray();
          int index = availableIndices[RandomUtils.random.nextInt(availableIndices.length)];
          genes.set(index, missingGene);
        });
    Collections.sort(genes);
  }
}
