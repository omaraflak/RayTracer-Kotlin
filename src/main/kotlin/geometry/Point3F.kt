package geometry

import kotlin.math.*

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

    operator fun times(p: Point3F): Point3F {
        return Point3F(x * p.x, y * p.y, z * p.z)
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
        return this - 2 * this.dot(p) * p
    }

    fun length(): Float {
        return sqrt(x * x + y * y + z * z)
    }

    fun normalized(): Point3F {
         return this * (1f / length())
    }

    fun rotate(angle: Float, axis: Point3F): Point3F {
        val r = angle * PI / 180
        val cosR = cos(r)
        val sinR = sin(r)
        return Point3F(
            ((cosR + axis.x.pow(2) * (1 - cosR)) * x + (axis.x * axis.y * (1 - cosR) - axis.z * sinR) * y + (axis.x * axis.z * (1 - cosR) + axis.y * sinR) * z).toFloat(),
            ((axis.y * axis.x * (1 - cosR) + axis.z * sinR) * x + (cosR + axis.y.pow(2) * (1 - cosR)) * y + (axis.y * axis.z * (1 - cosR) - axis.x * sinR) * z).toFloat(),
            ((axis.z * axis.x * (1 - cosR) - axis.y * sinR) * x + (axis.z * axis.y * (1 - cosR) + axis.x * sinR) * y + (cosR + axis.z.pow(2) * (1 - cosR)) * z).toFloat()
        )
    }

    fun rotate(angle: Float, axis: Point3F, origin: Point3F = origin()): Point3F {
        return (this - origin).rotate(angle, axis) + origin
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
