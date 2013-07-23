package pl.edu.pw.elka.pfus.eds.util.message;

import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class MessageFactoryTest {
    @Test
    public void testSampleMessage() throws Exception {
        Message message = MessageFactory.getMessage(MessageType.INFO, "text");

        assertThat(message.getType()).isEqualTo(MessageType.INFO);
        assertThat(message.getText()).isEqualTo("text");
        assertThat(message.getDate()).isNotNull();
    }
}
