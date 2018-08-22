import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.output.TermUi
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import org.mapdb.DBMaker
import org.mapdb.Serializer


class Person(map: Map<String, Any?>) {
    val name: String by map
    val age: Int     by map
}

class Example {
    var user: List<Person> ?= null

    fun start() {
        user = listOf<Person>(
                Person(mapOf("name" to "John Doe", "age" to 25)),
                Person(mapOf("name" to "Peter Schulz", "age" to 45)),
                Person(mapOf("name" to "Steven Kobs", "age" to 39)))
        user?.forEach { println("${it.name} ${it.age}") }

        var a = user?.map { it.name }

        println(a)
        println("${a!!::class.qualifiedName}")

        val db = DBMaker.fileDB("example.db").fileMmapEnable().make()

        val map_name = db.hashMap<String, String>("example", Serializer.STRING, Serializer.STRING).createOrOpen()
        val map_age = db.hashMap<String, String>("example", Serializer.STRING, Serializer.STRING).createOrOpen()

        map_name["name"] = user!!.map { it.name }.toString()
        map_age["age"] = user!!.map { it.age }.toString()

        println("${map_name.get("name")} ${map_age.get("age")}")
        db.commit()
        db.close()
    }

    fun show() {
        val db = DBMaker.fileDB("example.db").closeOnJvmShutdown().readOnly().make()
        println("${db.hashMap("example").open().get("name")} ${db.hashMap("example").open().get("age")}")
    }
}

class StartConsole : CliktCommand() {
    val readIinfo: String by option(help = "Working with data").prompt("")

    override fun run() {
        if(readIinfo == "start") {
            Example().start()
        } else if(readIinfo == "show") {
            TermUi.echo("Reading...")
            Example().show()
        } else {
            println("Nothing to find")
        }
    }
}

fun main(args: Array<String>) {
    TermUi.echo("Hello it is reading data")
    StartConsole().main(args)
}