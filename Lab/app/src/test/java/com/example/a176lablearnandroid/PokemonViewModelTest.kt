package com.example.a176lablearnandroid

import com.example.a176lablearnandroid.utils.PokedexResponse
import com.example.a176lablearnandroid.utils.PokemonEntry
import com.example.a176lablearnandroid.utils.PokemonSpecies
import com.example.a176lablearnandroid.utils.PokemonNetwork
import com.example.a176lablearnandroid.utils.PokemonApiService
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonViewModelTest {

    private lateinit var viewModel: PokemonViewModel
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var mockApiService: PokemonApiService

    @Before
    fun setUp() {
        // จำกัด Coroutine Dispatchers บน Unit Test (เปลี่ยน Main dispatcher เป็น Test)
        Dispatchers.setMain(testDispatcher)

        // สร้าง Mock สำหรับ API Service
        mockApiService = mockk()

        // สร้าง Mock สำหรับ Singleton Object ของ Network (ใช้ mockkObject)
        mockkObject(PokemonNetwork)
        every { PokemonNetwork.api } returns mockApiService
    }

    @After
    fun tearDown() {
        // เคลียร์ Main dispatcher และคืนค่า Mock object
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun fetchPokemon_success_updatesPokemonList() = runTest {
        // Arrange: เตรียมข้อมูลปลอมจำลอง API
        val mockEntries = listOf(
            PokemonEntry(1, PokemonSpecies("bulbasaur", "url1")),
            PokemonEntry(2, PokemonSpecies("ivysaur", "url2"))
        )
        val mockResponse = PokedexResponse(mockEntries)
        coEvery { mockApiService.getKantoPokedex() } returns mockResponse

        // Act: เริ่มสร้าง ViewModel (init จะเรียก fetchPokemon แบบ Coroutine)
        viewModel = PokemonViewModel()
        
        // รอให้ Coroutine บน Dispatcher ทำงานเสร็จ
        testDispatcher.scheduler.advanceUntilIdle() 

        // Assert: ตรวจสอบความถูกต้องว่า stateflow เก็บโปเกมอนที่ API ส่งมาจริงๆ
        val result = viewModel.pokemonList.value
        assertEquals(2, result.size)
        assertEquals("bulbasaur", result[0].pokemon_species.name)
        assertEquals("ivysaur", result[1].pokemon_species.name)
    }
    
    @Test
    fun fetchPokemon_error_listRemainsEmpty() = runTest {
        // Arrange: จำลองการยิง API พัง
        coEvery { mockApiService.getKantoPokedex() } throws Exception("Network Error")

        // Act
        viewModel = PokemonViewModel()
        testDispatcher.scheduler.advanceUntilIdle() 

        // Assert: ควรดัก Catch Exception แต่อย่างน้อย list ควรว่างเหมือนตอนเริ่มต้น (empty list)
        val result = viewModel.pokemonList.value
        assertEquals(0, result.size)
    }
}
