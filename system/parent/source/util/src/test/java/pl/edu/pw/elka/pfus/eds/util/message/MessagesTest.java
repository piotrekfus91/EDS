package pl.edu.pw.elka.pfus.eds.util.message;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class MessagesTest {
    private Messages messages;

    @BeforeMethod
    private void beforeMethod() {
        messages = new ListMessages();
    }

    @Test
    public void shouldBeEmptyAtStart() throws Exception {
        assertThat(messages.isEmpty()).isTrue();
        assertThat(messages).isEmpty();
        assertThat(messages.size()).isEqualTo(0);
    }

    @Test
    public void shouldNotBeEmptyAfterPost() throws Exception {
        messages.postMessage(MessageType.ALERT, "text");

        assertThat(messages.isEmpty()).isFalse();
        assertThat(messages.size()).isEqualTo(1);
    }

    @Test
    public void testContainment() throws Exception {
        Message message = MessageFactory.getMessage(MessageType.ALERT, "text");
        Message message2 = MessageFactory.getMessage(MessageType.ALERT, "text");

        messages.postMessage(message);
        messages.postMessage(message2);

        assertThat(messages.size()).isEqualTo(2);
        assertThat(messages).contains(message, message2);
    }

    @Test
    public void testClearing() throws Exception {
        assertThat(messages).isEmpty();

        Message message = MessageFactory.getMessage(MessageType.ALERT, "text");
        messages.postMessage(message);
        assertThat(messages).isNotEmpty();

        messages.clear();
        assertThat(messages).isEmpty();
    }
}
