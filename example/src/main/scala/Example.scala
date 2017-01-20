package example

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import scala.io.StdIn

import metarpheusdsl.MetarpheusDsl._
import metarpheusdsl.MarshallingSupport._

import io.circe.generic.auto._
import de.heikoseeberger.akkahttpcirce.CirceSupport._

import nozzle.monadicctrl.FutureCtrlFlow
import scalaz.std.scalaFuture._
import scalaz.syntax.monad._

import scala.concurrent.ExecutionContext.Implicits.global

object WebServer extends App {
  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  // implicit val executionContext = system.dispatcher

  val route =
    (get & path("hello") & parameters('name.as[String]) /**
      Says hello
      @param name the name
    */)(returns[Greeting].ctrl(greetingController.hello))

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done
}

object greetingController {
  def hello(name: String): FutureCtrlFlow[Greeting] = Greeting(name).point[FutureCtrlFlow]
}

case class Greeting(name: String)
