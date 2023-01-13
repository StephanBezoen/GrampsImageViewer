package nl.acidcats.imageviewer.extensions

import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class StringExtensionsKtTest {

    @Test
    fun `test that extension is determined correctly`() {
        val filename = "myname.jpg"
        val extension = filename.fileExtension
        assertEquals("jpg", extension)
    }

    @Test
    fun `test that extension is empty when not present`() {
        val filename = "myname"
        val extension = filename.fileExtension
        assertEquals("", extension)
    }

    @Test
    fun `test that extension is determined correctly when filename contains multiple dots`() {
        val filename = "myname.test.jpg"
        val extension = filename.fileExtension
        assertEquals("jpg", extension)
    }
}