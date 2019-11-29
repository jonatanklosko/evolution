package agh.cs.evolution;

public class Main {
  public static void main(String[] args) {
    Genome genome1 = new Genome();
    Genome genome2 = new Genome();
    System.out.println(genome1);
    System.out.println(genome2);
    System.out.println(genome1.combine(genome2));
  }
}
