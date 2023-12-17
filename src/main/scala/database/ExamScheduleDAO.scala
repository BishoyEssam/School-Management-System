// ExamScheduleDAO.scala
package org.school.app.dao

import java.sql.{Connection, PreparedStatement, ResultSet}
import org.school.app.ExamSchedule

import scala.collection.mutable.ListBuffer

object ExamScheduleDAO {
  def addExamSchedule(examSchedule: ExamSchedule): Unit = {
    val connection: Connection = Database.getConnection
    val query = "INSERT INTO exam_schedules (course_id, date, time) VALUES (?, ?, ?)"

    try {
      val preparedStatement: PreparedStatement = connection.prepareStatement(query)
      preparedStatement.setInt(1, examSchedule.courseId)
      preparedStatement.setString(2, examSchedule.date)
      preparedStatement.setString(3, examSchedule.time)
      preparedStatement.executeUpdate()
      // Print This To Make Sure That The ExamSchedule is Added
      println(s"ExamSchedule ${examSchedule.courseId} Added Succesfuly ")
    } catch {
      case e: Exception =>
        println("This id Is Already Exists")
    } finally {
      connection.close()
    }
  }

  def removeExamSchedule(courseId: Int): Unit = {
    val connection: Connection = Database.getConnection
    val query = "DELETE FROM exam_schedules WHERE course_id = ?"

    try {
      val preparedStatement: PreparedStatement = connection.prepareStatement(query)
      preparedStatement.setInt(1, courseId)
      preparedStatement.executeUpdate()
      // Print This To Make Sure That The ExamSchedule is Deleted
      println(s"ExamSchedule ${courseId} Deleted Succesfuly ")
    } catch {
      case e: Exception =>
        println("This id Not Exists")
    }finally {
      connection.close()
    }
  }

  def getAllExamSchedule(): List[ExamSchedule] = {
    val connection: Connection = Database.getConnection
    val query = "SELECT * FROM exam_schedules"
    var examSchedules: ListBuffer[ExamSchedule] = ListBuffer.empty
    try {
      val preparedStatement: PreparedStatement = connection.prepareStatement(query)
      val resultSet: ResultSet = preparedStatement.executeQuery()
      while (resultSet.next()) {
        val id = resultSet.getInt("id")
        val date = resultSet.getString("date")
        val time = resultSet.getString("time")

        val examSchedule = ExamSchedule(id, date, time)
        examSchedules += examSchedule
      }
    } finally {
      connection.close()
    }
    examSchedules.toList // Convert ListBuffer to immutable List
  }
}
