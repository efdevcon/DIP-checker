package org.devcon.dipchecker

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class TheDIPChecker {

    @Test
    fun shouldPassForValidDIPFolder() {
        assertTrue(checkFolder(File(javaClass.getResource("/valid/DIPs").toURI())))
    }

    @Test
    fun shouldFailOnExtraFile() {
        assertFailsWith(FoundExtraFileException::class) {
            assertTrue(checkFolder(File(javaClass.getResource("/invalid/extraFile/DIPs").toURI())))
        }
    }

    @Test
    fun shouldFailOnInvalidImage() {
        assertFailsWith(InvalidImageException::class) {
            assertTrue(checkFolder(File(javaClass.getResource("/invalid/invalidImage/DIPs").toURI())))
        }
    }

    @Test
    fun shouldFailOnInvalidHeaderStart() {
        assertFailsWith(InvalidHeaderStart::class) {
            assertTrue(checkFolder(File(javaClass.getResource("/invalid/invalidHeaderStart/DIPs").toURI())))
        }
    }

    @Test
    fun shouldFailOnInvalidFirstHeader() {
        assertFailsWith(FirsHeaderMustBeDIPHeader::class) {
            assertTrue(checkFolder(File(javaClass.getResource("/invalid/firstHeaderMustBeDIPHeader/DIPs").toURI())))
        }
    }

    @Test
    fun shouldFailOnImagesNotDir() {
        assertFailsWith(ImagesMustBeDirectory::class) {
            assertTrue(checkFolder(File(javaClass.getResource("/invalid/imagesMustBeDirectory/DIPs").toURI())))
        }
    }

    @Test
    fun shouldFailOnInvalidMDFileName() {
        assertFailsWith(MDMustStartWithDIPException::class) {
            assertTrue(checkFolder(File(javaClass.getResource("/invalid/mdMustStartWithDIP/DIPs").toURI())))
        }
    }

    @Test
    fun shouldFailOnIMDNotEndingWithNumber() {
        assertFailsWith(MDMustEndWithNUmber::class) {
            assertTrue(checkFolder(File(javaClass.getResource("/invalid/mdMustEndWithNumber/DIPs").toURI())))
        }
    }

    @Test
    fun shouldFailOnDIPNumberMissMatch() {
        assertFailsWith(DIPHeaderNumberDoesNotMatchFilename::class) {
            assertTrue(checkFolder(File(javaClass.getResource("/invalid/dipNumbersMustMatch/DIPs").toURI())))
        }
    }

}