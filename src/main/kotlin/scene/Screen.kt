package scene

import geometry.Point3F

class Screen(
    val topLeft: Point3F,
    val bottomLeft: Point3F,
    val topRight: Point3F,
    val width: Int,
    val height: Int
)
