package com.example.studywisely.data.model

data class RoutineVM(
    val id: Int? = null,
    val title: String = "",
    val description: String = "",

    // Date/heure de la routine (révision)
    val routineDateTimeMillis: Long? = null,

    // Date/heure de l'examen ou livrable prévu
    val examDateTimeMillis: Long? = null,

    // Priorité (calculée selon examDateTimeMillis, mais stockée aussi)
    val priority: PriorityType = PriorityType.Moyenne
) {
    companion object{
        fun fromEntity(entity: RoutineEntity): RoutineVM {
            return RoutineVM(
                id = entity.id,
                title = entity.title,
                description = entity.description ?: "",
                routineDateTimeMillis = entity.routineDateTimeMillis,
                examDateTimeMillis = entity.examDateTimeMillis,
                priority = entity.priority
            )
        }

        fun toEntity(vm: RoutineVM): RoutineEntity {
            return RoutineEntity(
                id = vm.id,
                title = vm.title,
                description = vm.description.ifEmpty { null },
                routineDateTimeMillis = vm.routineDateTimeMillis,
                examDateTimeMillis = vm.examDateTimeMillis,
                priority = vm.priority
            )
        }
    }
}