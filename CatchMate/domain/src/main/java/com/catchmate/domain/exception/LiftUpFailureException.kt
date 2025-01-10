package com.catchmate.domain.exception

import java.io.IOException

class LiftUpFailureException(
    message: String,
) : IOException(message)
