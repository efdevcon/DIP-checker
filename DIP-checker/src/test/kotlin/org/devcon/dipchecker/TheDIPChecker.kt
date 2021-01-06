package org.devcon.dipchecker

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class TheDIPChecker {

    @Test
    fun shouldPassForValidDIPFolder() {
        assertThat(checkFolder(File(javaClass.getResource("/valid/DIPs").toURI()))).startsWith("Successfully")
    }

    @Test
    fun shouldFailOnExtraFile() {
        assertFailsWith(FoundExtraFileException::class) {
            checkFolder(File(javaClass.getResource("/invalid/extraFile/DIPs").toURI()))
        }
    }

    @Test
    fun shouldFailOnInvalidImage() {
        assertFailsWith(InvalidImageException::class) {
            checkFolder(File(javaClass.getResource("/invalid/invalidImage/DIPs").toURI()))
        }
    }

    @Test
    fun shouldFailOnInvalidHeaderStart() {
        assertFailsWith(InvalidHeaderStart::class) {
            checkFolder(File(javaClass.getResource("/invalid/invalidHeaderStart/DIPs").toURI()))
        }
    }

    @Test
    fun shouldFailOnInvalidFirstHeader() {
        assertFailsWith(FirsHeaderMustBeDIPHeader::class) {
            checkFolder(File(javaClass.getResource("/invalid/firstHeaderMustBeDIPHeader/DIPs").toURI()))
        }
    }

    @Test
    fun shouldFailOnImagesNotDir() {
        assertFailsWith(ImagesMustBeDirectory::class) {
            checkFolder(File(javaClass.getResource("/invalid/imagesMustBeDirectory/DIPs").toURI()))
        }
    }

    @Test
    fun shouldFailOnInvalidMDFileName() {
        assertFailsWith(MDMustStartWithDIPException::class) {
            checkFolder(File(javaClass.getResource("/invalid/mdMustStartWithDIP/DIPs").toURI()))
        }
    }

    @Test
    fun shouldFailOnIMDNotEndingWithNumber() {
        assertFailsWith(MDMustEndWithNUmber::class) {
            checkFolder(File(javaClass.getResource("/invalid/mdMustEndWithNumber/DIPs").toURI()))
        }
    }

    @Test
    fun shouldFailOnDIPNumberMissMatch() {
        assertFailsWith(DIPHeaderNumberDoesNotMatchFilename::class) {
            checkFolder(File(javaClass.getResource("/invalid/dipNumbersMustMatch/DIPs").toURI()))
        }
    }

    @Test
    fun shouldFailForNonExistingPath() {
        assertFailsWith(FolderMustExist::class) {
            checkFolder(File("yolo"))
        }
    }

}