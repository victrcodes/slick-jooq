import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import Tables.profile.api._

case class UserData(id: Int, name: String, address: String)

object App extends App {
	
	val db = slick.jdbc.JdbcBackend.Database.forURL(
		"jdbc:h2:mem:test1;DB_CLOSE_DELAY=-1;INIT=runscript from 'src/main/resources/create.sql'",
		driver="org.h2.Driver"
	)
	
	val user: Future[Option[UserData]] = db.run(Tables.User.map(u => (u.id, u.name, u.address)).result.map(_.headOption.map(UserData.tupled)))
	
	val result: Future[Unit] = user.map(u => u.map(println))
	
	Await.ready(result, Duration.Inf)
	
}