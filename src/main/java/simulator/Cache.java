package simulator;

public class Cache {

  private int[] cache;
  private int capacity;

  Cache(int capacity) {
    this.capacity = capacity;
    cache = new int[capacity];
  }

  public int getFromCache(int index) {
    if (index >= capacity) {
      return -1;
    }
    return cache[index];
  }

  public void putInCache(int index, int value) {
    if (index >= capacity) {
      return;
    }
    cache[index] = value;
  }

  public String printCacheValues() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Cache Values\n");
    stringBuilder.append("============\n");
    for (int index = 0; index < cache.length; index++) {
      if (cache[index] > 0) {
        stringBuilder.append("[" + index + "]: " + cache[index] + "\n");
      }
    }
    return stringBuilder.toString();
  }
}
