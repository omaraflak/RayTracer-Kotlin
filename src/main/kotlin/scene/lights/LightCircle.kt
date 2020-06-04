package scene.lights

import geometry.Point3F

class LightCircle(
    val center: Point3F,
    radius: Float,
    towards: Point3F,
    intensity: Float = 2f,
    ambient: Float = 0.01f
): Light(center, intensity, ambient) {
    private val positions = mutableListOf(center)

    init {
        val normal = (towards - center).normalized()
        val orthogonal = Point3F(0f, -normal.z, normal.y).normalized()
        for (i in 10 until 100 step 10) {
            for (j in 0 until 360 step 20) {
                positions.add(center + orthogonal.rotate(j.toFloat(), normal) * (i / 100f) * radius)
            }
        }
        this.intensity /= positions.size
    }

    override fun pointsVisibleTo(point: Point3F): List<Point3F> {
        return positions
    }
}
