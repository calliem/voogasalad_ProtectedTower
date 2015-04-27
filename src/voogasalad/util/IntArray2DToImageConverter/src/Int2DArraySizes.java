package voogasalad.util.IntArray2DToImageConverter.src;




/**
 * Util for determining array width and height. Assumes a rectangular array. Performs null checks.
 * 
 * @author Ruslan
 *
 */
public class Int2DArraySizes {
        
        /**
         * Performs null checks, returns 0 if null
         * 
         * @param 2D (int[][]) intArray
         * @return (int) width
         */
        public static int width2DIntArray(int[][] intArray) {
                
                if (intArray != null) {
                        
                        int[] firstRow = intArray[0];
                        return firstRow.length; 
                        
                }

                return 0;

        }

        /**
         * Performs null checks, returns 0 if null
         * 
         * @param 2D (int[][]) intArray
         * @return (int) height
         */
        public static int height2DIntArray(int[][] intArray) {

                if (intArray != null) {

                        return intArray.length; 

                }

                return 0;

        }

}
