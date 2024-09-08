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

        @Test
        void shouldReturnErrorBecausePlayerExists(){
                CreateGameRequest request = new CreateGameRequest("test", "01", "Tano");

                ResponseEntity<String> response = restTemplate.postForEntity("/game", request, String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

                request = new CreateGameRequest("test", "01", "Tano");

                response = restTemplate.postForEntity("/game/test-01/join", request, String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }


        @Test
        void shouldReturnErrorBecauseGameDoesNotExist(){
                CreateGameRequest request = new CreateGameRequest("test", "01", "Tano");

                ResponseEntity<String> response = restTemplate.postForEntity("/game/thisdoesntexist/join", request, String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }

        @Test
        void shouldReturnErrorBecauseGameIsFull(){
                CreateGameRequest request = new CreateGameRequest("test", "01", "Tano1");

                ResponseEntity<String> response = restTemplate.postForEntity("/game", request, String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

                request = new CreateGameRequest("test", "01", "Tano2");

                response = restTemplate.postForEntity("/game/test-01/join", request, String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

                request = new CreateGameRequest("test", "01", "Tano3");

                response = restTemplate.postForEntity("/game/test-01/join", request, String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

                request = new CreateGameRequest("test", "01", "Tano4");

                response = restTemplate.postForEntity("/game/test-01/join", request, String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);


                request = new CreateGameRequest("test", "01", "Tano");

                response = restTemplate.postForEntity("/game/test-01/join", request, String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }
}