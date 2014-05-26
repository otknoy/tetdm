package module.MiningModules.TermFrequency;

public class Term implements Comparable {

  private String surface;
  private int frequency;

  public Term(String surface, int frequency) {
    this.surface = surface;
    this.frequency = frequency;
  }

  public String getSurface() { return surface; }
  public int getFrequency() { return frequency; }


  @Override
  public int compareTo(Object other) {
    Term t = (Term)other;
    return t.getFrequency() - this.getFrequency();
  }
}
