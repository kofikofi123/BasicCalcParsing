 // On my honor:
 // - I have not used source code obtained from another student,
 //   or any other unauthorized source, either modified or
 //   unmodified.
 //
 // - All source code and documentation used in my program is
 //   either my original work, or was derived by me from the
 //   source code published in the textbook for this course.
 //
 // - I have not discussed coding details about this project
 //   with anyone other than my partner (in the case of a joint
 //   submission), instructor, ACM/UPE tutors or the TAs assigned
 //   to this course. I understand that I may discuss the concepts
 //   of this program with other students, and that another student
 //   may help me debug my program so long as neither of us writes
 //   anything during the discussion or modifies any computer file
 //   during the discussion. I have violated neither the spirit nor
 //   letter of this restriction.
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.RandomAccessFile;

/**
 * HeapSort implementation for files containing
 * 4 byte key/value pairs (2 byte signed keys,
 *                         2 byte signed values)
 * @author erikchair
 * @version 10.24.2022
 */
public class HeapSort {
    /**
     * Main entrance to the sorting program
     * @param args The arguments the sort should work with
     *             requires 3 arguments:
     *             Name of file to work with,
     *             Number of Buffer for the Buffer Pool
     *             Name of the stats file to output to
     * @throws FileNotFoundException
     */
    public static void main(String[] args) 
        throws FileNotFoundException,
               IOException {
        PrintStream out = System.out;
        if (args.length != 3) {
            return;
        }
        
        String heapFilename = args[0];
        String statsFilename = args[2];
        
        
        RandomAccessFile file =
            new RandomAccessFile(
                new File(heapFilename),
                "rw");
        
        int bufferPoolSize = Integer.parseInt(args[1]);
        long fileSize = file.length();
        long entries = fileSize >> 2;
        
        BufferPool bufferPool =
            new BufferPool(file.getChannel(),
                           bufferPoolSize);
        
        MaxHeap heap = new MaxHeap(bufferPool,
                                   (entries));
        
        
        //main sort start
        long before = System.currentTimeMillis();
        for (int i = 0; i < entries; i++) {
            heap.removeMax();
        }
        
        bufferPool.flush();
        
        long after = System.currentTimeMillis();
        //main sort finish

        StringBuilder builder = new StringBuilder();
        int check = 0;
        
        for (int i = 0; i < entries; i += 4096) {
            short key = file.readShort();
            short value = file.readShort();
            
            builder.append(key).append(' ').append(value);
            
            if ((check++ % 8) == 7) {
                builder.append('\n');
                check = 0;
            }
            else {
                builder.append("    ");
            }
        }
        
        out.println(builder.toString());
        
        
        
        
        file.close();
        
        long timeLapse = ((after - before));
        
        builder.setLength(0);
        
        builder.append("------  STATS ------\n");
        builder.append("File Name: ")
            .append(heapFilename)
            .append('\n');
        builder.append("Cache Hits: ")
            .append(bufferPool.getCacheHit())
            .append('\n');
        builder.append("Cache Misses: ")
            .append(bufferPool.getCacheMiss())
            .append('\n');
        builder.append("Disk Reads: ")
            .append(bufferPool.getDiskReads())
            .append('\n');
        builder.append("Disk Writes: ")
            .append(bufferPool.getDiskWrites())
            .append('\n');
        builder.append("Time to Sort: ")
            .append(timeLapse)
            .append('\n');
        
        
        FileWriter writer = new FileWriter(statsFilename, true);
        writer.write(builder.toString());
        writer.flush();
        writer.close();
    }
}
