package net.iesochoa.silvia.projecto_intermodular.viewmodel

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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
class ProfileViewModelTest {

    private lateinit var viewModel: ProfileViewModel
    private val authRepository: AuthRepository = mockk()
    
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        coEvery { authRepository.getUser() } returns flowOf(User(id = 1, email = "test@example.com", name = "Test User"))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * CP-18: observeUser_updates_state_with_user_data
     * Verifica que al cargar el perfil se obtengan correctamente el nombre y email del usuario.
     */
    @Test
    fun `observeUser updates state with user data`() = runTest {
        viewModel = ProfileViewModel(authRepository)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("Test User", viewModel.uiState.value.username)
        assertEquals("test@example.com", viewModel.uiState.value.email)
    }

    /**
     * CP-18.1: updatePassword_with_mismatching_passwords_sets_error
     * Comprueba que si las contraseñas nuevas no coinciden se muestra un error de validación.
     */
    @Test
    fun `updatePassword with mismatching passwords sets error`() = runTest {
        viewModel = ProfileViewModel(authRepository)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.onCurrentPasswordChange("old")
        viewModel.onNewPasswordChange("newpassword123")
        viewModel.onRepeatPasswordChange("different")
        
        viewModel.updatePassword { }

        assertEquals("Las contraseñas no coinciden", viewModel.uiState.value.errorMessage)
    }

    /**
     * CP-18.2: updatePassword_success_calls_repository_and_onSuccess
     * Valida que el cambio de contraseña se envíe correctamente al repositorio tras pasar las validaciones.
     */
    @Test
    fun `updatePassword success calls repository and onSuccess`() = runTest {
        var successCalled = false
        coEvery { authRepository.changePassword("old", "newpassword123") } returns Unit

        viewModel = ProfileViewModel(authRepository)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.onCurrentPasswordChange("old")
        viewModel.onNewPasswordChange("newpassword123")
        viewModel.onRepeatPasswordChange("newpassword123")
        
        viewModel.updatePassword { successCalled = true }
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(true, successCalled)
        coVerify { authRepository.changePassword("old", "newpassword123") }
    }

    /**
     * CP-19: logout_calls_repository_and_success_callback
     * Verifica que la acción de cerrar sesión limpie los datos y ejecute la navegación de salida.
     */
    @Test
    fun `logout calls repository and success callback`() = runTest {
        var logoutCalled = false
        coEvery { authRepository.logout() } returns Unit

        viewModel = ProfileViewModel(authRepository)
        viewModel.logout { logoutCalled = true }
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(true, logoutCalled)
        coVerify { authRepository.logout() }
    }
}
