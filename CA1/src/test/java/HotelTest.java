import org.example.Hotel;
import org.example.Room;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class HotelTest {
    private Hotel hotel = new Hotel();

//    @BeforeEach
//     void init() {
//        this.hotel.initFromJson("CA1/data.json");
//    }

    @Test
    void test_getRooms() {
        Room room1 = new Room("1", 1);
        hotel.addRoom(room1);

        assertThat(hotel.getRooms(1)).containsOnly(room1);
    }

}
