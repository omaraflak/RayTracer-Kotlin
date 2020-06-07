package scene.lights

import geometry.Point3F

class LightPoint(
    position: Point3F,
    diffuseIntensity: Point3F = Point3F(1.5f),
    ambientIntensity: Point3F = Point3F(0.5f),
    specularIntensity: Point3F = Point3F.ones()
): Light(position, diffuseIntensity, ambientIntensity, specularIntensity) {
    override fun pointsVisibleTo(point: Point3F): List<Point3F> {
        return listOf(position)
    }
}
