package S;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegistrationServiceTest implements WithAssertions {
    @Test
    void constructor_whenInitialized_totalIsEqualToZero() {

        assertThrows(NullPointerException.class, () -> {RegistrationService registrationService = new RegistrationService(null, null, null);});
    }
}



