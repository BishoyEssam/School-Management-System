package org.school.app.dao

import java.sql.{Connection, PreparedStatement, ResultSet}

import org.school.app.Attendance
import scala.collection.mutable.ListBuffer
object AttendanceDAO {
  def addAttendance(studentId: Int, courseId: Int, date: String): Unit = {
    val connection: Connection = Database.getConnection
    val query = "INSERT INTO attendance (student_id, course_id, date) VALUES (?, ?, ?)"

    try {
      val preparedStatement: PreparedStatement = connection.prepareStatement(query)
      preparedStatement.setInt(1, studentId)
      preparedStatement.setInt(2, courseId)
      preparedStatement.setString(3, date)
      preparedStatement.executeUpdate()
      println(s"Attendance For Student : ${studentId} For Course : ${courseId} Added Succesfuly ")
    } catch {
      case e: Exception =>
        println("Student_id OR Course_id Is Not Exists")
    } finally {
      connection.close()
    }
  }
  //get all attendance for a student
  def getAllAttendanceForStudent(studentId: Int): List[Attendance] = {
    val connection: Connection = Database.getConnection
    val query = "SELECT * FROM attendance WHERE student_id = ?"
    var attendances: ListBuffer[Attendance] = ListBuffer.empty
    try {
      val preparedStatement: PreparedStatement = connection.prepareStatement(query)
      preparedStatement.setInt(1, studentId)
      val resultSet: ResultSet = preparedStatement.executeQuery()
      while (resultSet.next()) {
        val id = resultSet.getInt("id")
        val courseId = resultSet.getInt("course_id")
        val date = resultSet.getString("date")
        val attendance = Attendance(id,date, studentId, courseId)
        attendances += attendance
      }
    } finally {
      connection.close()
    }
    attendances.toList // Convert ListBuffer to immutable List
  }
  //get all attendance for a course
  def getAllAttendanceForCourse(courseId: Int): List[Attendance] = {
    val connection: Connection = Database.getConnection
    val query = "SELECT * FROM attendance WHERE course_id = ?"
    var attendances: ListBuffer[Attendance] = ListBuffer.empty
    try {
      val preparedStatement: PreparedStatement = connection.prepareStatement(query)
      preparedStatement.setInt(1, courseId)
      val resultSet: ResultSet = preparedStatement.executeQuery()
      while (resultSet.next()) {
        val id = resultSet.getInt("id")
        val studentId = resultSet.getInt("student_id")
        val date = resultSet.getString("date")
        val attendance = Attendance(id,  date,studentId, courseId)
        attendances += attendance
      }
    } finally {
      connection.close()
    }
    attendances.toList // Convert ListBuffer to immutable List
  }


}
