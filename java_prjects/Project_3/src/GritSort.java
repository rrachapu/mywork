/**
 * CS 251: Data Structures and Algorithms
 * Project 3: Part 1
 *
 * TODO: implement gritSort.
 *
 * @author TODO: add your name here
 * @username TODO: add your Purdue username here
 * @sources TODO: list your sources here
 *
 * Use the algorithms written in sorting to implement this class.
 *
 */
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class GritSort<Item extends Comparable<Item>> {

    /**
     *
     * Default Constructor
     *
     */
    public GritSort() {
    }

    /**
     *
     * Implement Grit Sort as defined in the Handout.
     * for example:
     * list = {3,5,6,8,10,2,4,5,6,1,2,4,5}
     * return value = {1,2,2,3,4,4,5,5,5,6,6,8,10}
     *
     *
     * @param list
     * @return sorted list
     *
     */
    public ArrayList<Item> grit(ArrayList<Item> list) {
        /*
            TODO: part 1
            TODO: STEP 1: implement and call makeChunks on list to divide the list into the sorted chunks.
            TODO: STEP 1.5: make the required number of buckets(can differ from implementation to implementation) and
            TODO            put each chunk into a bucket of proper size.
            TODO: STEP 2: implement and call mergeChunk to merge the chunks in a bucket. do this for every bucket
            TODO: STEP 3: merge the buckets and return the elements of the merged buckets as a list
         */
        ArrayList<ArrayList<Item>> chunks = makeChunks(list);
        HashMap<Integer, ArrayList<ArrayList<Item>>> hash = makeBuckets(chunks);
        Object[] arr = hash.keySet().toArray();
        ArrayList<ArrayList<Item>> buckets = new ArrayList<ArrayList<Item>>();
        for (int i = 0; i < arr.length; i++) {
            buckets.add(mergeChunk(hash.get(arr[i])));
        }
        ArrayList<Item> out = mergeChunk(buckets);
        /*
        ArrayList<Integer> hello = new ArrayList<Integer>();
        int[] intArray = new int[]{3,5,6,8,10,2,4,5,6,1,2,4,5};
        for (int i = 0; i < intArray.length; i++) {
           hello.add(intArray[i]);
        }
        System.out.println(hello);
        ArrayList<ArrayList<Item>> hello2 = makeChunks((ArrayList<Item>) hello);
        System.out.println(hello2);
        HashMap<Integer, ArrayList<ArrayList<Item>>> hello3 = makeBuckets(hello2);
        System.out.println(hello3);
        System.out.println(mergeChunk(hello3.get(4)));

         */
        return out;
    }

    /**
     *
     * This function takes in a list and returns an ArrayList of ArrayList.
     * Each chunk found is stored in an array list and then all the chunks are in turn stored in an array list which is
     * then returned.
     *
     * for example
     * list = {3,5,6,8,10,2,4,5,6,1,2,4,5}
     *
     * return value = {
     *                     {3,5,6,8,10},
     *                     {2,4,5,6},
     *                     {1,2,4,5}
     *                }
     *
     * @param list
     * @return array list of chunks(sorted sub-lists in the original input list)
     *
     */
    public ArrayList<ArrayList<Item>> makeChunks(ArrayList<Item> list) {
        // TODO: part 1
//        ArrayList<Item> hello = new ArrayList<Item>();
//        int[] intArray = new int[]{3,5,6,8,10,2,4,5,6,1,2,4,5};
//        for (int i = 0; i < intArray.length; i++) {
//            hello.add(intArray[i]);
//        }
//        list = hello;
        ArrayList<ArrayList<Item>> out = new ArrayList<ArrayList<Item>>();
        if (list.size() < 2) {
            out.add(list);
            return out;
        }
        Item last = list.get(0);
        int ind = 0;
        out.add(new ArrayList<Item>());
        out.get(ind).add(list.get(0));
        for (int i = 1; i < list.size(); i++) {
            if (last.compareTo(list.get(i)) > 0) {
                out.add(new ArrayList<Item>());
                ind++;
                out.get(ind).add(list.get(i));
            } else {
                out.get(ind).add(list.get(i));
            }
            last = list.get(i);
        }
        /*
        for (int i = 0; i < out.size(); i++) {
            System.out.println(out.get(i));
        }
        */
        return out;
    }



    /**
    *
    * This function takes in an ArrayList of ArrayList and returns an HashMap<Integer, ArrayList<ArrayList<Item>>> 
    * Chunks of the same size are stored in the same bucket. 
    * Each chunk of the size say S is stored in a bucket which corresponds to that size S.
    * Thus, for the returned HashMap, the key is the chunk size, and the corresponding value is a list of chunks of that size.
    * for example
    * chunks = {
    *              {3,5,6,8,10},
    *              {2,4,5,6},
    *              {1,2,4,5}
    *          }
    *
    * return value = {
    * 					4: {{2,4,5,6},{1,2,4,5}},
    * 					5: {{3,5,6,8,10}}
    * 				  }
    * i.e., the bucket for chunks of size 4 will have {{2,4,5,6},{1,2,4,5}} and
    * bucket for chunks of size 5 will have {{3,5,6,8,10}}
    *
    * @param chunks
    * @return HashMap, where key is the chunk size and value is a list of chunks of that size
    * (sorted sub-lists in the original input list)
    *
    */
    public HashMap<Integer, ArrayList<ArrayList<Item>>> makeBuckets(ArrayList<ArrayList<Item>> chunks) {
        //System.out.println(chunks);
    	HashMap<Integer, ArrayList<ArrayList<Item>>> buckets = new HashMap<Integer, ArrayList<ArrayList<Item>>>();
    	// make buckets is a helper function. 
    	// CHANGE THIS ACCORDING TO YOUR IMPLEMENTATION
        // if (chunks.size() > 20) {return buckets;}

        for (int i = 0; i < chunks.size(); i++) {
            // System.out.println("iteration " + i);
            if (buckets.get(chunks.get(i).size()) == null) {
                buckets.put(chunks.get(i).size(), new ArrayList<ArrayList<Item>>());
                buckets.get(chunks.get(i).size()).add(chunks.get(i));
            } else {
                buckets.get(chunks.get(i).size()).add(chunks.get(i));
            }
        }
        System.out.println("done w for loop");
	   	return buckets;
	}




    /**
     *
     * This function takes in a bucket(ArrayList of ArrayList) and returns an ArrayList which has the chunks in
     * the bucket merged.
     *
     * for example
     * bucket = {
     *          {2,4,5,6},
     *          {1,2,4,5}
     *         }
     *
     * return value = {1,2,2,4,4,5,5,6}
     *
     *
     * @param bucket
     * @return merged and sorted list
     *
     */
    public ArrayList<Item> mergeChunk(ArrayList<ArrayList<Item>> bucket) {
        // TODO: part 1
        if (bucket == null) {
            return null;
        } else if (bucket.get(0) == null) {
            return null;
        } else if (bucket.size() == 1) {
            return bucket.get(0);
        }

        ArrayList<Item> out = new ArrayList<Item>();
        int count = 0;
        for (int i = 0; i < bucket.size(); i++) {
            count += bucket.get(i).size();
        }
        int max;
        for (int i = 0; i < count; i++) {
            max = -1;
            for (int j = 0; j < bucket.size(); j++) {
                if (bucket.get(j).size() != 0) {
                    if (max == -1) {
                        max = j;
                    }
                    if (bucket.get(j).get(0).compareTo(bucket.get(max).get(0)) < 0) {
                        max = j;
                    }
                }
            }
            out.add(bucket.get(max).get(0));
            bucket.get(max).remove(0);
        }
        return out;
    }




    /**
     *
     * This is the main function to run the gritSort class to help with debugging.
     *
     * @param args
     */
    public static void main(String[] args) {
        Sorting<Integer> s = new Sorting<>();
        ArrayList<Integer> A = new ArrayList<Integer>();
        Integer[] K = {3,5,6,8,10,2,4,5,6,1,2,4,5};

        for (Integer k : K) {
            A.add(k);
        }

        s.print(A);

    }

}
