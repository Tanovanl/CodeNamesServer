package org.example.codenames;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.example.codenames.api.web.Request.CreateGameRequest;
import org.example.codenames.api.web.Request.RoleJoinRequest;
import org.example.codenames.api.web.Request.StartGameRequest;
import org.example.codenames.api.web.Request.TeamJoinRequest;
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
        @DirtiesContext
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
        @DirtiesContext
        void shouldReturnErrorBecausePlayerExists(){
                CreateGameRequest request = new CreateGameRequest("test", "01", "Tano");

                ResponseEntity<String> response = restTemplate.postForEntity("/game", request, String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

                request = new CreateGameRequest("test", "01", "Tano");

                response = restTemplate.postForEntity("/game/test-01/join", request, String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }


        @Test
        @DirtiesContext
        void shouldReturnErrorBecauseGameDoesNotExist(){
                CreateGameRequest request = new CreateGameRequest("test", "01", "Tano");

                ResponseEntity<String> response = restTemplate.postForEntity("/game/thisdoesntexist/join", request, String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }

        @Test
        @DirtiesContext
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

        @Test
        @DirtiesContext
        void shouldJoinTeamRedAsSpymaster() {
                CreateGameRequest request = new CreateGameRequest("test", "01", "Tano");
                restTemplate.postForEntity("/game", request, String.class);

                TeamJoinRequest teamRedJoinRequest = new TeamJoinRequest("RED", "SPYMASTER", "Tano");
                ResponseEntity<String> response = restTemplate.postForEntity("/game/test-01/player/team", teamRedJoinRequest, String.class);

                DocumentContext documentContext = JsonPath.parse(response.getBody());

                String team = documentContext.read("$.team");
                String role = documentContext.read("$.role");
                String playerName = documentContext.read("$.playerName");

                assertThat(team).isEqualTo("RED");
                assertThat(role).isEqualTo("SPYMASTER");
                assertThat(playerName).isEqualTo("Tano");
        }

        @Test
        @DirtiesContext
        void shouldJoinTeamBlueAsOperative() {
                CreateGameRequest request = new CreateGameRequest("test", "01", "Tano");
                restTemplate.postForEntity("/game", request, String.class);

                request = new CreateGameRequest("test", "01", "Tano2");
                restTemplate.postForEntity("/game/test-01/join", request, String.class);

                TeamJoinRequest teamBlueJoinRequest = new TeamJoinRequest("BLUE", "OPERATIVE", "Tano");
                ResponseEntity<String> response = restTemplate.postForEntity("/game/test-01/player/team", teamBlueJoinRequest, String.class);

                DocumentContext documentContext = JsonPath.parse(response.getBody());

                String team = documentContext.read("$.team");
                String role = documentContext.read("$.role");
                String playerName = documentContext.read("$.playerName");

                assertThat(team).isEqualTo("BLUE");
                assertThat(role).isEqualTo("OPERATIVE");
                assertThat(playerName).isEqualTo("Tano");
        }

        @Test
        @DirtiesContext
        void shouldNotAllowInvalidTeamJoinRequests() {
                CreateGameRequest request = new CreateGameRequest("test", "01", "Tano");
                restTemplate.postForEntity("/game", request, String.class);

                request = new CreateGameRequest("test", "01", "Tano2");
                restTemplate.postForEntity("/game/test-01/join", request, String.class);


                // Invalid team
                TeamJoinRequest teamBlueJoinRequest = new TeamJoinRequest("BLUE", "SPYMASTER2", "Tano2");
                ResponseEntity<String> response = restTemplate.postForEntity("/game/test-01/player/team", teamBlueJoinRequest, String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

                // Invalid role
                teamBlueJoinRequest = new TeamJoinRequest("BLUE2", "SPYMASTER", "Tano2");
                response = restTemplate.postForEntity("/game/test-01/player/team", teamBlueJoinRequest, String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

                // Invalid player
                teamBlueJoinRequest = new TeamJoinRequest("BLUE", "SPYMASTER", "Tano3");
                response = restTemplate.postForEntity("/game/test-01/player/team", teamBlueJoinRequest, String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }
}