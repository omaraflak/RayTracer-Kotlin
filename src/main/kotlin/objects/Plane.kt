package objects

import geometry.Point3F
import materials.Material
import kotlin.math.absoluteValue

class Plane(
    private val point: Point3F,
    private val normal: Point3F,
    material: Material
): Object(point, material) {
    override fun getNormalVectorSurface(point: Point3F): Point3F {
        return normal
    }

    override fun intersect(origin: Point3F, direction: Point3F): Float? {
        if (normal.dot(direction).absoluteValue < 1e-6) {
            return null
        }

        val t = normal.dot(point - origin) / normal.dot(direction)
        if (t > 1e-4) {
            return t
        }

        return null
    }
}
