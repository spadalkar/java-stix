package org.mitre.stix

import org.mitre.Checksum

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.GradleException

import groovy.io.FileType

import org.mitre.stix.Checksum

import org.apache.tools.ant.taskdefs.condition.Os

/**
 * Gradle Task used to attempt to automatically retrieve the schemas
 * 
 */
class AddAISMarkingSupportTask extends DefaultTask {

	@Input String schemaVersion
	
	AddAISMarkingSupportTask() {
		description "Add support for AIS:AISMarkingStructure schemas."
	}
	
	@TaskAction
	def start() {

        def src_file_path = project.file("AIS_Bundle_Marking_1.1.1_v1.0.xsd")
        def dst_file_path = project.file("src/main/resources/schemas/v${schemaVersion}/extensions/marking/AIS_Bundle_Marking_1.1.1_v1.0.xsd")
        
        if (!src_file_path.exists()) {
            println "    ${src_file_path.getPath()} is not missing...continue."
            return true
        }

        if (dst_file_path.exists()) {
            println "    ${dst_file_path.getPath()} has already been copied."
            return true
        }
		ant.copy(tofile: dst_file_path.getPath(), file: src_file_path.getPath(), overwrite:true)
		println "    Add support for ${src_file_path.getPath()}"
	}
}
