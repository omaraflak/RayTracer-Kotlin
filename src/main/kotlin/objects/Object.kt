package objects

import geometry.Point3F
import materials.Material
import scene.Scene

open class Object(val position: Point3F, private val material: Material) {
    open fun intersect(origin: Point3F, direction: Point3F): Float? {
        throw NotImplementedError()
    }

    open fun getNormalVectorSurface(point: Point3F): Point3F {
        throw NotImplementedError()
    }

    fun getLightColor(origin: Point3F, direction: Point3F, distance: Float, scene: Scene): Point3F {
        val intersection = origin + direction * distance
        val toCameraUnit = -direction
        val normalToSurface = getNormalVectorSurface(intersection)
        var color = Point3F()

        scene.lights.forEach { light ->
            light.pointsVisibleTo(intersection).forEach { lightPoint ->
                val toLightUnit = (lightPoint - intersection).normalized()
                if (!scene.objects.any { it.intersect(intersection, toLightUnit) != null }) {
                    color += material.getColor(intersection, normalToSurface, toLightUnit, toCameraUnit, light)
                }
            }
        }

        return color.apply {
            x = x.coerceIn(0f, 1f)
            y = y.coerceIn(0f, 1f)
            z = z.coerceIn(0f, 1f)
        }
    }
}
