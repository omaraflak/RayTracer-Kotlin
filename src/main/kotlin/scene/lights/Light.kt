package scene.lights

import geometry.Point3F

open class Light(
    val position: Point3F,
    val diffuseIntensity: Point3F,
    val ambientIntensity: Point3F,
    val specularIntensity: Point3F
) {
    open fun pointsVisibleTo(point: Point3F): List<Point3F> {
        throw NotImplementedError()
    }
}