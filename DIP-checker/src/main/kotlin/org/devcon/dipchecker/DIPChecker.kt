package  org.devcon.dipchecker

import org.devcon.dipchecker.ParseState.*
import java.io.File
import java.lang.Exception
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor

enum class ParseState { START, HEADER, BODY }

interface XIPHeaders {
    val mandatoryHeaders: List<String>
    val optionalHeaders: List<String>
    fun allHeaders() = mandatoryHeaders + optionalHeaders
}

class DIPHeaders : XIPHeaders {
    override val mandatoryHeaders: List<String> = listOf("DIP", "Title", "Status", "Themes", "Tags", "Authors", "Discussion", "Created")
    override val optionalHeaders: List<String> = listOf("Resources Required", "Requires")
}

interface XIPDefinition {
    val prefix: String
    val headers: XIPHeaders
}

class DIPDefinition : XIPDefinition {
    override val prefix = "DIP"
    override val headers = DIPHeaders()
}

class EIPHeaders : XIPHeaders {
    override val mandatoryHeaders: List<String> = listOf("eip", "title", "author", "status", "type", "created")
    override val optionalHeaders: List<String> = listOf("requires", "discussions-to", "superseded-by", "category", "review-period-end", "replaces", "updated")
}

class EIPDefinition : XIPDefinition {
    override val prefix = "eip"
    override val headers = EIPHeaders()
}


fun checkMarkDown(file: File, definition: XIPDefinition) {
    if (file.name == "eip-20-token-standard.md") return

    println("processing ${file.name}")
    val prefix = definition.prefix
    if (!file.name.startsWith("$prefix-")) throw MDMustStartWithPrefixException(prefix, file.name)
    val dipNumberFromFile = file.nameWithoutExtension.removePrefix("$prefix-")
    if (dipNumberFromFile.toIntOrNull() == null) throw MDMustEndWithNUmber(dipNumberFromFile)

    var parseState = START
    val foundHeaders = mutableListOf<String>()

    file.readText().lines().forEach { s ->
        when (parseState) {
            START -> {
                if (s != "---")
                    throw InvalidHeaderStart(file.name)
                else
                    parseState = HEADER
            }
            HEADER -> {
                val headerKeyValueList = s.split(": ")
                val headerName = headerKeyValueList.first()

                if (s == "---") parseState = BODY
                else if (headerKeyValueList.size < 2) throw InvalidHeaderException(s, file.name)
                else if (!definition.headers.allHeaders().contains(headerName)) throw InvalidHeaderException(s, file.name)
                else if (headerName == "Created") {
                    try {
                        val foo: TemporalAccessor = DateTimeFormatter
                            .ofPattern("yyyy-M-d").parse(headerKeyValueList.last())
                        LocalDate.from(foo)
                    } catch (e: Exception) {
                        throw InvalidDateException(s, file.name)
                    }

                } else if (headerName == prefix) {
                    val dipNumberFromHeader = headerKeyValueList.last()
                    if (dipNumberFromHeader != dipNumberFromFile) throw DIPHeaderNumberDoesNotMatchFilename(dipNumberFromFile, dipNumberFromHeader)
                }

                foundHeaders.add(headerName)
            }

        }
    }

    if (!foundHeaders.containsAll(definition.headers.mandatoryHeaders)) throw MissingHeaderException(
        definition.headers.mandatoryHeaders.subtract(foundHeaders).joinToString(),
        file.name
    )

    if (parseState != BODY) throw HeaderNotClosed(file.name)

}

fun checkFolder(folder: File, xipDefinition: XIPDefinition): String {

    if (!folder.exists()) throw FolderMustExist(folder.absolutePath)

    var processed = 0
    folder.walk().filter { it != folder }.forEach {

        when {
            it.parentFile == File(folder, "images") ->
                if (!imageExtensions.contains(it.extension)) throw InvalidImageException(it.name)
            it.extension == "md" -> checkMarkDown(it, xipDefinition)
            it.name == "images" -> if (!it.isDirectory) throw ImagesMustBeDirectory()
            else -> throw FoundExtraFileException(it.name)
        }
        processed++
    }
    return "Successfully checked $processed files"
}

