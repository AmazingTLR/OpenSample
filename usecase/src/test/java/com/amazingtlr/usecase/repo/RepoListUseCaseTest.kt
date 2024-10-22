package com.amazingtlr.usecase.repo

import com.amazingtlr.api.user.UserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class RepoListUseCaseTest {
    private val mockUserRepository: UserRepository = mockk {
        every { observeRepoByUserLogin(any(), any()) } returns mockk()
    }
    private val sut: RepoListUseCase = RepoListUseCase(mockUserRepository)


    @Test
    fun `When repoListUseCase is invoked, the userRepository is used`() {
        val fakeUserLogin = "userLogin"
        val fakeRepoPage = "1"

        sut.invoke(fakeUserLogin, fakeRepoPage)

        verify(exactly = 1) {
            mockUserRepository.observeRepoByUserLogin(fakeUserLogin, fakeRepoPage)
        }
    }
}