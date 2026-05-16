package net.iesochoa.silvia.projecto_intermodular.viewmodel

import io.mockk.coEvery
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
class RegisterViewModelTest {

    private lateinit var viewModel: RegisterViewModel
    private val authRepository: AuthRepository = mockk()
    
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = RegisterViewModel(authRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * CP-03: register_with_invalid_email_sets_error
     * Verifica que la validación de formato de correo electrónico funcione correctamente.
     */
    @Test
    fun `register with invalid email sets error`() = runTest {
        viewModel.onEmailChange("invalid-email")
        viewModel.register { }
        
        assertEquals("El formato del email no es válido", viewModel.uiState.value.errorMessage)
    }

    /**
     * CP-04: register_with_short_password_sets_error
     * Comprueba que se impida el registro si la contraseña no cumple la longitud mínima de seguridad.
     */
    @Test
    fun `register with short password sets error`() = runTest {
        viewModel.onEmailChange("test@example.com")
        viewModel.onPasswordChange("123")
        viewModel.register { }
        
        assertEquals("La contraseña debe tener al menos 8 caracteres", viewModel.uiState.value.errorMessage)
    }

    /**
     * CP-05: register_with_mismatching_passwords_sets_error
     * Valida que el sistema detecte si las contraseñas introducidas en el registro no coinciden.
     */
    @Test
    fun `register with mismatching passwords sets error`() = runTest {
        viewModel.onEmailChange("test@example.com")
        viewModel.onPasswordChange("password123")
        viewModel.onConfirmPasswordChange("different")
        viewModel.register { }
        
        assertEquals("Las contraseñas no coinciden", viewModel.uiState.value.errorMessage)
    }

    /**
     * CP-05.1: register_success_calls_onSuccess
     * Verifica que el registro de usuario se complete con éxito y llame al callback de navegación.
     */
    @Test
    fun `register success calls onSuccess`() = runTest {
        val email = "test@example.com"
        val password = "password123"
        var successCalled = false
        
        coEvery { authRepository.register(email, password) } returns User(id = 1, email = email)

        viewModel.onEmailChange(email)
        viewModel.onPasswordChange(password)
        viewModel.onConfirmPasswordChange(password)
        viewModel.register { successCalled = true }
        
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(true, successCalled)
        assertEquals(false, viewModel.uiState.value.isLoading)
    }
}
