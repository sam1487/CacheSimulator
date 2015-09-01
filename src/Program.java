import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


public class Program {
	static final int nMemoryAccesses = 100000;
	static Random generator = new Random(new Date().getTime());
	
	static ArrayList<Cache> directMappedCaches = new ArrayList<Cache>();
	static ArrayList<Cache> associativeFIFOCaches = new ArrayList<Cache>();
	static ArrayList<Cache> associativeLRUCaches = new ArrayList<Cache>();
	
	public static void main(String[] args) {
					
		int[] cacheSizesInKB = {4, 16, 64, 256};	// in KBytes
		int[] blockSizesInB = {16, 32, 64, 128, 256};	// in Bytes
		
		long[] memoryAddresses = getRandomMemoryAddresses();

		for(int cacheSize : cacheSizesInKB) {
			for(int blockSize: blockSizesInB) {				
				Cache directMappedCache = new DirectMappedCache(cacheSize, blockSize);
				Cache associativeCacheWithLRU = new FullyAssociativeCache(cacheSize, blockSize, new LRUReplacementStrategy());
				Cache associativeCacheWithFIFO = new FullyAssociativeCache(cacheSize, blockSize, new FIFOReplacementStrategy());
				
				for(int i = 0; i < nMemoryAccesses; ++i) {
					long address = memoryAddresses[generator.nextInt(memoryAddresses.length)];
					directMappedCache.fetch(address);					
					associativeCacheWithLRU.fetch(address);
					associativeCacheWithFIFO.fetch(address);				
				}		
				
				directMappedCaches.add(directMappedCache);
				associativeFIFOCaches.add(associativeCacheWithFIFO);
				associativeLRUCaches.add(associativeCacheWithLRU);
			}
		}	
		dumpFinalState();
		displayResults();
	}
	
	private static void dumpFinalState() {
		int count = directMappedCaches.size();		
		for(int i = 0; i < count; ++i) {
			Cache cache = directMappedCaches.get(i);
			System.out.println(cache.toString());
			System.out.println("\n");
		}
		
		for(int i = 0; i < count; ++i) {
			Cache cache = associativeFIFOCaches.get(i);
			System.out.println(cache.toString());
			System.out.println("\n");
		}
		
		for(int i = 0; i < count; ++i) {
			Cache cache = associativeLRUCaches.get(i);
			System.out.println(cache.toString());
			System.out.println("\n");
		}
		System.out.println("\n");
		
	}

	private static void displayResults() {
		int count = directMappedCaches.size();
		String output = "";
		
		output += "\nDirect Mapped\n";
		for(int i = 0; i < count; ++i) {
			Cache cache = directMappedCaches.get(i);
			output += String.format("%d, %d, %.2f\n", cache.cacheSize, cache.blockSize, 
					(cache.nmisses * 100.0) /  (cache.nmisses + cache.nhits));			
		}
		
		output += "\nAssociative with FIFO\n";
		for(int i = 0; i < count; ++i) {			
			Cache cache = associativeFIFOCaches.get(i);
			output += String.format("%d, %d, %.2f\n", cache.cacheSize, cache.blockSize, 
					(cache.nmisses * 100.0) /  (cache.nmisses + cache.nhits));
		}
			
		output += "\nAssociative with LRU\n";
		for(int i = 0; i < count; ++i) {
			Cache cache = associativeLRUCaches.get(i);
			output += String.format("%d, %d, %.2f\n", cache.cacheSize, cache.blockSize, 
					(cache.nmisses * 100.0) / (cache.nmisses + cache.nhits));			
		}
		
		System.out.println(output);
		
	}

	public static long[] getRandomMemoryAddresses() {
		long[] memoryAddresses = new long[100000];
		
		
		// fill in with random addresses
		Random generator = new Random(); 
		for(int i = 0; i < memoryAddresses.length; ++i) {
			memoryAddresses[i] = Math.abs(generator.nextLong()) % ((1L << 32) - 1);
			
			// Align the address to multiples of four
			memoryAddresses[i] = (memoryAddresses[i] >> 2) << 2;			
		}
		
		return memoryAddresses;			
	}
	
	/***
	 * Use this function to choose some sample memory addresses, and check 
	 * the results obtained against those we get manually. */
	public static long[] getHandPickedMemoryAddressesForTesting() {
		long[] memoryAddresses = new long[]{1,2,3,4,5,6,32,33,34,35,100, 2, 4, 2, 1, 5, 8, 3, 9, 38, 23};
		return memoryAddresses;
	}
	
}
