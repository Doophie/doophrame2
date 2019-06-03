package ca.doophie.doophrame2.objectManagment

class DoophSubscription<ObjectType: Any>(watchableObject: ObjectType) {

    private var subscribers: ArrayList<((ObjectType)->Unit)?> = ArrayList()

    private var subscribableObject = watchableObject
        set(value) {
            field = value

            subscribers.forEach {
                it?.invoke(value)
            }
        }

    fun subscribe(onDataChange: (ObjectType)->Unit) {
        subscribers.add(onDataChange)

        subscribers = ArrayList( subscribers.filter { it != null } )

        onDataChange(subscribableObject)
    }

    fun updateData(`object`: ObjectType) {
        subscribableObject = `object`
    }

}