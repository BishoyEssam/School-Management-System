// StudentActor.scala
package org.school.app.actors
import akka.actor.Actor
import org.school.app.Student
import org.school.app.dao.StudentDAO


class StudentActor extends Actor {
  //Create Empty List For Students
  var students: List[Student] = List.empty
  //DAO == Data Access Object which used to access the DB Table For This Object
  val studentDAO =  StudentDAO
  //Receive Method Specifies How The Actor Should React To Different Types Of Messages.
  def receive: Receive = {



    // Handel Message Called "AddStudent"
    case AddStudent(student) =>
      // This Line calling addStudent Function To INSERT New Student To Students Table
      studentDAO.addStudent(  student)
      // Print This To Make Sure That The Student is Added
      println(s"Student ${student.id} Added Succesfuly ")
      // Add New Student to the List "students"
      students = students :+ student
      // Finally, It Sends The Updated List Of Students Back To The Sender
      sender() ! students



    // Handel Message Called "RemoveStudent"
    case RemoveStudent(studentId) =>
      // This Line calling removeStudent Function To DELETE Specific Student From Students Table
      studentDAO.removeStudent(studentId)
      // Print This To Make Sure That The Student is Deleted
      println(s"Student ${studentId} Deleted Succesfuly ")
      //Updates The Local Students Variable By Filtering Out The Removed Student
      students = students.filterNot(_.id == studentId)
      // Finally, It Sends The Updated List Of Students Back To The Sender
      sender() ! students



    // Handel Message Called "UpdateStudent"
    case UpdateStudent(student) =>
      // This Line calling updateStudent Function To UPDATE Specific Student's Data In Students Table
      studentDAO.updateStudent(student)
      // Print This To Make Sure That The Student is Updated
      println(s"Student ${student.id} Updated Succesfuly ")
      // Assuming you want to update the local students list as well
      students = students.map {
        case existingStudent if existingStudent.id == student.id => student
        case otherStudent => otherStudent
      }
      sender() ! students



    // Handel Message Called "GetAllStudents"
    case GetAllStudents =>
      // This Line calling getAllStudents Function To SELECT * From Students Table
      val allStudents = studentDAO.getAllStudents()
      for (i <- 0 until allStudents.length) {
        println("---------------------------")
        println(s"Student_ID: ${allStudents(i).id}")
        println(s"Student_Name: ${allStudents(i).name}")
        println(s"Student_Grade: ${allStudents(i).grade}")
        println("----------------------------")
      }
      sender() ! allStudents


  }
}
// Declare Classes With Specific Parameters
case class AddStudent(student: Student)
case class RemoveStudent(studentId: Int)
case class UpdateStudent(student: Student)
case object GetAllStudents
