package net.iesochoa.silvia.projecto_intermodular.viewmodel

import io.mockk.coEvery
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
class AuthViewModelTest {

    private lateinit var viewModel: AuthViewModel
    private val authRepository: AuthRepository = mockk()
    
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        coEvery { authRepository.getUser() } returns flowOf(null)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * CP-19.1: login_handles_unauthorized_exception
     * Simula una respuesta 401 (Unauthorized) y verifica que el ViewModel maneje el error de sesión expirada.
     */
    @Test
    fun `login handles unauthorized exception`() = runTest {
        val email = "test@example.com"
        val password = "password"
        coEvery { authRepository.login(email, password) } throws Exception("Unauthorized")

        viewModel = AuthViewModel(authRepository)
        viewModel.login(email, password)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("Unauthorized", viewModel.uiState.value.error)
        assertEquals(false, viewModel.uiState.value.isAuthenticated)
    }

    /**
     * CP-05.2: register_updates_auth_state_on_success
     * Verifica que tras un registro exitoso, el estado de autenticación cambie a verdadero.
     */
    @Test
    fun `register updates auth state on success`() = runTest {
        coEvery { authRepository.register(any(), any()) } returns User(id = 1)

        viewModel = AuthViewModel(authRepository)
        viewModel.register("test@example.com", "password123")
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(true, viewModel.uiState.value.isAuthenticated)
    }
}
