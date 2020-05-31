package geometry

import kotlin.math.sqrt

class Point3F(var x: Float = 0f, var y: Float = 0f, var z: Float = 0f) {
    constructor(p: Point3F) : this() {
        set(p)
    }

    fun set(p: Point3F) {
        x = p.x
        y = p.y
        z = p.z
    }

    fun set(x: Float = this.x, y: Float = this.y, z: Float = this.z) {
        this.x = x
        this.y = y
        this.z = z
    }

    operator fun plus(p: Point3F): Point3F {
        return Point3F(x + p.x, y + p.y, z + p.z)
    }

    operator fun unaryMinus(): Point3F {
        return Point3F(-x, -y, -z)
    }

    operator fun minus(p: Point3F): Point3F {
        return this + (-p)
    }

    operator fun times(f: Float): Point3F {
        return Point3F(x * f, y * f, z * f)
    }

    fun dot(p: Point3F): Float {
        return x * p.x + y * p.y + z * p.z
    }

    fun cross(p: Point3F): Point3F {
        return Point3F(
            y * p.z - z * p.y,
            z * p.x - x * p.z,
            x * p.y - y * p.x
        )
    }

    fun reflection(p: Point3F): Point3F {
        return 2 * this.dot(p) * p - this
    }

    fun length(): Float {
        return sqrt(x * x + y * y + z * z)
    }

    fun normalized(): Point3F {
         return this * (1f / length())
    }

    fun copy(): Point3F {
        return Point3F(this)
    }

    override fun toString(): String {
        return "($x, $y, $z)"
    }

    companion object {
        fun origin(): Point3F = Point3F()
        fun ones(): Point3F = Point3F(1f, 1f, 1f)
        fun xUnit(): Point3F = Point3F(1f, 0f, 0f)
        fun yUnit(): Point3F = Point3F(0f, 1f, 0f)
        fun zUnit(): Point3F = Point3F(0f, 0f, 1f)
    }
}
