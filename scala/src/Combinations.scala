
import scala.annotation._

/**
 * Form all possible combinations of n mutually distinguishable
 * elements m at a time where each combination is a selection of m
 * elements from the n without regard to their order of arrangement.
 */
object Combinations {
  /** Test driver.
    * Usage: scala Combinations A B C D E
    */
  def main( elements: Array[String] ) {
    // Assume the elements are arranged from most to least
    // prominent. The elements are lexical in this example but
    // there is no restriction that they be lexical.

    // human order for all m
    println( "human order, m=0 to m=n:" )
    humanOrder( elements )
    println( "" )

    // machine order for all m
    println( "machine order, m=0 to m=n:" )
    machineOrder( elements )
    println( "" )


    val m: Int = 2

    // human order for a specific m
    println( "human order, m=" + m + " for example:" )
    humanOrderM( elements, m )
    println( "" )

    // machine order for a specific m
    println( "machine order, m=" + m + " for example:" )
    machineOrderM( elements, m )
  }


  /** Human order for all m.
    * @param elements The set of elements
    */
  private def humanOrder( elements: Array[String] ) {
    for ( m <- 0 to elements.length )
      humanOrderM( elements, m )
  }

  /** Human order for a specific m.
    * @param elements The set of elements
    * @param m The number of elements to be combined
    */
  private def humanOrderM( elements: Array[String], m: Int ) {
    // The possible number of non-repeating combinations for m
    val u = binomialCoefficient( elements.length, m )

    // The bit representation of the starting non-repeating combination
    val bits = ( BigInt( 1 ) << elements.length ) - 1

    // Recurse
    humanOrderM( elements, m, u, bits )
  }


  /** Human order for a specific m. Start with bits = 2**n - 1. Recurse
    * through all combinations of non-repeating elements, decrementing
    * by one.
    * @param elements The set of elements
    * @param m The number of elements to be combined
    * @param u The possible number of non-repeating combinations for m
    * @param bits The bit representation of the current non-repeating combination
    * @param count The current count of combinations with m elements
    */
  @tailrec
  private def humanOrderM( elements: Array[String], m: Int, u: BigInt, bits: BigInt, count: BigInt = 0 ) {
    //  check whether count is less than the possible number of non-repeating combinations for m
    if ( count < u ) {
      // Check whether this is a combination of m elements.
      if ( bits.bitCount == m ) {
        // Store and list the current combination. Reverse the order
        // to take advantage of the fact that less significant bits
        // change faster when incrementing or decrementing.
        listCombination( storeCombination( elements.reverse, bits ).reverse )

        humanOrderM( elements, m, u, bits - 1, count + 1 )
      }
      else {
        humanOrderM( elements, m, u, bits - 1, count )
      }
    }
  }
  

  /** Machine order for all m.
    * @param elements The set of elements
    */
  private def machineOrder( elements: Array[String] ) {

    // n is the number of elements
    val n = elements.length

    // t is the possible number of non-repeating combinations: 2**n
    val t = BigInt( 1 ) << n

    // Recurse
    machineOrder( elements, t )
  }
  

  /** Machine order for all m. Start with bits = 0. Recurse through all
    * combinations of non-repeating elements, incrementing by one.
    * @param elements The set of elements
    * @param t The possible number of non-repeating combinations
    * @param bits The bit representation of the current non-repeating combination
    */
  @tailrec
  private def machineOrder( elements: Array[String], t: BigInt, bits: BigInt = 0 ) {

    // n is the number of elements
    val n = elements.length

    // t is the possible number of non-repeating combinations: 2**n
    val t = BigInt( 1 ) << n
    if ( bits < t ) {
      // Store and list the current combination.
      listCombination( storeCombination( elements, bits ) )

      machineOrder( elements, t, bits + 1 )
    }
  }


  /** Machine order for a specific m.
    * @param elements The set of elements
    * @param m The number of elements to be combined
    */
  private def machineOrderM( elements: Array[String], m: Int ) {
    // The possible number of non-repeating combinations for a specific m
    val u = binomialCoefficient( elements.length, m )

    // Recurse
    machineOrderM( elements, m, u )
  }


  /** Machine order for a specific m. Start with bits = 0. Recurse
    * through all combinations of non-repeating elements, incrementing
    * by one.
    * @param elements The set of elements
    * @param m The number of elements to be combined
    * @param u The possible number of non-repeating combinations for a specific m
    * @param bits The bit representation of the current non-repeating combination
    * @param count The current count of combinations with m elements
    */
  @tailrec
  private def machineOrderM( elements: Array[String], m: Int, u: BigInt, bits: BigInt = 0, count: BigInt = 0 ) {
    //  check whether count is less than the possible number of non-repeating combinations for m
    if ( count < u ) {
      // Check whether this is a combination of m elements.
      if ( bits.bitCount == m ) {
        // Store and list the current combination.
        listCombination( storeCombination( elements, bits ) )

        machineOrderM( elements, m, u, bits + 1, count + 1 )
      }
      else {
        machineOrderM( elements, m, u, bits + 1, count )
      }
    }
  }


  /** Binomial coefficient for optimization with large n. Uses factorials.
    * @param n The total number of elements
    * @param m The number of elements to be combined
    */
  private def binomialCoefficient( n: Int, m: Int ): BigInt =
    factorial( n ) / ( factorial( m ) * factorial( n - m ) )


  /** Factorial used for binomial coefficient
    * @param n The number for which the factorial is calculated
    * @param accumulator The current factorial
    */
  @tailrec
  private def factorial( n: Int, accumulator: BigInt = 1 ): BigInt =
    if ( n == 0 ) accumulator else factorial( n - 1, accumulator * n )



  /** Store the combination
    * @param elements The set of elements
    * @param bits The bit representation of the current non-repeating combination
    */
  private def storeCombination( elements: Array[String], bits: BigInt = 0 ): Array[String] = {
    val combination = new Array[String]( elements.length )
    val empty = "-"

    // For all n elements test whether element j is present.
    for ( j <- elements.indices ) {
      // If present add it to the current combination.
      if ( bits.testBit( j ) )
        combination( j ) = elements( j )
      else
        combination( j ) = empty
    }
    combination
  }


  /** Process the given combination. Here we just write it to the console.
    *
    * @param combination The combination to process
    */
  private def listCombination( combination: Array[String] ) {
    val n = combination.length

    // For this combination list the elements that are present.
    for ( j <- 0 until n )
    {
      print( combination.apply(j).toString )
      print( " " ) // element separator
    }
    println( "" ) // end of this combination
  }
}

