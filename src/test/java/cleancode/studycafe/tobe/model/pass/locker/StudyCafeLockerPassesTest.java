package cleancode.studycafe.tobe.model.pass.locker;

import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class StudyCafeLockerPassesTest {

    @Test
    @DisplayName("pass에 대한 lockerPass 리스트를 출력한다.")
    void findLockerPassBySeatPass(){
        //given
        StudyCafeLockerPass lockerPass1 = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 4, 10000);
        StudyCafeLockerPass lockerPass2 = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 12, 30000);

        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED,4,250000,0.1);

        List<StudyCafeLockerPass> list = Arrays.asList(lockerPass1, lockerPass2);
        StudyCafeLockerPasses lockerPasses = StudyCafeLockerPasses.of(list);

        //when
        Optional<StudyCafeLockerPass> findLockerPasses = lockerPasses.findLockerPassBy(seatPass);

        //then
        assertTrue(findLockerPasses.isPresent());
        assertEquals(lockerPass1, findLockerPasses.get());

    }

}