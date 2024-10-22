package com.amazingtlr.usecase.user

import com.amazingtlr.api.user.UserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class UserSingleUseCaseTest {
    private val mockUserRepository: UserRepository = mockk {
        every { observeUserByUserLogin(any()) } returns mockk()
    }
    private val sut: UserSingleUseCase = UserSingleUseCase(mockUserRepository)

    @Test
    fun `When userSingleUseCase is invoked, the userRepository is used`() {
        val fakeUserLogin = "userLogin"

        sut.invoke(fakeUserLogin)

        verify(exactly = 1) {
            mockUserRepository.observeUserByUserLogin(fakeUserLogin)
        }
    }
}