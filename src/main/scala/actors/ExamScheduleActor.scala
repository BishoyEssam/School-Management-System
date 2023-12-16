// ExamScheduleActor.scala
package org.school.app.actors

import akka.actor.Actor
import org.school.app.ExamSchedule
import org.school.app.dao.ExamScheduleDAO

class ExamScheduleActor extends Actor {
  //Create Empty List For ExamSchedule
  var examSchedules: List[ExamSchedule] = List.empty
  //Receive Method Specifies How The Actor Should React To Different Types Of Messages.
  def receive: Receive = {
    // Handel Message Called "AddExamSchedule"
    case AddExamSchedule(examSchedule) =>
      // This Line calling addExamSchedule Function To INSERT New ExamSchedule To exam_schedule Table
      ExamScheduleDAO.addExamSchedule(examSchedule)
      // Print This To Make Sure That The ExamSchedule is Added
      println(s"ExamSchedule ${examSchedule.courseId} Added Succesfuly ")
      // Add New ExamSchedule to the List "examSchedules"
      examSchedules = examSchedules :+ examSchedule
      // Finally, It Sends The Updated List Of examSchedules Back To The Sender
      sender() ! examSchedules



    // Handel Message Called "RemoveExamSchedule"
    case RemoveExamSchedule(courseId) =>
      // This Line calling removeExamSchedule Function To DELETE Specific ExamSchedule From exam_schedule Table
      ExamScheduleDAO.removeExamSchedule(courseId)
      // Print This To Make Sure That The ExamSchedule is Deleted
      println(s"ExamSchedule ${courseId} Deleted Succesfuly ")
      //Updates The Local examSchedules Variable By Filtering Out The Removed ExamSchedule
      examSchedules = examSchedules.filterNot(_.courseId == courseId)
      // Finally, It Sends The Updated List Of examSchedules Back To The Sender
      sender() ! examSchedules



    // Handel Message Called "GetAllExamSchedule"
    case GetAllExamSchedule =>
      // This Line calling getAllExamSchedule Function To SELECT * From exam_schedule Table
      val allExamSchedule = ExamScheduleDAO.getAllExamSchedule()
      for (i <- 0 until allExamSchedule.length) {
        println("---------------------------")
        println(s"Course_ID: ${allExamSchedule(i).courseId}")
        println(s"Date: ${allExamSchedule(i).date}")
        println(s"Time: ${allExamSchedule(i).time}")
        println("----------------------------")
      }
      sender() ! allExamSchedule

  }
}

case class AddExamSchedule(examSchedule: ExamSchedule)
case class RemoveExamSchedule(courseId: Int)
case object GetAllExamSchedule
