
public abstract class Cache {
	ReplacementStrategy replacementStrategy;
	int currentTime = 0;
	int cacheSize;
	int blockSize;
	int nBlocks;
	
	int nhits = 0;
	int nmisses = 0;
//	int nreads = 0;
	
	public int getHitCount() {
		return nhits;
	}
	public int getMissCount() {
		return nmisses;
	}
	
	public abstract void fetch(long address);
	
	@Override
	public String toString() {
		
		String toret = "";
		toret += "Cache Size: \t\t" + this.cacheSize + " K\n";
		toret += "Block Size: \t\t" + this.blockSize + " B\n";
		toret += "# Blocks in cache: \t" + this.nBlocks + " \n";
		if(this.replacementStrategy != null)
			toret += "Replacement Strategy: \t" + this.replacementStrategy.toString() + " \n";
		toret += "Hits: \t\t\t" + this.nhits + "\n";
		toret += "Misses: \t\t" + this.nmisses + "\n";
		toret += "Miss rate: \t\t" + (this.nmisses * 100.0) / (this.nhits + this.nmisses) + " %";
		return toret;
	}
}
