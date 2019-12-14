package agh.cs.evolution.elements;

import agh.cs.evolution.utils.RandomUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Genome {
  public static final int GENOME_SIZE = 32;
  private final List<Byte> genes;

  private Genome(List<Byte> genes) {
    this.genes = genes;
  }

  public static Genome randomGenome() {
    return new Genome(Genome.randomGenes());
  }

  public Byte getRandomGene() {
    return RandomUtils.randomElement(this.genes);
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

  private static List<Byte> randomGenes() {
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
          List<Integer> availableIndices = IntStream.range(0, GENOME_SIZE).boxed()
              .filter(index -> Collections.frequency(genes, genes.get(index)) > 1)
              .collect(Collectors.toList());
          genes.set(RandomUtils.randomElement(availableIndices), missingGene);
        });
    Collections.sort(genes);
  }

  public String toGenesString() {
    return this.genes.stream()
        .map(String::valueOf)
        .collect(Collectors.joining(" "));
  }

  @Override
  public String toString() {
    return String.format("Genome{%s}", this.toGenesString());
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) return true;
    if (!(other instanceof Genome)) return false;
    Genome that = (Genome) other;
    return Objects.equals(genes, that.genes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(genes);
  }
}
