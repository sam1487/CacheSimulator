# CacheSimulator

The simulator is a Java application. It runs on JDK 1.7 or 1.6. It is in the form of an eclipse project, but can also be run from the command line. The main class is in Program.java All the files are under the CacheSimulator/src directory.
At end of the simulation run, a detailed statistics is displayed on the screen. Near the end of the output, there is a comma spearated list of numbers that can be pasted into Excel and generate graphs easily. The columns for the list are cache size, block size and miss rate %.

<h3>Running the code</h3>
  <ol>
  <li>In the terminal (command prompt) window, type: cd CacheSimulator/src </li>
  <li>Compile the java files: javac *.java</li>
￼  <li> Run the Program class: java Program</li>
  </ol>
  
<h3>Inside the code</h3>
<b>Program:</b>  The main entry point for the simulator. Here, the initial cache and block sizes, as well as displaying the statistics are handled.

<b>Cache:</b> An abstract class that defines the interface and basic functions for the caches in the system. Defines the abstract function fetch.

<b>DirectMappedCache:</b> Extends Cache, and implements the abstract method fetch. Calling fetch is equivalent to the CPU querying the cache for an address.

<b>FullyAssociativeCache:</b> Extends Cache, and implements the abstract method fetch. It also takes a ReplacementStrategy in its constructor.

<b>ReplacementStrategy:</b> Interface for LRU and FIFO implementation, defining only one method interface, getIndexForAddress

<b>FIFOReplacementStrategy:</b> Implements the ReplacementStrategy. Chooses the next block to remove in FIFO strategy

<b>LRUReplacementStrategy:</b> Implements the ReplacementStrategy by employing LRU CacheBlock Model class representing a single block in the cache
