// CourseActor.scala
package org.school.app.actors

import akka.actor.Actor
import org.school.app.Course
import org.school.app.dao.CourseDAO

class CourseActor extends Actor {
  //Create Empty List For Courses
  var courses: List[Course] = List.empty
  //Receive Method Specifies How The Actor Should React To Different Types Of Messages.
  def receive: Receive = {
    // Handel Message Called "AddCourse"
    case AddCourse(course) =>
      // This Line calling addCourse Function To INSERT New Course To Courses Table
      CourseDAO.addCourse(course)
      // Print This To Make Sure That The Courses is Added
      println(s"Course ${course.id} Added Succesfuly ")
      // Add New Course to the List "courses"
      courses = courses :+ course
      // Finally, It Sends The Updated List Of Courses Back To The Sender
      sender() ! courses



    // Handel Message Called "RemoveCourse"
    case RemoveCourse(courseId) =>
      // This Line calling removeCourse Function To DELETE Specific Course From Courses Table
      CourseDAO.removeCourse(courseId)
      // Print This To Make Sure That The Course is Deleted
      println(s"Course ${courseId} Deleted Succesfuly ")
      //Updates The Local Courses Variable By Filtering Out The Removed Course
      courses = courses.filterNot(_.id == courseId)
      // Finally, It Sends The Updated List Of Courses Back To The Sender
      sender() ! courses



    // Handel Message Called "GetAllCourses"
    case GetAllCourses =>
      // This Line calling getAllCourses Function To SELECT * From Courses Table
      val allCourses = CourseDAO.getAllCourses()
      for (i <- 0 until allCourses.length) {
        println("---------------------------")
        println(s"Course_ID: ${allCourses(i).id}")
        println(s"Course_Name: ${allCourses(i).name}")
        println("----------------------------")
      }
      sender() ! allCourses
  }
}

case class AddCourse(course: Course)
case class RemoveCourse(courseId: Int)
case class UpdateCourse(course: Course)
case object GetAllCourses