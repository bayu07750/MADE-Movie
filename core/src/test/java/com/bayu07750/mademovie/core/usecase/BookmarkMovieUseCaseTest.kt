package com.bayu07750.mademovie.core.usecase

import com.bayu07750.mademovie.core.domain.model.Movie
import com.bayu07750.mademovie.core.domain.usecase.AddBookmarkedMovieUseCase
import com.bayu07750.mademovie.core.domain.usecase.BookmarkMovieUseCase
import com.bayu07750.mademovie.core.domain.usecase.DeleteBookmarkedMovieUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class BookmarkMovieUseCaseTest : BaseUseCaseTest() {

    @Mock
    private lateinit var addBookmarkedMovieUseCase: AddBookmarkedMovieUseCase
    @Mock
    private lateinit var deleteBookmarkedMovieUseCase: DeleteBookmarkedMovieUseCase

    private lateinit var bookmarkMovieUseCase: BookmarkMovieUseCase

    @Before
    fun setUp() {
        bookmarkMovieUseCase = BookmarkMovieUseCase(
            addBookmarkedMovieUseCase, deleteBookmarkedMovieUseCase
        )
    }

    @Test
    fun `add to bookmark`() = runTest {
        val movie = Movie(0, "", false)
        bookmarkMovieUseCase.invoke(movie)

        Mockito.verify(addBookmarkedMovieUseCase).invoke(movie)
    }

    @Test
    fun `delete from bookmark`() = runTest {
        val movie = Movie(0, "", true)
        bookmarkMovieUseCase.invoke(movie)

        Mockito.verify(deleteBookmarkedMovieUseCase).invoke(movie)
    }
}