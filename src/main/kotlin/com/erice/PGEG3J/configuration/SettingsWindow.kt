package com.erice.PGEG3J.configuration

import com.erice.PGEG3J.MapWorkspace
import com.erice.PGEG3J.ProjectController
import com.erice.PGEG3J.ProjectModel
import javafx.scene.layout.Priority
import tornadofx.*

class SettingsFragment(val mapWorkspace: MapWorkspace) : Fragment() {
    val controller: ProjectController by inject()
    val projectModel: ProjectModel by inject()
    override val root = vbox {
        tabpane {
            tab("General") {
                isClosable = false
                label("YOU CANT CHANGE ANYTHING IM A DICTATOR ECKS DE")
            }
            tab("Project") {
                isClosable = false
                vbox {
                    label("Project Name: ")
                    textfield {
                        text = controller.project.name
                        projectModel.name.bind(text.toProperty())
                    }
                }
            }
        }

        hbox {
            pane {
              hgrow = Priority.ALWAYS
            }

            button("Ok").action {
                projectModel.commit()
                val project = projectModel.createItemFromProperties()
                println(project.name)
                println(projectModel.name.toString())
                mapWorkspace.changeProjectName(project.name)
                controller.project = project
                close()
            }

            button("Cancel").action { close() }
        }
    }

    init {
        title = "Settings"

        with(root) {
            setMaxSize(800.0, 600.0)
        }
    }
}