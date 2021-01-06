package  org.devcon.dipchecker

import java.io.File

fun checkMarkDown(it: File) {
    if (!it.name.startsWith("DIP-")) throw MDMustStartWithDIPException()
    val dipNumberFromFile = it.nameWithoutExtension.removePrefix("DIP-")
    if (dipNumberFromFile.toIntOrNull()==null) throw MDMustEndWithNUmber(dipNumberFromFile)

    it.readText().lines().forEachIndexed { index, s ->
        when (index) {
            0 -> if (s != "---") throw InvalidHeaderStart(it.name)
            1 -> {
                if (!s.startsWith("DIP: ")) throw FirsHeaderMustBeDIPHeader()
                val dipNumberFromHeader = s.removePrefix("DIP: ")
                if (dipNumberFromHeader != dipNumberFromFile) throw DIPHeaderNumberDoesNotMatchFilename(dipNumberFromFile,dipNumberFromHeader)
            }
        }
    }
}

fun checkFolder(folder: File): Boolean {

    folder.walk().filter { it != folder }.forEach {

        when {
            it.parentFile == File(folder, "images") ->
                if (!imageExtensions.contains(it.extension)) throw InvalidImageException(it.name)
            it.extension == "md" -> checkMarkDown(it)
            it.name == "images" -> if (!it.isDirectory) throw ImagesMustBeDirectory()
            else -> throw FoundExtraFileException(it.name)
        }

    }
    return true
}

