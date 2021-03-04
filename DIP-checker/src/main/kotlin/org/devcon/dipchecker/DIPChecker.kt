package  org.devcon.dipchecker

import org.devcon.dipchecker.ParseState.*
import java.io.File
import java.lang.Exception
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor

enum class ParseState { START, HEADER, BODY }

fun checkMarkDown(file: File, prefix: String="eip") {
    if (file.name == "eip-20-token-standard.md") return
    println("processing ${file.name}")
    if (!file.name.startsWith("$prefix-")) throw MDMustStartWithDIPException()
    val dipNumberFromFile = file.nameWithoutExtension.removePrefix("$prefix-")
    if (dipNumberFromFile.toIntOrNull() == null) throw MDMustEndWithNUmber(dipNumberFromFile)

    var parseState = START
    val headers = mutableListOf<String>()

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
                else if (!allHeaders.contains(headerName)) throw InvalidHeaderException(s, file.name)
                else if (headerName == "Created") {
                    try {
                        val foo: TemporalAccessor = DateTimeFormatter
                            .ofPattern("yyyy-M-d").parse(headerKeyValueList.last())
                        LocalDate.from(foo)
                    } catch (e: Exception) {
                        throw InvalidDateException(s, file.name)
                    }
                    /*

                        dateFormat.apply {
                            isLenient = true
                        }.parse(headerKeyValueList.last())
                    } catch (e: Exception) {

                    }

                     */
                } else if (headerName == prefix) {
                    val dipNumberFromHeader = headerKeyValueList.last()
                    if (dipNumberFromHeader != dipNumberFromFile) throw DIPHeaderNumberDoesNotMatchFilename(dipNumberFromFile, dipNumberFromHeader)
                }

                headers.add(headerName)
            }


        }
    }

    if (!headers.containsAll(mandatoryHeaders)) throw MissingHeaderException(mandatoryHeaders.subtract(headers).joinToString(), file.name)

    if (parseState != BODY) throw HeaderNotClosed(file.name)

}

fun checkFolder(folder: File): String? {

    if (!folder.exists()) throw FolderMustExist(folder.absolutePath)

    var processed = 0
    folder.walk().filter { it != folder }.forEach {

        when {
            it.parentFile == File(folder, "images") ->
                if (!imageExtensions.contains(it.extension)) throw InvalidImageException(it.name)
            it.extension == "md" -> checkMarkDown(it)
            it.name == "images" -> if (!it.isDirectory) throw ImagesMustBeDirectory()
            else -> throw FoundExtraFileException(it.name)
        }
        processed++
    }
    return "Successfully checked $processed files"
}

