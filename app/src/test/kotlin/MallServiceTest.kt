import com.example.mall.model.Commodity
import com.example.mall.model.CustomerAddress
import com.example.mall.model.Order
import com.example.mall.model.ResponseWrapper
import com.example.mall.service.MallService
import com.google.gson.GsonBuilder
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BffApiServiceTest {
    private var mockWebServer: MockWebServer = MockWebServer()

    private var mockService: MallService = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MallService::class.java)

    @After
    fun after() {
        mockWebServer.shutdown()
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.getResourceAsStream("/api-response/$fileName")
        val source = inputStream!!.source().buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(mockResponse.setBody(source.readString(Charsets.UTF_8)))
    }

    @Test
    fun `should get commodity list when execute getCommodityList`() = runBlocking {
        // given
        enqueueResponse("getCommodityListResponse")
        val expectedFirstCommodity = Commodity(
            "320000198007298438",
            "iPhone 12 Pro Max",
            "https://media.router-switch.com/media/catalog/product/cache/b90fceee6a5fa7acd36a04c7b968181c/i/p/iphone-12-pro.jpg",
            "Apple/苹果手机iPhone 12 Pro Max 手机",
            "5999"
        )

        // when
        val result = mockService.getCommodityList()

        // then
        val resultSize = result.data?.size
        assertEquals(3, resultSize)

        val firstCommodity = result.data?.first()
        assertEquals(expectedFirstCommodity, firstCommodity)

        val takeRequest = mockWebServer.takeRequest()
        Assert.assertThat(takeRequest.path, `is`("/commodity/list"))
        Assert.assertThat(takeRequest.method, `is`("GET"))
    }

    @Test
    fun `should create order success when execute createOrder`() = runBlocking {
        // given
        val body = Order(
            "1", 3, "1000", "3000",
            CustomerAddress("Mike", "18827368899", "湖北省武汉市洪山区保利时代")
        )
        val response: ResponseWrapper<HashMap<String, String>> =
            ResponseWrapper(200, data = hashMapOf("id" to "123"), null)
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200).setBody(GsonBuilder().create().toJson(response))
        )

        // when
        val result = mockService.createOrder(body)

        // then
        val resultId = result.data?.get("id")
        assertEquals("123", resultId)

        val takeRequest = mockWebServer.takeRequest()
        Assert.assertThat(takeRequest.path, `is`("/order/create"))
        Assert.assertThat(takeRequest.method, `is`("POST"))
    }
}