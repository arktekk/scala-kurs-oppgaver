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
    def sql(params:Any*):Sql = new Sql(sc.parts.mkString("?"), params.map(_.asInstanceOf[AnyRef]))
  }




  def tx[A](f:Session => A):A = {
    // tx start
    try {
      f(new Session{})
      // tx commit
    } catch {
      case ex =>
        //rollback
      throw ex
    }
  }

  class Sql(ps:String, params:Seq[AnyRef]){
    def update(implicit tx:Session):Unit = jdbc.update(ps, params :_*)
    def mapRow[A](f:ResultSet => A)(implicit tx:Session):List[A] =
      jdbc.query(ps, params.toArray, new RowMapper[A] {
        def mapRow(rs: ResultSet, rowNum: Int): A = f(rs)
      }).asScala.toList
  }
}
