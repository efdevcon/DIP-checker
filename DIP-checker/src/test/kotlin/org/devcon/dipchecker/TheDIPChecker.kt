package org.devcon.dipchecker

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class TheDIPChecker {

    @Test
    fun shouldPassForValidDIPFolder() {
        assertThat(checkFolder(File(javaClass.getResource("/valid").toURI()))).startsWith("Successfully")
    }

    @Test
    fun shouldFailOnExtraFile() {
        assertFailsWith(FoundExtraFileException::class) {
            checkFolder(File(javaClass.getResource("/invalid/extraFile").toURI()))
        }
    }

    @Test
    fun shouldFailOnHeaderNotClosed() {
        assertFailsWith(HeaderNotClosed::class) {
            checkFolder(File(javaClass.getResource("/invalid/headerNotClosed").toURI()))
        }
    }

    @Test
    fun shouldFailOnInvalidImage() {
        assertFailsWith(InvalidImageException::class) {
            checkFolder(File(javaClass.getResource("/invalid/invalidImage").toURI()))
        }
    }


    @Test
    fun shouldFailOnInvalidHeaderStart() {
        assertFailsWith(InvalidHeaderStart::class) {
            checkFolder(File(javaClass.getResource("/invalid/invalidHeaderStart").toURI()))
        }
    }

    @Test
    fun shouldFailOnInvalidHeader() {
        assertFailsWith(InvalidHeaderStart::class) {
            checkFolder(File(javaClass.getResource("/invalid/invalidHeader").toURI()))
        }
    }


    @Test
    fun shouldFailOnMissingHeader() {
        assertFailsWith(InvalidHeaderStart::class) {
            checkFolder(File(javaClass.getResource("/invalid/missingHeader").toURI()))
        }
    }


    @Test
    fun shouldFailOnImagesNotDir() {
        assertFailsWith(ImagesMustBeDirectory::class) {
            checkFolder(File(javaClass.getResource("/invalid/imagesMustBeDirectory").toURI()))
        }
    }

    @Test
    fun shouldFailOnInvalidMDFileName() {
        assertFailsWith(MDMustStartWithDIPException::class) {
            checkFolder(File(javaClass.getResource("/invalid/mdMustStartWithDIP").toURI()))
        }
    }

    @Test
    fun shouldFailOnIMDNotEndingWithNumber() {
        assertFailsWith(MDMustEndWithNUmber::class) {
            checkFolder(File(javaClass.getResource("/invalid/mdMustEndWithNumber").toURI()))
        }
    }

    @Test
    fun shouldFailOnDIPNumberMissMatch() {
        assertFailsWith(DIPHeaderNumberDoesNotMatchFilename::class) {
            checkFolder(File(javaClass.getResource("/invalid/dipNumbersMustMatch").toURI()))
        }
    }

    @Test
    fun shouldFailForNonExistingPath() {
        assertFailsWith(FolderMustExist::class) {
            checkFolder(File("yolo"))
        }
    }

}