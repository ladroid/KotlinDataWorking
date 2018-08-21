import ninja.sakib.pultusorm.annotations.PrimaryKey
import ninja.sakib.pultusorm.core.PultusORM
import java.util.*

class Student(
    @PrimaryKey
    var studentId: Int = 0,
    var name: String? = null,
    var department: String? = null,
    var cgpa: Double = 0.0,
    var dateOfBirth: Date? = null)

fun work() {
    val pultusORM = PultusORM("Student")
    val student = Student()
    student.name = "Sakib Sayem"
    student.department = "CSE"
    student.cgpa = 2.3
    student.cgpa = 2.3
    student.dateOfBirth = Date(1998, 5, 6)
    pultusORM.save(student)

    //pultusORM.delete(Student())

    val students = pultusORM.find(Student())
    for (it in students) {
        val student = it as Student
        println(student.studentId)
        println(student.name)
        println(student.department)
        println(student.cgpa)
        println(student.dateOfBirth)
        println()
    }
    pultusORM.close()
}

fun main(args: Array<String>) {
    work()
}