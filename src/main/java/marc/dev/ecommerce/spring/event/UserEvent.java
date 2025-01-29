package marc.dev.ecommerce.spring.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import marc.dev.ecommerce.spring.Entity.UserEntity;
import marc.dev.ecommerce.spring.enumeration.EventType;


import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class UserEvent {
    private UserEntity user;
    private EventType type;
    private Map<?,?> data;
}
