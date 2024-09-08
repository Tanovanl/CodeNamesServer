package org.example.codenames;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.example.codenames.api.web.Request.CreateGameRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CodeNamesApplicationTests {

        @Autowired
        TestRestTemplate restTemplate;

        @Test
        void shouldReturnAGameWhenGameIsCreated(){
                CreateGameRequest request = new CreateGameRequest("test", "01", "Tano");

                ResponseEntity<String> response = restTemplate.postForEntity("/game", request, String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

                DocumentContext documentContext = JsonPath.parse(response.getBody());

                String gameId = documentContext.read("$.game.gameId");
                List<String> players = documentContext.read("$.game.players");
                String playerName = documentContext.read("$.playerName");

                assertThat(gameId).isEqualTo("test-01");
                assertThat(players).containsExactly("Tano");
                assertThat(playerName).isEqualTo("Tano");
        }

        @Test
        @DirtiesContext
        void shouldReturnErrorBecauseGameExists(){
                CreateGameRequest request = new CreateGameRequest("test", "01", "Tano");

                ResponseEntity<String> response = restTemplate.postForEntity("/game", request, String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

                response = restTemplate.postForEntity("/game", request, String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }
}