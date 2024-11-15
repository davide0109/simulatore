package agricole.simulatore.mutuoCard.utils.messaging;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {

    private String message;

    private int status;

    private String timestamp;
}