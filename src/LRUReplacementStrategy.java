
public class LRUReplacementStrategy implements ReplacementStrategy {
	
	@Override
	public String toString() {
		return "LRU Strategy";
	}

	@Override
	public int getIndexForAddress(long address, CacheBlock[] memory) {
		int minIndex = 1;
		for(int i = 0; i < memory.length; ++i) {
			CacheBlock cacheBlock = memory[i];
			
			if(!cacheBlock.valid) return i;
			
			if(memory[i].lastAccessedAt > memory[minIndex].lastAccessedAt)
				minIndex = i;			
		}
		
		return minIndex;
	}
}
