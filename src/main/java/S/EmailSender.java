package S;


public class EmailSender implements CommunicationSender {

    public void sendCommunicationToUser(User user) {
        System.out.println("📧 Sending confirmation email to: " + user.getEmail());
    }
}
