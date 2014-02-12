package ar.edu.frp.primes.actors

import org.scalatest.junit.AssertionsForJUnit
import org.junit.Test
import org.scalatest.junit.JUnitSuite
import org.scalatest.junit.ShouldMatchersForJUnit

class PrimeAlgorithmTests extends JUnitSuite with ShouldMatchersForJUnit {

  val somePrimes = List[Long](2, 3, 5, 7, 11, 13, 17, 19)

  @Test
  def testDivisorsUntilSquareRootFound() {
    var divisor = ListBasedAlgorithm.findDivisorUntilSquareRoot(somePrimes, 70)

    assert(Some(2) === divisor)
    
    divisor = ListBasedAlgorithm.findDivisorUntilSquareRoot(somePrimes, 25)
    
    assert(Some(5) === divisor)
  }

  @Test
  def testDivisorsUntilSquareRootNotFound() {
    def divisor = ListBasedAlgorithm.findDivisorUntilSquareRoot(somePrimes, 71)

    assert(None === divisor)
  }

  @Test
  def testAlgorithmDetectsPrime() {
    assert(ListBasedAlgorithm.isPrime(somePrimes, 23))
  }
  
  @Test
  def testAlgorithmDetectsNotPrime() {
    assert(!ListBasedAlgorithm.isPrime(somePrimes, 25))
  }

}