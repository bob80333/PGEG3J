package com.erice.PGEG3J.project

import com.erice.PGEG3J.MapWorkspace
import com.erice.PGEG3J.ProjectController
import com.erice.PGEG3J.ProjectModel
import com.erice.PGEG3JL.Game
import javafx.scene.control.TextField
import javafx.stage.FileChooser
import tornadofx.*
import java.io.File
import java.util.logging.Level

class ProjectName : View("Name your Project") {
    val model: ProjectModel by inject()
    lateinit var name: Field
    override val root = form {
        fieldset("Project Names") {
            var projectName = TextField()
            var hasTouchedPath = false
            name = field("Project Name") {
                projectName = textfield()
                model.name.bind(projectName.textProperty())
            }

            field("Project file name") {
                val filename = textfield()
                model.filename.bind(filename.textProperty())
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
                model.absoluteFolderPath.bind(textPath.textProperty())
            }

        }
    }

    override fun onSave() {
        model.name.unbindBidirectional(name.textProperty)
        isComplete = model.commit()
    }
}

class ProjectRom : View("Choose your game") {
    val model: ProjectModel by inject()
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
                model.absoluteOriginalRomPath.bind(textPath.textProperty())
            }
            field("Game Detection") {
                val check = checkbox("Automatically detect game")
                val game = combobox(values = Game.values().toList()) {
                    disableWhen { check.selectedProperty() }

                }
                model.game.bind(game.valueProperty())
                check.setOnMouseClicked {
                    if(check.isSelected) {
                        model.game.unbind()
                        model.game.setValue(Game.AutoDetect)
                    } else {
                        model.game.bind(game.valueProperty())
                    }
                }
            }
        }
    }

    override fun onSave() {
        isComplete = model.commit()
    }
}

class CreateProjectWizard(val mapWorkspace: MapWorkspace) : Wizard() {
    val controller: ProjectController by inject()
    val model: ProjectModel by inject()
    init {
        with(root) {
            setPrefSize(800.0, 200.0)
            setMaxSize(800.0, 200.0)
        }
        add(ProjectName::class)
        add(ProjectRom::class)
        onComplete {
            val project = model.createItemFromProperties()
            log.log(Level.INFO, "Project Created Via Wizard")
            log.log(Level.FINEST, project.toString())
            mapWorkspace.changeProjectName(project.name)
            controller.project = project

            model.name.unbind()
            model.absoluteOriginalRomPath.unbind()
            model.absoluteFolderPath.unbind()
            model.filename.unbind()
            model.game.unbind()
        }
    }
}