import com.example.Order;
import com.example.OrderRepository;
import com.example.OrderService;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    private OrderService orderService;
    private OrderRepository orderRepository;

    @BeforeAll
    static void printInfo() {
        System.out.println("Начало теста");
    }

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        orderService = new OrderService(orderRepository);
    }

    @Test
    void SuccessProcessOrder() {
        //Arrange
        Order order = new Order(1, "car", 1, 1000);
        when(orderRepository.saveOrder(order)).thenReturn(1);

        //Act

        String result = orderService.processOrder(order);

        //Assert
        assertEquals("success", result);

        //Verify
        verify(orderRepository, times(1)).saveOrder(order);

    }

    @Test
    void FailProcessOrder() {
        //Arrange
        Order order = new Order(2, "ipod", 1, 1200);
        when(orderRepository.saveOrder(order)).thenReturn(0);

        //Act

        String result = orderService.processOrder(order);

        //Assert
        assertEquals("fail", result);

        //Verify
        verify(orderRepository, times(1)).saveOrder(order);

    }

    @Test
    void exeptionProcessOrder() {
        //Arrange
        Order order = new Order(3, "iphone", 1, 3000);
        when(orderRepository.saveOrder(order)).thenThrow(new RuntimeException("Data base error"));

        //Act
        String result = orderService.processOrder(order);

        //Assert
        assertEquals("fail", result);

        //Verify
        verify(orderRepository, times(1)).saveOrder(order);
    }

    @Test
    void NullProcessOrder() {


        String result = orderService.processOrder(null);

        assertEquals("fail", result);

        verify(orderRepository, never()).saveOrder(any());

    }

    @Test
    void successProcessCalculator() {

        int orderId = 1;
        Order mockOrder = new Order(3, "iphone", 3, 100);
        when(orderRepository.getOrderById(orderId)).thenReturn(Optional.of(mockOrder));

        double result = orderService.calculateTotal(orderId);

        assertEquals(300, result);
        verify(orderRepository, times(1)).getOrderById(orderId);


    }

    @Test
    void emptyProcessCalculator() {
        int orderId = 2;

        when(orderRepository.getOrderById(orderId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> orderService.calculateTotal(orderId));

        assertEquals("Заказ с ID2Не найден", exception.getMessage());

        verify(orderRepository, times(1)).getOrderById(orderId);
    }

    @Test
    void nullProcessCalculator() {
        int orderId = 3;
        Order mockOrder = new Order(3, "iphone", 0, 0);

        when(orderRepository.getOrderById(orderId)).thenReturn(Optional.of(mockOrder));

        double result = orderService.calculateTotal(orderId);

        assertEquals(0, result);
        verify(orderRepository, times(1)).getOrderById(orderId);

    }

    @AfterAll
    static void printInfoEnd() {
        System.out.println("конец теста");
    }


}
