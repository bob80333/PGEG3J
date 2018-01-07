package com.erice.PGEG3J

import com.erice.PGEG3JL.Game
import javafx.beans.property.SimpleBooleanProperty
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.stage.FileChooser
import tornadofx.*
import java.io.File
import java.io.IOException
import java.io.OutputStream
import java.nio.charset.Charset
import java.util.*

class TextEditorFragment(val documentViewModel: DocumentViewModel) : Fragment() {
    override val root = pane {
        title = documentViewModel.title.value
        textarea(documentViewModel.text) {
            this.prefWidthProperty().bind(this@pane.widthProperty());
            this.prefHeightProperty().bind(this@pane.heightProperty());

        }
    }

    init {
        documentViewModel.title.addListener { w, o, n ->
            this.title = n
        }
    }

    override val deletable = SimpleBooleanProperty(false)
    override val closeable = SimpleBooleanProperty(true)
    override val savable = documentViewModel.dirty
    override val refreshable = documentViewModel.dirty

    override fun onSave() {
        documentViewModel.commit()
    }

    override fun onRefresh() {
        documentViewModel.rollback()
    }
}


class EmptyView : View() {
    val controller: MapController by inject()
    override val root = label(controller.quote())
}

class AboutFragment : Fragment() {
    val controller: MapController by inject()
    override val root = vbox {
        label("PGEG3J   -    by Eric Engelhart (bob80333)")
        label("")
        label("v0.0.1D-A")
    }

    init {
        title = "About"

        with(root) {
            setPrefSize(350.0, 80.0)
            setMaxSize(350.0, 80.0)
        }
    }
}

class SettingsFragment : Fragment() {
    val controller: MapController by inject()
    override val root = tabpane {
        tab("General") {
            isClosable = false
            label("YOU CANT CHANGE ANYTHING IM A DICTATOR ECKS DE")
        }
    }

    init {
        title = "Settings"

        with(root) {
            setMaxSize(800.0, 600.0)
        }
    }
}

class ProjectName : View("Name your Project") {
    override val root = form {
        fieldset("Project Names") {
            var projectName: TextField = TextField()
            var hasTouchedPath: Boolean = false
            field("Project Name") {
                projectName = textfield()
            }

            field("Project file name") {
                textfield()
            }
            field("Project Folder") {
                val textPath = textfield("${System.getProperty("user.home")}\\PGEG3J_Projects")
                textPath.setOnKeyReleased { hasTouchedPath = true }
                projectName.setOnKeyReleased{
                    if (!hasTouchedPath) {
                        textPath.text = "${System.getProperty("user.home")}\\PGEG3J_Projects\\${projectName.text}"
                    }
                }
                button("Browse...").action {
                    textPath.text = chooseDirectory("", File(textPath.text))?.absolutePath?.toString()
                }
            }

        }
    }
}

class ProjectRom : View("Choose your game") {
    override val root = form {

        fieldset("Enter Game Data") {
            field("Choose Rom File") {
                val textPath = textfield(System.getProperty("user.home"))
                button("Browse...").action {
                    val single = FileChooserMode.Single
                    val filters = arrayOf(FileChooser.ExtensionFilter("Rom files", "*.gba"))
                    val chosen = chooseFile(filters =  filters, mode = single)
                    if (chosen.isNotEmpty()) {
                        textPath.text = chosen[0].absolutePath
                    }
                }
            }
            field("Game Detection") {
                val check = checkbox("Automatically detect game")
                combobox(values = Game.values().toList()) {
                    disableWhen { check.selectedProperty() }

                }
            }
        }
    }
}

class CreateProjectWizard : Wizard() {
    init {
        with(root) {
            setPrefSize(800.0, 200.0)
            setMaxSize(800.0, 200.0)
        }
        add(ProjectName::class)
        add(ProjectRom::class)
    }
}

/**
 * TextAreaOutputStream
 *
 * Binds an output stream to a textarea
 */
class TextAreaOutputStream(val textArea: TextArea) : OutputStream() {

    /**
     * This doesn't support multibyte characters streams like utf8
     */
    @Throws(IOException::class)
    override fun write(b: Int) {
        throw UnsupportedOperationException()
    }

    /**
     * Supports multibyte characters by converting the array buffer to String
     */
    @Throws(IOException::class)
    override fun write(b: ByteArray, off: Int, len: Int) {
        // redirects data to the text area
        textArea.appendText(String(Arrays.copyOf(b, len), Charset.defaultCharset()))
        // scrolls the text area to the end of data
        textArea.scrollTop = java.lang.Double.MAX_VALUE
    }

}
