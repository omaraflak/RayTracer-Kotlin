package scene.lights

import geometry.Point3F

class LightDisc(
    val center: Point3F,
    radius: Float,
    towards: Point3F,
    diffuseIntensity: Point3F = Point3F(2f),
    ambientIntensity: Point3F = Point3F(0.4f),
    specularIntensity: Point3F = Point3F.ones()
): Light(center, diffuseIntensity, ambientIntensity, specularIntensity) {
    private val positions = mutableListOf(center)

    init {
        val normal = (towards - center).normalized()
        val orthogonal = Point3F(0f, -normal.z, normal.y).normalized()
        for (i in 10 until 100 step 10) {
            for (j in 0 until 360 step 20) {
                positions.add(center + orthogonal.rotate(j.toFloat(), normal) * (i / 100f) * radius)
            }
        }
        diffuseIntensity.set(diffuseIntensity * (1f / positions.size))
        ambientIntensity.set(ambientIntensity * (1f / positions.size))
        specularIntensity.set(specularIntensity * (1f / positions.size))
    }

    override fun pointsVisibleTo(point: Point3F): List<Point3F> {
        return positions
    }
}
