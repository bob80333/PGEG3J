package com.erice.PGEG3J.hexEditor

import com.erice.PGEG3JL.Rom
import tornadofx.*

/**
 * This Workspace is the main hex editor assembled of the different views
 */
class HexEditor(val rom: Rom) : Workspace("Hex Editor", NavigationMode.Tabs) {
     val editorController : HexEditorController by inject()

    init {
        borderpane {
            center {
                editorController.view.updateViewContent(rom)
                editorController.view
            }

            left {
                editorController.info
            }

            right {
                editorController.calculator
            }
        }
    }
}

/**
 * The view of the hex itself.  It handles marking changes as well as showing where in the file you are.
 * For example, it will have addresses like line numbers and an area showing selection start/end addresses.
 */
class HexView : View() {
    var viewContent = ByteArray(16)
    override val root = scrollpane {  }

    fun updateViewContent(rom: Rom) {
        viewContent = rom.rom
    }
}

/**
 * More for convenience rather than strictly needed, this is a calculator for hex, decimal and octal.
 * This functionality makes it easier to work with hex numbers.
 */
class HexCalculator: View() {
    override val root = vbox { }
}

/**
 * This view shows information about the selection that may be useful, like the selection in little endian,
 * value in decimal, to ROM pointer, etc.  The information should be copy/paste-able
 */
class HexInformation: View() {
    override val root = vbox {  }
}
