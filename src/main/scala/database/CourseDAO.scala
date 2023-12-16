// CourseDAO.scala
package org.school.app.dao

import java.sql.{Connection, PreparedStatement, ResultSet}
import org.school.app.Course

import scala.collection.mutable.ListBuffer

object CourseDAO {
  def addCourse(course: Course): Unit = {
    val connection: Connection = Database.getConnection
    val query = "INSERT INTO courses (id, name) VALUES (?, ?)"

    try {
      val preparedStatement: PreparedStatement = connection.prepareStatement(query)
      preparedStatement.setInt(1, course.id)
      preparedStatement.setString(2, course.name)
      preparedStatement.executeUpdate()
    } finally {
      connection.close()
    }
  }

  def removeCourse(courseId: Int): Unit = {
    val connection: Connection = Database.getConnection
    val query = "DELETE FROM courses WHERE id = ?"

    try {
      val preparedStatement: PreparedStatement = connection.prepareStatement(query)
      preparedStatement.setInt(1, courseId)
      preparedStatement.executeUpdate()
    } finally {
      connection.close()
    }
  }

  def getAllCourses(): List[Course] = {
    val connection: Connection = Database.getConnection
    val query = "SELECT * FROM courses"
    var courses: ListBuffer[Course] = ListBuffer.empty
    try {
      val preparedStatement: PreparedStatement = connection.prepareStatement(query)
      val resultSet: ResultSet = preparedStatement.executeQuery()
      while (resultSet.next()) {
        val id = resultSet.getInt("id")
        val name = resultSet.getString("name")
        val course = Course(id, name)
        courses += course
      }
    } finally {
      connection.close()
    }
    courses.toList // Convert ListBuffer to immutable List
  }
}
