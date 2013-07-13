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

    @Test
    public void testTwoMessagesCompareDates() throws Exception {
        Message message = MessageFactory.getMessage(MessageType.ALERT, "text1");
        Message message2 = MessageFactory.getMessage(MessageType.ALERT, "text2");

        assertThat(message.getDate().before(message2.getDate()));
    }
}
