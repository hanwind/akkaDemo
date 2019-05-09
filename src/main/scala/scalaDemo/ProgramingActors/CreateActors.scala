package scalaDemo.ProgramingActors

import akka.actor.{ActorSystem, Props}

import scala.concurrent.Await
import scala.concurrent.duration._
import akka.pattern.ask
import akka.util.Timeout

object CreateActors extends App {
  val system = ActorSystem("samle")
  val depp = system.actorOf(Props[HollywoodActor])
  val hanks = system.actorOf(Props[HollywoodActor])
  depp ! Play("Wonka")
  hanks ! Play("Gump")
  depp ! Play("Wonka")
  hanks ! Play("Sparrow")
  println("Sent roles to play")
  implicit val timeout: Timeout = Timeout(2.seconds)
  val wonkaFuture = depp ? ReportCount("Wonka")
  val gumpFuture = hanks ? ReportCount("Gump")
  val sparrowFuture = hanks ? ReportCount("Sparrow")

  val wonkaCount = Await.result(wonkaFuture, timeout.duration)
  val gumpCount  =Await.result(gumpFuture, timeout.duration)
  val sparrowCount = Await.result(sparrowFuture, timeout.duration)

  println(s"Depp played Wonka $wonkaCount times")
  println(s"Hanks played gump $gumpCount times")
  println(s"Depp played Sparrow $sparrowCount times")
  val teminateFuture = system.terminate()
  Await.ready(teminateFuture, Duration.Inf)

}
