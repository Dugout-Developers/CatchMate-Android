package com.catchmate.domain

import java.io.IOException

class LiftUpFailureException(
    message: String,
) : IOException(message)