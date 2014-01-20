package sql

import org.springframework.jdbc.core.{RowMapper, JdbcTemplate}
import java.sql.ResultSet
import collection.JavaConverters._

class Jdbc(jdbc:JdbcTemplate) {

  implicit class SqlContext(sc:StringContext){
    def sql(params:Any*):Sql = ???
  }

  class Sql(ps:String, params:Seq[Any]){
    def update:Unit = ???
    def mapRow[A](f:(ResultSet, Int) => A):List[A] = ???
  }
}
