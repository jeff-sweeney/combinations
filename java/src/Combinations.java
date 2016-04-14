import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;


/**
 * Form all possible combinations of n mutually distinguishable
 * elements m at a time where each combination is a selection of m
 * elements from the n without regard to their order of arrangement.
 */
public class Combinations
{
    /** Test driver.
     * Usage: java -jar Combinations.jar a b c d e
     */
    public static void main( String[] elements )
    {
        // Assume the elements are arranged from most to least
        // prominent. The elements are lexical in this example but
        // there is no restriction that they be lexical.
        
        // n is the number of elements
        int n = Array.getLength( elements );

        // t is total number of nonrepeating combinations: 2^n
        BigInteger t = BigInteger.ONE.shiftLeft( n );

        
        // human order for all m
        System.out.println( "human order, m=0 to m=n:" );
        for ( int m = 0; m <= n; ++m )
            humanOrder( elements, n, t, m );
        System.out.println( "" );

        // machine order for all m
        System.out.println( "machine order, m=0 to m=n:" );
        machineOrder( elements, n, t );
        System.out.println( "" );

        
        int m = 2;
        
        // human order for a specific m
        System.out.println( String.format( "human order, m=%d for example:", m ) );
        humanOrder( elements, n, t, m );
        System.out.println( "" );

        // machine order for a specific m
        System.out.println( String.format( "machine order, m=%d for example:", m ) );
        machineOrder( elements, n, t, m );
    }    

    
    /** human order
     * @param elements The set of elements
     * @param n The total number of elements
     * @param t The total number of nonrepeating combinations
     * @param m The number of elements to be combined
     */
    private static void humanOrder( Object[] elements, int n, BigInteger t, int m )
    {
        // total number of combinations with m elements
        BigInteger b = binomialCoefficient( n, m );        
        // current count of combinations with m elements
        BigInteger q = BigInteger.ZERO;
            
        // Loop over all 2^n combinations, decrementing by one.
        for ( BigInteger i = t.subtract( BigInteger.ONE );
              i.compareTo( BigInteger.ZERO ) >= 0 && q.compareTo( b ) < 0;
              i = i.subtract( BigInteger.ONE ) )
        {
            // Check whether this is a combination of m elements.
            if ( i.bitCount() == m )
            {
                q = q.add( BigInteger.ONE );
                Object[] combination = new Object[n];
                String empty = "-";
                    
                // For all n elements test whether element j is present.
                for ( int j = 0; j < n; ++j )
                {
                    // If present add it to the current combination in reverse order.  Reverse the
                    // order to take advantage of the fact that less significant bits change faster when
                    // incrementing or decrementing.
                    if ( i.testBit( j ) )
                        combination[(n - 1) - j] = elements[(n - 1) - j];
                    else
                        combination[(n - 1) - j] = empty;
                }

                // Process the current combination.
                listCombination( combination );
            }
        }
    }

    
    /** machine order for all m
     * @param elements The set of elements
     * @param n The total number of elements
     * @param t The total number of nonrepeating combinations
     */
    private static void machineOrder( Object[] elements, int n, BigInteger t )
    {
        // Iterate through all combinations of nonrepeating elements, incrementing by one.
        for ( BigInteger i = new BigInteger( "0" ); i.compareTo( t ) < 0; i = i.add( BigInteger.ONE ) )
        {
            Object[] combination = new Object[n];
            String empty = "-";
                    
            // For all n elements test whether element j is present.
            for ( int j = 0; j < n; ++j )
            {
                // If present add it to the current combination.
                if ( i.testBit( j ) )
                    combination[j] = elements[j];
                else
                    combination[j] = empty;
            }

            // List the current combination.
            listCombination( combination );
        }
    }


    /** machine order for a specific m
     * @param elements The set of elements
     * @param n The total number of elements
     * @param t The total number of nonrepeating combinations
     * @param m The number of elements to be combined
     */
    private static void machineOrder( Object[] elements, int n, BigInteger t, int m )
    {
        // total number of combinations with m elements
        BigInteger b = binomialCoefficient( n, m );    
        // current count of combinations with m elements
        BigInteger q = BigInteger.ZERO;

        // Iterate through all combinations of nonrepeating elements, incrementing by one.
        for ( BigInteger i = new BigInteger( "0" );
              i.compareTo( t ) < 0 && q.compareTo( b ) < 0;
              i = i.add( BigInteger.ONE ) )
        {
            if ( i.bitCount() == m )
            {
                q = q.add( BigInteger.ONE );
                Object[] combination = new Object[n];
                String empty = "-";
                    
                // For all n elements test whether element j is present.
                for ( int j = 0; j < n; ++j )
                {
                    // If present add it to the current combination.
                    if ( i.testBit( j ) )
                        combination[j] = elements[j];
                    else
                        combination[j] = empty;
                }

                // List the current combination.
                listCombination( combination );
            }
        }
    }


    /** Binomial coefficient used for optimization with large n
     * @param n The total number of elements
     * @param m The number of elements to be combined
     */
    private static BigInteger binomialCoefficient( int n, int m )
    {
        ArrayList<BigInteger> b = new ArrayList<>(n + 1);
        b.add( BigInteger.ONE );
        for ( int i = 1; i <= n; ++i )
        {
            b.add( BigInteger.ONE );
            for ( int j = i-1; j > 0; --j )
                b.set( j, b.get( j ).add( b.get( j - 1 ) ) );
        }

        return b.get( m );
    }


    /** Process the given combination. Here we just write it to the console.
     * @param combination The combination to process
     */
    private static void listCombination( Object[] combination )
    {
        int n = Array.getLength( combination );

        // For this combination list the elements that are present.
        for ( int j = 0; j < n; ++j )
        {
            System.out.print( combination[j].toString() );
            System.out.print( " " ); // element separator
        }
        System.out.println( "" ); // end of this combination
    }
}

