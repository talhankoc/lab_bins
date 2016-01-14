import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Runs a number of algorithms that try to fit files onto disks.
 */
public class Bins {
	public Bins(){
		
	}
    public static final String DATA_FILE = "example.txt";
    
    /**
     * Reads list of integer data from the given input.
     *
     * @param input tied to an input source that contains space separated numbers
     * @return list of the numbers in the order they were read
     */
    public List<Integer> readData (Scanner input) {
        List<Integer> results = new ArrayList<Integer>();
        while (input.hasNext()) {
            results.add(input.nextInt());
        }
        return results;
    }

    
    /**
     * The main program.
     */
    public static void main (String args[]) {
        Bins bins = new Bins();
        Scanner input = new Scanner(Bins.class.getClassLoader().getResourceAsStream(DATA_FILE));
        List<Integer> data = bins.readData(input);

        PriorityQueue<Disk> pq = new PriorityQueue<Disk>();
        pq.add(new Disk(0));

        int diskId = 1;
        int total = 0;
        
        //
        for (Integer size : data) {
            Disk disk = pq.peek();
            if (disk.freeSpace() > size) {
                pq.poll();
                disk.add(size);
                pq.add(disk);
            } else {
            	//if doesn't fit, then create new disk
                Disk disk2 = new Disk(diskId);
                diskId++;
                disk2.add(size);
                pq.add(disk2);
            }
            total += size;
        }

        bins.printWorst(pq, total);

        //reverse the values in data
        Collections.sort(data, Collections.reverseOrder());
        pq.add(new Disk(0));

        diskId = 1;
        for (Integer size : data) {
            Disk d = pq.peek();
            if (d.freeSpace() >= size) {
                pq.poll();
                d.add(size);
                pq.add(d);
            } else {
                Disk d2 = new Disk(diskId);
                diskId++;
                d2.add(size);
                pq.add(d2);
            }
        }

        bins.printDec(pq);
    }
    
    /*
     * Prints priority queue in worst-fit order
     */
    private void printWorst(PriorityQueue pq, int total){
    	 System.out.println("total size = " + total / 1000000.0 + "GB");
         System.out.println();
         System.out.println("worst-fit method");
         System.out.println("number of pq used: " + pq.size());
         while (!pq.isEmpty()) {
             System.out.println(pq.poll());
         }
         System.out.println();
    }
    /*
     * Prints the values inside the priority queue in decreasing order
     */
    private void printDec(PriorityQueue pq){
    	System.out.println();
        System.out.println("worst-fit decreasing method");
        System.out.println("number of pq used: " + pq.size());
        while (!pq.isEmpty()) {
            System.out.println(pq.poll());
        }
        System.out.println();
    }
}
