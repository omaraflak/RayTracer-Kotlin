package scene.lights

import geometry.Point3F

class LightPoint(
    position: Point3F,
    intensity: Float = 1f,
    ambient: Float = 0.5f
): Light(position, intensity, ambient) {
    override fun pointsVisibleTo(point: Point3F): List<Point3F> {
        return listOf(position)
    }
}
