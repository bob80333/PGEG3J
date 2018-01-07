package com.erice.PGEG3J

import javafx.scene.paint.Color
import tornadofx.Stylesheet
import tornadofx.Stylesheet.Companion.root
import tornadofx.Stylesheet.Companion.textArea
import tornadofx.cssclass
import tornadofx.px
import javax.swing.text.html.StyleSheet

/**
 * Created by miguelius on 04/09/2017.
 */
class Style : Stylesheet() {
    companion object {
        val wrapper by cssclass()
        val consola by cssclass()
    }

    init {


        textArea and consola {
            baseColor= Color.BLACK
            fontFamily = "Consolas"
            textFill = Color.LIGHTGRAY
        }
    }
}