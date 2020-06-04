package scene.lights

import geometry.Point3F

open class Light(
    val position: Point3F,
    var intensity: Float,
    val ambient: Float
) {
    open fun pointsVisibleTo(point: Point3F): List<Point3F> {
        throw NotImplementedError()
    }
}