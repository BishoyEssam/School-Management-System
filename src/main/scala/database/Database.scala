// Database.scala
package org.school.app.dao

import java.sql.{Connection, DriverManager}

object Database {
  private val url = "jdbc:mysql://localhost/school_management"
  private val driver = "com.mysql.cj.jdbc.Driver"
  private val username = "root"
  private val password = ""

  // Establish a database connection
  def getConnection: Connection = {
    try {
      Class.forName(driver)
      val connection = DriverManager.getConnection(url, username, password)
      // Set auto-commit to false if needed
      // connection.setAutoCommit(false)
      connection
    } catch {
      case e: ClassNotFoundException =>
        throw new RuntimeException("Failed to load JDBC driver", e)
      case e: Exception =>
        throw new RuntimeException("Failed to establish a database connection", e)
    }
  }
}
