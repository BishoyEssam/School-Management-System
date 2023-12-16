// SchoolManagementSystem.scala
package org.school.app

import akka.actor.{ActorRef, ActorSystem, Props}
import org.school.app.actors._
import org.school.app.dao.Database
//import org.school.app.gui.SchoolManagementGUI

object SchoolManagementSystem extends App {
  // Create an Akka system
  val system = ActorSystem("SchoolManagementSystem")
  val database=  Database.getConnection
  println(database.toString)

  // Create database connection;
  // Create Akka actors
  val studentActor: ActorRef = system.actorOf(Props[StudentActor], "studentActor")
  val teacherActor: ActorRef = system.actorOf(Props[TeacherActor], "teacherActor")
  val courseActor: ActorRef = system.actorOf(Props[CourseActor], "courseActor")
  val examScheduleActor: ActorRef = system.actorOf(Props[ExamScheduleActor], "examScheduleActor")
  val attendanceActor: ActorRef = system.actorOf(Props[AttendanceActor], "attendanceActor")
  //test all actors

//  studentActor ! UpdateStudent(Student(3,"Bishoy" ,"A+"))
//  studentActor ! RemoveStudent(4)
//  studentActor ! AddStudent(Student(2, "Mary" , "A"))
//  studentActor ! AddStudent(Student(3, "Peter" , "B"))
//  studentActor ! AddStudent(Student(4, "Paul" , "C"))
//  studentActor ! AddStudent(Student(5, "Jane" , "D"))
//  studentActor ! AddStudent(Student(6, "Mark" , "F"))
//  studentActor ! AddStudent(Student(7, "Luke" , "A"))
//  studentActor ! AddStudent(Student(8, "Matthew" , "B"))
//  studentActor ! AddStudent(Student(9, "James" , "C"))
//  studentActor ! AddStudent(Student(10, "Simon" , "D"))

//  teacherActor ! UpdateTeacher(Teacher(1, "Mr. saied" , "Math"))
//  teacherActor ! GetAllTeachers
//  teacherActor ! AddTeacher(Teacher(2, "Mrs. Jones" , "English"))
//  teacherActor ! AddTeacher(Teacher(3, "Mr. Brown" , "Science"))
//  teacherActor ! AddTeacher(Teacher(4, "Mrs. Davis" , "History"))
//  teacherActor ! AddTeacher(Teacher(5, "Mr. Miller" , "Geography"))
//  teacherActor ! AddTeacher(Teacher(6, "Mrs. Wilson" , "Art"))
//  teacherActor ! AddTeacher(Teacher(7, "Mr. Moore" , "Music"))
//  teacherActor ! AddTeacher(Teacher(8, "Mrs. Taylor" , "Physical Education"))
//  teacherActor ! AddTeacher(Teacher(9, "Mr. Anderson" , "Computer Science"))
//  teacherActor ! AddTeacher(Teacher(10, "Mrs. Thomas" , "Home Economics"))

//  courseActor ! RemoveCourse(1)
//  courseActor ! GetAllCourses
//  courseActor ! AddCourse(Course(3, "Science" ))
//  courseActor ! AddCourse(Course(4, "History" ))
//  courseActor ! AddCourse(Course(5, "Geography" ))
//  courseActor ! AddCourse(Course(6, "Art" ))
//  courseActor ! AddCourse(Course(7, "Music" ))
//  courseActor ! AddCourse(Course(8, "Physical Education" ))
//  courseActor ! AddCourse(Course(9, "Computer Science" ))
//  courseActor ! AddCourse(Course(10, "Home Economics" ))


//    examScheduleActor ! RemoveExamSchedule(4)
//    examScheduleActor ! GetAllExamSchedule
//  examScheduleActor ! AddExamSchedule(ExamSchedule(2,"2024/1/4","10:30 AM"))
//  examScheduleActor ! AddExamSchedule(ExamSchedule(3,"2024/1/5","10:30 AM"))
//  examScheduleActor ! AddExamSchedule(ExamSchedule(4,"2024/1/6","10:30 AM"))
//  examScheduleActor ! AddExamSchedule(ExamSchedule(5,"2024/1/9","10:30 AM"))
//  examScheduleActor ! AddExamSchedule(ExamSchedule(6,"2024/1/10","10:30 AM"))
  attendanceActor ! AddAttendance(Attendance(1,"2024/1/4",1,1))
}
