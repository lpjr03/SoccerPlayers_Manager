package it.unimol.player_manager.app;

import it.unimol.player_manager.entity.Player;
import it.unimol.player_manager.exceptions.EmptyManagerException;
import it.unimol.player_manager.exceptions.PlayerExistsException;
import it.unimol.player_manager.exceptions.PlayerNotExistsException;
import it.unimol.player_manager.persistence.PostgreConnection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayersManagerTest {
    private PlayersManager manager;
    private MockedStatic<PostgreConnection> postgreConnectionMockedStatic;
    private PostgreConnection postgreConnectionMock;

    @BeforeEach
    void setUp() {
        // Reset singleton instance via reflection for isolation
        try {
            var field = PlayersManager.class.getDeclaredField("instance");
            field.setAccessible(true);
            field.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        manager = PlayersManager.getInstance();
        postgreConnectionMock = mock(PostgreConnection.class);
        postgreConnectionMockedStatic = Mockito.mockStatic(PostgreConnection.class);
        postgreConnectionMockedStatic.when(PostgreConnection::getInstance).thenReturn(postgreConnectionMock);
    }

    @AfterEach
    void tearDown() {
        postgreConnectionMockedStatic.close();
    }

    @Test
    void addPlayer_success() {
        Player player = mock(Player.class);
        when(player.getJerseyNumber()).thenReturn(10);
        boolean result = manager.addPlayer(player);
        assertTrue(result);
        verify(postgreConnectionMock).insertPlayer(player);
    }

    @Test
    void addPlayer_duplicateJersey_throwsException() {
        Player player1 = mock(Player.class);
        when(player1.getJerseyNumber()).thenReturn(7);
        manager.addPlayer(player1);
        Player player2 = mock(Player.class);
        when(player2.getJerseyNumber()).thenReturn(7);
        assertThrows(PlayerExistsException.class, () -> manager.addPlayer(player2));
    }

    @Test
    void addPlayer_null_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> manager.addPlayer(null));
    }

    @Test
    void removePlayer_success() {
        Player player = mock(Player.class);
        when(player.getJerseyNumber()).thenReturn(9);
        manager.addPlayer(player);
        boolean result = manager.removePlayer(9);
        assertTrue(result);
        verify(postgreConnectionMock).deletePlayer(9);
    }

    @Test
    void removePlayer_notExists_throwsException() {
        assertThrows(PlayerNotExistsException.class, () -> manager.removePlayer(99));
    }

    @Test
    void getPlayerByJersey_success() {
        Player player = mock(Player.class);
        when(player.getJerseyNumber()).thenReturn(5);
        manager.addPlayer(player);
        Player found = manager.getPlayerByJersey(5);
        assertNotNull(found);
        assertEquals(5, found.getJerseyNumber());
    }

    @Test
    void getPlayerByJersey_emptyManager_throwsException() {
        assertThrows(EmptyManagerException.class, () -> manager.getPlayerByJersey(1));
    }

    @Test
    void getPlayerByJersey_notFound_returnsNull() {
        Player player = mock(Player.class);
        when(player.getJerseyNumber()).thenReturn(3);
        manager.addPlayer(player);
        Player found = manager.getPlayerByJersey(99);
        assertNull(found);
    }
}
