package materials

import geometry.Point3F
import geometry.times
import scene.lights.Light

class CheckeredMaterial(
    private val uniformMaterial1: UniformMaterial = UniformMaterial(Point3F()),
    private val uniformMaterial2: UniformMaterial = UniformMaterial(Point3F.ones() * 0.6f),
    private val spaceNormal: Point3F = Point3F(0f, 1f, 0f),
    private val squareSize: Float = 0.2f
): Material {
    private val u = Point3F()
    private val v = Point3F()

    init {
        spaceNormal.set(spaceNormal.normalized())
        u.set(Point3F(0f, spaceNormal.z, -spaceNormal.y).normalized())
        v.set(u.cross(spaceNormal).normalized())
    }

    override fun getColor(point: Point3F, normal: Point3F, toLight: Point3F, toCamera: Point3F, light: Light): Point3F {
        val projected = point - (point - this.spaceNormal).dot(this.spaceNormal) * this.spaceNormal
        val distance = (point - projected).length()
        projected.set(projected + distance * u)

        val tmp = (projected - this.spaceNormal)
        val a = tmp.dot(u)
        val b = tmp.dot(v)

        val i = (a / squareSize).run { if (this < 0) this - 1 else this }.toInt()
        val j = (b / squareSize).run { if (this < 0) this - 1 else this }.toInt()

        if ((i + j % 2) % 2 == 0) {
            return uniformMaterial1.getColor(point, normal, toLight, toCamera, light)
        }
        return uniformMaterial2.getColor(point, normal, toLight, toCamera, light)
    }
}
