package org.school.app.actors
import akka.actor.{Actor, ActorRef, Props}
import org.school.app.Attendance
import org.school.app.dao.AttendanceDAO

class AttendanceActor  extends  Actor{
  override def receive: Receive = {
    case AddAttendance(attendance) =>
      println(s"Adding attendance for student ${attendance.student_id} for course ${attendance.course_id}")
      AttendanceDAO.addAttendance(attendance.student_id, attendance.course_id, attendance.date)
      sender() ! Attendance(attendance.course_id, attendance.date, attendance.student_id, 1)
    case GetAttendanceForStudent(studentId) =>
      println(s"Getting attendance for student $studentId")
      AttendanceDAO.getAllAttendanceForStudent(studentId)
      sender() ! Attendance(1, "2019-01-01", studentId, 1)
    case GetAttendanceForCourse(courseId) =>

      println(s"Getting attendance for course $courseId")
      AttendanceDAO.getAllAttendanceForCourse(courseId)
      sender() ! Attendance(courseId, "2019-01-01", 1, 1)
  }
}
case  class AddAttendance(attendance: Attendance)
case class GetAttendanceForStudent(studentId: Int)
case class GetAttendanceForCourse(courseId: Int)
