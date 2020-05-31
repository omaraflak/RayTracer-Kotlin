package scene

import geometry.Point3F

class Light(
    val position: Point3F,
    val intensity: Float = 1f,
    val ambient: Float = 0.5f
)