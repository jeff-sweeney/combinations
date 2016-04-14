
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


    // bimonial coefficients for all m
    println("binomial coefficients without factorials, m=0 to m=n:")
    for ( m <- 0 to n ) {
      println( binomialCoefficient( n, m ) )
    }
    println("")

    println("binomial coefficients with factorials, m=0 to m=n:")
    for ( m <- 0 to n ) {
      println( binomialCoefficientFactorial( n, m ) )
    }
    println("")


    // human order for all m
    println("human order, m=0 to m=n:")
    for ( m <- 0 to n )
      humanOrder( elements, n, t, m )
    println("")

    // machine order for all m
    println("machine order, m=0 to m=n:")
    machineOrder(elements, n, t)
    println("")


    val m = 2

    // human order for a specific m
    println("human order, m=" + m + " for example:")
    humanOrder(elements, n, t, m)
    println("")

    // machine order for a specific m
    println("machine order, m=" + m + " for example:")
    machineOrder(elements, n, t, m)
  }


  /** human order
    *
    * @param elements The set of elements
     * @param n The total number of elements
     * @param t The total number of nonrepeating combinations
     * @param m The number of elements to be combined
     */
    private def humanOrder( elements: Array[String], n: Int, t: BigInt, m: Int ) {
      // total number of combinations with m elements
      val b = binomialCoefficientFactorial( n, m )
      // current count of combinations with m elements
      var q = BigInt( 0 )

      // Loop over all 2^n combinations, decrementing by one.
      var i = t - BigInt( 1 )
      while ( i >= BigInt( 0 ) && q != b ) {
        // Check whether this is a combination of m elements.
        if ( i.bitCount == m ) {
          q = q + 1
          val combination = new Array[String]( n )
          val empty = "-"
                    
          // For all n elements test whether element j is present.
          for ( j <- 0 until n )
          {
            // If present add it to the current combination in reverse order.  Reverse the
            // order to take advantage of the fact that less significant bits change faster when
            // incrementing or decrementing.
            if ( i.testBit( j ) )
              combination( n - 1 - j ) = elements( n - 1 - j )
            else
              combination( n - 1 - j ) = empty
          }

          // Process the current combination.
          listCombination( combination )
        }
        i = i - 1
      }
    }

    
    /** machine order for all m
      *
      * @param elements The set of elements
     * @param n The total number of elements
     * @param t The total number of nonrepeating combinations
     */
    private def machineOrder( elements: Array[String], n: Int, t: BigInt ) {
        // Iterate through all combinations of nonrepeating elements, incrementing by one.
      var i = BigInt( 0 )
      while ( i != t ) {
        val combination = new Array[String]( n )
        val empty = "-"
                    
        // For all n elements test whether element j is present.
        for ( j <- 0 until n ) {
          // If present add it to the current combination.
          if ( i.testBit( j ) )
            combination( j ) = elements( j )
          else
            combination( j ) = empty
        }

        // List the current combination.
        listCombination( combination )
        i = i + 1
      }
    }


    /** machine order for a specific m
      *
      * @param elements The set of elements
     * @param n The total number of elements
     * @param t The total number of nonrepeating combinations
     * @param m The number of elements to be combined
     */
    private def machineOrder( elements: Array[String], n: Int, t: BigInt, m: Int ) {
      // total number of combinations with m elements
      val b = binomialCoefficientFactorial( n, m )
      // current count of combinations with m elements
      var q = BigInt( 0 )

      // Iterate through all combinations of nonrepeating elements, incrementing by one.
      var i = BigInt( 0 )
      while ( i != t && q != b ) {
        if ( i.bitCount == m )
        {
          q = q + 1
          val combination = new Array[String]( n )
          val empty = "-"
                    
          // For all n elements test whether element j is present.
          for ( j <- 0 until n ) {
            // If present add it to the current combination.
            if ( i.testBit( j ) )
              combination( j ) = elements( j )
            else
              combination( j ) = empty
          }

          // List the current combination.
          listCombination( combination )
        }
        i = i + 1
      }
    }


  /** Binomial coefficient used for optimization with large n
    * @param n The total number of elements
    * @param m The number of elements to be combined
    */
  private def binomialCoefficient( n: Int, m: Int ): BigInt = {
    val b = new Array[BigInt]( n + 1 )
    b( 0 ) = 1
    for ( i <- 1 to n ) {
      b( i ) = 1
      for ( j <- i-1 until 0 by -1 )
        b( j ) =  b( j ) + b( j - 1 )
    }

    b( m )
  }

  /** Binomial coefficient used for optimization with large n using factorials
    * @param n The total number of elements
    * @param m The number of elements to be combined
    */
  private def binomialCoefficientFactorial( n: Int, m: Int ): BigInt =
    factorial( n ) / ( factorial( m ) * factorial( n - m ) )

  /** Factorial used for binomial coefficient
    *  @param n The number for which the facorial is calculated
    */
  private def factorial( n: Int ): BigInt =
    if ( n == 0 ) 1 else n * factorial( n - 1 )

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

