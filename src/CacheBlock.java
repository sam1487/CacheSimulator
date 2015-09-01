
public class CacheBlock {
	boolean valid;
	int tag;
	int data;
	int createdAt;
	int lastAccessedAt;
	
	public CacheBlock() {
		this.valid = false;
		this.createdAt = Integer.MAX_VALUE;
		this.lastAccessedAt = Integer.MIN_VALUE;
	}
}
