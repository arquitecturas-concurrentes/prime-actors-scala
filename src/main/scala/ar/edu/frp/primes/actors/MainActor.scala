package ar.edu.frp.primes.actors;

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Props
import akka.actor.ActorLogging

case class IsPrime(currentPrimes: Seq[Long], candidate: Long)
case class PrimeFound(prime: Long)
case class NotPrime(notPrime: Long)
case object Statistics

class MainActor extends Actor with ActorLogging {
  val numberOfWorkers = 10
  val throughput = 2

  var workers: Seq[ActorRef] = Seq()
  var currentPrimes = List[Long](2, 3, 5, 7, 11, 13, 17, 19, 23)
  var currentCandidate = 25
  var startStamp: Long = 0

  override def preStart(): Unit = {
    workers = (1 to numberOfWorkers).map { i =>
      context.actorOf(Props[PrimesFinder], s"finder$i")
    }

    workers.foreach(sendPrimeCandidateTo(_))

    startStamp = System.currentTimeMillis

    context.system.scheduler.schedule(1.second, 1.second, context.self, Statistics)
  }

  override def receive = {
    case PrimeFound(prime) => {
      currentPrimes :+= prime
      sendPrimeCandidateTo(sender)
    }
    case NotPrime(_) => sendPrimeCandidateTo(sender)
    case Statistics => printStatistics
  }

  def sendPrimeCandidateTo(worker: ActorRef) {
    for (i <- (1 to throughput)) {
      worker ! IsPrime(currentPrimes, currentCandidate)
      currentCandidate += 2
    }
  }

  def printStatistics {
    val primesAmount = currentPrimes.size
    val elapsedSeconds = (System.currentTimeMillis() - startStamp).millis.toSeconds
    println(s"Primes found: $primesAmount")
    println(s"Last found: ${currentPrimes.last}")
    println(s"Current candidate: $currentCandidate")
    println(s"Elapsed time: $elapsedSeconds seconds")
    println(s"Primes/s: ${primesAmount / elapsedSeconds}")
    println(s"Memory consumption: ${Humanize.filesize(Runtime.getRuntime().totalMemory())}")
  }
}