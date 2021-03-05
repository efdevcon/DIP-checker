package org.devcon.dipchecker

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertFailsWith

class TheDIPChecker {

    @Test
    fun shouldPassForValidDIPFolder() {
        assertThat(checkFolder(File(javaClass.getResource("/valid").toURI()), DIPDefinition())).startsWith("Successfully")
    }

    @Test
    fun shouldFailOnExtraFile() {
        assertFailsWith(FoundExtraFileException::class) {
            checkFolder(File(javaClass.getResource("/invalid/extraFile").toURI()), DIPDefinition())
        }
    }

    @Test
    fun shouldFailOnHeaderNotClosed() {
        assertFailsWith(HeaderNotClosed::class) {
            checkFolder(File(javaClass.getResource("/invalid/headerNotClosed").toURI()), DIPDefinition())
        }
    }

    @Test
    fun shouldFailOnInvalidImage() {
        assertFailsWith(InvalidImageException::class) {
            checkFolder(File(javaClass.getResource("/invalid/invalidImage").toURI()), DIPDefinition())
        }
    }


    @Test
    fun shouldFailOnInvalidHeaderStart() {
        assertFailsWith(InvalidHeaderStart::class) {
            checkFolder(File(javaClass.getResource("/invalid/invalidHeaderStart").toURI()), DIPDefinition())
        }
    }

    @Test
    fun shouldFailOnInvalidHeader() {
        assertFailsWith(InvalidHeaderStart::class) {
            checkFolder(File(javaClass.getResource("/invalid/invalidHeader").toURI()), DIPDefinition())
        }
    }


    @Test
    fun shouldFailOnMissingHeader() {
        assertFailsWith(InvalidHeaderStart::class) {
            checkFolder(File(javaClass.getResource("/invalid/missingHeader").toURI()), DIPDefinition())
        }
    }


    @Test
    fun shouldFailOnImagesNotDir() {
        assertFailsWith(ImagesMustBeDirectory::class) {
            checkFolder(File(javaClass.getResource("/invalid/imagesMustBeDirectory").toURI()), DIPDefinition())
        }
    }


    @Test
    fun shouldFailOnInvalidDate() {
        assertFailsWith(InvalidDateException::class) {
            checkFolder(File(javaClass.getResource("/invalid/invalidDate").toURI()), DIPDefinition())
        }
    }

    @Test
    fun shouldFailOnInvalidMDFileName() {
        assertFailsWith(MDMustStartWithPrefixException::class) {
            checkFolder(File(javaClass.getResource("/invalid/mdMustStartWithDIP").toURI()), DIPDefinition())
        }
    }

    @Test
    fun shouldFailOnIMDNotEndingWithNumber() {
        assertFailsWith(MDMustEndWithNUmber::class) {
            checkFolder(File(javaClass.getResource("/invalid/mdMustEndWithNumber").toURI()), DIPDefinition())
        }
    }

    @Test
    fun shouldFailOnDIPNumberMissMatch() {
        assertFailsWith(DIPHeaderNumberDoesNotMatchFilename::class) {
            checkFolder(File(javaClass.getResource("/invalid/dipNumbersMustMatch").toURI()), DIPDefinition())
        }
    }

    @Test
    fun shouldFailForNonExistingPath() {
        assertFailsWith(FolderMustExist::class) {
            checkFolder(File("yolo"), DIPDefinition())
        }
    }

}