package com.catchmate.presentation.view.components

import java.time.LocalDate

sealed interface FilterSheetType {
    data class Date(val initialDate: LocalDate?) : FilterSheetType
    data class Club(val initialClubIds: List<Int>) : FilterSheetType
    data class Member(val initialCount: String) : FilterSheetType
}
