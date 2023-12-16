// TeacherDAO.scala
package org.school.app.dao

import java.sql.{Connection, PreparedStatement, ResultSet}
import org.school.app.Teacher
import scala.collection.mutable.ListBuffer

object TeacherDAO {
  def addTeacher(teacher: Teacher): Unit = {
    val connection: Connection = Database.getConnection
    val query = "INSERT INTO teachers (id, name, subject) VALUES (?, ?, ?)"

    try {
      val preparedStatement: PreparedStatement = connection.prepareStatement(query)
      preparedStatement.setInt(1, teacher.id)
      preparedStatement.setString(2, teacher.name)
      preparedStatement.setString(3, teacher.subject)
      preparedStatement.executeUpdate()
    } finally {
      connection.close()
    }
  }

  def removeTeacher(teacherId: Int): Unit = {
    val connection: Connection = Database.getConnection
    val query = "DELETE FROM teachers WHERE id = ?"

    try {
      val preparedStatement: PreparedStatement = connection.prepareStatement(query)
      preparedStatement.setInt(1, teacherId)
      preparedStatement.executeUpdate()
    } finally {
      connection.close()
    }
  }

  def updateTeacher(teacher: Teacher): Unit = {
    val connection: Connection = Database.getConnection
    val query = "UPDATE teachers set name = ? , subject = ?  WHERE id = ?"

    try {
      val preparedStatement: PreparedStatement = connection.prepareStatement(query)
      preparedStatement.setString(1, teacher.name)
      preparedStatement.setString(2, teacher.subject)
      preparedStatement.setInt(3, teacher.id)
      preparedStatement.executeUpdate()
    } finally {
      connection.close()
    }
  }

  def getAllTeachers(): List[Teacher] = {
    val connection: Connection = Database.getConnection
    val query = "SELECT * FROM teachers"
    var teachers: ListBuffer[Teacher] = ListBuffer.empty
    try {
      val preparedStatement: PreparedStatement = connection.prepareStatement(query)
      val resultSet: ResultSet = preparedStatement.executeQuery()
      while (resultSet.next()) {
        val id = resultSet.getInt("id")
        val name = resultSet.getString("name")
        val subject = resultSet.getString("subject")

        val teacher = Teacher(id, name, subject)
        teachers += teacher
      }
    } finally {
      connection.close()
    }
    teachers.toList // Convert ListBuffer to immutable List
  }
}
