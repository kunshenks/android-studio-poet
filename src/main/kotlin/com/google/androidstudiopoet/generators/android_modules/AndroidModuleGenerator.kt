package com.google.androidstudiopoet.generators.android_modules

import com.google.androidstudiopoet.generators.PackagesGenerator
import com.google.androidstudiopoet.generators.android_modules.resources.ImagesGenerator
import com.google.androidstudiopoet.generators.android_modules.resources.LayoutResourcesGenerator
import com.google.androidstudiopoet.generators.android_modules.resources.ResourcesGenerator
import com.google.androidstudiopoet.generators.android_modules.resources.StringResourcesGenerator
import com.google.androidstudiopoet.models.AndroidModuleBlueprint
import com.google.androidstudiopoet.utils.joinPath
import com.google.androidstudiopoet.writers.FileWriter

class AndroidModuleGenerator(private val stringResourcesGenerator: StringResourcesGenerator,
                             private val imageResourcesGenerator: ImagesGenerator,
                             private val layoutResourcesGenerator: LayoutResourcesGenerator,
                             private val resourcesGenerator: ResourcesGenerator,
                             private val packagesGenerator: PackagesGenerator,
                             private val activityGenerator: ActivityGenerator,
                             private val manifestGenerator: ManifestGenerator,
                             private val proguardGenerator: ProguardGenerator,
                             private val buildGradleGenerator: AndroidModuleBuildGradleGenerator,
                             private val fileWriter: FileWriter) {

    /**
     *  Generate android module, including module folder
     */
    fun generate(blueprint: AndroidModuleBlueprint) {
        generateMainFolders(blueprint)

        proguardGenerator.generate(blueprint)
        buildGradleGenerator.generate(blueprint)
        resourcesGenerator.generate(blueprint.resourcesBlueprint)
        packagesGenerator.writePackages(blueprint.packagesBlueprint)
        val methodsToCall: List<String> = listOf()
        activityGenerator.generate(blueprint, methodsToCall)
        manifestGenerator.generate(blueprint)
    }

    private fun generateMainFolders(blueprint: AndroidModuleBlueprint) {
        val moduleRoot = blueprint.moduleRoot
        fileWriter.mkdir(moduleRoot)
        fileWriter.mkdir(moduleRoot.joinPath("libs"))
        fileWriter.mkdir(blueprint.srcPath)
        fileWriter.mkdir(blueprint.mainPath)
        fileWriter.mkdir(blueprint.codePath)
        fileWriter.mkdir(blueprint.resDirPath)
    }
}