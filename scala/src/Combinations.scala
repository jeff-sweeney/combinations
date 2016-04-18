
import scala.annotation._

/**
 * Form all possible combinations of n mutually distinguishable
 * elements m at a time where each combination is a selection of m
 * elements from the n without regard to their order of arrangement.
 */
object Combinations {
  /** Test driver.
    * Usage: java -jar Combinations.jar a b c d e
    */
  def main( elements: Array[String] ) {
    // Assume the elements are arranged from most to least
    // prominent. The elements are lexical in this example but
    // there is no restriction that they be lexical.

    // n is the number of elements
    val n = elements.length

    // t is total number of nonrepeating combinations: 2^n
    val t = BigInt( 1 ) << n

    // human order for all m
    println("human order, m=0 to m=n:")
    for ( m <- 0 to n )
      humanOrderM( elements, n, t, binomialCoefficient( n, m ), m, t - 1 )
    println("")

    // machine order for all m
    println("machine order, m=0 to m=n:")
    machineOrder(elements, n, t)
    println("")


    val m = 2

    // human order for a specific m
    println("human order, m=" + m + " for example:")
    humanOrderM( elements, n, t, binomialCoefficient( n, m ), m, t - 1 )
    println("")

    // machine order for a specific m
    println("machine order, m=" + m + " for example:")
    machineOrderM(elements, n, t, binomialCoefficient( n, m ), m )
  }


  /** Human order for a specific m
    * @param elements The set of elements
    * @param n The total number of elements
    * @param t The total number of nonrepeating combinations
    * @param u The total number of nonrepeating combinations for a specific m
    * @param m The number of elements to be combined
    * @param accumulator The current nonrepeating combination
    * @param breaker The current count of combinations with m elements
    */
  @tailrec
  private def humanOrderM( elements: Array[String], n: Int, t: BigInt, u: BigInt, m: Int, accumulator: BigInt = 0, breaker: BigInt = 0 ) {
    var q = breaker

    // Recurse through all combinations of nonrepeating elements, decrementing by one.
    if ( accumulator != 0 && breaker != u ) {
      // Check whether this is a combination of m elements.
      if ( accumulator.bitCount == m ) {
        q = q + 1
        val combination = new Array[String]( n )
        val empty = "-"

        // For all n elements test whether element j is present.
        for ( j <- 0 until n ) {
          // If present add it to the current combination in reverse order.  Reverse the
          // order to take advantage of the fact that less significant bits change faster when
          // incrementing or decrementing.
          if ( accumulator.testBit( j ) )
            combination( n - 1 - j ) = elements( n - 1 - j )
          else
            combination( n - 1 - j ) = empty
        }

        // Process the current combination.
        listCombination( combination )
      }
      humanOrderM( elements, n, t, u, m, accumulator - 1, q )
    }
  }
  

  /** machine order for all m. Recurse through all combinations of nonrepeating elements.
    * @param elements The set of elements
    * @param n The total number of elements
    * @param t The total number of nonrepeating combinations
    * @param accumulator The current nonrepeating combination
    */
  @tailrec
  private def machineOrder( elements: Array[String], n: Int, t: BigInt, accumulator: BigInt = 0 ) {
    if ( accumulator != t ) {
      val combination = new Array[String]( n )
      val empty = "-"
                    
      // For all n elements test whether element j is present.
      for ( j <- 0 until n ) {
        // If present add it to the current combination.
        if ( accumulator.testBit( j ) )
          combination( j ) = elements( j )
        else
          combination( j ) = empty
      }

      // List the current combination.
      listCombination( combination )
      machineOrder( elements, n, t, accumulator + 1 )
    }
  }


  /** Machine order for a specific m
    * @param elements The set of elements
    * @param n The total number of elements
    * @param t The total number of nonrepeating combinations for all m
    * @param u The total number of nonrepeating combinations for a specific m
    * @param m The number of elements to be combined
    * @param accumulator The current nonrepeating combination
    * @param breaker The current count of combinations with m elements
    */
  @tailrec
  private def machineOrderM( elements: Array[String], n: Int, t: BigInt, u: BigInt, m: Int, accumulator: BigInt = 0, breaker: BigInt = 0 ) {
    var q = breaker

    // Recurse through all combinations of nonrepeating elements, incrementing by one.
    if ( accumulator != t && breaker != u ) {
      // Check whether this is a combination of m elements.
      if ( accumulator.bitCount == m ) {
        q = q + 1
        val combination = new Array[String]( n )
        val empty = "-"

        // For all n elements test whether element j is present.
        for ( j <- 0 until n ) {
          // If present add it to the current combination.
          if ( accumulator.testBit( j ) )
            combination( j ) = elements( j )
          else
            combination( j ) = empty
        }

        // List the current combination.
        listCombination( combination )
      }
      machineOrderM( elements, n, t, u, m, accumulator + 1, q )
    }
  }


  /** Binomial coefficient for optimization with large n. Uses factorials.
    * @param n The total number of elements
    * @param m The number of elements to be combined
    */
  private def binomialCoefficient( n: Int, m: Int ): BigInt =
    factorial( n ) / ( factorial( m ) * factorial( n - m ) )


  /** Factorial used for binomial coefficient
    *  @param n The number for which the factorial is calculated
    */
  @tailrec
  private def factorial( n: Int, accumulator: BigInt = 1 ): BigInt =
    if ( n == 0 ) accumulator else factorial( n - 1, accumulator * n )


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

