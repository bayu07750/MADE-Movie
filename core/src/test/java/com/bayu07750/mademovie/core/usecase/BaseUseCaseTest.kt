package com.bayu07750.mademovie.core.usecase

import com.bayu07750.mademovie.core.domain.repository.MovieRepository
import org.mockito.Mock

open class BaseUseCaseTest {

    @Mock protected lateinit var movieRepository: MovieRepository
}