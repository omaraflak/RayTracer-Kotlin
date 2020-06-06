package scene

import geometry.Point3F

class Camera(
    val position: Point3F,
    private val lookAt: Point3F,
    private val planeNormal: Point3F,
    private val distanceToScreen: Float = 1f,
    private val width: Int,
    private val height: Int
) {
    lateinit var screen: Screen

    init {
        updateScreen()
    }

    fun updatePosition(newPosition: Point3F) {
        position.set(newPosition)
        updateScreen()
    }

    private fun updateScreen() {
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