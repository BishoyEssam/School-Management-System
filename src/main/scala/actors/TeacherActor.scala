// TeacherActor.scala
package org.school.app.actors

import akka.actor.Actor
import org.school.app.Teacher
import org.school.app.dao.TeacherDAO

class TeacherActor extends Actor {
  //Create Empty List For Teachers
  var teachers: List[Teacher] = List.empty
  //DAO == Data Access Object which used to access the DB Table For This Object
  val teacherDAO =  TeacherDAO
  //Receive Method Specifies How The Actor Should React To Different Types Of Messages.
  def receive: Receive = {
    // Handel Message Called "AddTeacher"
    case AddTeacher(teacher) =>
      // This Line calling addTeacher Function To INSERT New Teacher To Teachers Table
      TeacherDAO.addTeacher(teacher)
      // Print This To Make Sure That The Teacher is Added
      println(s"Teacher ${teacher.id} Added Succesfuly ")
      // Add New Teacher to the List "teachers"
      teachers = teachers :+ teacher
      // Finally, It Sends The Updated List Of Students Back To The Sender
      sender() ! teachers


    // Handel Message Called "RemoveTeacher"
    case RemoveTeacher(teacherId) =>
      // This Line calling removeTeacher Function To DELETE Specific Teacher From Teachers Table
      TeacherDAO.removeTeacher(teacherId)
      // Print This To Make Sure That The Teacher is Deleted
      println(s"Teacher ${teacherId} Deleted Succesfuly ")
      //Updates The Local teachers Variable By Filtering Out The Removed Student
      teachers = teachers.filterNot(_.id == teacherId)
      // Finally, It Sends The Updated List Of Students Back To The Sender
      sender() ! teachers


    // Handel Message Called "UpdateTeacher"
    case UpdateTeacher(teacher) =>
      // This Line calling UpdateTeacher Function To UPDATE Specific Teacher's Data In Teachers Table
      teacherDAO.updateTeacher(teacher)
      // Print This To Make Sure That The Teacher is Updated
      println(s"Teacher ${teacher.id} Updated Succesfuly ")
      // Assuming you want to update the local Teachers list as well
      teachers = teachers.map {
        case existingTeacher if existingTeacher.id == teacher.id => teacher
        case otherTeacher => otherTeacher
      }
      sender() ! teachers



    // Handel Message Called "GetAllTeachers"
    case GetAllTeachers =>
      // This Line calling GetAllTeachers Function To SELECT * From Students Table
      val allTeachers = teacherDAO.getAllTeachers()
      for (i <- 0 until allTeachers.length) {
        println("---------------------------")
        println(s"Teacher_ID: ${allTeachers(i).id}")
        println(s"Teacher_Name: ${allTeachers(i).name}")
        println(s"Teacher_Subject: ${allTeachers(i).subject}")
        println("----------------------------")
      }
      sender() ! allTeachers
  }


}

case class AddTeacher(teacher: Teacher)
case class RemoveTeacher(teacherId: Int)
case class UpdateTeacher(teacher: Teacher)
case object GetAllTeachers

