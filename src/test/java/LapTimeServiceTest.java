import com.foxminded.LapTimeService;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LapTimeServiceTest {

    private final LapTimeService lapTimeService = new LapTimeService();

    @Test
    void nullTest() throws IOException {
        try {
            lapTimeService.raceAnalyzer(null, "gdgd" , null);
            fail("Expected exception not thrown");
        } catch (IllegalArgumentException nullObject) {
        }
    }

    @Test
    void wrongNameFile() throws  IOException{
        try {
            lapTimeService.raceAnalyzer("wrongName", "end.log", "wrongname2.txt");
            fail("Expected exception not thrown");
        } catch (FileNotFoundException exception) {
        }
    }

    @Test
    void inputTest() throws IOException {
        String expected = """
                1|Sebastian Vettel         |FERRARI                   |01:04.415
                2|Daniel Ricciardo         |RED BULL RACING TAG HEUER |01:12.013
                3|Valtteri Bottas          |MERCEDES                  |01:12.434
                4|Lewis Hamilton           |MERCEDES                  |01:12.460
                5|Stoffel Vandoorne        |MCLAREN RENAULT           |01:12.463
                6|Kimi Raikkonen           |FERRARI                   |01:12.639
                7|Fernando Alonso          |MCLAREN RENAULT           |01:12.657
                8|Sergey Sirotkin          |WILLIAMS MERCEDES         |01:12.706
                9|Charles Leclerc          |SAUBER FERRARI            |01:12.829
                10|Sergio Perez            |FORCE INDIA MERCEDES      |01:12.848
                11|Romain Grosjean         |HAAS FERRARI              |01:12.930
                12|Pierre Gasly            |SCUDERIA TORO ROSSO HONDA |01:12.941
                13|Carlos Sainz            |RENAULT                   |01:12.950
                14|Esteban Ocon            |FORCE INDIA MERCEDES      |01:13.028
                15|Nico Hulkenberg         |RENAULT                   |01:13.065
                ----------------------------------------------------------------
                16|Brendon Hartley         |SCUDERIA TORO ROSSO HONDA |01:13.179
                17|Marcus Ericsson         |SAUBER FERRARI            |01:13.265
                18|Lance Stroll            |WILLIAMS MERCEDES         |01:13.323
                19|Kevin Magnussen         |HAAS FERRARI              |01:13.393
                """;
        assertEquals(expected, lapTimeService.raceAnalyzer("start.log", "end.log" , "abbreviations.txt"));
    }

}
