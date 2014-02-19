package ar.edu.frp.primes.actors

import org.scalatest.junit.AssertionsForJUnit
import org.junit.Test
import org.scalatest.junit.JUnitSuite
import org.scalatest.junit.ShouldMatchersForJUnit

class BruteForceAlgorithmTests extends JUnitSuite with AssertionsForJUnit {

  @Test
  def testAlgorithmDetectsPrime() {
    assert(BruteForceAlgorithm.isPrime(IsPrimeSimple(37)))
  }
  
  @Test
  def testAlgorithmDetectsNotPrime() {
    assert(!BruteForceAlgorithm.isPrime(IsPrimeSimple(25)))
  }

}