package net.iesochoa.silvia.projecto_intermodular.viewmodel

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import net.iesochoa.silvia.projecto_intermodular.data.AuthRepository
import net.iesochoa.silvia.projecto_intermodular.data.User
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel
    private val authRepository: AuthRepository = mockk()
    
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = LoginViewModel(authRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * CP-01: login_with_empty_fields_sets_error_message
     * Verifica que si los campos están vacíos, se establece el mensaje de error correspondiente.
     */
    @Test
    fun `login with empty fields sets error message`() = runTest {
        viewModel.onEmailChange("")
        viewModel.onPasswordChange("")
        
        viewModel.login { }

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals("Email y contraseña son obligatorios", state.errorMessage)
        }
    }

    /**
     * CP-02: login_success_calls_onSuccess_callback
     * Verifica que un inicio de sesión exitoso invoque el callback de éxito.
     */
    @Test
    fun `login success calls onSuccess callback`() = runTest {
        val email = "test@example.com"
        val password = "password123"
        var successCalled = false
        
        coEvery { authRepository.login(email, password) } returns User(id = 1, email = email)

        viewModel.onEmailChange(email)
        viewModel.onPasswordChange(password)
        viewModel.login { successCalled = true }
        
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(true, successCalled)
        coVerify { authRepository.login(email, password) }
    }

    /**
     * CP-XX: login_failure_sets_error_message
     * Verifica que un error en la autenticación se refleje en el estado de la UI.
     */
    @Test
    fun `login failure sets error message`() = runTest {
        val email = "test@example.com"
        val password = "wrong"
        
        coEvery { authRepository.login(email, password) } throws Exception("Invalid credentials")

        viewModel.onEmailChange(email)
        viewModel.onPasswordChange(password)
        viewModel.login { }
        
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals("Invalid credentials", state.errorMessage)
            assertEquals(false, state.isLoading)
        }
    }
}
