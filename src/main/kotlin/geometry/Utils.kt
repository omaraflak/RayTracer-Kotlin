package geometry

operator fun Float.times(p: Point3F): Point3F {
    return p.times(this)
}
