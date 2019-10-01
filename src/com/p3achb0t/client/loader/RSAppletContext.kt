package com.p3achb0t.client.loader

import java.applet.Applet
import java.applet.AppletContext
import java.applet.AudioClip
import java.awt.Desktop
import java.awt.Image
import java.io.IOException
import java.io.InputStream
import java.net.URISyntaxException
import java.net.URL
import java.util.*
import javax.imageio.ImageIO

public class RSAppletContext : AppletContext {

    private val streams = HashMap<String, InputStream>()
    private var applet: Applet? = null

    override fun getAudioClip(url: URL): AudioClip {
        // Use the Java Applet implementation of getting an AudioClip
        return Applet.newAudioClip(url)
    }

    override fun getImage(url: URL): Image {
        try {
            // Pretty standard stuff, as long as we return an Image we are good
            return ImageIO.read(url)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

    }

    override fun getApplet(name: String): Applet? {
        // Return the Runescape Applet
        return applet
    }

    override fun getApplets(): Enumeration<Applet> {
        // Create a Vector of Applets and add the Runescape one to it
        // We use a Vector because it's the easiest way to get an Enumeration<Applet>
        val applets = Vector<Applet>()
        applets.add(applet)
        return applets.elements()
    }

    override fun showDocument(url: URL) {
        // Make Java open up the requested url if it's supported
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(url.toURI())
            } catch (e: IOException) {
                throw RuntimeException("Unable to open document " + url.path)
            } catch (e: URISyntaxException) {
                throw RuntimeException("Unable to open document " + url.path)
            }

        }
    }

    override fun showDocument(url: URL, target: String) {
        // Make Java open up the requested url if it's supported
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(url.toURI())
            } catch (e: IOException) {
                throw RuntimeException("Unable to open document " + url.path)
            } catch (e: URISyntaxException) {
                throw RuntimeException("Unable to open document " + url.path)
            }

        }
    }

    override fun showStatus(status: String) {
        // We don't really need to do anything with the status
    }

    @Throws(IOException::class)
    override fun setStream(key: String, stream: InputStream) {
        // Basic implementation of a stream map that the AppletContext requires
        if (streams.containsKey(key)) {
            streams.remove(key)
        }
        streams[key] = stream
    }

    override fun getStream(key: String): InputStream {
        // Basic implementation of a stream map that the AppletContext requires
        return streams[key]!!
    }

    override fun getStreamKeys(): Iterator<String> {
        // Return the keys to the stream map
        return streams.keys.iterator()
    }

    fun setApplet(applet: Applet) {
        // Our one and only method we are going to add to this. It sets the Applet so we can return it and the Enumeration of Applets
        this.applet = applet
    }
}