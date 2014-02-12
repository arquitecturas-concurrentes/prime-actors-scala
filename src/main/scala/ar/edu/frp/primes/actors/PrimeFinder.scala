package ar.edu.frp.primes.actors

import akka.actor.Actor
import akka.actor.ActorLogging

trait PrimeAlgorithm {
  def isPrime(currentPrimes: Seq[Long], candidate: Long): Boolean

}

object ListBasedAlgorithm extends PrimeAlgorithm {
  implicit class MyRichLong(value: Long) {
    def isDivisibleBy(x: Long) = value % x == 0
  }

  def isPrime(currentPrimes: Seq[Long], candidate: Long): Boolean = findDivisorUntilSquareRoot(currentPrimes, candidate).isEmpty

  def findDivisorUntilSquareRoot(currentPrimes: Seq[Long], candidate: Long) = 
    currentPrimes.toStream.takeWhile(x => x * x <= candidate).find(candidate.isDivisibleBy(_))
}

class PrimesFinder extends Actor with ActorLogging {

  def algorithm = ListBasedAlgorithm;

  def receive = {
    case m @ IsPrime(currentPrimes, candidate) => {
      val isPrime = algorithm.isPrime(currentPrimes, candidate)

      if (isPrime) sender ! PrimeFound(candidate) else sender ! NotPrime(candidate)
    }
  }
}