import monix.eval.Task
import monix.execution.Scheduler

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Main extends App {

  val scheduler = Scheduler.io()

  def test = () => {
    Await.result((Task(println("...but this is never executed!")) onErrorHandleWith { case e: Exception => Task.raiseError(e) }).runAsync(scheduler), Duration.Inf)
  }

  Await.result((for {
    _ <- Task(println("This prints fine..."))
  } yield {
    test()
  }).runAsync(scheduler), Duration.Inf)
}
