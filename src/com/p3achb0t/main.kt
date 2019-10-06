import com.p3achb0t.client.ui.GameWindow
import com.p3achb0t.client.ui.setup


object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        setup()
        val g = GameWindow()

        g.run()
    }
}
