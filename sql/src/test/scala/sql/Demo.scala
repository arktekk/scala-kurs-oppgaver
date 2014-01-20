package sql

object Demo {

  def main(args:Array[String]) {

    import org.springframework.jdbc.core.JdbcTemplate
    import org.springframework.jdbc.datasource.DriverManagerDataSource

    Class.forName("org.h2.Driver")
    val app = new DemoApp(new Jdbc(new JdbcTemplate(new DriverManagerDataSource("jdbc:h2:mem:demo;DB_CLOSE_DELAY=-1"))))

    app.create
    app.insert("Mr A")
    app.insert("Mr B")
    app.insert("Mr C")

    app.list.foreach(println)
  }

}

case class User(id:Int, name:String)

class DemoApp(jdbc:Jdbc){
  import jdbc._

  def create = sql"create table user (id int primary key auto_increment, name varchar(50));".update

  def insert(name:String) = sql"insert into user (name) values($name);".update

  def list = sql"select id, name from user".mapRow((rs, i) => User(rs.getInt("id"), rs.getString("name")))
}
