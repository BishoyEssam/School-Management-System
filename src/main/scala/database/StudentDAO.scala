// StudentDAO.scala
package org.school.app.dao

import org.school.app.Student
import scala.collection.mutable.ListBuffer
import java.sql.{Connection, PreparedStatement, ResultSet}


object StudentDAO {
  def addStudent(student: Student): Unit = {
    val connection: Connection = Database.getConnection
    val query = "INSERT INTO students (id, name, grade) VALUES (?, ?, ?)"

    try {
      val preparedStatement: PreparedStatement = connection.prepareStatement(query)
      preparedStatement.setInt(1, student.id)
      preparedStatement.setString(2, student.name)
      preparedStatement.setString(3, student.grade)
      preparedStatement.executeUpdate()
      // Print This To Make Sure That The Student is Added
      println(s"Student ${student.id} Added Succesfuly ")

    } catch {
      case e: Exception =>
        println("This id Is Already Exists")
    }
    finally {
      connection.close()
    }
  }

  def updateStudent(student: Student): Unit = {
    val connection: Connection = Database.getConnection
    val query = "UPDATE students set name = ? , grade = ?  WHERE id = ?"

    try {
      val preparedStatement: PreparedStatement = connection.prepareStatement(query)
      preparedStatement.setString(1, student.name)
      preparedStatement.setString(2, student.grade)
      preparedStatement.setInt(3, student.id)
      preparedStatement.executeUpdate()
      // Print This To Make Sure That The Student is Updated
      println(s"Student ${student.id} Updated Succesfuly ")
    } catch {
      case e: Exception =>
        println("This id Not Exists")
    }finally {
      connection.close()
    }
  }

  def getAllStudents(): List[Student]  = {
    val connection: Connection = Database.getConnection
    val query = "SELECT * FROM students"
    var students: ListBuffer[Student] = ListBuffer.empty
    try {
      val preparedStatement: PreparedStatement = connection.prepareStatement(query)
      val resultSet: ResultSet = preparedStatement.executeQuery()
      while (resultSet.next()) {
        val id = resultSet.getInt("id")
        val name = resultSet.getString("name")
        val grade = resultSet.getString("grade")

        val student = Student(id, name, grade)
        students += student
      }
    } finally {
      connection.close()
    }
    students.toList // Convert ListBuffer to immutable List
  }

  def removeStudent(studentId: Int): Unit = {
    val connection: Connection = Database.getConnection
    val query = "DELETE FROM students WHERE id = ?"

    try {
      val preparedStatement: PreparedStatement = connection.prepareStatement(query)
      preparedStatement.setInt(1, studentId)
      preparedStatement.executeUpdate()
      // Print This To Make Sure That The Student is Deleted
      println(s"Student ${studentId} Deleted Succesfuly ")
    } catch {
      case e: Exception =>
        println("This id Not Exists")
    }finally {
      connection.close()
    }
  }
}
