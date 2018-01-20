package com.erice.PGEG3J

import javafx.beans.property.SimpleBooleanProperty
import tornadofx.*

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