package scene

import geometry.Point3F

class Camera(
    val position: Point3F,
    lookAt: Point3F,
    planeNormal: Point3F,
    distanceToScreen: Float = 1f,
    width: Int,
    height: Int
) {
    val screen: Screen

    init {
        val diff = lookAt - position
        val tmp = diff.cross(planeNormal)

        val z = 1f
        val y = -z * (diff.x * tmp.z - tmp.x * diff.z) / (diff.x * tmp.y - tmp.x * diff.y)
        val x = (-tmp.y * y - tmp.z * z) / tmp.x
        val dirTop = Point3F(x, y, z).normalized()
        if (dirTop.dot(planeNormal) < 0) {
            dirTop.set(dirTop * -1f)
        }

        val ratio = width / height.toFloat()
        val screenCenter = position + (lookAt - position).normalized() * distanceToScreen
        val scaledLeft = dirTop.cross(lookAt - position).normalized()
        val scaledTop = dirTop * ( 1f / ratio)
        screen = Screen(
            topLeft = screenCenter + scaledLeft + scaledTop,
            topRight = screenCenter - scaledLeft + scaledTop,
            bottomLeft = screenCenter + scaledLeft - scaledTop,
            width = width,
            height = height
        )
    }
}