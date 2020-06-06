package objects

import geometry.Point3F
import materials.Material
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

class Sphere(
    private val center: Point3F,
    private val radius: Float,
    material: Material
): Object(center, material) {
    override fun getNormalVectorSurface(point: Point3F): Point3F {
        return (point - center).normalized()
    }

    override fun intersect(origin: Point3F, direction: Point3F): Float? {
        val b = direction.dot((origin - center) * 2f)
        val c = (origin - center).length().pow(2) - radius.pow(2)
        val delta = b.pow(2) - 4 * c
        if (delta > 0) {
            val t1 = (-b + sqrt(delta)) / 2f
            val t2 = (-b - sqrt(delta)) / 2f
            val t = min(max(0f, t1), max(0f, t2))
            if (t > 0) {
                return t
            }
        }
        return null
    }
}
