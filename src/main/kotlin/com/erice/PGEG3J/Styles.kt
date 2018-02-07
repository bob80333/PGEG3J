package com.erice.PGEG3J

import javafx.scene.paint.Color
import tornadofx.*

class Style : Stylesheet() {
    companion object {
        val wrapper by cssclass()
        val consola by cssclass()
        val bankButton by cssclass()
        val white = c("#ffffff")
    }

    init {


        consola {
            baseColor= Color.BLACK
            fontFamily = "Consolas"
            textFill = Color.LIGHTGRAY
        }

        bankButton {
            backgroundColor += white
            baseColor = Color.WHITE
            accentColor = white
            focusColor = white
        }
    }
}