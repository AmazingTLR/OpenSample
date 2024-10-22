package com.amazingtlr.usecase.user

import com.amazingtlr.api.user.UserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class UserListUseCaseTest {
    private val mockUserRepository: UserRepository = mockk {
        every { observeUsers(any()) } returns mockk()
    }
    private val sut: UserListUseCase = UserListUseCase(mockUserRepository)

    @Test
    fun `When userListUseCase is invoked, the userRepository is used`() {
        val fakeLastUserLogin = "userLogin"

        sut.invoke(fakeLastUserLogin)

        verify(exactly = 1) {
            mockUserRepository.observeUsers(fakeLastUserLogin)
        }
    }
}