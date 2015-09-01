
public class FullyAssociativeCache extends Cache {
	CacheBlock[] memory;
	
	public FullyAssociativeCache(int cacheSize, int blockSize, ReplacementStrategy strategy) {
		this.cacheSize = cacheSize;
		this.blockSize = blockSize;
		this.replacementStrategy = strategy;
		
		setupMemory();
	}
	
	private void setupMemory() {
		int cacheSizeInBytes = this.cacheSize * 1024;
		
		this.nBlocks = cacheSizeInBytes / this.blockSize;
		memory = new CacheBlock[this.nBlocks];
		for(int i = 0; i < this.memory.length; ++i) 
			this.memory[i] = new CacheBlock();				
	}

	/***
	 * In a full association, we don't need an index, as any block in memory can 
	 * reside at any block in the cache. The only deciding factors, in order of 
	 * precedence, are 
	 * 	1. Block in cache that is empty
	 * 	2. Space held by another block who got in the cache the earliest (FIFO)
	 * 	3. Space held by another block which is referenced longest time back (LRU)
	 */
	public void fetch(long address) {
		
		address = (address * 4) / this.blockSize;
		
		boolean addressFound = false;
		// Look if its already in the cache
		for(CacheBlock cacheBlock : memory) {
			if(cacheBlock.valid && cacheBlock.tag == (int)address) {
				// We have a hit
				cacheBlock.lastAccessedAt = this.currentTime;
				this.nhits++;
				addressFound = true;
				break;
			}
		}
		
		if(!addressFound) {
			// If code reaches here, the block was not found in the cache.
			// Use a replacement strategy to get the appropriate slot for the block
			int blockIndex = this.replacementStrategy.getIndexForAddress(address, memory);
			
			CacheBlock cacheBlock = memory[blockIndex];
			cacheBlock.valid = true;
			cacheBlock.tag = (int)address;
			cacheBlock.createdAt = this.currentTime;
			cacheBlock.lastAccessedAt = this.currentTime;
			this.nmisses++;
		}
		
		this.currentTime++;
	}
	
	@Override
	public String toString() {
		String toret = "Fully Associative Cache \n" + super.toString();
		return toret;
	}
}
