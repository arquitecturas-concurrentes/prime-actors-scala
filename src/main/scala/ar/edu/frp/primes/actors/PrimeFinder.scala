package ar.edu.frp.primes.actors

import akka.actor.Actor
import akka.actor.ActorLogging
import PrimesFinder._

trait PrimeAlgorithm {
  implicit class MyRichLong(value: Long) {
    def isDivisibleBy(x: Long) = value % x == 0
  }

  def isPrime(m: IsPrimeMessage): Boolean
  def buildMessage(currentCandidate: Int, currentPrimes: List[Long]): IsPrimeMessage
  def findDivisorUntilSquareRoot(currentPrimes: Seq[Long], candidate: Long) =
    currentPrimes.toStream.takeWhile(x => x * x <= candidate).find(candidate.isDivisibleBy(_))
}

object ListBasedAlgorithm extends PrimeAlgorithm {

  def isPrime(m: IsPrimeMessage): Boolean = m match {
  	case IsPrime(currentPrimes, candidate) => findDivisorUntilSquareRoot(currentPrimes, candidate).isEmpty
  }

  def buildMessage(currentCandidate: Int, currentPrimes: List[Long]) = IsPrime(currentPrimes, currentCandidate)
}

object BruteForceAlgorithm extends PrimeAlgorithm {

  def isPrime(m: IsPrimeMessage): Boolean = m match {
  	case IsPrimeSimple(candidate) => findDivisorUntilSquareRoot((3l to candidate), candidate).isEmpty
  }

  def buildMessage(currentCandidate: Int, currentPrimes: List[Long]) = IsPrimeSimple(currentCandidate)
}

object PrimesFinder {
  val algorithm = BruteForceAlgorithm
}

class PrimesFinder extends Actor with ActorLogging {
  def receive = {
    case m: IsPrimeMessage => {
      val isPrime = algorithm.isPrime(m)
      if (isPrime) sender ! PrimeFound(m.candidate) else sender ! NotPrime(m.candidate)
    }
  }
}