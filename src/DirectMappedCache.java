
public class DirectMappedCache extends Cache {
	CacheBlock[] memory;
	
	public DirectMappedCache(int cacheSize, int blockSize) {
		this.cacheSize = cacheSize;
		this.blockSize = blockSize;
		setupMemory();
	}
	private void setupMemory() {
		int cacheSizeInBytes = this.cacheSize * 1024;
		this.nBlocks = cacheSizeInBytes / this.blockSize;
		
		memory = new CacheBlock[this.nBlocks];
		for(int i = 0; i < this.memory.length; ++i) 
			this.memory[i] = new CacheBlock();
	}
	public void fetch(long address) {
		long a = address ;
		
		// convert 4-byte word address to block address
		a = (address * 4) / this.blockSize;
		
		// The index is the number of bits required to represent the number of blocks
		// we have. The rest of the bits are considered as tag
		int index = (int) Math.abs(a % (long)this.nBlocks);
		int tag = (int) Math.abs(a / Integer.highestOneBit(this.nBlocks));
		
		// Get the current block stored in the index
		CacheBlock block = this.memory[index];
		
		if(!block.valid || block.tag != tag) {
			block.tag = tag;
			block.valid = true;
			block.createdAt = this.currentTime;
			block.lastAccessedAt = this.currentTime;
			this.nmisses++;			
		}
		else {
			block.lastAccessedAt = this.currentTime;
			this.nhits++;
		}
		
		this.currentTime++;
	}
	
	@Override
	public String toString() {
		String toret = "Direct Mapped Cache \n" + super.toString();
		return toret;
	}
	
}
