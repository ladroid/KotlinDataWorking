import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.output.TermUi
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import krangl.*
import java.util.*

fun static() {
    val user: DataFrame = dataFrameOf("id", "name", "job", "salary")(
            1, "Jhon", "Programmer", 2500,
            2, "Anna", "Manager", 2000,
            3, "Steve", "CEO", 4000)

    user.print()

    //sort by
    user.sortedBy("salary").print()

    user.sortedByDescending("salary").print()

    //select somth
    user.select("name").print()

    //count salary
    user.count("salary", "name").print()

    //statistics of salary
    user.summarize("mean_salary") { it["salary"].mean(true) }.print()

    //min and max salary
    user.summarize("min_salary") { it["salary"].min(true) }.print()
    user.summarize("max_salary") { it["salary"].max(true) }.print()

    println("It is $user")
}

data class User(val id : Int, val Name : String, val LastName : String,
                 val Date : Date, val Job : String, val Salary : Int)

class Info {
    var listOfUsers: MutableList<User> ?= null
    var list: MutableList<User> ?= null

    var users: DataFrame ?= null
    fun list() {
        listOfUsers = mutableListOf(
                User(1, "Jhon", "Lutkovski", Date(1984, 7, 4), "Programmer", 2500),
                User(2, "Anna", "Brain", Date(1990, 4, 25), "Manager", 2000),
                User(3, "Steven", "Burk", Date(1974, 3, 20), "CEO", 4000),
                User(4, "Andrey", "Karn", Date(1989, 5, 10), "Programmer", 2500))
        users = listOfUsers?.asDataFrame()
        users?.print()

        users?.sortedBy("Salary")?.print()

        users?.select("Job")?.groupBy("Job")?.print()

        users?.select("Name", "Salary")?.filter { it["Salary"] gt 2000 }?.print()
        users?.select("Salary")?.filterByRow { it["Salary"] as Int > 2000 }?.print()
    }

    fun add_to_list(id : Int, Name : String, LastName : String,
                    Date : Date, Job : String, Salary : Int) {
        listOfUsers?.add(User(id, Name, LastName, Date, Job, Salary))
        if(listOfUsers?.add(User(id, Name, LastName, Date, Job, Salary)) == true) {
            println("Added")
        } else
            println("Error")
        users = listOfUsers?.asDataFrame()
        users?.print()
    }

    fun ex() {
        list = mutableListOf(User(1, "Jhon", "Lutkovski", Date(1984, 7, 4), "Programmer", 2500))
    }
    fun w(id : Int, name: String, lastName: String, Date: Date, Job: String, Salary: Int) {
        list?.add(User(id, name, lastName, Date, Job, Salary))
        if(list?.add(User(id, name, lastName, Date, Job, Salary)) == true) {
            println("Added")
        } else {
            println("Error")
        }
        println(list)
    }
    fun show() {
        ex()
        w(2, "KK", "KK", Date(12,12,12), "KK", 12)
    }
}

fun readCVS() {
    val info = DataFrame.readCSV("/Users/lado/Documents/Java/Work/KotlinDataWorkingKrangl/annual-balance-sheet-2007-16-provisional-csv-tables.csv")
    info.select("Institutional_sector_name", "Institutional_sector_code",
            "Year", "Status", "Data_value").filter {
        (it["Year"] gt 2015) AND
                (it["Institutional_sector_code"] eq 27) AND (it["Status"] eq "FINAL")
    }.print()
}

class Start_From_Console : CliktCommand() {
    val readIinfo: String by option(help = "Read list").prompt("Read info")

    override fun run() {
        if(readIinfo == "static") {
            TermUi.echo("Reading...")
            static()
        } else if(readIinfo == "list") {
            Info().list()
        } else if(readIinfo == "readCVS") {
            readCVS()
        } else if(readIinfo == "add") {
            Info().add_to_list(5, "Jhon", "Nesh", Date(1950, 4, 6), "Mathamatic", 1000)
        }
        else
            println("Nothing to find")
    }
}

fun main(args: Array<String>) {
//    Info().ex()
//    Info().show()
    TermUi.echo("Hello it is reading data")
    Start_From_Console().main(args)
}