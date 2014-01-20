package sql

object Demo {

  def main(args:Array[String]) {

    import org.springframework.jdbc.core.JdbcTemplate
    import org.springframework.jdbc.datasource.DriverManagerDataSource

    Class.forName("org.h2.Driver")
    val jdbc = new Jdbc(new JdbcTemplate(new DriverManagerDataSource("jdbc:h2:mem:demo;DB_CLOSE_DELAY=-1")))
    val app = new DemoApp(jdbc)

    jdbc.tx{ implicit tx =>

    app.create
    app.insert("Mr A")
    app.insert("Mr B")
    app.insert("Mr C")

    }

    jdbc.tx{ implicit tx =>
      app.list.foreach(println)
    }


  }

}

case class User(id:Int, name:String)

class DemoApp(jdbc:Jdbc){
  import jdbc._

  def create(implicit s:Session) = sql"create table user (id int primary key auto_increment, name varchar(50));".update

  def insert(name:String)(implicit s:Session) = sql"insert into user (name) values($name);".update

  def list(implicit s:Session) = sql"select id, name from user".mapRow(rs => User(rs.getInt("id"), rs.getString("name")))
}
