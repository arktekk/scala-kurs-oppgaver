package sql

import org.springframework.jdbc.core.{RowMapper, JdbcTemplate}
import java.sql.ResultSet
import collection.JavaConverters._

trait Session{
  def rollback = ???
  def commit = ???
}

class Jdbc(jdbc:JdbcTemplate) {

  implicit class SqlContext(sc:StringContext){
    def sql(params:Value*):Sql = new Sql(sc.parts.mkString("?"), params.map(_.value))
  }

  trait Result[A]{
    def get(name:String, rs:ResultSet):A
  }

  object Result{
    implicit object int extends Result[Int]{
      def get(name: String, rs: ResultSet): Int = rs.getInt(name)
    }
    implicit object string extends Result[String]{
      def get(name: String, rs: ResultSet): String = rs.getString(name)
    }
    implicit def option[A : Result]:Result[Option[A]] = new Result[Option[A]]{
      def get(name: String, rs: ResultSet): Option[A] = Option(implicitly[Result[A]].get(name, rs))
    }
//    implicit def option[A](implicit ra:Result[A]):Result[Option[A]] = new Result[Option[A]]{
//      def get(name: String, rs: ResultSet): Option[A] = Option(ra.get(name, rs))
//    }
  }

  implicit class RS(rs:ResultSet){
    def get[A : Result](name:String):A = implicitly[Result[A]].get(name, rs)
  }

  def tx[A](f:Session => A):A = {
    // tx start
    try {
      f(new Session{})
      // tx commit
    } catch {
      case ex:Throwable =>
        //rollback
      throw ex
    }
  }

  implicit def value[A:Param](a:A) = Value(implicitly[Param[A]].value(a))

  case class Value(value:AnyRef)

  trait Param[A]{
    def value(a:A):AnyRef
  }

  object Param {
    implicit object string extends Param[String]{
      def value(a: String): AnyRef = a
    }

    implicit object int extends Param[Int]{
      def value(a: Int): AnyRef = a.asInstanceOf[AnyRef]
    }

//    implicit object datetime extends Param[DateTime]{
//      def value(a: DateTime): AnyRef = ???
//    }
  }

  class Sql(ps:String, params:Seq[AnyRef]){
    def update(implicit tx:Session):Unit = jdbc.update(ps, params :_*)
    def mapRow[A](f:ResultSet => A)(implicit tx:Session):List[A] =
      jdbc.query(ps, params.toArray, new RowMapper[A] {
        def mapRow(rs: ResultSet, rowNum: Int): A = f(rs)
      }).asScala.toList
  }
}
