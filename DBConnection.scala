
import java.sql.DriverManager
import java.sql.Connection

object DBConnection {
 // there's probably a better way to do this
    var connection:Connection = null
  def main(args: Array[String]) {
    
    createConnection()
    createBook(1001, "ZZZ", 200, "XYZ")
    getBook(1001)
    deleteBook(1001);
    connection.close
  }
  
  def createConnection()={
     // connect to the database named "mysql" on the localhost
    val driver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://localhost/book_store_db"
    val username = "root"
    val password = ""
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
    } catch {
      case e => e.printStackTrace
    }
    

    
  }
  
  def createBook(id : Int,title : String ,price : Int,author : String): Boolean = {
    val statement = connection.prepareStatement("INSERT INTO book VALUES (?, ?,?,?)")
    statement.setInt(1, id)
    statement.setString(2, title)
    statement.setInt(3, price)
    statement.setString(4, author)
    statement.executeUpdate()
    true
  }
  
  def getBook(id : Int)={
    var statement=connection.createStatement();
    var resultSet=statement.executeQuery(s"SELECT * FROM book WHERE id=$id");
    while(resultSet.next()){
      println(resultSet.getInt("id")+"  "+resultSet.getString("author"))
    }
  }
  
  def deleteBook(id : Int){
    var statement=connection.createStatement();
    var resultSet=statement.executeUpdate(s"DELETE FROM book WHERE id=$id")
    println("Record deleted "+resultSet)
   
  }
}